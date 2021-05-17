package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.TaxonV2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.Objects.isNull;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@Slf4j
public class InstamartApiCheckpoints {

    @Step("Ответ вернул 200")
    public static void checkStatusCode200(Response response) {
        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Step("Ответ вернул 400")
    public static void checkStatusCode400(final Response response) {
        response.then().statusCode(400);
    }

    @Step("Ответ вернул 401")
    public static void checkStatusCode401(final Response response) {
        response.then().statusCode(401);
    }

    @Step("Ответ вернул 403")
    public static void checkStatusCode403(final Response response) {
        response.then().statusCode(403);
    }

    @Step("Ответ вернул 404")
    public static void checkStatusCode404(final Response response) {
        response.then().statusCode(404);
    }

    @Step("Ответ вернул 200 или 404")
    public static void checkStatusCode200or404(final Response response) {
        response.then().statusCode(anyOf(is(200), is(404)));
    }

    @Step("Ответ вернул 422")
    public static void checkStatusCode422(final Response response) {
        response.then().statusCode(422);
    }

    /**
     * Проверяем, что дата доставки заказа сегодня
     */
    @Step("Проверяем, что дата доставки заказа сегодня")
    public static String checkIsDeliveryToday(OrderV2 order) {
        LocalDateTime deliveryTime = LocalDateTime
                .parse(order
                        .getShipments()
                        .get(0)
                        .getDeliveryWindow()
                        .getStartsAt()
                        .substring(0, 19))
                .plusHours(2); // у gitlab время по гринвичу, а в тестовом магазине +2

        LocalDateTime nextDay = LocalDateTime
                .now()
                .truncatedTo(ChronoUnit.DAYS)
                .plusDays(1);

        if (deliveryTime.isBefore(nextDay)) {
            return "Заказ оформлен не на сегодня\nnow: " + LocalDateTime.now()
                    + "\ndelivery time: " + deliveryTime;
        } else return "";
    }

    /**
     * Софт-ассерт, что количество товаров в категории равно сумме товаров в подкатегориях
     */
    @Step("Проверяем, что количество товаров в категории равно сумме товаров в подкатегориях")
    public static void checkProductsCountEqualsChildrenSum(TaxonV2 taxon, SoftAssert softAssert) {
        List<TaxonV2> children = taxon.getChildren();
        if (isNull(children)) return;
        int sum = 0;
        for (TaxonV2 child : children) {
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
