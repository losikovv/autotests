package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.component.TableComponent;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

//Заказы -> Список заказов
@Slf4j
public final class OrdersTable extends TableComponent {

    private static final By retailerImage = By.xpath(".//img");
    private static final By retailerAddress = By.xpath(".//a[@data-qa='shipments_table_store_address']");

    private static final By orderLink = By.xpath(".//a[contains(@href,'edit')]");
    private static final By totalWeight = By.xpath(".//div[.='Вес']/following-sibling::div");
    private static final By itemsCount = By.xpath(".//div[.='кол-во']/following-sibling::div");
    private static final By platform = By.xpath("..//div[.='кол-во']/../following-sibling::div");
    private static final By documentsLink = By.xpath(".//a[contains(@href,'documentation')]");

    private static final By shipmentNumber = By.xpath(".//div[contains(@class,'ant-dropdown-trigger')]/span");
    private static final By deliveryTimeLocal = By.xpath(".//a[@data-qa='shipments_table_delivery_time_local']");
    private static final By deliveryTimeMoscow = By.xpath(".//div[@data-qa='shipments_table_delivery_time_moscow']");
    private static final By deliveryDate = By.xpath(".//div[@data-qa='shipments_table_delivery_date']");
    private static final By shipmentStatus = By.xpath(".//span[contains(@class,'ant-tag-default')]/span[2]");

    private static final By orderSum = By.xpath("./div/div/div[contains(.,'₽')]");
    private static final By paymentMethod = By.xpath("./div/div/div[contains(.,'₽')]/following-sibling::div");
    private static final By promoLabel = By.xpath(".//span[@data-qa='shipments_table_payment_tag_promo']");
    private static final By paymentStatus = By.xpath(".//a[contains(@href,'payments')]/span");

    private static final By customerName = By.xpath(".//a[contains(@href,'customer')]");
    private static final By customerPhone = By.xpath(".//a[contains(@href,'customer')]/following-sibling::div");

    private static final By collector = By.xpath("(./div/div/div[2])[1]");
    private static final By removeCollectorButton = By.xpath(".(//button)[1]");
    private static final By deliveryMan = By.xpath("(./div/div/div[2])[2]");
    private static final By removeDeliveryManButton = By.xpath(".(//button)[2]");

    private static final By buttonAddComment = By.xpath(".//button[@data-qa='shipments_table_add_comment']");
    private static final By commentInput = By.xpath(".//textarea[@data-qa='shipments_table_comment_input']");
    private static final By buttonSaveComment = By.xpath(".//button[@data-qa='shipments_table_save_comment']");
    private static final By buttonCancelComment = By.xpath(".//button[@data-qa='shipments_table_cancel_comment']");

    public String getRetailerStoreAddress(final int line) {
        final var cellElement = getCellElement(Column.RETAILER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(retailerAddress).getText();
        }
        return "empty";
    }

    public List<String> getAllRetailersList() {
        return getElementsFromColumn(Column.RETAILER.label).stream()
                .map(webElement ->
                        webElement.findElement(retailerAddress).getText().replaceAll(",+.*",""))
                .collect(Collectors.toList());
    }

    public List<String> getAllBasicStoresList() {
        return getElementsFromColumn(Column.RETAILER.label).stream()
                .map(webElement ->
                        webElement.findElement(retailerAddress).getText().replaceAll(", .+, ",", "))
                .collect(Collectors.toList());
    }

    public void clickRetailerStoreAddress(final int line) {
        final var cellElement = getCellElement(Column.RETAILER.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(retailerAddress).click();
        }
    }

    public String getOrderNumber(final int line) {
        final var cellElement = getCellElement(Column.ORDER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(orderLink).getText();
        }
        return "empty";
    }

    public List<String> getAllOrderNumbersList() {
        return getElementsFromColumn(Column.ORDER.label).stream()
                .map(webElement ->
                        webElement.findElement(orderLink).getText())
                .collect(Collectors.toList());
    }

    public void goToOrderEdit(final int line) {
        final var cellElement = getCellElement(Column.ORDER.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(orderLink).click();
        }
    }

    public String getPlatformName(final int line) {
        final var cellElement = getCellElement(Column.ORDER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(platform).getText();
        }
        return "empty";
    }

    public List<String> getAllPlatformsList() {
        return getElementsFromColumn(Column.ORDER.label).stream()
                .map(webElement ->
                        webElement.findElement(platform).getText())
                .collect(Collectors.toList());
    }

    public String getShipmentNumber(final int line) {
        final var cellElement = getCellElement(Column.SHIPMENT.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(shipmentNumber).getText();
        }
        return "empty";
    }

    public List<String> getAllShipmentNumbersList() {
        return getElementsFromColumn(Column.SHIPMENT.label).stream()
                .map(webElement ->
                        webElement.findElement(shipmentNumber).getText())
                .collect(Collectors.toList());
    }

    public List<String> getAllShipmentStatusesList() {
        return getElementsFromColumn(Column.SHIPMENT.label).stream()
                .map(webElement ->
                        webElement.findElement(shipmentStatus).getText())
                .collect(Collectors.toList());
    }

    public void openShipmentDropdownMenu(final int line) {
        final var cellElement = getCellElement(Column.SHIPMENT.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(shipmentNumber).click();
        }
    }

    public List<String> getAllPaymentMethodsList() {
        return getElementsFromColumn(Column.PAYMENT.label).stream()
                .map(webElement ->
                        webElement.findElement(paymentMethod).getText())
                .collect(Collectors.toList());
    }

    public List<String> getAllPaymentStatusesList() {
        return getElementsFromColumn(Column.PAYMENT.label).stream()
                .map(webElement ->
                        webElement.findElement(paymentStatus).getText())
                .collect(Collectors.toList());
    }

    @RequiredArgsConstructor
    public enum Column {

        RETAILER("Ритейлер"),
        ORDER("Заказ"),
        SHIPMENT("Доставка"),
        PAYMENT("Оплата"),
        CLIENT("Заказчик"),
        EXECUTORS("Исполнители"),
        COMMENT("Комментарий"),
        ;

        @Getter
        private final String label;
    }
}
