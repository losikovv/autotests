package ru.instamart.test.api.on_demand.surgelevel;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.helper.SurgeLevelHelper;
import ru.instamart.api.request.surgelevel.BoostSurgeRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.jdbc.dao.eta.DisableEtaIntervalsDao;
import ru.instamart.jdbc.dao.surgelevel.ResultDao;
import ru.instamart.jdbc.dao.surgelevel.StoreDao;
import ru.instamart.kraken.util.ThreadUtil;
import surgelevelevent.Surgelevelevent;
import surgelevelevent.Surgelevelevent.SurgeEvent.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.helper.SurgeLevelHelper.addStore;
import static ru.instamart.api.helper.SurgeLevelHelper.checkSurgeLevelProduce;
import static ru.instamart.kraken.util.TimeUtil.getTimestampLongFromStringDtdb;

@Epic("Surgelevel")
@Feature("HTTP")
public class BoostTest extends RestBase {

    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final int BOOSTED_SURGELEVEL = 2;
    private String secret;
    private boolean storesAdded;

    @BeforeClass(alwaysRun = true)
    public void preConditions() {
        secret = SurgeLevelHelper.getInstance().getAuthToken();
        if (Objects.nonNull(secret)) {
            storesAdded = true;
            addStore(FIRST_STORE_ID, UUID.randomUUID().toString(), null, true, 1f, 1f, null, 1, 0, null);
            addStore(SECOND_STORE_ID, UUID.randomUUID().toString(), null, true, 1f, 1f, null, 1, 0, null);
        }
    }

