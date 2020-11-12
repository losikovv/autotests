package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.FavoritesListItemsResponse;
import instamart.core.common.AppManager;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class FavoritesList extends RestBase {

    @BeforeMethod(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        apiV2.authorisation(AppManager.session.admin);
    }

    @CaseId(13)
    @Test(  description = "Получаем любимые товары",
            groups = {"api-v2-smoke"})
    public void getFavoritesListItems() {
        response = ApiV2Requests.getFavoritesListItems(1);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(FavoritesListItemsResponse.class).getItems(), "Не вернулись любимые товары");
    }
}
