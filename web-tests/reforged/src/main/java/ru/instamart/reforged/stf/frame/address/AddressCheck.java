package ru.instamart.reforged.stf.frame.address;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AddressCheck extends AddressElement {

    default void checkAddressModalIsNotVisible() {
        waitAction().shouldNotBeVisible(addressModal);
    }

    default void checkMarkerOnMapInAdviceIsNotVisible() {
        waitAction().shouldNotBeVisible(markerImageOnMapInAdvice);
    }
}
