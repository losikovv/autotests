package ru.instamart.reforged.stf.block.retail_rocket;

import io.qameta.allure.Step;

import java.util.Objects;

public final class RetailRocket implements RetailRocketCheck {

    @Step("Получаем название первого товара блока рекоммендаций '{blockName}'")
    public String getFirstItemTitleInBlockByName(final String blockName) {
        //Если название больше 56 символов, появляется aria-label в котором содержится полное название
        final var firstElement = itemNamesRecommendationCarouselByName.getElements(blockName)
                .stream().findFirst().orElseThrow();
        final var label = firstElement.getAttribute("aria-label");
        if (Objects.isNull(label)) {
            return firstElement.getText();
        }
        return label;
    }

    @Step("Открываем карточку первого товара блока рекоммендаций '{blockName}'")
    public void openProductCardFirstItemInBlockByName(final String blockName) {
        itemsRecommendationCarouselByName.clickOnFirst(blockName);
    }

    @Step("Нажимаем кнопку 'Добавить в корзину' первого товара блока рекоммендаций '{blockName}'")
    public void addToCartFirstItemInBlockByName(final String blockName) {
        addToCartItemsRecommendationBlockByName.clickOnFirst(blockName);
    }

    @Step("Открыть карточку первого товар из карусели 'Популярные товары'")
    public void clickToFirstProductInPopular() {
        itemsPopular.clickOnFirst();
    }

    @Step("Добавить первый товар из карусели 'Популярные товары'")
    public void addToCartFirstProductInPopular() {
        addItemButtonsPopular.clickOnFirst();
    }

    @Step("Открыть карточку первого товар из карусели")
    public void clickToFirstProductInCarousel() {
        items.clickOnFirst();
    }

    @Step("Открыть карточку первого товар из карусели 'Похожие'")
    public void clickToFirstProductInSimilar() {
        itemsSimilar.clickOnFirst();
    }

    @Step("Добавить первый товар из карусели")
    public void addToCartFirstProductInCarousel() {
        addItemButtons.clickOnFirst();
    }

    @Step("Добавить первый товар из карусели 'Похожие'")
    public void addToCartFirstProductInSimilar() {
        addItemButtonsSimilar.clickOnFirst();
    }
}
