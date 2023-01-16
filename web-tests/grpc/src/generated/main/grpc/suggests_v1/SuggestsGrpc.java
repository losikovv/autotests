package suggests_v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/catalog/suggests_v1.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SuggestsGrpc {

  private SuggestsGrpc() {}

  public static final String SERVICE_NAME = "suggests_v1.Suggests";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<suggests_v1.SuggestsV1.GetSuggestsRequest,
      suggests_v1.SuggestsV1.GetSuggestsResponse> getGetMobileSuggestsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMobileSuggests",
      requestType = suggests_v1.SuggestsV1.GetSuggestsRequest.class,
      responseType = suggests_v1.SuggestsV1.GetSuggestsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<suggests_v1.SuggestsV1.GetSuggestsRequest,
      suggests_v1.SuggestsV1.GetSuggestsResponse> getGetMobileSuggestsMethod() {
    io.grpc.MethodDescriptor<suggests_v1.SuggestsV1.GetSuggestsRequest, suggests_v1.SuggestsV1.GetSuggestsResponse> getGetMobileSuggestsMethod;
    if ((getGetMobileSuggestsMethod = SuggestsGrpc.getGetMobileSuggestsMethod) == null) {
      synchronized (SuggestsGrpc.class) {
        if ((getGetMobileSuggestsMethod = SuggestsGrpc.getGetMobileSuggestsMethod) == null) {
          SuggestsGrpc.getGetMobileSuggestsMethod = getGetMobileSuggestsMethod =
              io.grpc.MethodDescriptor.<suggests_v1.SuggestsV1.GetSuggestsRequest, suggests_v1.SuggestsV1.GetSuggestsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMobileSuggests"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  suggests_v1.SuggestsV1.GetSuggestsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  suggests_v1.SuggestsV1.GetSuggestsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SuggestsMethodDescriptorSupplier("GetMobileSuggests"))
              .build();
        }
      }
    }
    return getGetMobileSuggestsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SuggestsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SuggestsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SuggestsStub>() {
        @java.lang.Override
        public SuggestsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SuggestsStub(channel, callOptions);
        }
      };
    return SuggestsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SuggestsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SuggestsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SuggestsBlockingStub>() {
        @java.lang.Override
        public SuggestsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SuggestsBlockingStub(channel, callOptions);
        }
      };
    return SuggestsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SuggestsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SuggestsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SuggestsFutureStub>() {
        @java.lang.Override
        public SuggestsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SuggestsFutureStub(channel, callOptions);
        }
      };
    return SuggestsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SuggestsImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Возвращает предложения товаров/категорий/брендов по поисковому запросу для мобильных платформ
     * Обязательные параметры:
     *    text - поисковый запрос
     *    store_id - идентификатор магазина
     *    retailer_id - идентификатор ритейлера
     * Необязательные параметры:
     *    tenant_id - идентификатор тенанта
     * Примеры stage:
     *    grpcurl -emit-defaults -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' paas-content-catalog.gw-stage.sbmt.io:443 suggests_v1.Suggests.GetMobileSuggests
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://paas-content-catalog.gw-stage.sbmt.io/api/mobile/v1/suggests
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://stf-gw.k-stage.sbmt.io/catalog/api/mobile/v1/suggests
     * Примеры prod:
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://stf-gw.sbmt.io/catalog/api/mobile/v1/suggests
     * </pre>
     */
    public void getMobileSuggests(suggests_v1.SuggestsV1.GetSuggestsRequest request,
        io.grpc.stub.StreamObserver<suggests_v1.SuggestsV1.GetSuggestsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMobileSuggestsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMobileSuggestsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                suggests_v1.SuggestsV1.GetSuggestsRequest,
                suggests_v1.SuggestsV1.GetSuggestsResponse>(
                  this, METHODID_GET_MOBILE_SUGGESTS)))
          .build();
    }
  }

  /**
   */
  public static final class SuggestsStub extends io.grpc.stub.AbstractAsyncStub<SuggestsStub> {
    private SuggestsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SuggestsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SuggestsStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает предложения товаров/категорий/брендов по поисковому запросу для мобильных платформ
     * Обязательные параметры:
     *    text - поисковый запрос
     *    store_id - идентификатор магазина
     *    retailer_id - идентификатор ритейлера
     * Необязательные параметры:
     *    tenant_id - идентификатор тенанта
     * Примеры stage:
     *    grpcurl -emit-defaults -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' paas-content-catalog.gw-stage.sbmt.io:443 suggests_v1.Suggests.GetMobileSuggests
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://paas-content-catalog.gw-stage.sbmt.io/api/mobile/v1/suggests
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://stf-gw.k-stage.sbmt.io/catalog/api/mobile/v1/suggests
     * Примеры prod:
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://stf-gw.sbmt.io/catalog/api/mobile/v1/suggests
     * </pre>
     */
    public void getMobileSuggests(suggests_v1.SuggestsV1.GetSuggestsRequest request,
        io.grpc.stub.StreamObserver<suggests_v1.SuggestsV1.GetSuggestsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMobileSuggestsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SuggestsBlockingStub extends io.grpc.stub.AbstractBlockingStub<SuggestsBlockingStub> {
    private SuggestsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SuggestsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SuggestsBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает предложения товаров/категорий/брендов по поисковому запросу для мобильных платформ
     * Обязательные параметры:
     *    text - поисковый запрос
     *    store_id - идентификатор магазина
     *    retailer_id - идентификатор ритейлера
     * Необязательные параметры:
     *    tenant_id - идентификатор тенанта
     * Примеры stage:
     *    grpcurl -emit-defaults -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' paas-content-catalog.gw-stage.sbmt.io:443 suggests_v1.Suggests.GetMobileSuggests
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://paas-content-catalog.gw-stage.sbmt.io/api/mobile/v1/suggests
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://stf-gw.k-stage.sbmt.io/catalog/api/mobile/v1/suggests
     * Примеры prod:
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://stf-gw.sbmt.io/catalog/api/mobile/v1/suggests
     * </pre>
     */
    public suggests_v1.SuggestsV1.GetSuggestsResponse getMobileSuggests(suggests_v1.SuggestsV1.GetSuggestsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMobileSuggestsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SuggestsFutureStub extends io.grpc.stub.AbstractFutureStub<SuggestsFutureStub> {
    private SuggestsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SuggestsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SuggestsFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает предложения товаров/категорий/брендов по поисковому запросу для мобильных платформ
     * Обязательные параметры:
     *    text - поисковый запрос
     *    store_id - идентификатор магазина
     *    retailer_id - идентификатор ритейлера
     * Необязательные параметры:
     *    tenant_id - идентификатор тенанта
     * Примеры stage:
     *    grpcurl -emit-defaults -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' paas-content-catalog.gw-stage.sbmt.io:443 suggests_v1.Suggests.GetMobileSuggests
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://paas-content-catalog.gw-stage.sbmt.io/api/mobile/v1/suggests
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://stf-gw.k-stage.sbmt.io/catalog/api/mobile/v1/suggests
     * Примеры prod:
     *    curl -d '{"tenant_id":"sbermarket", "store_id":"1", "retailer_id":"1", "text":"ко"}' https://stf-gw.sbmt.io/catalog/api/mobile/v1/suggests
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<suggests_v1.SuggestsV1.GetSuggestsResponse> getMobileSuggests(
        suggests_v1.SuggestsV1.GetSuggestsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMobileSuggestsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_MOBILE_SUGGESTS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SuggestsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SuggestsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_MOBILE_SUGGESTS:
          serviceImpl.getMobileSuggests((suggests_v1.SuggestsV1.GetSuggestsRequest) request,
              (io.grpc.stub.StreamObserver<suggests_v1.SuggestsV1.GetSuggestsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SuggestsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SuggestsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return suggests_v1.SuggestsV1.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Suggests");
    }
  }

  private static final class SuggestsFileDescriptorSupplier
      extends SuggestsBaseDescriptorSupplier {
    SuggestsFileDescriptorSupplier() {}
  }

  private static final class SuggestsMethodDescriptorSupplier
      extends SuggestsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SuggestsMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SuggestsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SuggestsFileDescriptorSupplier())
              .addMethod(getGetMobileSuggestsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
