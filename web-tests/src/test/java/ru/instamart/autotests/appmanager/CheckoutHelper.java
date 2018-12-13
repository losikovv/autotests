package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.*;

public class CheckoutHelper extends HelperBase {

    private ApplicationManager kraken;

    CheckoutHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    public boolean isOnCheckout() {
        return kraken.detect().isElementPresent(Elements.Site.Checkout.header());
    }


    // ======= Order-making methods =======

    public void fillAllFields() {
        checkCheckoutIsReady();
        doStep1();                  // Заполняем адрес и пожелания тестовыми значениями
        doStep2();                  // Используем существующий телефон
        doStep3();                  // Выбираем дефолтный способ замен
        doStep4();                  // Выбираем тестовый способ оплаты наличными
        doStep5();                  // Выбираем стандартный тестовый слот доставки
    }

    /**
     * Complete checkout with the predefined standard test options
     */
    public void complete() {
        checkCheckoutIsReady();
        doStep1();                  // Заполняем адрес и пожелания тестовыми значениями
        doStep2();                  // Используем существующий телефон
        doStep3();                  // Выбираем дефолтный способ замен
        doStep4();                  // Выбираем тестовый способ оплаты наличными
        doStep5();                  // Выбираем стандартный тестовый слот доставки
        sendOrder();                // Жмем "Оформить заказ"
    }

    /**
     * Complete checkout with the predefined standard test options and a given payment type
     * Use only for existing users which have telephone numbers and all payment types
     */
    public void complete(String paymentType) {
        checkCheckoutIsReady();
        doStep1();                  // Заполняем адрес и пожелания тестовыми значениями
        doStep2();                  // Используем существующий телефон
        doStep3();                  // Выбираем дефолтный способ замен
        doStep4(paymentType);       // Выбираем указанный способ оплаты
        doStep5();                  // Выбираем стандартный тестовый слот доставки
        sendOrder();                // Жмем "Оформить заказ"
    }

    /**
     * Complete checkout with the predefined standard test options
     * Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     */
    public void complete(String paymentType, String loyaltyProgram, String promocode) {
        checkCheckoutIsReady();
        doStep1();                  // Заполняем адрес и пожелания тестовыми значениями
        doStep2();                  // Используем существующий телефон
        doStep3();                  // Выбираем дефолтный способ замен
        doStep4(paymentType);       // Выбираем указанный способ оплаты
        doStep5();                  // Выбираем стандартный тестовый слот доставки
        addLoyalty(loyaltyProgram); // Добавляем программу лояльности
        addPromocode(promocode);    // Добавляем промокод
        sendOrder();                // Жмем "Оформить заказ"
    }

    /**
     * Complete checkout with the given options
     * Use only for existing users which have telephone numbers and all payment types usable
     */
    public void complete(String orderInstructions, int replacementPolicy, String paymentType) {
        checkCheckoutIsReady();
        doStep1(orderInstructions); // Заполняем адрес и пожелания указанными значениями
        doStep2();                  // Заполняем контакты
        doStep3(replacementPolicy); // Выбираем указанный способ замен
        doStep4(paymentType);       // Выбираем указанный способ оплаты
        doStep5();                  // Выбираем стандартный тестовый слот доставки
        sendOrder();                // Жмем "Оформить заказ"
    }


    // ======= Step 1 - Shipping address =======

    private void doStep1() {
        final int stepNumber = 1;
        final String stepName = "Address";
        if (checkStep(stepNumber, stepName)) {
            specifyTestAddressDetails();
            fillTestOrderInstructions();
            hitNextButton(stepNumber);
        } // TODO else { для стабильности прерываем оформление, обновляем чекаут и проходим чекаут заново}
    }

    private void doStep1(String orderInstructions) {
        final int stepNumber = 1;
        final String stepName = "Address";
        if (checkStep(stepNumber, stepName)) {
            specifyTestAddressDetails();
            fillOrderInstructions(orderInstructions);
            hitNextButton(stepNumber);
        }
    }

