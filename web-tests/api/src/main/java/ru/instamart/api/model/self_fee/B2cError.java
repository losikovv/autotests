package ru.instamart.api.model.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class B2cError extends BaseObject {

	@JsonProperty("filename")
	private String filename;

	@JsonProperty("id")
	private String id;

	@JsonProperty("error")
	private String error;
}