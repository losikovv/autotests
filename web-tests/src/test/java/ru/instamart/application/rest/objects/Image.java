package ru.instamart.application.rest.objects;

public class Image extends BaseObject {

    private String mini_url;
    private String small_url;
    private String product_url;
    private String preview_url;
    private String original_url;

    /**
     * No args constructor for use in serialization
     *
     */
    public Image() {
    }

    /**
     *
     * @param small_url
     * @param product_url
     * @param original_url
     * @param preview_url
     * @param mini_url
     */
    public Image(String mini_url, String small_url, String product_url, String preview_url, String original_url) {
        super();
        this.mini_url = mini_url;
        this.small_url = small_url;
        this.product_url = product_url;
        this.preview_url = preview_url;
        this.original_url = original_url;
    }

    public String getMini_url() {
        return mini_url;
    }

    public void setMini_url(String mini_url) {
        this.mini_url = mini_url;
    }

    public String getSmall_url() {
        return small_url;
    }

    public void setSmall_url(String small_url) {
        this.small_url = small_url;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

}
