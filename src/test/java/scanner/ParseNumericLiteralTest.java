package scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParseNumericLiteralTest {
    LiteralParser pnl = LiteralParser.getInstance();

    @Test
    public void shouldGetCorrectValueForDoubleFormat() {
        double d = 42.31412;
        String dInS = "42.31412";
        Assertions.assertEquals(d, pnl.parseFloat(dInS));
    }

    @Test
    public void shouldGetCorrectValueForSciFormat() {
        double d1 = 2.3e+10;
        String d1S = "2.3e+10";

        double d2 = 42.31412e-10;
        String d2S = "42.31412e-10";

        Assertions.assertEquals(d1, pnl.parseFloat(d1S));
        Assertions.assertEquals(d2, pnl.parseFloat(d2S));
    }


    @Test
    public void canParseInt() {
        int i1 = 232425;
        String i1S = "232425";

        Assertions.assertEquals(i1, pnl.parseInteger(i1S));
    }

    @Test
    public void canParseLong() {
        long l1 = 232425;
        String l1S = "232425L";

        Assertions.assertEquals(l1, pnl.parseLong(l1S));
    }


    @Test
    public void canParseHex() {
        int i1 = 0x123456;
        String i1S =  "0x123456";
        Assertions.assertEquals(0x123456, pnl.parseHexInt(i1S));
    }

    @Test
    public void canParseDecimal() {
        int i1 = 1234;
        String i1S = "1234";

        long l1 = 12345L;
        String l1S = "12345L";

        Assertions.assertEquals(i1, pnl.parseDecimal(i1S));
        Assertions.assertEquals(i1, pnl.parseDecimal("1234L"));


        Assertions.assertEquals(l1, pnl.parseDecimal(l1S));
        Assertions.assertEquals(l1, pnl.parseDecimal("12345"));
    }
}
