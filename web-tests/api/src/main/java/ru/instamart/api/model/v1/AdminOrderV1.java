
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v1.b2b.CompanyDocumentV1;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminOrderV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("company_document")
    private CompanyDocumentV1 companyDocument;

    @Null
    @JsonSchema(required = true)
    private String email;

    @JsonSchema(required = true)
    private Long id;

    @Null
    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    @JsonProperty("order_promotions")
    private List<Object> orderPromotions;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("payment_state")
    private String paymentState;

    @JsonSchema(required = true)
    @JsonProperty("payment_state_name")
    private String paymentStateName;

    @JsonSchema(required = true)
    private String platform;

    @JsonSchema(required = true)
    private Boolean promo;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("ship_address")
    private AdminShipAddressV1 shipAddress;
}
