package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



    // Checkout helper
    // Handles all the process of finalizing and sending the order



public class CheckoutHelper extends HelperBase {

    public CheckoutHelper(WebDriver driver) {
        super(driver);
    }



    // ======= Order-making methods =======

    /**
     * Complete checkout with given options
     * Use only for existing users which have telephone numbers and all payment types usable
     */
    public void completeCheckout(String orderInstructions, int replacementPolicy, String paymentType){
        // TODO добавить проверку на нахождение в чекауте
        printMessage("Checking-out\n");
        doStep1(orderInstructions); // Заполняем адрес и пожелания
        doStep2(); // Заполняем контакты
        doStep3(replacementPolicy); /// Выбираем способ замен
        doStep4(paymentType); // Выбираем способ оплаты
        doStep5(); // Выбираем слот доставки
        hitSendButton(); // Жмем Оформить заказ
    }

    /**
     * Complete checkout with predefined standard test options
     * Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     */
    public void completeCheckout(String paymentType, String loyaltyProgram, String promoCode){

    }



    // ======= Step 1 - Shipping address =======

    private void doStep1(String orderInstructions) {
        final int stepNumber = 1;
        printMessage("Step " + stepNumber + " - Address");
        printMessage("Details:");
        specifyDetail("apartment", "111");
        specifyDetail("floor", "222");
        specifyDetail("entrance", "333");
        specifyDetail("elevator",true);
        fillOrderInstructions(orderInstructions);
        hitNextButton(stepNumber);
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

    private void fillOrderInstructions(String orderInstructions) {
        fillField(By.name("order[special_instructions]"),orderInstructions);
        printMessage("- order instructions: " + orderInstructions);
    }



    // ======= Step 2 - Contacts =======

    private void doStep2() {
        final int stepNumber = 2;
        printMessage("Step " + stepNumber + " - Contacts");
        //TODO добавить определение наличия телефонов добавление и удаление телефона
        hitNextButton(stepNumber);
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

    private void doStep3(int replacementPolicy) {
        final int stepNumber = 3;
        printMessage("Step " + stepNumber + " - Replacement policy");
        selectReplacementPolicy(replacementPolicy);
        hitNextButton(stepNumber);
    }

    private void selectReplacementPolicy(int option){
        click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[3]/div[2]/div/div/div[2]/div["+option+"]/label"));
        printMessage("Replacement policy #" + option + " selected");
    }



    // ======= Payment =======

    private void doStep4(String paymentType) {
        final int stepNumber = 4;
        printMessage("Step " + stepNumber + " - Payment");
        selectPaymentType(paymentType);
        hitNextButton(stepNumber);
    }

    private void selectPaymentType(String paymentType){
        switch(paymentType){
            case "cash":
                //click(By.tagName("Наличными курьеру"));
                click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[4]/div[2]/div/div/div[1]/div[2]"));
                break;
            case "card":
                //click(By.name("Оплата банковской картой"));
                click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[4]/div[2]/div/div/div[1]/div[1]"));
                break;
            case "bank":
                //click(By.name("Банковский перевод"));
                click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[4]/div[2]/div/div/div[1]/div[3]"));
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

    private void doStep5(){
        final int stepNumber = 5;
        printMessage("Step " + stepNumber + " - Delivery time");
        // выбираем первый слот в 7 день
        // selectDeliveryDay("last");
        selectDeliveryDay("last");
        selectDeliverySlot("first");
    }

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

    private void selectDeliveryDay(int dayNumber){
        if(dayNumber == 1){
            click(By.xpath("//div/span[2]"));
        } else {
            click(By.xpath("//div[" + dayNumber + "]/span[2]"));
        }
        waitForIt();
        printMessage("Selected delivery on day " + dayNumber);
    }

    private void selectDeliverySlot(String slot){
        switch(slot){
            case "first":
                click(By.xpath("//div[2]/div/div/span[2]"));
                break;
            case "last":
                //TODO
                break;
        }
        waitForIt();
        printMessage("Selected " + slot + " available delivery slot\n");
    }

    // TODO private void selectDeliverySlot(int slotPosition) {}

    // TODO добавить определение недоступности слотов в выбранный день - isDeliveryAvailable()
    // TODO добавить определение доступности слотов на основе isEnabled() и isDisplayed()
    // TODO changeDeliverySlot - изменить слот доставки
    // TODO isDeliveryWindowAvailable(int deliveryDay, int slotPosition)
    // TODO добавить определение выбранности слота (свернут блок) через try-catch, нажимать Изменить и выбирать заслот заново если требуется



    // ======= Promocodes =======

    // TODO addPromocode(String promocode) - добавить указанный промо-код к заказу, при необходимности дропнув текущий, если он отличается от указанного
    // TODO clearPromocode - убрать промо-код из заказа
    // TODO isPromocodeApplied - проверка добавлен ли промокод к заказу
    // TODO isPromocodeApplied(String promocode) - проверка добавлен ли указанный промокод к заказу



    // ======= Loyalty Programs =======

    // TODO addLoyaltyProgram(LoyaltyProgramData loyaltyProgram) - добавить программу лояльности к заказу
    // TODO clearLoyaltyProgram - убрать программу лояльности
    // TODO isLoyaltyProgramApplied



    // ======= Common =======

    private void hitNextButton(int step) {
        if(step == 1){
            click(By.xpath("(//button[@type='button'])"));
        } else {
            click(By.xpath("(//button[@type='button'])["+step+"]"));
        }
        printMessage("Next\n");
        waitForIt();
    }

    private void hitSendButton() {
        // TODO Нажимать кнопку только если isSendButtonActive() == true
        click(By.className("checkout-btn--make-order"));
        // TODO добавить проверку на наличие спиннера отправки заказа, писать Order sent только если есть спиннер
        printMessage("Order sent\n");
        waitForIt();
    }

    // TODO - определять активна ли кнопка отправки заказа
    // public boolean isSendButtonActive(){ return true; }

}
