%{
int chars = 0;
int words = 0; 
int lines = 0; 
%}

%%
[a-zA-Z]+ { words++; 
chars += strlen(yytext); }
\n { lines++; }
. { chars++; }
%%
main(argc, argv) int argc;
char **argv;
{
	if(argc > 1) {
		if(!(yyin = fopen(argv[1], "r"))) {
			perror(argv[1]);
			return (1);
		}
	}
yylex();
printf ("Lineas: %3d ", lines);
printf ("Palabras: %3d ", words);
printf ("Caracteres: %3d \n", chars);
}