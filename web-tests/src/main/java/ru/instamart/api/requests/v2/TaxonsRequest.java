package ru.instamart.api.requests.v2;

import ru.instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class TaxonsRequest {

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
    @Step("{method} /" + ApiV2EndPoints.Taxons.ID)
    public static Response GET(final int taxonId, final int sid) {
        return givenCatch().get(ApiV2EndPoints.Taxons.ID, taxonId, sid);
    }
}
