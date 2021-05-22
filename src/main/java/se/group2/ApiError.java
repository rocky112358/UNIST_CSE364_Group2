package se.group2;

public class ApiError {
    private final String error;
    private final String message;
    private final Integer statusCode;

    public ApiError(Integer s, String e, String m) {
        statusCode = s;
        error = e;
        message = m;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}
