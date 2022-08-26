package ru.instamart.reforged.stf.frame.order_evaluation_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface OrderEvaluationModalElement {

    ElementCollection evaluationStars = new ElementCollection(By.xpath("//button[@data-qa='review_modal_main_display_details_button']/preceding-sibling::ul/li"), "Звёзды оценки заказа");
    Button orderDetails = new Button(By.xpath("//button[@data-qa='review_modal_main_display_details_button']"), "Кнопка 'Детали заказа'");
    ElementCollection negativeTags = new ElementCollection(By.xpath("(//button[@data-qa='review_modal_main_display_send_feedback_button']/../../ul)[2]/li"), "Список тэгов для негативной оценки");
    Input commentTextarea = new Input(By.xpath("//label[contains(.,'Ваш комментарий')]/preceding-sibling::textarea"), "Поле ввода 'Ваш комментарий'");
    Element addedFile = new Element(By.xpath("//label[contains(.,'Ваш комментарий')]/../../preceding-sibling::div"), "Превью добавленного файла в блоке 'Ваш комментарий");
    Link addPhoto = new Link(By.xpath("//label[contains(.,'Добавить фото')]"), "Кнопка 'Добавить фото'");
    Upload photoUpload = new Upload(By.xpath("//label[contains(.,'Добавить фото')]//input"), "Инпут для загрузки файла");
    Button sentFeedback = new Button(By.xpath("//button[@data-qa='review_modal_main_display_send_feedback_button']"), "Кнопка 'Отправить отзыв'");
}
