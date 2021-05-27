package ru.instamart.api.response.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.LegalEntityV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class LegalEntityV1Response extends BaseResponseObject {
	@JsonProperty(value = "legal_entity")
	private LegalEntityV1 legalEntity;
}
