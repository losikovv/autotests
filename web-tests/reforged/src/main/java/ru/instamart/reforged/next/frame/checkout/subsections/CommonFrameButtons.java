package ru.instamart.reforged.next.frame.checkout.subsections;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.next.frame.Close;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CommonFrameButtons extends Close {

    Element modal = new Element(By.xpath("//div[@class='modal-form']"), "модальное окно");
    Button save = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Сохранить']"), "кнопка Сохранить");
    Button cancel = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Отменить']"), "кнопка Отменить");
    Button delete = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Удалить']"), "кнопка Удалить");
    Button close = new Button(By.xpath("//header/button"), "кнопка Закрыть");

    @Step("Модальное окно открыто")
    default void checkModalWindow() {
        waitAction().shouldBeVisible(modal);
        waitAction().shouldNotBeAnimated(modal);
    }

    @Step("Модальное окно не отображается")
    default void checkModalWindowNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(modal);
    }

    @Step("Нажать 'Сохранить'")
    default void clickToSaveModal() {
        save.click();
    }

    @Step("Нажать 'Удалить'")
    default void clickToDeleteModal() {
        delete.click();
    }

    @Step("Нажать 'Отменить'")
    default void clickToCancelModal() {
        cancel.click();
    }

    @Step("Нажать 'Закрыть'")
    default void clickToCloseModal() {
        close.click();
    }
}
