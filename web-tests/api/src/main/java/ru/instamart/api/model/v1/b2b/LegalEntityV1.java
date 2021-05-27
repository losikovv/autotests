package ru.instamart.api.model.v1.b2b;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class LegalEntityV1 extends BaseObject {
	private String address;
	private String name;
	private String kpp;
}
