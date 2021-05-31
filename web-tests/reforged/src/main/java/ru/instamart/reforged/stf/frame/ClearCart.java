package ru.instamart.reforged.stf.frame;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.stf.component.Button;

public class ClearCart implements Close {

    private final Button confirm = new Button(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_confirm_btn']"));
    private final Button cancel = new Button(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_cancel_btn']"));

    @Step("Подтвердить очистку корзины")
    public void confirm(){
        confirm.click();
    }

    @Step("Подтвердить очистку корзины")
    public void cancel(){
        cancel.click();
    }
}
