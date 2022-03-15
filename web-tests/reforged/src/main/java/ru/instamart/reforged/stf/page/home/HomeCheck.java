package ru.instamart.reforged.stf.page.home;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HomeCheck extends Check, HomeElement {

    @Step("Проверяем, что отображается шапка лендинга Сбермаркета")
    default void checkHeaderContainerIsVisible() {
        waitAction().shouldBeVisible(headerContainer);
    }

    @Step("Проверяем, что отображается логотип Сбермаркета в шапке лендинга")
    default void checkHeaderLogoIsVisible() {
        waitAction().shouldBeVisible(headerLogo);
    }

    @Step("Отображается блок для авторизованного пользователя")
    default void checkAuthoredBlockVisible() {
        waitAction().shouldBeVisible(headerAuthBlockContainer);
    }

    @Step("Проверяем, что кнопка логина видима")
    default void checkLoginButtonIsVisible() {
        waitAction().shouldBeVisible(loginButton);
    }

    @Step("Проверяем, что отображается блок адреса доставки лендинга Сбермаркета")
    default void checkAddressBlockContainerIsVisible() {
        waitAction().shouldBeVisible(addressBlockContainer);
    }

    @Step("Проверяем, что отображается кнопка с указанием адреса доставки на лендинге")
    default void checkAddressBlockAddressButtonIsVisible() {
        waitAction().shouldBeVisible(addressBlockAddressButton);
    }

    @Step("Проверяем, что отображается текст лендинга Сбермаркета")
    default void checkAddressBlockTextIsVisible() {
        waitAction().shouldBeVisible(addressBlockText);
    }

    @Step("Проверяем, что отображается блок магазинов лендинга Сбермаркета")
    default void checkDeliveryContainerVisible() {
        waitAction().shouldBeVisible(deliveryRetailersBlockContainer);
    }

    @Step("Проверяем, что отображается заголовок доставки магазинов лендинга Сбермаркета")
    default void checkDeliveryTitleVisible() {
        waitAction().shouldBeVisible(deliveryRetailersBlockTitle);
    }

    @Step("Проверяем, что отображается суб заголовок доставки магазинов лендинга Сбермаркета")
    default void checkDeliverySubTitleVisible() {
        waitAction().shouldBeVisible(deliveryRetailersBlockSubTitle);
    }

    @Step("Проверяем, что отображается блок advantages лендинга Сбермаркета")
    default void checkAdvantagesContainerVisible() {
        waitAction().shouldBeVisible(advantagesBlockContainer);
    }

    @Step("Проверяем, что отображается блок info лендинга Сбермаркета")
    default void checkInfoContainerVisible() {
        waitAction().shouldBeVisible(infoBlockContainer);
    }

    @Step("Проверяем, что отображается блок cities лендинга Сбермаркета")
    default void checkCitiesContainerVisible() {
        waitAction().shouldBeVisible(citiesBlockContainer);
    }

    @Step("Проверяем, что отображается блок b2b лендинга Сбермаркета")
    default void checkB2bContainerVisible() {
        waitAction().shouldBeVisible(b2bBannerBlockContainer);
    }

    @Step("Проверяем, что отображается кнопка блока b2b лендинга Сбермаркета")
    default void checkB2bButtonContainerVisible() {
        waitAction().shouldBeVisible(b2bBannerBlockButton);
    }

    @Step("Проверяем, что отображается блок steps лендинга Сбермаркета")
    default void checkStepsContainerVisible() {
        waitAction().shouldBeVisible(stepsBlockContainer);
    }

    @Step("Проверяем, что отображается блок app лендинга Сбермаркета")
    default void checkAppContainerVisible() {
        waitAction().shouldBeVisible(appBlockContainer);
    }

    @Step("Проверяем, что отображается кнопка google play лендинга Сбермаркета")
    default void checkGooglePlayButtonVisible() {
        waitAction().shouldBeVisible(googlePlay);
    }

    @Step("Проверяем, что отображается кнопка appstore лендинга Сбермаркета")
    default void checkAppStoreButtonVisible() {
        waitAction().shouldBeVisible(appStore);
    }

    @Step("Проверяем, что отображается кнопка huawei play лендинга Сбермаркета")
    default void checkHuaweiPlayButtonVisible() {
        waitAction().shouldBeVisible(appGallery);
    }

    @Step("Проверяем, что отображается ФИО пользователя")
    default void checkUserCredentialsDisplayed() {
        waitAction().shouldBeVisible(headerAuthCredential);
    }

    @Step("Проверяем, что отображается кнопка выхода")
    default void checkLogoutButtonDisplayed() {
        waitAction().shouldBeVisible(headerAuthLogoutButton);
    }

    @Step("Проверяем, что заголовок баннера: '{expectedTitle}'")
    default void checkBannerTitleText(final String actualTitle, final String expectedTitle) {
        Assert.assertEquals(actualTitle, expectedTitle,
                String.format("Заголовок баннера: '%s' отличается от ожидаемого: '%s'", actualTitle, expectedTitle));
    }

    @Step("Проверяем, что заголовок блока найденных магазинов: '{expectedBlockTitle}'")
    default void checkDeliveryBlockTitle(final String actualBlockTitle, final String expectedBlockTitle) {
        Assert.assertEquals(actualBlockTitle, expectedBlockTitle,
                String.format("Заголовк блока с магазинами (ритейлерами): '%s' отличается от ожидаемого: '%s' магазинов", actualBlockTitle, expectedBlockTitle));
    }
}
