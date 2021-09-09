package ru.instamart.reforged.stf.block.retail_rocket;

import io.qameta.allure.Step;

public final class RetailRocket implements RetailRocketCheck {

    @Step("Открыть карточку первого товар из карусели")
    public void clickToFirstProductInCarousel() {
        items.clickOnFirst();
    }

    @Step("Добавить первый товар из карусели")
    public void addToCartFirstProductInCarousel() {
        addItemButtons.clickOnFirst();
    }
}
