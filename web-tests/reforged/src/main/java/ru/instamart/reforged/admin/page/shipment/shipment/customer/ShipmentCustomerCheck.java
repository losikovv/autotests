package ru.instamart.reforged.admin.page.shipment.shipment.customer;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface ShipmentCustomerCheck extends Check, ShipmentCustomerElement {

    @Step("Проверяем, что значение в поле ввода 'Имя': '{expectedFirstNameValue}'")
    default void checkCustomerName(final String expectedFirstNameValue) {
        Assert.assertEquals(customerFirstName.getValue(), expectedFirstNameValue, "Значение в поле ввода 'Имя' отличается от ожидаемого");
    }
}
