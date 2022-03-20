package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.custom_exceptions.NoSuchElementInCollection;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.drawer.cart.container.Item;
import ru.instamart.reforged.stf.drawer.cart.container.Retailer;
import ru.instamart.reforged.stf.frame.ClearCartModal;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Корзина. Основные элементы
 */
public final class Cart implements CartCheck {

    public RetailRocket interactRetailRocket() {
        return retailRocket;
    }

    public ClearCartModal interactCartModal() {
        return clearCartModal;
    }

    @Step("Кликаем 'Закрыть' корзину")
    public void closeCart() {
        closeButton.click();
    }

    @Step("Получаем {0} по порядку магазин корзины")
    public Retailer getRetailerByOrder(final int retailerOrder) {
        final var index = retailerOrder - 1;
        final List<Retailer> retailers = getAllRetailers();
        if (retailers.size() >= index) {
            return retailers.get(index);
        } else {
            var exception = new NoSuchElementInCollection(index);
            Assert.fail(exception.getMessage(), exception);
            return new Retailer(null);
        }
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
    public double getOrderAmount() {
        return StringUtil.stringToDouble(orderAmount.getText());
    }

    @Step("Получаем значение количества позиций в корзине")
    public int getPositionsCount() {
        return StringUtil.extractNumberFromString(positionsCount.getText());
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

    @Step("Получаем количество ритейлеров в корзине")
    public int getRetailersCount() {
        return retailers.elementCount();
    }


    @Step("Кликаем на кнопку 'Посмотреть' в сообщении об успешном переносе товаров в активный заказ")
    public void clickToViewOrder() {
        viewOrder.click();
    }
}
