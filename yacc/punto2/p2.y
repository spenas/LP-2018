%{
#include<stdio.h>
int at_end = 0;
%}

%code requires
  {
    #define YYSTYPE double
  }
%token NAME NUMBER FLOAT
%left '-' '+'
%left '*' '/'
%nonassoc UNIMUS

%%
statement: NAME '=' expression
	| expression {printf("= %f\n",$1);}
	;
expression: expression '+' expression {$$ = $1 + $3; }
	| expression '-' expression {$$ = $1 - $3; }
	| expression '*' expression {$$ = $1 * $3; }
	| expression '/' expression
		 {	if($3 == 0.0)
				yyerror("divide by zero");
			else
				$$ = $1 / $3; 
		}
	| '-' expression %prec UNIMUS { $$ = -$2; }
	| '(' expression ')'	{ $$ = $2; }
	| NUMBER		{$$ = $1; }
	| FLOAT
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

