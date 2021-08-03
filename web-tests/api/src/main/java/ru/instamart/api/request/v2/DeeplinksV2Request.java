package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class DeeplinksV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.DEEPLINKS)
    public static Response GET() {
        return givenWithSpec()
                .get(ApiV2EndPoints.DEEPLINKS);
    }

    @Step("{method} /" + ApiV2EndPoints.Deeplinks.BY_URL)
    public static Response GET(final String webUrl) {
        return givenWithSpec()
                .get(ApiV2EndPoints.Deeplinks.BY_URL, webUrl);
    }

}
