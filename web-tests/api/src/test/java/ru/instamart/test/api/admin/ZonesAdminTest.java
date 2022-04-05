package ru.instamart.test.api.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.admin.ZonesAdminRequest;
import ru.instamart.jdbc.dao.stf.SpreeZonesDao;
import ru.instamart.jdbc.entity.stf.SpreeZonesEntity;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Optional;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Admin")
@Feature("Торговые зоны")
public class ZonesAdminTest extends RestBase {

    private Long zoneId;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.auth();
    }

    @CaseId(1887)
    @Test(groups = {"api-instamart-regress"}, description = "Получение списка торговых зон")
    public void getAllZones() {
        final Response response = ZonesAdminRequest.GET();
        checkStatusCode(response, 200, ContentType.HTML);
    }

    @CaseIDs(value = {@CaseId(2060), @CaseId(2061)})
    @Test(groups = {"api-instamart-regress"},
            description = "Создание новой торговой зоны",
            dataProvider = "adminZones",
            dataProviderClass = RestDataProvider.class)
    public void createZone(ZonesAdminRequest.Zone zone) {
        final Response response = ZonesAdminRequest.POST(zone);
        checkStatusCode302(response);
        SpreeZonesEntity createdZone = SpreeZonesDao.INSTANCE.getZoneByDescription(zone.getZoneDescription());
        checkFieldIsNotEmpty(createdZone, "торговая зона в БД");
        compareTwoObjects(createdZone.getName(), zone.getZoneName());
        zoneId = createdZone.getId();
    }

    @CaseId(2064)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание новой торговой зоны без имени")
    public void createZoneWithoutName() {
        ZonesAdminRequest.Zone zone = ZonesAdminRequest.Zone.builder()
                .zoneDescription("Autotest-" + Generate.literalString(6))
                .zoneCountryId(1L)
                .build();
        final Response response = ZonesAdminRequest.POST(zone);
        checkStatusCode(response, 200, "text/html; charset=utf-8");
        SpreeZonesEntity createdZone = SpreeZonesDao.INSTANCE.getZoneByDescription(zone.getZoneDescription());
        Assert.assertNull(createdZone);
    }

    @CaseIDs(value = {@CaseId(2062), @CaseId(2063)})
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование торговой зоны",
            dataProvider = "adminZones",
            dataProviderClass = RestDataProvider.class,
            dependsOnMethods = "createZone")
    public void editZone(ZonesAdminRequest.Zone zone) {
        final Response response = ZonesAdminRequest.PATCH(zone, zoneId);
        checkStatusCode302(response);
        Optional<SpreeZonesEntity> createdZone = SpreeZonesDao.INSTANCE.findById(zoneId);
        Assert.assertFalse(createdZone.isEmpty());
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(createdZone.get().getName(), zone.getZoneName(), softAssert);
        compareTwoObjects(createdZone.get().getDescription(), zone.getZoneDescription(), softAssert);
        softAssert.assertAll();
    }

    @CaseId(2065)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование торговой зоны с пустым имени",
            dependsOnMethods = "createZone")
    public void editZoneWithoutName() {
        ZonesAdminRequest.Zone zone = ZonesAdminRequest.Zone.builder()
                .zoneName("")
                .zoneDescription("Autotest-" + Generate.literalString(6))
                .zoneCountryId(1L)
                .build();
        final Response response = ZonesAdminRequest.PATCH(zone, zoneId);
        checkStatusCode(response, 200, "text/html; charset=utf-8");
        Optional<SpreeZonesEntity> createdZone = SpreeZonesDao.INSTANCE.findById(zoneId);
        Assert.assertFalse(createdZone.isEmpty());
        checkFieldIsNotEmpty(createdZone.get().getName(), "имя торговой зоны в БД");
    }

    @CaseId(2066)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление торговой зоны",
            dependsOnMethods = {"editZone", "editZoneWithoutName"})
    public void deleteZone() {
        final Response response = ZonesAdminRequest.DELETE(zoneId);
        checkStatusCode302(response);
        Optional<SpreeZonesEntity> createdZone = SpreeZonesDao.INSTANCE.findById(zoneId);
        Assert.assertTrue(createdZone.isEmpty());
    }

    @AfterClass(alwaysRun = true)
    public void clearData() {
        SpreeZonesDao.INSTANCE.deleteZonesByName("Autotest");
    }
}
