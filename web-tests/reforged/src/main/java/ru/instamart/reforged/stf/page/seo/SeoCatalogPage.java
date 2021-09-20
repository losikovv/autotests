package ru.instamart.reforged.stf.page.seo;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

public final class SeoCatalogPage implements StfPage, SeoCatalogCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Header interactHeader() {
        return header;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    @Step("Открыть карточку товара")
    public void openFirstProductCard() {
        firstProductCard.click();
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + shop.getUrl() + pageUrl());
    }

    @Override
    public String pageUrl() {
        return "/c/new-bakalieia/krupy/griechnievaia";
    }
}
