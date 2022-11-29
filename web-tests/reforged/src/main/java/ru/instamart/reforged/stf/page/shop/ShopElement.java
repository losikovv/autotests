package ru.instamart.reforged.stf.page.shop;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.drawer.category_menu.CategoryMenu;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.address.AddressLarge;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.disclaimer.Disclaimer;
import ru.instamart.reforged.stf.frame.order_evaluation_modal.OrderEvaluationModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.frame.store_modal.StoreModal;

public interface ShopElement {

    Header header = new Header();
    StoreModal storeModal = new StoreModal();
    RetailRocket retailRocket = new RetailRocket();
    ProductCard productCard = new ProductCard();
    Cart cart = new Cart();
    AddressLarge addressLarge = new AddressLarge();
    Address address = new Address();
    CategoryMenu categoryMenu = new CategoryMenu();
    Footer footer = new Footer();
    AuthModal authModal = new AuthModal();
    HelpDesk helpDesk = new HelpDesk();
    OrderEvaluationModal orderEvaluationModal = new OrderEvaluationModal();
    Disclaimer disclaimer = new Disclaimer();

    Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"), "empty");
    Element firstProductCardProd = new Element(By.xpath("//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[1]"), "Карточка первого товара в каталоге на проде");
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_1_product_item_0']"), "Карточка первого товара в каталоге на стейдже");
    ElementCollection addToFavoriteButtons = new ElementCollection(By.xpath("//button[@title='Добавить в избранное']"), "Кнопки 'Добавить в избранное' товаров");
    ElementCollection removeFromFavorite = new ElementCollection(By.xpath("//button[contains(@class,'ProductCardFavoriteButton_styles_active')]"), "Кнопка 'Добавить в избранное' избранного товара");
    Button plusItemToCart = new Button(ByKraken.xpathExpression("//div[@data-qa='catalog_page_taxons_list_taxon_item_%s_product_item_%s']//button[@title='Добавить в корзину']"),
            "Кнопка добавить в корзину у элемента");
    ElementCollection productsCard = new ElementCollection(ByKraken.xpathExpression("//section[@data-qa='catalog_page_taxons_list_taxon_item_%s']//li"),
            "Карточки продукта");
    ElementCollection productsCardTitlesFromFirstCategory = new ElementCollection(ByKraken.xpathExpression("//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li//h3"),
            "Названия продуктов на карточках");
    ElementCollection addToCartButtonsProd = new ElementCollection(By.xpath("//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li//button[@title='Добавить в корзину']"),
            "Кнопка добавить в корзину у элемента");
    Button minusItemFromCart = new Button(ByKraken.xpathExpression("//div[@data-qa='catalog_page_taxons_list_taxon_item_%s_product_item_%s']//button[contains(@title, 'Убрать из корзины')]"),
            "Кнопка убрать из корзины у элемента");
    Element productTitle = new Element(ByKraken.xpathExpression("//div[@data-qa='catalog_page_taxons_list_taxon_item_%s_product_item_%s']//h3"), "Имя первого товара в каталоге на стейдже");

    Element imageInFirstItem = new Element(By.xpath("//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[contains(@class,'Carousel_slide')]//img"), "Изображение товара");
    Element nameInFirstItem = new Element(By.xpath("//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[contains(@class,'Carousel_slide')]//h3"), "Название товара");
    Element packageSizeInFirstItem = new Element(By.xpath("//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[contains(@class,'Carousel_slide')]//h3/following-sibling::div[1]"), "Размер упаковки товара");
    ElementCollection pricesInItemWithoutDiscount = new ElementCollection(By.xpath("(//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[contains(@class,'Carousel_slide')][not(.//li[contains(@class,'ProductCardBadgeGroup')])])[1]//div[contains(@class,'ProductCardPrice_styles_root')]/div"), "Отображаемые цены (для первого товара без скидки)");
    ElementCollection pricesInItemWithDiscount = new ElementCollection(By.xpath("(//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[contains(@class,'Carousel_slide')][.//li[contains(@class,'ProductCardBadgeGroup')]])[1]//div[contains(@class,'ProductCardPrice_styles_root')]/div"), "Отображаемые цены (для первого товара со скидкой)");
    Element priceInFirstItemWithoutDiscount = new Element(By.xpath("(//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[contains(@class,'Carousel_slide')][not(.//li[contains(@class,'ProductCardBadgeGroup')])])[1]//div[./span[contains(.,'Цена за')]]"), "Цена товара (для первого товара без скидки)");
    Element fullPriceInFirstItemWithDiscount = new Element(By.xpath("(//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[contains(@class,'Carousel_slide')][.//li[contains(@class,'ProductCardBadgeGroup')]])[1]//div[./span[contains(.,'Цена без скидки')]]"), "Цена товара без скидки (для первого товара со скидкой)");
    Element discountPriceInFirstItemWithDiscount = new Element(By.xpath("(//section[@data-qa='catalog_page_taxons_list_taxon_item_0']//li[contains(@class,'Carousel_slide')][.//li[contains(@class,'ProductCardBadgeGroup')]])[1]//div[./span[contains(.,'Цена со скидкой')]]"), "Цена товара со скидкой (для первого товара со скидкой)");

    Link firstProductsCategoryTitle = new Link(By.xpath("//a[@data-qa='catalog_page_taxons_list_taxon_item_0_title_link']"), "Заголовок первой категории товаров");
    ElementCollection firstCategoryProductNames = new ElementCollection(By.xpath("//div[contains(@data-qa,'catalog_page_taxons_list_taxon_item_0_product_item_')]//h3"), "Названия продуктов первой категории в списке");
    Link youBoughtBeforeCategory = new Link(By.xpath("//a[contains(@data-qa, '_title_link')][contains(.,'Вы покупали ранее')]"), "Категория 'Вы покупали ранее'");
}
