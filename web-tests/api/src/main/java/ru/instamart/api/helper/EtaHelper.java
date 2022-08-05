package ru.instamart.api.helper;

import eta.Eta;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.request.eta.RetailerParametersEtaRequest;
import ru.instamart.api.request.eta.ServiceParametersEtaRequest;
import ru.instamart.api.request.eta.StoreParametersEtaRequest;
import ru.instamart.api.response.eta.RetailerParametersEtaResponse;
import ru.instamart.api.response.eta.ServiceParametersEtaResponse;
import ru.instamart.api.response.eta.StoreParametersEtaResponse;
import ru.instamart.jdbc.dao.eta.ServiceParametersDao;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.eta.StoreParametersEntity;
import ru.instamart.jdbc.entity.stf.StoresEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.kraken.util.TimeUtil.convertStringToTime;

public class EtaHelper {

    private static final List<Eta.EstimateSource> acceptableEstimateSource = List.of(Eta.EstimateSource.FALLBACK, Eta.EstimateSource.ML);

    @Step("Получаем запрос для user ETA")
    public static Eta.StoreUserEtaRequest getStoreUserEtaRequest(final String storeUuid, final float lat, final float lon) {
        return Eta.StoreUserEtaRequest.newBuilder()
                .addStoreUuids(storeUuid)
                .setLat(lat)
                .setLon(lon)
                .build();
    }

    @Step("Получаем запрос для basket ETA")
    public static Eta.UserEtaRequest getUserEtaRequest(final String userUuid, final float userLat, final float userLon, final String storeUuid, final float storeLat, final float storeLon, String orderUuid, String shipmentUuid) {
        return Eta.UserEtaRequest.newBuilder()
                .setUser(Eta.UserData.newBuilder()
                        .setUserUuid(userUuid)
                        .setLat(userLat)
                        .setLon(userLon)
                        .build())
                .setOrder(Eta.OrderData.newBuilder()
                        .setOrderUuid(orderUuid)
                        .addShipments(Eta.ShipmentData.newBuilder()
                                .setShipmentUuid(shipmentUuid)
                                .setStoreInfo(Eta.StoreData.newBuilder()
                                        .setStoreUuid(storeUuid)
                                        .setLat(storeLat)
                                        .setLon(storeLon)
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
    }

    @Step("Проверяем ETA магазина")
    public static void checkStoreEta(Eta.StoreUserEtaResponse response, String storeUuid, final int eta, String error, Eta.EstimateSource estimateSource) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(response.getData(0).getStoreUuid(), storeUuid, softAssert);
        compareTwoObjects(response.getData(0).getEstimateSource(), estimateSource, softAssert);
        softAssert.assertTrue(response.getData(0).getEta() > eta, error);
        softAssert.assertTrue(response.getData(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertAll();
    }

    @Step("Проверяем ETA нескольких магазинов")
    public static void checkMultipleStoreEta(Eta.StoreUserEtaResponse response, String storeUuid, String secondStoreUuid) {
        List<String> storesUuids = Stream.of(storeUuid, secondStoreUuid).sorted().collect(Collectors.toList());
        List<String> storeUuidsFromResponse = Stream.of(response.getData(0).getStoreUuid(), response.getData(1).getStoreUuid()).sorted().collect(Collectors.toList());

        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(response.getDataCount(), 2, softAssert);
        compareTwoObjects(storeUuidsFromResponse, storesUuids, softAssert);
        softAssert.assertTrue(acceptableEstimateSource.contains(response.getData(0).getEstimateSource()));
        softAssert.assertTrue(response.getData(0).getEta() > 300, "Поле eta меньше 300 секунд");
        softAssert.assertTrue(response.getData(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertTrue(acceptableEstimateSource.contains(response.getData(1).getEstimateSource()));
        softAssert.assertTrue(response.getData(1).getEta() > 300, "Поле eta меньше 300 секунд");
        softAssert.assertTrue(response.getData(1).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertAll();
    }

    @Step("Проверяем ETA корзины")
    public static void checkBasketEta(Eta.UserEtaResponse response, String orderUuid, String shipmentUuid, Integer eta, String error, Eta.EstimateSource estimateSource) {
        assertTrue(response.getOrder().getShipmentEtasCount() > 0, "В ответе нет ЕТА для заказа");

        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(response.getOrder().getOrderUuid(), orderUuid, softAssert);
        compareTwoObjects(response.getOrder().getShipmentEtas(0).getShipmentUuid(), shipmentUuid, softAssert);
        compareTwoObjects(response.getOrder().getShipmentEtas(0).getEstimateSource(), estimateSource, softAssert);
        softAssert.assertTrue(response.getOrder().getShipmentEtas(0).getEta() > eta, error);
        softAssert.assertTrue(response.getOrder().getShipmentEtas(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertAll();
    }

    @Step("Проверяем установленный таймаут и уменьшаем его при необходимости")
    public static boolean checkMLTimeout(String mlTimeout) {
        LocalTime waitMlTimeoutFromDb = convertStringToTime(mlTimeout);
        LocalTime expectedWaitMlTimeout = convertStringToTime("00:00:00.0");
        if (waitMlTimeoutFromDb.isAfter(expectedWaitMlTimeout)) {
            ServiceParametersDao.INSTANCE.updateWaitMlTimeout("00:00:00.0");
            return true;
        } else return false;
    }

    @Step("Добавляем магазин в БД")
    public static void addStore(final String storeUuid, final Float lat, final Float lon, final String timezone, final Boolean isMlEnabled, final String openingTime, final String closingTime, final String closingDelta, final Boolean isSigmaEnabled) {
        boolean isStoreAdded = StoreParametersDao.INSTANCE.addStore(storeUuid, lat, lon, timezone, isMlEnabled, openingTime, closingTime, closingDelta, isSigmaEnabled);
        Allure.step("Проверяем что магазин добавился", () -> assertTrue(isStoreAdded, "Не удалось добавить магазин в БД"));
    }

    @Step("Обновляем время работы магазина")
    public static void updateStoreWorkingTime(final String storeUuid, final String openingDate, final String closingDate, final String closingDelta) {
        var state = StoreParametersDao.INSTANCE.updateStoreWorkingTime(storeUuid, openingDate, closingDate, closingDelta);
        assertTrue(state, "Не удалось обновить магазин");
    }

    @Step("Обновляем статус ML магазина")
    public static void updateStoreMLStatus(final String storeUuid, final Boolean isMlEnabled) {
        var state = StoreParametersDao.INSTANCE.updateStoreMLStatus(storeUuid, isMlEnabled);
        assertTrue(state, "Не удалось обновить магазин");
    }

    @Step("Изменяем настройки ETA сервиса")
    public static void updateServiceParameters(ServiceParametersEtaResponse serviceParameters) {
        final Response response = ServiceParametersEtaRequest.PUT(serviceParameters);
        checkStatusCode(response, 200);
    }

    @Step("Изменяем настройки ритейлера")
    public static void updateRetailerParameters(String retailerId, RetailerParametersEtaResponse retailerParameters) {
        final Response response = RetailerParametersEtaRequest.PUT(retailerId, retailerParameters);
        checkStatusCode(response, 200);
    }

    @Step("Изменяем настройки магазина")
    public static void updateStoreParameters(String storeId, StoreParametersEtaResponse storeParameters) {
        final Response response = StoreParametersEtaRequest.PUT(storeId, storeParameters);
        checkStatusCode(response, 200);
    }
}
