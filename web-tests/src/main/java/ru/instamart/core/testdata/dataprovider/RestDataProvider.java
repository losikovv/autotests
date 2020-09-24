package instamart.core.testdata.dataprovider;

import instamart.core.testdata.AuthProviders;
import instamart.api.common.RestBase;
import instamart.api.objects.Retailer;
import instamart.api.objects.Store;
import instamart.api.objects.Zone;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static instamart.api.common.RestHelper.*;

public class RestDataProvider extends RestBase {

    @DataProvider(name = "authProviders")
    public static Object[][] getAuthProviders() {
        Object[][] authProviderArray = new Object[4][1];
        authProviderArray[0][0] = AuthProviders.Metro.ID;
        authProviderArray[1][0] = AuthProviders.SberApp.ID;
        authProviderArray[2][0] = AuthProviders.Vkontakte.ID;
        authProviderArray[3][0] = AuthProviders.Facebook.ID;
        return authProviderArray;
    }

    @Test()
    public static void selfTestRetailers() {
        getAvailableRetailers();
    }

    @DataProvider(name = "retailers")
    public static Object[][] getAvailableRetailers() {

        List<Retailer> retailerList = availableRetailers();

        Object[][] retailerArray = new Object[retailerList.size()][1];

        for (int i = 0; i < retailerList.size(); i++) {
            retailerArray[i][0] = retailerList.get(i);
        }
        return retailerArray;
    }

    @Test()
    public void selfTestRetailersV1() {
        getAvailableRetailersSpree();
    }

    @DataProvider(name = "retailersSpree")
    public static Object[][] getAvailableRetailersSpree() {

        List<Retailer> retailerList = availableRetailersSpree();

        Object[][] retailerArray = new Object[retailerList.size()][2];

        for (int i = 0; i < retailerList.size(); i++) {
            retailerArray[i][0] = retailerList.get(i).getSlug();
            retailerArray[i][1] = retailerList.get(i).getAvailable();
        }
        return retailerArray;
    }

    @Test()
    public void selfTestStores() {
        getAvailableStores();
    }

    @DataProvider(name = "stores-parallel", parallel = true)
    public static Object[][] getAvailableStoresParallel() {
        return getAvailableStores();
    }

    @DataProvider(name = "stores")
    public static Object[][] getAvailableStores() {

        List<Store> storeList = availableStoresWithoutZones();

        Object[][] storeArray = new Object[storeList.size()][1];

        for (int i = 0; i < storeList.size(); i++) {
            storeArray[i][0] = storeList.get(i);
        }
        return storeArray;
    }

    @Test()
    public static void selfTestZones() {
        getAvailableZones();
    }

    @DataProvider(name = "zones-parallel", parallel = true)
    public static Object[][] getAvailableZonesParallel() {
        return getAvailableZones();
    }

    @DataProvider(name = "zones")
    public static Object[][] getAvailableZones() {

        List<Store> stores = availableStores();

        List<Store> zoneStores = new ArrayList<>();
        List<String> zoneNames = new ArrayList<>();
        List<Zone> coordinates = new ArrayList<>();

        for (Store store : stores) {
            List<List<Zone>> zones = store.getZones();
            for (int i = 0; i < zones.size(); i++) {
                zoneStores.add(store);
                zoneNames.add("Зона #" + (i + 1));
                coordinates.add(getInnerPoint(zones.get(i)));
            }
        }
        Object[][] zoneArray = new Object[zoneStores.size()][3];

        for (int i = 0; i < zoneStores.size(); i++) {
            zoneArray[i][0] = zoneStores.get(i);
            zoneArray[i][1] = zoneNames.get(i);
            zoneArray[i][2] = coordinates.get(i);
        }
        return zoneArray;
    }
}
