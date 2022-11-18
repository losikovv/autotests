package ru.instamart.test.reforged.stf_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Межритейлерный поиск")
public final class MultiretailerSearchTests {
    private final ApiHelper apiHelper = new ApiHelper();
    private final String searchText = "сыр";

    @CaseId(3847)
    @Test(description = "Недоступность межритейлерного поиска при невыбранном адресе", groups = {STF_PROD_S})
    public void testMultisearchUnavailableWithoutSetAddress() {
        home().goToPage();
        home().checkPageIsAvailable();
        home().checkLoginButtonIsVisible();

        home().checkAddressBlockContainerIsVisible();
        home().interactMultisearchHeader().checkMultisearchInputNotVisible();
    }

    @CaseId(3848)
    @Test(description = "Тест успешной отработки межритейлерного саджестора", groups = {STF_PROD_S})
    public void testMultisearchPositiveInSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3849)
    @Test(description = "Открытие карточки товара из межритейлерного саджестора", groups = {STF_PROD_S})
    public void testOpenProductCardFromMultisearchSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3850)
    @Test(description = "Добавление товара в корзину из межритейлерного саджестора", groups = {STF_PROD_S})
    public void testAddProductToCartFromMultisearchSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3851)
    @Test(description = "Успешная отработки межритейлерного поиска", groups = {STF_PROD_S})
    public void testMultisearchPositiveOnPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3852)
    @Test(description = "Открытие карточки товара в межритейлерном поиске", groups = {STF_PROD_S})
    public void testOpenProductCardFromMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3853)
    @Test(description = "Добавление товара в корзину в межритейлерном поиске", groups = {STF_PROD_S})
    public void testAddProductToCartFromMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.checkoutAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3854)
    @Test(description = "Добавление товара в любимые в межритейлерном поиске", groups = {STF_PROD_S})
    public void testAddProductToFavouritesFromMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3855)
    @Test(description = "Переключения слага ритейлера в межритейлерном поиске", groups = {STF_PROD_S})
    public void testSwitchRetailerOnMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3856)
    @Test(description = "Переключение категорий в межритейлерном поиске", groups = {STF_PROD_S})
    public void testCategoryFiltersOnMultisearchPage() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3858)
    @Test(description = "Недоступность межритейлерного поиска при самовывозе на главной", groups = {STF_PROD_S})
    public void testCheckMultisearchNotAvailableOnMainPageViaPickup() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
        home().checkLoginButtonIsVisible();

        home().openLoginModal();
        home().interactAuthModal().authViaPhone(userData);
        home().interactAuthModal().checkModalIsNotVisible();

        home().interactMultisearchHeader().switchToPickup();
        home().interactMultisearchHeader().checkPickupActive();

        home().interactMultisearchHeader().checkMultisearchInputNotVisible();
    }

    @CaseId(3859)
    @Test(description = "Недоступность межритейлерного поиска при самовывозе в результатах поиска", groups = {STF_PROD_S})
    public void testCheckMultisearchNotAvailableOnMultiearchPageViaPickup() {
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
        multiSearch().interactMultisearchHeader().switchToPickup();
        multiSearch().interactMultisearchHeader().interactAddressLarge().checkYmapsReady();

        multiSearch().interactMultisearchHeader().interactAddressLarge().clickTakeFromHere();
        multiSearch().interactMultisearchHeader().interactAddressLarge().checkAddressModalIsNotVisible();

        shop().waitPageLoad();
        shop().interactHeader().checkSearchButtonVisible();
    }

    @CaseId(3860)
    @Test(description = "Переключение слага ритейлера в межритейлерном саджесторе", groups = {STF_PROD_S})
    public void testSwitchRetailerInSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        home().goToPage();
        home().checkPageIsAvailable();
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

    @CaseId(3857)
    @Test(description = "Переключение слага ритейлера при переходе на товары из блоков 'С этим товаром смотрят' и 'Похожие'", groups = {STF_PROD_S})
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
