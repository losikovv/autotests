package ru.instamart.api.model.self_fee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Other{

	@JsonProperty("partner_count")
	private int partnerCount;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("total_sum")
	private String totalSum;

	@JsonProperty("receipt_count")
	private int receiptCount;
}