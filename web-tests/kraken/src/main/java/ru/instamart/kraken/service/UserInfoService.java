package ru.instamart.kraken.service;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public final class UserInfoService {

    public UserInfo createUserInfo() {
        final var userInfoBuilder = UserInfo.builder();

        if (isNull(System.getenv("CI_PIPELINE_SOURCE"))) {
            userInfoBuilder.gitName(getGitName())
                    .gitEmail(getGitEmail())
                    .computerName(System.getProperty("user.name"))
            ;
        } else {
            userInfoBuilder.gitName(System.getenv("GITLAB_USER_NAME"))
                    .gitEmail(System.getenv("GITLAB_USER_EMAIL"))
                    .computerName(System.getProperty("user.name"))
            ;
        }

        return userInfoBuilder.build();
    }

    //git config --global --get user.email
    private String getGitEmail() {
        final var processBuilder = new ProcessBuilder();
        processBuilder.command("git", "config", "--global", "--get", "user.email");
        return exec(processBuilder);
    }

    //git config --global --get user.name
    private String getGitName() {
        final var processBuilder = new ProcessBuilder();
        processBuilder.command("git", "config", "--global", "--get", "user.name");
        return exec(processBuilder);
    }

    private String exec(final ProcessBuilder processBuilder) {
        try {
            final var process = processBuilder.start();

            try (final var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                final var output = new StringBuilder();
                String line;
                while (nonNull(line = reader.readLine())) {
                    output.append(line);
                }
                return output.toString();
            }
        } catch (IOException e) {
            log.error("Can't exec command: {}", processBuilder.command());
        }
        return "empty";
    }

    @Data
    @Builder
    public static final class UserInfo {
        private String gitName;
        private String gitEmail;
        private String computerName;

        @Override
        public String toString() {
            return "(gitName=" + gitName + " gitEmail=" + gitEmail + " computerName=" + computerName + ")";
        }
    }
}
