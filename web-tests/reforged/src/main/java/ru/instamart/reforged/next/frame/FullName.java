package ru.instamart.reforged.next.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public final class FullName implements Close {

    private final Input firstName = new Input(By.name("firstname"), "поле для ввода Имени");
    private final Input lastName = new Input(By.name("lastname"), "поле для ввода Фамилии");
    private final Button submit = new Button(By.xpath("//button[@data-qa='user_edit_personal_form_submit_button' and text()='Сохранить']"), "кнопка 'Сохранить'");
    private final Button cancel = new Button(By.xpath("//button[@data-qa='user_edit_personal_form_submit_button' and text()='Отменить']"), "кнопка 'Отменить'");

    @Step("Заполнить поле 'Имя' значением {0}")
    public void fillFirstName(final String data) {
        firstName.fill(data);
    }

    @Step("Заполнить поле 'Фамилия' значением {0}")
    public void fillLastName(final String data) {
        lastName.fill(data);
    }

    @Step("Подтвердить форму")
    public void submit() {
        submit.click();
    }

    @Step("Отменить форму")
    public void cancel() {
        cancel.click();
    }
}