    private void specifyTestAddressDetails() {
        printMessage("Детали:");
        specifyDetail("apartment", "111");
        specifyDetail("floor", "222");
        specifyDetail("elevator", true);
        specifyDetail("entrance", "333");
        specifyDetail("doorPhone", "44 ключ 5555");
    }

    private void specifyAddressDetails(String apartment, String floor, boolean elevator, String entrance, String doorPhone) {
        printMessage("Детали:");
        specifyDetail("apartment", apartment);
        specifyDetail("floor", floor);
        specifyDetail("elevator", elevator);
        specifyDetail("entrance", entrance);
        specifyDetail("doorPhone", doorPhone);
    }

    private void specifyDetail(String field, String value) {
        kraken.perform().fillField(By.name(field), value);
        printMessage("- " + field + ": " + value);
    }

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

    private void fillTestOrderInstructions() {
        String text = "ТЕСТОВЫЙ ЗАКАЗ / НЕ СОБИРАТЬ";
        kraken.perform().fillField(By.name("order[special_instructions]"), text);
        printMessage("- Комментарии по доставке: " + text);
    }

    private void fillOrderInstructions(String orderInstructions) {
        kraken.perform().fillField(By.name("order[special_instructions]"), orderInstructions);
        printMessage("- Комментарии по доставке: " + orderInstructions);
    }


    // ======= Step 2 - Contacts =======

    private void doStep2() {
        doStep2(Config.testUserPhone);
    }

