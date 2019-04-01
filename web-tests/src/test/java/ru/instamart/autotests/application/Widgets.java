package ru.instamart.autotests.application;

import ru.instamart.autotests.models.WidgetData;

public class Widgets {

    public interface RetailRocket {

        interface MainPage {

            static WidgetData PopularItems() {
                return new WidgetData("RetailRocket", "Популярные товары", "rr-widget-5b3e38e397a525a4a460e8d4");
            }
            static WidgetData RecentlyViewed() {
                return new WidgetData("RetailRocket", "Вы недавно смотрели", "rr-widget-5b3e38ee97a525a4a460e8d5");
            }
        }
    }
}
