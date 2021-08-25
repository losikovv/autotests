package ru.instamart.kraken.service;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.qa.Api;
import ru.instamart.qa.model.Setting;
import ru.instamart.qa.model.response.QaSessionResponse;

public final class QaService {

    private final Api api;

    public QaService() {
        this.api = new Api(new Setting(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth()));
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
