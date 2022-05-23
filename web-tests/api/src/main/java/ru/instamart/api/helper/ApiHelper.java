package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import ru.instamart.api.enums.v1.ImportStatusV1;
import ru.instamart.api.model.shopper.app.ShipmentSHP;
import ru.instamart.api.model.v1.DeliveryWindowV1;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.model.v1.RetailerV1;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.DeliveryWindowV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.SessionV2;
import ru.instamart.api.request.admin.CitiesAdminRequest;
import ru.instamart.api.request.admin.PagesAdminRequest;
import ru.instamart.api.request.admin.ShippingMethodsRequest;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.api.request.v1.RetailersV1Request;
import ru.instamart.api.request.v1.ShippingMethodsV1Request;
import ru.instamart.api.request.v1.admin.ShipmentsAdminV1Request;
import ru.instamart.api.request.v2.CreditCardsV2Request.CreditCard;
import ru.instamart.api.request.v2.CurrentTimeV2Request;
import ru.instamart.api.request.v2.PaymentsV2Request;
import ru.instamart.api.response.v1.PricersV1Response;
import ru.instamart.api.response.v1.ShippingMethodsResponse;
import ru.instamart.api.response.v1.admin.ShipmentsAdminV1Response;
import ru.instamart.api.response.v2.CreditCardAuthorizationV2Response;
import ru.instamart.api.response.v2.CurrentTimeV2Response;
import ru.instamart.jdbc.dao.shopper.OperationalZonesShopperDao;
import ru.instamart.jdbc.dao.shopper.RetailersShopperDao;
import ru.instamart.jdbc.dao.stf.*;
import ru.instamart.jdbc.entity.stf.StoresEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.PaymentCardData;
import ru.instamart.kraken.data.StaticPageData;
import ru.instamart.kraken.data.StoreLabelData;
import ru.instamart.kraken.data.StoreZonesCoordinates;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.CryptCard;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.kraken.util.TimeUtil;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.request.admin.StoresAdminRequest.getStoreForRetailerTests;
import static ru.instamart.kraken.data.user.UserRoles.B2B_MANAGER;
import static ru.sbermarket.common.FileUtils.changeXlsFileSheetName;

@Slf4j
public final class ApiHelper {

    private final ApiV2Helper apiV2 = new ApiV2Helper();
    private final AdminHelper admin = new AdminHelper();
    private final ShopperAppApiHelper shopperApp = new ShopperAppApiHelper();

    @Step("Подтверждение кода с помощью API")
    public SessionV2 confirmPhone(final String phone, final String code, final boolean promoTermsAccepted) {
        return apiV2.confirmPhone(phone, code, promoTermsAccepted);
    }

    @Step("Добавить {count} продукт в список избранного")
    public void addFavorites(final UserData userData, final int sid, final int count) {
        apiV2.authByPhone(userData);
        apiV2.addFavoritesListProductBySid(sid, count);
    }

    @Step("Добавить распроданный товар в избранное")
    public void addSoldProductToFavorite(final UserData user) {
        apiV2.authByPhone(user);
        apiV2.addFavoriteByProductId(OffersDao.INSTANCE.getSoldProduct());
    }

    @Step("Наполняем корзину избранным товаром с помощью API")
    public void dropAndFillCartFromFavorites(final UserData userData, final int sid) {
        apiV2.authByPhone(userData);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(userData, apiV2.getAddressBySid(sid));
        var products = apiV2.getProductsFromFavorites(sid);
        apiV2.fillCart(products);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Очищаем корзину с помощью API")
    public OrderV2 dropCart(final UserData user) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        return apiV2.deleteAllShipments();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCart(apiV2.getProductFromEachDepartmentOnMainPage(sid));
    }

    @Step("Удаляем ритейлера: '{retailerName}'")
    public void deleteRetailerWithStores(final String retailerName) {
        SpreeRetailersCleanDao.INSTANCE.deleteRetailerWithStores(retailerName);
    }

