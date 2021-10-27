package ru.instamart.qa;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import ru.instamart.qa.model.Setting;
import ru.instamart.utils.Mapper;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class ApiClient {

    public static final MediaType TYPE = MediaType.get("application/json; charset=utf-8");

    private final Setting setting;
    private final OkHttpClient client;

    public ApiClient(final Setting setting) {
        this.setting = setting;
        this.client = new OkHttpClient.Builder()
                .authenticator(new BasicAuth(setting.getBasicUrl()))
                //.addInterceptor(new LoggingInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public <T> T post(Class<T> responseClass, final String endpoint, final Object json) throws IOException {
        final RequestBody body = RequestBody.create(Mapper.INSTANCE.objectToString(json), TYPE);
        final Request request = new Request.Builder()
                .url(setting.getBasicUrl() + endpoint)
                .post(body)
                .build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException(String.format("FAILED post request '%s' with status: '%s'", endpoint,  response.code()));
            }
            return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }

    public <T> T get(Class<T> responseClass, final String endpoint) throws IOException {
        final Request request = new Request.Builder()
                .url(setting.getBasicUrl() + endpoint)
                .get()
                .build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException(String.format("FAILED get request %s with status: %s", endpoint,  response.code()));
            }
            return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }

    public <T> T put(Class<T> responseClass, final String endpoint, final Object json) throws IOException {
        final RequestBody body = RequestBody.create(Mapper.INSTANCE.objectToString(json), TYPE);
        final Request request = new Request.Builder()
                .url(setting.getBasicUrl() + endpoint)
                .put(body)
                .build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException(String.format("FAILED put request %s with status: %s", endpoint,  response.code()));
            }
            return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }

    public <T> T delete(Class<T> responseClass, final String endpoint, final Object json) throws IOException {
        final RequestBody body = RequestBody.create(Mapper.INSTANCE.objectToString(json), TYPE);
        final Request request = new Request.Builder()
                .url(setting.getBasicUrl() + endpoint)
                .delete(body)
                .build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException(String.format("FAILED delete request %s with status: %s", endpoint,  response.code()));
            }
            return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }

    public String delete(final String endpoint) throws IOException {
        final Request request = new Request.Builder()
                .url(setting.getBasicUrl() + endpoint)
                .delete()
                .build();
        try (final Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException(String.format("FAILED delete request %s with status: %s", endpoint,  response.code()));
            }
        }
        return "ok";
    }
}
