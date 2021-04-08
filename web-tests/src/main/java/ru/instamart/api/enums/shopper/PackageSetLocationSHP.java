package ru.instamart.api.enums.shopper;

public enum PackageSetLocationSHP {
    BASKET("basket",1),
    RACK("rack",2),
    FRIDGE("fridge",3),
    FREEZER("freezer",4);

    private final String location;
    private final int boxNumber;

    PackageSetLocationSHP(String location, int boxNumber) {
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
