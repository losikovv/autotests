package ru.instamart.api.response.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.self_fee.File;
import ru.instamart.api.model.self_fee.Summary;

@Data
@EqualsAndHashCode(callSuper = false)
public class RegistryV3Response{

	@JsonProperty("summary")
	private Summary summary;

	@JsonProperty("partner_count")
	private Integer partnerCount;

	@JsonProperty("file")
	private File file;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("total_sum")
	private String totalSum;

	@JsonProperty("receipt_count")
	private Integer receiptCount;

	@JsonProperty("status")
	private String status;
}