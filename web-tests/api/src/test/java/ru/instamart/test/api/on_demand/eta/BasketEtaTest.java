package ru.instamart.test.api.on_demand.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import eta.PredEtaGrpc.PredEtaBlockingStub;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.EtaBase;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.helper.EtaHelper;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getZoneDbDate;

@Epic("ETA")
@Feature("Basket Eta")
public class BasketEtaTest extends EtaBase {

    private PredEtaBlockingStub clientEta;
    private final String ORDER_UUID = UUID.randomUUID().toString();
    private final String SHIPMENT_UUID = UUID.randomUUID().toString();
    private final String USER_UUID = UUID.randomUUID().toString();
    private boolean etaEnableOnDemandCheck;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        etaEnableOnDemandCheck = EtaHelper.getInstance().isStoreOndemand();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCache() {
        updateStoreWorkingTime(STORE_UUID, "00:00:00", "00:00:00", "00:00:00");
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID));
    }

    @TmsLinks(value = {@TmsLink("11"), @TmsLink("40"), @TmsLink("49"), @TmsLink("60")})
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с валидными координатами пользователя",
            groups = "ondemand-eta")
    public void getBasketEta() {
        var request = getUserEtaRequest(USER_UUID, 55.7006f, 37.7266f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("21")
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с координатами клиента, соответствующими координатам магазина",
            groups = "ondemand-eta")
    public void getBasketEtaWithStoreCoordinates() {
        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("20")
    @Story("Basket ETA")
    @Test(description = "Изменение результата, при изменении координат пользователя",
            groups = "ondemand-eta")
    public void getBasketEtaForStoreWithDifferentCoordinates() {
        var requestFirstCoordinates = getUserEtaRequest(USER_UUID, 55.7006f, 37.7266f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);
        var requestSecondCoordinates = getUserEtaRequest(USER_UUID, 55.7000f, 37.7200f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var responseFirstCoordinates = clientEta.getBasketEta(requestFirstCoordinates);
        var responseSecondCoordinates = clientEta.getBasketEta(requestSecondCoordinates);
        checkBasketEta(responseFirstCoordinates, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
        checkBasketEta(responseSecondCoordinates, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);

        Allure.step("Проверяем изменение результата при изменении координат пользователя", () -> assertNotEquals(responseFirstCoordinates.getOrder().getShipmentEtas(0).getEta(), responseSecondCoordinates.getOrder().getShipmentEtas(0).getEta(), "Поля eta равны для разных координат"));
    }

    @TmsLink("12")
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с не валидными координатами пользователя",
            groups = "ondemand-eta",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getBasketEtaWithInvalidCoordinates() {
        var request = getUserEtaRequest(USER_UUID, 124f, 312f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        clientEta.getBasketEta(request);
    }

    @TmsLink("13")
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с невалидным магазином",
            groups = "ondemand-eta",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation failed")
    public void getBasketEtaWithInvalidStore() {
        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, "test", 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        clientEta.getBasketEta(request);
    }

    @TmsLink("14")
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с валидным, но несуществующим в БД store_uuid",
            groups = "ondemand-eta")
    public void getBasketEtaForNonExistentStore() {
        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, UUID.randomUUID().toString(), 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.toString(), "");
    }

    @TmsLink("64")
    @Story("Basket ETA")
    @Test(description = "Отправка запросов с различным количеством структур в массиве sku",
            groups = "ondemand-eta")
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

    @TmsLink("22")
    @Story("Basket ETA")
    @Test(description = "Отправка запросов с различным значением в параметре unit_quantity",
            groups = "ondemand-eta")
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

    @TmsLink("31")
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с двумя шипментами из двух разных магазинов",
            groups = "ondemand-eta")
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

    @TmsLink("41")
    @Story("Basket ETA")
    @Test(description = "Получение пустого ответа при запросе в закрытый магазин (ETA_ENABLE_STORE_ON_DEMAND_CHECK=true)",
            groups = "ondemand-eta")
    public void getBasketEtaForClosedStoreTrue() {

        if (!etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = false");
        }

        final var openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(2)));
        final var closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(1)));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:00:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.getOrder().getShipmentEtasCount(), 0);
    }

    @TmsLink("45")
    @Story("Basket ETA")
    @Test(description = "Получение пустого ответа при запросе в пределах работы параметра OnDemandClosingDelta (ETA_ENABLE_STORE_ON_DEMAND_CHECK=true)",
            groups = "ondemand-eta")
    public void getBasketEtaForClosedStoreViaClosingDeltaTrue() {

        if (!etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = false");
        }

        final var openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        final var closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:30:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.getOrder().getShipmentEtasCount(), 0);
    }

    @TmsLink("50")
    @Story("Basket ETA")
    @Test(description = "Получение пустого ответа при запросе с OnDemandClosingDelta равным времени работы магазина (ETA_ENABLE_STORE_ON_DEMAND_CHECK=true)",
            groups = "ondemand-eta")
    public void getBasketEtaForClosedStoreEqualClosingDeltaTrue() {

        if (!etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = false");
        }

        final var openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        final var closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1)));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "02:00:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.getOrder().getShipmentEtasCount(), 0);
    }

    @TmsLink("248")
    @Story("Basket ETA")
    @Test(description = "Получение ЕТА при запросе в закрытый магазин (ETA_ENABLE_STORE_ON_DEMAND_CHECK=false)",
            groups = "ondemand-eta")
    public void getBasketEtaForClosedStoreFalse() {

        if (etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = true");
        }

        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(2)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(1)));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:00:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("251")
    @Story("Basket ETA")
    @Test(description = "Получение ЕТА при запросе в пределах работы параметра OnDemandClosingDelta (ETA_ENABLE_STORE_ON_DEMAND_CHECK=false)",
            groups = "ondemand-eta")
    public void getBasketEtaForClosedStoreViaClosingDeltaFalse() {

        if (etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = true");
        }

        final var openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        final var closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "00:30:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("252")
    @Story("Basket ETA")
    @Test(description = "Получение ЕТА при запросе с OnDemandClosingDelta равным времени работы магазина (ETA_ENABLE_STORE_ON_DEMAND_CHECK=false)",
            groups = "ondemand-eta")
    public void getBasketEtaForClosedStoreEqualClosingDeltaFalse() {

        if (etaEnableOnDemandCheck) {
            throw new SkipException("Пропускапем, потому что ETA_ENABLE_STORE_ON_DEMAND_CHECK = true");
        }

        final var openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        final var closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().plusHours(1)));
        updateStoreWorkingTime(STORE_UUID, openingDate, closingDate, "02:00:00");

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("55")
    @Story("Basket ETA")
    @Test(description = "Отправка валидного запроса в магазин, который в одном часовом поясе открыт, а другом закрыт",
            groups = "ondemand-eta")
    public void getBasketEtaInDifferentTimezone() {
        String openingDate = getZoneDbDate(LocalDateTime.now().minusMinutes(5));
        String closingDate = getZoneDbDate(LocalDateTime.now().plusMinutes(5));
        updateStoreWorkingTime(STORE_UUID_WITH_DIFFERENT_TIMEZONE, openingDate, closingDate, "00:00:00");
        RedisService.del(RedisManager.getConnection(Redis.ETA), String.format("store_%s", STORE_UUID_WITH_DIFFERENT_TIMEZONE));

        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID_WITH_DIFFERENT_TIMEZONE, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @TmsLink("59")
    @Story("Basket ETA")
    @Test(description = "Получение ответа от ML",
            groups = "ondemand-eta")
    public void getBasketEtaWithML() {
        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID_WITH_ML, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.ML);
    }

    @TmsLink("37")
    @Story("Basket ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае, если ML возвращает ноль",
            groups = "ondemand-eta")
    public void getBasketEtaWithZeroML() {
        var request = getUserEtaRequest(USER_UUID, 55.7010f, 37.7280f, STORE_UUID_UNKNOWN_FOR_ML, 55.7010f, 37.7280f, ORDER_UUID, SHIPMENT_UUID);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, ORDER_UUID, SHIPMENT_UUID, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }
}
