package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.enums.v1.ImportStatusV1;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
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
import ru.instamart.api.request.v1.*;
import ru.instamart.api.request.v1.admin.ShipmentsAdminV1Request;
import ru.instamart.api.request.v1.b2b.CompaniesV1Request;
import ru.instamart.api.request.v2.CreditCardsV2Request.CreditCard;
import ru.instamart.api.response.v1.DeliveryWindowsV1Response;
import ru.instamart.api.response.v1.PricerV1Response;
import ru.instamart.api.response.v1.PricersV1Response;
import ru.instamart.api.response.v1.ShippingMethodsResponse;
import ru.instamart.api.response.v1.admin.ShipmentsAdminV1Response;
import ru.instamart.api.response.v1.b2b.CompaniesV1Response;
import ru.instamart.jdbc.dao.*;
import ru.instamart.jdbc.dao.shopper.OperationalZonesShopperDao;
import ru.instamart.jdbc.dao.shopper.RetailersShopperDao;
import ru.instamart.jdbc.entity.OffersEntity;
import ru.instamart.jdbc.entity.StoresEntity;
import ru.instamart.kraken.data.StaticPageData;
import ru.instamart.kraken.data.StoreZonesCoordinates;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.util.ThreadUtil;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.AdminHelper.getOfferFiles;
import static ru.instamart.api.helper.K8sHelper.changeToShip;
import static ru.instamart.api.request.admin.StoresAdminRequest.getStoreForRetailerTests;
import static ru.instamart.kraken.data.user.UserRoles.B2B_MANAGER;
import static ru.instamart.kraken.util.TimeUtil.getDbDeliveryDateFrom;
import static ru.instamart.kraken.util.TimeUtil.getDbDeliveryDateTo;
import static ru.sbermarket.common.FileUtils.changeXlsFileSheetName;

@Slf4j
public final class ApiHelper {

    private final InstamartApiHelper apiV2 = new InstamartApiHelper();
    private final AdminHelper admin = new AdminHelper();

    @Step("Подтверждение кода с помощью API")
    public SessionV2 confirmPhone(final String phone, final String code, final boolean promoTermsAccepted) {
        return apiV2.confirmPhone(phone, code, promoTermsAccepted);
    }

    @Step("Добавить {count} продукт в список избранного")
    public void addFavorites(final UserData userData, final int sid, final int count) {
        apiV2.auth(userData);
        apiV2.addFavoritesListProductBySid(sid, count);
    }

    @Step("Добавить распроданный товар в избранное")
    public void addSoldProductToFavorite(final UserData user) {
        apiV2.auth(user);
        apiV2.addFavoriteByProductId(OffersDao.INSTANCE.getSoldProduct());
    }

