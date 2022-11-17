package ru.instamart.reforged.stf.page.checkout_new;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.core.page.Window;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.page.StfPage;
import ru.instamart.reforged.stf.page.checkout_new.add_payment_card_modal.AddPaymentCardModal;
import ru.instamart.reforged.stf.page.checkout_new.b2b_order_modal.B2BOrderModal;
import ru.instamart.reforged.stf.page.checkout_new.delivery_slots_modal.DeliverySlotsModal;
import ru.instamart.reforged.stf.page.checkout_new.payment_methods_modal.PaymentMethodsModal;
import ru.instamart.reforged.stf.page.checkout_new.sber_spasibo_card_modal.SberSpasiboCardModal;

public class CheckoutNewPage implements StfPage, CheckoutNewCheck, Window {

    public PaymentMethodsModal interactPaymentMethodsModal() {
        return paymentMethodsModal;
    }

    public AddPaymentCardModal interactAddPaymentCardModal() {
        return addPaymentCardModal;
    }

    public DeliverySlotsModal interactDeliverySlotsModal() {
        return deliverySlotsModal;
    }

    public B2BOrderModal interactB2BOrderModal() {
        return b2bOrderModal;
    }

    public SberSpasiboCardModal interactSberSpasiboModal() {
        return sberSpasiboCardModal;
    }

    public HelpDesk interactHelpDesk() {
        return helpDesk;
    }

    @Step("Переходим на вкладку 'Самовывоз'")
    public void switchToPickup() {
        pickupTab.click();
    }

    @Step("Переходим на вкладку 'Доставка'")
    public void switchToDelivery() {
        deliveryTab.click();
    }

    @Step("Получаем полный адрес доставки из заголовка блока")
    public String getAddressFromTitle() {
        return addressTitle.getText();
    }

    @Step("Кликаем по заголовку блока адреса")
    public void clickAddressTitle() {
        addressTitle.click();
    }

    @Step("Вводим в поле 'Кв, офис': '{apartmentValue}'")
    public void fillApartment(final String apartmentValue) {
        apartment.fill(apartmentValue);
    }

    @Step("Вводим в поле 'Подъезд': '{entranceValue}'")
    public void fillEntrance(final String entranceValue) {
        entrance.fill(entranceValue);
    }

    @Step("Вводим в поле 'Этаж': '{floorValue}'")
    public void fillFloor(final String floorValue) {
        floor.fill(floorValue);
    }

    @Step("Вводим в поле 'Домофон': '{doorPhoneValue}'")
    public void fillDoorPhone(final String doorPhoneValue) {
        doorPhone.fill(doorPhoneValue);
    }

    @Step("Вводим в поле 'Комментарий': '{commentValue}'")
    public void fillComment(final String commentValue) {
        comment.fill(commentValue);
    }

    @Step("Выбираем чекбокс 'Бесконтактная доставка'")
    public void clickDeliveryToDoor() {
        deliveryToDoor.check();
    }

    @Step("Нажимаем на ссылку 'Заказываю для бизнеса'")
    public void clickOrderForBusiness() {
        b2bLink.click();
    }

    @Step("Проскролливаем список к первому слоту")
    public void scrollToFirstSlot() {
        deliverySlotPrice.scrollTo();
    }

    @Step("Нажимаем на кнопку прокрутки списка 'Вперёд'")
    public void clickNext() {
        deliverySlotsCarouselNext.hoverAndClick();
    }

    @Step("Нажимаем на кнопку прокрутки списка 'Назад'")
    public void clickPrev() {
        deliverySlotsCarouselPrev.hoverAndClick();
    }

    @Step("Прокручиваем список слотов к началу")
    public void scrollSlotsToStart() {
        for (int i = 0; i < 3; i++) {
            clickPrev();
            ThreadUtil.simplyAwait(1);
        }
    }

    @Step("Прокручиваем список слотов к концу списка")
    public void scrollSlotsToEnd() {
        for (int i = 0; i < 3; i++) {
            clickNext();
            ThreadUtil.simplyAwait(1);
        }
    }

    @Step("Выбираем ближайший доступный слот доставки")
    public void clickFirstSlot() {
        allDeliverySlots.clickOnFirst();
    }

    @Step("Выбираем OnDemand слот доставки")
    public void clickOnDemandSlot() {
        onDemandDeliverySlots.clickOnFirst();
    }

    @Step("Выбираем последний доступный слот доставки")
    public void clickLastSlot() {
        deliverySlots.clickOnLast();
    }

