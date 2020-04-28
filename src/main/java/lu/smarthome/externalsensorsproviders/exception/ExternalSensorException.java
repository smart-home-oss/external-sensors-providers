package lu.smarthome.externalsensorsproviders.exception;

public class ExternalSensorException extends RuntimeException {
    private HttpStatus statusCode;

    public ExternalSensorException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public ExternalSensorException(String s, Exception e) {
        super(s, e);
    }

    @Override
    public String getMessage() {
        return String.format(
                "Error loading sensor information, status code: %d, reason: %s",
                statusCode.value(),
                statusCode.getReasonPhrase()
        );
    }
}
