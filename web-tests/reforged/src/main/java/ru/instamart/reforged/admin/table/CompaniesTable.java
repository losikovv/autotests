package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Table;

import static java.util.Objects.nonNull;

@Slf4j
public final class CompaniesTable extends Table {

    private final By companyName = By.xpath("./a");

    public int getRowsCount() {
        return getTableDataLines().size();
    }

    public String getCompanyName(final int line) {
        final var cellElement = getCellElement(Column.COMPANY_NAME.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }

    public void clickCompanyName(final int line) {
        final var cellElement = getCellElement(Column.COMPANY_NAME.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(companyName).click();
        }
    }

    public String getInn(final int line) {
        final var cellElement = getCellElement(Column.INN.label, line);
        if (nonNull(cellElement)) {
            return cellElement.getText();
        }
        return "empty";
    }

    public Status getStatus(final int line) {
        final var cellElement = getCellElement(Column.STATUS.label, line);
        if (nonNull(cellElement)) {
            return Status.valueOfLabel(cellElement.getText());
        }
        return Status.NOT_FOUND;
    }

    public PaymentType getPaymentType(final int line) {
        final var cellElement = getCellElement(Column.PAYMENT_TYPE.label, line);
        if (nonNull(cellElement)) {
            return PaymentType.valueOfLabel(cellElement.getText());
        }
        return PaymentType.NOT_FOUND;
    }

    @RequiredArgsConstructor
    public enum Column {

        COMPANY_NAME("Название"),
        INN("ИНН"),
        STATUS("Статус"),
        PAYMENT_TYPE("Тип оплаты");

        @Getter
        private final String label;
    }

    @RequiredArgsConstructor
    public enum PaymentType {

        IS_EMPTY("Отсутствует"),
        NOT_FOUND("НЕ НАЙДЕН ЭЛЕМЕНТ 'ТИП ОПЛАТЫ'");

        @Getter
        private final String label;

        public static PaymentType valueOfLabel(final String label) {
            for (final var status : values()) {
                if (status.label.equals(label)) {
                    return status;
                }
            }
            return NOT_FOUND;
        }
    }

    @RequiredArgsConstructor
    public enum Status {

        REQUIRES_APPROVAL("Требует одобрения"),
        APPROVED("Одобрен"),
        NOT_FOUND("НЕ НАЙДЕН ЭЛЕМЕНТ 'СТАТУС'");

        @Getter
        private final String label;

        public static Status valueOfLabel(final String label) {
            for (final var status : values()) {
                if (status.label.equals(label)) {
                    return status;
                }
            }
            return NOT_FOUND;
        }
    }
}
