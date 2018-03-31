%{
/*
* Este ejemplo es un pequeño diccionario de 10 palabras de 
*ingles a español
*/
%}

%% 

[\t]+  /* ignora el espacio en blanco*/;

hello {printf("Traduccion de %s: hola" ,yytext);}

bye {printf("Traduccion de %s: adios",yytext);}

chair {printf("Traduccion de %s: silla",yytext);}

table {printf("Traduccion de %s: mesa",yytext);}

pen {printf("Traduccion de %s: esfero",yytext);}

pencil {printf("Traduccion de %s: lapiz",yytext);}

cat {printf("Traduccion de %s: gato",yytext);}

dog {printf("Traduccion de %s: perro",yytext);}

big {printf("Traduccion de %s: grande",yytext);}

small {printf("Traduccion de %s: pequeño",yytext);}

[a-zA-Z]+ { printf("La palabra %s no existe", yytext);}

.|\n	{ ECHO; /*normal default anyway */}

%%

main()
{
	yylex();
}