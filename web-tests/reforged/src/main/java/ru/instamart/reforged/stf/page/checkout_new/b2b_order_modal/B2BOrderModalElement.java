package ru.instamart.reforged.stf.page.checkout_new.b2b_order_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface B2BOrderModalElement {

    Element modalTitle = new Element(By.xpath("//h3[contains(@class,'CheckoutModal') and contains(text(),'для бизнеса')]"), "Заголовок модалки заказа для бизнеса");
    Button close = new Button(By.xpath("//button[contains(@class,'Modal_closeButton')]/div"), "Кнопка 'Закрыть'");
    Button confirmButton = new Button(By.xpath("//a[contains(@class,'B2bTransferModal_confirmButton')]"), "Кнопка 'Да, хочу'");
}