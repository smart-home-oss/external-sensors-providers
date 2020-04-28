package lu.smarthome.externalsensorsproviders.exception;

//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OauthHelperException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Internal error occurred, please check the logs or contact the Operations.";
    }
}
