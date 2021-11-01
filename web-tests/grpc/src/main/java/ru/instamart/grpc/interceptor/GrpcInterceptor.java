package ru.instamart.grpc.interceptor;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.TextFormat;
import io.grpc.*;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class GrpcInterceptor implements ClientInterceptor {

    public <M> String cyrillic(M message) {
        return TextFormat.printer().escapingNonAscii(false).printToString((MessageOrBuilder) message);
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            final MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new BackendForwardingClientCall<ReqT, RespT>(method,
                next.newCall(method, callOptions.withDeadlineAfter(10000, TimeUnit.MILLISECONDS))) {

            @Override
            public void sendMessage(ReqT message) {
                log.debug("Method: {}, Request: {}", methodName, cyrillic(message));
                Allure.addAttachment("Request", "application/json", cyrillic(message), ".json");
                super.sendMessage(message);
            }

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                BackendListener<RespT> backendListener = new BackendListener<>(methodName, responseListener);
                super.start(backendListener, headers);
            }
        };
    }

    private class BackendListener<RespT> extends ClientCall.Listener<RespT> {

        String methodName;
        ClientCall.Listener<RespT> responseListener;

        protected BackendListener(String methodName, ClientCall.Listener<RespT> responseListener) {
            super();
            this.methodName = methodName;
            this.responseListener = responseListener;
        }

        @Override
        public void onMessage(RespT message) {
            log.debug("Method: {}, Response: {}", methodName, cyrillic(message));
            Allure.addAttachment("Response", "application/json", cyrillic(message), ".json");
            responseListener.onMessage(message);
        }

        @Override
        public void onHeaders(Metadata headers) {
            log.debug("Method: {}, Headers: {}", methodName, headers);
            responseListener.onHeaders(headers);
        }

        @Override
        public void onClose(Status status, Metadata trailers) {
            log.info(status.getCode().name() + " " + status.getDescription());
            Allure.step(status.getCode().name() + " " + status.getDescription());
            responseListener.onClose(status, trailers);
        }

        @Override
        public void onReady() {
            responseListener.onReady();
        }
    }

    private class BackendForwardingClientCall<ReqT, RespT> extends ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT> {

        String methodName;

        protected BackendForwardingClientCall(MethodDescriptor<ReqT, RespT> method, ClientCall delegate) {
            super(delegate);
            methodName = method.getFullMethodName();
        }
    }
}
