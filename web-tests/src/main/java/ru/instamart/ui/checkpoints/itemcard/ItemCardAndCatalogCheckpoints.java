package ru.instamart.ui.checkpoints.itemcard;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static ru.instamart.ui.modules.Base.kraken;

public class ItemCardAndCatalogCheckpoints extends BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(ItemCardAndCatalogCheckpoints.class);

    private final SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что карточка товара открыта")
    public void checkIsItemCardOpen(String errorMessage){
        log.info("> проверяем, что карточка товара открылась");
        softAssert.assertTrue(
                kraken.detect().isItemCardOpen(),
                errorMessage);
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что карточка товара закрыта")
    public void checkIsItemCardClosed(String errorMessage){
        log.info("> проверяем, что карточка товара закрылась");
        softAssert.assertFalse(
                kraken.detect().isItemCardOpen(),
                errorMessage);
        softAssert.assertAll();
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что шторка каталога открылась")
    public void checkIsCatalogDrawerOpen(String errorMessage){
        log.info("> проверяем, что шторка каталога открылась");
        Assert.assertTrue(
                kraken.detect().isCatalogDrawerOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что шторка каталога закрылась")
    public void checkIsCatalogDrawerClosed(String errorMessage){
        log.info("> проверяем, что шторка каталога закрылась");
        Assert.assertFalse(
                kraken.detect().isCatalogDrawerOpen(),
                errorMessage);
        log.info("✓ Успешно");
    }
}
