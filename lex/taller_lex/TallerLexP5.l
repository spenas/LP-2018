%{
#define NUMBER 400
#define COMMENT 401
#define TEXT 402
#define COMMAND 403
%}

%%
[\t]+  /* ignora el espacio en blanco*/;

[0-9]+\+[0-9]+\i {printf("Numero Complejo = "); ECHO;; printf("\n");}

[0-9]+\i {printf("Numero Complejo = "); ECHO;; printf("\n");}

[0-9]+ {printf("Numero = "); ECHO;; printf("\n");}

[+] {printf("Suma = "); ECHO;; printf("\n");}

[-] {printf("Resta = "); ECHO;; printf("\n");}

[*] {printf("Multiplicacion = "); ECHO;; printf("\n");}

[/] {printf("Division = "); ECHO;; printf("\n");}

[%] {printf("Modulo = "); ECHO;; printf("\n");}

[\n] {printf("EOF");; printf("\n");}

[a-zA-Z]+ { printf("Token no reconocible = "); ECHO;; printf("\n");}

%%
#include <stdio.h>

main(argc,argv)
int argc;
char *argv[];
{
int val;
while(val = yylex()) printf("value is %d\n",val);
}