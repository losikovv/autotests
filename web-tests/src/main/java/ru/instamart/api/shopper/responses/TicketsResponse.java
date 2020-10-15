package instamart.api.shopper.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.TicketData;

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
