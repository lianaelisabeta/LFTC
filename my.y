%{
#include <string.h>
#include <stdio.h>     /* C declarations used in actions */
#include <stdlib.h>
void yyerror (char const *s);
#define YYERROR_VERBOSE ;
%}
%start program
%token plus
%token minus
%token my_mul
%token my_div
%token atr
%token eq
%token dif
%token lt
%token gt
%token leq
%token geq
%token my_read
%token my_write
%token my_main 
%token my_int
%token my_cin
%token my_while
%token my_cout
%token my_double
%token my_if
%token my_else
%token my_endl
%token my_iostream 
%token my_char
%token my_stdstr
%token incstring
%token identificator
%token intreg_negativ
%token intreg_pozitiv
%token real
%token caracter
%token sir_de_caractere
%%

program: my_header my_main '(' ')' '{' decllist lista_instructiuni '}' {printf("Program corect sintactic\n");}
	;
my_header : my_iostream {printf("my_iostream\n");}
	| my_iostream incstring {printf("my_iostream incstring\n");}
	;
decllist : decl ';' {printf("decllist 1\n");}
	| decl ';' decllist {printf("decllist 2\n");}
	;
decl : type identificator '=' constanta {printf("decl 2\n");}
	|type identificator '[' intreg_pozitiv ']'  {printf("decl 3\n");}
	|type identificator {printf("decl 1\n");}
	;
type : my_int {printf("type 1\n");}
	| my_char {printf("type 2\n");}
	| my_double {printf("type 3\n");}
	| my_stdstr {printf("type 4\n");}
	;
lista_instructiuni : instructiune {printf("lista_instructiuni 1\n");}
	| instructiune lista_instructiuni {printf("lista_instructiuni 2\n");}
	;
instructiune : instructiune_simpla {printf("instructiune_simpla\n");}
	| instructiune_compusa {printf("instructiune_compusa\n");}
	;
instructiune_simpla : atribuire ';' {printf("instructiune_simpla atribuire \n");}
	| operatieIO ';' {printf("instructiune_simpla operatieIO\n");}
	;
atribuire : identificator '=' expresie {printf("atribuire expresie\n");}
	;
expresie : termen plus termen {printf("expresie plus\n");}
	| termen minus termen {printf("expresie minus\n");}
	| termen plus expresie {printf("expresie plus termen\n");}
	| termen minus expresie
	| termen
	;
termen : factor my_mul factor {printf("expresie mul\n");}
	| factor my_div factor {printf("expresie div\n");}
	|factor my_mul termen
	|factor my_div termen
	| factor {printf("termen factor\n");}
	;
factor : '(' expresie ')' {printf("factor expresie\n");}
	| identificator  {printf("factor identificator\n");}
	| constanta {printf("factor constanta\n");}
	| identificator '[' index ']' {printf("factor identificator index\n");}
	;
operatieIO : op_citire {printf("operatieIO citire\n");}
	| op_scriere {printf("operatieIO scriere\n");}
	;
op_citire : my_cin my_read identificator {printf("op_citire identificator\n");}
	| my_cin my_read identificator '[' index ']' {printf("op_citire identificator index\n");}
	;
op_scriere : my_cout my_write identificator {printf("op_scriere identificator\n");}
	| my_cout my_write identificator '[' index ']' {printf("op_scriere identificator index \n");}
	| my_cout my_write my_endl {printf("op_scriere endl\n");}
	| my_cout my_write constanta {printf("op_scriere constanta\n");}
	;
instructiune_compusa : instr_while {printf("instructiune_compusa while\n");}
	| instr_if {printf("instructiune_compusa if\n");}
	;
instr_while : my_while '(' conditie ')' '{' lista_instructiuni '}' {printf("instr_while\n");}
	;
instr_if : my_if '(' conditie ')' '{' lista_instructiuni '}' {printf("instr_if\n");}
	| my_if '(' conditie ')' '{' lista_instructiuni '}' my_else '{' lista_instructiuni '}' {printf("instr_if_else\n");}
	;	
conditie : expresie op_relational expresie {printf("conditie\n");}
	;
op_relational : eq {printf("op_relational1 \n");}
	| dif {printf("op_relational2 \n");}
	| lt {printf("op_relational3 \n");}
	| gt {printf("op_relational4 \n");}
	| leq {printf("op_relational5 \n");}
	| geq {printf("op_relational \n");}
	;
intreg : intreg_negativ {printf("intreg_negativ \n");}
	| intreg_pozitiv {printf("intreg_pozitiv \n");}
	;
constanta : intreg {printf("constanta intreg \n");}
	| real {printf("constanta real \n");}
	|caracter {printf("constanta caracter \n");}
	|sir_de_caractere {printf("constanta sir_de_caractere \n");}
	;
index : identificator {printf("index identificator \n");}
	| intreg_pozitiv {printf("index intreg_pozitiv \n");}
	;
%%
int main(){
		return yyparse();
}
void yyerror (char const *s)
{
  fprintf (stderr, "Eroare: %s\n", s);
}