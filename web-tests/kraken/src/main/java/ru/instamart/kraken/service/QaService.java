package ru.instamart.kraken.service;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.qa.Api;
import ru.instamart.qa.model.Setting;
import ru.instamart.qa.model.response.QaSessionResponse;

public enum QaService {

    INSTANCE;

    private final Api api;

    QaService() {
        this.api = new Api(new Setting(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH));
    }

    /**
     * Создает сессию пользователя
     * @param password - пароль для пользователя
     * @return {@link QaSessionResponse}
     */
    public QaSessionResponse createSession(final String password) {
        return api.getSessionService().getSession(password);
    }

    //TODO: Not implemented
    public void deleteSession(final String sessionId) {
        api.getSessionService().deleteSession(sessionId);
    }
}
