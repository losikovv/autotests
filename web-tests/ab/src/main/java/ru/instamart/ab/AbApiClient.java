package ru.instamart.ab;

import okhttp3.*;
import ru.instamart.ab.interceptor.JwtAuthInterceptor;
import ru.instamart.ab.model.Setting;
import ru.instamart.ab.service.Mapper;

import java.io.IOException;
import java.util.Objects;

public final class AbApiClient {

    public static final MediaType HEADER = MediaType.get("application/json; charset=utf-8");

    private final Setting setting;
    private final OkHttpClient client;

    public AbApiClient(final Setting setting) {
        this.setting = setting;
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new JwtAuthInterceptor(setting.getBasicUrl(), setting.getEmail(), setting.getPassword()))
                .build();
    }

    public <T> T post(Class<T> responseClass, final String endpoint, final Object json) throws IOException {
        final RequestBody body = RequestBody.create(Mapper.INSTANCE.objectToString(json), HEADER);
        final Request request = new Request.Builder()
                .url(setting.getBasicUrl() + endpoint)
                .post(body)
                .build();
        try (final Response response = client.newCall(request).execute()) {
            return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }

    public <T> T get(Class<T> responseClass, final String endpoint) throws IOException {
        final Request request = new Request.Builder()
                .url(setting.getBasicUrl() + endpoint)
                .get()
                .build();
        try (final Response response = client.newCall(request).execute()) {
            return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }
}
