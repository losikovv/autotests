package ru.instamart.kraken.testdata;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.util.Crypt;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
public final class UserManager {

    private static final List<UserData> USER_DATA_LIST = new ArrayList<>();

    private static final String PASSWD_1 = Crypt.INSTANCE.decrypt("pPOEBSnWKrokeN1dNasL0g==");
    private static final String PASSWD_2 = Crypt.INSTANCE.decrypt("y3Brgz0jBmYYkmXSkdw5Jw==");
    private static final String PASSWD_3 = Crypt.INSTANCE.decrypt("HfaITuMU+0KIfKR2+YYg5A==");

    private static UserData defaultUser;
    private static UserData defaultAdmin;
    private static UserData defaultShopper;
    private static UserData defaultGmailUser;
    private static UserData defaultVkUser;
    private static UserData defaultFbUser;
    private static UserData defaultMailRuUser;
    private static UserData defaultSberIdUser;
    private static UserData defaultApiUser;

    public static UserData getNullUser() {
        return generateData("empty", 0);
    }

    public static UserData getUser() {
        return generateData("ru.instamart.test.ui.user", 0);
    }

    public static UserData getUser(final int prefix) {
        return generateData("ru.instamart.test.ui.user", prefix);
    }

    public static UserData getAdmin() {
        return generateData("admin", 0);
    }

    public static UserData getAdmin(final int prefix) {
        return generateData("admin", prefix);
    }

    public static JuridicalData juridical() {
        return new JuridicalData(
                "ЗАО \"Лидер-" + Generate.digitalString(4) + "\"",
                Generate.string(8),
                Generate.generateINN(10),
                Generate.digitalString(9),
                Generate.digitalString(20),
                Generate.digitalString(9),
                Generate.string(8),
                Generate.digitalString(20)
        );
    }

    public static UserData getDefaultUser() {
        if (isNull(defaultUser)) {
            defaultUser = new UserData(
                    "superuser",
                    Crypt.INSTANCE.decrypt("aDPCwj7Br+dx8nAMvfc+/zywS4BuPQ25pLnnhiT3WnQ="),
                    "1488148814",
                    PASSWD_1,
                    "autotest superuser"
            );
        }
        return defaultUser;
    }

    public static UserData getDefaultAdmin() {
        if (isNull(defaultAdmin)) {
            return defaultAdmin = new UserData(
                    "superadmin",
                    Crypt.INSTANCE.decrypt("Gh1MsACysUuEYv98vkOuOOx/HVxUh5J54NKCNSJCPFQ="),
                    "7777777777",
                    PASSWD_1,
                    "autotest superadmin",
                    Crypt.INSTANCE.decrypt("etIbXhyM1zqCCpiTObFcm0Bb5vTw6rAFrB5Ir9/shcQ="),
                    null
            );
        }
        return defaultAdmin;
    }

    public static UserData getDefaultShopper() {
        if (isNull(defaultShopper)) {
            return defaultShopper = new UserData(
                    Crypt.INSTANCE.decrypt("/IsVBUY1et+En340g78Rvg=="),
                    PASSWD_2);
        }
        return defaultShopper;
    }

    public static UserData getDefaultGmailUser() {
        if (isNull(defaultGmailUser)) {
            return  defaultGmailUser = new UserData(
                    Crypt.INSTANCE.decrypt("mh5OayUtpk/8stH+dR7HBnyeKJB94fsqjaZfeO77LqI="),
                    PASSWD_2
            );
        }
        return defaultGmailUser;
    }

    public static UserData getDefaultVkUser() {
        if (isNull(defaultVkUser)) {
            return defaultVkUser = new UserData(
                    Crypt.INSTANCE.decrypt("6zWHFoRF1JgL9dmADTKTpQm7X0OkZzcK7JlvmU7dlLo="),
                    PASSWD_1
            );
        }
        return defaultVkUser;
    }

    public static UserData getDefaultFbUser() {
        if (isNull(defaultFbUser)) {
            return defaultFbUser = new UserData(
                    Crypt.INSTANCE.decrypt("6zWHFoRF1JgL9dmADTKTpQm7X0OkZzcK7JlvmU7dlLo="),
                    PASSWD_1
            );
        }
        return defaultFbUser;
    }

    public static UserData getDefaultMailRuUser() {
        if (isNull(defaultMailRuUser)) {
            return defaultMailRuUser = new UserData(
                    Crypt.INSTANCE.decrypt("6F+U8tpf0M8xSLKvz+UawQ=="),
                    PASSWD_1
            );
        }
        return defaultMailRuUser;
    }

    public static UserData getDefaultSberIdUser() {
        if (isNull(defaultSberIdUser)) {
            return  defaultSberIdUser = new UserData(
                    Crypt.INSTANCE.decrypt("6ln1zIxi8BWCxz3YNZwc8w=="),
                    PASSWD_3
            );
        }
        return defaultSberIdUser;
    }

    public static UserData getDefaultApiUser() {
        if (isNull(defaultApiUser)) {
            defaultApiUser = new UserData(
                    "superuser",
                    "test@test.test",
                    "79999999966",
                    "",
                    "autotest superuser",
                    "",
                    "bjg8q2s53S057R4rWgL9PHDhF6UOdFIPGwzzhMH+BYE="
            );
        }
        return defaultApiUser;
    }

    public static UserData getDeliveryClubUser() {
        if (isNull(defaultApiUser)) {
            return  defaultApiUser = new UserData(
                    "dc",
                    "dcsmstage"
            );
        }
        return defaultApiUser;
    }

    public static List<UserData> getUserDataList() {
        return USER_DATA_LIST;
    }

    private static UserData generateData(final String role, final int prefix) {
        final UserData userData = testCredentials(role, prefix);
        USER_DATA_LIST.add(userData);

        return userData;
    }

    public static UserData testCredentials(final String role, final int prefixLength) {
        final String testUserId = Generate.userId();
        final UserData testUser = new UserData(
                role,
                Generate.email(),
                Generate.testUserPhone(testUserId),
                TestVariables.CompanyParams.companyName,
                Generate.testUserName(role)
        );
        if (prefixLength > 0) {
            final String prefix = Generate.literalString(prefixLength);
            testUser.setLogin(prefix + "-" + testUser.getLogin());
            testUser.setPassword(prefix + "-" + testUser.getPassword());
            testUser.setName(prefix + "-" + testUser.getName());
        }
        log.info("Сгенерированы тестовые реквизиты для роли {}", role);
        log.info("Телефон: {}", testUser.getPhone());
        log.info("Email: {}", testUser.getLogin());
        log.info("Пароль: {}", testUser.getPassword());
        log.info("Имя: {}", testUser.getFirstName());
        log.info("Фамилия: {}", testUser.getLastName());

        return testUser;
    }
}
