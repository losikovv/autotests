package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.CompanyDocumentV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyDocumentV2Response extends BaseResponseObject {
	@JsonProperty("company_document")
	private CompanyDocumentV2 companyDocument;
}
