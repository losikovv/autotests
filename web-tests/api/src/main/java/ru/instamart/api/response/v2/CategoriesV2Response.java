package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.CategoryV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class CategoriesV2Response extends BaseResponseObject {
    private List<CategoryV2> categories;
}
