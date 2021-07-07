package ru.instamart.ab.interceptor;

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@RequiredArgsConstructor
public final class JwtAuthInterceptor implements Interceptor {

    private final String jwtToken;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = newRequestWithAccessToken(chain.request(), jwtToken);
        return chain.proceed(request);
    }

    private Request newRequestWithAccessToken(final Request request, final String accessToken) {
        return request.newBuilder()
                .header("Authorization", "JWT " + accessToken)
                .build();
    }
}
