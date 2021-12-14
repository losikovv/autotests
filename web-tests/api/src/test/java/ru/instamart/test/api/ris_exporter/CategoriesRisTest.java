package ru.instamart.test.api.ris_exporter;

import io.qameta.allure.Epic;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.ris_exporter.CategoriesRisRequest;
import ru.instamart.api.request.ris_exporter.ProductsRisRequest;
import ru.instamart.api.response.delivery_club.CatalogCategoriesDCResponse;
import ru.instamart.api.response.ris_exporter.CategoriesRisResponse;
import ru.instamart.api.response.ris_exporter.ProductsRisResponse;
import ru.instamart.api.response.shopper.app.AssemblySHPResponse;
import ru.instamart.kraken.data.user.UserManager;

import java.time.LocalDateTime;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.api.helper.JsonSchemaHelper.getJsonSchema;

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
        checkResponseJsonSchema(response, CategoriesRisResponse.class);
    }

    @CaseId(426)
    @Test(groups = {},
            description = "Получение категорий по неверному storeId")
    public void getCategories404() {
        Response response = CategoriesRisRequest.GET(9999999);

        checkStatusCode404(response);
    }

    @CaseId(434)
    @Test(groups = {},
            description = "Проверка количества категорий",
            dataProviderClass = RestDataProvider.class,
            dataProvider = "risStoreIds" )
    public void getCategoriesValidCount(Integer sid) {
        Response response = CategoriesRisRequest.GET(sid);

        assertTrue(response.as(CategoriesRisResponse.class)
                .getTotalCount() > 100, "Категорий меньше минимума");
    }

}
