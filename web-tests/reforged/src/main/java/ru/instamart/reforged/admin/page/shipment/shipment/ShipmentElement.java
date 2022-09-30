package ru.instamart.reforged.admin.page.shipment.shipment;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface ShipmentElement {

    AuthoredHeader authoredHeader = new AuthoredHeader();

    Element orderInfo = new Element(By.xpath("//div[@id='order-form-wrapper']"), "Информация о заказе");
    Link deliveryWindowLink = new Link(By.xpath("//a[contains(@class,'icon-calendar')][./span[contains(.,'Магазин и время доставки')]]"), "Ссылка 'Магазин и время доставки'");
    Link paymentsLink = new Link(By.xpath("//a[contains(@class,'icon-credit-card')][./span[contains(.,'Платежи')]]"), "Ссылка 'Платежи'");
    Link sendPromo = new Link(By.xpath("//a[@class='button' and contains(@href,'/compensations/new')]"), "Ссылка 'Отправить промокод'");
    Element promoCodeData = new Element(By.xpath("//tr[@class='show-promotion total']/td/span"), "Строка с промокодом");
    Element compensationPromoStatusPending = new Element(By.xpath("//span[contains(@class,'compensation-promo__status--pending') and contains(text(),'Скоро отправим')]"), "Статус отправки промокода с извинениями - скоро отправим");
    Element compensationPromoStatusOnApprove = new Element(By.xpath("//span[contains(@class,'compensation-promo__status--pending') and contains(text(),'На согласовании')]"), "Статус отправки промокода с извинениями - на согласовании");
    Element compensationPromoText = new Element(By.xpath("//span[@class='compensation-promo__value ']"), "Текст промокода");
    Element compensationPromoStatus = new Element(By.xpath("//span[contains(@class,'compensation-promo__status')]"), "Статус промо");
    Element compensationPromoDeleteButton = new Element(By.xpath("//a[@class='compensation-cancel']"), "Кнопка 'Удалить промокод'");
    Element compensationPromoExpandButton = new Element(By.xpath("//span[contains(@class,'order-compensation__expand-btn')]"), "Кнопка 'Развернуть'");
    Element compensationPromoExpirationDate = new Element(By.xpath("//div[contains(@class,'order-compensation__title')]/following-sibling::div"), "Срок действия промокода");
    Element compensationPromoReason = new Element(By.xpath("//span[@class='compensation-promo__prefix' and text()='Причина выдачи:']"), "Причина выдачи промокода");
    Element compensationPromoIssueDate = new Element(By.xpath("//span[@class='compensation-promo__prefix' and text()='Выдан:']"), "Дата выдачи промокода");
    Element compensationPromoOperatorName = new Element(By.xpath("//span[@class='compensation-promo__prefix' and text()='Оператор:']"), "Оператор, выдавший промокод");
    Element compensationPromoComment = new Element(By.xpath("//span[@class='compensation-promo__prefix' and text()='Комментарий:']"), "Комментарий к промокоду");
    Element compensationPromoReturnItems = new Element(By.xpath("//table[@class='compensation-line-items-table']"), "Таблица с возвратными товарами");
    Element compensationPromoApproveButton = new Element(By.xpath("//a[@class='compensation-approve']"), "Кнопка аппрува промокода");
}
