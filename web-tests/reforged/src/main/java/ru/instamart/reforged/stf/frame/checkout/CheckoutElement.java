package ru.instamart.reforged.stf.frame.checkout;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;

public interface CheckoutElement {

    Button checkoutButton = new Button(By.xpath("//div[@class='checkout-sidebar__content']/descendant::button[@data-qa='checkout_order_button']"));
}
