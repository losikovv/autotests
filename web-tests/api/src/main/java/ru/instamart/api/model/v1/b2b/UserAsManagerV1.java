package ru.instamart.api.model.v1.b2b;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserAsManagerV1 {
	private int id;
	private String fullname;
	private String email;

}
