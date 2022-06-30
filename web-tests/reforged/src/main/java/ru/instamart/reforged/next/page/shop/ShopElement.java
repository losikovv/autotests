package ru.instamart.reforged.next.page.shop;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.next.block.footer.Footer;
import ru.instamart.reforged.next.block.header.Header;
import ru.instamart.reforged.next.block.helpdesk.HelpDesk;
import ru.instamart.reforged.next.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.next.drawer.cart.Cart;
import ru.instamart.reforged.next.drawer.category_menu.CategoryMenu;
import ru.instamart.reforged.next.frame.address.Address;
import ru.instamart.reforged.next.frame.address.AddressLarge;
import ru.instamart.reforged.next.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.next.frame.order_evaluation_modal.OrderEvaluationModal;
import ru.instamart.reforged.next.frame.product_card.ProductCard;
import ru.instamart.reforged.next.frame.store_modal.StoreModal;

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

    Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"), "empty");
    Element firstProductCardProd = new Element(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_0_product_item_0']"), "Карточка первого товара в каталоге на проде");
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_1_product_item_0']"), "Карточка первого товара в каталоге на стейдже");
    Element addFirstItemToFavorite = new Element(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_1_product_item_0']/button"), "Кнопка 'Добавить в избранное' у первого товара");
    Element deleteFirstItemFromFavorite = new Element(By.xpath("//div[contains(@class, 'favorite-button--active')]"), "empty");
    Button plusItemToCart = new Button(ByKraken.xpathExpression("//div[@data-qa='catalog_page_taxons_list_taxon_item_%s_product_item_%s']//button[@title='Добавить в корзину']"),
            "Кнопка добавить в корзину у элемента");
    Button minusItemFromCart = new Button(ByKraken.xpathExpression("//div[@data-qa='catalog_page_taxons_list_taxon_item_%s_product_item_%s']//button[contains(@title, 'Убрать из корзины')]"),
            "Кнопка убрать из корзины у элемента");
    Element productTitle = new Element(ByKraken.xpathExpression("//div[@data-qa='catalog_page_taxons_list_taxon_item_%s_product_item_%s']//h3"), "Имя первого товара в каталоге на стейдже");

    Element imageInFirstItem = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')]//img"), "Изображение товара");
    Element nameInFirstItem = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')]//h3"), "Название товара");
    Element packageSizeInFirstItem = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')]//h3/following-sibling::div[1]"), "Размер упаковки товара");
    ElementCollection pricesInItemWithoutDiscount = new ElementCollection(By.xpath("(//div[contains(@data-qa,'_product_item_')][not(.//li[.//span[contains(.,'Скидка')]])])[1]//h3/following-sibling::div[2]/div"), "Отображаемые цены (для первого товара без скидки)");
    ElementCollection pricesInItemWithDiscount = new ElementCollection(By.xpath("(//div[contains(@data-qa,'_product_item_')][.//li[.//span[contains(.,'Скидка')]]])[1]//h3/following-sibling::div[2]/div[./span]"), "Отображаемые цены (для первого товара со скидкой)");
    Element priceInFirstItemWithoutDiscount = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')][not(.//li[.//span[contains(.,'Скидка')]])]//div[./span[contains(.,'Цена за')]]"), "Цена товара (для первого товара без скидки)");
    Element fullPriceInFirstItemWithDiscount = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')][.//li[.//span[contains(.,'Скидка')]]]//h3/following-sibling::div[2]/div[contains(.,'Цена без скидки')]"), "Цена товара без скидки (для первого товара со скидкой)");
    Element discountPriceInFirstItemWithDiscount = new Element(By.xpath("//div[contains(@data-qa,'_product_item_')][.//li[.//span[contains(.,'Скидка')]]]//h3/following-sibling::div[2]/div[contains(.,'Цена со скидкой')]"), "Цена товара со скидкой (для первого товара со скидкой)");

    Link firstProductsCategoryTitle = new Link(By.xpath("//a[@data-qa='catalog_page_taxons_list_taxon_item_0_title_link']"), "Заголовок первой категории товаров");
    ElementCollection firstCategoryProductNames = new ElementCollection(By.xpath("//div[contains(@data-qa,'catalog_page_taxons_list_taxon_item_0_product_item_')]//h3"), "Названия продуктов первой категории в списке");
    Link youBoughtBeforeCategory = new Link(By.xpath("//a[contains(@data-qa, '_title_link')][contains(.,'Вы покупали ранее')]"), "Категория 'Вы покупали ранее'");
}
