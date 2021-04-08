package ru.instamart.api.responses.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.CategoryV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class CategoriesV2Response extends BaseResponseObject {
    private List<CategoryV2> categories;
}
