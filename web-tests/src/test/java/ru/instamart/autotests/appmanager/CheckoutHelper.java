package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.models.*;
import ru.instamart.autotests.application.Config;

public class CheckoutHelper extends HelperBase {

    private ApplicationManager kraken;

    CheckoutHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    public void complete() {
        makeOrder(Config.testOrderDetails());
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
        if(contactsDetails.isSendEmail()) specifyDetail("order[send_emails]", contactsDetails.isSendEmail());

        if(contactsDetails.isNewPhone()) {
            //TODO сделать добавление нового номера телефона
        }
        if(kraken.detect().isPhoneNumberEntered()) {
            printMessage("Используем существующий номер телефона");
        } else {
            kraken.perform().fillField(Elements.Site.Checkout.phoneNumberField(), contactsDetails.getPhone());
            printMessage("Добавляем номер телефона +7" + contactsDetails.getPhone());
        }
    }

    public void chooseReplacementPolicy(ReplacementPolicyData policy) {
        kraken.perform().click(Elements.Site.Checkout.replacementPolicy(policy.getPosition()));
        printMessage("Выбираем способ осуществления замен #" + policy.getPosition() + " (" + policy.getDescription() + ")");
    }

    public void choosePaymentMethod (PaymentDetailsData paymentDetails) {
        kraken.perform().click(Elements.Site.Checkout.paymentTypeSelector(paymentDetails.getPaymentType().getPosition()));
        printMessage("Выбираем способ оплаты " + paymentDetails.getPaymentType().getDescription());
        if(paymentDetails.isNewCreditCard()) {
            //TODO сделать добавление новой банковской карты
            // TODO addNewPaymentCard - добавить карту оплаты
            // TODO changePaymentCard - изменить карту оплаты
            // TODO deletePaymentCard - удалить карту оплаты
            // TODO deleteAllPaymentCards - удалить все карты оплаты
            // TODO selectPaymentCard - выбрать карту оплаты
        }
        if(paymentDetails.isNewJuridical()) {
            //TODO сделать добавление нового юрлица
            // TODO addNewJuridical - добавить юрлицо
            // TODO changeJuridical - изменить юрлицо
            // TODO deleteJuridical - удалить юрлицо
            // TODO deleteAllJuridicals - удалить все юрлица
            // TODO selectJuridical - выбрать юрлицо
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








    // УДОЛИТЬ ВСЕ ЧТО НИЖЕ

    /**
     * Complete checkout with the predefined standard test options and a given payment type
     * Use only for existing users which have telephone numbers and all payment types
     */
    public void complete(String paymentType) {
        initCheckout();
        doStep1();                  // Заполняем адрес и пожелания тестовыми значениями
        doStep2();                  // Используем существующий телефон
        doStep3();                  // Выбираем дефолтный способ замен
        doStep4(paymentType);       // Выбираем указанный способ оплаты
        doStep5();                  // Выбираем стандартный тестовый слот доставки
        sendOrder();                // Жмем "Оформить заказ"
    }

    /**
     * Complete checkout with the given options
     * Use only for existing users which have telephone numbers and all payment types usable
     */
    public void complete(String orderInstructions, int replacementPolicy, String paymentType) {
        initCheckout();
        doStep1(orderInstructions); // Заполняем адрес и пожелания указанными значениями
        doStep2();                  // Заполняем контакты
        doStep3(replacementPolicy); // Выбираем указанный способ замен
        doStep4(paymentType);       // Выбираем указанный способ оплаты
        doStep5();                  // Выбираем стандартный тестовый слот доставки
        sendOrder();                // Жмем "Оформить заказ"
    }


    // ======= Шаг 1 - Адрес доставки =======

    private void doStep1() {
        final int stepNumber = 1;
        final String stepName = "Адрес доставки";
        if (initStep(stepNumber, stepName)) {
            specifyTestAddressDetails();
            specifyOrderInstructions();
            hitNextButton(stepNumber);
        }
    }

    private void doStep1(String orderInstructions) {
        final int stepNumber = 1;
        final String stepName = "Адрес доставки";
        if (initStep(stepNumber, stepName)) {
            specifyTestAddressDetails();
            specifyOrderInstructions(orderInstructions);
            hitNextButton(stepNumber);
        }
    }

    private void specifyTestAddressDetails() {
        specifyAddressDetails("1","22",true, "333","44 ключ 5555");
    }

    private void specifyAddressDetails(String apartment, String floor, boolean elevator, String entrance, String domofonCode) {
        printMessage("Заполняем детали:");
        specifyDetail("apartment", apartment);
        specifyDetail("floor", floor);
        specifyDetail("elevator", elevator);
        specifyDetail("entrance", entrance);
        specifyDetail("doorPhone", domofonCode);
    }

    private void specifyOrderInstructions() {
        specifyOrderInstructions("ТЕСТОВЫЙ ЗАКАЗ / НЕ СОБИРАТЬ");
    }

    private void specifyOrderInstructions(String orderInstructions) {
        kraken.perform().fillField(By.name("order[special_instructions]"), orderInstructions);
        printMessage("- комментарии по доставке: " + orderInstructions);
    }


    // ======= Шаг 2 - Контакты =======

    private void doStep2() {
        doStep2(Config.testUserPhone);
    }

    private void doStep2(String phoneNumber) {
        final int stepNumber = 2;
        final String stepName = "Контакты";
        if (initStep(stepNumber, stepName)) {
            if(kraken.detect().isPhoneNumberEntered()) {
                printMessage("Используем существующий номер телефона");
            } else {
                kraken.perform().fillField(Elements.Site.Checkout.phoneNumberField(), phoneNumber);
                printMessage("Добавляем номер телефона +7" + phoneNumber);
            }
            hitNextButton(stepNumber);
        }
    }

    // TODO specifyContacts - уточнить контакты
    // TODO clearAllContacts - очистить все контакты
    // TODO changeName - изменить имя
    // TODO changeFamilyName - изменить фамилию
    // TODO changeEmail - изменить email
    // TODO addNewTelephoneNumber - добавить телефон
    // TODO changeTelephoneNumber - изменить телефон
    // TODO deleteTelephoneNumber - удалить телефон
    // TODO deleteAllTelephoneNumbers - удалить все телефоны
    // TODO selectTelephoneNumber - выбрать телефон


    // ======= Шаг 3 - Замены =======

    /**
     * Выбрать дефолтный способ замен
     */
    private void doStep3() {
        doStep3(1);
    }

    /**
     * Выбрать указанный способ замен
     */
    private void doStep3(int policyOption) {
        final int stepNumber = 3;
        final String stepName = "Способ замен";
        if (initStep(stepNumber, stepName)) {
            selectReplacementPolicy(policyOption);
            hitNextButton(stepNumber);
        }
    }

    /**
     * Выбрать способ замен по позиции в списке опций
     */
    private void selectReplacementPolicy(int option) {
        kraken.perform().click(Elements.Site.Checkout.replacementPolicy(option));
        printMessage("Выбираем способ осуществления замен #" + option + " (" + kraken.grab().text(Elements.locator()) + ")");
    }


    // ======= Шаг 4 - Оплата =======

    /**
     * Выбрать указанный способ оплаты
     */
    private void doStep4(String paymentType) {
        final int stepNumber = 4;
        final String stepName = "Способ оплаты";
        if (initStep(stepNumber, stepName)) {
            kraken.perform().click(Elements.Site.Checkout.paymentTypeSelector(PaymentTypes.getPosition(paymentType)));
            printMessage("Выбираем способ оплаты " + paymentType + " - " + kraken.grab().text(Elements.locator()));
            hitNextButton(stepNumber);
        }
    }

    /**
     * Шаг 5 - выбор даты и времени доставки
     * Выбираем дефолтную тестовую доставку - первый слот в самый дальний день
     */
    private void doStep5() {
        final int stepNumber = 5;
        final String stepName = "Время доставки";
        if (initStep(stepNumber, stepName)) {
            chooseDeliveryTime(7,1);
        }
    }
}