    @Step("Выбираем второй доступный слот доставки")
    public void clickSecondSlot() {
        allDeliverySlots.getElements().get(1).click();
    }

    @Step("Получаем дату выбранного слота")
    public String getActiveSlotDate() {
        return activeDeliverySlotDate.getText();
    }

    @Step("Получаем время выбранного слота")
    public String getActiveSlotTime() {
        return activeDeliverySlotTime.getText();
    }

    @Step("Получаем стоимость доставки выбранного слота")
    public String getActiveSlotCost() {
        return activeDeliverySlotCost.getText();
    }

    @Step("Получаем сумму заказа")
    public String getOrderAmount() {
        return orderAmount.getText();
    }

    @Step("Получаем сумму заказа")
    public Double getOrderAmountDouble() {
        return StringUtil.stringToDouble(orderAmount.getText());
    }

    @Step("Убеждаемся что сумма заказа уменьшилась после применения промокода")
    public void checkAmountDecreaseAfterApplyPromo(String text) {
        Assert.assertTrue(StringUtil.stringToDouble(orderAmount.getText()) < StringUtil.stringToDouble(text), "Сумма заказа не стала меньше после применения промокода");
    }

    @Step("Убеждаемся что сумма заказа не изменилась")
    public void checkAmountNotChanged(String text) {
        Assert.assertEquals(StringUtil.stringToDouble(orderAmount.getText()), StringUtil.stringToDouble(text), "Сумма заказа изменилась");
    }

    @Step("Открываем модальное окно выбора слотов доставки из заголовка блока слотов")
    public void openDeliverySlotsModalFromTitle() {
        openDeliverySlotsModalInTitle.click();
    }

    @Step("Открываем модальное окно выбора слотов доставки из блока 'Другие дни'")
    public void openDeliverySlotsModalFromOthers() {
        openDeliverySlotsModalInCarousel.scrollTo();
        openDeliverySlotsModalInCarousel.getActions().clickWithOffset(0, 35);
    }

    @Step("Открываем модальное окно выбора способа оплаты")
    public void openPaymentMethodModal() {
        selectPaymentMethod.click();
    }

    @Step("Нажимаем на кнопку 'Выбрать компанию'")
    public void clickSelectCompany() {
        selectCompany.click();
    }

    @Step("Нажимаем 'Изменить' (контакты)")
    public void clickEditContacts() {
        contactsEdit.click();
    }

    @Step("Очищаем поле 'Телефон'")
    public void clearPhone() {
        contactsPhone.clear(true);
    }

    @Step("Вводим в поле 'Телефон': '{phoneValue}'")
    public void fillPhone(final String phoneValue) {
        contactsPhone.fill(phoneValue);
    }

    @Step("Очищаем поле 'Email'")
    public void clearEmail() {
        contactsEmail.clearByKeysCombination();
    }

    @Step("Вводим в поле 'Email': '{emailValue}'")
    public void fillEmail(final String emailValue) {
        contactsEmail.fill(emailValue);
    }

    @Step("Нажимаем 'Изменить' (Замена товара)")
    public void clickEditReplacementPolicy() {
        replacementEdit.click();
    }

    @Step("Кликаем в селектор выбора политики замен товара")
    public void clickReplacementPolicy() {
        replacementPolicy.click();
    }

    @Step("Выбираем перый вариант замены товара в выпадающем списке")
    public void selectFirstReplacementPolicy() {
        replacementPolicyList.clickOnFirst();
    }

    @Step("Выбираем политику безопасности: '{policyName}'")
    public void selectReplacementPolicyByName(final String policyName) {
        replacementPolicyByName.click(policyName);
    }

    @Step("Вводим промокод: '{promoCodeValue}'")
    public void fillPromoCode(final String promoCodeValue) {
        promoCode.clear();
        promoCode.fill(promoCodeValue);
    }

    @Step("Нажимаем 'Применить' (промокод)")
    public void clickApplyPromoCode() {
        promoCodeApply.click();
    }

    @Step("Нажимаем 'Отменить' (промокод)")
    public void clickCancelPromoCode() {
        promoCodeCancel.click();
    }

    @Step("Нажимаем 'Оплатить'")
    public void clickConfirmPay() {
        confirmPay.click();
    }

    @Step("Нажимаем 'Заказать'")
    public void clickConfirmOrder() {
        confirmOrder.click();
    }

    @Override
    public String pageUrl() {
        return "checkout/order/%s";
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу оформления заказа необходимо использовать метод с параметрами");
    }

    public void goToPage(final String orderNumber) {
        goToPage(String.format(pageUrl(), orderNumber));
    }
}
