package ru.instamart.reforged.admin.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Table;

import static java.util.Objects.nonNull;

@Slf4j
//Пользователь -> Реквизиты компаний
public final class UserCompaniesTable extends Table {
    private final By edit = By.xpath(".//button[@data-qa='users_company_documents_table_edit']");

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

    public void clickEdit(final int line) {
        final var cellElement = getCellElement(Column.ACTION.label, line);
        if (nonNull(cellElement)) {
            cellElement.findElement(edit).click();
        }
    }

    @RequiredArgsConstructor
    public enum Column {

        COMPANY_NAME("Наименование"),
        ACTION("ACTION");

        @Getter
        private final String label;
    }
}
