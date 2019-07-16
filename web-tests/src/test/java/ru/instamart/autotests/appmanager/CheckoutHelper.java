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

    // TODO РАЗБИТЬ НА ПОДКЛАССЫ КАК В SHOPHELPER

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
        if(orderDetails.getPromocode() != null) {Promocode.add(orderDetails.getPromocode());}
        if(orderDetails.getBonus() != null) {Bonuses.add(orderDetails.getBonus());}
        if(orderDetails.getRetailerCard() != null) { addRetailerCard(orderDetails.getRetailerCard());}
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
            if(addressDetails.isElevatorAvailable()) { kraken.perform().click(Elements.Checkout.elevatorCheckbox()); }
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
            kraken.perform().fillField(Elements.Checkout.phoneNumberField(), contactsDetails.getPhone());
        } else if(kraken.detect().isPhoneNumberEmpty()) {
            message("Добавляем номер телефона +7 " + contactsDetails.getPhone());
            kraken.perform().fillField(Elements.Checkout.phoneNumberField(), contactsDetails.getPhone());
            } else {
            message("Используем существующий номер телефона");
        }
    }

    public void chooseReplacementPolicy(ReplacementPolicyData policy) {
        kraken.perform().click(Elements.Checkout.replacementPolicy(policy.getPosition()));
        message("Выбираем способ замен #" + policy.getPosition() + " (" + policy.getUserDescription() + ")");
    }

    public void choosePaymentMethod (PaymentDetailsData paymentDetails) {
        kraken.perform().click(Elements.Checkout.paymentTypeSelector(paymentDetails.getPaymentType().getPosition()));
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
        kraken.perform().click(Elements.Checkout.deliveryDaySelector(day));
        kraken.await().implicitly(1); // Ожидание загрузки слотов дня в чекауте
        if (kraken.detect().isElementPresent(Elements.Checkout.deliveryWindowsPlaceholder())){
            throw new AssertionError("Нет доступных слотов доставки");
        }
        if(slot != 0) {
        message("Выбираем " + slot + " слот ("
                + kraken.grab().text(Elements.Checkout.slotTime(day, slot))
                + " / "
                + kraken.grab().text(Elements.Checkout.slotPrice(day, slot))
                + ")\n");
        kraken.perform().click(Elements.Checkout.chooseSlotButton(day, slot));
        } else {
            message("Выбираем первый доступный интервал доставки\n");
            kraken.perform().click(Elements.Checkout.chooseSlotButton());
        }
        // TODO заменить на fluent-ожидание исчезновения спиннера + 1 implicity
        kraken.await().implicitly(3); // Ожидание применения слота доставки в чекауте
    }

    /** Промокод */
    public static class Promocode {

        /** Применить промокод по акции */
        public static void add(PromoData promo) {
            verboseMessage("Акция " + promo.getDescription());
            add(promo.getCode());
        }

        /** Применить промокод */
        public static void add(String promocode) {
            if (kraken.detect().isPromocodeApplied()) {
                verboseMessage("Уже есть применённый промокод, поэтому сначала удаляем его... ");
                delete();
            }
            verboseMessage("Применяем промокод '" + promocode + "'...");
            Modal.open();
            Modal.fill(promocode);
            Modal.submit();
            kraken.await().implicitly(1); // Ожидание применения промокода в чекауте
            // TODO добавить fluent-ожидание
        }

        /** Удалить промокод */
        public static void delete() throws AssertionError {
            if (kraken.detect().isPromocodeApplied()) {
                verboseMessage("Удаляем промокод...");
                kraken.perform().click(Elements.Checkout.Promocode.deleteButton());
                kraken.await().implicitly(1); // Ожидание удаления промокода в чекауте
                // TODO добавить fluent-ожидание
            } else {
                throw new AssertionError("Невозможно удалить промокод, так как он не применен");
            }
        }

        /** Операции c модалкой промокода */
        public static class Modal {

            /** Открыть модалку */
            public static void open() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.addButton())) {
                    kraken.perform().click(Elements.Checkout.Promocode.addButton());
                } else {
                    throw new AssertionError("Невозможно открыть модалку ввода промокода, так как в данный момент применен промокод");
                }
            }

            /** Закрыть модалку */
            public static void fill(String promocode) {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                    kraken.perform().fillField(Elements.Checkout.Promocode.Modal.inputField(), promocode);
                } else {
                    throw new AssertionError("Невозможно ввести промокод, так как не открыта модалка промокода");
                }
            }

            /** Засубмитить модалку кнопкой "Добавить код" */
            public static void submit() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                    kraken.perform().click(Elements.Checkout.Promocode.Modal.submitButton());
                } else {
                    throw new AssertionError("Невозможно нажать кнопку \"Отмена\", так как не открыта модалка промокода");
                }
            }

            /** Закрыть модалку кнопкой "Отмена" */
            public static void cancel() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                    kraken.perform().click(Elements.Checkout.Promocode.Modal.cancelButton());
                } else {
                    throw new AssertionError("Невозможно нажать кнопку \"Отмена\", так как не открыта модалка промокода");
                }
            }

            /** Закрыть модалку */
            public static void close() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                    kraken.perform().click(Elements.Checkout.Promocode.Modal.closeButton());
                } else {
                    debugMessage("Пропускаем закрытие модалки промокода, так как она не открыта");
                }
            }
        }
    }

    /** Бонусные программы */
    public static class Bonuses {

        /** Добавление бонусной программы */
        public static void add(LoyaltiesData bonus) {
            verboseMessage("Добавляем бонусную программу " + bonus.getName());
            kraken.perform().click(Elements.Checkout.Bonus.Program.addButton(bonus.getName()));
            kraken.perform().fillField(Elements.Checkout.Bonus.Modal.inputField(), bonus.getCardNumber());
            kraken.perform().click(Elements.Checkout.Bonus.Modal.saveButton());
            kraken.await().implicitly(1); // Ожидание добавления бонусной программы в чекауте
            // TODO добавить fluent-ожидание
        }

        /** Редактирование бонусной программы */
        public static void edit(LoyaltiesData bonus) throws AssertionError {
            if (kraken.detect().isBonusAdded(bonus)) {
                verboseMessage("Редактируем бонусную программу " + bonus.getName());
                kraken.perform().click(Elements.Checkout.Bonus.Program.addButton(bonus.getName()));
                kraken.perform().fillField(Elements.Checkout.Bonus.Modal.inputField(), bonus.getCardNumber());
                kraken.perform().click(Elements.Checkout.Bonus.Modal.saveButton());
                kraken.await().implicitly(1); // Ожидание редактирования бонусной программы в чекауте
                // TODO добавить fluent-ожидание
            } else {
                throw new AssertionError("Невозможно отредактировать бонусную программу " + bonus.getName() + ", так как она не добавлена");
            }
        }

        /** Выбор бонусной программы в списке добавленных */
        public static void select(LoyaltiesData bonus) throws AssertionError {
            if (kraken.detect().isBonusAdded(bonus)) {
                verboseMessage("Выбираем бонусную программу " + bonus.getName());
                kraken.perform().click(Elements.Checkout.Bonus.Program.snippet(bonus.getName()));
                kraken.await().simply(1); // Ожидание выбора бонусной программы в чекауте
            } else {
                throw new AssertionError("Невозможно выбрать бонусную программу " + bonus.getName() + ", так как она не добавлена");
            }
        }

        /** Удаление бонусной программы */
        public static void delete(LoyaltiesData bonus) throws AssertionError {
            if (kraken.detect().isBonusAdded(bonus)) {
                verboseMessage("Удаляем бонусную программу " + bonus.getName());
                kraken.perform().click(Elements.Checkout.Bonus.Program.editButton(bonus.getName()));
                kraken.perform().click(Elements.Checkout.Bonus.Modal.deleteButton());
                kraken.await().implicitly(1); // Ожидание удаления программы лояльности в чекауте
                // TODO добавить fluent-ожидание
            } else {
                throw new AssertionError("Невозможно удалить бонусную программу " + bonus.getName() + ", так как она не добавлена");
            }
        }

        /** Удаление всех добавленных бонусных программ */
        public static void deleteAll() {
            verboseMessage("Удаляем все бонусные программы в чекауте\n");
            if (kraken.detect().isBonusAdded(BonusPrograms.mnogoru())) {
                delete(BonusPrograms.mnogoru());
            }
            if (kraken.detect().isBonusAdded(BonusPrograms.aeroflot())) {
                delete(BonusPrograms.aeroflot());
            }
        }

        /** Операции c модалкой бонусных мрограмм */
        public static class Modal {

            //TODO public void open() {bonus}

            //TODO public void fill() {}

            //TODO public void submit() {}

            //TODO public void cancel() {}

            /** Закрыть модалку */
            public void close() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Bonus.Modal.popup())) {
                    kraken.perform().click(Elements.Checkout.Bonus.Modal.closeButton());
                } else {
                    debugMessage("Пропускаем закрытие бонусной модалки, так как она не открыта");
                }
            }
        }
    }

    // TODO переделать
    /** Добавляем карту ритейлера */
    public void addRetailerCard(LoyaltiesData card) {
        if (kraken.detect().isRetailerCardAdded()) {
            deleteRetailerCard();
        }
        message("Добавляем карту ритейлера \"" + card.getName() + "\"");
        //kraken.perform().click(Elements.Checkout.loyaltyProgramsSelector());
        //kraken.perform().fillField(By.name("number"), card.getCardNumber() + "\uE007");
        kraken.await().implicitly(1); // Ожидание добавления карты ритейлера в чекауте
    }

    //TODO public void editRetailerCard(LoyaltiesData retailerCard) {

    // TODO переделать
    /** Удаляем карту ритейлера */
    public void deleteRetailerCard() {
        message("Удаляем карту ритейлера");
        //kraken.perform().click(Elements.Checkout.loyaltyProgramsEditButton());
        //kraken.perform().click(Elements.Checkout.deleteBonusProgramButton());
        kraken.await().implicitly(1); // Ожидание удаления карты ритейлера в чекауте
    }

    /** Проверяем готовность чекаута перед заполнением */
    private void initCheckout() {
        new FluentWait<>(driver)
                .withTimeout(waitingTimeout, TimeUnit.SECONDS)
                .withMessage("Не открывается чекаут")
                .pollingEvery(basicTimeout, TimeUnit.SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(Elements.Checkout.header().getLocator()));
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
        kraken.perform().click(Elements.Checkout.nextButton(step));
        message("Жмем Продолжить\n");
        kraken.await().implicitly(1); // Ожидание загрузки следующего шага в чекауте
    }

    /** Нажимаем кнопки "Изменить" в шагах чекаута */
    private void hitChangeButton(int step) {
        switch (step) {
            case 1 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 2 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 3 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 4 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 5 :
                kraken.perform().click(Elements.Checkout.changeStep5Button());
                break;
        }
        message("Изменяем шаг " + step + "\n");
        kraken.await().implicitly(1); // Ожидание разворачивания шага в чекауте
    }

    /** Нажимаем кнопку отправки заказа */
    public void sendOrder() {
        verboseMessage("Отправляем заказ ...");
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Checkout.sendOrderButton().getLocator()),
                            "Неактивна кнопка отправки заказа\n");
        kraken.perform().click(Elements.Checkout.sendOrderButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(
                        Elements.UserProfile.OrderDetailsPage.activeOrderAttribute().getLocator()),
                            "Не отправляется заказ\n");
        message("✓ Заказ оформлен\n");
    }

    /** Удалить все номера телефонов */
    private void deletePhoneNumbers() {
        if (kraken.detect().isPhoneNumberEmpty()) {
            kraken.perform().click(Elements.Checkout.editPhoneButton());
            kraken.perform().click(Elements.Checkout.deletePhoneButton());
            message("Удоляем номер телефона " + kraken.grab().text(Elements.Checkout.phoneNumber()));
            kraken.await().implicitly(1); // ожидание удаления предыдущего номера телефона
            deletePhoneNumbers();
        }
    }

    /** Добавить новую карту оплаты */
    private void addNewPaymentCard(CreditCardData creditCardData) {
        message("Добавляем карту оплаты " + creditCardData.getCardNumber());

        if (kraken.detect().isElementDisplayed(Elements.Checkout.addPaymentCardButton())) {
            kraken.perform().click(Elements.Checkout.addPaymentCardButton());
            fillPaymentCardDetails(creditCardData);
            kraken.perform().click(Elements.Checkout.PaymentCardModal.confirmButton());
            kraken.await().implicitly(1); // Ожидание добавления новой карты оплаты

            kraken.perform().click(Elements.Checkout.paymentCardTitle(
                    kraken.grab().listSize(Elements.Checkout.paymentCardsList())));
            kraken.await().implicitly(1); // Ожидание применения новой карты оплаты
        } else {
            fillPaymentCardDetails(creditCardData);
        }
    }

    /** Заполнить данные карты оплаты */
    private void fillPaymentCardDetails(CreditCardData creditCardData) {
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.cardNumberField(), creditCardData.getCardNumber());
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.monthField(), creditCardData.getExpiryMonth());
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.yearField(), creditCardData.getExpiryYear());
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.cvvField(), creditCardData.getCvvNumber());
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.nameField(), creditCardData.getCardholderName());
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
        kraken.perform().click(Elements.Checkout.changePaymentCardButton());
        message(
                "Удаляем карту оплаты •••• " + kraken.grab().text(Elements.Checkout.PaymentCardModal.cardNumber()));
        kraken.perform().click(Elements.Checkout.PaymentCardModal.deleteButton());
        kraken.await().implicitly(1); // Ожидание удаления карты оплаты
    }

    /** Выбрать карту оплаты */
    private void selectPaymentCard(CreditCardData creditCardData) {
        ElementData title = Elements.Checkout.paymentCardTitle(creditCardData);
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
        if (kraken.detect().isElementDisplayed(Elements.Checkout.Cloudpayments.answerField())) {
            kraken.perform().fillField(Elements.Checkout.Cloudpayments.answerField(), "4");
            kraken.perform().click(Elements.Checkout.Cloudpayments.confirmButton());
            kraken.await().implicitly(1); // Ожидание перехода со страницы cloudpayments
        }
    }

    /** Добавить новое юр. лицо */
    private void addNewJuridical(JuridicalData juridicalData) {
        message(
                "Добавляем данные юр. лица " + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn());
        if (kraken.detect().isElementDisplayed(Elements.Checkout.addJuridicalButton())) {
            kraken.perform().click(Elements.Checkout.addJuridicalButton());
            fillJuridicalDetails(juridicalData);
            kraken.perform().click(Elements.Checkout.JuridicalModal.confirmButton());
            kraken.await().implicitly(1); // Ожидание добавления нового юрлица
        } else {
            fillJuridicalDetails(juridicalData);
        }
    }

    /** Заполнить данные юр. лица */
    private void fillJuridicalDetails(JuridicalData juridicalData) {
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.nameField(), juridicalData.getJuridicalName());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.addressField(), juridicalData.getJuridicalAddress());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.innField(), juridicalData.getInn());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.kppField(), juridicalData.getKpp());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.operatingAccountField(), juridicalData.getAccountNumber());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.bikField(), juridicalData.getBik());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.bankField(), juridicalData.getBankName());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.correspondentAccountField(), juridicalData.getCorrespondentAccountNumber());
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
        kraken.perform().click(Elements.Checkout.changeJuridicalButton());
        message(
                "Удаляем данные юр. лица " + kraken.grab().value(Elements.Checkout.JuridicalModal.nameField())
                        + ", ИНН: " + kraken.grab().value(Elements.Checkout.JuridicalModal.innField()));
        kraken.perform().click(Elements.Checkout.JuridicalModal.deleteButton());
        kraken.await().implicitly(1); // Ожидание удаления юрлица
    }

    /** Выбрать юр. лицо */
    private void selectJuridical(JuridicalData juridicalData) {
        ElementData title = Elements.Checkout.juridicalTitle(juridicalData);
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
        kraken.perform().click(Elements.Checkout.changeJuridicalButton());
        message("Меняем данные юр. лица\nС : " + kraken.grab().value(Elements.Checkout.JuridicalModal.nameField())
                + ", ИНН: " + kraken.grab().value(Elements.Checkout.JuridicalModal.innField())
                + "\nНА: " + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn());
        fillJuridicalDetails(juridicalData);
        kraken.perform().click(Elements.Checkout.JuridicalModal.confirmButton());
        kraken.await().implicitly(1); // Ожидание сохранения изменений юрлица
    }
}
