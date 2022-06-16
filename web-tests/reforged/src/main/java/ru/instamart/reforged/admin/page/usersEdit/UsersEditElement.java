package ru.instamart.reforged.admin.page.usersEdit;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.admin.block.flash_alert.FlashAlert;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface UsersEditElement {

   FlashAlert alert = new FlashAlert();
   AuthoredHeader authoredHeader = new AuthoredHeader();

   Button backToAllUsers = new Button(By.xpath("//button[@data-qa='users_form_return_back']"), "Кнопка возврата к списку пользователей");

   Input userEmail = new Input(By.xpath("//input[@data-qa='users_form_email_input']"), "Инпут емейла пользователя");
   Input password = new Input(By.xpath("//input[@data-qa='users_form_password_input']"), "Инпут пароля пользователя");
   Input passwordConfirmation = new Input(By.xpath("//input[@data-qa='users_form_password_confirmation_input']"), "Инпут подтверждения пароля пользователя");
   Input userComment = new Input(By.xpath("//input[@data-qa='users_form_customer_comment_input']"), "Примечание пользователя");
   Element roleAdminCheckbox = new Element(By.xpath("//input[@data-qa='users_form_role_ids_checkboxes_1']/ancestor::label"), "Чекбокс роли админ");
   Element b2bUserUnchecked = new Element(By.xpath("//input[@data-qa='users_form_b2b_checkbox' and @value='false']/ancestor::label"), "Невыбранный чекбокс B2B для юзера");
   Element b2bUserChecked = new Element(By.xpath("//input[@data-qa='users_form_b2b_checkbox' and @value='true']/ancestor::label"), "Выбранный чекбокс B2B для юзера");

   Button blockAllCards = new Button(By.xpath("//button[@data-qa='users_form_ban_credit_cards_button']"), "кнопка 'Заблокировать карты'");
   Button blockPhone = new Button(By.xpath("//button[@data-qa='users_form_cancel_phone_token_number_button']"), "кнопка 'Отвязать'");

   Button saveRoleChanges = new Button(By.xpath("//button[@data-qa='users_form_update_role_ids_button']"), "Кнопка применения ролей пользовтеля");
   Button saveB2BChanges = new Button(By.xpath("//button[@data-qa='users_form_update_b2b_button']"), "Кнопка применения b2b-чекбокса пользовтеля");
   Button savePassordChanges = new Button(By.xpath("//button[@data-qa='users_form_update_password_button']"), "Кнопка применения пароля пользовтеля");
}
