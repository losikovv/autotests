package ru.instamart.reforged.hr_ops_partners.frame.success_sent_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface SuccessSentModalElement {

    Button close = new Button(By.xpath("//button[contains(@class,'Modal_closeButton_')]"), "Кнопка 'X' (закрыть)");
    Element modalTitle = new Element(By.xpath("(//div[contains(@class,'SuccesModal_title_')])[1]"), "Заголовок окна успешной отправки");
    Element description = new Element(By.xpath("//div[contains(@class,'SuccesModal_description_')]"), "Информация в окне успешной отправки");
    Element adviceBlockTitle = new Element(By.xpath("(//div[contains(@class,'SuccesModal_title_')])[2]"), "Заголовок блока 'Важно не забыть'");
    Element faqQuestionByTitle = new Element(ByKraken.xpathExpression("//div[contains(@class,'SuccesModal_root_')]//button[contains(@class,'Faq_itemTitle_')][contains(.,'%s')]"), "Пункт FAQ по заголовку");
    Element faqAnswerByTitle = new Element(ByKraken.xpathExpression("//div[contains(@class,'SuccesModal_root_')]//button[contains(@class,'Faq_itemTitle_')][contains(.,'%s')]/following-sibling::div"), "Ответ FAQ по заголовку вопроса");
}
