package ru.instamart.reforged.business.page.checkout.deliveryStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;

public interface DeliveryOptionElement {

    Button submit = new Button(By.xpath("//button[@data-qa='checkout_ship_address_form_submit_button']"), "Кнопка 'Продолжить' шага 'Способ получения'");
}