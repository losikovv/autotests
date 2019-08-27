package ru.instamart.autotests.application.libs;

import ru.instamart.autotests.appmanager.models.WidgetData;

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

        interface CatalogPage {

            static WidgetData CustomersChoice() {
                return new WidgetData("RetailRocket", "Выбор покупателей","rr-widget-5b436c9e97a525ea0c642d7f");
            }
            static WidgetData RecentlyViewed() {
                return new WidgetData("RetailRocket", "Вы недавно смотрели", "rr-widget-5b436cb397a525ea0c642d80");
            }
        }

        interface ItemCard {

            static WidgetData WithThisItemBuy() {
                return new WidgetData("RetailRocket", "С этим товаром покупают", "rr-widget-5b436cd097a5289cc80b3fcf");
            }
            static WidgetData SimilarItems() {
                return new WidgetData("RetailRocket", "Похожие товары", "rr-widget-5b436f1d97a525ea0c642dbb");
            }
            static WidgetData RecentlyViewed() {
                return new WidgetData("RetailRocket", "Вы недавно смотрели", "rr-widget-5b436f3b97a5289cc80b4019");
            }
        }

        interface Search {
            static WidgetData SimilarItems() {
                return new WidgetData("RetailRocket", "Мы нашли для вас похожие товары", "rr-widget-5b43767f97a5289cc80b4388");
            }
            static WidgetData FindersChoice() {
                return new WidgetData("RetailRocket", "Те, кто ищут выбирают", "rr-widget-5b4375fd97a5289cc80b437d");
            }
            static WidgetData RecentlyViewed() {
                return new WidgetData("RetailRocket", "Вы недавно смотрели", "rr-widget-5b43760697a5289cc80b437e");
            }
        }

        interface Cart {
            static WidgetData DontForgetToBuy() {
                return new WidgetData( "RetailRocket","Не забудьте купить", "rr-widget-5b43769d97a5289cc80b47bf");
            }
        }

        interface SeoCatalog {
            static WidgetData CustomersChoice() {
                return new WidgetData("RetailRocket", "Выбор покупателей", "rr-widget-5b436cc297a5289cc80b3fce");
            }
        }
    }
}
