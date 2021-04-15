package ru.instamart.tests.ui.administration;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.common.lib.Pages;
import ru.instamart.ui.modules.Administration;
import ru.instamart.ui.modules.User;
import ru.instamart.ui.objectsmap.Elements;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

@Epic("Админка STF")
@Feature("Базовый функционал и навигация в админке")
public class BasicAdministrationTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeClass(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        User.Logout.quicklyAdmin();
        kraken.reach().admin();
        User.Auth.withEmail(UserManager.getDefaultAdmin());
    }

    @CaseId(419)
    @Story("Тест доступности корневых разделов админки")
    @Test(  description = "Тест доступности корневых разделов админки",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successCheckAdminSectionsAvailability() {
//        kraken.get().page(Pages.Admin.login());
        baseChecks.checkPageIsAvailable(Pages.Admin.shipments());
        baseChecks.checkPageIsAvailable(Pages.Admin.retailers());
        baseChecks.checkPageIsAvailable(Pages.Admin.products());
        baseChecks.checkPageIsAvailable(Pages.Admin.imports());
        baseChecks.checkPageIsAvailable(Pages.Admin.settings());
        baseChecks.checkPageIsAvailable(Pages.Admin.marketing());
        baseChecks.checkPageIsAvailable(Pages.Admin.staff());
        baseChecks.checkPageIsAvailable(Pages.Admin.users());
        baseChecks.checkPageIsAvailable(Pages.Admin.pages());
    }

    @CaseId(420)
    @Story("Тест доступности вьюхи oktell")
    @Test(  description = "Тест доступности вьюхи oktell",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successCheckOktellViewAvailability() {
        baseChecks.checkPageIsAvailable(Pages.Admin.oktell());
    }

    @CaseId(416)
    @Story("Проверка наличия элементов в шапке админки")
    @Test(  description = "Проверка наличия элементов в шапке админки",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successValidateHeader() {
        baseChecks.checkIsElementPresent(Elements.Administration.Header.adminNavigationTitle());
        baseChecks.checkIsElementPresent(Elements.Administration.Header.adminName());
        baseChecks.checkIsElementPresent(Elements.Administration.Header.adminAvatar());
        baseChecks.checkIsElementPresent(Elements.Administration.Header.logoutDropdown());
    }

    @CaseId(4)
    @Story("Тест валидности ссылок навигационного меню в шапке админки")
    @Test(  description = "Тест валидности ссылок навигационного меню в шапке админки",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successValidateNavigationMenu() {
        Administration.AdminNavigation.openMenuDropdown("Заказы");
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Управление логистикой"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Параметры маршрутизации"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Мульти заказ"));

        Administration.AdminNavigation.openMenuDropdown("Магазины");
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Регионы"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Ритейлеры"));
        baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Территория доставки"));

        Administration.AdminNavigation.openMenuDropdown("Контент");
        baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Продукты"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Товарные опции"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Свойства"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Бренды"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Производители"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Страны производства"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Категории"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Импорт"));

        baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Настройки"));

        Administration.AdminNavigation.openMenuDropdown("Персонал");
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Смены"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Тарифы"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Сборщики"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Импорт партнеров"));
        baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Пользователи"));
        baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Страницы"));
        baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Компании"));

        Administration.AdminNavigation.openMenuDropdown("Маркетинг");
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Промо карточки"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Промоакции"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Welcome баннеры"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Реклама"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Корзины"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Бонусные карты"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Программы ритейлеров"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Реферальная программа"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Новые города"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("In app баннеры"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Бонусный счет"));
//            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Редиректы"));

            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Семплинг"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuElement("Маркетинговые категории"));


    }
}
