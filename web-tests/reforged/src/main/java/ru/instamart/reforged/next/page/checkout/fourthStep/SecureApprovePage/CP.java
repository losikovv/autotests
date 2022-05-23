package ru.instamart.reforged.next.page.checkout.fourthStep.SecureApprovePage;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public class CP {

    private final Input value = new Input(By.xpath("//input[@id='password']"), "empty");
    private final Button submit = new Button(By.xpath("//button[@type='submit']"), "empty");

    @Step("Заполнить проверочное значение на странице подтверждения 3ds(CP)")
    public void fillValue(String data) {
        value.fill(data);
    }

    @Step("Заполнить проверочное значение на странице подтверждения 3ds(CP)")
    public void clickToSubmit(String data) {
        submit.click();
    }
}
