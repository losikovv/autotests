package ru.instamart.reforged.core.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.common.Specification;
import ru.instamart.kraken.listener.ExecutionListener;
import ru.instamart.kraken.testdata.UserManager;

@Slf4j
public final class UiExecutionListener extends ExecutionListener {

    public UiExecutionListener() {
        super();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
        log.info("Init rest specification");
        Specification.INSTANCE.initSpec();
    }

    @Override
    public void onExecutionFinish() {
        // Тут может быть код для очистки окружения после прогона тестов
        log.info("We create {} new users", UserManager.getUserDataList().size());
    }
}
