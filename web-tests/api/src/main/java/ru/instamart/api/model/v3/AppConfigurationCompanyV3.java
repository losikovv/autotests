package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppConfigurationCompanyV3 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("company_phone")
    private String companyPhone;

    @JsonSchema(required = true)
    @JsonProperty("company_name")
    private String companyName;

    @JsonSchema(required = true)
    @JsonProperty("company_address")
    private String companyAddress;

    @JsonSchema(required = true)
    @JsonProperty("company_inn")
    private String companyInn;

    @JsonSchema(required = true)
    @JsonProperty("company_kpp")
    private String companyKpp;

    @JsonSchema(required = true)
    @JsonProperty("company_ogrn")
    private String companyOgrn;

    @JsonSchema(required = true)
    @JsonProperty("company_settlement_acc")
    private String companySettlementAcc;

    @JsonSchema(required = true)
    @JsonProperty("company_bank")
    private String companyBank;

    @JsonSchema(required = true)
    @JsonProperty("company_bik")
    private String companyBik;

    @JsonSchema(required = true)
    @JsonProperty("company_correspondent_acc")
    private String companyCorrespondentAcc;
}
