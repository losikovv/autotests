package ru.instamart.test.api.dispatch.eta;

import eta.Eta;
import eta.PredEtaGrpc;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.eta.ServiceParametersDao;
import ru.instamart.jdbc.dao.stf.*;
import ru.instamart.jdbc.entity.eta.ServiceParametersEntity;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.EtaCheckpoints.checkBasketEta;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.api.request.admin.StoresAdminRequest.getStoreKaliningradTest;
import static ru.instamart.kraken.util.TimeUtil.getZoneDbDate;

@Epic("On Demand")
@Feature("ETA")
public class BasketEtaTest extends RestBase {

    private PredEtaGrpc.PredEtaBlockingStub clientEta;
    private AddressV2 address;
    private UserData userData;
    private OrderV2 order;
    private String storeUuid;
    private String shipmentUuid;
    private String secondStoreUuid;
    private ServiceParametersEntity serviceParameters;
    private boolean isMLTimeoutUpdated;
    private int storeId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientEta = PredEtaGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ETA));
        address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
        order = apiV2.getOpenOrder();
        storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).get().getUuid();
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        secondStoreUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_SID).get().getUuid();
        admin.auth();
    }

    @CaseIDs(value = {@CaseId(21), @CaseId(60)})
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с координатами клиента, соответствующими координатам магазина",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithStoreCoordinates() {
        var request = getUserEtaRequest(address, order, userData, shipmentUuid, order.getShipments().get(0).getStore().getUuid());

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, order.getUuid(), shipmentUuid, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(11)
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с валидными координатами пользователя",
            groups = "dispatch-eta-smoke")
    public void getBasketEta() {
        var request = getUserEtaRequest(address, order, userData, shipmentUuid, order.getShipments().get(0).getStore().getUuid());

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, order.getUuid(), shipmentUuid, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(64)
    @Story("Basket ETA")
    @Test(description = "Отправка запросов с различным количеством структур в массиве sku",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithFewSku() {
        var firstRequest = getUserEtaRequest(address, order, userData, shipmentUuid, order.getShipments().get(0).getStore().getUuid());

        var firstResponse = clientEta.getBasketEta(firstRequest);
        checkBasketEta(firstResponse, order.getUuid(), shipmentUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);

        var secondRequest = Eta.UserEtaRequest.newBuilder()
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
                                        .setStoreUuid(order.getShipments().get(0).getStore().getUuid())
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
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku(order.getShipments().get(0).getLineItems().get(0).getProduct().getSku())
                                                .setUnitQuantity(order.getShipments().get(0).getLineItems().get(0).getUnitQuantity().floatValue())
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        var secondResponse = clientEta.getBasketEta(secondRequest);
        final SoftAssert secondSoftAssert = new SoftAssert();
        compareTwoObjects(secondResponse.getOrder().getOrderUuid(), order.getUuid(), secondSoftAssert);
        compareTwoObjects(secondResponse.getOrder().getShipmentEtas(0).getShipmentUuid(), shipmentUuid, secondSoftAssert);
        compareTwoObjects(secondResponse.getOrder().getShipmentEtas(0).getEstimateSource(), Eta.EstimateSource.FALLBACK, secondSoftAssert);
        secondSoftAssert.assertTrue(secondResponse.getOrder().getShipmentEtas(0).getEta() > firstResponse.getOrder().getShipmentEtas(0).getEta(), "Поле eta второго запроса меньше или равно полю eta из первого");
        secondSoftAssert.assertAll();
    }

    @CaseId(22)
    @Story("Basket ETA")
    @Test(description = "Отправка запросов с различным значением в параметре unit_quantity",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithDifferentQuantities() {
        var firstRequest = getUserEtaRequest(address, order, userData, shipmentUuid, order.getShipments().get(0).getStore().getUuid());

        var firstResponse = clientEta.getBasketEta(firstRequest);
        checkBasketEta(firstResponse, order.getUuid(), shipmentUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);

        var secondRequest = Eta.UserEtaRequest.newBuilder()
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
                                        .setStoreUuid(order.getShipments().get(0).getStore().getUuid())
                                        .setLat(address.getLat().floatValue())
                                        .setLon(address.getLon().floatValue())
                                        .build())
                                .setBasket(Eta.UserStoreBasket.newBuilder()
                                        .setWeight(order.getShipments().get(0).getTotalWeight())
                                        .addSku(Eta.SkuData.newBuilder()
                                                .setSku(order.getShipments().get(0).getLineItems().get(0).getProduct().getSku())
                                                .setUnitQuantity(21f)
                                                .setPriceType(ProductPriceTypeV2.PER_ITEM.getValue())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        var secondResponse = clientEta.getBasketEta(secondRequest);
        final SoftAssert secondSoftAssert = new SoftAssert();
        compareTwoObjects(secondResponse.getOrder().getOrderUuid(), order.getUuid(), secondSoftAssert);
        compareTwoObjects(secondResponse.getOrder().getShipmentEtas(0).getShipmentUuid(), shipmentUuid, secondSoftAssert);
        compareTwoObjects(secondResponse.getOrder().getShipmentEtas(0).getEstimateSource(), Eta.EstimateSource.FALLBACK, secondSoftAssert);
        secondSoftAssert.assertTrue(secondResponse.getOrder().getShipmentEtas(0).getEta() > firstResponse.getOrder().getShipmentEtas(0).getEta(), "Поле eta второго запроса меньше или равно полю eta из первого");
        secondSoftAssert.assertAll();
    }

    @CaseId(31)
    @Story("Basket ETA")
    @Test(description = "Отправка запроса с двумя шипментами из двух разных магазинов",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaFromDifferentStores() {
        var request = Eta.UserEtaRequest.newBuilder()
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
                                        .setStoreUuid(order.getShipments().get(0).getStore().getUuid())
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
                        .addShipments(Eta.ShipmentData.newBuilder()
                                .setShipmentUuid(UUID.randomUUID().toString())
                                .setStoreInfo(Eta.StoreData.newBuilder()
                                        .setStoreUuid(secondStoreUuid)
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

        var response = clientEta.getBasketEta(request);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(response.getOrder().getOrderUuid(), order.getUuid(), softAssert);
        compareTwoObjects(response.getOrder().getShipmentEtasCount(), 2, softAssert);
        compareTwoObjects(response.getOrder().getShipmentEtas(0).getShipmentUuid(), shipmentUuid, softAssert);
        compareTwoObjects(response.getOrder().getShipmentEtas(0).getEstimateSource(), Eta.EstimateSource.FALLBACK, softAssert);
        softAssert.assertTrue(response.getOrder().getShipmentEtas(0).getEta() > 300, "Поле eta меньше 300 секунд");
        softAssert.assertTrue(response.getOrder().getShipmentEtas(0).getSigma() > 0, "Поле sigma меньше или равно нулю");
        compareTwoObjects(response.getOrder().getShipmentEtas(1).getShipmentUuid(), request.getOrder().getShipments(1).getShipmentUuid(), softAssert);
        compareTwoObjects(response.getOrder().getShipmentEtas(1).getEstimateSource(), Eta.EstimateSource.FALLBACK, softAssert);
        softAssert.assertTrue(response.getOrder().getShipmentEtas(1).getEta() > 300, "Поле eta меньше 300 секунд");
        softAssert.assertTrue(response.getOrder().getShipmentEtas(1).getSigma() > 0, "Поле sigma меньше или равно нулю");
        softAssert.assertAll();
    }

    @CaseId(45)
    @Story("Basket ETA")
    @Test(description = "Отправка валидного запроса в пределах работы параметра OnDemandClosingDelta",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaForClosedStore() {
        String openingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1)));
        String closingDate = getZoneDbDate(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        StoresEntity store = getStoreWithUpdatedSchedule(openingDate, closingDate, "00:30:00");
        var request = getUserEtaRequest(address, order, userData, shipmentUuid, store.getUuid());

        var response = clientEta.getBasketEta(request);
        compareTwoObjects(response.getOrder().getShipmentEtasCount(), 0);
    }

    @CaseId(55)
    @Story("Basket ETA")
    @Test(description = "Отправка валидного запроса в магазин, который в одном часовом поясе открыт, а другом закрыт",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaInDifferentTimezone() {
        StoresAdminRequest.Store store = getStoreKaliningradTest();
        admin.createStore(store);
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        checkFieldIsNotEmpty(storeFromDb, "магазин в БД");
        storeId = storeFromDb.getId();
        var request = getUserEtaRequest(address, order, userData, shipmentUuid, storeFromDb.getUuid());

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, order.getUuid(), shipmentUuid, 300, "Поле eta меньше 300 секунд", Eta.EstimateSource.FALLBACK);
    }

    @CaseId(37)
    @Story("Basket ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае, если ML возвращает ноль",
            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithZeroML() {
        apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_ON_DEMAND_SID);
        OrderV2 order = apiV2.getOpenOrder();
        String shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        var request = getUserEtaRequest(address, order, userData, shipmentUuid, UUID.randomUUID().toString());

        var response = clientEta.getBasketEta(request);
        Assert.assertTrue(response.toString().isEmpty(), "Пришел не пустой ответ");
    }

    @CaseId(61)
    @Story("Basket ETA")
    @Test(description = "Проверка, что рассчитывается фоллбэк, в случае, если ML не возвращает ответ по таймауту",            groups = "dispatch-eta-smoke")
    public void getBasketEtaWithMLTimeout() {
        String storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_ON_DEMAND_SID).get().getUuid();
        serviceParameters = ServiceParametersDao.INSTANCE.getServiceParameters();
        isMLTimeoutUpdated = checkMLTimeout(serviceParameters.getWaitMlTimeout());
        var request = getUserEtaRequest(address, order, userData, shipmentUuid, storeUuid);

        var response = clientEta.getBasketEta(request);
        checkBasketEta(response, order.getUuid(), shipmentUuid, 0, "Поле eta меньше или равно нулю", Eta.EstimateSource.FALLBACK);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if(isMLTimeoutUpdated) {
            ServiceParametersDao.INSTANCE.updateWaitMlTimeout(serviceParameters.getWaitMlTimeout());
        }
        if (Objects.nonNull(storeId)) {
            StoresDao.INSTANCE.delete(storeId);
            StoreConfigsDao.INSTANCE.deleteByStoreId(storeId);
            StoresTenantsDao.INSTANCE.deleteStoreTenantByStoreId(storeId);
            PaymentMethodStoresDao.INSTANCE.deletePaymentMethodByStoreId(storeId);
        }
    }
}
