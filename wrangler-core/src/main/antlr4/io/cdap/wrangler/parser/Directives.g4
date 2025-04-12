/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

grammar Directives;

options {
  language = Java;
}

@lexer::header {
/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
}

// ====================== LEXER RULES ======================

// Punctuation
SEMICOLON : ';';
LBRACE : '{';
RBRACE : '}';
LPAREN : '(';
RPAREN : ')';
LBRACKET : '[';
RBRACKET : ']';
COLON : ':';
COMMA : ',';
DOT : '.';
AT : '@';
PIPE : '|';
DOLLAR : '$';
TILDE : '~';
QMARK : '?';
EXCL : '!';
ASSIGN : '=';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';
MOD : '%';
POW : '^';
GT : '>';
LT : '<';
OR : '||';
AND : '&&';
EQ : '==';
NEQ : '!=';
GTE : '>=';
LTE : '<=';
MATCH : '=~';
NOT_MATCH : '!~';
QUEST_COLON : '?:';
STARTS_WITH : '=^';
NOT_STARTS_WITH : '!^';
ENDS_WITH : '=$';
NOT_ENDS_WITH : '!$';
PLUS_EQ : '+=';
MINUS_EQ : '-=';
MULT_EQ : '*=';
DIV_EQ : '/=';
MOD_EQ : '%=';
AND_EQ : '&=';
OR_EQ : '|=';
XOR_EQ : '^=';
BACKSLASH : '\\';

// Keywords
IF : 'if';
ELSE : 'else';
FOR : 'for';
PRAGMA : '#pragma';
LOAD_DIRECTIVES : 'load-directives';
VERSION : 'version';
EXP : 'exp';
PROP : 'prop';
TRUE : 'true';
FALSE : 'false';

// Complex tokens
BYTE_SIZE : Digit+ Space* BYTE_UNIT;
TIME_DURATION : Digit+ Space* TIME_UNIT;

fragment BYTE_UNIT : [KkMmGgTt] [Bb]?;
fragment TIME_UNIT : [Nn]?[Ss] | [Mm][Ss] | [Uu][Ss] | [Mm][Ii][Nn] | [Hh][Rr]?;

Number : Int ('.' Digit*)?;
Identifier : [a-zA-Z_\-] [a-zA-Z_0-9\-]*;
Macro : [a-zA-Z_] [a-zA-Z_0-9]*;
Column : ':' [a-zA-Z_\-] [:a-zA-Z_0-9\-]*;
String : '\'' ( EscapeSequence | ~('\'') )* '\'' | '"' ( EscapeSequence | ~('"') )* '"';

fragment EscapeSequence
   : '\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')
   | UnicodeEscape
   | OctalEscape
   ;

fragment UnicodeEscape
   : '\\' 'u' HexDigit HexDigit HexDigit HexDigit
   ;

fragment OctalEscape
   : '\\' ('0'..'3') ('0'..'7') ('0'..'7')
   | '\\' ('0'..'7') ('0'..'7')
   | '\\' ('0'..'7')
   ;

fragment HexDigit : ('0'..'9'|'a'..'f'|'A'..'F');
fragment Int : '-'? [1-9] Digit* [L]* | '0';
fragment Digit : [0-9];

Comment : ('//' ~[\r\n]* | '/*' .*? '*/' | '--' ~[\r\n]*) -> skip;
Space : [ \t\r\n\u000C]+ -> skip;

// ====================== PARSER RULES ======================

recipe : statements EOF;

statements : (Comment | macro | directive SEMICOLON | pragma SEMICOLON | ifStatement)*;

directive : command
  ( codeblock
  | identifier
  | macro
  | text
  | number
  | bool
  | column
  | colList
  | numberList
  | boolList
  | stringList
  | numberRanges
  | properties
  | byteSizeArg
  | timeDurationArg
  )*?;

ifStatement : ifStat elseIfStat* elseStat? RBRACE;

ifStat : IF LBRACE statements;

elseIfStat : RBRACE ELSE IF LBRACE statements;

elseStat : RBRACE ELSE LBRACE statements;

expression : LPAREN (~LPAREN | expression)* RPAREN;

forStatement : FOR LPAREN Identifier ASSIGN expression SEMICOLON expression SEMICOLON expression RPAREN LBRACE statements RBRACE;

macro : DOLLAR LBRACE (~LBRACE | macro | Macro)*? RBRACE;

pragma : PRAGMA (pragmaLoadDirective | pragmaVersion);

pragmaLoadDirective : LOAD_DIRECTIVES identifierList;

pragmaVersion : VERSION Number;

codeblock : EXP Space* COLON condition;

identifier : Identifier;

properties : PROP COLON LBRACE propertyList+ RBRACE
  | PROP COLON LBRACE LBRACE propertyList+ RBRACE { notifyErrorListeners("Too many start parenthesis"); }
  | PROP COLON LBRACE propertyList+ RBRACE RBRACE { notifyErrorListeners("Too many start parenthesis"); }
  | PROP COLON propertyList+ RBRACE { notifyErrorListeners("Missing opening brace"); }
  | PROP COLON LBRACE propertyList+ { notifyErrorListeners("Missing closing brace"); };

propertyList : property (COMMA property)*;

property : Identifier ASSIGN (text | number | bool | byteSizeArg | timeDurationArg);

numberRanges : numberRange (COMMA numberRange)*;

numberRange : Number COLON Number ASSIGN value;

byteSizeArg : BYTE_SIZE;

timeDurationArg : TIME_DURATION;

value : String | Number | Column | Bool | BYTE_SIZE | TIME_DURATION;

ecommand : EXCL Identifier;

config : Identifier;

column : Column;

text : String;

number : Number;

bool : Bool;

condition : LBRACE (~RBRACE | condition)* RBRACE;

command : Identifier;

colList : Column (COMMA Column)+;

numberList : Number (COMMA Number)+;

boolList : Bool (COMMA Bool)+;

stringList : String (COMMA String)+;

identifierList : Identifier (COMMA Identifier)*;