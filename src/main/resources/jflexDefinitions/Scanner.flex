package scanner;
import shared.models.TokenType;
import shared.models.Token;
%%
%public

%class CodeScanner
%line
%column
%unicode
%function getNext
%type Token

%{
    LiteralParser literalParser = LiteralParser.getInstance();
    StringBuilder sb = new StringBuilder();
%}

%eofval{
    return Token.of(null, TokenType.EOF, yyline, yycolumn);
%eofval}

d = [0-9]
c = [a-zA-Z]

identifier_len = short|long
type = int|float|double|char|string|auto|bool|void
prefix = volatile|const|static|signed
bools = true|false
condition = if|else
loops = switch|case|default|for|foreach|while|do|break|continue|repeat|until
functions = sizeof|function|println|return|new|in

key_words = goto|record|{functions}|{loops}|{condition}|{bools}|{prefix}|{type}|{identifier_len}

integers = [+-]?{d}+
longs = {integers}L
decimal_int = {integers}|{longs}
hex = 0x{d}+
real = [+-]?({d}+\.{d}+|\.{d}+|{d}+\.)
scientific = {real}e{integers}

floating_point_numbers = {real}|{scientific}


end_of_line = \r\n|\n|\r
whitespaces = [ ]+


comment_single = \/\/
comment_begin = \/\*
comment_end = \*\/

single_line_comment = {comment_single}.*
multi_line_commnet = {comment_begin}(.|{end_of_line})*{comment_end}

comment = {single_line_comment} | {multi_line_commnet}

identifier = [{c}|_][{c}|{d}|_]*


%x  string_double_quote

%%


{floating_point_numbers}    {return Token.of(literalParser.parseFloat(yytext()), TokenType.FLOAT_LITERAL, yyline, yycolumn);}
{decimal_int}               {return Token.of(literalParser.parseDecimal(yytext()), TokenType.INTEGER_LITERAL, yyline, yycolumn);}
{hex}                       {return Token.of(literalParser.parseHexInt(yytext()), TokenType.INTEGER_LITERAL, yyline, yycolumn);}

{key_words}     {return Token.of(yytext(), TokenType.KEYWORD, yyline, yycolumn);}
{identifier}    {return Token.of(yytext(), TokenType.IDENTIFIER, yyline, yycolumn);}


'\\t'   {return Token.of('\t', TokenType.CHAR_LITERAL, yyline, yycolumn);}
'\\n'   {return Token.of('\n', TokenType.CHAR_LITERAL, yyline, yycolumn);}
'\\r'   {return Token.of('\r', TokenType.CHAR_LITERAL, yyline, yycolumn);}
'\\''   {return Token.of('\'', TokenType.CHAR_LITERAL, yyline, yycolumn);}
'\\'    {return Token.of('\\', TokenType.CHAR_LITERAL, yyline, yycolumn);}
'.'     {return Token.of(yytext().charAt(1), TokenType.CHAR_LITERAL, yyline, yycolumn);}


\" {yybegin(string_double_quote); sb.setLength(0);}
<string_double_quote> {
    \"                      {yybegin(YYINITIAL); return Token.of(sb.toString(), TokenType.STRING_LITERAL, yyline, yycolumn);}
    \\t                     {sb.append('\t');}
    \\n                     {sb.append('\n');}
    \\r                     {sb.append('\r');}
    \\\"                    {sb.append('\"');}
    \\                      {sb.append('\\');}
    [^\n\r\"\\]+   {sb.append(yytext());}
}

{comment}       {}
{end_of_line}   {}
{whitespaces}   {}

[^]     {throw new Error(String.format("Illegal character <%s> at line: %d column: %d", yytext(), yyline, yycolumn));}




