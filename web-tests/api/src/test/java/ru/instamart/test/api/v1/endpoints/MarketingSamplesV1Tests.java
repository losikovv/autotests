package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.MarketingSampleV1;
import ru.instamart.api.request.v1.MarketingSamplesV1Request;
import ru.instamart.api.response.v1.MarketingSampleV1Response;
import ru.instamart.api.response.v1.MarketingSamplesV1Response;
import ru.instamart.api.response.v2.ProfileV2Response;
import ru.instamart.jdbc.dao.stf.MarketingSamplesDao;
import ru.instamart.jdbc.entity.stf.MarketingSamplesEntity;

import java.util.List;
import java.util.Optional;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.compareMarketingSamples;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV1")
@Feature("Admin Web")
public class MarketingSamplesV1Tests extends RestBase {

    private Long sampleId;
    private ProfileV2Response profile;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @Story("Маркетинговые сэмплы")
    @CaseId(978)
    @Test(description = "Получение списка всех маркетинговых сэмплов",
            groups = {"api-instamart-regress"})
    public void getMarketingSamples() {
        final Response response = MarketingSamplesV1Request.GET();
        checkStatusCode200(response);
        List<MarketingSampleV1> marketingSamplesFromResponse = response.as(MarketingSamplesV1Response.class).getMarketingSamples();
        int marketingSamplesCountFromDb = MarketingSamplesDao.INSTANCE.getCount();
        compareTwoObjects(marketingSamplesFromResponse.size(), marketingSamplesCountFromDb);
    }

    @Story("Маркетинговые сэмплы")
    @CaseId(977)
    @Test(description = "Создание нового маркетингового сэмпла",
            groups = {"api-instamart-regress"})
    public void createMarketingSample() {
        SessionFactory.makeSession(SessionType.API_V2);
        profile = apiV2.getProfile();
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = MarketingSamplesV1Request.POST(profile.getUser().getId());
        checkStatusCode200(response);
        MarketingSampleV1 marketingSampleFromResponse = response.as(MarketingSampleV1Response.class).getMarketingSample();
        sampleId = marketingSampleFromResponse.getId();
        Optional<MarketingSamplesEntity> marketingSamplesEntity = MarketingSamplesDao.INSTANCE.findByIdWithUser(sampleId);
        compareMarketingSamples(profile.getUser().getEmail(), marketingSampleFromResponse, marketingSamplesEntity, "Test marketing sample");
    }

    @Story("Маркетинговые сэмплы")
    @CaseId(983)
    @Test(description = "Получение маркетингового сэмпла",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createMarketingSample")
    public void getMarketingSample() {
        final Response response = MarketingSamplesV1Request.GET(sampleId);
        checkStatusCode200(response);
        MarketingSampleV1 marketingSampleFromResponse = response.as(MarketingSampleV1Response.class).getMarketingSample();
        Optional<MarketingSamplesEntity> marketingSamplesEntity = MarketingSamplesDao.INSTANCE.findByIdWithUser(sampleId);
        compareMarketingSamples(profile.getUser().getEmail(), marketingSampleFromResponse, marketingSamplesEntity, "Test marketing sample");
    }

    @Story("Маркетинговые сэмплы")
    @CaseId(979)
    @Test(description = "Изменение маркетингового сэмпла",
            groups = {"api-instamart-regress"},
            dependsOnMethods = {"createMarketingSample", "getMarketingSample"})
    public void editMarketingSample() {
        SessionFactory.makeSession(SessionType.API_V2);
        ProfileV2Response profile = apiV2.getProfile();
        SessionFactory.clearSession(SessionType.API_V2);

        final Response response = MarketingSamplesV1Request.PUT(sampleId, profile.getUser().getId());
        checkStatusCode200(response);
        MarketingSampleV1 marketingSampleFromResponse = response.as(MarketingSampleV1Response.class).getMarketingSample();
        Optional<MarketingSamplesEntity> marketingSamplesEntity = MarketingSamplesDao.INSTANCE.findByIdWithUser(sampleId);
        compareMarketingSamples(profile.getUser().getEmail(), marketingSampleFromResponse, marketingSamplesEntity, "Updated Test marketing sample");
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @Story("Маркетинговые сэмплы")
    @CaseId(980)
    @Test(description = "Удаление маркетингового сэмпла",
            groups = {"api-instamart-regress"},
            dependsOnMethods = {"createMarketingSample", "getMarketingSample", "editMarketingSample"})
    public void deleteMarketingSample() {
        final Response response = MarketingSamplesV1Request.DELETE(sampleId);
        checkStatusCode200(response);
        Optional<MarketingSamplesEntity> marketingSamplesEntity = MarketingSamplesDao.INSTANCE.findById(sampleId);
        checkFieldIsNotEmpty(marketingSamplesEntity.get().getDeletedAt(), "дата удаления");
    }

    @Story("Маркетинговые сэмплы")
    @CaseId(981)
    @Test(description = "Изменение несуществующего маркетингового сэмпла",
            groups = {"api-instamart-regress"})
    public void editNonExistingMarketingSample() {
        final Response response = MarketingSamplesV1Request.PUT(0L, profile.getUser().getId());
        checkStatusCode404(response);
    }

    @Issue("B2C-6444")
    @Skip(onServer = Server.STAGING)
    @Story("Маркетинговые сэмплы")
    @CaseId(982)
    @Test(description = "Удаление несуществующего маркетингового сэмпла",
            groups = {"api-instamart-regress"})
    public void deleteNonExistingMarketingSample() {
        final Response response = MarketingSamplesV1Request.DELETE(0L);
        checkStatusCode404(response);
    }

    @Story("Маркетинговые сэмплы")
    @CaseId(984)
    @Test(description = "Получение несуществующего маркетингового сэмпла",
            groups = {"api-instamart-regress"})
    public void getNonExistingMarketingSample() {
        final Response response = MarketingSamplesV1Request.GET(0L);
        checkStatusCode404(response);
    }
}
