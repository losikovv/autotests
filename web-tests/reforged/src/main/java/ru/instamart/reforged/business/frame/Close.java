package ru.instamart.reforged.business.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.instamart.reforged.core.component.Button;

public interface Close {

    Button closeModal = new Button(By.xpath("//div[@id='react-modal']//button[@aria-label='Закрыть']"), "кнопка закрытия модального окна");

    @Step("Закрыть фрейм")
    default void close() {
        closeModal.click();
    }

    @Step("Закрыть фрейм нажатием клавиши Esc")
    default void closeByEsc() {
        closeModal.getActions().sendKeys(Keys.ESCAPE);
    }
}
