package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.requests.v2.BonusCardsV2Request;
import ru.instamart.api.responses.v2.BonusCardsV2Response;
import ru.instamart.core.testdata.UserManager;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Список доступных бонусных карт")
public final class BonusCardsV2Test extends RestBase {

    @CaseId(374)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Нет бонусных карт")
    public void testNoBonusCards() {
        SessionFactory.makeSession(SessionType.APIV2);
        final Response response = BonusCardsV2Request.GET();
        checkStatusCode200(response);
        final BonusCardsV2Response bonusCardsV2Response = response.as(BonusCardsV2Response.class);
        assertTrue(bonusCardsV2Response.getBonusCards().isEmpty());
    }

    @CaseId(375)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Одна или несколько бонусных карт")
    public void testWithBonusCards() {
        SessionFactory.createSessionToken(SessionType.APIV2, UserManager.getDefaultUser());
        final Response response = BonusCardsV2Request.GET();
        checkStatusCode200(response);
        final BonusCardsV2Response bonusCardsV2Response = response.as(BonusCardsV2Response.class);
        assertFalse(bonusCardsV2Response.getBonusCards().isEmpty());
    }
}
