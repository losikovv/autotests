package ru.instamart.reforged.stf.drawer.store_selector;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface StoreSelectorElement {

    Element storeSelector = new Element(By.xpath("//div[@id='react-modal']//div[@data-qa= 'store-selector']"));
    Link storeCard = new Link(By.xpath("//a[@data-qa='store-card']"));
}