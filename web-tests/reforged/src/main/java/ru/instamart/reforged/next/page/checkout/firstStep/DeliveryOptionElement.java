package ru.instamart.reforged.next.page.checkout.firstStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.RadioButton;

public interface DeliveryOptionElement {

    Button delivery = new Button(By.xpath("//button[@data-qa='checkout_form_shipping_kind_by_courier']"), "кнопка Доставка");
    Button pickUp = new Button(By.xpath("//button[@data-qa='checkout_form_shipping_kind_pickup']"), "кнопка Самовывоз");
    RadioButton forSelf = new RadioButton(By.xpath("//input[@data-qa='checkout_form_kind_home_radio_button']"), "радиобаттон Для себя");
    RadioButton forBusiness = new RadioButton(By.xpath("//input[@data-qa='checkout_form_kind_office_radio_button']"), "радиобаттон Для бизнеса");
    Button addCompany = new Button(By.xpath("//button[@data-qa='checkout_add_company_button']"), "кнопка Добавить компанию");
    Input apartment = new Input(By.xpath("//input[@name='apartment']"), "поле Номер квартиры/офис");
    Input floor = new Input(By.xpath("//input[@name='floor']"), "поле Этаж");
    Checkbox elevator = new Checkbox(By.xpath("//input[@data-qa='ship_address_form_elevator_checkbox']"), "чекбокс Есть лифт");
    Input entrance = new Input(By.xpath("//input[@name='entrance']"), "поле Подъезд");
    Input doorPhone = new Input(By.xpath("//input[@name='doorPhone']"), "поле Код домофона");
    Checkbox contactlessDelivery = new Checkbox(By.xpath("//input[@data-qa='ship_address_form_contactless_delivery_checkbox']"), "чекбокс бесплатной доставки");
    Input comments = new Input(By.xpath("//textarea[@id='FormGroup_comments']"), "поле Комментарии по доставке");

    Button submitStepWithDelivery = new Button(By.xpath("//button[@data-qa='checkout_ship_address_form_submit_button']"), "кнопка Продолжить, если выбрана Доставка");
    Button submitStepWithPickUp = new Button(By.xpath("//button[@data-qa=\"checkout_shipping_method_button'\"]"), "кнопка Продолжить, если выбран Самовывоз");
}