
import shared.models.TokenType;
import shared.models.Token;
%%
%public

%class Scanner
%line
%column
%unicode


%{



%}

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

integers = {d}+[Ll]?
hex = 0x{d}+
real = \.{d}+|{d}+\.|{d}+\.{d}+
scientific = {real}e[\+\-]{integers}

numbers = {integers}|{hex}|{real}|{scientific}

end_of_line = \r\n|\n|\r
whitespaces = [ ]+

special_char = \\.

comment_single = \/\/
comment_begin = \/\*
comment_end = \*\/

single_line_comment = {comment_single}.*
multi_line_commnet = {comment_begin}(.|{end_of_line})*{comment_end}

comment = {single_line_comment} | {multi_line_commnet}

quote = '
escaped_quote = \\'
double_quote = \"
escaped_double_quote = \\\"


identifier = [{c}|_][{c}|{d}|_]*


%x string_quote, string_double_quote

%%

<string_quote> {
    {quote} {maker.addString(yytext());yybegin(YYINITIAL);}
    [^] {maker.addString(yytext());}
}
<string_double_quote> {
    {escaped_double_quote} { maker.addSpecialChar(yytext());}
    {special_char} {maker.addSpecialChar(yytext());}
    {double_quote} {maker.addString(yytext());yybegin(YYINITIAL);}
    [^] {maker.addString(yytext());}
}

{end_of_line} {}
{whitespaces} {}
{real} {return Token.of(yytext(), TokenType.VALUE, yyline, yycolumn);}
{scientific} {return Token.of(yytext(), TokenType.VALUE, yyline, yycolumn);}
{integers} {return Token.of(yytext(), TokenType.VALUE, yyline, yycolumn);}
{hex} {return Token.of(yytext(), TokenType.VALUE, yyline, yycolumn);}
{key_words} {return Token.of(yytext(), TokenType.KEYWORD, yyline, yycolumn);}
{identifier} {return Token.of(yytext(), TokenType.IDENTIFIER, yyline, yycolumn);}
{comment} {}
{quote} {yybegin(string_quote);}
{double_quote} {yybegin(string_double_quote);}
[^] {throw new Error(String.format("Illegal character <%s> at line: %d column: %d", yytext(), yyline, yycolumn));}




