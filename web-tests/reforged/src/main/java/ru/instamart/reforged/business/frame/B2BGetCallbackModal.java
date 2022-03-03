package ru.instamart.reforged.business.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

import static ru.instamart.reforged.core.Kraken.waitAction;

public class B2BGetCallbackModal implements B2BClose {

    private final Element callbackModal = new Element(By.xpath("//div[./form][contains(.,'Заказать обратный звонок')]"), "Модальное окно 'Заказать обратный звонок'");
    private final Input nameInput = new Input(By.xpath("//input[@id='FormGroup_userName']"), "Поле ввода 'Как вас зовут?'");
    private final Input companyNameInput = new Input(By.xpath("//input[@id='FormGroup_companyName']"), "Поле ввода 'Название компании'");
    private final Input phoneInput = new Input(By.xpath("//input[@id='FormGroup_phone']"), "Поле ввода 'Номер телефона'");
    private final Button confirm = new Button(By.xpath("//button[@data-qa='b2b_callback_request_button']"), "Кнопка 'Оставить заявку'");

    @Step("Проверяем, что окно 'Заказать обратный звонок' отображается")
    public void checkCallbackModalVisible() {
        waitAction().shouldBeVisible(callbackModal);
    }

    @Step("Вводим имя: {name}")
    public void fillName(final String name) {
        nameInput.fill(name);
    }

    @Step("Вводим название компании: {companyName}")
    public void fillCompanyName(final String companyName) {
        companyNameInput.fill(companyName);
    }

    @Step("Вводим телефон: {phone}")
    public void fillPhone(final String phone) {
        phoneInput.fillField(phone, true);
    }

    @Step("Нажимаем кнопку 'Оставить заявку'")
    public void clickConfirm() {
        confirm.click();
    }
}
