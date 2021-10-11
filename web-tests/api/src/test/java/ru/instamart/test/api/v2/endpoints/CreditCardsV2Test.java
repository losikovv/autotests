package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.CreditCardV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.CreditCardsV2Request;
import ru.instamart.api.request.v2.CreditCardsV2Request.CreditCard;
import ru.instamart.api.response.v2.CreditCardV2Response;
import ru.instamart.api.response.v2.CreditCardsV2Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Получение категорий")
public class CreditCardsV2Test extends RestBase {

    private Integer creditCardId;

    @BeforeMethod(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @Issue("STF-6633")
    @CaseId(500)
    @Story("Добавить новую карту")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Добавить новую карту с указанием обязательных параметров")
    public void addANewCardWithRequiredParameters() {
        String card = CreditCardV2.CARD1.getNumber();
        String lastDigits = card.substring(card.lastIndexOf(" ") + 1);
        final CreditCard creditCard = CreditCard.builder()
                .name("TESTOV TEST")
                .year(CreditCardV2.CARD1.getYear())
                .month(CreditCardV2.CARD1.getMonth())
                .last_digits(lastDigits)
                .cryptogram_packet(CreditCardV2.CARD1.getCryptogramPacket())
                .build();
        final Response response = CreditCardsV2Request.POST(creditCard);
        checkStatusCode200(response);
        CreditCardV2Response creditCards = response.as(CreditCardV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(creditCards.getCreditCard().getName(), "TESTOV TEST", "Неправильные данные держателя карты");
        softAssert.assertEquals(creditCards.getCreditCard().getYear(), CreditCardV2.CARD1.getYear(), "Год не совпадает");
        softAssert.assertEquals(creditCards.getCreditCard().getMonth(), CreditCardV2.CARD1.getMonth(), "Месяц не совпадает");
        softAssert.assertEquals(creditCards.getCreditCard().getLastDigits(), lastDigits, "Последние цифры карты не совпадают");
        softAssert.assertAll();
    }

    @Issue("STF-6633")
    @CaseId(501)
    @Story("Добавить новую карту")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Добавить новую карту с дополнительным полем title")
    public void AddANewCardWithAnAdditionalTitleField() {
        String card = CreditCardV2.CARD1.getNumber();
        String lastDigits = card.substring(card.lastIndexOf(" ") + 1);
        final CreditCard creditCard = CreditCard.builder()
                .title("Новая карта")
                .name("TESTOV TEST")
                .year(CreditCardV2.CARD1.getYear())
                .month(CreditCardV2.CARD1.getMonth())
                .last_digits(lastDigits)
                .cryptogram_packet(CreditCardV2.CARD1.getCryptogramPacket())
                .build();
        final Response response = CreditCardsV2Request.POST(creditCard);
        checkStatusCode200(response);
        CreditCardV2Response creditCards = response.as(CreditCardV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(creditCards.getCreditCard().getTitle(), "Новая карта", "Имя карты не совпадает");
        softAssert.assertEquals(creditCards.getCreditCard().getName(), "TESTOV TEST", "Неправильные данные держателя карты");
        softAssert.assertEquals(creditCards.getCreditCard().getYear(), CreditCardV2.CARD1.getYear(), "Год не совпадает");
        softAssert.assertEquals(creditCards.getCreditCard().getMonth(), CreditCardV2.CARD1.getMonth(), "Месяц не совпадает");
        softAssert.assertEquals(creditCards.getCreditCard().getLastDigits(), lastDigits, "Последние цифры карты не совпадают");
        softAssert.assertAll();
    }

    @Issue("STF-6633")
    @CaseId(502)
    @Story("Добавить новую карту")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Добавить новую карту с дополнительным полем title")
    public void addNewCard() {
        String card = CreditCardV2.CARD1.getNumber();
        String lastDigits = card.substring(card.lastIndexOf(" ") + 1);
        final CreditCard creditCard = CreditCard.builder()
                .title("Новая карта")
                .name("TESTOV TEST")
                .year(CreditCardV2.CARD1.getYear())
                .month(CreditCardV2.CARD1.getMonth())
                .last_digits(lastDigits)
                .cryptogram_packet(CreditCardV2.CARD1.getCryptogramPacket())
                .build();
        final Response response = CreditCardsV2Request.POST(creditCard);
        checkStatusCode200(response);
        CreditCardV2Response creditCardV2 = response.as(CreditCardV2Response.class);
        creditCardId = creditCardV2.getCreditCard().getId();
        //TODO Добавить проверки после фикса задачи
    }

    @CaseId(495)
    @Story("Получение списка всех банковских карт")
    @Test(groups = {"api-instamart-regress"},
            description = "У пользователя нет добавленных карт")
    public void testNoCreditCards() {
        final Response response = CreditCardsV2Request.GET();
        checkStatusCode200(response);
        final CreditCardsV2Response creditCardsV2Response = response.as(CreditCardsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(creditCardsV2Response.getCreditCards().isEmpty(), "credit_cards вернулся не пустым");
    }

    @Issue("STF-6633")
    @CaseId(496)
    @Story("Получение списка всех банковских карт")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "У пользователя одна добавленная карта",
            dependsOnMethods = "addANewCardWithRequiredParameters")
    public void testOneCreditCards() {
        final Response response = CreditCardsV2Request.GET();
        checkStatusCode200(response);
        final CreditCardsV2Response creditCardsV2Response = response.as(CreditCardsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(creditCardsV2Response.getCreditCards().size(), 1, "credit_cards вернулся пустым или несколько");
    }

    @Issue("STF-6633")
    @CaseId(497)
    @Story("Получение списка всех банковских карт")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "У пользователя несколько добавленных карт",
            dependsOnMethods = "addNewCard")
    public void testSomeCreditCards() {
        Response response = CreditCardsV2Request.GET();
        checkStatusCode200(response);
        final CreditCardsV2Response creditCardsV2Response = response.as(CreditCardsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(creditCardsV2Response.getCreditCards().size() > 1, "credit_cards вернулся пустым или один");
    }

    @CaseId(506)
    @Story("Получение списка всех банковских карт")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление карты по не существующим ID"
    )
    public void failedTestDeleteCreditCards() {
        Response response = CreditCardsV2Request.DELETE("failedId");
        checkStatusCode404(response);
        errorAssert(response, "Кредитная карта не существует");
    }

    @Issue("STF-6633")
    @CaseId(505)
    @Story("Получение списка всех банковских карт")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            description = "Удаление карты по существующему ID",
            dependsOnMethods = "addNewCard")
    public void testDeleteCreditCards() {
        Response response = CreditCardsV2Request.DELETE(creditCardId.toString());
        checkStatusCode200(response);
    }

}
