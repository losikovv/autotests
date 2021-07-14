package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Pages;

import static ru.instamart.reforged.admin.AdminRout.*;


@Epic("Админка STF")
    @Feature("Базовый функционал и навигация в админке")
    public final class BasicAdministrationTests {

        @BeforeClass(alwaysRun = true,
                description = "Выполняем шаги предусловий для теста")
        public void beforeTest() {
            login().goToPage();
            login().auth(UserManager.getDefaultAdmin());
        }

        @CaseId(419)
        @Story("Тест доступности корневых разделов админки")
        @Test(  description = "Тест доступности корневых разделов админки",
                groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
        ) public void successCheckAdminSectionsAvailability() {

        }

        @CaseId(420)
        @Story("Тест доступности вьюхи oktell")
        @Test(  description = "Тест доступности вьюхи oktell",
                groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
        ) public void successCheckOktellViewAvailability() {
            final String oktellUrl = Pages.Admin.oktell().getPath();
            main().goToPage();
            main().openSitePage(oktellUrl);
            pages().checkPageIsAvailable();
        }

        @CaseId(416)
        @Story("Проверка наличия элементов в шапке админки")
        @Test(  description = "Проверка наличия элементов в шапке админки",
                groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
        ) public void successValidateHeader() {
            main().goToPage();
            main().checkAdminNavigationTitle();
            main().checkAdminName();
            main().checkAdminAvatar();
            main().checkLogoutDropdown();
        }

        @CaseId(4)
        @Story("Тест валидности ссылок навигационного меню в шапке админки")
        @Test(  description = "Тест валидности ссылок навигационного меню в шапке админки",
                groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
        ) public void successValidateNavigationMenu() {

        }
}
