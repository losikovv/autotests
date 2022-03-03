package ru.instamart.api.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.*;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.api.model.v2.NextDeliveryV2;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.request.v1.*;
import ru.instamart.api.request.v1.b2b.CompaniesV1Request;
import ru.instamart.api.request.v1.b2b.UserCompaniesV1Request;
import ru.instamart.api.response.v1.*;
import ru.instamart.api.response.v1.b2b.CompaniesV1Response;
import ru.instamart.api.response.v1.b2b.CompanyV1Response;
import ru.instamart.api.response.v2.RetailersV2Response;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.Juridical;
import ru.instamart.kraken.data.user.UserData;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Slf4j
public class ApiV1Helper {

    private final ThreadLocal<LineItemV1> currentLineItem = new ThreadLocal<>();
    private final ThreadLocal<Long> currentReplacementPolicyId = new ThreadLocal<>();
    private final ThreadLocal<MultiretailerOrderShipmentV1> currentShipment = new ThreadLocal<>();

    public void authByPhone(UserData user) {
        SessionFactory.createSessionToken(SessionType.API_V1, SessionProvider.PHONE, user);
    }

    public void authByEmail(UserData user) {
        SessionFactory.createSessionToken(SessionType.API_V1, SessionProvider.EMAIL, user);
    }

