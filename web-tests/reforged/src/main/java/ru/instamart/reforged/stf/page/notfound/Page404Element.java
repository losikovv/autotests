package ru.instamart.reforged.stf.page.notfound;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Link;

public interface Page404Element {

    Link goToMain = new Link(By.xpath("//a[contains(@href, '/')]"), "кнопка перехода на главную страницу");
}
