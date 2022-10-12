package ru.instamart.reforged.admin.page.partners_map;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.MultiSelector;

public interface PartnersMapElement {

    MultiSelector citySelector = new MultiSelector(By.xpath("//div[@data-qa='filters_operational_zones_list']"), "селектор выбора 'Города'");
    MultiSelector typeOfCouriersSelector = new MultiSelector(By.xpath("//div[@data-qa='filters_performers_control']"), "селектор выбора 'Типа курьера'");
    MultiSelector deliveryAreaSelector = new MultiSelector(By.xpath("//div[@data-qa='filters_delivery_areas_control']"), "селектор выбора 'Территория доставки'");

    Input filterInput = new Input(By.xpath("//input[@data-qa='filter_query']"), "инпут 'ФИО, телефон или номер заказа'");

    Button refreshMapButton = new Button(By.xpath("//button[@data-qa='refresh_button']"), "кнопка 'Обновить карту'");
    Button resetButton = new Button(By.xpath("//button[@data-qa='filters_reset_button']"), "кнопка 'Сбросить'");
    Button applyButton = new Button(By.xpath("//button[@data-qa='filters_apply_button']"), "кнопка 'Применить'");

    Element map = new Element(By.xpath("//div[@data-qa='partners_map_page']"), "карта 'Ymap'");
    Element balloon = new Element(By.xpath("//ymaps[contains(@class,'balloon__content')]"), "балун с партнером");
    Element partnerNameBalloon = new Element(By.xpath("//ymaps[contains(@class,'balloon__content')]//ymaps/div/div[2]"), "имя партнера в балуне");
    Element partnerPhoneBalloon = new Element(By.xpath("//ymaps[contains(@class,'balloon__content')]//ymaps/div/div[5]"), "телефон партнера в балуне");
}
