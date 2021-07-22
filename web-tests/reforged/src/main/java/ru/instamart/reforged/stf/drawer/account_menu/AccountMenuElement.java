package ru.instamart.reforged.stf.drawer.account_menu;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface AccountMenuElement {

    Element username = new Element(By.xpath("//div[@data-qa='account-menu-username']"));
    Link profile = new Link(By.xpath("//a[@data-qa='account-menu-profile']"));
    Link terms = new Link(By.xpath("//a[@data-qa='account-menu-terms']"));
    Button logout = new Button(By.xpath("//button[@data-qa='account-menu-logout']"));
    Button delivery = new Button(By.xpath("//button[@data-qa='account-menu-delivery']"));
    Link faq = new Link(By.xpath("//a[@data-qa='account-menu-faq']"));
}
