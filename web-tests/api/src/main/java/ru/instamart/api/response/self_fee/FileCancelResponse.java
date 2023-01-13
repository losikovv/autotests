package ru.instamart.api.response.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class FileCancelResponse{

	@JsonProperty("filename")
	private String filename;

	@JsonProperty("id")
	private String id;
}