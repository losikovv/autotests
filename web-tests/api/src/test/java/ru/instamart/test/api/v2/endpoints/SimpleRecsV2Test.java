package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.*;
import ru.sbermarket.qase.annotation.CaseIDs;

import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.v2.SimpleRecsV2Request;
import ru.instamart.api.response.v2.SimpleRecsV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;

import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Рекомендации")
public final class SimpleRecsV2Test extends RestBase {


    private SimpleRecsV2Request.SimpleRecsV2 allRequiredParameters;

    @BeforeMethod(alwaysRun = true)
    public void prepareData(){
        allRequiredParameters = SimpleRecsV2Request.SimpleRecsV2.builder()
                .context(SimpleRecsV2Request.Context.builder()
                        .app(SimpleRecsV2Request.App.builder()
                                .ext(SimpleRecsV2Request.AppExt.builder()
                                        .storeId(EnvironmentProperties.DEFAULT_SID)
                                        .tenantId(0)
                                        .build())
                                .domain("ru.sbermarket.new-app")
                                .build())
                        .user(SimpleRecsV2Request.User.builder()
                                .geo(SimpleRecsV2Request.Geo.builder()
                                        .lat(55.790447999999998D)
                                        .lon(37.680517000000002D)
                                        .build())
                                .ext(SimpleRecsV2Request.UserExt.builder()
                                        .anonymousId(UUID.randomUUID().toString())
                                        .build())
                                .build())
                        .build())
                .ext(
                        SimpleRecsV2Request.Ext.builder()
                                .place("main").build()
                )
                .build();
    }

    @CaseIDs(value = {@CaseId(287), @CaseId(1094), @CaseId(1095), @CaseId(1096), @CaseId(1097), @CaseId(1098)})
    @Story("Упрощенные рекомендации (simple-recs)")
    @Test(groups = {"api-instamart-regress"},
            description = "Упрощенный запрос блока рекомендаций с обязательными параметрами",
            dataProvider = "simpleRecsData",
            dataProviderClass = RestDataProvider.class)
    public void testSimpleRecsTest(String place, String orderNumber, String sku) {
        allRequiredParameters.getExt().setPlace(place);
        allRequiredParameters.getContext().getApp().getExt().setSku(sku);
        allRequiredParameters.getContext().getApp().getExt().setOrderNumber(orderNumber);
        final Response response = SimpleRecsV2Request.Personal.POST(allRequiredParameters);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SimpleRecsV2Response.class);
    }

    @CaseIDs(value = {@CaseId(289), @CaseId(1099), @CaseId(1100), @CaseId(1101)})
    @Story("Упрощенные рекомендации (simple-recs)")
    @JsonDataProvider(path = "data/json_v2/api_v2_blank_simple_recs_data.json", type = RestDataProvider.SimpleRecsV2TestDataRoot.class)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Упрощенный запрос блока рекомендаций с отсутствующим обязательным параметром",
            dataProvider = "json",
            dataProviderClass = JsonProvider.class
    )
    public void getBlankSimpleRecsTest(RestDataProvider.SimpleRecsV2TestData testData) {
        Allure.step(testData.getDescription());
        SimpleRecsV2Request.SimpleRecsV2 simpleRecsV2 = testData.getSimpleRec();
        final Response response = SimpleRecsV2Request.Personal.POST(simpleRecsV2);
        checkStatusCode400(response);
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");
    }

    @CaseIDs(value = {@CaseId(288), @CaseId(1102)})
    @Story("Упрощенные рекомендации (simple-recs)")
    @JsonDataProvider(path = "data/json_v2/api_v2_invalid_simple_recs_data.json", type = RestDataProvider.SimpleRecsV2TestDataRoot.class)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Упрощенный запрос блока рекомендаций с невалидным обязательным параметром",
            dataProvider = "json",
            dataProviderClass = JsonProvider.class
    )
    public void getInvalidSimpleRecsTest(RestDataProvider.SimpleRecsV2TestData testData) {
        Allure.step(testData.getDescription());
        SimpleRecsV2Request.SimpleRecsV2 simpleRecsV2 = testData.getSimpleRec();
        final Response response = SimpleRecsV2Request.Personal.POST(simpleRecsV2);
        checkStatusCode(response, testData.getStatusCode());
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");

    }

    @CaseId(844)
    @Story("Упрощенные рекомендации (simple-recs)")
    @Test(groups = {"api-instamart-regress"},
            description = "Упрощенный запрос блока рекомендаций coпутствующих товаров")
    public void getComplementarySimpleRecsTest() {
        final Response response = SimpleRecsV2Request.Complementary.POST(allRequiredParameters);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SimpleRecsV2Response.class);
    }

    @CaseId(845)
    @Story("Упрощенные рекомендации (simple-recs)")
    @Test(groups = {"api-instamart-regress"},
            description = "Упрощенный запрос блока рекомендаций товаров-заменителей")
    public void getSubstituteSimpleRecsTest() {
        final Response response = SimpleRecsV2Request.Substitute.POST(allRequiredParameters);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SimpleRecsV2Response.class);
    }
}
