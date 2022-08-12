package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.Assert;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.util.CryptCard;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.CreditCardsV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.credit_cards.CreditCardV2;
import ru.instamart.api.request.v2.CreditCardsV2Request;
import ru.instamart.api.request.v2.CreditCardsV2Request.CreditCard;
import ru.instamart.api.response.v2.CreditCardV2Response;
import ru.instamart.api.response.v2.CreditCardsV2Response;

import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

@Epic("ApiV2")
@Feature("Банковские карты")
public class CreditCardsV2Test extends RestBase {

    private Integer creditCardId;
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAol3r+CqqmOgfLXbpTPrrQqP9yYEKDSLHVQgyWeUnoZdy06ZD8Yt0s5ENtoKOsfdvA1GYDhBwxq9v15AXiynAxkhu9UZ/b3RjMh9tJ/sNrVBGfnJvKRBhIedcNgMtvmMsuiQDUqIV+Ia6BP3S0w8l9WEkFelK+y7bZeXfaAEfCyMAcsW7C8CGKryXG8v5ZKBwqmAo5D5O43wGPjVoHL9G9EGIhQbdNcx2bmy7zFffMhI9hiaTWjhkwmPsqanLB9AzYiZ1431s3lYGu+ZCu66aI7MkmsDGeHV/Z/4HCIzA5KrSx/KZ2Yw27n6Hqj3AYfiYehETKNAEAKF7gNpEU+ad2QIDAQAB";

    @BeforeMethod(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @Skip
    @Issue("STF-6633")
    @CaseId(500)
    @Story("Добавить новую карту")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Добавить новую карту с указанием обязательных параметров")
    public void addANewCardWithRequiredParameters() {
        String card = CreditCardsV2.CARD2.getNumber();
        String cvc = CreditCardsV2.CARD2.getCvc();
        String expDate = CreditCardsV2.CARD2.getYear() + CreditCardsV2.CARD2.getMonth();
        String lastDigits = card.substring(card.length() - 4);

        String resultString = getTimestamp() + "/" + UUID.randomUUID() + "/" + card + "/" + cvc + "/" + expDate + "/" + UUID.randomUUID();
        final CreditCard expectedCreditCard = CreditCard.builder()
                .name("TESTOV TEST")
                .year(CreditCardsV2.CARD2.getYear())
                .month(CreditCardsV2.CARD2.getMonth())
                .lastDigits(lastDigits)
                .cryptogramPacket(CryptCard.INSTANCE.encrypt(resultString,publicKey))
                .build();
        final Response response = CreditCardsV2Request.POST(expectedCreditCard);
        checkStatusCode200(response);
        CreditCardV2 creditCardFromResponse = response.as(CreditCardV2Response.class).getCreditCard();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(creditCardFromResponse.getName(), expectedCreditCard.getName(), "Неправильные данные держателя карты");
        softAssert.assertEquals(creditCardFromResponse.getYear(), expectedCreditCard.getYear(), "Год не совпадает");
        softAssert.assertEquals(creditCardFromResponse.getMonth(), expectedCreditCard.getMonth(), "Месяц не совпадает");
        softAssert.assertEquals(creditCardFromResponse.getLastDigits(), expectedCreditCard.getLastDigits(), "Последние цифры карты не совпадают");
        softAssert.assertAll();
    }

    @Skip
    @Issue("STF-6633")
    @CaseId(501)
    @Story("Добавить новую карту")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Добавить новую карту с дополнительным полем title")
    public void addANewCardWithAnAdditionalTitleField() {
        String card = CreditCardsV2.CARD1.getNumber();
        String lastDigits = card.substring(card.lastIndexOf(" ") + 1);
        final CreditCard expectedCreditCard = CreditCard.builder()
                .title("Новая карта")
                .name("TESTOV TEST")
                .year(CreditCardsV2.CARD1.getYear())
                .month(CreditCardsV2.CARD1.getMonth())
                .lastDigits(lastDigits)
                .cryptogramPacket(CreditCardsV2.CARD1.getCryptogramPacket())
                .build();
        final Response response = CreditCardsV2Request.POST(expectedCreditCard);
        checkStatusCode200(response);
        CreditCardV2 creditCardFromResponse = response.as(CreditCardV2Response.class).getCreditCard();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(creditCardFromResponse.getTitle(), expectedCreditCard.getTitle(), "Имя карты не совпадает");
        softAssert.assertEquals(creditCardFromResponse.getName(), expectedCreditCard.getName(), "Неправильные данные держателя карты");
        softAssert.assertEquals(creditCardFromResponse.getYear(), expectedCreditCard.getYear(), "Год не совпадает");
        softAssert.assertEquals(creditCardFromResponse.getMonth(), expectedCreditCard.getMonth(), "Месяц не совпадает");
        softAssert.assertEquals(creditCardFromResponse.getLastDigits(), expectedCreditCard.getLastDigits(), "Последние цифры карты не совпадают");
        softAssert.assertAll();
    }

    @Skip
    @Issue("STF-6633")
    @CaseId(502)
    @Story("Добавить новую карту")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Добавить новую карту с дополнительным полем title")
    public void addNewCard() {
        String card = CreditCardsV2.CARD1.getNumber();
        String lastDigits = card.substring(card.lastIndexOf(" ") + 1);
        final CreditCard creditCard = CreditCard.builder()
                .title("Новая карта")
                .name("TESTOV TEST")
                .year(CreditCardsV2.CARD1.getYear())
                .month(CreditCardsV2.CARD1.getMonth())
                .lastDigits(lastDigits)
                .cryptogramPacket(CreditCardsV2.CARD1.getCryptogramPacket())
                .build();
        final Response response = CreditCardsV2Request.POST(creditCard);
        checkStatusCode200(response);
        CreditCardV2Response creditCardV2 = response.as(CreditCardV2Response.class);
        creditCardId = creditCardV2.getCreditCard().getId();
        //TODO Добавить проверки после фикса задачи
    }

    @CaseId(495)
    @Story("Получение списка всех банковских карт")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "У пользователя нет добавленных карт")
    public void testNoCreditCards() {
        final Response response = CreditCardsV2Request.GET();
        checkStatusCode200(response);
        final CreditCardsV2Response creditCardsV2Response = response.as(CreditCardsV2Response.class);
        Assert.assertTrue(creditCardsV2Response.getCreditCards().isEmpty(), "credit_cards вернулся не пустым");
    }

