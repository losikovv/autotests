package instamart.api.helpers;

import instamart.api.objects.shopper.AssemblyData;
import instamart.api.objects.shopper.ShipmentData;
import instamart.api.requests.ShopperApiRequests;
import instamart.api.responses.shopper.AssembliesResponse;
import instamart.api.responses.shopper.SessionsResponse;
import instamart.api.responses.shopper.ShipmentsResponse;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.StringJoiner;

import static instamart.api.checkpoints.ShopperApiCheckpoints.assertStatusCode200;
import static instamart.ui.modules.Base.kraken;

public class ShopperApiHelper extends HelperBase {

    public ShopperApiHelper() {
    }

    /**
     * Авторизация
     */
    public void authorisation(String userName, String password) {
        ShopperApiRequests.token = ShopperApiRequests.postSessions(userName, password)
                .as(SessionsResponse.class)
                .getData()
                .getAttributes()
                .getAccessToken();
        System.out.println("Авторизуемся: " + userName + " / " + password);
        System.out.println("access_token: " + ShopperApiRequests.token + "\n");
    }

    public void authorisation(UserData user) {
        authorisation(user.getLogin(), user.getPassword());
    }

    public boolean authorized() {
        return ShopperApiRequests.token != null;
    }

    /**
     * Получаем shipment id по shipment number
     */
    private String getShipmentIdIteration(String shipmentNumber) {
        kraken.await().simply(10);
        String shipmentId = null;
        Response response = ShopperApiRequests.getShopperShipments();
        List<ShipmentData> shipments = response.as(ShipmentsResponse.class).getData();

        StringJoiner availableShipmentNumbers = new StringJoiner(
                "\n• ",
                "Список доступных для сборки заказов:\n• ",
                "\n");
        for (ShipmentData shipment : shipments) {
            String number = shipment.getAttributes().getNumber();
            if (shipment.getAttributes().getNumber().equalsIgnoreCase(shipmentNumber)) {
                shipmentId = shipment.getId();
                availableShipmentNumbers.add(greenText(number + " <<< Выбран"));
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
        Response response = ShopperApiRequests.getShopperAssemblies();
        assertStatusCode200(response);
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
            ShopperApiRequests.deleteAssembly(currentAssemblyId);
        }
    }

    /**
     * Создаем смену сборщику
     */
    public void createShopperShift() {
        Response response = ShopperApiRequests.postShopperOperationShifts(
                1,
                true,
                getTodayDateAndTime(),
                getNextDayDateAndTime(),
                55.751244,
                37.618423);
        response.prettyPeek();
    }
}
