package ru.instamart.application.rest.objects;

import java.util.List;

public class Product extends BaseObject {

    private Long id;
    private String sku;
    private String retailer_sku;
    private String name;
    private Double price;
    private Double original_price;
    private Double discount;
    private String human_volume;
    private Integer items_per_pack;
    private String description;
    private List<Image> images = null;
    private List<Property> properties = null;
    private List<Object> related_products = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getRetailer_sku() {
        return retailer_sku;
    }

    public void setRetailer_sku(String retailer_sku) {
        this.retailer_sku = retailer_sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Double original_price) {
        this.original_price = original_price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getHuman_volume() {
        return human_volume;
    }

    public void setHuman_volume(String human_volume) {
        this.human_volume = human_volume;
    }

    public Integer getItems_per_pack() {
        return items_per_pack;
    }

    public void setItems_per_pack(Integer items_per_pack) {
        this.items_per_pack = items_per_pack;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Object> getRelated_products() {
        return related_products;
    }

    public void setRelated_products(List<Object> related_products) {
        this.related_products = related_products;
    }

}
