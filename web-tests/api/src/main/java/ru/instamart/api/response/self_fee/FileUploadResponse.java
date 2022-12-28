package ru.instamart.api.response.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileUploadResponse{

	@JsonProperty("id")
	private Integer id;
}