package ru.instamart.autotests.tests.administration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.appmanager.platform.User;
import ru.instamart.autotests.tests.TestBase;

import static ru.instamart.autotests.appmanager.ApplicationManager.session;

public class BasicAdministrationTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Do.quickLogout();
    }


    // TODO перенести все тесты доступа в AdministrationAccessTests

    // TODO сделать тест перехода в корневые разделы админки по прямым ссылкам без авторизации

    // TODO сделать тест перехода в корневые разделы админки по прямым ссылкам без админ привилегий

    @Test(  description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"acceptance","regression"},
            priority = 10000
    )
    public void noAccessToAdministrationWithoutAdminPrivileges() {
        User.Do.loginAs(session.user);

        assertPageIsUnavailable(Pages.Admin.shipments());
        User.Do.quickLogout();
    }

    @Test(  description = "Тест доступности админки пользователю c админ. правми",
            groups = {"acceptance","regression"},
            priority = 10001
    )
    public void successAccessAdministrationThroughAuthOnSite() {
        User.Do.loginAs(session.admin);

        assertPageIsAvailable(Pages.Admin.shipments());
    }

    @Test(  description = "Тест доступности корневых разделов админки",
            groups = {"smoke","acceptance","regression"},
            priority = 10002
    )
    public void successCheckAdminSections() throws AssertionError {
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

    @Test(  description = "Проверка наличия элементов в шапке админки",
            groups = {"smoke","acceptance","regression"},
            priority = 10003
    ) public void successValidateHeader() {
        kraken.reach().admin();
        assertElementPresence(Elements.Administration.Header.userEmail());
        assertElementPresence(Elements.Administration.Header.logoutButton());
    }

    @Test(  description = "Тест валидности ссылок навигационного меню в шапке админки",
            groups = {"smoke","acceptance","regression"},
            priority = 10004
    )
    public void successValidateNavigationMenu() {
        kraken.reach().admin();

        validateTransition(Elements.Administration.menuButton("Заказы"));
            validateTransition(Elements.Administration.submenuButton("Veeroute"));
            validateTransition(Elements.Administration.submenuButton("Мульти заказ"));

        validateTransition(Elements.Administration.menuButton("Магазины"));
            validateTransition(Elements.Administration.submenuButton("Регионы"));
            validateTransition(Elements.Administration.submenuButton("Ритейлеры"));

        validateTransition(Elements.Administration.menuButton("Контент"));
            validateTransition(Elements.Administration.submenuButton("Товарные опции"));
            validateTransition(Elements.Administration.submenuButton("Свойства"));
            validateTransition(Elements.Administration.submenuButton("Бренды"));
            validateTransition(Elements.Administration.submenuButton("Производители"));
            validateTransition(Elements.Administration.submenuButton("Страны производства"));
            validateTransition(Elements.Administration.submenuButton("Категории"));
            validateTransition(Elements.Administration.submenuButton("Импорт"));
        validateTransition(Elements.Administration.submenuButton("Продукты"));

        validateTransition(Elements.Administration.menuButton("Настройки"));

        validateTransition(Elements.Administration.menuButton("Маркетинг"));
            validateTransition(Elements.Administration.submenuButton("Промоакции"));
            validateTransition(Elements.Administration.submenuButton("Welcome баннеры"));
            validateTransition(Elements.Administration.submenuButton("Реклама"));
            validateTransition(Elements.Administration.submenuButton("Корзины"));
            validateTransition(Elements.Administration.submenuButton("Бонусные карты"));
            validateTransition(Elements.Administration.submenuButton("Программы ритейлеров"));
            validateTransition(Elements.Administration.submenuButton("Реферальная программа"));
            validateTransition(Elements.Administration.submenuButton("Промоигра"));
            validateTransition(Elements.Administration.submenuButton("Новые Города"));
            validateTransition(Elements.Administration.submenuButton("In App баннеры"));
            validateTransition(Elements.Administration.submenuButton("Промо карточки"));

        validateTransition(Elements.Administration.menuButton("Персонал"));
            //validateTransition(Elements.Administration.submenuButton("Сборщики"));

        validateTransition(Elements.Administration.menuButton("Пользователи"));

        validateTransition(Elements.Administration.menuButton("Страницы"));
    }
}
