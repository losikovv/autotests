package instamart.api.objects.v2;

import com.google.common.base.Objects;
import instamart.api.objects.BaseObject;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Icon icon = (Icon) o;
        return Objects.equal(mini_url, icon.mini_url) && Objects.equal(normal_url, icon.normal_url) && Objects.equal(small_url, icon.small_url) && Objects.equal(product_url, icon.product_url) && Objects.equal(preview_url, icon.preview_url) && Objects.equal(original_url, icon.original_url) && Objects.equal(url, icon.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mini_url, normal_url, small_url, product_url, preview_url, original_url, url);
    }

    @Override
    public String toString() {
        return "Icon{" +
                "mini_url='" + mini_url + '\'' +
                ", normal_url='" + normal_url + '\'' +
                ", small_url='" + small_url + '\'' +
                ", product_url='" + product_url + '\'' +
                ", preview_url='" + preview_url + '\'' +
                ", original_url='" + original_url + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}