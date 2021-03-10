package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class MarketingSampleItemsResponse extends BaseResponseObject {

    private List<Object> marketing_sample_items = null;

    public List<Object> getMarketing_sample_items() {
        return marketing_sample_items;
    }

    public void setMarketing_sample_items(List<Object> marketing_sample_items) {
        this.marketing_sample_items = marketing_sample_items;
    }

}
