package ru.instamart.api.checkpoint;


import eta.Eta;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

public class EtaCheckpoints {

    @Step("Проверяем ETA магазина")
    public static void checkStoreEta(Eta.StoreUserEtaResponse response, String storeUuid, int eta, String error) {
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(response.getData(0).getStoreUuid(), storeUuid, softAssert);
        compareTwoObjects(response.getData(0).getEstimateSource(), Eta.EstimateSource.FALLBACK, softAssert);
        softAssert.assertTrue(response.getData(0).getEta() > eta, error);
        softAssert.assertTrue(response.getData(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
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
