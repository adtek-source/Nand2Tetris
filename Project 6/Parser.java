import java.io.File;
import java.util.Scanner;

enum Instruction {
    A_INSTRUCTION,
    C_INSTRUCTION,
    L_INSTRUCTION
}

public class Parser {
    private Scanner scanner;
    private String currentLine = "";

    public Parser(String fileName) {
        try {
            File file = new File(fileName);
            scanner = new Scanner(file);
        } catch (Exception e) {System.err.println("Error opening file: " + e.getMessage());}
    }

    public boolean hasMoreLines() {return scanner.hasNextLine();}

    public String advance() {
        String line;
        while (hasMoreLines()) {
            line = scanner.nextLine().trim();
            if (!line.isEmpty() && !line.startsWith("//")) {
                currentLine = line;
                break;
            } // Avoiding comments and empty lines
        }
        return currentLine;
    }

    public Instruction instructionType() {
        if (currentLine.startsWith("@")) return Instruction.A_INSTRUCTION;
        else if (currentLine.startsWith("(") && currentLine.endsWith(")")) return Instruction.L_INSTRUCTION;
        else return Instruction.C_INSTRUCTION;
    }

    public String symbol() {
        if (currentLine.startsWith("(")) return currentLine.substring(1, currentLine.length() - 1);
        else return currentLine.substring(1);
    }

    public String dest() {
        if (currentLine.contains("=")) {
            int eqIndex = currentLine.indexOf('=');
            return currentLine.substring(0, eqIndex);
        } else return ""; // No destination specified
    }

    public String comp() {
        if (currentLine.contains("=") && currentLine.contains(";")) {
            int eqIndex = currentLine.indexOf('=');
            int semIndex = currentLine.indexOf(';');
            return currentLine.substring(eqIndex + 1, semIndex - eqIndex - 1);
        } else if (currentLine.contains("=")) {
            int eqIndex = currentLine.indexOf('=');
            return currentLine.substring(eqIndex + 1);
        } else if (currentLine.contains(";")) return currentLine.substring(0, currentLine.indexOf(';'));
        else return currentLine;
    }

    public String jump() {
        if (currentLine.contains(";")) {
            int semIndex = currentLine.indexOf(';');
            return currentLine.substring(semIndex + 1);
        } else return ""; // No jump specified
    }
}