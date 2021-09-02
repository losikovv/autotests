package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class LegalEntityV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.LEGAL_ENTITY)
    public static Response GET() {
        return givenWithSpec()
                .get(ApiV2EndPoints.LEGAL_ENTITY);
    }

    public final static class ByINN {

        @Step("{method} /" + ApiV2EndPoints.LegalEntity.BY_INN)
        public static Response GET(String inn) {
            return givenWithSpec()
                    .get(ApiV2EndPoints.LegalEntity.BY_INN, inn);
        }
    }
}
