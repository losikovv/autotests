package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SourceV2 extends BaseObject {
    private String type;
    @JsonProperty(value = "company_document")
    private CompanyDocumentV2 companyDocument;
    @JsonProperty(value = "credit_card")
    private CreditCardV2 creditCard;
}
