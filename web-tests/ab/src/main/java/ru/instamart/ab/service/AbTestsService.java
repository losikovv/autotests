package ru.instamart.ab.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.ab.AbApiClient;
import ru.instamart.ab.Endpoint;
import ru.instamart.ab.model.response.AuthorsResponse;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public final class AbTestsService {

    private final AbApiClient client;

    public AuthorsResponse getAuthor() {
        try {
            return client.get(AuthorsResponse.class, Endpoint.AUTHORS);
        } catch (IOException e) {
            log.error("", e);
        }
        return new AuthorsResponse();
    }
}
