package ru.instamart.reforged.admin.shipment;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Table;

public interface ShipmentsElement {

    Input orderDateFrom = new Input(By.id("search_completed_at_st"));
    Input orderDateTo = new Input(By.id("search_completed_at_end"));
    Input customerName = new Input(By.id("search_first_name"));
    Input customerSurname = new Input(By.id("search_last_name"));
    Input juridicalNameContains = new Input(By.id("search_order_company_name"));
    Input shopperLogin = new Input(By.id("search_shopper_login"));
    Input driverLogin = new Input(By.id("search_driver_login"));
    Input deliveryTimeFrom = new Input(By.id("search_delivery_window_starts_at_st"));
    Input deliveryTimeTo = new Input(By.id("search_delivery_window_starts_at_end"));
    Input phoneNumberContains = new Input(By.id("search_order_phone"));
    Input email = new Input(By.id("search_email"));
    Input searchNumber = new Input(By.id("search_number"));
    Input innNumber = new Input(By.id("search_inn"));
    Input invoiceNumber = new Input(By.id("search_invoice_number"));
    Input orderStatus = new Input(By.id("s2id_search_state"));
    Input retailer = new Input(By.id("s2id_search_retailer_id"));
    Input store = new Input(By.id("s2id_search_store_id"));
    Input paymentMethod = new Input(By.id("s2id_search_payment_method_id"));
    Input paymentStatus = new Input(By.id("s2id_search_payment_state"));
    Input promocode = new Input(By.id("search_coupon_code"));
    Input itemsFrom = new Input(By.id("search_item_count_st"));
    Input itemsTo = new Input(By.id("search_item_count_end"));
    Input weightFrom = new Input(By.id("search_total_weight_st"));
    Input weightTo = new Input(By.id("search_total_weight_end"));
    Input completedOnly = new Input(By.id("search_only_completed"));
    Input b2bOnly = new Input(By.id("search_only_b2b"));
    Input metroOnly = new Input(By.id("search_tenant"));
    Input deliveryChangedOnly = new Input(By.id("search_delivery_window_changed"));
    Button applyFilterButton = new Button(By.xpath("//button[@class='icon-search button']"));
    Button clearFilterButton = new Button(By.xpath("//a[@class='button icon-remove']"));
    Element foundCount = new Element(By.className("leader-text"));

    Table table = new Table();

    Button submit = new Button(By.xpath("//button[@type='submit']"));

    Element title = new Element(By.xpath("//div[@class='table-cell']"));
}
