package ru.instamart.reforged.stf.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;

public final class ShipmentCancelModal implements Close {

    private final Button accept = new Button(By.xpath("//button[@data-qa='user-shipment-cancel-modal-btn-cancel']"), "кнопка 'Да, отменить'");
    private final Button decline = new Button(By.xpath("//button[@data-qa='user-shipment-cancel-modal-btn-dismiss']"), "кнопка 'Не отменять'");

    @Step("Подтвердить действие")
    public void clickToAccept() {
        accept.click();
    }

    @Step("Отклонить действие")
    public void clickToDecline() {
        decline.click();
    }
}
