package ru.instamart.test.reforged.stf_prod.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.search;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Поиск товаров")
public final class ShoppingSearchTests {

    private final ApiHelper apiHelper = new ApiHelper();

    @TmsLink("2587")
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров c использованием категорийных саджестов", groups = {STF_PROD_S})
    public void successSearchItemUsingCategorySuggests() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().waitPageLoad();
        shop().interactHeader().fillSearch("сыры");
        shop().interactHeader().checkSuggesterVisible();
        shop().interactHeader().clickShowAllSearchResultsProd();
        search().checkPageIsAvailable();
        search().checkSearchTitle("сыры");
    }

    @TmsLink("2588")
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров c использованием товарных саджестов", groups = {STF_PROD_S})
    public void successSearchItemUsingSuggests() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().waitPageLoad();
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisibleProd();
        shop().interactHeader().clickOnFirstSuggesterSearchResultProd();
        search().interactProductCard().checkProductCardVisible();
    }

    @TmsLink("2989")
    @Skip //В новом саджесторе нет вкладок
    @Story("Позитивные сценарии")
    @Test(description = "Изменение кнопки показать результат от выбранной категории", groups = {STF_PROD_S})
    public void changeAmountOnButtonSearchResult() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().waitPageLoad();
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        final String textOnButtonAllCategory = shop().interactHeader().getTextOnButtonSearchSuggester();
        shop().interactHeader().clickOnLastSuggesterCategory();
        final String textOnButtonSmthCategory = shop().interactHeader().getTextOnButtonSearchSuggester();
        shop().interactHeader().checkTextSearchButton(textOnButtonAllCategory, textOnButtonSmthCategory);
    }

    // В этом тесте выполняется по два раза swipeScrollTabHeadersRight/swipeScrollTabHeadersLeft
    //    // Потому что при одиночном скроле категория "Все сразу" не скрывается
    @TmsLink("2991")
    @Skip  //В новом саджесторе нет вкладок
    @Story("Позитивные сценарии")
    @Test(description = "Работоспособность стрелочки пролистывающей категории", groups = {STF_PROD_S})
    public void swipeCategoryItemInSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().waitPageLoad();
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        shop().interactHeader().swipeScrollTabHeadersRight();
        shop().interactHeader().swipeScrollTabHeadersRight();
        shop().interactHeader().checkCategoryAllInvisible();
        shop().interactHeader().swipeScrollTabHeadersLeft();
        shop().interactHeader().swipeScrollTabHeadersLeft();
        shop().interactHeader().checkCategoryAllVisible();
    }

    @TmsLink("3105")
    @Story("Позитивные сценарии")
    @Test(description = "Удаление поискового запроса по крестику в поиске", groups = {STF_PROD_S})
    public void clearSearchBar() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().waitPageLoad();
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisibleProd();
        shop().interactHeader().clearSearchInput();
        shop().interactHeader().checkSearchBarEmpty();
    }

    @TmsLink("1581")
    @Test(description = "Добавление товара в корзину из поиска товаров", groups = {STF_PROD_S})
    public void successAddItemToCartFromSearchResults() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddress();
        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.trainingAddressProd());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().waitPageLoad();
        shop().interactHeader().fillSearch("сыры");
        shop().interactHeader().clickSearchButton();
        search().checkSearchImgLoaded();
        search().interactHeader().checkEnteredAddressIsVisible();
        search().clickAddToCartFirstSearchResult();
        search().interactHeader().clickToCart();
        search().interactCart().checkCartOpen();
        search().interactCart().checkCartNotEmpty();
    }

    @TmsLink("2589")
    @Test(description = "Работоспособность сортировки товаров", groups = {STF_PROD_S})
    public void successApplySort() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultProdAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().waitPageLoad();
        shop().interactHeader().fillSearch("печенье");
        shop().interactHeader().clickSearchButton();

        search().waitPageLoad();
        search().selectSort("Сначала дешевые");

        search().waitPageLoad();
        search().checkPriceAscSortCorrect();
    }
}
