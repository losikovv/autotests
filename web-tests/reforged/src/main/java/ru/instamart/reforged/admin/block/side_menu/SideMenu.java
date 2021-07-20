package ru.instamart.reforged.admin.block.side_menu;

import io.qameta.allure.Step;

import static ru.instamart.reforged.core.Kraken.waitAction;

public final class SideMenu implements SideMenuCheck{
    @Step("Открытие storesDropdown")
    public void storesDropdownOpen(){
        storesDropdown.scrollTo();

        do{waitAction().shouldBeVisible(storesDropdown).click();}
        while (!waitAction().shouldBeVisible(regions).isDisplayed());
    }

    @Step("Сворачивание storesDropdown")
     public void storesDropdownClose(){
        storesDropdown.scrollTo();

        do{waitAction().shouldBeVisible(storesDropdown).click();}
        while (!waitAction().shouldNotBeVisible(regions));
    }

    @Step("Нажатие на regions")
    public void regionsClick(){
        regions.scrollTo();
        waitAction().shouldBeVisible(regions).click();
    }

    @Step("Нажатие на retailers")
    public void retailersClick(){
        retailers.scrollTo();
        waitAction().shouldBeVisible(retailers).click();
    }

    @Step("Нажатие на shipmentArea")
    public void shipmentAreaClick(){
        shipmentArea.scrollTo();
        waitAction().shouldBeVisible(shipmentArea).click();
    }

    @Step("Открытие ordersDropdown")
    public void ordersDropdownOpen(){
        storesDropdown.scrollTo();

        do{waitAction().shouldBeVisible(ordersDropdown).click();}
        while (!waitAction().shouldBeVisible(logisticControl).isDisplayed());
    }

    @Step("Сворачивание ordersDropdown")
    public void ordersDropdownClose(){
        storesDropdown.scrollTo();

        do{waitAction().shouldBeVisible(ordersDropdown).click();}
        while (!waitAction().shouldNotBeVisible(logisticControl));
    }

    @Step("Нажатие на logisticControl")
    public void logisticControlClick(){
        logisticControl.scrollTo();
        waitAction().shouldBeVisible(logisticControl).click();
    }

    @Step("Нажатие на rootsParameters")
    public void rootsParametersClick(){
        rootsParameters.scrollTo();
        waitAction().shouldBeVisible(rootsParameters).click();
    }

    @Step("Нажатие на multipleOrder")
    public void multipleOrderClick(){
        multipleOrder.scrollTo();
        waitAction().shouldBeVisible(multipleOrder).click();
    }


    @Step("Открытие contentDropdown")
    public void contentDropdownOpen(){
        contentDropdown.scrollTo();

        do{waitAction().shouldBeVisible(contentDropdown).click();}
        while (!waitAction().shouldBeVisible(products).isDisplayed());
    }

    @Step("Сворачивание contentDropdown")
    public void contentDropdownClose(){
        contentDropdown.scrollTo();

        do{waitAction().shouldBeVisible(contentDropdown).click();}
        while (!waitAction().shouldNotBeVisible(products));
    }

    @Step("Нажатие на products")
    public void productsClick(){
        products.scrollTo();
        waitAction().shouldBeVisible(products).click();
    }

    @Step("Нажатие на goodsOptions")
    public void goodsOptionsClick(){
        goodsOptions.scrollTo();
        waitAction().shouldBeVisible(goodsOptions).click();
    }

    @Step("Нажатие на properties")
    public void propertiesClick(){
        properties.scrollTo();
        waitAction().shouldBeVisible(properties).click();
    }

    @Step("Нажатие на brands")
    public void brandsClick(){
        brands.scrollTo();
        waitAction().shouldBeVisible(brands).click();
    }

    @Step("Нажатие на manufacturers")
    public void manufacturersClick(){
        manufacturers.scrollTo();
        waitAction().shouldBeVisible(manufacturers).click();
    }

    @Step("Нажатие на manufacturersCountries")
    public void manufacturersCountriesClick(){
        manufacturingCountries.scrollTo();
        waitAction().shouldBeVisible(manufacturingCountries).click();
    }

    @Step("Нажатие на categories")
    public void categoriesClick(){
        categories.scrollTo();
        waitAction().shouldBeVisible(categories).click();
    }

    @Step("Нажатие на contentImport")
    public void contentImportClick(){
        contentImport.scrollTo();
        waitAction().shouldBeVisible(contentImport).click();
    }

    @Step("Нажатие на settings")
    public void settingsClick(){
        settings.scrollTo();
        waitAction().shouldBeVisible(settings).click();
    }


