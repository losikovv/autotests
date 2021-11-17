package ru.instamart.reforged.admin.page.sections.settings.shipping_method;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface ShippingMethodElement {

    Button editButton = new Button(ByKraken.xpath("//td[text()='%s']/ancestor::tr/descendant::button"), "редактирование метода доставки");
    Button addNewMarketingRule = new Button(By.xpath("//div/span[text()='Правила маркетинговой стоимости доставки']/parent::div/following::div[3]//button"),
            "кнопка добавления правила");
    Element selectMarketingCalculator = new Element(By.xpath("//div/span[text()='Правила маркетинговой стоимости доставки']/parent::div/following::div[2]//div[text()='Тип калькулятора']"),
            "селектор Тип калькулятора");
    Element selectMarketingRule = new Element(By.xpath("//div/span[text()='Правила маркетинговой стоимости доставки']/parent::div/following::div[2]//div[text()='Тип правила']"),
            "селектор Тип правила");
    Button deleteMarketingRule = new Button(By.xpath("/div/span[text()='Правила маркетинговой стоимости доставки']/parent::div/following::div[2]//span[@aria-label='delete']"),
            "кнопка удаления маркетингова правила");

    Button addNewNominalRule = new Button(By.xpath("//div/span[text()='Правила номинальной стоимости доставки']/parent::div/following::div[3]//button"),
            "кнопка добавления правила");
    Element selectNominalCalculator = new Element(By.xpath("//div/span[text()='Правила номинальной стоимости доставки']/parent::div/following::div[2]//div[text()='Тип калькулятора']"),
            "селектор Тип калькулятора");
    Element selectNominalRule = new Element(By.xpath("//div/span[text()='Правила номинальной стоимости доставки']/parent::div/following::div[2]//div[text()='Тип правила']"),
            "селектор Тип правила");
    Button deleteNominalRule = new Button(By.xpath("/div/span[text()='Правила номинальной стоимости доставки']/parent::div/following::div[2]//span[@aria-label='delete']"),
            "кнопка удаления номинального правила");

    Element selectorValue = new Element(ByKraken.xpath("//div[contains(text(), '%s')]"), "пункт в селекторе");
    Input deliveryPrice = new Input(By.xpath("//label[text()='Цена доставки']/parent::div//input"), "поле Цена доставки");
    Input assemblySurcharge = new Input(By.xpath("//label[text()='Надбавка за сборку']/parent::div//input"), "поле Надбавка за сборку");
    Input bagSurcharge = new Input(By.xpath("//label[text()='Надбавка за пакеты']/parent::div//input"), "поле Надбавка за пакеты");

    Input basePrice = new Input(By.xpath("//label[text()='Базовая цена']/parent::div//input"), "поле Базовая цена");
    Input baseCount = new Input(By.xpath("//label[text()='Базовое кол-во позиций']/parent::div//input"), "поле Базовое кол-во позиций");
    Input baseWeight = new Input(By.xpath("//label[text()='Базовая масса']/parent::div//input"), "поле Базовая масса");
    Input weight = new Input(By.xpath("//label[text()='Дополнительная масса']/parent::div//input"), "поле Дополнительная масса");
    Input surchargeWeight = new Input(By.xpath("//label[text()='Надбавка за доп. массу']/parent::div//input"), "поле Надбавка за доп. массу");
    Input addedCount = new Input(By.xpath("//label[text()='Дополнительное количество']/parent::div//input"), "поле Дополнительное количество");
    Input surchargeCount = new Input(By.xpath("//label[text()='Надбавка за доп. количество']/parent::div//input"), "поле Надбавка за доп. количество");

    Button submitChanges = new Button(By.xpath("//span[text()='Применить изменения']"), "кнопка Применить изменения");
}
