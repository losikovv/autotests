package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.enums.v2.ProductSortTypeV2;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.model.v1.MarketingSampleV1;
import ru.instamart.api.model.v2.*;
import ru.instamart.api.response.v2.ExternalPartnersServicesV2Response;
import ru.instamart.jdbc.dao.SpreeUsersDao;
import ru.instamart.jdbc.entity.MarketingSamplesEntity;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.kraken.util.TimeUtil.getDateWithoutTime;
import static ru.instamart.kraken.util.TimeUtil.getFutureDateWithoutTime;

@Slf4j
public class InstamartApiCheckpoints {

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

    @Step("Проверяем, что при неверных запросах не возвращаются поисковые подсказки")
    public static void checkSearchSuggestionsNegative(SuggestionV2 suggestion) {
        assertNull(suggestion.getProducts(), "Вернулись продукты в поисковых подсказках");
        assertNull(suggestion.getSearches(), "Вернулся поисковый запрос в поисковых подсказках");
        assertNull(suggestion.getSearchPhrases(), "Вернулся поисковый запрос в поисковых подсказках");
        assertNull(suggestion.getTaxons(), "Вернулись группы в поисковых подсказках");
    }

    @Step("Сравниваем информацию о товарах в подзаказе с информацией, пришедшей в ответе")
    public static void checkAssemblyItem(ShipmentV2 shipment, AssemblyItemV2 assemblyItem, StateV2 state) {
        SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(assemblyItem.getTotal(), shipment.getItemTotal(), softAssert);
        compareTwoObjects(assemblyItem.getPcs(), shipment.getItemCount(), softAssert);
        compareTwoObjects(assemblyItem.getPriceType(), shipment.getLineItems().get(0).getProduct().getPriceType(), softAssert);
        compareTwoObjects(assemblyItem.getProductId(), shipment.getLineItems().get(0).getProduct().getId(), softAssert);
        compareTwoObjects(assemblyItem.getState(), state.getValue(), softAssert);
        softAssert.assertNull(assemblyItem.getReplacement(), "В подзаказе есть замена");
        softAssert.assertNull(assemblyItem.getFoundQuantity(), "Количество не пустое");
        softAssert.assertNull(assemblyItem.getQuantity(), "Количество не пустое");
        softAssert.assertAll();
    }

    @Step("Проверяем маркетинговые сэмплы из ответа и сравниваем с БД")
    public static void compareMarketingSamples(String email, MarketingSampleV1 marketingSampleFromResponse, Optional<MarketingSamplesEntity> marketingSamplesEntity, String name) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(marketingSampleFromResponse.getName().contains(name));
        compareTwoObjects(marketingSampleFromResponse.getName(), marketingSamplesEntity.get().getName(), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getDescription(), marketingSamplesEntity.get().getDescription(), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getComment(), marketingSamplesEntity.get().getComment(), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getExpiresAt().substring(0, 10), getFutureDateWithoutTime(5L), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getStartsAt().substring(0, 10), getDateWithoutTime(), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getStores().get(0).getId(), EnvironmentProperties.DEFAULT_SID, softAssert);
        compareTwoObjects(marketingSampleFromResponse.getStores().get(0).getRetailer().getName(), "METRO", softAssert);
        compareTwoObjects(marketingSamplesEntity.get().getUserId(), SpreeUsersDao.INSTANCE.getIdByEmail(email), softAssert);
        softAssert.assertAll();
    }
    @Step("Проверяем информацию о подписке, пришедшую в ответе")
    public static void checkExternalPartnersServices(Response response, Boolean isActive) {
        ServicesV2 service = response.as(ExternalPartnersServicesV2Response.class).getServices().get(0);
        checkFieldIsNotEmpty(service, "сервис");
        SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(service.getName(), "SberPrime", softAssert);
        compareTwoObjects(service.getKind(), "sber_prime", softAssert);
        compareTwoObjects(service.getText(), "Бесплатная доставка", softAssert);
        compareTwoObjects(service.getDiscountType(), "free_delivery", softAssert);
        compareTwoObjects(service.getSubscription().getActive(), isActive, softAssert);
        softAssert.assertAll();
    }
}
