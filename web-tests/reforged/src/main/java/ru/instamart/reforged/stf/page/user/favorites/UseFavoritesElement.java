package ru.instamart.reforged.stf.page.user.favorites;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface UseFavoritesElement {

    Header header = new Header();
    ProductCard productCart = new ProductCard();

    Element emptyFavorites = new Element(By.xpath("//div[@data-qa='favorites_no_products_title']"), "плейсхолдер пустого списка");
    ElementCollection favoriteButton = new ElementCollection(By.xpath("//ul[contains(@class,'FavoriteProducts')]/li//button[@title='Добавить в избранное']"),
            "список всех кнопок для добавления в избранное");
    ElementCollection allFavorites = new ElementCollection(By.xpath("//ul[contains(@class,'FavoriteProducts')]/li//a"), "список всех избранных");
    ElementCollection addToCart = new ElementCollection(By.xpath("//ul[contains(@class,'FavoriteProducts')]/li//button[@title='Добавить в корзину']"),
            "список всех кнопок добавления в корзину");

    Element activeElementFilter = new Element(By.xpath("//a[contains(@class,'favorite-list-filter__link--active')]"), "признак активного фильтра");
    Link allGoods = new Link(By.xpath("//a[@data-content='Все товары']"), "кнопка Все товары");
    Link inStock = new Link(By.xpath("//a[@data-content='В наличии']"), "кнопка В наличии");
    Link outOfStock = new Link(By.xpath("//a[@data-content='Нет в наличии']"), "кнопка Нет в наличии");
}
