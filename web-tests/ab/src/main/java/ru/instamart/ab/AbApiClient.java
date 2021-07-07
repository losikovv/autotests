package ru.instamart.ab;

import okhttp3.*;
import ru.instamart.kraken.service.MapperService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class AbApiClient {

    public static final MediaType HEADER = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client;

    public AbApiClient() {
        this.client = new OkHttpClient.Builder()
                //.addInterceptor(new BasicAuthInterceptor())
                .build();
    }

    public <T> T post(Class<T> responseClass, final String endpoint, final String json) throws IOException {
        final RequestBody body = RequestBody.create(json, HEADER);
        final Request request = new Request.Builder()
                .url(AbApi.BASE_AB_SERVICE_URL + endpoint)
                .post(body)
                .build();
        try (final Response response = client.newCall(request).execute()) {
            return MapperService.INSTANCE.jsonToObject(Objects.requireNonNull(response.body()).string(), responseClass);
        }
    }
}
