package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class TaxonsV2Request {

    /**
     * Получение таксонов в выбранном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.TAXONS)
    public static Response GET(final int sid) {
        return givenCatch().get(ApiV2EndPoints.TAXONS, sid);
    }

    /**
     * Получение конкретного таксона в выбранном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.Taxons.BY_ID)
    public static Response GET(final int taxonId, final int sid) {
        return givenCatch().get(ApiV2EndPoints.Taxons.BY_ID, taxonId, sid);
    }
}
