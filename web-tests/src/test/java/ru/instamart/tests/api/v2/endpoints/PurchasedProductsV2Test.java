package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.request.v2.PurchasedProductsV2Request;
import ru.instamart.api.response.v2.ProductsV2Response;
import ru.instamart.ui.data.pagesdata.EnvironmentData;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

@Epic("ApiV2")
@Feature("Купленные продукты")
public final class PurchasedProductsV2Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        apiV2.order(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentData.INSTANCE.getDefaultSid());
    }

    @AfterClass(alwaysRun = true,
            description = "Отменяем заказ")
    public void cleanup() {
        apiV2.cancelCurrentOrder();
    }

    @CaseId(266)
    @Story("Получить список ранее купленных продуктов")
    @Test(  description = "Существующий sid",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void testGetPurchasedProductWithValidSid() {
        final Response response = PurchasedProductsV2Request.GET(
                SessionFactory.getSession(SessionType.API_V2).getToken(),
                EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode200(response);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        assertNotNull(productsV2Response, "Вернулся пустой ответ");
        assertNotNull(productsV2Response.getProducts(), "Список продуктов не вернулся");
        assertFalse(productsV2Response.getProducts().isEmpty(), "Список продуктов вернулся пустой");
    }

    @CaseId(267)
    @Story("Получить список ранее купленных продуктов")
    @Test(  description = "Несуществующий sid",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void testGetPurchasedProductWithInvalidSid() {
        final Response response = PurchasedProductsV2Request.GET(
                SessionFactory.getSession(SessionType.API_V2).getToken(),
                6666666);
        checkStatusCode404(response);
    }

    @CaseId(268)
    @Story("Получить список ранее купленных продуктов")
    @Test(  description = "Отправка запроса без авторизации",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void testGetPurchasedProductWithValidSidAndInvalidAuth() {
        final Response response = PurchasedProductsV2Request.GET(
                "invalid_token",
                EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode401(response);
    }
}
