package ru.instamart.reforged.stf.frame.prereplacement_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.Close;

public final class PrereplacementModal implements PrereplacementModalCheck, Close {

    @Step("Получаем количество товаров, для которых нужно выбрать предзамены")
    public int getItemsToReplaceCount() {
        return itemsToReplace.elementCount();
    }

    @Step("Получаем количество товаров, доступных для замены")
    public int getItemsForReplaceCount() {
        return itemsForReplace.elementCount();
    }

    @Step("Получаем наименование {itemPosition}-го товара на замену")
    public String getItemForReplaceName(final int itemPosition) {
        return itemsForReplaceNames.getElementText(itemPosition - 1);
    }

    @Step("Нажимаем 'Выбрать' у первого товара на замену")
    public void selectFirstItemForReplace() {
        selectForReplace.clickOnFirst();
    }

    @Step("Нажимаем 'Выбрать' у второго товара на замену")
    public void selectSecondItemForReplace() {
        selectForReplace.clickOnLast();
    }

    @Step("Нажимаем 'Подойдёт любой товар'")
    public void clickAnyWillSuit() {
        anyWillSuit.click();
    }

    @Step("Нажимаем 'Готово'")
    public void clickConfirm() {
        confirm.click();
    }
}
