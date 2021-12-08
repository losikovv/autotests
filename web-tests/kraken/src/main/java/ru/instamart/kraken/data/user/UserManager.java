package ru.instamart.kraken.data.user;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import ru.instamart.ab.model.request.UserGroups;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.kraken.service.AbService;
import ru.instamart.kraken.service.QaService;
import ru.instamart.qa.model.response.QaSessionResponse;
import ru.instamart.utils.Crypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class UserManager {

    private static final String CI_PIPELINE_SOURCE = Optional.ofNullable(System.getenv("CI_PIPELINE_SOURCE")).orElse("local");

    private static final List<UserData> USER_DATA_LIST = new ArrayList<>();

    private static final String PASSWD_1 = Crypt.INSTANCE.decrypt("pPOEBSnWKrokeN1dNasL0g==");
    private static final String PASSWD_2 = Crypt.INSTANCE.decrypt("y3Brgz0jBmYYkmXSkdw5Jw==");
    private static final String PASSWD_3 = Crypt.INSTANCE.decrypt("HfaITuMU+0KIfKR2+YYg5A==");

    private static UserData defaultUser;
    private static UserData defaultAdmin;
    private static UserData defaultAdminAllRoles;
    private static UserData defaultShopper;
    private static UserData defaultGmailUser;
    private static UserData defaultVkUser;
    private static UserData defaultFbUser;
    private static UserData defaultMailRuUser;
    private static UserData defaultSberIdUser;
    private static UserData defaultApiUser;
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

    public static UserData getDefaultAdmin() {
        if (isNull(defaultAdmin)) {
            defaultAdmin = UserData.builder()
                    .role("superadmin")
                    .email(Crypt.INSTANCE.decrypt("Gh1MsACysUuEYv98vkOuOOx/HVxUh5J54NKCNSJCPFQ="))
                    .phone(Crypt.INSTANCE.decrypt("WnA112EejZXmMlMlnVFwgQ=="))
                    .password(PASSWD_1)
                    .name("autotest superadmin")
                    .token(Crypt.INSTANCE.decrypt("etIbXhyM1zqCCpiTObFcm0Bb5vTw6rAFrB5Ir9/shcQ="))
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

    public static UserData getDefaultShopper() {
        log.info("GitLab env CI_PIPELINE_SOURCE: {}", CI_PIPELINE_SOURCE);
        if (isNull(defaultShopper)) {
            switch (CI_PIPELINE_SOURCE.toLowerCase()) {
                case "schedule":
                    log.info("User shopper SCHEDULE login");
                    defaultShopper = UserData.builder()
                            .email(Crypt.INSTANCE.decrypt("DNzBqyrPJQuc1LP0FzyiiQ=="))
                            .phone(Crypt.INSTANCE.decrypt("mbL67AMvUKR2bSJ/2k7FTA=="))
                            .password(PASSWD_2)
                            .build();
                    break;
                case "push":
                    log.info("User shopper PUSH login");
                    defaultShopper = UserData.builder()
                            .email(Crypt.INSTANCE.decrypt("K0wOsUQv9wDe1F4a6TtDKg=="))
                            .phone(Crypt.INSTANCE.decrypt("yLvA69qlzJsxOIvcma2zXg=="))
                            .password(PASSWD_2)
                            .build();
                    break;
                case "local":
                    log.info("User shopper local login");
                    defaultShopper = UserData.builder()
                            .email(Crypt.INSTANCE.decrypt("qq6/4elx6MF64Jw9VdI6xg=="))
                            .phone(Crypt.INSTANCE.decrypt("wOpdY2rf7+p9gnSoSndRWg=="))
                            .password(PASSWD_2)
                            .build();
                    break;
                default:
                    log.info("User shopper default login");
                    defaultShopper = UserData.builder()
                            .email(Crypt.INSTANCE.decrypt("/IsVBUY1et+En340g78Rvg=="))
                            .phone(Crypt.INSTANCE.decrypt("8lsSwjJUEjlPTeu4hDGU0w=="))
                            .password(PASSWD_2)
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

    public static UserData getDefaultFbUser() {
        if (isNull(defaultFbUser)) {
            defaultFbUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("6zWHFoRF1JgL9dmADTKTpQm7X0OkZzcK7JlvmU7dlLo="))
                    .password(PASSWD_1)
                    .build();
        }
        return defaultFbUser;
    }

    public static UserData getDefaultMailRuUser() {
        if (isNull(defaultMailRuUser)) {
            defaultMailRuUser = UserData.builder()
                    .email(Crypt.INSTANCE.decrypt("6F+U8tpf0M8xSLKvz+UawQ=="))
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
        final UserData userData = testCredentials(role, prefix);
        USER_DATA_LIST.add(userData);

        return userData;
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
     * @return - {@link UserData}
     */
    public static UserData getQaUserWithoutAb(final String abTestId, final String abTestGroupId) {
        final UserData userData = createUserWithoutAb(TestVariables.CompanyParams.companyName, abTestId, abTestGroupId);
        USER_DATA_LIST.add(userData);

        return userData;
    }

    /**
     * Создание пользователя с использованием тестовой ручки
     * @param password - обязательный параметр для создания через ручку
     * @return - возвращает собранную {@link UserData} из параметров ответа
     */
    public static UserData createUser(final String password) {
        final String role = UserRoles.USER.getRole();
        final String userName = Generate.testUserName(role);
        final QaSessionResponse sessionResponse = QaService.INSTANCE.createSession(password);

        Assert.assertNotNull(sessionResponse, "Ответ от QA сервиса не вернулся");

        log.debug("Сгенерированы тестовые реквизиты для роли {}", role);
        log.debug("Телефон: {}", sessionResponse.getUser().getPhone());
        log.debug("Email: {}", sessionResponse.getUser().getEmail());
        log.debug("Anonymous id: {}", sessionResponse.getAnonymous().getValue());
        log.debug("Пароль: {}", password);
        log.debug("Сессия: {}", sessionResponse.getSession().getAccessToken());
        log.debug("ФИО: {}", userName);

        return UserData.builder()
                .id(sessionResponse.getId())
                .role(role)
                .email(sessionResponse.getUser().getEmail())
                .phone(sessionResponse.getUser().getPhone().substring(1))
                .password(password)
                .name(userName)
                .anonymousId(sessionResponse.getAnonymous().getValue())
                .build();
    }

    /**
     * Создание пользователя с использованием {@link UserManager#createUser(String)}
     * @param password - обязательный параметр для создания через ручку
     * @param abTestId - UUID теста, который нужно будет переключить для созданного пользователя
     * @param abTestGroupId - UUID группы на которую нужно будет переключить созданного пользователя
     * @return - возвращает собранную {@link UserData} из параметров ответа
     */
    public static UserData createUserWithoutAb(final String password, final String abTestId, final String abTestGroupId) {
        final UserData newUser = createUser(password);

        final UserGroups userGroups = new UserGroups();
        userGroups.setAbTestId(abTestId);
        userGroups.setAbGroupId(abTestGroupId);
        userGroups.setIdentityId(newUser.getAnonymousId());
        AbService.INSTANCE.changeUserGroup(userGroups);

        log.debug("================================");
        log.debug("Измененный Ab Test {}", abTestId);
        log.debug("Группа для Ab теста {}", abTestGroupId);
        log.debug("================================");

        return newUser;
    }

    public static List<UserData> getUserDataList() {
        return USER_DATA_LIST;
    }

    public static void cleanupUsers() {
        USER_DATA_LIST.forEach(userData -> {
            final String userId = userData.getId();
            if (nonNull(userId) && !userId.isEmpty()) {
                QaService.INSTANCE.deleteSession(userId);
                log.debug("Remove user {}", userData.getPhone());
            }
        });
    }
}
