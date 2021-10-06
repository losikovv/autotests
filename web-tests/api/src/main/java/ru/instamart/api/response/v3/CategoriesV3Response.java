package ru.instamart.api.response.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.CategoryV3;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CategoriesV3Response extends BaseResponseObject {
    private List<CategoryV3> categories;
}
