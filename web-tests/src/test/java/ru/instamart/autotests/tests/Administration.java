package ru.instamart.autotests.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;

import static ru.instamart.autotests.application.Config.enableAdministrationTests;
import static ru.instamart.autotests.appmanager.ApplicationManager.session;

// Тесты админки

public class Administration extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        kraken.get().page("metro");
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"acceptance","regression"},
            priority = 2000
    )
    public void noAccessToAdministrationWithoutAdminPrivileges() throws Exception {
        kraken.perform().loginAs(session.user);

        assertPageIsUnavailable(Pages.Admin.shipments());
        kraken.perform().quickLogout();
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест доступности админки пользователю c админ. правми",
            groups = {"acceptance","regression"},
            priority = 2001
    )
    public void successAccessAdministration() throws Exception {
        kraken.perform().loginAs(session.admin);

        assertPageIsAvailable(Pages.Admin.shipments());
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест доступности корневых разделов админки",
            groups = {"smoke","acceptance","regression"},
            priority = 2002
    )
    public void successCheckAdminPages() throws AssertionError {
        kraken.reach().admin();

        // TODO переделать на assertPagesAvailable(Pages.Admin.*)
        assertPageIsAvailable(Pages.Admin.shipments());
        assertPageIsAvailable(Pages.Admin.retailers());
        assertPageIsAvailable(Pages.Admin.products());
        assertPageIsAvailable(Pages.Admin.imports());
        assertPageIsAvailable(Pages.Admin.settings());
        assertPageIsAvailable(Pages.Admin.marketing());
        assertPageIsAvailable(Pages.Admin.staff());
        assertPageIsAvailable(Pages.Admin.users());
        assertPageIsAvailable(Pages.Admin.pages());
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Проверка наличия элементов в шапке админки",
            groups = {"smoke","acceptance","regression"},
            priority = 2003
    ) public void successValidateHeader() {
        kraken.reach().admin();
        kraken.check().elementPresence(Elements.Admin.Header.logo());
        kraken.check().elementPresence(Elements.Admin.Header.userAccount());
        kraken.check().elementPresence(Elements.Admin.Header.accountButton());
        kraken.check().elementPresence(Elements.Admin.Header.logoutButton());
        kraken.check().elementPresence(Elements.Admin.Header.backButton());
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест валидности ссылок навигационного меню в шапке админки",
            groups = {"smoke","acceptance","regression"},
            priority = 2004
    )
    public void successValidateNavigationMenu() {
        kraken.reach().admin();

        validateTransition(Elements.Admin.menuButton("Заказы"));
            validateTransition(Elements.Admin.submenuButton("Veeroute"));
            validateTransition(Elements.Admin.submenuButton("Мульти заказ"));

        validateTransition(Elements.Admin.menuButton("Магазины"));
            validateTransition(Elements.Admin.submenuButton("Регионы"));
            validateTransition(Elements.Admin.submenuButton("Ритейлеры"));

        validateTransition(Elements.Admin.menuButton("Контент"));
            validateTransition(Elements.Admin.submenuButton("Товарные опции"));
            validateTransition(Elements.Admin.submenuButton("Свойства"));
            validateTransition(Elements.Admin.submenuButton("Бренды"));
            validateTransition(Elements.Admin.submenuButton("Производители"));
            validateTransition(Elements.Admin.submenuButton("Страны производства"));
            validateTransition(Elements.Admin.submenuButton("Категории"));
            validateTransition(Elements.Admin.submenuButton("Импорт"));
        validateTransition(Elements.Admin.submenuButton("Продукты"));

        validateTransition(Elements.Admin.menuButton("Настройки"));

        validateTransition(Elements.Admin.menuButton("Маркетинг"));
            validateTransition(Elements.Admin.submenuButton("Промоакции"));
            validateTransition(Elements.Admin.submenuButton("Welcome баннеры"));
            validateTransition(Elements.Admin.submenuButton("Реклама"));
            validateTransition(Elements.Admin.submenuButton("Корзины"));
            validateTransition(Elements.Admin.submenuButton("Бонусные карты"));
            validateTransition(Elements.Admin.submenuButton("Программы ритейлеров"));
            validateTransition(Elements.Admin.submenuButton("Реферальная программа"));
            validateTransition(Elements.Admin.submenuButton("Промоигра"));
            validateTransition(Elements.Admin.submenuButton("Новые Города"));
            validateTransition(Elements.Admin.submenuButton("In App баннеры"));
            validateTransition(Elements.Admin.submenuButton("Промо карточки"));

        validateTransition(Elements.Admin.menuButton("Персонал"));
            //validateTransition(Elements.Admin.submenuButton("Сборщики"));

        validateTransition(Elements.Admin.menuButton("Пользователи"));

        validateTransition(Elements.Admin.menuButton("Страницы"));
    }
}
