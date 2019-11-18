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
    public void successValidateInstamartLanding() {
        runTestOnlyOn(Tenants.instamart());

        assertPageIsAvailable();

        assertPresence(Elements.Landings.Instamart.MainBlock.logo());
        assertPresence(Elements.Landings.Instamart.MainBlock.howWeWorkLink());
        assertPresence(Elements.Landings.Instamart.MainBlock.helpLink());
        assertPresence(Elements.Landings.Instamart.MainBlock.mnogoruLogo());
        assertPresence(Elements.Landings.Instamart.MainBlock.hotlineLink());
        assertPresence(Elements.Landings.Instamart.MainBlock.loginButton());

        assertPresence(Elements.Landings.Instamart.MainBlock.mainTitle());
        assertPresence(Elements.Landings.Instamart.MainBlock.goToCatalogButton());
        assertPresence(Elements.Landings.Instamart.MainBlock.advantages());

        assertPresence(Elements.Landings.Instamart.PricesPromoBlock.panel());
        assertPresence(Elements.Landings.Instamart.PricesPromoBlock.goToCatalogButton());

        assertPresence(Elements.Landings.Instamart.UserReviewsPromoBlock.panel());

        assertPresence(Elements.Landings.Instamart.MobileAppPromoBlock.panel());
        assertPresence(Elements.Landings.Instamart.MobileAppPromoBlock.phoneField());
        assertPresence(Elements.Landings.Instamart.MobileAppPromoBlock.sendLinkButton());

        assertPresence(Elements.Landings.Instamart.SeoBlock.panel());

        assertPresence(Elements.Footer.container());
    }

    @Test(
            description = "Тест перехода по ссылке 'Как мы работаем' в шапке лендинга",
            groups = {"acceptance","regression"},
            priority = 52
    )
    public void successAssertTransitionOnHeaderHowWeWorkLink() {
        assertTransition(Elements.Landings.Instamart.MainBlock.howWeWorkLink());
    }

    @Test(
            description = "Тест перехода по ссылке 'Помощь' в шапке лендинга",
            groups = {"acceptance","regression"},
            priority = 53
    )
    public void successAssertTransitionOnHeaderHelpLink() {
        assertTransition(Elements.Landings.Instamart.MainBlock.helpLink());
    }

    //todo починить тест, сделав метод детектящий открытие новой вкладки
    @Test( enabled = false,
            description = "Тест перехода по ссылке на логотипе 'Много.ру' в шапке лендинга",
            groups = {"acceptance","regression"},
            priority = 54
    )
    public void successAssertTransitionOnHeaderMnogoruLogo() {
        assertTransition(Elements.Landings.Instamart.MainBlock.mnogoruLogo());
    }

    @Test(
            description = "Тест перехода по кнопке 'Перейти в каталог' в главном блоке лендинга",
            groups = {"acceptance","regression"},
            priority = 55
    )
    public void successAssertTransitionOnGoToCatalogButtonFromMainBlock() {
        assertTransition(Elements.Landings.Instamart.MainBlock.goToCatalogButton());
    }

    @Test(
            description = "Тест перехода по кнопке 'Перейти в каталог' в промоблоке 'Цены как в магазине' лендинга",
            groups = {"acceptance","regression"},
            priority = 56
    )
    public void successAssertTransitionOnGoToCatalogButtonFromPricesPromoBlock() {
        assertTransition(Elements.Landings.Instamart.PricesPromoBlock.goToCatalogButton());
    }

    @Test(
            description = "Тест отправки ссылки на скачивание мобильного приложения с лендинга",
            groups = {"acceptance","regression"},
            priority = 57
    )
    public void successSendAppDownloadLinkFromAppPromoBlock() {
        kraken.perform().fillField(Elements.Landings.Instamart.MobileAppPromoBlock.phoneField(),"9629422123");
        kraken.perform().click(Elements.Landings.Instamart.MobileAppPromoBlock.sendLinkButton());

        Assert.assertTrue(
                kraken.detect().isElementPresent(
                        Elements.Landings.Instamart.MobileAppPromoBlock.successPlaceholder()),
                            "Не отправилась ссылка на скачивание мобильного приложения с лендинга");
    }
}
