package ru.instamart.reforged.stf.frame.product_card;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;

public interface ProductCardElement {

    RetailRocket retailRocket = new RetailRocket();

    Element categoriesBreadscrumbs = new Element(By.xpath("//div[@itemtype='http://schema.org/BreadcrumbList']"), "Навигационная цепочка категорий (хлебные крошки)");
    Element image = new Element(By.xpath("//div[@itemtype='http://schema.org/Product']//img"), "Изображение товара");
    Element discountLabel = new Element(By.xpath("//div[@itemtype='http://schema.org/Product']//h1/preceding-sibling::ul/li[contains(.,'%')]"), "Флажок с указанием процента скидки (для товара со скидкой)");
    Element itemName = new Element(By.xpath("//h1[@itemprop='name']"), "название продукта");
    Element favorite = new Element(By.xpath("//div[@data-qa='addToCart_favorite']"), "кнопка добавления в избранное");
    Element packageSize = new Element(By.xpath("//div[@itemtype='http://schema.org/Offer']//p"), "Размер упаковки товара");
    ElementCollection prices = new ElementCollection(By.xpath("//div[@itemtype='http://schema.org/Offer']/div[1]/div"), "цены товара");
    Element price = new Element(By.xpath("//div[@itemtype='http://schema.org/Offer']/div[1]/div[1]"), "Цена товара (для товара без скидки)");
    Element priceWithDiscount = new Element(By.xpath("//div[@itemtype='http://schema.org/Offer']/div[1]/div[2]"), "Цена товара со скидкой (для товара со скидкой)");
    Element stockRateDescription = new Element(By.xpath("//div[@class='stock-rate__description']"), "количество продукта");
    Button buy = new Button(By.xpath("//button[@data-qa='addToCart_buy_button']"), "добавить продукт в корзину");
    Button decrease = new Button(By.xpath("//button[@data-qa='addToCart_minus']"), "уменьшить количество продуктов в корзине");
    Button increase = new Button(By.xpath("//button[@data-qa='addToCart_plus']"), "увеличить количество продуктов в корзине");

    Element description = new Element(By.xpath("//div[@itemprop='description']/div"), "описание продукта");
    Element generalInformationBlock = new Element(By.xpath("//ul[@class='product-properties']"), "Блок 'Общая информация'");
    Element hideDetInfo = new Element(By.xpath("//div[contains(text(),'Скрыть подробную информацию')]"), "скрыть подробную информацию");
    Element showDetInfo = new Element(By.xpath("//div[contains(text(),'Показать всю информацию')]"), "отобразить подробную информацию");

    Element relatedProducts = new Element(By.xpath("//div[@class='widgettitle'][contains(text(),'Похожие товары')]"), "похожие товары");
    Button relatedProductsNext = new Button(By.xpath("//div[@data-template-param-header-text='С этим смотрят']//button[contains(@class, 'arrow--right')]"),
            "листать список вправо");
    Button relatedProductsPrev = new Button(By.xpath("//div[@data-template-param-header-text='С этим смотрят']//button[contains(@class, 'arrow--left')]"),
            "листать список влево");

    Element recentlyViewed = new Element(By.xpath("//div[@class='widgettitle'][contains(text(),'Вы недавно смотрели')]"), "недавно просмотренные товары");
    Element alcoAlert = new Element(By.xpath("//div[contains(text(), 'достигшим 18 лет')]"), "алерт алко 18+ на карточке товара");

    Element alcoStub = new Element(By.cssSelector("div[itemtype*=Product] > div > div > div > div > svg"), "картинка-заглушка алко 18+ на карточке товара");//TODO:исправить на более стабильный локатор

    Button reserveButton = new Button(By.xpath("//button[contains(text(), 'Зарезервировать') and @data-qa='addToCart_buy_button']"),
            "Кнопка 'Зарезервировать' на карточке товара алко");
}
