package ru.instamart.api.common;

import io.grpc.ManagedChannel;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterClass;
import ru.instamart.api.helper.GrpcHelper;

@Slf4j
abstract public class GrpcBase {
    protected ManagedChannel channel;
    protected GrpcHelper grpcStep = new GrpcHelper();

    @AfterClass(alwaysRun = true)
    public void channelShutdown() {
        log.info("Shutdown channel: " + channel);
        channel.shutdown();
    }
}
