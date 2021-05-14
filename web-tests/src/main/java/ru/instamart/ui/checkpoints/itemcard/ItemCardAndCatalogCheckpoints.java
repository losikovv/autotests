package ru.instamart.ui.checkpoints.itemcard;

import io.qameta.allure.Step;
import ru.instamart.ui.checkpoints.Checkpoint;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.ui.modules.Base.kraken;

public class ItemCardAndCatalogCheckpoints implements Checkpoint {

    @Step("Проверяем, что карточка товара открыта")
    public void checkIsItemCardOpen(String errorMessage){
        log.info("> проверяем, что карточка товара открылась");
         assertTrue(
                kraken.detect().isItemCardOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что карточка товара закрыта")
    public void checkIsItemCardClosed(String errorMessage){
        log.info("> проверяем, что карточка товара закрылась");
        assertFalse(
                kraken.detect().isItemCardOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что шторка каталога открылась")
    public void checkIsCatalogDrawerOpen(String errorMessage){
        log.info("> проверяем, что шторка каталога открылась");
        assertTrue(
                kraken.detect().isCatalogDrawerOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что шторка каталога закрылась")
    public void checkIsCatalogDrawerClosed(String errorMessage){
        log.info("> проверяем, что шторка каталога закрылась");
        assertFalse(
                kraken.detect().isCatalogDrawerOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }
}
