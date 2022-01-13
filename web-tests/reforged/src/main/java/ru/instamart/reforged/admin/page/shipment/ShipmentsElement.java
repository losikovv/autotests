package ru.instamart.reforged.admin.page.shipment;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface ShipmentsElement {

    Input orderDateFrom = new Input(By.id("search_completed_at_st"), "empty");
    Input orderDateTo = new Input(By.id("search_completed_at_end"), "empty");
    Input customerName = new Input(By.id("search_first_name"), "empty");
    Input customerSurname = new Input(By.id("search_last_name"), "empty");
    Input juridicalNameContains = new Input(By.id("search_order_company_name"), "empty");
    Input shopperLogin = new Input(By.id("search_shopper_login"), "empty");
    Input driverLogin = new Input(By.id("search_driver_login"), "empty");
    Input deliveryTimeFrom = new Input(By.id("search_delivery_window_starts_at_st"), "время доставки от");
    Input deliveryTimeTo = new Input(By.id("search_delivery_window_starts_at_end"), "время доставки до");
    Input phoneNumberContains = new Input(By.id("search_order_phone"), "empty");
    Input email = new Input(By.id("search_email"), "empty");
    Input searchNumber = new Input(By.id("search_number"), "empty");
    Input innNumber = new Input(By.id("search_inn"), "empty");
    Input invoiceNumber = new Input(By.id("search_invoice_number"), "empty");
    Input orderStatus = new Input(By.id("s2id_search_state"), "empty");
    Input retailer = new Input(By.id("s2id_search_retailer_id"), "empty");
    Input store = new Input(By.id("s2id_search_store_id"), "empty");
    Input paymentMethod = new Input(By.id("s2id_search_payment_method_id"), "empty");
    Input paymentStatus = new Input(By.id("s2id_search_payment_state"), "empty");
    Input promocode = new Input(By.id("search_coupon_code"), "empty");
    Input itemsFrom = new Input(By.id("search_item_count_st"), "empty");
    Input itemsTo = new Input(By.id("search_item_count_end"), "empty");
    Input weightFrom = new Input(By.id("search_total_weight_st"), "empty");
    Input weightTo = new Input(By.id("search_total_weight_end"), "empty");
    Input completedOnly = new Input(By.id("search_only_completed"), "empty");
    Checkbox b2bOnly = new Checkbox(By.id("search_only_b2b"), "empty");
    Input metroOnly = new Input(By.id("search_tenant"), "empty");
    Input deliveryChangedOnly = new Input(By.id("search_delivery_window_changed"), "empty");
    Button applyFilterButton = new Button(By.xpath("//button[@class='icon-search button']"), "empty");
    Button clearFilterButton = new Button(By.xpath("//a[@class='button icon-remove']"), "empty");
    Element foundCount = new Element(By.className("leader-text"), "empty");

    Table table = new Table();

    Button submit = new Button(By.xpath("//button[@type='submit']"), "отправка запроса на фильтрацию");

    Element title = new Element(By.xpath("//div[@class='table-cell']"), "empty");

    Button nextPager = new Button(By.linkText("Следущая"),
            "ссылка Следующая страница");
    Button previousPager = new Button(By.linkText("Предыдущая"),
            "ссылка Предыдущая страница");
    Button lastPage = new Button(By.linkText("Последняя"),
            "ссылка Последняя страница");
    Button firstPage = new Button(By.linkText("Первая"),
            "ссылка Первая страница");
    Button currentPage = new Button(By.xpath("//span[@class='page current']"),
            "номер текущей страницы пейджера");

    Element dateAndTimeFirstCell = new Element(
            By.xpath("//th[text()='Дата и время доставки']//following::a[contains(@href,'delivery_windows')] [1]"),
            "дата и время доставки из первой ячейки в таблице");

    ElementCollection dateAndTimeColumn = new ElementCollection(
            By.xpath("//th[text()='Дата и время доставки']//following::a[contains(@href,'delivery_windows')]"),
            "список всех дат и времен доставки из колонки после фильтрации");

    Element phoneFirstCell = new Element(
            By.xpath("//th[text()='Куда']//following::div[@class='where-to__phone with-tip'] [1]"),
            "телефон клиента из первой ячейки в таблице");

    ElementCollection phoneColumn = new ElementCollection(
            By.xpath("//table/tbody/tr/td[count(//table/thead/tr/th[.='Куда']/preceding-sibling::th)+1]"),
            "список всех телефонов из колонки после фильтрации");

    Element foundShipments = new Element(
            By.xpath("//div[contains(text(),'Найдено заказов:')]"),
            "элемент с количеством найденных заказов");
}
