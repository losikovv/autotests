package instamart.api.condition;

import instamart.api.SessionFactory;
import instamart.api.action.Registration;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.UserData;

@SuppressWarnings("unchecked")
public interface ICondition<T> {

    default void createSession() {
        registrationAndAuth(UserManager.getUser());
    }

    default T auth(final String login, final String password) {
        SessionFactory.createSessionToken(login, password);
        return (T) this;
    }

    default T auth(final UserData userData) {
        SessionFactory.createSessionToken(userData.getLogin(), userData.getPassword());
        return (T) this;
    }

    default T registration(final String email, final String firstName, final String lastName, final String password) {
        Registration.registration(email, firstName, lastName, password);
        return (T) this;
    }

    default T registration(final UserData userData) {
        Registration.registration(userData);
        return (T) this;
    }

    default T registrationAndAuth(final UserData userData) {
        registration(userData);
        auth(userData);
        return (T) this;
    }
}
