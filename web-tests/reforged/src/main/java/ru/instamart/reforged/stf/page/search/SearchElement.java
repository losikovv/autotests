package ru.instamart.reforged.stf.page.search;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface SearchElement {
    Header header = new Header();
    ProductCard productCard = new ProductCard();

    Button firstAddToCartButton = new Button(By.xpath("//button[@title='Добавить в корзину']"), "Кнопка добавить в корзину у первого элемента на странице поиска");
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid_item_0']"), "Карточка первого товара на странице поиска");

    Element searchProductGrid = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid']"), "Сетка продуктов в результатах поиска");
    Element emptySearchPlaceHolder = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid_no_products_title']"), "Заголовок пустых результатов поиска");
}
