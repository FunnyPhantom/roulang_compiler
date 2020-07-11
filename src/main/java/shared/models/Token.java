package shared.models;

import lombok.Getter;

public class Token {
    @Getter
    private final String token;
    @Getter
    private final TokenType type;

    @Getter
    private final int line;
    @Getter
    private final int column;

    private Token(String token, TokenType type, int line, int column) {
        this.token = token;
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public static Token of(String token, TokenType tokenType, int line, int column) {
        return new Token(token, tokenType, line, column);
    }
}
