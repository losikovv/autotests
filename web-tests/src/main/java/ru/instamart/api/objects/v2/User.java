package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;
import instamart.api.objects.v1.Role;

import java.util.List;

public class User extends BaseObject {

    private String id;
    private String email;
    private String display_email;
    private String email_md5;
    private String first_name;
    private String last_name;
    private String fullname;
    private String phone;
    private Boolean is_admin;
    private String uuid;
    private String external_uuid;
    private Boolean promo_terms_accepted;
    private String promo_terms_changed_at;
    private Boolean privacy_terms;
    private Integer shipped_and_paid_orders_count;
    private Boolean b2b;
    private Boolean has_confirmed_phone;
    private List<Role> roles = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplay_email() {
        return display_email;
    }

    public void setDisplay_email(String display_email) {
        this.display_email = display_email;
    }

    public String getEmail_md5() {
        return email_md5;
    }

    public void setEmail_md5(String email_md5) {
        this.email_md5 = email_md5;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExternal_uuid() {
        return external_uuid;
    }

    public void setExternal_uuid(String external_uuid) {
        this.external_uuid = external_uuid;
    }

    public Boolean getPromo_terms_accepted() {
        return promo_terms_accepted;
    }

    public void setPromo_terms_accepted(Boolean promo_terms_accepted) {
        this.promo_terms_accepted = promo_terms_accepted;
    }

    public String getPromo_terms_changed_at() {
        return promo_terms_changed_at;
    }

    public void setPromo_terms_changed_at(String promo_terms_changed_at) {
        this.promo_terms_changed_at = promo_terms_changed_at;
    }

    public Boolean getPrivacy_terms() {
        return privacy_terms;
    }

    public void setPrivacy_terms(Boolean privacy_terms) {
        this.privacy_terms = privacy_terms;
    }

    public Integer getShipped_and_paid_orders_count() {
        return shipped_and_paid_orders_count;
    }

    public void setShipped_and_paid_orders_count(Integer shipped_and_paid_orders_count) {
        this.shipped_and_paid_orders_count = shipped_and_paid_orders_count;
    }

    public Boolean getB2b() {
        return b2b;
    }

    public void setB2b(Boolean b2b) {
        this.b2b = b2b;
    }

    public Boolean getHas_confirmed_phone() {
        return has_confirmed_phone;
    }

    public void setHas_confirmed_phone(Boolean has_confirmed_phone) {
        this.has_confirmed_phone = has_confirmed_phone;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", display_email='" + display_email + '\'' +
                ", email_md5='" + email_md5 + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", fullname='" + fullname + '\'' +
                ", phone='" + phone + '\'' +
                ", is_admin=" + is_admin +
                ", uuid='" + uuid + '\'' +
                ", external_uuid='" + external_uuid + '\'' +
                ", promo_terms_accepted=" + promo_terms_accepted +
                ", promo_terms_changed_at='" + promo_terms_changed_at + '\'' +
                ", privacy_terms=" + privacy_terms +
                ", shipped_and_paid_orders_count=" + shipped_and_paid_orders_count +
                ", b2b=" + b2b +
                ", has_confirmed_phone=" + has_confirmed_phone +
                ", roles=" + roles +
                '}';
    }
}
