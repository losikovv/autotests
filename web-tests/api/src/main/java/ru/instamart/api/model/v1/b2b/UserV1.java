package ru.instamart.api.model.v1.b2b;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserV1 extends BaseObject {
	private Integer id;
	private String fullname;
	private String email;
	private String phone;

}
