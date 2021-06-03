package ru.instamart.reforged.stf.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.stf.component.Button;
import ru.instamart.reforged.stf.component.Element;


public final class ProductCard implements Close {

    private final Element itemName = new Element(By.xpath("//h1[@itemprop='name']"));

    private final Element favorite = new Element(By.xpath("//div[@data-qa='addToCart_favorite']"));
    private final Element unFavorite = new Element(By.xpath("//div[@data-qa='addToCart_favorite'][contains(@class,'button--active')]"));
    private final Element favoriteHover = new Element(By.xpath("//div[@data-qa='addToCart_favorite'][contains(@class,'button--hover')]"));
    private final Element price = new Element(By.xpath("//div[contains(@class,' components')]//span"));
    private final Element stockRateDescription = new Element(By.xpath("//div[@class='stock-rate__description']"));
    private final Element stockRateItemFilled = new Element(By.xpath("//div[contains(@class, 'item--filled')]"));
    private final Button buy = new Button(By.xpath("//button[@data-qa='addToCart_buy_button']"));
    private final Button decrease = new Button(By.xpath("//button[@data-qa='addToCart_minus']"));
    private final Button increase = new Button(By.xpath("//button[@data-qa='addToCart_plus']"));
    private final Element sumCounter = new Element(By.xpath("//div[contains(@class, 'Spinner')]"));


    private final Element titleDescription = new Element(By.xpath("//div[contains(text(),'Описание')]"));
    private final Element description = new Element(By.xpath("//div[@itemprop='description']/div"));
    private final Element hideDetInfo = new Element(By.xpath("//div[contains(text(),'Скрыть подробную информацию')]"));
    private final Element showDetInfo = new Element(By.xpath("//div[contains(text(),'Показать всю информацию')]"));

    private final Element relatedProducts = new Element(By.xpath("//div[@class='widgettitle'][contains(text(),'Похожие товары')]"));
    private final Button relatedProductsNext = new Button(By.xpath("//div[@data-template-param-header-text='С этим смотрят']//button[contains(@class, 'arrow--right')]"));
    private final Button relatedProductsPrev = new Button(By.xpath("//div[@data-template-param-header-text='С этим смотрят']//button[contains(@class, 'arrow--left')]"));

    private final Element recentlyViewed = new Element(By.xpath("//div[@class='widgettitle'][contains(text(),'Вы недавно смотрели')]"));

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

    @Step("Нажать удалить из избранного")
    public void deleteFromFavorite() {
        unFavorite.click();
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
}
