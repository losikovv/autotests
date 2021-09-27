package ru.instamart.api.helper;

import io.qameta.allure.Step;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.SessionV2;
import ru.instamart.kraken.testdata.UserData;

public class ApiHelper {
    private final InstamartApiHelper apiV2 = new InstamartApiHelper();

    @Step ("Подтверждение кода с помощью API")
    public SessionV2 confirmPhone(final String phone, final String code, final boolean promoTermsAccepted) {
        return apiV2.confirmPhone(phone, code, promoTermsAccepted);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step ("Регистрация/авторизация по номеру телефона с помощью API")
    public void auth(final UserData user) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step ("Очищаем корзину с помощью API")
    public OrderV2 dropCart(final UserData user) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);

        apiV2.getCurrentOrderNumber();
        return apiV2.deleteAllShipments();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step ("Наполняем корзину с помощью API")
    public void dropAndFillCart(final UserData user, final Integer sid) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteAllShipments();
        apiV2.setAddressAttributes(user, apiV2.getAddressBySid(sid));
        apiV2.fillCartOnSid(sid);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step ("Указываем адрес с помощью API")
    public AddressV2 setAddress(final UserData user, AddressV2 address) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);

        apiV2.getCurrentOrderNumber();

        return apiV2.setAddressAttributes(user, address);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step ("Оформляем заказ с помощью API")
    public OrderV2 makeOrder(final UserData user, final Integer sid, final Integer itemsNumber) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);

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
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step ("Отменяем заказ с помощью API")
    public OrderV2 cancelOrder(final UserData user, final String orderNumber) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);

        return apiV2.cancelOrder(orderNumber);
    }

    @Step("Отменяем все заказы с помощью API")
    public void cancelAllActiveOrders(final UserData userData) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, userData);
        apiV2.cancelActiveOrders();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step ("Оформляем и отменяем заказ с помощью API")
    public void makeAndCancelOrder(final UserData user, final Integer sid, final Integer itemsNumber) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);

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
}
