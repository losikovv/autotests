package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import instamart.api.objects.v2.Category;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public final class CategoriesResponse extends BaseResponseObject {

    @JsonProperty(value = "categories")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriesResponse that = (CategoriesResponse) o;
        return Objects.equal(categories, that.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categories);
    }

    @Override
    public String toString() {
        return "CategoriesResponse{" +
                "categories=" + categories +
                '}';
    }
}
