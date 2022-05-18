package ru.instamart.reforged.next.page.seo_incognito;

import io.qameta.allure.Step;
import ru.instamart.reforged.next.block.header.Header;
import ru.instamart.reforged.next.page.StfPage;

/**
 * Страница каталога SEO. Доступна под чистой сессией (или в режиме инкогнито) по ссылке вида https://stf-kraken.k-stage.sbermarket.tech/categories/new-ovoshchi-i-frukty/frukty
 * При авторизованном пользователе, а также при выбранном магазине редиректит в "обычный" каталог.
 */
public final class SeoIncognitoPage implements StfPage, SeoIncognitoCheck {

    public Header interactHeader() {
        return header;
    }

    @Step("Наводим курсор мыши на первый продукт в списке")
    public void hoverFirstProduct() {
        firstProduct.getActions().mouseOver();
    }

    @Step("Нажимаем на кнопку 'Добавить в корзину'")
    public void clickAddToCartButton() {
        addFirstProductToCart.click();
    }

    @Override
    public String pageUrl() {
        return "categories/new-ovoshchi-i-frukty/frukty";
    }
}
