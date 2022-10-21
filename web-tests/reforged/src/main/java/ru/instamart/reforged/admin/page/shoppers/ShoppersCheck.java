package ru.instamart.reforged.admin.page.shoppers;


import io.qameta.allure.Step;
import org.testng.Assert;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public interface ShoppersCheck extends ShoppersElement {

    @Step("Проверяем, что спиннер отображается")
    default void checkSpinnerVisible() {
        spinner.should().visible();
    }

    @Step("Проверяем, что спиннер не отображается")
    default void checkSpinnerNotVisible() {
        spinner.should().invisible();
    }

    @Step("Проверяем, что партнер c именем {0} найден")
    default void checkShopperWasFound(String name) {
        shopperNameInTable.should().visible(name);
    }

    @Step("Проверяем, что теги в списке тегов отображаются по алфавиту")
    default void compareSelectedTagsWithActual(Set<String> namesSetExpected) {
        Assert.assertEquals(tags.getTextFromAllElementsInOrder(), namesSetExpected.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()), "Теги выбранные в списке отображаются не по алфавиту");
    }

    @Step("Проверяем, что количество тегов в списке соответствует ожидаемому - {0}")
    default void compareSelectedTagsQuantityWithActual(int tagsCount) {
        Assert.assertEquals(tags.elementCount(), tagsCount, "Количество тегов не соответствует ожидаемому");
    }

    @Step("Проверяем, что тег с именем: {0} отображается в списке тегов")
    default void checkTagWithNameVisible(String name) {
        tag.should().visible(name);
    }

    @Step("Проверяем, что тег с именем: {0} не отображается в списке тегов")
    default void checkTagWithNameNotVisible(String name) {
        tag.should().invisible(name);
    }

    @Step("Проверить, что текст на кнопке разворачивания списка тегов - {0}")
    default void checkCollapseTagListButtonText(String text) {
        Assert.assertEquals(collapseTagsButtons.getElements().get(0).getText(), text, "Текст на кнопке раскрытия списков тегов не '" + text +"'");
    }

    @Step("Проверяем, что кнопка 'Добавить тег' отображается")
    default void checkAddTagButtonVisible() {
        addTagButtons.should().visible();
    }

    @Step("Проверяем, что модальное окно выбора тега отображается")
    default void checkModalVisible() {
        modal.should().invisible();
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

    @Step("Проверяем, что тег: {0} выбран")
    default void checkTagSelected(String name) {
        selectedTagInList.should().visible(name);
    }

    @Step("Проверяем, что тег: {0} отображается у каждого отфильтрованного партнера в списке")
    default void checkTagSelectedInFilterVisibleOnAllPartners(String name) {
        Assert.assertEquals(partnerEntryInTable.elementCount(), tagsWithName.getElements(name).size(), "Не у каждого партнера в списке есть отфильтрованный тег");
    }

    @Step("Проверяем, что теги в списке тегов показаны")
    default void compareSelectedTagsInFieldWithActual(Set<String> namesSetExpected) {
        Assert.assertTrue(namesSetExpected.containsAll(selectedTagsInField.getTextFromAllElements()), "Теги выбранные в списке не соответствуют отображаемым в поле");
    }
}
