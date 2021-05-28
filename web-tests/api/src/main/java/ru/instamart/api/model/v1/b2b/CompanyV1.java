package ru.instamart.api.model.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyV1 extends BaseObject {
    private Boolean prepay;
    private Boolean postpay;
    private String inn;
    private String name;
    private Boolean deposit;
    private Integer id;
    @JsonProperty(value = "sales_contract")
    private Object salesContract;
}
