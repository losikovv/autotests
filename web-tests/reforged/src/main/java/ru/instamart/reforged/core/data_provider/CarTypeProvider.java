package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.ShoppersData;
import ru.instamart.kraken.data.VehiclesData;

public final class CarTypeProvider {

    private static final ApiHelper helper = new ApiHelper();

    @DataProvider(name = "carType")
    public static Object[][] getCarType() {
        final var shopperData = ShoppersData.courier();
        final var shopper = helper.createShopper(shopperData);
        return new Object[][] {
                {shopper, VehiclesData.car()},
                {shopper, VehiclesData.truck()},
                {shopper, VehiclesData.bicycle()},
                {shopper, VehiclesData.scooter()},
                {shopper, VehiclesData.electricbicycle()},
                {shopper, VehiclesData.other()}
        };
    }
}
