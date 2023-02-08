package ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.collector;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface CollectorElement {

    Element vacancyCartImage = new Element(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//div[contains(@class,'VacancyCart_image_')]"), "Картинка вакансии");
    Element vacancyCartTitle = new Element(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//div[contains(@class,'VacancyCart_title')]"), "Заголовок вакансии");
    Element vacancyCartDescription = new Element(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//div[contains(@class,'VacancyCart_description')]"), "Описание вакансии");
    Element vacancyCartSalary = new Element(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//div[contains(@class,'VacancyCart_salary')]"), "Уровень дохода по вакансии");

    ElementCollection advantageList = new ElementCollection(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//div[contains(@class,'Advantage_info')]"), "Примущества вакансии");
    ElementCollection advantageTitles = new ElementCollection(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//div[contains(@class,'Advantage_title')]"), "Преимуцщества вакансии - заголовок");
    ElementCollection advantageDescriptions = new ElementCollection(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//div[contains(@class,'Advantage_description')]"), "Преимуцщества вакансии - описание");

    Button respond = new Button(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//button[contains(@class,'VacancyCart_respondAction')]"), "Кнопка 'Откликнуться'");
    Button disabledRespond = new Button(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//button[contains(@class,'VacancyCart_respondAction')][@disabled]"), "Заблокированная кнопка 'Откликнуться'");

    Button moreInfo = new Button(By.xpath("//div[contains(@class,'VacancyCart_wrapper')][.//div[contains(.,'Cборщик в СберМаркет')]]//button[contains(@class,'VacancyCart_moreBtn')]"), "Кнопка 'Подробнее'");

}
