package ru.instamart.test.api.on_demand.eta;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.helper.EtaHelper;
import ru.instamart.jdbc.dao.eta.DisableEtaIntervalsDao;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import io.qameta.allure.TmsLink;

import java.util.Objects;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.helper.EtaHelper.*;
import static ru.instamart.api.helper.SurgeLevelHelper.publishEventSurge;

@Epic("ETA")
@Feature("Surge Reaction")
public class SurgeReactionEtaTest extends RestBase {

    private boolean etaSurgeIntervalsEnabled;
    private boolean storesAdded;
    private float surgeLevel;
    private int disableEtaDuration;
    private int disableSlotDuration;
    private int intervalsCount;
    private final String STORE_UUID_WITH_SURGE = UUID.randomUUID().toString();
    private final String STORE_UUID_WITH_LOW_SURGE = UUID.randomUUID().toString();
    private final String STORE_UUID_WITHOUT_SURGE = UUID.randomUUID().toString();

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        final var surgeIntervals = EtaHelper.getInstance().getSurgeIntervals();

        if (!surgeIntervals.isBlank()) {
            surgeLevel = getIntervalsBoundary(surgeIntervals);
            disableEtaDuration = getIntervalsDisableEtaDuration(surgeIntervals);
            disableSlotDuration = getIntervalsDisableSlotsDuration(surgeIntervals);
            if (surgeLevel > 0f && (disableEtaDuration > 0 || disableSlotDuration > 0)) {
                etaSurgeIntervalsEnabled = true;
                storesAdded = true;
                addStore(STORE_UUID_WITH_SURGE, 55.7010f, 37.7280f, "Europe/Moscow", false, "00:00:00", "00:00:00", "00:00:00", true, true, 1);
                addStore(STORE_UUID_WITH_LOW_SURGE, 55.7010f, 37.7280f, "Europe/Moscow", false, "00:00:00", "00:00:00", "00:00:00", true, true, 1);
                addStore(STORE_UUID_WITHOUT_SURGE, 55.7010f, 37.7280f, "Europe/Moscow", false, "00:00:00", "00:00:00", "00:00:00", true, false, 1);
            }
        }
    }

    @TmsLink("270")
    @Story("ETA surge reaction")
    @Test(description = "Автоматическое создание интервала отключения ЕТА и слотов, при повышенном сурдже",
            groups = "ondemand-eta")
    public void etaSurgeReactionOn() {

        if (!etaSurgeIntervalsEnabled) {
            throw new SkipException("Пропускаем, потому что SURGE_INTERVALS пустые");
        }

        publishEventSurge(STORE_UUID_WITH_SURGE, surgeLevel, 1);

        Allure.step("Проверка автоматического отключения ЕТА и слотов", () -> {
            final var intervalsList = DisableEtaIntervalsDao.INSTANCE.getIntervals(STORE_UUID_WITH_SURGE).toString();
            if (disableEtaDuration > 0) {
                assertTrue(intervalsList.contains("eta"), "Не создали интервал отключения ЕТА");
                intervalsCount++;
            }
            if (disableSlotDuration > 0) {
                assertTrue(intervalsList.contains("slot"), "Не создали интервал отключения слота");
                intervalsCount++;
            }
            assertEquals(StringUtils.countMatches(intervalsList, "auto"), intervalsCount, "Не верное кол-во автоматически отключенных интервалов");
        });
    }

    @TmsLink("271")
    @Story("ETA surge reaction")
    @Test(description = "Интервал отключения ЕТА и слотов не создается автоматически, если у магазина выключен флаг surge_enabled",
            groups = "ondemand-eta")
    public void etaSurgeReactionOff() {

        if (!etaSurgeIntervalsEnabled) {
            throw new SkipException("Пропускаем, потому что SURGE_INTERVALS пустые");
        }

        publishEventSurge(STORE_UUID_WITHOUT_SURGE, surgeLevel, 1);

        Allure.step("Проверка отсутствия автоматического отключения ЕТА и слотов", () -> {
            final var intervalsList = DisableEtaIntervalsDao.INSTANCE.getIntervals(STORE_UUID_WITHOUT_SURGE);
            assertTrue(intervalsList.isEmpty(), "Не пустой список интервалов отключения ЕТА и слотов");
        });
    }

    @TmsLink("272")
    @Story("ETA surge reaction")
    @Test(description = "Интервал отключения ЕТА и слотов не создается автоматически, если сурдж у магазина не попадет в интервалы SURGE_INTERVALS",
            groups = "ondemand-eta")
    public void etaSurgeReactionNotHighEnough() {

        if (!etaSurgeIntervalsEnabled) {
            throw new SkipException("Пропускаем, потому что SURGE_INTERVALS пустые");
        }

        publishEventSurge(STORE_UUID_WITH_LOW_SURGE, 0, 1);

        Allure.step("Проверка отсутствия автоматического отключения ЕТА и слотов", () -> {
            final var intervalsList = DisableEtaIntervalsDao.INSTANCE.getIntervals(STORE_UUID_WITH_LOW_SURGE);
            assertTrue(intervalsList.isEmpty(), "Не пустой список интервалов отключения ЕТА и слотов");
        });
    }

    @TmsLink("273")
    @Story("ETA surge reaction")
    @Test(description = "Интервал отключения ЕТА и слотов не создается автоматически, если у магазина уже есть интервал на текущее время",
            groups = "ondemand-eta",
            dependsOnMethods = "etaSurgeReactionOn")
    public void etaSurgeReactionNoReplacing() {

        if (!etaSurgeIntervalsEnabled) {
            throw new SkipException("Пропускаем, потому что SURGE_INTERVALS пустые");
        }

        publishEventSurge(STORE_UUID_WITH_SURGE, surgeLevel, 1);

        Allure.step("Проверка отсутствия автоматического отключения ЕТА и слотов", () -> {
            final var intervalsList = DisableEtaIntervalsDao.INSTANCE.getIntervals(STORE_UUID_WITH_SURGE).toString();
            assertEquals(StringUtils.countMatches(intervalsList, "auto"), intervalsCount, "Не верное кол-во автоматически отключенных интервалов");
        });
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (storesAdded) {
            if (Objects.nonNull(STORE_UUID_WITH_SURGE)) {
                StoreParametersDao.INSTANCE.delete(STORE_UUID_WITH_SURGE);
            }
            if (Objects.nonNull(STORE_UUID_WITH_LOW_SURGE)) {
                StoreParametersDao.INSTANCE.delete(STORE_UUID_WITH_LOW_SURGE);
            }
            if (Objects.nonNull(STORE_UUID_WITHOUT_SURGE)) {
                StoreParametersDao.INSTANCE.delete(STORE_UUID_WITHOUT_SURGE);
            }
        }
    }
}
