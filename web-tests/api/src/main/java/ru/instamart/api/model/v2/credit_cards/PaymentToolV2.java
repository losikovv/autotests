package ru.instamart.api.model.v2.credit_cards;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentToolV2 extends BaseObject {
	private String name;
	private Integer id;
	private String type;
}