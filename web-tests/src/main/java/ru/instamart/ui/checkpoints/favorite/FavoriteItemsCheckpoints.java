package ru.instamart.ui.checkpoints.favorite;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.ui.modules.Base.kraken;

public interface FavoriteItemsCheckpoints {

    Logger log = LoggerFactory.getLogger(FavoriteItemsCheckpoints.class);

    @Step("Проверяем переход в категорию любимых товаров")
    default void checkIsFavoriteOpen(){
        log.info("> проверяем, что категория любимых товаров открыта");
        assertTrue(
                kraken.detect().isInFavorites(),
                "Не работает переход в любимые товары по кнопке в шапке\n");
        log.info("✓ Успешно");
    }

    @Step("Проверка что список избранного пуст")
    default void checkFavoriteIsEmpty(){
        log.info("> проверяем, что категория любимых товаров пуста");
        assertTrue(
                kraken.detect().isFavoritesEmpty(),
                "Дефолтный список любимых товаров у нового пользователя не пуст\n");
        log.info("✓ Успешно");
    }

    @Step("Проверка что список избранного пуст")
    default void checkFavoriteIsNotEmpty(){
        log.info("> проверяем, что категория любимых товаров не пуста");
        assertFalse(
                kraken.detect().isFavoritesEmpty(),
                "Не работает добавление любимого товара из карточки товара\n");
        log.info("✓ Успешно");
    }
}
