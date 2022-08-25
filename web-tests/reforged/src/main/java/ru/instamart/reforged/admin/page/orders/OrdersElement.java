package ru.instamart.reforged.admin.page.orders;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.table.OrdersTable;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.*;

public interface OrdersElement {

    OrdersTable tableComponent = new OrdersTable();

    Button regionsFilter = new Button(By.xpath("//button[@data-qa='open-regions-filter-button']"), "Кнопка 'Выберите регион для просмотра заказов на ручную маршрутизацию'");
    Input shipmentNumber = new Input(By.xpath("//input[@data-qa='shipment_number']"), "Поле ввода 'Номер заказа'");
    SelectorMulti shipmentStatusSelector = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Статус заказа')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Статус заказа'");
    Input shipmentCreateDateStart = new Input(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Создание заказа')]/following-sibling::div//input[@placeholder='Начальная дата']"), "Поле ввода 'Создание заказа -> Начальная дата'");
    Input shipmentCreateDateEnd = new Input(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Создание заказа')]/following-sibling::div//input[@placeholder='Конечная дата']"), "Поле ввода 'Создание заказа -> Конечная дата'");
    Element shipmentCreateDateTimePicker = new Element(By.xpath("//div[@class='ant-picker-datetime-panel']"), "Виджет выбора даты и времени создания заказа");
    Input shipmentDeliveryDateStart = new Input(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Доставка заказа')]/following-sibling::div//input[@placeholder='Начальная дата']"), "Поле ввода 'Создание заказа -> Начальная дата'");
    Input shipmentDeliveryDateEnd = new Input(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Доставка заказа')]/following-sibling::div//input[@placeholder='Конечная дата']"), "Поле ввода 'Создание заказа -> Конечная дата'");
    Element shipmentDeliveryDateTimePicker = new Element(By.xpath("(//div[@class='ant-picker-datetime-panel'])[2]"), "Виджет выбора даты и времени доставки заказа");
    Input totalWeightStart = new Input(By.xpath("//input[@data-qa='shipment_total_weight_start']"), "Поле ввода 'Вес заказа -> От'");
    Input totalWeightEnd = new Input(By.xpath("//input[@data-qa='shipment_total_weight_end']"), "Поле ввода 'Вес заказа -> До'");
    Input itemsCountStart = new Input(By.xpath("//input[@data-qa='shipment_item_count_start']"), "Поле ввода 'Кол-во позиций -> От'");
    Input itemsCountEnd = new Input(By.xpath("//input[@data-qa='shipment_item_count_end']"), "Поле ввода 'Кол-во позиций -> До'");
    SelectorMulti platformSelector = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Платформа')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Платформа'");
    SelectorMulti retailerSelector = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Ритейлер')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Ритейлер'");
    SelectorMulti basicStoreSelector = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Базовый магазин')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Базовый магазин'");
    SelectorMulti storeSelector = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Магазин')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Магазин'");
    SelectorMulti paymentMethodSelector = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Способ оплаты')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Способ оплаты'");
    SelectorMulti paymentStatusSelector = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Статус оплаты')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Статус оплаты'");
    Input promoCode = new Input(By.xpath("//input[@name='couponCode']"), "Поле ввода 'Промокод'");
    Input companyName = new Input(By.xpath("//input[@name='orderCompanyName']"), "Поле ввода 'Юридическое лицо'");
    Input companyINN = new Input(By.xpath("//input[@name='inn']"), "Поле ввода 'ИНН'");
    Input invoiceNumber = new Input(By.xpath("//input[@name='invoiceNumber']"), "Поле ввода 'Номер накладной'");
    Input collector = new Input(By.xpath("//div[contains(@class,'ant-form-item-label')][.='Сборщик']/following-sibling::div"), "Селектор поля фильтра 'Сборщик'");
    ElementCollection collectorDropdownList = new ElementCollection(By.xpath("//div[@id='rc_select_7_list']/following-sibling::div//div[contains(@class,'ant-select-item ')]"), "Выпадающий список селектора 'Сборщик'");
    Input courier = new Input(By.xpath("//div[contains(@class,'ant-form-item-label')][.='Курьер']/following-sibling::div"), "Селектор поля фильтра 'Курьер'");
    ElementCollection courierDropdownList = new ElementCollection(By.xpath("//div[@id='rc_select_8_list']/following-sibling::div//div[contains(@class,'ant-select-item ')]"), "Выпадающий список селектора 'Курьер'");
    Input phoneNumber = new Input(By.xpath("//input[@name='orderPhone']"), "Поле ввода 'Информация о клиенте -> Телефон'");
    Input lastName = new Input(By.xpath("//input[@name='lastName']"), "Поле ввода 'Информация о клиенте -> Фамилия'");
    Input firstName = new Input(By.xpath("//input[@name='firstName']"), "Поле ввода 'Информация о клиенте -> Имя'");
    Input email = new Input(By.xpath("//input[@name='email']"), "Поле ввода 'Информация о клиенте -> e-mail'");
    SelectorMulti regionSelector = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Регион')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Регион'");
    SelectorMulti collectingStatus = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Статус сборки')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Статус сборки'");
    SelectorMulti deliveryStatus = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Статус доставки')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Статус доставки'");
    SelectorMulti quickFilters = new SelectorMulti(By.xpath("//div[contains(@class,'ant-form-item-label')][contains(.,'Быстрые фильтры')]/following-sibling::div//div[contains(@class,'ant-select ')]"), "Селектор 'Быстрые фильтры'");
    Button resetFilters = new Button(By.xpath("//button[@data-qa='reset_shipments_filter_button']"), "Кнопка 'Сбросить'");
    Button applyFilters = new Button(By.xpath("//button[@data-qa='apply_shipments_filter_button']"), "Кнопка 'Применить фильтры'");

    Element searchResultCount = new Element(By.xpath("//div[contains(@aria-label,'shipments loaded')]/../preceding-sibling::div"), "Заголовок 'Найдено ... заказов'");
    Element shipmentsLoadingLabel = new Element(By.xpath("//div[contains(@aria-label,'loading')]"), "Лоадер загрузки/применения фильтра");
    Element emptyShipmentsListLabel = new Element(By.xpath("//div[contains(@class,'ant-empty-normal')]"), "Иконка пустого списка заказов");

    //дропдаун-меню доставки раскрывается при клике на номер доставки.
    Element shipmentDropdownMenu = new Element(By.xpath("//ul[contains(@class,'ant-dropdown-menu')]"), "Выпадающее меню доставки");
    Element dropdownMenuItemByName = new Element(ByKraken.xpathExpression("//ul[contains(@class,'ant-dropdown-menu')]/li[.//span[contains(.,'%s')]]"), "Пункт выпадающего меню доставки по названию");

    //Модалка подтверждения удаления назначения сборщика/доставщика
    Element removeAssignmentModal = new Element(By.xpath("//div[@class='ant-modal-content']"), "Модальное окно подтверждения удаления назначения");
    Input selectReason = new Input(By.xpath("//div[@class='ant-modal-content']//input"), "Поле ввода причины");

    //Для любого списка
    Element selectOptionByName = new Element(ByKraken.xpathExpression("//div[contains(@class,'ant-select-item ')][contains(.,'%s')]"), "Пункт выпадающего списка селектора по имени");

    Element shipment = new Element(By.xpath("//tr[contains(@class,'ant-table-row')]"), "Первый элемент таблицы");
}
