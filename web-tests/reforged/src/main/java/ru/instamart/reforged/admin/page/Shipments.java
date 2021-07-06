package ru.instamart.reforged.admin.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.checkpoint.ShipmentsCheck;
import ru.instamart.reforged.admin.element.ShipmentsElement;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Table;

public final class Shipments extends ShipmentsCheck implements AdminPage {

    public Shipments() {
        super(new ShipmentsElement());
    }

    @Step("Получить номер заказа из первой строки")
    public String getOrderNumber() {
        return element.table().getFirstLine().findElement(By.xpath("//td/span[2]/a")).getText();
    }

    @Step("Получить номер отправки из первой строки")
    public String getShipmentNumber() {
        return element.table().getFirstLine().findElement(By.xpath("//td/span/a")).getText();
    }

    @Step("Получить количество найденных товаров")
    public String getFoundCount() {
        return element.foundCount().getText();
    }

    @Step("Вставить номер отправки или заказа {0} в поле для поиска")
    public void setShipmentOrOrderNumber(final String shipmentNumber) {
        element.searchNumber().fill(shipmentNumber);
    }

    @Step("Начать поиск с введенными данными")
    public void search() {
        element.submit().click();
    }

    @Override
    public String pageUrl() {
        return "shipments";
    }
}
