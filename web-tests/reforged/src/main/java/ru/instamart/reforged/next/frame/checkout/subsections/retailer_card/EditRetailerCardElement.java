package ru.instamart.reforged.next.frame.checkout.subsections.retailer_card;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Input;

public interface EditRetailerCardElement {

    Input value = new Input(By.xpath("//div[@class='rc-modal__body']//input[@placeholder='Номер карты']"),
            "поле ввода номера карты в модалке карты ритейлера в чекауте");
}
