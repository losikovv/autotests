package ru.instamart.reforged.admin.page.shipment.shipment.send_promo;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.page.shipment.shipment.send_promo.confirm_modal.ConfirmModal;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;

public interface SendPromoElement {

    ConfirmModal confirmModal = new ConfirmModal();

    Element promoPage = new Element(By.xpath("//div[@data-qa='order_compensation_create_page']"), "Страница выдачи промо");
    Element reason = new Element(By.xpath("//div[@data-qa='order_compensation_reasons']"), "Причина выдачи промо");
    Element reasonInList = new Element(ByKraken.xpathExpression("//div[@id='reason_list']/following-sibling::div//div[text()='%s']"), "Причина выдачи промо в списке");
    Element compensation = new Element(By.xpath("//div[@data-qa='order_compensation_promo_type']"), "Вид выдаваемого промо");
    Element compensationInList = new Element(ByKraken.xpathExpression("//div[@id='promoType_list']/following-sibling::div//div[text()='%s']"), "Тип промо в списке");
    Element compensationItem = new Element(By.xpath("//div[@data-qa='order_compensation_line_items']"), "Выбор товаров для возврата");
    ElementCollection compensationItemInList = new ElementCollection(By.xpath("//div[@id='orderLineItemIds_list']/following-sibling::div"), "Продукты в списке");
    Element compensationValue = new Element(By.xpath("//div[@data-qa='order_compensation_promotions']"), "Сумма выдаваемого промо");
    Element compensationValueInList = new Element(ByKraken.xpathExpression("//div[@id='promotionId_list']/following-sibling::div//div[contains(text(),'%s')]"), "Cумма промо в списке");
    Input compensationValueInput = new Input(By.xpath("//input[@id='promotionId']"), "Инпут суммы промо");
    Button sendPromoButton = new Button(ByKraken.xpathExpression("//div[@data-qa='order_compensation_create_page']//button[@type='submit']"), "Кнопка 'Отправить'");
    Input emailInput = new Input(By.xpath("//input[@data-qa='order_compensation_email']"), "Инпут емейла");
    Input commentInput = new Input(By.xpath("//input[@data-qa='order_compensation_comment']"), "Инпут комментария");
}