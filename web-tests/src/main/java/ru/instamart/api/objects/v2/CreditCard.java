package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;

public class CreditCard extends BaseObject {

    private Integer id;
    private Object title;
    private String name;
    private String year;
    private String month;
    private String last_digits;
    private String state;
    private String cc_type;
    private Boolean eligible;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getLast_digits() {
        return last_digits;
    }

    public void setLast_digits(String last_digits) {
        this.last_digits = last_digits;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCc_type() {
        return cc_type;
    }

    public void setCc_type(String cc_type) {
        this.cc_type = cc_type;
    }

    public Boolean getEligible() {
        return eligible;
    }

    public void setEligible(Boolean eligible) {
        this.eligible = eligible;
    }

}
