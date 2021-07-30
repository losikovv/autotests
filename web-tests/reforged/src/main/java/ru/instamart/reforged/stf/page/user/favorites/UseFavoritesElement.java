package ru.instamart.reforged.stf.page.user.favorites;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.block.header.AuthoredHeader;
import ru.instamart.reforged.stf.frame.ProductCard;

public interface UseFavoritesElement {

    AuthoredHeader header = new AuthoredHeader();
    ProductCard productCart = new ProductCard();

    Element emptyFavorites = new Element(By.className("empty-favorites"));
    ElementCollection favoriteButton = new ElementCollection(By.xpath("//div[@data-qa='addToCart_favorite']"));
    ElementCollection allFavorites = new ElementCollection(By.xpath("//a[contains(@class, 'favorites__')]"));
    ElementCollection addToCart = new ElementCollection(By.xpath("//div[@class='cart-actions']"));
    Button showMore = new Button(By.className("load-more-products"));

    Element activeElementFilter = new Element(By.xpath("//a[contains(@class,'favorite-list-filter__link--active')]"));
    Link allGoods = new Link(By.xpath("//a[@data-content='Все товары']"));
    Link inStock = new Link(By.xpath("//a[@data-content='В наличии']"));
    Link outOfStock = new Link(By.xpath("//a[@data-content='Нет в наличии']"));
}
