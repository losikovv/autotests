package ru.instamart.reforged.stf.page.user.profile;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.frame.EmailFrame;
import ru.instamart.reforged.stf.frame.EmailSuccessFrame;
import ru.instamart.reforged.stf.frame.FullName;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;

public interface UserProfileElement {

    FullName fullNameFrame = new FullName();
    EmailFrame emailFrame = new EmailFrame();
    AuthModal authModal = new AuthModal();

    Button openFavorite = new Button(By.xpath("//div[text()='Любимые товары']"), "кнопка любимые товары в профиде пользователя");
    Button openOrders = new Button(By.xpath("//div[text()='Заказы']"), "кнопка любимые товары в профиде пользователя");
    Button openAccount = new Button(By.xpath("//div[text()='Аккаунт']"), "кнопка любимые товары в профиде пользователя");

    Element userPhone = new Element(By.xpath("//div[@class='user-block__container']/div[1]/div[2]"), "поле с номером телефона пользователя");
    Element userName = new Element(By.xpath("//div[@class='user-block__container']/div[2]/div[2]"), "поле с именем и фамилией пользователя");
    Element userEmail = new Element(By.xpath("//div[@class='user-block__container']/div[3]/div[2]"), "поле с email пользователя");

    Button buttonChangePhone = new Button(By.xpath("//div[@class='user-block__container']/div[1]/div[3]/button"), "кнопка изменить номер телефона пользователя");
    Button buttonChangeName = new Button(By.xpath("//div[@class='user-block__container']/div[2]/div[3]/button"), "кнопка изменить имя и фамилию пользователя");
    Button buttonChangeEmail = new Button(By.xpath("//div[@class='user-block__container']/div[3]/div[3]/button"), "кнопка изменить email пользователя");

    Element alert = new Element(By.xpath("//span[@class='alert__msg' and text()='Данные успешно сохранены']"), "алерт о том что данные сохранились");
}
