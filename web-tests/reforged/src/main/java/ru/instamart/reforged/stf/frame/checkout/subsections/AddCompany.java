package ru.instamart.reforged.stf.frame.checkout.subsections;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.stf.frame.Close;

public class AddCompany implements Close {

    private final Input inn = new Input(By.xpath("//input[@name='inn']"));
    private final Button submit = new Button(By.xpath("//button[@data-qa='create_company_form_submit_button']"));
    private final Input name = new Input(By.xpath("//input[@name='name']"));
    private final Button okButton = new Button(By.xpath("//button[@data-qa='create_company_ok_button']"));

    @Step("Ввести ИНН")
    public void fillInn(String data) {
        inn.fill(data);
    }

    @Step("Нажать Добавить компанию")
    public void clickToSubmit() {
        submit.click();
    }

    @Step("Ввести Название")
    public void fillName(String data) {
        name.fill(data);
    }

    @Step("Нажать Хорошо")
    public void clickToOkButton() {
        okButton.click();
    }
}