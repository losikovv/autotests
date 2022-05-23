package ru.instamart.reforged.next.page.landings.drivers_hiring;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Selector;

public interface DriversHiringElement {

    interface DriverJobForm {

        Button submitButton = new Button(By.xpath("//button[@name='btnSubmit']"),
                "кнопка отправки заявки на лендинге найма водителей Сбермаркета");

        Input nameField = new Input(By.xpath("//input[@name='full_name']"),
                "поле для ввода имени и фамилии");

        Selector citySelector = new Selector(By.id("city"),
                "селектор с городами");

        Selector countrySelector = new Selector(By.id("citizenship"),
                "селектор с доступными гражданствами");


        Input phoneField = new Input(By.xpath("//input[@name='phone_number']"),
                "поле для ввода номера телефона водителя");


        Element workConditions = new Element(By.xpath("//div[@class='driverJob__middleBlock']"),
                "блок с условиями работы");


        Element howToJoin = new Element(By.xpath("//h2[contains(text(),'команде просто')]"),
                "блок с описанием процесса трудоустройства");


        Element whatToDo = new Element(By.xpath("//div[text()='Что нужно делать?']"),
                "блок с кратким описанием обязанностей");

    }
}
