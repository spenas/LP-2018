%{
#include "y.tab.h"

%}

%%

[0-9]+.[0-9]+ {
    yylval = atof(yytext);
    return FLOAT;
}
[ \t] ;
\n return 0;
.  return yytext[0];
%%	
