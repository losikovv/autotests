package ru.instamart.application.rest.objects;

public class ErrorMessage extends BaseObject {

    private String field;
    private String message;
    private String human_message;

    /**
     * No args constructor for use in serialization
     *
     */
    public ErrorMessage() {
    }

    /**
     *
     * @param field
     * @param human_message
     * @param message
     */
    public ErrorMessage(String field, String message, String human_message) {
        super();
        this.field = field;
        this.message = message;
        this.human_message = human_message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHuman_message() {
        return human_message;
    }

    public void setHuman_message(String human_message) {
        this.human_message = human_message;
    }

}
