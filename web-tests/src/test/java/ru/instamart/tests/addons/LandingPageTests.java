package ru.instamart.tests.addons;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.Tenants;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

public class LandingPageTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        User.Logout.quickly();
    }

    @Test(
            description = "Тест валидности и наличия элемнтов лендинга Инстамарт",
            groups = {"smoke","acceptance","regression"},
            priority = 51
    )
    public void successValidateLanding() {
        runTestOnlyOn(Tenants.instamart());

        assertPageIsAvailable();

        assertElementPresence(Elements.Landing.MainBlock.logo());
        assertElementPresence(Elements.Landing.MainBlock.howWeWorkLink());
        assertElementPresence(Elements.Landing.MainBlock.helpLink());
        assertElementPresence(Elements.Landing.MainBlock.mnogoruLogo());
        assertElementPresence(Elements.Landing.MainBlock.hotlineLink());
        assertElementPresence(Elements.Landing.MainBlock.loginButton());

        assertElementPresence(Elements.Landing.MainBlock.mainTitle());
        assertElementPresence(Elements.Landing.MainBlock.goToCatalogButton());
        assertElementPresence(Elements.Landing.MainBlock.advantages());

        assertElementPresence(Elements.Landing.PricesPromoBlock.panel());
        assertElementPresence(Elements.Landing.PricesPromoBlock.goToCatalogButton());

        assertElementPresence(Elements.Landing.UserReviewsPromoBlock.panel());

        assertElementPresence(Elements.Landing.MobileAppPromoBlock.panel());
        assertElementPresence(Elements.Landing.MobileAppPromoBlock.phoneField());
        assertElementPresence(Elements.Landing.MobileAppPromoBlock.sendLinkButton());

        assertElementPresence(Elements.Landing.SeoBlock.panel());

        assertElementPresence(Elements.Footer.container());
    }

    @Test(
            description = "Тест перехода по ссылке 'Как мы работаем' в шапке лендинга",
            groups = {"acceptance","regression"},
            priority = 52
    )
    public void successAssertTransitionOnHeaderHowWeWorkLink() {
        assertTransition(Elements.Landing.MainBlock.howWeWorkLink());
    }

    @Test(
            description = "Тест перехода по ссылке 'Помощь' в шапке лендинга",
            groups = {"acceptance","regression"},
            priority = 53
    )
    public void successAssertTransitionOnHeaderHelpLink() {
        assertTransition(Elements.Landing.MainBlock.helpLink());
    }

    //todo починить тест, сделав метод детектящий открытие новой вкладки
    @Test( enabled = false,
            description = "Тест перехода по ссылке на логотипе 'Много.ру' в шапке лендинга",
            groups = {"acceptance","regression"},
            priority = 54
    )
    public void successAssertTransitionOnHeaderMnogoruLogo() {
        assertTransition(Elements.Landing.MainBlock.mnogoruLogo());
    }

    @Test(
            description = "Тест перехода по кнопке 'Перейти в каталог' в главном блоке лендинга",
            groups = {"acceptance","regression"},
            priority = 55
    )
    public void successAssertTransitionOnGoToCatalogButtonFromMainBlock() {
        assertTransition(Elements.Landing.MainBlock.goToCatalogButton());
    }

    @Test(
            description = "Тест перехода по кнопке 'Перейти в каталог' в промоблоке 'Цены как в магазине' лендинга",
            groups = {"acceptance","regression"},
            priority = 56
    )
    public void successAssertTransitionOnGoToCatalogButtonFromPricesPromoBlock() {
        assertTransition(Elements.Landing.PricesPromoBlock.goToCatalogButton());
    }

    @Test(
            description = "Тест отправки ссылки на скачивание мобильного приложения с лендинга",
            groups = {"acceptance","regression"},
            priority = 57
    )
    public void successSendAppDownloadLinkFromAppPromoBlock() {
        kraken.perform().fillField(Elements.Landing.MobileAppPromoBlock.phoneField(),"9629422123");
        kraken.perform().click(Elements.Landing.MobileAppPromoBlock.sendLinkButton());

        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Landing.MobileAppPromoBlock.successPlaceholder()),
                "Не отправилась ссылка на скачивание мобильного приложения с лендинга");
    }
}
