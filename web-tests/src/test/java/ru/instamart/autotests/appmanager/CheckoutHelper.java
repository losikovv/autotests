package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class CheckoutHelper extends HelperBase {

    public CheckoutHelper(WebDriver driver) {
        super(driver);
    }

    // хелпер чекаута

    //TODO методы

    /** Complete checkout with predefined standard test options
     *  Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     * */
    public void completeCheckout(){
        // TODO зарефакторить после написания методов чекаута
        printMessage("Making test order");
        //заполняем поле пожеланий к заказу
        fillField(By.name("order[special_instructions]"),"ТЕСТОВЫЙ ЗАКАЗ");
        //жмем Продолжить
        driver.findElement(By.xpath("//button[@type='button']")).click();
        //жмем Продолжить
        driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
        //жмем Продолжить
        driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
        //жмем Продолжить
        driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
        //выбираем первый слот доставки в 7 день
        driver.findElement(By.xpath("//div[7]/span[2]")).click();
        driver.findElement(By.xpath("//div[2]/div/div/span[2]")).click();
        //жмем Оформить заказ
        driver.findElement(By.xpath("//form/div/div/div[2]/div[2]/div/button")).click();

        printMessage("Cancelling test order");
        //возврат
        driver.findElement(By.linkText("Все заказы")).click();
        //отмена заказа
        driver.findElement(By.xpath("//div[@id='wrap']/div/div/div/div[2]/div/div/div/div/div/div/div/div[2]/button")).click();
    }

    /** Complete checkout with given options
     *  Use only for existing users which have telephone numbers and all payment types, cards and loyalty programs
     * */
    public void completeCheckout(String paymentType, String loyaltyProgram, String promoCode){
        // TODO
        //String comment - захардкодить коммент ТЕСТОВЫЙ ЗАКАЗ
        //deliveryWindow - захардкодить первое окно в 7 день
    }

    // TODO clickProceed - нажать на кнопку Продолжить (на всех этапах)

    // TODO specifyShippingAddress - уточнить адрес доставки

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

    // TODO selectPaymentOption - выбрать метод оплаты
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

    // TODO selectDeliveryWindow - выбрать слот доставки
        // TODO selectDayAndDeliveryWindow - выбрать день и слот доставки
        // TODO selectTodayDeliveryWindow - выбрать слот доставки на сегодня
        // TODO selectClosestDeliveryWindow - выбрать ближайший слот доставки на сегодня
        // TODO changeDeliveryWindowSelection - изменить слот доставки

    // TODO addPromoCode - добавить промо-код к заказу
    // TODO clearPromoCode - убрать промо-код из заказа

    // TODO addLoyaltyProgram(LoyaltyProgramData loyaltyProgram) - добавить программу лояльности к заказу
    // TODO clearLoyaltyProgram - убрать программу лояльности

    // TODO sendOrder - завершить заказ

}
