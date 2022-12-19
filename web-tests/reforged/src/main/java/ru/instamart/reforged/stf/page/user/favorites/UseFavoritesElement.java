package ru.instamart.reforged.stf.page.user.favorites;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface UseFavoritesElement {

    Header header = new Header();
    ProductCard productCart = new ProductCard();

    Element emptyFavorites = new Element(By.xpath("//section[contains(@class,'FavoritesNoItemsLayout_root')]"), "плейсхолдер пустого списка");
    //TODO Три дня подряд правлю туда-сюда этот локатор. Что-то подсказывает, что дело не в локаторе. Нужно изучить вопрос. Может на проде пользователь из АБ перестал исключаться, тогда почему это только в Избранном вылезает?
    Element emptyFavoritesProd = new Element(By.xpath("//section[@data-qa='favorites_with_shipments_favorites_tab_no_products']"), "Сообщение пустого списка избранного на проде");
    ElementCollection favoriteButton = new ElementCollection(By.xpath("//div[contains(@class,'FavoriteProducts')]/div/button[@title='Добавить в избранное']"),
            "список всех кнопок для добавления в избранное");
    ElementCollection allFavorites = new ElementCollection(By.xpath("//div[contains(@class,'FavoriteProducts')]/div//a"), "список всех избранных");
    ElementCollection addToCart = new ElementCollection(By.xpath("//div[contains(@class,'FavoriteProducts')]/div//button[@title='Добавить в корзину']"),
            "список всех кнопок добавления в корзину");
    Element spinner = new Element(By.xpath("//div[contains(@class,'Spinner')]"), "Спиннер");

}
