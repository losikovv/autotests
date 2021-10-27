package ru.instamart.grpc.common;

import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import ru.instamart.grpc.helper.AllureHelper;
import ru.instamart.grpc.helper.GrpcHelper;

@Slf4j
abstract public class GrpcBase {
    protected ManagedChannel channel;
    protected GrpcHelper grpc = new GrpcHelper();
    protected AllureHelper allure = new AllureHelper();

    @AfterClass(alwaysRun = true)
    public void channelShutdown() {
        log.debug("Shutdown channel: " + channel);
        channel.shutdown();
    }
}
