package ru.instamart.api.dataprovider;

import io.restassured.response.Response;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v1.RulesAttributesTypeV1;
import ru.instamart.api.enums.v2.AnalyzeResultV2;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.OfferV1;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.model.v1.OrderPaymentMethodV1;
import ru.instamart.api.model.v2.*;
import ru.instamart.api.request.admin.PagesAdminRequest;
import ru.instamart.api.request.admin.StoresAdminRequest;
import ru.instamart.api.request.admin.ZonesAdminRequest;
import ru.instamart.api.request.v1.PromotionCardsV1Request;
import ru.instamart.api.request.v1.RetailersV1Request;
import ru.instamart.api.request.v1.ShippingPoliciesV1Request;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.request.v2.AddressesV2Request.Addresses;
import ru.instamart.api.request.v2.*;
import ru.instamart.api.response.v2.*;
import ru.instamart.jdbc.dao.stf.*;
import ru.instamart.jdbc.dto.stf.PromotionCodesFilters;
import ru.instamart.jdbc.entity.stf.PromotionCodesEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data_provider.DataList;
import ru.sbermarket.common.Crypt;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.common.RestAddresses.Moscow;
import static ru.instamart.api.common.RestAddresses.getDefaultAllAddress;
import static ru.instamart.api.enums.v1.CombinedStateV1.SHIPMENT_PENDING;
import static ru.instamart.api.enums.v1.CombinedStateV1.SHIPMENT_READY;
import static ru.instamart.api.enums.v1.FiltersFilesModeV1.*;
import static ru.instamart.api.enums.v1.PaymentStatusV1.*;
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

    @Data
    public static class StoresAdminTestData {
        private StoresAdminRequest.Store store;
        private String description;
        private Integer statusCode;
    }

    @Data
    public static class StoresAdminTestDataRoot implements DataList<StoresAdminTestData> {
        private List<StoresAdminTestData> data;
    }

    @Data
    public static class RetailerV1TestData {
        private RetailersV1Request.Retailer retailer;
        private String errorMessage;
    }

    @Data
    public static class RetailerV1TestDataRoot implements DataList<RetailerV1TestData> {
        private List<RetailerV1TestData> data;
    }

    @Data
    public static class PromotionCardsV1TestData {
        private PromotionCardsV1Request.PromotionCard promotionCard;
        private String errorMessage;
    }

    @Data
    public static class PromotionCardsV1TestDataRoot implements DataList<PromotionCardsV1TestData> {
        private List<PromotionCardsV1TestData> data;
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

    @DataProvider(name = "authProvidersWithParams")
    public static Object[][] getAuthProvidersWithParams() {
        return new Object[][]{
                {AuthProviderV2.VKONTAKTE, AuthParamsV2Response.class},
                {AuthProviderV2.FACEBOOK, AuthParamsV2Response.class},
                {AuthProviderV2.MAIL_RU, AuthParamsV2Response.class},
                {AuthProviderV2.SBERBANK, AuthParamsSberbankV2Response.class},
        };
    }

    @DataProvider(name = "positiveQuery", parallel = true)
    public static Object[][] getPositiveQuery() {
        return new Object[][]{
                {"хлеб"},
                {"ХЛЕБ"},
                {"а"},
        };
    }

    @DataProvider(name = "negativeQuery", parallel = true)
    public static Object[][] getNegativeQuery() {
        return new Object[][]{
                {"SELECT * FROM spree_products"},
                {"!@#$%^&*()"},
                {RandomStringUtils.randomAlphabetic(8140)},
        };
    }

    @DataProvider(name = "emptyQueries", parallel = true)
    public static Object[][] getEmptyQuery() {
        return new Object[][]{
                {""},
                {" "}
        };
    }

    @DataProvider(name = "negativeOperationalZonesNames", parallel = true)
    public static Object[][] getNegativeOperationalZonesNames() {
        return new Object[][]{
                {400, null},
                {422, Generate.literalCyrillicString(10)}, //todo bug - null error message
                {422, ""},
                {422, " "}, //todo bug - null error message
               // {422, "москва"}//, //todo bug - null error message 500 error
                // {422, "тест-" + Generate.literalCyrillicString(251)} //todo bug 500
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

        if (EnvironmentProperties.Env.isProduction()) {
            return new Object[][]{
                    {retailerList.get(0)},
                    {retailerList.get(1)}
            };
        } else {
            return retailerList.stream()
                    .map(list -> new Object[]{list})
                    .toArray(Object[][]::new);
        }
    }

    @DataProvider(name = "retailersSpree-parallel", parallel = true)
    public static Object[][] getAvailableRetailersSpreeParallel() {
        return getAvailableRetailersSpree();
    }

    @DataProvider(name = "retailersSpree", parallel = true)
    public static Object[][] getAvailableRetailersSpree() {
        Specification.setResponseSpecDataProvider();

        List<RetailerV2> retailerList = apiV1.getAvailableRetailers().stream().filter(RetailerV2::getAvailable).collect(Collectors.toList());

        Specification.setResponseSpecDefault();

        if (EnvironmentProperties.Env.isProduction()) {
            return new Object[][]{
                    {retailerList.get(0)},
                    {retailerList.get(1)}
            };
        } else {
            return retailerList.stream()
                    .map(list -> new Object[]{list})
                    .toArray(Object[][]::new);
        }
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

        if (EnvironmentProperties.Env.isProduction()) {
            return new Object[][]{
                    {storeList.get(0)},
                    {storeList.get(1)}
            };
        } else {
            return storeList.stream()
                    .map(list -> new Object[]{list})
                    .toArray(Object[][]::new);
        }
    }

    @DataProvider(name = "storeOfEachRetailer-parallel", parallel = true)
    public static Object[][] getStoreOfEachRetailerParallel() {
        return getStoreOfEachRetailer();
    }

    @DataProvider(name = "storeOfEachRetailer", parallel = true)
    public static Object[][] getStoreOfEachRetailer() {
        Specification.setResponseSpecDataProvider();

        if (EnvironmentProperties.Env.isProduction()) {
            Response response = RetailersV1Request.Stores.GET(apiV2.getAvailableRetailers().get(1).getId());
            checkStatusCode200(response);
            List<StoreV2> retailerStores = response.as(StoresV2Response.class).getStores();

            return new Object[][]{
                    {retailerStores.get(0)},
                    {retailerStores.get(1)}
            };
        } else {
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

    /**
     * @return 20 операционных зон (10 первых и 10 последних)
     */
    @DataProvider(name = "operationalZones", parallel = true)
    public static Object[][] getOperationalZones() {
        Specification.setResponseSpecDataProvider();

        List<OperationalZoneV1> operationalZones = apiV1.getAllOperationalZones();
        List<OperationalZoneV1> newOperationalZones = operationalZones.subList(operationalZones.size() - 10, operationalZones.size());
        operationalZones = operationalZones.subList(0, 10);
        operationalZones.addAll(newOperationalZones);

        Specification.setResponseSpecDefault();

        return operationalZones.stream()
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

        if (EnvironmentProperties.Env.isProduction()) {
            Response response = RetailersV1Request.Stores.GET(apiV2.getAvailableRetailers().get(0).getId());
            checkStatusCode200(response);
            List<StoreV2> retailerStores = response.as(StoresV2Response.class).getStores();
            List<OfferV1> offerList = apiV1.getActiveOffers(retailerStores.get(0).getUuid());
            return new Object[][]{
                    {offerList.get(0)},
                    {offerList.get(1)}
            };
        } else {

            List<StoreV2> storeList = apiV2.getAvailableRetailers()
                    .stream().parallel()
                    .map(apiV2::getAvailableStores)
                    .map(retailerStores -> retailerStores.get(retailerStores.size() - 1))
                    .collect(Collectors.toList());

            List<OfferV1> offerList = storeList
                    .stream().parallel()
                    .map(store -> apiV1.getActiveOffers(store.getUuid()))
                    .filter(storeOffers -> !storeOffers.isEmpty())
                    .map(storeOffers -> storeOffers.get(0))
                    .collect(Collectors.toList());

            Specification.setResponseSpecDefault();

            return offerList.stream()
                    .map(list -> new Object[]{list})
                    .toArray(Object[][]::new);
        }
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

    @DataProvider(name = "ordersLineItems", parallel = true)
    public static Object[][] ordersLineItems() {
        List<ProductV2> products = apiV2.getProducts(EnvironmentProperties.DEFAULT_SID);
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
        Integer productId = apiV2.dropAndFillCart(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
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

        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
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

    @DataProvider(name = "priceTypes")
    public static Object[][] getPriceType() {
        return new Object[][]{
                {ProductPriceTypeV2.PER_ITEM},
                {ProductPriceTypeV2.PER_PACK}
        };
    }

    @DataProvider(name = "orderNumbers")
    public static Object[][] getOrderNumber() {
        OrderV2 order = OrdersV2Request.POST().as(OrderV2Response.class).getOrder();
        return new Object[][]{
                {order.getNumber()},
                {"failedNumber"}
        };
    }

    @DataProvider(name = "transactionNumbers")
    public static Object[][] getTransactionNumber() {
        OrderV2 order = OrdersV2Request.POST().as(OrderV2Response.class).getOrder();
        CreditCardAuthorizationV2 creditCardAuthorization = PaymentsV2Request.POST(order.getNumber()).
                as(CreditCardAuthorizationV2Response.class).getCreditCardAuthorization();
        return new Object[][]{
                {creditCardAuthorization.getTransactionNumber(), 422, "Ошибка добавления карты"},
                {"0000-0000", 404, "translation missing: ru.activerecord.models.card_authorization_payment не существует"}
        };
    }

    @DataProvider(name = "invalidTransactionData")
    public static Object[][] getInvalidTransactionData() {
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
        SessionFactory.makeSession(SessionType.API_V2);
        String orderNumber = OrdersV2Request.POST().as(OrderV2Response.class).getOrder().getNumber();
        Long offerId = SpreeProductsDao.INSTANCE.getOfferIdBySku("26331", EnvironmentProperties.DEFAULT_SID);
        LineItemsV2Request.POST(offerId, 1, orderNumber);
        return new Object[][]{
                {MAIN.name(), null, null},
                {NOT_FOUND.name(), null, null},
                {EMPTY_SEARCH.name(), null, null},
                {CART.name(), orderNumber, null},
                {CARD_COMPLEMENTARY.name(), null, "12191"},
                {CARD_SUBSTITUTE.name(), null, "12191"},
        };
    }

    @DataProvider(name = "userDataForReferralProgram", parallel = true)
    public static Object[][] getUserDataForReferralProgramm() {
        SessionFactory.makeSession(SessionType.API_V2);
        String token = SessionFactory.getSession(SessionType.API_V2).getToken();
        UserV2 user = apiV2.getProfile().getUser();
        Long userId = SpreeUsersDao.INSTANCE.getUserByEmail(user.getEmail()).getId();
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
        SessionFactory.makeSession(SessionType.API_V2);
        String token = SessionFactory.getSession(SessionType.API_V2).getToken();
        return new Object[][]{
                {"thisisnonexistingemail@testest.com", token, 404},
                {UUID.randomUUID().toString(), token, 500},
        };
    }

    @DataProvider(name = "pageData")
    public static Object[][] getPages() {

        return new Object[][]{
                {PagesAdminRequest.Page.builder()
                        .title("Autotest-" + Generate.literalString(6))
                        .slug("Autotest-" + Generate.literalString(6))
                        .body("Autotest-" + Generate.literalString(6))
                        .position(1)
                        .foreignLink("Autotest-" + Generate.literalString(6))
                        .metaTitle("Autotest-" + Generate.literalString(6))
                        .metaDescription("Autotest-" + Generate.literalString(6))
                        .metaKeywords("Autotest-" + Generate.literalString(6))
                        .layout("Autotest-" + Generate.literalString(6))
                        .visible(0)
                        .showInFooter(1)
                        .showInHeader(1)
                        .showInSidebar(1)
                        .renderLayoutAsPartial(1)
                        .build(),
                        0, 1},
                {PagesAdminRequest.Page.builder()
                        .title("Autotest-" + Generate.literalString(6))
                        .slug("Autotest-" + Generate.literalString(6))
                        .body("Autotest-" + Generate.literalString(6))
                        .build(),
                        1, 0},
        };
    }

    @DataProvider(name = "updatedPageData")
    public static Object[][] getUpdatedPages() {

        return new Object[][]{
                {PagesAdminRequest.Page.builder()
                        .title("Autotest-" + Generate.literalString(6))
                        .slug("Autotest-" + Generate.literalString(6))
                        .body("Autotest-" + Generate.literalString(6))
                        .build(),
                        1, 0},
                {PagesAdminRequest.Page.builder()
                        .title("Autotest-" + Generate.literalString(6))
                        .slug("Autotest-" + Generate.literalString(6))
                        .body("Autotest-" + Generate.literalString(6))
                        .position(1)
                        .foreignLink("Autotest-" + Generate.literalString(6))
                        .metaTitle("Autotest-" + Generate.literalString(6))
                        .metaDescription("Autotest-" + Generate.literalString(6))
                        .metaKeywords("Autotest-" + Generate.literalString(6))
                        .layout("Autotest-" + Generate.literalString(6))
                        .visible(0)
                        .showInFooter(1)
                        .showInHeader(1)
                        .showInSidebar(1)
                        .renderLayoutAsPartial(1)
                        .build(),
                        0, 1}
        };
    }

    @DataProvider(name = "invalidProductsId")
    public static Object[][] getInvalidProductsId() {
        Long productId = apiV2.getProductsFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_SID).get(0).getId();
        Long alcoholProductId = SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(1);
        Long pharmaProductId = SpreeProductsDao.INSTANCE.getOfferIdForPharma(1);
        return new Object[][]{
                {productId, "store_id", "Дозаказать можно только из того же магазина"},
                {alcoholProductId, "shipping_category_id", "В заказе алкоголь. Его нельзя добавить к заказу с доставкой"},
                {pharmaProductId, "shipping_category_id", "В заказе лекарства. Их нельзя добавить к заказу с доставкой"},
        };
    }

    @DataProvider(name = "storesDataForTransferMethodOnlyCourier")
    public static Object[][] getStoresForTransferMethodCheckCourier() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_SID, AnalyzeResultV2.OK},
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, AnalyzeResultV2.OK},
                {2, AnalyzeResultV2.OK},
                {22, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
                {89, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
        };
    }

    @DataProvider(name = "storesDataForTransferMethodAllShipping")
    public static Object[][] getStoresForTransferMethodCheckAll() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, AnalyzeResultV2.OK},
                {94, AnalyzeResultV2.OK},
                {EnvironmentProperties.DEFAULT_SID, AnalyzeResultV2.OK},
                {22, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
                {89, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
        };
    }

    @DataProvider(name = "negativeStoresForPickupTransferMethodOnlyCourier")
    public static Object[][] getNegativeStoresForPickupTransferMethodCheckCourier() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_SID},
                {2}
        };
    }

    @DataProvider(name = "risStoreIdsProduction")
    public static Object[][] getRisStoreIdsProduction() {
        return new Object[][]{
                {257}, //auchan
                {242}, //alleya
                {1995}, //auchansm
                //{6164}, //auchansmfd - нет больше такого сида на проде
                {1372}, //globus
                {999}, //megamart
                {10}, //metro
                //{772}, //okey - 404
                {4} //selgros
        };
    }

    @DataProvider(name = "storesDataForPickupTransferMethodOnlyCourier")
    public static Object[][] getStoresForPickupTransferMethodCheckCourier() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, AnalyzeResultV2.OK},
                {22, AnalyzeResultV2.OK},
                {89, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
        };
    }

    @DataProvider(name = "storesDataForPickupTransferMethodAll")
    public static Object[][] getStoresForPickupTransferMethodCheckAll() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, AnalyzeResultV2.OK},
                {94, AnalyzeResultV2.OK},
                {22, AnalyzeResultV2.OK},
                {89, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
        };
    }

    @DataProvider(name = "storesDataForCourierTransferMethod")
    public static Object[][] getStoresForCourierTransferMethodCheck() {
        return new Object[][]{
                {22, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, AnalyzeResultV2.OK},
                {EnvironmentProperties.DEFAULT_SID, AnalyzeResultV2.OK},
                {82, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
                {89, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
        };
    }

    @DataProvider(name = "storesDataForPickupTransferMethod")
    public static Object[][] getStoresForPickupTransferMethodCheck() {
        return new Object[][]{
                {22, AnalyzeResultV2.OK},
                {89, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID, AnalyzeResultV2.OK},
                {82, AnalyzeResultV2.OK},
        };
    }

    @DataProvider(name = "storesDataForCourierAlcoholTransferMethod")
    public static Object[][] getStoresForCourierAlcoholTransferMethodCheck() {
        return new Object[][]{
                {2},
                {22},
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID}
        };
    }

    @DataProvider(name = "storesDataForPickupAlcoholTransferMethod")
    public static Object[][] getStoresForPickupAlcoholTransferMethodCheck() {
        return new Object[][]{
                {22, AnalyzeResultV2.OK},
                {94, AnalyzeResultV2.OK},
                {89, AnalyzeResultV2.ALL_PRODUCTS_DISAPPEARS},
        };
    }

    @DataProvider(name = "storesForCourierWithoutLosses")
    public static Object[][] getStoresForCourierWithoutLosses() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_SID},
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID},
                {2}
        };
    }

    @DataProvider(name = "storesForCourierWithLosses")
    public static Object[][] getStoresForCourierWithLosses() {
        return new Object[][]{
                {22},
                {89}
        };
    }

    @DataProvider(name = "storesForPickupWithoutLosses")
    public static Object[][] getStoresForPickupWithoutLosses() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID},
                {22}
        };
    }

    @DataProvider(name = "storesForCouriersWithoutLosses")
    public static Object[][] getStoresForCouriersWithoutLosses() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID},
                {94},
                {EnvironmentProperties.DEFAULT_SID}
        };
    }

    @DataProvider(name = "storesForPickupAndCourierWithoutLosses")
    public static Object[][] getStoresForPickupAndCourierWithoutLosses() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID},
                {94},
                {22}
        };
    }

    @DataProvider(name = "storesForPickupCourierWithoutLosses")
    public static Object[][] getStoresForPickupCourierWithoutLosses() {
        return new Object[][]{
                {EnvironmentProperties.DEFAULT_SID},
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID}
        };
    }

    @DataProvider(name = "storesForPickupCourierWithLosses")
    public static Object[][] getStoresForPickupCourierWithLosses() {
        return new Object[][]{
                {22},
                {82},
                {89},
        };
    }

    @DataProvider(name = "storeForPickupsWithoutLosses")
    public static Object[][] getStoresForPickupsWithoutLosses() {
        return new Object[][]{
                {22},
                {EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID},
                {82},
        };
    }

    @DataProvider(name = "storeForPickupAlcoholWithoutLosses")
    public static Object[][] getStoresForPickupAlcoholWithoutLosses() {
        return new Object[][]{
                {22},
                {94},
        };
    }

    @DataProvider(name = "shipmentReviewsData")
    public static Object[][] getShipmentReviewsData() {
        return new Object[][]{
                {5},
                {2}
        };
    }

    @DataProvider(name = "shipmentReviewsCallbackData")
    public static Object[][] getShipmentReviewsCallbackData() {
        return new Object[][]{
                {5, true},
                {2, false}
        };
    }

    @DataProvider(name = "retailerNameData", parallel = true)
    public static Object[][] getRetailerNameData() {
        return new Object[][]{
                {"лента"},
                {"лен"},
                {"НТА"},
                {"metro"},
                {"MET"},
                {"etro"},
        };
    }

    @DataProvider(name = "shippingMethods")
    public static Object[][] getShippingMethods() {
        return new Object[][]{
                {ShippingMethodV2.BY_COURIER.getMethod()},
                {ShippingMethodV2.PICKUP.getMethod()},
        };
    }

    @DataProvider(name = "incorrectRetailerParams", parallel = true)
    public static Object[][] getIncorrectRetailerParams() {
        return new Object[][]{
                {RetailersV1Request.RetailerParams.builder()
                        .operationalZoneId(0L)
                        .build()},
                {RetailersV1Request.RetailerParams.builder()
                        .nameCont("jhglkjrgjsbgbbldjfgb")
                        .build()},
                {RetailersV1Request.RetailerParams.builder()
                        .retailerId(0L)
                        .build()},
                {RetailersV1Request.RetailerParams.builder()
                        .page(10000)
                        .perPage(10)
                        .build()}
        };
    }

    @DataProvider(name = "nextDeliveriesParams", parallel = true)
    public static Object[][] getNextDeliveriesParams() {
        AddressV2 address = apiV2.getAddressBySid(EnvironmentProperties.DEFAULT_SID);
        return new Object[][]{
                {StoresV1Request.NextDeliveriesParams.builder()
                        .cargo(false)
                        .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                        .lat(address.getLat())
                        .lon(address.getLon())
                        .build()},
                {StoresV1Request.NextDeliveriesParams.builder()
                        .lat(address.getLat())
                        .lon(address.getLon())
                        .build()},
                {StoresV1Request.NextDeliveriesParams.builder()
                        .build()}
        };
    }

    @DataProvider(name = "citiesNameData", parallel = true)
    public static Object[][] getCitiesNameData() {
        return new Object[][]{
                {"Москва"},
                {"МОСКВА"},
                {"мос"},
                {"ква"},
        };
    }

    @DataProvider(name = "citiesInvalidParams", parallel = true)
    public static Object[][] getCitiesInvalidParams() {
        return new Object[][]{
                {CitiesV2Request.CitiesParams.builder()
                        .keyword("пврврварв")
                        .build()},
                {CitiesV2Request.CitiesParams.builder()
                        .page(2000)
                        .perPage(3)
                        .build()}
        };
    }

    @DataProvider(name = "recsData", parallel = true)
    public static Object[][] getRecs() {
        return new Object[][]{
                {PersonalV2Request.RecsV2.builder()
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
                                        .user(PersonalV2Request.User.builder()
                                                .ext(PersonalV2Request.UserExt.builder()
                                                        .anonymousId(UUID.randomUUID().toString())
                                                        .build())
                                                .build())
                                        .build()
                        )
                        .build()},
                {PersonalV2Request.RecsV2.builder()
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
                        .build()}
        };
    }

    @DataProvider(name = "filtersImportModes")
    public static Object[][] getFiltersImportModes() {
        return new Object[][]{
                {REPLACE_FOR_FILTER_GROUPS},
                {UPDATE_ONLY},
                {REPLACE_FOR_CATEGORY},
        };
    }

    @DataProvider(name = "shipmentStatuses")
    public static Object[][] getShipmentStatuses() {
        return new Object[][]{
                {SHIPMENT_READY},
                {SHIPMENT_PENDING}
        };
    }

    @DataProvider(name = "paymentStatuses")
    public static Object[][] getPaymentStatuses() {
        return new Object[][]{
                {PAID},
                {NOT_PAID},
                {BALANCE_DUE},
                {OVERPAID},
                {FAILED}
        };
    }

    @DataProvider(name = "replacementPolicies")
    public static Object[][] getReplacementPolicies() {
        return apiV1.getReplacementPolicies().stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "adminZones")
    public static Object[][] getZones() {
        return new Object[][]{
                {ZonesAdminRequest.Zone.builder()
                        .zoneName("Autotest-" + Generate.literalString(6))
                        .zoneDescription("Autotest-" + Generate.literalString(6))
                        .zoneStateId(OperationalZonesDao.INSTANCE.getIdByName("Москва"))
                        .build()},
                {ZonesAdminRequest.Zone.builder()
                        .zoneName("Autotest-" + Generate.literalString(6))
                        .zoneDescription("Autotest-" + Generate.literalString(6))
                        .zoneCountryId(1L)
                        .build()}
        };
    }

    @DataProvider(name = "paymentTools")
    public static Object[][] getPaymentTools() {
        List<OrderPaymentMethodV1> paymentMethods = new ArrayList<>();
        apiV1.getPaymentTools().forEach(tool -> paymentMethods.add(tool.getPaymentMethod()));
        return paymentMethods.stream().filter(p -> !p.getType().equals("sber_pay"))
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "booleanData")
    public static Object[][] getBooleanData() {
        return new Object[][]{
                {true},
                {false}
        };
    }

    @DataProvider(name = "shippingPolicyRulesData")
    public static Object[][] getShippingPolicyRules() {
        return new Object[][]{
                {ShippingPoliciesV1Request.ShippingPolicyRules.builder()
                        .ruleType(RulesAttributesTypeV1.DAYS_FROM_NOW.getValue())
                        .preferences(ShippingPoliciesV1Request.RulesAttribute.builder()
                                .preferredDays(RandomUtils.nextInt(1, 15))
                                .build())
                        .build()},
                {ShippingPoliciesV1Request.ShippingPolicyRules.builder()
                        .ruleType(RulesAttributesTypeV1.TIME_BEFORE_SHIPMENT.getValue())
                        .preferences(ShippingPoliciesV1Request.RulesAttribute.builder()
                                .preferredTimeGap(RandomUtils.nextInt(500, 1000))
                                .build())
                        .build()},
                {ShippingPoliciesV1Request.ShippingPolicyRules.builder()
                        .ruleType(RulesAttributesTypeV1.TIME_FROM_MIDNIGHT.getValue())
                        .preferences(ShippingPoliciesV1Request.RulesAttribute.builder()
                                .preferredTime(RandomUtils.nextInt(60000, 80000))
                                .build())
                        .build()},
                {ShippingPoliciesV1Request.ShippingPolicyRules.builder()
                        .ruleType(RulesAttributesTypeV1.DELIVERY_WINDOW_NUMBER.getValue())
                        .preferences(ShippingPoliciesV1Request.RulesAttribute.builder()
                                .preferredNumber(RandomUtils.nextInt(1, 10))
                                .build())
                        .build()},
                {ShippingPoliciesV1Request.ShippingPolicyRules.builder()
                        .ruleType(RulesAttributesTypeV1.TIME_BEFORE_EXPRESS_SHIPMENT.getValue())
                        .preferences(ShippingPoliciesV1Request.RulesAttribute.builder()
                                .preferredTimeGap(RandomUtils.nextInt(1000, 1500))
                                .build())
                        .build()}
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
                {
                        CompanyDocumentsV2Request.CompanyDocument.builder()
                                .name(name)
                                .inn(generateInnUL())
                                .kpp(generateKpp())
                                .bik("failedNumber")
                                .correspondent_account(corAcc)
                                .operating_account(generateRS())
                                .build(),
                        "БИК не является числом"
                },
                //TODO: не отрабатывает ошибка создана задача STF-9206
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

    @DataProvider(name = "forMapFailedTestParams")
    public static Object[][] forMapFailedTestParams() {
        return new Object[][]{
                {
                        null,
                        "Недопустимые параметры запроса: bbox отсутствует"
                },
                {
                        StoresV2Request.ForMapParams.builder()
                                .retailerIds("1")
                                .build(),
                        "Недопустимые параметры запроса: bbox отсутствует"
                },
                {
                        StoresV2Request.ForMapParams.builder()
                                .shippingMethod(ShippingMethodV2.BY_COURIER.getMethod())
                                .build(),
                        "Недопустимые параметры запроса: bbox отсутствует"
                },
                {
                        StoresV2Request.ForMapParams.builder()
                                .bbox("55.640161, 37.682901~55.712339, 37.748081")
                                .build(),
                        "Недопустимые параметры запроса: bbox {:text=>\"Неправильное значение параметра bbox, должно быть вида '160.599911,55.101101~160.7555111,55.311111'\", :code=>:invalid_bbox_param}"
                },
                {
                        StoresV2Request.ForMapParams.builder()
                                .bbox("56.291423,43.967728~56.332495,44.058287")
                                .shippingMethod("failedShippingMethod")
                                .build(),
                        "Недопустимые параметры запроса: shipping_method должно быть одним из: by_courier, by_courier_for_companies, pickup"
                },
                {
                        StoresV2Request.ForMapParams.builder()
                                .bbox("56.291423,43.967728~56.332495,44.058287")
                                .retailerIds("failedRetailerIds")
                                .build(),
                        "Недопустимые параметры запроса: retailer_ids {:text=>\"Все значения в списке должны быть integer\", :code=>:invalid_types_in_list}"
                }
        };
    }

    @DataProvider(name = "notificationsFailed")
    public static Object[][] notificationsFailed() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String orderNumber = apiV2.getOpenOrder().getNumber();

        return new Object[][]{
                {
                        null
                },
                {
                        NotificationsV2Request.Events.builder()
                                .event(NotificationsV2Request.Event.builder()
                                        .type("order.paid")
                                        .payload(NotificationsV2Request.Payload.builder()
                                                .orderId(orderNumber)
                                                .build())
                                        .build())
                                .build()
                }
        };
    }

    @DataProvider(name = "sendProductFeedbacks")
    public static Object[][] sendProductFeedbacks() {
        String productSku = apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getSku();
        return new Object[][]{
                {
                        ProductFeedbacksV2Request.Feedbacks.builder()
                                .sku(productSku)
                                .storeId(String.valueOf(EnvironmentProperties.DEFAULT_SID))
                                .score(4)
                                .pros("свежий")
                                .cons("Слишком зеленый")
                                .text("Очень хрупкий")
                                .build()
                },
                {
                        ProductFeedbacksV2Request.Feedbacks.builder()
                                .sku(productSku)
                                .storeId(String.valueOf(EnvironmentProperties.DEFAULT_SID))
                                .score(4)
//                                .pros("свежий")
                                .cons("Слишком зеленый")
                                .text("Очень хрупкий")
                                .build()
                },
                {
                        ProductFeedbacksV2Request.Feedbacks.builder()
                                .sku(productSku)
                                .storeId(String.valueOf(EnvironmentProperties.DEFAULT_SID))
                                .score(4)
                                .pros("свежий")
//                                .cons("Слишком зеленый")
                                .text("Очень хрупкий")
                                .build()
                },
                {
                        ProductFeedbacksV2Request.Feedbacks.builder()
                                .sku(productSku)
                                .storeId(String.valueOf(EnvironmentProperties.DEFAULT_SID))
                                .score(4)
                                .pros("свежий")
                                .cons("Слишком зеленый")
//                                .text("Очень хрупкий")
                                .build()
                }
        };
    }

    @DataProvider(name = "getAllFeatureFlags", parallel = true)
    public static Object[][] getAllFeatureFlags(){
        final Response response = FeatureFlagsV2Request.GET();
        checkStatusCode200(response);
        FeatureFlagsV2Response featureFlags = response.as(FeatureFlagsV2Response.class);
        return featureFlags.getFeatureFlags().stream()
                .map(list -> new Object[]{list})
                .toArray(Object[][]::new);
    }


    @DataProvider(name = "userEmails")
    public static Object[][] getUserEmails() {
        return new Object[][]{
                {"autotest"},
                {Crypt.INSTANCE.decrypt("wTfubFbVMEA2P1HT80pKDXJfzWnJ15xgPBJr240lktU=")}
        };
    }
}
