package ru.instamart.ui.checkpoints.users;

import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static ru.instamart.ui.modules.Base.kraken;

public class FavoriteItemsCheckpoints extends BaseUICheckpoints {

    private static final Logger log = LoggerFactory.getLogger(FavoriteItemsCheckpoints.class);

    @Step("Проверяем переход в категорию любимых товаров")
    public void checkIsFavoriteOpen(){
        log.info("> проверяем, что категория любимых товаров открыта");
        Assert.assertTrue(
                kraken.detect().isInFavorites(),
                "Не работает переход в любимые товары по кнопке в шапке\n");
        log.info("✓ Успешно");
    }
}
