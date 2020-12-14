package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;

import java.util.List;

public class MasterCategory extends BaseObject {

    private Integer id;
    private String name;
    private Object special_condition;
    private List<Object> master_category_attributes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSpecial_condition() {
        return special_condition;
    }

    public void setSpecial_condition(Object special_condition) {
        this.special_condition = special_condition;
    }

    public List<Object> getMaster_category_attributes() {
        return master_category_attributes;
    }

    public void setMaster_category_attributes(List<Object> master_category_attributes) {
        this.master_category_attributes = master_category_attributes;
    }

}
