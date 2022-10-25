package ru.instamart.reforged.hr_ops_partners.block.header;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.hr_ops_partners.frame.regions_modal.RegionsModal;

public interface HeaderElement {

    RegionsModal regionsModal = new RegionsModal();

    Link logo = new Link(By.xpath("//a[contains(@class,'Header_logoLink')]"), "Ссылка перехода на Главную");
    Element phone = new Element(By.xpath("//div[contains(@class,'Header_phone_')]"), "Номер телефона");
    Button selectedCity = new Button(By.xpath("//button[contains(@class,'Header_locationButton')]"), "Кнопка текущего города");
    Element headerTooltip = new Element(By.xpath("//div[contains(@class,'Header_location')]//div[contains(@class,'Header_tooltip_')]"), "Тултип с предложением 'Ваш город ...?'");
    Element suggestedCityInTooltip = new Element(By.xpath("//div[contains(@class,'Header_location')]//span[contains(@class,'Header_textGreen')]"), "Название города в тултипе (автоопределение)");
    Button confirmRegion = new Button(By.xpath("//div[contains(@class,'Header_location')]//button[.='Да']"), "Кнопка 'Да' подтверждения города в тултипе");
    Button selectAnotherRegion = new Button(By.xpath("//div[contains(@class,'Header_location')]//button[.='Выбрать другой']"), "Кнопка 'Выбрать другой' перехода к выбору города в тултипе");
}
