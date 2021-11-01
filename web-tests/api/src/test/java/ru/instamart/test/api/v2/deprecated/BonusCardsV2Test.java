package ru.instamart.test.api.v2.deprecated;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.BonusCardsV2Request;
import ru.instamart.api.response.v2.BonusCardV2Response;
import ru.instamart.api.response.v2.BonusCardsV2Response;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Список доступных бонусных карт")
@Deprecated
public final class BonusCardsV2Test extends RestBase {
    private final String bonusCardNumber = "1" + RandomStringUtils.randomNumeric(7);
    private int bonusCardId;

    @Deprecated
    @Test(groups = {},
            description = "Нет бонусных карт")
    public void testNoBonusCards() {
        SessionFactory.makeSession(SessionType.API_V2_FB);

        final Response response = BonusCardsV2Request.GET();
        checkStatusCode200(response);
        final BonusCardsV2Response bonusCardsV2Response = response.as(BonusCardsV2Response.class);
        assertTrue(bonusCardsV2Response.getBonusCards().isEmpty(), "bonus_cards вернулся не пустым");
    }

    @Deprecated
    @Test(groups = {},
            description = "Одна или несколько бонусных карт",
            dependsOnMethods = "testAddBonusCard200")
    public void testWithBonusCards() {
        SessionFactory.createSessionToken(SessionType.API_V2_FB, UserManager.getDefaultUser());
        final Response response = BonusCardsV2Request.GET();
        checkStatusCode200(response);
        final BonusCardsV2Response bonusCardsV2Response = response.as(BonusCardsV2Response.class);
        assertFalse(bonusCardsV2Response.getBonusCards().isEmpty(), "bonus_cards вернулся пустой");
    }

    @Deprecated
    @Test(groups = {},
            description = "Добавление бонусной карты")
    public void testAddBonusCard200() {
        SessionFactory.createSessionToken(SessionType.API_V2_FB, UserManager.getDefaultUser());
        final Response response = BonusCardsV2Request.POST(3, bonusCardNumber);
        checkStatusCode200(response);
        final BonusCardV2Response bonusCardV2Response = response.as(BonusCardV2Response.class);
        assertEquals(bonusCardV2Response.getBonusCard().getNumber(), bonusCardNumber, "bonus_cards.number не равен введенному");
        bonusCardId = bonusCardV2Response.getBonusCard().getId();
    }

    @Deprecated
    @Test(groups = {},
            description = "Добавление некорректной бонусной карты")
    public void testAddBonusCard422WrongBonusCardNumber() {
        SessionFactory.createSessionToken(SessionType.API_V2_FB, UserManager.getDefaultUser());
        final Response response = BonusCardsV2Request.POST(3, "1234123");
        checkStatusCode422(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Добавление бонусной карты несуществующей бонусной программы")
    public void testAddBonusCard422WrongBonusProgramId() {
        SessionFactory.createSessionToken(SessionType.API_V2_FB, UserManager.getDefaultUser());
        final Response response = BonusCardsV2Request.POST(0, bonusCardNumber);
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Удаление бонусной карты",
            dependsOnMethods = "testWithBonusCards")
    public void testDeleteBonusCard200() {
        SessionFactory.createSessionToken(SessionType.API_V2_FB, UserManager.getDefaultUser());
        final Response response = BonusCardsV2Request.DELETE(bonusCardId);
        checkStatusCode200(response);
        final BonusCardV2Response bonusCardV2Response = response.as(BonusCardV2Response.class);
        assertEquals(bonusCardV2Response.getBonusCard().getNumber(), bonusCardNumber, "bonus_cards.number не равен введенному");
    }

    @Deprecated
    @Test(groups = {},
            description = "Удаление несуществующей бонусной карты")
    public void testDeleteBonusCard404() {
        SessionFactory.createSessionToken(SessionType.API_V2_FB, UserManager.getDefaultUser());
        final Response response = BonusCardsV2Request.DELETE(0);
        checkStatusCode404(response);
    }
}
