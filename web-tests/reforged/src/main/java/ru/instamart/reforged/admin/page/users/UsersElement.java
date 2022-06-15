package ru.instamart.reforged.admin.page.users;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;

public interface UsersElement {

    Input searchUserByEmailOrPhone = new Input(By.xpath("//input[@data-qa='users_search']"), "Поле поиска пользователя по емейлу/номеру телефона");
    Button submitSearch = new Button(By.xpath("//form[@data-qa='users_search_form']//button[contains(@class,'search-button')]"), "Кнопка подтверждения поискового запроса");
    Button editUser = new Button(By.xpath("//button[contains(@data-qa,'users_edit')]"), "Кнопка редактирования найденного юзера");
    Link foundUserEmail = new Link(By.xpath("//th[text()='Профиль']/following-sibling::td//a"), "Емейл найденного пользователя");
}
