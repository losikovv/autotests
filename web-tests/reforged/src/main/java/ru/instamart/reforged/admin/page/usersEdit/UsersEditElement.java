package ru.instamart.reforged.admin.page.usersEdit;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.block.flash_alert.FlashAlert;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;

public interface UsersEditElement {

   FlashAlert alert = new FlashAlert();

   Link backToAllUsers = new Link(By.xpath("//a[@icon='icon-arrow-left']"), "empty");

   Input userEmail = new Input(By.id("user_email"), "empty");
   Input password = new Input(By.id("user_password"), "empty");
   Input passwordConfirmation = new Input(By.id("user_password_confirmation"), "empty");
   Input userComment = new Input(By.id("user_customer_comment"), "empty");
   Checkbox roleAdminCheckbox = new Checkbox(By.xpath("//label[text()='Admin']/preceding-sibling::input[@type='checkbox']"), "empty");
   Checkbox b2bUser = new Checkbox(By.id("user_b2b"), "empty");

   Button blockCard = new Button(By.xpath("//button[text()='Заблокировать карты']"), "кнопка 'Заблокировать карты'");
   Button blockPhone = new Button(By.xpath("//div[@id='user_phone_token_field']/a"), "кнопка 'Отвязать'");

   Button saveChanges = new Button(By.xpath("//div[@data-hook='admin_user_edit_form_button']//button[@class='icon-refresh button']"), "empty");
}
