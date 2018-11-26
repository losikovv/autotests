package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;
import ru.instamart.autotests.configuration.PaymentTypes;
import ru.instamart.autotests.testdata.Loyalties;



    // Checkout helper
    // Handles all the process of finalizing and sending the order



public class CheckoutHelper extends HelperBase {

    private ApplicationManager kraken;

    public CheckoutHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }

    public boolean isOnCheckout(){
        return isElementPresent(Elements.Site.Checkout.header());
    }



    // ======= Order-making methods =======

    public void fillCheckout(){
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
    public void completeCheckout(){
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
    public void completeCheckout(String paymentType){
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
    public void completeCheckout(String paymentType, String loyaltyProgram, String promocode){
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
    public void completeCheckout(String orderInstructions, int replacementPolicy, String paymentType){
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
        if(checkStep(stepNumber, stepName)){
            specifyTestAddressDetails();
            fillTestOrderInstructions();
            hitNextButton(stepNumber);
        } // TODO else { для стабильности прерываем оформление, обновляем чекаут и проходим чекаут заново}
    }

    private void doStep1(String orderInstructions) {
        final int stepNumber = 1;
        final String stepName = "Address";
        if(checkStep(stepNumber, stepName)){
            specifyTestAddressDetails();
            fillOrderInstructions(orderInstructions);
            hitNextButton(stepNumber);
        }
    }

    private void specifyTestAddressDetails() {
        printMessage("Details:");
        specifyDetail("apartment", "111");
        specifyDetail("floor", "222");
        specifyDetail("elevator",true);
        specifyDetail("entrance", "333");
        specifyDetail("doorPhone", "44 ключ 5555");
    }

    private void specifyAddressDetails(String apartment, String floor, boolean elevator, String entrance, String doorPhone) {
        printMessage("Details:");
        specifyDetail("apartment", apartment);
        specifyDetail("floor", floor);
        specifyDetail("elevator",elevator);
        specifyDetail("entrance", entrance);
        specifyDetail("doorPhone", doorPhone);
    }

    private void specifyDetail(String field, String value) {
        kraken.perform().fillField(By.name(field), value);
        printMessage("- " + field + ": " + value);
    }

    private void specifyDetail(String field, boolean value) {
        if(value){
            if(!isCheckboxSelected(By.name(field))){
                kraken.perform().click(By.name(field));
            }
            printMessage("- " + field + ": ✓");
        } else {
            if(isCheckboxSelected(By.name(field))){
                kraken.perform().click(By.name(field));
            }
            printMessage("- " + field + ": ✕");
        }
    }

    private void fillTestOrderInstructions() {
        String text = "ТЕСТОВЫЙ ЗАКАЗ / НЕ СОБИРАТЬ";
        kraken.perform().fillField(By.name("order[special_instructions]"),text);
        printMessage("- order instructions: " + text);
    }

    private void fillOrderInstructions(String orderInstructions) {
        kraken.perform().fillField(By.name("order[special_instructions]"),orderInstructions);
        printMessage("- order instructions: " + orderInstructions);
    }



    // ======= Step 2 - Contacts =======

    private void doStep2() {
        final int stepNumber = 2;
        final String stepName = "Contacts";
        if(checkStep(stepNumber, stepName)) {
            printMessage("Using existing phone number");
            hitNextButton(stepNumber);
        }
    }

    //TODO
    private void doStep2(String phoneNumber) {
        final int stepNumber = 2;
        final String stepName = "Contacts";
        if(checkStep(stepNumber, stepName)) {
            //TODO добавить определение наличия телефонов, добавление и удаление телефона
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

    /** Шаг 4 - выбор способа замен */

    private void doStep3() {
        final int stepNumber = 3;
        final String stepName = "Replacement policy";
        if(checkStep(stepNumber, stepName)) {
            selectReplacementPolicy(1);
            hitNextButton(stepNumber);
        }
    }

    private void doStep3(int policyOption) {
        final int stepNumber = 3;
        final String stepName = "Replacement policy";
        if(checkStep(stepNumber, stepName)) {
            selectReplacementPolicy(policyOption);
            hitNextButton(stepNumber);
        }
    }

    /** Выбрать способ замен по позиции в списке опций */
    private void selectReplacementPolicy(int option){
        kraken.perform().click(Elements.Site.Checkout.replacementPolicy(option));
        printMessage("Replacement policy #" + option + " selected (" + fetchText(Elements.getLocator()) + ")");
    }



    // ======= Способ оплаты =======

    /** Шаг 4 - выбор способа оплаты */

    private void doStep4() {
        final int stepNumber = 4;
        final String stepName = "Payment";
        if(checkStep(stepNumber, stepName)) {
            selectPaymentType("cash");
            hitNextButton(stepNumber);
        }
    }

    private void doStep4(String paymentType) {
        final int stepNumber = 4;
        final String stepName = "Payment";
        if(checkStep(stepNumber, stepName)) {
            selectPaymentType(paymentType);
            hitNextButton(stepNumber);
        }
    }

    /** Выбрать способ оплаты */
    private void selectPaymentType(String type){
        kraken.perform().click(Elements.Site.Checkout.payment(PaymentTypes.getPosition(type)));
        printMessage("Paying with " + type + " - " + fetchText(Elements.getLocator()));
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
    private void doStep5(){
        final int stepNumber = 5;
        final String stepName = "Delivery time";
        if(checkStep(stepNumber, stepName)) {
            selectDeliveryDay("last");
            selectDeliverySlot("first");
        }
    }

    /**
     * Выбираем день доставки из фиксированных вариантов today / tomorrow / last
     */
    private void selectDeliveryDay(String day){
        switch(day){
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
    private void selectDeliveryDay(int dayNumber){
        if(dayNumber == 1){
            kraken.perform().click(By.xpath("//div/span[2]"));
        } else {
            kraken.perform().click(By.xpath("//div[" + dayNumber + "]/span[2]"));
        }
        waitFor(1);
        printMessage("Selected delivery on day " + dayNumber);
    }

    /**
     * Выбираем слот доставки из фиксированных вариантов first / last
     */
    private void selectDeliverySlot(String slot){
        switch(slot){
            case "first":
                kraken.perform().click(Elements.Site.Checkout.deliverySlot());
                break;
            case "last":
                //TODO
                break;
        }
        waitFor(2);
        printMessage("Selected " + slot + " available delivery slot\n");
    }

    // TODO private void selectDeliverySlot(int slotPosition) {}

    // TODO добавить определение недоступности слотов в выбранный день - isDeliveryAvailable()
    // TODO добавить определение доступности слотов на основе isEnabled() и isDisplayed()
    // TODO changeDeliverySlot - изменить слот доставки
    // TODO isDeliveryWindowAvailable(int deliveryDay, int slotPosition)
    // TODO добавить определение выбранности слота (свернут блок) через try-catch, нажимать Изменить и выбирать заслот заново если требуется



    // ======= Promocodes =======

    /**
     * Определяем добавлен ли промокод в чекауте
     */
    public boolean isPromocodeApplied() {
        if (isElementDetected(Elements.Site.Checkout.appliedPromocodeAttribute())) {
            printMessage("✓ Promocode applied");
            return true;
        } else {
            printMessage("No promocode applied");
            return false;
        }
    }


    /** Добавляем промокод в чекауте */
    public void addPromocode(String promocode){
        if (isPromocodeApplied()) {
            printMessage("There's some promocode is already applied, so clearing it first... ");
            clearPromocode();
        }
        kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
        if(isElementDetected(Elements.Site.Checkout.PromocodeModal.title())) {
            printMessage("Applying promocode '" + promocode + "'...");
            kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), promocode);
            kraken.perform().click(Elements.Site.Checkout.PromocodeModal.applyButton());
            waitFor(1);
        } else {
            printMessage("Can't open promo modal! Trying again...");
            kraken.perform().click(Elements.Site.Checkout.addPromocodeButton());
            printMessage("> Applying promocode '" + promocode + "'");
            kraken.perform().fillField(Elements.Site.Checkout.PromocodeModal.field(), promocode);
            kraken.perform().click(Elements.Site.Checkout.PromocodeModal.applyButton());
            waitFor(1);
        }
    }


    /** Удаляем промокод в чекауте */
    public void clearPromocode(){
        if(isPromocodeApplied()) {
            printMessage("Clearing promocode...");
            kraken.perform().click(Elements.Site.Checkout.clearPromocodeButton());
            waitFor(1);
        } else {
            printMessage("Skip clearing promocode, it's not applied at the moment");
        }
    }



    // ======= Loyalty Programs =======

    /**
     * Определяем применена ли программа лояльности
     */
    public boolean isLoyaltyApplied(String name) {
        return isElementPresent(By.xpath("//aside/div/div[3]/div[2]/div[" + Loyalties.getPosition(name) + "]/div[2]"));
    }


    /**
     * Добавляем программу лояльности к заказу в чекауте
     */
    public void addLoyalty(String name){
        if(isLoyaltyApplied(name)){
            clearLoyalty(name);
        }
        printMessage("Adding loyalty program \"" + name + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + Loyalties.getPosition(name) + "]"));
        kraken.perform().fillField(By.name("number"), Loyalties.getNumber(name) + "\uE007");
        waitFor(1);
    }


    /**
     * Выбираем программу лояльности для заказа из добавленных
     */
    public void selectLoyalty(String name){
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + Loyalties.getPosition(name) + "]"));
    }


    /**
     * Удаляем программу лояльности в чекауте
     */
    public void clearLoyalty(String name){
        printMessage("Clearing loyalty program \"" + name + "\"");
        kraken.perform().click(By.xpath("//aside/div/div[3]/div[2]/div[" + Loyalties.getPosition(name) + "]/div[2]"));
        kraken.perform().fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        waitFor(1);
    }




    // ======= Retailer Loyalties =======

    /** Определяем доступна ли программа лояльности ритейлера в чекауте */
    public boolean isRetailerLoyaltyAvailable(){
        return isElementDetected(
                "//aside/div/div[4]/div[2]",
                "Карты лояльности магазинов");
    }

    /** Определяем применена ли программа лояльности ритейлера в чекауте */
    public boolean isRetailerLoyaltyApplied(){
        return isElementPresent(By.xpath("//aside/div/div[4]/div[3]/div/div[2]"));
    }

    /**
     * Добавляем программу лояльности ритейлера в чекауте
     */
    public void addRetailerLoyalty(String name){
        if(isRetailerLoyaltyApplied()){
            clearRetailerLoyalty();
        }
        printMessage("Adding retailer loyalty program");
        kraken.perform().click(By.xpath("//aside/div/div[4]/div[3]/div"));
        kraken.perform().fillField(By.name("number"), Loyalties.getNumber(name) + "\uE007");
        waitFor(1);
    }

    /** Удаляем программу лояльности ритейлера в чекауте */
    public void clearRetailerLoyalty(){
        printMessage("Clearing retailer loyalty program");
        kraken.perform().click(By.xpath("//aside/div/div[4]/div[3]/div"));
        kraken.perform().fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        waitFor(1);
    }




    // ======= Common =======

    /** Проверяем готовность чекаута перед заполнением */
    private void checkCheckoutIsReady() {
        if(isOnCheckout()){
            printMessage("✓ Checking-out\n");
        } else {
            printMessage("Waiting for checkout\n");
            waitFor(1);
        }
    }


    /** Нажимаем кнопки "Продолжить" в шагах чекаута */
    private void hitNextButton(int step) {
        kraken.perform().click(Elements.Site.Checkout.nextButton(step));
        printMessage("Next\n");
        waitFor(1);
    }


    /** Нажимаем кнопку отправки заказа и ждем пока заказ оформится */
    public void sendOrder() {
      if (isSendButtonActive()) {
          kraken.perform().click(Elements.Site.Checkout.sendOrderButton());
          waitFor(3);
          printMessage("✓ Order sent\n");
       } else {
          waitFor(3);
           if (isSendButtonActive()) {
               kraken.perform().click(Elements.Site.Checkout.sendOrderButton());
               waitFor(3);
               printMessage("✓ Order sent\n");
           } else printMessage("Can't make order, send button is not active - check manually\n");
        }
    }


    /** Проверяем готовность шага чекаута перед заполнением */
    private boolean checkStep(int stepNumber, String stepName) {
        if (stepNumber != 5) { // костыль если доставки остался выбраным в предыдущих тестах
            if (isStepActive(stepNumber)) {
                printMessage("Step " + stepNumber + " - " + stepName);
                return true;
            } else {
                waitFor(1); // задержка для стабильности, возможно что шаг не развернулся из-за тормозов
                if (!isStepActive(stepNumber)) {
                    printMessage("Step " + stepNumber + " isn't opened at the moment");
                    return false;
                } else return true;
            }
        } else {
            if(isElementDisplayed(By.className("windows-selector-panel"))) {
                printMessage("Step " + stepNumber + " - " + stepName);
                return true;
            } else {
                printMessage("Step " + stepNumber + " - " + stepName);
                printMessage("Delivery slot has already been selected\n");
                return false;
            }
        }
    }


    /** Определяем активен ли шаг чекаута в данный момент, по наличию кнопок "Продолжить" */
    private boolean isStepActive(int step){
        return isElementPresent((By.xpath("(//button[@type='button'])["+step+"]")));
    }


    /** Определяем активна ли кнопка отправки заказа */
    private boolean isSendButtonActive(){
        return isElementEnabled(By.xpath("//aside/div/div[1]/div/button"));
    }

    /** Проверка стоимости доставки */
    public boolean checkDeliveryPrice(int price) {
        int deliveryPrice = round(fetchText(Elements.Site.Checkout.deliveryPrice()));
        if (deliveryPrice == price) {
            printMessage("✓ Delivery price " + deliveryPrice + "р\n");
            return true;
        } else return false;
    }

}
