package instamart.api.responses.v1;

import instamart.api.objects.v1.LineItem;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class LineItemsResponse extends BaseResponseObject {

    private List<LineItem> line_items = null;

    public List<LineItem> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<LineItem> line_items) {
        this.line_items = line_items;
    }

}