    @Skip
    @Issue("STF-6633")
    @CaseId(496)
    @Story("Получение списка всех банковских карт")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "У пользователя одна добавленная карта",
            dependsOnMethods = "addANewCardWithRequiredParameters")
    public void testOneCreditCards() {
        final Response response = CreditCardsV2Request.GET();
        checkStatusCode200(response);
        final CreditCardsV2Response creditCardsV2Response = response.as(CreditCardsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(creditCardsV2Response.getCreditCards().size(), 1, "credit_cards вернулся пустым или несколько");
    }

    @Skip
    @Issue("STF-6633")
    @CaseId(497)
    @Story("Получение списка всех банковских карт")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "У пользователя несколько добавленных карт",
            dependsOnMethods = "addNewCard")
    public void testSomeCreditCards() {
        final Response response = CreditCardsV2Request.GET();
        checkStatusCode200(response);
        final CreditCardsV2Response creditCardsV2Response = response.as(CreditCardsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(creditCardsV2Response.getCreditCards().size() > 1, "credit_cards вернулся пустым или один");
    }

    @CaseId(506)
    @Story("Получение списка всех банковских карт")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Удаление карты по не существующим ID"
    )
    public void failedTestDeleteCreditCards() {
        final Response response = CreditCardsV2Request.DELETE("failedId");
        checkStatusCode404(response);
        checkError(response, "Кредитная карта не существует");
    }

    @Skip
    @Issue("STF-6633")
    @CaseId(505)
    @Story("Получение списка всех банковских карт")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Удаление карты по существующему ID",
            dependsOnMethods = "addNewCard")
    public void testDeleteCreditCards() {
        final Response response = CreditCardsV2Request.DELETE(creditCardId.toString());
        checkStatusCode200(response);
    }
}
