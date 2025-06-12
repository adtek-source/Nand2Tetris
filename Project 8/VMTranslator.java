import java.io.File;
import java.util.ArrayList;

public class VMTranslator {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java VMTranslator <input file>.vm/<input directory>");
            return;
        } // Input should of the form java VMTranslator <input file>.vm or java VMTranslator <input directory>
        File inputFile = new File(args[0]);
        ArrayList<String> vmFiles = new ArrayList<>(); // List to store .vm files

        CodeWriter codeWriter;
        if (inputFile.isDirectory()) {
            for (File file : inputFile.listFiles()) {
                if (file.getName().endsWith(".vm")) vmFiles.add(file.getName());
            }
            codeWriter = new CodeWriter(args[0] + File.separator + args[0] + ".asm");
        } else if (inputFile.getName().endsWith(".vm")) {
            vmFiles.add(inputFile.getName());
            codeWriter = new CodeWriter(args[0].replace(".vm", ".asm"));
        } else {
            System.err.println("Invalid input: " + args[0]);
            return;
        }

        if (vmFiles.contains("Sys.vm")) {
            int sysIndex = vmFiles.indexOf("Sys.vm");
            String firstFile = vmFiles.get(0);
            vmFiles.set(0, vmFiles.get(sysIndex));
            vmFiles.set(sysIndex, firstFile);
        } // Swaping Sys.vm to be the first entry

        while (!vmFiles.isEmpty()) {
            Parser parser;
            if (inputFile.isDirectory()) {
                String vmFile = vmFiles.remove(0);
                parser = new Parser(args[0] + File.separator + vmFile);
                codeWriter.setFileName(vmFile);
            } else {
                String vmFile = vmFiles.remove(0);
                parser = new Parser(vmFile);
                codeWriter.setFileName(inputFile.getName());
            }

            while (parser.hasMoreLines()) {
                parser.advance();
                Command commandType = parser.commandType();
                switch (commandType) {
                    case C_ARITHMETIC:
                        codeWriter.writeArithmetic(parser.arg1());
                        break;
                    case C_PUSH:
                        codeWriter.writePushPop(commandType, parser.arg1(), parser.arg2());
                        break;
                    case C_POP:
                        codeWriter.writePushPop(commandType, parser.arg1(), parser.arg2());
                        break;
                    case C_LABEL:
                        codeWriter.writeLabel(parser.arg1());
                        break;
                    case C_GOTO:
                        codeWriter.writeGoto(parser.arg1());
                        break;
                    case C_IF:
                        codeWriter.writeIf(parser.arg1());
                        break;
                    case C_FUNCTION:
                        codeWriter.writeFunction(parser.arg1(), parser.arg2());
                        break;
                    case C_CALL:
                        codeWriter.writeCall(parser.arg1(), parser.arg2());
                        break;
                    case C_RETURN:
                        codeWriter.writeReturn();
                        break;
                    default:
                        System.err.println("Unknown command type: " + commandType);
                }
            }
        }
        codeWriter.close();
    }
}