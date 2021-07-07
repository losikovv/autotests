package ru.instamart.ab.interceptor;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class BasicAuthInterceptor implements Interceptor {

    private static final String BASIC_USER = "fmcg-user";
    private static final String BASIC_PASS = "fmcg-pass";

    private final String credentials = Credentials.basic(BASIC_USER, BASIC_PASS);

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Request request = chain.request();
        final Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }
}
