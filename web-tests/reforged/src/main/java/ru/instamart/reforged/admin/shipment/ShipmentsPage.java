package ru.instamart.reforged.admin.shipment;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.action.JsAction;
import ru.instamart.reforged.admin.variables.*;

public final class ShipmentsPage implements AdminPage, ShipmentsCheck {

    @Step("Получить номер заказа из первой строки")
    public String getOrderNumber() {
        return table.getFirstLine().findElement(By.xpath("//td/span[2]/a")).getText();
    }

    @Step("Получить номер отправки из первой строки")
    public String getShipmentNumber() {
        return table.getFirstLine().findElement(By.xpath("//td/span/a")).getText();
    }

    @Step("Получить количество найденных товаров")
    public String getFoundCount() {
        return foundCount.getText();
    }

    @Step("Вставить номер отправки или заказа {0} в поле для поиска")
    public void setShipmentOrOrderNumber(final String shipmentNumber) {
        searchNumber.fill(shipmentNumber);
    }

    @Step("Начать поиск с введенными данными")
    public void search() {
        submit.click();
    }

    @Step("Перейти на следующую страницу с заказами")
    public void nextPagerClick(){
        JsAction.INSTANCE.scrollToTheBottom();
        nextPager.click();
    }

    @Step("Перейти на предыдущую страницу с заказами")
    public void previousPagerClick(){
        JsAction.INSTANCE.scrollToTheBottom();
        previousPager.click();
    }

    @Step("Перейти на последнюю страницу с заказами")
    public void lastPageClick(){
        JsAction.INSTANCE.scrollToTheBottom();
        lastPage.click();
    }

    @Step("Перейти на первую страницу с заказами")
    public void firstPageClick(){
        JsAction.INSTANCE.scrollToTheBottom();
        firstPage.click();
    }

    @Step("Подставляем в фильтр Дата и Время доставки первое значение из таблицы заказов")
    public void setDateAndTimeFilterFromTableDefault(){
        dateAndTimeFirstCell.mouseOver();
        String [] DateAndTime = dateAndTimeFirstCell.getText().split(" ");
        deliveryTimeFrom.fill(DateAndTime[0].replace("с",""));
        variables.deliveryDate = DateAndTime[0].replace("с","");
        deliveryTimeFrom.fill(variables.deliveryDate);
        applyFilterButton.click();

    }

    @Override
    public String pageUrl() {
        return "shipments";
    }
}
