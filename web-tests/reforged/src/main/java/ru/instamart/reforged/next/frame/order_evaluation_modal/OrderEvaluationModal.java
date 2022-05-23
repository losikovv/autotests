package ru.instamart.reforged.next.frame.order_evaluation_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.next.frame.Close;

public final class OrderEvaluationModal implements OrderEvaluationModalCheck, Close {

    @Step("Ставим заказу оценку (1-5): {evaluationValue}")
    public void setEvaluationValue(final int evaluationValue) {
        evaluationStars.getElements().get(evaluationValue - 1).click();
    }

    @Step("Кликаем на тег негативной оценки: {tagText}")
    public void clickNegativeTag(final String tagText) {
        negativeTags.clickOnElementWithText(tagText);
    }

    @Step("Вводим к заказу комментарий: '{commentText}'")
    public void addComment(final String commentText) {
        commentTextarea.fill(commentText);
    }

    @Step("Кликаем на 'Добавить фото'")
    public void clickAddPhoto() {
        addPhoto.click();
    }

    @Step("Добавляем к комментарию фото: {filePath}")
    public void attachPhoto(final String filePath) {
        photoUpload.upload(filePath);
    }

    @Step("Нажимаем 'Отправить отзыв'")
    public void clickSend() {
        sentFeedback.click();
    }
}
