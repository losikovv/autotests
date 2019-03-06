package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.models.*;
import ru.instamart.autotests.application.Config;

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
            printMessage("Шаг " + step.getPosition() + " - " + step.getName());
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
            kraken.perform().fillField(Elements.Site.Checkout.phoneNumberField(), contactsDetails.getPhone());
            printMessage("Добавляем номер телефона +7 " + contactsDetails.getPhone());
        } else if(kraken.detect().isPhoneNumberEntered()) {
            printMessage("Используем существующий номер телефона");
        } else {
            kraken.perform().fillField(Elements.Site.Checkout.phoneNumberField(), contactsDetails.getPhone());
            printMessage("Добавляем номер телефона +7 " + contactsDetails.getPhone());
        }
    }

    public void chooseReplacementPolicy(ReplacementPolicyData policy) {
        kraken.perform().click(Elements.Site.Checkout.replacementPolicy(policy.getPosition()));
        printMessage("Выбираем способ осуществления замен #" + policy.getPosition() + " (" + policy.getUserDescription() + ")");
    }

    public void choosePaymentMethod (PaymentDetailsData paymentDetails) {
        kraken.perform().click(Elements.Site.Checkout.paymentTypeSelector(paymentDetails.getPaymentType().getPosition()));
        String description = paymentDetails.getPaymentType().getDescription();
        printMessage("Выбираем способ оплаты " + description);
        if (paymentDetails.isNewCreditCard()) {
            //TODO сделать добавление новой банковской карты
            // TODO addNewPaymentCard - добавить карту оплаты
            // TODO changePaymentCard - изменить карту оплаты
            // TODO deletePaymentCard - удалить карту оплаты
            // TODO deleteAllPaymentCards - удалить все карты оплаты
            // TODO selectPaymentCard - выбрать карту оплаты
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
        printMessage("Переключаемся на " + day + " день");
        kraken.perform().click(Elements.Site.Checkout.deliveryDaySelector(day));
        kraken.perform().waitingFor(1); // Ожидание загрузки слотов дня в чекауте
        if (!kraken.detect().isDeliveryWindowSelectorShown()) {
            printMessage("⚠ Медленно подгружаются слоты доставки\n");
            kraken.perform().waitingFor(2); // Доп. задержка для загрузки слотов в чекауте при тормозах
        }
        printMessage("Выбираем " + slot + " слот ("
                + kraken.grab().text(Elements.Site.Checkout.slotTime(day, slot))
                + " / "
                + kraken.grab().text(Elements.Site.Checkout.slotPrice(day, slot))
                + ")\n");
        kraken.perform().click(Elements.Site.Checkout.chooseSlotButton(day, slot));
        kraken.perform().waitingFor(2); // Ожидание применения слота доставки в чекауте
    }

    /** Добавляем промокод */
    public void addPromocode(String promocode) {
        if (kraken.detect().isPromocodeApplied()) {
            printMessage("Уже есть применённый промокод, поэтому сначала удаляем его... ");
            clearPromocode();
        }
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        if (!kraken.detect().element(Elements.Site.Checkout.PromocodeModal.title())) {
            printMessage("⚠ Не открывается промо-модалка\n");
            kraken.perform().waitingFor(1); // Ожидание открытия промо-модалки в чекауте
        }
        printMessage("Применяем промокод '" + promocode + "'...");
        kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), promocode);
        kraken.perform().click(Elements.Site.Checkout.PromocodeModal.applyButton());
        kraken.perform().waitingFor(1); // Ожидание применения промокода в чекауте
    }

    /** Удаляем промокод */
    public void clearPromocode() {
        if (kraken.detect().isPromocodeApplied()) {
            printMessage("Удаляем промокод...");
            kraken.perform().click(Elements.Site.Checkout.clearPromocodeButton());
            kraken.perform().waitingFor(1); // Ожидание удаления промокода в чекауте
        } else {
            printMessage("Пропускаем удаление промокода, так как он не был применён");
        }
    }

    /** Добавляем бонус */
    public void addBonus(BonusProgramData bonus) {
        if (kraken.detect().isBonusAdded(bonus)) {
            clearBonus(bonus);
        }
        printMessage("Добавляем бонус \"" + bonus.getName() + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + bonus.getPosition() + "]"));
        kraken.perform().fillField(By.name("number"), bonus.getCardNumber() + "\uE007");
        kraken.perform().waitingFor(1); // Ожидание применения бонуса в чекауте
    }

    /** Выбираем бонус в списке добавленных */
    public void selectBonus(BonusProgramData bonus) {
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + bonus.getPosition() + "]"));
        kraken.perform().waitingFor(1); // Ожидание выбора бонусной программы в чекауте
    }

    /** Удаляем бонус */
    public void clearBonus(BonusProgramData bonus) {
        printMessage("Удаляем бонусную программу \"" + bonus.getName() + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + bonus.getPosition() + "]/div[2]"));
        kraken.perform().fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        kraken.perform().waitingFor(1); // Ожидание удаления программы лояльности в чекауте
    }

    /** Добавляем программу лояльности */
    public void addLoyalty(LoyaltyProgramData loyalty) {
        if (kraken.detect().isLoyaltyAdded(loyalty)) {
            clearLoyalty();
        }
        printMessage("Добавляем программу лояльности \"" + loyalty.getName() + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[4]/div[3]/div")); // TODO вынести в Elements
        kraken.perform().fillField(By.name("number"), loyalty.getCardNumber() + "\uE007");
        kraken.perform().waitingFor(1); // Ожидание применения программы лояльности ритейлера в чекауте
    }

    /** Удаляем программу лояльности */
    public void clearLoyalty() {
        printMessage("Удаляем программу лояльности");
        kraken.perform().click(By.xpath("//aside/div/div[4]/div[3]/div")); // TODO вынести в Elements
        kraken.perform().fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        kraken.perform().waitingFor(1); // Ожидание удаления программы лояльности ритейлера в чекауте
    }

    /** Проверяем готовность чекаута перед заполнением */
    private void initCheckout() {
        if (!kraken.detect().isOnCheckout()) {
            printMessage("⚠ Проблемы с производительностью: медленно грузится чекаут\n");
            kraken.perform().waitingFor(1); // Ожидание загрузки чекаута
        }
        printMessage("✓ Чекаут\n");
    }

    /** Проверяем готовность шага чекаута перед заполнением */
    private boolean initStep(int stepNumber, String stepName) {
        if (stepNumber != 5) { // костыль на случай если слот доставки остался выбраным в предыдущих тестах
            if (kraken.detect().isCheckoutStepActive(stepNumber)) {
                //printMessage("Шаг " + stepNumber + " - " + stepName);
                return true;
            } else {
                kraken.perform().waitingFor(1); // Задержка для стабильности, если шаг не развернулся из-за тормозов
                if (!kraken.detect().isCheckoutStepActive(stepNumber)) {
                    printMessage("Не открывается шаг " + stepNumber);
                    return false;
                } else return true;
            }
        } else {
            if (kraken.detect().isElementDisplayed(By.className("windows-selector-panel"))) {
                printMessage("Шаг " + stepNumber + " - " + stepName);
                return true;
            } else {
                printMessage("Шаг " + stepNumber + " - " + stepName);
                printMessage("Слот доставки уже выбран\n");
                return false;
            }
        }
    }

    /** Заполняем текстовые детали заказа в шагах чекаута */
    private void specifyDetail(String field, String value) {
        kraken.perform().fillField(By.name(field), value);
        printMessage("- " + field + ": " + value);
    }

    /** Заполняем логические детали заказа в шагах чекаута */
    private void specifyDetail(String field, boolean value) {
        if (value) {
            if (!kraken.detect().isCheckboxSelected(By.name(field))) {
                kraken.perform().click(By.name(field));
            }
            printMessage("- " + field + ": ✓");
        } else {
            if (kraken.detect().isCheckboxSelected(By.name(field))) {
                kraken.perform().click(By.name(field));
            }
            printMessage("- " + field + ": ✕");
        }
    }

    /** Нажимаем кнопки "Продолжить" в шагах чекаута */
    private void hitNextButton(int step) {
        kraken.perform().click(Elements.Site.Checkout.nextButton(step));
        printMessage("Жмем Продолжить\n");
        kraken.perform().waitingFor(1); // Ожидание загрузки следующего шага в чекауте
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
        printMessage("Изменяем шаг " + step + "\n");
        kraken.perform().waitingFor(1); // Ожидание разворачивания шага в чекауте
    }

    /** Нажимаем кнопку отправки заказа */
    public void sendOrder() {
        if (!kraken.detect().isSendButtonActive()) {
            printMessage("⚠ Кнопка отправки заказа не активна, медленно грузится чекаут\n");
            kraken.perform().waitingFor(2); // Ожидание активации кнопки отправки заказ в чекауте
        }
        if (kraken.detect().isSendButtonActive()) {
            kraken.perform().click(Elements.Site.Checkout.sendOrderButton());
            kraken.perform().waitingFor(3); // Ожидание оформления заказа
            printMessage("✓ Заказ отправлен\n");
        } else printMessage("Невозможно офрмить заказ, кнопка отправки не активна\n");
    }

    /** Удалить все номера телефонов */
    private void deletePhoneNumbers() {
        if (kraken.detect().isPhoneNumberEntered()) {
            kraken.perform().click(Elements.Site.Checkout.editPhoneButton());
            kraken.perform().click(Elements.Site.Checkout.deletePhoneButton());
            kraken.perform().printMessage("Удоляем номер телефона " + kraken.grab().text(Elements.Site.Checkout.phoneNumber()));
            kraken.perform().waitingFor(1); // ожидание удаления предыдущего номера телефона
            deletePhoneNumbers();
        }
    }

    /** Добавить новое юр. лицо */
    private void addNewJuridical(JuridicalData juridicalData) {
        kraken.perform().printMessage(
                "Добавляем данные юр. лица " + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn());
        if (kraken.detect().isElementDisplayed(Elements.Site.Checkout.addJuridicalButton())) {
            kraken.perform().click(Elements.Site.Checkout.addJuridicalButton());
            fillJuridicalDetails(juridicalData);
            kraken.perform().click(Elements.Site.Checkout.JuridicalModal.confirmButton());
            kraken.perform().waitingFor(1); // Ожидание добавление нового юрлица
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
        kraken.perform().printMessage(
                "Удаляем данные юр. лица " + kraken.grab().value(Elements.Site.Checkout.JuridicalModal.nameField())
                        + ", ИНН: " + kraken.grab().value(Elements.Site.Checkout.JuridicalModal.innField()));
        kraken.perform().click(Elements.Site.Checkout.JuridicalModal.deleteButton());
        kraken.perform().waitingFor(1); // Ожидание удаления юрлица
    }

    /** Выбрать юр. лицо */
    private void selectJuridical(JuridicalData juridicalData) {
        Elements title = Elements.Site.Checkout.juridicalTitle(juridicalData);
        if (kraken.detect().isElementDisplayed(title)) {
            kraken.perform().printMessage("Выбираем данные юр. лица " + kraken.grab().text(title));
            kraken.perform().click(title);
            kraken.perform().waitingFor(1); // Ожидание применения выбранного юрлица
        } else if (kraken.detect().isJuridicalEntered()) {
            changeJuridical(juridicalData);
        } else {
            addNewJuridical(juridicalData);
        }
    }

    /** Изменить юр. лицо */
    private void changeJuridical(JuridicalData juridicalData) {
        kraken.perform().click(Elements.Site.Checkout.changeJuridicalButton());
        kraken.perform().printMessage(
                "Меняем данные юр. лица\nС : " + kraken.grab().value(Elements.Site.Checkout.JuridicalModal.nameField())
                        + ", ИНН: " + kraken.grab().value(Elements.Site.Checkout.JuridicalModal.innField()) +
                        "\nНА: " + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn());
        fillJuridicalDetails(juridicalData);
        kraken.perform().click(Elements.Site.Checkout.JuridicalModal.confirmButton());
        kraken.perform().waitingFor(1); // Ожидание сохранения изменений юрлица
    }

}
