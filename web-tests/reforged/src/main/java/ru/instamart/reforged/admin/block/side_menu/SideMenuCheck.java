package ru.instamart.reforged.admin.block.side_menu;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SideMenuCheck extends Check, SideMenuElement {


    @Step("Открытие storesDropdown")
    default void storesDropdownOpen(){
        storesDropdown().scrollTo();

        do{waitAction().shouldBeVisible(storesDropdown).click();}
        while (!waitAction().shouldBeVisible(regions).isDisplayed());
    }

    @Step("Сворачивание storesDropdown")
    default void storesDropdownClose(){
        sideMenuElement.storesDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.storesDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.regions()));
    }

    @Step("Нажатие на regions")
    default void regionsClick(){
        sideMenuElement.regions().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.regions()).click();
    }

    @Step("Нажатие на retailers")
    default void retailersClick(){
        sideMenuElement.retailers().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.retailers()).click();
    }

    @Step("Нажатие на shipmentArea")
    default void shipmentAreaClick(){
        sideMenuElement.shipmentArea().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.shipmentArea()).click();
    }

    @Step("Открытие ordersDropdown")
    default void ordersDropdownOpen(){
        sideMenuElement.storesDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.ordersDropdown()).click();}
        while (!waitAction().shouldBeVisible(sideMenuElement.logisticControl()).isDisplayed());
    }

    @Step("Сворачивание ordersDropdown")
    default void ordersDropdownClose(){
        sideMenuElement.storesDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.ordersDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.logisticControl()));
    }

    @Step("Нажатие на logisticControl")
    default void logisticControlClick(){
        sideMenuElement.logisticControl().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.logisticControl()).click();
    }

    @Step("Нажатие на rootsParameters")
    default void rootsParametersClick(){
        sideMenuElement.rootsParameters().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.rootsParameters()).click();
    }

    @Step("Нажатие на multipleOrder")
    default void multipleOrderClick(){
        sideMenuElement.multipleOrder().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.multipleOrder()).click();
    }


    @Step("Открытие contentDropdown")
    default void contentDropdownOpen(){
        sideMenuElement.contentDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.contentDropdown()).click();}
        while (!waitAction().shouldBeVisible(sideMenuElement.products()).isDisplayed());
    }

    @Step("Сворачивание contentDropdown")
    default void contentDropdownClose(){
        sideMenuElement.contentDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.contentDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.products()));
    }

    @Step("Нажатие на products")
    default void productsClick(){
        sideMenuElement.products().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.products()).click();
    }

    @Step("Нажатие на goodsOptions")
    default void goodsOptionsClick(){
        sideMenuElement.goodsOptions().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.goodsOptions()).click();
    }

    @Step("Нажатие на properties")
    default void propertiesClick(){
        sideMenuElement.properties().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.properties()).click();
    }

    @Step("Нажатие на brands")
    default void brandsClick(){
        sideMenuElement.brands().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.brands()).click();
    }

    @Step("Нажатие на manufacturers")
    default void manufacturersClick(){
        sideMenuElement.manufacturers().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.manufacturers()).click();
    }

    @Step("Нажатие на manufacturersCountries")
    default void manufacturersCountriesClick(){
        sideMenuElement.manufacturingCountries().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.manufacturingCountries()).click();
    }

    @Step("Нажатие на categories")
    default void categoriesClick(){
        sideMenuElement.categories().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.categories()).click();
    }

    @Step("Нажатие на contentImport")
    default void contentImportClick(){
        sideMenuElement.contentImport().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.contentImport()).click();
    }

    @Step("Нажатие на settings")
    default void settingsClick(){
        sideMenuElement.settings().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.settings()).click();
    }


    @Step("Открытие marketingDropdown")
    default void marketingDropdownOpen(){
        sideMenuElement.marketingDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.marketingDropdown()).click();}
        while (!waitAction().shouldBeVisible(sideMenuElement.promoCards()).isDisplayed());
    }

    @Step("Сворачивание marketingDropdown")
    default void marketingDropdownClose(){
        sideMenuElement.marketingDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.marketingDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.promoCards()));
    }

    @Step("Нажатие на promoCards")
    default void promoCardsClick(){
        sideMenuElement.promoCards().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.promoCards()).click();
    }

    @Step("Нажатие на promoActions")
    default void promoActionsClick(){
        sideMenuElement.promoActions().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.promoActions()).click();
    }

    @Step("Нажатие на welcomeBanners")
    default void welcomeBannersClick(){
        sideMenuElement.welcomeBanners().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.welcomeBanners()).click();
    }

    @Step("Нажатие на ads")
    default void adsClick(){
        sideMenuElement.ads().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.ads()).click();
    }

    @Step("Нажатие на carts")
    default void cartsClick(){
        sideMenuElement.carts().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.carts()).click();
    }

    @Step("Нажатие на bonusCards")
    default void bonusCardsClick(){
        sideMenuElement.bonusCards().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.bonusCards()).click();
    }

    @Step("Нажатие на retailerPrograms")
    default void retailerProgramsClick(){
        sideMenuElement.retailerPrograms().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.retailerPrograms()).click();
    }

    @Step("Нажатие на referralProgram")
    default void referralProgramClick(){
        sideMenuElement.referralProgram().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.referralProgram()).click();
    }

    @Step("Нажатие на newCities")
    default void newCitiesClick(){
        sideMenuElement.newCities().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.newCities()).click();
    }

    @Step("Нажатие на inAppBanners")
    default void inAppBannersClick(){
        sideMenuElement.inAppBanners().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.inAppBanners()).click();
    }

    @Step("Нажатие на bonusCount")
    default void bonusCountClick(){
        sideMenuElement.bonusCount().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.bonusCount()).click();
    }

    @Step("Нажатие на redirects")
    default void redirectsClick(){
        sideMenuElement.redirects().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.redirects()).click();
    }

    @Step("Нажатие на sampling")
    default void samplingClick(){
        sideMenuElement.sampling().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.sampling()).click();
    }

    @Step("Нажатие на marketingCategories")
    default void marketingCategoriesClick(){
        sideMenuElement.marketingCategories().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.marketingCategories()).click();
    }


    @Step("Открытие staffDropdown")
    default void staffDropdownOpen(){
        sideMenuElement.staffDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.staffDropdown()).click();}
        while (!waitAction().shouldBeVisible(sideMenuElement.shifts()).isDisplayed());
    }

    @Step("Сворачивание staffDropdown")
    default void staffDropdownClose(){
        sideMenuElement.staffDropdown().scrollTo();

        do{waitAction().shouldBeVisible(sideMenuElement.staffDropdown()).click();}
        while (!waitAction().shouldNotBeVisible(sideMenuElement.shifts()));
    }


    @Step("Нажатие на workSchedule")
    default void workScheduleClick(){
        sideMenuElement.shifts().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.shifts()).click();
    }

    @Step("Нажатие на tariffs")
    default void tariffsClick(){
        sideMenuElement.tariffs().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.tariffs()).click();
    }

    @Step("Нажатие на collectors")
    default void collectorsClick(){
        sideMenuElement.collectors().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.collectors()).click();
    }

    @Step("Нажатие на partnersInput")
    default void partnersImportClick(){
        sideMenuElement.partnersImport().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.partnersImport()).click();
    }

    @Step("Нажатие на users")
    default void usersClick(){
        sideMenuElement.users().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.users()).click();
    }

    @Step("Нажатие на pages")
    default void pagesClick(){
        sideMenuElement.pages().scrollTo();
        waitAction().shouldBeVisible(sideMenuElement.pages()).click();
    }

    @Step("Нажатие на companies")
    default void companiesClick(){
        sideMenuElement.companies().scrollTo();
        waitAction().shouldBeClickable(sideMenuElement.companies()).click();
    }
}
