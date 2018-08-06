package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.LoyaltiesData;


// Checkout helper
    // Handles all the process of finalizing and sending the order



public class CheckoutHelper extends HelperBase {

    public CheckoutHelper(WebDriver driver, EnvironmentData environment) {
        super(driver, environment);
    }

    public boolean isOnCheckout(){
        return isElementPresent(By.className("chekout-header"));
    }



    // ======= Order-making methods =======

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
        hitSendButton();            // Жмем "Оформить заказ"
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
        hitSendButton();            // Жмем "Оформить заказ"
    }

    //TODO
    /**
     * Complete checkout with the predefined standard test options
     * Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     */
    public void completeCheckout(String paymentType, String loyaltyProgram, String promoCode){
        checkCheckoutIsReady();
        doStep1();                  // Заполняем адрес и пожелания тестовыми значениями
        doStep2();                  // Используем существующий телефон
        doStep3();                  // Выбираем дефолтный способ замен
        doStep4(paymentType);       // Выбираем указанный способ оплаты
        doStep5();                  // Выбираем стандартный тестовый слот доставки
        //TODO loyaltyProgram
        //TODO promoCode
        hitSendButton();            // Жмем "Оформить заказ"
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
        hitSendButton();            // Жмем "Оформить заказ"
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
        fillField(By.name(field), value);
        printMessage("- " + field + ": " + value);
    }

    private void specifyDetail(String field, boolean value) {
        if(value){
            if(!isCheckboxSelected(By.name(field))){
                click(By.name(field));
            }
            printMessage("- " + field + ": ✓");
        } else {
            if(isCheckboxSelected(By.name(field))){
                click(By.name(field));
            }
            printMessage("- " + field + ": ✕");
        }
    }

    private void fillTestOrderInstructions() {
        String text = "ТЕСТОВЫЙ ЗАКАЗ / НЕ СОБИРАТЬ";
        fillField(By.name("order[special_instructions]"),text);
        printMessage("- order instructions: " + text);
    }

    private void fillOrderInstructions(String orderInstructions) {
        fillField(By.name("order[special_instructions]"),orderInstructions);
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



    // ======= Step 3 - Replacement policy =======

    /**
     * Шаг 4 - выбор способа замен
     * Выбираем дефолтный тестовый способ - верхний в списке
     */
    private void doStep3() {
        final int stepNumber = 3;
        final String stepName = "Replacement policy";
        if(checkStep(stepNumber, stepName)) {
            selectReplacementPolicy(1);
            hitNextButton(stepNumber);
        }
    }

    /**
     * Шаг 4 - выбор способа замен
     * Выбираем способ
     */
    private void doStep3(int policyOption) {
        final int stepNumber = 3;
        final String stepName = "Replacement policy";
        if(checkStep(stepNumber, stepName)) {
            selectReplacementPolicy(policyOption);
            hitNextButton(stepNumber);
        }
    }

    /**
     * Выбираем способ замен по позиции в списке опций
     */
    private void selectReplacementPolicy(int policyOption){
        click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[3]/div[2]/div/div/div[2]/div["+policyOption+"]/label"));
        printMessage("Replacement policy #" + policyOption + " selected");
    }



    // ======= Payment =======

    /**
     * Шаг 4 - выбор способа оплаты
     * Выбираем дефолтный тестовый способ - наличными
     */
    private void doStep4() {
        final int stepNumber = 4;
        final String stepName = "Payment";
        if(checkStep(stepNumber, stepName)) {
            selectPaymentType("cash");
            hitNextButton(stepNumber);
        }
    }

    /**
     * Шаг 4 - выбор способа оплаты
     * Выбираем способ оплаты из вариантов
     */
    private void doStep4(String paymentType) {
        final int stepNumber = 4;
        final String stepName = "Payment";
        if(checkStep(stepNumber, stepName)) {
            selectPaymentType(paymentType);
            hitNextButton(stepNumber);
        }
    }

    /**
     * Выбираем способ оплаты
     */
    private void selectPaymentType(String paymentType){
        switch(paymentType){
            case "card-online":
                click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[4]/div[2]/div/div/div[1]/div[1]"));
                break;
            case "card-courier":
                click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[4]/div[2]/div/div/div[1]/div[2]"));
                break;
            case "cash":
                click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[4]/div[2]/div/div/div[1]/div[3]"));
                break;
            case "bank":
                click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[4]/div[2]/div/div/div[1]/div[4]"));
                break;
        }
        printMessage("Paying with " + paymentType);
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
            click(By.xpath("//div/span[2]"));
        } else {
            click(By.xpath("//div[" + dayNumber + "]/span[2]"));
        }
        waitForIt(1);
        printMessage("Selected delivery on day " + dayNumber);
    }

    /**
     * Выбираем слот доставки из фиксированных вариантов first / last
     */
    private void selectDeliverySlot(String slot){
        switch(slot){
            case "first":
                click(By.xpath("//div[2]/div/div/span[2]"));
                break;
            case "last":
                //TODO
                break;
        }
        waitForIt(1);
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
        final String APPLIED_TEXT = "Промо-код:";
        final String APPLIED_XPATH = "//aside/div/div[2]/div[3]/div/span/span";
        if (isElementPresent(By.xpath(APPLIED_XPATH)) && getText(By.xpath(APPLIED_XPATH)).equals(APPLIED_TEXT)){
            printMessage("Promocode applied");
            return true;
        } else {
            printMessage("No promocode applied");
            return false;
        }
    }


    /** Добавляем промокод в чекауте */
    public void addPromocode(String promocode){
        if(!isPromocodeApplied()) {
            printMessage("Adding promocode \"" + promocode + "\"...");
            click(By.linkText("Добавить промо-код"));
            fillField(By.id("couponCode"), "unicorn");
            click(By.xpath("//div[2]/button"));
            waitForIt(1);
        } else {
            printMessage("Can't add promocode - it is already applied");
        }
    }


    /** Удаляем промокод в чекауте */
    public void clearPromocode(){
        if(isPromocodeApplied()) {
            printMessage("Clearing promocode...");
            click(By.linkText("Удалить"));
            waitForIt(1);
        } else {
            printMessage("Can't clear promocode - it is not applied at the moment");
        }
    }




    // ======= Loyalty Programs =======

    /**
     * Определяем применена ли программа лояльности
     */
    public boolean isLoyaltyApplied(String name) {
        return isElementPresent(By.xpath("//aside/div/div[3]/div[2]/div[" + LoyaltiesData.getPosition(name) + "]/div[2]"));
    }


    /**
     * Добавляем программу лояльности к заказу в чекауте
     */
    public void addLoyalty(String name){
        if(isLoyaltyApplied(name)){
            clearLoyalty(name);
        }
        printMessage("Adding loyalty program \"" + name + "\"");
        click(By.xpath("//aside/div/div[3]/div[2]/div[" + LoyaltiesData.getPosition(name) + "]"));
        fillField(By.name("number"), LoyaltiesData.getNumber(name) + "\uE007");
        waitForIt(1);
    }


    /**
     * Выбираем программу лояльности для заказа из добавленных
     */
    public void selectLoyalty(String name){
        click(By.xpath("//aside/div/div[3]/div[2]/div[" + LoyaltiesData.getPosition(name) + "]"));
    }


    /**
     * Удаляем программу лояльности в чекауте
     */
    public void clearLoyalty(String name){
        printMessage("Clearing loyalty program \"" + name + "\"");
        click(By.xpath("//aside/div/div[3]/div[2]/div[" + LoyaltiesData.getPosition(name) + "]/div[2]"));
        fillField(By.name("number"), 1 + "\uE004" + "\uE007");
        waitForIt(1);
    }




    // ======= Retailer Loyalties =======

    // TODO методы работы с программами лояльностей ретайлеров (нали чие программы + методы как для обычных лояльностей)

    /** Определяем доступна ли программа лояльности ритейлера в чекауте */
    public boolean isReatailerLoyaltyAvailable(){
        final String TEXT = "Карты лояльности магазинов";
        final String XPATH = "//aside/div/div[4]/div[2]";
        return isElementPresent(By.xpath(XPATH)) && getText(By.xpath(XPATH)).equals(TEXT);
    }




    // ======= Common =======

    /** Проверяем готовность чекаута перед заполнением */
    private void checkCheckoutIsReady() {
        if(isOnCheckout()){
            printMessage("Checking-out\n");
        } else {
            printMessage("Waiting for checkout\n");
            waitForIt(1);
        }
    }


    /** Нажимаем кнопки "Продолжить" в шагах чекаута */
    private void hitNextButton(int step) {
        click(By.xpath("(//button[@type='button'])["+step+"]"));
        printMessage("Next\n");
        waitForIt(1);
    }


    /** Нажимаем кнопку отправки заказа и ждем пока заказ оформится */
    private void hitSendButton() {
        if (isSendButtonActive()) {
            click(By.className("checkout-btn--make-order"));
        } else {
            printMessage("Can't send order - send button is not active at the moment\n");
        }
        waitForIt(3);
        printMessage("Order sent\n");
    }


    /** Проверяем готовность шага чекаута перед заполнением */
    private boolean checkStep(int stepNumber, String stepName) {
        if (stepNumber != 5) { // костыль если доставки остался выбраным в предыдущих тестах
            if (isStepActive(stepNumber)) {
                printMessage("Step " + stepNumber + " - " + stepName);
                return true;
            } else {
                waitForIt(1); // задержка для стабильности, возможно что шаг не развернулся из-за тормозов
                if (!isStepActive(stepNumber)) {
                    printMessage("Step " + stepNumber + " isn't opened at the moment");
                    return false;
                } else return true;
            }
        } else {
            if(isElementPresent(By.className("windows-selector-panel"))) {
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
        return isElementEnabled(By.className("checkout-finalize__button"));
    }

}
