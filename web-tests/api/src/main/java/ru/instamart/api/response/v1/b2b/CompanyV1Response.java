package ru.instamart.api.response.v1.b2b;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyV1Response extends BaseResponseObject {
	private CompanyV1 company;
}
