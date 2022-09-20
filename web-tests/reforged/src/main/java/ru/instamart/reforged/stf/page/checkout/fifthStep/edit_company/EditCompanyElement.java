package ru.instamart.reforged.stf.page.checkout.fifthStep.edit_company;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface EditCompanyElement {

    Element slotsSpinner = new Element(By.xpath("//div[@class = 'windows-selector-content']//div[contains(@class, 'Spinner')]"), "empty");

    Input name = new Input(By.xpath("//input[@name='requisites.name']"), "поле для ввода имени компании");
    Input address = new Input(By.xpath("//input[@name='requisites.address']"), "поле для ввода адреса компании");
    Input kpp = new Input(By.xpath("//input[@name='requisites.kpp']"), "поле для ввода кпп компании");

    Input consigneeName = new Input(By.xpath("//input[@name='requisites.companyConsigneeName']"), "поле для ввода имени компании-грузополучателя");
    Input consigneeAddress = new Input(By.xpath("//input[@name='requisites.companyConsigneeAddress']"), "поле для ввода адреса компании-грузополучателя");
    Input consigneeKpp = new Input(By.xpath("//input[@name='requisites.companyConsigneeKpp']"), "поле для ввода кпп компании-грузополучателя");

    Button save = new Button(By.xpath("//div[contains(@class, 'checkout-panel--active')]//div[@class='button-block']//button"), "Нажать кнопку 'сохранить'");

}
