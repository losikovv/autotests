package ru.instamart.reforged.stf.page.seo;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface SeoCatalogElement {

    Header header = new Header();
    AuthModal authModal = new AuthModal();
    ProductCard productCard = new ProductCard();

    Element productGrid = new Element(By.xpath("//div[@data-qa='category_taxon_products_products_grid']"));
    Element firstProductCard = new Element(By.xpath("//div[@data-qa='category_taxon_products_products_grid_item_0']"));
}
