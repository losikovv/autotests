package ru.instamart.application.rest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.rest.objects.Product;
import ru.instamart.application.rest.objects.Retailer;
import ru.instamart.application.rest.objects.Store;
import ru.instamart.application.rest.objects.Zone;

import java.util.ArrayList;
import java.util.List;

public class RestDataProvider extends RestHelper {

    public RestDataProvider(EnvironmentData environment) {
        super(environment);
    }

    @Test()
    public void selfTestRetailers() {
        RestDataProvider.getAvailableRetailers();
    }
    @DataProvider(name = "retailers")
    public static Object[][] getAvailableRetailers() {

        List<Retailer> retailerList = availableRetailers();

        Object[][] retailerArray = new Object[retailerList.size()][1];

        for (int i = 0; i < retailerList.size(); i++) {
            retailerArray[i][0] = retailerList.get(i).getSlug();
        }
        return retailerArray;
    }

    @Test()
    public void selfTestRetailersV1() {
        RestDataProvider.getAvailableRetailersV1();
    }
    @DataProvider(name = "retailersV1")
    public static Object[][] getAvailableRetailersV1() {

        List<Retailer> retailerList = availableRetailersV1();

        Object[][] retailerArray = new Object[retailerList.size()][2];

        for (int i = 0; i < retailerList.size(); i++) {
            retailerArray[i][0] = retailerList.get(i).getSlug();
            retailerArray[i][1] = retailerList.get(i).getAvailable();
        }
        return retailerArray;
    }

    @Test()
    public void selfTestStores() {
        RestDataProvider.getAvailableStores();
    }
    @DataProvider(name = "stores")
    public static Object[][] getAvailableStores() {

        List<Store> storeList = availableStores();

        Object[][] storeArray = new Object[storeList.size()][2];

        for (int i = 0; i < storeList.size(); i++) {
            storeArray[i][0] = storeList.get(i).getName();
            storeArray[i][1] = storeList.get(i).getId();
        }
        return storeArray;
    }

    @DataProvider(name = "zones")
    public static Object[][] getAvailableZones() {

        List<Store> stores = availableStores();

        List<Store> newStores  = new ArrayList<>();
        List<String> zoneNames = new ArrayList<>();
        List<Zone> coordinates = new ArrayList<>();

        for (Store store : stores) {
            List<List<Zone>> zones = store.getZones();
            for (int i = 0; i < zones.size(); i++) {
                Store newStore = new Store();
                newStore.setId(store.getId());
                newStore.setName(store.getName());

                newStores.add(newStore);
                zoneNames.add("Зона #" + (i + 1));
                coordinates.add(getInnerPoint(zones.get(i)));
            }
        }
        Object[][] zoneArray = new Object[newStores.size()][5];

        for (int i = 0; i < newStores.size(); i++) {
            zoneArray[i][0] = newStores.get(i).getName();
            zoneArray[i][1] = newStores.get(i).getId();
            zoneArray[i][2] = zoneNames.get(i);
            zoneArray[i][3] = coordinates.get(i).getLat();
            zoneArray[i][4] = coordinates.get(i).getLon();
        }
        return zoneArray;
    }

    @Test()
    public void selfTestProducts() {
        RestDataProvider.getMainPageProducts();
    }
    @DataProvider(name = "products")
    public static Object[][] getMainPageProducts() {

        List<Store> storeList = availableStores();
        List<Product> productList = new ArrayList<>();

        for (Store store : storeList) {
            productList.addAll(getProductsFromEachDepartmentInStore(
                    store.getId(),
                    1,
                    true));
        }
        Object[][] productArray = new Object[productList.size()][2];

        for (int i = 0; i < productList.size(); i++) {
            productArray[i][0] = productList.get(i).getName();
            productArray[i][1] = productList.get(i).getId();
        }
        return productArray;
    }
}
