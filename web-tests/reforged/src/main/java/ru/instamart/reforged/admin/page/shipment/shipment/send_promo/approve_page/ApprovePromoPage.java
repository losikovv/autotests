package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.approve_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.page.shipment.shipment.send_promo.approve_page.cancel_modal.CancelPromoModal;

public class ApprovePromoPage implements AdminPage, ApprovePromoCheck {

    public CancelPromoModal interactCancelModal() {
        return cancelModal;
    }

    @Step("Нажимаем на кнопку 'Отказать'")
    public void clickOnCancel() {
        cancelPromoButton.scrollTo();
        cancelPromoButton.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}