    @Step("Открытие marketingDropdown")
    public void marketingDropdownOpen(){
        marketingDropdown.scrollTo();

        do{waitAction().shouldBeVisible(marketingDropdown).click();}
        while (!waitAction().shouldBeVisible(promoCards).isDisplayed());
    }

    @Step("Сворачивание marketingDropdown")
    public void marketingDropdownClose(){
        marketingDropdown.scrollTo();

        do{waitAction().shouldBeVisible(marketingDropdown).click();}
        while (!waitAction().shouldNotBeVisible(promoCards));
    }

    @Step("Нажатие на promoCards")
    public void promoCardsClick(){
        promoCards.scrollTo();
        waitAction().shouldBeVisible(promoCards).click();
    }

    @Step("Нажатие на promoActions")
    public void promoActionsClick(){
        promoActions.scrollTo();
        waitAction().shouldBeVisible(promoActions).click();
    }

    @Step("Нажатие на welcomeBanners")
    public void welcomeBannersClick(){
        welcomeBanners.scrollTo();
        waitAction().shouldBeVisible(welcomeBanners).click();
    }

    @Step("Нажатие на ads")
    public void adsClick(){
        ads.scrollTo();
        waitAction().shouldBeVisible(ads).click();
    }

    @Step("Нажатие на carts")
    public void cartsClick(){
        carts.scrollTo();
        waitAction().shouldBeVisible(carts).click();
    }

    @Step("Нажатие на bonusCards")
    public void bonusCardsClick(){
        bonusCards.scrollTo();
        waitAction().shouldBeVisible(bonusCards).click();
    }

    @Step("Нажатие на retailerPrograms")
    public void retailerProgramsClick(){
        retailerPrograms.scrollTo();
        waitAction().shouldBeVisible(retailerPrograms).click();
    }

    @Step("Нажатие на referralProgram")
    public void referralProgramClick(){
        referralProgram.scrollTo();
        waitAction().shouldBeVisible(referralProgram).click();
    }

    @Step("Нажатие на newCities")
    public void newCitiesClick(){
        newCities.scrollTo();
        waitAction().shouldBeVisible(newCities).click();
    }

    @Step("Нажатие на inAppBanners")
    public void inAppBannersClick(){
        inAppBanners.scrollTo();
        waitAction().shouldBeVisible(inAppBanners).click();
    }

    @Step("Нажатие на bonusCount")
    public void bonusCountClick(){
        bonusCount.scrollTo();
        waitAction().shouldBeVisible(bonusCount).click();
    }

    @Step("Нажатие на redirects")
    public void redirectsClick(){
        redirects.scrollTo();
        waitAction().shouldBeVisible(redirects).click();
    }

    @Step("Нажатие на sampling")
    public void samplingClick(){
        sampling.scrollTo();
        waitAction().shouldBeVisible(sampling).click();
    }

    @Step("Нажатие на marketingCategories")
    public void marketingCategoriesClick(){
        marketingCategories.scrollTo();
        waitAction().shouldBeVisible(marketingCategories).click();
    }


    @Step("Открытие staffDropdown")
    public void staffDropdownOpen(){
        staffDropdown.scrollTo();

        do{waitAction().shouldBeVisible(staffDropdown).click();}
        while (!waitAction().shouldBeVisible(shifts).isDisplayed());
    }

    @Step("Сворачивание staffDropdown")
    public void staffDropdownClose(){
        staffDropdown.scrollTo();

        do{waitAction().shouldBeVisible(staffDropdown).click();}
        while (!waitAction().shouldNotBeVisible(shifts));
    }


    @Step("Нажатие на workSchedule")
    public void workScheduleClick(){
        shifts.scrollTo();
        waitAction().shouldBeVisible(shifts).click();
    }

    @Step("Нажатие на tariffs")
    public void tariffsClick(){
        tariffs.scrollTo();
        waitAction().shouldBeVisible(tariffs).click();
    }

    @Step("Нажатие на collectors")
    public void collectorsClick(){
        collectors.scrollTo();
        waitAction().shouldBeVisible(collectors).click();
    }

    @Step("Нажатие на partnersInput")
    public void partnersImportClick(){
        partnersImport.scrollTo();
        waitAction().shouldBeVisible(partnersImport).click();
    }

    @Step("Нажатие на users")
    public void usersClick(){
        users.scrollTo();
        waitAction().shouldBeVisible(users).click();
    }

    @Step("Нажатие на pages")
    public void pagesClick(){
        pages.scrollTo();
        waitAction().shouldBeVisible(pages).click();
    }

    @Step("Нажатие на companies")
    public void companiesClick(){
        companies.scrollTo();
        waitAction().shouldBeClickable(companies).click();
    }
}
