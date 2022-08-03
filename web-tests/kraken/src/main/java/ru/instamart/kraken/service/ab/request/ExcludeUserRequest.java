package ru.instamart.kraken.service.ab.request;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.kraken.service.ab.AbEndpoint;
import ru.instamart.kraken.service.ab.AbRequestBase;
import ru.instamart.kraken.service.ab.model.ExcludedUser;
import ru.sbermarket.common.Mapper;

public final class ExcludeUserRequest extends AbRequestBase {

    @Step("{method} /" + AbEndpoint.EXCLUDED_USER)
    public static Response POST(final ExcludedUser excludedUser) {
        return givenWithAuth()
                .body(Mapper.INSTANCE.objectToMap(excludedUser))
                .post(AbEndpoint.EXCLUDED_USER);
    }
}
