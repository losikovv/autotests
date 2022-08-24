package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorsV2 extends BaseObject {
    private String email;
    private String password;
    private String fullname;
    private String shipments;
    private String base;
    private String payments;
    private String value;
    @JsonProperty("company_security_code")
    private String companySecurityCode;
    private String name;
    @JsonProperty("shipment_id")
    private String shipmentId;
}
