package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.models.*;
import ru.instamart.autotests.application.Config;

import java.util.concurrent.TimeUnit;

import static ru.instamart.autotests.application.Config.basicTimeout;
import static ru.instamart.autotests.application.Config.waitingTimeout;

public class CheckoutHelper extends HelperBase {

    CheckoutHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    public void complete() {
        makeOrder(Config.testOrderDetails());
    }

    public void complete(PaymentTypeData payment) {
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment);
        makeOrder(details);
    }

    public void complete(ReplacementPolicyData policy) {
        OrderDetailsData details = new OrderDetailsData();
        details.setReplacementPolicy(policy);
        makeOrder(details);
    }

    public void complete(boolean newPhone, String phone) {
        OrderDetailsData details = new OrderDetailsData();
        details.setContactsDetails(new ContactsDetailsData(newPhone, phone));
        makeOrder(details);
    }

    public void complete(PaymentTypeData payment, boolean newPaymentCard, CreditCardData card) {
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment, newPaymentCard, card);
        makeOrder(details);
    }

    public void complete(PaymentTypeData payment, boolean newJuridical, JuridicalData juridical) {
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment, newJuridical, juridical);
        makeOrder(details);
    }

    public void makeOrder(OrderDetailsData orderDetails) {
        fillOrderDetails(orderDetails);
        if(orderDetails.getPromocode() != null) {addPromocode(orderDetails.getPromocode());}
        if(orderDetails.getBonus() != null) {addBonus(orderDetails.getBonus());}
        if(orderDetails.getLoyalty() != null) { addLoyalty(orderDetails.getLoyalty());}
        sendOrder();
        if(orderDetails.getPaymentDetails().getPaymentType().getDescription().equalsIgnoreCase(PaymentTypes.cardOnline().getDescription())) {
            cloudpaymentsFlow();
        }
    }

    public void fillOrderDetails() {
        fillOrderDetails(Config.testOrderDetails());
    }

    public void fillOrderDetails(OrderDetailsData orderDetails) {
        initCheckout();
        for (int position = 1; position <= 5; position++) {
            fillStep(position,orderDetails);
        }
    }

    private void fillStep(int position, OrderDetailsData orderDetails) {
        CheckoutStepData step = CheckoutSteps.getStepDetails(position);
        assert step != null;
        if (initStep(step.getPosition(), step.getDescription())) {
            message("Шаг " + step.getPosition() + " - " + step.getName());
        } else {
            hitChangeButton(step.getPosition());
        }
        switch (step.getName()) {
            case "Адрес" :
                fillAddressDetails(orderDetails.getAddressDetails());
                hitNextButton(step.getPosition());
                break;
            case "Контакты" :
                fillContactsDetails(orderDetails.getContactsDetails());
                hitNextButton(step.getPosition());
                break;
            case "Замены" :
                chooseReplacementPolicy(orderDetails.getReplacementPolicy());
                hitNextButton(step.getPosition());
                break;
            case "Оплата" :
                choosePaymentMethod(orderDetails.getPaymentDetails());
                hitNextButton(step.getPosition());
                break;
            case "Доставка" :
                chooseDeliveryTime(orderDetails.getDeliveryTime());
                break;
        }
    }

    public void fillAddressDetails(AddressDetailsData addressDetails) {
            specifyDetail("apartment", addressDetails.getApartment());
            specifyDetail("floor", addressDetails.getFloor());
            specifyDetail("elevator", addressDetails.isElevatorAvailable());
            specifyDetail("entrance", addressDetails.getEntrance());
            specifyDetail("doorPhone", addressDetails.getDomofon());
            specifyDetail("order[special_instructions]", addressDetails.getComments());
    }

    public void fillContactsDetails(ContactsDetailsData contactsDetails) {
        if(contactsDetails.getName() != null) specifyDetail("order[ship_address_attributes][firstname]", contactsDetails.getName());
        if(contactsDetails.getSurname() != null) specifyDetail("order[ship_address_attributes][lastname]", contactsDetails.getSurname());
        if(contactsDetails.getEmail() != null) specifyDetail("order[email]", contactsDetails.getEmail());
        // TODO сделать проставление галки согласия на коммерческие коммуникации
        //if(contactsDetails.isSendEmail()) specifyDetail("order[send_emails]", contactsDetails.isSendEmail()); // TODO переделать

        if(contactsDetails.isNewPhone()) {
            deletePhoneNumbers();
            message("Добавляем номер телефона +7 " + contactsDetails.getPhone());
            kraken.perform().fillField(Elements.Site.Checkout.phoneNumberField(), contactsDetails.getPhone());
        } else if(kraken.detect().isPhoneNumberEmpty()) {
            message("Добавляем номер телефона +7 " + contactsDetails.getPhone());
            kraken.perform().fillField(Elements.Site.Checkout.phoneNumberField(), contactsDetails.getPhone());
            } else {
            message("Используем существующий номер телефона");
        }
    }

    public void chooseReplacementPolicy(ReplacementPolicyData policy) {
        kraken.perform().click(Elements.Site.Checkout.replacementPolicy(policy.getPosition()));
        message("Выбираем способ замен #" + policy.getPosition() + " (" + policy.getUserDescription() + ")");
    }

    public void choosePaymentMethod (PaymentDetailsData paymentDetails) {
        kraken.perform().click(Elements.Site.Checkout.paymentTypeSelector(paymentDetails.getPaymentType().getPosition()));
        String description = paymentDetails.getPaymentType().getDescription();
        message("Выбираем оплату " + description);

        if (description.equalsIgnoreCase(PaymentTypes.cardOnline().getDescription())) {
            if (paymentDetails.isNewCreditCard()) {
                deleteAllExceptOnePaymentCard();
                if (paymentDetails.getCreditCard() != null) {
                    addNewPaymentCard(paymentDetails.getCreditCard());
                } else {
                    addNewPaymentCard(Config.testOrderDetails().getPaymentDetails().getCreditCard());
                }
            } else {
                if (paymentDetails.getCreditCard() != null) {
                    selectPaymentCard(paymentDetails.getCreditCard());
                }
            }
        }

        if (description.equalsIgnoreCase(PaymentTypes.bankTransfer().getDescription())) {
            if (paymentDetails.isNewJuridical()) {
                deleteAllExceptOneJuridical();
                if (paymentDetails.getJuridical() != null) {
                    addNewJuridical(paymentDetails.getJuridical());
                } else {
                    addNewJuridical(Config.testOrderDetails().getPaymentDetails().getJuridical());
                }
            } else {
                if (paymentDetails.getJuridical() != null) {
                selectJuridical(paymentDetails.getJuridical());
                }
            }
        }
    }

    public void chooseDeliveryTime(DeliveryTimeData deliveryTime) {
        chooseDeliveryTime(deliveryTime.getDay(),deliveryTime.getSlot());
    }

    public void chooseDeliveryTime(int day, int slot) {
        message("Переключаемся на " + day + " день");
        kraken.perform().click(Elements.Site.Checkout.deliveryDaySelector(day));
        kraken.await().implicitly(1); // Ожидание загрузки слотов дня в чекауте
        if (kraken.detect().isElementPresent(Elements.Site.Checkout.deliveryWindowsPlaceholder())){
            throw new AssertionError("Нет доступных слотов доставки");
        }
        if(slot != 0) {
        message("Выбираем " + slot + " слот ("
                + kraken.grab().text(Elements.Site.Checkout.slotTime(day, slot))
                + " / "
                + kraken.grab().text(Elements.Site.Checkout.slotPrice(day, slot))
                + ")\n");
        kraken.perform().click(Elements.Site.Checkout.chooseSlotButton(day, slot));
        } else {
            message("Выбираем первый доступный интервал доставки\n");
            kraken.perform().click(Elements.Site.Checkout.chooseSlotButton());
        }
        // TODO заменить на fluent-ожидание исчезновения спиннера + 1 implicity
        kraken.await().implicitly(3); // Ожидание применения слота доставки в чекауте
    }

    /** Добавляем промокод */
    public void addPromocode(String promocode) {
        if (kraken.detect().isPromocodeApplied()) {
            message("Уже есть применённый промокод, поэтому сначала удаляем его... ");
            clearPromocode();
        }
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        if (!kraken.detect().element(Elements.Site.Checkout.PromocodeModal.title())) {
            message("⚠ Не открывается промо-модалка\n");
            kraken.await().implicitly(1); // Ожидание открытия промо-модалки в чекауте
        }
        message("Применяем промокод '" + promocode + "'...");
        kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), promocode);
        kraken.perform().click(Elements.Site.Checkout.PromocodeModal.applyButton());
        kraken.await().implicitly(1); // Ожидание применения промокода в чекауте
    }

    /** Удаляем промокод */
    public void clearPromocode() {
        if (kraken.detect().isPromocodeApplied()) {
            message("Удаляем промокод...");
            kraken.perform().click(Elements.Site.Checkout.clearPromocodeButton());
            kraken.await().implicitly(1); // Ожидание удаления промокода в чекауте
        } else {
            message("Пропускаем удаление промокода, так как он не был применён");
        }
    }

    /** Добавляем бонус */
    public void addBonus(BonusProgramData bonus) {
        if (kraken.detect().isBonusAdded(bonus)) {
            clearBonus(bonus);
        }
        message("Добавляем бонус \"" + bonus.getName() + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + bonus.getPosition() + "]"));
        kraken.perform().fillField(By.name("number"), bonus.getCardNumber() + "\uE007");
        kraken.await().implicitly(1); // Ожидание применения бонуса в чекауте
    }

    /** Выбираем бонус в списке добавленных */
    public void selectBonus(BonusProgramData bonus) {
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + bonus.getPosition() + "]"));
        kraken.await().implicitly(1); // Ожидание выбора бонусной программы в чекауте
    }

    /** Удаляем бонус */
    public void clearBonus(BonusProgramData bonus) {
        message("Удаляем бонусную программу \"" + bonus.getName() + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + bonus.getPosition() + "]/div[2]"));
        kraken.perform().fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        kraken.await().implicitly(1); // Ожидание удаления программы лояльности в чекауте
    }

    /** Добавляем программу лояльности */
    public void addLoyalty(LoyaltyProgramData loyalty) {
        if (kraken.detect().isLoyaltyAdded(loyalty)) {
            clearLoyalty();
        }
        message("Добавляем программу лояльности \"" + loyalty.getName() + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[4]/div[3]/div")); // TODO вынести в Elements
        kraken.perform().fillField(By.name("number"), loyalty.getCardNumber() + "\uE007");
        kraken.await().implicitly(1); // Ожидание применения программы лояльности ритейлера в чекауте
    }

    /** Удаляем программу лояльности */
    public void clearLoyalty() {
        message("Удаляем программу лояльности");
        kraken.perform().click(By.xpath("//aside/div/div[4]/div[3]/div")); // TODO вынести в Elements
        kraken.perform().fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        kraken.await().implicitly(1); // Ожидание удаления программы лояльности ритейлера в чекауте
    }

    /** Проверяем готовность чекаута перед заполнением */
    private void initCheckout() {
        new FluentWait<>(driver)
                .withTimeout(waitingTimeout, TimeUnit.SECONDS)
                .withMessage("Не открывается чекаут")
                .pollingEvery(basicTimeout, TimeUnit.SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(Elements.Site.Checkout.header().getLocator()));
        message("✓ Чекаут\n");
    }

    /** Проверяем готовность шага чекаута перед заполнением */
    private boolean initStep(int stepNumber, String stepName) {
        if (stepNumber != 5) { // костыль на случай если слот доставки остался выбраным в предыдущих тестах
            if (kraken.detect().isCheckoutStepActive(stepNumber)) {
                //message("Шаг " + stepNumber + " - " + stepName);
                return true;
            } else {
                kraken.await().implicitly(1); // Задержка для стабильности, если шаг не развернулся из-за тормозов
                if (!kraken.detect().isCheckoutStepActive(stepNumber)) {
                    message("Не открывается шаг " + stepNumber);
                    return false;
                } else return true;
            }
        } else {
            if (kraken.detect().isElementDisplayed(By.className("windows-selector-panel"))) {
                message("Шаг " + stepNumber + " - " + stepName);
                return true;
            } else {
                message("Шаг " + stepNumber + " - " + stepName);
                message("Слот доставки уже выбран\n");
                return false;
            }
        }
    }

    /** Заполняем текстовые детали заказа в шагах чекаута */
    private void specifyDetail(String field, String value) {
        kraken.perform().fillField(By.name(field), value);
        message("- " + field + ": " + value);
    }

    /** Заполняем логические детали заказа в шагах чекаута */
    private void specifyDetail(String field, boolean value) {
        if (value) {
            if (!kraken.detect().isCheckboxSelected(By.name(field))) {
                kraken.perform().click(By.name(field));
            }
            message("- " + field + ": ✓");
        } else {
            if (kraken.detect().isCheckboxSelected(By.name(field))) {
                kraken.perform().click(By.name(field));
            }
            message("- " + field + ": ✕");
        }
    }

    /** Нажимаем кнопки "Продолжить" в шагах чекаута */
    private void hitNextButton(int step) {
        kraken.perform().click(Elements.Site.Checkout.nextButton(step));
        message("Жмем Продолжить\n");
        kraken.await().implicitly(1); // Ожидание загрузки следующего шага в чекауте
    }

    /** Нажимаем кнопки "Изменить" в шагах чекаута */
    private void hitChangeButton(int step) {
        switch (step) {
            case 1 :
                kraken.perform().click(Elements.Site.Checkout.changeStepButton(step));
                break;
            case 2 :
                kraken.perform().click(Elements.Site.Checkout.changeStepButton(step));
                break;
            case 3 :
                kraken.perform().click(Elements.Site.Checkout.changeStepButton(step));
                break;
            case 4 :
                kraken.perform().click(Elements.Site.Checkout.changeStepButton(step));
                break;
            case 5 :
                kraken.perform().click(Elements.Site.Checkout.changeStep5Button());
                break;
        }
        message("Изменяем шаг " + step + "\n");
        kraken.await().implicitly(1); // Ожидание разворачивания шага в чекауте
    }

    /** Нажимаем кнопку отправки заказа */
    public void sendOrder() {
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Site.Checkout.sendOrderButton().getLocator()),
                "\nНеактивна кнопка отправки заказа\n");
        kraken.perform().click(Elements.Site.Checkout.sendOrderButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.Site.UserProfile.OrderDetailsPage.activeOrderAttribute().getLocator()),
                "\nНе отправляется заказ\n");
        message("✓ Заказ оформлен\n");
    }

    /** Удалить все номера телефонов */
    private void deletePhoneNumbers() {
        if (kraken.detect().isPhoneNumberEmpty()) {
            kraken.perform().click(Elements.Site.Checkout.editPhoneButton());
            kraken.perform().click(Elements.Site.Checkout.deletePhoneButton());
            message("Удоляем номер телефона " + kraken.grab().text(Elements.Site.Checkout.phoneNumber()));
            kraken.await().implicitly(1); // ожидание удаления предыдущего номера телефона
            deletePhoneNumbers();
        }
    }

    /** Добавить новую карту оплаты */
    private void addNewPaymentCard(CreditCardData creditCardData) {
        message("Добавляем карту оплаты " + creditCardData.getCardNumber());

        if (kraken.detect().isElementDisplayed(Elements.Site.Checkout.addPaymentCardButton())) {
            kraken.perform().click(Elements.Site.Checkout.addPaymentCardButton());
            fillPaymentCardDetails(creditCardData);
            kraken.perform().click(Elements.Site.Checkout.PaymentCardModal.confirmButton());
            kraken.await().implicitly(1); // Ожидание добавления новой карты оплаты

            kraken.perform().click(Elements.Site.Checkout.paymentCardTitle(
                    kraken.grab().listSize(Elements.Site.Checkout.paymentCardsList())));
            kraken.await().implicitly(1); // Ожидание применения новой карты оплаты
        } else {
            fillPaymentCardDetails(creditCardData);
        }
    }

    /** Заполнить данные карты оплаты */
    private void fillPaymentCardDetails(CreditCardData creditCardData) {
        kraken.perform().fillField(Elements.Site.Checkout.PaymentCardModal.cardNumberField(), creditCardData.getCardNumber());
        kraken.perform().fillField(Elements.Site.Checkout.PaymentCardModal.monthField(), creditCardData.getExpiryMonth());
        kraken.perform().fillField(Elements.Site.Checkout.PaymentCardModal.yearField(), creditCardData.getExpiryYear());
        kraken.perform().fillField(Elements.Site.Checkout.PaymentCardModal.cvvField(), creditCardData.getCvvNumber());
        kraken.perform().fillField(Elements.Site.Checkout.PaymentCardModal.nameField(), creditCardData.getCardholderName());
    }

    /** Удалить все карты оплаты, кроме одной */
    private void deleteAllExceptOnePaymentCard() {
        if (kraken.detect().isSecondPaymentCardEntered()) {
            deletePaymentCard();
            deleteAllExceptOnePaymentCard();
        }
    }

    /** Удалить карту оплаты */
    private void deletePaymentCard() {
        kraken.perform().click(Elements.Site.Checkout.changePaymentCardButton());
        message(
                "Удаляем карту оплаты •••• " + kraken.grab().text(Elements.Site.Checkout.PaymentCardModal.cardNumber()));
        kraken.perform().click(Elements.Site.Checkout.PaymentCardModal.deleteButton());
        kraken.await().implicitly(1); // Ожидание удаления карты оплаты
    }

    /** Выбрать карту оплаты */
    private void selectPaymentCard(CreditCardData creditCardData) {
        ElementData title = Elements.Site.Checkout.paymentCardTitle(creditCardData);
        if (kraken.detect().isElementDisplayed(title)) {
            message("Выбираем карту оплаты " + kraken.grab().text(title));
            kraken.perform().click(title);
            kraken.await().implicitly(1); // Ожидание применения выбранной карты оплаты
        } else {
            addNewPaymentCard(creditCardData);
        }
    }

    /** Вести 3ds код на странице cloudpayments */
    private void cloudpaymentsFlow() {
        if (kraken.detect().isElementDisplayed(Elements.Site.Checkout.Cloudpayments.answerField())) {
            kraken.perform().fillField(Elements.Site.Checkout.Cloudpayments.answerField(), "4");
            kraken.perform().click(Elements.Site.Checkout.Cloudpayments.confirmButton());
            kraken.await().implicitly(1); // Ожидание перехода со страницы cloudpayments
        }
    }

    /** Добавить новое юр. лицо */
    private void addNewJuridical(JuridicalData juridicalData) {
        message(
                "Добавляем данные юр. лица " + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn());
        if (kraken.detect().isElementDisplayed(Elements.Site.Checkout.addJuridicalButton())) {
            kraken.perform().click(Elements.Site.Checkout.addJuridicalButton());
            fillJuridicalDetails(juridicalData);
            kraken.perform().click(Elements.Site.Checkout.JuridicalModal.confirmButton());
            kraken.await().implicitly(1); // Ожидание добавления нового юрлица
        } else {
            fillJuridicalDetails(juridicalData);
        }
    }

    /** Заполнить данные юр. лица */
    private void fillJuridicalDetails(JuridicalData juridicalData) {
        kraken.perform().fillField(Elements.Site.Checkout.JuridicalModal.nameField(), juridicalData.getJuridicalName());
        kraken.perform().fillField(Elements.Site.Checkout.JuridicalModal.addressField(), juridicalData.getJuridicalAddress());
        kraken.perform().fillField(Elements.Site.Checkout.JuridicalModal.innField(), juridicalData.getInn());
        kraken.perform().fillField(Elements.Site.Checkout.JuridicalModal.kppField(), juridicalData.getKpp());
        kraken.perform().fillField(Elements.Site.Checkout.JuridicalModal.operatingAccountField(), juridicalData.getAccountNumber());
        kraken.perform().fillField(Elements.Site.Checkout.JuridicalModal.bikField(), juridicalData.getBik());
        kraken.perform().fillField(Elements.Site.Checkout.JuridicalModal.bankField(), juridicalData.getBankName());
        kraken.perform().fillField(Elements.Site.Checkout.JuridicalModal.correspondentAccountField(), juridicalData.getCorrespondentAccountNumber());
    }

    /** Удалить всех юр. лиц, кроме одного */
    private void deleteAllExceptOneJuridical() {
        if (kraken.detect().isSecondJuridicalEntered()) {
            deleteJuridical();
            deleteAllExceptOneJuridical();
        }
    }

    /** Удалить юр. лицо */
    private void deleteJuridical() {
        kraken.perform().click(Elements.Site.Checkout.changeJuridicalButton());
        message(
                "Удаляем данные юр. лица " + kraken.grab().value(Elements.Site.Checkout.JuridicalModal.nameField())
                        + ", ИНН: " + kraken.grab().value(Elements.Site.Checkout.JuridicalModal.innField()));
        kraken.perform().click(Elements.Site.Checkout.JuridicalModal.deleteButton());
        kraken.await().implicitly(1); // Ожидание удаления юрлица
    }

    /** Выбрать юр. лицо */
    private void selectJuridical(JuridicalData juridicalData) {
        ElementData title = Elements.Site.Checkout.juridicalTitle(juridicalData);
        if (kraken.detect().isElementDisplayed(title)) {
            message("Выбираем данные юр. лица " + kraken.grab().text(title));
            kraken.perform().click(title);
            kraken.await().implicitly(1); // Ожидание применения выбранного юрлица
        } else if (kraken.detect().isJuridicalEntered()) {
            changeJuridical(juridicalData);
        } else {
            addNewJuridical(juridicalData);
        }
    }

    /** Изменить юр. лицо */
    private void changeJuridical(JuridicalData juridicalData) {
        kraken.perform().click(Elements.Site.Checkout.changeJuridicalButton());
        message("Меняем данные юр. лица\nС : " + kraken.grab().value(Elements.Site.Checkout.JuridicalModal.nameField())
                + ", ИНН: " + kraken.grab().value(Elements.Site.Checkout.JuridicalModal.innField())
                + "\nНА: " + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn());
        fillJuridicalDetails(juridicalData);
        kraken.perform().click(Elements.Site.Checkout.JuridicalModal.confirmButton());
        kraken.await().implicitly(1); // Ожидание сохранения изменений юрлица
    }
}
