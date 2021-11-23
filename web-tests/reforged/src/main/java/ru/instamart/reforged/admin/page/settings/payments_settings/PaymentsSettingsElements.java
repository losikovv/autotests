package ru.instamart.reforged.admin.page.settings.payments_settings;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface PaymentsSettingsElements {
    Element pageTitle = new Element(By.xpath("//h1[contains(@class, 'page-title')]"), "Заголовок страницы");
    Element paymentListing = new Element(By.id("listing_payment_methods"), "Таблица методов оплаты");
}
