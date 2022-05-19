package ru.instamart.reforged.next.page.user.favorites;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.next.block.header.Header;
import ru.instamart.reforged.next.frame.product_card.ProductCard;

public interface UseFavoritesElement {

    Header header = new Header();
    ProductCard productCart = new ProductCard();

    Element emptyFavorites = new Element(By.xpath("//div[@class='empty-favorites']"), "плейсхолдер пустого списка");
    ElementCollection favoriteButton = new ElementCollection(By.xpath("//div[@data-qa='addToCart_favorite']"),
            "список всех кнопок для добавления в избранное");
    ElementCollection allFavorites = new ElementCollection(By.xpath("//div[@class='favorites-list']/a"), "список всех избранных");
    ElementCollection addToCart = new ElementCollection(By.xpath("//div[@class='cart-actions']/button[not(contains(@class,'cart-actions__btn--left'))]"),
            "список всех кнопок добавления в корзину");
    Button showMore = new Button(By.xpath("//button[@class='load-more-products']"), "кнопка Показать еще");

    Element activeElementFilter = new Element(By.xpath("//a[contains(@class,'favorite-list-filter__link--active')]"), "признак активного фильтра");
    Link allGoods = new Link(By.xpath("//a[@data-content='Все товары']"), "кнопка Все товары");
    Link inStock = new Link(By.xpath("//a[@data-content='В наличии']"), "кнопка В наличии");
    Link outOfStock = new Link(By.xpath("//a[@data-content='Нет в наличии']"), "кнопка Нет в наличии");
}
