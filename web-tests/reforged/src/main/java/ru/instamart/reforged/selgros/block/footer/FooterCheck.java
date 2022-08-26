package ru.instamart.reforged.selgros.block.footer;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface FooterCheck extends FooterElement, Check {

    @Step("Проверяем, что контейнер для футера отображается")
    default void checkFooterVisible() {
        krakenAssert.assertTrue(footer.is().displayed(), "футер сбермаркета не отображается");
    }

    @Step("Проверяем, что лого сбермаркета в футере отображается")
    default void checkLogoVisible() {
        krakenAssert.assertTrue(logo.is().displayed(), "лого сбермаркета не отображается");

    }

    @Step("Проверяем, что логотип отображается")
    default void checkCopyrightTextVisible() {
        krakenAssert.assertTrue(copyrightText.is().displayed(), "логотип не отображается");
    }

    @Step("Проверяем, что лого компании партнера в футере отображается")
    default void checkPartnershipLogoVisible() {
        krakenAssert.assertTrue(partnershipLogo.is().displayed(), "лого компании партнера в футере не отображается");
    }

    @Step("Проверяем, что полное название компании-партнера в футере отображается")
    default void checkCopyrightShopNameVisible() {
        krakenAssert.assertTrue(copyrightShopName.is().displayed(), "полное название компании-партнера в футере не отображается");
    }

    @Step("Проверяем, что текст-заголовок о работе службы поддержки в футере отображается")
    default void checkHotlineTextVisible() {
        krakenAssert.assertTrue(hotlineText.is().displayed(), "текст-заголовок о работе службы поддержки не отображается");
    }

    @Step("Проверяем, что время работы горячей линии в футере отображается")
    default void checkHotlineWorkHoursVisible() {
        krakenAssert.assertTrue(hotlineWorkHoursText.is().displayed(), "время работы горячей линии в футере не отображается");
    }

    @Step("Проверяем, что номер телефона горячей линии в футере отображается")
    default void checkHotlinePhoneNumberVisible() {
        krakenAssert.assertTrue(hotlinePhoneNumber.is().displayed(), "номер телефона горячей линии в футере не отображается");
    }

    @Step("Проверяем, что дисклеймер об оказании услуг в футере отображается")
    default void checkDisclaimerVisible() {
        krakenAssert.assertTrue(disclaimer.is().displayed(), "дисклеймер об оказании услуг в футере не отображается");
    }

    @Step("Проверяем, что раздел 'Помощь покупателю' в футере отображается")
    default void checkCustomerHelpVisible() {
        krakenAssert.assertTrue(customerHelp.is().displayed(), "раздел 'Помощь покупателю' в футере не отображается");
    }

    @Step("Проверяем, что раздел 'Политика возвратов' в футере отображается")
    default void checkReturnsPolicyLinkVisible() {
        krakenAssert.assertTrue(returnsPolicyLink.is().displayed(), "раздел 'Политика возвратов' в футере не отображается");
    }

    @Step("Проверяем, что раздел 'Официальное уведомление' в футере отображается")
    default void checkPublicOfferLinkVisible() {
        krakenAssert.assertTrue(publicOfferLink.is().displayed(), "раздел 'Официальное уведомление' в футере не отображается");
    }

    @Step("Проверяем, что раздел 'Политика обработки данных' в футере отображается")
    default void checkPersonalDataPolicyLinkVisible() {
        krakenAssert.assertTrue(personalDataPolicyLink.is().displayed(), "раздел 'Политика обработки данных' не отображается");
    }

    @Step("Проверяем, что раздел 'О компании' в футере отображается")
    default void checkAboutCompanyVisible() {
        krakenAssert.assertTrue(aboutCompany.is().displayed(), "раздел 'О компании' в футере не отображается");
    }

    @Step("Проверяем, что раздел 'Зоны доставки' в футере отображается")
    default void checkDeliveryZoneVisible() {
        krakenAssert.assertTrue(deliveryZone.is().displayed(), "раздел 'Зоны доставки' в футере не отображается");
    }

    @Step("Проверяем, что раздел 'Доставка и оплата' в футере отображается")
    default void checkDeliveryAndPaymentVisible() {
        krakenAssert.assertTrue(deliveryAndPayment.is().displayed(), "раздел 'Доставка и оплата' в футере не отображается");
    }

    @Step("Проверяем, что раздел 'Оплата' в футере отображается")
    default void checkPaymentInfoVisible() {
        krakenAssert.assertTrue(paymentInfo.is().displayed(), "раздел 'Оплата' в футере не отображается");
    }
}
