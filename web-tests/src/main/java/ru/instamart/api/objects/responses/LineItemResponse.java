package instamart.api.objects.responses;

import instamart.api.objects.LineItem;

public class LineItemResponse extends BaseResponseObject {

    private LineItem line_item;

    /**
     * No args constructor for use in serialization
     *
     */
    public LineItemResponse() {
    }

    /**
     *
     * @param line_item
     */
    public LineItemResponse(LineItem line_item) {
        super();
        this.line_item = line_item;
    }

    public LineItem getLine_item() {
        return line_item;
    }

    public void setLine_item(LineItem line_item) {
        this.line_item = line_item;
    }

}