package ru.instamart.reforged.hr_ops_partners.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public class VacancyNotAvailableModal {

    private final Button close = new Button(By.xpath("//button[contains(@class,'Modal_closeButton_')]"), "Кнопка 'X' (закрыть)");
    private final Element title = new Element(By.xpath("//h3[contains(@class,'Modal_title')][.='Вакансия не найдена']"), "Заголовок окна 'Вакансия не найдена'");
    private final Element content = new Element(By.xpath("//main[contains(@class,'Modal_content')]"), "Текст в окне 'Вакансия не найдена'");

    @Step("Проверяем, что отображается окно 'Вакансия не найдена'")
    public void checkModalVisible() {
        Kraken.waitAction().shouldBeVisible(title);
    }

    @Step("Проверяем, что окно 'Вакансия не найдена' не отображается")
    public void checkModalNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(title);
    }

    @Step("Проверяем, что текст в окне 'Вакансия не найдена' содержит: '{expectedText}'")
    public void checkModalContent(final String expectedText) {
        Assert.assertTrue(content.getText().contains(expectedText), "Сообщение в окне: '" + content.getText() + "' не содержит ожидаемый текст: '" + expectedText + "'");
    }

    @Step("Нажимаем 'Х' (закрыть)")
    public void close() {
        close.click();
    }
}
