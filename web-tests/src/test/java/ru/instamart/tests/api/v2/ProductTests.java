package ru.instamart.tests.api.v2;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.application.rest.Requests;
import ru.instamart.application.rest.RestBase;
import ru.instamart.application.rest.RestDataProvider;

public class ProductTests extends RestBase {

    @BeforeClass(description = "Проверка самих провайдеров")
    public void selfTest() {
        RestDataProvider.getMainPageProducts();
    }

    @Test(  dataProvider = "products",
            dataProviderClass = RestDataProvider.class,
            description = "Тест продуктов на главных страницах всех магазинов",
            groups = {})
    public void productsOnMainPages(String name, long id) {

        System.out.println(name);

        Requests.getProducts(id);
    }
}
