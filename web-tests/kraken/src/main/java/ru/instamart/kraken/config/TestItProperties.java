package ru.instamart.kraken.config;

import ru.instamart.kraken.common.config.Config;

public final class TestItProperties {

    public static final String NAME = "testit";

    @Config(configName = NAME, fieldName = "url", defaultValue = "")
    public static String URL;
    @Config(configName = NAME, fieldName = "privateToken", defaultValue = "", env = "TESTIT_TOKEN", encrypted = true)
    public static String PRIVATE_TOKEN;

    @Config(configName = NAME, fieldName = "projectId", defaultValue = "", env = "PROJECT_ID")
    public static String PROJECT_ID;
    @Config(configName = NAME, fieldName = "configurationId", defaultValue = "null", env = "CONFIGURATION_ID")
    public static String CONFIGURATION_ID;
    @Config(configName = NAME, fieldName = "testRunId", defaultValue = "null", env = "TEST_RUN_ID")
    public static String TEST_RUN_ID;
    @Config(configName = NAME, fieldName = "testRunName", defaultValue = "default_run", env = "TEST_RUN_NAME")
    public static String TEST_RUN_NAME;
    @Config(configName = NAME, fieldName = "adapterMode", defaultValue = "0", env = "MODE")
    public static String ADAPTER_MODE;
}
