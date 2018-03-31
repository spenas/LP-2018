%{
#include<stdio.h>

%}

%token NAME NUMBER
%left '-' '+'
%left '*' '/'
%nonassoc UNIMUS

%%
statement: NAME '=' expression
	| expression {printf("= %d\n",$1);}
	;
expression: expression '+' expression {$$ = $1 + $3; }
	| expression '-' expression {$$ = $1 - $3; }
	| expression '*' expression {$$ = $1 * $3; }
	| expression '/' expression
		 {	if($3 == 0)
				yyerror("divide by zero");
			else
				$$ = $1 / $3; 
		}
	| '-' expression %prec UNIMUS { $$ = -$2; }
	| '(' expression ')'	{ $$ = $2; }
	| NUMBER		{$$ = $1; }
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

