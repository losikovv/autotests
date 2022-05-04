package ru.instamart.reforged.admin.page.shipment;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.table.ShipmentTable;
import ru.instamart.reforged.core.Kraken;

public final class ShipmentsPage implements AdminPage, ShipmentsCheck {

    @Step("Получить номер заказа из строки {0}")
    public String getOrderNumber(final int line) {
        return tableComponent.getOrderNumber(line);
    }

    @Step("Нажать на номер заказа из строки {0}")
    public void clickToOrderNumber(final int line) {
        tableComponent.clickToOrderNumber(line);
    }

    @Step("Получить номер отправки из строки {0}")
    public String getShipmentNumber(final int line) {
        return tableComponent.getShipmentNumber(line);
    }

    @Step("Нажать на номер доставки из строки {0}")
    public void clickToShipmentNumber(final int line) {
        tableComponent.clickToShipmentNumber(line);
    }

    @Step("Получить номер отправки из строки {0}")
    public String getSum(final int line) {
        return StringUtil.parseNumericFromString(tableComponent.getSum(line));
    }

    @Step("Получить статус оплаты заказа из строки {0}")
    public ShipmentTable.PaymentStatus getPaymentStatus(final int line) {
        return tableComponent.getPaymentStatus(line);
    }

    @Step("Получить статус доставки заказа из строки {0}")
    public ShipmentTable.DeliveryStatus getDeliveryStatus(final int line) {
        return tableComponent.getDeliveryStatus(line);
    }

    @Step("Получить ретейлера из строки {0}")
    public String getRetailer(final int line) {
        return tableComponent.getRetailer(line);
    }

    @Step("Получить город ретейлера из строки {0}")
    public String getRetailerCity(final int line) {
        return tableComponent.getRetailerCity(line);
    }

    @Step("Получить комментарий к заказу из строки {0}")
    public String getComment(final int line) {
        return tableComponent.getComment(line);
    }

    @Step("Добавить комментарий {0} к заказу из строки {1}")
    public void addComment(final String text, final int line) {
        tableComponent.addComment(text, line);
    }

    @Step("Нажать на редактирование заказа из строки {0}")
    public void clickToEdit(final int line) {
        tableComponent.clickOnEdit(line);
    }

    @Step("Получить количество найденных товаров")
    public int getFoundCount() {
        return foundCount.getNumericValue();
    }

    @Step("Вставить номер отправки или заказа {0} в поле для поиска")
    public void setShipmentOrOrderNumber(final String shipmentNumber) {
        searchNumber.fill(shipmentNumber);
    }

    @Step("Вводим в поле 'Ритейлер'")
    public void fillRetailer(final String retailerName) {
        retailer.fill(retailerName);
    }

    @Step("Выбираем первый найденные результат в выпадающем списке виджета")
    public void clickOnFirstResultInDropDown() {
        inputSearchResults.selectFirst();
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

    @Step("Подставляем в фильтр 'Дата и Время от' переданное значение: {0}")
    public void setDateAndTimeFilterFromTableDefault(final String deliveryDate) {
        deliveryTimeFrom.fill(deliveryDate);
    }

    @Step("Подставляем в фильтр 'Дата и Время до' переданное значение: {0}")
    public void setDateAndTimeFilterToTableDefault(final String deliveryDate) {
        deliveryTimeTo.fill(deliveryDate);
    }

    @Step("Подставляем в фильтр телефон переданное значение: {0}")
    public void setPhoneFilterFromTableDefault(String phone) {
        phoneNumberContains.fill(phone);
    }

    @Step("Подставляем в фильтры телефон и дата переданные значения: {0}, {1}")
    public void setPhoneAndDateFilterDefault(String phone, String date) {
        phoneNumberContains.fill(phone);
        setDateAndTimeFilterFromTableDefault(date);
    }

    @Step("Получаем количество найденных заказов")
    public String getNumberOfShipments() {
        return foundShipments.getText().replace("НАЙДЕНО ЗАКАЗОВ: ", "");
    }

    @Step("Получаем количество страниц пейджера после применения фильтра")
    public String getNumberOfPagesAfterFiltration(final String numberOfShipments) {
        return String.valueOf((Integer.parseInt(numberOfShipments) + 50 - 1) / 50);
    }

    @Step("Выбрать фильтр только B2B клиенты")
    public void setB2BOrders() {
        b2bOnly.check();
    }

    @Override
    public String pageUrl() {
        return "shipments";
    }
}
