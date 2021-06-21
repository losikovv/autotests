package ru.instamart.reforged.stf.frame.checkout;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;

public class EditRequisites implements CommonFrameButtons {

    private final Input inn = new Input(By.xpath("//input[@name='requisites.inn']"));
    private final Input requisitesName = new Input(By.xpath("//input[@name='requisites.name']"));
    private final Input requisitesAddress = new Input(By.xpath("//input[@name='requisites.address']"));
    private final Checkbox needInvoice = new Checkbox(By.xpath("//div[@class='modal-form']//input[@type='checkbox']"));

    @Step("Заполнить поле Юридического лицо")
    public void fillRequisitesName(String data) {
        requisitesName.fill(data);
    }

    @Step("Заполнить поле Юридического лицо")
    public void fillRequisitesAddress(String data) {
        requisitesAddress.fill(data);
    }

    @Step("Выбрать чекбокс Необходима накладная ТОРГ-12")
    public void checkNeedInvoice(String data) {
        needInvoice.check();
    }

    @Step("Снять чекбокс Необходима накладная ТОРГ-12")
    public void uncheckNeedInvoice(String data) {
        needInvoice.uncheck();
    }
}
