package ru.instamart.ab.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.ab.AbApiClient;
import ru.instamart.ab.Endpoint;
import ru.instamart.ab.model.response.AbTests;
import ru.instamart.ab.model.response.AuthorsResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public final class AbTestsService {

    private final AbApiClient client;

    /**
     * Получить список авторов
     * @return - {@link AuthorsResponse}
     */
    public AuthorsResponse getAuthor() {
        try {
            return client.get(AuthorsResponse.class, Endpoint.AUTHORS);
        } catch (IOException e) {
            log.error("FATAL: Can't get authors", e);
        }
        return new AuthorsResponse();
    }

    /**
     * curl -X GET "http://bs-ab-admin.k-stage.sbermarket.tech/api/v1/ab-tests?limit=1000&offset=0&status=2"
     * -H  "accept: application/json"
     * -H "Authorization:JWT {access_token}"
     * @return - {@link AbTests}
     */
    public AbTests getAllAbTests() {
        try {
            return client.get(AbTests.class, Endpoint.AB_TESTS);
        } catch (IOException e) {
            log.error("FATAL: Can't obtain all tests", e);
        }
        return new AbTests();
    }
}
