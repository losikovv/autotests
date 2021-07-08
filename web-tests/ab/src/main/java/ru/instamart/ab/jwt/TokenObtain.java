package ru.instamart.ab.jwt;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.instamart.ab.AbApiClient;
import ru.instamart.ab.Endpoint;
import ru.instamart.ab.model.request.JwtObtain;
import ru.instamart.ab.model.response.JwtResponse;
import ru.instamart.ab.service.Mapper;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class TokenObtain {

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    public JwtResponse execute(final String url, final String email, final String password) throws IOException {
        final JwtObtain obtain = new JwtObtain();
        obtain.setEmail(email);
        obtain.setPassword(password);
        final Request request = new Request.Builder()
                .url(url + Endpoint.OBTAIN)
                .post(RequestBody.create(Mapper.INSTANCE.objectToString(obtain), AbApiClient.HEADER))
                .build();
        final Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("FAILED jwt token obtain: " + Objects.requireNonNull(response.body()).string());
        }

        return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), JwtResponse.class);
    }
}
