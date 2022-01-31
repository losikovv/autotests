package ru.instamart.reforged.admin.page.retailers.add_new_retailer;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Input;

public interface RetailerAddElements {

    Input nameInput = new Input(By.xpath("//input[@data-qa='retailer_create_name']"), "Инпут имени нового ретейлера на странице создания ретейлера");
}
