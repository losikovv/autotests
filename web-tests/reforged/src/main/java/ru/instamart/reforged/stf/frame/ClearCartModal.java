package ru.instamart.reforged.stf.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;

public class ClearCartModal implements Close {

    private final Button confirm = new Button(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_confirm_btn']"),"кнопка Удалить товары");
    private final Button cancel = new Button(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_cancel_btn']"), "кнопка Не удалять");

    @Step("Подтвердить очистку корзины")
    public void confirm(){
        confirm.click();
    }

    @Step("Отменить очистку корзины")
    public void cancel(){
        cancel.click();
    }
}
