package ru.instamart.reforged.stf.frame.shipment_cancel_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface ShipmentCancelModalElement {

    Element shipmentCancelModal = new Element(By.xpath("//div[@data-qa='user-shipment-cancel-modal']"), "окно отмены заказа");
    Button accept = new Button(By.xpath("//button[@data-qa='user-shipment-cancel-modal-btn-cancel']"), "кнопка 'Да, отменить'");
    Button acceptProd = new Button(By.xpath("//div[@data-qa='user-shipment-cancel-modal']//button[@type='submit']"), "кнопка 'Да, отменить' на проде");
    Button decline = new Button(By.xpath("//button[@data-qa='user-shipment-cancel-modal-btn-dismiss']"), "кнопка 'Не отменять'");
}
