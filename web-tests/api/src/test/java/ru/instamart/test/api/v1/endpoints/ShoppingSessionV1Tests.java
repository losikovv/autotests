package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.ShoppingSessionV1Request;
import ru.instamart.api.model.v1.ShoppingSessionV1;
import ru.instamart.api.response.v1.ShoppingSessionV1Response;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Сессия")
public class ShoppingSessionV1Tests extends RestBase {

    @CaseId(1417)
    @Story("Сессия покупки")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение сессии для авторизованного пользователя")
    public void getShoppingSessionWithAuth() {
        UserData user = UserManager.getDefaultAdminAllRoles();
        SessionFactory.createSessionToken(SessionType.API_V1, user);
        final Response response = ShoppingSessionV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShoppingSessionV1Response.class);
        ShoppingSessionV1 shoppingSession = response.as(ShoppingSessionV1Response.class).getShoppingSession();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shoppingSession.getCurrentUser().getEmail(), user.getEmail(), softAssert);
        softAssert.assertTrue(shoppingSession.getCurrentUser().getIsAdmin(), "Пришел статус не-админ");
        softAssert.assertAll();
    }

    @CaseId(1418)
    @Story("Сессия покупки")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение сессии для неавторизованного пользователя")
    public void getShoppingSessionWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = ShoppingSessionV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShoppingSessionV1Response.class);
        ShoppingSessionV1 shoppingSession = response.as(ShoppingSessionV1Response.class).getShoppingSession();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNull(shoppingSession.getCurrentUser(), "Текущий пользователь не пустой");
        softAssert.assertNull(shoppingSession.getStore(), "Магазин не пустой");
        softAssert.assertNull(shoppingSession.getShippingMethodKind(), "Метод доставки не пустой");
        softAssert.assertNull(shoppingSession.getCurrentShipAddress(), "Адрес не пустой");
        softAssert.assertAll();
    }
}
