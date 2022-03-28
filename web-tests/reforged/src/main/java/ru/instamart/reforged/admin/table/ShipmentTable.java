package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.TableComponent;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
public final class ShipmentTable extends TableComponent {

    private final By orderNumber = By.xpath("./span[2]/a");
    private final By shipmentNumber = By.xpath("./span/a");
    private final By sum = By.xpath("./div[1]");
    private final By retailerName = By.xpath("./div[1]/img");
    private final By retailerCity = By.xpath("./div[2]");
    private final By comment = By.xpath("./table/tbody/tr[2]/td");
    private final By commentButton = By.xpath("./table/tbody/tr[2]/td[2]/button");
    private final By commentInput = By.xpath("./table/tbody/tr[1]/td/input");
    private final By commentAccept = By.xpath("./table/tbody/tr[1]/td[2]/button[1]");
    private final By commentDecline = By.xpath("./table/tbody/tr[1]/td[2]/button[2]");
    private final By phone = By.xpath(".//div[@class='where-to__phone with-tip']");
    private final By edit = By.xpath("./a[@data-action='edit']");

    public String getOrderNumber(final int line) {
        final var cellElement = getCellElement(Column.NUMBER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(orderNumber).getText();
        }
        return "empty";
    }

    public void clickToOrderNumber(final int line) {
        final var cellElement = getCellElement(Column.NUMBER.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(orderNumber).click();
        }
    }

    public String getShipmentNumber(final int line) {
        final var cellElement = getCellElement(Column.NUMBER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(shipmentNumber).getText();
        }
        return "empty";
    }

    public void clickToShipmentNumber(final int line) {
        final var cellElement = getCellElement(Column.NUMBER.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(shipmentNumber).click();
        }
    }

    public String getSum(final int line) {
        final var cellElement = getCellElement(Column.SUM.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(sum).getText();
        }
        return "empty";
    }

    public PaymentStatus getPaymentStatus(final int line) {
        final var cellElement = getCellElement(Column.PAYMENT_STATUS.label, line);
        if (nonNull(cellElement)) {
            return PaymentStatus.valueOfLabel(cellElement.getText());
        }
        return PaymentStatus.NOT_FOUND;
    }

    public DeliveryStatus getDeliveryStatus(final int line) {
        final var cellElement = getCellElement(Column.DELIVERY_STATUS.label, line);
        if (nonNull(cellElement)) {
            return DeliveryStatus.valueOfLabel(cellElement.getText());
        }
        return DeliveryStatus.NOT_FOUND;
    }

    public String getRetailer(final int line) {
        final var cellElement = getCellElement(Column.RETAILER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(retailerName).getAttribute("alt");
        }
        return "empty";
    }

    public String getRetailerCity(final int line) {
        final var cellElement = getCellElement(Column.RETAILER.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(retailerCity).getText();
        }
        return "empty";
    }

    public String getDeliveryDateAndTime(final int line) {
        final var cellElement = getCellElement(Column.DATE_AND_TIME_FOR_DELIVERY.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }

    public String getComment(final int line) {
        final var cellElement = getCellElement(Column.COMMENT.label, line);
        if (nonNull(cellElement)) {
            return cellElement.findElement(comment).getText();
        }
        return "empty";
    }

    public void addComment(final String text, final int line) {
        final var cellElement = getCellElement(Column.COMMENT.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(commentButton).click();
            final var input = cellElement.findElement(commentInput);
            input.clear();
            input.sendKeys(text);
            cellElement.findElement(commentAccept).click();
        }
    }

    public List<String> getPhones() {
        final var column = getElementsFromColumn(Column.WHERE.label);
        final var phones = new ArrayList<String>();
        column.forEach(line -> {
            phones.add(line.findElement(phone).getText());
        });
        return phones;
    }

    public void clickOnEdit(final int line) {
        final var cellElement = getCellElement(Column.ACTION.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(edit).click();
        }
    }

    @RequiredArgsConstructor
    public enum Column {

        NUMBER("НОМЕР"),
        SUM("СУММА"),
        PAYMENT_STATUS("СОСТОЯНИЕ ОПЛАТЫ"),
        DELIVERY_STATUS("СОСТОЯНИЕ ДОСТАВКИ"),
        RETAILER("РИТЕЙЛЕР"),
        DATE_AND_TIME_FOR_DELIVERY("ДАТА И ВРЕМЯ ДОСТАВКИ"),
        COMMENT("КОММЕНТАРИЙ"),
        WHERE("КУДА"),
        COLLECTOR("СБОРЩИК"),
        DRIVER("ВОДИТЕЛЬ"),
        TOTAL_WEIGHT("ОБЩИЙ ВЕС"),
        DOCUMENTATION("ДОКУМЕНТАЦИЯ"),
        SHOP("МАГАЗИН СБОРКИ"),
        ACTION("ACTION")
        ;

        @Getter
        private final String label;
    }

    @RequiredArgsConstructor
    public enum PaymentStatus {

        PAID("ОПЛАЧЕН"),
        NOT_PAID("НЕ ОПЛАЧЕН"),
        UNDERPAYMENT("НЕДОПЛАТА"),
        OVERPAYMENT("ПЕРЕПЛАТА"),
        ERROR("ОШИБКА"),
        NOT_FOUND("НЕ ПЛАТЕЖНЫЙ СТАТУС ЭЛЕМЕНТ")
        ;

        @Getter
        private final String label;

        public static PaymentStatus valueOfLabel(final String label) {
            for (final var status : values()) {
                if (status.label.equals(label)) {
                    return status;
                }
            }
            return NOT_FOUND;
        }
    }

    @RequiredArgsConstructor
    public enum DeliveryStatus {

        DECORATED("ОФОРМЛЕН"),
        DELIVERED("ДОСТАВЛЕН"),
        CANCELED("ОТМЕНЕН"),
        NOT_FOUND("НЕ НАЙДЕН ЭЛЕМЕНТ СТАТУС ДОСТАВКИ")
        ;

        @Getter
        private final String label;

        public static DeliveryStatus valueOfLabel(final String label) {
            for (final var status : values()) {
                if (status.label.equals(label)) {
                    return status;
                }
            }
            return NOT_FOUND;
        }
    }
}
