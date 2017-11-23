// antlr4 -package parser -o antlr-generated  -no-listener parser/PiLang.g4
grammar PiLang;

prog: varDecls funcDecl*
	;

funcDecl: 'function' IDENTIFIER '(' params ')' '{' varDecls stmt* '}'
    ;

params:   /* no parameter */
    | IDENTIFIER (',' IDENTIFIER)*
    ;

varDecls: ('var' IDENTIFIER ';')*
    ;

stmt: '{' stmt* '}'							# compoundStmt
	| IDENTIFIER '=' expr ';'				# assignStmt
	| 'if' '(' expr ')' stmt 'else' stmt	# ifStmt
	| 'while' '(' expr ')' stmt				# whileStmt
	| 'return' expr ';'						# returnStmt
	;

expr: addExpr
      ;

addExpr: addExpr ADDOP mulExpr
	| mulExpr
	;

mulExpr: mulExpr MULOP unaryExpr
	| unaryExpr
	;

unaryExpr: VALUE			# literalExpr
	| IDENTIFIER			# varExpr
	| '(' expr ')'			# parenExpr
	| IDENTIFIER '(' args ')' # callExpr
	;

args: /* no arguments */
	| expr ( ',' expr )*
	;

ADDOP: '+'|'-';
MULOP: '*'|'/';

IDENTIFIER: 'x'|'y'|'z'|'main';
VALUE: [0-9]+;
WS: [ \t\r\n] -> skip;
