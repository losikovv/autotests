package ru.instamart.api.helper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.*;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.model.v1.b2b.ManagerV1;
import ru.instamart.api.model.v1.b2b.UserV1;
import ru.instamart.api.request.admin.*;
import ru.instamart.api.request.v1.*;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.MarketingPricers;
import ru.instamart.api.request.v1.ShippingMethodsV1Request.NominalPricers;
import ru.instamart.api.request.v1.admin.ShipmentsAdminV1Request;
import ru.instamart.api.request.v1.admin.StoreLabelsAdminV1Request;
import ru.instamart.api.request.v1.b2b.CompaniesV1Request;
import ru.instamart.api.request.v1.b2b.CompanyEmployeesV1Request;
import ru.instamart.api.request.v1.b2b.CompanyManagersV1Request;
import ru.instamart.api.response.v1.*;
import ru.instamart.api.response.v1.admin.ShipmentsAdminV1Response;
import ru.instamart.api.response.v1.admin.StoreLabelsAdminV1Response;
import ru.instamart.api.response.v1.b2b.CompaniesV1Response;
import ru.instamart.api.response.v1.imports.OffersFilesV1Response;
import ru.instamart.jdbc.dao.stf.SpreeUsersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StoreLabelData;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import java.util.List;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.api.helper.K8sHelper.createAdmin;

public class AdminHelper {

    public void createCity(CitiesAdminRequest.City city) {
        final Response response = CitiesAdminRequest.POST(city);
        checkStatusCode302(response);
    }

    public void deleteCity(String cityName) {
        final Response response = CitiesAdminRequest.DELETE(cityName);
        checkStatusCode302(response);
    }

    public void createStaticPage(PagesAdminRequest.Page page) {
        final Response response = PagesAdminRequest.POST(page);
        checkStatusCode302(response);
    }

    public void deleteStaticPage(Long pageId) {
        final Response response = PagesAdminRequest.DELETE(pageId);
        checkStatusCode302(response);
    }

    public void addCompany(final String inn, final String companyName, final String ownerEmail) {
        final Response response = CompaniesV1Request.POST(inn, companyName, ownerEmail);
        checkStatusCode200(response);
    }

    @Step("Добавляем сотрудника в компанию")
    public void addEmployee(final Integer companyId, final String userId) {
        UserV1 userV1 = new UserV1();
        userV1.setId(Integer.parseInt(userId));
        final Response response = CompanyEmployeesV1Request.POST(companyId, userV1);
        checkStatusCode200(response);
    }

    public void addManager(final Integer companyId, final String userId) {
        final ManagerV1 manager = new ManagerV1();
        UserV1 managerAsUser = new UserV1();
        managerAsUser.setId(Integer.parseInt(userId));
        manager.setUser(managerAsUser);
        final Response response = CompanyManagersV1Request.POST(companyId, manager);
        checkStatusCode200(response);
    }

    public void checkDeliveryWindows(Integer storeId) {
        final Response responseForGet = StoresV1Request.DeliveryWindows.GET(storeId);
        checkStatusCode200(responseForGet);
        DeliveryWindowsV1Response deliveryWindowsV1Response = responseForGet.as(DeliveryWindowsV1Response.class);
        if (deliveryWindowsV1Response.getDeliveryWindows().size() == 0) {
            final Response responseForGenerate = StoresV1Request.DeliveryWindows.POST(storeId);
            checkStatusCode200(responseForGenerate);
        }
    }

    public OperationalZoneV1 addOperationalZones(String zoneName) {
        final Response response = OperationalZonesV1Request.POST(zoneName);
        checkStatusCode200(response);
        return response.as(OperationalZoneV1Response.class).getOperationalZone();
    }

    public List<ShippingMethodV1> getShippingMethods() {
        final var response = ShippingMethodsV1Request.GET();
        checkStatusCode200(response);

        return response.as(ShippingMethodsV1Response.class).getShippingMethods();
    }

    public void createShippingMethod(final ShippingMethodsRequest.ShippingMethod shippingMethod) {
        final var response = ShippingMethodsRequest.POST(shippingMethod);
        checkStatusCode302(response);
    }

