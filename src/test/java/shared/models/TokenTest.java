package shared.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import scanner.LiteralParser;

public class TokenTest {

    LiteralParser numericalParser = LiteralParser.getInstance();

    @Test
    public void canMakeToken() {
        var t1 = Token.of("Salam", TokenType.KEYWORD, 0, 0);
        var t2 = Token.of(2, TokenType.INTEGER_LITERAL, 0, 0);
        var t3 = Token.of(234L, TokenType.INTEGER_LITERAL, 0, 0);
        var t4 = Token.of(numericalParser.parseFloat("23.45"), TokenType.FLOAT_LITERAL, 0, 0);

        Assertions.assertEquals("Salam", t1.getValue());
        Assertions.assertEquals(2, t2.getValue());
        Assertions.assertEquals(234L, t3.getValue());
        Assertions.assertEquals(23.45, t4.getValue());



    }
}
