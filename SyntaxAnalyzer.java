import java.io.*;
import java.util.*;

public class SyntaxAnalyzer {

    public enum Keywords {
        begin, end, semicolon, declare, comma, assignment, period, ifsym,
        thensym, elsesym, end_if, odd, colon, leftbrack, rightbrack, whilesym, loop,
        plus, minus, product, division, leftpar, rightpar,
        dosym, equals, var, constsym, call, procedure, notequals, lessthan,
        greaterthan, greaterequals, lessequals, ident, number
    }

    private static String line = "";
    private static String[] tokens;
    private static int index = 0;
    private static Keywords k;
    private static int lineCounter = 0;

    public static void main(String[] args) {

        File input = new File("src/example.txt");
        Scanner sc = null;
        int i;

        try {
            sc = new Scanner(input);

            while(sc.hasNextLine()) {
                line += sc.nextLine();
            }

            line = line.replace(" ", "");
            //System.out.println(line);

            findSymbol();
            block();


        }  catch(FileNotFoundException e) {
            System.err.println("ERROR: Could not locate file for input tokens");
        }

    }

    private static void findSymbol() {

        if(line.charAt(index) == 'c' && line.charAt(index+1) == 'o' && line.charAt(index+2) == 'n'
                && line.charAt(index+3) == 's' && line.charAt(index+4) == 't')
        {
            k = Keywords.constsym;
            index += 5;
            return;
        }
        else if(line.charAt(index) == 'v' && line.charAt(index+1) == 'a'
                && line.charAt(index+2) == 'r')
        {
            k = Keywords.var;
            index += 3;
            return;
        }
        else if(line.charAt(index) == 'p' && line.charAt(index+1) == 'r'
                && line.charAt(index+2) == 'o' && line.charAt(index+3) == 'c'
                && line.charAt(index+4) == 'e' && line.charAt(index+5) == 'd'
                && line.charAt(index+6) == 'u' && line.charAt(index+7) == 'r'
                && line.charAt(index+8) == 'e')
        {
            k = Keywords.procedure;
            index += 9;
            return;
        }
        else if(line.charAt(index) == 'b' && line.charAt(index+1) == 'e'
                && line.charAt(index+2) == 'g' && line.charAt(index+3) == 'i'
                && line.charAt(index+4) == 'n')
        {
            k = Keywords.begin;
            index += 5;
            return;

        }
        else if(line.charAt(index) == 'o' && line.charAt(index+1) == 'd'
                && line.charAt(index+2) == 'd')
        {
            k = Keywords.odd;
            index += 3;
            return;
        }
        else if(line.charAt(index) == 'i' && line.charAt(index+1) == 'f')
        {
            k = Keywords.ifsym;
            index += 2;
            return;
        }
        else if(line.charAt(index) == 't' && line.charAt(index+1) == 'h'
                && line.charAt(index+2) == 'e' && line.charAt(index+3) == 'n')
        {
            k = Keywords.thensym;
            index += 4;
            return;
        }
        else if(line.charAt(index) == 'w' && line.charAt(index+1) == 'h'
                && line.charAt(index+2) == 'i' && line.charAt(index+3) == 'l'
                && line.charAt(index+4) == 'e')
        {
            k = Keywords.whilesym;
            index += 5;
            return;
        }
        else if(line.charAt(index) == 'd' && line.charAt(index+1) == 'o')
        {
            k = Keywords.dosym;
            index += 2;
            return;
        }
        else if(line.charAt(index) == 'e' && line.charAt(index+1) == 'n'
                && line.charAt(index+2) == 'd')
        {
            k = Keywords.end;
            index += 3;
            return;
        }
        else if((int)line.charAt(index) >= 48 && (int)line.charAt(index) <= 57)
        {
            k = Keywords.number;
            index++;

            while((int)line.charAt(index) >= 48 && (int)line.charAt(index) <= 57) {
                index++;
            }

            return;
        }
        else if(line.charAt(index) == ',')
        {
            k = Keywords.comma;
            index++;
            return;
        }
        else if(line.charAt(index) == ';')
        {
            k = Keywords.semicolon;
            index++;
            return;
        }
        else if(line.charAt(index) == ':' && line.charAt(index+1) == '=')
        {
            k = Keywords.assignment;
            index += 2;
            return;
        }
        else if(line.charAt(index) == '+')
        {
            k = Keywords.plus;
            index++;
            return;
        }
        else if(line.charAt(index) == '-')
        {
            k = Keywords.minus;
            index++;
            return;
        }
        else if(line.charAt(index) == '*')
        {
            k = Keywords.product;
            index++;
            return;
        }
        else if(line.charAt(index) == '/')
        {
            k = Keywords.division;
            index++;
            return;
        }
        else if(line.charAt(index) == '=')
        {
            k = Keywords.equals;
            index++;
            return;
        }
        else if(line.charAt(index) == '<' && line.charAt(index+1) == '=')
        {
            k = Keywords.lessequals;
            index += 2;
            return;
        }
        else if(line.charAt(index) == '>' && line.charAt(index+1) == '=')
        {
            k = Keywords.greaterequals;
            index += 2;
            return;
        }
        else if(line.charAt(index) == '<' && line.charAt(index+1) == '>') {
            k = Keywords.notequals;
            index += 2;
            return;
        }
        else if(line.charAt(index) == '<')
        {
            k = Keywords.lessthan;
            index++;
            return;
        }
        else if(line.charAt(index) == '>')
        {
            k = Keywords.greaterthan;
            index++;
            return;
        }

        else {
            k = Keywords.ident;
            index++;
            return;
        }



    }

