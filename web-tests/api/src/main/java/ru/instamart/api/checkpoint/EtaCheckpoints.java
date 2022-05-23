package ru.instamart.api.checkpoint;


import eta.Eta;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

public class EtaCheckpoints {

    private static final List<Eta.EstimateSource> acceptableEstimateSource = List.of(Eta.EstimateSource.FALLBACK, Eta.EstimateSource.ML);

    @Step("Проверяем ETA магазина")
    public static void checkStoreEta(Eta.StoreUserEtaResponse response, String storeUuid, int eta, String error, Eta.EstimateSource estimateSource) {
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
        softAssert.assertTrue(response.getData(0).getEta() > 0, "Поле eta меньше или равно нулю");
        softAssert.assertTrue(response.getData(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertTrue(acceptableEstimateSource.contains(response.getData(1).getEstimateSource()));
        softAssert.assertTrue(response.getData(1).getEta() > 0, "Поле eta меньше или равно нулю");
        softAssert.assertTrue(response.getData(1).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertAll();
    }

    @Step("Проверяем ETA корзины")
    public static void checkBasketEta(Eta.UserEtaResponse response, String orderUuid, String shipmentUuid, int eta, String error, Eta.EstimateSource estimateSource) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(response.getOrder().getOrderUuid(), orderUuid, softAssert);
        compareTwoObjects(response.getOrder().getShipmentEtas(0).getShipmentUuid(), shipmentUuid, softAssert);
        compareTwoObjects(response.getOrder().getShipmentEtas(0).getEstimateSource(), estimateSource, softAssert);
        softAssert.assertTrue(response.getOrder().getShipmentEtas(0).getEta() > eta, error);
        softAssert.assertTrue(response.getOrder().getShipmentEtas(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertAll();
    }
}
