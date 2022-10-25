package ru.instamart.reforged.business.page.home;

import org.openqa.selenium.By;
import ru.instamart.reforged.business.block.header.B2BHeader;
import ru.instamart.reforged.business.frame.B2BGetCallbackModal;
import ru.instamart.reforged.business.frame.auth.auth_modal.B2BAuthModal;
import ru.instamart.reforged.business.block.header_multisearch.B2BMultisearchHeader;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;

public interface B2BHomeElement {

    B2BHeader header = new B2BHeader();
    B2BMultisearchHeader multisearchHeader = new B2BMultisearchHeader();
    B2BAuthModal authModal = new B2BAuthModal();
    B2BGetCallbackModal getCallBackModal = new B2BGetCallbackModal();
    Element b2bLandingStores = new Element(By.xpath("//div[@data-qa='b2b_home_landing_delivery_retailers_block']"), "Раздел магазинов на лендинге");
    Element storeBySid = new Element(ByKraken.xpathExpression("//a[contains(@data-qa,'b2b_home_landing_stores_block_store_card')][@href='/stores/%s']"), "Карточка магазина по номеру SID");
    ElementCollection retailerNames = new ElementCollection(By.xpath("(//button[./div[contains(@data-qa,'b2b_landing_stores_image')]])/div[2]/div[1]"), "Названия ритейлеров в блоке 'До 20% от чека'");

    Button getCallback = new Button(By.xpath("//footer//button[.//span[contains(.,'Заказать обратный звонок')]]"), "Кнопка 'Заказать обратный звонок'");
    Button addCompany = new Button(By.xpath("//button[contains(.,'Создать профиль компании')]"), "Кнопка 'Создать профиль компании'");
    Link delivery = new Link(By.xpath("//footer//a[contains(.,'Доставка')]"), "Кнопка 'Доставка'");
    Link benefits = new Link(By.xpath("//footer//a[contains(.,'Преимущества')]"), "Кнопка 'Преимущества'");
    Link howToOrder = new Link(By.xpath("//footer//a[contains(.,'Как заказать')]"), "Кнопка 'Как заказать'");
    Link cities = new Link(By.xpath("//footer//a[contains(.,'Города')]"), "Кнопка 'Города'");
    Link FAQ = new Link(By.xpath("//footer//a[contains(.,'Ответы на вопросы')]"), "Кнопка 'Ответы на вопросы'");
    Link certificates = new Link(By.xpath("//footer//a[contains(.,'Сертификаты')]"), "Кнопка 'Сертификаты'");
}
