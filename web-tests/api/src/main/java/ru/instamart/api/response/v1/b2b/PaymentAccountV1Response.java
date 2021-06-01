package ru.instamart.api.response.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.b2b.PaymentAccountV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentAccountV1Response extends BaseResponseObject {
	@JsonProperty(value = "payment_account")
	private PaymentAccountV1 paymentAccount;
}
