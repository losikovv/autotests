package instamart.api.objects.responses;

import instamart.api.objects.Cancellation;

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