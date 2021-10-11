package ru.instamart.api.request.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.utils.Mapper;

public class CreditCardsV2Request extends ApiV2RequestBase {
    @Step("{method} /" + ApiV2EndPoints.CREDIT_CARDS)
    public static Response POST(CreditCard creditCard) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(creditCard))
                .post(ApiV2EndPoints.CREDIT_CARDS);
    }

    @Step("{method} /" + ApiV2EndPoints.CREDIT_CARDS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.CREDIT_CARDS);
    }

    @Step("{method} /" + ApiV2EndPoints.CreditCards.BY_ID)
    public static Response DELETE(String creditCardId) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.CreditCards.BY_ID, creditCardId);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class CreditCard {
        @JsonProperty("credit_card[title]")
        private final String title;
        @JsonProperty("credit_card[name]")
        private final String name;
        @JsonProperty("credit_card[year]")
        private final String year;
        @JsonProperty("credit_card[month]")
        private final String month;
        @JsonProperty("credit_card[last_digits]")
        private final String last_digits;
        @JsonProperty("credit_card[cryptogram_packet]")
        private final String cryptogram_packet;
    }
}
