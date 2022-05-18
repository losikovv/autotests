package ru.instamart.reforged.core.page;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface PageElement {

    Element page404Error = new Element(By.xpath("//div[contains(@class,'ErrorSplash_styles_root')]"), "Ошибка открытия страницы (404)");
}
