
package Analizador;
import java_cup.runtime.Symbol;

%%
%class lexico
%public
%line
%char
%cup
%unicode
%ignorecase

%init{

%init}
//___________Expresiones regulares_______________
IGNORAR=[ \r\t\n]+
LETTERS=[a-zA-Z]
DIGIT=[0-9]
PC=";"
CP="+" //cerradura positiva
CK="*" 
OR="|"
I="?"
P="."
V="~"
ESPECIALES=(\\n|\\\'|\\\")
DPORCENTAJE=%%
COM_MTL=\<\!\<*([^<!]|[^!]"<"|"!"[^>])*\!*\!\>
LLAVEIZ=\{
LLAVED=\}
ID={LETTERS}({LETTERS}|{DIGIT}|"_")*
DOS_P=":"
ASIGNACION="-"">"
SIMBOLOS_CONJ=([ -\/]|[\:-\@]|[\[-\´]|[\{-\}])
SIMBOLOS={LETTERS}|{DIGIT}|{SIMBOLOS_CONJ}|{ESPECIALES}
LISTA_SIMBOLOS={SIMBOLOS}(","{SIMBOLOS})*
CADENA=\"([^\"]|{ESPECIALES})*\"
CONJUNTO= ({LLAVEIZ}{ID}{LLAVED})
COMENT=("/""/"([^\n]*))
CONJ="CONJ"

%%
//___________Tokens___________

{IGNORAR} {}
{CONJ} {return new Symbol(sym.TK_CONJ, yytext());}
{DIGIT} {return new Symbol(sym.TK_DIGIT, yytext());}
{LETTERS} {return new Symbol(sym.TK_LETTERS, yytext());}
{DPORCENTAJE} {return new Symbol(sym.TK_DPORCENTAJE, yytext());}
{ID} {return new Symbol(sym.TK_ID, yytext());}
{PC} {return new Symbol(sym.TK_PC, yytext());}
{CP} {return new Symbol(sym.TK_CP, yytext());}
{CK} {return new Symbol(sym.TK_CK, yytext());}
{OR} {return new Symbol(sym.TK_OR, yytext());}
{I} {return new Symbol(sym.TK_I, yytext());}
{P} {return new Symbol(sym.TK_P, yytext());}
{V} {return new Symbol(sym.TK_V, yytext());}
{ESPECIALES} {return new Symbol(sym.TK_ESPECIALES, yytext());}
{LLAVEIZ} {return new Symbol(sym.TK_LLAVEIZ, yytext());}
{LLAVED} {return new Symbol(sym.TK_LLAVED, yytext());}
{COMENT} {return new Symbol(sym.TK_COMENT, yytext());}
{COM_MTL} {return new Symbol(sym.TK_COM_MTL, yytext());}
{CADENA} {return new Symbol(sym.TK_CADENA, yytext());}
{ASIGNACION} {return new Symbol(sym.TK_ASIGNACION, yytext());}
{DOS_P} {return new Symbol(sym.TK_DOS_P, yytext());}
{SIMBOLOS} {return new Symbol(sym.TK_SIMBOLOS, yytext());}
{CONJUNTO} {return new Symbol(sym.TK_CONJUNTO, yytext());}
{LISTA_SIMBOLOS} {return new Symbol(sym.TK_LISTA_SIMBOLOS, yytext());}


//
. {
    System.out.println(" Error lexico"+yytext());
}


