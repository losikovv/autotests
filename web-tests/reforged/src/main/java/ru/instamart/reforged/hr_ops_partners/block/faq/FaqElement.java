package ru.instamart.reforged.hr_ops_partners.block.faq;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface FaqElement {
    Element title = new Element(By.xpath("//div[contains(@class,'SectionWrapper_wrapper')][.//div[contains(@class,'Faq_root')]]/div[contains(@class,'SectionWrapper_title_')]"), "Заголовок блока");
    ElementCollection allFaqsList = new ElementCollection(By.xpath("//div[contains(@class,'Faq_root_')]//button[contains(@class,'Faq_itemTitle_')]"), "Полный список вопросов");
    Button faqByTitle = new Button(ByKraken.xpathExpression("//button[contains(@class,'Faq_itemTitle_')][contains(.,'%s')]"), "Вопрос по заголовку");
    Button faqAnswerByTitle = new Button(ByKraken.xpathExpression("//button[contains(@class,'Faq_itemTitle_')][contains(.,'%s')]/following-sibling::div"), "Ответ на вопрос по заголовку");
    ElementCollection mainFaqsList = new ElementCollection(By.xpath("//div[contains(@class,'Faq_root_')]/div[contains(@class,'Expand_root')]//button[contains(@class,'Faq_itemTitle_')]"), "Основной список вопросов");
    ElementCollection hiddenFaqsList = new ElementCollection(By.xpath("//div[contains(@class,'Faq_collapseBlock_')]//button[contains(@class,'Faq_itemTitle_')]"), "Список вопросов под спойлером");
    ElementCollection expandedFaqsList = new ElementCollection(By.xpath("//button[contains(@class,'Faq_itemTitle_')][@aria-expanded='true']"), "Список FAQ с развернутыми ответами");
    Button moreFaq = new Button(By.xpath("//button[contains(@class,'Faq_showMoreButton_')][.='Больше вопросов']"), "Кнопка 'Больше вопросов'");
    Button lessFaq = new Button(By.xpath("//button[contains(@class,'Faq_showMoreButton_')][.='Меньше вопросов']"), "Кнопка 'Меньше вопросов'");
}
