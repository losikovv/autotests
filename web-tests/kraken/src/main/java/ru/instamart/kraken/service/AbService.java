package ru.instamart.kraken.service;

import ru.instamart.ab.AbApi;
import ru.instamart.ab.model.Setting;
import ru.instamart.ab.model.request.UserGroups;
import ru.instamart.ab.model.response.AbTests;
import ru.instamart.ab.model.response.AuthorsResponse;
import ru.instamart.kraken.util.Crypt;

public final class AbService {

    private static final String BASE_AB_SERVICE_URL = "https://bs-ab-admin.k-stage.sbermarket.tech/api/v1";
    private final String USER = Crypt.INSTANCE.decrypt("xsgWkIGVrwj0lHZaP7QICWE5QMclQo2TQ54YaDTOeCk=");
    private final String PASS = Crypt.INSTANCE.decrypt("q0ZylSwt4ousASKzV9fcm4xG20UvV1IOGxOOplS0Cvw=");

    private final AbApi abApi;

    public AbService() {
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

    public static void main(String[] args) {
        Crypt.INSTANCE.init();
        AbService abService = new AbService();
        abService.getAllTests().getTests().forEach(System.out::println);
    }
}
