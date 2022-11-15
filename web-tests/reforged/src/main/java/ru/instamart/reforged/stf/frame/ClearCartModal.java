package ru.instamart.reforged.stf.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public class ClearCartModal implements Close {

    private final Element modal = new Element(By.xpath("//*[@id=\"__next\"]/div[3]/div[2]/div/div/div"), "модальное окно удаления всех товаров");
    private final Button confirm = new Button(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_confirm_btn']"), "кнопка Удалить товары");
    private final Button cancel = new Button(By.xpath("//button[@data-qa='confirm_shipments_remove_modal_cancel_btn']"), "кнопка Не удалять");

    @Step("Подтвердить очистку корзины")
    public void confirm() {
        confirm.click();
    }

    @Step("Отменить очистку корзины")
    public void cancel() {
        cancel.click();
    }

    @Step("Модальное окно открыто")
    public void checkModalIsOpen() {
        modal.should().visible();
        Assert.assertTrue(modal.is().animationFinished());
    }
}
