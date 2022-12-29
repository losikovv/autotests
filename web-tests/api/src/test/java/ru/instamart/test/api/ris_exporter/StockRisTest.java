package ru.instamart.test.api.ris_exporter;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.ris_exporter.StockRisRequest;
import ru.instamart.api.response.ris_exporter.StockRisResponse;
import ru.instamart.kraken.data.user.UserManager;
import io.qameta.allure.TmsLink;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("Остатки")
public class StockRisTest extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.RIS_EXPORTER, UserManager.getRisUser());
    }

    @TmsLink("427")
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение остатка на складах")
    public void getStock200() {
        Response response = StockRisRequest.GET(1);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, StockRisResponse.class);
    }

    @TmsLink("435")
    @Test(  groups = {"api-ris-exporter"},
            description = "Получение остатка на складах по неверному storeId")
    public void getStock404() {
        Response response = StockRisRequest.GET(9999999);

        checkStatusCode404(response);
    }

    @TmsLink("436")
    @Test(  groups = {"api-ris-exporter"},
            description = "Проверка количества остатков на складах для всех ритейлеров",
            dataProviderClass = RestDataProvider.class,
            dataProvider = "risStoreIdsProduction")
    public void getProductsValidCount(Integer sid) {
        Response response = StockRisRequest.GET(sid);

        checkStatusCode200(response);
        assertTrue(response.as(StockRisResponse.class)
                .getTotalCount() > 5000, "Продуктов меньше минимума");
    }
}
