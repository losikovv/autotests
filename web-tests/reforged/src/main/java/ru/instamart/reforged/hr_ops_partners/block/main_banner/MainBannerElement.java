package ru.instamart.reforged.hr_ops_partners.block.main_banner;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface MainBannerElement {
    Element mainBannerTitle = new Element(By.xpath("//div[contains(@class,'MainBanner_title_')]"), "Заголовок главного баннера");
    Element mainBannerSubtitle = new Element(By.xpath("//div[contains(@class,'MainBanner_subtitle')]"), "Подзаголовок главного баннера");
    Element mainBannerSalary = new Element(By.xpath("//div[contains(@class,'MainBanner_salary_')]"), "Инфо о доходе главного баннера");
    Element mainBannerShortDescription = new Element(By.xpath("//div[contains(@class,'MainBanner_shortDescription_')]/div"), "Краткая сводная информация главного баннера");
    Element fullDescriptionTitle = new Element(By.xpath("//div[contains(@class,'MainBanner_infoTitle_')][.='Что нужно делать']"), "Загловок блока с информацией о вакансии (на странице вакансии)");
    Element mainBannerFullDescription = new Element(By.xpath("//div[contains(@class,'MainBanner_infoDescription')]"), "Полное описание вакансии в баннере (на странице вакансии)");
    Button vacancyActionsButton = new Button(ByKraken.xpathExpression("//button[contains(@class,'MainBanner_button')][.='%s']"), "Кнопка действий на баннере");
    Element mainBannerImage = new Element(By.xpath("//div[contains(@class,'MainBanner_image_')]"), "Картинка баннера");
}
