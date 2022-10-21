package ru.instamart.reforged.admin.page.shoppers.tags.delete_tag;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public final class DeleteTagModal {

    Element modal = new Element(By.xpath("//div[@class='ant-modal-content']"), "Модалка удаления тега");
    Button cancel = new Button(By.xpath("//div[@class='ant-modal-content']//button[contains(@class,'ant-btn-default')]"), "Кнопка 'Отмена'");
    Button approve = new Button(By.xpath("//div[@class='ant-modal-content']//button[contains(@class,'ant-btn-primary')]"), "Кнопка 'OK'");

    @Step("Проверяем, что модальное окно удаления тега отображается")
    public void checkModalVisible() {
        modal.should().visible();
    }

    @Step("Проверяем, что модальное окно удаления тега не отображается")
    public void checkModalNotVisible() {
        modal.should().invisible();
    }

    @Step("Нажимаем на кнопку отмены удаления")
    public void clickOnCancelButton() {
        cancel.click();
    }

    @Step("Нажимаем на кнопку подтверждения удаления")
    public void clickOnApproveButton() {
        approve.click();
    }


}
