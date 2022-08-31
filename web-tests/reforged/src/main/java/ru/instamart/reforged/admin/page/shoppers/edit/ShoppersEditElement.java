package ru.instamart.reforged.admin.page.shoppers.edit;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.block.flash_alert.FlashAlert;
import ru.instamart.reforged.admin.modal.CarTypeModal;
import ru.instamart.reforged.admin.popover.Popover;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.*;

public interface ShoppersEditElement {

    FlashAlert alert = new FlashAlert();
    CarTypeModal carTypeModal = new CarTypeModal();
    Popover popover = new Popover();

    Input nameInput = new Input(By.xpath("//input[@name='name']"), "инпут с именем");
    Input loginInput = new Input(By.xpath("//input[@name='login']"), "инпут с логином");
    Input phoneInput = new Input(By.xpath("//input[@name='phone']"), "инпут с телефоном");

    Button saveButton = new Button(By.xpath("//div[@role='tabpanel' and @tabindex='0']//button[@type='submit']"), "кнопка 'Сохранить'");

    MultiSelector currentShopSelector = new MultiSelector(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Текущий магазин')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "селектор 'Текущий магазин'");
    MultiSelector shoppersRoleSelector = new MultiSelector(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Роли сотрудника')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "селектор 'Роли сотрудника'");

    Element currentShop = new Element(By.xpath("//div[@label='Текущий магазин']/div/span[2]/span"), "селектор 'Текущий магазин'");
    Element innInput = new Element(By.xpath("//input[@name='inn']"), "инпут с инн");
    ElementCollection rolesCollection = new ElementCollection(By.xpath("//div[@label='Роли сотрудника']/div/div/div/span/span[1]"), "выбранные роли сотрудника");

    Element emptyCar = new Element(By.xpath("//div[@class='ant-empty-image']"), "картинка для отсутствующего транспорта");

    Button addEquipmentButton = new Button(By.xpath("//button[@type='button']/span[text()='Добавить оборудование']"), "кнопка 'Добавить оборудование'");
    Button addUniformButton = new Button(By.xpath("//button[@type='button']/span[text()='Добавить униформу']"), "кнопка 'Добавить униформу'");
    Button addVehicleButton = new Button(By.xpath("//button[@type='button']/span[text()='Добавить транспортное средство']"), "кнопка 'Добавить транспортное средство'");

    ElementCollection unselectedHearts = new ElementCollection(By.xpath("//div/button[not(contains(@class,'oPrOS'))]/span[@aria-label='heart']"), "коллекция невыбранных сердец =(");
    Button selectedHeart = new Button(By.xpath("//div/button[contains(@class, 'oPrOS')]"), "кнопка с выбранным сердцем");

    ElementCollection deleteButton = new ElementCollection(By.xpath("//button[@alt='Удалить']"), "коллекция кнопок удаления");

    Element information = new Element(ByKraken.xpathExpression("//span[text()='%s']/parent::th/parent::tr/following-sibling::tr/td/span"), "получения информации из табличек");
    Element tab = new Element(ByKraken.xpathExpression("//div[@role='tab' and contains(text(), '%s')]"), "вкладка с настройками пользователя");

    Input newVehiclesModel = new Input(By.xpath("//input[@name='newVehicles.0.model']"), "поле 'Модель' для нового транспорта");
    Input newVehiclesNumber = new Input(By.xpath("//input[@name='newVehicles.0.number']"), "поле 'Номер' для нового транспорта");
    Input newVehiclesVolume = new Input(By.xpath("//input[@name='newVehicles.0.volume']"), "поле 'Грузоподъемность' для нового транспорта");

    Input editableVehiclesModel = new Input(By.xpath("//input[@name='editableVehicles.0.model']"), "поле 'Модель' для редактируемого транспорта");
    Input editableVehiclesNumber = new Input(By.xpath("//input[@name='editableVehicles.0.number']"), "поле 'Номер' для редактируемого транспорта");
    Input editableVehiclesVolume = new Input(By.xpath("//input[@name='editableVehicles.0.volume']"), "поле 'Грузоподъемность' для редактируемого транспорта");
}
