package ru.instamart.tests.administration;

import instamart.core.testdata.UserManager;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.TestBase;

public class BasicAdministrationTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
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
        User.Do.loginAs(UserManager.getDefaultUser());
        baseChecks.checkPageIsUnavailable(Pages.Admin.shipments());
        User.Logout.quickly();
    }

    @Test(  description = "Тест доступности админки пользователю c админ. правми",
            priority = 10001,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successAccessAdministrationThroughAuthOnSite() {
        User.Do.loginAs(UserManager.getDefaultAdmin());
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

    @Test(  description = "Тест доступности вьюхи oktell",
            priority = 10003,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successCheckOktellViewAvailability() {
        kraken.reach().admin();

        baseChecks.checkPageIsAvailable(Pages.Admin.oktell());
    }

    @Test(  description = "Проверка наличия элементов в шапке админки",
            priority = 10004,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successValidateHeader() {
        kraken.reach().admin();
        baseChecks.checkIsElementPresent(Elements.Administration.Header.userEmail());
        baseChecks.checkIsElementPresent(Elements.Administration.Header.logoutButton());
    }

    @Test(  description = "Тест валидности ссылок навигационного меню в шапке админки",
            priority = 10005,
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    ) public void successValidateNavigationMenu() {
        kraken.reach().admin();

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Заказы"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Veeroute"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Мульти заказ"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Магазины"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Регионы"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Ритейлеры"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Контент"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Товарные опции"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Свойства"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Бренды"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Производители"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Страны производства"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Категории"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Импорт"));
        baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Продукты"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Настройки"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Маркетинг"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Промоакции"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Welcome баннеры"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Реклама"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Корзины"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Бонусные карты"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Программы ритейлеров"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Реферальная программа"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Новые Города"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("In App баннеры"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Промо карточки"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Персонал"));
            //validateTransition(Elements.Administration.submenuButton("Сборщики"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Пользователи"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Страницы"));
    }
}
