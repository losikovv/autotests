package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class OperationShift extends BaseObject {

    private Integer id;
    private Integer shopperId;
    private Integer roleId;
    private String planStartsAt;
    private String factStartsAt;
    private String planEndsAt;
    private Object factEndsAt;
    private String state;
    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopperId() {
        return shopperId;
    }

    public void setShopperId(Integer shopperId) {
        this.shopperId = shopperId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPlanStartsAt() {
        return planStartsAt;
    }

    public void setPlanStartsAt(String planStartsAt) {
        this.planStartsAt = planStartsAt;
    }

    public String getFactStartsAt() {
        return factStartsAt;
    }

    public void setFactStartsAt(String factStartsAt) {
        this.factStartsAt = factStartsAt;
    }

    public String getPlanEndsAt() {
        return planEndsAt;
    }

    public void setPlanEndsAt(String planEndsAt) {
        this.planEndsAt = planEndsAt;
    }

    public Object getFactEndsAt() {
        return factEndsAt;
    }

    public void setFactEndsAt(Object factEndsAt) {
        this.factEndsAt = factEndsAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
