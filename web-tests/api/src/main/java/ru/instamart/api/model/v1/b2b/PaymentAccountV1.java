package ru.instamart.api.model.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentAccountV1 extends BaseObject {
	private Double balance;
	@JsonProperty(value = "refreshed_at")
	private String refreshedAt;
	private Integer id;
	private ErrorsV1 errors;
}
