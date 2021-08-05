package ru.instamart.kraken.service;

import ru.instamart.qa.Api;
import ru.instamart.qa.model.Setting;
import ru.instamart.qa.model.response.QaSessionResponse;

public final class QaService {

    private final Api api;

    private QaService() {
        this.api = new Api(new Setting(/*EnvironmentData.INSTANCE.getBasicUrl()*/"https://stf-kraken.k-stage.sbermarket.tech/"));
    }

    /**
     * Создает сессию пользователя
     * @param password - пароль для пользователя
     * @return {@link QaSessionResponse}
     */
    public QaSessionResponse createSession(final String password) {
        return api.getSessionService().getSession(password);
    }
}
