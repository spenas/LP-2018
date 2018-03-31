%{
#include "y.tab.h"
extern int yylval;

%}

%% 
[a-z]       {
                 yylval = *yytext - 'a';
                 return ID;
             }
[0-9]+      {
                 yylval = atoi(yytext);
                 return INT;
             }


[-+()=/*\n] { return *yytext; }

[ \t]      ;
.               yyerror("invalid character");
%% 