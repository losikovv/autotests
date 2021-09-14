package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.AdsImagesV2Request;
import ru.instamart.api.request.v2.SimpleAdsV2Request;

import java.util.List;
import java.util.UUID;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

@Epic("ApiV2")
@Feature("Упрощенный запрос нативной рекламы.")
public class SimpleAdsV2Test extends RestBase {

    @BeforeMethod
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @CaseId(282)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
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
                                        .storeId(1)
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

    @CaseId(284)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "negativeSimpleAdsData",
            dataProviderClass = RestDataProvider.class,
            description = "Упрощенный запрос нативной рекламы с негативными параметрами")
    @Parameters({"RequestJson", "Description"})
    public void simpleAdsNegativeParameterTest(SimpleAdsV2Request.SimpleAdsV2 simpleAdsV2, String desc) {
        final Response response = SimpleAdsV2Request.POST(simpleAdsV2);
        checkStatusCode400(response);
    }

    @CaseId(285)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
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
                        .nativeAdTypeId("3")
                        .build()
                )
                .build();
        String imagePath = apiV2.getSimpleAdsFirstImage(allRequiredParameters);

        final Response responseImage = AdsImagesV2Request.GET(imagePath);
        responseImage.then()
                .statusCode(200)
                .contentType("image/");
    }


    @CaseId(286)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Запрос проверки не существующего изображения")
    public void simpleAdsGetNotExistingImageTest() {
        String imagePath = "imageNotFound";

        final Response responseImage = AdsImagesV2Request.GET(imagePath);
        checkStatusCode404(responseImage);
    }
}
