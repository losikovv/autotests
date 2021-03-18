package instamart.api.objects.v2;

import com.google.common.base.Objects;
import instamart.api.objects.BaseObject;

public final class AltIcon extends BaseObject {

    private String url;

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AltIcon altIcon = (AltIcon) o;
        return Objects.equal(url, altIcon.url);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(url);
    }

    @Override
    public String toString() {
        return "AltIcon{" +
                "url='" + url + '\'' +
                '}';
    }
}
