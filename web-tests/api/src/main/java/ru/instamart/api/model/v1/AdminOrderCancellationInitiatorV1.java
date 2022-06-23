package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminOrderCancellationInitiatorV1 extends BaseObject {

	@JsonSchema(required = true)
	private int id;

	@Null
    private String firstname;

	@Null
	private String lastname;

	@JsonProperty("contact_email")
	@JsonSchema(required = true)
	private String contactEmail;

	@JsonSchema(required = true)
    private List<AdminOrderCancellationInitiatorRolesV1> roles;
}