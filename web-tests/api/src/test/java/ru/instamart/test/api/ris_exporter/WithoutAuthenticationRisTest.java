package ru.instamart.test.api.ris_exporter;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.ris_exporter.CategoriesRisRequest;
import ru.instamart.api.request.ris_exporter.ProductsRisRequest;
import ru.instamart.api.request.ris_exporter.StockRisRequest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("Аутентификация")
public class WithoutAuthenticationRisTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.clearSession(SessionType.RIS_EXPORTER);
    }

    @CaseId(430)
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение категорий без авторизации")
    public void getCategories401() {
        Response response = CategoriesRisRequest.GET(1);

        checkStatusCode401(response);
    }

    @CaseId(431)
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение списка продуктов без авторизации")
    public void getProducts401() {
        Response response = ProductsRisRequest.GET(1);

        checkStatusCode401(response);
    }

    @CaseId(432)
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение остатка на складах без авторизации")
    public void getStock401() {
        Response response = StockRisRequest.GET(1);

        checkStatusCode401(response);
    }
}
