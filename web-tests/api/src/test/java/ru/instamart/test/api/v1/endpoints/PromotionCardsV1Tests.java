package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v1.PromotionCardV1;
import ru.instamart.api.request.v1.PromotionCardsV1Request;
import ru.instamart.api.response.v1.PromotionCardV1Response;
import ru.instamart.api.response.v1.PromotionCardsV1Response;
import ru.instamart.jdbc.dao.PromotionCardsDao;
import ru.instamart.jdbc.entity.PromotionCardsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkPromotionCard;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("In App баннеры")
public class PromotionCardsV1Tests extends RestBase {

    private List<PromotionCardV1> promotionCards;
    private Long promotionCardId;
    private PromotionCardsV1Request.PromotionCard promotionCard;
    private List<Integer> storeIds;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
        storeIds = List.of(EnvironmentProperties.DEFAULT_SID, EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
    }

    @CaseId(2263)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка In App баннеров")
    public void getPromotionCards() {
        final Response response = PromotionCardsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionCardsV1Response.class);
        promotionCards = response.as(PromotionCardsV1Response.class).getPromotionCards();
        int countFromDb = PromotionCardsDao.INSTANCE.getCount();
        compareTwoObjects(promotionCards.size(), countFromDb);
    }

    @CaseId(2264)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание In App баннера",
            dependsOnMethods = "getPromotionCards")
    public void createPromotionCard() {
        List<PromotionCardV1> filteredPromotionCards = promotionCards.stream().filter(p -> Objects.nonNull(p.getCategory())).collect(Collectors.toList());
        promotionCard = PromotionCardsV1Request.PromotionCard.builder()
                .backgroundColor("#7934c2")
                .fullDescription("Autotest-" + Generate.literalString(6))
                .shortDescription("Autotest-" + Generate.literalString(2))
                .messageColor("#c2f1f2")
                .tenantIds(filteredPromotionCards.get(0).getTenantIds().get(0))
                .title("Autotest-" + Generate.literalString(5))
                .type("PromotionCards::" + filteredPromotionCards.get(0).getType())
                .categoryId(filteredPromotionCards.get(0).getCategory().getId())
                .published(false)
                .webAppLink("https://sbermarket.ru/2")
                .mobileAppLink("https://sbermarket.ru")
                .promotionId(apiV1.getPromotions().get(0).getId())
                .build();
        final Response response = PromotionCardsV1Request.POST(promotionCard, storeIds);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionCardV1Response.class);
        PromotionCardV1 promotionCardFromResponse = response.as(PromotionCardV1Response.class).getPromotionCard();
        promotionCardId = promotionCardFromResponse.getId();
        checkPromotionCard(promotionCard, promotionCardFromResponse, storeIds);
    }

    @JsonDataProvider(path = "data/json_v1/api_v1_negative_promotion_card_data.json", type = RestDataProvider.PromotionCardsV1TestDataRoot.class)
    @CaseIDs(value = {@CaseId(2265), @CaseId(2266), @CaseId(2267), @CaseId(2268)})
    @Test(description = "Создание In App баннера с невалидными параметрами",
            groups = {"api-instamart-regress"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class)
    public void createPromotionCardWithInvalidParams(RestDataProvider.PromotionCardsV1TestData testData) {
        final Response response = PromotionCardsV1Request.POST(testData.getPromotionCard(), storeIds);
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains(testData.getErrorMessage()), "Текст ошибки неверный");
    }

    @CaseId(2269)
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение In App баннера",
            dependsOnMethods = "createPromotionCard")
    public void editPromotionCard() {
        promotionCard.setFullDescription("Autotest-" + Generate.literalString(6));
        promotionCard.setTitle("Autotest-" + Generate.literalString(4));
        promotionCard.setMessageColor("#c2f1f6");
        promotionCard.setPublished(true);
        final Response response = PromotionCardsV1Request.PUT(promotionCardId, promotionCard, "src/test/resources/data/sample.jpg");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionCardV1Response.class);
        PromotionCardV1 promotionCardFromResponse = response.as(PromotionCardV1Response.class).getPromotionCard();
        checkPromotionCard(promotionCard, promotionCardFromResponse, storeIds);
    }

    @CaseId(2270)
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение несуществующего In App баннера",
            dependsOnMethods = "createPromotionCard")
    public void editNonExistentPromotionCard() {
        final Response response = PromotionCardsV1Request.PUT(0L, promotionCard, "src/test/resources/data/sample.jpg");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2271)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение In App баннера",
            dependsOnMethods = "editPromotionCard")
    public void getPromotionCard() {
        final Response response = PromotionCardsV1Request.GET(promotionCardId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionCardV1Response.class);
        PromotionCardV1 promotionCardFromResponse = response.as(PromotionCardV1Response.class).getPromotionCard();
        checkPromotionCard(promotionCard, promotionCardFromResponse, storeIds);
    }

    @CaseId(2272)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение несуществующего In App баннера")
    public void getNonExistentPromotionCard() {
        final Response response = PromotionCardsV1Request.GET(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2273)
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение позиции In App баннера",
            dependsOnMethods = "getPromotionCard")
    public void changePromotionCardPosition() {
        final Response response = PromotionCardsV1Request.PUT(promotionCardId, 22);
        checkStatusCode200(response);
        PromotionCardsEntity promotionCardFromDb = PromotionCardsDao.INSTANCE.findById(promotionCardId).get();
        compareTwoObjects(promotionCardFromDb.getPosition(), 22);
    }

    @CaseId(2274)
    @Test(groups = {"api-instamart-regress"},
            description = "Изменение позиции несуществующего In App баннера")
    public void changeNonExistentPromotionCardPosition() {
        final Response response = PromotionCardsV1Request.PUT(0L, 22);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2275)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление In App баннера",
            dependsOnMethods = "changePromotionCardPosition")
    public void deletePromotionCard() {
        admin.authApi();
        final Response response = PromotionCardsV1Request.DELETE(promotionCardId);
        checkStatusCode200(response);
        Assert.assertTrue(PromotionCardsDao.INSTANCE.findById(promotionCardId).isEmpty(), "Промо-карточка не удалилась");
    }

    @CaseId(2276)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление несуществующего In App баннера",
            dependsOnMethods = "changePromotionCardPosition")
    public void deleteNonExistentPromotionCard() {
        final Response response = PromotionCardsV1Request.DELETE(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