    @TmsLinks({@TmsLink("169"), @TmsLink("175")})
    @Story("boost")
    @Test(description = "Увеличение цен",
            groups = "ondemand-surgelevel")
    public void boostPrices() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid(FIRST_STORE_ID)
                        .action("increase_prices")
                        .duration(1)
                        .build());

        checkStatusCode(response, 200, "");

        ThreadUtil.simplyAwait(10);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID, 1L);
        checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), FIRST_STORE_ID, BOOSTED_SURGELEVEL, BOOSTED_SURGELEVEL, 0, 0, Method.MANUAL);
    }

    @TmsLink("170")
    @Story("boost")
    @Test(description = "Отключение еты",
            groups = "ondemand-surgelevel")
    public void boostEta() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid(FIRST_STORE_ID)
                        .action("disable_eta")
                        .duration(1)
                        .build());

        checkStatusCode(response, 200, "");
        Allure.step("Проверка блокирования ЕТА магазина", () -> {
            final var intervalsList = DisableEtaIntervalsDao.INSTANCE.getIntervals(FIRST_STORE_ID);
            assertEquals(intervalsList.size(), 1, "Пустой список интервалов");

            final var startAt = intervalsList.get(0).getStartAt();
            final var endAt = intervalsList.get(0).getEndAt();
            final var startAtMillis = getTimestampLongFromStringDtdb(startAt.substring(0, startAt.length() - 7));
            final var endAtMillis = getTimestampLongFromStringDtdb(endAt.substring(0, endAt.length() - 7));

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(intervalsList.get(0).getIntervalType(), "eta", "Не верный тип интервала");
            softAssert.assertEquals(intervalsList.get(0).getSource(), "manual", "Не верный тип источника");
            softAssert.assertEquals(endAtMillis - startAtMillis, 3600000, "Не верная длительность");
            softAssert.assertAll();
        });
    }

    @TmsLink("171")
    @Story("boost")
    @Test(description = "Отключение еты и слотов",
            groups = "ondemand-surgelevel")
    public void boostEtaAndSlots() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid(SECOND_STORE_ID)
                        .action("disable_eta_and_slots")
                        .duration(1)
                        .build());

        checkStatusCode(response, 200, "");
        Allure.step("Проверка блокирования ЕТА и слотов магазина", () -> {
            final var intervalsList = DisableEtaIntervalsDao.INSTANCE.getIntervals(SECOND_STORE_ID);
            assertEquals(intervalsList.size(), 2, "Пустой список интервалов");

            var intervalTypes = new ArrayList<>(List.of("slot", "eta"));
            var firstIntervalType = intervalsList.get(0).getIntervalType();

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(intervalTypes.contains(intervalsList.get(0).getIntervalType()), "Не верный тип интервала");
            softAssert.assertEquals(intervalsList.get(0).getSource(), "manual", "Не верный тип источника");
            intervalTypes.remove(firstIntervalType);
            softAssert.assertEquals(intervalsList.get(1).getIntervalType(), intervalTypes.get(0), "Не верный тип интервала");
            softAssert.assertEquals(intervalsList.get(1).getSource(), "manual", "Не верный тип источника");
            softAssert.assertAll();
        });
    }

    @TmsLink("172")
    @Story("boost")
    @Test(description = "Отмена увеличения цен",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "boostPrices")
    public void boostPricesCancel() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid(FIRST_STORE_ID)
                        .action("cancel_increase_prices")
                        .duration(1)
                        .build());

        checkStatusCode(response, 200, "");

        ThreadUtil.simplyAwait(60);

        List<Surgelevelevent.SurgeEvent> surgeLevels = kafka.waitDataInKafkaTopicSurgeLevel(FIRST_STORE_ID, 3L);
        checkSurgeLevelProduce(surgeLevels, surgeLevels.size(), FIRST_STORE_ID, BOOSTED_SURGELEVEL, 0, 0, 0, Method.ACTUAL);
    }

    @TmsLink("172")
    @Story("boost")
    @Test(description = "Отмена отключения еты",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "boostEta")
    public void boostEtaCancel() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid(FIRST_STORE_ID)
                        .action("cancel_disable_eta")
                        .duration(1)
                        .build());

        checkStatusCode(response, 200, "");
        Allure.step("Проверка отмены блокирования ЕТА магазина", () -> {
            final var deletedIntervalsList = DisableEtaIntervalsDao.INSTANCE.getDeletedIntervals(FIRST_STORE_ID);
            assertEquals(deletedIntervalsList.size(), 1, "Пустой список удаленных интервалов");
            final var intervalsList = DisableEtaIntervalsDao.INSTANCE.getIntervals(FIRST_STORE_ID);
            assertEquals(intervalsList.size(), 1, "Пустой список интервалов");

            final var startAt = intervalsList.get(0).getStartAt();
            final var endAt = intervalsList.get(0).getEndAt();
            final var startAtMillis = getTimestampLongFromStringDtdb(startAt.substring(0, startAt.length() - 7));
            final var endAtMillis = getTimestampLongFromStringDtdb(endAt.substring(0, endAt.length() - 7));
            assertEquals(endAtMillis - startAtMillis, 1000, "Не верная длительность");
        });
    }

    @TmsLink("173")
    @Story("boost")
    @Test(description = "Отмена отключения еты и слотов",
            groups = "ondemand-surgelevel",
            dependsOnMethods = "boostEtaAndSlots")
    public void boostEtaAndSlotsCancel() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid(SECOND_STORE_ID)
                        .action("cancel_disable_eta_and_slots")
                        .duration(1)
                        .build());

        checkStatusCode(response, 200, "");
        Allure.step("Проверка отмены блокирования ЕТА и слотов магазина", () -> {
            final var deletedIntervalsList = DisableEtaIntervalsDao.INSTANCE.getDeletedIntervals(SECOND_STORE_ID);
            assertEquals(deletedIntervalsList.size(), 2, "Пустой список удаленных интервалов");
            final var intervalsList = DisableEtaIntervalsDao.INSTANCE.getIntervals(SECOND_STORE_ID);
            assertEquals(intervalsList.size(), 2, "Пустой список интервалов");
        });
    }

    @TmsLink("177")
    @Story("boost")
    @Test(description = "Отправка запроса на несколько магазинов",
            groups = "ondemand-surgelevel")
    public void boostMultipleStores() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuids(List.of(FIRST_STORE_ID, SECOND_STORE_ID))
                        .action("increase_prices")
                        .duration(1)
                        .build());

        checkStatusCode(response, 200, "");
        Allure.step("Проверка установки мануального сюрджа для нескольких магазинов", () -> {
            ThreadUtil.simplyAwait(10);

            final var firstResult = ResultDao.INSTANCE.findResult(FIRST_STORE_ID);
            final var secondResult = ResultDao.INSTANCE.findResult(SECOND_STORE_ID);

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(firstResult.getSurgeLevel().intValue(), BOOSTED_SURGELEVEL, "Не верный surgelevel");
            softAssert.assertEquals(secondResult.getSurgeLevel().intValue(), BOOSTED_SURGELEVEL, "Не верный surgelevel");
            softAssert.assertEquals(firstResult.getMethod().intValue(), 1, "Не верный метод расчета");
            softAssert.assertEquals(secondResult.getMethod().intValue(), 1, "Не верный метод расчета");
            softAssert.assertAll();
        });
    }

    @TmsLink("178")
    @Story("boost")
    @Test(description = "Получение ошибки при пустом списке магазинов",
            groups = "ondemand-surgelevel")
    public void boostEmptyStores() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .action("increase_prices")
                        .duration(1)
                        .build());

        checkStatusCode(response, 400, ContentType.JSON);
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "empty store uuids");
    }

    @TmsLink("179")
    @Story("boost")
    @Test(description = "Получение ошибки при не валидном действии",
            groups = "ondemand-surgelevel")
    public void boostEmptyAction() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid(UUID.randomUUID().toString())
                        .duration(1)
                        .build());

        checkStatusCode(response, 400, ContentType.JSON);
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "invalid action");
    }

    @TmsLink("180")
    @Story("boost")
    @Test(description = "Получение ошибки при не валидной длительности",
            groups = "ondemand-surgelevel")
    public void boostEmptyDuration() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid(UUID.randomUUID().toString())
                        .action("increase_prices")
                        .build());

        checkStatusCode(response, 400, ContentType.JSON);
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "invalid duration");
    }

    @TmsLink("181")
    @Story("boost")
    @Test(description = "Получение ошибки авторизации",
            groups = "ondemand-surgelevel")
    public void boostNoAuth() {
        final Response response = BoostSurgeRequest.POST_NO_AUTH(BoostSurgeRequest.PostBoost.builder()
                .storeUuid(UUID.randomUUID().toString())
                .action("increase_prices")
                .duration(1)
                .build());

        checkStatusCode(response, 401, ContentType.JSON);
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "Unauthorized");
    }

    @TmsLink("182")
    @Story("boost")
    @Test(description = "Получение ошибки не валидного списка магазинов",
            groups = "ondemand-surgelevel")
    public void boostInvalid() {

        if (Objects.isNull(secret)) {
            throw new SkipException("Пропускаем, потому что не смогли получить secret");
        }

        final Response response = BoostSurgeRequest.POST(BoostSurgeRequest.PostBoost.builder()
                        .storeUuid("test")
                        .action("increase_prices")
                        .duration(1)
                        .build());

        checkStatusCode(response, 400, ContentType.JSON);
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "invalid store uuid format");
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        if (storesAdded) {
            if (Objects.nonNull(FIRST_STORE_ID)) {
                StoreDao.INSTANCE.delete(FIRST_STORE_ID);
            }
            if (Objects.nonNull(SECOND_STORE_ID)) {
                StoreDao.INSTANCE.delete(SECOND_STORE_ID);
            }
        }
    }
}
