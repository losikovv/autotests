package ru.instamart.reforged.stf.drawer.cart.container;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Container;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.inner.InnerButton;
import ru.instamart.reforged.core.component.inner.InnerCollectionComponent;
import ru.instamart.reforged.core.component.inner.InnerElement;
import ru.instamart.reforged.core.component.inner.InnerLink;
import ru.instamart.reforged.core.custom_exceptions.NoSuchElementInCollection;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

/**
 * Отдельный магазин с товарами в корзине
 */
@Slf4j
public final class Retailer extends Container {

    private final InnerElement name = new InnerElement(getContainer(), By.xpath(".//div[@class='cart-retailer-details__name']"), "Название магазина");
    private final InnerElement itemsCountInHeader = new InnerElement(getContainer(), By.xpath("(.//div[@class='cart-retailer-details__well'])[1]"), "Количество позиций товара в шапке");
    private final InnerElement totalWeight = new InnerElement(getContainer(), By.xpath(".//div[@class='cart-retailer-details__weight']"), "Общий вес заказа");
    private final InnerElement totalAmount = new InnerElement(getContainer(), By.xpath("(.//div[@class='cart-retailer-details__well'])[2]"), "Общая стоимость заказа");
    private final InnerElement nearestDeliveryInfo = new InnerElement(getContainer(), By.xpath("(.//div[@class='cart-retailer-details__well'])[2]"), "Информация о доставке");
    private final InnerElement minAmountAlert = new InnerElement(getContainer(), By.xpath("//div[@class='cart-retailer__alert-message-box']"), "Сообщение о минимальной сумме заказа");
    private final InnerButton buttonRemoveShipments = new InnerButton(getContainer(), By.xpath(".//button[@data-qa='cart_remove_shipments_button']"), "Кнопку Удалить");
    private final InnerCollectionComponent itemInList = new InnerCollectionComponent(getContainer(), By.xpath(".//div[@data-qa='line-item']"), "Список товаров магазина");
    private final InnerElement mergeChecker = new InnerElement(getContainer(), By.xpath("//div[@class='cart-retailer__merge-checker']"), "Сообщение о возможности сделать дозаказ");
    private final InnerButton mergeButton = new InnerButton(getContainer(), By.xpath("//button[@data-qa='merge_products_button']"), "Кнопка 'перенести' в сообщении дозаказа");

    public Retailer(final WebElement container) {
        super(container);
    }

    @Step("Получаем название магазина")
    public String getName() {
        return name.getText();
    }

    @Step("Проверяем, что сообщение о минимальной сумме заказа не отображается")
    public void isAlertNotDisplayed() {
        Kraken.waitAction().shouldNotBeVisible(minAmountAlert, getContainer());
    }

    @Step("Получаем {0} по порядку продукт из списка продуктов магазина")
    public Item getItem(final int productOrder) {
        var index = productOrder - 1;
        final List<Item> items = getAllItems();
        if (items.size() >= index) {
            return items.get(index);
        } else {
            var exception = new NoSuchElementInCollection(index);
            Assert.fail(exception.getMessage(), exception);
            return null;
        }
    }

    @Step("Получаем количество наименований продуктов в списке магазина")
    public int getItemsCountInList() {
        return getAllItems().size();
    }

    @Step("Кликаем на кнопку 'Удалить' магазин из корзины")
    public void removeRetailer() {
        buttonRemoveShipments.click();
    }

    @Step("Удаляем все товары магазина по одному")
    public void removeAllItemsFromRetailer() {
        getAllItems().forEach(Item::deleteItem);
    }

    @Step("Получаем минимальную сумму заказа из плашки-алерта")
    public double returnMinOrderAmount() {
        return StringUtil.stringToDoubleParse(minAmountAlert.getText());
    }

    @Step("Кликаем на кнопку 'Перенести' в сообщении о возможности перенести товары в активный заказ")
    public void mergeProducts() {
        mergeButton.click();
    }

    @Step("Сравнить количество уникальных товаров магазина с ожидаемым значением {0}")
    public void compareItemsInCart(final int expected) {
        assertEquals(itemInList.elementCount(), expected, "Количество товаров в корзине отличается от ожидаемого");
    }

    @Step("Получаем количество продуктов, указанное в шапке магазина")
    private String getItemsCountInHeader() {
        return itemsCountInHeader.getText();
    }

    @Step("Получаем итоговый вес продуктов, указанный в шапке магазина ")
    private String getTotalWeight() {
        return totalWeight.getText();
    }

    @Step("Получаем итоговую стоимость продуктов, указанную в шапке магазина")
    private String getTotalAmount() {
        return totalAmount.getText();
    }

    @Step("Получаем информацию о ближайшей доставке")
    private String getNearestDeliveryInfo() {
        return nearestDeliveryInfo.getText();
    }

    private List<Item> getAllItems() {
        return itemInList.getComponents().stream().map(Item::new).collect(Collectors.toList());
    }
}
