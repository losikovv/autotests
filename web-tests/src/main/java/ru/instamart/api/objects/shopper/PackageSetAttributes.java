package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class PackageSetAttributes extends BaseObject {
    private String location;
    private Object qty;
    private String number;
    private String boxNumber;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Object getQty() {
        return qty;
    }

    public void setQty(Object qty) {
        this.qty = qty;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

}
