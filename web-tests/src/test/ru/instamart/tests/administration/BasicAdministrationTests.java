package ru.instamart.tests.administration;

import instamart.core.helpers.ConsoleOutputCapturerHelper;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

import static instamart.core.common.AppManager.session;

public class BasicAdministrationTests extends TestBase {
    ConsoleOutputCapturerHelper capture = new ConsoleOutputCapturerHelper();
    //TODO актуализировать базовые тесты, все лишнее вынести в отдельные классы и порешать тудушки - ATST-235

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    // TODO перенести все тесты доступа в AdministrationAccessTests

    // TODO сделать тест перехода в корневые разделы админки по прямым ссылкам без авторизации

    // TODO сделать тест перехода в корневые разделы админки по прямым ссылкам без админ привилегий

    @Test(  description = "Тест недоступности админки пользователю без админ. прав",
            priority = 10000,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void noAccessToAdministrationWithoutAdminPrivileges() {
        User.Do.loginAs(session.user);

        assertPageIsUnavailable(Pages.Admin.shipments());
        User.Logout.quickly();
    }

    @Test(  description = "Тест доступности админки пользователю c админ. правми",
            priority = 10001,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successAccessAdministrationThroughAuthOnSite() {
        User.Do.loginAs(session.admin);
        kraken.get().page(Pages.Admin.shipments());

        Assert.assertTrue(
                kraken.detect().isInAdmin(),
                    "Не доступна админка пользователю c админ. правми");
    }

    @Test(  description = "Тест доступности корневых разделов админки",
            priority = 10002,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successCheckAdminSectionsAvailability() {
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

    @Test(  description = "Тест доступности вьюхи oktell",
            priority = 10003,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successCheckOktellViewAvailability() {
        kraken.reach().admin();

        assertPageIsAvailable(Pages.Admin.oktell());
    }

    @Test(  description = "Проверка наличия элементов в шапке админки",
            priority = 10004,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successValidateHeader() {
        kraken.reach().admin();
        assertPresence(Elements.Administration.Header.userEmail());
        assertPresence(Elements.Administration.Header.logoutButton());
    }

    @Test(  description = "Тест валидности ссылок навигационного меню в шапке админки",
            priority = 10005,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successValidateNavigationMenu() {
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
            validateTransition(Elements.Administration.submenuButton("Новые Города"));
            validateTransition(Elements.Administration.submenuButton("In App баннеры"));
            validateTransition(Elements.Administration.submenuButton("Промо карточки"));

        validateTransition(Elements.Administration.menuButton("Персонал"));
            //validateTransition(Elements.Administration.submenuButton("Сборщики"));

        validateTransition(Elements.Administration.menuButton("Пользователи"));

        validateTransition(Elements.Administration.menuButton("Страницы"));
    }
}