    private void doStep2(String phoneNumber) {
        final int stepNumber = 2;
        final String stepName = "Contacts";
        if (checkStep(stepNumber, stepName)) {
            if(kraken.detect().isPhoneNumberEntered()) {
                printMessage("Используем существующий номер телефона"); //TODO сделать принудительное добавление выбранного номера
            } else {
                kraken.perform().fillField(Elements.Site.Checkout.phoneNumberField(), phoneNumber);
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


    // ======= Политика замен =======

    /**
     * Шаг 4 - выбор способа замен
     */
    private void doStep3() {
        final int stepNumber = 3;
        final String stepName = "Replacement policy";
        if (checkStep(stepNumber, stepName)) {
            selectReplacementPolicy(1);
            hitNextButton(stepNumber);
        }
    }

    private void doStep3(int policyOption) {
        final int stepNumber = 3;
        final String stepName = "Replacement policy";
        if (checkStep(stepNumber, stepName)) {
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


    // ======= Способ оплаты =======

    /**
     * Шаг 4 - выбор способа оплаты
     */
    private void doStep4() {
        final int stepNumber = 4;
        final String stepName = "Payment";
        if (checkStep(stepNumber, stepName)) {
            selectPaymentType("cash");
            hitNextButton(stepNumber);
        }
    }

    private void doStep4(String paymentType) {
        final int stepNumber = 4;
        final String stepName = "Payment";
        if (checkStep(stepNumber, stepName)) {
            selectPaymentType(paymentType);
            hitNextButton(stepNumber);
        }
    }

    /**
     * Выбрать способ оплаты
     */
    private void selectPaymentType(String type) {
        kraken.perform().click(Elements.Site.Checkout.payment(PaymentTypes.getPosition(type)));
        printMessage("Выбираем способ оплаты " + type + " - " + kraken.grab().text(Elements.locator()));
    }

    // TODO addNewPaymentCard - добавить карту оплаты
    // TODO changePaymentCard - изменить карту оплаты
    // TODO deletePaymentCard - удалить карту оплаты
    // TODO deleteAllPaymentCards - удалить все карты оплаты
    // TODO selectPaymentCard - выбрать карту оплаты

    // TODO addNewEntity - добавить юрлицо
    // TODO changeEntity - изменить юрлицо
    // TODO deleteEntity - удалить юрлицо
    // TODO deleteAllEntities - удалить все юрлица
    // TODO selectEntity - выбрать юрлицо


    // ======= Step 5 - Delivery time =======

    /**
     * Шаг 5 - выбор даты и времени доставки
     * Выбираем дефолтную тестовую доставку - первый слот в самый дальний день
     */
    private void doStep5() {
        final int stepNumber = 5;
        final String stepName = "Delivery time";
        if (checkStep(stepNumber, stepName)) {
            selectDeliveryDay("last");
            selectDeliverySlot("first");
        }
    }

    /**
     * Выбираем день доставки из фиксированных вариантов today / tomorrow / last
     */
    private void selectDeliveryDay(String day) {
        switch (day) {
            case "today":
                selectDeliveryDay(1);
                break;
            case "tomorrow":
                selectDeliveryDay(2);
                break;
            case "last":
                selectDeliveryDay(7);
                break;
        }
    }

    /**
     * Выбираем день доставки по порядковому номеру, начиная с текущего
     */
    private void selectDeliveryDay(int dayNumber) {
        if (dayNumber == 1) {
            kraken.perform().click(By.xpath("//div/span[2]"));
        } else {
            kraken.perform().click(By.xpath("//div[" + dayNumber + "]/span[2]"));
        }
        kraken.perform().waitingFor(1);
        printMessage("Выбираем день доставки " + dayNumber);
    }

    /**
     * Выбираем слот доставки из фиксированных вариантов first / last
     */
    private void selectDeliverySlot(String slot) {
        switch (slot) {
            case "first":
                kraken.perform().click(Elements.Site.Checkout.deliverySlot());
                break;
            case "last":
                //TODO
                break;
        }
        kraken.perform().waitingFor(2);
        printMessage("Выбираем слот доставки " + slot + "\n");
    }

    // TODO private void selectDeliverySlot(int slotPosition) {}

    // TODO добавить определение недоступности слотов в выбранный день - isDeliveryAvailable()
    // TODO добавить определение доступности слотов на основе isEnabled() и isDisplayed()
    // TODO changeDeliverySlot - изменить слот доставки
    // TODO isDeliveryWindowAvailable(int deliveryDay, int slotPosition)
    // TODO добавить определение выбранности слота (свернут блок) через try-catch, нажимать Изменить и выбирать заслот заново если требуется


    // ======= Promocodes =======

    /**
     * Добавляем промокод в чекауте
     */
    public void addPromocode(String promocode) {
        if (kraken.detect().isPromocodeApplied()) {
            printMessage("Уже есть применённый промокод, поэтому сначала удаляем его... ");
            clearPromocode();
        }
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        if (kraken.detect().element(Elements.Site.Checkout.PromocodeModal.title())) {
            printMessage("Применяем промокод '" + promocode + "'...");
            kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), promocode);
            kraken.perform().click(Elements.Site.Checkout.PromocodeModal.applyButton());
            kraken.perform().waitingFor(1);
        } else {
            printMessage("Не открывается промо модалка! Пробуем ещё раз...");
            kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
            printMessage("> Применяем промокод '" + promocode + "'");
            kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), promocode);
            kraken.perform().click(Elements.Site.Checkout.PromocodeModal.applyButton());
            kraken.perform().waitingFor(1);
        }
    }

    /**
     * Удаляем промокод в чекауте
     */
    public void clearPromocode() {
        if (kraken.detect().isPromocodeApplied()) {
            printMessage("Удаляем промокод...");
            kraken.perform().click(Elements.Site.Checkout.clearPromocodeButton());
            kraken.perform().waitingFor(1);
        } else {
            printMessage("Пропускаем удаление промокода, так как он не был применён");
        }
    }


    // ======= Loyalty Programs =======

    /**
     * Добавляем программу лояльности к заказу в чекауте
     */
    public void addLoyalty(String name) {
        if (kraken.detect().isLoyaltyApplied(name)) {
            clearLoyalty(name);
        }
        printMessage("Добавляем программу лояльности \"" + name + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + Loyalties.getPosition(name) + "]"));
        kraken.perform().fillField(By.name("number"), Loyalties.getNumber(name) + "\uE007");
        kraken.perform().waitingFor(1);
    }

