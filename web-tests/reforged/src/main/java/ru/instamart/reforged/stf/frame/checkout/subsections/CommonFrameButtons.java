package ru.instamart.reforged.stf.frame.checkout.subsections;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;

public interface CommonFrameButtons {

    Button save = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Сохранить']"));
    Button cancel = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Отменить']"));
    Button delete = new Button(By.xpath("//div[@class='modal-form']//button[text() = 'Удалить']"));
    Button close = new Button(By.xpath("//header/button"));

    @Step("Нажать 'Сохранить'")
    default void clickToSaveModal() {
        save.click();
    }

    @Step("Нажать 'Отменить'")
    default void clickToCancelModal() {
        cancel.click();
    }

    @Step("Нажать 'Удалить'")
    default void clickToDeleteModal() {
        delete.click();
    }

    @Step("Нажать 'Закрыть'")
    default void clickToCloseModal() {
        close.click();
    }
}
