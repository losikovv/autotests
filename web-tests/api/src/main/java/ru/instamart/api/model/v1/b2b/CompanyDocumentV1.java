package ru.instamart.api.model.v1.b2b;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyDocumentV1 extends BaseObject {
	private String bank;
	private String address;
	private String bik;
	@JsonProperty(value = "operating_account")
	private String operatingAccount;
	@JsonProperty(value = "correspondent_account")
	private String correspondentAccount;
	private String name;
	private String inn;
	private String kpp;
	private Integer id;
	@JsonProperty(value = "user_id")
	private Integer userId;
	private Boolean approved;

}
