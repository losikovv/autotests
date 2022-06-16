package ru.instamart.test.reforged.stf;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.testng.annotations.Test;
import ru.instamart.kraken.data_provider.DataList;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.mock.Mock;
import ru.instamart.reforged.core.mock.MockRoutes;
import ru.instamart.reforged.stf.page.StfRouter;

import java.util.List;

public final class Debug {

    @Test
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"COOKIE_ALERT", "RETAILERS_REMINDER_MODAL", "EXTERNAL_ANALYTICS_ANONYMOUS_ID"})
    @Mock(routeMockClass = MockRoutes.class, route = "metroRout")
    public void bar() {
        StfRouter.home().goToPage();
        ThreadUtil.simplyAwait(5);
        StfRouter.shop().goToPage();
        ThreadUtil.simplyAwait(10);
    }

    @Data
    public static final class Root implements DataList<UserData> {
        @JsonProperty("UserData")
        public List<UserData> data;
    }

    @Data
    public static final class UserData {
        public String name;
        public int age;
        public String city;
        public boolean married;
    }

    @JsonDataProvider(path = "data/test.json", type = Root.class)
    @Test(dataProviderClass = JsonProvider.class, dataProvider = "json")
    public void foo(final UserData userData) {
        System.out.println(userData);
    }
}
