package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.approve_page;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.page.shipment.shipment.send_promo.approve_page.cancel_modal.CancelPromoModal;
import ru.instamart.reforged.core.component.Element;

public interface ApprovePromoElement {

    CancelPromoModal cancelModal = new CancelPromoModal();

    Element approvePromoPage = new Element(By.xpath("//div[@data-qa='order_compensation_edit_page']"), "Страница подтверждения промо");
    Element cancelPromoButton = new Element(By.xpath("//button[@data-qa='cancel-button']"), "Кнопка 'Отказать'");
}