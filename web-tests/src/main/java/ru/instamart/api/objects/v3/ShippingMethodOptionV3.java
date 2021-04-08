package ru.instamart.api.objects.v3;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ShippingMethodOptionV3 {
    private String id;
    private Integer price;
    private Integer original_price;
    private String starts_at;
    private String ends_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Integer original_price) {
        this.original_price = original_price;
    }

    public String getStarts_at() {
        return starts_at;
    }

    public void setStarts_at(String starts_at) {
        this.starts_at = starts_at;
    }

    public String getEnds_at() {
        return ends_at;
    }

    public void setEnds_at(String ends_at) {
        this.ends_at = ends_at;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("price", price).append("original_price", original_price).append("starts_at", starts_at).append("ends_at", ends_at).toString();
    }

}