public class ParseError extends Exception {
    private String msg;
    ParseError(String message){
        msg = message;
    }
    public String getMessage() {
        return msg + "\nerror in parsing";
    }
}