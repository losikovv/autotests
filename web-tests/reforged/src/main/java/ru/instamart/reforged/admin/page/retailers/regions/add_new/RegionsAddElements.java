package ru.instamart.reforged.admin.page.retailers.regions.add_new;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface RegionsAddElements {

    Element addNewRegionModal = new Element(By.xpath("//div[@data-qa='operational_zone_edit_drawer']"), "Модальное окно создания региона");
    Input newRegionName = new Input(By.xpath("//input[@data-qa='operational_zone_edit_name']"), "Инпут для ввода имени нового региона");
    Button newRegionCreateButton = new Button(By.xpath("//button[@data-qa='operationalZone_edit_submit_edit_btn']"), "Кнопка подтверждения создания нового региона");
}
