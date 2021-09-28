package ru.instamart.reforged.stf.page.shop;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.drawer.CategoryMenu;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;

public interface ShopElement {

    Header header = new Header();
    RetailRocket retailRocket = new RetailRocket();
    ProductCard productCard = new ProductCard();
    Cart cart = new Cart();
    Address address = new Address();
    CategoryMenu categoryMenu = new CategoryMenu();
    Footer footer = new Footer();
    AuthModal authModal = new AuthModal();
    HelpDesk helpDesk = new HelpDesk();

    Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    Element firstProductCardProd = new Element(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_0_product_item_0']"));
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_1_product_item_0']"));
    Element addFirstItemToFavorite = new Element(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_1_product_item_0']/button"));
    Element deleteFirstItemFromFavorite = new Element(By.xpath("//div[contains(@class, 'favorite-button--active')]"));
    Button plusFirstItemToCartAddedAddress = new Button(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_1_product_item_0']//button[@title='Добавить в корзину']"),
            "Кнопка добавить в корзину у первого элемента");
    Button minusFirstItemFromCartAddedAddress = new Button(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_1_product_item_0']//button[contains(@title, 'Убрать из корзины')]"),
            "Кнопка убрать из корзины у первого элемента");
    Element spinner = new Element(By.xpath("//div[contains(@class, 'Recommendations')]//div[contains(@class, 'Spinner')]"), "Спиннер блока рекомендаций на главной");

}
