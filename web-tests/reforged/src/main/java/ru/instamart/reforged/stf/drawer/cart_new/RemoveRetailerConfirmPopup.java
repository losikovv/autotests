package ru.instamart.reforged.stf.drawer.cart_new;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

/**
 * Окно подтверждения удаления всех товаров ритейлера из корзины
 */
public class RemoveRetailerConfirmPopup {

    Element title = new Element(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_cancel_btn']/../../div[1]"));
    Element text = new Element(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_cancel_btn']/../../div[2]"));
    Button buttonConfirm = new Button(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_confirm_btn']"));
    Button buttonCancel = new Button(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_cancel_btn']"));
    Button buttonClose = new Button(By.xpath(".//button[@aria-label='Закрыть']"));//button[@data-qa='confirm_shipments_remove_modal_confirm_btn']/../..//button[@aria-label='Закрыть']

    public String getTitle(){
        return title.getText();
    }

    public String getText(){
        return text.getText();
    }

    @Step("Нажимаем 'Удалить товары'")
    public void clickConfirm(){
        buttonConfirm.click();
    }

    @Step("Нажимаем 'Не удалять'")
    public void clickCancel(){
        buttonConfirm.click();
    }

    @Step("Нажимаем 'Закрыть'")
    public void clickClose(){
        buttonConfirm.click();
    }


}
