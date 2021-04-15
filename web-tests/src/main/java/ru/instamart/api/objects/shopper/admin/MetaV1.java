package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MetaV1 extends BaseObject {
    private Integer currentPage;
    private Integer nextPage;
    private Integer previousPage;
    private Integer totalPages;
    private Integer perPage;
    private Integer totalCount;
}
