package ru.instamart.test.api.dispatch.shifts;

import candidates.StoreChangedOuterClass;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.kafka.configs.KafkaConfigs.configStoreChanged;

@Epic("Shifts")
@Feature("Endpoints")
public class ShiftsPlanningPeriodsTest extends RestBase {


    @BeforeClass(alwaysRun = true,
            description = "Отправка информации о создании магазина через кафку")
    public void preconditions() {
        var storeChanged = StoreChangedOuterClass.StoreChanged.newBuilder()

                .build();
        kafka.publish(configStoreChanged(), storeChanged.toByteArray());
    }

    @CaseId(81)
    @Story("Импорт плановых периодов")
    @Test(groups = {"api-shifts"},
            description = "Добавление плановых периодов используя идентификатор зоны доставки")
    public void importPlanningPeriod200() {

    }
}
