package ru.instamart.autotests.tests;

import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Pages;


// Проверка страниц



public class CheckPages extends TestBase {


    @Test(
            enabled = false,
            description = "Тест лендинга"
    )
    public void checkLandingPage() throws Exception, AssertionError {
        assertPageIsAvailable("https://instamart.ru/metro");

        // TODO добавить проверку элементов и функционала лендинга

    }


    @Test(
            description = "Тест доступности витрин рителйеров",
            groups = {"smoke","acceptance","regression"},
            priority = 801
    )
    // TODO переделать чек страниц всех ретейлеров по списку ретейлеров
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    // TODO проверять витрины активных ритейлеров на доступность, витрины неактивных - на недоступность
    public void checkRetailerPages() throws Exception, AssertionError {
        assertPageIsAvailable("https://instamart.ru/metro");
        assertPageIsAvailable("https://instamart.ru/lenta");
        assertPageIs404("https://instamart.ru/selgros"); // неактивный ритейлер
        assertPageIsAvailable("https://instamart.ru/vkusvill");
        assertPageIsAvailable("https://instamart.ru/karusel");
    }


    @Test(
            description = "Тест доступности партнерских лендингов",
            groups = {"smoke","acceptance","regression"},
            priority = 802
    )
    // TODO переделать чек лендингов циклом по списку
    public void checkLandings() throws Exception, AssertionError {
        assertPageIsAvailable(Pages.Site.Landings.mnogoru());
        assertPageIsAvailable(Pages.Site.Landings.sovest());
        assertPageIsAvailable(Pages.Site.Landings.halva());
        assertPageIsAvailable(Pages.Site.Landings.feedback());
        assertPageIsAvailable("https://vkusvill.instamart.ru/"); // - переделать
        assertPageIsAvailable(Pages.Site.Landings.kazan());
        assertPageIsAvailable(Pages.Site.Landings.mobile());
        // добавить метро лэндинг
    }


    @Test(
            description = "Тест доступности статических страниц",
            groups = {"smoke","acceptance","regression"},
            priority = 803
    )
    // TODO переделать чек страниц циклом по списку
    public void checkStaticPages() throws Exception, AssertionError {
        assertPageIsAvailable(Pages.Site.Static.about());
        assertPageIsAvailable(Pages.Site.Static.delivery());
        assertPageIsAvailable(Pages.Site.Static.rules());
        assertPageIsAvailable(Pages.Site.Static.payment());
        assertPageIsAvailable(Pages.Site.Static.returnPolicy());
        assertPageIsAvailable(Pages.Site.Static.faq());
        assertPageIsAvailable(Pages.Site.Static.terms());
        assertPageIsAvailable(Pages.Site.Static.contacts());
    }


    @Test(
            description = "Тест доступности страниц профиля пользователя",
            groups = {"smoke","acceptance","regression"},
            priority = 804
    )
    public void checkProfilePages() throws Exception, AssertionError {
        app.getHelper().getBaseUrl();
        app.getSessionHelper().doLoginAs("admin");

        assertPageIsAvailable(Pages.Site.Profile.edit());
        assertPageIsAvailable(Pages.Site.Profile.orders());
        assertPageIsAvailable(Pages.Site.Profile.addresses());
    }




    @Test(
            description = "Тест доступности корневых разделов админки",
            groups = {"smoke","acceptance","regression"},
            priority = 805
    )
    public void checkAdminPages() throws Exception, AssertionError {
        app.getSessionHelper().getUrlAsAdmin("https://instamart.ru/admin/retailers");

        assertPageIsAvailable(Pages.Admin.shipments());
        assertPageIsAvailable(Pages.Admin.retailers());
        assertPageIsAvailable(Pages.Admin.products());
        assertPageIsAvailable(Pages.Admin.imports());
        assertPageIsAvailable(Pages.Admin.reports());
        assertPageIsAvailable(Pages.Admin.settings());
        assertPageIsAvailable(Pages.Admin.marketing());
        assertPageIsAvailable(Pages.Admin.users());
        assertPageIsAvailable(Pages.Admin.pages());
    }


    @Test(
            description = "Тест доступности страниц футера",
            groups = {"smoke","acceptance","regression"},
            priority = 806
    )

    //Пока не рабоатет из-за Jivosite
    //Спрятать все лишнее

    public void checkFooterPages() throws Exception {

        footer();

    }



    private void footer() throws Exception {
        app.getNavigationHelper().goFooterAboutCompany();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterContacts();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterDelivery();
        assertPageIsAvailable();
        isDeliveryPopupOpened();


        app.getNavigationHelper().goFooterPayment();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterPartners();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterFaq();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterFeedbackForm();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterReturnPolicy();
        assertPageIsAvailable();

        app.getNavigationHelper().goFooterPublicOffer();
        assertPageIsAvailable();
    }

    //- Чек попапа доставка - перенести в другое место 

    private boolean isDeliveryPopupOpened() {
        app.getNavigationHelper().printMessage("Checking delivery popup...");
        if(app.getNavigationHelper().isElementDetected("//*[@id='popup-modal']/div/div/div[2]/div[1]/h3",
                "Доступные интервалы")) {
            app.getNavigationHelper().printMessage("Delivery popup active");
            return true;
        }else {
            return false;
        }

    }





}
