package ru.instamart.reforged.stf.block.footer;

import io.qameta.allure.Step;

public final class Footer implements FooterCheck {

    @Step("Переход по ссылке 'О компании'")
    public void clickToAbout() {
        aboutCompanyLink.click();
    }

    @Step("Переход по ссылке 'Контакты'")
    public void clickToContacts() {
        contactsLink.click();
    }

    @Step("Переход по ссылке 'Политика возврата'")
    public void clickToReturnPolicy() {
        returnsPolicyLink.click();
    }

    @Step("Переход по ссылке 'Официальное уведомление'")
    public void clickToPublicOffer() {
        publicOfferLink.click();
    }
}
