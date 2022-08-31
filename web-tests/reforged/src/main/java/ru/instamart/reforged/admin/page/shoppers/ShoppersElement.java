package ru.instamart.reforged.admin.page.shoppers;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;

public interface ShoppersElement {

    Button createShoppersButton = new Button(By.xpath("//button/span[@aria-label='user-add']"), "кнопка 'Добавить сотрудника'");
}
