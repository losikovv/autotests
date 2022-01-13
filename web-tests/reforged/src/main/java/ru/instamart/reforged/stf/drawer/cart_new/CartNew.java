package ru.instamart.reforged.stf.drawer.cart_new;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.drawer.cart_new.container.Retailer;
import ru.instamart.reforged.stf.frame.ClearCartModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Корзина. Основные элементы
 */
public final class CartNew implements CartNewCheck {

    public ClearCartModal interactClearCart() {
        return clearCartModal;
    }

    public ProductCard interactProductCart() {
        return productCard;
    }

    @Step("Закрыть корзину")
    public void closeCart() {
        closeButton.click();
    }

    @Step("Получить контейнер ретейлера {0}")
    public Retailer getRetailerByName(final String retailerName) {
        return getAllRetailers()
                .stream()
                .filter(retailer -> retailer.getName().equals(retailerName))
                .findFirst().orElseThrow();
    }

    public List<String> getAllRetailerNames() {
        return getAllRetailers()
                .stream()
                .map(Retailer::getName)
                .collect(Collectors.toList());
    }

    private List<Retailer> getAllRetailers() {
        return retailers.getElements()
                .stream()
                .map(Retailer::new)
                .collect(Collectors.toList());
    }
}
