package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;

public class Deals extends BaseObject {

    private DepartmentWithAisles department;

    /**
     * No args constructor for use in serialization
     *
     */
    public Deals() {
    }

    /**
     *
     * @param department
     */
    public Deals(DepartmentWithAisles department) {
        super();
        this.department = department;
    }

    public DepartmentWithAisles getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentWithAisles department) {
        this.department = department;
    }

}
