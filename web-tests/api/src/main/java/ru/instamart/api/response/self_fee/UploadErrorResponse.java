package ru.instamart.api.response.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.self_fee.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class UploadErrorResponse {

	@JsonProperty("b2c")
	private B2cError b2c;

	@JsonProperty("file")
	private FileError file;
}