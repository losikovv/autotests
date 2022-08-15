package ru.instamart.kraken.service.qa;

import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.service.qa.request.QaSessionRequest;
import ru.instamart.kraken.service.qa.response.QaSessionResponse;

public enum QaService {

    INSTANCE;

    /**
     * Создает сессию пользователя
     *
     * @param password - пароль для пользователя
     * @return {@link QaSessionResponse}
     */
    public QaSessionResponse createSession(final String password) {
        final var response = QaSessionRequest.POST(password);
        response.then().statusCode(200);
        return response.as(QaSessionResponse.class);
    }

    /**
     * Удаляет пользователя
     *
     * @param userId - {@link UserData#getId()}
     */
    public void deleteSession(final String userId) {
        final var response = QaSessionRequest.DELETE(userId);
        response.then().statusCode(200);
    }
}
