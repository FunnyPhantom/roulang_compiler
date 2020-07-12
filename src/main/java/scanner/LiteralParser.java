package scanner;

public class LiteralParser {
    private static LiteralParser instance = new LiteralParser();

    private LiteralParser() {
    }

    public static LiteralParser getInstance() {
        return instance;
    }

    public double parseFloat(String floatString) {
        return Double.parseDouble(floatString);
    }

    public int parseInteger(String integerString) {
        return Integer.parseInt(integerString);
    }

    public long parseLong(String longString) {
        return Long.parseLong(longString.substring(0, longString.length() - 1));
    }

    public int parseHexInt(String hexIntString) {
        return Integer.parseInt(hexIntString.substring(2), 16);
    }
    public long parseDecimal(String decimalString) {
        if (decimalString.endsWith("L") || decimalString.endsWith("l")) {
            return parseLong(decimalString);
        }
        else {
            return parseInteger(decimalString);
        }
    }

}
