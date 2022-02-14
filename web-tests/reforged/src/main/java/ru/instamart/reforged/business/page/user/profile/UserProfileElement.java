package ru.instamart.reforged.business.page.user.profile;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface UserProfileElement {

    Element userInfoBlock = new Element(By.xpath("//div[contains(@class,'user-block')][./div[contains(.,'Информация об аккаунте')]]"), "Блок 'Информация об аккаунте'");

    Button openAccount = new Button(By.xpath("//a[contains(.,'Аккаунт')]"), "Кнопка 'Аккаунт'");
    Button openFavorite = new Button(By.xpath("//a[contains(.,'Любимые товары')]"), "Кнопка 'Любимые товары'");
    Button openOrders = new Button(By.xpath("//a[contains(.,'Заказы')]"), "Кнопка 'Заказы'");

    Element userPhone = new Element(By.xpath("//div[@class='user-block__container']/div[1]/div[2]"), "поле с номером телефона пользователя");
    Element userName = new Element(By.xpath("//div[@class='user-block__container']/div[2]/div[2]"), "поле с именем и фамилией пользователя");
    Element userEmail = new Element(By.xpath("//div[@class='user-block__container']/div[3]/div[2]"), "поле с email пользователя");

    Button buttonChangePhone = new Button(By.xpath("//div[@class='user-block__container']/div[1]/div[3]/button"), "кнопка изменить номер телефона пользователя");
    Button buttonChangeName = new Button(By.xpath("//div[@class='user-block__container']/div[2]/div[3]/button"), "кнопка изменить имя и фамилию пользователя");
    Button buttonChangeEmail = new Button(By.xpath("//div[@class='user-block__container']/div[3]/div[3]/button"), "кнопка изменить email пользователя");

    Element alert = new Element(By.xpath("//span[@class='alert__msg' and text()='Данные успешно сохранены']"), "алерт о том что данные сохранились");
}
