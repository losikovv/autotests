package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.ErrorMessage;
import instamart.api.objects.v2.Errors;

import java.util.List;

public class ErrorResponse extends BaseResponseObject {

    private String error;
    private Errors errors;
    private List<ErrorMessage> error_messages = null;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
