package ru.instamart.reforged.admin.users;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;

public interface UsersElement {

    Input email = new Input(By.id("search_email"));
    Input phoneNumber = new Input(By.id("search_phone"));
    Button submit = new Button(By.xpath("//button[@type='submit']"));

    Link editUser = new Link(By.xpath("//a[@data-action='edit']"));
    Link userEmail = new Link(By.xpath("//div[@class='user_email']//a"));

    Link backToAllUsers = new Link(By.xpath("//a[@icon='icon-arrow-left']"));
}
