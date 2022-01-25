package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ShippingMethodsV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.jdbc.dao.OffersDao;
import ru.instamart.jdbc.dao.SpreeOrdersDao;
import ru.instamart.jdbc.dao.SpreeProductsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkEmptyLossesWithOrder;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkLossesWithOrder;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

@Epic("ApiV2")
@Feature("Проверка потерь")
public class TransferMethodV2Test extends RestBase {

    private UserData userData;
    private LineItemV2 lineItem;
    private String currentOrderNumber;
    private AddressV2 addressDefaultSid;
    private Long offerIdDefaultSid;
    private AddressV2 addressDefaultMetroMoscowSid;
    private Long offerIdDefaultMetroMoscowSid;
    private AddressV2 addressPickupOnlySid;
    private Long offerIdPickupOnlySid;
    private Long alcoholOfferId;


    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        lineItem = apiV2.fillCart(userData, EnvironmentProperties.DEFAULT_SID).get(0);
        currentOrderNumber = apiV2.getOpenOrder().getNumber();
        addressDefaultSid = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_SID);
        offerIdDefaultSid = OffersDao.INSTANCE.getOfferByStoreId(EnvironmentProperties.DEFAULT_SID).getId();
        addressDefaultMetroMoscowSid = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        offerIdDefaultMetroMoscowSid = OffersDao.INSTANCE.getOfferByStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).getId();
        addressPickupOnlySid = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        offerIdPickupOnlySid = OffersDao.INSTANCE.getOfferByStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).getId();
        alcoholOfferId = SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
    }

    @CaseIDs(value = {@CaseId(1724), @CaseId(1725), @CaseId(1726)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesForCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeLossesForStoreWoPickupNoLosses(Integer storeId) {
        AddressV2 address = apiV2.getAddressBySidMy(storeId);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1727), @CaseId(1728)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesForCourierWithLosses",
            dataProviderClass = RestDataProvider.class,
            priority = 1)
    public void analyzeLossesForStoreWoPickupLosses(Integer storeId) {
        lineItem = apiV2.changeAddressAndAddItemToCart(addressDefaultSid, offerIdDefaultSid, 1);
        AddressV2 address = apiV2.getAddressBySidMy(storeId);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }


    @CaseIDs(value = {@CaseId(1734), @CaseId(1735)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "negativeStoresForPickupTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzeNegativePickupLossesForStoreWoPickup(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseIDs(value = {@CaseId(1736), @CaseId(1737)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesForPickupWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWoPickupNoLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultSid, offerIdDefaultSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseId(1738)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 1)
    public void analyzePickupLossesForStoreWoPickupAnotherRetailer() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultSid, offerIdDefaultSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseId(1721)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери c отсуствующим обязательным параметром")
    public void analyzeCourierLossesWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: address_params Для способа доставки курьером, требуется указать address_params");
    }

    @CaseId(1739)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери c отсуствующим обязательным параметром",
            priority = 2)
    public void analyzePickupLossesWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: pickup_store_id Для способа доставки самовывоз, требуется указать pickup_store_id");
    }

    @CaseIDs(value = {@CaseId(1729), @CaseId(1730), @CaseId(1731)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 3,
            dataProvider = "storesForCouriersWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeLossesForStoreWithPickupAndCourierNoLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        AddressV2 address = apiV2.getAddressBySidMy(storeId);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1732), @CaseId(1733)})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 3,
            dataProvider = "storesForCourierWithLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeLossesForStoreWithPickupAndCourierLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        AddressV2 address = apiV2.getAddressBySidMy(storeId);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1740), @CaseId(1741), @CaseId(1742)})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 4,
            dataProvider = "storesForPickupAndCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWithAllNoLossees(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseId(1743)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 4)
    public void analyzeNegativePickupLossesForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1744)
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз (другой ритейлер)",
            priority = 4)
    public void analyzePickupLossesForStoreWithAll() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseId(1722)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для несуществующей зоны",
            priority = 4)
    public void analyzeTransferForNotExistingZone() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .city("Норильск")
                .street("Московская")
                .building("17")
                .lat(69.353499)
                .lon(88.205530)
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1745), @CaseId(1746), @CaseId(1747)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 5,
            dataProvider = "storesForCouriersWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierPickupLossesForStoreWithAllNoLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodsV2.PICKUP.getMethod());
        AddressV2 address = apiV2.getAddressBySidMy(storeId);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1748), @CaseId(1749)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 5,
            dataProvider = "storesForCourierWithLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierPickupLossesForStoreWithAll(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodsV2.PICKUP.getMethod());
        AddressV2 address = apiV2.getAddressBySidMy(storeId);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1755), @CaseId(1756), @CaseId(1757)})
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 6,
            dataProvider = "storesForPickupAndCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupsLossesForStoreWithAll(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseId(1759)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз (другой ритейлер)",
            priority = 5)
    public void analyzePickupsLossesForStoreWithAll() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseId(1758)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 6)
    public void analyzeNegativePickupsLossesForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1723)
    @Story("Трансфер доставка-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для пустой корзины",
            priority = 7)
    public void analyzeLossesForEmptyCart() {
        apiV2.dropCart(userData,addressPickupOnlySid);
        currentOrderNumber = OrdersV2Request.POST().as(OrderV2Response.class).getOrder().getNumber();
        AddressV2 address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);

        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodsV2.PICKUP.getMethod());
    }

    @CaseIDs(value = {@CaseId(1753), @CaseId(1754)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 8,
            dataProvider = "storesForPickupCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierLossesForStoreWithСourierNoLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressPickupOnlySid, offerIdPickupOnlySid, 1);
        AddressV2 address = apiV2.getAddressBySidMy(storeId);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1750), @CaseId(1751), @CaseId(1752)})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 8,
            dataProvider = "storesForPickupCourierWithLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierLossesForStoreWithСourier(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressPickupOnlySid, offerIdPickupOnlySid, 1);
        AddressV2 address = apiV2.getAddressBySidMy(storeId);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1760), @CaseId(1761), @CaseId(1762)})
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 8,
            dataProvider = "storeForPickupsWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWithСourierNoLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressPickupOnlySid, offerIdPickupOnlySid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseId(1764)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступен только самовывоз (другой ритейлер)",
            priority = 8)
    public void analyzePickupLossesForStoreWithСourier() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressPickupOnlySid, offerIdPickupOnlySid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseId(1763)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 8)
    public void analyzeNegativePickupsLossesForStoreWithPickup() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @CaseId(1765)
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для пустой корзины",
            priority = 9)
    public void analyzePickupsTransferForEmptyCart() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID));
        currentOrderNumber = OrdersV2Request.POST().as(OrderV2Response.class).getOrder().getNumber();

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1768), @CaseId(1769), @CaseId(1770)})
    @Story("Трансфер доставка, алкоголь")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина c разными способами доставки",
            priority = 11,
            dataProvider = "storesDataForCourierAlcoholTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierLossesForAlcohol(Integer storeId) {
        apiV2.setAddressAttributes(addressDefaultMetroMoscowSid);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodsV2.PICKUP.getMethod());
        lineItem =  apiV2.addItemToCart(alcoholOfferId, 1);
        AddressV2 address = apiV2.getAddressBySidMy(storeId);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseId(1771)
    @Story("Трансфер доставка, алкоголь")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для несуществующего адреса",
            priority = 11)
    public void analyzeCourierTransferForAlcoholWoStore() {
        apiV2.setAddressAttributes(addressDefaultMetroMoscowSid);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodsV2.PICKUP.getMethod());
        lineItem =  apiV2.addItemToCart(alcoholOfferId, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .city("Норильск")
                .street("Московская")
                .building("17")
                .lat(69.353499)
                .lon(88.205530)
                .shippingMethod(ShippingMethodsV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseIDs(value = {@CaseId(1773), @CaseId(1774)})
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина c разными способами доставки",
            priority = 10,
            dataProvider = "storeForPickupAlcoholWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForAlcohol(Integer storeId) {
        apiV2.setAddressAttributes(addressDefaultMetroMoscowSid);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodsV2.PICKUP.getMethod());
        lineItem =  apiV2.addItemToCart(alcoholOfferId, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @CaseId(1775)
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина c разными способами доставки (другой ритейлер)",
            priority = 11)
    public void analyzePickupLossesForAlcohol() {
        apiV2.setAddressAttributes(addressDefaultMetroMoscowSid);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodsV2.PICKUP.getMethod());
        lineItem =  apiV2.addItemToCart(alcoholOfferId, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @CaseId(1772)
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {"api-instamart-regress"},
            description = "Проверяем потери для магазина без самовывоза",
            priority = 10)
    public void analyzePickupTransferForAlcoholWoPickup() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodsV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }
}
