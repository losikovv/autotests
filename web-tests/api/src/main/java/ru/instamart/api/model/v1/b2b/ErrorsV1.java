package ru.instamart.api.model.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorsV1 extends BaseObject {
	private List<String> inn;
	@JsonProperty(value = "external_payment_account")
	private List<String> externalPaymentAccount;
}