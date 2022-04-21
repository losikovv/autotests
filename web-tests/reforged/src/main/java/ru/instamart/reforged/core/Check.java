package ru.instamart.reforged.core;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.kraken.helper.KrakenAssert;

public interface Check {

    Logger log = LoggerFactory.getLogger(Check.class);
    KrakenAssert krakenAssert = new KrakenAssert();

    default void assertAll() {
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что установленный адрес: '{0}'\n совпадает с адресом, отображаемом на странице: '{1}'")
    default void checkIsSetAddressEqualToInput(final String defaultAddress, final String currentAddress) {
        final var defaultAddressList = defaultAddress
                .replaceAll("г\\.", "")
                .replaceAll("ул\\.", "")
                .replaceAll("улица", "")
                .split(", ");
        log.debug("> проверяем, что установленный адрес: '{}' совпадает с адресом на странице: '{}'",
                defaultAddress,
                currentAddress);
        for (final var check : defaultAddressList) {
            krakenAssert.assertTrue(
                    currentAddress.contains(check),
                    "\n> В адресе отображаемом на странице отсутствует элемент: "
                            + "\n> отображаемый адрес: " + currentAddress
                            + "\n> Ожидаемый элемент: " + check
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что все запросы загрузились")
    default void checkRequestsWasLoad() {
        Kraken.jsAction().checkPendingRequests();
    }
}
