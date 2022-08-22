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
import ru.instamart.kraken.common.Mapper;

public class CompanyDocumentsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.COMPANY_DOCUMENTS)
    public static Response POST(CompanyDocument companyDocument) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(companyDocument))
                .post(ApiV2EndPoints.COMPANY_DOCUMENTS);
    }

    /**
     * company_document[name]	Да	Юридическое лицо
     * company_document[inn]	Да	ИНН
     * company_document[kpp]	Да	КПП
     * company_document[bik]	Да	БИК
     * company_document[correspondent_account]	Да	Корреспондентский счет
     * company_document[operating_account]	Да	Расчетный счет
     * company_document[address]	-	Юридическое адресс
     * company_document[bank]	-	Банк
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class CompanyDocument {
        @JsonProperty("company_document[name]")
        private final String name;
        @JsonProperty("company_document[inn]")
        private final String inn;
        @JsonProperty("company_document[kpp]")
        private final String kpp;
        @JsonProperty("company_document[bik]")
        private final String bik;
        @JsonProperty("company_document[correspondent_account]")
        private final String correspondent_account;
        @JsonProperty("company_document[operating_account]")
        private final String operating_account;
        @JsonProperty("company_document[address]")
        private final String address;
        @JsonProperty("company_document[bank]")
        private final String bank;
    }
}
