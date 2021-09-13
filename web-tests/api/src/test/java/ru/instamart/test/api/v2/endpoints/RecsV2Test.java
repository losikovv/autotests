package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.v2.PersonalV2Request;
import ru.instamart.api.response.v2.RecsV2Response;

import java.util.UUID;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode400;


@Epic("ApiV2")
@Feature("Запрос рекомендаций")
public class RecsV2Test extends RestBase {

    @CaseId(287)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Запрос рекомендаций с обязательными параметрами")
    public void testRecsTest() {
        PersonalV2Request.RecsV2 recsV2 = PersonalV2Request.RecsV2.builder()
                .reqId(UUID.randomUUID().toString())
                .tmax(5000)
                .placement(
                        PersonalV2Request.PlacementsItem.builder()
                                .placementId(UUID.randomUUID().toString())
                                .ext(PersonalV2Request.PlacementsExt.builder()
                                        .componentId(2)
                                        .order(1)
                                        .build())
                                .build()
                )
                .context(
                        PersonalV2Request.Context.builder()
                                .app(
                                        PersonalV2Request.App.builder()
                                                .domain("ru.sbermarket.new-app")
                                                .ver("1.0.0.1")
                                                .ext(PersonalV2Request.SiteAndAppExt.builder()
                                                        .categoryId(1)
                                                        .storeId("1")
                                                        .build())
                                                .build()
                                )
                                .user(PersonalV2Request.User.builder()
                                        .id(UUID.randomUUID().toString())
                                        .build())
                                .build()
                )
                .build();

        final Response response = PersonalV2Request.POST(recsV2);
        checkStatusCode200(response);
        final RecsV2Response recsV2Response = response.as(RecsV2Response.class);
        assertNotNull(recsV2Response.getRecs(), "Рекомендации вернулись пустые");

    }

    @CaseId(288)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Запрос рекомендаций с отсутствующим обязательным параметром",
            dataProvider = "testNegativeRecsTest",
            dataProviderClass = RestDataProvider.class
    )
    @Parameters({"RequestJson", "Description"})
    public void testNegativeRecsTest(PersonalV2Request.RecsV2 simpleRecsV2, String desc) {
        final Response response = PersonalV2Request.POST(simpleRecsV2);
        checkStatusCode400(response);
    }
}
