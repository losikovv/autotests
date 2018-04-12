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
     * Complete checkout with predefined standard test options
     * Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     */
    public void completeCheckout(String paymentType){
        // TODO зарефакторить после написания методов чекаута
        printMessage("Making test order");

        //заполняем поле пожеланий к заказу
        fillField(By.name("order[special_instructions]"),"ТЕСТОВЫЙ ЗАКАЗ");
        hitNextButton();
        //proceedNext(1);
        waitForIt();

        //жмем Продолжить
        waitForIt();
        hitNextButton();
        //proceedNext(2);
        waitForIt();

        //жмем Продолжить
        waitForIt();
        hitNextButton();
        //proceedNext(3);
        waitForIt();

        // выбираем указанный способ оплаты
        selectPaymenttype(paymentType);
        waitForIt();
        hitNextButton();
        //proceedNext(4);
        waitForIt();

        //выбираем первый слот доставки в 7 день
        driver.findElement(By.xpath("//div[7]/span[2]")).click();
        waitForIt();
        driver.findElement(By.xpath("//div[2]/div/div/span[2]")).click();
        waitForIt();

        //жмем Оформить заказ
        hitSendButton();
        waitForIt();
        printMessage("Order sent");

    }

    /**
     * Complete checkout with given options
     * Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     */
    public void completeCheckout(String paymentType, String loyaltyProgram, String promoCode){

    }



    // ======= Common =======

    public void hitNextButton() {
        click(By.className("checkout-btn"));
    }

    public void hitNextButton(int step) {
        click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[" + step + "]/div[2]/div/div/div[4]/button"));
    }



    // ======= Shipping address =======

    // TODO setShippingAddress - установить адрес доставки



    // ======= Contacts =======

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



    // ======= Payment =======

    public void selectPaymenttype(String paymentType){
        switch(paymentType){
            case "cash":
                click(By.name("Наличными курьеру"));
                break;
            case "card":
                click(By.name("Оплата банковской картой"));
                break;
            case "bank":
                click(By.name("Банковский перевод"));
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



    // ======= Delivery time =======

    // TODO selectDeliveryWindow - выбрать слот доставки
    //опции дней слотов доставки
    // today / tomorrow / last day
    // TODO changeDeliveryWindowSelection - изменить слот доставки



    // ======= Promocodes =======

    // TODO addPromocode - добавить промо-код к заказу
    // TODO clearPromocode - убрать промо-код из заказа
    // TODO isPromocodeApplied



    // ======= Loyalty Programs =======

    // TODO addLoyaltyProgram(LoyaltyProgramData loyaltyProgram) - добавить программу лояльности к заказу
    // TODO clearLoyaltyProgram - убрать программу лояльности
    // TODO isLoyaltyProgramApplied



    // ======= Send order =======

    public boolean isSendButtonActive(){
        //TODO - определять активна ли кнопка отправки заказа
        return true;
    }

    public void hitSendButton() {
        // кнопка Завершить внизу
        //click(By.xpath("//form/div/div/div[2]/div[2]/div/button"));
        click(By.className("checkout-btn--make-order"));

        // кнопка Завершить вверху
        //click(By.xpath("/html/body/div[2]/div/form/div/aside/div/div[1]/div"));
    }

}
