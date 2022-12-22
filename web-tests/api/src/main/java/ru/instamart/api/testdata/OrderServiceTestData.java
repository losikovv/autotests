package ru.instamart.api.testdata;

import com.google.protobuf.Timestamp;
import com.google.type.Money;
import protobuf.order_data.OrderOuterClass;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OrderServiceTestData {
    public static String address1 = "Москва, Шоссейная улица, 2Дс7, кв. 5";
    public static String apartment = "5";
    public static String building = "2Дс7";
    public static String city = "Москва";
    public static String fullAddress = "Москва, Шоссейная улица, 2Дс7, кв. 5";
    public static String kindAddress = "home";
    public static String phone = "79999999999";
    public static String street = "Шоссейная улица";
    public static String name = "sbermarket";
    public static String uuid = "30872e00-06ea-40d3-bd6c-42d47445e085";
    public static Timestamp completedAt = Timestamp.newBuilder()
            .setSeconds(LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond())
            .build();
    public static String currency = "RUB";
    public static String email = "test@sbermarket.ru";
    public static Money money = Money.newBuilder()
            .setCurrencyCode("RUB")
            .setNanos(2)
            .setUnits(4798)
            .build();
    public static OrderOuterClass.Order.Payment.PaymentMethod paymentMethod = OrderOuterClass.Order.Payment.PaymentMethod.newBuilder()
            .setKey("card_to_courier")
            .setName("Картой при получении")
            .build();
    public static OrderOuterClass.Order.Payment.PaymentState statePayments = OrderOuterClass.Order.Payment.PaymentState.CHECKOUT;
    public static String replacePolicy = "call_or_replace";
    public static String segment = "b2c";
    public static OrderOuterClass.Order.Shipment.ContractType contractType = OrderOuterClass.Order.Shipment.ContractType.AGENT;

    public static Money adjustmentTotal = Money.newBuilder()
            .setCurrencyCode("RUB")
            .build();
    public static Money cost = Money.newBuilder()
            .setCurrencyCode("RUB")
            .setUnits(100)
            .build();
    public static Money ItemTotal = Money.newBuilder()
            .setCurrencyCode("RUB")
            .setNanos(2)
            .setUnits(4698)
            .build();
    public static Long itemCount = 1L;
    public static Money total = Money.newBuilder()
            .setCurrencyCode("RUB")
            .setNanos(2)
            .setUnits(4798)
            .build();
    public static Long totalQuantity = 2L;
    public static OrderOuterClass.Order.Payment.PaymentState paymentState = OrderOuterClass.Order.Payment.PaymentState.CHECKOUT;
    public static Long idRetailer = 1L;
    public static String nameRetailer = "METRO";
    public static String shortNameRetailer = "METRO";
    public static String slugRetailer = "metro";
    public static String uuidRetailer = "1f7b042f-650f-46ef-9f4d-10aacf71a532";
    public static String nameShippingCategory = "Default";
    public static String slugShippingCategory = "default";
    public static String nameShippingMethod = "Курьером СБЕРМАРКЕТ";
    public static String storeUuid = "6bc4dc40-37a0-45fe-ac7f-d4185c29da63";
    public static String tenantId = "sbermarket";
    public static Long totalWeight = 10000L;
    public static OrderOuterClass.Order.OrderState stateGlobal = OrderOuterClass.Order.OrderState.forNumber(1);
    public static Timestamp updatedAt = Timestamp.newBuilder()
            .setSeconds(LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond())
            .build();
    public static boolean canChangePaymentTool = false;
    public static boolean isFirstOrder = false;
    public static String firstName = "test";
    public static String lastName = "test";
    public static String fullName = "test test";
}
