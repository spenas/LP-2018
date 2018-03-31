%{
#include "y.tab.h"
#include <math.h>


%}

%%
([0-9]+|[-+]?[0-9]*\.?[0-9]+) {yylval = atof(yytext); return NUMBER; }
[ \t] ;
\n return 0 ;
.  return yytext[0];
%%	
