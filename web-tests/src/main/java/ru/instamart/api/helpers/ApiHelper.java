package instamart.api.helpers;

import instamart.api.objects.v2.Address;
import instamart.api.objects.v2.Order;
import instamart.api.objects.v2.Zone;
import instamart.ui.common.pagesdata.UserData;

import static instamart.ui.modules.Base.kraken;

public class ApiHelper {

    public ApiHelper() {
    }

    /**
     * Регистрация
     */
    public void registration(UserData user) {
        kraken.apiV2().registration(user);
    }

    /**
     * Регистрация
     */
    public void registration(String email, String firstName, String lastName, String password) {
        kraken.apiV2().registration(email, firstName, lastName, password);
    }

    /**
     * Поменять адрес у юзера
     */
    public void setAddress(UserData user, Address address) {
        kraken.apiV2().setAddress(user, address);
    }

    /**
     * Наполнить корзину и выбрать адрес по умолчанию у юзера по определенному адресу
     */
    public void fillCart(UserData user, Address address) {
        kraken.apiV2().fillCart(user, address);
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера по определенному адресу у определенного ритейлера
     */
    public void fillCart(UserData user, Address address, String retailer) {
        kraken.apiV2().fillCart(user, address, retailer);
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера в определенном магазине
     */
    public void fillCart(UserData user, int sid) {
        kraken.apiV2().fillCart(user, sid);
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера в определенном магазине по определенным координатам
     */
    public void fillCart(UserData user, int sid, Zone coordinates) {
        kraken.apiV2().fillCart(user, sid, coordinates);
    }

    /**
     * Очистить корзину и выбрать адрес у юзера
     */
    public void dropCart(UserData user, Address address) {
        kraken.apiV2().dropCart(user, address);
    }

    /**
     * Оформить тестовый заказ у юзера по определенному адресу
     */
    public Order order(UserData user, Address address, String retailer) {
        return kraken.apiV2().order(user, address, retailer);
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине
     */
    public Order order(UserData user, int sid) {
        return kraken.apiV2().order(user, sid);
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине по определенным координатам
     */
    public Order order(UserData user, int sid, Zone coordinates) {
        return kraken.apiV2().order(user, sid, coordinates);
    }

    /**
     * Отменить последний заказ (с которым взаимодействовали в этой сессии через REST API)
     */
    public void cancelCurrentOrder() {
        kraken.apiV2().cancelCurrentOrder();
    }

    /**
     * Отменить активные (принят, собирается, в пути) заказы (у юзера, с которым взаимодействовали в этой сессии через REST API)
     */
    public void cancelActiveOrders() {
        kraken.apiV2().cancelActiveOrders();
    }

}
