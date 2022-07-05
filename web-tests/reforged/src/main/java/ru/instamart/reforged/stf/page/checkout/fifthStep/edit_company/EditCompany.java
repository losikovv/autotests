package ru.instamart.reforged.stf.page.checkout.fifthStep.edit_company;

import io.qameta.allure.Step;

public class EditCompany implements EditCompanyElement {

    @Step("Ввести Название '{0}'")
    public void fillName(final String data) {
        name.fill(data);
    }

    @Step("Ввести адрес '{0}'")
    public void fillAddress(final String data) {
        address.fill(data);
    }

    @Step("Ввести кпп '{0}'")
    public void fillKpp(final String data) {
        kpp.fill(data);
    }

    @Step("Нажать 'сохранить'")
    public void saveCompanyInfo() {
        save.click();
    }
}
