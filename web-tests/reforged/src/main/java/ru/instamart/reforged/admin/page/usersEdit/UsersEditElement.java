package ru.instamart.reforged.admin.page.usersEdit;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface UsersEditElement {

        Link backToAllUsers = new Link(By.xpath("//a[@icon='icon-arrow-left']"));

        Element successFlash = new Element(By.xpath("//div[@id='flashWrapper']//div[@class = 'flash success']"));
        Element errorFlash = new Element(By.xpath("//div[@id='flashWrapper']//div[@class = 'flash error']"));
        Input userEmail = new Input(By.id("user_email"));
        Input password = new Input(By.id("user_password"));
        Input passwordConfirmation = new Input(By.id("user_password_confirmation"));
        Input userComment = new Input(By.id("user_customer_comment"));
        Checkbox roleAdminCheckbox = new Checkbox(By.xpath("//label[text()='Admin']/preceding-sibling::input[@type='checkbox']"));
        Checkbox b2bUser = new Checkbox(By.id("user_b2b"));

        Button saveChanges = new Button(By.xpath("//div[@data-hook='admin_user_edit_form_button']//button[@class='icon-refresh button']"));
}
