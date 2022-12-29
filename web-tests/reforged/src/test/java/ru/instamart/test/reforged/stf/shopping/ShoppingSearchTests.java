package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.enums.ShopUrl;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.SMOKE_STF;
import static ru.instamart.reforged.stf.page.StfRouter.search;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Поиск товаров")
public final class ShoppingSearchTests {

    ApiHelper apiHelper = new ApiHelper();

    @TmsLink("1609")
    @Story("Проверка наличия элементов")
    @Test(description = "Тест валидации элементов поиска", groups = REGRESSION_STF)
    public void successValidateSearch() {
        shop().goToPage();
        shop().interactHeader().checkSearchContainerVisible();
        shop().interactHeader().checkSearchInputVisible();
        shop().interactHeader().checkSearchButtonVisible();
        shop().assertAll();
    }

    @TmsLink("2585")
    @Story("Негативные сценарии")
    @Test(description = "Тест поиска по запросу, не возвращающему результатов", groups = REGRESSION_STF)
    public void successSearchForNonExistingItem() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().fillSearch("смысл жизни");
        shop().interactHeader().clickSearchButton();
        search().checkEmptySearchPlaceholderVisible();
    }

    @TmsLink("2586")
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров", groups = REGRESSION_STF)
    public void successSearchItem() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        search().checkPageIsAvailable();
        search().checkSearchProductGridVisible();
    }

    @TmsLink("2587")
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров c использованием категорийных саджестов", groups = {REGRESSION_STF, SMOKE_STF})
    public void successSearchItemUsingCategorySuggests() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().fillSearch("сыры");
        shop().interactHeader().checkSuggesterVisible();
        shop().interactHeader().clickShowAllSearchResults();
        search().checkPageIsAvailable();
        search().checkSearchTitle("сыры");
    }

    @TmsLink("2588")
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров c использованием товарных саджестов", groups = {REGRESSION_STF, SMOKE_STF})
    public void successSearchItemUsingSuggests() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        shop().interactHeader().clickOnFirstSuggesterSearchResult();
        search().interactProductCard().checkProductCardVisible();
    }

    @TmsLink("2989")
    @Story("Позитивные сценарии")
    @Test(description = "Изменение кнопки показать результат от выбранной категории", groups = {REGRESSION_STF, SMOKE_STF})
    public void changeAmountOnButtonSearchResult() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

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
    @Story("Позитивные сценарии")
    @Test(description = "Работоспособность стрелочки пролистывающей категории", groups = {REGRESSION_STF})
    public void swipeCategoryItemInSuggester() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

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
    @Test(description = "Удаление поискового запроса по крестику в поиске", groups = {REGRESSION_STF, SMOKE_STF})
    public void clearSearchBar() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        shop().interactHeader().clearSearchInput();
        shop().interactHeader().checkSearchBarEmpty();
    }

    @TmsLink("1615")
    @Story("Негативные сценарии")
    @Test(description = "Тест поиска по очень длинному запросу, не возвращающему результатов", groups = REGRESSION_STF)
    public void successSearchItemWithLongQuery() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().fillSearch(Generate.string(1000));
        shop().interactHeader().clickSearchButton();
        search().checkEmptySearchPlaceholderVisible();
    }

    @TmsLink("1581")
    @Test(description = "Добавление товара в корзину из поиска товаров", groups = {REGRESSION_STF, SMOKE_STF})
    public void successAddItemToCartFromSearchResults() {
        shop().goToPage();
        shop().interactHeader().clickToSelectAddressFirstTime();

        shop().interactAddressLarge().checkYmapsReady();
        shop().interactAddressLarge().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddressLarge().selectFirstAddress();
        shop().interactAddressLarge().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddressLarge().clickSave();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage();
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
    @Test(description = "Работоспособность сортировки товаров", groups = {REGRESSION_STF, SMOKE_STF})
    public void successApplySort() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().fillSearch("печенье");
        shop().interactHeader().clickSearchButton();

        search().checkSearchImgLoaded();
        search().selectSort("Сначала дешевые");

        search().refresh();
        search().checkSearchImgLoaded();
        search().checkSortEnabled("Сначала дешевые");
        search().checkPriceAscSortCorrect();
    }

    @TmsLink("2590")
    @Test(description = "Фильтрация товаров", groups = {REGRESSION_STF})
    public void successApplyFilters() {
        shop().goToPage();
        shop().goToPage(ShopUrl.METRO.getUrl() + "/c/new-molochnyie-produkty/moloko/korovie");
        search().waitPageLoad();

        search().checkProductsQuantityVisible();
        final int startProductsQuantity = search().returnSearchProductsQuantity();

        search().clickToDiscountFilter();
        search().waitPageLoad();

        search().refresh();
        search().waitPageLoad();

        search().checkProductsQuantityVisible();
        final int discountFilterProductsQuantity = search().returnSearchProductsQuantity();

        search().checkQuantitiesNotEquals(startProductsQuantity, discountFilterProductsQuantity);

        shop().goToPage(ShopUrl.METRO.getUrl() + "/c/new-molochnyie-produkty/moloko/korovie");
        search().waitPageLoad();

        search().clickOnFilter("Ультрапастеризованное");
        search().checkFilterActivePinVisible();
        search().refresh();

        search().waitPageLoad();

        search().checkProductsQuantityVisible();
        final int someFilterProductsQuantity = search().returnSearchProductsQuantity();

        search().checkQuantitiesNotEquals(discountFilterProductsQuantity, someFilterProductsQuantity);
    }

    @TmsLink("2591")
    @Test(description = "Сортировка + фильтрация товаров: сначала дешевые, по популярности", groups = {REGRESSION_STF})
    public void successApplyFiltersAndSortCheapAsc() {
        shop().goToPage();
        shop().goToPage("okey/search?keywords=кофе&sid=128");

        search().checkSearchImgLoaded();

        search().selectSort("Сначала дешевые");

        search().refresh();
        search().checkSearchImgLoaded();

        search().scrollDown();
        search().checkPageScrolled();
        search().checkPriceAscSortCorrect();

        shop().goToPage("okey/search?keywords=кофе&sid=128");

        search().checkSortEnabled("По популярности");
        search().refresh();
        search().checkSearchImgLoaded();
        search().scrollDown();
        search().checkPageScrolled();
    }

    @TmsLink("2591")
    @Test(description = "Сортировка + фильтрация товаров: сначала дорогие, скидки + убывание, конкретный бренд", groups = {REGRESSION_STF})
    public void successApplyFiltersAndSortExpensiveDesc() {
        shop().goToPage();
        shop().goToPage("okey/search?keywords=чай&sid=128");

        search().checkSearchImgLoaded();

        search().selectSort("Сначала дорогие");

        search().refresh();
        search().checkSearchImgLoaded();

        search().scrollDown();
        search().checkPageScrolled();
        search().checkPriceDescSortCorrect();

        search().scrollUp();

        search().clickToDiscountFilter();
        search().checkPriceDescSortCorrect();

        shop().goToPage("okey/search?keywords=чай&sid=128");
        search().checkSearchImgLoaded();

        search().checkSortEnabled("По популярности");
        search().clickOnFilter("Greenfield");

        search().checkSearchImgLoaded();
    }

    @TmsLink("2592")
    @Test(description = "При применении фильтра для выданных товаров блокируются другие фильтры (неприменимые к ним)", groups = {REGRESSION_STF})
    public void successApplyOtherFilters() {

        shop().goToPage(ShopUrl.METRO.getUrl() + "/c/new-molochnyie-produkty/moloko/korovie");
        shop().waitPageLoad();

        search().clickToDiscountFilter();
        search().waitPageLoad();

        search().checkFilterDisabled("Стерилизованное");
    }

    @TmsLink("2737")
    @Test(description = "Отображение алкоголя в результатах поиска при неподтверждении возраста: нажатие за пределы модального окна", groups = {REGRESSION_STF})
    public void alcoholSearchModalClose() {
        final UserData userData = UserManager.getQaUser();
        apiHelper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().fillSearch("вино красное");
        shop().interactHeader().checkSuggesterVisible();
        search().interactHeader().checkAlcoStubInSuggest();
        search().interactHeader().clickSearchButton();

        search().checkSearchProductGridVisible();
        search().checkSearchProductsSkeletonNotVisible();
        search().checkSearchProductsSpinnerNotVisible();

        search().checkAlcoStubInProductsSearch();

        search().interactDisclaimerModal().clickOffTheModalToCloseDisclaimer();

        search().clickOnFirstSearchResult();
        search().interactDisclaimerModal().clickOffTheModalToCloseDisclaimer();

        search().interactProductCard().checkProductCardVisible();
        search().interactProductCard().checkAlcoAlertVisible();
        search().interactProductCard().checkAlcoStubVisible();
        search().interactProductCard().checkReserveButtonVisible();
        search().interactProductCard().clickOnReserve();

        search().interactDisclaimerModal().checkDisclaimerModalNotAnimated();
        search().interactDisclaimerModal().clickOffTheModalToCloseDisclaimer();
        search().interactDisclaimerModal().checkDisclaimerModalNotVisible();

        search().interactProductCard().checkAlcoAlertVisible();
        search().interactProductCard().checkAlcoStubVisible();
        search().interactProductCard().clickOnClose();

        search().checkAlcoStubInProductsSearch();
        search().clickOnFirstSearchResult();

        search().interactProductCard().checkProductCardVisible();
        search().interactProductCard().checkAlcoAlertVisible();
        search().interactProductCard().checkAlcoStubVisible();
        search().interactProductCard().checkReserveButtonVisible();

        search().assertAll();
    }
}
