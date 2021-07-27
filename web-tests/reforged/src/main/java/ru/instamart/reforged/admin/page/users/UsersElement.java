package ru.instamart.reforged.admin.page.users;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface UsersElement {

        Input searchEmail = new Input(By.id("search_email"));
        Input searchPhoneNumber = new Input(By.id("search_phone"));
        Button submitSearch = new Button(By.xpath("//button[@type='submit']"));
        Link editUser = new Link(By.xpath("//a[@data-action='edit']"));
        Link foundUserEmail = new Link(By.xpath("//div[@class='user_email']//a"));

}
