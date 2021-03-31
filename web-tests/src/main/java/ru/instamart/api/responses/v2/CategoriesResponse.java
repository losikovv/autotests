package instamart.api.responses.v2;

import instamart.api.objects.v2.Category;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class CategoriesResponse extends BaseResponseObject {
    private List<Category> categories;
}
