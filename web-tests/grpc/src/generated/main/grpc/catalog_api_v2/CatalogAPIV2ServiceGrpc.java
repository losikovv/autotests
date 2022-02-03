package catalog_api_v2;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * slack:
 *    #product-hub
 * swagger prod:
 *     https://paas-content-catalog.sbmt.io/api
 * swagger stg:
 *     https://paas-content-catalog.gw-stage.sbmt.io/api
 * public prod grpc uri:
 *    paas-content-catalog.sbmt.io:443
 * public stg grpc uri:
 *    paas-content-catalog.gw-stage.sbmt.io:443
 * description:
 *    Карточка товара и листинг для мобильного приложения
 *    Предназначет для real-time нагрузки.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/catalog/catalog_api_v2.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CatalogAPIV2ServiceGrpc {

  private CatalogAPIV2ServiceGrpc() {}

  public static final String SERVICE_NAME = "catalog_api_v2.CatalogAPIV2Service";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<catalog_api_v2.CatalogApiV2.GetProductRequest,
      catalog_api_v2.CatalogApiV2.GetProductResponse> getGetProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProduct",
      requestType = catalog_api_v2.CatalogApiV2.GetProductRequest.class,
      responseType = catalog_api_v2.CatalogApiV2.GetProductResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog_api_v2.CatalogApiV2.GetProductRequest,
      catalog_api_v2.CatalogApiV2.GetProductResponse> getGetProductMethod() {
    io.grpc.MethodDescriptor<catalog_api_v2.CatalogApiV2.GetProductRequest, catalog_api_v2.CatalogApiV2.GetProductResponse> getGetProductMethod;
    if ((getGetProductMethod = CatalogAPIV2ServiceGrpc.getGetProductMethod) == null) {
      synchronized (CatalogAPIV2ServiceGrpc.class) {
        if ((getGetProductMethod = CatalogAPIV2ServiceGrpc.getGetProductMethod) == null) {
          CatalogAPIV2ServiceGrpc.getGetProductMethod = getGetProductMethod =
              io.grpc.MethodDescriptor.<catalog_api_v2.CatalogApiV2.GetProductRequest, catalog_api_v2.CatalogApiV2.GetProductResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v2.CatalogApiV2.GetProductRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v2.CatalogApiV2.GetProductResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogAPIV2ServiceMethodDescriptorSupplier("GetProduct"))
              .build();
        }
      }
    }
    return getGetProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<catalog_api_v2.CatalogApiV2.GetProductListRequest,
      catalog_api_v2.CatalogApiV2.GetProductListResponse> getGetProductListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductList",
      requestType = catalog_api_v2.CatalogApiV2.GetProductListRequest.class,
      responseType = catalog_api_v2.CatalogApiV2.GetProductListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog_api_v2.CatalogApiV2.GetProductListRequest,
      catalog_api_v2.CatalogApiV2.GetProductListResponse> getGetProductListMethod() {
    io.grpc.MethodDescriptor<catalog_api_v2.CatalogApiV2.GetProductListRequest, catalog_api_v2.CatalogApiV2.GetProductListResponse> getGetProductListMethod;
    if ((getGetProductListMethod = CatalogAPIV2ServiceGrpc.getGetProductListMethod) == null) {
      synchronized (CatalogAPIV2ServiceGrpc.class) {
        if ((getGetProductListMethod = CatalogAPIV2ServiceGrpc.getGetProductListMethod) == null) {
          CatalogAPIV2ServiceGrpc.getGetProductListMethod = getGetProductListMethod =
              io.grpc.MethodDescriptor.<catalog_api_v2.CatalogApiV2.GetProductListRequest, catalog_api_v2.CatalogApiV2.GetProductListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v2.CatalogApiV2.GetProductListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v2.CatalogApiV2.GetProductListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogAPIV2ServiceMethodDescriptorSupplier("GetProductList"))
              .build();
        }
      }
    }
    return getGetProductListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CatalogAPIV2ServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV2ServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV2ServiceStub>() {
        @java.lang.Override
        public CatalogAPIV2ServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogAPIV2ServiceStub(channel, callOptions);
        }
      };
    return CatalogAPIV2ServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CatalogAPIV2ServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV2ServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV2ServiceBlockingStub>() {
        @java.lang.Override
        public CatalogAPIV2ServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogAPIV2ServiceBlockingStub(channel, callOptions);
        }
      };
    return CatalogAPIV2ServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CatalogAPIV2ServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV2ServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogAPIV2ServiceFutureStub>() {
        @java.lang.Override
        public CatalogAPIV2ServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogAPIV2ServiceFutureStub(channel, callOptions);
        }
      };
    return CatalogAPIV2ServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * swagger prod:
   *     https://paas-content-catalog.sbmt.io/api
   * swagger stg:
   *     https://paas-content-catalog.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog.gw-stage.sbmt.io:443
   * description:
   *    Карточка товара и листинг для мобильного приложения
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static abstract class CatalogAPIV2ServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Возвращает карточку товаров
     * Обязательные параметры:
     *    product_id - идентификатор офера
     * Примеры:
     *    Вернуть карточку:
     *        https://paas-content-catalog.sbmt.io/api/v2/products/11318962
     * </pre>
     */
    public void getProduct(catalog_api_v2.CatalogApiV2.GetProductRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v2.CatalogApiV2.GetProductResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает листинг товаров
     * Обязательные параметры:
     *    sid - идентификатор магазина
     *	  tid - идентификатор категории (mysql.taxon)
     * Примеры:
     *    Вернуть листинг товаров:
     *        https://paas-content-catalog.sbmt.io/api/v2/products?sid=63&amp;tid=43492
     * </pre>
     */
    public void getProductList(catalog_api_v2.CatalogApiV2.GetProductListRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v2.CatalogApiV2.GetProductListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetProductMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog_api_v2.CatalogApiV2.GetProductRequest,
                catalog_api_v2.CatalogApiV2.GetProductResponse>(
                  this, METHODID_GET_PRODUCT)))
          .addMethod(
            getGetProductListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog_api_v2.CatalogApiV2.GetProductListRequest,
                catalog_api_v2.CatalogApiV2.GetProductListResponse>(
                  this, METHODID_GET_PRODUCT_LIST)))
          .build();
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * swagger prod:
   *     https://paas-content-catalog.sbmt.io/api
   * swagger stg:
   *     https://paas-content-catalog.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog.gw-stage.sbmt.io:443
   * description:
   *    Карточка товара и листинг для мобильного приложения
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class CatalogAPIV2ServiceStub extends io.grpc.stub.AbstractAsyncStub<CatalogAPIV2ServiceStub> {
    private CatalogAPIV2ServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogAPIV2ServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogAPIV2ServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает карточку товаров
     * Обязательные параметры:
     *    product_id - идентификатор офера
     * Примеры:
     *    Вернуть карточку:
     *        https://paas-content-catalog.sbmt.io/api/v2/products/11318962
     * </pre>
     */
    public void getProduct(catalog_api_v2.CatalogApiV2.GetProductRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v2.CatalogApiV2.GetProductResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает листинг товаров
     * Обязательные параметры:
     *    sid - идентификатор магазина
     *	  tid - идентификатор категории (mysql.taxon)
     * Примеры:
     *    Вернуть листинг товаров:
     *        https://paas-content-catalog.sbmt.io/api/v2/products?sid=63&amp;tid=43492
     * </pre>
     */
    public void getProductList(catalog_api_v2.CatalogApiV2.GetProductListRequest request,
        io.grpc.stub.StreamObserver<catalog_api_v2.CatalogApiV2.GetProductListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * swagger prod:
   *     https://paas-content-catalog.sbmt.io/api
   * swagger stg:
   *     https://paas-content-catalog.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog.gw-stage.sbmt.io:443
   * description:
   *    Карточка товара и листинг для мобильного приложения
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class CatalogAPIV2ServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CatalogAPIV2ServiceBlockingStub> {
    private CatalogAPIV2ServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogAPIV2ServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogAPIV2ServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает карточку товаров
     * Обязательные параметры:
     *    product_id - идентификатор офера
     * Примеры:
     *    Вернуть карточку:
     *        https://paas-content-catalog.sbmt.io/api/v2/products/11318962
     * </pre>
     */
    public catalog_api_v2.CatalogApiV2.GetProductResponse getProduct(catalog_api_v2.CatalogApiV2.GetProductRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает листинг товаров
     * Обязательные параметры:
     *    sid - идентификатор магазина
     *	  tid - идентификатор категории (mysql.taxon)
     * Примеры:
     *    Вернуть листинг товаров:
     *        https://paas-content-catalog.sbmt.io/api/v2/products?sid=63&amp;tid=43492
     * </pre>
     */
    public catalog_api_v2.CatalogApiV2.GetProductListResponse getProductList(catalog_api_v2.CatalogApiV2.GetProductListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductListMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * swagger prod:
   *     https://paas-content-catalog.sbmt.io/api
   * swagger stg:
   *     https://paas-content-catalog.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog.gw-stage.sbmt.io:443
   * description:
   *    Карточка товара и листинг для мобильного приложения
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class CatalogAPIV2ServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CatalogAPIV2ServiceFutureStub> {
    private CatalogAPIV2ServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogAPIV2ServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogAPIV2ServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Возвращает карточку товаров
     * Обязательные параметры:
     *    product_id - идентификатор офера
     * Примеры:
     *    Вернуть карточку:
     *        https://paas-content-catalog.sbmt.io/api/v2/products/11318962
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog_api_v2.CatalogApiV2.GetProductResponse> getProduct(
        catalog_api_v2.CatalogApiV2.GetProductRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает листинг товаров
     * Обязательные параметры:
     *    sid - идентификатор магазина
     *	  tid - идентификатор категории (mysql.taxon)
     * Примеры:
     *    Вернуть листинг товаров:
     *        https://paas-content-catalog.sbmt.io/api/v2/products?sid=63&amp;tid=43492
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog_api_v2.CatalogApiV2.GetProductListResponse> getProductList(
        catalog_api_v2.CatalogApiV2.GetProductListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCT = 0;
  private static final int METHODID_GET_PRODUCT_LIST = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CatalogAPIV2ServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CatalogAPIV2ServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCT:
          serviceImpl.getProduct((catalog_api_v2.CatalogApiV2.GetProductRequest) request,
              (io.grpc.stub.StreamObserver<catalog_api_v2.CatalogApiV2.GetProductResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCT_LIST:
          serviceImpl.getProductList((catalog_api_v2.CatalogApiV2.GetProductListRequest) request,
              (io.grpc.stub.StreamObserver<catalog_api_v2.CatalogApiV2.GetProductListResponse>) responseObserver);
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

  private static abstract class CatalogAPIV2ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CatalogAPIV2ServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return catalog_api_v2.CatalogApiV2.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CatalogAPIV2Service");
    }
  }

  private static final class CatalogAPIV2ServiceFileDescriptorSupplier
      extends CatalogAPIV2ServiceBaseDescriptorSupplier {
    CatalogAPIV2ServiceFileDescriptorSupplier() {}
  }

  private static final class CatalogAPIV2ServiceMethodDescriptorSupplier
      extends CatalogAPIV2ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CatalogAPIV2ServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CatalogAPIV2ServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CatalogAPIV2ServiceFileDescriptorSupplier())
              .addMethod(getGetProductMethod())
              .addMethod(getGetProductListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
