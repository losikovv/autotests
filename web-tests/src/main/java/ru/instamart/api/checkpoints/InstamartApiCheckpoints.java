package instamart.api.checkpoints;

import instamart.api.objects.v2.Order;
import instamart.api.objects.v2.Taxon;
import instamart.api.responses.v2.ErrorResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

public class InstamartApiCheckpoints {

    @Step("Ответ вернул 200")
    public static void checkStatusCode200(Response response) {
        checkStatusCode200(response, "");
    }

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
                break;
            case 502:
                responseText = "СЕРВЕР ЛЕЖИТ";
                break;
        }
        Assert.assertEquals(response.statusCode(),200, message + "\n" + responseText);
    }

    @Step("Ответ вернул 401")
    public static void checkStatusCode401(final Response response) {
        Assert.assertEquals(response.statusCode(), 401);
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
        String today = String.valueOf(LocalDate.now());
        String deliveryTime = order.getShipments().get(0).getDelivery_window().getStarts_at();
        if (!deliveryTime.contains(today)) org.junit.Assert.fail("Заказ оформлен не на сегодня\ntoday: " +
                today + "\ndelivery time: " + deliveryTime);
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
            sum += child.getProducts_count();
            checkProductsCountEqualsChildrenSum(child, softAssert);
        }
        softAssert.assertEquals(sum, taxon.getProducts_count(),
                "\n" + taxon.getName() +
                        ", id: " + taxon.getId() +
                        ", children_sum: " + sum +
                        ", product_count: " + taxon.getProducts_count());
    }
}
