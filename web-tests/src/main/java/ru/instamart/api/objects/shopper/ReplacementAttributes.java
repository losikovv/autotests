package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class ReplacementAttributes extends BaseObject {
    private Integer fromItemId;
    private Integer toItemId;
    private String reason;

    public Integer getFromItemId() {
        return fromItemId;
    }

    public void setFromItemId(Integer fromItemId) {
        this.fromItemId = fromItemId;
    }

    public Integer getToItemId() {
        return toItemId;
    }

    public void setToItemId(Integer toItemId) {
        this.toItemId = toItemId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
