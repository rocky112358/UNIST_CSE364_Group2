package se.group2;

public class InvalidInputException extends Exception {
    private final String message;

    InvalidInputException(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }

}
