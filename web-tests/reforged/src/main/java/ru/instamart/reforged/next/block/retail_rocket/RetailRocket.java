package ru.instamart.reforged.next.block.retail_rocket;

import io.qameta.allure.Step;

public final class RetailRocket implements RetailRocketCheck {

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
