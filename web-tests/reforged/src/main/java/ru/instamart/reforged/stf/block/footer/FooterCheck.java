package ru.instamart.reforged.stf.block.footer;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface FooterCheck extends Check, FooterElement {

    @Step("Проверяем что футер отображается")
    default void checkFooterVisible() {
        krakenAssert.assertTrue(container.is().displayed(), "Футер не отображается");
    }

    @Step("Проверяем что логотип отображается")
    default void checkLogoVisible() {
        krakenAssert.assertTrue(logo.is().displayed(), "Логотип не отображается");
    }

    @Step("Проверяем что отображается заголовок Sbermarket")
    default void checkSbermarketTitleVisible() {
        krakenAssert.assertTrue(sbermarketTitle.is().displayed(), "Заголовок не отображается");
    }

    @Step("Проверяем что отображается ссылка 'О Компании'")
    default void checkAboutCompanyLinkVisible() {
        krakenAssert.assertTrue(aboutCompanyLink.is().displayed(), "Ссылка 'о компании' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Контакты'")
    default void checkContactsLinkVisible() {
        krakenAssert.assertTrue(contactsLink.is().displayed(), "Ссылка 'Контакты' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Вакансии'")
    default void checkVacanciesLinkVisible() {
        krakenAssert.assertTrue(vacanciesLink.is().displayed(), "ссылка 'Вакансии' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Документы'")
    default void checkDocumentsLinkVisible() {
        krakenAssert.assertTrue(documentsLink.is().displayed(), "ссылка 'Документы' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Партнеры'")
    default void checkPartnersLinkVisible() {
        krakenAssert.assertTrue(partnersLink.is().displayed(), "ссылка 'Партнеры' не отображается");
    }

    @Step("Проверяем что отображается заголовок 'Помощь покупателю'")
    default void checkCustomerHelpTitleVisible() {
        krakenAssert.assertTrue(customerHelpTitle.is().displayed(), "заголовок 'Помощь покупателю' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Как мы работаем'")
    default void checkHowWeWorkVisible() {
        krakenAssert.assertTrue(howWeWork.is().displayed(), "ссылка 'Как мы работаем' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Зоны доставки'")
    default void checkDeliveryZoneVisible() {
        krakenAssert.assertTrue(deliveryZone.is().displayed(), "ссылка 'Зоны доставки' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Доставка и оплата'")
    default void checkDeliveryAndPaymentVisible() {
        krakenAssert.assertTrue(deliveryAndPayment.is().displayed(), "ссылка 'Доставка и оплата' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Помощь'")
    default void checkHelpVisible() {
        krakenAssert.assertTrue(help.is().displayed(), "ссылка 'Помощь' не отображается");
    }

    @Step("Проверяем что отображается контактный телефон")
    default void checkHotlinePhoneNumberVisible() {
        krakenAssert.assertTrue(hotlinePhoneNumber.is().displayed(), "контактный телефон не отображается");
    }

    @Step("Проверяем что отображаются часы работы поддержки")
    default void checkHotlineWorkHoursTextVisible() {
        krakenAssert.assertTrue(hotlineWorkHoursText.is().displayed(), "часы работы поддержки не отображаются");
    }

    @Step("Проверяем что отображается кнопка 'Facebook'")
    default void checkFacebookButtonVisible() {
        krakenAssert.assertTrue(facebookButton.is().displayed(), "кнопка 'Facebook' не отображается");
    }

    @Step("Проверяем что отображается кнопка 'Vkontakte'")
    default void checkVkontakteButtonVisible() {
        krakenAssert.assertTrue(vkontakteButton.is().displayed(), "кнопка 'Vkontakte' не отображается");
    }

    @Step("Проверяем что отображается кнопка 'Instagram'")
    default void checkInstagramButtonVisible() {
        krakenAssert.assertTrue(instagramButton.is().displayed(), "кнопка 'Instagram' не отображается");
    }

    @Step("Проверяем что отображается кнопка 'Twitter'")
    default void checkTwitterButtonVisible() {
        krakenAssert.assertTrue(twitterButton.is().displayed(), "кнопка 'Twitter' не отображается");
    }

    @Step("Проверяем что отображается кнопка 'Google Play'")
    default void checkGooglePlayButtonVisible() {
        krakenAssert.assertTrue(googlePlayButton.is().displayed(), "кнопка 'Google Play' не отображается");
    }

    @Step("Проверяем что отображается кнопка 'App Store'")
    default void checkAppstoreButtonVisible() {
        krakenAssert.assertTrue(appstoreButton.is().displayed(), "кнопка 'App Store' не отображается");
    }

    @Step("Проверяем что отображается кнопка 'Huawei Store'")
    default void checkHuaweiButtonVisible() {
        krakenAssert.assertTrue(huaweiButton.is().displayed(), "кнопка 'Huawei Store' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Политика возврата'")
    default void checkReturnsPolicyLinkVisible() {
        krakenAssert.assertTrue(returnsPolicyLink.is().displayed(), "ссылка 'Политика возврата' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Обработка персональных данных'")
    default void checkPersonalDataPolicyLinkVisible() {
        krakenAssert.assertTrue(personalDataPolicyLink.is().displayed(), "ссылка 'Обработка персональных данных' не отображается");
    }

    @Step("Проверяем что отображается ссылка 'Официальное уведомление'")
    default void checkPublicOfferLinkVisible() {
        krakenAssert.assertTrue(publicOfferLink.is().displayed(), "ссылка 'Официальное уведомление' не отображается");
    }
}
