package ru.instamart.reforged.core.config;

import ru.instamart.kraken.common.config.Config;

public final class AshotProperties {

    public static final String NAME = "ashot";

    @Config(configName = NAME, fieldName = "allowableDiffSize", defaultValue = "1")
    public static int ALLOWABLE_DIFF_SIZE;
    @Config(configName = NAME, fieldName = "ashotPath", defaultValue = "src/test/resources/ashot")
    public static String ASHOT_PATH;
}
