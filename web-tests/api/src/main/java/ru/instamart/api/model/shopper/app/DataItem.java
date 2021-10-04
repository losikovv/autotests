package ru.instamart.api.model.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataItem  extends BaseObject {
	private Relationships relationships;
	private Attributes attributes;
	private String id;
	private String type;
}