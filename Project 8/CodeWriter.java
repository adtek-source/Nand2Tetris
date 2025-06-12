import java.io.FileWriter;

public class CodeWriter {
    private FileWriter writer;
    private String currentFileName = "";
    private int eqCount = 0;
    private int gtCount = 0;
    private int ltCount = 0;
    private int fnCount = 0;

    public CodeWriter(String fileName) {
        try {
            writer = new FileWriter(fileName);
            int firstIndex = fileName.lastIndexOf('\\');
            if (firstIndex != -1) {
                String directoryName = fileName.substring(0, firstIndex);
                int secondIndex = fileName.lastIndexOf('.');
                if (secondIndex != -1) {
                    currentFileName = fileName.substring(firstIndex + 1, secondIndex);
                    if (directoryName.equals(currentFileName)) writer.write("// Bootstrap code(function Sys.init 0)\n@261\nD=A\n@SP\nM=D\n");
                } // If the file is in a directory, we require the bootstrap code
            }
        } catch (Exception e) {System.err.println("Error opening file: " + e.getMessage());}
    }

    public void setFileName(String fileName) {
        currentFileName = fileName;
        int index = fileName.lastIndexOf('\\');
        if (index != -1) currentFileName = fileName.substring(index + 1);
        System.out.println("Translating file: " + fileName);
    }

    public void writeArithmetic(String command) {
        try {
            writer.write("// " + command + "\n@SP\n");
            switch (command) {
                case "add":
                    writer.write("AM=M-1\nD=M\nA=A-1\nM=D+M\n");
                    break;
                case "sub":
                    writer.write("AM=M-1\nD=M\nA=A-1\nM=M-D\n");
                    break;
                case "neg":
                    writer.write("A=M-1\nM=-M\n");
                    break;
                case "eq":
                    writer.write("AM=M-1\nD=M\nA=A-1\nD=M-D\nM=-1\n");
                    writer.write("@EQ_TRUE" + eqCount + "\n");
                    writer.write("D;JEQ\n@SP\nA=M-1\nM=!M\n");
                    writer.write("(EQ_TRUE" + eqCount + ")\n");
                    eqCount++;
                    break;
                case "gt":
                    writer.write("AM=M-1\nD=M\nA=A-1\nD=M-D\nM=-1\n");
                    writer.write("@GT_TRUE" + gtCount + "\n");
                    writer.write("D;JGT\n@SP\nA=M-1\nM=!M\n");
                    writer.write("(GT_TRUE" + gtCount + ")\n");
                    gtCount++;
                    break;
                case "lt":
                    writer.write("AM=M-1\nD=M\nA=A-1\nD=M-D\nM=-1\n");
                    writer.write("@LT_TRUE" + ltCount + "\n");
                    writer.write("D;JLT\n@SP\nA=M-1\nM=!M\n");
                    writer.write("(LT_TRUE" + ltCount + ")\n");
                    ltCount++;
                    break;
                case "and":
                    writer.write("AM=M-1\nD=M\nA=A-1\nM=D&M\n");
                    break;
                case "or":
                    writer.write("AM=M-1\nD=M\nA=A-1\nM=D|M\n");
                    break;
                default: // "not"
                    writer.write("A=M-1\nM=!M\n");
            }
        } catch (Exception e) {System.err.println("Error writing arithmetic command: " + e.getMessage());}
    }

