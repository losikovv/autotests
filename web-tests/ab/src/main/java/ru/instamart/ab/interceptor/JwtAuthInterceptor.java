package ru.instamart.ab.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import ru.instamart.ab.jwt.TokenObtain;
import ru.instamart.ab.model.response.JwtResponse;

import java.io.IOException;

@Slf4j
public final class JwtAuthInterceptor implements Interceptor {

    private JwtResponse jwtResponse = new JwtResponse();

    public JwtAuthInterceptor(final String url, final String email, final String password) {
        final TokenObtain obtain = new TokenObtain();
        try {
            this.jwtResponse = obtain.execute(url, email, password);
        } catch (IOException e) {
            log.error("FATAL: Can't obtain jwt token", e);
        }
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Request request = newRequestWithAccessToken(chain.request(), jwtResponse.getAccess());
        return chain.proceed(request);
    }

    private Request newRequestWithAccessToken(final Request request, final String accessToken) {
        return request.newBuilder()
                .header("Authorization", "JWT " + accessToken)
                .build();
    }
}
