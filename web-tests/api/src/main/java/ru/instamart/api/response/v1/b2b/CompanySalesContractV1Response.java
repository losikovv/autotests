package ru.instamart.api.response.v1.b2b;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.SalesContractV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanySalesContractV1Response extends BaseResponseObject {
    @JsonProperty(value = "sales_contract")
    private SalesContractV1 salesContract;
}
