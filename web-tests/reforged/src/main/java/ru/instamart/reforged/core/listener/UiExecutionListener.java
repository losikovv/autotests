package ru.instamart.reforged.core.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.common.Specification;
import ru.instamart.kraken.listener.ExecutionListener;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.ConfigManager;

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
    }

    @Override
    public void onExecutionFinish() {
        super.onExecutionFinish();
        // Тут может быть код для очистки окружения после прогона тестов
        log.debug("We create {} new users", UserManager.getUserDataList().size());
    }
}
