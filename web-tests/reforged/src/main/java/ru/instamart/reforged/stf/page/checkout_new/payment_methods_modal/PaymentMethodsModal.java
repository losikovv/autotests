package ru.instamart.reforged.stf.page.checkout_new.payment_methods_modal;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public final class PaymentMethodsModal {

    private final Element title = new Element(By.xpath("//h3[contains(@class,'CheckoutModal_title')]"), "Заголовок модального окна");
    private final Button closeButton = new Button(By.xpath("//button[contains(@class,'Modal_closeButton')]"), "Кнопка 'X' (закрыть)");

    private final ElementCollection paymentMethods = new ElementCollection(By.xpath("//main[contains(@class,'Modal_content')]//div[contains(@class,'PaymentMethod_label')]"),
            "Доступные способы оплаты");
    private final Element paymentMethodByName = new Element(ByKraken.xpathExpression("//main[contains(@class,'Modal_content')]//div[contains(@class,'PaymentMethod_label')][contains(.,'%s')]"),
            "Способ оплаты по названию");
    private final Button deletePaymentMethodByName = new Button(ByKraken.xpathExpression("//main[contains(@class,'Modal_content')]//div[contains(@class,'PaymentMethod_label')][contains(.,'%s')]/../../button[contains(@class,'PaymentMethodDeleteButton')]"),
            "Кнопка 'Удалить' способа оплаты по названию");
    private final Button confirm = new Button(By.xpath("//main[contains(@class,'Modal_content')]//button[contains(.,'Выбрать')]"), "Кнопка подтверждения выбора способа оплаты");

    @Step("Проверяем, что модальное окно появилось")
    public void checkModalVisible() {
        Kraken.waitAction().shouldBeVisible(title);
        Assert.assertTrue(title.is().animationFinished());
    }

    @Step("Проверяем, что модальное окно закрылось")
    public void checkModalNotVisible() {
        Assert.assertTrue(title.is().invisible());
    }

    @Step("Проверяем, что способ оплаты '{paymentMethod}' не отображается")
    public void checkPaymentMethodNotVisible(final String paymentMethod){
        Assert.assertTrue(paymentMethodByName.is().invisible(paymentMethod));
    }

    @Step("Закрываем модальное окно выбора способа оплаты")
    public void closeModal() {
        closeButton.click();
    }

    @Step("Выбираем способ оплаты: '{paymentMethod}'")
    public void selectPaymentMethod(final String paymentMethod) {
        paymentMethodByName.click(paymentMethod);
    }

    @Step("Нажимаем 'Удалить' способ оплаты: '{paymentMethod}'")
    public void removePaymentMethod(final String paymentMethod) {
        paymentMethodByName.click(paymentMethod);
        deletePaymentMethodByName.click(paymentMethod);
    }

    @Step("Нажимаем кнопку 'Выбрать'")
    public void clickConfirm() {
        confirm.click();
    }
}
