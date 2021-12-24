package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.AdsImagesV2Request;
import ru.instamart.api.request.v2.SimpleAdsV2Request;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;

import java.util.List;
import java.util.UUID;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Нативная реклама")
public final class SimpleAdsV2Test extends RestBase {

    @BeforeMethod
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @CaseId(282)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Упрощенный запрос нативной рекламы с обязательными параметрами")
    public void simpleAdsTest() {
        SimpleAdsV2Request.SimpleAdsV2 allRequiredParameters = SimpleAdsV2Request.SimpleAdsV2.builder()
                .context(SimpleAdsV2Request.Context.builder()
                        .user(SimpleAdsV2Request.User.builder()
                                .geo(SimpleAdsV2Request.Geo.builder()
                                        .lat(55.790447999999998D)
                                        .lon(37.680517000000002D)
                                        .build()
                                )
                                .ext(SimpleAdsV2Request.UserExt.builder()
                                        .anonymousId(UUID.randomUUID().toString())
                                        .build()
                                )
                                .build()
                        )
                        .app(SimpleAdsV2Request.App.builder()
                                .ext(SimpleAdsV2Request.AppExt.builder()
                                        .storeId(EnvironmentProperties.DEFAULT_SID)
                                        .tenantId(0)
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .ext(SimpleAdsV2Request.Ext.builder()
                        .nativeAdTypeId("2")
                        .build()
                )
                .build();

        final Response response = SimpleAdsV2Request.POST(allRequiredParameters);
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Упрощенный запрос нативной рекламы с всеми доступными параметрами")
    public void simpleAdsAllParameterTest() {
        SimpleAdsV2Request.SimpleAdsV2 allRequiredParameters = SimpleAdsV2Request.SimpleAdsV2.builder()
                .context(SimpleAdsV2Request.Context.builder()
                        .user(SimpleAdsV2Request.User.builder()
                                .geo(SimpleAdsV2Request.Geo.builder()
                                        .lat(55.790447999999998D)
                                        .lon(37.680517000000002D)
                                        .build()
                                )
                                .ext(SimpleAdsV2Request.UserExt.builder()
                                        .anonymousId(UUID.randomUUID().toString())
                                        .build()
                                )
                                .build()
                        )
                        .app(SimpleAdsV2Request.App.builder()
                                .ext(SimpleAdsV2Request.AppExt.builder()
                                        .storeId(1)
                                        .tenantId(0)
                                        .categoryId(1)
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .ext(SimpleAdsV2Request.Ext.builder()
                        .nativeAdTypeId("2")
                        .build()
                )
                .build();

        Headers headers = new Headers(List.of(
                new Header("App-Version", "1.1.1"),
                new Header("App-Platform", "android"),
                new Header("App-OS-Version", "7.1.1")
        ));


        final Response response = SimpleAdsV2Request.POST(allRequiredParameters, headers);
        checkStatusCode200(response);
    }

    @CaseIDs(value = {@CaseId(284), @CaseId(1087), @CaseId(1088), @CaseId(1089)})
    @JsonDataProvider(path = "data/json_v2/api_v2_blank_simple_ads_data.json", type = RestDataProvider.SimpleAdsV2TestDataRoot.class)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Упрощенный запрос нативной рекламы с отсуствующими параметрами")
    public void simpleAdsBlankParametersTest(RestDataProvider.SimpleAdsV2TestData testData) {
        Allure.step(testData.getDescription());
        final Response response = SimpleAdsV2Request.POST(testData.getSimpleAds());
        checkStatusCode400(response);
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");
    }

    @CaseIDs(value = {@CaseId(283), @CaseId(1090)})
    @JsonDataProvider(path = "data/json_v2/api_v2_invalid_simple_ads_data.json", type = RestDataProvider.SimpleAdsV2TestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Упрощенный запрос нативной рекламы с невалидными параметрами")
    public void simpleAdsInvalidParametersTest(RestDataProvider.SimpleAdsV2TestData testData) {
        Allure.step(testData.getDescription());
        final Response response = SimpleAdsV2Request.POST(testData.getSimpleAds());
        checkStatusCode(response, testData.getStatusCode());
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");
    }

    @CaseId(285)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Запрос проверки существующего изображения")
    public void simpleAdsGetExistingImageTest() {
        SimpleAdsV2Request.SimpleAdsV2 allRequiredParameters = SimpleAdsV2Request.SimpleAdsV2.builder()
                .context(SimpleAdsV2Request.Context.builder()
                        .user(SimpleAdsV2Request.User.builder()
                                .geo(SimpleAdsV2Request.Geo.builder()
                                        .lat(55.849691D)
                                        .lon(37.620905)
                                        .build()
                                )
                                .ext(SimpleAdsV2Request.UserExt.builder()
                                        .anonymousId(UUID.randomUUID().toString())
                                        .build()
                                )
                                .build()
                        )
                        .app(SimpleAdsV2Request.App.builder()
                                .ext(SimpleAdsV2Request.AppExt.builder()
                                        .storeId(EnvironmentProperties.DEFAULT_SID)
                                        .tenantId(0)
                                        .categoryId(1)
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .ext(SimpleAdsV2Request.Ext.builder()
                        .nativeAdTypeId("3")
                        .build()
                )
                .build();
        String imagePath = apiV2.getSimpleAdsFirstImage(allRequiredParameters);

        final Response responseImage = AdsImagesV2Request.GET(imagePath);
        checkContentTypeImage(responseImage);
    }


    @CaseId(286)
    @Test(groups = {"api-instamart-regress"},
            description = "Запрос проверки не существующего изображения")
    public void simpleAdsGetNotExistingImageTest() {
        String imagePath = "imageNotFound";

        final Response responseImage = AdsImagesV2Request.GET(imagePath);
        checkStatusCode404(responseImage);
    }
}
