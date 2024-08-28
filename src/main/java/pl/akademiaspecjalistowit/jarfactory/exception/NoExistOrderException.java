package pl.akademiaspecjalistowit.jarfactory.exception;

public class NoExistOrderException extends RuntimeException{
    public NoExistOrderException(String message) {
        super(message);
    }
}
