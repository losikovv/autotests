package ru.instamart.test.reforged.stf.shopping;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.Generate;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.test.reforged.BaseTest;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.search;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Поиск товаров")
public final class ShoppingSearchTests extends BaseTest {

    @CaseId(1609)
    @Story("Проверка наличия элементов")
    @Test(description = "Тест валидации элементов поиска", groups = "regression")
    public void successValidateSearch() {
        shop().goToPage(true);
        shop().interactHeader().checkSearchContainerVisible();
        shop().interactHeader().checkSearchInputVisible();
        shop().interactHeader().checkSearchButtonVisible();
    }

    @CaseId(2585)
    @Story("Негативные сценарии")
    @Test(description = "Тест поиска по запросу, не возвращающему результатов", groups = "regression")
    public void successSearchForNonExistingItem() {
        shop().goToPage(true);
        shop().interactHeader().fillSearch("смысл жизни");
        shop().interactHeader().clickSearchButton();
        search().checkEmptySearchPlaceholderVisible();
    }

    @CaseId(2586)
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров", groups = "regression")
    public void successSearchItem() {
        shop().goToPage(true);
        shop().interactHeader().fillSearch("молоко");
        shop().interactHeader().clickSearchButton();
        search().checkPageIsAvailable();
        search().checkSearchProductGridVisible();
    }

    // AB поиска
    @CaseId(2587)
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров c использованием категорийных саджестов", groups = {"smoke", "regression"})
    public void successSearchItemUsingCategorySuggests() {
        shop().goToPage(true);
        shop().interactHeader().fillSearch("сыры");
        shop().interactHeader().checkSuggesterVisible();
        shop().interactHeader().clickShowAllSearchResults();
        search().checkPageIsAvailable();
        search().checkSearchTitle("сыры");
    }

    @CaseId(2588)
    @Story("Позитивные сценарии")
    @Test(description = "Тест успешного поиска товаров c использованием товарных саджестов", groups = {"regression"})
    public void successSearchItemUsingSuggests() {
        shop().goToPage(true);
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        shop().interactHeader().clickOnFirstSuggesterSearchResult();
        search().interactProductCard().checkProductCardVisible();
    }

    @CaseId(2989)
    @Story("Позитивные сценарии")
    @Test(description = "Изменение кнопки показать результат от выбранной категории", groups = {"regression"})
    public void changeAmountOnButtonSearchResult() {
        shop().goToPage(true);
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        final String textOnButtonAllCategory = shop().interactHeader().getTextOnButtonSearchSuggester();
        shop().interactHeader().clickOnLastSuggesterCategory();
        final String textOnButtonSmthCategory = shop().interactHeader().getTextOnButtonSearchSuggester();
        shop().interactHeader().checkTextSearchButton(textOnButtonAllCategory, textOnButtonSmthCategory);
    }

    // В этом тесте выполняется по два раза swipeScrollTabHeadersRight/swipeScrollTabHeadersLeft
    //    // Потому что при одиночном скроле категория "Все сразу" не скрывается
    @CaseId(2991)
    @Story("Позитивные сценарии")
    @Test(description = "Работоспособность стрелочки пролистывающей категории", groups = {"regression"})
    public void swipeCategoryItemInSuggester() {
        shop().goToPage(true);
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        shop().interactHeader().swipeScrollTabHeadersRight();
        shop().interactHeader().swipeScrollTabHeadersRight();
        shop().interactHeader().checkCategoryAllInvisible();
        shop().interactHeader().swipeScrollTabHeadersLeft();
        shop().interactHeader().swipeScrollTabHeadersLeft();
        shop().interactHeader().checkCategoryAllVisible();
    }

    @CaseId(3105)
    @Story("Позитивные сценарии")
    @Test(description = "Удаление поискового запроса по крестику в поиске", groups = {"regression"})
    public void clearSearchBar() {
        shop().goToPage(true);
        shop().interactHeader().fillSearch("шоколад");
        shop().interactHeader().checkSearchSuggestsVisible();
        shop().interactHeader().clearSearchInput();
        shop().interactHeader().checkSearchBarEmpty();
    }

    @CaseId(1615)
    @Story("Негативные сценарии")
    @Test(description = "Тест поиска по очень длинному запросу, не возвращающему результатов", groups = "regression")
    public void successSearchItemWithLongQuery() {
        shop().goToPage(true);
        shop().interactHeader().fillSearch(Generate.string(1000));
        shop().interactHeader().clickSearchButton();
        search().checkEmptySearchPlaceholderVisible();
    }

    @CaseId(1581)
    @Test(description = "Добавление товара в корзину из поиска товаров", groups = "regression")
    public void successAddItemToCartFromSearchResults() {
        shop().goToPage(true);
        shop().interactHeader().clickToSelectAddressFirstTime();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().goToPage(true);
        shop().interactHeader().fillSearch("сыры");
        shop().interactHeader().clickSearchButton();
        search().checkSearchImgLoaded();
        search().interactHeader().checkEnteredAddressIsVisible();
        search().clickAddToCartFirstSearchResult();
        search().interactHeader().clickToCart();
        search().interactCart().checkCartOpen();
        search().interactCart().checkCartNotEmpty();
    }

