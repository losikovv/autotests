package ru.instamart.api.checkpoint;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.enums.v2.ProductSortTypeV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.SortV2;
import ru.instamart.api.model.v2.SuggestionV2;
import ru.instamart.api.model.v2.TaxonV2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.util.Objects.isNull;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@Slf4j
public class InstamartApiCheckpoints {

    public static void checkStatusCode(Response response, int statusCode) {
        Allure.step("Проверка statusCode = "+statusCode+" для response");
        assertEquals(
                response.statusCode(),
                statusCode,
                "\n" + response.statusLine() + "\n" + response.body().asString());
        if (statusCode == 200) response.then().contentType(ContentType.JSON);
    }

    public static void checkStatusCode200(Response response) {
        checkStatusCode(response, 200);
    }

    public static void checkStatusCode400(final Response response) {
        checkStatusCode(response, 400);
    }

    public static void checkStatusCode401(final Response response) {
        checkStatusCode(response, 401);
    }

    public static void checkStatusCode403(final Response response) {
        checkStatusCode(response, 403);
    }

    public static void checkStatusCode404(final Response response) {
        checkStatusCode(response, 404);
    }

    public static void checkStatusCode500(final Response response) {
        checkStatusCode(response, 500);
    }

    public static void checkStatusGroup400(final Response response){
        Allure.step("Проверка statusCode в группе клиентских ошибок (400-499) для response");
        assertEquals(
                Math.abs(response.statusCode()/100),
                4,
                "\n" + response.statusLine() + "\n" + response.body().asString());
    }

    public static void checkStatusCode200or404(final Response response) {
        Allure.step("Проверка statusCode 200 или 404 для response");
        response.then().statusCode(anyOf(is(200), is(404)));
    }

    public static void checkStatusCode422(final Response response) {
        checkStatusCode(response, 422);
    }

    @Step("Продукты отсортированы {sortTypeV2.name}")
    public static void checkSort(final ProductSortTypeV2 sortTypeV2, final List<SortV2> sorts) {

        for (SortV2 sort : sorts) {
            Boolean active = sort.getKey().equals(sortTypeV2.getKey());
            assertEquals(sort.getActive(), active);

            if (active) {
                assertEquals(sort.getName(), sortTypeV2.getName());
                assertEquals(sort.getOrder(), sortTypeV2.getOrder());
            }
        }
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

    @Step("Проверяем, что два объекта совпадают")
    public static <T> void compareTwoObjects(T firstObject, T secondObject, SoftAssert softAssert) {
        softAssert.assertEquals(firstObject, secondObject, "Объекты не совпадают");
    }

    @Step("Проверяем, что при неверных запросах не возвращаются поисковые подсказки")
    public static void checkSearchSuggestionsNegative(SuggestionV2 suggestion) {
        assertNull(suggestion.getProducts(), "Вернулись продукты в поисковых подсказках");
        assertNull(suggestion.getSearches(), "Вернулся поисковый запрос в поисковых подсказках");
        assertNull(suggestion.getSearchPhrases(), "Вернулся поисковый запрос в поисковых подсказках");
        assertNull(suggestion.getTaxons(), "Вернулись группы в поисковых подсказках");
    }
}
