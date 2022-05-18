package ru.instamart.reforged.stf.page.search;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.*;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.disclaimer.Disclaimer;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface SearchElement {

    Header header = new Header();
    Cart cart = new Cart();
    ProductCard productCard = new ProductCard();
    Disclaimer disclaimer = new Disclaimer();

    Element searchResultsTitle = new Element(ByKraken.xpathExpression("//h1[@data-qa='search_result_header_category_title'][contains(.,'%s')]"), "заголовок 'Нашлось по запросу:...'");

    ElementCollection subCategories = new ElementCollection(By.xpath("//a[contains(@class, 'SimpleTaxons')]"), "Подкатегории");
    Button firstAddToCartButton = new Button(By.xpath("//button[@title='Добавить в корзину']"), "Кнопка добавить в корзину у первого элемента на странице поиска");
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid_item_0']"), "Карточка первого товара на странице поиска");

    ElementCollection searchProductsCollection = new ElementCollection(By.xpath("//div[contains(@data-qa, 'search_result_products_products_grid_item')]"), "Коллекция продуктов в поиске");
    Element searchSpinner = new Element(By.xpath("//div[contains(@class, 'Spinner')]"), "Спиннер в поиске");
    Element infiniteSearchSpinner = new Element(By.xpath("//div[contains(@class, 'InfiniteScroll')]"), "Спиннер в бесконечном поиске");

    ElementCollection searchProductsImagesCollection = new ElementCollection(By.xpath("//div[contains(@data-qa, 'grid_item')]//img"), "Коллекция картинок продуктов в поиске");

    ElementCollection searchProductsCollectionImagesAlco = new ElementCollection(By.xpath("//div[@data-qa='search_result_products_products_grid']//img[contains(@src, 'adult-warning')]"), "Коллекция картинок-заглушек алко в поиске");

    Selector selectSort = new Selector(By.xpath("//div[@data-qa='search_result_products_filter']//select"), "Селектор сортировки продуктов");
    Selector selectSortApplied = new Selector(ByKraken.xpathExpression("//div[@data-qa='search_result_products_filter']//select/option[contains(text(),'%s')]"), "Селектор сортировки продуктов");

    Element searchProductGrid = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid']"), "Сетка продуктов в результатах поиска");
    Element emptySearchPlaceHolder = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid_no_products_title']"), "Заголовок пустых результатов поиска");

    Element searchProductsSkeleton = new Element(By.xpath("//ul[@aria-hidden='true']"), "Заглушка продуктов в поиске");

    ElementCollection searchProductPrices = new ElementCollection(By.xpath("//span[contains(text(),'Цена за 1 шт.') or contains(text(), 'со скидкой')]/parent::div"), "Коллекция элементов цен товаров в поиске");
    Element discountFilter = new Element(By.xpath("//span[contains(text(), 'Товары со скидкой')]/parent::label"), "Фильтр 'Товары со скидкой'");

    Element searchProductsQuantity = new Element(By.xpath("//div[@data-qa='category_taxon_products_filter']//h4"), "Надпись с колвом товаров в поиске");

    ElementCollection filtersCollection = new ElementCollection(By.xpath("//label[@role='option']//span"), "Коллекция элементов фильтров поиска");
    Selector filterCheckbox = new Selector(ByKraken.xpathExpression("//span[contains(text(), '%s')]/parent::label/input"), "Чекбокс фильтра");

    Element productsStub = new Element(By.xpath("//div[@data-qa='search_result_products_products_grid']//span[text()='Загрузка...']"), "Заглушка загрузки товаров");

    Image productImg = new Image(ByKraken.xpathExpression("//img[contains(@src, '%s')]"), "Конкретное отдельное изображение на странице");

    Element filterActivePin = new Element(By.xpath("//span[contains(text(), 'фильтр активен')]"), "Пин активного фильтра с текстом");
}
