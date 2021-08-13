package ru.instamart.api.dataprovider;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.enums.v2.SippingMethodsV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.OfferV1;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.model.v2.*;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.request.v2.AddressesV2Request.Addresses;
import ru.instamart.api.request.v2.PersonalV2Request;
import ru.instamart.api.request.v2.SimpleAdsV2Request;
import ru.instamart.api.request.v2.SimpleRecsPersonalV2Request;
import ru.instamart.api.request.v2.StoresV2Request;
import ru.instamart.api.response.v1.OperationalZonesV1Response;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.instamart.api.common.RestAddresses.Moscow;
import static ru.instamart.api.common.RestAddresses.getDefaultAllAddress;

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

        List<RetailerV2> retailerList = apiV2.getAvailableRetailers();

        Specification.setResponseSpecDefault();

        return retailerList.stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "retailersSpree-parallel", parallel = true)
    public static Object[][] getAvailableRetailersSpreeParallel() {
        return getAvailableRetailersSpree();
    }

    @DataProvider(name = "retailersSpree", parallel = true)
    public static Object[][] getAvailableRetailersSpree() {
        Specification.setResponseSpecDataProvider();

        List<RetailerV2> retailerList = apiV2.getAvailableRetailersSpree();

        Specification.setResponseSpecDefault();

        return retailerList.stream()
                .filter(RetailerV2::getAvailable) //Фильтрует только доступных ретейлеров
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "stores-parallel", parallel = true)
    public static Object[][] getAvailableStoresParallel() {
        return getAvailableStores();
    }

    @DataProvider(name = "stores")
    public static Object[][] getAvailableStores() {
        Specification.setResponseSpecDataProvider();

        List<StoreV2> storeList = apiV2.getAvailableStores();
        Specification.setResponseSpecDefault();

        return storeList.stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "storeOfEachRetailer-parallel", parallel = true)
    public static Object[][] getStoreOfEachRetailerParallel() {
        return getStoreOfEachRetailer();
    }

    @DataProvider(name = "storeOfEachRetailer", parallel = true)
    public static Object[][] getStoreOfEachRetailer() {
        Specification.setResponseSpecDataProvider();

        List<StoreV2> storeList = apiV2.getAvailableRetailers()
                .stream().parallel()
                .map(apiV2::getAvailableStores)
                .map(retailerStores -> retailerStores.get(retailerStores.size() - 1))
                .collect(Collectors.toList());

        Specification.setResponseSpecDefault();

        return storeList.stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
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

        List<StoreV2> stores = apiV2.getAvailableStores();

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

        return operationalZoneList.stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
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

        List<StoreV2> storeList = apiV2.getAvailableRetailers()
                .stream().parallel()
                .map(apiV2::getAvailableStores)
                .map(retailerStores -> retailerStores.get(retailerStores.size() - 1))
                .collect(Collectors.toList());

        List<OfferV1> offerList = storeList
                .stream().parallel()
                .map(store -> apiV2.getActiveOffers(store.getUuid()))
                .filter(storeOffers -> !storeOffers.isEmpty())
                .map(storeOffers -> storeOffers.get(0))
                .collect(Collectors.toList());

        Specification.setResponseSpecDefault();

        return offerList.stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "getStores")
    public static Object[][] getStores() {
        return new Object[][]{
                {StoresV2Request.Store.builder()
                        .lat(Moscow.defaultAddress().getLat())
                        .lon(Moscow.defaultAddress().getLon())
                        .build(),
                        200
                },
                {StoresV2Request.Store.builder()
                        .lat(Moscow.defaultAddress().getLat())
                        .build(),
                        422
                },
                {StoresV2Request.Store.builder()
                        .lon(Moscow.defaultAddress().getLon())
                        .build(),
                        422
                },
                {StoresV2Request.Store.builder()
                        .lat(Moscow.defaultAddress().getLat())
                        .lon(Moscow.defaultAddress().getLon())
                        .shippingMethod("pickup")
                        .build(),
                        200
                },
                {StoresV2Request.Store.builder()
                        .lat(Moscow.defaultAddress().getLat())
                        .lon(Moscow.defaultAddress().getLon())
                        .shippingMethod("by_courier")
                        .build(),
                        200
                },
                {StoresV2Request.Store.builder()
                        .lat(Moscow.defaultAddress().getLat())
                        .lon(Moscow.defaultAddress().getLon())
                        .shippingMethod("fake")
                        .build(),
                        200
                },
                {StoresV2Request.Store.builder()
                        .lat(Moscow.defaultAddress().getLat())
                        .lon(Moscow.defaultAddress().getLon())
                        .operationalZoneId(1)
                        .build(),
                        200
                },
                {StoresV2Request.Store.builder()
                        .lat(Moscow.defaultAddress().getLat())
                        .lon(Moscow.defaultAddress().getLon())
                        .operationalZoneId(99999999)
                        .build(),
                        200
                },
        };
    }

    @DataProvider(name = "getAddresses")
    public static Object[][] getAddresses() {
        return new Object[][]{
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

    @DataProvider(name = "testNegativeSimpleRecsTest")
    public static Object[][] testNegativeSimpleRecsTest() {
        return new Object[][]{
                {
                        SimpleRecsPersonalV2Request.SimpleRecsV2.builder()
                                .context(SimpleRecsPersonalV2Request.Context.builder()
                                        .app(SimpleRecsPersonalV2Request.App.builder()
                                                .ext(SimpleRecsPersonalV2Request.AppExt.builder()
                                                        .tenantId(0)
                                                        .build())
                                                .domain("ru.sbermarket.new-app")
                                                .build())
                                        .user(SimpleRecsPersonalV2Request.User.builder()
                                                .geo(SimpleRecsPersonalV2Request.Geo.builder()
                                                        .lat(55.790447999999998D)
                                                        .lon(37.680517000000002D)
                                                        .build())
                                                .ext(SimpleRecsPersonalV2Request.UserExt.builder()
                                                        .anonymousId(UUID.randomUUID().toString())
                                                        .build())
                                                .build())
                                        .build())
                                .ext(
                                        SimpleRecsPersonalV2Request.Ext.builder()
                                                .place("main").build()
                                )
                                .build(),
                        "Отсутствует context.app.ext.store_id"
                },
                {
                        SimpleRecsPersonalV2Request.SimpleRecsV2.builder()
                                .context(SimpleRecsPersonalV2Request.Context.builder()
                                        .app(SimpleRecsPersonalV2Request.App.builder()
                                                .ext(SimpleRecsPersonalV2Request.AppExt.builder()
                                                        .storeId(1)
                                                        .tenantId(0)
                                                        .build())
                                                .build())
                                        .user(SimpleRecsPersonalV2Request.User.builder()
                                                .geo(SimpleRecsPersonalV2Request.Geo.builder()
                                                        .lat(55.790447999999998D)
                                                        .lon(37.680517000000002D)
                                                        .build())
                                                .ext(SimpleRecsPersonalV2Request.UserExt.builder()
                                                        .anonymousId(UUID.randomUUID().toString())
                                                        .build())
                                                .build())
                                        .build())
                                .ext(
                                        SimpleRecsPersonalV2Request.Ext.builder()
                                                .place("main").build()
                                )
                                .build(),
                        "Отсутствует context.app.domain"
                },
                {
                        SimpleRecsPersonalV2Request.SimpleRecsV2.builder()
                                .context(SimpleRecsPersonalV2Request.Context.builder()
                                        .app(SimpleRecsPersonalV2Request.App.builder()
                                                .ext(SimpleRecsPersonalV2Request.AppExt.builder()
                                                        .storeId(1)
                                                        .tenantId(0)
                                                        .build())
                                                .domain("ru.sbermarket.new-app")
                                                .build())
                                        .user(SimpleRecsPersonalV2Request.User.builder()
                                                .geo(SimpleRecsPersonalV2Request.Geo.builder()
                                                        .lat(55.790447999999998D)
                                                        .lon(37.680517000000002D)
                                                        .build())
                                                .build())
                                        .build())
                                .ext(
                                        SimpleRecsPersonalV2Request.Ext.builder()
                                                .place("main").build()
                                )
                                .build(),
                        "Отсутствует context.user.ext"
                },
                {
                        SimpleRecsPersonalV2Request.SimpleRecsV2.builder()
                                .context(SimpleRecsPersonalV2Request.Context.builder()
                                        .app(SimpleRecsPersonalV2Request.App.builder()
                                                .ext(SimpleRecsPersonalV2Request.AppExt.builder()
                                                        .storeId(1)
                                                        .tenantId(0)
                                                        .build())
                                                .domain("ru.sbermarket.new-app")
                                                .build())
                                        .user(SimpleRecsPersonalV2Request.User.builder()
                                                .geo(SimpleRecsPersonalV2Request.Geo.builder()
                                                        .lat(55.790447999999998D)
                                                        .lon(37.680517000000002D)
                                                        .build())
                                                .ext(SimpleRecsPersonalV2Request.UserExt.builder()
                                                        .anonymousId(UUID.randomUUID().toString())
                                                        .build())
                                                .build())
                                        .build())
                                .build(),
                        "Отсутствует ext"
                }
        };
    }

    @DataProvider(name = "testNegativeRecsTest")
    public static Object[][] testNegativeRecsTest() {
        return new Object[][]{
                {PersonalV2Request.RecsV2.builder()
                        .reqId(UUID.randomUUID().toString())
                        .placement(
                                PersonalV2Request.PlacementsItem.builder()
                                        .placementId(UUID.randomUUID().toString())
                                        .ext(PersonalV2Request.PlacementsExt.builder()
                                                .componentId(2)
                                                .order(1)
                                                .build())
                                        .build()
                        )
                        .context(
                                PersonalV2Request.Context.builder()
                                        .app(
                                                PersonalV2Request.App.builder()
                                                        .domain("ru.sbermarket.new-app")
                                                        .ver("1.0.0.1")
                                                        .ext(PersonalV2Request.SiteAndAppExt.builder()
                                                                .categoryId(1)
                                                                .storeId("1")
                                                                .build())
                                                        .build()
                                        )
                                        .user(PersonalV2Request.User.builder()
                                                .id(UUID.randomUUID().toString())
                                                .build())
                                        .build()
                        )
                        .build(),
                        "Отсутствует tmax"
                },
//               TODO:  task STF-8880
//                {
//                        PersonalV2Request.RecsV2.builder()
//                                .reqId(UUID.randomUUID().toString())
//                                .tmax(5000)
//                                .context(
//                                        PersonalV2Request.Context.builder()
//                                                .app(
//                                                        PersonalV2Request.App.builder()
//                                                                .domain("ru.sbermarket.new-app")
//                                                                .ver("1.0.0.1")
//                                                                .ext(PersonalV2Request.SiteAndAppExt.builder()
//                                                                        .categoryId(1)
//                                                                        .storeId("1")
//                                                                        .build())
//                                                                .build()
//                                                )
//                                                .user(PersonalV2Request.User.builder()
//                                                        .id(UUID.randomUUID().toString())
//                                                        .build())
//                                                .build()
//                                )
//                                .build(),
//                        "Отсутствует placements"
//                },
                {
                        PersonalV2Request.RecsV2.builder()
                                .reqId(UUID.randomUUID().toString())
                                .tmax(5000)
                                .placement(
                                        PersonalV2Request.PlacementsItem.builder()
                                                .ext(PersonalV2Request.PlacementsExt.builder()
                                                        .componentId(2)
                                                        .order(1)
                                                        .build())
                                                .build()
                                )
                                .context(
                                        PersonalV2Request.Context.builder()
                                                .app(
                                                        PersonalV2Request.App.builder()
                                                                .domain("ru.sbermarket.new-app")
                                                                .ver("1.0.0.1")
                                                                .ext(PersonalV2Request.SiteAndAppExt.builder()
                                                                        .categoryId(1)
                                                                        .storeId("1")
                                                                        .build())
                                                                .build()
                                                )
                                                .user(PersonalV2Request.User.builder()
                                                        .id(UUID.randomUUID().toString())
                                                        .build())
                                                .build()
                                )
                                .build(),
                        "Отсутствует placements.placement_id"
                },
                {
                        PersonalV2Request.RecsV2.builder()
                                .reqId(UUID.randomUUID().toString())
                                .tmax(5000)
                                .placement(
                                        PersonalV2Request.PlacementsItem.builder()
                                                .placementId(UUID.randomUUID().toString())
                                                .ext(PersonalV2Request.PlacementsExt.builder()
                                                        .order(1)
                                                        .build())
                                                .build()
                                )
                                .context(
                                        PersonalV2Request.Context.builder()
                                                .app(
                                                        PersonalV2Request.App.builder()
                                                                .domain("ru.sbermarket.new-app")
                                                                .ver("1.0.0.1")
                                                                .ext(PersonalV2Request.SiteAndAppExt.builder()
                                                                        .categoryId(1)
                                                                        .storeId("1")
                                                                        .build())
                                                                .build()
                                                )
                                                .user(PersonalV2Request.User.builder()
                                                        .id(UUID.randomUUID().toString())
                                                        .build())
                                                .build()
                                )
                                .build(),
                        "Отсутствует placements.ext.component_id"
                },
                {
                        PersonalV2Request.RecsV2.builder()
                                .reqId(UUID.randomUUID().toString())
                                .tmax(5000)
                                .placement(
                                        PersonalV2Request.PlacementsItem.builder()
                                                .placementId(UUID.randomUUID().toString())
                                                .ext(PersonalV2Request.PlacementsExt.builder()
                                                        .componentId(2)
                                                        .order(1)
                                                        .build())
                                                .build()
                                )
                                .context(
                                        PersonalV2Request.Context.builder()
                                                .user(PersonalV2Request.User.builder()
                                                        .id(UUID.randomUUID().toString())
                                                        .build())
                                                .build()
                                )
                                .build(),
                        "Отсутствует context.app"
                },
                {
                        PersonalV2Request.RecsV2.builder()
                                .reqId(UUID.randomUUID().toString())
                                .tmax(5000)
                                .placement(
                                        PersonalV2Request.PlacementsItem.builder()
                                                .placementId(UUID.randomUUID().toString())
                                                .ext(PersonalV2Request.PlacementsExt.builder()
                                                        .componentId(2)
                                                        .order(1)
                                                        .build())
                                                .build()
                                )
                                .context(
                                        PersonalV2Request.Context.builder()
                                                .app(
                                                        PersonalV2Request.App.builder()
                                                                .ver("1.0.0.1")
                                                                .ext(PersonalV2Request.SiteAndAppExt.builder()
                                                                        .categoryId(1)
                                                                        .storeId("1")
                                                                        .build())
                                                                .build()
                                                )
                                                .user(PersonalV2Request.User.builder()
                                                        .id(UUID.randomUUID().toString())
                                                        .build())
                                                .build()
                                )
                                .build(),
                        "Отсутствует context.app.domain"
                },
                {
                        PersonalV2Request.RecsV2.builder()
                                .reqId(UUID.randomUUID().toString())
                                .tmax(5000)
                                .placement(
                                        PersonalV2Request.PlacementsItem.builder()
                                                .placementId(UUID.randomUUID().toString())
                                                .ext(PersonalV2Request.PlacementsExt.builder()
                                                        .componentId(2)
                                                        .order(1)
                                                        .build())
                                                .build()
                                )
                                .context(
                                        PersonalV2Request.Context.builder()
                                                .app(
                                                        PersonalV2Request.App.builder()
                                                                .domain("ru.sbermarket.new-app")
                                                                .ver("1.0.0.1")
                                                                .build()
                                                )
                                                .user(PersonalV2Request.User.builder()
                                                        .id(UUID.randomUUID().toString())
                                                        .build())
                                                .build()
                                )
                                .build(),
                        "Отсутствует context.app.ext"
                },
                {
                        PersonalV2Request.RecsV2.builder()
                                .reqId(UUID.randomUUID().toString())
                                .tmax(5000)
                                .placement(
                                        PersonalV2Request.PlacementsItem.builder()
                                                .placementId(UUID.randomUUID().toString())
                                                .ext(PersonalV2Request.PlacementsExt.builder()
                                                        .componentId(2)
                                                        .order(1)
                                                        .build())
                                                .build()
                                )
                                .context(
                                        PersonalV2Request.Context.builder()
                                                .app(
                                                        PersonalV2Request.App.builder()
                                                                .domain("ru.sbermarket.new-app")
                                                                .ver("1.0.0.1")
                                                                .ext(PersonalV2Request.SiteAndAppExt.builder()
                                                                        .categoryId(1)
                                                                        .storeId("1")
                                                                        .build())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build(),
                        "Отсутствует context.user"
                }
        };
    }

    @DataProvider(name = "negativeSimpleAdsData")
    public static Object[][] negativeSimpleAdsData() {
        return new Object[][]{
                {
                        SimpleAdsV2Request.SimpleAdsV2.builder()
                                .context(SimpleAdsV2Request.Context.builder()
                                        .user(SimpleAdsV2Request.User.builder()
                                                .geo(SimpleAdsV2Request.Geo.builder()
                                                        .lat(55.790447999999998D)
                                                        .lon(37.680517000000002D)
                                                        .build()
                                                )
                                                .ext(SimpleAdsV2Request.UserExt.builder()
                                                        .anonymousId(UUID.randomUUID().toString())
                                                        .build()
                                                )
                                                .build()
                                        )
                                        .app(SimpleAdsV2Request.App.builder()
                                                .ext(SimpleAdsV2Request.AppExt.builder()
                                                        .tenantId(0)
                                                        .build()
                                                )
                                                .build()
                                        )
                                        .build()
                                )
                                .ext(SimpleAdsV2Request.Ext.builder()
                                        .nativeAdTypeId("2")
                                        .build()
                                ).build(),
                        "Отсутствует context.app.store_id"
                },
                {
                        SimpleAdsV2Request.SimpleAdsV2.builder()
                                .context(SimpleAdsV2Request.Context.builder()
                                        .user(SimpleAdsV2Request.User.builder()
                                                .geo(SimpleAdsV2Request.Geo.builder()
                                                        .lat(55.790447999999998D)
                                                        .lon(37.680517000000002D)
                                                        .build()
                                                )
                                                .ext(SimpleAdsV2Request.UserExt.builder()
                                                        .anonymousId(UUID.randomUUID().toString())
                                                        .build()
                                                )
                                                .build()
                                        )
                                        .app(SimpleAdsV2Request.App.builder()
                                                .build()
                                        )
                                        .build()
                                )
                                .ext(SimpleAdsV2Request.Ext.builder()
                                        .nativeAdTypeId("2")
                                        .build()
                                ).build(),
                        "Отсутствует context.app.tanant_id"
                },
                {
                        SimpleAdsV2Request.SimpleAdsV2.builder()
                                .context(SimpleAdsV2Request.Context.builder()
                                        .user(SimpleAdsV2Request.User.builder()
                                                .geo(SimpleAdsV2Request.Geo.builder()
                                                        .lat(55.790447999999998D)
                                                        .lon(37.680517000000002D)
                                                        .build()
                                                )
                                                .ext(SimpleAdsV2Request.UserExt.builder()
                                                        .anonymousId(UUID.randomUUID().toString())
                                                        .build()
                                                )
                                                .build()
                                        )
                                        .app(SimpleAdsV2Request.App.builder()
                                                .ext(SimpleAdsV2Request.AppExt.builder()
                                                        .storeId(1)
                                                        .tenantId(0)
                                                        .build()
                                                )
                                                .build()
                                        )
                                        .build()
                                )
                                .ext(SimpleAdsV2Request.Ext.builder()
                                        .build()
                                ).build(),
                        "Отсутствует ext.native_ad_type_id"
                },
                {
                        SimpleAdsV2Request.SimpleAdsV2.builder()
                                .context(SimpleAdsV2Request.Context.builder()
                                        .user(SimpleAdsV2Request.User.builder()
                                                .geo(SimpleAdsV2Request.Geo.builder()
                                                        .lat(55.790447999999998D)
                                                        .lon(37.680517000000002D)
                                                        .build()
                                                )
                                                .ext(SimpleAdsV2Request.UserExt.builder()
                                                        .anonymousId(UUID.randomUUID().toString())
                                                        .build()
                                                )
                                                .build()
                                        )
                                        .app(SimpleAdsV2Request.App.builder()
                                                .ext(SimpleAdsV2Request.AppExt.builder()
                                                        .storeId(1)
                                                        .tenantId(0)
                                                        .build()
                                                )
                                                .build()
                                        )
                                        .build()
                                ).build(),
                        "Отсутствует ext"
                }
        };
    }

    @DataProvider(name = "ordersLineItems")
    public static Object[][] ordersLineItems() {
        List<ProductV2> products = apiV2.getProductFromEachDepartmentInStore(EnvironmentData.INSTANCE.getDefaultSid());
        Long product = products.get(0).getId();
        return new Object[][]{
                {0, 0, "failedOrderNumbers"},
                {0, 0, apiV2.getCurrentOrderNumber()},
                {product, 0, "failedOrderNumbers"},
                {product, 0, apiV2.getCurrentOrderNumber()},
                {0, 1, apiV2.getCurrentOrderNumber()}
        };
    }

    @DataProvider(name = "changeLineItems")
    public static Object[][] changeLineItems() {
        Integer productId = apiV2.fillCart(
                SessionFactory.getSession(SessionType.API_V2_FB).getUserData(),
                EnvironmentData.INSTANCE.getDefaultSid()
        ).get(0).getId();
        return new Object[][]{
                {0, 0},
                {0, 1},
                {-1, 1},
                {0, -1},
                {productId, -1}
        };
    }

    @DataProvider(name = "fillingInOrderInformationDp")
    public static Object[][] fillingInOrderInformationDp() {

        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        Integer paymentsId = apiV2.getPaymentTools().get(0).getId();
        Integer shipmentId = apiV2.getShippingWithOrder().getId();
        Integer shipmentMethodId = apiV2.getShippingMethods().get(0).getId();
        Integer deliveryWindow = apiV2.getAvailableDeliveryWindow().getId();
        return new Object[][]{
                {0, "", "", 0, 0, 0, 0, ""},
                {0, "", "", 0, 0, 0, 0, apiV2.getCurrentOrderNumber()},
                {1, "", "", 0, 0, 0, 0, ""},
                {0, "+79771234567", "", 0, 0, 0, 0, ""},
                {0, "", "test", 0, 0, 0, 0, ""},
                {0, "", "", paymentsId, 0, 0, 0, ""},
                {0, "", "", 0, shipmentId, 0, 0, ""},
                {0, "", "", 0, 0, deliveryWindow, shipmentMethodId, ""},

                {0, "", "", paymentsId, shipmentId, 0, shipmentMethodId, apiV2.getCurrentOrderNumber()},
                {1, "", "", paymentsId, shipmentId, 0, shipmentMethodId, apiV2.getCurrentOrderNumber()},

                {1, "", "", 0, shipmentId, 0, shipmentMethodId, apiV2.getCurrentOrderNumber()},
                {1, "", "", paymentsId, 0, 0, shipmentMethodId, apiV2.getCurrentOrderNumber()},
                {1, "", "", paymentsId, shipmentId, 0, 0, apiV2.getCurrentOrderNumber()},
                {1, "+79771234567", "test", paymentsId, shipmentId, 0, shipmentMethodId, ""}
        };
    }

    @DataProvider(name = "defaultAddressDelivery")
    public static Object[][] defaultAddressDelivery() {
        return getDefaultAllAddress().stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "shipmentsServiceRateData")
    public static Object[][] shipmentsServiceRateData() {
        return new Object[][]{
                //shipmentNumber, deliveryWindowId
                {"failedShipmentNumber", null},
                {"", "failedDeliveryWindow"}
        };
    }

    @DataProvider(name = "dateFormats")
    public static Object[][] dateFormats() {
        return new Object[][]{
                {DateTimeFormatter.BASIC_ISO_DATE},
                {DateTimeFormatter.ISO_OFFSET_DATE},
                {DateTimeFormatter.ISO_DATE},
                {DateTimeFormatter.ISO_LOCAL_DATE_TIME},
                {DateTimeFormatter.ISO_OFFSET_DATE_TIME},
                {DateTimeFormatter.ISO_ZONED_DATE_TIME},
                {DateTimeFormatter.ISO_DATE_TIME},
//                {DateTimeFormatter.ISO_ORDINAL_DATE},
//                {DateTimeFormatter.ISO_WEEK_DATE},
                {DateTimeFormatter.ISO_INSTANT},
                {DateTimeFormatter.RFC_1123_DATE_TIME}
        };
    }

    @DataProvider(name = "storeData")
    public static Object[][] storeData() {
        return new Object[][]{
                {StoresV2Request.Store.builder()
                        .lat(RestAddresses.Moscow.defaultAddress().getLat())
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lon(RestAddresses.Moscow.defaultAddress().getLon())
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .shippingMethod(SippingMethodsV2.BY_COURIER.getMethod())
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .shippingMethod(SippingMethodsV2.PICKUP.getMethod())
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .operationalZoneId(1)
                        .build()
                }
        };
    }

    @DataProvider(name = "storeDataWithLatAndLon")
    public static Object[][] storeDataWithLatAndLon() {
        AddressV2 address = Moscow.defaultAddress();
        return new Object[][]{
                {StoresV2Request.Store.builder()
                        .lat(address.getLat())
                        .lon(address.getLon())
                        .shippingMethod(SippingMethodsV2.BY_COURIER.getMethod())
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lat(address.getLat())
                        .lon(address.getLon())
                        .shippingMethod(SippingMethodsV2.PICKUP.getMethod())
                        .build()
                },
                {StoresV2Request.Store.builder()
                        .lat(address.getLat())
                        .lon(address.getLon())
                        .operationalZoneId(1)
                        .build()
                }
        };
    }
}
