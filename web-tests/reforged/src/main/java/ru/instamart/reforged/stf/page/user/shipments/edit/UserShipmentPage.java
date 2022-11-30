package ru.instamart.reforged.stf.page.user.shipments.edit;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.RepeatModal;
import ru.instamart.reforged.stf.frame.order_evaluation_modal.OrderEvaluationModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.frame.shipment_cancel_modal.ShipmentCancelModal;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.reforged.stf.page.user.shipments.edit.delivery_slots_modal.DeliverySlotsModal;

import java.util.Set;

public final class UserShipmentPage implements StfPage, UserShipmentCheck {

    public Header interactHeader() {
        return header;
    }

    public RepeatModal interactRepeatModal() {
        return repeatModal;
    }

    public ShipmentCancelModal interactShipmentCancelModal() {
        return shipmentCancelModal;
    }

    public DeliverySlotsModal interactDeliverySlotsModal() {
        return deliverySlotsModal;
    }

    public OrderEvaluationModal interactOrderEvaluationModal() {
        return orderEvaluationModal;
    }

    public ProductCard interactProductCard(){
        return productCard;
    }

    @Step("Нажимаем на '{starPosition}'-ю звездочку в блоке оценки заказа ")
    public void clickStar(final int starPosition) {
        rateStars.getElements().get(starPosition - 1).click();
    }

    @Step("Наводим курсор на '3'-ю звездочку в блоке оценки заказа ")
    public void hoverThirdStar() {
        thirdRateStar.getActions().mouseOver();
    }

    @Step("Выбираем фильтр 'Собрано'")
    public void selectCollectedFilter() {
        filterCollected.click();
    }

    @Step("Выбираем фильтр 'Замены'")
    public void selectReplacementFilter() {
        filterReplaced.click();
    }

    @Step("Выбираем фильтр 'Отмены'")
    public void selectCancelledFilter() {
        filterCancelled.click();
    }

    @Step("Выбираем фильтр 'Все'")
    public void selectAlldFilter() {
        filterAll.click();
    }

    @Step("Нажимаем на кнопку 'Повторить заказ' на странице заказа")
    public void clickToRepeatFromOrder() {
        repeatOrder.click();
    }

    @Step("Нажимаем на кнопку 'Отменить заказ'")
    public void clickToCancelFromOrder() {
        cancelOrder.click();
    }

    @Step("Нажимаем на кнопку 'Изменить' (слот доставки)")
    public void clickChangeDeliverySlot() {
        changeDeliverySlot.click();
    }

    @Step("Получаем номер заказа")
    public String getShipmentNumber() {
        return shipmentNumber.getText();
    }

    @Step("Получаем стоимость заказа")
    public String getTotalCost() {
        return totalCost.getText();
    }

    @Step("Получаем список товаров в заказе")
    public Set<String> getItemsList() {
        return productInOrderNames.getTextFromAllElements();
    }

    @Step("Кликаем на название первого продукта")
    public void clickFirstProductName(){
        productInOrderNames.clickOnFirst();
    }

    @Step("Нажимаем кнопку 'Посмотреть ещё'")
    public void clickSeeMore() {
        seeMore.click();
    }

    @Override
    public String pageUrl() {
        return "user/shipments/%s";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу информации о заказе необходимо использовать метод с параметрами");
    }

    public void openShipmentPageByNumber(final String shipmentNumber) {
        goToPage(String.format(pageUrl(), shipmentNumber));
    }
}
