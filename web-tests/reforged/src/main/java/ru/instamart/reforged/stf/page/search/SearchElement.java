package ru.instamart.reforged.stf.page.search;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Selector;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.disclaimer.Disclaimer;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface SearchElement {

    Header header = new Header();
    Cart cart = new Cart();
    ProductCard productCard = new ProductCard();
    Disclaimer disclaimer = new Disclaimer();

    Element categoryTitle = new Element(ByKraken.xpath("//h1[@data-qa='category_header_title' and text()='%s']"), "заголовок найденной категории");

    ElementCollection subCategories = new ElementCollection(By.xpath("//a[contains(@class, 'SimpleTaxons')]"), "Подкатегории");
    Button firstAddToCartButton = new Button(By.xpath("//button[@title='Добавить в корзину']"), "Кнопка добавить в корзину у первого элемента на странице поиска");
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid_item_0']"), "Карточка первого товара на странице поиска");

    ElementCollection searchProductsCollection = new ElementCollection(By.xpath("//div[contains(@data-qa, 'search_result_products_products_grid_item')]"), "Коллекция продуктов в поиске");
    Element searchSpinner = new Element(By.xpath("//div[contains(@class, 'Spinner')]"), "Спиннер в поиске");
    Element infiniteSearchSpinner = new Element(By.xpath("//div[contains(@class, 'InfiniteScroll')]"), "Спиннер в бесконечном поиске");

    ElementCollection searchProductsImagesCollection = new ElementCollection(By.xpath("//div[contains(@data-qa, 'search_result_products_products_grid_item')]//img"), "Коллекция картинок продуктов в поиске");

    ElementCollection searchProductsCollectionImagesAlco = new ElementCollection(By.xpath("//div[@data-qa='search_result_products_products_grid']//img[contains(@src, 'adult-warning')]"), "Коллекция картинок-заглушек алко в поиске");

    Selector selectSort = new Selector(By.xpath("//select[contains(@class, 'ProductsFilterSort')]"), "Селектор сортировки продуктов");
    Selector selectSortApplied = new Selector(ByKraken.xpath("//select[contains(@class, 'ProductsFilterSort')]/option[contains(text(),'%s')]"), "Селектор сортировки продуктов");

    Element searchProductGrid = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid']"), "Сетка продуктов в результатах поиска");
    Element emptySearchPlaceHolder = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid_no_products_title']"), "Заголовок пустых результатов поиска");

    Element searchProductsSkeleton = new Element(By.xpath("//div[contains(@class,'Skeleton')] "), "Заглушка продуктов в поиске");

    ElementCollection searchProductPrices = new ElementCollection(By.xpath("//span[contains(text(),'Цена за 1 шт.') or contains(text(), 'со скидкой')]/parent::div[contains(@class, 'ProductCardPrice')]"), "Коллекция элементов цен товаров в поиске");
    Element discountFilter = new Element(By.xpath("//span[contains(text(), 'Товары со скидкой')]/parent::label"), "Фильтр 'Товары со скидкой'");

    Element searchProductsQuantity = new Element(By.xpath("//h4[contains(@class, 'Filter')]"), "Надпись с колвом товаров в поиске");

    ElementCollection filtersCollection = new ElementCollection(By.xpath("//div[contains(@class, 'ProductsFilter')]//label[contains(@class,'FlatSelectSearch')]"), "Коллекция элементов фильтров поиска");
    Selector filterCheckbox = new Selector(ByKraken.xpath("//span[contains(text(), '%s')]/parent::label/input"), "Чекбокс фильтра");

    Element productsStub = new Element(By.xpath("//div[contains(@class, 'AnimatedLinearGradient')]"), "Заглушка загрузки товаров");

    Element productImg = new Element(ByKraken.xpath("//img[contains(@src, '%s')]"), "Конкретное отдельное изображение на странице");

}
