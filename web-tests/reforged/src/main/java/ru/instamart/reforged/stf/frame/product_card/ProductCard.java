package ru.instamart.reforged.stf.frame.product_card;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.frame.Close;

import static ru.instamart.reforged.core.Kraken.waitAction;

public final class ProductCard implements ProductCardCheck, Close {

    public RetailRocket interactRetailRocket() {
        return  retailRocket;
    }

    @Step("Нажать купить")
    public void clickOnBuy() {
        buy.click();
    }

    @Step("Нажать увеличить количество товара")
    public void increaseItemCount() {
        increase.click();
    }

    @Step("Нажать уменьшить количество товара")
    public void decreaseItemCount() {
        decrease.click();
    }

    @Step("Нажать товар в избранное")
    public void addToFavorite() {
        favorite.click();
    }

    @Step("Нажать товар в избранное для закрытия дисклеймера")
    public void addToFavoriteToCloseDisclaimer() {
        favorite.hoverAndClick();
    }

    @Step("Нажать скрыть подробную информацию")
    public void hideDetailedInfo() {
        hideDetInfo.click();
    }

    @Step("Нажать показать всю информацию")
    public void showDetailedInfo() {
        showDetInfo.click();
    }

    @Step("Получить количество товара")
    public void getStockRate() {
        stockRateDescription.getText();
    }

    @Step("Нажать следующий слайд в блоке С этим смотрят")
    public void clickOnNextSlide() {
        relatedProductsNext.click();
    }

    @Step("Нажать предыдущий слайд в блоке С этим смотрят")
    public void clickOnPrevSlide() {
        relatedProductsPrev.click();
    }

    @Step("Нажать зарезервировать")
    public void clickOnReserve() {
        reserveButton.click();
    }
}
