import java.io.FileWriter;

public class HackAssembler {
    // This is a function that converts a decimal number to a 15-bit binary string
    public static String decToBin(int dec) {return String.format("%15s", Integer.toBinaryString(dec)).replace(' ', '0');}

    public static void main(String[] args) {
        if (args.length != 1 && args[0].endsWith(".asm")) {
            System.out.println("Usage: java HackAssembler <filename>.asm");
            return;
        } // Input should be of the form java HackAssembler <filename>.asm

        String filename = args[0];
        Parser parser1 = new Parser(filename);
        SymbolTable symbolTable = new SymbolTable();
        Code code = new Code();

        // First pass: collect labels
        int lineNumber = 0;
        while (parser1.hasMoreLines()) {
            parser1.advance();
            if (parser1.instructionType() == Instruction.L_INSTRUCTION) {
                String symbol = parser1.symbol();
                symbolTable.addEntry(symbol, lineNumber);
            } else lineNumber++;
        }

        Parser parser2 = new Parser(filename);
        lineNumber = 16; // Start with 16 for variable addresses
        String name = filename.substring(0, filename.length() - 4) + ".hack";
        try (FileWriter writer = new FileWriter(name)) {
            // Second pass: translate instructions
            while (parser2.hasMoreLines()) {
                String line = parser2.advance();
                if (line.isEmpty()) break; // The final line has been reached
                if (parser2.instructionType() == Instruction.A_INSTRUCTION) {
                    String symbol = parser2.symbol();
                    try {
                        int address = Integer.parseInt(symbol);
                        writer.write("0" + decToBin(address) + "\n");
                    } catch (NumberFormatException e) {
                        // If it's not a number, treat it as a variable
                        if (!symbolTable.contains(symbol)) {
                            // If the symbol is not in the table, add it with the next available address
                            symbolTable.addEntry(symbol, lineNumber);
                            lineNumber++;
                        }
                        writer.write("0" + decToBin(symbolTable.getAddress(symbol)) + "\n");
                    }
                } else if (parser2.instructionType() == Instruction.C_INSTRUCTION) {
                    String dest = parser2.dest();
                    String comp = parser2.comp();
                    String jump = parser2.jump();
                    String binaryInstruction = "111" + code.comp(comp) + code.dest(dest) + code.jump(jump);
                    writer.write(binaryInstruction + "\n");
                } // We don't write L_INSTRUCTION to the output file
            }
        } catch (Exception e) {System.err.println("Error writing to file: " + e.getMessage());}
    }
}