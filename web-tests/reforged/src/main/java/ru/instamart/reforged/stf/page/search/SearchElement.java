package ru.instamart.reforged.stf.page.search;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Selector;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface SearchElement {

    Header header = new Header();
    Cart cart = new Cart();
    ProductCard productCard = new ProductCard();

    Element categoryTitle = new Element(ByKraken.xpath("//h1[@data-qa='category_header_title' and text()='%s']"), "заголовок найденной категории");

    ElementCollection subCategories = new ElementCollection(By.xpath("//a[contains(@class, 'SimpleTaxons')]"), "Подкатегории");
    Button firstAddToCartButton = new Button(By.xpath("//button[@title='Добавить в корзину']"), "Кнопка добавить в корзину у первого элемента на странице поиска");
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid_item_0']"), "Карточка первого товара на странице поиска");

    Selector selectSort = new Selector(By.xpath("//select[contains(@class, 'ProductsFilterSort')]"), "Селектор сортировки продуктов");
    Element searchProductGrid = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid']"), "Сетка продуктов в результатах поиска");
    Element emptySearchPlaceHolder = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid_no_products_title']"), "Заголовок пустых результатов поиска");

    Element searchProductsSkeleton = new Element(By.xpath("//div[contains(@class,'Skeleton')] "), "Заглушка продуктов в поиске");

    ElementCollection searchProductPrices = new ElementCollection(By.xpath("//div[contains(@class,'ProductCardPrice')]/div"), "Коллекция элементов цен товаров в поиске");
    Element discountFilter = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid']"), "Фильтр 'Товары со скидкой'");

}
