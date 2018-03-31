%{
#include<stdio.h>

%}

%token NAME NUMBER 
%%
statement: NAME '=' expression
	| expression {printf("= %f\n",$1);}
	;
expression: expression '+' NUMBER {$$ = $1 + $3; }
	| expression '-' NUMBER {$$ = $1 - $3; }
	| expression '*' NUMBER {$$ = $1 * $3; }
	| expression '/' NUMBER {if ($3 == 0.0)  yyerror("Error dividir entre cero");
								else
								$$ = $1 / $3; }
	| NUMBER {$$ = $1;}
	;

	
%%
extern FILE *yyin;
main()
{
	yyparse();
}
yyerror(s)
char *s;
{
	fprintf(stderr, "%s\n",s);
}

