package ru.instamart.reforged.admin.page.users;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;

public interface UsersElement {

    Input searchEmail = new Input(By.id("search_email"), "empty");
    Input searchPhoneNumber = new Input(By.id("search_phone"), "empty");
    Button submitSearch = new Button(By.xpath("//button[@type='submit']"), "empty");
    Link editUser = new Link(By.xpath("//a[@data-action='edit']"), "empty");
    Link foundUserEmail = new Link(By.xpath("//div[@class='user_email']//a"), "empty");
}
