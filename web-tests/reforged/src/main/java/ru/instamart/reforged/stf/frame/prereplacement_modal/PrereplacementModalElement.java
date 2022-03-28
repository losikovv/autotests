package ru.instamart.reforged.stf.frame.prereplacement_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface PrereplacementModalElement {
    //Задача на добавление data-qa атрибутов B2C-8386

    Element prereplacementModal = new Element(By.xpath("//div[./div/div/p[contains(.,'Выберите замену для товара')]]"), "Попап-окно 'Выберите замену для товара'");
    Element title = new Element(By.xpath("//p[contains(.,'Выберите замену для товара')]/.."), "Заголовок попап-окна");

    Button previous = new Button(By.xpath("//div[contains(@id,'Carousel')]/button[@title='Назад']"), "Кнопка прокрутки товаров 'Назад'");
    Button next = new Button(By.xpath("//div[contains(@id,'Carousel')]/button[@title='Вперед']"), "Кнопка прокрутки товаров 'Вперед'");

    ElementCollection itemsToReplace = new ElementCollection(By.xpath("//ul[contains(@id,'Carousel')]/li"), "Товары, для которых подобраны предзамены");

    ElementCollection itemsForReplace = new ElementCollection(By.xpath("//li[.//button[contains(.,'Выбра')]]"), "Товары на замену");
    ElementCollection itemsForReplaceNames = new ElementCollection(By.xpath("//li[.//button[contains(.,'Выбра')]]//h3"), "Наименования товаров на замену");
    ElementCollection itemsForReplacementCounts = new ElementCollection(By.xpath("//li[.//button[contains(.,'Выбра')]]//h3/following-sibling::div//span"), "Количества товаров на замену");
    ElementCollection selectForReplace = new ElementCollection(By.xpath("//li//button[contains(.,'Выбрать')]"), "Кнопки 'Выбрать' товара на замену");
    ElementCollection selectedForReplace = new ElementCollection(By.xpath("//li//button[contains(.,'Выбрано')]"), "Кнопки 'Выбрано' товара на замену");
    Button anyWillSuit = new Button(By.xpath("//button[contains(.,'Подойдет любой товар')]"), "Кнопка 'Подойдёт любой товар'");
    Button confirm = new Button(By.xpath("//button[contains(.,'Готово')]"), "Кнопка 'Готово'");
}
