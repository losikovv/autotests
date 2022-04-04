
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PeoplePreferencesV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("company_accountant")
    private String companyAccountant;

    @JsonSchema(required = true)
    @JsonProperty("company_accountant_poa")
    private String companyAccountantPoa;

    @JsonSchema(required = true)
    @JsonProperty("company_commercial_officer")
    private String companyCommercialOfficer;

    @JsonSchema(required = true)
    @JsonProperty("company_commercial_officer_poa")
    private String companyCommercialOfficerPoa;

    @JsonSchema(required = true)
    @JsonProperty("company_head")
    private String companyHead;

    @JsonSchema(required = true)
    @JsonProperty("company_head_poa")
    private String companyHeadPoa;

    @JsonSchema(required = true)
    @JsonProperty("company_logistics_head")
    private String companyLogisticsHead;

    @JsonSchema(required = true)
    @JsonProperty("company_logistics_head_poa")
    private String companyLogisticsHeadPoa;
}
