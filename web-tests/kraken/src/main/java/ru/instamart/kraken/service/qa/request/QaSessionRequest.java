package ru.instamart.kraken.service.qa.request;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.instamart.kraken.service.qa.QaEndpoint;
import ru.instamart.kraken.service.qa.QaRequestBase;
import ru.instamart.kraken.service.qa.model.QaSession;
import ru.sbermarket.common.Mapper;

public final class QaSessionRequest extends QaRequestBase {


    @Step("{method} " + QaEndpoint.SESSION)
    public static Response POST(final String password) {
        final var user = new QaSession.User();
        user.setPassword(password);
        final var request = new QaSession();
        request.setUser(user);

        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(Mapper.INSTANCE.objectToString(request))
                .post(QaEndpoint.SESSION);
    }

    @Step("{method} " + QaEndpoint.DELETE_SESSION)
    public static Response DELETE(final String userId) {
        return givenWithAuth()
                .delete(QaEndpoint.DELETE_SESSION, userId);
    }
}
