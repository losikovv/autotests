package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.TicketData;

import java.util.List;

public class TicketsResponse extends BaseResponseObject {

    private List<TicketData> data = null;

    public List<TicketData> getData() {
        return data;
    }

    public void setData(List<TicketData> data) {
        this.data = data;
    }

}
