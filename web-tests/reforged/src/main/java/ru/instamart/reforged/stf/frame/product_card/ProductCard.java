package ru.instamart.reforged.stf.frame.product_card;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;

public final class ProductCard implements ProductCardCheck {

    public RetailRocket interactRetailRocket() {
        return retailRocket;
    }

    @Step("Нажать 'Закрыть'")
    public void clickOnClose() {
        close.click();
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

    @Step("Получаем цену товара ")
    public String getPrice() {
        return price.getText();
    }

    @Step("Получаем цену товара со скидкой (для товара со скидкой)")
    public String getPriceWithDiscount() {
        return priceWithDiscount.getText();
    }

    @Step("Получаем цену первого товара из рекомендаций")
    public String getFirstProductNameFromRecs() {
        return productRecsNames.getElementText(0);
    }

    @Step("Нажимаем на кнопку 'Добавить в корзину' у первого товара рекомендаций")
    public void plusFirstProductToCart() {
        plusFirstItemToCartButtons.clickOnFirst();
    }

    @Step("Получаем ссылку на продукт")
    public String getProductPermalink() {
        return StringUtil.getLastCatalogFromPath(Kraken.currentUrl());
    }
}
