package ru.instamart.reforged.business.frame.checkout.subsections;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.business.frame.B2BClose;
import ru.instamart.reforged.core.component.Button;

public interface B2BCommonFrameButtons extends B2BClose {

    Button save = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Сохранить']"), "Кнопка 'Сохранить'");
    Button cancel = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Отменить']"), "Кнопка 'Отменить'");
    Button delete = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Удалить']"), "Кнопка 'Удалить'");
    Button close = new Button(By.xpath("//header/button"), "кнопка Закрыть");

    @Step("Нажимаем 'Сохранить'")
    default void clickToSaveModal() {
        save.click();
    }

    @Step("Нажимаем 'Удалить'")
    default void clickToDeleteModal() {
        delete.click();
    }

    @Step("Нажимаем 'Отменить'")
    default void clickToCancelModal() {
        cancel.click();
    }

    @Step("Нажимаем 'Закрыть'")
    default void clickToCloseModal() {
        close.click();
    }
}