    private static void block() {

        if(k == Keywords.constsym)
        {
            lineCounter++;
            while(true) {
                findSymbol();
                if(k == Keywords.ident) {
                    findSymbol();
                }  else {
                    System.err.println("ERROR: Expecting an identifier on line " + lineCounter);
                }
                if(k == Keywords.equals) {
                    findSymbol();
                }  else {
                    System.err.println("ERROR: Expecting equals symbol on line " + lineCounter);
                }
                if(k == Keywords.number) {
                    findSymbol();
                }  else {
                    System.err.println("ERROR: Expecting a constant on line " + lineCounter);
                }
                if(k == Keywords.comma) {
                }  else if(k == Keywords.semicolon) {
                    findSymbol();
                    break;
                }  else {
                    if(k != Keywords.ident && k != Keywords.comma) {
                        System.err.println("ERROR: Expecting a semicolon on line " + lineCounter);
                        break;
                    }  else {
                        System.err.println("ERROR: Expecting a comma on line " + lineCounter);
                    }

                    System.out.println(k);
                }
            }
        }

        if(k == Keywords.var)
        {
            lineCounter++;
            findSymbol();
            while(true) {
                if(k == Keywords.ident) {
                    findSymbol();
                    while(true) {
                        if(k == Keywords.ident) {
                            findSymbol();
                        }  else {
                            break;
                        }
                    }
                }  else {
                    System.err.println("ERROR: Expecting an identifier on line " + lineCounter);
                }
                if(k == Keywords.comma) {
                    findSymbol();
                }  else if(k == Keywords.semicolon) {
                    findSymbol();
                    break;
                }  else {
                    System.out.println(k);
                    if(k != Keywords.ident && k != Keywords.comma) {
                        System.err.println("ERROR: Expecting a semicolon on line " + lineCounter);
                        break;
                    }  else {
                        System.out.println(k);
                        System.err.println("ERROR: Expecting a comma on line " + lineCounter);
                    }
                }
            }
        }

        if(k == Keywords.procedure)
        {
            lineCounter++;
            findSymbol();
            if(k == Keywords.ident) {
            }  else {
                System.err.println("ERROR: Expecting an identifier on line " + lineCounter);
            }

            while(k == Keywords.ident) {
                findSymbol();
            }

            if(k == Keywords.semicolon) {
                findSymbol();
            }  else {
                System.err.println("ERROR: Expecting a semicolon on line " + lineCounter);
            }
            if(k == Keywords.var) {
                block();
            }
        }
        statement();
    }


