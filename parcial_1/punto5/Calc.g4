grammar Calc;

prog:   stat+ ;

stat:   expr NEWLINE                
    |   ID '=' expr NEWLINE         
    |   NEWLINE                     
    ;

expr:   expr op=('*'|'/'|'%') expr      
    |   expr op=('+'|'-') expr  
    |   TRIG '(' expr ')'
    |   INT  
    |   FLOAT 
    |	COMPL                          
    |   ID                          
    |   '(' expr ')'                
    ;

MUL :   '*' ;
DIV :   '/' ;
MOD :   '%' ;
SUM :   '+' ;
RES :   '-' ;
TRIG:   'sin' | 'cos' | 'tan' | 'asin' | 'acos' | 'atan';
ID  :   [a-zA-Z][a-zA-Z0-9]* ;      
INT :   [0-9]+ ;
FLOAT:  [0-9]+'.'[0-9]+ ;	
COMPL:  [0-9]+('.'[0-9]+)?('+'|'-')([0-9]+('.'[0-9]+)?)?'i' ;
NEWLINE:'\r'? '\n' ;     
WS  :   [ \t]+ -> skip ; 