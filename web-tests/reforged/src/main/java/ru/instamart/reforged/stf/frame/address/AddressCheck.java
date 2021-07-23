package ru.instamart.reforged.stf.frame.address;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AddressCheck extends AddressElement{

    default void checkSaveButtonIsClickable() {
        waitAction().shouldBeClickable(save);
    }

    default void checkAddressDropdownNotVisible() {
        waitAction().shouldNotBeVisible(dropDownAddress);
    }
}
