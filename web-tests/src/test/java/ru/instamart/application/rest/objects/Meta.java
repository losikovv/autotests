package ru.instamart.application.rest.objects;

public class Meta extends BaseObject {

    private Object per_page;

    /**
     * No args constructor for use in serialization
     *
     */
    public Meta() {
    }

    /**
     *
     * @param per_page
     */
    public Meta(Object per_page) {
        super();
        this.per_page = per_page;
    }

    public Object getPer_page() {
        return per_page;
    }

    public void setPer_page(Object per_page) {
        this.per_page = per_page;
    }

}
