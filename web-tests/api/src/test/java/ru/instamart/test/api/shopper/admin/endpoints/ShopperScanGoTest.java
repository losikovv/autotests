package ru.instamart.test.api.shopper.admin.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.admin.ShopperAdminRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.shopper.admin.ScangoEnginesResponse;
import ru.instamart.kraken.data.user.UserManager;
import io.qameta.allure.TmsLink;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.Group.API_SHOPPER_PROD;
import static ru.instamart.api.Group.API_SHOPPER_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("Shopper Admin Panel API")
@Feature("Endpoints")
public class ShopperScanGoTest extends RestBase {

    @Story("Scan&Go")
    @TmsLink("103")
    @Test(description = "Получение конфига с валидным токеном",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void shopperScanGo200() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_ADMIN, UserManager.getDefaultAdminOld());
        response = ShopperAdminRequest.Scango.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ScangoEnginesResponse.class);
    }

    @Story("Scan&Go")
    @TmsLink("103")
    @Test(description = "Получение конфига с не валидным токеном",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void shopperScanGo401() {
        SessionFactory.clearSession(SessionType.SHOPPER_ADMIN);
        response = ShopperAdminRequest.Scango.GET();
        checkStatusCode401(response);
        assertEquals(response.as(ErrorResponse.class).getError(), "token_missing", "Error message not valid");
    }
}
