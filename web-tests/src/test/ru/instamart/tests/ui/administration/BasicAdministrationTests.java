package ru.instamart.tests.ui.administration;

import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.common.lib.Pages;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.tests.ui.TestBase;

public class BasicAdministrationTests extends TestBase {
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @CaseId(419)
    @Test(  description = "Тест доступности корневых разделов админки",
            priority = 10002,
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successCheckAdminSectionsAvailability() {
        kraken.get().page(Pages.Admin.login());
        kraken.reach().admin();

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
    @Test(  description = "Тест доступности вьюхи oktell",
            priority = 10003,
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successCheckOktellViewAvailability() {
        kraken.reach().admin();
        baseChecks.checkPageIsAvailable(Pages.Admin.oktell());
    }

    @CaseId(416)
    @Test(  description = "Проверка наличия элементов в шапке админки",
            priority = 10004,
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successValidateHeader() {
        kraken.reach().admin();
        baseChecks.checkIsElementPresent(Elements.Administration.Header.userEmail());
        baseChecks.checkIsElementPresent(Elements.Administration.Header.logoutButton());
    }

    @CaseId(4)
    @Test(  description = "Тест валидности ссылок навигационного меню в шапке админки",
            priority = 10005,
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    ) public void successValidateNavigationMenu() {
        kraken.reach().admin();

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Заказы"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Управление логистикой"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Параметры маршрутизации"));
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
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Бонусный счет"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Редиректы"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Семплинг"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Маркетинговые категории"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Персонал"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Смены"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Тарифы"));
            baseChecks.checkTransitionValidation(Elements.Administration.submenuButton("Сборщики"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Пользователи"));

        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Страницы"));
        baseChecks.checkTransitionValidation(Elements.Administration.menuButton("Компании"));
    }
}
