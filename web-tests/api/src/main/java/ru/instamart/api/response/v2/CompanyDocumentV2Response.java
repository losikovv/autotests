package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.v2.CompanyDocumentPaymentToolsV2;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyDocumentV2Response extends BaseResponseObject {
	@JsonProperty("company_document")
	private CompanyDocumentPaymentToolsV2 companyDocument;
}