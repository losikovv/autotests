package ru.instamart.reforged.next.frame.checkout.subsections.edit_payment_card;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface EditPaymentCardElement {

    Element editPaymentCardModal = new Element(By.xpath("//div[@id='PaymentToolSelector']"),
            "Модальное окно редактирования карты");
}
