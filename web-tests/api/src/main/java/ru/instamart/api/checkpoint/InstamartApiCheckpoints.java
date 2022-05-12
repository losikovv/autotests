package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.enums.v2.ProductSortTypeV2;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.model.common.OfferForRequest;
import ru.instamart.api.model.v1.*;
import ru.instamart.api.model.v2.*;
import ru.instamart.api.request.admin.CitiesAdminRequest;
import ru.instamart.api.request.admin.PagesAdminRequest;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.api.request.v1.CitiesV1Request;
import ru.instamart.api.request.v1.OrdersV1Request;
import ru.instamart.api.request.v1.PromotionCardsV1Request;
import ru.instamart.api.request.v1.ShippingPoliciesV1Request;
import ru.instamart.api.request.v1.admin.UsersV1Request;
import ru.instamart.api.response.v1.CompleteOrderV1Response;
import ru.instamart.api.response.v1.MobileConfigsV1Response;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v1.UserShipmentV1Response;
import ru.instamart.api.response.v2.ExternalPartnersServicesV2Response;
import ru.instamart.api.response.v2.ReviewIssuesV2Response;
import ru.instamart.api.response.v2.TransferMethodLossesV2Response;
import ru.instamart.api.response.v2.TransferMethodV2Response;
import ru.instamart.jdbc.dao.stf.ShipmentReviewIssuesDao;
import ru.instamart.jdbc.dao.stf.SpreeAddressesDao;
import ru.instamart.jdbc.dao.stf.SpreeRetailersDao;
import ru.instamart.jdbc.dao.stf.SpreeUsersDao;
import ru.instamart.jdbc.entity.stf.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
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
            return "Проверьте дату оформления заказа (Заказ должен быть оформлен на сегодня)\nnow: " + LocalDateTime.now()
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
        final SoftAssert softAssert = new SoftAssert();
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
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(marketingSampleFromResponse.getName().contains(name));
        compareTwoObjects(marketingSampleFromResponse.getName(), marketingSamplesEntity.get().getName(), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getDescription(), marketingSamplesEntity.get().getDescription(), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getComment(), marketingSamplesEntity.get().getComment(), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getExpiresAt().substring(0, 10), getFutureDateWithoutTime(5L), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getStartsAt().substring(0, 10), getDateWithoutTime(), softAssert);
        compareTwoObjects(marketingSampleFromResponse.getStores().get(0).getId(), EnvironmentProperties.DEFAULT_SID, softAssert);
        compareTwoObjects(marketingSampleFromResponse.getStores().get(0).getRetailer().getName(), "METRO", softAssert);
        compareTwoObjects(marketingSamplesEntity.get().getUserId(), SpreeUsersDao.INSTANCE.getUserByEmail(email).getId(), softAssert);
        softAssert.assertAll();
    }
    @Step("Проверяем информацию о подписке, пришедшую в ответе")
    public static void checkExternalPartnersServices(Response response, Boolean isActive, String text) {
        List<ServicesV2> services = response.as(ExternalPartnersServicesV2Response.class).getServices();
        checkFieldIsNotEmpty(services, "сервисы");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(services.get(0).getName(), "SberPrime", softAssert);
        compareTwoObjects(services.get(0).getKind(), "sber_prime", softAssert);
        compareTwoObjects(services.get(0).getText(), text, softAssert);
        compareTwoObjects(services.get(0).getDiscountType(), "free_delivery", softAssert);
        compareTwoObjects(services.get(0).getSubscription().getActive(), isActive, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем сохранившуюся информацию о городе в БД")
    public static void checkCityInDb(CitiesAdminRequest.City city, CitiesEntity cityFromDb) {
        checkFieldIsNotEmpty(cityFromDb, "город в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(city.getNameFrom(), cityFromDb.getNameFrom(), softAssert);
        compareTwoObjects(city.getNameTo(), cityFromDb.getNameTo(), softAssert);
        compareTwoObjects(city.getNameIn(), cityFromDb.getNameIn(), softAssert);
        compareTwoObjects(city.getSlug(), cityFromDb.getSlug(), softAssert);
        compareTwoObjects(city.getLocked(), cityFromDb.getLocked(), softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем страницу в БД")
    public static void checkPageInDb(PagesAdminRequest.Page page, SpreePagesEntity pageFromDb, int visible, int position) {
        checkFieldIsNotEmpty(pageFromDb, "страница в БД");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(page.getTitle(), pageFromDb.getTitle(), softAssert);
        softAssert.assertTrue(pageFromDb.getBody().contains(page.getBody()));
        compareTwoObjects(page.getMetaDescription(), pageFromDb.getMetaDescription(), softAssert);
        compareTwoObjects(page.getMetaTitle(), pageFromDb.getMetaTitle(), softAssert);
        compareTwoObjects(page.getMetaKeywords(), pageFromDb.getMetaKeywords(), softAssert);
        compareTwoObjects(page.getForeignLink(), pageFromDb.getForeignLink(), softAssert);
        compareTwoObjects(page.getLayout(), pageFromDb.getLayout(), softAssert);
        compareTwoObjects(visible, pageFromDb.getVisible(), softAssert);
        compareTwoObjects(position, pageFromDb.getShowInFooter(), softAssert);
        compareTwoObjects(position, pageFromDb.getShowInHeader(), softAssert);
        compareTwoObjects(position, pageFromDb.getShowInSidebar(), softAssert);
        compareTwoObjects(position, pageFromDb.getRenderLayoutAsPartial(), softAssert);
        if(position == 1) {
            compareTwoObjects(position, pageFromDb.getPosition(), softAssert);
        }
        softAssert.assertAll();
    }

    @Step("Сравниваем магазин с сохраненным в БД")
    public static void checkStoreInDb(StoresAdminRequest.Store store, StoresEntity storeFromDb, StoreConfigsEntity storeConfigs) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(store.getRetailerId(), storeFromDb.getRetailerId(), softAssert);
        compareTwoObjects(store.getOperationalZoneId(), storeFromDb.getOperationalZoneId(), softAssert);
        compareTwoObjects(store.getTimeZone(), storeFromDb.getTimeZone(), softAssert);
        compareTwoObjects(store.getShipmentBaseKilos() * 1000, storeConfigs.getShipmentBaseWeight(), softAssert);
        compareTwoObjects(store.getShipmentBaseItemsCount(), storeConfigs.getShipmentBaseItemsCount(), softAssert);
        compareTwoObjects(store.getMinFirstOrderAmount(), storeConfigs.getMinFirstOrderAmount(), softAssert);
        compareTwoObjects(store.getMinOrderAmount(), storeConfigs.getMinOrderAmount(), softAssert);
        compareTwoObjects(store.getMinOrderAmountPickup(), storeConfigs.getMinOrderAmountPickup(), softAssert);
        compareTwoObjects(store.getMinFirstOrderAmountPickup(), storeConfigs.getMinFirstOrderAmountPickup(), softAssert);
        compareTwoObjects(store.getDisallowOrderEditingHours(), storeConfigs.getDisallowOrderEditingHours(), softAssert);
        softAssert.assertAll();
    }

    @Step("Сравниваем полученные правила доступности слотов доставки с отправленными")
    public static void compareShippingPolicies(ShippingPoliciesV1Request.ShippingPolicies shippingPolicies, ShippingPolicyV1 updatedShippingPolicy) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(updatedShippingPolicy.getTitle(), shippingPolicies.getShippingPolicy().getTitle(), softAssert);
        compareTwoObjects(updatedShippingPolicy.getRules().get(0).getPreferences().getDays(), shippingPolicies.getShippingPolicy().getRulesAttributes().get(0).getPreferredDays(), softAssert);
        compareTwoObjects(updatedShippingPolicy.getRules().get(1).getPreferences().getTimeGap(), shippingPolicies.getShippingPolicy().getRulesAttributes().get(1).getPreferredTimeGap(), softAssert);
        compareTwoObjects(updatedShippingPolicy.getRules().get(2).getPreferences().getTime(), shippingPolicies.getShippingPolicy().getRulesAttributes().get(2).getPreferredTime(), softAssert);
        compareTwoObjects(updatedShippingPolicy.getRules().get(3).getPreferences().getNumber(), shippingPolicies.getShippingPolicy().getRulesAttributes().get(3).getPreferredNumber(), softAssert);
        compareTwoObjects(updatedShippingPolicy.getRules().get(4).getPreferences().getTimeGap(), shippingPolicies.getShippingPolicy().getRulesAttributes().get(4).getPreferredTimeGap(), softAssert);
        softAssert.assertAll();
    }

    @Step("Сравнение полученной инфомарции о доставке с эталонной")
    public static void checkShipmentInfo(ActiveShipmentV2 shipmentFromResponse, OrderV2 order) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipmentFromResponse.getOrder().getNumber(), order.getNumber(), softAssert);
        compareTwoObjects(shipmentFromResponse.getId(), order.getShipments().get(0).getId(), softAssert);
        compareTwoObjects(shipmentFromResponse.getNumber(), order.getShipments().get(0).getNumber(), softAssert);
        compareTwoObjects(shipmentFromResponse.getState(), StateV2.READY.getValue(), softAssert);
        compareTwoObjects(shipmentFromResponse.getDeliveryWindow().getId(), order.getShipments().get(0).getDeliveryWindow().getId(), softAssert);
        compareTwoObjects(shipmentFromResponse.getLineItems().size(), order.getShipments().get(0).getLineItems().size(), softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем сохранение адреса в базе данных")
    public static void checkAddressInDb(AddressV2 address, UserData user) {
        SpreeAddressesEntity addressFromDb = SpreeAddressesDao.INSTANCE.getAddressByUserPhone(user.getPhone());
        checkFieldIsNotEmpty(addressFromDb, "адрес пользователя");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(addressFromDb.getCity(), address.getCity(), softAssert);
        compareTwoObjects(addressFromDb.getStreet(), address.getStreet(), softAssert);
        compareTwoObjects(addressFromDb.getBuilding(), address.getBuilding(), softAssert);
        compareTwoObjects(addressFromDb.getFullAddress(), address.getFullAddress(), softAssert);
        compareTwoObjects(addressFromDb.getLat(), address.getLat(), softAssert);
        compareTwoObjects(addressFromDb.getLon(), address.getLon(), softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем потери")
    public static void checkLosses(Response response, LineItemV2 lineItem) {
        checkResponseJsonSchema(response, TransferMethodLossesV2Response.class);
        List<LossV2> losses = response.as(TransferMethodLossesV2Response.class).getLosses();
        checkFieldIsNotEmpty(losses, "потери");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects((long) losses.get(0).getRetailer().getId(), SpreeRetailersDao.INSTANCE.getIdBySlug("metro"), softAssert);
        compareTwoObjects(losses.get(0).getOffers().size(), 1);
        compareTwoObjects(losses.get(0).getOffers().get(0).getId(), lineItem.getProduct().getId(), softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем, что потери отсутствуют")
    public static void checkEmptyLossesWithOrder(Response response, String orderNumber) {
        checkResponseJsonSchema(response, TransferMethodV2Response.class);
        TransferMethodV2Response transferMethod = response.as(TransferMethodV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(transferMethod.getLosses().size(), 0, softAssert);
        compareTwoObjects(transferMethod.getOrder().getNumber(), orderNumber, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем потери")
    public static void checkLossesWithOrder(Response response, LineItemV2 lineItem, String orderNumber) {
        checkResponseJsonSchema(response, TransferMethodV2Response.class);
        TransferMethodV2Response transferMethod = response.as(TransferMethodV2Response.class);
        checkFieldIsNotEmpty(transferMethod.getLosses(), "потери");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(transferMethod.getLosses().size(), 1);
        compareTwoObjects(transferMethod.getLosses().get(0).getOffers().get(0).getId(), lineItem.getProduct().getId(), softAssert);
        compareTwoObjects(transferMethod.getOrder().getNumber(), orderNumber, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем пришедшие проблемы для отзыва и сравниваем их с БД")
    public static void checkReviewIssuesList(Response response) {
        checkResponseJsonSchema(response, ReviewIssuesV2Response.class);
        List<ReviewIssueV2> reviewIssues = response.as(ReviewIssuesV2Response.class).getReviewIssues();
        List<ReviewIssueV2> sortedReviewIssues = reviewIssues.stream().sorted(Comparator.comparing(ReviewIssueV2::getPosition)).collect(Collectors.toList());
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(reviewIssues, sortedReviewIssues, softAssert);
        compareTwoObjects(reviewIssues.size(), ShipmentReviewIssuesDao.INSTANCE.getCount(), softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем пришедший заказ пользователя {user.email}")
    public static void checkUserShipmentFromResponse(Response response, MultiretailerOrderV1Response order, UserData user, String state, String delayText) {
        checkResponseJsonSchema(response, UserShipmentV1Response.class);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipmentFromResponse.getNumber(),order.getShipments().get(0).getNumber(), softAssert);
        compareTwoObjects(shipmentFromResponse.getOrderNumber(), order.getNumber(), softAssert);
        compareTwoObjects(shipmentFromResponse.getState(), state, softAssert);
        compareTwoObjects(shipmentFromResponse.getEmail(),user.getEmail(), softAssert);
        compareTwoObjects(shipmentFromResponse.getStoreId(), EnvironmentProperties.DEFAULT_SID, softAssert);
        if (delayText == null) {
            compareTwoObjects(shipmentFromResponse.getDelay(), null, softAssert);
        } else {
            checkFieldIsNotEmpty(shipmentFromResponse.getDelay(), "текст опоздания");
            compareTwoObjects(shipmentFromResponse.getDelay().getText(), "Задерживаемся, но очень торопимся", softAssert);
        }
        softAssert.assertAll();
    }

    @Step("Проверяем пришедший заказ пользователя {user.email}")
    public static void checkUserShipmentFromResponse(Response response, OrderV2 order, UserData user, String state, String delayText) {
        checkResponseJsonSchema(response, UserShipmentV1Response.class);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(shipmentFromResponse.getNumber(),order.getShipments().get(0).getNumber(), softAssert);
        compareTwoObjects(shipmentFromResponse.getOrderNumber(), order.getNumber(), softAssert);
        compareTwoObjects(shipmentFromResponse.getState(), state, softAssert);
        compareTwoObjects(shipmentFromResponse.getEmail(),user.getEmail(), softAssert);
        compareTwoObjects(shipmentFromResponse.getStoreId(), order.getShipments().get(0).getStore().getId(), softAssert);
        if (delayText == null) {
            compareTwoObjects(shipmentFromResponse.getDelay(), null, softAssert);
        } else {
            checkFieldIsNotEmpty(shipmentFromResponse.getDelay(), "текст опоздания");
            compareTwoObjects(shipmentFromResponse.getDelay().getText(), "Задерживаемся, но очень торопимся", softAssert);
        }
        softAssert.assertAll();
    }

    @Step("Проверяем информацию в созданном заказе {completedOrder.shipmentNumber}")
    public static void compareWithUserShipment(UserShipmentV1 userShipment, UserData user, PaymentToolV1 paymentTool, CompleteOrderV1Response completedOrder) {
        final SoftAssert nextSoftAssert = new SoftAssert();
        compareTwoObjects(userShipment.getState(), StateV2.READY.getValue(), nextSoftAssert);
        compareTwoObjects(userShipment.getPaymentMethod().getId(), (long) paymentTool.getPaymentMethod().getId(), nextSoftAssert);
        compareTwoObjects(userShipment.getEmail(), user.getEmail(), nextSoftAssert);
        nextSoftAssert.assertAll();
    }

    @Step("Сравниваем оффер из запроса с оффером из ответа")
    public static void checkOffer(OfferForRequest offer, OfferV1 offerFromResponse, boolean isPublished) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(offerFromResponse.getName(), offer.getOffer().getName(), softAssert);
        compareTwoObjects(offerFromResponse.getPriceType(), offer.getOffer().getPriceType(), softAssert);
        compareTwoObjects(offerFromResponse.getStock(), offer.getOffer().getStock(), softAssert);
        compareTwoObjects(offerFromResponse.getMaxStock(), offer.getOffer().getMaxStock(), softAssert);
        compareTwoObjects(offerFromResponse.getPickupOrder(), 1, softAssert);
        compareTwoObjects(offerFromResponse.getPublished(), isPublished, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем компенсацию за заказ в БД")
    public static void checkOrderCompensations(Optional<OrderCompensationsEntity> orderCompensationFromDb, OrdersV1Request.Compensation compensation) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(orderCompensationFromDb.get().getEmail(), compensation.getCompensation().getEmail(), softAssert);
        compareTwoObjects(orderCompensationFromDb.get().getEmailBody(), compensation.getCompensation().getEmailBody(), softAssert);
        compareTwoObjects(orderCompensationFromDb.get().getComment(), compensation.getCompensation().getComment(), softAssert);
        compareTwoObjects(orderCompensationFromDb.get().getPromotionId(), compensation.getCompensation().getPromotionId(), softAssert);
        compareTwoObjects(orderCompensationFromDb.get().getPromoType(), compensation.getCompensation().getPromoType(), softAssert);
        compareTwoObjects(orderCompensationFromDb.get().getReason(), compensation.getCompensation().getReason(), softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем промо-карточку")
    public static void checkPromotionCard(PromotionCardsV1Request.PromotionCard promotionCard, PromotionCardV1 promotionCardFromResponse, List<Integer> storeIds) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(promotionCardFromResponse.getBackgroundColor(), promotionCard.getBackgroundColor(), softAssert);
        compareTwoObjects(promotionCardFromResponse.getMessageColor(), promotionCard.getMessageColor(), softAssert);
        compareTwoObjects(promotionCardFromResponse.getFullDescription(), promotionCard.getFullDescription(), softAssert);
        compareTwoObjects(promotionCardFromResponse.getShortDescription(), promotionCard.getShortDescription(), softAssert);
        compareTwoObjects(promotionCardFromResponse.getTitle(), promotionCard.getTitle(), softAssert);
        compareTwoObjects("PromotionCards::" + promotionCardFromResponse.getType(), promotionCard.getType(), softAssert);
        compareTwoObjects(promotionCardFromResponse.getPublished(), promotionCard.getPublished(), softAssert);
        compareTwoObjects(promotionCardFromResponse.getStoreIds(), storeIds, softAssert);
        compareTwoObjects(promotionCardFromResponse.getTenantIds().get(0), promotionCard.getTenantIds(), softAssert);
        compareTwoObjects(promotionCardFromResponse.getCategory().getId(), promotionCard.getCategoryId(), softAssert);
        compareTwoObjects(promotionCardFromResponse.getPromotion().getId(), promotionCard.getPromotionId(), softAssert);
        if(Objects.nonNull(promotionCardFromResponse.getBackgroundImage())) {
            softAssert.assertTrue(promotionCardFromResponse.getBackgroundImage().contains("sample.jpg"));
        }
        softAssert.assertAll();
    }
    @Step("Проверяем платеж")
    public static void checkPayment(PaymentV1 paymentFromResponse, MultiretailerOrderV1Response order) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(paymentFromResponse.getAmount(), order.getTotal(), softAssert);
        compareTwoObjects(paymentFromResponse.getState(), "completed", softAssert);
        compareTwoObjects(paymentFromResponse.getPaymentMethod().getName(), "Картой онлайн", softAssert);
        compareTwoObjects(paymentFromResponse.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber(), softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем, что интервалы доставки для первой зоны: '{zone1DeliveryInterval}' и второй зоны: '{zone2DeliveryInterval}' отличаются")
    public static void checkDeliveryIntervalsNonEquals(final String zone1DeliveryInterval, final String zone2DeliveryInterval) {
        Assert.assertNotEquals(zone1DeliveryInterval, zone2DeliveryInterval, "Интервалы доставки для выбранных зон одинаковы");
    }

    @Step("Выбираем ближайший интервал доставки из двух: '{zone1DeliveryInterval}' и '{zone2DeliveryInterval}'")
    public static String getNearestInterval(final String zone1DeliveryInterval, final String zone2DeliveryInterval) {
        final String intervalReplacement = "сегодня |\\–.+";
        LocalTime firstDeliveryStartAt = LocalTime.parse(zone1DeliveryInterval.replaceAll(intervalReplacement, ""));
        LocalTime secondDeliveryStartAt = LocalTime.parse(zone2DeliveryInterval.replaceAll(intervalReplacement, ""));
        return firstDeliveryStartAt.isBefore(secondDeliveryStartAt) ? zone1DeliveryInterval : zone2DeliveryInterval;
    }

    @Step("Проверяем созданный город")
    public static void checkCity(CityV1 cityFromResponse, CitiesV1Request.City city) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(cityFromResponse.getName(), city.getName(), softAssert);
        compareTwoObjects(cityFromResponse.getSlug(), city.getSlug(), softAssert);
        compareTwoObjects(cityFromResponse.getNameFrom(), city.getNameFrom(), softAssert);
        compareTwoObjects(cityFromResponse.getNameTo(), city.getNameTo(), softAssert);
        compareTwoObjects(cityFromResponse.getNameIn(), city.getNameIn(), softAssert);
        compareTwoObjects(cityFromResponse.getLocked(), false, softAssert);
        softAssert.assertAll();
    }
    @Step("Проверяем FAQ")
    public static void checkFaq(FaqV1 faqResponse, String text, Long faqGroupId, int position) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(faqResponse.getAnswer(), text, softAssert);
        compareTwoObjects(faqResponse.getQuestion(), text, softAssert);
        compareTwoObjects(faqResponse.getFaqGroupId(), faqGroupId, softAssert);
        compareTwoObjects(faqResponse.getPosition(), position, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем конфигурацию мобильного приложения")
    public static void checkMobileConfig(Response response, int mobileExtendId, String property) {
        MobileExtendV1 mobileExtend = response.as(MobileConfigsV1Response.class).getMobileExtend();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(mobileExtend.getId(), (long) mobileExtendId, softAssert);
        compareTwoObjects(mobileExtend.getProp(), property, softAssert);
        softAssert.assertAll();
    }

    @Step("Сравниваем пользователя из запроса с пользователем из ответа")
    public static void checkUsers(UsersV1Request.UserRequest user, AdminUserV1 userFromResponse) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(userFromResponse.getEmail(), user.getUser().getEmail(), softAssert);
        compareTwoObjects(userFromResponse.getCustomerComment(), user.getUser().getCustomerComment(), softAssert);
        compareTwoObjects(userFromResponse.getPreferredCardPaymentMethod(), user.getUser().getPreferredCardPaymentMethod(), softAssert);
        compareTwoObjects(userFromResponse.getRoleIds().stream().sorted().collect(Collectors.toList()), user.getUser().getRoleIds(), softAssert);
        softAssert.assertAll();
    }
}
