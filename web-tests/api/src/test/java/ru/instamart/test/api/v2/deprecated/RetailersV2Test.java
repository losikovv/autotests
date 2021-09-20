package ru.instamart.test.api.v2.deprecated;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.request.v2.RetailersV2Request;
import ru.instamart.api.response.v2.RetailerV2Response;
import ru.instamart.api.response.v2.RetailersV2Response;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Получение данных о ретейлере")
@Deprecated
public final class RetailersV2Test extends RestBase {

    private List<RetailerV2> retailers;

    @BeforeClass(alwaysRun = true)
    @Story("Получение списка ретейлеров")
    public void preconditions() {
        final Response response = RetailersV2Request.GET();
        checkStatusCode200(response);
        retailers = response.as(RetailersV2Response.class).getRetailers();
        assertNotEquals(retailers.size(), 0, "Список ретейлеров пустой");
    }

    @Deprecated
    @Test(  groups = {},
            description = "Получение ретейлера по валидному retailer id")
    public void testGetRetailerWithValidRetailerId() {
        final Optional<RetailerV2> retailerOption = retailers.stream().findAny();
        assertTrue(retailerOption.isPresent(), "Ретейлеров нет в списке");
        final Response response = RetailersV2Request.GET(retailerOption.get().getId());
        checkStatusCode200(response);
        final RetailerV2 retailer = response.as(RetailerV2Response.class).getRetailer();
        assertNotNull(retailer, "Ретейлер не был получен");
    }

    @Deprecated
    @Test(  groups = {},
            description = "Получение ретейлера по невалидному retailer id")
    public void testGetRetailerWithInvalidRetailerId() {
        final Response response = RetailersV2Request.GET(66666);
        checkStatusCode404(response);
    }
}
