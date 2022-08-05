package ru.instamart.test.api.on_demand.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getZoneDbDate;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ETA")
public class BasketEtaTest extends RestBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    private final String STORE_UUID = UUID.randomUUID().toString();
    private final String STORE_UUID_WITH_DIFFERENT_TIMEZONE = UUID.randomUUID().toString();
    //ML работает не со всеми магазинами на стейдже, с STORE_UUID_WITH_ML должно работать
    private final String STORE_UUID_WITH_ML = "684609ad-6360-4bae-9556-03918c1e41c1";
    private final String ORDER_UUID = UUID.randomUUID().toString();
    private final String SHIPMENT_UUID = UUID.randomUUID().toString();
    private final String USER_UUID = UUID.randomUUID().toString();

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        addStore(STORE_UUID, 55.7010f, 37.7280f, "Europe/Moscow", false, "00:00:00", "00:00:00", "00:00:00", true);
        addStore(STORE_UUID_WITH_DIFFERENT_TIMEZONE, 55.7030f, 37.7230f, "Europe/Kaliningrad", false, "00:00:00", "00:00:00", "00:00:00", true);
    }

    @AfterMethod(alwaysRun = true)
    public void clearCache() {
        updateStoreWorkingTime(STORE_UUID, "00:00:00", "00:00:00", "00:00:00");
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID));
    }

    @CaseIDs(value = {@CaseId(11), @CaseId(40), @CaseId(49), @CaseId(60)})
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с валидными координатами пользователя",
            groups = "dispatch-eta-smoke")
    public void getBasketEta() {
        var request = getUserEtaRequest(USER_UUID, 55.7006f, 37.7266f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(21)
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с координатами клиента, соответствующими координатам магазина",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithStoreCoordinates() {
        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(20)
    @Story("Basket ETA")
    @Test(description = "Изменение результата, при изменении координат пользователя",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaForStoreWithDifferentCoordinates() {
        var requestFirstCoordinates = getUserEtaRequest(USER_UUID, 55.7006f, 37.7266f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);
        var requestSecondCoordinates = getUserEtaRequest(USER_UUID, 55.7000f, 37.7200f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var responseFirstCoordinates = clientEta.getBasketEta(requestFirstCoordinates);
        var responseSecondCoordinates = clientEta.getBasketEta(requestSecondCoordinates);
        checkBasketEta(responseFirstCoordinates, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
        checkBasketEta(responseSecondCoordinates, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);

        Allure.step("Проверяем изменение результата при изменении координат пользователя", () -> assertNotEquals(responseFirstCoordinates.getOrder().getShipmentEtas(0).getEta(), responseSecondCoordinates.getOrder().getShipmentEtas(0).getEta(), "Поля eta равны для разных координат"));
    }

    @CaseId(12)
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с не валидными координатами пользователя",
            groups = "dispatch-eta-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getBasketEtaWithInvalidCoordinates() {
        var request = getUserEtaRequest(USER_UUID, 124f, 312f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        clientEta.getBasketEta(request);
    }

    @CaseId(13)
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с невалидным магазином",
            groups = "dispatch-eta-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getBasketEtaWithInvalidStore() {
        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, "test", 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        clientEta.getBasketEta(request);
    }

    @CaseId(14)
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с валидным, но несуществующим в БД store_uuid",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaForNonExistentStore() {
        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, UUID.randomUUID().toString(), 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.toString(), "");
    }

    @CaseId(64)
    @Story("Basket ETA")
    @Test(description = "Отправка запросов с различным количеством структур в массиве sku",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithFewSku() {
        var firstRequest = getUserEtaRequest(USER_UUID, 55.7006f, 37.7266f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);
        var secondRequest = Eta.UserEtaRequest.newBuilder()
                .setUser(Eta.UserData.newBuilder()
                        .setUserUuid(USER_UUID)
                        .setLat(55.7006f)
                        .setLon(37.7266f)
                        .build())
                .setOrder(Eta.OrderData.newBuilder()
                        .setOrderUuid(ORDER_UUID)
                        .addShipments(Eta.ShipmentData.newBuilder()
                                .setShipmentUuid(SHIPMENT_UUID)
                                .setStoreInfo(Eta.StoreData.newBuilder()
                                        .setStoreUuid(STORE_UUID)
                                        .setLat(55.7010f)
                                        .setLon(37.7280f)
                                        .build())
                                .setBasket(Eta.UserStoreBasket.newBuilder()
                                        .setWeight(15.0f)
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku("sku-1")
                                                .setUnitQuantity(5)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku("sku-2")
                                                .setUnitQuantity(5)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku("sku-3")
                                                .setUnitQuantity(5)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku("sku-4")
                                                .setUnitQuantity(5)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku("sku-5")
                                                .setUnitQuantity(5)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        var firstResponse = clientEta.getBasketEta(firstRequest);
        var secondResponse = clientEta.getBasketEta(secondRequest);

        checkBasketEta(firstResponse, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
        checkBasketEta(secondResponse, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
        Allure.step("Проверка различной ETA для запросов", () -> assertTrue(secondResponse.getOrder().getShipmentEtas(0).getEta() > firstResponse.getOrder().getShipmentEtas(0).getEta(), "Поле eta второго запроса меньше или равно полю eta из первого"));
    }

    @CaseId(22)
    @Story("Basket ETA")
    @Test(description = "Отправка запросов с различным значением в параметре unit_quantity",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithDifferentQuantities() {
        var firstRequest = getUserEtaRequest(USER_UUID, 55.7006f, 37.7266f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);
        var secondRequest = Eta.UserEtaRequest.newBuilder()
                .setUser(Eta.UserData.newBuilder()
                        .setUserUuid(USER_UUID)
                        .setLat(55.7006f)
                        .setLon(37.7266f)
                        .build())
                .setOrder(Eta.OrderData.newBuilder()
                        .setOrderUuid(ORDER_UUID)
                        .addShipments(Eta.ShipmentData.newBuilder()
                                .setShipmentUuid(SHIPMENT_UUID)
                                .setStoreInfo(Eta.StoreData.newBuilder()
                                        .setStoreUuid(STORE_UUID)
                                        .setLat(55.7010f)
                                        .setLon(37.7280f)
                                        .build())
                                .setBasket(Eta.UserStoreBasket.newBuilder()
                                        .setWeight(5.0f)
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku("sku")
                                                .setUnitQuantity(50)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        var firstResponse = clientEta.getBasketEta(firstRequest);
        var secondResponse = clientEta.getBasketEta(secondRequest);

        checkBasketEta(firstResponse, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
        checkBasketEta(secondResponse, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);

        Allure.step("Проверка различной ETA для запросов", () -> assertTrue(secondResponse.getOrder().getShipmentEtas(0).getEta() > firstResponse.getOrder().getShipmentEtas(0).getEta(), "Поле eta второго запроса меньше или равно полю eta из первого"));
    }

    @CaseId(31)
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с двумя шипментами из двух разных магазинов",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaFromDifferentStores() {
        String secondShipmentUuid = UUID.randomUUID().toString();
        var request = Eta.UserEtaRequest.newBuilder()
                .setUser(Eta.UserData.newBuilder()
                        .setUserUuid(USER_UUID)
                        .setLat(55.7006f)
                        .setLon(37.7266f)
                        .build())
                .setOrder(Eta.OrderData.newBuilder()
                        .setOrderUuid(ORDER_UUID)
                        .addShipments(Eta.ShipmentData.newBuilder()
                                .setShipmentUuid(SHIPMENT_UUID)
                                .setStoreInfo(Eta.StoreData.newBuilder()
                                        .setStoreUuid(STORE_UUID)
                                        .setLat(55.7010f)
                                        .setLon(37.7280f)
                                        .build())
                                .setBasket(Eta.UserStoreBasket.newBuilder()
                                        .setWeight(5.0f)
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku("sku")
                                                .setUnitQuantity(1)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .build())
                                .build())
                        .addShipments(Eta.ShipmentData.newBuilder()
                                .setShipmentUuid(secondShipmentUuid)
                                .setStoreInfo(Eta.StoreData.newBuilder()
                                        .setStoreUuid(STORE_UUID_WITH_DIFFERENT_TIMEZONE)
                                        .setLat(55.7030f)
                                        .setLon(37.7230f)
                                        .build())
                                .setBasket(Eta.UserStoreBasket.newBuilder()
                                        .setWeight(5.0f)
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku("sku")
                                                .setUnitQuantity(1)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        var response = clientEta.getBasketEta(request);

        Allure.step("Проверка ЕТА в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            compareTwoObjects(response.getOrder().getOrderUuid(), ORDER_UUID, softAssert);
            compareTwoObjects(response.getOrder().getShipmentEtasCount(), 2, softAssert);
            compareTwoObjects(response.getOrder().getShipmentEtas(0).getShipmentUuid(), SHIPMENT_UUID, softAssert);
            compareTwoObjects(response.getOrder().getShipmentEtas(1).getShipmentUuid(), secondShipmentUuid, softAssert);
            compareTwoObjects(response.getOrder().getShipmentEtas(0).getEstimateSource(), Eta.EstimateSource.FALLBACK, softAssert);
            compareTwoObjects(response.getOrder().getShipmentEtas(1).getEstimateSource(), Eta.EstimateSource.FALLBACK, softAssert);
            softAssert.assertTrue(response.getOrder().getShipmentEtas(0).getEta() > 300, "Поле eta меньше 300 секунд");
            softAssert.assertTrue(response.getOrder().getShipmentEtas(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
            softAssert.assertTrue(response.getOrder().getShipmentEtas(1).getEta() > 300, "Поле eta меньше 300 секунд");
            softAssert.assertTrue(response.getOrder().getShipmentEtas(1).getSigma() > 0, "Поле sigma меньше или равно нулю");
            softAssert.assertAll();
        });
    }

    @CaseId(41)
    @Story("Basket ETA")
    @Test(description = "Отправка валидного запроса в закрытый магазин",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaForClosedStore() {
        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(2)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(1)));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:00:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.getOrder().getShipmentEtasCount(), 0);
    }

    @CaseId(45)
    @Story("Basket ETA")
    @Test(description = "Отправка валидного запроса в пределах работы параметра OnDemandClosingDelta",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaForClosedStoreViaClosingDelta() {
        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:30:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.getOrder().getShipmentEtasCount(), 0);
    }

    @CaseId(50)
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с OnDemandClosingDelta равным времени работы магазина",
            groups = "dispatch-eta-regress")
    public void getBasketEtaForClosedStoreEqualClosingDelta() {
        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1)));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "02:00:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.getOrder().getShipmentEtasCount(), 0);
    }

    @CaseId(55)
    @Story("Basket ETA")
    @Test(description = "Отправка валидного запроса в магазин, который в одном часовом поясе открыт, а другом закрыт",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaInDifferentTimezone() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(5));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusMinutes(5));
        updateStoreWorkingTime(STORE_UUID_WITH_DIFFERENT_TIMEZONE, openingDate, closingDate, "00:00:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID_WITH_DIFFERENT_TIMEZONE, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(59)
    @Story("Basket ETA")
    @Test(description = "Получение ответа от ML",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithML() {
        updateStoreMLStatus(STORE_UUID_WITH_ML, true);
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID_WITH_ML));

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID_WITH_ML, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.ML);
    }

    @CaseId(37)
    @Story("Basket ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае, если ML возвращает ноль",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithZeroML() {
        //магазин, которого нет в ML, но есть в ETA
        String storeUuid = "7f6b0fa1-ec20-41f9-9246-bfa0d6529dad";
        updateStoreMLStatus(storeUuid, true);

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, storeUuid, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (Objects.nonNull(STORE_UUID) ) {
            StoreParametersDao.INSTANCE.delete(STORE_UUID);
        }
        if (Objects.nonNull(STORE_UUID_WITH_DIFFERENT_TIMEZONE) ) {
            StoreParametersDao.INSTANCE.delete(STORE_UUID_WITH_DIFFERENT_TIMEZONE);
        }
    }
}
