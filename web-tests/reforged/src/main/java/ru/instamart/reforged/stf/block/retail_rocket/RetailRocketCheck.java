package ru.instamart.reforged.stf.block.retail_rocket;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailRocketCheck extends Check, RetailRocketElement {

    @Step("Проверяем, что блок рекомендаций '{blockName}' отображается")
    default void checkRecommendationsBlockVisible(final String blockName) {
        waitAction().shouldBeVisible(recommendationCarouselByName, blockName);
    }

    @Step("Наличие блока 'Популярные товары'")
    default void checkBlockPopular() {
        waitAction().shouldBeVisible(carouselPopular);
    }

    @Step("Наличие блока 'Добавить к заказу?'")
    default void checkBlockAddToCart() {
        waitAction().shouldBeVisible(carousel);
    }

    @Step("Наличие блока 'Похожие'")
    default void checkBlockSimilar() {
        waitAction().shouldBeVisible(carouselSimilar);
    }

    @Step("Наличие блока 'С этим товаром смотрят'")
    default void checkBlockWithThisProduct() {
        waitAction().shouldBeVisible(carousel);
    }
}