    public void writePushPop(Command command, String segment, int index) {
        try {
            if (command == Command.C_PUSH) {
                writer.write("// push " + segment + " " + index + "\n");
                switch (segment) {
                    case "constant":
                        writer.write("@" + index + "\nD=A\n");
                        break;
                    case "local":
                        writer.write("@" + index + "\nD=A\n@LCL\nA=D+M\nD=M\n");
                        break;
                    case "argument":
                        writer.write("@" + index + "\nD=A\n@ARG\nA=D+M\nD=M\n");
                        break;
                    case "this":
                        writer.write("@" + index + "\nD=A\n@THIS\nA=D+M\nD=M\n");
                        break;
                    case "that":
                        writer.write("@" + index + "\nD=A\n@THAT\nA=D+M\nD=M\n");
                        break;
                    case "static":
                        writer.write("@" + currentFileName + "." + index + "\nD=M\n");
                        break;
                    case "temp":
                        writer.write("@R" + (5 + index) + "\nD=M\n");
                        break;
                    default: // "pointer"
                        if (index == 0) writer.write("@THIS\nD=M\n");
                        else if (index == 1) writer.write("@THAT\nD=M\n");
                }
                writer.write("@SP\nA=M\nM=D\n@SP\nM=M+1\n");
            } else {
                writer.write("// pop " + segment + " " + index + "\n");
                switch (segment) {
                    case "local":
                        writer.write("@" + index + "\nD=A\n@LCL\nD=D+M\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\n");
                        break;
                    case "argument":
                        writer.write("@" + index + "\nD=A\n@ARG\nD=D+M\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\n");
                        break;
                    case "this":
                        writer.write("@" + index + "\nD=A\n@THIS\nD=D+M\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\n");
                        break;
                    case "that":
                        writer.write("@" + index + "\nD=A\n@THAT\nD=D+M\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\n");
                        break;
                    case "static":
                        writer.write("@SP\nAM=M-1\nD=M\n@" + currentFileName + "." + index + "\n");
                        break;
                    case "temp":
                        writer.write("@SP\nAM=M-1\nD=M\n@R" + (5 + index) + "\n");
                        break;
                    default: // "pointer"
                        writer.write("@SP\nAM=M-1\nD=M\n");
                        if (index == 0) writer.write("@THIS\n");
                        else if (index == 1) writer.write("@THAT\n");
                }
                writer.write("M=D\n");
            }
        } catch (Exception e) {System.err.println("Error writing push command: " + e.getMessage());}
    }

    public void writeLabel(String label) {
        try {writer.write("// label " + label + "\n(" + label + ")\n");} 
        catch (Exception e) {System.err.println("Error writing label: " + e.getMessage());}
    }

    public void writeGoto(String label) {
        try {writer.write("// goto " + label + "\n@" + label + "\n0;JMP\n");} 
        catch (Exception e) {System.err.println("Error writing goto: " + e.getMessage());}
    }

    public void writeIf(String label) {
        try {writer.write("// if-goto " + label + "\n@SP\nAM=M-1\nD=M\n@" + label + "\nD;JNE\n");} 
        catch (Exception e) {System.err.println("Error writing if-goto: " + e.getMessage());}
    }

    public void writeFunction(String functionName, int numVars) {
        try {
            if (!functionName.equals("Sys.init")) {
                writer.write("// function " + functionName + " " + numVars + "\n(" + functionName + ")\n");
                for (int i = 0; i < numVars; i++) writePushPop(Command.C_PUSH, "local", 0);
            }
            
        } catch (Exception e) {System.err.println("Error writing function: " + e.getMessage());}
    }

    public void writeCall(String functionName, int numArgs) {
        try {
            if (!functionName.equals("Sys.init")) {
                writer.write("// call " + functionName + " " + numArgs + "\n");
                writer.write("@RET" + fnCount + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
                writer.write("@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
                writer.write("@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
                writer.write("@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
                writer.write("@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nMD=M+1\n");
                
                writer.write("@" + (numArgs + 5) + "\nD=D-A\n@ARG\nM=D\n");
                writer.write("@SP\nD=M\n@LCL\nM=D\n");
                writer.write("@" + functionName + "\n0;JMP\n");
                writer.write("(RET" + fnCount + ")\n");
                fnCount++;
            }
        } catch (Exception e) {System.err.println("Error writing call: " + e.getMessage());}
    }

    public void writeReturn() {
        try {
            writer.write("// return\n@LCL\nD=M\n@5\nA=D-A\nD=M\n@R14\nM=D\n");
            writer.write("@SP\nAM=M-1\nD=M\n@ARG\nA=M\nM=D\n");
            writer.write("@ARG\nD=M+1\n@SP\nM=D\n");

            writer.write("@LCL\nA=M-1\nD=M\n@THAT\nM=D\n");
            writer.write("@LCL\nD=M-1\nA=D-1\nD=M\n@THIS\nM=D\n");
            writer.write("@LCL\nD=M-1\n@2\nA=D-A\nD=M\n@ARG\nM=D\n");
            writer.write("@LCL\nD=M-1\n@3\nA=D-A\nD=M\n@LCL\nM=D\n");
            writer.write("@R14\nA=M\n0;JMP\n");
        } catch (Exception e) {System.err.println("Error writing return: " + e.getMessage());}
    }
    
    public void close() {
        try {writer.close();} 
        catch (Exception e) {System.err.println("Error closing file: " + e.getMessage());}
    }
}