    public List<OfferV1> getActiveOffers(String storeUuid) {
        Response response = StoresV1Request.Offers.GET(
                storeUuid,
                "вода",
                "");
        checkStatusCode200(response);
        return response.as(OffersV1Response.class)
                .getOffers()
                .stream()
                .filter(OfferV1::getActive)
                .collect(Collectors.toList());
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    @Step("Получаем список активных ритейлеров: ")
    public List<RetailerV2> getAvailableRetailers() {
        Response response = RetailersV1Request.GET(new RetailersV1Request.RetailerParams());
        checkStatusCode200(response);
        List<RetailerV2> retailers = response.as(RetailersV2Response.class).getRetailers();

        StringJoiner availableRetailers = new StringJoiner(
                "\n• ",
                "Список активных ретейлеров:\n• ",
                "\n");
        for (RetailerV2 retailer : retailers)
            if (retailer.getAvailable()) availableRetailers.add(retailer.toString());
        log.debug(availableRetailers.toString());
        Allure.addAttachment("Активные ритейлеры:", ContentType.TEXT.toString(), availableRetailers.toString());
        StringJoiner notAvailableRetailers = new StringJoiner(
                "\n• ",
                "Список неактивных ретейлеров:\n• ",
                "\n");
        for (RetailerV2 retailer : retailers)
            if (!retailer.getAvailable()) notAvailableRetailers.add(retailer.toString());
        log.debug(notAvailableRetailers.toString());
        Allure.addAttachment("Неактивные ритейлеры:", ContentType.TEXT.toString(), notAvailableRetailers.toString());

        return retailers;
    }

    @Step("Получаем список всех операционных зон")
    public List<OperationalZoneV1> getAllOperationalZones() {
        log.debug("Получаем список всех операционных зон");
        Response response = OperationalZonesV1Request.GET();
        checkStatusCode200(response);
        return response.as(OperationalZonesV1Response.class).getOperationalZones();
    }

    @Step("Получаем информацию о заказе")
    public MultiretailerOrderV1Response getMultiRetailerOrder() {
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        return response.as(MultiretailerOrderV1Response.class);
    }

    @Step("Добавляем товар в корзину")
    public LineItemV1 addItemToCart(Long offerId) {
        final Response response = LineItemsV1Request.POST(offerId);
        checkStatusCode200(response);
        currentLineItem.set(response.as(LineItemV1Response.class).getLineItem());
        return currentLineItem.get();
    }

    @Step("Изменяем количество товара")
    public void changeItemCountInCart(int count) {
        currentLineItem.get().setPacks(count);
        final Response response = LineItemsV1Request.PUT(currentLineItem.get());
        checkStatusCode200(response);
    }

    @Step("Меняем адрес пользователя")
    public void changeAddress(AddressV2 address, String shippingMethod) {
        final Response response = ShoppingContextV1Request.PUT(address, shippingMethod);
        checkStatusCode200(response);
    }

    @Step("Получаем все политики замены")
    public List<ReplacementPolicyV1> getReplacementPolicies() {
        final Response response = ReplacementPoliciesV1Request.GET();
        checkStatusCode200(response);
        List<ReplacementPolicyV1> replacementPolicies = response.as(ReplacementPoliciesV1Response.class).getReplacementPolicies();
        currentReplacementPolicyId.set(replacementPolicies.get(0).getId());
        return replacementPolicies;
    }

    @Step("Получаем доступные способы оплаты")
    public List<PaymentToolV1> getPaymentTools() {
        final Response response = AvailablePaymentToolsV1Request.GET();
        checkStatusCode200(response);
        return response.as(PaymentToolsV1Response.class).getPaymentTools();
    }

    @Step("Наполняем корзину")
    public void fillCart(AddressV2 address, String shippingMethod, Long offerId) {
        changeAddress(address, shippingMethod);
        addItemToCart(offerId);
        MultiretailerOrderShipmentV1 shipment = getMultiRetailerOrder().getShipments().get(0);
        double minOrderAmount = shipment.getStore().getMinOrderAmount();
        double price = shipment.getLineItems().get(0).getPrice();
        if (price < minOrderAmount) {
            changeItemCountInCart((int) Math.ceil(minOrderAmount / price));
        }
    }

    @Step("Получаем доставку пользователя")
    public UserShipmentV1 getUserShipment(String userId, String shipmentNumber) {
        final Response response = UsersV1Request.GET(userId, shipmentNumber);
        checkStatusCode200(response);
        return response.as(UserShipmentV1Response.class).getShipment();
    }

    @Step("Добавляем политику замен")
    public MultiretailerOrderShipmentV1 addReplacementPolicy() {
        final Response response = CheckoutV1Request.PUT(currentReplacementPolicyId.get());
        checkStatusCode200(response);
        MultiretailerOrderShipmentV1 shipment = response.as(MultiretailerOrderV1Response.class).getShipments().get(0);
        currentShipment.set(shipment);
        return shipment;
    }

    @Step("Добавляем окно доставки")
    public void addDeliveryWindow() {
        List<NextDeliveryV2> filteredNextDeliveries = currentShipment.get().getNextDeliveries().stream().filter(d -> d.getId() >= 0).collect(Collectors.toList());
        final Response response = CheckoutV1Request.PUT(currentShipment.get(), filteredNextDeliveries.get(0));
        checkStatusCode200(response);
    }

    @Step("Получаем компанию по ИНН {inn}")
    public List<CompanyV1> getCompanyByInn(String inn) {
        final Response response = CompaniesV1Request.GET(inn);
        checkStatusCode200(response);
        return response.as(CompaniesV1Response.class).getCompanies();
    }

    @Step("Получаем компанию c существующим балансом по ИНН {inn}")
    public CompanyV1 getCompanyWithBalanceByInn(String inn) {
        List<CompanyV1> companies = getCompanyByInn(inn);
        CompanyV1 company;
        if(companies.size() == 0) {
            Juridical companyData = new Juridical(
                    "ЗАО \"Лидер-" + Generate.digitalString(4) + "\"",
                    Generate.string(8),
                    inn,
                    Generate.digitalString(9),
                    Generate.digitalString(20),
                    Generate.digitalString(9),
                    Generate.string(8),
                    Generate.digitalString(20)
            );
            final Response response = UserCompaniesV1Request.POST(companyData);
            checkStatusCode200(response);
            company = response.as(CompanyV1Response.class).getCompany();
        } else {
            company = companies.get(0);
        }
        return company;
    }
}
