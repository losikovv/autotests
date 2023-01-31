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
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.LineItemV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.jdbc.dao.stf.OffersDao;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
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


    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        lineItem = apiV2.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID).get(0);
        currentOrderNumber = apiV2.createOrder().getNumber();
        addressDefaultSid = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_SID);
        offerIdDefaultSid = OffersDao.INSTANCE.getOfferByStoreId(EnvironmentProperties.DEFAULT_SID).getId();
        addressDefaultMetroMoscowSid = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        offerIdDefaultMetroMoscowSid = OffersDao.INSTANCE.getOfferByStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).getId();
        addressPickupOnlySid = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        offerIdPickupOnlySid = OffersDao.INSTANCE.getOfferByStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID).getId();
        alcoholOfferId = SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
    }

    @TmsLinks(value = {@TmsLink("1724"), @TmsLink("1725"), @TmsLink("1726")})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
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
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1727"), @TmsLink("1728")})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
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
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }


    @TmsLinks(value = {@TmsLink("1734"), @TmsLink("1735")})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "negativeStoresForPickupTransferMethodOnlyCourier",
            dataProviderClass = RestDataProvider.class)
    public void analyzeNegativePickupLossesForStoreWoPickup(Integer storeId) {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @TmsLinks(value = {@TmsLink("1736"), @TmsLink("1737")})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            dataProvider = "storesForPickupWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWoPickupNoLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultSid, offerIdDefaultSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLink("1738")
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 1)
    public void analyzePickupLossesForStoreWoPickupAnotherRetailer() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultSid, offerIdDefaultSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLink("1721")
    @Story("Трансфер доставка-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери c отсуствующим обязательным параметром")
    public void analyzeCourierLossesWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: address_params Для способа доставки курьером, требуется указать address_params");
    }

    @TmsLink("1739")
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери c отсуствующим обязательным параметром",
            priority = 2)
    public void analyzePickupLossesWoRequiredParams() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Недопустимые параметры запроса: pickup_store_id Для способа доставки самовывоз, требуется указать pickup_store_id");
    }

    @TmsLinks(value = {@TmsLink("1729"), @TmsLink("1730"), @TmsLink("1731")})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
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
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1732"), @TmsLink("1733")})
    @Story("Трансфер доставка-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
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
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1740"), @TmsLink("1741"), @TmsLink("1742")})
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 4,
            dataProvider = "storesForPickupAndCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWithAllNoLossees(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLink("1743")
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 4)
    public void analyzeNegativePickupLossesForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @TmsLink("1744")
    @Story("Трансфер доставка-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз (другой ритейлер)",
            priority = 4)
    public void analyzePickupLossesForStoreWithAll() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLink("1722")
    @Story("Трансфер доставка-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
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
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1745"), @TmsLink("1746"), @TmsLink("1747")})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 5,
            dataProvider = "storesForCouriersWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierPickupLossesForStoreWithAllNoLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        AddressV2 address = apiV2.getAddressBySidMy(storeId);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1748"), @TmsLink("1749")})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 5,
            dataProvider = "storesForCourierWithLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierPickupLossesForStoreWithAll(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        AddressV2 address = apiV2.getAddressBySidMy(storeId);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1755"), @TmsLink("1756"), @TmsLink("1757")})
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз",
            priority = 6,
            dataProvider = "storesForPickupAndCourierWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupsLossesForStoreWithAll(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLink("1759")
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступны и доставка, и самовывоз (другой ритейлер)",
            priority = 5)
    public void analyzePickupsLossesForStoreWithAll() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressDefaultMetroMoscowSid, offerIdDefaultMetroMoscowSid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLink("1758")
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 6)
    public void analyzeNegativePickupsLossesForStoreWithAll() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @TmsLink("1723")
    @Story("Трансфер доставка-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для пустой корзины",
            priority = 7)
    public void analyzeLossesForEmptyCart() {
        apiV2.dropCart(userData,addressPickupOnlySid);
        currentOrderNumber = apiV2.createOrder().getNumber();
        AddressV2 address = apiV2.getAddressBySidMy(EnvironmentProperties.DEFAULT_SID);

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);

        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
    }

    @TmsLinks(value = {@TmsLink("1753"), @TmsLink("1754")})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
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
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1750"), @TmsLink("1751"), @TmsLink("1752")})
    @Story("Трансфер самовывоз-доставка")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
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
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1760"), @TmsLink("1761"), @TmsLink("1762")})
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступен только самовывоз",
            priority = 8,
            dataProvider = "storeForPickupsWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupLossesForStoreWithСourierNoLosses(Integer storeId) {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressPickupOnlySid, offerIdPickupOnlySid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLink("1764")
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступен только самовывоз (другой ритейлер)",
            priority = 8)
    public void analyzePickupLossesForStoreWithСourier() {
        lineItem =  apiV2.changeAddressAndAddItemToCart(addressPickupOnlySid, offerIdPickupOnlySid, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLink("1763")
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина, где доступна только доставка",
            priority = 8)
    public void analyzeNegativePickupsLossesForStoreWithPickup() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }

    @TmsLink("1765")
    @Story("Трансфер самовывоз-самовывоз")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для пустой корзины",
            priority = 9)
    public void analyzePickupsTransferForEmptyCart() {
        apiV2.dropCart(userData, apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID));
        currentOrderNumber = apiV2.createOrder().getNumber();

        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1768"), @TmsLink("1769"), @TmsLink("1770")})
    @Story("Трансфер доставка, алкоголь")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина c разными способами доставки",
            priority = 11,
            dataProvider = "storesDataForCourierAlcoholTransferMethod",
            dataProviderClass = RestDataProvider.class)
    public void analyzeCourierLossesForAlcohol(Integer storeId) {
        apiV2.setAddressAttributes(addressDefaultMetroMoscowSid);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        lineItem =  apiV2.addItemToCart(alcoholOfferId, 1);
        AddressV2 address = apiV2.getAddressBySidMy(storeId);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .lat(address.getLat())
                .lon(address.getLon())
                .city(address.getCity())
                .street(address.getStreet())
                .building(address.getBuilding())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLink("1771")
    @Story("Трансфер доставка, алкоголь")
    @Test(enabled = false,
            groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для несуществующего адреса",
            priority = 11)
    public void analyzeCourierTransferForAlcoholWoStore() {
        apiV2.setAddressAttributes(addressDefaultMetroMoscowSid);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        lineItem =  apiV2.addItemToCart(alcoholOfferId, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .city("Норильск")
                .street("Московская")
                .building("17")
                .lat(69.353499)
                .lon(88.205530)
                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLinks(value = {@TmsLink("1773"), @TmsLink("1774")})
    @Story("Трансфер самовывоз, алкоголь")
    @Test(enabled = false,
            groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина c разными способами доставки",
            priority = 10,
            dataProvider = "storeForPickupAlcoholWithoutLosses",
            dataProviderClass = RestDataProvider.class)
    public void analyzePickupTransferForAlcohol(Integer storeId) {
        apiV2.setAddressAttributes(addressDefaultMetroMoscowSid);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        lineItem =  apiV2.addItemToCart(alcoholOfferId, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(storeId)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkEmptyLossesWithOrder(response, currentOrderNumber);
    }

    @TmsLink("1775")
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина c разными способами доставки (другой ритейлер)",
            priority = 11)
    public void analyzePickupLossesForAlcohol() {
        apiV2.setAddressAttributes(addressDefaultMetroMoscowSid);
        SpreeOrdersDao.INSTANCE.updateShippingKind(currentOrderNumber, ShippingMethodV2.PICKUP.getMethod());
        lineItem =  apiV2.addItemToCart(alcoholOfferId, 1);
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(89)
                .build(), currentOrderNumber);
        checkStatusCode200(response);
        checkLossesWithOrder(response, lineItem, currentOrderNumber);
    }

    @TmsLink("1772")
    @Story("Трансфер самовывоз, алкоголь")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Проверяем потери для магазина без самовывоза",
            priority = 10)
    public void analyzePickupTransferForAlcoholWoPickup() {
        final Response response = OrdersV2Request.TransferMethod.PUT(OrdersV2Request.TransferMethodParams.builder()
                .shippingMethod(ShippingMethodV2.PICKUP.getMethod())
                .pickupStoreId(EnvironmentProperties.DEFAULT_SID)
                .build(), currentOrderNumber);
        checkStatusCode422(response);
        checkError(response, "Самовывоз из магазина не работает. Пожалуйста, выберите другой");
    }
}
