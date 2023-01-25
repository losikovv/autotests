package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.enums.shadowcat.PromotionType;
import ru.instamart.api.enums.shadowcat.SimpleCondition;
import ru.instamart.api.enums.shadowcat.TwoStepCondition;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.model.shadowcat.*;
import ru.instamart.api.model.shadowcat.actions.Actions;
import ru.instamart.api.model.shadowcat.conditions.Condition;
import ru.instamart.api.request.shadowcat.ConditionRequest;
import ru.instamart.api.request.shadowcat.PromocodeRequest;
import ru.instamart.api.response.shadowcat.PromocodeResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode201;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode204or404;
import static ru.instamart.api.enums.RailsConsole.Shadowcat.CREATE_JWT_TOKEN;
import static ru.instamart.api.enums.shadowcat.SimpleCondition.*;
import static ru.instamart.api.enums.v2.StateV2.PENDING;
import static ru.instamart.k8s.K8sConsumer.execRailsCommandWithPod;
import static ru.instamart.kraken.data.Generate.digitalString;
import static ru.instamart.kraken.util.TimeUtil.*;

@Slf4j
public class ShadowcatHelper {

    private static volatile ShadowcatHelper INSTANCE;
    @Getter
    private String jwtToken;

    private static final String startDate = getZonedDate();
    private static final String finalDate = getZonedFutureDate(3L);

    private ShadowcatHelper() {
        this.jwtToken = createJwtTokenService();
    }

    public static ShadowcatHelper getInstance() {
        ShadowcatHelper RESULT = INSTANCE;
        if (RESULT != null) {
            return INSTANCE;
        }
        synchronized (ShadowcatHelper.class) {
            if (INSTANCE == null) {
                INSTANCE = new ShadowcatHelper();
            }
            return INSTANCE;
        }
    }

    private String createJwtTokenService() {
        log.debug("JWT Token create");
        List<String> strings = execRailsCommandWithPod(CREATE_JWT_TOKEN.get());
        log.debug("Логи рельсовой консоли: \n {}", String.join("\n\t", strings));
        assertTrue(strings.size() > 0, "logs is empty");
        return strings.get(0);
    }

    @Step("Добавление условия с 2 запросами: {condition}")
    public static void addTwoStepCondition(int promotion, TwoStepCondition condition) {
        Response response;
        response = ConditionRequest.AddTwoStepConditionRule.PUT(promotion, true, condition);
        checkStatusCode204or404(response);
        response = ConditionRequest.AddTwoStepConditionRule.PUT(promotion, condition);
        checkStatusCode204or404(response);
    }

    @Step("Создадим промокод с типом: {promotionType}")
    public static String createPromocode(PromotionType promotionType){
        Response response = PromocodeRequest.Promocodes.POST(promotionType.getId());
        checkStatusCode201(response);
        PromocodeResponse promocode = response.as(PromocodeResponse.class);
        return promocode.getCode();
    }

    public static <T> T returnBody(SimpleCondition conditions) {
        if (conditions.equals(B2B_ORDER) || conditions.equals(FIRST_ORDER) ||
                conditions.equals(NOT_FIRST_ORDER) || conditions.equals(SBER_CUSTOMER)) {
            return (T) "true";
        } else return (T) createBodyForSimpleCondition(conditions);
    }

    public static JSONObject createBodyForSimpleCondition(SimpleCondition conditions) {
        JSONObject requestBody = new JSONObject();
        switch (conditions) {
            case ORDER_PRICE:
                requestBody.put("condition", "gt");
                requestBody.put("amount", 3200);
                requestBody.put("diff_percent", 10);
                return requestBody;
            case FIRST_NTH_ORDER:
                requestBody.put("number", 1000);
                return requestBody;
            default:
                throw new IllegalArgumentException("Неверный condition");

        }
    }

    public static JSONObject createBodyForFirstRequest(boolean status, TwoStepCondition condition) {
        JSONObject requestBody = new JSONObject();
        if (!condition.equals(TwoStepCondition.PRODUCT))
            requestBody.put("inclusion_type", status ? "include" : "exclude");
        else {
            requestBody.put("inclusion_condition", "or");
            requestBody.put("inclusion_type", "include");
            requestBody.put("min_count", 1);
            requestBody.put("min_price", 1);
        }
        return requestBody;
    }

    public static JSONObject requestList(TwoStepCondition conditions) {
        JSONObject body = new JSONObject();
        JSONArray array = new JSONArray();
        array.add(conditions.getConditionValuePass());
        body.put("value", array);
        return body;
    }

