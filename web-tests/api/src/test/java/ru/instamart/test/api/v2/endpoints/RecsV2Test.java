package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.v2.PersonalV2Request;
import ru.instamart.api.response.v2.RecsV2Response;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode400;


@Epic("ApiV2")
@Feature("Рекомендации")
public final class RecsV2Test extends RestBase {

    @Skip // TODO: нужно включить после доработки получения реков по номеру закоза
    @Story("Полные рекомендации (recs)")
    @CaseId(974)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Запрос рекомендаций с обязательными параметрами",
            dataProvider = "recsData",
            dataProviderClass = RestDataProvider.class)
    public void testRecsTest(PersonalV2Request.RecsV2 recsV2) {
        final Response response = PersonalV2Request.POST(recsV2);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RecsV2Response.class);
    }

    @Story("Полные рекомендации (recs)")
    @CaseId(975)
    @JsonDataProvider(path = "data/json_v2/api_v2_negative_recs_data.json", type = RestDataProvider.RecsV2TestDataRoot.class)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Запрос рекомендаций с отсутствующим обязательным параметром",
            dataProvider = "json",
            dataProviderClass = JsonProvider.class)
    public void testNegativeRecsTest(RestDataProvider.RecsV2TestData testData) {
        Allure.step(testData.getDescription());
        PersonalV2Request.RecsV2 recsV2 = testData.getRec();
        recsV2.setReqId(UUID.randomUUID().toString());
        final Response response = PersonalV2Request.POST(recsV2);
        checkStatusCode400(response);
    }
}
