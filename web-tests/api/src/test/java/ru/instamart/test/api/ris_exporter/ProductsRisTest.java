package ru.instamart.test.api.ris_exporter;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.ris_exporter.ProductsRisRequest;
import ru.instamart.api.response.ris_exporter.ProductsRisResponse;
import ru.instamart.kraken.data.user.UserManager;
import io.qameta.allure.TmsLink;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("Продукты")
public class ProductsRisTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.RIS_EXPORTER, UserManager.getRisUser());
    }

    @TmsLink("427")
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение списка продуктов")
    public void getProducts200() {
        Response response = ProductsRisRequest.GET(1);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsRisResponse.class);
    }

    @TmsLink("429")
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение списка продуктов по неверному storeId")
    public void getProducts404() {
        Response response = ProductsRisRequest.GET(9999999);

        checkStatusCode404(response);
    }

    @TmsLink("433")
    @Test(  groups = {"api-ris-exporter"},
            description = "Проверка количества продуктов для всех ритейлеров",
            dataProviderClass = RestDataProvider.class,
            dataProvider = "risStoreIdsProduction")
    public void getProductsValidCount(Integer sid) {
        Response response = ProductsRisRequest.GET(sid);

        checkStatusCode200(response);
        assertTrue(response.as(ProductsRisResponse.class)
                .getTotalCount() > 5000, "Продуктов меньше минимума");
    }
}
