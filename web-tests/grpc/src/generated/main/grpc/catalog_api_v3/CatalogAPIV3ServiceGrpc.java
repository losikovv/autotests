package catalog_api_v3;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/catalog/catalog_api_v3.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CatalogAPIV3ServiceGrpc {

  private CatalogAPIV3ServiceGrpc() {}

  public static final String SERVICE_NAME = "catalog_api_v3.CatalogAPIV3Service";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductListRequest,
      catalog_api_v3.CatalogApiV3.GetProductListResponse> getGetProductListV3Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductListV3",
      requestType = catalog_api_v3.CatalogApiV3.GetProductListRequest.class,
      responseType = catalog_api_v3.CatalogApiV3.GetProductListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductListRequest,
      catalog_api_v3.CatalogApiV3.GetProductListResponse> getGetProductListV3Method() {
    io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductListRequest, catalog_api_v3.CatalogApiV3.GetProductListResponse> getGetProductListV3Method;
    if ((getGetProductListV3Method = CatalogAPIV3ServiceGrpc.getGetProductListV3Method) == null) {
      synchronized (CatalogAPIV3ServiceGrpc.class) {
        if ((getGetProductListV3Method = CatalogAPIV3ServiceGrpc.getGetProductListV3Method) == null) {
          CatalogAPIV3ServiceGrpc.getGetProductListV3Method = getGetProductListV3Method =
              io.grpc.MethodDescriptor.<catalog_api_v3.CatalogApiV3.GetProductListRequest, catalog_api_v3.CatalogApiV3.GetProductListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductListV3"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetProductListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetProductListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogAPIV3ServiceMethodDescriptorSupplier("GetProductListV3"))
              .build();
        }
      }
    }
    return getGetProductListV3Method;
  }

  private static volatile io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductRequest,
      catalog_api_v3.CatalogApiV3.GetProductResponse> getGetProductV3Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductV3",
      requestType = catalog_api_v3.CatalogApiV3.GetProductRequest.class,
      responseType = catalog_api_v3.CatalogApiV3.GetProductResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductRequest,
      catalog_api_v3.CatalogApiV3.GetProductResponse> getGetProductV3Method() {
    io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductRequest, catalog_api_v3.CatalogApiV3.GetProductResponse> getGetProductV3Method;
    if ((getGetProductV3Method = CatalogAPIV3ServiceGrpc.getGetProductV3Method) == null) {
      synchronized (CatalogAPIV3ServiceGrpc.class) {
        if ((getGetProductV3Method = CatalogAPIV3ServiceGrpc.getGetProductV3Method) == null) {
          CatalogAPIV3ServiceGrpc.getGetProductV3Method = getGetProductV3Method =
              io.grpc.MethodDescriptor.<catalog_api_v3.CatalogApiV3.GetProductRequest, catalog_api_v3.CatalogApiV3.GetProductResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductV3"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetProductRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetProductResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogAPIV3ServiceMethodDescriptorSupplier("GetProductV3"))
              .build();
        }
      }
    }
    return getGetProductV3Method;
  }

  private static volatile io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetSuggestsRequest,
      catalog_api_v3.CatalogApiV3.GetSuggestsResponse> getGetSuggestsV3Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSuggestsV3",
      requestType = catalog_api_v3.CatalogApiV3.GetSuggestsRequest.class,
      responseType = catalog_api_v3.CatalogApiV3.GetSuggestsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetSuggestsRequest,
      catalog_api_v3.CatalogApiV3.GetSuggestsResponse> getGetSuggestsV3Method() {
    io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetSuggestsRequest, catalog_api_v3.CatalogApiV3.GetSuggestsResponse> getGetSuggestsV3Method;
    if ((getGetSuggestsV3Method = CatalogAPIV3ServiceGrpc.getGetSuggestsV3Method) == null) {
      synchronized (CatalogAPIV3ServiceGrpc.class) {
        if ((getGetSuggestsV3Method = CatalogAPIV3ServiceGrpc.getGetSuggestsV3Method) == null) {
          CatalogAPIV3ServiceGrpc.getGetSuggestsV3Method = getGetSuggestsV3Method =
              io.grpc.MethodDescriptor.<catalog_api_v3.CatalogApiV3.GetSuggestsRequest, catalog_api_v3.CatalogApiV3.GetSuggestsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSuggestsV3"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetSuggestsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetSuggestsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogAPIV3ServiceMethodDescriptorSupplier("GetSuggestsV3"))
              .build();
        }
      }
    }
    return getGetSuggestsV3Method;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CatalogAPIV3ServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV3ServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV3ServiceStub>() {
        @java.lang.Override
        public CatalogAPIV3ServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogAPIV3ServiceStub(channel, callOptions);
        }
      };
    return CatalogAPIV3ServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CatalogAPIV3ServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV3ServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV3ServiceBlockingStub>() {
        @java.lang.Override
        public CatalogAPIV3ServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogAPIV3ServiceBlockingStub(channel, callOptions);
        }
      };
    return CatalogAPIV3ServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CatalogAPIV3ServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV3ServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV3ServiceFutureStub>() {
        @java.lang.Override
        public CatalogAPIV3ServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogAPIV3ServiceFutureStub(channel, callOptions);
        }
      };
    return CatalogAPIV3ServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CatalogAPIV3ServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Возвращает листинг товаров
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    category_permalink - пермалинк категории
     *    tenant_id - идентификатор тенанта
     *    q - поисковый запрос
     * </pre>
     */
    public void getProductListV3(catalog_api_v3.CatalogApiV3.GetProductListRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductListV3Method(), responseObserver);
    }

    /**
     */
    public void getProductV3(catalog_api_v3.CatalogApiV3.GetProductRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductV3Method(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает листинг товаров/категорий/брендов по поисковому запросу
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    retailer_id - идентификатор ритейлера
     *    text - поисковый запрос
     * Примеры:
     *    grpcurl -emit-defaults -d '{"store_id":"1", "retailer_id":"1", "text":"ко"}' paas-content-catalog.gw-stage.sbmt.io:443 catalog_api_v3.CatalogAPIV3Service.GetSuggestsV3
     *    curl --data-urlencode 'retailer_id=1' --data-urlencode 'text=ко'  https://paas-content-catalog.gw-stage.sbmt.io/api/v3/stores/1/suggests
     * </pre>
     */
    public void getSuggestsV3(catalog_api_v3.CatalogApiV3.GetSuggestsRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetSuggestsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSuggestsV3Method(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetProductListV3Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog_api_v3.CatalogApiV3.GetProductListRequest,
                catalog_api_v3.CatalogApiV3.GetProductListResponse>(
                  this, METHODID_GET_PRODUCT_LIST_V3)))
          .addMethod(
            getGetProductV3Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog_api_v3.CatalogApiV3.GetProductRequest,
                catalog_api_v3.CatalogApiV3.GetProductResponse>(
                  this, METHODID_GET_PRODUCT_V3)))
          .addMethod(
            getGetSuggestsV3Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog_api_v3.CatalogApiV3.GetSuggestsRequest,
                catalog_api_v3.CatalogApiV3.GetSuggestsResponse>(
                  this, METHODID_GET_SUGGESTS_V3)))
          .build();
    }
  }

  /**
   */
  public static final class CatalogAPIV3ServiceStub extends io.grpc.stub.AbstractAsyncStub<CatalogAPIV3ServiceStub> {
    private CatalogAPIV3ServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogAPIV3ServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogAPIV3ServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает листинг товаров
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    category_permalink - пермалинк категории
     *    tenant_id - идентификатор тенанта
     *    q - поисковый запрос
     * </pre>
     */
    public void getProductListV3(catalog_api_v3.CatalogApiV3.GetProductListRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductListV3Method(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProductV3(catalog_api_v3.CatalogApiV3.GetProductRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductV3Method(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает листинг товаров/категорий/брендов по поисковому запросу
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    retailer_id - идентификатор ритейлера
     *    text - поисковый запрос
     * Примеры:
     *    grpcurl -emit-defaults -d '{"store_id":"1", "retailer_id":"1", "text":"ко"}' paas-content-catalog.gw-stage.sbmt.io:443 catalog_api_v3.CatalogAPIV3Service.GetSuggestsV3
     *    curl --data-urlencode 'retailer_id=1' --data-urlencode 'text=ко'  https://paas-content-catalog.gw-stage.sbmt.io/api/v3/stores/1/suggests
     * </pre>
     */
    public void getSuggestsV3(catalog_api_v3.CatalogApiV3.GetSuggestsRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetSuggestsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSuggestsV3Method(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CatalogAPIV3ServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CatalogAPIV3ServiceBlockingStub> {
    private CatalogAPIV3ServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogAPIV3ServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogAPIV3ServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает листинг товаров
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    category_permalink - пермалинк категории
     *    tenant_id - идентификатор тенанта
     *    q - поисковый запрос
     * </pre>
     */
    public catalog_api_v3.CatalogApiV3.GetProductListResponse getProductListV3(catalog_api_v3.CatalogApiV3.GetProductListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductListV3Method(), getCallOptions(), request);
    }

    /**
     */
    public catalog_api_v3.CatalogApiV3.GetProductResponse getProductV3(catalog_api_v3.CatalogApiV3.GetProductRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductV3Method(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает листинг товаров/категорий/брендов по поисковому запросу
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    retailer_id - идентификатор ритейлера
     *    text - поисковый запрос
     * Примеры:
     *    grpcurl -emit-defaults -d '{"store_id":"1", "retailer_id":"1", "text":"ко"}' paas-content-catalog.gw-stage.sbmt.io:443 catalog_api_v3.CatalogAPIV3Service.GetSuggestsV3
     *    curl --data-urlencode 'retailer_id=1' --data-urlencode 'text=ко'  https://paas-content-catalog.gw-stage.sbmt.io/api/v3/stores/1/suggests
     * </pre>
     */
    public catalog_api_v3.CatalogApiV3.GetSuggestsResponse getSuggestsV3(catalog_api_v3.CatalogApiV3.GetSuggestsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSuggestsV3Method(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CatalogAPIV3ServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CatalogAPIV3ServiceFutureStub> {
    private CatalogAPIV3ServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogAPIV3ServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogAPIV3ServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает листинг товаров
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    category_permalink - пермалинк категории
     *    tenant_id - идентификатор тенанта
     *    q - поисковый запрос
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog_api_v3.CatalogApiV3.GetProductListResponse> getProductListV3(
        catalog_api_v3.CatalogApiV3.GetProductListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductListV3Method(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog_api_v3.CatalogApiV3.GetProductResponse> getProductV3(
        catalog_api_v3.CatalogApiV3.GetProductRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductV3Method(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает листинг товаров/категорий/брендов по поисковому запросу
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    retailer_id - идентификатор ритейлера
     *    text - поисковый запрос
     * Примеры:
     *    grpcurl -emit-defaults -d '{"store_id":"1", "retailer_id":"1", "text":"ко"}' paas-content-catalog.gw-stage.sbmt.io:443 catalog_api_v3.CatalogAPIV3Service.GetSuggestsV3
     *    curl --data-urlencode 'retailer_id=1' --data-urlencode 'text=ко'  https://paas-content-catalog.gw-stage.sbmt.io/api/v3/stores/1/suggests
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog_api_v3.CatalogApiV3.GetSuggestsResponse> getSuggestsV3(
        catalog_api_v3.CatalogApiV3.GetSuggestsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSuggestsV3Method(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCT_LIST_V3 = 0;
  private static final int METHODID_GET_PRODUCT_V3 = 1;
  private static final int METHODID_GET_SUGGESTS_V3 = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CatalogAPIV3ServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CatalogAPIV3ServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCT_LIST_V3:
          serviceImpl.getProductListV3((catalog_api_v3.CatalogApiV3.GetProductListRequest) request,
              (io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductListResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCT_V3:
          serviceImpl.getProductV3((catalog_api_v3.CatalogApiV3.GetProductRequest) request,
              (io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductResponse>) responseObserver);
          break;
        case METHODID_GET_SUGGESTS_V3:
          serviceImpl.getSuggestsV3((catalog_api_v3.CatalogApiV3.GetSuggestsRequest) request,
              (io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetSuggestsResponse>) responseObserver);
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

  private static abstract class CatalogAPIV3ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CatalogAPIV3ServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return catalog_api_v3.CatalogApiV3.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CatalogAPIV3Service");
    }
  }

  private static final class CatalogAPIV3ServiceFileDescriptorSupplier
      extends CatalogAPIV3ServiceBaseDescriptorSupplier {
    CatalogAPIV3ServiceFileDescriptorSupplier() {}
  }

  private static final class CatalogAPIV3ServiceMethodDescriptorSupplier
      extends CatalogAPIV3ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CatalogAPIV3ServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CatalogAPIV3ServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CatalogAPIV3ServiceFileDescriptorSupplier())
              .addMethod(getGetProductListV3Method())
              .addMethod(getGetProductV3Method())
              .addMethod(getGetSuggestsV3Method())
              .build();
        }
      }
    }
    return result;
  }
}
