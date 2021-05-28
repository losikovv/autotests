package ru.instamart.api.model.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyByIDV1 {
	private boolean prepay;
	private boolean postpay;
	private String inn;
	private String name;
	private boolean deposit;
	private int id;
	@JsonProperty(value = "sales_contract")
	private Object salesContract;
}
