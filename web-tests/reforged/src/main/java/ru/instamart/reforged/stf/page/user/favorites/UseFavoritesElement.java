package ru.instamart.reforged.stf.page.user.favorites;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface UseFavoritesElement {

    Header header = new Header();
    ProductCard productCart = new ProductCard();

    Element emptyFavorites = new Element(By.xpath("//div[contains(@class,'FavoritesAllEmpty_textContent')]"), "плейсхолдер пустого списка");
    ElementCollection favoriteButton = new ElementCollection(By.xpath("//div[contains(@class,'FavoriteProducts')]/div/button[@title='Добавить в избранное']"),
            "список всех кнопок для добавления в избранное");
    ElementCollection allFavorites = new ElementCollection(By.xpath("//div[contains(@class,'FavoriteProducts')]/div//a"), "список всех избранных");
    ElementCollection addToCart = new ElementCollection(By.xpath("//div[contains(@class,'FavoriteProducts')]/div//button[@title='Добавить в корзину']"),
            "список всех кнопок добавления в корзину");
    Element spinner = new Element(By.xpath("//div[contains(@class,'Spinner')]"), "Спиннер");

}
