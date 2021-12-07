package ru.instamart.reforged.stf.page.user.companies;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.frame.EmailFrame;
import ru.instamart.reforged.stf.frame.FullName;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.checkout.subsections.create_company.AddCompany;

public interface UserCompaniesElement {

    FullName fullNameFrame = new FullName();
    EmailFrame emailFrame = new EmailFrame();
    AuthModal authModal = new AuthModal();
    AddCompany addCompanyModal = new AddCompany();

    Button buttonAddCompany = new Button(By.xpath("//button[@data-qa='user_companies_add_company_button']"), "кнопка добавления комании пользователю");

    Element alert = new Element(By.xpath("//span[@class='alert__msg' and text()='Данные успешно сохранены']"), "алерт о том что данные сохранились");

    Link userProfile = new Link(By.xpath("//a[@data-qa='user_companies_profile_link']"), "перейти в личный профиль пользователя");
}
