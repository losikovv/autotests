package ru.instamart.application.rest.objects;

public class Meta extends BaseObject {

    private Integer current_page;
    private Object next_page;
    private Integer total_pages;
    private Integer per_page;
    private Integer total_count;

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Object getNext_page() {
        return next_page;
    }

    public void setNext_page(Object next_page) {
        this.next_page = next_page;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

}
