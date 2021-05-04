package ru.instamart.ui.helpers;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.core.common.AppManager;
import ru.instamart.core.settings.Config;
import ru.instamart.ui.common.pagesdata.DeliveryTimeData;
import ru.instamart.ui.common.pagesdata.ReplacementPolicyData;
import ru.instamart.ui.modules.Base;
import ru.instamart.ui.modules.shop.ShippingAddressModal;
import ru.instamart.ui.objectsmap.Elements;

public final class DeliveryHelper extends Base {

    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);
    public DeliveryHelper(final AppManager kraken) {
        super(kraken);
    }


    public static void chooseDeliveryTime(DeliveryTimeData deliveryTime) {
        chooseDeliveryTime(deliveryTime.getDay(),deliveryTime.getSlot());
    }


    public static void chooseDeliveryTime(int day, int slot) {
        log.info("> переключаемся на {} день", day);
        //тут иногда падает из-за отсутсвия элемента
        kraken.await().implicitly(1); // Ожидание загрузки слотов дня в чекауте
//        kraken.await().fluently(ExpectedConditions.elementToBeClickable(
//                Elements.Checkout.deliveryDaySelector(day).getLocator()),
//                "Селектор дня доставки не появился", Config.BASIC_TIMEOUT);
        kraken.perform().click(Elements.Checkout.deliveryDaySelector(day));
        kraken.await().implicitly(1); // Ожидание загрузки слотов дня в чекауте
        if (kraken.detect().isElementPresent(Elements.Checkout.deliveryWindowsPlaceholder())){
            throw new AssertionError("Нет доступных слотов доставки");
            //Выбрасывать ошибку только если во всех датах нет слотов доставки, перебирать дни. В чем проблема: в выбранном дне может не быть слотов, но они могут быть в других(или будут, но недоступны). Либо генерирть слоты перед тестом
        }
        if (slot != 0) {
            log.info("> выбираем {} слот ({} / {})",
                    slot,
                    kraken.grab().text(Elements.Checkout.slotTime(day, slot)),
                    kraken.grab().text(Elements.Checkout.slotPrice(day, slot))
            );
            kraken.perform().click(Elements.Checkout.chooseSlotButton(day, slot));
        } else {
            log.info("> выбираем первый доступный интервал доставки");
            kraken.perform().click(Elements.Checkout.chooseSlotButton());
        }
        // TODO заменить на fluent-ожидание исчезновения спиннера + 1 implicity
        kraken.await().implicitly(3); // Ожидание применения слота доставки в чекауте
    }

    public static void choosePolicy(ReplacementPolicyData policy) {
        kraken.perform().click(Elements.Checkout.replacementPolicy(policy.getPosition()));
        log.info("> выбираем способ замен #{} ({})", policy.getPosition(), policy.getUserDescription());
    }
}
