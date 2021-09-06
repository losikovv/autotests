package ru.instamart.reforged.admin.page.settings.all_cities.city_add;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public interface CityAddElements {

    Input cityNameInput = new Input(By.id("city_name"));
    Button createButton = new Button(By.xpath("//button[@type='submit']"));
}
