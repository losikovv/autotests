package instamart.core.testdata;

import instamart.core.testdata.ui.Generate;
import instamart.core.util.Crypt;
import instamart.ui.common.pagesdata.UserData;

public final class Users {

    private static final String PASSWD_1 = Crypt.INSTANCE.decrypt("pPOEBSnWKrokeN1dNasL0g==");
    private static final String PASSWD_2 = Crypt.INSTANCE.decrypt("y3Brgz0jBmYYkmXSkdw5Jw==");
    private static final String PASSWD_3 = Crypt.INSTANCE.decrypt("HfaITuMU+0KIfKR2+YYg5A==");

    public static UserData superadmin(){
        return new UserData(
                "superadmin",
                "autotestuser@instamart.ru",
                "7777777777",
                PASSWD_1,
                "autotest superadmin",
                "E20135A0F9B9E9BC26D7222BAC");
    }

    public static UserData superuser(){
        return new UserData(
                "superuser",
                "instatestuser@yandex.ru",
                "1488148814",
                PASSWD_1,
                "autotest superuser");
    }

    public static UserData shopper(){
        return new UserData(
                "kraken",
                PASSWD_2);
    }

    public static UserData gmail() {
        return new UserData(
                "instamartmailtest@gmail.com",
                PASSWD_2);
    }

    public static UserData vkontakte() {

        return new UserData(
                "instamart.qa@mail.ru",
                PASSWD_1);
    }

    public static UserData facebook() {
        return new UserData(
                "instamart.qa@mail.ru",
                PASSWD_1);
    }

    public static UserData mailRu() {
        return new UserData(
                "instamart.qa",
                PASSWD_1);
    }

    public static UserData sberId() {
        return new UserData(
                "79629422123",
                PASSWD_3);
    }

    public static UserData apiUser() {
        return new UserData(
                Generate.email(),
                PASSWD_2,
                "Василий Автотестов");
    }
}
