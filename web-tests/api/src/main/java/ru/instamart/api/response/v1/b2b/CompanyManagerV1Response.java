package ru.instamart.api.response.v1.b2b;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.ManagerV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyManagerV1Response extends BaseResponseObject {
	private ManagerV1 manager;
}
