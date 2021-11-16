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
import ru.instamart.api.model.v2.*;
import ru.instamart.api.request.v2.ReviewableShipmentV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.ReviewableShipmentV2Response;
import ru.instamart.api.response.v2.ShipmentReviewV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
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
        apiV2.fillCart(userData, sid);
        order = apiV2.getOpenOrder();
        if (order == null) throw new SkipException("Заказ не удалось оплатить");
        shipmentNumber = order.getShipments().get(0).getNumber();
        changeToShip(shipmentNumber);
    }

    @CaseId(468)
    @Story("Получение последнего подзаказа без отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Автоматическое получение последнего шипмента без оценки при старте приложения. Заказ на аккаунте совершен.")
    public void automaticReceiptLastMessage200() {
        OrderV2 order = apiV2.setDefaultAttributesAndCompleteOrder();
        final Response response = ReviewableShipmentV2Request.GET();
        checkStatusCode200(response);
        ReviewableShipmentV2 revShipment = response.as(ReviewableShipmentV2Response.class).getReviewableShipment();
        final SoftAssert softAssert = new SoftAssert();
        final ShipmentV2 shipment = order.getShipments().get(0);
        softAssert.assertEquals(revShipment.getNumber(), shipment.getNumber(), "Number not valid");
        softAssert.assertEquals(revShipment.getCost(), shipment.getCost(), "Cost not valid");
        softAssert.assertEquals(revShipment.getItemTotal(), shipment.getItemTotal(), "Item total not valid");
        softAssert.assertEquals(revShipment.getItemDiscountTotal(), shipment.getItemDiscountTotal(), "item discount total not valid");
        softAssert.assertEquals(revShipment.getTotal(), shipment.getTotal(), "Shipments not valid");
        softAssert.assertEquals(revShipment.getAlerts(), shipment.getAlerts(), "Alerts not valid");
        softAssert.assertEquals(revShipment.getTotalWeight(), shipment.getTotalWeight(), "Total weight not valid");
        softAssert.assertEquals(revShipment.getItemCount(), shipment.getItemCount(), "Item count not valid");
//        softAssert.assertEquals(revShipment.getDeliveryWindow(), shipment.getDeliveryWindow(), "Delivery window not valid");
        softAssert.assertEquals(revShipment.getPayment(), shipment.getPayment(), "Payment not valid");
        softAssert.assertEquals(revShipment.getStore(), shipment.getStore(), "Store not valid");
        softAssert.assertAll();
    }


    @CaseId(472)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с существующим номером")
    public void shipmentsReviews() {
        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shipmentReview.getRate(), Integer.valueOf(5), "Rate not valid");
        softAssert.assertNull(shipmentReview.getComment(), "Comment not null");
        softAssert.assertAll();

    }

    @CaseId(475)
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
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shipmentReview.getRate(), Integer.valueOf(5), "Rate not valid");
        softAssert.assertEquals(shipmentReview.getComment(), "Тестовый комментарий", "Comment not null");
        softAssert.assertAll();
    }

    @CaseId(476)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с несколькими значениями для review[issue_ids]")
    public void shipmentsReviewsWithIds() {
        List<ReviewIssueV2> reviewIssues = apiV2.getReviewIssues(shipmentNumber);

        ShipmentsV2Request.Review review = ShipmentsV2Request.Review.builder()
                .rate(5)
                .issueId(reviewIssues.get(1).getId())
                .issueId(reviewIssues.get(2).getId())
                .build();
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber, review);
        checkStatusCode200(response);
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        checkFieldIsNotEmpty(shipmentReview, "отзыв о заказе");
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shipmentReview.getRate(), review.getRate(), "Rate не совпадает с введенным");
        softAssert.assertEquals(shipmentReview.getComment(), review.getComment(), "Comment не совпадает с введенным");

        softAssert.assertEquals(shipmentReview.getIssues().get(0).getId(), reviewIssues.get(1).getId(), "issues id не совпадает с введенным");
        softAssert.assertEquals(shipmentReview.getIssues().get(0).getPosition(), reviewIssues.get(1).getPosition(), "issues position не совпадает с введенным");
        softAssert.assertEquals(shipmentReview.getIssues().get(0).getDescription(), reviewIssues.get(1).getDescription(), "issues description не совпадает с введенным");
        softAssert.assertEquals(shipmentReview.getIssues().get(0).getCommentNeeded(), reviewIssues.get(1).getCommentNeeded(), "issues comment needed не совпадает с введенным");

        softAssert.assertEquals(shipmentReview.getIssues().get(1).getId(), reviewIssues.get(2).getId(), "issues id не совпадает с введенным");
        softAssert.assertEquals(shipmentReview.getIssues().get(1).getPosition(), reviewIssues.get(2).getPosition(), "issues position не совпадает с введенным");
        softAssert.assertEquals(shipmentReview.getIssues().get(1).getDescription(), reviewIssues.get(2).getDescription(), "issues description не совпадает с введенным");
        softAssert.assertEquals(shipmentReview.getIssues().get(1).getCommentNeeded(), reviewIssues.get(2).getCommentNeeded(), "issues comment needed не совпадает с введенным");

        softAssert.assertAll();
    }

    @CaseId(477)
    @Story("Создание отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание отзыва о заказе с [images_attributes]")
    public void shipmentsReviewsWithImage() {
        final Response response = ShipmentsV2Request.Reviews.POST(shipmentNumber);
        checkStatusCode200(response);
        ShipmentReviewV2 shipmentReview = response.as(ShipmentReviewV2Response.class).getShipmentReview();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(shipmentReview.getRate(), Integer.valueOf(5), "Пришла неверная оценка");
        softAssert.assertTrue(shipmentReview.getImages().get(0).getOriginalUrl().contains("sample.jpg"), "Изображение не добавлено");
        softAssert.assertAll();
    }
}
