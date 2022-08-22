package ru.instamart.kraken.service.ab.request;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.kraken.common.Mapper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.service.ab.AbEndpoint;
import ru.instamart.kraken.service.ab.AbRequestBase;
import ru.instamart.kraken.service.ab.model.JwtObtain;

public final class ObtainJwtRequest extends AbRequestBase {

    @Step("{method} /" + AbEndpoint.OBTAIN)
    public static Response POST(final UserData userData) {
        final var jwtObtain = new JwtObtain();
        jwtObtain.setEmail(userData.getEmail());
        jwtObtain.setPassword(userData.getPassword());
        return givenWithSpec()
                .body(Mapper.INSTANCE.objectToMap(jwtObtain))
                .post(AbEndpoint.OBTAIN);
    }
}
