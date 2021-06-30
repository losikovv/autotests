package ru.instamart.reforged.admin.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.checkpoint.ShipmentsCheck;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Table;

public final class Shipments implements AdminPage, ShipmentsCheck {

    private final Table table = new Table(true);
    private final Input searchNumber = new Input(By.id("search_number"));
    private final Button submit = new Button(By.xpath("//button[@type='submit']"));

    private final Element foundShipment = new Element(By.className("leader-text"));

    @Step("Получить номер заказа из первой строки")
    public String getShipmentNumber() {
        return table.getFirstLine().findElement(By.xpath("//td/span[2]/a")).getText();
    }

    @Step("Получить количество найденных товаров")
    public String getFoundShipmentCount() {
        return foundShipment.getText();
    }

    @Step("Вставить номер заказа {0} в поле для поиска")
    public void setShipmentNumber(final String shipmentNumber) {
        searchNumber.fill(shipmentNumber);
    }

    @Step("Начать поиск с введенными данными")
    public void search() {
        submit.click();
    }

    @Override
    public String pageUrl() {
        return "shipments";
    }
}
