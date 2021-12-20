package ru.instamart.reforged.admin.page.settings.shipping_method.new_method;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.block.flash_alert.FlashAlert;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Selector;

public interface ShippingNewMethodElement {

    FlashAlert alert = new FlashAlert();

    Input methodName = new Input(By.name("shipping_method[name]"), "инпут для ввода названия метода");
    Selector methodType = new Selector(By.name("shipping_method[shipping_method_kind_id]"), "селектор для выбора типа метода");
    Input methodCategory = new Input(By.id("s2id_autogen1"), "выбор категории для метода");
    Element methodCategoryList = new Element(By.xpath("//div[@class='select2-result-label']"), "выбор категории для метода из списка");

    Button submit = new Button(By.xpath("//button[@type='submit']"), "кнопка сабмита формы");
}
