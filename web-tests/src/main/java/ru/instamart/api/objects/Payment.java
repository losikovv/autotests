package instamart.api.objects;

public class Payment extends BaseObject {

    private String number;
    private String state;
    private Boolean is_finalization_needed;
    private Object finalization_url;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getIs_finalization_needed() {
        return is_finalization_needed;
    }

    public void setIs_finalization_needed(Boolean is_finalization_needed) {
        this.is_finalization_needed = is_finalization_needed;
    }

    public Object getFinalization_url() {
        return finalization_url;
    }

    public void setFinalization_url(Object finalization_url) {
        this.finalization_url = finalization_url;
    }

}
