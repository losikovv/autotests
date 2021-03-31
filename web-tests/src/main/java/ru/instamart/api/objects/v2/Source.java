package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Source extends BaseObject {
    private String type;
    @JsonProperty(value = "company_document")
    private CompanyDocument companyDocument;
    @JsonProperty(value = "credit_card")
    private CreditCard creditCard;
}