    public List<PricerV1> getMarketingRule(final int methodId) {
        final var response = MarketingPricers.GET(methodId);
        checkStatusCode200(response);

        return response.as(PricersV1Response.class).getPricers();
    }

    public PricerV1 createMarketingRule(final int id) {
        final var response = MarketingPricers.POST(id);
        checkStatusCode200(response);

        return response.as(PricerV1Response.class).getPricer();
    }

    public void deleteMarketingRule(final int id) {
        final var response = MarketingPricers.DELETE(id);
        checkStatusCode200(response);
    }

    public List<PricerV1> getNominalRule(final int methodId) {
        final var response = NominalPricers.GET(methodId);
        checkStatusCode200(response);

        return response.as(PricersV1Response.class).getPricers();
    }

    public PricerV1 createNominalRule(final int id) {
        final var response = NominalPricers.POST(id);
        checkStatusCode200(response);

        return response.as(PricerV1Response.class).getPricer();
    }

    public void deleteNominalRule(final int id) {
        final var response = NominalPricers.DELETE(id);
        checkStatusCode200(response);
    }

    public void createPricerRule(final int ruleId, final ShippingMethodsV1Request.Rules rules) {
        final var response = ShippingMethodsV1Request.Rules.POST(ruleId, rules);
        checkStatusCode200(response);
    }

    public void createPricerCalculator(final int ruleId, final ShippingMethodsV1Request.Calculators calculators) {
        final var response = ShippingMethodsV1Request.Calculators.POST(ruleId, calculators);
        checkStatusCode200(response);
    }

    public void updateRule(final int ruleId, final ShippingMethodsV1Request.Rules rules) {
        final var response = ShippingMethodsV1Request.Rules.PUT(ruleId, rules);
        checkStatusCode200(response);
    }

    public void updateCalculator(final int ruleId, final ShippingMethodsV1Request.Calculators calculators) {
        final var response = ShippingMethodsV1Request.Calculators.PUT(ruleId, calculators);
        checkStatusCode200(response);
    }

    public List<ShipmentsAdminV1Response.Shipment> getShipments(final ShipmentsAdminV1Request.ShipmentsData shipmentsData) {
        final var response = ShipmentsAdminV1Request.GET(shipmentsData);
        checkStatusCode200(response);
        return response.as(ShipmentsAdminV1Response.class).getShipments();
    }

