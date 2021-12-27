package ru.instamart.api.response.shopper.admin;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.ScangoEnginesItem;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper=false)
public class ScangoEnginesResponse extends BaseResponseObject {

	@NotEmpty
	@JsonProperty("scangoEngines")
	@JsonSchema(required = true)
	private List<ScangoEnginesItem> scangoEngines;
}