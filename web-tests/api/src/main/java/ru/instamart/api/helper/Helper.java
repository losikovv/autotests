package ru.instamart.api.helper;

import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.kraken.testdata.UserData;

public class Helper {
    private final InstamartApiHelper apiV2 = new InstamartApiHelper();

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    public void dropCart(UserData user) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteItemsFromCart();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    public void fillCart(UserData user, int sid) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);
        apiV2.getCurrentOrderNumber();
        apiV2.deleteItemsFromCart();
        apiV2.fillCartOnSid(sid);
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     * encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    public void setAddress(UserData user, AddressV2 address) {
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, user);
        apiV2.getCurrentOrderNumber();
        apiV2.setAddressAttributes(user, address);
    }
}
