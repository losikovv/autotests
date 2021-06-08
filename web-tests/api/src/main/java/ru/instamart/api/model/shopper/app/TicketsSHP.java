package ru.instamart.api.model.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class TicketsSHP extends BaseObject {
    private List<TicketSHP.Data> data = null;
}
