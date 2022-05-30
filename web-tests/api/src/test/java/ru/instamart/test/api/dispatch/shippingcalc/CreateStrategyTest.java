package ru.instamart.test.api.dispatch.shippingcalc;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.ConditionsDao;
import ru.instamart.jdbc.dao.shippingcalc.RulesDao;
import ru.instamart.jdbc.dao.shippingcalc.StrategiesDao;
import ru.instamart.jdbc.entity.shippingcalc.ConditionsEntity;
import ru.instamart.jdbc.entity.shippingcalc.RulesEntity;
import ru.instamart.jdbc.entity.shippingcalc.StrategiesEntity;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.ShippingcalcGrpc;
import shippingcalc.ShippingcalcOuterClass;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

@Epic("On Demand")
@Feature("ShippingCalc")
public class CreateStrategyTest extends RestBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
    }

    //просто проверка работы методов, позже переделать
    @CaseId(46)
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с валидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void createStrategy() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(1)
                        .setScriptParamValues("{}")
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("test")
                .setName("test")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("test")
                .setDeliveryTypeValue(2)
                .build();

        var response = clientShippingCalc.createStrategy(request);

        StrategiesEntity strategy = StrategiesDao.INSTANCE.getStrategy(response.getStrategyId());
        List<RulesEntity> rules = RulesDao.INSTANCE.getRules(response.getStrategyId());
        List<ConditionsEntity> conditions = ConditionsDao.INSTANCE.getConditions(rules.get(0).getId(), rules.get(0).getId());

        compareTwoObjects(response.getStrategyId(), strategy.getId());
        Assert.assertFalse(rules.isEmpty());
        Assert.assertFalse(conditions.isEmpty());
    }
}
