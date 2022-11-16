package ru.instamart.reforged.admin.page.shoppers.tags.add_tag;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import java.util.Collections;
import java.util.Set;

public interface AddTagModalCheck extends Check, AddTagModalElements {

    @Step("Проверяем, что модальное окно выбора тега отображается")
    default void checkModalVisible() {
        modal.should().visible();
    }

    @Step("Проверяем, что модальное окно выбора тега не отображается")
    default void checkModalNotVisible() {
        modal.should().invisible();
    }

    @Step("Проверяем, что кнопка добавления тегов отображается и задизейблена")
    default void checkAddTagsButtonInactive() {
        addTagsButtonInactive.should().visible();
    }


    @Step("Проверяем, что кнопка добавления тегов отображается и доступна")
    default void checkAddTagsButtonActive() {
        addTagsButtonActive.should().visible();
    }

    @Step("Проверяем, что теги в списке тегов скрыты")
    default void checkTagsDropdownInvisible() {
        tagsInList.should().invisible();
    }

    @Step("Проверяем, что теги в списке тегов показаны")
    default void checkTagsDropdownVisible() {
        tagsInList.should().visible();
    }

    @Step("Проверяем, что кнопки удаления в списке тегов показаны")
    default void checkTagsInFieldHaveRemoveButtons() {
        Assert.assertEquals(selectedTagsInField.elementCount(), selectedTagsInFieldRemoveButtons.elementCount(), "Количество кнопок удаления не соответствует количеству тегов");
    }

    @Step("Проверяем, что тег {0} выбран")
    default void checkTagSelected(String name) {
        selectedTagInList.should().visible(name);
    }

    @Step("Проверяем, что выбранные теги не отображаются в поле")
    default void checkTagSelectedExcludeInTagsList(Set<String> namesSetExcluded) {
        Assert.assertTrue(Collections.disjoint(tagsInList.getTextFromAllElements(), namesSetExcluded), "Теги выбранные в списке отображаются в поле для выбора");
    }

    @Step("Проверяем, что поле выбранных тегов пустое")
    default void checkSelectedTagsInFieldEmpty() {
        selectedTagsInField.should().invisible();
    }

    @Step("Проверяем, что теги в списке тегов показаны")
    default void compareSelectedTagsWithActual(Set<String> namesSetExpected) {
        Assert.assertTrue(namesSetExpected.containsAll(selectedTagsInField.getTextFromAllElements()), "Теги выбранные в списке не соответствуют отображаемым в поле");
    }
}
