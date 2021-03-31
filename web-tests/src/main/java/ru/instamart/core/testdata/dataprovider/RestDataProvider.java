package instamart.core.testdata.dataprovider;

import instamart.api.common.RestAddresses;
import instamart.api.common.RestBase;
import instamart.api.common.Specification;
import instamart.api.enums.v2.AuthProvider;
import instamart.api.objects.v1.Offer;
import instamart.api.objects.v1.OperationalZone;
import instamart.api.objects.v2.Retailer;
import instamart.api.objects.v2.Store;
import instamart.api.objects.v2.Zone;
import instamart.api.requests.ApiV1Requests;
import instamart.api.requests.v2.StoresRequest;
import instamart.api.responses.v1.OperationalZonesResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestDataProvider extends RestBase {

    @DataProvider(name = "authProviders")
    public static Object[][] getAuthProviders() {
        Object[][] authProviderArray = new Object[4][1];
        authProviderArray[0][0] = AuthProvider.METRO;
        authProviderArray[1][0] = AuthProvider.SBERAPP;
        authProviderArray[2][0] = AuthProvider.VKONTAKTE;
        authProviderArray[3][0] = AuthProvider.FACEBOOK;
        return authProviderArray;
    }

    @Test()
    public static void selfTestRetailers() {
        getAvailableRetailers();
    }

    @DataProvider(name = "retailers")
    public static Object[][] getAvailableRetailers() {
        Specification.setResponseSpecDataProvider();

        List<Retailer> retailerList = apiV2.availableRetailers();

        Specification.setResponseSpecDefault();

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

    @DataProvider(name = "retailersSpree-parallel", parallel = true)
    public static Object[][] getAvailableRetailersSpreeParallel() {
        return getAvailableRetailersSpree();
    }

    @DataProvider(name = "retailersSpree")
    public static Object[][] getAvailableRetailersSpree() {
        Specification.setResponseSpecDataProvider();

        List<Retailer> retailerList = apiV2.availableRetailersSpree();

        Specification.setResponseSpecDefault();

        Object[][] retailerArray = new Object[retailerList.size()][1];
        for (int i = 0; i < retailerList.size(); i++) {
            retailerArray[i][0] = retailerList.get(i);
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
        Specification.setResponseSpecDataProvider();

        List<Store> storeList = apiV2.availableStores();
        Specification.setResponseSpecDefault();

        Object[][] storeArray = new Object[storeList.size()][1];
        for (int i = 0; i < storeList.size(); i++) {
            storeArray[i][0] = storeList.get(i);
        }
        return storeArray;
    }

    @DataProvider(name = "storeOfEachRetailer-parallel", parallel = true)
    public static Object[][] getStoreOfEachRetailerParallel() {
        return getStoreOfEachRetailer();
    }

    @DataProvider(name = "storeOfEachRetailer")
    public static Object[][] getStoreOfEachRetailer() {
        Specification.setResponseSpecDataProvider();

        List<Store> storeList = apiV2.availableRetailers()
                .stream().parallel()
                .map(apiV2::availableStores)
                .map(retailerStores -> retailerStores.get(retailerStores.size() - 1))
                .collect(Collectors.toList());

        Specification.setResponseSpecDefault();

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
        Specification.setResponseSpecDataProvider();

        List<Store> stores = apiV2.availableStores();

        List<Store> zoneStores = new ArrayList<>();
        List<String> zoneNames = new ArrayList<>();
        List<Zone> coordinates = new ArrayList<>();

        for (Store store : stores) {
            List<List<Zone>> zones = apiV2.storeZones(store.getId());
            for (int i = 0; i < zones.size(); i++) {
                zoneStores.add(store);
                zoneNames.add("Зона #" + (i + 1));
                coordinates.add(apiV2.getInnerPoint(zones.get(i)));
            }
        }
        Specification.setResponseSpecDefault();

        Object[][] zoneArray = new Object[zoneStores.size()][3];
        for (int i = 0; i < zoneStores.size(); i++) {
            zoneArray[i][0] = zoneStores.get(i);
            zoneArray[i][1] = zoneNames.get(i);
            zoneArray[i][2] = coordinates.get(i);
        }
        return zoneArray;
    }

    @DataProvider(name = "operationalZones", parallel = true)
    public static Object[][] getOperationalZones() {
        Specification.setResponseSpecDataProvider();

        List<OperationalZone> operationalZoneList = ApiV1Requests.OperationalZones.GET()
                .as(OperationalZonesResponse.class).getOperationalZones();

        Specification.setResponseSpecDefault();

        Object[][] operationalZoneArray = new Object[operationalZoneList.size()][1];
        for (int i = 0; i < operationalZoneList.size(); i++) {
            operationalZoneArray[i][0] = operationalZoneList.get(i);
        }
        return operationalZoneArray;
    }

    @Test()
    public static void selfTestOfferOfEachRetailer() {
        getOfferOfEachRetailer();
    }

    @DataProvider(name = "offerOfEachRetailer-parallel", parallel = true)
    public static Object[][] getOfferOfEachRetailerParallel() {
        return getOfferOfEachRetailer();
    }

    @DataProvider(name = "offerOfEachRetailer")
    public static Object[][] getOfferOfEachRetailer() {
        Specification.setResponseSpecDataProvider();

        List<Store> storeList = apiV2.availableRetailers()
                .stream().parallel()
                .map(apiV2::availableStores)
                .map(retailerStores -> retailerStores.get(retailerStores.size() - 1))
                .collect(Collectors.toList());

        List<Offer> offerList = storeList
                .stream().parallel()
                .map(store -> apiV2.getOffers(store.getUuid()))
                .filter(storeOffers -> !storeOffers.isEmpty())
                .map(storeOffers -> storeOffers.get(0))
                .collect(Collectors.toList());

        Specification.setResponseSpecDefault();

        Object[][] offerArray = new Object[offerList.size()][1];
        for (int i = 0; i < offerList.size(); i++) {
            offerArray[i][0] = offerList.get(i);
        }
        return offerArray;
    }

    @DataProvider(name = "getStores")
    public static Object[][] getStores() {
        return new Object[][] {
                {StoresRequest.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .build()
                },
                {StoresRequest.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .build()
                },
                {StoresRequest.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .shippingMethod("pickup")
                        .build()
                },
                {StoresRequest.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .shippingMethod("by_courier")
                        .build()
                },
                {StoresRequest.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .shippingMethod("fake")
                        .build()
                },
                {StoresRequest.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .operationalZoneId(1)
                        .build()
                },
                {StoresRequest.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .operationalZoneId(99999999)
                        .build()
                }
        };
    }
}
