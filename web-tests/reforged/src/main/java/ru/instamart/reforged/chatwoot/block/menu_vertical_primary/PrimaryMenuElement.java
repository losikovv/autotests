package ru.instamart.reforged.chatwoot.block.menu_vertical_primary;

import org.openqa.selenium.By;
import ru.instamart.reforged.chatwoot.frame.account_menu.AccountMenu;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface PrimaryMenuElement {

    AccountMenu accountMenu = new AccountMenu();

    Element chatwootLogoLink = new Element(By.xpath("//a[@class='router-link-exact-active router-link-active']"), "Логотип Chatwoot");
    Button dialogs = new Button(By.xpath("//nav[@class='menu vertical']/a[./span[.='CONVERSATIONS']]"), "Левое меню, кнопка 'Диалоги'");
    Button contacts = new Button(By.xpath("//nav[@class='menu vertical']/a[./span[.='CONTACTS']]"), "Левое меню, кнопка 'Контакты'");
    Button analytics = new Button(By.xpath("//nav[@class='menu vertical']/a[./span[.='ANALYTICS']]"), "Левое меню, кнопка 'Аналитика'");
    Button settings = new Button(By.xpath("//nav[@class='menu vertical']/a[./span[.='SETTINGS']]"), "Левое меню, кнопка 'Настройка'");

    Button unreadNotificationLink = new Button(By.xpath("//button[contains(@class,'notifications-link--button')]"), "Кнопка открытия окна непрочитанных уведомлений");
    Button accountMenuButton = new Button(By.xpath("//div[@id='account-details']"), "Кнопка меню пользовательского аккаунта");
    Element accountMenuButtonIndicator = new Element(By.xpath("//sup[contains(@class,'nt-badge-dot')]"), "Индикация статуса на кнопке пользовательского меню");
    Button quickKeysInfoLink = new Button(By.xpath("//li[@class='ant-dropdown-menu-item'][.='Клавиши быстрого доступа']"), "Ссылка 'Клавиши быстрого доступа'");
    Button logout = new Button(By.xpath("//li[@class='ant-dropdown-menu-item'][.='Выйти']"), "Кнопка 'Выйти'");
}
