package instamart.api.objects;

public class Icon extends BaseObject {

    private String mini_url;
    private String normal_url;
    private String small_url;
    private String product_url;
    private String preview_url;
    private String original_url;
    private String url;

    public String getMini_url() {
        return mini_url;
    }

    public void setMini_url(String mini_url) {
        this.mini_url = mini_url;
    }

    public String getNormal_url() {
        return normal_url;
    }

    public void setNormal_url(String normal_url) {
        this.normal_url = normal_url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}