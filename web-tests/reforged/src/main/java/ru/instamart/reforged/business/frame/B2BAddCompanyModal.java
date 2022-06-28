package ru.instamart.reforged.business.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;
import static ru.instamart.reforged.core.config.WaitProperties.WAITING_TIMEOUT;

public class B2BAddCompanyModal {

    private final Input inn = new Input(By.xpath("//input[@id='FormGroup_inn']"), "Поле ввода ИНН");
    private final Element innError = new Element(By.xpath("//input[@id='FormGroup_inn']/following-sibling::label[2]"), "Ошибка ввода в поле 'ИНН'");
    private final Input name = new Input(By.xpath("//input[@id='FormGroup_name']"), WAITING_TIMEOUT, "Поле ввода названия");
    private final Button addCompany = new Button(By.xpath("//button[@data-qa='create_company_form_submit_button']"), "Кнопка 'Добавить компанию'");
    private final Button close = new Button(By.xpath("//button[@aria-label='Закрыть']"), "Кнопка 'Закрыть'");

    @Step("Закрываем модальное окно добавления компании")
    public void closeModal() {
        close.click();
    }

    @Step("Проверяем, что окно 'Добавление компании' отображается")
    public void checkAddCompanyModalVisible() {
        waitAction().shouldBeVisible(addCompany);
    }

    @Step("Проверяем, что окно 'Добавление компании' не отображается")
    public void checkAddCompanyModalNotVisible() {
        waitAction().shouldNotBeVisible(addCompany);
    }

    @Step("Вводим ИНН: {innCode}")
    public void fillInn(final String innCode) {
        inn.fill(innCode);
    }

    @Step("Проверяем, что ошибка ввода в поле ИНН отображается")
    public void checkInnInputErrorIsVisible() {
        waitAction().shouldBeVisible(innError);
    }

    @Step("Проверяем, что текст ошибки ввода: {expectedErrorText}")
    public void checkErrorTextEquals(final String expectedErrorText) {
        assertEquals(innError.getText(), expectedErrorText, "Текст ошибки ввода отличается от ожидаемого");
    }

    @Step("Вводим название компании: {companyName}")
    public void fillName(final String companyName) {
        name.fill(companyName);
    }

    @Step("Нажимаем кнопку 'Добавить компанию'")
    public void clickAddCompany() {
        addCompany.click();
    }
}
