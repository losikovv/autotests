package ru.instamart.qa;

import lombok.RequiredArgsConstructor;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public final class BasicAuth implements Authenticator {

    private final String url;

    private static final String NAMED_STAGE_TOKEN = "UN6zae5B0xWcmVFm9LZVLgge3Y4JkWr5OAB2juMXDL-AfBBvFTydCxijHIFWc0KfrgeixCpJzhTJiJV-ylGedqjAZR3BCdokbinADShRH7i3UQEEu66XoENH_UYKuJdr";
    private static final String FEATURE_STAGE_TOKEN = "2K_n568xLpSe1Va5MHk5PAo9rbvrOlE227hjkBPjxZXQ-ye1sQ4_CwOdoLq6PmKY6AP0ZF6PSG7fVBdLOY9mn_rA2Iukmkv-paIRDZ7X_k83WwzbDdHbqM1jDa9ELos3";

    @Override
    public Request authenticate(@Nullable Route route, @NotNull Response response) {
        if (url.contains("feature")) {
            return response.request().newBuilder().header("Authorization", "Token token=" + FEATURE_STAGE_TOKEN).build();
        } else {
            return response.request().newBuilder().header("Authorization", "Token token=" + NAMED_STAGE_TOKEN).build();
        }
    }
}
