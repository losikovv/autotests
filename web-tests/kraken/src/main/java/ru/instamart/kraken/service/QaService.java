package ru.instamart.kraken.service;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qa.Api;
import ru.sbermarket.qa.model.Setting;
import ru.sbermarket.qa.model.response.QaSessionResponse;

public enum QaService {

    INSTANCE;

    private final Api api;

    QaService() {
        this.api = new Api(new Setting(EnvironmentProperties.Env.QA_FULL_URL));
    }

    /**
     * Создает сессию пользователя
     *
     * @param password - пароль для пользователя
     * @return {@link QaSessionResponse}
     */
    public QaSessionResponse createSession(final String password) {
        return api.getSessionService().getSession(password);
    }

    /**
     * Удаляет пользователя
     *
     * @param userId - {@link UserData#getId()}
     */
    public void deleteSession(final String userId) {
        api.getSessionService().deleteSession(userId);
    }
}
