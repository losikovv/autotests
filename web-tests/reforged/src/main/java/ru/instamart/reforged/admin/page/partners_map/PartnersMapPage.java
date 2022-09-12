package ru.instamart.reforged.admin.page.partners_map;

import ru.instamart.reforged.admin.AdminPage;

public class PartnersMapPage implements AdminPage, PartnersMapCheck {

    @Override
    public String pageUrl() {
        return "partners_map?query=%s";
    }

    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу редактирования заказа необходимо использовать метод с параметрами");
    }
}
