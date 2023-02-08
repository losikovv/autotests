package ru.instamart.test.reforged.stf_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Межритейлерный поиск")
public final class MultiretailerSearchTests {

    private final ApiHelper apiHelper = new ApiHelper();
    private final String searchText = "сыр";

    @TmsLink("3847")
    @Test(description = "Недоступность межритейлерного поиска при невыбранном адресе", groups = {STF_PROD_S})
    public void testMultisearchUnavailableWithoutSetAddress() {
        home().goToPage();
        home().checkPageIsAvailable();
        home().checkLoginButtonIsVisible();

        home().checkAddressBlockContainerIsVisible();
        home().interactMultisearchHeader().checkMultisearchInputNotVisible();
    }

    @TmsLink("3848")
    @Issue("B2C-12914")
    @Test(description = "Тест успешной отработки межритейлерного саджестора", groups = {STF_PROD_S})
    public void testMultisearchPositiveInSuggester() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
        home().checkPageIsAvailable();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);

        home().interactMultisearchHeader().checkRetailersVisibleInSuggester();
        home().interactMultisearchHeader().checkProductsVisibleInSuggester();
        home().interactMultisearchHeader().checkSearchResultsContains(searchText);
    }

    @TmsLink("3849")
    @Test(description = "Открытие карточки товара из межритейлерного саджестора", groups = {STF_PROD_S})
    public void testOpenProductCardFromMultisearchSuggester() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().checkProductsVisibleInSuggester();
        home().interactMultisearchHeader().clickProductInSuggester(1);
        home().interactMultisearchHeader().interactProductCard().checkProductCardVisible();
    }

    @TmsLink("3850")
    @Test(description = "Добавление товара в корзину из межритейлерного саджестора", groups = {STF_PROD_S})
    public void testAddProductToCartFromMultisearchSuggester() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        home().goToPageWithAuth(userData);
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
    @Issue("B2C-12914")
    @Test(description = "Успешная отработка межритейлерного поиска", groups = {STF_PROD_S})
    public void testMultisearchPositiveOnPage() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
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
    @Test(description = "Открытие карточки товара в межритейлерном поиске", groups = {STF_PROD_S})
    public void testOpenProductCardFromMultisearchPage() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
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
    @Test(description = "Добавление товара в корзину в межритейлерном поиске", groups = {STF_PROD_S})
    public void testAddProductToCartFromMultisearchPage() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        home().goToPageWithAuth(userData);
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
    @Test(description = "Добавление товара в любимые в межритейлерном поиске", groups = {STF_PROD_S})
    public void testAddProductToFavouritesFromMultisearchPage() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().interactMultisearchHeader().checkMultisearchInputVisible();
        multiSearch().checkProductsCardsVisible();
        multiSearch().clickAddProductToFavouritesByPosition(1);

        multiSearch().interactMultisearchHeader().clickToFavourites();
        userFavorites().checkNotEmptyFavoritesProd();
    }

    @TmsLink("3855")
    @Issue("B2C-12914")
    @Test(description = "Переключения слага ритейлера в межритейлерном поиске", groups = {STF_PROD_S})
    public void testSwitchRetailerOnMultisearchPage() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
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
    @Issue("B2C-12914")
    @Test(description = "Переключение категорий в межритейлерном поиске", groups = {STF_PROD_S})
    public void testCategoryFiltersOnMultisearchPage() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().checkProductsCardsVisible();
        final var productListNames = multiSearch().getProductNames();
        multiSearch().checkSearchResultsContains(searchText);

        multiSearch().selectCategoryByPosition(2);
        multiSearch().checkProductsCardsVisible();
        multiSearch().checkSearchResultsContains(searchText);
        multiSearch().checkIfProductsListChanged(productListNames);
    }

    @TmsLink("3858")
    @Test(description = "Недоступность межритейлерного поиска при самовывозе на главной", groups = {STF_PROD_S})
    public void testCheckMultisearchNotAvailableOnMainPageViaPickup() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
        home().interactMultisearchHeader().switchToPickup();
        home().interactMultisearchHeader().checkPickupActive();

        home().interactMultisearchHeader().checkMultisearchInputNotVisible();
    }

    @TmsLink("3859")
    @Test(description = "Недоступность межритейлерного поиска при самовывозе в результатах поиска", groups = {STF_PROD_S})
    public void testCheckMultisearchNotAvailableOnMultiearchPageViaPickup() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
        home().interactMultisearchHeader().checkUserActionsButtonVisible();

        home().interactMultisearchHeader().checkMultisearchInputVisible();
        home().interactMultisearchHeader().fillMultisearch(searchText);
        home().interactMultisearchHeader().clickStartSearch();

        multiSearch().waitPageLoad();
        multiSearch().interactMultisearchHeader().switchToPickup();
        multiSearch().interactMultisearchHeader().interactAddressLarge().checkYmapsReady();

        multiSearch().interactMultisearchHeader().interactAddressLarge().clickTakeFromHere();
        multiSearch().interactMultisearchHeader().interactAddressLarge().checkAddressModalIsNotVisible();

        shop().waitPageLoad();
        shop().interactHeader().checkSearchButtonVisible();
    }

    @TmsLink("3860")
    @Issue("B2C-12914")
    @Test(description = "Переключение слага ритейлера в межритейлерном саджесторе", groups = {STF_PROD_S})
    public void testSwitchRetailerInSuggester() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
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
    @Test(description = "Переключение слага ритейлера при переходе на товары из блоков 'С этим товаром смотрят' и 'Похожие'", groups = {STF_PROD_S})
    public void testAddProductToCartFromFromRecommendationsViaAnotherProductCard() {
        final var userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPageWithAuth(userData);
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
