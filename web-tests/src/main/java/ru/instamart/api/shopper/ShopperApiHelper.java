package instamart.api.shopper;

import instamart.api.shopper.objects.AssemblyData;
import instamart.api.shopper.objects.ShipmentData;
import instamart.api.shopper.responses.SessionsResponse;
import instamart.api.shopper.responses.ShipmentsResponse;
import instamart.api.shopper.responses.AssembliesResponse;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;
import java.util.StringJoiner;

public class ShopperApiHelper extends ShopperApiRequests {

    public ShopperApiHelper() {
    }

    /**
     * Авторизация
     */
    public void authorisation(String userName, String password) {
        token = postSessions(userName, password)
                .as(SessionsResponse.class)
                .getData()
                .getAttributes()
                .getAccessToken();
        System.out.println("Авторизуемся: " + userName + " / " + password);
        System.out.println("access_token: " + token + "\n");
    }

    public void authorisation(UserData user) {
        authorisation(user.getLogin(), user.getPassword());
    }

    public boolean authorized() {
        return token != null;
    }

    /**
     * Получаем shipment id по shipment number
     */
    private String getShipmentIdIteration(String shipmentNumber) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String shipmentId = null;
        Response response = getShopperShipments();
        List<ShipmentData> shipments = response.as(ShipmentsResponse.class).getData();

        StringJoiner availableShipmentNumbers = new StringJoiner(
                "\n• ",
                "Список доступных для сборки заказов:\n• ",
                "\n");
        for (ShipmentData shipment : shipments) {
            String number = shipment.getAttributes().getNumber();
            if (shipment.getAttributes().getNumber().equalsIgnoreCase(shipmentNumber)) {
                shipmentId = shipment.getId();
                availableShipmentNumbers.add(number + " <<< Выбран");
            } else availableShipmentNumbers.add(number);
        }
        System.out.println(availableShipmentNumbers);
        return shipmentId;
    }

    public String getShipmentId(String shipmentNumber) {
        String shipmentId;
        String error = "Оформленного заказа нет в списке " + shipmentNumber;
        for (int i = 0; i < 6; i++) {
            shipmentId = getShipmentIdIteration(shipmentNumber);
            if (shipmentId != null) {
                return shipmentId;
            } else System.err.println(error);
        }
        Assert.fail(error);
        return null;
    }

    /**
     * Получаем assembly id взятого в сборку заказа
     */
    public String getCurrentAssemblyId() {
        Response response = getShopperAssemblies();
        List<AssemblyData> assemblies = response.as(AssembliesResponse.class).getData();
        if (assemblies.size() > 0) {
            System.out.println("Получаем id собираемой сборки: " + assemblies.get(0).getId());
            return assemblies.get(0).getId();
        } else {
            System.out.println("Нет собираемой сборки");
            return null;
        }
    }

    /**
     * Удаляем текущую сборку
     */
    public void deleteCurrentAssembly() {
        String currentAssemblyId = getCurrentAssemblyId();
        if (currentAssemblyId != null) {
            System.out.println("Удаляем сборку: " + currentAssemblyId);
            deleteAssembly(currentAssemblyId);
        }
    }
}
