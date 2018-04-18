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
    public void completeCheckout(int replacementPolicy, String paymentType){
        // TODO добавить проверку на нахождение в чекауте
        printMessage("Checking-out\n");
        // Заполняем адрес
        doStep1();
        // Заполняем контакты
        doStep2();
        // Выбираем способ замен
        doStep3(replacementPolicy);
        // Выбираем способ оплаты
        doStep4(paymentType);
        // Выбираем слот доставки
        doStep5();
        // Жмем Оформить заказ
        hitSendButton();
    }

    /**
     * Complete checkout with predefined standard test options
     * Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     */
    public void completeCheckout(String paymentType, String loyaltyProgram, String promoCode){

    }



    // ======= Step 1 - Shipping address =======

    private void doStep1() {
        final int stepNumber = 1;
        final String text = "- ТЕСТОВЫЙ ЗАКАЗ -";
        printMessage("Step " + stepNumber + " - Address");
        fillField(By.name("apartment"),"111");
        fillField(By.name("floor"),"222");
        click(By.name("elevator"));
        fillField(By.name("entrance"),"333");
        fillField(By.name("order[special_instructions]"),text);
        printMessage("Order is marked as " + text);
        hitNextButton(stepNumber);
    }
    // TODO setShippingAddress - установить адрес доставки



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
        printMessage("Step + " + stepNumber + " - Payment");
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

    public void doStep5() {
        final int stepNumber = 5;
        printMessage("Step + " + stepNumber + " - Delivery time");
        // Захардкожен первый слот доставки в 7 день // TODO параметризовать
        click(By.xpath("//div[7]/span[2]"));
        //waitForIt();
        click(By.xpath("//div[2]/div/div/span[2]"));
        waitForIt();
    }

    // TODO selectDeliveryWindow - выбрать слот доставки
    // TODO changeDeliveryWindowSelection - изменить слот доставки
    // опции дней слотов доставки: int deliveryDay
    // обернуть deliveryDay чтобы возвращал позицию для - today / tomorrow / last day
    // опции слотов доставки: int slotPosition
    // TODO isDeliveryWindowAvailable(int deliveryDay, int slotPosition)



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
        //printMessage("Done step " + step);
        printMessage("Next\n");
        waitForIt();
    }

    private void hitSendButton() {
        click(By.className("checkout-btn--make-order"));
        printMessage("Order sent\n");
        waitForIt();
    }

    // TODO - определять активна ли кнопка отправки заказа
    // public boolean isSendButtonActive(){ return true; }

}
