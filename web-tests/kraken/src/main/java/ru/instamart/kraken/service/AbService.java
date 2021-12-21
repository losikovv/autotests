package ru.instamart.kraken.service;

import ru.sbermarket.ab.AbApi;
import ru.sbermarket.ab.model.Setting;
import ru.sbermarket.ab.model.request.ExcludedUserRequest;
import ru.sbermarket.ab.model.request.UserGroups;
import ru.sbermarket.ab.model.response.AbTests;
import ru.sbermarket.ab.model.response.AuthorsResponse;
import ru.sbermarket.ab.model.response.ExcludedUserResponse;
import ru.sbermarket.common.Crypt;

import java.util.UUID;

import static java.util.Objects.isNull;

public enum AbService {

    INSTANCE;

    private static final String BASE_AB_SERVICE_URL = "https://paas-content-growth-ab-platform-back.k-stage.sbmt.io/api/v1";
    private final String USER = Crypt.INSTANCE.decrypt("xsgWkIGVrwj0lHZaP7QICWE5QMclQo2TQ54YaDTOeCk=");
    private final String PASS = Crypt.INSTANCE.decrypt("q0ZylSwt4ousASKzV9fcm4xG20UvV1IOGxOOplS0Cvw=");

    private final AbApi abApi;

    AbService() {
        this.abApi = new AbApi(new Setting(BASE_AB_SERVICE_URL, USER, PASS));
    }

    /**
     * Изменение группы пользователя
     * @param userGroups - параметры запроса
     */
    public void changeUserGroup(final UserGroups userGroups) {
        this.abApi.getUserGroupsService().update(userGroups);
    }

    /**
     * Удаление пользователя из тестовой группы
     * @param userGroups - параметры запроса
     */
    public void deleteUserGroup(final UserGroups userGroups) {
        this.abApi.getUserGroupsService().delete(userGroups);
    }

    /**
     * Получение списка всех авторов
     * @return - {@link AuthorsResponse}
     */
    public AuthorsResponse getAuthor() {
        return this.abApi.getAbTestsService().getAuthor();
    }

    /**
     * Получение всех активных АБ тестов
     * @return - {@link AbTests}
     */
    public AbTests getAllTests() {
        return this.abApi.getAbTestsService().getAllAbTests();
    }

    public ExcludedUserResponse excludeUser(String anonymousId) {
        if (isNull(anonymousId)) {
            anonymousId = UUID.randomUUID().toString();
        }
        var exclude = new ExcludedUserRequest();
        exclude.setAnonymousID(anonymousId);
        return this.abApi.getExcludedUserService().excludeUser(exclude);
    }
}
