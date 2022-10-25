package ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.driver_courier;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface DriverCourierElement {

    Element vacancyCartImage = new Element(By.xpath("//div[contains(@class,'AnotherVacancy_root')][.//a[@href='/voditel-kurer']]/div[contains(@class,'AnotherVacancy_overlay_')]"), "Картинка вакансии");
    Element vacancyCartTitle = new Element(By.xpath("//div[contains(@class,'AnotherVacancy_root')][.//a[@href='/voditel-kurer']]//div[contains(@class,'AnotherVacancy_title')]"), "Заголовок вакансии");
    Element vacancyCartDescription = new Element(By.xpath("//div[contains(@class,'AnotherVacancy_root')][.//a[@href='/voditel-kurer']]//div[contains(@class,'AnotherVacancy_description_')]"), "Описание вакансии");
    Button moreInfo = new Button(By.xpath("//div[contains(@class,'AnotherVacancy_root')][.//a[@href='/voditel-kurer']]//div[contains(@class,'AnotherVacancy_buttonDescription_')]"), "Кнопка 'Подробнее'");
}
