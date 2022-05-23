package ru.instamart.reforged.next.page.notfound;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.next.block.helpdesk.HelpDesk;

public interface Page404Element {

    HelpDesk helpDesk = new HelpDesk();
    Link goToMain = new Link(By.xpath("//a[contains(@href, '/')]"), "кнопка перехода на главную страницу");
}
