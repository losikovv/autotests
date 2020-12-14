package instamart.api.objects.v1;

import instamart.api.objects.BaseObject;

public class ShoppersBackend extends BaseObject {

    private String base_url;
    private String client_jwt;
    private String client_id;

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getClient_jwt() {
        return client_jwt;
    }

    public void setClient_jwt(String client_jwt) {
        this.client_jwt = client_jwt;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
