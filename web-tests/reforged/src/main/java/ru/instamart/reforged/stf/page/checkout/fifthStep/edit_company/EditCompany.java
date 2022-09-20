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

    @Step("Ввести юрлицо грузополучателя '{0}'")
    public void fillConsigneeName(final String data) {
        consigneeName.fill(data);
    }

    @Step("Ввести адрес грузополучателя '{0}'")
    public void fillConsigneeAddress(final String data) {
        consigneeAddress.fill(data);
    }

    @Step("Ввести кпп грузополучателя '{0}'")
    public void fillConsigneeKpp(final String data) {
        consigneeKpp.fill(data);
    }

    @Step("Нажать 'сохранить'")
    public void saveCompanyInfo() {
        save.click();
    }
}
