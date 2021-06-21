package ru.instamart.reforged.stf.page.checkout.SecureApprovePage;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public class Sber {

    private final Input value = new Input(By.xpath("//input[@id='password']"));
    private final Button cancel = new Button(By.xpath("//a[text() = 'Отмена']"));

    @Step("Заполнить проверочное значение на странице подтверждения 3ds(sber)")
    public void fillValue(String data) {
        value.fill(data);
    }

    @Step("Нажать отменить на странице подтверждения 3ds(sber)")
    public void clickToSubmit(String data) {
        cancel.click();
    }
}
