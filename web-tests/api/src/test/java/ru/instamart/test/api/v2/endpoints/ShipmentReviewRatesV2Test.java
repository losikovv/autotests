package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.K8sHelper;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.ShipmentReviewRatesV2Request;
import ru.instamart.api.response.v2.ShipmentReviewRatesV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Раздельная оценка заказа")
public class ShipmentReviewRatesV2Test extends RestBase {
    private String shipmentNumber;
    private final int assemblyRate = 5;
    private final int shippingRate = 2;


    @BeforeClass(alwaysRun = true)
    public void precondition() {
        apiV2.authByPhone(UserManager.getDefaultApiUser());
        //todo починить на стейдже GET /v2/shipments/null/shipping_rates
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        shipmentNumber = order.getShipments().get(0).getNumber();
        K8sHelper.changeToShip(shipmentNumber);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2642)
    @Test(description = "Оценить заказ | доставка + сборка",
            groups = {"api-instamart-regress"})
    public void postShipmentReviewRates200() {
        Response response = ShipmentReviewRatesV2Request.POST(shipmentNumber, shippingRate, assemblyRate);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentReviewRatesV2Response.class);

        var rates =  response.as(ShipmentReviewRatesV2Response.class).getShipmentReview().getRates();

        assertEquals(rates.get(0).getRate(), assemblyRate);
        assertEquals(rates.get(1).getRate(), shippingRate);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2790)
    @Test(description = "Оценить заказ | несуществующий номер заказа",
            groups = {"api-instamart-regress"})
    public void postShipmentReviewRates404() {
        Response response = ShipmentReviewRatesV2Request.POST("H37574873852", shippingRate, assemblyRate);

        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }
}
