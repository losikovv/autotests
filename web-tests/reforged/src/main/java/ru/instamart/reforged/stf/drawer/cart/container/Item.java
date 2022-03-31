package ru.instamart.reforged.stf.drawer.cart.container;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.instamart.reforged.core.Container;
import ru.instamart.reforged.core.component.inner.InnerButton;
import ru.instamart.reforged.core.component.inner.InnerCollectionComponent;
import ru.instamart.reforged.core.component.inner.InnerElement;
import ru.instamart.reforged.core.component.inner.InnerInput;

import static ru.instamart.reforged.core.Check.krakenAssert;
import static ru.instamart.reforged.core.Kraken.waitAction;

/**
 * Товар в корзине
 */
public final class Item extends Container {

    private final InnerButton buttonOpenItemPopupInfo = new InnerButton(getContainer(), By.xpath(".//a[@data-qa='open-button']"), "Открыть карточку товара");
    private final InnerButton buttonDeleteItem = new InnerButton(getContainer(), By.xpath(".//button[@data-qa='cart_delete_item_button']"), "Кнопка удалить товар");
    private final InnerButton buttonIncreaseItemsCount = new InnerButton(getContainer(), By.xpath(".//button[@data-qa='increase-button']"), "Кнопка увеличить количество товара");
    private final InnerButton buttonDecreaseItemsCount = new InnerButton(getContainer(), By.xpath(".//button[@data-qa='decrease-button']"), "Кнопка уменьшить количество товара");
    private final InnerInput itemCountInput = new InnerInput(getContainer(), By.xpath(".//div[@data-qa='line-item-counter']//span"), "Поле ввода кол-ва товара");
    private final InnerElement itemName = new InnerElement(getContainer(), By.xpath(".//dt"), "Название товара");
    private final InnerElement itemPackageSize = new InnerElement(getContainer(), By.xpath(".//dd"), "Размер упаковки товара");
    private final InnerElement itemsAmount = new InnerElement(getContainer(), By.xpath(".//button[@data-qa='cart_delete_item_button']/../div"), "Общая стоимость товара");
    private final InnerElement costSpinner = new InnerElement(getContainer(), By.xpath("//div[@data-qa='line-item']//span[contains(text(),'Загрузка')]"), "Спиннер пересчета цены позиции");

    //Предзамены Задача на добавление data-qa атрибутов B2C-8387
    private final InnerElement selectReplacement = new InnerElement(getContainer(), By.xpath("//button[contains(.,'Выбрать замену')]"), "Кнопка 'Выбрать замену'");
    private final InnerElement prereplacementBlock = new InnerElement(getContainer(), By.xpath("//div[./b[contains(.,'Ваша замена')]]/.."), "Блок товаров, выбранных для предзамены");
    private final InnerCollectionComponent itemForReplacementImage = new InnerCollectionComponent(getContainer(), By.xpath("//div[./b[contains(.,'Ваша замена')]]/../div[3]/picture"), "Изображения товаров на замену");
    private final InnerElement itemForReplaceName = new InnerElement(getContainer(), By.xpath("//div[./b[contains(.,'Ваша замена')]]/../div[4]/div[1]/div[1]"), "Название товара, выбранного на замену");
    private final InnerElement editReplacement = new InnerElement(getContainer(), By.xpath("//div[./b[contains(.,'Ваша замена')]]/../button[1]/div"), "Кнопка 'Редактировать' (замену)");
    private final InnerElement removeReplacement = new InnerElement(getContainer(), By.xpath("//div[./b[contains(.,'Ваша замена')]]/../button[2]"), "Кнопка 'Удалить' (замену)");

    public Item(final WebElement container) {
        super(container);
    }

    @Step("Удаляем товар")
    public void deleteItem() {
        itemName.getActions().mouseOver();
        buttonDeleteItem.getActions().moveToElementAndClick();
    }

    @Step("Открываем продуктовую карту")
    public void openProductCart() {
        buttonOpenItemPopupInfo.click();
    }

    @Step("Получаем количество единиц продукта в корзине")
    public int getCount() {
        return itemCountInput.getNumericValue();
    }

    @Step("Получаем наименование продукта")
    public String getName() {
        return itemName.getText();
    }

    @Step("Сравниваем кол-во штук товара с ожидаемым значением {0}")
    public void compareItemQuantityInCart(final int expected) {
        krakenAssert.assertEquals(itemCountInput.getNumericValue(), expected, "Количество штук товара в корзине отличается от ожидаемого");
    }

    @Step("Проверяем, что спиннер пропал")
    public void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(costSpinner);
    }

    @Step("Проверяем, что спиннер отображается")
    public void checkSpinnerIsVisible() {
        waitAction().shouldBeVisible(costSpinner);
    }

    @Step("Кликаем на кнопку 'Уменьшить' количество товара")
    public void increaseCount() {
        itemCountInput.getActions().mouseOver(); //кнопка становится видимой только после того, как наводимся на поле
        buttonIncreaseItemsCount.getActions().moveToElementAndClick();
    }

    @Step("Кликаем на кнопку 'Увеличить' количество товара")
    public void decreaseCount() {
        itemCountInput.getActions().mouseOver(); //кнопка становится видимой только после того, как наводимся на поле
        buttonDecreaseItemsCount.getActions().moveToElementAndClick();
    }

    @Step("Получаем размер упаковки продукта")
    private String getPackageSize() {
        return itemPackageSize.getText();
    }

    @Step("Получаем итоговую стоимость продукта")
    private String getTotalAmount() {
        return itemsAmount.getText();
    }

    @Step("Проверяем, что отображается кнопка 'Выбрать замену'")
    public void checkReplaceButtonDisplayed() {
        waitAction().shouldBeVisible(selectReplacement);
    }

    @Step("Нажимаем на кнопку 'Выбрать замену'")
    public void clickSelectReplacement() {
        selectReplacement.hoverAndClick();
    }

    @Step("Проверяем, что отображается блок с выбранными предзаменами")
    public void checkPrereplacementBlockDisplayed() {
        waitAction().shouldBeVisible(prereplacementBlock);
    }

    @Step("Проверяем, что блок с выбранными предзаменами не отображается")
    public void checkPrereplacementBlockNotDisplayed() {
        waitAction().shouldNotBeVisible(prereplacementBlock);
    }

    @Step("Нажимаем на кнопку 'Редактировать' (замену)")
    public void clickEditReplacement() {
        editReplacement.hoverAndClick();
    }

    @Step("Нажимаем на кнопку 'Удалить' (замену)")
    public void clickRemoveReplacement() {
        removeReplacement.hoverAndClick();
    }

    @Step("Проверяем, что название выбранного на замену товара соответствует ожидаемому: {expectedReplacementItemName}")
    public void checkReplacementItemNameEquals(final String expectedReplacementItemName) {
        Assert.assertEquals(itemForReplaceName.getText(), expectedReplacementItemName, "Наименование товара, выбранного на замену отличается от ожидаемого");
    }

    @Step("Проверяем, что в блоке с предзаменами указано несколько товаров")
    public void checkPrereplacementAnySuiteDisplayed() {
        Assert.assertTrue(itemForReplacementImage.elementCount() > 1, "Количество товаров, отображаемых в блоке выбранных замен не соответствует ожидаемому");
    }
}
