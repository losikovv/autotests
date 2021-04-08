package ru.instamart.api.responses.shopper;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.shopper.TicketData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class TicketsResponse extends BaseResponseObject {
    private List<TicketData> data = null;
}
