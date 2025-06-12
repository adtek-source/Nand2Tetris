public class VMTranslator {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java VMTranslator <input file>.vm");
            return;
        } // Input should be of the form <filename>.vm

        Parser parser = new Parser(args[0]);
        CodeWriter codeWriter = new CodeWriter(args[0].replace(".vm", ".asm"));
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
                default:
                    System.err.println("Unknown command type: " + commandType);
            }
        }
        codeWriter.close();
    }
}