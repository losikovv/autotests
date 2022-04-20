package ru.instamart.api.model.shifts;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PolySHP extends BaseObject {

	@JsonProperty("coordinates")
	private List<List<List<List<Double>>>> coordinates;

	@JsonProperty("type")
	private String type;
}