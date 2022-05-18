package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.model.v3.CheckoutOrderV3;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.model.v3.PaymentToolV3;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v3.ErrorV3Response;
import ru.instamart.api.response.v3.ErrorsV3Response;
import ru.instamart.api.response.v3.OrderV3Response;
import ru.instamart.kraken.data.user.UserData;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

public class ApiV3Checkpoints {

    @Step("Проверяем содержание ошибки")
    public static void checkError(Response response, String errorType, String errorTitle) {
        ErrorV3Response error = response.as(ErrorV3Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(error.getType(), errorType, softAssert);
        compareTwoObjects(error.getTitle(), errorTitle, softAssert);
        softAssert.assertAll();
    }

    @Step("Сравниваем заказ с полученным в ответе")
    public static void checkOrder(Response response, MultiretailerOrderV1Response order, UserData user, String shippingMethod, boolean isAlcohol) {
        final SoftAssert softAssert = new SoftAssert();
        CheckoutOrderV3 orderFromResponse = response.as(OrderV3Response.class).getOrder();
        compareTwoObjects(orderFromResponse.getNumber(), order.getNumber(), softAssert);
        compareTwoObjects(orderFromResponse.getTotal(), order.getTotal(), softAssert);
        compareTwoObjects(orderFromResponse.getShippingMethodKind(), shippingMethod, softAssert);
        compareTwoObjects(orderFromResponse.getIsAlcohol(), isAlcohol, softAssert);
        compareTwoObjects(orderFromResponse.getContacts().getEmail(), user.getEmail(), softAssert);
        compareTwoObjects(orderFromResponse.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber(), softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем ошибки")
    public static void checkErrors(Response response, String errorType, String errorField, String errorMessage) {
        List<ErrorV3> errors = response.as(ErrorsV3Response.class).getErrors();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(errors.get(0).getType(), errorType, softAssert);
        compareTwoObjects(errors.get(0).getField(), errorField, softAssert);
        compareTwoObjects(errors.get(0).getMessage(), errorMessage, softAssert);
        softAssert.assertAll();
    }

    @Step("Сравниваем способ оплаты в заказе с полученным в ответе")
    public static void checkOrderPaymentTools(CheckoutOrderV3 orderFromResponse, PaymentToolV3 paymentTool) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(orderFromResponse.getPaymentTool().getId(), paymentTool.getId(), softAssert);
        compareTwoObjects(orderFromResponse.getPaymentTool().getName(), paymentTool.getName(), softAssert);
        compareTwoObjects(orderFromResponse.getPaymentTool().getType(), paymentTool.getType(), softAssert);
        compareTwoObjects(orderFromResponse.getPaymentTool().getPaymentMethod().getId(), paymentTool.getPaymentMethod().getId(), softAssert);
        compareTwoObjects(orderFromResponse.getPaymentTool().getPaymentMethod().getName(), paymentTool.getPaymentMethod().getName(), softAssert);
        compareTwoObjects(orderFromResponse.getPaymentTool().getPaymentMethod().getDescription(), paymentTool.getPaymentMethod().getDescription(), softAssert);
        softAssert.assertAll();
    }
}
