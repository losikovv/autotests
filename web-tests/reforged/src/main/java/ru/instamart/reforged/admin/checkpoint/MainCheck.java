package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.admin.block.AuthoredHeader;
import ru.instamart.reforged.admin.block.SideMenu;
import ru.instamart.reforged.admin.element.MainElement;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.component.Component;

import static ru.instamart.reforged.core.Kraken.waitAction;

@RequiredArgsConstructor
public class MainCheck implements Check {

    protected final MainElement element;
    protected final AuthoredHeader headerElement;
    protected  final SideMenu sideMenuElement;
    protected Component component;


    @Step("Проверяем, что отображается adminNavigationTitle")
    public void checkAdminNavigationTitle() {
        waitAction().shouldBeVisible(headerElement.adminNavigationTitle()).isDisplayed();
    }

    @Step("Проверяем, что отображается adminName")
    public void checkAdminName() {
        waitAction().shouldBeVisible(headerElement.adminName()).isDisplayed();
    }

    @Step("Проверяем, что отображается adminAvatar")
    public void checkAdminAvatar() {
        waitAction().shouldBeVisible(headerElement.adminAvatar()).isDisplayed();
    }

    @Step("Проверяем, что отображается logoutDropdown")
    public void checkLogoutDropdown() {
        waitAction().shouldBeVisible(headerElement.logoutDropdown()).isDisplayed();
    }

    @Step("Пользователь авторизовался")
    public void checkAuth() {
        waitAction().shouldBeVisible(element.user()).isDisplayed();
    }

