%{
#define NUMBER 400
#define COMMENT 401
#define TEXT 402
#define COMMAND 403
%}


%% 

[\t]+  /* ignora el espacio en blanco*/;

[0-9]+ {printf("258 = "); ECHO;; printf("\n");}

[+] {printf("259 = "); ECHO;; printf("\n");}

[-] {printf("260 = "); ECHO;; printf("\n");}

[*] {printf("261 = "); ECHO;; printf("\n");}

[/] {printf("262 = "); ECHO;; printf("\n");}

[|] {printf("263 = "); ECHO;; printf("\n");}

[\n] {printf("263 = EOF");; printf("\n");}

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