package ru.instamart.reforged.next.frame.order_evaluation_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface OrderEvaluationModalCheck extends Check, OrderEvaluationModalElement {

    @Step("Проверяем, что появилось окно оценки заказа")
    default void checkOrderEvaluationModalDisplayed() {
        waitAction().shouldBeVisible(evaluationStars);
    }

    @Step("Проверяем, что окно оценки заказа закрылось")
    default void checkOrderEvaluationModalNotDisplayed() {
        waitAction().shouldNotBeVisible(evaluationStars);
    }

    @Step("Проверяем, что появилась кнопка 'Отправить отзыв'")
    default void checkSendFeedbackDisplayed() {
        waitAction().shouldBeVisible(sentFeedback);
    }

    @Step("Проверяем, что в поле 'Ваш комментарий' введено: {expectedText}")
    default void checkCommentText(final String expectedText) {
        Assert.assertEquals(commentTextarea.getText(), expectedText, "Введенный в поле 'Ваш комментарий' текст отличается от ожидаемого");
    }

    @Step("Проверяем наличие добавленного фото")
    default void checkPhotoAdded() {
        waitAction().shouldBeVisible(addedFile);
    }
}
