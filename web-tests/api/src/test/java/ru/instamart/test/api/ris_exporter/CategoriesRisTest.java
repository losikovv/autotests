package ru.instamart.test.api.ris_exporter;

import io.qameta.allure.Epic;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.ris_exporter.CategoriesRisRequest;
import ru.instamart.api.response.ris_exporter.CategoriesRisResponse;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("Категории")
public class CategoriesRisTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.RIS_EXPORTER, UserManager.getRisUser());
    }

    @TmsLink("424")
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение категорий по storeId")
    public void getCategories200() {
        Response response = CategoriesRisRequest.GET(1);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, CategoriesRisResponse.class);
    }

    @TmsLink("426")
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение категорий по неверному storeId")
    public void getCategories404() {
        Response response = CategoriesRisRequest.GET(9999999);

        checkStatusCode404(response);
    }

    @TmsLink("434")
    @Test(  groups = {"api-ris-exporter"},
            description = "Проверка количества категорий",
            dataProviderClass = RestDataProvider.class,
            dataProvider = "risStoreIdsProduction")
    public void getCategoriesValidCount(Integer sid) {
        Response response = CategoriesRisRequest.GET(sid);

        checkStatusCode200(response);
        assertTrue(response.as(CategoriesRisResponse.class)
                .getTotalCount() > 100, "Категорий меньше минимума");
    }
}
