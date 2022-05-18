package ru.instamart.reforged.next.block.footer;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface FooterCheck extends Check, FooterElement {

    @Step("Проверяем что футер отображается")
    default void checkFooterVisible() {
        waitAction().shouldBeVisible(container);
    }

    @Step("Проверяем что логотип отображается")
    default void checkLogoVisible() {
        waitAction().shouldBeVisible(logo);
    }

    @Step("Проверяем что отображается заголовок Sbermarket")
    default void checkSbermarketTitleVisible() {
        waitAction().shouldBeVisible(sbermarketTitle);
    }

    @Step("Проверяем что отображается ссылка 'О Компании'")
    default void checkAboutCompanyLinkVisible() {
        waitAction().shouldBeVisible(aboutCompanyLink);
    }

    @Step("Проверяем что отображается ссылка 'Контакты'")
    default void checkContactsLinkVisible() {
        waitAction().shouldBeVisible(contactsLink);
    }

    @Step("Проверяем что отображается ссылка 'Вакансии'")
    default void checkVacanciesLinkVisible() {
        waitAction().shouldBeVisible(vacanciesLink);
    }

    @Step("Проверяем что отображается ссылка 'Документы'")
    default void checkDocumentsLinkVisible() {
        waitAction().shouldBeVisible(documentsLink);
    }

    @Step("Проверяем что отображается ссылка 'Партнеры'")
    default void checkPartnersLinkVisible() {
        waitAction().shouldBeVisible(partnersLink);
    }

    @Step("Проверяем что отображается заголовок 'Помощь покупателю'")
    default void checkCustomerHelpTitleVisible() {
        waitAction().shouldBeVisible(customerHelpTitle);
    }

    @Step("Проверяем что отображается ссылка 'Как мы работаем'")
    default void checkHowWeWorkVisible() {
        waitAction().shouldBeVisible(howWeWork);
    }

    @Step("Проверяем что отображается ссылка 'Зоны доставки'")
    default void checkDeliveryZoneVisible() {
        waitAction().shouldBeVisible(deliveryZone);
    }

    @Step("Проверяем что отображается ссылка 'Доставка и оплата'")
    default void checkDeliveryAndPaymentVisible() {
        waitAction().shouldBeVisible(deliveryAndPayment);
    }

    @Step("Проверяем что отображается ссылка 'Помощь'")
    default void checkHelpVisible() {
        waitAction().shouldBeVisible(help);
    }

    @Step("Проверяем что отображается контактный телефон")
    default void checkHotlinePhoneNumberVisible() {
        waitAction().shouldBeVisible(hotlinePhoneNumber);
    }

    @Step("Проверяем что отображаются часы работы поддержки")
    default void checkHotlineWorkHoursTextVisible() {
        waitAction().shouldBeVisible(hotlineWorkHoursText);
    }

    @Step("Проверяем что отображается кнопка 'Facebook'")
    default void checkFacebookButtonVisible() {
        waitAction().shouldBeVisible(facebookButton);
    }

    @Step("Проверяем что отображается кнопка 'Vkontakte'")
    default void checkVkontakteButtonVisible() {
        waitAction().shouldBeVisible(vkontakteButton);
    }

    @Step("Проверяем что отображается кнопка 'Instagram'")
    default void checkInstagramButtonVisible() {
        waitAction().shouldBeVisible(instagramButton);
    }

    @Step("Проверяем что отображается кнопка 'Twitter'")
    default void checkTwitterButtonVisible() {
        waitAction().shouldBeVisible(twitterButton);
    }

    @Step("Проверяем что отображается кнопка 'Google Play'")
    default void checkGooglePlayButtonVisible() {
        waitAction().shouldBeVisible(googlePlayButton);
    }

    @Step("Проверяем что отображается кнопка 'App Store'")
    default void checkAppstoreButtonVisible() {
        waitAction().shouldBeVisible(appstoreButton);
    }

    @Step("Проверяем что отображается кнопка 'Huawei Store'")
    default void checkHuaweiButtonVisible() {
        waitAction().shouldBeVisible(huaweiButton);
    }

    @Step("Проверяем что отображается ссылка 'Политика возврата'")
    default void checkReturnsPolicyLinkVisible() {
        waitAction().shouldBeVisible(returnsPolicyLink);
    }

    @Step("Проверяем что отображается ссылка 'Обработка персональных данных'")
    default void checkPersonalDataPolicyLinkVisible() {
        waitAction().shouldBeVisible(personalDataPolicyLink);
    }

    @Step("Проверяем что отображается ссылка 'Официальное уведомление'")
    default void checkPublicOfferLinkVisible() {
        waitAction().shouldBeVisible(publicOfferLink);
    }
}
