package ru.instamart.api.dataprovider;

import io.restassured.response.Response;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.OfferV1;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.model.v2.*;
import ru.instamart.api.request.v1.OperationalZonesV1Request;
import ru.instamart.api.request.v2.AddressesV2Request.Addresses;
import ru.instamart.api.request.v2.*;
import ru.instamart.api.response.v1.OperationalZonesV1Response;
import ru.instamart.api.response.v2.CreditCardAuthorizationV2Response;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.jdbc.dao.InstacoinAccountsDao;
import ru.instamart.jdbc.dao.PromotionCodesDao;
import ru.instamart.jdbc.dao.SpreeProductsDao;
import ru.instamart.jdbc.dao.SpreeUsersDao;
import ru.instamart.jdbc.dto.PromotionCodesFilters;
import ru.instamart.jdbc.entity.PromotionCodesEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data_provider.DataList;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.common.RestAddresses.Moscow;
import static ru.instamart.api.common.RestAddresses.getDefaultAllAddress;
import static ru.instamart.api.enums.v2.RecsPlaceV2.*;
import static ru.instamart.kraken.helper.LegalEntityHelper.*;

public class RestDataProvider extends RestBase {

    @Data
    public static class SimpleRecsV2TestData {
        private SimpleRecsV2Request.SimpleRecsV2 simpleRec;
        private String description;
        private Integer statusCode;
        private String errorMessage;
    }

    @Data
    public static class SimpleRecsV2TestDataRoot implements DataList<SimpleRecsV2TestData> {
        private List<SimpleRecsV2TestData> data;
    }

    @Data
    public static class RecsV2TestData {
        private PersonalV2Request.RecsV2 rec;
        private String description;
    }

    @Data
    public static class RecsV2TestDataRoot implements DataList<RecsV2TestData> {
        private List<RecsV2TestData> data;
    }

    @Data
    public static class SimpleAdsV2TestData {
        private SimpleAdsV2Request.SimpleAdsV2 simpleAds;
        private String description;
        private Integer statusCode;
        private String errorMessage;
    }

    @Data
    public static class SimpleAdsV2TestDataRoot implements DataList<SimpleAdsV2TestData> {
        private List<SimpleAdsV2TestData> data;
    }

    @Data
    public static class StoreDataRoot implements DataList<StoresV2Request.Store> {
        private List<StoresV2Request.Store> data;
    }

    @DataProvider(name = "authProviders")
    public static Object[][] getAuthProviders() {
        Object[][] authProviderArray = new Object[4][1];
        authProviderArray[0][0] = AuthProviderV2.METRO;
        authProviderArray[1][0] = AuthProviderV2.SBERAPP;
        authProviderArray[2][0] = AuthProviderV2.VKONTAKTE;
        authProviderArray[3][0] = AuthProviderV2.FACEBOOK;
        return authProviderArray;
    }

    @DataProvider(name = "positiveQuery", parallel = true)
    public static Object[][] getPositiveQuery() {
        Object[][] queryArray = new Object[2][2];
        queryArray[0] = new Object[]{1, "хлеб",};
        queryArray[1] = new Object[]{1, "а"};
        return queryArray;
    }

    @DataProvider(name = "negativeQuery", parallel = true)
    public static Object[][] getNegativeQuery() {
        Object[][] queryArray = new Object[3][2];
        queryArray[0] = new Object[]{1, "SELECT * FROM spree_products"};
        queryArray[1] = new Object[]{1, "!@#$%^&*()"};
        queryArray[2] = new Object[]{1, RandomStringUtils.randomAlphabetic(8140)};
        //queryArray[7] = new Object[]{1, RandomStringUtils.randomAlphabetic(8141), 414};
        //пока не можем проверить, так как в теле ответа текст вместо json
        return queryArray;
    }

