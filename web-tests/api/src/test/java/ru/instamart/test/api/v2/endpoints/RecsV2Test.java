package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.v2.PersonalV2Request;
import ru.instamart.api.request.v2.SimpleRecsPersonalV2Request;
import ru.instamart.api.response.v2.RecsV2Response;

import java.util.Arrays;
import java.util.UUID;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode400;


@Epic("ApiV2")
@Feature("Запрос рекомендаций")
public class RecsV2Test extends RestBase {

    @CaseId(287)
    @Test(groups = {"api-instamart-regress"}, description = "")
    public void testRecsTest() {
        PersonalV2Request.RecsV2 recsV2 = PersonalV2Request.RecsV2.builder()
                .reqId(UUID.randomUUID().toString())
                .placements(
                        Arrays.asList(PersonalV2Request.PlacementsItem.builder()
                                .placementId(UUID.randomUUID().toString())
                                .ext(PersonalV2Request.PlacementsExt.builder()
                                        .componentId(2)
                                        .order(1)
                                        .build())
                                .build())
                )
                .context(
                        PersonalV2Request.Context.builder()
                                .site(PersonalV2Request.Site.builder()
                                        .domain("ru.sbermarket.new-app")
                                        .page("test")
                                        .ext(PersonalV2Request.SiteAndAppExt.builder()
                                                .categoryId(1)
                                                .storeId("1")
                                                .tenantId(1)
                                                .build())
                                        .build()
                                )
                                .app(
                                        PersonalV2Request.App.builder()
                                                .ext(PersonalV2Request.SiteAndAppExt.builder()
                                                        .categoryId(1)
                                                        .storeId("1")
                                                        .tenantId(1)
                                                        .build())
                                                .build()
                                )
                                .build()
                )
                .build();

        final Response response = PersonalV2Request.POST(recsV2);
        checkStatusCode200(response);
        response.as(RecsV2Response.class);
    }

    @CaseId(288)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "",
            dataProvider = "testNegativeRecsTest",
            dataProviderClass = RestDataProvider.class
    )
    public void testNegativeRecsTest(SimpleRecsPersonalV2Request.SimpleRecsV2 simpleRecsV2) {
        final Response response = SimpleRecsPersonalV2Request.POST(simpleRecsV2);
        checkStatusCode400(response);
    }
}
