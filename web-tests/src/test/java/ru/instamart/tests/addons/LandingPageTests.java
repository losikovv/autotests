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
            priority = 51,
            groups = {"smoke","acceptance","regression"}
    )
    public void successValidateLanding() {
        runTestOnlyOn(Tenants.instamart());

        assertPageIsAvailable();

        assertPresence(Elements.Landing.MainBlock.logo());
        assertPresence(Elements.Landing.MainBlock.howWeWorkLink());
        assertPresence(Elements.Landing.MainBlock.helpLink());
        assertPresence(Elements.Landing.MainBlock.mnogoruLogo());
        assertPresence(Elements.Landing.MainBlock.hotlineLink());
        assertPresence(Elements.Landing.MainBlock.loginButton());

        assertPresence(Elements.Landing.MainBlock.mainTitle());
        assertPresence(Elements.Landing.MainBlock.goToCatalogButton());
        assertPresence(Elements.Landing.MainBlock.advantages());

        assertPresence(Elements.Landing.PricesPromoBlock.panel());
        assertPresence(Elements.Landing.PricesPromoBlock.goToCatalogButton());

        assertPresence(Elements.Landing.UserReviewsPromoBlock.panel());

        assertPresence(Elements.Landing.MobileAppPromoBlock.panel());
        assertPresence(Elements.Landing.MobileAppPromoBlock.phoneField());
        assertPresence(Elements.Landing.MobileAppPromoBlock.sendLinkButton());

        assertPresence(Elements.Landing.SeoBlock.panel());

        assertPresence(Elements.Footer.container());
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
