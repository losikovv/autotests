package ru.instamart.grpc.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.helper.AllureHelper;
import ru.instamart.kraken.listener.ExecutionListener;

import java.util.Map;

@Slf4j
public final class GrpcCustomerPaymentsExecutionListener extends ExecutionListener {
    public GrpcCustomerPaymentsExecutionListener() {
        super();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
        this.revealKraken();
    }

    @Override
    public void onExecutionFinish() {
        super.onExecutionFinish();
    }

    @Override
    public void setupAllureReport() {
        AllureHelper.allureEnvironmentWriter(
                Map.ofEntries(
                        Map.entry("gRPC", GrpcContentHosts.PAAS_CONTENT_CUSTOMER_PAYMENTS)));
    }

    public void revealKraken() {
        log.debug("gRPC {}", GrpcContentHosts.PAAS_CONTENT_CUSTOMER_PAYMENTS);
        log.debug("TEST RUN ID: {}", runId);
    }
}
