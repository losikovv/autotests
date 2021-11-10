package ru.instamart.reforged.stf.frame.disclaimer;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;

public interface DisclaimerElement {

    Button approve = new Button(By.xpath("//button[@data-qa='disclaimer_modal_ok_button']"), "Кнопка принятия на модальном окне дисклеймера");
}
