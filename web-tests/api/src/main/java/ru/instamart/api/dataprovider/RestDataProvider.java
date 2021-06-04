package ru.instamart.api.dataprovider;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.model.v1.OfferV1;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.api.model.v2.ZoneV2;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.request.v2.AddressesV2Request.Addresses;
import ru.instamart.api.request.v2.StoresV2Request;
import ru.instamart.api.response.v1.OperationalZonesV1Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestDataProvider extends RestBase {

    @DataProvider(name = "authProviders")
    public static Object[][] getAuthProviders() {
        Object[][] authProviderArray = new Object[4][1];
        authProviderArray[0][0] = AuthProviderV2.METRO;
        authProviderArray[1][0] = AuthProviderV2.SBERAPP;
        authProviderArray[2][0] = AuthProviderV2.VKONTAKTE;
        authProviderArray[3][0] = AuthProviderV2.FACEBOOK;
        return authProviderArray;
    }

    @DataProvider(name = "query")
    public static Object[][] getQuery() {
        Object[][] queryArray = new Object[7][3];
        queryArray[0] = new Object[]{1, "хлеб", 200};
        queryArray[1] = new Object[]{0, "хлеб", 404};
        queryArray[2] = new Object[]{1, "", 200};
        queryArray[3] = new Object[]{1, "хлеб; DROP TABLE Offers", 200};
        queryArray[4] = new Object[]{1, "!@#$%^&*()", 200};
        queryArray[5] = new Object[]{1, "а", 200};
        queryArray[6] = new Object[]{1, RandomStringUtils.randomAlphabetic(8140), 200};
        //queryArray[7] = new Object[]{1, RandomStringUtils.randomAlphabetic(8141), 414};
        //пока не можем проверить, так как в теле ответа текст вместо json
        return queryArray;
    }

    @Test()
    public static void selfTestRetailers() {
        getAvailableRetailers();
    }

    @DataProvider(name = "retailers")
    public static Object[][] getAvailableRetailers() {
        Specification.setResponseSpecDataProvider();

        List<RetailerV2> retailerList = apiV2.availableRetailers();

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

    @DataProvider(name = "retailersSpree", parallel = true)
    public static Object[][] getAvailableRetailersSpree() {
        Specification.setResponseSpecDataProvider();

        List<RetailerV2> retailerList = apiV2.availableRetailersSpree();

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

        List<StoreV2> storeList = apiV2.availableStores();
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

    @DataProvider(name = "storeOfEachRetailer", parallel = true)
    public static Object[][] getStoreOfEachRetailer() {
        Specification.setResponseSpecDataProvider();

        List<StoreV2> storeList = apiV2.availableRetailers()
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

        List<StoreV2> stores = apiV2.availableStores();

        List<StoreV2> zoneStores = new ArrayList<>();
        List<String> zoneNames = new ArrayList<>();
        List<ZoneV2> coordinates = new ArrayList<>();

        for (StoreV2 store : stores) {
            List<List<ZoneV2>> zones = apiV2.storeZones(store.getId());
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

        List<OperationalZoneV1> operationalZoneList = OperationalZonesV1Request.GET()
                .as(OperationalZonesV1Response.class).getOperationalZones();

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

        List<StoreV2> storeList = apiV2.availableRetailers()
                .stream().parallel()
                .map(apiV2::availableStores)
                .map(retailerStores -> retailerStores.get(retailerStores.size() - 1))
                .collect(Collectors.toList());

        List<OfferV1> offerList = storeList
                .stream().parallel()
                .map(store -> apiV2.getActiveOffers(store.getUuid()))
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
                {StoresV2Request.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .shippingMethod("pickup")
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .shippingMethod("by_courier")
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .shippingMethod("fake")
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .operationalZoneId(1)
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .operationalZoneId(99999999)
                        .build()
                }
        };
    }

    @DataProvider(name = "getAddresses")
    public static Object[][] getAddresses() {
        return new Object[][] {
                {Addresses.builder()
                        .firstName("")
                        .build()
                },
                {Addresses.builder()
                        .lastName("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .fullAddress("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .city("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .street("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .building("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .block("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .entrance("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .floor("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .apartment("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .comments("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .lon("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .lat("<script>alert()</script>")
                        .build()
                },
                {Addresses.builder()
                        .doorPhone("<script>alert()</script>")
                        .build()
                }
        };
    }



    @DataProvider(name = "deliveryAvailabilityV2TestData")
    public static Object[][] deliveryAvailabilityV2TestData() {
        return new Object[][]{
                {"", ""}, //Координаты не указаны
                {"55.658228", ""}, //Указан lat
                {"", "37.748818"} //Указан lon
        };
    }
}
