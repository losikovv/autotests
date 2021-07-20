package ru.instamart.reforged.admin.block.side_menu;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SideMenuCheck extends Check, SideMenuElement {


    @Step("Открытие storesDropdown")
    default void storesDropdownOpen(){
        storesDropdown.scrollTo();

        do{waitAction().shouldBeVisible(storesDropdown).click();}
        while (!waitAction().shouldBeVisible(regions).isDisplayed());
    }

    @Step("Сворачивание storesDropdown")
    default void storesDropdownClose(){
        storesDropdown.scrollTo();

        do{waitAction().shouldBeVisible(storesDropdown).click();}
        while (!waitAction().shouldNotBeVisible(regions));
    }

    @Step("Нажатие на regions")
    default void regionsClick(){
        regions.scrollTo();
        waitAction().shouldBeVisible(regions).click();
    }

    @Step("Нажатие на retailers")
    default void retailersClick(){
        retailers.scrollTo();
        waitAction().shouldBeVisible(retailers).click();
    }

    @Step("Нажатие на shipmentArea")
    default void shipmentAreaClick(){
        shipmentArea.scrollTo();
        waitAction().shouldBeVisible(shipmentArea).click();
    }

    @Step("Открытие ordersDropdown")
    default void ordersDropdownOpen(){
        storesDropdown.scrollTo();

        do{waitAction().shouldBeVisible(ordersDropdown).click();}
        while (!waitAction().shouldBeVisible(logisticControl).isDisplayed());
    }

    @Step("Сворачивание ordersDropdown")
    default void ordersDropdownClose(){
        storesDropdown.scrollTo();

        do{waitAction().shouldBeVisible(ordersDropdown).click();}
        while (!waitAction().shouldNotBeVisible(logisticControl));
    }

    @Step("Нажатие на logisticControl")
    default void logisticControlClick(){
        logisticControl.scrollTo();
        waitAction().shouldBeVisible(logisticControl).click();
    }

    @Step("Нажатие на rootsParameters")
    default void rootsParametersClick(){
        rootsParameters.scrollTo();
        waitAction().shouldBeVisible(rootsParameters).click();
    }

    @Step("Нажатие на multipleOrder")
    default void multipleOrderClick(){
        multipleOrder.scrollTo();
        waitAction().shouldBeVisible(multipleOrder).click();
    }


    @Step("Открытие contentDropdown")
    default void contentDropdownOpen(){
        contentDropdown.scrollTo();

        do{waitAction().shouldBeVisible(contentDropdown).click();}
        while (!waitAction().shouldBeVisible(products).isDisplayed());
    }

    @Step("Сворачивание contentDropdown")
    default void contentDropdownClose(){
        contentDropdown.scrollTo();

        do{waitAction().shouldBeVisible(contentDropdown).click();}
        while (!waitAction().shouldNotBeVisible(products));
    }

    @Step("Нажатие на products")
    default void productsClick(){
        products.scrollTo();
        waitAction().shouldBeVisible(products).click();
    }

    @Step("Нажатие на goodsOptions")
    default void goodsOptionsClick(){
        goodsOptions.scrollTo();
        waitAction().shouldBeVisible(goodsOptions).click();
    }

    @Step("Нажатие на properties")
    default void propertiesClick(){
        properties.scrollTo();
        waitAction().shouldBeVisible(properties).click();
    }

    @Step("Нажатие на brands")
    default void brandsClick(){
        brands.scrollTo();
        waitAction().shouldBeVisible(brands).click();
    }

    @Step("Нажатие на manufacturers")
    default void manufacturersClick(){
        manufacturers.scrollTo();
        waitAction().shouldBeVisible(manufacturers).click();
    }

    @Step("Нажатие на manufacturersCountries")
    default void manufacturersCountriesClick(){
        manufacturingCountries.scrollTo();
        waitAction().shouldBeVisible(manufacturingCountries).click();
    }

    @Step("Нажатие на categories")
    default void categoriesClick(){
        categories.scrollTo();
        waitAction().shouldBeVisible(categories).click();
    }

    @Step("Нажатие на contentImport")
    default void contentImportClick(){
        contentImport.scrollTo();
        waitAction().shouldBeVisible(contentImport).click();
    }

    @Step("Нажатие на settings")
    default void settingsClick(){
        settings.scrollTo();
        waitAction().shouldBeVisible(settings).click();
    }


    @Step("Открытие marketingDropdown")
    default void marketingDropdownOpen(){
        marketingDropdown.scrollTo();

        do{waitAction().shouldBeVisible(marketingDropdown).click();}
        while (!waitAction().shouldBeVisible(promoCards).isDisplayed());
    }

    @Step("Сворачивание marketingDropdown")
    default void marketingDropdownClose(){
        marketingDropdown.scrollTo();

        do{waitAction().shouldBeVisible(marketingDropdown).click();}
        while (!waitAction().shouldNotBeVisible(promoCards));
    }

    @Step("Нажатие на promoCards")
    default void promoCardsClick(){
        promoCards.scrollTo();
        waitAction().shouldBeVisible(promoCards).click();
    }

    @Step("Нажатие на promoActions")
    default void promoActionsClick(){
        promoActions.scrollTo();
        waitAction().shouldBeVisible(promoActions).click();
    }

    @Step("Нажатие на welcomeBanners")
    default void welcomeBannersClick(){
        welcomeBanners.scrollTo();
        waitAction().shouldBeVisible(welcomeBanners).click();
    }

    @Step("Нажатие на ads")
    default void adsClick(){
        ads.scrollTo();
        waitAction().shouldBeVisible(ads).click();
    }

    @Step("Нажатие на carts")
    default void cartsClick(){
        carts.scrollTo();
        waitAction().shouldBeVisible(carts).click();
    }

    @Step("Нажатие на bonusCards")
    default void bonusCardsClick(){
        bonusCards.scrollTo();
        waitAction().shouldBeVisible(bonusCards).click();
    }

    @Step("Нажатие на retailerPrograms")
    default void retailerProgramsClick(){
        retailerPrograms.scrollTo();
        waitAction().shouldBeVisible(retailerPrograms).click();
    }

    @Step("Нажатие на referralProgram")
    default void referralProgramClick(){
        referralProgram.scrollTo();
        waitAction().shouldBeVisible(referralProgram).click();
    }

    @Step("Нажатие на newCities")
    default void newCitiesClick(){
        newCities.scrollTo();
        waitAction().shouldBeVisible(newCities).click();
    }

    @Step("Нажатие на inAppBanners")
    default void inAppBannersClick(){
        inAppBanners.scrollTo();
        waitAction().shouldBeVisible(inAppBanners).click();
    }

    @Step("Нажатие на bonusCount")
    default void bonusCountClick(){
        bonusCount.scrollTo();
        waitAction().shouldBeVisible(bonusCount).click();
    }

    @Step("Нажатие на redirects")
    default void redirectsClick(){
        redirects.scrollTo();
        waitAction().shouldBeVisible(redirects).click();
    }

    @Step("Нажатие на sampling")
    default void samplingClick(){
        sampling.scrollTo();
        waitAction().shouldBeVisible(sampling).click();
    }

    @Step("Нажатие на marketingCategories")
    default void marketingCategoriesClick(){
        marketingCategories.scrollTo();
        waitAction().shouldBeVisible(marketingCategories).click();
    }


    @Step("Открытие staffDropdown")
    default void staffDropdownOpen(){
        staffDropdown.scrollTo();

        do{waitAction().shouldBeVisible(staffDropdown).click();}
        while (!waitAction().shouldBeVisible(shifts).isDisplayed());
    }

    @Step("Сворачивание staffDropdown")
    default void staffDropdownClose(){
        staffDropdown.scrollTo();

        do{waitAction().shouldBeVisible(staffDropdown).click();}
        while (!waitAction().shouldNotBeVisible(shifts));
    }


    @Step("Нажатие на workSchedule")
    default void workScheduleClick(){
        shifts.scrollTo();
        waitAction().shouldBeVisible(shifts).click();
    }

    @Step("Нажатие на tariffs")
    default void tariffsClick(){
        tariffs.scrollTo();
        waitAction().shouldBeVisible(tariffs).click();
    }

    @Step("Нажатие на collectors")
    default void collectorsClick(){
        collectors.scrollTo();
        waitAction().shouldBeVisible(collectors).click();
    }

    @Step("Нажатие на partnersInput")
    default void partnersImportClick(){
        partnersImport.scrollTo();
        waitAction().shouldBeVisible(partnersImport).click();
    }

    @Step("Нажатие на users")
    default void usersClick(){
        users.scrollTo();
        waitAction().shouldBeVisible(users).click();
    }

    @Step("Нажатие на pages")
    default void pagesClick(){
        pages.scrollTo();
        waitAction().shouldBeVisible(pages).click();
    }

    @Step("Нажатие на companies")
    default void companiesClick(){
        companies.scrollTo();
        waitAction().shouldBeClickable(companies).click();
    }
}
