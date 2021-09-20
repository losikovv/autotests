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

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Получение категорий")
public class CreditCardsV2Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @Issue("STF-6633")
    @CaseId(500)
    @Story("Добавить новую карту")
    @Test(  enabled = false,
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
    @Test(  enabled = false,
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
    @Test(  enabled = false,
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
        final Response responseCard2 = CreditCardsV2Request.POST(creditCard);
        checkStatusCode200(response);
        //TODO Добавить проверки после фикса задачи
    }

}
