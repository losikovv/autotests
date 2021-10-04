package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class MetaSHP extends BaseObject {
    private Object previousPage;
    private Integer perPage;
    private Object nextPage;
    private Integer totalPages;
    private Integer currentPage;
    private Integer totalCount;
}