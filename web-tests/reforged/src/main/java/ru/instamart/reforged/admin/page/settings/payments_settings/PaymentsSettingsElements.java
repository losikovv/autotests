package ru.instamart.reforged.admin.page.settings.payments_settings;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface PaymentsSettingsElements {
    Element pageTitle = new Element(By.xpath("//h4[text()='Способы оплаты']"), "Заголовок страницы");
    Element paymentListing = new Element(By.xpath("//tr[@data-qa='payment_methods_table_row']/ancestor::div[@class='ant-table-wrapper']"), "Таблица методов оплаты");
}
