package ru.instamart.api.response.v1.b2b;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.CompanyByIDV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyByIDV1Response extends BaseResponseObject {
	private CompanyByIDV1 company;
}
