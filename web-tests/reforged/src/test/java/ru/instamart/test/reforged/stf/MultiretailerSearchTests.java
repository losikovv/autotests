package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.*;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Межритейлерный поиск")
public final class MultiretailerSearchTests {

    private final ApiHelper apiHelper = new ApiHelper();
    private final String searchText = "сыр";

    @TmsLink("3847")
    @Test(description = "Недоступность межритейлерного поиска при невыбранном адресе", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testMultisearchUnavailableWithoutSetAddress() {
        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().checkAddressBlockContainerIsVisible();
        home().interactMultisearchHeader().checkMultisearchInputNotVisible();
    }

    @TmsLink("3848")
    @Test(description = "Тест успешной отработки межритейлерного саджестора", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testMultisearchPositiveInSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);

        home().interactMultisearchHeader().checkRetailersVisibleInSuggester();
        home().interactMultisearchHeader().checkProductsVisibleInSuggester();
        home().interactMultisearchHeader().checkSearchResultsContains(searchText);
    }

    @TmsLink("3849")
    @Test(description = "Открытие карточки товара из межритейлерного саджестора", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testOpenProductCardFromMultisearchSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().checkProductsVisibleInSuggester();

        home().interactMultisearchHeader().clickProductInSuggester(1);

        home().interactMultisearchHeader().interactProductCard().checkProductCardVisible();
    }

    @TmsLink("3850")
    @Test(description = "Добавление товара в корзину из межритейлерного саджестора", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testAddProductToCartFromMultisearchSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().checkProductsVisibleInSuggester();

        home().interactMultisearchHeader().clickAddProductToCartInSuggester(1);

        home().interactMultisearchHeader().clickOutOffSuggester();
        home().interactMultisearchHeader().checkProductsNotVisibleInSuggester();

        home().interactMultisearchHeader().clickToCart();
        home().interactMultisearchHeader().interactCart().checkCartNotEmpty();
    }

    @TmsLink("3851")
    @Test(description = "Успешная отработки межритейлерного поиска", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testMultisearchPositiveOnPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().interactMultisearchHeader().checkMultisearchInputVisible();
        multiSearch().checkRetailerCardsVisible();
        multiSearch().checkProductsCardsVisible();
        multiSearch().checkSearchResultsContains(searchText);
    }

    @TmsLink("3852")
    @Test(description = "Открытие карточки товара в межритейлерном поиске", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testOpenProductCardFromMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().interactMultisearchHeader().checkMultisearchInputVisible();
        multiSearch().checkProductsCardsVisible();
        multiSearch().clickProductSnippetByPosition(1);

        multiSearch().interactProductCard().checkProductCardVisible();
    }

    @TmsLink("3853")
    @Test(description = "Добавление товара в корзину в межритейлерном поиске", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testAddProductToCartFromMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().interactMultisearchHeader().checkMultisearchInputVisible();
        multiSearch().checkProductsCardsVisible();
        multiSearch().clickAddProductToCartByPosition(1);

        multiSearch().interactMultisearchHeader().clickToCart();
        multiSearch().interactMultisearchHeader().interactCart().checkCartNotEmpty();
    }

    @TmsLink("3854")
    @Test(description = "Добавление товара в любимые в межритейлерном поиске", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testAddProductToFavouritesFromMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().interactMultisearchHeader().checkMultisearchInputVisible();
        multiSearch().checkProductsCardsVisible();
        multiSearch().clickAddProductToFavouritesByPosition(1);

        multiSearch().interactMultisearchHeader().clickToFavourites();
        userFavorites().checkNotEmptyFavorites();
    }

