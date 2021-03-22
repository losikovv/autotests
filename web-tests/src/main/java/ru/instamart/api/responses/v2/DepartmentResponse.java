package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import instamart.api.objects.BaseObject;
import instamart.api.objects.v2.Aisles;

import java.util.List;

/**
 * "department":{
 *       "id":16116,
 *       "type":"department",
 *       "name":"Постное меню",
 *       "products_count":63,
 *       "aisles": []
 */
public final class DepartmentResponse extends BaseObject {

    private Department department;

    public Department getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentResponse that = (DepartmentResponse) o;
        return Objects.equal(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(department);
    }

    @Override
    public String toString() {
        return "DepartmentResponse{" +
                "department=" + department +
                '}';
    }

    public static final class Department {
        private int id;
        private String type;
        private String name;
        @JsonProperty(value = "products_count")
        private int productCount;
        private List<Aisles> aisles;

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public int getProductCount() {
            return productCount;
        }

        public List<Aisles> getAisles() {
            return aisles;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Department that = (Department) o;
            return id == that.id && productCount == that.productCount && Objects.equal(type, that.type) && Objects.equal(name, that.name) && Objects.equal(aisles, that.aisles);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id, type, name, productCount, aisles);
        }

        @Override
        public String toString() {
            return "DepartmentResponse{" +
                    "id=" + id +
                    ", type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", productCount=" + productCount +
                    ", aisles=" + aisles +
                    '}';
        }
    }
}
