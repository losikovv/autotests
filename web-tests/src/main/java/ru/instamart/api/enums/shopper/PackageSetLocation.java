package instamart.api.enums.shopper;

public enum PackageSetLocation {
    BASKET("basket",1),
    RACK("rack",2),
    FRIDGE("fridge",3),
    FREEZER("freezer",4);

    private final String location;
    private final int boxNumber;

    PackageSetLocation(String location, int boxNumber) {
        this.location = location;
        this.boxNumber = boxNumber;
    }

    public String getLocation() {
        return location;
    }

    public int getBoxNumber() {
        return boxNumber;
    }
}
