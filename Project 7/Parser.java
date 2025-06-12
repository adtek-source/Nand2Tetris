import java.io.File;
import java.util.Scanner;

enum Command {
    C_ARITHMETIC,
    C_PUSH,
    C_POP
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

    public void advance() {
        String line;
        while (hasMoreLines()) {
            line = scanner.nextLine().trim();
            if (!line.isEmpty() && !line.startsWith("//")) {
                currentLine = line;
                break;
            } // Avoiding comment and empty lines
        }
    }

    public Command commandType() {
        if (currentLine.equals("add") || currentLine.equals("sub") || currentLine.equals("neg") || currentLine.equals("eq") || currentLine.equals("gt") || currentLine.equals("lt") || currentLine.equals("and") || currentLine.equals("or") || currentLine.equals("not")) return Command.C_ARITHMETIC;
        int spaceIndex = currentLine.indexOf(' ', 0);
        String command = currentLine.substring(0, spaceIndex);
        if (command.equals("push")) return Command.C_PUSH;
        else return Command.C_POP;
    }

    public String arg1() {
        if (commandType() == Command.C_ARITHMETIC) return currentLine;
        int spaceIndex1 = currentLine.indexOf(' ');
        String lineWithoutCommand = currentLine.substring(spaceIndex1 + 1);
        int spaceIndex2 = lineWithoutCommand.indexOf(' ');
        return lineWithoutCommand.substring(0, spaceIndex2);
    }

    public int arg2() {
        try {
            int spaceIndex1 = currentLine.indexOf(' ');
            String lineWithoutCommand = currentLine.substring(spaceIndex1 + 1);
            int spaceIndex2 = lineWithoutCommand.indexOf(' ');
            String value = lineWithoutCommand.substring(spaceIndex2 + 1);
            int argument = Integer.parseInt(value);
            return argument;
        } catch (Exception e) {
            System.err.println("Error in the second argument: " + e.getMessage());
            return 0; // or throw an exception, depending on your error handling strategy
        }
    }
}