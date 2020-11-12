package instamart.api.checkpoints;

import instamart.api.objects.v2.Order;
import instamart.api.objects.v2.Taxon;
import instamart.api.responses.v2.ErrorResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

public class ApiV2Checkpoints {

    /**
     * Ассерт, что статус код 200
     */
    public static void assertStatusCode200(Response response) {
        switch (response.statusCode()) {
            case 200: break;
            case 400:
            case 401:
            case 403:
            case 404:
            case 422:
                Assert.assertEquals(response.statusCode(),200,
                        response.as(ErrorResponse.class).getErrors().getBase());
                break;
            case 502:
                Assert.assertEquals(response.statusCode(),200,
                        "СЕРВЕР ЛЕЖИТ");
            default:
                Assert.assertEquals(response.statusCode(),200, response.body().print());
        }
    }

    /**
     * Ассерт, что дата доставки заказа сегодня
     */
    @Step("Проверяем, что дата доставки заказа сегодня")
    public static void assertIsDeliveryToday(Order order) {
        String today = String.valueOf(LocalDate.now());
        String deliveryTime = order.getShipments().get(0).getDelivery_window().getStarts_at();
        if (!deliveryTime.contains(today)) org.junit.Assert.fail("Заказ оформлен не на сегодня\ntoday: " +
                today + "\ndelivery time: " + deliveryTime);
    }

    /**
     * Софт-ассерт, что количество товаров в категории равно сумме товаров в подкатегориях
     */
    @Step("Проверяем, что количество товаров в категории равно сумме товаров в подкатегориях")
    public static void assertProductsCountEqualsChildrenSum(Taxon taxon, SoftAssert softAssert) {
        List<Taxon> children = taxon.getChildren();
        if (children == null) return;
        int sum = 0;
        for (Taxon child : children) {
            sum += child.getProducts_count();
            assertProductsCountEqualsChildrenSum(child, softAssert);
        }
        softAssert.assertEquals(sum, taxon.getProducts_count(),
                "\n" + taxon.getName() +
                        ", id: " + taxon.getId() +
                        ", children_sum: " + sum +
                        ", product_count: " + taxon.getProducts_count());
    }
}
