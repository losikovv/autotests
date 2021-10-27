package ru.instamart.reforged.stf.frame.checkout.subsections.create_company;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.Juridical;
import ru.instamart.reforged.stf.frame.Close;

public final class AddCompany implements Close, AddCompanyCheck {

    @Step("Добавить компанию {0}")
    public void fillCompany(final Juridical data) {
        fillInn(data.getInn());
        clickToSubmit();
        fillName(data.getJuridicalName());
        clickToSubmit();
    }

    @Step("Ввести ИНН '{0}'")
    public void fillInn(final String data) {
        inn.fill(data);
    }

    @Step("Нажать Добавить компанию")
    public void clickToSubmit() {
        submit.click();
    }

    @Step("Ввести Название '{0}'")
    public void fillName(final String data) {
        name.fill(data);
    }

    @Step("Нажать Хорошо")
    public void clickToOkButton() {
        okButton.click();
    }
}