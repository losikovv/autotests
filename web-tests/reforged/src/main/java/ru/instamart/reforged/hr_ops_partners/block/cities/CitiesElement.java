package ru.instamart.reforged.hr_ops_partners.block.cities;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface CitiesElement {
    Element title = new Element(By.xpath("//div[contains(@class,'Cities_title_')]"), "Заголовок блока");
    Element subtitle = new Element(By.xpath("//div[contains(@class,'Cities_subtitle_')]"),"Подзаголовок блока");
    ElementCollection allCities = new ElementCollection(By.xpath("//div[contains(@class,'Cities_item_')]"),"Полный список городов");
    ElementCollection visibleCities = new ElementCollection(By.xpath("//div[contains(@class,'Cities_row')]//div[contains(@class,'Cities_item_')]"),"Список отображаемых городов");
    ElementCollection hiddenCities = new ElementCollection(By.xpath("//div[contains(@class,'Cities_collapse')]//div[contains(@class,'Cities_item_')]"), "Список городов скрытых под спойлером");
    Button showAllButton = new Button(ByKraken.xpathExpression("//button[contains(@class,'Cities_button_')][.='Показать все']"), "Кнопка 'Показать все'");
    Button hideButton = new Button(ByKraken.xpathExpression("//button[contains(@class,'Cities_button_')][.='Скрыть']"), "Кнопка 'Скрыть'");

}
