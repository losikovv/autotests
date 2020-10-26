package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.Error;

import java.util.List;

public class ErrorResponse extends BaseResponseObject {

    private List<Error> errors = null;

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

}
