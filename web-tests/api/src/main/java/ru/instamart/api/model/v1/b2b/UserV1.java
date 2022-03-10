package ru.instamart.api.model.v1.b2b;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserV1 extends BaseObject {
	@JsonSchema(required = true)
	private Integer id;
	@JsonSchema(required = true)
	private String fullname;
	@JsonSchema(required = true)
	private String email;
	private String phone;

}
