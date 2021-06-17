package ru.instamart.api.model.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class SalesContractV1 extends BaseObject {
	private Integer number;
	private Boolean active;
	private Integer id;
	@JsonProperty(value = "signing_date")
	private String signingDate;
}