    @TmsLink("3855")
    @Test(description = "Переключения слага ритейлера в межритейлерном поиске", groups = {REGRESSION_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testSwitchRetailerOnMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().checkProductsCardsVisible();
        final var productListNames = multiSearch().getProductNames();
        multiSearch().checkSearchResultsContains(searchText);

        multiSearch().switchToRetailerByPosition(3);
        multiSearch().checkProductsCardsVisible();
        multiSearch().checkSearchResultsContains(searchText);
        multiSearch().checkIfProductsListChanged(productListNames);
    }

    @TmsLink("3856")
    @Test(description = "Переключение категорий в межритейлерном поиске", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testCategoryFiltersOnMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().checkProductsCardsVisible();
        final var productListNames = multiSearch().getProductNames();
        multiSearch().checkSearchResultsContains(searchText);

        multiSearch().selectCategoryByPosition(3);
        multiSearch().checkProductsCardsVisible();
        multiSearch().checkSearchResultsContains(searchText);
        multiSearch().checkIfProductsListChanged(productListNames);
    }

    @TmsLink("3858")
    @Test(description = "Недоступность межритейлерного поиска при самовывозе на главной", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testCheckMultisearchNotAvailableOnMainPageViaPickup() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().switchToPickup();
        home().interactMultisearchHeader().checkPickupActive();

        home().interactMultisearchHeader().checkMultisearchInputNotVisible();
    }

    @TmsLink("3859")
    @Test(description = "Недоступность межритейлерного поиска при самовывозе в результатах поиска", groups = {REGRESSION_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testCheckMultisearchNotAvailableOnMultiearchPageViaPickup() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();
        home().interactMultisearchHeader().checkUserActionsButtonVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().interactMultisearchHeader().switchToPickup();
        multiSearch().interactMultisearchHeader().interactAddressLarge().checkYmapsReady();
        multiSearch().interactMultisearchHeader().interactAddressLarge().clickShowAsList();
        multiSearch().interactMultisearchHeader().interactAddressLarge().checkPickupStoresModalVisible();
        multiSearch().interactMultisearchHeader().interactAddressLarge().selectRetailerByName("METRO");
        multiSearch().interactMultisearchHeader().interactAddressLarge().selectStoreByAddress(RestAddresses.Moscow.checkoutAddress().fullAddress().toString());
        multiSearch().interactMultisearchHeader().interactAddressLarge().checkAddressModalIsNotVisible();

        shop().waitPageLoad();
        shop().interactHeader().checkSearchButtonVisible();
    }

    @TmsLink("3860")
    @Test(description = "Переключение слага ритейлера в межритейлерном саджесторе", groups = {REGRESSION_STF, SMOKE_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testSwitchRetailerInSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        home().goToPage();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);

        home().interactMultisearchHeader().checkRetailersVisibleInSuggester();
        home().interactMultisearchHeader().checkProductsVisibleInSuggester();
        home().interactMultisearchHeader().checkSearchResultsContains(searchText);
        final var firstRetailerProductNames = home().interactMultisearchHeader().getProductNames();

        home().interactMultisearchHeader().switchToRetailerByPosition(2);
        home().interactMultisearchHeader().checkProductsVisibleInSuggester();
        home().interactMultisearchHeader().checkSearchResultsContains(searchText);
        home().interactMultisearchHeader().checkIfProductsListChanged(firstRetailerProductNames);
    }

    @TmsLink("3857")
    @Test(description = "Переключение слага ритейлера при переходе на товары из блоков 'С этим товаром смотрят' и 'Похожие'", groups = {REGRESSION_STF, MULTIRETAILER_SEARCH, MALSTROM})
    public void testAddProductToCartFromFromRecommendationsViaAnotherProductCard() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();
        home().interactMultisearchHeader().checkUserActionsButtonVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().checkProductsCardsVisible();
        multiSearch().clickProductSnippetByPosition(1);

        multiSearch().interactProductCard().checkProductCardVisible();
        var recommendationBlockName = "С этим товаром смотрят";
        multiSearch().interactProductCard().interactRetailRocket().checkRecommendationsBlockVisible(recommendationBlockName);
        var productNameInRecommendation = multiSearch().interactProductCard().interactRetailRocket().getFirstItemTitleInBlockByName(recommendationBlockName);
        multiSearch().interactProductCard().interactRetailRocket().openProductCardFirstItemInBlockByName(recommendationBlockName);

        multiSearch().interactProductCard().checkProductCardVisible();
        multiSearch().interactProductCard().checkProductTitle(productNameInRecommendation);
        multiSearch().interactProductCard().interactRetailRocket().checkRecommendationsBlockVisible(recommendationBlockName);
        multiSearch().interactProductCard().checkBuyButtonVisible();
    }
}
