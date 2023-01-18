package ru.instamart.reforged.stf.frame.auth.phone_confirm_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface PhoneConfirmModalElement {
    Element modalConfirmPhone = new Element(By.xpath("//form[@data-qa='tel_login_form'][contains(.,'Подтвердите свой номер')]"), 20, "Модальное окно подтверждения номера телефона");
}