    @Step("Авторизация администратором")
    public void auth() {
        UserData user = UserManager.getDefaultAdmin();
        if (!EnvironmentProperties.Env.isProduction()) {
            if (SpreeUsersDao.INSTANCE.getUserByEmail(user.getEmail()) == null) {
                createAdmin(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
            }
        }
        SessionFactory.createSessionToken(SessionType.ADMIN, user);
    }

    @Step("Авторизация администратором для API")
    public void authApi() {
        UserData user = UserManager.getDefaultAdmin();
        if (!EnvironmentProperties.Env.isProduction()) {
            if (SpreeUsersDao.INSTANCE.getUserByEmail(user.getEmail()) == null) {
                createAdmin(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
            }
        }
        SessionFactory.createSessionToken(SessionType.API_V1, SessionProvider.EMAIL, user);
    }

    @Step("Авторизация администратором со включенными новыми ролями для API")
    //временное решение, пока полностью не переделают админку
    public void authApiWithAdminNewRoles() {
        UserData user = UserManager.getDefaultAdminAllRoles();
        if (!EnvironmentProperties.Env.isProduction()) {
            if (SpreeUsersDao.INSTANCE.getUserByEmail(user.getEmail()) == null) {
                createAdmin(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
            }
        }
        SessionFactory.createSessionToken(SessionType.API_V1, SessionProvider.EMAIL, user);
    }

    @Step("Удаляем страну производства")
    public void deleteManufacturingCountries(String permalink) {
        final Response response = ManufacturingCountriesAdminRequest.DELETE(permalink);
        checkStatusCode302(response);
    }

    @Step("Создаем магазин")
    public void createStore(StoresAdminRequest.Store store) {
        final Response storeResponse = StoresAdminRequest.POST(store);
        checkStatusCode302(storeResponse);
    }

    @Step("Редактируем магазин")
    public void editStore(Integer sid, StoresAdminRequest.Store store) {
        final Response storeResponse = StoresAdminRequest.PATCH(store, sid);
        checkStatusCode302(storeResponse);
    }

    @Step("Получаем список импортов офферов")
    public List<ImportsFileV1> getOfferFiles() {
        final Response response = ImportsV1Request.OffersFiles.GET();
        checkStatusCode200(response);
        return response.as(OffersFilesV1Response.class).getOffersFiles();
    }

    public RetailerV1 createRetailer(RetailersV1Request.Retailer retailer) {
        final Response response = RetailersV1Request.POST(retailer);
        checkStatusCode200(response);
        return response.as(RetailerV1Response.class).getRetailer();
    }

    public List<CompanyV1> getCompanies(String inn) {
        final Response response = CompaniesV1Request.GET(inn);
        checkStatusCode200(response);
        return response.as(CompaniesV1Response.class).getCompanies();
    }

    public void importOffers(byte[] fileBytes) {
        final Response response = ImportsV1Request.OffersFiles.POST(fileBytes);
        checkStatusCode200(response);
    }

    public void importStoreZoneFile(Integer storeId, String filePath) {
        final Response response = StoreZonesV1Request.ZoneFiles.POST(storeId, filePath);
        checkStatusCode200(response);
    }

    public void importStoreZone(Integer storeId, String area) {
        final Response response = StoreZonesV1Request.Zones.POST(storeId, area);
        checkStatusCode200(response);
    }

    public void createStoreSchedule(String storeUuid) {
        final Response response = StoreSchedulesV1Request.Schedules.POST(storeUuid);
        checkStatusCode200(response);
    }

    public void updateDeliveryWindowWithDefaultValues(Long deliveryWindowId) {
        final Response response = DeliveryWindowsV1Request.PUT(
                deliveryWindowId,
                1,
                1,
                1,
                1,
                ShippingMethodV2.PICKUP.getMethod());
        checkStatusCode200(response);
    }

    public void createDeliveryWindow(Integer storeId) {
        final Response responsePost = StoresV1Request.DeliveryWindows.POST(storeId);
        checkStatusCode200(responsePost);
    }

    public List<DeliveryWindowV1> getDeliveryWindows(Integer storeId) {
        final Response responseGet = StoresV1Request.DeliveryWindows.GET(storeId);
        checkStatusCode200(responseGet);
        return responseGet.as(DeliveryWindowsV1Response.class).getDeliveryWindows();
    }

    public List<AdminStoreLabelsItemV1> getStoreLabels() {
        final Response responseGet = StoreLabelsAdminV1Request.GET();
        checkStatusCode200(responseGet);
        return responseGet.as(StoreLabelsAdminV1Response.class).getStoreLabels();
    }

    public AdminStoreLabelsItemV1 getStoreLabelByName(final String storeLabelName) {
        return getStoreLabels().stream()
                .filter(storeLabelsItemV1 -> storeLabelsItemV1.getTitle().equals(storeLabelName))
                .findFirst()
                .orElse(new AdminStoreLabelsItemV1());
    }

    public void createStoreLabel(StoreLabelData storeLabelData) {
        AdminStoreLabelsItemV1 storeLabelsItemV1 = AdminStoreLabelsItemV1.builder()
                .title(storeLabelData.getTitle())
                .subtitle(storeLabelData.getSubtitle())
                .code(storeLabelData.getCode())
                .icon(storeLabelData.getIcon())
                .level(storeLabelData.getLevel())
                .position(storeLabelData.getPosition())
                .build();
        final Response response = StoreLabelsAdminV1Request.POST(storeLabelsItemV1);
        checkStatusCode(response, 201);
    }

    public void deleteStoreLabel(Integer storeLabelID) {
        final Response response = StoreLabelsAdminV1Request.DELETE(storeLabelID);
        checkStatusCode204or404(response);
    }
}
