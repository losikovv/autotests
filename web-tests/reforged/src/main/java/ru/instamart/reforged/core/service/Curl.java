package ru.instamart.reforged.core.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class Curl {

    @Getter
    private final List<String> opt;

    public static final class Builder {

        private final List<String> opt;
        private final String url;

        public Builder(final String url) {
            this.opt = new ArrayList<>();
            this.url = url;
            this.opt.add("curl");
            this.opt.add("-X");
            this.opt.add("GET");
            this.opt.add("-s");
            this.opt.add("-o");
            this.opt.add("/dev/null");
            this.opt.add("--head");
            this.opt.add("-w");
            this.opt.add("%{http_code}");
        }

        public Builder withHeader(final String header) {
            this.opt.add("-H");
            this.opt.add("sbm-forward-feature-version-stf:" + header);
            return this;
        }

        public Builder withUserAgent(final String userAgent) {
            this.opt.add("--user-agent");
            this.opt.add(userAgent);
            return this;
        }

        public Curl build() {
            this.opt.add(url);
            return new Curl(opt);
        }
    }
}
