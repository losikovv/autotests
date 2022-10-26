package ru.instamart.reforged.admin.page.shoppers.tags.add_tag;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

import java.util.Collections;
import java.util.Set;

public final class AddTagModal implements AddTagModalCheck {

    @Step("Нажимаем на кнопку добавления тегов")
    public void clickOnAddTagsButton() {
        addTagsButtonActive.click();
    }

    @Step("Нажимаем на селектор добавления тегов")
    public void clickOnTagsSelector() {
        tagsSelector.click();
    }

    @Step("Клик на {0}-й элемент списка тегов")
    public void clickOnTagInList(int elementOrder) {
        tagsInList.getElements().get(elementOrder - 1).click();
    }

    @Step("Получить название {0}-го элемента списка тегов")
    public String getTagNameFromList(int elementOrder) {
        return tagsInList.getElements().get(elementOrder - 1).getText();
    }
}