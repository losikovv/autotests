package ru.instamart.tests.api.v2.endpoints;

import instamart.api.requests.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.responses.v2.FavoritesListItemsResponse;
import instamart.core.common.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class FavoritesList extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.apiV2().authorisation(AppManager.session.admin);
    }

    @Test(  description = "Получаем любимые товары",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 13)
    public void getFavoritesListItems() {
        response = ApiV2Requests.getFavoritesListItems(1);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(FavoritesListItemsResponse.class).getItems(), "Не вернулись любимые товары");
    }
}
