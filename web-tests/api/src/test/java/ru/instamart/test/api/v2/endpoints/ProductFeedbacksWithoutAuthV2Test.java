package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.ProductFeedbacksV2Request;
import ru.instamart.api.response.ErrorTypeResponse;
import ru.instamart.api.response.v2.FeedbacksV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Product feedbacks")
public class ProductFeedbacksWithoutAuthV2Test extends RestBase {
    private String productSku;

    @BeforeClass(alwaysRun = true, description = "Получение SKU")
    public void before() {
        productSku = apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getSku(); //todo починить Index 0 out of bounds for length 0 на стейдже
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2304)
    @Story("Получение списка отзывов без авторизации")
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
    @CaseId(2304)
    @Story("Получение списка отзывов без авторизации")
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

    @Skip //TODO: исправить после решения ошибки
    @CaseId(2303)
    @Story("Создание отзыва на товар без авторизации")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Создание отзыва на товар")
    public void sendProductFeedbacks200() {
        final Response response = ProductFeedbacksV2Request.POST(
                ProductFeedbacksV2Request.Feedbacks.builder()
                        .sku(productSku)
                        .storeId(String.valueOf(EnvironmentProperties.DEFAULT_SID))
                        .score(4)
                        .pros("свежий")
                        .cons("Слишком зеленый")
                        .text("Очень хрупкий")
                        .build());
        checkStatusCode200(response);
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2302)
    @Story("Проверка, может ли пользователь опубликовать отзыв без авторизации")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверка, может ли пользователь опубликовать отзыв")
    public void canPostFeedback200() {
        final Response response = ProductFeedbacksV2Request.CanPostFeedback.GET(String.valueOf(EnvironmentProperties.DEFAULT_SID), productSku);
        checkStatusCode200(response);
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
