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
                Crypt.INSTANCE.decrypt("Gh1MsACysUuEYv98vkOuOOx/HVxUh5J54NKCNSJCPFQ="),
                "7777777777",
                PASSWD_1,
                "autotest superadmin",
                Crypt.INSTANCE.decrypt("etIbXhyM1zqCCpiTObFcm0Bb5vTw6rAFrB5Ir9/shcQ="));
    }

    public static UserData superuser(){
        return new UserData(
                "superuser",
                Crypt.INSTANCE.decrypt("aDPCwj7Br+dx8nAMvfc+/zywS4BuPQ25pLnnhiT3WnQ="),
                "1488148814",
                PASSWD_1,
                "autotest superuser");
    }

    public static UserData shopper(){
        return new UserData(
                Crypt.INSTANCE.decrypt("/IsVBUY1et+En340g78Rvg=="),
                PASSWD_2);
    }

    public static UserData gmail() {
        return new UserData(
                Crypt.INSTANCE.decrypt("mh5OayUtpk/8stH+dR7HBnyeKJB94fsqjaZfeO77LqI="),
                PASSWD_2);
    }

    public static UserData vkontakte() {

        return new UserData(
                Crypt.INSTANCE.decrypt("6zWHFoRF1JgL9dmADTKTpQm7X0OkZzcK7JlvmU7dlLo="),
                PASSWD_1);
    }

    public static UserData facebook() {
        return new UserData(
                Crypt.INSTANCE.decrypt("6zWHFoRF1JgL9dmADTKTpQm7X0OkZzcK7JlvmU7dlLo="),
                PASSWD_1);
    }

    public static UserData mailRu() {
        return new UserData(
                Crypt.INSTANCE.decrypt("6F+U8tpf0M8xSLKvz+UawQ=="),
                PASSWD_1);
    }

    public static UserData sberId() {
        return new UserData(
                Crypt.INSTANCE.decrypt("6ln1zIxi8BWCxz3YNZwc8w=="),
                PASSWD_3);
    }

    public static UserData apiUser() {
        return new UserData(
                Generate.email(),
                PASSWD_2,
                "Василий Автотестов");
    }
}
