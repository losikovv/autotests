package ru.instamart.reforged.sber_payments.rbsuat_payments;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public interface RbsuatPaymentsElement {

    Input passwordInput = new Input(By.xpath("//input[@id='password']"), "Поле ввода пароля");
    Button cancel = new Button(By.xpath("//a[contains(.,'Отмена')]"), "Кнопка 'Отмена'");
}
