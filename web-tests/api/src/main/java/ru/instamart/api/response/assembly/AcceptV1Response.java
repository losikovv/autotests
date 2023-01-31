package ru.instamart.api.response.assembly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class AcceptV1Response{

	@JsonProperty("assembly_id")
	private String assemblyId;
}