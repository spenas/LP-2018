grammar Expr;

// REGLAS SINTACTICAS
expr	:	term ( (MAS | MENOS) term)*
	{ System.out.println("Análisis terminado.");
	};

term	:	factor ( (MULT | DIV) factor)*;

factor	:	ENTERO;


// TOKENS
MAS 	:	'+';
MENOS 	:	'-';
MULT 	:	'*';
DIV 	:	'/';

// REGLAS LEXICAS
ENTERO :'0'..'9'+;

ESPACIO:   ( ' '
        | '\t'
        | '\r'
        | '\n'
        )+ -> channel(HIDDEN)
    ;

