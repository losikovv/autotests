package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.FavoritesListItemsResponse;
import instamart.core.testdata.Users;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class FavoritesList extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        if (!apiV2.authorized()) {
            final UserData user = Users.apiUser();
            apiV2.registration(user);
            apiV2.authorisation(user);
        }
    }

    @CaseId(13)
    @Test(  description = "Получаем любимые товары",
            groups = {"api-v2-smoke"})
    public void getFavoritesListItems() {
        response = ApiV2Requests.FavoritesList.Items.GET(1);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(FavoritesListItemsResponse.class).getItems(), "Не вернулись любимые товары");
    }
}
