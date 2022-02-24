package ru.instamart.reforged.business.page.b2c;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface StfShopElement {

    Header header = new Header();
    AuthModal authModal = new AuthModal();
    Address address = new Address();
    ProductCard productCard = new ProductCard();

    Button plusFirstItemToCartAddedAddress = new Button(By.xpath("//div[@data-qa='catalog_page_taxons_list_taxon_item_1_product_item_0']//button[@title='Добавить в корзину']"),
            "Кнопка добавить в корзину у первого элемента");
}
