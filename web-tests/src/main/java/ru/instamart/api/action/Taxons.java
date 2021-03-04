package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Taxons {

    /**
     * Получение таксонов в выбранном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.TAXONS)
    public static Response GET(int sid) {
        return givenCatch().get(ApiV2EndPoints.TAXONS, sid);
    }

    /**
     * Получение конкретного таксона в выбранном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.Taxons.ID)
    public static Response GET(int taxonId, int sid) {
        return givenCatch().get(ApiV2EndPoints.Taxons.ID, taxonId, sid);
    }
}
