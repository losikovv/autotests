package ru.instamart.reforged.stf.page.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public class PaymentStep {

    private final Button byCardToCourier = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_lifepay']"));
    private final Button byBusinessAccount = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_sber_bank_invoice']"));
    private final Button byCardOnline = new Button(By.xpath("//button[@data-qa='checkout_form_payment_method_cloud_payments_gateway']"));

    //byBankInvoice
    private final Input inn = new Input(By.xpath("//input[@name='requisites.inn']"));
    private final Input requisitesName = new Input(By.xpath("//input[@name='requisites.name']"));
    private final Input requisitesAddress = new Input(By.xpath("//input[@name='requisites.address']"));
    private final Checkbox needInvoice = new Checkbox(By.xpath("//div[contains(@class, 'checkout-panel--active')]//input[@type='checkbox']"));
    private final Button save = new Button(By.xpath("//div[contains(@class, 'checkout-panel--active')]//div[@class='button-block']//button"));
    private final Button addNewRequisites = new Button(By.xpath("//span[contains(text(),'Добавить реквизиты')]"));
    private final Button change = new Button(By.xpath("//div[contains(@class, 'checkout-panel--active')]//button[contains(text(),'Изменить')]"));//общий локтатор для карты и бизнеса

    private final Button addNewPaymentCard = new Button(By.xpath("//span[contains(text(),'Добавить новую карту')]"));
    private final Button submitFromCheckoutColumn = new Button(By.xpath("//div[@class='checkout-column']//button[@data-qa='checkout_order_button']"));
    //    private final ElementCollection selectPaymentCard = new ElementCollection(By.xpath("//div[contains(@class, 'payment_tools')]//div"));
//    private final ElementCollection changeFirstPaymentCard = new ElementCollection(By.xpath("//div[contains(@class, 'checkout-panel--active')]//button[contains(text(),'Изменить')]"));    private final ElementCollection selectPaymentCard = new ElementCollection(By.xpath("//div[contains(@class, 'payment_tools')]//div"));
    private final ElementCollection selectPaymentCard = new ElementCollection(By.xpath("//div[contains(@class, 'payment_tools')]//div"));
    private final ElementCollection changeFirstPaymentCard = new ElementCollection(By.xpath("//div[contains(@class, 'checkout-panel--active')]//button[contains(text(),'Изменить')]"));
    ////div[@class='loyalty-program__name'][contains(text(), 'Много')]//ancestor::div[@class='loyalty-program__content']//following-sibling::div[@class='loyalty-program__edit']
    //div[contains(@class, 'payment_tools')]/div[contains(text(), '4444')]//ancestor::button//following-sibling::button[text() ='Изменить']    private final Button submitFromCheckoutColumn = new Button(By.xpath("//div[@class='checkout-column']//button[@data-qa='checkout_order_button']"));
    ////div[@class='loyalty-program__edit']/parent::div//div[contains(text(), 'Много')]
    @Step("Выбрать метод оплаты Картой при получении")
    public void clickToByCardToCourier() {
        byCardToCourier.click();
    }

    @Step("Выбрать метод оплаты По счету бизнеса")
    public void clickToByBusinessAccount() {
        byBusinessAccount.click();
    }

    @Step("Выбрать метод оплаты По счеты бизнеса")
    public void clickToByCardOnline() {
        byCardOnline.click();
    }

    //действия с элементами метода оплаты По счету бизнеса
    @Step("Заполнить поле Юридического лицо")
    public void fillRequisitesName(String data) {
        requisitesName.fill(data);
    }

    @Step("Заполнить поле Юридического лицо")
    public void fillRequisitesAddress(String data) {
        requisitesAddress.fill(data);
    }

    @Step("Выбрать чекбокс Необходима накладная ТОРГ-12")
    public void checkNeedInvoice(String data) {
        needInvoice.check();
    }

    @Step("Снять чекбокс Необходима накладная ТОРГ-12")
    public void uncheckNeedInvoice(String data) {
        needInvoice.uncheck();
    }

    @Step("Нажать Сохранить")
    public void clickToSave() {
        save.click();
    }

    @Step("Нажать Добавить реквизиты")
    public void clickToAddRequisites() {
        addNewRequisites.click();
    }

    @Step("Нажать Изменить")
    public void clickToChange() {
        change.click();
    }

    @Step("Нажать добавить новую карту")
    public void clickToAddNewPaymentCard() {
        addNewPaymentCard.click();
    }

    @Step("Нажать Оформить заказ в блоке заполнения чекаута")
    public void clickToSubmitFromCheckoutColumn() {
        submitFromCheckoutColumn.click();
    }

    @Step("Выбрать карту {0}")
    public void clickToPaymentCard(String data){
        selectPaymentCard.clickOnElementWithText(data);
    }

    @Step("Изменить карту {0}")
    public void clickToChangePaymentCard(String data){
        changeFirstPaymentCard.clickOnElementWithText(data);
    }

}
