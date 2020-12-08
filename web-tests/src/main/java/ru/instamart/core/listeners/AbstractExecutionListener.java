package instamart.core.listeners;

import instamart.api.common.Specification;
import instamart.core.service.BannerService;
import instamart.core.settings.Config;
import instamart.ui.common.pagesdata.EnvironmentData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import static instamart.core.settings.Config.LOG;

public class AbstractExecutionListener implements IExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(AbstractExecutionListener.class);

    // Нужно инициализировать в констркуторе, что бы гарантировать наличие конфигов до запуска чего либо
    public AbstractExecutionListener() {
        logger.info("Load config");
        Config.load();
    }

    @Override
    public void onExecutionStart() {
        logger.info("Load KRAKEN");
        BannerService.printBanner();

        logger.info("Load environment config");
        EnvironmentData.INSTANCE.load();

        if (LOG) {
            logger.info("Configure system out to slf4j");
            SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        }

        logger.info("Init rest specification");
        Specification.INSTANCE.initSpec();
    }

    @Override
    public void onExecutionFinish() {
        // Тут может быть код для очистки окружения после прогона тестов
    }
}
