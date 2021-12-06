package ru.instamart.test.api.ris_exporter;

import io.qameta.allure.Epic;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.ris_exporter.CategoriesRisRequest;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Категории")
public class CategoriesRisTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.RIS_EXPORTER, UserManager.getRisUser());
    }

    @CaseId(424)
    @Test(groups = {},
            description = "Получение категорий по storeId")
    public void getCategories200() {
        Response response = CategoriesRisRequest.GET(1);

        checkStatusCode200(response);
    }
}