    @DataProvider(name = "emptyQueries", parallel = true)
    public static Object[][] getEmptyQuery() {
        return new Object[][]{
                {"",},
                {" "}
        };
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

    @Deprecated
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

    @Deprecated
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

    @Deprecated
    @DataProvider(name = "deliveryAvailabilityV2TestData", parallel = true)
    public static Object[][] deliveryAvailabilityV2TestData() {
        return new Object[][]{
                {"", ""}, //Координаты не указаны
                {"55.658228", ""}, //Указан lat
                {"", "37.748818"} //Указан lon
        };
    }

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

    @DataProvider(name = "ordersLineItems", parallel = true)
    public static Object[][] ordersLineItems() {
        List<ProductV2> products = apiV2.getProductFromEachDepartmentInStore(EnvironmentProperties.DEFAULT_SID);
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
                EnvironmentProperties.DEFAULT_SID
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

        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
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

//                {0, "", "", paymentsId, shipmentId, 0, shipmentMethodId, apiV2.getCurrentOrderNumber()},
//                {1, "", "", paymentsId, shipmentId, 0, shipmentMethodId, apiV2.getCurrentOrderNumber()},

                {1, "", "", 0, shipmentId, 0, shipmentMethodId, apiV2.getCurrentOrderNumber()},
                {1, "", "", paymentsId, 0, 0, shipmentMethodId, apiV2.getCurrentOrderNumber()},
//                {1, "", "", paymentsId, shipmentId, 0, 0, apiV2.getCurrentOrderNumber()},
                {1, "+79771234567", "test", paymentsId, shipmentId, 0, shipmentMethodId, ""}
        };
    }

    @Deprecated
    @DataProvider(name = "defaultAddressDelivery")
    public static Object[][] defaultAddressDelivery() {
        return getDefaultAllAddress().stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @Deprecated
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

    @DataProvider(name = "innFailedList", parallel = true)
    public static Object[][] innFailedList() {
        return new Object[][]{
                {"failedInnNumbers"},
                {"461312796100"}, //ФЛ
                {"2690798098"} //ЮЛ
        };
    }

    @DataProvider(name = "priceTypes", parallel = true)
    public static Object[][] getPriceType() {
        return new Object[][]{
                {ProductPriceTypeV2.PER_ITEM},
                {ProductPriceTypeV2.PER_PACK}
        };
    }

    @DataProvider(name = "orderNumbers", parallel = true)
    public static Object[][] getOrderNumber() {
        OrderV2 order = OrdersV2Request.POST().as(OrderV2Response.class).getOrder();
        return new Object[][]{
                {order.getNumber()},
                {"failedNumber"}
        };
    }

    @DataProvider(name = "transactionNumbers", parallel = true)
    public static Object[][] getTransactionNumber() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        OrderV2 order = OrdersV2Request.POST().as(OrderV2Response.class).getOrder();
        CreditCardAuthorizationV2 creditCardAuthorization = PaymentsV2Request.POST(order.getNumber()).
                as(CreditCardAuthorizationV2Response.class).getCreditCardAuthorization();
        return new Object[][]{
                {creditCardAuthorization.getTransactionNumber(), 422, "Ошибка добавления карты"},
                {"0000-0000", 404, "translation missing: ru.activerecord.models.card_authorization_payment не существует"}
        };
    }

