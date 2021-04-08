package ru.instamart.api.checkpoints;

import ru.instamart.api.objects.v2.Order;
import ru.instamart.api.objects.v2.Taxon;
import ru.instamart.api.responses.ErrorResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class InstamartApiCheckpoints {

    public static void checkStatusCode200(Response response) {
        checkStatusCode200(response, "");
    }

    @Step("Ответ вернул 200")
    public static void checkStatusCode200(Response response, String message) {
        response.then().assertThat().contentType(ContentType.JSON);
        String responseText = response.statusLine();
        switch (response.statusCode()) {
            case 200:
                break;
            case 400:
            case 401:
            case 403:
            case 404:
            case 422:
                ErrorResponse errorResponse = response.as(ErrorResponse.class);
                if (errorResponse.getErrors() != null) responseText = errorResponse.getErrors().getBase();
                else if (errorResponse.getError() != null) responseText = errorResponse.getError();
                else if (errorResponse.getMessage() != null) responseText =  errorResponse.getMessage();
                break;
            case 502:
                responseText = "СЕРВЕР ЛЕЖИТ";
                break;
        }
        Assert.assertEquals(response.statusCode(),200, message + "\n" + responseText);
    }

    @Step("Ответ вернул 400")
    public static void checkStatusCode400(final Response response) {
        Assert.assertEquals(response.statusCode(), 400);
    }

    @Step("Ответ вернул 401")
    public static void checkStatusCode401(final Response response) {
        Assert.assertEquals(response.statusCode(), 401);
    }

    @Step("Ответ вернул 403")
    public static void checkStatusCode403(final Response response) {
        Assert.assertEquals(response.statusCode(), 403);
    }

    @Step("Ответ вернул 404")
    public static void checkStatusCode404(final Response response) {
        Assert.assertEquals(response.statusCode(), 404);
    }

    @Step("Ответ вернул 422")
    public static void checkStatusCode422(final Response response) {
        Assert.assertEquals(response.statusCode(), 422);
    }

    /**
     * Ассерт, что дата доставки заказа сегодня
     */
    @Step("Проверяем, что дата доставки заказа сегодня")
    public static void checkIsDeliveryToday(Order order) {
        LocalDateTime deliveryTime = LocalDateTime
                .parse(order
                        .getShipments()
                        .get(0)
                        .getDeliveryWindow()
                        .getStartsAt()
                        .substring(0, 19))
                .plus(2, ChronoUnit.HOURS); // у gitlab время по гринвичу, а в тестовом магазине +2

        LocalDateTime nextDay = LocalDateTime
                .now()
                .truncatedTo(ChronoUnit.DAYS)
                .plus(1, ChronoUnit.DAYS);

        Assert.assertTrue(deliveryTime.isBefore(nextDay),
                "Заказ оформлен не на сегодня\nnow: " + LocalDateTime.now()
                        + "\ndelivery time: " + deliveryTime);
    }

    /**
     * Софт-ассерт, что количество товаров в категории равно сумме товаров в подкатегориях
     */
    @Step("Проверяем, что количество товаров в категории равно сумме товаров в подкатегориях")
    public static void checkProductsCountEqualsChildrenSum(Taxon taxon, SoftAssert softAssert) {
        List<Taxon> children = taxon.getChildren();
        if (children == null) return;
        int sum = 0;
        for (Taxon child : children) {
            sum += child.getProductsCount();
            checkProductsCountEqualsChildrenSum(child, softAssert);
        }
        softAssert.assertEquals(sum, taxon.getProductsCount(),
                "\n" + taxon.getName() +
                        ", id: " + taxon.getId() +
                        ", children_sum: " + sum +
                        ", product_count: " + taxon.getProductsCount());
    }
}
