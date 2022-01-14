package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.drawer.cart.container.Item;
import ru.instamart.reforged.stf.drawer.cart.container.Retailer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Корзина. Основные элементы
 */
public final class Cart implements CartCheck {

    public RetailRocket interactRetailRocket() {
        return retailRocket;
    }

    @Step("Кликаем 'Закрыть' корзину")
    public void closeCart() {
        closeButton.click();
    }

    @Step("Выбираем в корзине магазин с названием {0}")
    public Retailer getRetailerByName(final String retailerName) {
        return getAllRetailers()
                .stream()
                .filter(retailer -> retailer.getName().equals(retailerName))
                .findFirst().orElseThrow();
    }

    @Step("Получаем названия всех магазинов в корзине")
    public List<String> getAllRetailerNames() {
        return getAllRetailers()
                .stream()
                .map(Retailer::getName)
                .collect(Collectors.toList());
    }

    @Step("Получаем первый магазин в корзине")
    public Retailer getFirstRetailer() {
        return new Retailer(firstRetailer.getElement());
    }

    @Step("Получаем первый товар в корзине")
    public Item getFirstItem() {
        return new Item(firstItem.getElement());
    }

    @Step("Кликаем 'Сделать' заказ")
    public void submitOrder() {
        submitOrder.click();
    }

    @Step("Получаем значение стоимости товаров в корзине")
    public double returnOrderAmount() {
        return StringUtil.stringToDoubleParse(orderAmount.getText());
    }

    @Step("Увеличиваем кол-во товара до тех пор, пока заказ не станет доступен")
    public void increaseFirstItemCountToMin() {
        Item item = getFirstItem();
        while (!checkOrderButtonIsEnabled()) {
            item.increaseCount();
            item.checkSpinnerIsNotVisible();
        }
    }

    private List<Retailer> getAllRetailers() {
        return retailers.getElements()
                .stream()
                .map(Retailer::new)
                .collect(Collectors.toList());
    }
}
