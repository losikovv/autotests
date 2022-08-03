package ru.instamart.kraken.service.ab;

import ru.instamart.kraken.service.ab.model.ExcludedUser;
import ru.instamart.kraken.service.ab.request.ExcludeUserRequest;
import ru.instamart.kraken.service.ab.response.ExcludedUserResponse;

import java.util.UUID;

import static java.util.Objects.isNull;

public enum AbService {

    INSTANCE;

    public ExcludedUserResponse excludeUser(String anonymousId) {
        if (isNull(anonymousId)) {
            anonymousId = UUID.randomUUID().toString();
        }
        final var exclude = new ExcludedUser();
        exclude.setAnonymousID(anonymousId);
        final var response = ExcludeUserRequest.POST(exclude);
        response.then().statusCode(200);
        return response.as(ExcludedUserResponse.class);
    }
}
