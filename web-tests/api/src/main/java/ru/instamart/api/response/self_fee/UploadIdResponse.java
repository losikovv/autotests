package ru.instamart.api.response.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.self_fee.B2c;
import ru.instamart.api.model.self_fee.File;
import ru.instamart.api.model.self_fee.Other;
import ru.instamart.api.model.self_fee.Sber;

@Data
@EqualsAndHashCode(callSuper = false)
public class UploadIdResponse{

	@JsonProperty("b2c")
	private B2c b2c;

	@JsonProperty("other")
	private Other other;

	@JsonProperty("file")
	private File file;

	@JsonProperty("sber")
	private Sber sber;
}