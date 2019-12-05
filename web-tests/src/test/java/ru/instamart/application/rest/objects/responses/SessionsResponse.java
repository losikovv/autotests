package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Session;

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
