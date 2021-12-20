package ru.instamart.reforged.admin.page.settings.shipping_method.new_method;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.block.flash_alert.FlashAlert;

public final class ShippingNewMethodPage implements AdminPage, ShippingNewMethodCheck {

    public FlashAlert interactFlashAlert() {
        return alert;
    }

    @Step("Ввести название метода {0}")
    public void fillMethodName(final String data) {
        methodName.fill(data);
    }

    @Step("Выбрать тип метода {0}")
    public void selectType(final String data) {
        methodType.selectByText(data);
    }

    @Step("Выбрать категорию метода {0}")
    public void addDeliveryCategory(final String data) {
        methodCategory.fill(data);
        methodCategoryList.click();
    }

    @Step("Сохранить новый метод")
    public void clickToSubmit() {
        submit.click();
    }

    @Override
    public String pageUrl() {
        return "shipping_methods/new";
    }
}
