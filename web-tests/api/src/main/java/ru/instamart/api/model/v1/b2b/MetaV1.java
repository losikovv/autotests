package ru.instamart.api.model.v1.b2b;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MetaV1 extends BaseObject {
	private Integer nextPage;
	private Integer perPage;
	private Integer totalCount;
	private Integer totalPages;
	private Integer currentPage;
}
