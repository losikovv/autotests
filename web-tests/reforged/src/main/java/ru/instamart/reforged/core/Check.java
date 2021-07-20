package ru.instamart.reforged.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.kraken.helper.KrakenAssert;
import ru.instamart.reforged.admin.variables.TestVariables;

public interface Check {

    Logger log = LoggerFactory.getLogger(Check.class);
    KrakenAssert krakenAssert = new KrakenAssert();

    TestVariables variables = new TestVariables();
    default void assertAll() {
        krakenAssert.assertAll();
    }
}
