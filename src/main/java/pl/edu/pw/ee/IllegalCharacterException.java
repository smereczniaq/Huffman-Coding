package pl.edu.pw.ee;

public class IllegalCharacterException extends RuntimeException {

    IllegalCharacterException(String errorMessage) {
        super(errorMessage);
    }
}
