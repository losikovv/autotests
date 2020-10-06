package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.ErrorMessage;
import instamart.api.v2.objects.Errors;

import java.util.List;

public class ErrorResponse extends BaseResponseObject {

    private Errors errors;
    private List<ErrorMessage> error_messages = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ErrorResponse() {
    }

    /**
     *
     * @param errors
     * @param error_messages
     */
    public ErrorResponse(Errors errors, List<ErrorMessage> error_messages) {
        super();
        this.errors = errors;
        this.error_messages = error_messages;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public List<ErrorMessage> getError_messages() {
        return error_messages;
    }

    public void setError_messages(List<ErrorMessage> error_messages) {
        this.error_messages = error_messages;
    }

}
