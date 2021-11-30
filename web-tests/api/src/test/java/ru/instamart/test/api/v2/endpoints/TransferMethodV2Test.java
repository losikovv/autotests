package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.AnalyzeResultV2;
import ru.instamart.api.enums.v2.ShippingMethodsV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ZoneV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.TransferMethodV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;

import java.util.HashMap;
import java.util.Map;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Проверка потерь")
public class TransferMethodV2Test extends RestBase {

    private OrderV2 order;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.fillCart(userData, EnvironmentProperties.DEFAULT_SID);
        order = apiV2.getOpenOrder();
    }

    @CaseIDs(value = {@CaseId(994), @CaseId(995), @CaseId(996), @CaseId(997), @CaseId(998)})
    @Story("Магазин без самовывоза")
    @Test(  groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesDataForTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzeTransferToAnotherAddress(Integer storeId, AnalyzeResultV2 analyzeResult) {
        ZoneV2 zone = apiV2.getCoordinates(storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("shipping_method_kind", ShippingMethodsV2.BY_COURIER.getMethod());
        params.put("address_params[lat]", zone.getLat());
        params.put("address_params[lon]", zone.getLon());
        final Response response = OrdersV2Request.TransferMethod.GET(params, order.getNumber());
        checkStatusCode200(response);
        compareTwoObjects(analyzeResult.getValue(), response.as(TransferMethodV2Response.class).getResult());
    }
}
