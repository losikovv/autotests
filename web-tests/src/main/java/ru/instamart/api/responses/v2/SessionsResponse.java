package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Session;

public class SessionsResponse extends BaseResponseObject {

    private Session session;

    /**
     * No args constructor for use in serialization
     *
     */
    public SessionsResponse() {
    }

    /**
     *
     * @param session
     */
    public SessionsResponse(Session session) {
        super();
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
