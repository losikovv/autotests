package instamart.api.objects;

public class Alert extends BaseObject {

    private String message;
    private String full_message;
    private String type;

    /**
     * No args constructor for use in serialization
     *
     */
    public Alert() {
    }

    /**
     *
     * @param full_message
     * @param message
     */
    public Alert(String message, String full_message) {
        super();
        this.message = message;
        this.full_message = full_message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFull_message() {
        return full_message;
    }

    public void setFull_message(String full_message) {
        this.full_message = full_message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
