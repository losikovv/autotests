package instamart.core.testdata;

import instamart.core.testdata.ui.Generate;
import instamart.ui.common.pagesdata.UserData;

public final class Users {

    public static UserData superadmin(){
        return new UserData(
                "superadmin",
                "autotestuser@instamart.ru",
                "7777777777",
                "hex78.Berwyn",
                "autotest superadmin");
    }

    public static UserData superuser(){
        return new UserData(
                "superuser",
                "instatestuser@yandex.ru",
                "1488148814",
                "hex78.Berwyn",
                "autotest superuser");
    }

    public static UserData shopper(){
        return new UserData(
                "kraken",
                "instamart");
    }

    public static UserData gmail() {
        return new UserData(
                "instamartmailtest@gmail.com",
                "instamart");
    }

    public static UserData vkontakte() {

        return new UserData(
                "instamart.qa@mail.ru",
                "hex78.Berwyn");
    }

    public static UserData facebook() {
        return new UserData(
                "instamart.qa@mail.ru",
                "hex78.Berwyn");
    }

    public static UserData mailRu() {
        return new UserData(
                "instamart.qa",
                "hex78.Berwyn");
    }

    public static UserData sberId() {
        return new UserData(
                "79629422123",
                "Bobylev1");
    }

    public static UserData apiUser() {
        return new UserData(
                Generate.email(),
                "instamart",
                "Василий Автотестов");
    }
}
