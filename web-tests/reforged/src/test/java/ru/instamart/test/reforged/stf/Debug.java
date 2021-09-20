package ru.instamart.test.reforged.stf;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.SneakyThrows;
import org.testng.annotations.Test;
import ru.instamart.kraken.data_provider.DataList;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;
import ru.instamart.reforged.stf.page.StfRouter;

import java.util.List;

public final class Debug {

    @JsonDataProvider(path = "data/test.json", type = Root.class)
    @Test(dataProviderClass = JsonProvider.class, dataProvider = "json")
    public void foo(final UserData userData) {
        System.out.println(userData);
    }

    @Data
    public static final class UserData {
        public String name;
        public int age;
        public String city;
        public boolean married;
    }

    @Data
    public static final class Root implements DataList<UserData> {
        @JsonProperty("UserData")
        public List<UserData> data;
    }

    @SneakyThrows
    @Test
    public void bar() {
        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().fillPhone("79999999999");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.shop().interactHeader().clickToProfile();
        StfRouter.home().goToPage();
    }

    @SneakyThrows
    @Test
    public void bar2() {
        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
    }

    @SneakyThrows
    @Test
    public void bar3() {
        String retailerName = "METRO";
        String callAndReplace = "Позвонить мне. Подобрать замену, если не смогу ответить";
        String callAndRemove = "Позвонить мне. Убрать из заказа, если не смогу ответить";
        String noCallAndReplace = "Не звонить мне. Подобрать замену";
        String noCallAndRemove = "Не звонить мне. Убрать из заказа";
        //9999919613

        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
//        StfRouter.home().interactAuthModal().fillPhone("9999919613");
//        StfRouter.home().interactAuthModal().sendSms();
//        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.home().interactAuthModal().fillPhone("9999919613");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.shop().interactHeader().clickToCategoryMenu();
        StfRouter.shop().interactCategoryMenu().moveOnCategory("Постное меню");
        StfRouter.shop().interactCategoryMenu().clickToCategoryByName("Растительные масла");
    }
}
