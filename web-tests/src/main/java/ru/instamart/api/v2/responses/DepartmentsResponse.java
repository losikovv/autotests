package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.Deals;
import instamart.api.v2.objects.Department;

import java.util.List;

public class DepartmentsResponse extends BaseResponseObject {

    private List<Department> departments = null;
    private Deals deals;

    /**
     * No args constructor for use in serialization
     *
     */
    public DepartmentsResponse() {
    }

    /**
     *
     * @param deals
     * @param departments
     */
    public DepartmentsResponse(List<Department> departments, Deals deals) {
        super();
        this.departments = departments;
        this.deals = deals;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Deals getDeals() {
        return deals;
    }

    public void setDeals(Deals deals) {
        this.deals = deals;
    }

}