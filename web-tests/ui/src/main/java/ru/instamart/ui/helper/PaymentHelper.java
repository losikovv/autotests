package ru.instamart.ui.helper;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.kraken.testdata.PaymentTypes;
import ru.instamart.kraken.testdata.pagesdata.PaymentCardData;
import ru.instamart.kraken.testdata.pagesdata.PaymentDetailsData;
import ru.instamart.ui.Elements;
import ru.instamart.ui.config.WaitProperties;
import ru.instamart.ui.data.ElementData;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Base;

import static io.qameta.allure.Allure.step;
import static ru.instamart.kraken.testdata.TestVariables.testOrderDetails;
import static ru.instamart.ui.helper.JuridicalHelper.addNewJuridical;

@Slf4j
public final class PaymentHelper extends Base {

    public PaymentHelper(final AppManager kraken) {
        super(kraken);
    }

    @Step("Выбор метода оплаты")
    public static void choosePaymentMethod(PaymentDetailsData paymentDetails) {
        String description = paymentDetails.getPaymentType().getDescription();
        if (kraken.detect().isPaymentTypeAvailable(description)){
            log.debug("> выбираем тип оплаты: {}", description);
            kraken.perform().click(Elements.Checkout.paymentTypeSelector(description));
        } else throw new AssertionError("В чекауте недоступна оплата " + description);

        if (description.equalsIgnoreCase(PaymentTypes.cardOnline().getDescription())) {
            if (kraken.detect().isElementDisplayed(Elements.Checkout.paymentCardTitle(paymentDetails.getCreditCard()))){
                kraken.perform().click(Elements.Checkout.paymentCardTitle(paymentDetails.getCreditCard()));
                log.debug("> выбрали существующую карту оплаты: {}",
                        kraken.grab().text(Elements.Checkout.paymentCardTitle(paymentDetails.getCreditCard())));
            }
            else if (paymentDetails.isNewCreditCard()) {
                //deleteAllExceptOnePaymentCard();
                if (paymentDetails.getCreditCard() != null) {
                    addNewPaymentCard(paymentDetails.getCreditCard());
                } else {
                    addNewPaymentCard(testOrderDetails().getPaymentDetails().getCreditCard());
                }
            } else {
                if (paymentDetails.getCreditCard() != null) {
                    selectPaymentCard(paymentDetails.getCreditCard());
                }
            }
        }

        if (description.equalsIgnoreCase(PaymentTypes.bankTransfer().getDescription())) {
            if (paymentDetails.isNewJuridical()) {
                JuridicalHelper.deleteAllExceptOneJuridical();
                if (paymentDetails.getJuridical() != null) {
                    addNewJuridical(paymentDetails.getJuridical());
                } else {
                    addNewJuridical(testOrderDetails().getPaymentDetails().getJuridical());
                }
            } else {
                if (paymentDetails.getJuridical() != null) {
                    JuridicalHelper.selectJuridical(paymentDetails.getJuridical());
                }
            }
        }
    }

    /** Удалить все карты оплаты, кроме одной */
    private static void deleteAllExceptOnePaymentCard() {
        if (kraken.detect().isSecondPaymentCardEntered()) {
            deletePaymentCard();
            deleteAllExceptOnePaymentCard();
        }
    }

    @Step("Удаляем карту оплаты")
    private static void deletePaymentCard() {
        kraken.perform().click(Elements.Checkout.changePaymentCardButton());
        log.debug("> удаляем карту оплаты •••• {}", kraken.grab().text(Elements.Checkout.PaymentCardModal.cardNumber()));
        kraken.perform().click(Elements.Checkout.PaymentCardModal.deleteButton());
//        kraken.await().implicitly(1); // Ожидание удаления карты оплаты
    }

    @Step("Добавление новой карты")
    private static void addNewPaymentCard(PaymentCardData creditCardData) {
        log.debug("> добавляем карту оплаты {}", creditCardData.getCardNumber());
        if (kraken.detect().isElementDisplayed(Elements.Checkout.paymentCardTitle(creditCardData))){
            kraken.perform().click(Elements.Checkout.paymentCardTitle(creditCardData));
        }
        if (kraken.detect().isElementDisplayed(Elements.Checkout.addPaymentCardButton())) {
            kraken.perform().click(Elements.Checkout.addPaymentCardButton());
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Checkout.PaymentCardModal.cardModal().getLocator()),
                    "модальное окно добавления карты не появилось", WaitProperties.BASIC_TIMEOUT);
            fillPaymentCardDetails(creditCardData);
            kraken.perform().click(Elements.Checkout.PaymentCardModal.confirmButton());
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(
                    Elements.Checkout.PaymentCardModal.cardModal().getLocator()),
                    "Не исчезло модальное окно добавления карты", WaitProperties.BASIC_TIMEOUT);
            kraken.perform().click(Elements.Checkout.paymentCardTitle(creditCardData));
//            kraken.await().implicitly(1); // Ожидание применения новой карты оплаты
        } else {
            fillPaymentCardDetails(creditCardData);
        }
    }

    @Step("Заполнение данных карты")
    private static void fillPaymentCardDetails(PaymentCardData creditCardData) {
        log.debug("> заполнение данных карты");
        String cardNumber = creditCardData.getCardNumber();
        String expiryMonth = creditCardData.getExpiryMonth();
        String expiryYear = creditCardData.getExpiryYear();
        String cvvNumber = creditCardData.getCvvNumber();
        String cardHolderName = creditCardData.getCardholderName();

        step("Заполнение номера карты: " + cardNumber,()->
                kraken.perform().fillField(Elements.Checkout.PaymentCardModal.cardNumberField(), cardNumber));
        step("месяц: " + expiryMonth,()->
                kraken.perform().fillField(Elements.Checkout.PaymentCardModal.monthField(), expiryMonth));
        step("год: " + expiryYear, ()->
                kraken.perform().fillField(Elements.Checkout.PaymentCardModal.yearField(),expiryYear ));
        step("ccv: " + cvvNumber, ()->
                kraken.perform().fillField(Elements.Checkout.PaymentCardModal.cvvField(),cvvNumber ));
        step("имя держателя: " + cardHolderName, ()->
                kraken.perform().fillField(Elements.Checkout.PaymentCardModal.nameField(),cardHolderName ));
    }

    @Step("Выбираем карту оплаты")
    private static void selectPaymentCard(PaymentCardData creditCardData) {
        ElementData title = Elements.Checkout.paymentCardTitle(creditCardData);
        if (kraken.detect().isElementDisplayed(title)) {
            log.debug("> выбираем карту оплаты {}", kraken.grab().text(title));
            kraken.perform().click(title);
//            kraken.await().implicitly(1); // Ожидание применения выбранной карты оплаты
        } else {
            addNewPaymentCard(creditCardData);
        }
    }

    //TODO сделать подтверждение оплаты для эквайринга сбера
    @Step("Подтверждение карты 3ds")
    public static void cloudpaymentsFlow() {
        log.debug("> открытие страницы подтверждения карты");
        if (kraken.detect().isElementDisplayed(Elements.Checkout.Cloudpayments.answerField())) {
            kraken.perform().fillField(Elements.Checkout.Cloudpayments.answerField(), "4");
            kraken.perform().click(Elements.Checkout.Cloudpayments.confirmButton());
//            kraken.await().implicitly(1); // Ожидание перехода со страницы cloudpayments
        }
    }
}
