package ru.instamart.reforged.admin.users;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;

public interface UsersElement {

    interface UsersMain {

        Input searchEmail = new Input(By.id("search_email"));
        Input searchPhoneNumber = new Input(By.id("search_phone"));
        Button submitSearch = new Button(By.xpath("//button[@type='submit']"));
        Link editUser = new Link(By.xpath("//a[@data-action='edit']"));
        Link foundUserEmail = new Link(By.xpath("//div[@class='user_email']//a"));
    }

    interface UsersEdit {

        Link backToAllUsers = new Link(By.xpath("//a[@icon='icon-arrow-left']"));

        Element successFlash = new Element(By.xpath("//div[@id='flashWrapper']//div[text()='Учетная запись обновлена!']"));
        Input userEmail = new Input(By.id("user_email"));
        Input password = new Input(By.id("user_password"));
        Input passwordConfirmation = new Input(By.id("user_password_confirmation"));
        Input userComment = new Input(By.id("user_customer_comment"));

        //уточнить, случше искать по явному совпадению 2х классов или со совпадению одного класса
        Button saveChanges = new Button(By.xpath("//div[@data-hook='admin_user_edit_form_button']//button[@class='icon-refresh button']"));
    }
}
