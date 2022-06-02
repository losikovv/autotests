package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.admin.PromotionCodesAdminRequest;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;


@Epic("Admim")
@Feature("Промокоды")
public class PromotionCodesAdminTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.auth();
    }

    @Test(description = "Открытие страницы поиска промокодов",
            groups = {"api-instamart-regress"})
    public void getPromotionCodes() {
        final Response response = PromotionCodesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }
}
