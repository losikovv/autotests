package ru.instamart.application.rest.objects;

import java.util.List;

public class Product extends BaseObject {

    private Integer id;
    private String sku;
    private String retailer_sku;
    private String name;
    private Double price;
    private Double original_price;
    private Double discount;
    private String human_volume;
    private Integer items_per_pack;
    private List<Image> images = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param original_price
     * @param images
     * @param price
     * @param name
     * @param discount
     * @param retailer_sku
     * @param items_per_pack
     * @param id
     * @param sku
     * @param human_volume
     */
    public Product(Integer id, String sku, String retailer_sku, String name, Double price, Double original_price, Double discount, String human_volume, Integer items_per_pack, List<Image> images) {
        super();
        this.id = id;
        this.sku = sku;
        this.retailer_sku = retailer_sku;
        this.name = name;
        this.price = price;
        this.original_price = original_price;
        this.discount = discount;
        this.human_volume = human_volume;
        this.items_per_pack = items_per_pack;
        this.images = images;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
