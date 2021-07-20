package ru.instamart.reforged.admin.element;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Table;

@Getter
@Accessors(fluent = true)
public final class ShipmentsElement {

    private final Input orderDateFrom = new Input(By.id("search_completed_at_st"));
    private final Input orderDateTo = new Input(By.id("search_completed_at_end"));
    private final Input customerName = new Input(By.id("search_first_name"));
    private final Input customerSurname = new Input(By.id("search_last_name"));
    private final Input juridicalNameContains = new Input(By.id("search_order_company_name"));
    private final Input shopperLogin = new Input(By.id("search_shopper_login"));
    private final Input driverLogin = new Input(By.id("search_driver_login"));
    private final Input deliveryTimeFrom = new Input(By.id("search_delivery_window_starts_at_st"));
    private final Input deliveryTimeTo = new Input(By.id("search_delivery_window_starts_at_end"));
    private final Input phoneNumberContains = new Input(By.id("search_order_phone"));
    private final Input email = new Input(By.id("search_email"));
    private final Input searchNumber = new Input(By.id("search_number"));
    private final Input innNumber = new Input(By.id("search_inn"));
    private final Input invoiceNumber = new Input(By.id("search_invoice_number"));
    private final Input orderStatus = new Input(By.id("s2id_search_state"));
    private final Input retailer = new Input(By.id("s2id_search_retailer_id"));
    private final Input store = new Input(By.id("s2id_search_store_id"));
    private final Input paymentMethod = new Input(By.id("s2id_search_payment_method_id"));
    private final Input paymentStatus = new Input(By.id("s2id_search_payment_state"));
    private final Input promocode = new Input(By.id("search_coupon_code"));
    private final Input itemsFrom = new Input(By.id("search_item_count_st"));
    private final Input itemsTo = new Input(By.id("search_item_count_end"));
    private final Input weightFrom = new Input(By.id("search_total_weight_st"));
    private final Input weightTo = new Input(By.id("search_total_weight_end"));
    private final Input completedOnly = new Input(By.id("search_only_completed"));
    private final Input b2bOnly = new Input(By.id("search_only_b2b"));
    private final Input metroOnly = new Input(By.id("search_tenant"));
    private final Input deliveryChangedOnly = new Input(By.id("search_delivery_window_changed"));
    private final Button applyFilterButton = new Button(By.xpath("//button[@class='icon-search button']"));
    private final Button clearFilterButton = new Button(By.xpath("//a[@class='button icon-remove']"));
    private final Element foundCount = new Element(By.className("leader-text"));

    private final Table table = new Table();

    private final Button submit = new Button(By.xpath("//button[@type='submit']"));

    private final Element title = new Element(By.xpath("//div[@class='table-cell']"));
}
