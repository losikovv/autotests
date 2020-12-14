package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;

public class Adjustment extends BaseObject {

    private Integer id;
    private Object source_type;
    private Object source_id;
    private String adjustable_type;
    private Integer adjustable_id;
    private String originator_type;
    private Integer originator_id;
    private Double amount;
    private String label;
    private Boolean mandatory;
    private Boolean eligible;
    private String created_at;
    private String updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getSource_type() {
        return source_type;
    }

    public void setSource_type(Object source_type) {
        this.source_type = source_type;
    }

    public Object getSource_id() {
        return source_id;
    }

    public void setSource_id(Object source_id) {
        this.source_id = source_id;
    }

    public String getAdjustable_type() {
        return adjustable_type;
    }

    public void setAdjustable_type(String adjustable_type) {
        this.adjustable_type = adjustable_type;
    }

    public Integer getAdjustable_id() {
        return adjustable_id;
    }

    public void setAdjustable_id(Integer adjustable_id) {
        this.adjustable_id = adjustable_id;
    }

    public String getOriginator_type() {
        return originator_type;
    }

    public void setOriginator_type(String originator_type) {
        this.originator_type = originator_type;
    }

    public Integer getOriginator_id() {
        return originator_id;
    }

    public void setOriginator_id(Integer originator_id) {
        this.originator_id = originator_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean getEligible() {
        return eligible;
    }

    public void setEligible(Boolean eligible) {
        this.eligible = eligible;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}
