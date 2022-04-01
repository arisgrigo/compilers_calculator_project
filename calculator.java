import java.io.InputStream;
import java.io.IOException;
import java.lang.Math;

class calculator {

    private int lookaheadToken;

    private InputStream input;

    public calculator(InputStream input) throws IOException {
        this.input = input;
        lookaheadToken = input.read();
    }

    private void consume(int symbol) throws IOException, ParseError {
        if (lookaheadToken != symbol)
            throw new ParseError("Character not allowed\n");
            lookaheadToken = input.read();
    }

    private int evalDigit(int digit){
        return digit - '0';
    }

    public int eval() throws IOException, ParseError {
        boolean flag=false;
        int token = expr(flag);
        if (lookaheadToken != '\n' && lookaheadToken != -1){

        }
        //throw new ParseError();
        return token;
    }

    public int factor2(int prevNum, boolean flag) throws IOException, ParseError{
        int result;
        int tempToken = lookaheadToken;

        if(flag){
            if(tempToken != '\n' && tempToken != -1  && tempToken != '(' && tempToken != ')' && tempToken != '+' && tempToken != '-' && tempToken != '*' && ((tempToken > '9') || tempToken < '0'))
                throw new ParseError("Character not allowed\n");
        }else if(tempToken != '\n' && tempToken != -1  && tempToken != '+' && tempToken != '-' && tempToken != '*' && ((tempToken > '9') || tempToken < '0'))
            throw new ParseError("Character not allowed\n");

        if((tempToken > '9') || tempToken < '0')
            return prevNum;

        consume(lookaheadToken);
        int temp = prevNum * 10 + evalDigit(tempToken);
        result = factor2(temp, flag);

        if(result == -1)
            return prevNum;

        return result;
    }

    public int factor(boolean flag) throws IOException, ParseError{
        int result;
        int rv;
        int tempToken = lookaheadToken;
        boolean pareFlag = false;
        if(tempToken != '(' && ((tempToken > '9') || tempToken < '0'))
            throw new ParseError("Character not allowed\n");

        consume(lookaheadToken);
        if(tempToken == '0' && (lookaheadToken >= '0' && lookaheadToken <= '9'))
            throw new ParseError("Character not allowed\n");

        if(tempToken == '(') {
            pareFlag = true;
            result = expr(pareFlag);
            rv = result;
        }else{
            result = factor2(evalDigit(tempToken), flag);
            if(result == -1){
                rv = evalDigit(tempToken);
            }else rv = result;

        }
        if(pareFlag){
            pareFlag=false;
            consume(')');
            tempToken = lookaheadToken;
            if(tempToken != '+' && tempToken != '-' && tempToken != ')' && tempToken != '*' && tempToken != '\n' && tempToken != -1)
                throw new ParseError("Character not allowed\n");
        }

        return rv;
    }

    public int term2(int termToken, boolean flag) throws IOException, ParseError{
        int tempToken = lookaheadToken;

        if(flag){
            if(tempToken != '*' && tempToken != '(' && tempToken != ')' && tempToken != '\n' && tempToken != -1  && tempToken != '+'  && tempToken != '-')
                throw new ParseError("Character not allowed\n");
        }else if(tempToken != '*' && tempToken != '\n' && tempToken != -1  && tempToken != '+'  && tempToken != '-')
            throw new ParseError("Character not allowed\n");

        if (tempToken == '\n' || tempToken == -1){
            return termToken;
        }

        if(tempToken == '*'){
            consume('*');
            consume('*');

            int token = factor(flag);

            int ret = term2(token, flag);

            if(ret == token)
                return (int)Math.pow(termToken,token);

            int result = (int)Math.pow(termToken,ret);


            return result;
        }

        return termToken;
    }

    public int term(boolean flag) throws IOException, ParseError{
        int token = factor(flag);
        int result = term2(token, flag);
        if(result == token)
            return token;
        return result;
    }

    public int expr2(int termToken, boolean flag) throws IOException, ParseError{
        int tempToken = lookaheadToken;


        if(flag){
            if(tempToken != '+' && tempToken != '-' && tempToken != '*' && tempToken != '(' && tempToken != ')' && tempToken != '\n' && tempToken != -1)
                throw new ParseError("Character not allowed\n");
        }else if(tempToken != '+' && tempToken != '-' && tempToken != '*'  && tempToken != '\n' && tempToken != -1)
            throw new ParseError("Character not allowed\n");


        if (tempToken == '\n' || tempToken == -1){
            return termToken;
        }

        if(tempToken == '+'){
            consume('+');
            int token = term(flag);
            int ret = expr2(token, flag);

            if(ret == token)
                return  termToken + token;
            return (termToken + ret);
        }

        if(tempToken == '-'){
            consume('-');
            int token = term(flag);
            token = token * (-1);
            int ret = expr2(token,flag);
            ret = ret * (-1);

            return (termToken - ret);
        }

        return termToken;

    }

    public int expr(boolean flag) throws IOException, ParseError{
        int token = term(flag);

        int result = expr2(token, flag);

        if(result==token)
            return token;

        return result;
    }

    public static void main(String[] args) {
        //while(true){
            try {
                System.out.println("Enter input:");
                calculator evaluate = new calculator(System.in);
                System.out.println(evaluate.eval());
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
            catch(ParseError err){
                System.err.println(err.getMessage());
            }
        //}
    }
}