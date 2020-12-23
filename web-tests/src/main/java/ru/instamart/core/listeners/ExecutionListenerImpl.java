package instamart.core.listeners;

import instamart.api.common.Specification;
import instamart.core.service.BannerService;
import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.core.util.Crypt;
import instamart.ui.common.pagesdata.EnvironmentData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;

public class ExecutionListenerImpl implements IExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionListenerImpl.class);

    // Нужно инициализировать в констркуторе, что бы гарантировать наличие конфигов до запуска чего либо
    public ExecutionListenerImpl() {
        logger.info("Load config");
        Config.load();
        logger.info("Init Crypt");
        Crypt.INSTANCE.init();
    }

    @Override
    public void onExecutionStart() {
        logger.info("Load KRAKEN");
        BannerService.printBanner();

        logger.info("Load environment config");
        EnvironmentData.INSTANCE.load();

        logger.info("Init rest specification");
        Specification.INSTANCE.initSpec();
    }

    @Override
    public void onExecutionFinish() {
        // Тут может быть код для очистки окружения после прогона тестов
        logger.info("We create {} new users", UserManager.getUserDataList().size());
    }
}
