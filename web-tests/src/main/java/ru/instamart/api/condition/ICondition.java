package instamart.api.condition;

import instamart.api.action.Registration;
import instamart.ui.common.pagesdata.UserData;

@SuppressWarnings("unchecked")
public interface ICondition<T> {

    default T auth(final String login, final String password) {
        return (T) this;
    }

    default T auth(final UserData userData) {
        return (T) this;
    }

    default T auth(final String token) {
        return (T) this;
    }

    T authFromFactory();

    default T registration(final String email, final String firstName, final String lastName, final String password) {
        Registration.registration(email, firstName, lastName, password);
        return (T) this;
    }

    default T registration(final UserData userData) {
        Registration.registration(userData);
        return (T) this;
    }
}
