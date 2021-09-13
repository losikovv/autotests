package ru.instamart.qa;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public final class LoggingInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Request request = chain.request();
        final long t1 = System.nanoTime();
        log.debug(String.format("Sending request %s on %n%s%n%s",
                request.url(), bodyToString(Objects.requireNonNull(request.body())), request.headers()));

        final Response response = chain.proceed(request);
        final long t2 = System.nanoTime();
        log.debug(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }

    private String bodyToString(final RequestBody request){
        try {
            final Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }
}
