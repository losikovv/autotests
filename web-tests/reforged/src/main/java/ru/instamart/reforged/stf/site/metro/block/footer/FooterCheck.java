package ru.instamart.reforged.stf.site.metro.block.footer;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface FooterCheck extends FooterElement, Check {

    @Step("Проверяем, что контейнер для футера отображается")
    default void checkFooterVisible() {
        waitAction().shouldBeVisible(footer);
    }

    @Step("Проверяем, что лого сбермаркета в футере отображается")
    default void checkLogoVisible() {
        waitAction().shouldBeVisible(logo);
    }

    @Step("Проверяем, что логотип отображается")
    default void checkCopyrightTextVisible() {
        waitAction().shouldBeVisible(copyrightText);
    }

    @Step("Проверяем, что лого компании партнера в футере отображается")
    default void checkPartnershipLogoVisible() {
        waitAction().shouldBeVisible(partnershipLogo);
    }

    @Step("Проверяем, что полное название компании-партнера в футере отображается")
    default void checkCopyrightShopNameVisible() {
        waitAction().shouldBeVisible(copyrightShopName);
    }

    @Step("Проверяем, что текст-заголовок о работе службы поддержки в футере отображается")
    default void checkHotlineTextVisible() {
        waitAction().shouldBeVisible(hotlineText);
    }

    @Step("Проверяем, что время работы горячей линии в футере отображается")
    default void checkHotlineWorkHoursVisible() {
        waitAction().shouldBeVisible(hotlineWorkHoursText);
    }

    @Step("Проверяем, что номер телефона горячей линии в футере отображается")
    default void checkHotlinePhoneNumberVisible() {
        waitAction().shouldBeVisible(hotlinePhoneNumber);
    }

    @Step("Проверяем, что дисклеймер об оказании услуг в футере отображается")
    default void checkDisclaimerVisible() {
        waitAction().shouldBeVisible(disclaimer);
    }

    @Step("Проверяем, что раздел 'Помощь покупателю' в футере отображается")
    default void checkCustomerHelpVisible() {
        waitAction().shouldBeVisible(customerHelp);
    }

    @Step("Проверяем, что раздел 'Политика возвратов' в футере отображается")
    default void checkReturnsPolicyLinkVisible() {
        waitAction().shouldBeVisible(returnsPolicyLink);
    }

    @Step("Проверяем, что раздел 'Официальное уведомление' в футере отображается")
    default void checkPublicOfferLinkVisible() {
        waitAction().shouldBeVisible(publicOfferLink);
    }

    @Step("Проверяем, что раздел 'Политика обработки данных' в футере отображается")
    default void checkPersonalDataPolicyLinkVisible() {
        waitAction().shouldBeVisible(personalDataPolicyLink);
    }

    @Step("Проверяем, что раздел 'О компании' в футере отображается")
    default void checkAboutCompanyVisible() {
        waitAction().shouldBeVisible(aboutCompany);
    }

    @Step("Проверяем, что раздел 'Зоны доставки' в футере отображается")
    default void checkDeliveryZoneVisible() {
        waitAction().shouldBeVisible(deliveryZone);
    }

    @Step("Проверяем, что раздел 'Доставка и оплата' в футере отображается")
    default void checkDeliveryAndPaymentVisible() {
        waitAction().shouldBeVisible(deliveryAndPayment);
    }

    @Step("Проверяем, что раздел 'Оплата' в футере отображается")
    default void checkPaymentInfoVisible() {
        waitAction().shouldBeVisible(paymentInfo);
    }
}
