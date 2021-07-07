package ru.instamart.ab;

import okhttp3.*;
import ru.instamart.ab.interceptor.JwtAuthInterceptor;
import ru.instamart.ab.service.Mapper;

import java.io.IOException;
import java.util.Objects;

public final class AbApiClient {

    public static final MediaType HEADER = MediaType.get("application/json; charset=utf-8");

    private final String basicUrl;
    private final OkHttpClient client;

    public AbApiClient(final String basicUrl) {
        this.basicUrl = basicUrl;
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new JwtAuthInterceptor(""))
                .build();
    }

    public <T> T post(Class<T> responseClass, final String endpoint, final Object json) throws IOException {
        final RequestBody body = RequestBody.create(Mapper.INSTANCE.objectToString(json), HEADER);
        final Request request = new Request.Builder()
                .url(basicUrl + endpoint)
                .post(body)
                .build();
        try (final Response response = client.newCall(request).execute()) {
            return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }

    public <T> T get(Class<T> responseClass, final String endpoint) throws IOException {
        final Request request = new Request.Builder()
                .url(basicUrl + endpoint)
                .get()
                .build();
        try (final Response response = client.newCall(request).execute()) {
            return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }
}
