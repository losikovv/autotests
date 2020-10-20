package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.LineItem;
import instamart.api.objects.v2.Meta;

import java.util.List;

public class LineItemsResponse extends BaseResponseObject {

    private List<LineItem> line_items = null;
    private Meta meta;

    public List<LineItem> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<LineItem> line_items) {
        this.line_items = line_items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
