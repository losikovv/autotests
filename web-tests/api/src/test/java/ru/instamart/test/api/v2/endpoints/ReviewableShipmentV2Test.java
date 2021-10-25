package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ReviewIssueV2;
import ru.instamart.api.request.v2.ReviewableShipmentV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.ReviewableShipmentV2Response;
import ru.instamart.api.response.v2.ShipmentReviewV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.ShopperApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.k8s.K8sConsumer.changeToShip;

@Epic("ApiV2")
@Feature("Отзывы о заказе")
@Slf4j
public class ReviewableShipmentV2Test extends RestBase {
    private String shipmentNumber;
    private UserData userData;
    private OrderV2 order;

    @BeforeMethod(alwaysRun = true)
    public void before() {
        var sid = EnvironmentProperties.DEFAULT_SID;
        SessionFactory.makeSession(SessionType.API_V2_PHONE);
        userData = SessionFactory.getSession(SessionType.API_V2_PHONE).getUserData();
        order = apiV2.order(userData, sid);
        if (order == null) throw new SkipException("Заказ не удалось оплатить");
        shipmentNumber = order.getShipments().get(0).getNumber();
        changeToShip(shipmentNumber);
    }

    @CaseId(468)
    @Issue("STF-8102")
    @Story("Получение последнего подзаказа без отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Автоматическое получение последнего шипмента без оценки при старте приложения. Заказ на аккаунте совершен.")
    public void automaticReceiptLastMessage200() {
        SessionFactory.createSessionToken(SessionType.API_V2_FB, userData);
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode200(response);
        ReviewableShipmentV2Response revShipment = response.as(ReviewableShipmentV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(revShipment.getReviewableShipment().getNumber(), order.getShipments().get(0).getNumber(), "Number not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getCost(), order.getShipments().get(0).getCost(), "Cost not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getItemTotal(), order.getShipments().get(0).getItemTotal(), "Item total not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getItemDiscountTotal(), order.getShipments().get(0).getItemDiscountTotal(), "item discount total not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getTotal(), order.getShipments().get(0).getTotal(), "Shipments not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getAlerts(), order.getShipments().get(0).getAlerts(), "Alerts not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getTotalWeight(), order.getShipments().get(0).getTotalWeight(), "Total weight not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getItemCount(), order.getShipments().get(0).getItemCount(), "Item count not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getDeliveryWindow(), order.getShipments().get(0).getDeliveryWindow(), "Delivery window not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getPayment(), order.getShipments().get(0).getPayment(), "Payment not valid");
        softAssert.assertEquals(revShipment.getReviewableShipment().getStore(), order.getShipments().get(0).getStore(), "Store not valid");
        softAssert.assertAll();
    }


    @CaseId(472)
    @Issue("STF-8102")
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с существующим номером")
    public void shipmentsReviews() {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        ShipmentReviewV2Response shipmentReviewV2Response = response.as(ShipmentReviewV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shipmentReviewV2Response.getShipmentReview().getRate(), Integer.valueOf(5), "Rate not valid");
        softAssert.assertNull(shipmentReviewV2Response.getShipmentReview().getComment(), "Comment not null");
        softAssert.assertAll();

    }

    @CaseId(475)
    @Issue("STF-8102")
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с существующим номером")
    public void shipmentsReviewsWithComments() {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .comment("Тестовый комментарий")
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        ShipmentReviewV2Response shipmentReviewV2Response = response.as(ShipmentReviewV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shipmentReviewV2Response.getShipmentReview().getRate(), Integer.valueOf(5), "Rate not valid");
        softAssert.assertEquals(shipmentReviewV2Response.getShipmentReview().getComment(), "Тестовый комментарий", "Comment not null");
        softAssert.assertAll();
    }

    @CaseId(476)
    @Issue("STF-8102")
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с несколькими значениями для review[issue_ids]")
    public void shipmentsReviewsWithIds() {
        List<ReviewIssueV2> reviewIssues = apiV2.getReviewIssues(shipmentNumber);

        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .issueIds(reviewIssues.get(1).getId())
//                .issueId(reviewIssues.get(2).getId()) //TODO: ATST-782
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        ShipmentReviewV2Response shipmentReviewV2Response = response.as(ShipmentReviewV2Response.class);
        checkFieldIsNotEmpty(shipmentReviewV2Response.getShipmentReview(), "отзыв о заказе");
    }
}
