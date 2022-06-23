package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminOrderCancellationsV1 extends BaseObject {

	@JsonSchema(required = true)
	private AdminOrderCancellationReasonV1 reason;

	@JsonSchema(required = true)
	private AdminOrderCancellationInitiatorV1 initiator;

	@JsonSchema(required = true)
	private int id;

	@Null
	@JsonProperty("reason_details")
	private String reasonDetails;

	@JsonProperty("created_at")
	@JsonSchema(required = true)
	private String createdAt;
}