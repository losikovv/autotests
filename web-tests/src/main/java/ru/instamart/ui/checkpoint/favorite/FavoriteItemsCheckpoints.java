package ru.instamart.ui.checkpoint.favorite;

import io.qameta.allure.Step;
import ru.instamart.core.util.StringUtil;
import ru.instamart.ui.checkpoint.Checkpoint;
import ru.instamart.ui.Elements;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.ui.module.Base.kraken;

public interface FavoriteItemsCheckpoints extends Checkpoint {

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

    @Step("Проверка фильтра {0} {1}")
    default void checkFavoriteFilter(final String filter, final String humanName) {
        krakenAssert.assertTrue(
                kraken.detect().isFavoritesFiltered(filter),
                StringUtil.failMessage("В любимых товарах по умолчанию не применен фильтр '" + humanName + "'"));
    }

    @Step("Проверка что кнопка 'Показать больше' не отображается")
    default void checkShowMoreNotDisplayed() {
        assertTrue(kraken.await().shouldNotBeVisible(Elements.Favorites.showMoreButton()));
    }

    @Step("Проверяем что подгрузились избранные товары")
    default void checkCountChange(final int initCount, final int finalCount) {
        assertTrue(initCount < finalCount, "Товары не подгрузились");
    }

    @Step("Проверяем что корзина открылась")
    default void checkCartIsOpen() {
        assertTrue(
                kraken.detect().isCartOpen(),
                "\nНе открывается корзина из списка любимых товаров\n");
    }

    @Step("Проверяем что корзина не пустая")
    default void checkCartNotEmpty() {
        assertTrue(
                kraken.detect().notEmptyCart(),
                "\nНе работает добавление товаров в корзину из карточки товара, открытой из списка любимых товаров\n");
    }

    @Step("Проверяем что корзина пустая")
    default void checkCartEmpty() {
        assertTrue(
                kraken.detect().isEmptyCart(),
                "\nКорзина не пустая\n");
    }
}