    private static void statement() {

        if(k == Keywords.ident)
        {
            System.out.println(k);
            findSymbol();

            if(k == Keywords.assignment) {
                System.out.println(k);
                findSymbol();
            }  else {
                System.err.println("ERROR: Expecting an assignment character on line " + lineCounter);
            }
            expression();
        }  else if(k == Keywords.assignment) {
            System.err.println("ERROR: Expecting an identifier on line " + lineCounter);
        }
        if(k == Keywords.call)
        {
            findSymbol();
            if(k == Keywords.ident) {
                findSymbol();
            }  else {
                System.err.println("ERROR: Expecting an identifier on line " + lineCounter);
            }
        }
        if(k == Keywords.begin)
        {
            lineCounter++;
            findSymbol();
            while(true) {
                statement();
                if(k == Keywords.semicolon) {
                    System.out.println(k);
                    findSymbol();
                }  else if(k == Keywords.end) {
                    findSymbol();
                    if(k == Keywords.end) {
                    }  else if(k == Keywords.semicolon) {
                        System.out.println("End of program");
                    }  else {
                        System.err.println("ERROR: Expecting semicolon or end statement");
                    }
                    break;
                }  else {
                    System.err.println("ERROR: Expecting a semicolon on line " + lineCounter);
                    findSymbol();
                }
            }
        }  else {
            System.err.println("ERROR: expecting a begin statement");
            return;
        }
        if(k == Keywords.ifsym)
        {
            System.out.println(k);
            condition();
            if(k == Keywords.thensym) {
                System.out.println(k);
                findSymbol();
            }  else {
                System.err.println("ERROR: Expecting then statement");
            }
            statement();
        }
        if(k == Keywords.whilesym)
        {
            System.out.println(k);
            condition();
            if(k == Keywords.dosym) {
                System.out.println(k);
                findSymbol();
            }  else {
                System.err.println("ERROR: Expecting do statement");
            }
            statement();
        }


    }

    private static void condition() {
        findSymbol();
        if(k == Keywords.odd)
        {
            System.out.println(k);
            findSymbol();
            expression();
        }
        if(k == Keywords.ident || k == Keywords.number)
        {
            expression();
            if(k == Keywords.equals || k == Keywords.notequals || k == Keywords.lessthan
                    || k == Keywords.greaterthan || k == Keywords.lessequals
                    || k == Keywords.greaterequals)
            {
                findSymbol();
                expression();
            }
        }
    }

    private static void expression() {

        if(k == Keywords.ident || k == Keywords.number)
        {

            System.out.println(k);
            term();

            while(true) {
                if(k == Keywords.plus || k == Keywords.minus) {
                    System.out.println(k);
                    findSymbol();
                    System.out.println(k);
                    term();
                    findSymbol();
                    System.out.println(k);
                }  else {
                    break;
                }
            }
        }


    }

    private static void term() {

        System.out.println("In term");
        if(k == Keywords.ident || k == Keywords.number)
        {
            factor();
            //findSymbol();
            while(true) {
                if(k == Keywords.product || k == Keywords.division) {
                    findSymbol();
                    factor();
                }  else {
                    findSymbol();
                    System.out.println(k);
                    break;
                }
            }
        }
        else if(k == Keywords.leftpar)
        {
            findSymbol();
            expression();
        }

    }

    private static void factor() {

        System.out.println("In factor");
        if(k == Keywords.ident || k == Keywords.number) {
        }  else if(k == Keywords.leftpar) {
            expression();
            if(k == Keywords.rightpar) {
            }  else {
                System.err.println("ERROR: Expecting a right paranthesis");
            }
        }  else {
            System.err.println("ERROR: Expecting a factor");

        }

    }

}