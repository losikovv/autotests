package ru.instamart.reforged.stf.page.shop;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.block.header.AuthoredHeader;
import ru.instamart.reforged.stf.component.user.User;
import ru.instamart.reforged.stf.drawer.CategoryMenu;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.ProductCard;

public interface ShopElement {

    AuthoredHeader header = new AuthoredHeader();
    ProductCard productCard = new ProductCard();
    Cart cart = new Cart();
    Address address = new Address();
    CategoryMenu categoryMenu = new CategoryMenu();
    User user = new User();

    Button openAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    Element firstProductCard = new Element(By.xpath("//li[@class='product']"));
    Element plusFirstItemToCart = new Element(By.xpath("//div[contains(@class, 'add-cart__up')]"));
    Element minusFirstItemFromCart = new Element(By.xpath("//div[contains(@class, 'add-cart__down')]"));
    Element addFirstItemToFavorite = new Element(By.xpath("//div[contains(@class, 'favorite-button-default')]"));
    Element deleteFirstItemFromFavorite = new Element(By.xpath("//div[contains(@class, 'favorite-button--active')]"));
}
