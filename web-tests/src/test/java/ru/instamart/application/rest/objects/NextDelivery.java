package ru.instamart.application.rest.objects;

public class NextDelivery extends BaseObject {

    private Integer id;
    private Double price;
    private String summary;
    private String starts_at;

    /**
     * No args constructor for use in serialization
     *
     */
    public NextDelivery() {
    }

    /**
     *
     * @param summary
     * @param price
     * @param starts_at
     */
    public NextDelivery(Double price, String summary, String starts_at) {
        super();
        this.price = price;
        this.summary = summary;
        this.starts_at = starts_at;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getStarts_at() { return starts_at; }

    public void setStarts_at(String starts_at) { this.starts_at = starts_at; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
