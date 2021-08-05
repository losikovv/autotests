package ru.instamart.kraken.service;

import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
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

    public static void main(String[] args) {
        QaService service = new QaService();
        System.out.println(service.createSession("passw0rd"));
    }
}
