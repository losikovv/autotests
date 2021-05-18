package ru.instamart.ui.checkpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.kraken.helper.KrakenAssert;

public interface Checkpoint {

    Logger log = LoggerFactory.getLogger(Checkpoint.class);
    KrakenAssert krakenAssert = new KrakenAssert();

    default void assertAll() {
        krakenAssert.assertAll();
    }
}
