package ru.instamart.reforged.cloud_payments.page.cloud_payments;

import io.qameta.allure.Step;
import ru.instamart.reforged.cloud_payments.page.CloudPaymentsPage;

public class DemoCloudPaymentsPage implements CloudPaymentsPage, DemoCloudPaymentsCheck {

    @Step("Заполнить ответ на вопрос капчи {0}")
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
