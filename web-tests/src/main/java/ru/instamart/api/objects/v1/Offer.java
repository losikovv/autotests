package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;
import instamart.api.objects.v2.Store;

public class Offer extends BaseObject {

    private Boolean active;
    private Long id;
    private String uuid;
    private String name;
    private Double price;
    private Double discount;
    private Boolean discounted;
    private Double instamart_price;
    private Integer items_per_pack;
    private Long product_id;
    private Integer retailer_id;
    private Integer store_id;
    private String retailer_sku;
    private String rsku;
    private Integer stock_rate;
    private String stock_rate_description;
    private Integer max_stock_rate;
    private Object discount_ends_at;
    private Boolean published;
    private String product_name;
    private Integer pickup_order;
    private String product_sku;
    private Double retailer_price;
    private Double cost_price;
    private String price_type;
    private Double offer_price;
    private Double unit_price;
    private Integer grams_per_unit;
    private String shelf_life;
    private Integer stock;
    private Integer max_stock;
    private Integer vat_rate;
    private String updated_at;
    private String permalink;
    private Store store;
    private Variant variant;
    private MasterCategory master_category;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Boolean getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Boolean discounted) {
        this.discounted = discounted;
    }

    public Double getInstamart_price() {
        return instamart_price;
    }

    public void setInstamart_price(Double instamart_price) {
        this.instamart_price = instamart_price;
    }

    public Integer getItems_per_pack() {
        return items_per_pack;
    }

    public void setItems_per_pack(Integer items_per_pack) {
        this.items_per_pack = items_per_pack;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Integer getRetailer_id() {
        return retailer_id;
    }

    public void setRetailer_id(Integer retailer_id) {
        this.retailer_id = retailer_id;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getRetailer_sku() {
        return retailer_sku;
    }

    public void setRetailer_sku(String retailer_sku) {
        this.retailer_sku = retailer_sku;
    }

    public String getRsku() {
        return rsku;
    }

    public void setRsku(String rsku) {
        this.rsku = rsku;
    }

    public Integer getStock_rate() {
        return stock_rate;
    }

    public void setStock_rate(Integer stock_rate) {
        this.stock_rate = stock_rate;
    }

    public String getStock_rate_description() {
        return stock_rate_description;
    }

    public void setStock_rate_description(String stock_rate_description) {
        this.stock_rate_description = stock_rate_description;
    }

    public Integer getMax_stock_rate() {
        return max_stock_rate;
    }

    public void setMax_stock_rate(Integer max_stock_rate) {
        this.max_stock_rate = max_stock_rate;
    }

    public Object getDiscount_ends_at() {
        return discount_ends_at;
    }

    public void setDiscount_ends_at(Object discount_ends_at) {
        this.discount_ends_at = discount_ends_at;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getPickup_order() {
        return pickup_order;
    }

    public void setPickup_order(Integer pickup_order) {
        this.pickup_order = pickup_order;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(String product_sku) {
        this.product_sku = product_sku;
    }

    public Double getRetailer_price() {
        return retailer_price;
    }

    public void setRetailer_price(Double retailer_price) {
        this.retailer_price = retailer_price;
    }

    public Double getCost_price() {
        return cost_price;
    }

    public void setCost_price(Double cost_price) {
        this.cost_price = cost_price;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public Double getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(Double offer_price) {
        this.offer_price = offer_price;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Integer getGrams_per_unit() {
        return grams_per_unit;
    }

    public void setGrams_per_unit(Integer grams_per_unit) {
        this.grams_per_unit = grams_per_unit;
    }

    public String getShelf_life() {
        return shelf_life;
    }

    public void setShelf_life(String shelf_life) {
        this.shelf_life = shelf_life;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getMax_stock() {
        return max_stock;
    }

    public void setMax_stock(Integer max_stock) {
        this.max_stock = max_stock;
    }

    public Integer getVat_rate() {
        return vat_rate;
    }

    public void setVat_rate(Integer vat_rate) {
        this.vat_rate = vat_rate;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public MasterCategory getMaster_category() {
        return master_category;
    }

    public void setMaster_category(MasterCategory master_category) {
        this.master_category = master_category;
    }

    public String toString() {
        return store.getName() + ", " + name;
    }

}
