package ru.instamart.api.helper;

import io.qameta.allure.Step;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.k8s.K8sPortForward;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.SessionV2;
import ru.instamart.jdbc.dao.OperationalZonesDao;
import ru.instamart.kraken.data.StaticPageData;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

public class ApiHelper {
    private final InstamartApiHelper apiV2 = new InstamartApiHelper();
    private final AdminHelper admin = new AdminHelper();

    @Step("Подтверждение кода с помощью API")
    public SessionV2 confirmPhone(final String phone, final String code, final boolean promoTermsAccepted) {
        return apiV2.confirmPhone(phone, code, promoTermsAccepted);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Регистрация/авторизация по номеру телефона с помощью API")
    public void auth(final UserData user) {
        SessionFactory.createSessionToken(SessionType.API_V2, SessionProvider.PHONE, user);
    }

    @Step("Авторизация администратором")
    public void authAdmin(){
        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminAllRoles());
    }

    @Step("Добавить {count} продукт в список избранного")
    public void addFavorites(final UserData userData, final int sid, final int count) {
        auth(userData);
        apiV2.addFavoritesListProductBySid(sid, count);
    }

    @Step("Наполняем корзину избранным товаром с помощью API")
    public void dropAndFillCartFromFavorites(final UserData userData, final int sid) {
        auth(userData);
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
        auth(user);
        apiV2.getCurrentOrderNumber();
        return apiV2.deleteAllShipments();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid) {
        auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid);
    }

    /**
     * На стейдже работать не будет для {@link ru.instamart.api.common.RestAddresses}, так как нет большей части магазинов
     */
    @Step("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid, final AddressV2 address) {
        auth(user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, address);
        apiV2.fillCartOnSid(sid);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Указываем адрес с помощью API")
    public AddressV2 setAddress(final UserData user, AddressV2 address) {
        auth(user);
        apiV2.getCurrentOrderNumber();
        return apiV2.setAddressAttributes(user, address);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Оформляем заказ с помощью API")
    public OrderV2 makeOrder(final UserData user, final Integer sid, final Integer itemsNumber) {
        auth(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid, itemsNumber);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod();
        apiV2.getAvailableDeliveryWindow();

        apiV2.setDefaultOrderAttributes();
        return apiV2.completeOrder();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Отменяем заказ с помощью API")
    public OrderV2 cancelOrder(final UserData user, final String orderNumber) {
        auth(user);
        return apiV2.cancelOrder(orderNumber);
    }

    @Step("Отменяем все заказы с помощью API")
    public void cancelAllActiveOrders(final UserData userData) {
        auth(userData);
        apiV2.cancelActiveOrders();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Оформляем и отменяем заказ с помощью API")
    public void makeAndCancelOrder(final UserData user, final Integer sid, final Integer itemsNumber) {
        auth(user);

        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();

        apiV2.setAddressAttributes(user, apiV2.getAddressBySidMy(sid));
        apiV2.fillCartOnSid(sid, itemsNumber);

        apiV2.getAvailablePaymentTool();
        apiV2.getAvailableShippingMethod();
        apiV2.getAvailableDeliveryWindow();

        apiV2.setDefaultOrderAttributes();
        OrderV2 orderInfo = apiV2.completeOrder();
        apiV2.cancelOrder(orderInfo.getNumber());
    }

    @Step("Добавляем новый город {cityName} в админке")
    public void createCityInAdmin(String cityName) {
        authAdmin();
        admin.createCity(cityName);
    }

    @Step("Удаляем город {cityName} в админке")
    public void deleteCityInAdmin(String cityName) {
        authAdmin();
        admin.deleteCity(cityName);
    }

    @Step("Добавить компанию {inn}/{companyName} пользователю {ownerEmail}")
    public void addCompanyForUser(final String inn, final String companyName, final String ownerEmail) {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
        admin.addCompany(inn, companyName, ownerEmail);
    }

    @Step("Добавляем новую статичную страницу {data} в админке")
    public void createStaticPageInAdmin(StaticPageData data) {
        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminAllRoles());
        admin.createStaticPage(data);
    }

    @Step("Удаляем статичную страницу {pageId} в админке")
    public void deleteStaticPageInAdmin(Integer pageId) {
        SessionFactory.createSessionToken(SessionType.ADMIN, UserManager.getDefaultAdminAllRoles());
        admin.deleteStaticPage(pageId);
    }

    @Step("Добавляем новый регион {zoneName} для магазина в админке")
    public OperationalZoneV1 createOperationalZonesInAdmin(String zoneName) {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdminAllRoles());
        return admin.addOperationalZones(zoneName);
    }

    @Step("Удаляем регион {zoneName} для магазина в админке")
    public void deleteOperationalZonesInAdmin(String zoneName) {
        K8sPortForward.getInstance().portForwardMySQL();
        OperationalZonesDao.INSTANCE.deleteZoneByName(zoneName);
    }
}