    @DataProvider(name = "invalidTransactionData", parallel = true)
    public static Object[][] getInvalidTransactionData() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        String orderNumber = OrdersV2Request.POST().as(OrderV2Response.class).getOrder().getNumber();
        String userUuid = apiV2.getProfile().getUser().getId();
        String transactionNumber = PaymentsV2Request.POST(orderNumber).
                as(CreditCardAuthorizationV2Response.class).getCreditCardAuthorization().getTransactionNumber();
        return new Object[][]{
                {transactionNumber, orderNumber, userUuid, "Кредитная карта не существует"},
                {transactionNumber, orderNumber, "0", "Пользователь не существует"},
        };
    }

    @DataProvider(name = "simpleRecsData")
    public static Object[][] getSimpleRecsData() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        String orderNumber = OrdersV2Request.POST().as(OrderV2Response.class).getOrder().getNumber();
        Long offerId = SpreeProductsDao.INSTANCE.getOfferIdBySku("26331", EnvironmentProperties.DEFAULT_SID);
        LineItemsV2Request.POST(offerId, 1, orderNumber);
        return new Object[][]{
                {MAIN.name(), null, null},
                {NOT_FOUND.name(), null, null},
                {EMPTY_SEARCH.name(), null, null},
                {CART.name(), orderNumber, null},
                {CARD_COMPLEMENTARY.name(), null, "26331"},
                {CARD_SUBSTITUTE.name(), null, "12191"},
        };
    }

    @DataProvider(name = "userDataForReferralProgram", parallel = true)
    public static Object[][] getUserDataForReferralProgramm() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        String token = SessionFactory.getSession(SessionType.API_V2_FB).getToken();
        UserV2 user = apiV2.getProfile().getUser();
        Long userId = SpreeUsersDao.INSTANCE.getIdByEmail(user.getEmail());
        PromotionCodesFilters filters = PromotionCodesFilters.builder()
                .value("auto%")
                .usageLimit(5000)
                .limit(1)
                .build();
        PromotionCodesEntity promotionCodesEntity = PromotionCodesDao.INSTANCE.findAll(filters).get(0);
        Response response = UsersV2Request.ReferralProgram.GET(user.getEmail(), token);
        checkStatusCode200(response);
        InstacoinAccountsDao.INSTANCE.updatePromotionCode(promotionCodesEntity.getId(), userId);
        return new Object[][]{
                {user.getEmail(), token, promotionCodesEntity.getValue(), userId},
                {user.getId(), token, promotionCodesEntity.getValue(), userId},
        };
    }

    @DataProvider(name = "invalidUserDataForReferralProgram", parallel = true)
    public static Object[][] getInvalidUserDataForReferralProgramm() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
        String token = SessionFactory.getSession(SessionType.API_V2_FB).getToken();
        return new Object[][]{
                {"thisisnonexistingemail@testest.com", token, 404},
                {UUID.randomUUID().toString(), token, 500},
        };
    }

    @DataProvider(name = "postCompanyDocuments")
    public static Object[][] postCompanyDocuments() {
        String name = "ООО \"Ромашка_" + (int) (Math.random() * 9999) + "\"";
        String bik = "043601607";
        String corAcc = "30101810200000000607";
        return new Object[][]{
                {
                        CompanyDocumentsV2Request.CompanyDocument.builder()
//                                .name(name)
                                .inn(generateInnUL())
                                .kpp(generateKpp())
                                .bik(bik)
                                .correspondent_account(corAcc)
                                .operating_account(generateRS())
                                .build(),
                        "Юридическое лицо не может быть пустым"
                },
                {
                        CompanyDocumentsV2Request.CompanyDocument.builder()
                                .name(name)
//                                .inn(generateInnUL())
                                .kpp(generateKpp())
                                .bik(bik)
                                .correspondent_account(corAcc)
                                .operating_account(generateRS())
                                .build(),
                        "ИНН не может быть пустым"
                },
                {
                        CompanyDocumentsV2Request.CompanyDocument.builder()
                                .name(name)
                                .inn(generateInnUL())
//                                .kpp(generateKpp())
                                .bik(bik)
                                .correspondent_account(corAcc)
                                .operating_account(generateRS())
                                .build(),
                        "КПП не является числом"
                },
                //TODO: не отрабатывает ошибка создана задача STF-9206
//                {
//                        CompanyDocumentsV2Request.CompanyDocument.builder()
//                                .name(name)
//                                .inn(generateInnUL())
//                                .kpp(generateKpp())
////                                .bik(bik)
//                                .correspondent_account(corAcc)
//                                .operating_account(generateRS())
//                                .build(),
//                        "БИК не является числом"
//                },
//                {
//                        CompanyDocumentsV2Request.CompanyDocument.builder()
//                                .name(name)
//                                .inn(generateInnUL())
//                                .kpp(generateKpp())
//                                .bik(bik)
////                                .correspondent_account(corAcc)
//                                .operating_account(generateRS())
//                                .build(),
//                        "Вы не состоите в компании с таким ИНН. Проверьте ИНН или закрепите компанию на сайте СберМаркет во вкладке «Для бизнеса»"
//                },
//                {
//                        CompanyDocumentsV2Request.CompanyDocument.builder()
//                                .name(name)
//                                .inn(generateInnUL())
//                                .kpp(generateKpp())
//                                .bik(bik)
//                                .correspondent_account(corAcc)
////                                .operating_account(generateRS())
//                                .build(),
//                        "Вы не состоите в компании с таким ИНН. Проверьте ИНН или закрепите компанию на сайте СберМаркет во вкладке «Для бизнеса»"
//                }
        };
    }
}
