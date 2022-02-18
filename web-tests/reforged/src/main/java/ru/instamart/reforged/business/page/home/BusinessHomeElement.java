package ru.instamart.reforged.business.page.home;

import org.openqa.selenium.By;
import ru.instamart.reforged.business.block.header.Header;
import ru.instamart.reforged.business.frame.GetCallbackModal;
import ru.instamart.reforged.business.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;

public interface BusinessHomeElement {

    Header header = new Header();
    AuthModal authModal = new AuthModal();
    GetCallbackModal getCallBackModal = new GetCallbackModal();

    Element b2bLandingStores = new Element(By.xpath("//div[@data-qa='b2b_landing_stores']"), "Раздел магазинов на лендинге");
    ElementCollection retailerNames = new ElementCollection(By.xpath("(//button[./div[contains(@data-qa,'b2b_landing_stores_image')]])/div[2]/div[1]"), "Названия ритейлеров в блоке 'До 20% от чека'");

    Button getCallback = new Button(By.xpath("//header//button[.//span[contains(.,'Заказать обратный звонок')]]"), "Кнопка 'Заказать обратный звонок'");
    Button addCompany = new Button(By.xpath("//button[contains(.,'Создать профиль компании')]"), "Кнопка 'Создать профиль компании'");
    Link delivery = new Link(By.xpath("//header//a[contains(.,'Доставка')]"), "Кнопка 'Доставка'");
    Link benefits = new Link(By.xpath("//header//a[contains(.,'Преимущества')]"), "Кнопка 'Преимущества'");
    Link howToOrder = new Link(By.xpath("//header//a[contains(.,'Как заказать')]"), "Кнопка 'Как заказать'");
    Link cities = new Link(By.xpath("//header//a[contains(.,'Города')]"), "Кнопка 'Города'");
    Link FAQ = new Link(By.xpath("//header//a[contains(.,'Ответы на вопросы')]"), "Кнопка 'Ответы на вопросы'");
    Link certificates = new Link(By.xpath("//header//a[contains(.,'Сертификаты')]"), "Кнопка 'Сертификаты'");
}
