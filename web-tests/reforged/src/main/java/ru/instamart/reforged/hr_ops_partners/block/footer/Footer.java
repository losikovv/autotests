package ru.instamart.reforged.hr_ops_partners.block.footer;

import io.qameta.allure.Step;

public final class Footer implements FooterCheck {

    @Step("Скроллим страницу до футера")
    public void scrollToBlock() {
        logo.scrollTo();
    }

    @Step("Нажимаем на '{position}'-ю иконку социальных сетей")
    public void clickSocialsByPosition(final int position) {
        socialLinks.getElements().get(position - 1).click();
    }

    @Step("Нажимаем на ссылку 'Программа 'Защита от травм''")
    public void clickHealthInsuranceLink(final int position) {
        partnerHealthInsurance.click();
    }


    @Step("Нажимаем на ссылку 'Пенсионное страхование'")
    public void clickPensionInsuranceLink(final int position) {
        partnerPensionInsurance.click();
    }


    @Step("Нажимаем на ссылку 'Страхование ответственности перед заказчиком'")
    public void clickLiabilityInsuranceLink(final int position) {
        partnerLiabilityInsurance.click();
    }
}
