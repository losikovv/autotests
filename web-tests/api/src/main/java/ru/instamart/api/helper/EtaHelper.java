package ru.instamart.api.helper;

import eta.Eta;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.eta.RetailerParametersEtaRequest;
import ru.instamart.api.request.eta.ServiceParametersEtaRequest;
import ru.instamart.api.request.eta.StoreParametersEtaRequest;
import ru.instamart.api.response.eta.RetailerParametersEtaResponse;
import ru.instamart.api.response.eta.ServiceParametersEtaResponse;
import ru.instamart.api.response.eta.StoreParametersEtaResponse;
import ru.instamart.jdbc.dao.eta.ServiceParametersDao;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import ru.instamart.jdbc.dao.stf.SpreeUsersDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;

import java.time.LocalTime;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.kraken.util.TimeUtil.convertStringToTime;

public class EtaHelper {

    @Step("Проверяем установленный таймаут и уменьшаем его при необходимости")
    public static boolean checkMLTimeout(String mlTimeout) {
        LocalTime waitMlTimeoutFromDb = convertStringToTime(mlTimeout);
        LocalTime expectedWaitMlTimeout = convertStringToTime("00:00:00.5");
        if (waitMlTimeoutFromDb.isAfter(expectedWaitMlTimeout)) {
            ServiceParametersDao.INSTANCE.updateWaitMlTimeout("00:00:00.4");
            return true;
        } else return false;
    }

    @Step("Получаем запрос для user ETA")
    public static Eta.StoreUserEtaRequest getStoreUserEtaRequest(String storeUuid, float lat, float lon) {
        return Eta.StoreUserEtaRequest.newBuilder()
                .addStoreUuids(storeUuid)
                .setLat(lat)
                .setLon(lon)
                .build();
    }

    @Step("Получаем запрос для basket ETA")
    public static Eta.UserEtaRequest getUserEtaRequest(AddressV2 address, OrderV2 order, UserData userData, String shipmentUuid, String storeUuid) {
        return Eta.UserEtaRequest.newBuilder()
                .setUser(Eta.UserData.newBuilder()
                        .setUserUuid(SpreeUsersDao.INSTANCE.getEmailByLogin(userData.getEmail()))
                        .setLat(StartPointsTenants.ETA.getLat().floatValue())
                        .setLon(StartPointsTenants.ETA.getLon().floatValue())
                        .build())
                .setOrder(Eta.OrderData.newBuilder()
                        .setOrderUuid(order.getUuid())
                        .addShipments(Eta.ShipmentData.newBuilder()
                                .setShipmentUuid(shipmentUuid)
                                .setStoreInfo(Eta.StoreData.newBuilder()
                                        .setStoreUuid(storeUuid)
                                        .setLat(address.getLat().floatValue())
                                        .setLon(address.getLon().floatValue())
                                        .build())
                                .setBasket(Eta.UserStoreBasket.newBuilder()
                                        .setWeight(order.getShipments().get(0).getTotalWeight())
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku(order.getShipments().get(0).getLineItems().get(0).getProduct().getSku())
                                                .setUnitQuantity(order.getShipments().get(0).getLineItems().get(0).getUnitQuantity().floatValue())
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();
    }

    @Step("Получаем недоступный магазин из БД с обновленным режимом работы")
    public static StoresEntity getStoreWithUpdatedSchedule(String openingTime, String closingTime, int delta, String closingDelta) {
        StoresEntity store = StoresDao.INSTANCE.getUnavailableStore();
        StoresDao.INSTANCE.updateOnDemandStore(store.getId(), openingTime, closingTime, delta);
        StoreParametersDao.INSTANCE.updateStoreParameters(store.getUuid(), openingTime, closingTime, closingDelta);
        return store;
    }

    @Step("Изменяем настройки ETA сервиса")
    public static void updateServiceParameters(ServiceParametersEtaResponse serviceParameters) {
        final Response response = ServiceParametersEtaRequest.PUT(serviceParameters);
        checkStatusCode(response, 200, "");
    }

    @Step("Изменяем настройки ритейлера")
    public static void updateRetailerParameters(String retailerId, RetailerParametersEtaResponse retailerParameters) {
        final Response response = RetailerParametersEtaRequest.PUT(retailerId, retailerParameters);
        checkStatusCode(response, 200, "");
    }

    @Step("Изменяем настройки магазина")
    public static void updateStoreParameters(String storeId, StoreParametersEtaResponse storeParameters) {
        final Response response = StoreParametersEtaRequest.PUT(storeId, storeParameters);
        checkStatusCode(response, 200, "");
    }
}
