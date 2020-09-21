package instamart.api.objects.responses;

import instamart.api.objects.Deals;
import instamart.api.objects.Department;

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