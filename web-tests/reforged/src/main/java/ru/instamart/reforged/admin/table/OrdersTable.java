package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Table;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

//Заказы -> Список заказов
@Slf4j
public final class OrdersTable extends Table {

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
    private static final By shipmentStatusCollecting = By.xpath("(.//span[contains(@class,'ant-tag-default')])[1]/span[2]");
    private static final By shipmentStatusDelivery = By.xpath("(.//span[contains(@class,'ant-tag-default')])[2]/span[2]");

    private static final By orderSum = By.xpath("./div/div/div[contains(.,'₽')]");
    private static final By paymentMethod = By.xpath("./div/div/div[contains(.,'₽')]/following-sibling::div");
    private static final By promoLabel = By.xpath(".//span[@data-qa='shipments_table_payment_tag_promo']");
    private static final By paymentStatus = By.xpath(".//a[contains(@href,'payments')]/span");

    private static final By customerName = By.xpath(".//a[contains(@href,'customer')]");
    private static final By customerPhone = By.xpath(".//a[contains(@href,'customer')]/following-sibling::div");

    private static final By collector = By.xpath("(./div/div/div[2])[1]");
    private static final By removeCollectorButton = By.xpath(".(//button)[1]");
    private static final By courier = By.xpath("(./div/div/div[2])[2]");
    private static final By removeCourierButton = By.xpath(".(//button)[2]");

    private static final By buttonAddComment = By.xpath(".//button[@data-qa='shipments_table_add_comment']");
    private static final By commentInput = By.xpath(".//textarea[@data-qa='shipments_table_comment_input']");
    private static final By buttonConfirmComment = By.xpath(".//button[@data-qa='shipments_table_save_comment']");
    private static final By buttonCancelComment = By.xpath(".//button[@data-qa='shipments_table_cancel_comment']");

    public String getRetailerName(final int line) {
        final var cellElement = getCellElement(Column.RETAILER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(retailerAddress).getText().replaceAll(",+.*", "");
        }
        return "empty";
    }

    public String getStoreName(final int line) {
        final var cellElement = getCellElement(Column.RETAILER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(retailerAddress).getText().replaceAll(", .+, ", ", ");
        }
        return "empty";
    }

    public List<String> getAllRetailersList() {
        return getElementsFromColumn(Column.RETAILER.label).stream()
                .map(webElement ->
                        webElement.findElement(retailerAddress).getText().replaceAll(",+.*", ""))
                .collect(Collectors.toList());
    }

    public List<String> getAllBasicStoresList() {
        return getElementsFromColumn(Column.RETAILER.label).stream()
                .map(webElement ->
                        webElement.findElement(retailerAddress).getText().replaceAll(", .+, ", ", "))
                .collect(Collectors.toList());
    }

    public void clickRetailerStoreAddress(final int line) {
        final var cellElement = getCellElement(Column.RETAILER.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(retailerAddress).click();
        }
    }

    public String getOrderNumber(final int line) {
        return get(Column.ORDER, line, orderLink);
    }

    public void clickOrderNumber(final int line) {
        final var cellElement = getCellElement(Column.ORDER.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(orderLink).click();
        }
    }

    public List<String> getAllOrderNumbersList() {
        return getAll(Column.ORDER, orderLink);
    }

    public String getWeight(final int line) {
        return get(Column.ORDER, line, totalWeight);
    }

    public List<String> getAllWeight() {
        return getAll(Column.ORDER, totalWeight);
    }

    public List<String> getAllItems() {
        return getAll(Column.ORDER, itemsCount);
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

    public void clickShipmentNumber(final int line) {
        final var cellElement = getCellElement(Column.SHIPMENT.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(shipmentNumber).click();
        }
    }

    public List<String> getAllShipmentNumbersList() {
        return getElementsFromColumn(Column.SHIPMENT.label).stream()
                .map(webElement ->
                        webElement.findElement(shipmentNumber).getText())
                .collect(Collectors.toList());
    }

    public void clickDeliveryTime(final int line) {
        final var cellElement = getCellElement(Column.SHIPMENT.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(deliveryTimeLocal).click();
        }
    }

    public List<String> getAllShipmentStatusesList() {
        return getElementsFromColumn(Column.SHIPMENT.label).stream()
                .filter(webElement -> webElement.findElements(shipmentStatus).size() == 1)
                .map(webElement ->
                        webElement.findElement(shipmentStatus).getText())
                .collect(Collectors.toList());
    }

    public void clickPaymentStatus(final int line) {
        final var cellElement = getCellElement(Column.PAYMENT.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(paymentStatus).click();
        }
    }

    public void clickClientName(final int line) {
        final var cellElement = getCellElement(Column.CLIENT.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(customerName).click();
        }
    }

    public List<String> getAllCollectingShipmentStatusesList() {
        return getElementsFromColumn(Column.SHIPMENT.label).stream()
                .filter(webElement -> webElement.findElements(shipmentStatus).size() == 2)
                .map(webElement ->
                        webElement.findElement(shipmentStatusCollecting).getText())
                .collect(Collectors.toList());
    }

    public List<String> getAllDeliveryShipmentStatusesList() {
        return getElementsFromColumn(Column.SHIPMENT.label).stream()
                .filter(webElement -> webElement.findElements(shipmentStatus).size() == 2)
                .map(webElement ->
                        webElement.findElement(shipmentStatusDelivery).getText())
                .collect(Collectors.toList());
    }

    public List<String> getAllPaymentMethodsList() {
        return getAll(Column.PAYMENT, paymentMethod);
    }

    public List<String> getAllPaymentStatusesList() {
        return getAll(Column.PAYMENT, paymentStatus);
    }

    public List<String> getAllCustomerNames() {
        return getAll(Column.CLIENT, customerName);
    }

    public List<String> getAllCollectorsList() {
        return getAll(Column.EXECUTORS, collector);
    }

    public List<String> getAllCouriersList() {
        return getAll(Column.EXECUTORS, courier);
    }

    public List<String> getAllDeliveryDate() {
        return getAll(Column.SHIPMENT, deliveryDate);
    }

    private List<String> getAll(final Column column, final By by) {
        return getElementsFromColumn(column.label).stream()
                .map(webElement ->
                        webElement.findElement(by).getText())
                .collect(Collectors.toList());
    }

    private String get(final Column column, final int line, final By by) {
        final var cellElement = getCellElement(column.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(by).getText();
        }
        return "empty";
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
