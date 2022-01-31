package ru.instamart.reforged.business.page.checkout.contactsStep;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;

public class ContactsStep implements ContactsStepCheck {

    @Step("Заполняем контактные данные рандомными значениями")
    public void fillContactInfo() {
        fillFirstName(Generate.literalString(8));
        fillLastName(Generate.literalString(8));
        fillEmail(Generate.email());
    }

    @Step("Заполняем контактные данные значениями из userData {0}")
    public void fillContactInfo(final UserData userData) {
        fillFirstName(userData.getFirstName());
        fillLastName(userData.getLastName());
        fillEmail(userData.getEmail());
    }

    @Step("Заполняем поле 'Имя'")
    public void fillFirstName(String data) {
        firstName.fill(data);
    }

    @Step("Заполняем поле 'Фамилия'")
    public void fillLastName(String data) {
        lastName.fill(data);
    }

    @Step("Заполняем поле 'email'")
    public void fillEmail(String data) {
        email.fill(data);
    }

    @Step("Нажимаем 'Продолжить'")
    public void clickToSubmit() {
        submit.scrollTo();
        submit.hoverAndClick();
    }
}
