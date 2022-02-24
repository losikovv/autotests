package ru.instamart.reforged.stf.page.checkout.secondStep;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.util.StringUtil;

public class ContactsStep implements ContactsStepElement {

    @Step("Заполнить контактные данные рандомными значениями")
    public void fillContactInfo() {
        fillFirstName(Generate.literalString(8));
        fillLastName(Generate.literalString(8));
        fillEmail(Generate.email());
    }

    @Step("Заполнить контактные данные значениями из userData {0}")
    public void fillContactInfo(final UserData userData) {
        fillFirstName(userData.getFirstName());
        fillLastName(userData.getLastName());
        fillEmail(userData.getEmail());
    }

    @Step("Заполнить имя")
    public void fillFirstName(String data) {
        firstName.fill(data);
    }

    @Step("Заполнить Фамилия")
    public void fillLastName(String data) {
        lastName.fill(data);
    }

    @Step("Заполнить email")
    public void fillEmail(String data) {
        email.fill(data);
    }

    @Step("Выбрать чекбокс Отправлять информацию о заказе на email")
    public void checkEmailInfo() {
        emailInfo.check();
    }

    @Step("Снять выбор чекбокса Отправлять информацию о заказе на email")
    public void uncheckEmailInfo() {
        emailInfo.uncheck();
    }

    @Step("Нажать Добавить новый телефон")
    public void clickToAddNewNumber() {
        addNewNumber.click();
    }

    @Step("Нажать Изменить")
    public void clickToChangePhone() {
        changePhone.click();
    }

    @Step("Нажать Изменить у определенного номера телефона")
    public void clickToChangePhoneWithText(final String phone) {
        changePhoneWithText.click(StringUtil.convertDigitsStringToPhoneNumberWithBrackets(phone));
    }

    @Step("Нажать Продолжить")
    public void clickToSubmit() {
        submit.scrollTo();
        submit.hoverAndClick();
    }

}
