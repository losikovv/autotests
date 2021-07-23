package ru.instamart.reforged.admin.page.shipment;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;

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
    public void nextPagerClick() {
        Kraken.jsAction().scrollToTheBottom();
        nextPager.click();
    }

    @Step("Перейти на предыдущую страницу с заказами")
    public void previousPagerClick() {
        Kraken.jsAction().scrollToTheBottom();
        previousPager.click();
    }

    @Step("Перейти на последнюю страницу с заказами")
    public void lastPageClick() {
        Kraken.jsAction().scrollToTheBottom();
        lastPage.click();
    }

    @Step("Перейти на первую страницу с заказами")
    public void firstPageClick() {
        Kraken.jsAction().scrollToTheBottom();
        firstPage.click();
    }

    @Step("Подставляем в фильтр Дата и Время переданное значение: {0}")
    public void setDateAndTimeFilterFromTableDefault(String deliveryDate) {
        deliveryTimeFrom.fill(deliveryDate);
    }

    @Step("Нажать кнопку применить фильтр")
    public void applyFilterButton() {
        applyFilterButton.click();
    }

    @Step("Получаем дату доставки из первой стройки таблицы с заказами")
    public String getFirstDeliveryDateFromTable() {
        dateAndTimeFirstCell.mouseOver();
        String[] dateAndTime = dateAndTimeFirstCell.getText().split(" ");
        return dateAndTime[0].replace("с", "");
    }

    @Step("Получаем номер телефона из первой стройки таблицы с заказами")
    public String getFirstPhoneFromTable() {
        dateAndTimeFirstCell.mouseOver();
        return phoneFirstCell.getText();
    }

    @Step("Подставляем в фильтр телефон переданное значение: {0}")
    public void setPhoneFilterFromTableDefault(String phone) {
        phoneNumberContains.fill(phone);
    }

    @Step("Подставляем в фильтр телефон и дата переданные значения: {0}, {1}")
    public void setPhoneAndDateFilterDefault(String phone, String date) {
        phoneNumberContains.fill(phone);
        deliveryTimeFrom.fill(date);
    }

    @Override
    public String pageUrl() {
        return "shipments";
    }
}
