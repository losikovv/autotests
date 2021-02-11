package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.FavoritesListItemsResponse;
import instamart.core.listeners.ExecutionListenerImpl;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import instamart.api.condition.FavoritesCondition;

import static org.testng.Assert.assertNotNull;

@Listeners(ExecutionListenerImpl.class)
public class FavoritesList {

    /*@BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        if (!apiV2.authorized()) {
            final UserData user = UserManager.getUser();
            apiV2.registration(user);
            apiV2.authorisation(user);
        }
    }

    @CaseId(13)
    @Test(  description = "Получаем любимые товары",
            groups = {"api-v2-smoke"})
    public void getFavoritesListItems() {
        response = ApiV2Requests.FavoritesList.Items.GET(1);
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(FavoritesListItemsResponse.class).getItems(), "Не вернулись любимые товары");
    }*/

    @CaseId(13)
    @Test(  description = "Получаем любимые товары",
            groups = {"api-v2-smoke"}
            )
    public void foo() {
        FavoritesCondition
                .newTest()
                .registrationAndAuth(UserManager.getUser())
                .getFavoritesItems(1)
                .emptyFavoritesList();
    }
}
