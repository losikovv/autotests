package ru.instamart.reforged.admin.page.retailers.regions.add_new;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public interface RegionsAddElements {

    Input newRegionName = new Input(By.id("operational_zone_name"), "Инпут для ввода имени нового региона");
    Button newRegionCreateButton = new Button(By.xpath("//button[@type='submit']"), "Кнопка подтверждения создания нового региона");
}
