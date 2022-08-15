package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ProductFeedbacksV2Request;
import ru.instamart.api.response.ErrorTypeResponse;
import ru.instamart.api.response.v2.CanPostFeedbackV2Response;
import ru.instamart.api.response.v2.FeedbacksV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode400;

@Epic("ApiV2")
@Feature("Product feedbacks")
public class ProductFeedbacksV2Test extends RestBase {
    private String productSku;

    @BeforeClass(alwaysRun = true,
            description = "Авторизация и получение SKU")
    public void before() {
        SessionFactory.makeSession(SessionType.API_V2);
        productSku = apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getSku(); //todo починить Index 0 out of bounds for length 0 на стейдже
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2297)
    @Story("Получение списка отзывов")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получение списка отзывов без обязательных параметров")
    public void getProductFeedbacks400() {
        final Response response = ProductFeedbacksV2Request.GET();
        checkStatusCode400(response);
        ErrorTypeResponse errorTypeResponse = response.as(ErrorTypeResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(errorTypeResponse.getType(), "validation-error", "type invalid");
        softAssert.assertEquals(errorTypeResponse.getTitle(), "Ошибка проверки входящих параметров", "title invalid");
        softAssert.assertEquals(errorTypeResponse.getDetail(), "Один или более переданных параметров не является допустимым", "detail invalid");
        softAssert.assertEquals(errorTypeResponse.getStatus(), 400, "status invalid");
        softAssert.assertEquals(errorTypeResponse.getInvalidParams().get(0).getName(), "store_id", "name invalid");
        softAssert.assertEquals(errorTypeResponse.getInvalidParams().get(0).getReason(), "не может быть пустым", "reason invalid");
        softAssert.assertAll();
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2307)
    @Story("Получение списка отзывов")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получение списка отзывов без обязательных параметров")
    public void getProductFeedbacks200() {
        final Response response = ProductFeedbacksV2Request.GET(
                String.valueOf(EnvironmentProperties.DEFAULT_SID),
                productSku
        );
        checkStatusCode200(response);
        FeedbacksV2Response feedbacksV2Response = response.as(FeedbacksV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(feedbacksV2Response.getFeedbacks().size(), 0, softAssert);

    }

    @Skip //TODO: возвращает 500
    @CaseIDs({@CaseId(2298), @CaseId(2299)})
    @Story("Создание отзыва на товар")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            dataProvider = "sendProductFeedbacks",
            dataProviderClass = RestDataProvider.class,
            description = "Создание отзыва на товар")
    public void sendProductFeedbacks200(final ProductFeedbacksV2Request.Feedbacks feedbacks) {
        final Response response = ProductFeedbacksV2Request.POST(feedbacks);
        checkStatusCode200(response);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2301)
    @Story("Проверка, может ли пользователь опубликовать отзыв")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверка, может ли пользователь опубликовать отзыв")
    public void canPostFeedback200() {
        final Response response = ProductFeedbacksV2Request.CanPostFeedback.GET(String.valueOf(EnvironmentProperties.DEFAULT_SID), productSku);
        checkStatusCode200(response);
        CanPostFeedbackV2Response canPostFeedbackV2Response = response.as(CanPostFeedbackV2Response.class);
        assertTrue(canPostFeedbackV2Response.isCanPostFeedback(), "Нельзя оставить отзыв");
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2308)
    @Story("Получить актуальный отзыв на товар")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получить актуальный отзыв на товар")
    public void getActualFeedback400() {
        final Response response = ProductFeedbacksV2Request.ActualFeedback.GET();
        checkStatusCode400(response);
        ErrorTypeResponse errorTypeResponse = response.as(ErrorTypeResponse.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(errorTypeResponse.getType(), "validation-error", "type invalid");
        softAssert.assertEquals(errorTypeResponse.getTitle(), "Ошибка проверки входящих параметров", "title invalid");
        softAssert.assertEquals(errorTypeResponse.getDetail(), "Один или более переданных параметров не является допустимым", "detail invalid");
        softAssert.assertEquals(errorTypeResponse.getStatus(), 400, "status invalid");
        softAssert.assertEquals(errorTypeResponse.getInvalidParams().get(0).getName(), "store_id", "name invalid");
        softAssert.assertEquals(errorTypeResponse.getInvalidParams().get(0).getReason(), "не может быть пустым", "reason invalid");
        softAssert.assertAll();
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2309)
    @Story("Получить актуальный отзыв на товар")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получить актуальный отзыв на товар")
    public void getActualFeedback200() {
        final Response response = ProductFeedbacksV2Request.ActualFeedback.GET(String.valueOf(EnvironmentProperties.DEFAULT_SID), productSku);
        checkStatusCode200(response);
        FeedbacksV2Response canPostFeedbackV2Response = response.as(FeedbacksV2Response.class);
        assertNull(canPostFeedbackV2Response.getFeedbacks(), "Отзывы пришел не пустым");
    }
}
