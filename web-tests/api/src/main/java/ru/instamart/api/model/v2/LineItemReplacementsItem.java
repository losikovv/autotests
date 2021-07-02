package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LineItemReplacementsItem{
	@JsonProperty("from_item")
	private FromItemV2 fromItem;
	@JsonProperty("to_item")
	private ToItemV2 toItem;
}