    @Step("Открытие storesDropdown")
    public void storesDropdownOpen(){
        sideMenuElement.storesDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.storesDropdown()).click();}
            while (!waitAction().shouldBeVisible(sideMenuElement.regions()).isDisplayed());
    }

    @Step("Сворачивание storesDropdown")
    public void storesDropdownClose(){
        sideMenuElement.storesDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.storesDropdown()).click();}
            while (!waitAction().shouldNotBeVisible(sideMenuElement.regions()));
    }

    @Step("Нажатие на regions")
    public void regionsClick(){
        sideMenuElement.regions().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.regions()).click();
    }

    @Step("Нажатие на retailers")
    public void retailersClick(){
        sideMenuElement.retailers().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.retailers()).click();
    }

    @Step("Нажатие на shipmentArea")
    public void shipmentAreaClick(){
        sideMenuElement.shipmentArea().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.shipmentArea()).click();
    }

    @Step("Открытие ordersDropdown")
    public void ordersDropdownOpen(){
        sideMenuElement.storesDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.ordersDropdown()).click();}
        while (!waitAction().shouldBeVisible(sideMenuElement.logisticControl()).isDisplayed());
    }

    @Step("Сворачивание ordersDropdown")
    public void ordersDropdownClose(){
        sideMenuElement.storesDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.ordersDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.logisticControl()));
    }

    @Step("Нажатие на logisticControl")
    public void logisticControlClick(){
        sideMenuElement.logisticControl().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.logisticControl()).click();
    }

    @Step("Нажатие на rootsParameters")
    public void rootsParametersClick(){
        sideMenuElement.rootsParameters().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.rootsParameters()).click();
    }

    @Step("Нажатие на multipleOrder")
    public void multipleOrderClick(){
        sideMenuElement.multipleOrder().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.multipleOrder()).click();
    }


    @Step("Открытие contentDropdown")
    public void contentDropdownOpen(){
        sideMenuElement.contentDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.contentDropdown()).click();}
        while (!waitAction().shouldBeVisible(sideMenuElement.products()).isDisplayed());
    }

    @Step("Сворачивание contentDropdown")
    public void contentDropdownClose(){
        sideMenuElement.contentDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.contentDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.products()));
    }

    @Step("Нажатие на products")
    public void productsClick(){
        sideMenuElement.products().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.products()).click();
    }

    @Step("Нажатие на goodsOptions")
    public void goodsOptionsClick(){
        sideMenuElement.goodsOptions().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.goodsOptions()).click();
    }

    @Step("Нажатие на properties")
    public void propertiesClick(){
        sideMenuElement.properties().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.properties()).click();
    }

    @Step("Нажатие на brands")
    public void brandsClick(){
        sideMenuElement.brands().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.brands()).click();
    }

    @Step("Нажатие на manufacturers")
    public void manufacturersClick(){
        sideMenuElement.manufacturers().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.manufacturers()).click();
    }

    @Step("Нажатие на manufacturersCountries")
    public void manufacturersCountriesClick(){
        sideMenuElement.manufacturersCountries().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.manufacturersCountries()).click();
    }

    @Step("Нажатие на categories")
    public void categoriesClick(){
        sideMenuElement.categories().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.categories()).click();
    }

    @Step("Нажатие на contentImport")
    public void contentImportClick(){
        sideMenuElement.contentImport().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.contentImport()).click();
    }

    @Step("Нажатие на settings")
    public void settingsClick(){
        sideMenuElement.settings().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.settings()).click();
    }


    @Step("Открытие marketingDropdown")
    public void marketingDropdownOpen(){
        sideMenuElement.marketingDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.marketingDropdown()).click();}
        while (!waitAction().shouldBeVisible(sideMenuElement.promoCards()).isDisplayed());
    }

    @Step("Сворачивание marketingDropdown")
    public void marketingDropdownClose(){
        sideMenuElement.marketingDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.marketingDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.promoCards()));
    }

    @Step("Нажатие на promoCards")
    public void promoCardsClick(){
        sideMenuElement.promoCards().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.promoCards()).click();
    }

    @Step("Нажатие на promoActions")
    public void promoActionsClick(){
        sideMenuElement.promoActions().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.promoActions()).click();
    }

    @Step("Нажатие на welcomeBanners")
    public void welcomeBannersClick(){
        sideMenuElement.welcomeBanners().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.welcomeBanners()).click();
    }

    @Step("Нажатие на ads")
    public void adsClick(){
        sideMenuElement.ads().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.ads()).click();
    }

    @Step("Нажатие на carts")
    public void cartsClick(){
        sideMenuElement.carts().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.carts()).click();
    }

    @Step("Нажатие на bonusCards")
    public void bonusCardsClick(){
        sideMenuElement.bonusCards().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.bonusCards()).click();
    }

    @Step("Нажатие на retailerPrograms")
    public void retailerProgramsClick(){
        sideMenuElement.retailerPrograms().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.retailerPrograms()).click();
    }

    @Step("Нажатие на referralProgram")
    public void referralProgramClick(){
        sideMenuElement.referralProgram().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.referralProgram()).click();
    }

    @Step("Нажатие на newCities")
    public void newCitiesClick(){
        sideMenuElement.newCities().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.newCities()).click();
    }

    @Step("Нажатие на inAppBanners")
    public void inAppBannersClick(){
        sideMenuElement.inAppBanners().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.inAppBanners()).click();
    }

    @Step("Нажатие на bonusCount")
    public void bonusCountClick(){
        sideMenuElement.bonusCount().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.bonusCount()).click();
    }

    @Step("Нажатие на redirects")
    public void redirectsClick(){
        sideMenuElement.redirects().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.redirects()).click();
    }

    @Step("Нажатие на sampling")
    public void samplingClick(){
        sideMenuElement.sampling().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.sampling()).click();
    }

    @Step("Нажатие на marketingCategories")
    public void marketingCategoriesClick(){
        sideMenuElement.marketingCategories().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.marketingCategories()).click();
    }


    @Step("Открытие staffDropdown")
    public void staffDropdownOpen(){
        sideMenuElement.staffDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.staffDropdown()).click();}
        while (!waitAction().shouldBeVisible(sideMenuElement.workSchedule()).isDisplayed());
    }

    @Step("Сворачивание staffDropdown")
    public void staffDropdownClose(){
        sideMenuElement.staffDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.staffDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.workSchedule()));
    }


    @Step("Нажатие на workSchedule")
    public void workScheduleClick(){
        sideMenuElement.workSchedule().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.workSchedule()).click();
    }

    @Step("Нажатие на tariffs")
    public void tariffsClick(){
        sideMenuElement.tariffs().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.tariffs()).click();
    }

    @Step("Нажатие на collectors")
    public void collectorsClick(){
        sideMenuElement.collectors().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.collectors()).click();
    }

    @Step("Нажатие на partnersInput")
    public void partnersImportClick(){
        sideMenuElement.partnersImport().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.partnersImport()).click();
    }

    @Step("Нажатие на users")
    public void usersClick(){
        sideMenuElement.users().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.users()).click();
    }

    @Step("Нажатие на pages")
    public void pagesClick(){
        sideMenuElement.pages().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.pages()).click();
    }

    @Step("Нажатие на companies")
    public void companiesClick(){
        sideMenuElement.companies().scrollTo();
        waitAction().shouldBeClickable(sideMenuElement.companies()).click();
    }

}
