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
        printMessage("Checking-out the test order");

        // Заполняем адрес
        fillField(By.name("apartment"),"111");
        fillField(By.name("floor"),"222");
        click(By.name("elevator"));
        fillField(By.name("entrance"),"333");
        fillField(By.name("order[special_instructions]"),"- ТЕСТОВЫЙ ЗАКАЗ -");
        hitNextButton(1);

        // Заполняем контакты
        hitNextButton(2);
        //TODO добавить определение наличия телефонов добавление и удаление телефона

        // Выбираем способ замен
        selectReplacementPolicy(4);
        hitNextButton(3);

        // Выбираем способ оплаты
        selectPaymentType(paymentType);
        hitNextButton(4);

        // Выбираем слот доставки
        // первый слот доставки в 7 день
        click(By.xpath("//div[7]/span[2]"));
        waitForIt();
        click(By.xpath("//div[2]/div/div/span[2]"));
        waitForIt();

        // Жмем Оформить заказ
        hitSendButton();

    }

    /**
     * Complete checkout with given options
     * Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     */
    public void completeCheckout(String paymentType, String loyaltyProgram, String promoCode){

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


    // ======= Replacement policy =======
    public void selectReplacementPolicy(int option){
        click(By.xpath("/html/body/div[2]/div/form/div/div/div/div[3]/div[2]/div/div/div[2]/div["+option+"]/label"));
        printMessage("Selected replacement policy #" + option);
    }



    // ======= Payment =======

    public void selectPaymentType(String paymentType){
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



    // ======= Delivery time =======

    // TODO selectDeliveryWindow - выбрать слот доставки
    // TODO changeDeliveryWindowSelection - изменить слот доставки
    // опции дней слотов доставки: int deliveryDay
    // обернуть deliveryDay чтобы возвращал позицию для - today / tomorrow / last day
    // опции слотов доставки: int slotPosition
    // TODO isDeliveryWindowAvailable(int deliveryDay, int slotPosition)



    // ======= Promocodes =======

    // TODO addPromocode - добавить промо-код к заказу
    // TODO clearPromocode - убрать промо-код из заказа
    // TODO isPromocodeApplied



    // ======= Loyalty Programs =======

    // TODO addLoyaltyProgram(LoyaltyProgramData loyaltyProgram) - добавить программу лояльности к заказу
    // TODO clearLoyaltyProgram - убрать программу лояльности
    // TODO isLoyaltyProgramApplied



    // ======= Common =======

    public void hitNextButton(int step) {
        if(step == 1){
            click(By.xpath("(//button[@type='button'])"));
        } else {
            click(By.xpath("(//button[@type='button'])["+step+"]"));
        }
        waitForIt();
        printMessage("Step " + step + " is done");
    }

    public void hitSendButton() {
        click(By.className("checkout-btn--make-order"));
        waitForIt();
        printMessage("Order sent");
    }

    // TODO - определять активна ли кнопка отправки заказа
    // public boolean isSendButtonActive(){ return true; }

}
