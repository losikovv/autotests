package ru.instamart.reforged.admin.modal;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.MultiSelector;

public final class CarTypeModal {

    private final Element dialog = new Element(By.xpath("//div[@role='dialog']"), "модальное окно выбора типа автомобиля");
    private final Button cancel = new Button(By.xpath("//button/span[text()='Отмена']"), "кнопка 'Отмена'");
    private final Button select = new Button(By.xpath("//button/span[text()='Выбрать']"), "кнопка 'Выбрать'");
    private final MultiSelector selector = new MultiSelector(By.xpath("//div[contains(@class,'ant-select ')]"), "селектор типа автомобиля");

    @Step("Нажать на кнопку 'Отмена'")
    public void clickOnCancel() {
        cancel.click();
    }

    @Step("Нажать на кнопку 'Выбрать'")
    public void clickOnSelect() {
        select.click();
    }

    //Легковой автомобиль
    //Грузовой автомобиль
    //Велосипед
    //Самокат
    //Электровелосипед
    //Другой транспорт
    @Step("Выбрать тип автомобиля")
    public void selectCarType(final String typeName) {
        selector.selectAutocompleteOff(typeName);
    }

    @Step("Модальное окно отображается")
    public void checkModalVisible() {
        dialog.should().visible();
    }
}