    public static Promotion createPromotionBody(PromotionType promotionType) {
        return Promotion.builder()
                .actions(createAction(promotionType))
                .aggregationFunc("max")
                .categoryId(0)
                .conditions(Condition.builder().build())
                .description("Kraken Test")
                .promoCodePrefix("krkn" + digitalString(4))
                .isActive(true)
                .name("Kraken" + digitalString(10))
                .startedAt(startDate)
                .stoppedAt(finalDate)
                .isAutoAccepted(false)
                .match("and")
                .userId(104901)
                .build();
    }
    @Step("Выберем акцию с типом: {promotionType}")
    private static Actions createAction(PromotionType promotionType){
        return Actions.builder()
                .act(Actions.Action.builder()
                        .method(promotionType.getType())
                        .properties(Actions.Properties.builder()
                                .maxFixAmount(promotionType.getValue())
                                .percent(promotionType.getPercent())
                                .build())
                        .build())
                .build();
    }


    public static OrderSC createOrderSC(String promocode) {
        return OrderSC.builder()
                .deliveryMethod(ShippingMethodV2.BY_COURIER.getMethod())
                .isB2b(false)
                .promo_code(promocode)
                .paymentMethod("Spree::Gateway::SberGateway")
                .orderId(UUID.randomUUID().toString())
                .shipments(Collections.singletonList(createShipmentSC()))
                .storeFront("sbermarket")
                .customer(createCustomerSC())
                .totalPrice(2000.00)
                .deliveryPrice(400.00)
                .build();
    }

    private static CustomerSC createCustomerSC() {
        return CustomerSC.builder()
                .ordersCount(0)
                .registeredAt(getDateWithoutTimeUTC())
                .subscription("")
                .id(UUID.randomUUID().toString())
                .build();
    }

    private static ProductSC createProduct() {
        return ProductSC.builder()
                .categoryIds(Arrays.asList("23456", "34567", "45678"))
                .id("ff3dd630-5795-4964-b7cc-d2af7b5ddcd6")
                .categorySlug("default")
                .price(1230.00)
                .discountPrice(1000.00)
                .quantity(5)
                .sku("12345")
                .build();
    }

    private static ShipmentSC createShipmentSC() {
        return ShipmentSC.builder()
                .regionId("1")
                .status(PENDING.getValue())
                .id(UUID.randomUUID().toString())
                .products(Collections.singletonList(createProduct()))
                .retailerId("9d54c27b-9b04-459e-a301-8167983be07b")
                .storeId("c547127e-c716-45bc-b5e5-8cf9740840dc")
                .deliveryPrices(createDeliveryPriceSC())
                .build();
    }

    private static DeliveryPriceSC createDeliveryPriceSC() {
        return DeliveryPriceSC.builder()
                .deliveryPrice(100.00)
                .assemblyPrice(100.00)
                .bagsPrice(100.00)
                .deliverySurge(100.00)
                .build();
    }

    public static OrderSC updateOrderSimpleCondition(OrderSC order, SimpleCondition condition, Boolean pass) {
        int value = pass ? condition.getConditionValuePass() : condition.getConditionValueFail();
        switch (condition) {
            case FIRST_ORDER:
            case FIRST_NTH_ORDER:
            case NOT_FIRST_ORDER:
                order.getCustomer().setOrdersCount(value);
                break;
            case SBER_CUSTOMER:
                order.getCustomer().setSberLoyalty(pass);
                break;
            case B2B_ORDER:
                order.setB2b(pass);
                break;
            case ORDER_PRICE:
                order.getShipments().get(0).getProducts().get(0).setDiscountPrice(value);
                break;
            default:
                fail("Condition not found");
        }
        return order;
    }

    public static OrderSC updateOrderTwoStepCondition(OrderSC order, TwoStepCondition condition, Boolean pass) {
        String value = pass ? condition.getConditionValuePass() : condition.getConditionValueFail();
        switch (condition) {
            case DELIVERY_METHOD:
                order.setDeliveryMethod(value);
                break;
            case CUSTOMER:
                order.getCustomer().setId(value);
                break;
            case PAYMENT:
                order.setPaymentMethod(value);
                break;
            case PRODUCT:
                order.getShipments().get(0).getProducts().get(0).setSku(value);
                break;
            case REGION:
                order.getShipments().get(0).setRegionId(value);
                break;
            case RETAILER:
                order.getShipments().get(0).setRetailerId(value);
                break;
            case STORE_FRONT:
                order.setStoreFront(value);
                break;
            case SUBSCRIPTION:
                order.getCustomer().setSubscription(value);
                break;
            default:
                fail("Condition not found");
        }
        return order;
    }
}
