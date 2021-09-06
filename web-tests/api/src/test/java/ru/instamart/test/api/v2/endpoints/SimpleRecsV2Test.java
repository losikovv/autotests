package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.v2.SimpleRecsPersonalV2Request;
import ru.instamart.api.response.v2.SimpleRecsV2Response;

import java.util.UUID;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode400;

@Epic("ApiV2")
@Feature("Упрощенный запрос блока рекомендаций.")
public class SimpleRecsV2Test extends RestBase {

    @Issue("STF-8819")
    @CaseId(287)
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Упрощенный запрос блока рекомендаций с обязательными параметрами")
    public void testSimpleRecsTest() {
        SimpleRecsPersonalV2Request.SimpleRecsV2 allRequiredParameters = SimpleRecsPersonalV2Request.SimpleRecsV2.builder()
                .context(SimpleRecsPersonalV2Request.Context.builder()
                        .app(SimpleRecsPersonalV2Request.App.builder()
                                .ext(SimpleRecsPersonalV2Request.AppExt.builder()
                                        .storeId(1)
                                        .tenantId(0)
                                        .build())
                                .domain("ru.sbermarket.new-app")
                                .build())
                        .user(SimpleRecsPersonalV2Request.User.builder()
                                .geo(SimpleRecsPersonalV2Request.Geo.builder()
                                        .lat(55.790447999999998D)
                                        .lon(37.680517000000002D)
                                        .build())
                                .ext(SimpleRecsPersonalV2Request.UserExt.builder()
                                        .anonymousId(UUID.randomUUID().toString())
                                        .build())
                                .build())
                        .build())
                .ext(
                        SimpleRecsPersonalV2Request.Ext.builder()
                                .place("main").build()
                )
                .build();
        final Response response = SimpleRecsPersonalV2Request.POST(allRequiredParameters);
        checkStatusCode200(response);
        response.as(SimpleRecsV2Response.class);
    }

    @CaseId(288)
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Упрощенный запрос блока рекомендаций с отсутствующим обязательным параметром",
            dataProvider = "testNegativeSimpleRecsTest",
            dataProviderClass = RestDataProvider.class
    )
    @Parameters({"RequestJson", "Description"})
    public void testNegativeSimpleRecsTest(SimpleRecsPersonalV2Request.SimpleRecsV2 simpleRecsV2, String desc) {
        final Response response = SimpleRecsPersonalV2Request.POST(simpleRecsV2);
        checkStatusCode400(response);
    }

}
