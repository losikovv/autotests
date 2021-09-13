package ru.instamart.ui.helper;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.testdata.pagesdata.DeliveryTimeData;
import ru.instamart.kraken.testdata.pagesdata.ReplacementPolicyData;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.Elements;

@Slf4j
public final class DeliveryHelper extends Base {

    public DeliveryHelper(final AppManager kraken) {
        super(kraken);
    }

    public static void chooseDeliveryTime(DeliveryTimeData deliveryTime) {
        chooseDeliveryTime(deliveryTime.getDay(),deliveryTime.getSlot());
    }

    public static void chooseDeliveryTime(int day, int slot) {
        log.debug("> переключаемся на {} день", day);
        //тут иногда падает из-за отсутсвия элемента
        kraken.await().implicitly(1); // Ожидание загрузки слотов дня в чекауте
        kraken.perform().click(Elements.Checkout.deliveryDaySelector(day));
        kraken.await().implicitly(1); // Ожидание загрузки слотов дня в чекауте
        if (kraken.detect().isElementPresent(Elements.Checkout.deliveryWindowsPlaceholder())){
            throw new AssertionError("Нет доступных слотов доставки");
            //Выбрасывать ошибку только если во всех датах нет слотов доставки, перебирать дни. В чем проблема: в выбранном дне может не быть слотов, но они могут быть в других(или будут, но недоступны). Либо генерирть слоты перед тестом
        }
        if (slot != 0) {
            log.debug("> выбираем {} слот ({} / {})",
                    slot,
                    kraken.grab().text(Elements.Checkout.slotTime(day, slot)),
                    kraken.grab().text(Elements.Checkout.slotPrice(day, slot))
            );
            kraken.perform().click(Elements.Checkout.chooseSlotButton(day, slot));
        } else {
            log.debug("> выбираем первый доступный интервал доставки");
            kraken.perform().click(Elements.Checkout.chooseSlotButton());
        }
        // TODO заменить на fluent-ожидание исчезновения спиннера + 1 implicity
        kraken.await().implicitly(3); // Ожидание применения слота доставки в чекауте
    }

    public static void choosePolicy(ReplacementPolicyData policy) {
        kraken.perform().click(Elements.Checkout.replacementPolicy(policy.getPosition()));
        log.debug("> выбираем способ замен #{} ({})", policy.getPosition(), policy.getUserDescription());
    }
}
