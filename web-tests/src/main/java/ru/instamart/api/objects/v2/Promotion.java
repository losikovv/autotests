package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;

public class Promotion extends BaseObject {

    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public Promotion() {
    }

    /**
     *
     * @param message
     */
    public Promotion(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
