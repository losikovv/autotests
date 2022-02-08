package ru.instamart.reforged.admin.page.retailers.activate_store_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface ActivateStoreModalElements {

    Element activateStoreModal = new Element(By.xpath("//input[@placeholder='Выберите дату']/ancestor::div[@class='ant-modal-content']"), "Модальное окно активации магазина");
    Button okButton = new Button(By.xpath("//div[@class='ant-modal-content']//button[contains(@class,'ant-btn-primary')]"), "Кнопка 'Ok' на модальном окне активации магазина");
}
