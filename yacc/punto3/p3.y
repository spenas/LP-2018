%{
#include <stdio.h>
#include <stdlib.h>
int at_end = 0;
int sym[26];
%}

 
%token NAME INT ID
%left '-' '+'
%left '*' '/'
%nonassoc UNIMUS


%%
program:
         program statement '\n'
         |
         ;

statement:
         expression                 { printf("%d\n", $1); }
         | ID '=' expression       { sym[$1] = $3; }
         ;  

expression:
         INT
         | ID                { $$ = sym[$1]; }
         | expression '+' expression           { $$ = $1 + $3; }
         | expression '-' expression           { $$ = $1 - $3; }
         | expression '*' expression           { $$ = $1 * $3; }
         | expression '/' expression           {  	
         if($3 == 0.0)
				yyerror("divide by zero");
			else
				$$ = $1 / $3; 
		 }
         | '(' expression ')'            { $$ = $2; }
         ;

%%
extern FILE *yyin;
main()
{
	while (! at_end) {
		yyparse();
		}
	
}
yyerror(s)
char *s;
{
	fprintf(stderr, "%s\n",s);
}

