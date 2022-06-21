package ru.instamart.kraken.data.user;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.kraken.retry.StepRetry;
import ru.instamart.kraken.service.AbService;
import ru.instamart.kraken.service.QaService;
import ru.sbermarket.common.Crypt;
import ru.sbermarket.qa.model.response.QaSessionResponse;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class UserManager {

    private static final String CI_PIPELINE_SOURCE = Optional.ofNullable(System.getenv("CI_PIPELINE_SOURCE")).orElse("local");
    private static final String API_SHOPPER_ALONE = System.getenv("API_SHOPPER_ALONE");
    private static final String CI_RUN_ALL_JOBS = System.getenv("CI_RUN_ALL_JOBS");

    private static final List<UserData> USER_DATA_LIST = Collections.synchronizedList(new ArrayList<>());

    private static final String PASSWD_1 = Crypt.INSTANCE.decrypt("pPOEBSnWKrokeN1dNasL0g==");
    private static final String PASSWD_2 = Crypt.INSTANCE.decrypt("y3Brgz0jBmYYkmXSkdw5Jw==");
    private static final String PASSWD_3 = Crypt.INSTANCE.decrypt("HfaITuMU+0KIfKR2+YYg5A==");
    private static final String PASSWD_4 = Crypt.INSTANCE.decrypt("Lnzny6uByX5iwcjViS9iTA==");

    private static UserData defaultUser;
    private static UserData defaultAdmin;
    private static UserData defaultAdminAllRoles;
    private static UserData defaultAdminSmsRole;
    private static UserData defaultShopper;
    private static UserData krakenUniversal;
    private static UserData krakenUniversal2;
    private static UserData krakenUniversal3;
    private static UserData stf6Shopper1;
    private static UserData stf6Shopper2;
    private static UserData stf6Shopper3;
    private static UserData stf6Shopper4;
    private static UserData defaultGmailUser;
    private static UserData defaultVkUser;
    private static UserData defaultFbUser;
    private static UserData defaultMailRuUser;
    private static UserData defaultSberIdUser;
    private static UserData defaultSberBusinessIdUser;
    private static UserData defaultApiUser;
    private static UserData getStf6ApiUser;
    private static UserData defaultDcUser;
    private static UserData defaultRisUser;
    private static UserData defaultUserWithoutPermission;
    private static UserData forB2BUser;
    private static UserData addressUser;

    public static UserData getDefaultUser() {
        if (isNull(defaultUser)) {
            defaultUser = UserData.builder()
                    .role("superuser")
                    .email(Crypt.INSTANCE.decrypt("aDPCwj7Br+dx8nAMvfc+/zywS4BuPQ25pLnnhiT3WnQ="))
                    .phone(Crypt.INSTANCE.decrypt("8pvQF1rKDw0Y+9PskqcKLQ=="))
                    .password(PASSWD_1)
                    .name("autotest superuser")
                    .build();
        }
        return defaultUser;
    }

    public static UserData getDefaultAdminStage() {
        if (isNull(defaultAdmin)) {
            defaultAdmin = UserData.builder()
                    .role("superadmin")
                    .email(Crypt.INSTANCE.decrypt("Gh1MsACysUuEYv98vkOuOOx/HVxUh5J54NKCNSJCPFQ="))
                    .phone(Crypt.INSTANCE.decrypt("z2UvelSsJ4QsKh9rGmQZDw=="))
                    .password(EnvironmentProperties.ADMIN_PASSWORD)
                    .name("autotest superadmin")
                    .token(Crypt.INSTANCE.decrypt("fOirPL+gZMmUegHv1749gumS6q0SJpPUPtjMwaVPTvv3fF6EoM9AZBSUACS5kY9E"))
                    .build();
        }
        return defaultAdmin;
    }

    public static UserData getDefaultAdminAllRoles() {
        if (isNull(defaultAdminAllRoles)) {
            defaultAdminAllRoles = UserData.builder()
                    .role("superadmin")
                    .email(Crypt.INSTANCE.decrypt("wTfubFbVMEA2P1HT80pKDXJfzWnJ15xgPBJr240lktU="))
                    .phone(Crypt.INSTANCE.decrypt("z2UvelSsJ4QsKh9rGmQZDw=="))
                    .password(PASSWD_1)
                    .name("autotest superadminallroles")
                    .build();
        }
        return defaultAdminAllRoles;
    }

    public static UserData getDefaultAdmin(){
        return (EnvironmentProperties.Env.isProduction() ? getDefaultAdminProd() : getDefaultAdminStage());
    }

    public static UserData getDefaultAdminProd() {
        if (isNull(defaultAdmin)) {
            defaultAdmin = UserData.builder()
                    .role("superadmin")
                    .email(Crypt.INSTANCE.decrypt("Gh1MsACysUuEYv98vkOuOOx/HVxUh5J54NKCNSJCPFQ="))
                    .phone(Crypt.INSTANCE.decrypt("z2UvelSsJ4QsKh9rGmQZDw=="))
                    .password(PASSWD_4)
                    .name("autotest admin")
                    .build();
        }
        return defaultAdmin;
    }

    public static UserData getAdminSmsRole() {
        if (isNull(defaultAdminSmsRole)) {
            defaultAdminSmsRole = UserData.builder()
                    .role("superadmin")
                    .email(Crypt.INSTANCE.decrypt("KlMoYV/REWC3sSm3Tzk0WtH1SVSFpTuQ7swGVY8kfHY="))
                    .phone(Crypt.INSTANCE.decrypt("z2UvelSsJ4QsKh9rGmQZDw=="))
                    .password(PASSWD_1)
                    .name("autotest superadminallroles")
                    .build();
        }
        return defaultAdminSmsRole;
    }

    public static UserData getShp6Shopper1() {
        if (isNull(stf6Shopper1)) {
            stf6Shopper1 = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("K0wOsUQv9wDe1F4a6TtDKg=="))
                    .phone(Crypt.INSTANCE.decrypt("NN6iTCNigNa9a4D/72ZAWQ=="))
                    .uuid("8608a93d-7def-4cdb-8d1d-8332ef526cd1")
                    .build();
        }
        return stf6Shopper1;
    }

    public static UserData getShp6Shopper2() {
        if (isNull(stf6Shopper2)) {
            stf6Shopper2 = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("C4fgAi97cuuXHMKobHn9Yw=="))
                    .phone(Crypt.INSTANCE.decrypt("cIo8g9LlgqN+TCDGKZ4PTw=="))
                    .uuid("175954c2-c5df-4474-89dd-4a9ea5c081ad")
                    .build();
        }
        return stf6Shopper2;
    }

    public static UserData getShp6Shopper3() {
        if (isNull(stf6Shopper3)) {
            stf6Shopper3 = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("xhH38k1XpJGpdk8/7LkCcw=="))
                    .phone(Crypt.INSTANCE.decrypt("3K4qmKFewgiQsQ3x1iZPtQ=="))
                    .uuid("9e6bd9f2-7689-49b8-8df3-5f66a36cd624")
                    .build();
        }
        return stf6Shopper3;
    }

    public static UserData getShp6Shopper4() {
        if (isNull(stf6Shopper4)) {
            stf6Shopper4 = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("xaL2G5Kxa2fI4vosV/yX8A=="))
                    .phone(Crypt.INSTANCE.decrypt("GA9lbHdxv4QkcxPVJMkWQw=="))
                    .uuid("d63b66b2-441e-4c41-85d2-64ff33daadf0")
                    .build();
        }
        return stf6Shopper4;
    }

    public static UserData getKrakenUniversal() {
        if (isNull(krakenUniversal)) {
            krakenUniversal = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("fge71qP00DC40VYpfYee6w=="))
                    .phone(Crypt.INSTANCE.decrypt("gqGtx2TyUwPh0VYmzd2ZYg=="))
                    .uuid("499fbd65-fca9-4da9-a7e0-32d64d88e3c8")
                    .build();
        }
        return krakenUniversal;
    }

    public static UserData getKrakenUniversal2() {
        if (isNull(krakenUniversal2)) {
            krakenUniversal2 = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("fge71qP00DC40VYpfYee6w=="))
                    .phone(Crypt.INSTANCE.decrypt("0KdZx6GFU+um3PcT/t4h2A=="))
                    .uuid("bb6306b6-cb54-424d-9121-71da64eba1d5")
                    .build();
        }
        return krakenUniversal2;
    }

    public static UserData getKrakenUniversal3() {
        if (isNull(krakenUniversal3)) {
            krakenUniversal3 = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("fge71qP00DC40VYpfYee6w=="))
                    .phone(Crypt.INSTANCE.decrypt("M9yxXPYPGa30eeScluKndQ=="))
                    .uuid("043f87a0-c81b-43cf-9ecc-f71db39e8e2d")
                    .build();
        }
        return krakenUniversal3;
    }

    public static UserData getDefaultShopper() {

        if (isNull(defaultShopper)) {
            log.debug("GitLab env CI_PIPELINE_SOURCE: {}", CI_PIPELINE_SOURCE);
            log.debug("GitLab env API_SHOPPER_ALONE: {}", API_SHOPPER_ALONE);
            log.debug("GitLab env CI_RUN_ALL_JOBS: {}", CI_RUN_ALL_JOBS);
            log.debug("SERVER: {}", EnvironmentProperties.SERVER);
        }

        if (isNull(defaultShopper) && EnvironmentProperties.Env.isProduction()) {
            defaultShopper = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("fge71qP00DC40VYpfYee6w=="))
                    .phone(Crypt.INSTANCE.decrypt("vodVNuxqMcNPIBazZFHfAA=="))
                    .uuid("777f2bc4-65f0-4c74-8304-ac1fba530062")
                    .build();
        }

        if (isNull(defaultShopper)) {
            switch (CI_PIPELINE_SOURCE.toLowerCase()) {
                case "schedule":
                    log.debug("User shopper SCHEDULE login");
                    defaultShopper = UserData.builder()
                            .email(Crypt.INSTANCE.decrypt("DNzBqyrPJQuc1LP0FzyiiQ=="))
                            .phone(Crypt.INSTANCE.decrypt("VKxPCYWxtW47hpCL/Qj43Q=="))
                            .uuid("d7f1f6aa-8890-42fe-9696-28502458048a")
                            .build();
                    break;
                case "push":
                    if (API_SHOPPER_ALONE != null && CI_PIPELINE_SOURCE != null && API_SHOPPER_ALONE.equals("true") && CI_RUN_ALL_JOBS.equals("false")) {
                        log.debug("User shopper API_SHOPPER_ALONE login");
                        defaultShopper = UserData.builder()
                                .email(Crypt.INSTANCE.decrypt("K0wOsUQv9wDe1F4a6TtDKg=="))
                                .phone(Crypt.INSTANCE.decrypt("SnggaupjoOoDXgTrdww1PA=="))
                                .uuid("bb6065f4-da72-4511-9d47-c61dab0d3055")
                                .build();
                    } else {
                        log.debug("User shopper CI_RUN_ALL_JOBS login");
                        defaultShopper = UserData.builder()
                                .email(Crypt.INSTANCE.decrypt("/IsVBUY1et+En340g78Rvg=="))
                                .phone(Crypt.INSTANCE.decrypt("91K1YSFP8P4lmL8eDk/bAg=="))
                                .uuid("3bc2867e-c7d1-4c2d-a359-a2673e255c33")
                                .build();
                    }
                    break;
                case "local":
                    log.debug("User shopper local login");
                    defaultShopper = UserData.builder()
                            .email(Crypt.INSTANCE.decrypt("qq6/4elx6MF64Jw9VdI6xg=="))
                            .phone(Crypt.INSTANCE.decrypt("jKQeEMXjzX5ROozXx40AgA=="))
                            .uuid("261d5dba-9a4a-4c56-bc71-a068c29f322a")
                            .build();
                    break;
                default:
                    log.debug("User shopper default login");
                    defaultShopper = UserData.builder()
                            .email(Crypt.INSTANCE.decrypt("/IsVBUY1et+En340g78Rvg=="))
                            .phone(Crypt.INSTANCE.decrypt("91K1YSFP8P4lmL8eDk/bAg=="))
                            .uuid("3bc2867e-c7d1-4c2d-a359-a2673e255c33")
                            .build();
                    break;
            }
        }
        return defaultShopper;
    }

    public static UserData getDefaultGmailUser() {
        if (isNull(defaultGmailUser)) {
            defaultGmailUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("mh5OayUtpk/8stH+dR7HBnyeKJB94fsqjaZfeO77LqI="))
                    .password(PASSWD_2)
                    .build();
        }
        return defaultGmailUser;
    }

    public static UserData getDefaultVkUser() {
        if (isNull(defaultVkUser)) {
            defaultVkUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("6zWHFoRF1JgL9dmADTKTpQm7X0OkZzcK7JlvmU7dlLo="))
                    .password(PASSWD_1)
                    .build();
        }
        return defaultVkUser;
    }

    public static UserData getNewVkUser() {
        return UserData.builder()
                .email(Crypt.INSTANCE.decrypt("GokqBKIyx6KtQFfpDDsEvA=="))
                .password(Crypt.INSTANCE.decrypt("subivm/a2hcvqUlEJfHAag=="))
                .build();
    }

    public static UserData getDefaultFbUser() {
        if (isNull(defaultFbUser)) {
            defaultFbUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("zXTDlp8g3Rux0gMsR8Lo0wOJ8bsCTtxnRe3fYkdLcFI="))
                    .password(Crypt.INSTANCE.decrypt("1/Ymjco2v4WNEveaGfP7dw8mJpQ+3pszBu9i9Datm98="))
                    .build();
        }
        return defaultFbUser;
    }

    public static UserData getDefaultMailRuUser() {
        if (isNull(defaultMailRuUser)) {
            defaultMailRuUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("TLSz46APgnhRyuJhSDZJr/2tC0Dzq0Twcf2gmRS3V1E="))
                    .password(PASSWD_1)
                    .build();
        }
        return defaultMailRuUser;
    }

    public static UserData getDefaultSberIdUser() {
        if (isNull(defaultSberIdUser)) {
            defaultSberIdUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("6ln1zIxi8BWCxz3YNZwc8w=="))
                    .password(PASSWD_2)
                    .build();
        }
        return defaultSberIdUser;
    }

    public static UserData getDefaultSberBusinessIdUser() {
        if (isNull(defaultSberBusinessIdUser)) {
            defaultSberBusinessIdUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("Cuih++LCNGJ/peMbzjrFkw=="))
                    .password(Crypt.INSTANCE.decrypt("N09waZFwNRQIUCCOOf8RLA=="))
                    .smsCode(Crypt.INSTANCE.decrypt("tLU2NTESIXtLt/eQcHvutw=="))
                    .build();
        }
        return defaultSberBusinessIdUser;
    }

    public static UserData getDefaultApiUser() {
        if (isNull(defaultApiUser)) {
            defaultApiUser = UserData.builder()
                    .role("superuser")
                    .email(Crypt.INSTANCE.decrypt("vgmGFDo8Onbhu9KH6UJjPtk4Q4LBcZh8xoLhogVWI9k="))
                    .phone(Crypt.INSTANCE.decrypt("5QQdvXjLBpVEu2dpZCT3iQ=="))
                    .name("autotest superuser")
                    .build();
        }
        return defaultApiUser;
    }

    public static UserData getStf6ApiUser() {
        if (isNull(getStf6ApiUser)) {
            getStf6ApiUser = UserData.builder()
                    .role("user")
                    .email(Crypt.INSTANCE.decrypt("vgmGFDo8Onbhu9KH6UJjPtk4Q4LBcZh8xoLhogVWI9k="))
                    .phone("9879871212")
                    .name("autotest user")
                    .build();
        }
        return getStf6ApiUser;
    }

    public static UserData getDeliveryClubUser() {
        if (isNull(defaultDcUser)) {
            defaultDcUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("nmg+iaPlmiNmb2oNDW+wig=="))
                    .password(Crypt.INSTANCE.decrypt("g/2urIK+Qxny6wkxsjoidw=="))
                    .build();
        }
        return defaultDcUser;
    }

    public static UserData getRisUser() {
        if (isNull(defaultRisUser)) {
            defaultRisUser = UserData.builder()
                    .email("")
                    .token("ZGNtYXJrZXQ6MzhlYjgxYjNkOTdiZDgx")
                    .build();
        }
        return defaultRisUser;
    }

    public static UserData userWithoutAdminPermission() {
        if (isNull(defaultUserWithoutPermission)) {
            defaultUserWithoutPermission = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("ECME0oVDIK76qsrZeUtsFPmH3StNoTg4V5ow1j3ejSI="))
                    .password(PASSWD_1)
                    .build();
        }
        return defaultUserWithoutPermission;
    }

    public static UserData forB2BUser() {
        if (isNull(forB2BUser)) {
            forB2BUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("4iwwd7hWsW7NN4TyGWohVfIbI/Qx5ujSol6s9rPHw0g="))
                    .phone(Crypt.INSTANCE.decrypt("pOR0GW7vjYFSN634MFxxxg=="))
                    .password(PASSWD_1)
                    .build();
        }
        return forB2BUser;
    }

    public static UserData addressUser() {
        if (isNull(addressUser)) {
            addressUser = UserData.builder()
                    .phone(Crypt.INSTANCE.decrypt("UFfwiiQLwQCGLRIVnqnyuQ=="))
                    .build();
        }
        return addressUser;
    }

    public static UserData checkoutUser() {
        return UserData.builder()
                .phone(Crypt.INSTANCE.decrypt("Il5CC+ZHETemFCNdOxuR0w=="))
                .email(Crypt.INSTANCE.decrypt("OvdigRywldL077CUQY5nV7DaRhGzRl2X0i18kkhZj9w="))
                .build();
    }

    public static UserData getNullUser() {
        return generateData("empty", 0);
    }

    public static UserData getUser() {
        return generateData(UserRoles.USER.getRole(), 0);
    }

    public static UserData getUser(final int prefix) {
        return generateData(UserRoles.USER.getRole(), prefix);
    }

    public static UserData getAdmin() {
        return generateData(UserRoles.ADMIN.getRole(), 0);
    }

    public static UserData getAdmin(final int prefix) {
        return generateData(UserRoles.ADMIN.getRole(), prefix);
    }

    private static UserData generateData(final String role, final int prefix) {
        return testCredentials(role, prefix);
    }

    public static UserData testCredentials(final String role, final int prefixLength) {
        final String testUserId = Generate.userId();
        final UserData testUser = UserData.builder()
                .role(role)
                .email(Generate.email())
                .phone(Generate.testUserPhone(testUserId))
                .password(TestVariables.CompanyParams.companyName)
                .name(Generate.testUserName(role))
                .build();

        if (prefixLength > 0) {
            final String prefix = Generate.literalString(prefixLength);
            testUser.setEmail(prefix + "-" + testUser.getEmail());
            testUser.setPassword(prefix + "-" + testUser.getPassword());
            testUser.setName(prefix + "-" + testUser.getName());
        }

        log.debug("Сгенерированы тестовые реквизиты для роли {}", role);
        log.debug("Телефон: {}", testUser.getPhone());
        log.debug("Email: {}", testUser.getEmail());
        log.debug("Пароль: {}", testUser.getPassword());
        log.debug("Имя: {}", testUser.getFirstName());
        log.debug("Фамилия: {}", testUser.getLastName());

        return testUser;
    }

    /**
     * При создании пользователь добавляется в список со всеми созданными пользователями,
     * для того что бы по завершению прогона можно было получить всех пользователей участвовавших в прогоне и удалить их
     *
     * @return - {@link UserData}
     */
    public static UserData getQaUser() {
        final UserData userData = createUser(TestVariables.CompanyParams.companyName);
        USER_DATA_LIST.add(userData);

        return userData;
    }

    /**
     * При создании пользователь добавляется в список со всеми созданными пользователями,
     * для того что бы по завершению прогона можно было получить всех пользователей участвовавших в прогоне и удалить их
     *
     * @return - {@link List<UserData>}
     */
    public static List<UserData> getQaUsers(final int usersCount) {
        List<UserData> usersData = new ArrayList<>();
        for (int i = 0; i < usersCount; i++) {
            usersData.add(getQaUser());
        }
        return usersData;
    }

    /**
     * При создании пользователь добавляется в список со всеми созданными пользователями,
     * для того что бы по завершению прогона можно было получить всех пользователей участвовавших в прогоне и удалить их
     *
     * @return - {@link UserData}
     */
    public static UserData getQaUserWithoutAb() {
        final UserData userData = createUserWithoutAb(TestVariables.CompanyParams.companyName);
        USER_DATA_LIST.add(userData);

        return userData;
    }

    public static String getGuestQaWithoutAb() {
        var excludedUser = AbService.INSTANCE.excludeUser(null);
        if (nonNull(excludedUser) && (nonNull(excludedUser.getAnonymousID()) && !excludedUser.getAnonymousID().isEmpty())) {
            var anonymousId = excludedUser.getAnonymousID();
            log.debug("Get guest with anonymousId='{}' excluded from all AB", anonymousId);
            return anonymousId;
        }
        log.error("Get guest without exclusion from AB {}", excludedUser);
        return UUID.randomUUID().toString();
    }

    /**
     * Создание пользователя с использованием тестовой ручки
     *
     * @param password - обязательный параметр для создания через ручку
     * @return - возвращает собранную {@link UserData} из параметров ответа
     */
    @Step("Создание тестового пользователя")
    @StepRetry(count = 5)
    public static UserData createUser(final String password) {
        final String role = UserRoles.USER.getRole();
        final String userName = Generate.testUserName(role);
        final QaSessionResponse sessionResponse = QaService.INSTANCE.createSession(password);

        Assert.assertNotNull(sessionResponse.getSession(), "Ответ от QA сервиса не вернулся");

        log.debug("Сгенерированы тестовые реквизиты для роли {}", role);
        log.debug("Телефон: {}", sessionResponse.getUser().getPhone());
        log.debug("Email: {}", sessionResponse.getUser().getEmail());
        log.debug("Anonymous id: {}", sessionResponse.getAnonymous().getValue());
        log.debug("Пароль: {}", password);
        log.debug("Сессия: {}", sessionResponse.getSession().getAccessToken());
        log.debug("ФИО: {}", userName);

        return UserData.builder()
                .id(sessionResponse.getUser().getId().toString())
                .role(role)
                .email(sessionResponse.getUser().getEmail())
                .phone(sessionResponse.getUser().getPhone().substring(1))
                .password(password)
                .name(userName)
                .anonymousId(sessionResponse.getAnonymous().getValue())
                .token(sessionResponse.getSession().getAccessToken())
                .qaSessionId(sessionResponse.getId())
                .build();
    }

    /**
     * Создание пользователя с использованием {@link UserManager#createUser(String)}
     *
     * @param password - обязательный параметр для создания через ручку
     * @return - возвращает собранную {@link UserData} из параметров ответа
     */
    public static UserData createUserWithoutAb(final String password) {
        final UserData newUser = createUser(password);

        AbService.INSTANCE.excludeUser(newUser.getAnonymousId());

        log.debug("================================");
        log.debug("Пользователь {} исключен из всех AB тестов", newUser);
        log.debug("================================");

        return newUser;
    }

    public static List<UserData> getUserDataList() {
        return USER_DATA_LIST;
    }

    public static void cleanupUsers() {
        USER_DATA_LIST.stream()
                .filter(Objects::nonNull)
                .forEach(userData -> {
                    final String qaSessionId = userData.getQaSessionId();
                    if (nonNull(qaSessionId) && !qaSessionId.isEmpty()) {
                        QaService.INSTANCE.deleteSession(qaSessionId);
                        log.debug("Remove user {}", userData.getPhone());
                    }
                });

    }
}