    @CaseId(2589)
    @Test(description = "Работоспособность сортировки товаров", groups = {"regression"})
    public void successApplySort() {
        shop().goToPage(ShopUrl.OKEY, true);
        shop().addCookie(CookieFactory.COOKIE_ALERT);
        shop().interactHeader().fillSearch("кофе");
        shop().interactHeader().clickSearchButton();

        search().selectSort("Сначала дешевые");
        search().checkSortEnabled("Сначала дешевые");

        search().checkPriceAscSortCorrect();
    }

    @CaseId(2590)
    @Test(description = "Фильтрация товаров", groups = {"regression"})
    public void successApplyFilters() {
        shop().openSitePage(ShopUrl.METRO.getUrl() + "/c/new-molochnyie-produkty/moloko/korovie");
        search().waitPageLoad();
        search().checkSearchImgLoaded();

        final int startProductsQuantity = search().returnSearchProductsQuantity();

        search().clickToDiscountFilter();
        search().waitPageLoad();
        search().checkSearchImgLoaded();

        search().refresh();
        search().waitPageLoad();
        search().checkSearchImgLoaded();

        final int discountFilterProductsQuantity = search().returnSearchProductsQuantity();

        search().checkQuantitiesNotEquals(startProductsQuantity, discountFilterProductsQuantity);

        shop().openSitePage(ShopUrl.METRO.getUrl() + "/c/new-molochnyie-produkty/moloko/korovie");
        search().waitPageLoad();
        search().checkSearchImgLoaded();

        search().clickOnFilter("Ультрапастеризованное");
        search().checkFilterActivePinVisible();
        search().refresh();

        search().waitPageLoad();
        search().checkSearchImgLoaded();

        final int someFilterProductsQuantity = search().returnSearchProductsQuantity();
        search().checkQuantitiesNotEquals(discountFilterProductsQuantity, someFilterProductsQuantity);

        search().assertAll();
    }

    //TODO: Переписать и поставить баги ATST-873

    @CaseId(2591)
    @Test(description = "Сортировка + фильтрация товаров: сначала дешевые, по популярности", groups = {"regression"})
    public void successApplyFiltersAndSortCheapAsc() {
        shop().openSitePage("okey/search?keywords=кофе&sid=128");

        search().checkSearchImgLoaded();

        search().selectSort("Сначала дешевые");

        search().checkProductsStubVisible();
        search().checkProductsStubNotVisible();

        search().waitPageLoad();
        search().checkSearchImgLoaded();

        search().scrollDown();
        search().checkPageScrolled();
        search().checkPriceAscSortCorrect();

        shop().openSitePage("okey/search?keywords=кофе&sid=128");

        search().checkSortEnabled("По популярности");
        search().checkSearchImgLoaded();
        search().scrollDown();
        search().checkPageScrolled();
    }

    @CaseId(2591)
    @Test(description = "Сортировка + фильтрация товаров: сначала дорогие, скидки + убывание, конкретный бренд", groups = {"regression"})
    public void successApplyFiltersAndSortExpensiveDesc() {
        shop().openSitePage("okey/search?keywords=чай&sid=128");

        search().checkSearchImgLoaded();

        search().selectSort("Сначала дорогие");

        search().checkProductsStubVisible();
        search().checkProductsStubNotVisible();

        search().checkSearchImgLoaded();

        search().scrollDown();
        search().checkPageScrolled();
        search().checkPriceDescSortCorrect();

        search().scrollUp();

        search().clickToDiscountFilter();
        search().checkPriceDescSortCorrect();

        shop().openSitePage("okey/search?keywords=чай&sid=128");

        search().checkSearchImgLoaded();

        search().checkSortEnabled("По популярности");
        search().clickOnFilter("Greenfield");

        search().checkProductsStubVisible();
        search().checkProductsStubNotVisible();
    }

    @CaseId(2592)
    @Test(description = "При применении фильтра для выданных товаров блокируются другие фильтры (неприменимые к ним)", groups = {"regression"})
    public void successApplyOtherFilters() {
        shop().openSitePage(ShopUrl.METRO.getUrl() + "/c/new-molochnyie-produkty/moloko/korovie");
        search().waitPageLoad();
        search().checkSearchImgLoaded();

        search().clickToDiscountFilter();
        search().waitPageLoad();
        search().checkSearchImgLoaded();

        search().checkFilterDisabled("Стерилизованное");
    }

    @CaseId(2737)
    @Test(description = "Отображение алкоголя в результатах поиска при неподтверждении возраста: нажатие за пределы модального окна", groups = {"regression"})
    public void alcoholSearchModalClose() {
        shop().goToPage();
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
        search().interactProductCard().close();

        search().checkAlcoStubInProductsSearch();
        search().clickOnFirstSearchResult();

        search().interactProductCard().checkProductCardVisible();
        search().interactProductCard().checkAlcoAlertVisible();
        search().interactProductCard().checkAlcoStubVisible();
        search().interactProductCard().checkReserveButtonVisible();

        search().assertAll();
    }
}
