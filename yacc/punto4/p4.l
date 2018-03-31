%{
#include "y.tab.h"


%}

%% 
[0-9]+.[0-9]+ {
    yylval = atof(yytext);
    return FLOAT;
}
sqrt { return SQRT; }
log { return LOG; }
e { return E; }
[ \t] ;
\n return 0;
.  return yytext[0];
%% 