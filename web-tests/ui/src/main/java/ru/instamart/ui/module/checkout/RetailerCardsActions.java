package ru.instamart.ui.module.checkout;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.core.testdata.pagesdata.LoyaltiesData;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.module.shop.ShippingAddressModal;
import ru.instamart.ui.Elements;

public final class RetailerCardsActions extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);
    public RetailerCardsActions(final AppManager kraken) {
        super(kraken);
    }

    @Step("Добавляем карту ритейлера")
    public static void addCard(LoyaltiesData card) {
        if (kraken.detect().isRetailerCardAdded()) {
            deleteCard();
        }
        log.info("> добавляем карту ритейлера \"{}\"", card.getName());
        kraken.perform().click(Elements.Checkout.RetailerCard.input());
        kraken.perform().fillField(Elements.Checkout.RetailerCard.Modal.inputField(),card.getCardNumber() + "\uE007");
        kraken.perform().click(Elements.Checkout.RetailerCard.Modal.saveButton());
//        kraken.await().implicitly(1); // Ожидание добавления карты ритейлера в чекауте
    }

    @Step("Удаляем карту ритейлера")
    public static void deleteCard() {
        log.info("> удаляем карту ритейлера");
        kraken.perform().click(Elements.Checkout.RetailerCard.input());
        kraken.perform().click(Elements.Checkout.RetailerCard.Modal.deleteButton());
//        kraken.await().implicitly(1); // Ожидание удаления карты ритейлера в чекауте
    }
}
