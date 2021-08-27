package ru.instamart.qa.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.qa.ApiClient;
import ru.instamart.qa.Endpoint;
import ru.instamart.qa.model.request.QaSessionRequest;
import ru.instamart.qa.model.response.QaSessionResponse;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public final class SessionService {

    private final ApiClient client;

    public QaSessionResponse getSession(final String password) {
        final QaSessionRequest.User user = new QaSessionRequest.User();
        user.setPassword(password);
        final QaSessionRequest request = new QaSessionRequest();
        request.setUser(user);

        try {
            return client.post(QaSessionResponse.class, Endpoint.SESSION, request);
        } catch (IOException e) {
            log.error("FATAL: Can't obtain user session", e);
        }

        return new QaSessionResponse();
    }

    public void deleteSession(final String sessionId) {
        try {
            client.delete(Endpoint.DELETE_SESSION + sessionId);
        } catch (IOException e) {
            log.error("FATAL: Can't delete user session={}", sessionId, e);
        }
    }
}
