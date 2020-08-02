package shared.models;

import lombok.Getter;

import java.util.Objects;

public class Token {
    @Getter
    private final TokenType type;
    @Getter
    private final Object value;
    @Getter
    private final int line;
    @Getter
    private final int column;

    private Token(Object value, TokenType type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.value = value;
    }


    public static Token of(Object value, TokenType tokenType, int line, int column) {
        return new Token(value, tokenType, line, column);
    }

    public final static Token EOF = Token.of("$",TokenType.EOF,0,0);;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token1 = (Token) o;
        return type == token1.type && Objects.equals(value, token1.value);
    }

    @Override
    public String toString(){
        return value+"";
    }

}
