package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;
import ru.instamart.kraken.data.Promos;

public final class PromoCodeProvider {

    @DataProvider(name = "promo_code")
    public static Object[][] getPromoCode() {
        return new Object[][] {
                {Promos.fixedDiscountOnFirstOrder()},
                {Promos.fixedDiscountForRetailer("metro")},
                {Promos.fixedDiscountForNewUser()},
                {Promos.freeDeliveryOnFirstOrder()},
                {Promos.fixedDiscountForSerialOrder()},
        };
    }
}
