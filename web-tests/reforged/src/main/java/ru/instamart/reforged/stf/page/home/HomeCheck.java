package ru.instamart.reforged.stf.page.home;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HomeCheck extends Check, HomeElement {

    @Step("Проверяем, что кнопка логина видима")
    default void checkLoginButtonIsVisible() {
        waitAction().shouldBeVisible(loginButton);
    }

    @Step("Проверяем, что отображается шапка лендинга Сбермаркета")
    default void checkHeaderContainerIsVisible() {
        waitAction().shouldBeVisible(headerContainer);
    }

    @Step("Проверяем, что отображается логотип Сбермаркета в шапке лендинга")
    default void checkHeaderLogoIsVisible() {
        waitAction().shouldBeVisible(headerLogo);
    }

    @Step("Проверяем, что отображается главный блок лендинга Сбермаркета")
    default void checkMainBlockContainerIsVisible() {
        waitAction().shouldBeVisible(mainBlockContainer);
    }

    @Step("Проверяем, что отображается главная иллюстрация лендинга Сбермаркета")
    default void checkMainBlockIllustrationIsVisible() {
        waitAction().shouldBeVisible(mainBlockIllustration);
    }

    @Step("Проверяем, что отображается кнопка с указанием адреса доставки на лендинге")
    default void checkMainBlockAddressButtonIsVisible() {
        waitAction().shouldBeVisible(mainBlockAddressButton);
    }

    @Step("Проверяем, что отображается текст лендинга Сбермаркета")
    default void checkMainBlockTextIsVisible() {
        waitAction().shouldBeVisible(mainBlockText);
    }

    @Step("Проверяем, что отображается блок со списком магазинов на лендинге Сбермаркета")
    default void checkStoresListIsVisible() {
        waitAction().shouldBeVisible(storesList);
    }

    @Step("Проверяем, что отображается кнопка первого магазина на лендинге Сбермаркета")
    default void checkStoresButtonIsVisible() {
        waitAction().shouldBeVisible(storesButton);
    }

    @Step("Проверяем, что отображается кнопка выбора магазина Ашан")
    default void checkStoresButtonAuchanIsVisible() {
        waitAction().shouldBeVisible(storesButtonAuchan);
    }

    @Step("Проверяем, что отображается кнопка выбора магазина Метро")
    default void checkStoresButtonMetroIsVisible() {
        waitAction().shouldBeVisible(storesButtonMetro);
    }

    @Step("Проверяем, что отображается кнопка выбора магазина Лента")
    default void checkStoresButtonLentaIsVisible() {
        waitAction().shouldBeVisible(storesButtonLenta);
    }

    @Step("Проверяем, что отображается кнопка показать все магазины")
    default void checkShowAllRetailerslIsVisible() {
        waitAction().shouldBeVisible(showAllRetailers);
    }

    @Step("Проверяем, что отображается блок преимуществ на лендинге Сбермаркета")
    default void checkAdvantagesBlockContainerIsVisible() {
        waitAction().shouldBeVisible(advantagesBlockContainer);
    }

    @Step("Проверяем, что отображается блок преимуществ быстрой доставки")
    default void checkDeliveryAdvIsVisible() {
        waitAction().shouldBeVisible(deliveryAdv);
    }

    @Step("Проверяем, что отображается преимущество доставки тяжелых товаров до двери")
    default void checkHeavyToDoorAdvIsVisible() {
        waitAction().shouldBeVisible(heavyToDoorAdv);
    }

    @Step("Проверяем, что отображается преимущество доставки товаров высокого качества")
    default void checkGoodQualityAdvIsVisible() {
        waitAction().shouldBeVisible(goodQualityAdv);
    }

    @Step("Проверяем, что отображается преимущество получения скидок от партнеров на большое количество товаров")
    default void checkSaleAdvIsVisible() {
        waitAction().shouldBeVisible(saleAdv);
    }

    @Step("Проверяем, что отображается блок зон доставки на лендинге Сбермаркета")
    default void checkZonesBlockContainerIsVisible() {
        waitAction().shouldBeVisible(zonesBlockContainer);
    }

    @Step("Проверяем, что отображается кнопка показать все города, где работает сбермаркет")
    default void checkZonesBlockShowAllButtonIsVisible() {
        waitAction().shouldBeVisible(zonesBlockShowAllButton);
    }

    @Step("Проверяем, что отображается блок механики заказа на лендинге Сбермаркета")
    default void checkOrderBlockContainerIsVisible() {
        waitAction().shouldBeVisible(orderBlockContainer);
    }

    @Step("Проверяем, что отображается первый шаг заказа на лендинге Сбермаркета")
    default void checkOrderBlockStepFirstIsVisible() {
        waitAction().shouldBeVisible(orderBlockStepFirst);
    }

    @Step("Проверяем, что отображается второй шага заказа на лендинге Сбермаркета")
    default void checkOrderBlockStepSecondIsVisible() {
        waitAction().shouldBeVisible(orderBlockStepSecond);
    }

    @Step("Проверяем, что отображается третий шаг заказа на лендинге Сбермаркета")
    default void checkOrderBlockStepThirdIsVisible() {
        waitAction().shouldBeVisible(orderBlockStepThird);
    }

    @Step("Проверяем, что отображается блок моб. приложений на лендинге Сбермаркета")
    default void checkAppsBlockContainerIsVisible() {
        waitAction().shouldBeVisible(appsBlockContainer);
    }

    @Step("Проверяем, что отображается кнопка скачивания приложения сбермаркет в google play")
    default void checkGooglePlayIsVisible() {
        waitAction().shouldBeVisible(googlePlay);
    }

    @Step("Проверяем, что отображается кнопка скачивания приложения сбермаркет в app store")
    default void checkAppStoreIsVisible() {
        waitAction().shouldBeVisible(appStore);
    }

    @Step("Проверяем, что отображается кнопка скачивания приложения сбермаркет в app gallery")
    default void checkAppGalleryContainerIsVisible() {
        waitAction().shouldBeVisible(appGallery);
    }

    @Step("Проверяем, что отображается подвал лендинга Сбермаркета")
    default void checkFooterContainerIsVisible() {
        waitAction().shouldBeVisible(footerContainer);
    }

}
