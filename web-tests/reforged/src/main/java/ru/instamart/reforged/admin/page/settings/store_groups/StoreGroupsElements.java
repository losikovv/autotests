package ru.instamart.reforged.admin.page.settings.store_groups;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.table.StoreGroupsTable;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface StoreGroupsElements {

    Button addNewGroup = new Button(By.xpath("//button[./span[contains(.,'Новая группа')]]"), "Кнопка '+ Новая группа'");
    Element confirmActionModal = new Element(By.xpath("//div[contains(@class,'ant-popover ')][not(contains(@class,'hidden'))]//div[@class='ant-popover-inner']"), "Всплывающее окно подтверждения действия");
    Element confirmActionModalText = new Element(By.xpath("//div[contains(@class,'ant-popover ')][not(contains(@class,'hidden'))]//div[@class='ant-popover-message']"), "Сообщение во всплывающем окне подтверждения действия");
    Button confirmActionModalYes = new Button(By.xpath("//div[contains(@class,'ant-popover ')][not(contains(@class,'hidden'))]//button[contains(.,'Да')]"), "Кнопка 'Да' всплывающего подтверждения");
    Element noticePopup = new Element(By.xpath("//div[@class='ant-message-notice-content']"), "Всплывающее уведомение о выполнении операции");

    StoreGroupsTable groupsTable = new StoreGroupsTable();
}
