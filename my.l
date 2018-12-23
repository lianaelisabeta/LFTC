%{
#include "y.tab.h"
int line =0;
%}
%%
[\n] {line=line+1;}
[[:space:]]+
(\+) {return plus;}  
(\*) {return my_mul;} 
(\/) {return my_div;} 
(==) {return eq;} 
(!=) {return dif;} 
(<) {return lt;} 
(>) {return gt;} 
(<=) {return leq;} 
(>=) {return geq;} 
(>>) {return my_read;} 
(<<) {return my_write;} 
(main) {return my_main;} 
(int) {return my_int;} 
(std::cin) {return my_cin;} 
(while) {return my_while;} 
(std::cout) {return my_cout;} 
(double) {return my_double;} 
(if) {return my_if;} 
(else) {return my_else;} 
(std::endl) {return my_endl;} 
(#include<iostream>) {return my_iostream;}
(char) {return my_char;} 
(std::string) {return my_stdstr;} 
(#include<string>) {return incstring;} 
(=) {return yytext[0];}
\[ {return yytext[0];}
\] {return yytext[0];}
\{ {return yytext[0];}
\} {return yytext[0];}
\( {return yytext[0];}
\) {return yytext[0];}
\; {return yytext[0];}
\, {return yytext[0];}
[a-zA-Z]([a-zA-Z0-9]*) {return identificator;}
((\-)([1-9]+[0-9]*)|([1-9]+[0-9]*|[0]))"\."[0-9]+ {return real;} 
(\-)([1-9]+[0-9]*) {return intreg_negativ;}
(\-) {return minus;}
[1-9]+[0-9]*|[0] {return intreg_pozitiv;} 
"\'"([a-zA-Z0-9]|("\."|"\!"|"\?"|"\:"|"\,"|"\-"))"\'" {return caracter;} 
[\"](([a-zA-Z0-9]|("\."|"\!"|"\?"|"\:"|"\,"|"\-"))*)[\"] {return sir_de_caractere;}  
[0-9]+[a-zA-Z]+([a-zA-Z0-9])* {yyerror(yytext);} 
[\']([a-zA-Z0-9]|("\."|"\!"|"\?"|"\:"|"\,"|"\-")) {yyerror(yytext);return 1;} 
([a-zA-Z0-9]|("\."|"\!"|"\?"|"\:"|"\,"|"\-"))[\'] {yyerror(yytext);return 1;} 
([\"]([a-zA-Z0-9]|("\."|"\!"|"\?"|"\:"|"\,"|"\-"))) {yyerror(yytext); return 1;}
(([a-zA-Z0-9]|("\."|"\!"|"\?"|"\:"|"\,"|"\-"))[\"]) {yyerror(yytext); return 1;}
.                      {yyerror(yytext);return 1;}
%%
int yywrap (void) {return 1;}
	