    /**
     * Выбираем программу лояльности для заказа из добавленных
     */
    public void selectLoyalty(String name) {
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + Loyalties.getPosition(name) + "]"));
    }

    /**
     * Удаляем программу лояльности в чекауте
     */
    public void clearLoyalty(String name) {
        printMessage("Удаляем программу лояльности \"" + name + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + Loyalties.getPosition(name) + "]/div[2]"));
        kraken.perform().fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        kraken.perform().waitingFor(1);
    }


    // ======= Retailer Loyalties =======

    /**
     * Определяем применена ли программа лояльности ритейлера в чекауте
     */
    public boolean isRetailerLoyaltyApplied() {
        return kraken.detect().isElementPresent(By.xpath("//aside/div/div[4]/div[3]/div/div[2]"));
    }

    /**
     * Добавляем программу лояльности ритейлера в чекауте
     */
    public void addRetailerLoyalty(String name) {
        if (isRetailerLoyaltyApplied()) {
            clearRetailerLoyalty();
        }
        printMessage("Добавляем программу лояльности ритейлера \"" + name + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[4]/div[3]/div"));
        kraken.perform().fillField(By.name("number"), Loyalties.getNumber(name) + "\uE007");
        kraken.perform().waitingFor(1);
    }

    /**
     * Удаляем программу лояльности ритейлера в чекауте
     */
    public void clearRetailerLoyalty() {
        printMessage("Удаляем программу лояльности ритейлера");
        kraken.perform().click(By.xpath("//aside/div/div[4]/div[3]/div"));
        kraken.perform().fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        kraken.perform().waitingFor(1);
    }


    // ======= Common =======

    /**
     * Проверяем готовность чекаута перед заполнением
     */
    private void checkCheckoutIsReady() {
        if (isOnCheckout()) {
            printMessage("✓ Чекаут открыт\n");
        } else {
            printMessage("Ожидание открытия чекаута...\n");
            kraken.perform().waitingFor(1);
        }
    }

    /**
     * Нажимаем кнопки "Продолжить" в шагах чекаута
     */
    private void hitNextButton(int step) {
        kraken.perform().click(Elements.Site.Checkout.nextButton(step));
        printMessage("Продолжить\n");
        kraken.perform().waitingFor(1);
    }

    /**
     * Нажимаем кнопку отправки заказа и ждем пока заказ оформится
     */
    public void sendOrder() {
        if (kraken.detect().isSendButtonActive()) {
            kraken.perform().click(Elements.Site.Checkout.sendOrderButton());
            kraken.perform().waitingFor(3);
            printMessage("✓ Заказ отправлен\n");
        } else {
            kraken.perform().waitingFor(3);
            if (kraken.detect().isSendButtonActive()) {
                kraken.perform().click(Elements.Site.Checkout.sendOrderButton());
                kraken.perform().waitingFor(3);
                printMessage("✓ Заказ отправлен\n");
            } else printMessage("Не возможно завершить заказ, кнопка отправки не активна\n");
        }
    }

    /**
     * Проверяем готовность шага чекаута перед заполнением
     */
    private boolean checkStep(int stepNumber, String stepName) {
        if (stepNumber != 5) { // костыль на случай если слот доставки остался выбраным в предыдущих тестах
            if (kraken.detect().isStepActive(stepNumber)) {
                printMessage("Шаг " + stepNumber + " - " + stepName);
                return true;
            } else {
                kraken.perform().waitingFor(1); // Задержка для стабильности, если шаг не развернулся из-за тормозов
                if (!kraken.detect().isStepActive(stepNumber)) {
                    printMessage("Шаг " + stepNumber + " не открыт в данный момент");
                    return false;
                } else return true;
            }
        } else {
            if (kraken.detect().isElementDisplayed(By.className("windows-selector-panel"))) {
                printMessage("Шаг " + stepNumber + " - " + stepName);
                return true;
            } else {
                printMessage("Шаг " + stepNumber + " - " + stepName);
                printMessage("Слот доставки уже был выбран\n");
                return false;
            }
        }
    }
}
