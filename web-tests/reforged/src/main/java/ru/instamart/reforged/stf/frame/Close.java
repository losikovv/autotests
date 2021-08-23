package ru.instamart.reforged.stf.frame;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;

public interface Close {

    Button closeModal = new Button(By.xpath("//div[@id='react-modal']//button[@aria-label='Закрыть']"));

    default void close() {
        closeModal.click();
    }
}
