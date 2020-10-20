package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Cancellation;

public class CancellationsResponse extends BaseResponseObject {

    private Cancellation cancellation;

    /**
     * No args constructor for use in serialization
     *
     */
    public CancellationsResponse() {
    }

    /**
     *
     * @param cancellation
     */
    public CancellationsResponse(Cancellation cancellation) {
        super();
        this.cancellation = cancellation;
    }

    public Cancellation getCancellation() {
        return cancellation;
    }

    public void setCancellation(Cancellation cancellation) {
        this.cancellation = cancellation;
    }

}