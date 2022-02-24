package ru.instamart.reforged.cloud_payments.page.cloud_payments;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.page.Page;

public class DemoCloudPaymentsPage implements Page, DemoCloudPaymentsCheck {

    @Step("Заполнить ответ на вопрос капчи")
    public void fillAnswer(final String data) {
        captchaAnswerInput.fill(data);
    }

    @Step("Нажать кнопку 'Подтвердить'")
    public void clickOnConfirmPaymentButton() {
        confirmPaymentButton.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
