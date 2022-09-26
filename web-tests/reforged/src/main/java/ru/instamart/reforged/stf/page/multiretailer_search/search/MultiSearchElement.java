package ru.instamart.reforged.stf.page.multiretailer_search.search;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.header_multisearch.MultisearchHeader;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface MultiSearchElement {

    MultisearchHeader header = new MultisearchHeader();
    Cart cart = new Cart();
    ProductCard productCard = new ProductCard();

    ElementCollection retailerCardsList = new ElementCollection(By.xpath("//div[contains(@class,'StoresList_root')]//li"), "Карточки ритейлеров");
    Element retailerCardByName = new Element(ByKraken.xpathExpression("//div[contains(@class,'StoresList_root')]//li[.//img[@alt='%s']]"), "Карточка ритейлера по имени");

    ElementCollection categoriesFilters = new ElementCollection(By.xpath("//button[contains(@class,'MultiSearchFacets_button')]"), "Кнопки фильтров по категориям");

    ElementCollection productsSnippets = new ElementCollection(By.xpath("//ul[contains(@class,'MultiSearchProductsGrid_grid')]/li"), "Сниппеты найденных продуктов");
    ElementCollection productsTitles = new ElementCollection(By.xpath("//ul[contains(@class,'MultiSearchProductsGrid_grid')]/li//h3"), "Названия продуктов");
    ElementCollection productsLinks = new ElementCollection(By.xpath(""), "Ссылки на продукты");
    ElementCollection productAddToFavouriteButtons = new ElementCollection(By.xpath("//ul[contains(@class,'MultiSearchProductsGrid_grid')]/li//button[@title='Добавить в избранное']"), "Кнопки 'Добавить в избранное'");
    ElementCollection productsAddToCartButtons = new ElementCollection(By.xpath("//ul[contains(@class,'MultiSearchProductsGrid_grid')]/li//button[@title='Добавить в корзину']"), "Кнопки 'Добавить в корзину'");

}
