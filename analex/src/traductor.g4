// Gramatica hecha por:
// Diego Rodriguez Chaparro
// Nicolas Ricardo Enciso

grammar traductor;

// inicializador de todas las funciones

init: complete1;
 
// TOKENS *******************************************************************************
// identificador 
ID: ([a-z] | [A-Z] | '_')([a-z] | [A-Z] | '_' | [0-9])*;

// espacios a ignorar
WS: [ \t\r\n]+ -> skip;

//ignorar comentarios
LINE_COMMENT
    : '#' ~[\r\n]* -> skip;

//tokens
TK_PUNTO: '.' ;
CADENA: '"' ~['"']* '"';
TK_COR_IZQ: '[';
TK_COR_DER: ']';
TK_COMA: ',';
TK_PAR_DER: ')';
TK_PAR_IZQ: '(';
TK_OR: '||';
TK_AND: '&&';
TK_IGUAL_NUM: '==';
TK_ASSIGN: '=';
TK_DOSP: ':';
TK_LLAVE_IZQ: '{';
TK_LLAVE_DER: '}';
TK_NOT: '!';
TK_DIFF_NUM: '!=';
TK_MAYOR: '>';
TK_MENOR:  '<';
TK_MAYOR_IGUAL: '>=';
TK_MENOR_IGUAL: '<=';
TK_MUL: '*';
TK_DIV: '/';
TK_MOD: '%';
TK_POT: '^';
TK_INT: ([0-9]+);
TK_MAS: '+';
TK_MENOS: '-'; 
TK_FLOAT: ([0-9]* '.' [0-9]+);

// funcion import********************************************************************************
//************************************ COMPLETADO************************
import1: import2 import3;

import2: 'desde' import4
    |
    ;

import3: 'importar' import4 import5;

import4: ID;

import5: TK_PUNTO import6
    |
    ;

import6: ID import5;

//declaracion de funciones********************************************************************************
//************************************ COMPLETADO************************
declaraFun1: 'funcion' ID declaraFun2 declaraFun6 declaraFun7;

declaraFun2: TK_PAR_IZQ declaraFun3 TK_PAR_DER;

declaraFun3: ID declaraFun4
    |
    ;

declaraFun4: TK_COMA ID declaraFun4
    |
    ;

declaraFun5: line1 declaraFun6;

declaraFun6: declaraFun5
    |
    ;

declaraFun7: 'retorno' return1 'end' 'funcion'
    | 'end' 'funcion'
    ;


// declaracion de variables**************************************************************************
declaraVar1: ID callVar2 declaraVar2;

declaraVar2: TK_ASSIGN declaraVar3;

declaraVar3: CADENA
    | 'nil'
    | declaraAy1
    | declaraEst1
    | TK_MENOS declaraVar3
    | opeGeneral1
    ;

// declaracion de arrays***********************************************************************
//***************************************** COMPLETADO ************************************
declaraAy1: TK_COR_IZQ declaraAy2 TK_COR_DER;

declaraAy2: opeGeneral1 declaraAy3
    | CADENA declaraAy3
    | declaraAy1 declaraAy3
    | declaraEst1 declaraAy3
    | callVar1
    |
    ;

declaraAy3: TK_COMA declaraAy2
    |
    ;

// declaracion de estructura********************************************************************************
//************************************** PENDIENTE POR "" EN LAS CLAVES*******************
declaraEst1: TK_LLAVE_IZQ declaraEst2 TK_LLAVE_DER;

declaraEst2: ID declaraEst3 declaraEst5;

declaraEst3: TK_DOSP declaraEst4;

declaraEst4: declaraEst1
    | declaraAy1
    | CADENA 
    | opeGeneral1
    | callVar1
    ;

declaraEst5: TK_COMA declaraEst2
    |
    ;

// operaciones generales***************************************************************
opeGeneral1: opeGeneral1 TK_OR opeGeneral2
    | opeGeneral2
    ;

opeGeneral2: opeGeneral2 TK_AND opeGeneral3
    | opeGeneral3
    ;

opeGeneral3: opeGeneral3 TK_IGUAL_NUM opeGeneral4
    | opeGeneral3 TK_DIFF_NUM opeGeneral4
    | opeGeneral4
    ;

opeGeneral4: opeGeneral4 TK_MAYOR opeGeneral5
    | opeGeneral4 TK_MENOR opeGeneral5
    | opeGeneral4 TK_MAYOR_IGUAL opeGeneral5
    | opeGeneral4 TK_MENOR_IGUAL opeGeneral5
    | opeGeneral5
    ;

opeGeneral5: opeGeneral5 TK_MAS opeGeneral6
    | opeGeneral5 TK_MENOS opeGeneral6
    | opeGeneral6
    ;

opeGeneral6: opeGeneral6 TK_MUL opeGeneral7
    | opeGeneral6 TK_DIV opeGeneral7
    | opeGeneral6 TK_MOD opeGeneral7
    | opeGeneral7
    ;

opeGeneral7: opeGeneral7 TK_POT opeGeneral8
    | opeGeneral8
    ;

