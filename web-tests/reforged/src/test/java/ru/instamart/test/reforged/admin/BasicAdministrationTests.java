package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.UserManager;


import static ru.instamart.reforged.admin.AdminRout.*;
import static ru.instamart.reforged.stf.page.StfRouter.oktell;


@Epic("Админка STF")
@Feature("Базовый функционал и навигация в админке")
public final class BasicAdministrationTests {

    @BeforeClass(alwaysRun = true,
            description = "Выполняем шаги предусловий для теста")
    public void beforeTest() {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
        main().goToPage();
    }

    @CaseId(419)
    @Story("Тест доступности корневых разделов админки")
    @Test(description = "Тест доступности корневых разделов админки",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successCheckAdminSectionsAvailability() {
        shipments().goToPage();
        shipments().checkPageIsAvailable();
        retailers().goToPage();
        retailers().checkPageIsAvailable();
        products().goToPage();
        products().checkPageIsAvailable();
        imports().goToPage();
        imports().checkPageIsAvailable();
        settings().goToPage();
        settings().checkPageIsAvailable();
        marketing().goToPage();
        marketing().checkPageIsAvailable();
        staff().goToPage();
        staff().checkPageIsAvailable();
        users().goToPage();
        users().checkPageIsAvailable();
        pages().goToPage();
        pages().checkPageIsAvailable();
    }

    @CaseId(420)
    @Story("Тест доступности вьюхи oktell")
    @Test(description = "Тест доступности вьюхи oktell",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successCheckOktellViewAvailability() {
        oktell().goToPage();
        oktell().checkPageIsAvailable();
    }

    @CaseId(416)
    @Story("Проверка наличия элементов в шапке админки")
    @Test(description = "Проверка наличия элементов в шапке админки",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successValidateHeader() {
        main().checkAdminNavigationTitle();
        main().checkAdminName();
        main().checkAdminAvatar();
        main().checkLogoutDropdown();
    }

    @CaseId(4)
    @Story("Тест валидности ссылок навигационного меню в шапке админки")
    @Test(description = "Тест валидности ссылок навигационного меню в шапке админки",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successValidateNavigationMenu() {
        main().storesDropdownOpen();
        main().regionsClick();
        main().checkPageIsAvailable();
        main().retailersClick();
        main().checkPageIsAvailable();
        main().shipmentAreaClick();
        main().checkPageIsAvailable();
        main().storesDropdownClose();

        main().ordersDropdownOpen();
        main().logisticControlClick();
        main().checkPageIsAvailable();
        main().rootsParametersClick();
        main().checkPageIsAvailable();
        main().multipleOrderClick();
        main().checkPageIsAvailable();
        main().ordersDropdownClose();

        main().contentDropdownOpen();
        main().productsClick();
        main().checkPageIsAvailable();
        main().goodsOptionsClick();
        main().checkPageIsAvailable();
        main().propertiesClick();
        main().checkPageIsAvailable();
        main().brandsClick();
        main().checkPageIsAvailable();
        main().manufacturersClick();
        main().checkPageIsAvailable();
        main().manufacturersCountriesClick();
        main().checkPageIsAvailable();
        main().categoriesClick();
        main().checkPageIsAvailable();
        main().contentImportClick();
        main().checkPageIsAvailable();
        main().contentDropdownClose();

        main().settingsClick();
        main().checkPageIsAvailable();

        main().marketingDropdownOpen();
        main().promoCardsClick();
        main().checkPageIsAvailable();
        main().promoActionsClick();
        main().checkPageIsAvailable();
        main().welcomeBannersClick();
        main().checkPageIsAvailable();
        main().adsClick();
        main().checkPageIsAvailable();
        main().cartsClick();
        main().checkPageIsAvailable();
        main().bonusCardsClick();
        main().checkPageIsAvailable();
        main().retailerProgramsClick();
        main().checkPageIsAvailable();
        main().referralProgramClick();
        main().checkPageIsAvailable();
        main().newCitiesClick();
        main().checkPageIsAvailable();
        main().inAppBannersClick();
        main().checkPageIsAvailable();
        main().bonusCountClick();
        main().checkPageIsAvailable();
        main().redirectsClick();
        main().checkPageIsAvailable();
        main().samplingClick();
        main().checkPageIsAvailable();
        main().marketingCategoriesClick();
        main().checkPageIsAvailable();
        main().marketingDropdownClose();

        main().staffDropdownOpen();
        main().workScheduleClick();
        main().checkPageIsAvailable();
        main().tariffsClick();
        main().checkPageIsAvailable();
        main().collectorsClick();
        main().checkPageIsAvailable();
        main().partnersImportClick();
        main().checkPageIsAvailable();
        main().staffDropdownClose();

        main().usersClick();
        main().checkPageIsAvailable();

        main().pagesClick();
        main().checkPageIsAvailable();

        main().companiesClick();
        main().checkPageIsAvailable();
    }
}