    /**
     * Указание адреса apiV2.setAddressAttributes()
     * перезатирает данные для пользователя который оформлял заказ и указывал параметры адреса доставки(этаж, номер квартиры и тп)
     * что ломает некоторые кейсы
     */
    @Step("Наполняем корзину с помощью API без указания адреса")
    public void dropAndFillCartWithoutSetAddress(final UserData user, final Integer sid) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.fillCart(apiV2.getProducts(sid));
    }

    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCartMultiple(final UserData user, AddressV2 address, final Integer firstShopSid, final Integer secondShopSid) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCart(apiV2.getProducts(firstShopSid));
        apiV2.fillCart(apiV2.getProducts(secondShopSid));
    }

    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCartMultiple(final UserData user, AddressV2 address, final Integer firstShopSid, final Integer firstShopItemCount, final Integer secondShopSid, final Integer secondShopItemCount) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCart(apiV2.getProducts(firstShopSid), firstShopItemCount);
        apiV2.fillCart(apiV2.getProducts(secondShopSid), secondShopItemCount);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid, final Integer itemsNumber) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCart(apiV2.getProducts(sid), itemsNumber);
    }

    /**
     * На стейдже работать не будет для {@link ru.instamart.api.common.RestAddresses}, так как нет большей части магазинов
     */
    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid, final AddressV2 address) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCart(apiV2.getProducts(sid));
    }

    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final String retailerName, final AddressV2 address) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCart(apiV2.getProductFromEachDepartmentOnMainPage(apiV2.getCurrentStore(address, retailerName).getId()));
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Наполняем корзину с помощью API")
    public void addItemsToCart(final UserData user, final Integer sid, final List<Long> productIDs) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.addItemsToCart(productIDs);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Указываем адрес с помощью API")
    public AddressV2 setAddress(final UserData user, AddressV2 address) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        return apiV2.setAddressAttributes(user, address);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Указываем адрес с помощью API")
    public AddressV2 setAddressBySid(final UserData user, final Integer sid) {
        apiV2.authByPhone(user);
        apiV2.getCurrentOrderNumber();
        return apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
    }

    @Step("Добавить карту '{1}' пользователю '{0}'")
    public void addCreditCard(final UserData user, final CreditCard creditCard) {
        apiV2.authByPhone(user);
        apiV2.addCreditCard(creditCard);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Оформляем заказ с помощью API")
    public OrderV2 makeOrder(final UserData user, final Integer sid, final Integer itemsNumber) {
        return makeOrder(user, sid, itemsNumber, apiV2.getAddressBySid(sid));
    }

    public OrderV2 makeOrder(final UserData user, final int sid, final int itemsNumber, final AddressV2 address) {
        apiV2.authByPhone(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, address);
        apiV2.fillCart(apiV2.getProducts(sid), itemsNumber);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod(sid);
        apiV2.getAvailableDeliveryWindow();

        apiV2.setDefaultOrderAttributes();
        return apiV2.completeOrder();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    public OrderV2 makeOrderWithComment(final UserData user, final int sid, final String comment) {
        var address = apiV2.getAddressBySid(sid);

        apiV2.authByPhone(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCart(apiV2.getProducts(sid), 1);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod(sid);
        apiV2.getAvailableDeliveryWindow();

        apiV2.setDefaultOrderAttributes(comment, "7" + user.getPhone());
        return apiV2.completeOrder();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Оформляем мульти заказ с помощью API")
    public void makeMultipleOrder(final UserData user, AddressV2 address, final Integer sid, final Integer sid2) {
        apiV2.authByPhone(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, address);

        apiV2.fillCart(apiV2.getProducts(sid));

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod(sid);
        apiV2.getAvailableDeliveryWindow();

        apiV2.setDefaultOrderAttributes();

        apiV2.fillCart(apiV2.getProducts(sid2));

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod(sid2);
        apiV2.getAvailableDeliveryWindow();

        apiV2.setDefaultOrderAttributes();
        apiV2.completeOrder();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Оформляем заказ с помощью API на завтра")
    public void makeOrderOnTomorrow(final UserData user, final Integer sid, final Integer itemsNumber) {
        apiV2.authByPhone(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCart(apiV2.getProducts(sid), itemsNumber);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod();
        apiV2.getAvailableDeliveryWindowOnTomorrow();

        apiV2.setDefaultOrderAttributes();
        apiV2.completeOrder();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Оформляем заказ ON_DEMAND с помощью API")
    public void makeOnDemandOrder(final UserData user, final Integer sid, final Integer itemsNumber) {
        apiV2.authByPhone(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCart(apiV2.getProducts(sid), itemsNumber);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod();
        apiV2.getAvailableDeliveryWindowOnDemand();

        apiV2.setDefaultOrderAttributesOnDemand();
    }

    public void makeAndCancelOrder(final UserData user, final Integer sid, final Integer itemsNumber) {
        makeAndCancelOrder(user, sid, itemsNumber, apiV2.getAddressBySidMy(sid));
    }

    @Step("Оформляем и отменяем заказ с выбранным адресом {address} при помощи API")
    public void makeAndCancelOrder(final UserData user, final int sid, final int itemsNumber, final AddressV2 address) {
        final var order = makeOrder(user, sid, itemsNumber, address);
        apiV2.cancelOrder(order.getNumber());
    }

    public void makeAndCompleteOrder(final UserData user, final int sid, final int itemsNumber) {
        final var shipment = makeOrder(user, sid, itemsNumber);
        final var order = SpreeOrdersDao.INSTANCE.getOrderByShipment(shipment.getShipments().get(0).getNumber());
        SpreeOrdersDao.INSTANCE.updateShipmentStateToShip(order.getNumber(), TimeUtil.getDbDate());
    }

    @Step("Отменяем заказ с помощью API")
    public void cancelOrder(final UserData user, final String orderNumber) {
        apiV2.authByPhone(user);
        apiV2.cancelOrder(orderNumber);
    }

    @Step("Отменяем все заказы с помощью API")
    public void cancelAllActiveOrders(final UserData userData) {
        apiV2.authByPhone(userData);
        apiV2.cancelActiveOrders();
    }

    @Step("Добавляем новый город {cityName} в админке")
    public void createCityInAdmin(String cityName) {
        admin.auth();
        admin.createCity(new CitiesAdminRequest.City(cityName));
    }

    @Step("Удаляем город {cityName} в админке")
    public void deleteCityInAdmin(String cityName) {
        admin.auth();
        admin.deleteCity(cityName);
    }

    @Step("Добавить компанию {inn}/{companyName} пользователю {ownerEmail}")
    public void addCompanyForUser(final String inn, final String companyName, final String ownerEmail) {
        admin.authApi();
        admin.addCompany(inn, companyName, ownerEmail);
    }

    @Step("Добавляем роль менеджера пользователю '{userData}'")
    public void addManagerRoleToUser(UserData userData) {
        SpreeUsersDao.INSTANCE.addRoleToUser(Integer.parseInt(userData.getId()), B2B_MANAGER.getId());
    }

    @Step("Добавляем менеджера '{userData}' компании '{inn}'")
    public void addManagerForCompany(String inn, UserData userData) {
        admin.authApi();
        addManagerRoleToUser(userData);
        int companyId = admin.getCompanies(inn).get(0).getId();
        admin.addManager(companyId, userData.getId());
    }


    @Step("Добавить сотрудника {userData} в компанию {inn}")
    public void addEmployeeForCompany(final String inn, UserData userData) {
        admin.authApi();
        int companyId = admin.getCompanies(inn).get(0).getId();
        admin.addEmployee(companyId, userData.getId());
    }

    @Step("Добавить сотрудников в компанию {inn}")
    public void addEmployeesForCompany(final String inn, List<UserData> usersData) {
        usersData.forEach(userData -> addEmployeeForCompany(inn, userData));
    }

    @Step("Добавляем новую статичную страницу {data} в админке")
    public void createStaticPageInAdmin(StaticPageData data) {
        PagesAdminRequest.Page page = PagesAdminRequest.Page.builder()
                .slug(data.getPageURL())
                .body(data.getDescription())
                .title(data.getPageName())
                .metaDescription(data.getText())
                .metaKeywords(data.getText())
                .metaTitle(data.getText())
                .foreignLink(data.getPageURL())
                .position(Integer.parseInt(data.getPosition()))
                .build();
        admin.auth();
        admin.createStaticPage(page);
    }

    @Step("Удаляем статичную страницу {pageId} в админке")
    public void deleteStaticPageInAdmin(Long pageId) {
        admin.auth();
        admin.deleteStaticPage(pageId);
    }

    @Step("Добавляем новый регион {zoneName} для магазина в админке")
    public OperationalZoneV1 createOperationalZonesInAdmin(String zoneName) {
        admin.authApi();
        return admin.addOperationalZones(zoneName);
    }

    @Step("Удаляем регион {zoneName} для магазина в админке")
    public void deleteOperationalZonesInAdmin(String zoneName) {
        OperationalZonesDao.INSTANCE.deleteZoneByName(zoneName);
    }

    @Step("Удаляем регион {zoneName} для магазина из шоппера")
    public void deleteOperationalZonesInShopper(String zoneName) {
        OperationalZonesShopperDao.INSTANCE.deleteZoneByName(zoneName);
    }

    @Step("Получение реферального промокода для пользователя")
    public String getReferralPromotionCode(final UserData user) {
        apiV2.authByPhone(user);
        String userId = apiV2.getProfile().getUser().getId();
        return apiV2.getReferralPromotionCode(userId);
    }

    @Step("Создание промокода")
    public void createPromotionCode(String value, Integer promotionId,
                                    String createdAt, String updatedAt, Integer usageLimit) {
        PromotionCodesDao.INSTANCE.createPromoCode(value, promotionId, createdAt, updatedAt, usageLimit);
    }

    @Step("Получить список доступных методов доставки")
    public List<ShippingMethodsResponse.ShippingMethods> getShippingMethod() {
        admin.authApi();
        return admin.getShippingMethods();
    }

    @Step("Создание нового способа доставки {0}")
    public void createShippingMethod(final ShippingMethodsRequest.ShippingMethod shippingMethod) {
        admin.auth();
        admin.createShippingMethod(shippingMethod);
    }

    @Step("Получить список маркетинговых правил доставки для метода {0}")
    public List<PricersV1Response.Pricer> getMarketingRule(final int methodId) {
        admin.authApi();
        return admin.getMarketingRule(methodId);
    }

    @Step("Создание нового маркетингово правила для доставки {0}")
    public PricersV1Response.Pricer createMarketingRule(final int id) {
        admin.authApi();
        return admin.createMarketingRule(id);
    }

    @Step("Удаление нового маркетингово правила для доставки {0}")
    public void deleteMarketingRule(final int id) {
        admin.authApi();
        admin.deleteMarketingRule(id);
    }

    @Step("Получить список номинальных правил доставки для метода {0}")
    public List<PricersV1Response.Pricer> getNominalRule(final int methodId) {
        admin.authApi();
        return admin.getNominalRule(methodId);
    }

    @Step("Создание нового номинального правила для доставки {0}")
    public PricersV1Response.Pricer createNominalRule(final int id) {
        admin.authApi();
        return admin.createNominalRule(id);
    }

    @Step("Удаление нового номинального правила для доставки {0}")
    public void deleteNominalRule(final int id) {
        admin.authApi();
        admin.deleteNominalRule(id);
    }

    @Step("Создать правило {0} данными {1}")
    public void createPricerRules(final int ruleId, final ShippingMethodsV1Request.Rules data) {
        admin.authApi();
        admin.createPricerRule(ruleId, data);
    }

    @Step("Создать калькулятор {0} данными {1}")
    public void createPricerCalculator(final int ruleId, final ShippingMethodsV1Request.Calculators data) {
        admin.authApi();
        admin.createPricerCalculator(ruleId, data);
    }

    @Step("Обновить правило {0} данными {1}")
    public void updateRules(final int ruleId, final ShippingMethodsV1Request.Rules data) {
        admin.authApi();
        admin.updateRule(ruleId, data);
    }

    @Step("Обновить калькулятор {0} данными {1}")
    public void updateCalculator(final int ruleId, final ShippingMethodsV1Request.Calculators data) {
        admin.authApi();
        admin.updateCalculator(ruleId, data);
    }

    @Step("Получить данные по заказам {0}")
    public List<ShipmentsAdminV1Response.Shipment> getShipments(final ShipmentsAdminV1Request.ShipmentsData shipmentsData) {
        admin.authApi();
        return admin.getShipments(shipmentsData);
    }

    @Step("Получаем первый доступный слот ON_DEMAND")
    public DeliveryWindowV2 getAvailableDeliveryWindowOnDemand(final UserData user, final Integer sid) {
        apiV2.authByPhone(user);

        apiV2.getCurrentOrderNumber();
        //apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCart(apiV2.getProducts(sid));

        //apiV2.getAvailablePaymentTool();
        //apiV2.getAvailableShippingMethod();
        return apiV2.getAvailableDeliveryWindowOnDemand();
    }

    public void updateStore(final int storeId, final String availabilityDate) {
        StoresDao.INSTANCE.updateWithSetAvailability(storeId, availabilityDate);
    }

    @Step("Получаем Id компании по ИНН: {inn}")
    public int getCompanyId(final String inn) {
        admin.authApi();
        return admin.getCompanies(inn).get(0).getId();
    }

    @Step("Добавляем для компании Id: {companyId} счёт с балансом: {balance}")
    public void addPaymentAccountForCompany(Integer companyId, Integer balance) {
        CompanyPaymentAccountsDao.INSTANCE.createCompanyAccount(companyId, balance);
    }

    @Step("Устанавливаем на счёте компании с Id: {companyId} сумму: {balance}")
    public void setPaymentAccountBalance(Integer companyId, Integer balance) {
        CompanyPaymentAccountsDao.INSTANCE.updateBalance(companyId, balance);
    }

    @Step("Добавляем нового ретейлера {retailerName} в админке")
    public RetailerV1 createRetailerInAdmin(String retailerName) {
        admin.authApi();
        RetailersV1Request.Retailer retailer = RetailersV1Request.getRetailer();
        retailer.setName(retailerName);
        retailer.setSlug(retailerName + "_slug");
        return admin.createRetailer(retailer);
    }

    @Step("Удаляем ретейлера {retailerName} из шоппера")
    public void deleteRetailerInShopper(String retailerName) {
        RetailersShopperDao.INSTANCE.deleteRetailerByNameFromShopper(retailerName);
    }

    @Step("Удаляем ретейлера c id {retailerId} из админки")
    public void deleteRetailerByIdInAdmin(Long retailerId) {
        SpreeRetailersDao.INSTANCE.delete(retailerId);
    }

    @Step("Удаляем ретейлера c именем {retailerName} из админки")
    public void deleteRetailerByNameInAdmin(final String retailerName) {
        SpreeRetailersDao.INSTANCE.deleteRetailerByName(retailerName);
    }

    @Step("Создаем магазин для ретейлера {retailerName} в городе {city}")
    public StoresAdminRequest.Store createStoreInAdmin(String retailerName, String city) {
        admin.auth();
        StoresAdminRequest.Store store = getStoreForRetailerTests(retailerName, city);
        admin.createStore(store);
        return store;
    }

    @Step("Настройка города для привязки магазинов")
    public void setupCity(String city) {
        admin.auth();
        createCityInAdmin(city);
        createOperationalZonesInAdmin(city);
    }

    @Step("Настройка магазина для включения в админке")
    public void setupStoreForActivation(StoresAdminRequest.Store store) {
        importOffersInStore(store);
        importStoreZones(store);
        createScheduleMockup(store);
        createDeliveryZones(store);
    }

    @Step("Удаляем магазин из админки")
    public void deleteStoreInAdmin(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        Integer storeId = storeFromDb.getId();

        StoresDao.INSTANCE.delete(storeId);
        StoreConfigsDao.INSTANCE.deleteByStoreId(storeId);
        StoresTenantsDao.INSTANCE.deleteStoreTenantByStoreId(storeId);

        StoreZonesDao.INSTANCE.deleteStoreZoneByStoreId(storeId);

        PaymentMethodStoresDao.INSTANCE.deletePaymentMethodByStoreId(storeId);

        OffersDao.INSTANCE.deleteByStoreId(storeId);
        PricesDao.INSTANCE.deletePriceByStoreId(storeId);

        StoreSchedulesDao.INSTANCE.deleteByStoreId(storeId);
    }

    @Step("Импорт оффера для магазина")
    public void importOffersInStore(StoresAdminRequest.Store store) {
        admin.auth();
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        Integer storeId = storeFromDb.getId();
        StoresTenantsDao.INSTANCE.addStoreTenant(storeId, "sbermarket");
        String importKey = SpreeRetailersDao.INSTANCE.findById(storeFromDb.getRetailerId()).get().getKey() + "-" + store.getImportKeyPostFix();

        byte[] fileBytes = changeXlsFileSheetName("src/test/resources/data/offers.xlsx", importKey, 0);
        admin.importOffers(fileBytes);

        int count = 0;
        String status = null;
        while (count < 20) {
            status = admin.getOfferFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
    }

    @Step("Импорт зон магазина")
    public void importStoreZones(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        admin.auth();
        admin.importStoreZoneFile(storeFromDb.getId(), "src/test/resources/data/zone.kml");
        admin.importStoreZone(storeFromDb.getId(), StoreZonesCoordinates.testMoscowZoneCoordinates());
    }

    @Step("Создание шаблона расписания магазина")
    public void createScheduleMockup(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        admin.auth();
        admin.createStoreSchedule(storeFromDb.getUuid());
    }

    @Step("Создание зон доставки")
    public void createDeliveryZones(StoresAdminRequest.Store store) {
        Long deliveryWindowId = createAndGetAvailableDeliveryWindows(store).get(0).getId();
        admin.auth();
        admin.updateDeliveryWindowWithDefaultValues(deliveryWindowId);
    }

    @Step("Получение списка окон доставки")
    public List<DeliveryWindowV1> createAndGetAvailableDeliveryWindows(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        admin.auth();
        admin.createDeliveryWindow(storeFromDb.getId());

        return admin.getDeliveryWindows(storeFromDb.getId());
    }

    @Step("Получаем информацию о ближайшей возможной доставке из магазина SID = '{sid}' по адресу {address}")
    public String getNextDeliveryInfo(final int sid, final AddressV2 address) {
        return apiV2.getNextDeliveryInfo(sid, address);
    }

    @Step("Удаляем группу магазинов: '{storeLabelName}'")
    public void deleteStoreLabel(final String storeLabelName) {
        admin.authApi();
        admin.deleteStoreLabel(admin.getStoreLabelByName(storeLabelName).getId());
    }

    @Step("Добавляем группу магазинов: '{storeLabelData}'")
    public void createStoreLabel(final StoreLabelData storeLabelData) {
        admin.authApi();
        admin.createStoreLabel(storeLabelData);
    }

    @Step("Получаем заказы из шоппера с комментарием: {comment}")
    public ShipmentSHP.Data getShipmentByComment(String comment) {
        shopperApp.authorisation(UserManager.getDefaultShopper());
        comment = EnvironmentProperties.SERVER.equals("production") ? comment + RandomUtils.nextInt(1, 50) : comment;
        return shopperApp.getShipmentByComment(comment);
    }

    @Step("Получаем текущее время")
    public String getCurrentTimeResponse() {
        final Response responseTime = CurrentTimeV2Request.GET();
        checkStatusCode200(responseTime);
        CurrentTimeV2Response currentTimeV2Response = responseTime.as(CurrentTimeV2Response.class);
        return currentTimeV2Response.getCurrentTime();
    }

    @Step("Привязываем карту юзеру {user}")
    public void bindCardToUser(final UserData user, final int sid, PaymentCardData creditCard) {
        String uuid = user.getUuid();
        String pan = creditCard.getCardNumber();
        String cvv = creditCard.getCvvNumber();
        String expDate = "20" + creditCard.getExpiryYear() + creditCard.getExpiryMonth();
        String publicKey = EnvironmentProperties.PUBLIC_CRYPTO_KEY;

        apiV2.authByPhone(user);
        apiV2.fillingCartAndOrderAttributesWithoutCompletion(
                user,
                sid
        );
        OrderV2 openOrder = apiV2.getOpenOrder();

        final Response response = PaymentsV2Request.POST(openOrder.getNumber());
        checkStatusCode200(response);
        CreditCardAuthorizationV2Response creditCardAuthorizationV2Response = response.as(CreditCardAuthorizationV2Response.class);
        String transactionNumber = creditCardAuthorizationV2Response.getCreditCardAuthorization().getTransactionNumber();

        String resultString = getCurrentTimeResponse() + "/" + uuid + "/" + pan + "/" + cvv + "/" + expDate + "/" + transactionNumber;
        String encrypt = CryptCard.INSTANCE.encrypt(resultString, publicKey);

        final Response response2 = PaymentsV2Request.PUT(transactionNumber, encrypt);
        checkStatusCode200(response2);
    }

    @Step("Получаем номер активного заказа по его комментарию")
    public String getActiveOrderNumberWithComment(final String comment) {
        return SpreeOrdersDao.INSTANCE.getActiveOrderByComment(comment).getNumber();
    }
}