    @Step("Наполняем корзину избранным товаром с помощью API")
    public void dropAndFillCartFromFavorites(final UserData userData, final int sid) {
        apiV2.auth(userData);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(userData, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid, 1, true, ProductPriceTypeV2.PER_ITEM);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Очищаем корзину с помощью API")
    public OrderV2 dropCart(final UserData user) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        return apiV2.deleteAllShipments();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid);
    }

    /**
     * Указание адреса apiV2.setAddressAttributes()
     * перезатирает данные для пользователя который оформлял заказ и указывал параметры адреса доставки(этаж, номер квартиры и тп)
     * что ломает некоторые кейсы
     */
    @Step("Наполняем корзину с помощью API без указания адреса")
    public void dropAndFillCartWithoutSetAddress(final UserData user, final Integer sid) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.fillCartOnSid(sid);
    }

    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCartMultiple(final UserData user, AddressV2 address, final Integer firstShopSid, final Integer secondShopSid) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCartOnSid(firstShopSid);
        apiV2.fillCartOnSid(secondShopSid);
    }

    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCartMultiple(final UserData user, AddressV2 address, final Integer firstShopSid, final Integer firstShopItemCount, final Integer secondShopSid, final Integer secondShopItemCount) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCartOnSid(firstShopSid, firstShopItemCount);
        apiV2.fillCartOnSid(secondShopSid, secondShopItemCount);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid, final Integer itemsNumber) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid, itemsNumber);
    }

    /**
     * На стейдже работать не будет для {@link ru.instamart.api.common.RestAddresses}, так как нет большей части магазинов
     */
    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid, final AddressV2 address) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCartOnSid(sid);
    }

    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final String retailerName, final AddressV2 address) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCartOnSid(apiV2.getCurrentStore(address, retailerName).getId());
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Указываем адрес с помощью API")
    public AddressV2 setAddress(final UserData user, AddressV2 address) {
        apiV2.auth(user);
        apiV2.getCurrentOrderNumber();
        return apiV2.setAddressAttributes(user, address);
    }

    @Step("Добавить карту '{1}' пользователю '{0}'")
    public void addCreditCard(final UserData user, final CreditCard creditCard) {
        apiV2.auth(user);
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
        apiV2.auth(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, address);
        apiV2.fillCartOnSid(sid, itemsNumber);

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
    @Step("Оформляем мульти заказ с помощью API")
    public void makeMultipleOrder(final UserData user, AddressV2 address, final Integer sid, final Integer sid2) {
        apiV2.auth(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, address);

        apiV2.fillCartOnSid(sid);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod(sid);
        apiV2.getAvailableDeliveryWindow();

        apiV2.setDefaultOrderAttributes();

        apiV2.fillCartOnSid(sid2);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod(sid2);
        apiV2.getAvailableDeliveryWindow();

        apiV2.setDefaultOrderAttributes();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Оформляем заказ с помощью API на завтра")
    public void makeOrderOnTomorrow(final UserData user, final Integer sid, final Integer itemsNumber) {
        apiV2.auth(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid, itemsNumber);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod();
        apiV2.getAvailableDeliveryWindowOnTomorrow();

        apiV2.setDefaultOrderAttributes();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Оформляем заказ ON_DEMAND с помощью API")
    public void makeOnDemandOrder(final UserData user, final Integer sid, final Integer itemsNumber) {
        apiV2.auth(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid, itemsNumber);

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
        final var order = makeOrder(user, sid, itemsNumber);
        final var shipmentNumber = order.getShipments().get(0).getNumber();
        changeToShip(shipmentNumber);
    }

    @Step("Отменяем заказ с помощью API")
    public void cancelOrder(final UserData user, final String orderNumber) {
        apiV2.auth(user);
        apiV2.cancelOrder(orderNumber);
    }

    @Step("Отменяем все заказы с помощью API")
    public void cancelAllActiveOrders(final UserData userData) {
        apiV2.auth(userData);
        apiV2.cancelActiveOrders();
    }

    @Step("Добавляем новый город {cityName} в админке")
    public void createCityInAdmin(String cityName) {
        admin.authAdmin();
        admin.createCity(new CitiesAdminRequest.City(cityName));
    }

    @Step("Удаляем город {cityName} в админке")
    public void deleteCityInAdmin(String cityName) {
        admin.authAdmin();
        admin.deleteCity(cityName);
    }

    @Step("Добавить компанию {inn}/{companyName} пользователю {ownerEmail}")
    public void addCompanyForUser(final String inn, final String companyName, final String ownerEmail) {
        admin.authAdminApi();
        admin.addCompany(inn, companyName, ownerEmail);
    }

    @Step("Добавляем менеджера '{userData}' компании '{inn}'")
    public void addManagerForCompany(String inn, UserData userData) {
        admin.authAdminApi();
        K8sHelper.addRoleToUser(userData.getId(), B2B_MANAGER.getRole());
        Response response = CompaniesV1Request.GET(inn);
        checkStatusCode200(response);
        int companyId = response.as(CompaniesV1Response.class).getCompanies().get(0).getId();
        admin.addManager(companyId, userData.getId());
    }


    @Step("Добавить сотрудника {userData} в компанию {inn}")
    public void addEmployeeForCompany(final String inn, UserData userData) {
        admin.authAdminApi();
        Response response = CompaniesV1Request.GET(inn);
        checkStatusCode200(response);
        int companyId = response.as(CompaniesV1Response.class).getCompanies().get(0).getId();
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
        admin.authAdmin();
        admin.createStaticPage(page);
    }

    @Step("Удаляем статичную страницу {pageId} в админке")
    public void deleteStaticPageInAdmin(Long pageId) {
        admin.authAdmin();
        admin.deleteStaticPage(pageId);
    }

    @Step("Добавляем новый регион {zoneName} для магазина в админке")
    public OperationalZoneV1 createOperationalZonesInAdmin(String zoneName) {
        admin.authAdminApi();
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
        apiV2.auth(user);
        String userId = apiV2.getProfile().getUser().getId();
        return apiV2.getReferralPromotionCode(userId);
    }

    @Step("Создание промокода")
    public void createPromotionCode(String value, Integer promotionId,
                                    String createdAt, String updatedAt, Integer usageLimit) {
        PromotionCodesDao.INSTANCE.createPromoCode(value, promotionId, createdAt, updatedAt, usageLimit);
    }

    @Step("Получить список доступных методов доставки")
    public ShippingMethodsResponse getShippingMethod() {
        admin.authAdminApi();
        return admin.getShippingMethods();
    }

    @Step("Создание нового способа доставки {0}")
    public void createShippingMethod(final ShippingMethodsRequest.ShippingMethod shippingMethod) {
        admin.authAdmin();
        admin.createShippingMethod(shippingMethod);
    }

    @Step("Получить список маркетинговых правил доставки для метода {0}")
    public PricersV1Response getMarketingRule(final int methodId) {
        admin.authAdminApi();
        return admin.getMarketingRule(methodId);
    }

    @Step("Создание нового маркетингово правила для доставки {0}")
    public PricerV1Response createMarketingRule(final int id) {
        admin.authAdminApi();
        return admin.createMarketingRule(id);
    }

    @Step("Удаление нового маркетингово правила для доставки {0}")
    public void deleteMarketingRule(final int id) {
        admin.authAdminApi();
        admin.deleteMarketingRule(id);
    }

    @Step("Получить список номинальных правил доставки для метода {0}")
    public PricersV1Response getNominalRule(final int methodId) {
        admin.authAdminApi();
        return admin.getNominalRule(methodId);
    }

    @Step("Создание нового номинального правила для доставки {0}")
    public PricerV1Response createNominalRule(final int id) {
        admin.authAdminApi();
        return admin.createNominalRule(id);
    }

    @Step("Удаление нового номинального правила для доставки {0}")
    public void deleteNominalRule(final int id) {
        admin.authAdminApi();
        admin.deleteNominalRule(id);
    }

    @Step("Создать правило {0} данными {1}")
    public void createPricerRules(final int ruleId, final ShippingMethodsV1Request.Rules data) {
        admin.authAdminApi();
        admin.createPricerRule(ruleId, data);
    }

    @Step("Создать калькулятор {0} данными {1}")
    public void createPricerCalculator(final int ruleId, final ShippingMethodsV1Request.Calculators data) {
        admin.authAdminApi();
        admin.createPricerCalculator(ruleId, data);
    }

    @Step("Обновить правило {0} данными {1}")
    public void updateRules(final int ruleId, final ShippingMethodsV1Request.Rules data) {
        admin.authAdminApi();
        admin.updateRule(ruleId, data);
    }

    @Step("Обновить калькулятор {0} данными {1}")
    public void updateCalculator(final int ruleId, final ShippingMethodsV1Request.Calculators data) {
        admin.authAdminApi();
        admin.updateCalculator(ruleId, data);
    }

    @Step("Получить данные по заказам {0}")
    public ShipmentsAdminV1Response getShipments(final ShipmentsAdminV1Request.ShipmentsData shipmentsData) {
        admin.authAdminApi();
        return admin.getShipments(shipmentsData);
    }

    @Step("Получаем первый доступный слот ON_DEMAND")
    public DeliveryWindowV2 getAvailableDeliveryWindowOnDemand(final UserData user, final Integer sid) {
        apiV2.auth(user);

        apiV2.getCurrentOrderNumber();
        //apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid, 1);

        //apiV2.getAvailablePaymentTool();
        //apiV2.getAvailableShippingMethod();
        return apiV2.getAvailableDeliveryWindowOnDemand();
    }

    public void updateStore(final int storeId, final String availabilityDate) {
        StoresDao.INSTANCE.updateWithSetAvailability(storeId, availabilityDate);
    }

    @Step("Получаем Id компании по ИНН: {inn}")
    public int getCompanyId(final String inn) {
        final Response response = CompaniesV1Request.GET(inn);
        checkStatusCode200(response);
        return response.as(CompaniesV1Response.class).getCompanies().get(0).getId();
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
        admin.authAdminApi();
        RetailersV1Request.Retailer retailer = RetailersV1Request.getRetailer();
        retailer.setName(retailerName);
        retailer.setSlug(retailerName);
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

    @Step("Создаем магазин для ретейлера {retailerName}")
    public StoresAdminRequest.Store createStoreInAdmin(String retailerName) {
        admin.authAdmin();
        StoresAdminRequest.Store store = getStoreForRetailerTests(retailerName);
        AdminHelper.createStoreInAdmin(store);
        return store;
    }

    @Step("Настройка магазина для включения в админке")
    public void setupStoreForActivation(StoresAdminRequest.Store store) {
        importOffersInStore(store);
        importStoreZones(store);
        createScheduleMockup(store);
        createStoreSchedule(store);
    }

    @Step("Удаляем магазин из админки")
    public void deleteStoreInAdmin(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        Integer storeId = storeFromDb.getId();

        StoresDao.INSTANCE.delete(storeId);
        StoreConfigsDao.INSTANCE.deleteByStoreId(storeId);
        StoresTenantsDao.INSTANCE.deleteStoreTenantByStoreId(storeId);

        StoreZonesDao.INSTANCE.deleteStoreZoneByStoreId(storeId);

        OffersDao.INSTANCE.deleteByStoreId(storeId);
        PricesDao.INSTANCE.deletePriceByStoreId(storeId);
    }

    @Step("Импорт оффера для магазина")
    public Long importOffersInStore(StoresAdminRequest.Store store) {
        Long offerId;
        byte[] fileBytes;
        String importKey;

        admin.authAdmin();
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        Integer storeId = storeFromDb.getId();
        StoresTenantsDao.INSTANCE.addStoreTenant(storeId, "sbermarket");
        importKey = SpreeRetailersDao.INSTANCE.findById(storeFromDb.getRetailerId()).get().getKey() + "-" + store.getImportKeyPostFix();

        fileBytes = changeXlsFileSheetName("src/test/resources/data/offers.xlsx", importKey, 0);

        final Response response = ImportsV1Request.OffersFiles.POST(fileBytes);
        checkStatusCode200(response);

        int count = 0;
        String status = null;
        while (count < 20) {
            status = getOfferFiles().getOffersFiles().get(0).getStatus();
            if (status.equals(ImportStatusV1.DONE.getValue()))
                break;
            ThreadUtil.simplyAwait(1);
            count++;
        }
        compareTwoObjects(status, ImportStatusV1.DONE.getValue());
        OffersEntity offerFromDb = OffersDao.INSTANCE.getOfferByStoreId(storeId);
        offerId = offerFromDb.getId();
        return offerId;
    }

    @Step("Импорт зон магазина из файла")
    public void importStoreZonesFile(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        Integer storeId = storeFromDb.getId();

        final Response response = StoreZonesV1Request.ZoneFiles.POST("src/test/resources/data/zone.kml", storeId);
        checkStatusCode200(response);
    }

    @Step("Импорт зон магазина запрос")
    public void importStoreZonesBody(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        Integer storeId = storeFromDb.getId();

        final Response response = StoreZonesV1Request.Zones.POST(storeId, StoreZonesCoordinates.testMoscowZoneCoordinates());
        checkStatusCode200(response);
    }

    @Step("Импорт зон магазина")
    public void importStoreZones(StoresAdminRequest.Store store) {
       importStoreZonesFile(store);
       importStoreZonesBody(store);
    }

    @Step("Создание шаблона расписания магазина")
    public void createScheduleMockup(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        String storeUuid = storeFromDb.getUuid();
        final Response response = StoreSchedulesV1Request.Schedules.POST(storeUuid);
        checkStatusCode200(response);
    }

    @Step("Создание расписания магазина")
    public void createStoreSchedule(StoresAdminRequest.Store store) {
        Long deliveryWindowId = getAvailableDeliveryWindowsV1(store);
        final Response response = DeliveryWindowsV1Request.PUT(deliveryWindowId, 1);
        checkStatusCode200(response);
    }

    @Step("Получение списка окон доставки")
    public Long getAvailableDeliveryWindowsV1(StoresAdminRequest.Store store) {
        StoresEntity storeFromDb = StoresDao.INSTANCE.getStoreByCoordinates(store.getLat(), store.getLon());
        Integer storeId = storeFromDb.getId();

        final Response responsePost = StoresV1Request.DeliveryWindows.POST(storeId);
        checkStatusCode200(responsePost);

        final Response responseGet = StoresV1Request.DeliveryWindows.GET(storeId);
        checkStatusCode200(responseGet);

        List<DeliveryWindowV1> deliveryWindowsFromResponse = responseGet.as(DeliveryWindowsV1Response.class).getDeliveryWindows();
        int deliveryWindowsFromDbCount = DeliveryWindowsDao.INSTANCE.getCount(storeId, getDbDeliveryDateFrom(1L), getDbDeliveryDateTo(1L));
        compareTwoObjects(deliveryWindowsFromResponse.size(), deliveryWindowsFromDbCount);
        return deliveryWindowsFromResponse.get(0).getId();
    }
}
