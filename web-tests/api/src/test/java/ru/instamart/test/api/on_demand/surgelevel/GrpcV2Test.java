package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.surgelevel.*;
import ru.sbermarket.qase.annotation.CaseId;
import surgelevel.v2.SurgelevelV2;

import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.lang3.RandomUtils.nextFloat;
import static org.apache.commons.lang3.RandomUtils.nextLong;

@Epic("Surgelevel")
@Feature("gRPC V2")
public class GrpcV2Test extends GrpcBase {

    private surgelevel.v2.ServiceGrpc.ServiceBlockingStub client;
    private final long REGION_ID = nextLong(1000000, 2000000);
    private final long RETAILER_ID = nextLong(1000000, 2000000);
    private final float CONFIG_STEP = nextFloat(1f, 2f);
    private final String STORE_ID = UUID.randomUUID().toString();
    private final String FORMULA_ID = UUID.randomUUID().toString();
    private final String FORMULA_SCRIPT = "function main(scope) {return scope.demand.length + scope.supply.length;}";

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SURGELEVEL);
        client = surgelevel.v2.ServiceGrpc.newBlockingStub(channel);
    }

    @CaseId(52)
    @Story("Set Config")
    @Test(description = "Изменить дефолтную конфигурацию",
            groups = "ondemand-surgelevel")
    public void setConfig() {
        var request = SurgelevelV2.SetConfigRequest.newBuilder()
                .setConfig(SurgelevelV2.Config.Option.newBuilder()
                        .setStep(CONFIG_STEP)
                        .build())
                .build();
        var response = client.setConfig(request);

        check.notNull(response.getConfig());
        check.notNull(response.getConfig().getId());
        check.equals(response.getConfig().getStep(), CONFIG_STEP);
        check.notNull(response.getConfig().getFormula());
        check.notNull(response.getConfig().getFormula().getId());
        check.notNull(response.getConfig().getFormula().getName());
        check.notNull(response.getConfig().getFormula().getScript());
    }

    @CaseId(50)
    @Story("Get Config")
    @Test(description = "Получить дефолтную конфигурацию",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "setConfig")
    public void getConfig() {
        var request = SurgelevelV2.GetConfigRequest.newBuilder().build();
        var response = client.getConfig(request);

        check.notNull(response.getConfig());
        check.notNull(response.getConfig().getId());
        check.equals(response.getConfig().getStep(), CONFIG_STEP);
        check.notNull(response.getConfig().getFormula());
        check.notNull(response.getConfig().getFormula().getId());
        check.notNull(response.getConfig().getFormula().getName());
        check.notNull(response.getConfig().getFormula().getScript());
    }

    @CaseId(88)
    @Story("Save Region")
    @Test(description = "Сохранение региона",
            groups = "ondemand-surgelevel")
    public void saveRegion() {
        var request = SurgelevelV2.SaveRegionRequest.newBuilder()
                .addRegion(SurgelevelV2.Region.Option.newBuilder().setId(REGION_ID).build())
                .build();
        var response = client.saveRegion(request);

        check.notNull(response.getRegion(0));
        check.equals(response.getRegion(0).getId(), REGION_ID);
        check.notNull(response.getRegion(0).getConfig());
        check.notNull(response.getRegion(0).getConfig().getFormula());
        check.notNull(response.getRegion(0).getConfig().getInheritance(0));
        check.notNull(response.getRegion(0).getConfig().getInheritance(0).getFormula());
    }

    @CaseId(112)
    @Story("Find Region")
    @Test(description = "Поиск региона",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "saveRegion")
    public void findRegion() {
        var request = SurgelevelV2.FindRegionRequest.newBuilder()
                .setCondition(SurgelevelV2.Region.Condition.newBuilder().addId(REGION_ID).build())
                .build();
        var response = client.findRegion(request);

        check.notNull(response.getRegion(0));
        check.equals(response.getRegion(0).getId(), REGION_ID);
        check.notNull(response.getRegion(0).getConfig());
        check.notNull(response.getRegion(0).getConfig().getFormula());
        check.notNull(response.getRegion(0).getConfig().getInheritance(0));
        check.notNull(response.getRegion(0).getConfig().getInheritance(0).getFormula());
    }

    @CaseId(62)
    @Story("Save Retailer")
    @Test(description = "Сохранение ритейлера",
            groups = "ondemand-surgelevel")
    public void saveRetailer() {
        var request = SurgelevelV2.SaveRetailerRequest.newBuilder()
                .addRetailer(SurgelevelV2.Retailer.Option.newBuilder().setId(RETAILER_ID).build())
                .build();
        var response = client.saveRetailer(request);

        check.notNull(response.getRetailer(0));
        check.equals(response.getRetailer(0).getId(), RETAILER_ID);
        check.notNull(response.getRetailer(0).getConfig());
        check.notNull(response.getRetailer(0).getConfig().getFormula());
        check.notNull(response.getRetailer(0).getConfig().getInheritance(0));
        check.notNull(response.getRetailer(0).getConfig().getInheritance(0).getFormula());
    }

    @CaseId(117)
    @Story("Find Retailer")
    @Test(description = "Поиск ритейлера",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "saveRetailer")
    public void findRetailer() {
        var request = SurgelevelV2.FindRetailerRequest.newBuilder()
                .setCondition(SurgelevelV2.Retailer.Condition.newBuilder().addId(RETAILER_ID).build())
                .build();
        var response = client.findRetailer(request);

        check.notNull(response.getRetailer(0));
        check.equals(response.getRetailer(0).getId(), RETAILER_ID);
        check.notNull(response.getRetailer(0).getConfig());
        check.notNull(response.getRetailer(0).getConfig().getFormula());
        check.notNull(response.getRetailer(0).getConfig().getInheritance(0));
        check.notNull(response.getRetailer(0).getConfig().getInheritance(0).getFormula());
    }

    @CaseId(71)
    @Story("Save Store")
    @Test(description = "Сохранение магазина",
            groups = "ondemand-surgelevel")
    public void saveStore() {
        var request = SurgelevelV2.SaveStoreRequest.newBuilder()
                .addStore(SurgelevelV2.Store.Option.newBuilder()
                        .setId(STORE_ID)
                        .setRegionId(1L)
                        .setRetailerId(1L)
                        .setDeliveryarea(1L)
                        .setLocation(SurgelevelV2.Location.newBuilder().setLat(55f).setLon(55f))
                        .setOndemand(true)
                        .build())
                .build();
        var response = client.saveStore(request);

        check.notNull(response.getStore(0));
        check.equals(response.getStore(0).getId(), STORE_ID);
        check.equals(response.getStore(0).getRegionId(), 1L);
        check.equals(response.getStore(0).getRetailerId(), 1L);
        check.equals(response.getStore(0).getDeliveryarea(), 1L);
        check.equals(response.getStore(0).getOndemand(), true);
        check.equals(response.getStore(0).getLocation().getLat(), 55f);
        check.equals(response.getStore(0).getLocation().getLon(), 55f);
        check.notNull(response.getStore(0).getConfig());
        check.notNull(response.getStore(0).getConfig().getFormula());
        check.notNull(response.getStore(0).getConfig().getInheritance(0));
        check.notNull(response.getStore(0).getConfig().getInheritance(0).getFormula());
    }

    @CaseId(102)
    @Story("Find Store")
    @Test(description = "Поиск магазина",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "saveStore")
    public void findStore() {
        var request = SurgelevelV2.FindStoreRequest.newBuilder()
                .setCondition(SurgelevelV2.Store.Condition.newBuilder().addId(STORE_ID).build())
                .build();
        var response = client.findStore(request);

        check.notNull(response.getStore(0));
        check.equals(response.getStore(0).getId(), STORE_ID);
        check.equals(response.getStore(0).getRegionId(), 1L);
        check.equals(response.getStore(0).getRetailerId(), 1L);
        check.equals(response.getStore(0).getDeliveryarea(), 1L);
        check.equals(response.getStore(0).getOndemand(), true);
        check.equals(response.getStore(0).getLocation().getLat(), 55f);
        check.equals(response.getStore(0).getLocation().getLon(), 55f);
        check.notNull(response.getStore(0).getConfig());
        check.notNull(response.getStore(0).getConfig().getFormula());
        check.notNull(response.getStore(0).getConfig().getInheritance(0));
        check.notNull(response.getStore(0).getConfig().getInheritance(0).getFormula());
    }

    @CaseId(79)
    @Story("Save Formula")
    @Test(description = "Сохранени формулы",
            groups = "ondemand-surgelevel")
    public void saveFormula() {
        var request = SurgelevelV2.SaveFormulaRequest.newBuilder()
                .addFormula(SurgelevelV2.Formula.Option.newBuilder()
                        .setId(FORMULA_ID)
                        .setName("autotest")
                        .setScript(FORMULA_SCRIPT)
                        .build())
                .build();
        var response = client.saveFormula(request);

        check.notNull(response.getFormula(0));
        check.equals(response.getFormula(0).getId(), FORMULA_ID);
        check.equals(response.getFormula(0).getName(), "autotest");
        check.equals(response.getFormula(0).getScript(), FORMULA_SCRIPT);
    }

    @CaseId(109)
    @Story("Find Formula")
    @Test(description = "Поиск формулы",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "saveFormula")
    public void findFormula() {
        var request = SurgelevelV2.FindFormulaRequest.newBuilder()
                .setCondition(SurgelevelV2.Formula.Condition.newBuilder().addId(FORMULA_ID).build())
                .build();
        var response = client.findFormula(request);

        check.notNull(response.getFormula(0));
        check.equals(response.getFormula(0).getId(), FORMULA_ID);
        check.equals(response.getFormula(0).getName(), "autotest");
        check.equals(response.getFormula(0).getScript(), FORMULA_SCRIPT);
    }

    @CaseId(97)
    @Story("Call Formula")
    @Test(description = "Выполнение формулы",
            groups = "ondemand-surgelevel")
    public void callFormula() {
        String storeId = UUID.randomUUID().toString();

        var request = SurgelevelV2.CallFormulaRequest.newBuilder()
                .addSituation(SurgelevelV2.Situation.Option.newBuilder()
                        .setStore(SurgelevelV2.Store.Option.newBuilder()
                                .setId(storeId)
                                .setConfig(SurgelevelV2.Config.Option.newBuilder()
                                        .setFormula(SurgelevelV2.Formula.Option.newBuilder()
                                                .setScript(FORMULA_SCRIPT)
                                                .build())
                                        .build())
                                .build())
                        .addDemand(SurgelevelV2.Demand.Option.newBuilder()
                                .setStore(SurgelevelV2.Store.Option.newBuilder().setId(storeId).build())
                                .setShipment(SurgelevelV2.Shipment.Option.newBuilder()
                                        .setId(UUID.randomUUID().toString())
                                        .setStore(SurgelevelV2.Store.Option.newBuilder().setId(storeId).build())
                                        .setOndemand(true)
                                        .build())
                                .build())
                        .addDemand(SurgelevelV2.Demand.Option.newBuilder()
                                .setStore(SurgelevelV2.Store.Option.newBuilder().setId(storeId).build())
                                .setShipment(SurgelevelV2.Shipment.Option.newBuilder()
                                        .setId(UUID.randomUUID().toString())
                                        .setStore(SurgelevelV2.Store.Option.newBuilder().setId(storeId).build())
                                        .setOndemand(true)
                                        .build())
                                .build())
                        .addDemand(SurgelevelV2.Demand.Option.newBuilder()
                                .setStore(SurgelevelV2.Store.Option.newBuilder().setId(storeId).build())
                                .setShipment(SurgelevelV2.Shipment.Option.newBuilder()
                                        .setId(UUID.randomUUID().toString())
                                        .setStore(SurgelevelV2.Store.Option.newBuilder().setId(storeId).build())
                                        .setOndemand(true)
                                        .build())
                                .build())
                        .addSupply(SurgelevelV2.Supply.Option.newBuilder()
                                .setStore(SurgelevelV2.Store.Option.newBuilder().setId(storeId).build())
                                .setCandidate(SurgelevelV2.Candidate.Option.newBuilder()
                                        .setId(UUID.randomUUID().toString())
                                        .setOndemand(true)
                                        .build())
                                .build())
                        .addSupply(SurgelevelV2.Supply.Option.newBuilder()
                                .setStore(SurgelevelV2.Store.Option.newBuilder().setId(storeId).build())
                                .setCandidate(SurgelevelV2.Candidate.Option.newBuilder()
                                        .setId(UUID.randomUUID().toString())
                                        .setOndemand(true)
                                        .build())
                                .build())
                        .build())
                .build();
        var response = client.callFormula(request);

        check.notNull(response.getResult(0));
        check.notNull(response.getResult(0).getId());
        check.notNull(response.getResult(0).getMethod());
        check.equals(response.getResult(0).getWeight(), 5f);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (Objects.nonNull(STORE_ID)) {
            StoreDao.INSTANCE.delete(STORE_ID);
        }
        if (Objects.nonNull(FORMULA_ID)) {
            FormulaDao.INSTANCE.delete(FORMULA_ID);
        }
        if (Objects.nonNull(REGION_ID)) {
            RegionDao.INSTANCE.delete(REGION_ID);
        }
        if (Objects.nonNull(RETAILER_ID)) {
            RetailerDao.INSTANCE.delete(RETAILER_ID);
        }
    }
}
