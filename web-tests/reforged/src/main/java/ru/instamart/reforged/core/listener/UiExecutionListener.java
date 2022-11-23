package ru.instamart.reforged.core.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.common.Specification;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.helper.AllureHelper;
import ru.instamart.kraken.listener.ExecutionListener;
import ru.instamart.reforged.core.config.ConfigManager;
import ru.instamart.reforged.core.config.UiProperties;

import java.util.Map;

@Slf4j
public final class UiExecutionListener extends ExecutionListener {

    public UiExecutionListener() {
        super();
        log.debug("Load UI config");
        ConfigManager.getInstance().loadConfig();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
        log.debug("Init rest specification");
        Specification.INSTANCE.initSpec();
        log.debug("Update Allure env");
        updateAllureReport();
    }

    public void updateAllureReport() {
        AllureHelper.allureEnvironmentUpdate(
                Map.ofEntries(
                        Map.entry("STF URL", UiProperties.STF_URL),
                        Map.entry("STF_FORWARD", UiProperties.HEADER_STF_FORWARD_TO),
                        Map.entry("ADMIN URL", UiProperties.ADMIN_URL),
                        Map.entry("ADMIN FORWARD", UiProperties.HEADER_ADMIN_FORWARD_TO)));
    }

    @Override
    public void onExecutionFinish() {
        super.onExecutionFinish();
        // Тут может быть код для очистки окружения после прогона тестов
        log.debug("We create {} new users", UserManager.userDataCount());
    }
}
