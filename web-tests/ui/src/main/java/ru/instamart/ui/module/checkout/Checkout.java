package ru.instamart.ui.module.checkout;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.kraken.testdata.JuridicalData;
import ru.instamart.kraken.testdata.pagesdata.*;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.ui.Elements;
import ru.instamart.ui.config.WaitProperties;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.module.shop.Order;

import static ru.instamart.kraken.testdata.TestVariables.testOrderDetails;

@Slf4j
public final class Checkout extends Base {

    public Checkout(final AppManager kraken) {
        super(kraken);
    }

    @Step("Нажимаем кнопку Продолжить")
    public static void hitNext(CheckoutStepData step){
        log.info("> жмем 'Продолжить' в шаге '{}'", step.getName());
        kraken.perform().click(Elements.Checkout.Step.nextButton(step));
        ThreadUtil.simplyAwait(1); // Ожидание сохранения данных в шаге чекаута после нажатия "Продолжить"
    }

    @Step("Нажимаем кнопку Изменить")
    public static void hitChange(CheckoutStepData step){
        log.info("> жмем 'Изменить' в шаге '{}'", step.getName());
        kraken.perform().click(Elements.Checkout.MinimizedStep.changeButton(step));
        ThreadUtil.simplyAwait(1); // Ожидание разворота шага чекаута после нажатия "Изменить"
    }

    @Step("Нажимаем кнопку Сохранить")
    public static void hitSave(CheckoutStepData step){
        log.info("> жмем 'Сохранить' в шаге '{}'", step.getName());
        kraken.perform().click(Elements.Checkout.Step.saveButton(step));
        ThreadUtil.simplyAwait(1); // Ожидание сохранения данных в шаге чекаута после нажатия "Сохранить"
    }


    public void complete() {
        Order.makeOrder(testOrderDetails());
    }

    public void complete(PaymentTypeData payment) {
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment);
        Order.makeOrder(details);
    }

    @Step("Оформление заказа с различной политикой замена/удаление")
    public void complete(ReplacementPolicyData policy) {
        OrderDetailsData details = new OrderDetailsData();
        details.setReplacementPolicy(policy);
        Order.makeOrder(details);
    }

    public void complete(boolean newPhone, String phone) {
        OrderDetailsData details = new OrderDetailsData();
        details.setContactsDetails(new ContactsDetailsData(newPhone, phone));
        Order.makeOrder(details);
    }

    @Step("Оформление заказа")
    public void complete(PaymentTypeData payment, boolean newPaymentCard, PaymentCardData cardData) {
        log.info("> оформление заказа с методом оплаты картой онлайн");
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment, newPaymentCard, cardData);
        Order.makeOrder(details);
    }

    @Step("Оформление заказа с методом оплаты картой онлайн")
    public void completeWithCreditCard(PaymentTypeData payment, boolean newPaymentCard, PaymentCardData cardData, boolean reorder) {
        log.info("> оформление заказа с методом оплаты картой онлайн");
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment, newPaymentCard, cardData);
        Order.makeOrderWithCreditCard(details, reorder);
    }

    @Step("Оформление заказа с новым юр. лицом")
    public void complete(PaymentTypeData payment, boolean newJuridical, JuridicalData juridical) {
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment, newJuridical, juridical);
        Order.makeOrder(details);
    }

    @Step("Проверяем готовность чекаута перед заполнением")
    public static void initCheckout() {
        log.info("> проверяем готовность чекаута перед заполнением");
        kraken.await().fluently(ExpectedConditions.presenceOfElementLocated(Elements.Checkout.header().getLocator()),
                "Не открывается чекаут", WaitProperties.BASIC_TIMEOUT);
        log.info("✓ Чекаут");
    }
}