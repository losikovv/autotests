package ru.instamart.reforged.hr_ops_partners.block.footer;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;

public interface FooterElement {
    Element logo = new Element(By.xpath("//*[contains(@class,'Footer_logo_')]"), "Логотип 'Сбермаркет'");
    Element socialsTitle = new Element(By.xpath("//p[contains(@class,'Footer_socialTitle')]"), "Заголовок блока соцсетей");
    ElementCollection socialLinks = new ElementCollection(By.xpath("//div[contains(@class,'Footer_socialBlock_')]/a"), "Иконки соцсетей");
    Element textBlockLeft = new Element(By.xpath("//div[contains(@class,'Footer_textBlock_')][1]"), "Информационный блок 'По вопросам сотрудничества'");
    Element textBlockRight = new Element(By.xpath("//div[contains(@class,'Footer_textBlock_')][2]"), "Инфомационный блок 'Адрес'");
    Element partnersInfoBlock = new Element(By.xpath("//div[contains(@class,'Footer_partnersInfo_')]"), "Информационный блок для партнеров");
    Link partnerHealthInsurance = new Link(By.xpath("//a[contains(@class,'Footer_partnerLink_')][.='Программа «Защита от травм»']"), "Ссылка 'Программа 'Защита от травм''");
    Link partnerPensionInsurance = new Link(By.xpath("//a[contains(@class,'Footer_partnerLink_')][.='Пенсионное страхование']"), "Ссылка 'Пенсионное страхование'");
    Link partnerLiabilityInsurance = new Link(By.xpath("//a[contains(@class,'Footer_partnerLink_')][.='Страхование ответственности перед заказчиком']"), "Ссылка 'Страхование ответственности перед заказчиком'");

}