opeGeneral8: TK_NOT opeGeneral5
    | 'true'
    | 'false'
    | TK_INT
    | TK_FLOAT
    | callVar1
    | TK_PAR_IZQ opeGeneral1 TK_PAR_DER
    ;

// operaciones booleanas*****************************************************************
opeBool1: opeBool1 TK_OR opeBool2
    | opeBool2
    ;

opeBool2: opeBool2 TK_AND opeBool3
    | opeBool3
    ;

opeBool3: opeBool3 TK_IGUAL_NUM opeBool4
    | opeBool3 TK_DIFF_NUM opeBool4
    | opeBool4
    ;

opeBool4: opeBool4 TK_MAYOR opeBool5
    | opeBool4 TK_MENOR opeBool5
    | opeBool4 TK_MAYOR_IGUAL opeBool5
    | opeBool4 TK_MENOR_IGUAL opeBool5
    | opeBool5
    ;

opeBool5: TK_NOT opeBool5
    | 'true'
    | 'false'
    | TK_INT
    | TK_FLOAT
    | callVar1
    |TK_PAR_IZQ opeBool1 TK_PAR_DER
    ;

// operaciones aritmeticas*************************************************************
opeAritmetica1: opeAritmetica1 TK_MAS opeAritmetica2
    | opeAritmetica1 TK_MENOS opeAritmetica2
    | opeAritmetica2
    ;

opeAritmetica2: opeAritmetica2 TK_MUL opeAritmetica3
    | opeAritmetica2 TK_DIV opeAritmetica3
    | opeAritmetica2 TK_MOD opeAritmetica3
    | opeAritmetica3
    ;

opeAritmetica3: opeAritmetica3 TK_POT opeAritmetica4
    | opeAritmetica4
    ;

opeAritmetica4: TK_INT
    | TK_FLOAT
    | 'nil'
    | callVar1
    | TK_PAR_IZQ opeAritmetica1 TK_PAR_DER
    ;

// condicionales if********************************************************************
if1: 'if' if2;

if2: TK_PAR_IZQ opeGeneral1 TK_PAR_DER if3;

if3: TK_LLAVE_IZQ if4
    | line1 if5
    ;

if4: line1 if4
    | TK_LLAVE_DER if5
    ;

if5: 'else' if6
    |
    ;

if6: if1
    | TK_LLAVE_IZQ if7
    | line1
    ;

if7: line1 if7
    | TK_LLAVE_DER
    ;

// ciclo while**************************************************************************
while1: 'while' while2 while4;

while2: TK_PAR_IZQ while3 TK_PAR_DER;

while3: opeGeneral1;

while4: TK_LLAVE_IZQ while5
    | line1
    ;

while5: TK_LLAVE_DER
    | line1 while5
    ;

// ciclos for*************************************************************************
for1: 'for' for2;

for2: ID for3;

for3: 'in' for4;

for4: declaraAy1 for5
    | callVar1 for5
    ;

for5: TK_LLAVE_IZQ for6
    | line1
    ;

for6: line1 for6
    | TK_LLAVE_DER
    ;

// entrar a estructura/diccionario*******************************************************
accessEstruc1: TK_PUNTO accessEstruc2;

accessEstruc2: ID accessEstruc3;

accessEstruc3: TK_PUNTO accessEstruc2
    |
    ;

//entrar a array************************************************************************
accessArray1: TK_COR_IZQ accessArray2 accessArray3 TK_COR_DER;

accessArray2: opeAritmetica1 accessArray3;

accessArray3: TK_COMA accessArray2
    |
    ; 

// llamado de variables******************************************************************
callVar1: ID callVar2;

callVar2: accessArray1
    | accessEstruc1
    | return1
    |
    ;

// retornar en funciones*****************************************************************
//************************************ COMPLETADO************************
return1: TK_PAR_IZQ return2 TK_PAR_DER ;

return2: opeGeneral1 return3
    | declaraEst1 return3
    | CADENA return3
    | declaraAy1 return3
    |
    ;

return3: TK_COMA return2
    |
    ;

// entrada de datos por leer a raw_input()******************************************
//*********************************** COMPLETADO************************************
lectura1: 'leer' lectura2;

lectura2: TK_PAR_IZQ lectura3 TK_PAR_DER;

lectura3: callVar1;

//imprimir los datos log  a print*************************************************
//********************************* COMPLETADO************************************
print1: 'log' print2;

print2: TK_PAR_IZQ print3 TK_PAR_DER;

print3: CADENA print4
    | opeGeneral1 print4
    ;

print4: TK_MAS print3
    | TK_MUL TK_INT
    |
    ;

// delcaracion de una linea con condiciones o argumentos***********************************
//********************************** COMPLETADO  *****************************************
line1: lectura1
    | print1
    | while1
    | for1
    | if1
    | declaraVar1
    | opeGeneral1
    | declaraAy1
    | declaraEst1
    |return1
    | CADENA
    | 'nil'
    | TK_MENOS opeAritmetica1
    ;

//toda la estructura general de un archivo import funciones y sentencias*************************************
//********************************* COMPLETADO POR DEFECTO ********************************
complete1: import1 complete1
    | complete2
    ;

complete2: declaraFun1 complete2
    | complete3
    ;

complete3: line1 complete3
    | EOF
    ;


