package parser;

import lombok.Getter;
import scanner.CodeScanner;
import shared.models.Token;
import shared.models.TokenType;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Lexical {

    final CodeScanner cs;
    List<String> userDefinedTypes = new ArrayList<>();
    private Token currentToken = null;
    @Getter
    private File in;


    public Lexical(File in) {
        try {
            this.in = in;
            cs = new CodeScanner(new FileReader(in));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Token currentToken() {
        return currentToken;
    }

    public String nextToken() {
        try {
            currentToken = cs.getNext();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (currentToken == null || currentToken.getValue() == null) {
            System.out.println("file tamum");
            currentToken = Token.EOF;
        }

        System.out.println(">> " + currentToken);

        switch (currentToken.getType()) {
            case IDENTIFIER:
                if (userDefinedTypes.contains(currentToken.getValue().toString())) {
                    currentToken = Token.of(currentToken.getValue(), TokenType.VAR_TYPE, currentToken.getLine(), currentToken.getColumn());
                    return "type_id";
                }
                switch (currentToken.getValue().toString()) {
                    case "start":
                        return "start";
                    default:
                        return "id";
                }
            case VAR_TYPE:
                return "type_id";
            case COMMA:
                return "#comma";
            case INTEGER_LITERAL:
                return "#icv";
            case CHAR_LITERAL:
                return "#ccv";
            case FLOAT_LITERAL:
                return "#fcv";
            case STRING_LITERAL:
                return "#scv";
            default:
                return currentToken.getValue().toString();

        }
    }

    public void addRecord() {
        userDefinedTypes.add(currentToken.getValue().toString());
    }
}
