package ru.instamart.reforged.admin.block.side_menu;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SideMenuCheck extends Check, SideMenuElement {

    @Step("Проверяем, что дропдаун магазинов кликабелен")
    default void checkStoresDropdownIsClickable() {
        waitAction().shouldBeClickable(storesDropdown);
    }

    @Step("Проверяем, что дропдаун заказов кликабелен")
    default void checkOrdersDropdownIsClickable() {
        waitAction().shouldBeClickable(ordersDropdown);
    }

    @Step("Проверяем, что дропдаун контента кликабелен")
    default void checkContentDropdownIsClickable() {
        waitAction().shouldBeClickable(contentDropdown);
    }

    @Step("Проверяем, что дропдаун маркетинга кликабелен")
    default void checkMarketingDropdownIsClickable() {
        waitAction().shouldBeClickable(marketingDropdown);
    }

    @Step("Проверяем, что дропдаун персонала кликабелен")
    default void checkStaffDropdownIsClickable() {
        waitAction().shouldBeClickable(staffDropdown);
    }
}
