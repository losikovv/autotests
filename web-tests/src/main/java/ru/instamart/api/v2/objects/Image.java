package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

public class Image extends BaseObject {

    private String mini_url;
    private String small_url;
    private String product_url;
    private String preview_url;
    private String original_url;
    private String default_url;

    /**
     * No args constructor for use in serialization
     *
     */
    public Image() {
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

    public String getDefault_url() {
        return default_url;
    }

    public void setDefault_url(String default_url) {
        this.default_url = default_url;
    }
}
