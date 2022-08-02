package ru.instamart.reforged.stf.page.seo;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.*;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface SeoCatalogElement {

    Header header = new Header();
    Cart cart = new Cart();
    AuthModal authModal = new AuthModal();
    ProductCard productCard = new ProductCard();

    Element catalogPageTitle = new Element(By.xpath("//h1[@data-qa='category_header_title']"), "Заголовок страницы каталога");
    Element productGrid = new Element(By.xpath("//div[@data-qa='category_taxon_products_products_grid']"), "Продукты на странице каталога");
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='category_taxon_products_products_grid']/div/div[1]"), "Карточка первого товара на странице каталога");
    Element firstProductCardOnDepartment = new Element(By.xpath("//div[@data-qa='category_department_taxons_list_taxon_item_0_product_item_0']"), "Карточка первого товара на странице каталога");
    Button firstProductAddToCart = new Button(By.xpath("//div[@data-qa='category_department_taxons_list_taxon_item_0_product_item_0']//button[@title='Добавить в корзину']"), "Кнопка 'Добавить в корзину' первого товара в каталоге");
    Button firstProductRemoveFromCart = new Button(By.xpath("//div[@data-qa='category_department_taxons_list_taxon_item_0_product_item_0']//button[@title='Убрать из корзины']"), "Кнопка 'Убрать из корзины' первого товара в каталоге");
    Element spinner = new Element(By.xpath("//div[contains(@class, 'Recommendations')]//div[contains(@class, 'Spinner')]"), "Спиннер блока рекомендаций");
    ElementCollection catalogSubCategories = new ElementCollection(By.xpath("//nav[@data-qa='category_header_taxons_nav']/a"), "Подкатегории в каталоге");

    Selector selectSort = new Selector(By.xpath("//option[@value='popularity']/parent::select"), "Выпадающий список сортировки");
    Element productsStub = new Element(By.xpath("//div[@data-qa='category_taxon_products_products_grid']//ul[@aria-hidden='true']"), "Заглушка загрузки товаров");

    ElementCollection seoProductPrices = new ElementCollection(By.xpath("//span[contains(text(),'Цена за 1 шт.') or contains(text(), 'со скидкой')]/parent::div"), "Коллекция элементов цен товаров в сео каталоге");
    ElementCollection seoProductsImagesCollection = new ElementCollection(By.xpath("//div[@data-qa ='category_taxon_products_products_grid']//img"), "Коллекция картинок продуктов в сео каталоге");

    Image productImg = new Image(ByKraken.xpathExpression("//img[@src='%s']"), "Конкретное отдельное изображение на странице");

    Selector selectSortApplied = new Selector(ByKraken.xpathExpression("//option[@value='popularity']/parent::select/option[contains(text(),'%s')]"), "Селектор сортировки продуктов");
}
