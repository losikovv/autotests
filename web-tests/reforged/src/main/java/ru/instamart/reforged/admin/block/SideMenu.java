package ru.instamart.reforged.admin.block;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

@Getter
@Accessors(fluent = true)
public final class SideMenu {
    //TODO: dropdown elements
    private final Element storesDropdown = new Element(By.xpath("//div[@role='button']//*[text()='Магазины']"));
        private final Element regionsSideMenuElem  = new Element(By.xpath("//a[@title='Регионы']"));
        private final Element retailers  = new Element(By.xpath("//a[@title='Ритейлеры']"));
        private final Element shipmentArea  = new Element(By.xpath("//a[@title='Территория доставки']"));

    private final Element ordersDropdown = new Element(By.xpath("//div[@role='button']//*[text()='Заказы']"));
        private final Element logisticControl  = new Element(By.xpath("//a[@title='Управление логистикой']"));
        private final Element rootsParameters  = new Element(By.xpath("//a[@title='Параметры маршрутизации']"));
        private final Element multipleOrder  = new Element(By.xpath("//a[@title='Мульти заказ']"));

    private final Element ContentDropdown = new Element(By.xpath("//div[@role='button']//*[text()='Контент']"));
        private final Element products  = new Element(By.xpath("//a[@title='Продукты']"));
        private final Element goodsOptions  = new Element(By.xpath("//a[@title='Товарные опции']"));
        private final Element properties  = new Element(By.xpath("//a[@title='Свойства']"));
        private final Element brands  = new Element(By.xpath("//a[@title='Бренды']"));
        private final Element manufacturers  = new Element(By.xpath("//a[@title='Производители']"));
        private final Element manufacturersCountries  = new Element(By.xpath("//a[@title='Страны производства']"));
        private final Element categories  = new Element(By.xpath("//a[@title='Категории']"));
        private final Element contentImport  = new Element(By.xpath("//a[@title='Импорт']"));

    private final Element settings  = new Element(By.xpath("//a[@title='Настройки']"));

    private final Element marketing = new Element(By.xpath("//div[@role='button']//*[text()='Маркетинг']"));
        private final Element promoCards  = new Element(By.xpath("//a[@title='Промо карточки']"));
        private final Element promoActions = new Element(By.xpath("//a[@title='Промоакции']"));
        private final Element welcomeBanners  = new Element(By.xpath("//a[@title='Welcome баннеры']"));
        private final Element ads  = new Element(By.xpath("//a[@title='Реклама']"));
        private final Element carts  = new Element(By.xpath("//a[@title='Корзины']"));
        private final Element bonusCards  = new Element(By.xpath("//a[@title='Бонусные карты']"));
        private final Element retailerPrograms = new Element(By.xpath("//a[@title='Программы ритейлеров']"));
        private final Element referralProgram  = new Element(By.xpath("//a[@title='Реферальная программа']"));
        private final Element newCities  = new Element(By.xpath("//a[@title='Новые города']"));
        private final Element inAppBanners  = new Element(By.xpath("//a[@title='In app баннеры']"));
        private final Element bonusCount  = new Element(By.xpath("//a[@title='Бонусный счет']"));
        private final Element redirects  = new Element(By.xpath("//a[@title='Редиректы']"));
        private final Element sampling  = new Element(By.xpath("//a[@title='Семплинг']"));
        private final Element marketingCategories  = new Element(By.xpath("//a[@title='Маркетинговые категории']"));


    private final Element staff = new Element(By.xpath("//div[@role='button']//*[text()='Персонал']"));
        private final Element workSchedule  = new Element(By.xpath("//a[@title='Смены']"));
        private final Element tariffs  = new Element(By.xpath("//a[@title='Тарифы']"));
        private final Element collectors  = new Element(By.xpath("//a[@title='Сборщики']"));
        private final Element partnersInput  = new Element(By.xpath("//a[@title='Импорт партнеров']"));

    private final Element users  = new Element(By.xpath("//a[@title='Пользователи']"));

    private final Element pages  = new Element(By.xpath("//a[@title='Страницы']"));

    private final Element companies  = new Element(By.xpath("//a[@title='Компании']"));


}
