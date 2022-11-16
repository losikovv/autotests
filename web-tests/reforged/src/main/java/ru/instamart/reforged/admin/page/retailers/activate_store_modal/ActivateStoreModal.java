package ru.instamart.reforged.admin.page.retailers.activate_store_modal;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.frame.Close;

import static ru.instamart.reforged.core.Kraken.waitAction;

public class ActivateStoreModal implements Close {

    private final Element activateStoreModal = new Element(By.xpath("//input[@placeholder='Выберите дату']/ancestor::div[@class='ant-modal-content']"), "Модальное окно активации магазина");
    private final Button okButton = new Button(By.xpath("//div[@class='ant-modal-content']//button[contains(@class,'ant-btn-primary')]"), "Кнопка 'Ok' на модальном окне активации магазина");

    @Step("Нажать на кнопку 'Ok' на модалке активации магазина")
    public void clickOnOkButton() {
        okButton.click();
    }

    @Step("Проверяем, что модальное окно активации магазина показано")
    public void checkActivateStoreModalVisible() {
        waitAction().shouldBeVisible(activateStoreModal);
        activateStoreModal.should().animationFinished();
    }

    @Step("Проверяем, что модальное окно активации магазина скрыто")
    public void checkActivateStoreModalNotVisible() {
        activateStoreModal.should().invisible();
    }
}
