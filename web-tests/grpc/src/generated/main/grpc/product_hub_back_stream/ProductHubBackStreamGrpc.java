package product_hub_back_stream;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 **
 * slack:
 *    #product-hub
 * base:
 *     base-product-hub-back.paas-content-product-hub:3009
 * local:
 *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
 * swagger prod:
 *     https://paas-content-product-hub-back.sbmt.io/api
 * swagger stg:
 *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
 * public prod grpc uri:
 *    paas-content-product-hub-back.sbmt.io:443
 * public stg grpc uri:
 *    paas-content-product-hub-back.gw-stage.sbmt.io:443
 * description:
 *    Stream сервис product-hub для полного экспорта данных другим потребителям, например, поиску.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/product_hub/product-hub-back-stream.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductHubBackStreamGrpc {

  private ProductHubBackStreamGrpc() {}

  public static final String SERVICE_NAME = "product_hub_back_stream.ProductHubBackStream";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest,
      product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> getGetProductsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProducts",
      requestType = product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest.class,
      responseType = product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest,
      product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> getGetProductsMethod() {
    io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest, product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> getGetProductsMethod;
    if ((getGetProductsMethod = ProductHubBackStreamGrpc.getGetProductsMethod) == null) {
      synchronized (ProductHubBackStreamGrpc.class) {
        if ((getGetProductsMethod = ProductHubBackStreamGrpc.getGetProductsMethod) == null) {
          ProductHubBackStreamGrpc.getGetProductsMethod = getGetProductsMethod =
              io.grpc.MethodDescriptor.<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest, product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProducts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackStreamMethodDescriptorSupplier("GetProducts"))
              .build();
        }
      }
    }
    return getGetProductsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest,
      product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse> getGetOffersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOffers",
      requestType = product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest.class,
      responseType = product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest,
      product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse> getGetOffersMethod() {
    io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest, product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse> getGetOffersMethod;
    if ((getGetOffersMethod = ProductHubBackStreamGrpc.getGetOffersMethod) == null) {
      synchronized (ProductHubBackStreamGrpc.class) {
        if ((getGetOffersMethod = ProductHubBackStreamGrpc.getGetOffersMethod) == null) {
          ProductHubBackStreamGrpc.getGetOffersMethod = getGetOffersMethod =
              io.grpc.MethodDescriptor.<product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest, product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOffers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackStreamMethodDescriptorSupplier("GetOffers"))
              .build();
        }
      }
    }
    return getGetOffersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest,
      product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse> getGetPricesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPrices",
      requestType = product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest.class,
      responseType = product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest,
      product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse> getGetPricesMethod() {
    io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest, product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse> getGetPricesMethod;
    if ((getGetPricesMethod = ProductHubBackStreamGrpc.getGetPricesMethod) == null) {
      synchronized (ProductHubBackStreamGrpc.class) {
        if ((getGetPricesMethod = ProductHubBackStreamGrpc.getGetPricesMethod) == null) {
          ProductHubBackStreamGrpc.getGetPricesMethod = getGetPricesMethod =
              io.grpc.MethodDescriptor.<product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest, product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPrices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackStreamMethodDescriptorSupplier("GetPrices"))
              .build();
        }
      }
    }
    return getGetPricesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest,
      product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse> getGetStocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStocks",
      requestType = product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest.class,
      responseType = product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest,
      product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse> getGetStocksMethod() {
    io.grpc.MethodDescriptor<product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest, product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse> getGetStocksMethod;
    if ((getGetStocksMethod = ProductHubBackStreamGrpc.getGetStocksMethod) == null) {
      synchronized (ProductHubBackStreamGrpc.class) {
        if ((getGetStocksMethod = ProductHubBackStreamGrpc.getGetStocksMethod) == null) {
          ProductHubBackStreamGrpc.getGetStocksMethod = getGetStocksMethod =
              io.grpc.MethodDescriptor.<product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest, product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackStreamMethodDescriptorSupplier("GetStocks"))
              .build();
        }
      }
    }
    return getGetStocksMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductHubBackStreamStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubBackStreamStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubBackStreamStub>() {
        @java.lang.Override
        public ProductHubBackStreamStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubBackStreamStub(channel, callOptions);
        }
      };
    return ProductHubBackStreamStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductHubBackStreamBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubBackStreamBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubBackStreamBlockingStub>() {
        @java.lang.Override
        public ProductHubBackStreamBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubBackStreamBlockingStub(channel, callOptions);
        }
      };
    return ProductHubBackStreamBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductHubBackStreamFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubBackStreamFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubBackStreamFutureStub>() {
        @java.lang.Override
        public ProductHubBackStreamFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubBackStreamFutureStub(channel, callOptions);
        }
      };
    return ProductHubBackStreamFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub-back.paas-content-product-hub:3009
   * local:
   *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub-back.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub-back.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub-back.gw-stage.sbmt.io:443
   * description:
   *    Stream сервис product-hub для полного экспорта данных другим потребителям, например, поиску.
   * </pre>
   */
  public static abstract class ProductHubBackStreamImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     **
     * Возвращает описание товаров.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    display_attributes - возможность запрашивать только конкретные атрибуты описания, например, картинки
     *      - фильтрация по key: {"keys":["brand", "image"]}
     *      - филььрация по flag: {"flags":["show_as_characteristic"]}
     *      - фильтрация по flag+key: {"keys":["brand", "image"], "flags":["show_as_characteristic"]}
     * </pre>
     */
    public void getProducts(product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     * </pre>
     */
    public void getOffers(product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOffersMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает цены.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    shard_number - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать shard_number. shard_number можно забрать из последнего response до обрыва соединения. Для скачивания всех цен, шард должен иметь дефолтное значение.
     * </pre>
     */
    public void getPrices(product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPricesMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает остатки.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    shard_number - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать shard_number. shard_number можно забрать из последнего response до обрыва соединения. Для скачивания всех цен, шард должен иметь дефолтное значение.
     * </pre>
     */
    public void getStocks(product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStocksMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetProductsMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest,
                product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse>(
                  this, METHODID_GET_PRODUCTS)))
          .addMethod(
            getGetOffersMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest,
                product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse>(
                  this, METHODID_GET_OFFERS)))
          .addMethod(
            getGetPricesMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest,
                product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse>(
                  this, METHODID_GET_PRICES)))
          .addMethod(
            getGetStocksMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest,
                product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse>(
                  this, METHODID_GET_STOCKS)))
          .build();
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub-back.paas-content-product-hub:3009
   * local:
   *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub-back.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub-back.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub-back.gw-stage.sbmt.io:443
   * description:
   *    Stream сервис product-hub для полного экспорта данных другим потребителям, например, поиску.
   * </pre>
   */
  public static final class ProductHubBackStreamStub extends io.grpc.stub.AbstractAsyncStub<ProductHubBackStreamStub> {
    private ProductHubBackStreamStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubBackStreamStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubBackStreamStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    display_attributes - возможность запрашивать только конкретные атрибуты описания, например, картинки
     *      - фильтрация по key: {"keys":["brand", "image"]}
     *      - филььрация по flag: {"flags":["show_as_characteristic"]}
     *      - фильтрация по flag+key: {"keys":["brand", "image"], "flags":["show_as_characteristic"]}
     * </pre>
     */
    public void getProducts(product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetProductsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     * </pre>
     */
    public void getOffers(product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetOffersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает цены.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    shard_number - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать shard_number. shard_number можно забрать из последнего response до обрыва соединения. Для скачивания всех цен, шард должен иметь дефолтное значение.
     * </pre>
     */
    public void getPrices(product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetPricesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает остатки.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    shard_number - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать shard_number. shard_number можно забрать из последнего response до обрыва соединения. Для скачивания всех цен, шард должен иметь дефолтное значение.
     * </pre>
     */
    public void getStocks(product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetStocksMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub-back.paas-content-product-hub:3009
   * local:
   *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub-back.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub-back.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub-back.gw-stage.sbmt.io:443
   * description:
   *    Stream сервис product-hub для полного экспорта данных другим потребителям, например, поиску.
   * </pre>
   */
  public static final class ProductHubBackStreamBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductHubBackStreamBlockingStub> {
    private ProductHubBackStreamBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubBackStreamBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubBackStreamBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    display_attributes - возможность запрашивать только конкретные атрибуты описания, например, картинки
     *      - фильтрация по key: {"keys":["brand", "image"]}
     *      - филььрация по flag: {"flags":["show_as_characteristic"]}
     *      - фильтрация по flag+key: {"keys":["brand", "image"], "flags":["show_as_characteristic"]}
     * </pre>
     */
    public java.util.Iterator<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> getProducts(
        product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetProductsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     * </pre>
     */
    public java.util.Iterator<product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse> getOffers(
        product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetOffersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает цены.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    shard_number - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать shard_number. shard_number можно забрать из последнего response до обрыва соединения. Для скачивания всех цен, шард должен иметь дефолтное значение.
     * </pre>
     */
    public java.util.Iterator<product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse> getPrices(
        product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetPricesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает остатки.
     * Параметры запроса:
     *    cursor_id - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать cursor_id. cursor_id можно забрать из последнего response до обрыва соединения.
     *    shard_number - при обрыве соединения для продолжения выгрузки места разрыва необходимо передать shard_number. shard_number можно забрать из последнего response до обрыва соединения. Для скачивания всех цен, шард должен иметь дефолтное значение.
     * </pre>
     */
    public java.util.Iterator<product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse> getStocks(
        product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetStocksMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub-back.paas-content-product-hub:3009
   * local:
   *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub-back.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub-back.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub-back.gw-stage.sbmt.io:443
   * description:
   *    Stream сервис product-hub для полного экспорта данных другим потребителям, например, поиску.
   * </pre>
   */
  public static final class ProductHubBackStreamFutureStub extends io.grpc.stub.AbstractFutureStub<ProductHubBackStreamFutureStub> {
    private ProductHubBackStreamFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubBackStreamFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubBackStreamFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GET_PRODUCTS = 0;
  private static final int METHODID_GET_OFFERS = 1;
  private static final int METHODID_GET_PRICES = 2;
  private static final int METHODID_GET_STOCKS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductHubBackStreamImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductHubBackStreamImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCTS:
          serviceImpl.getProducts((product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse>) responseObserver);
          break;
        case METHODID_GET_OFFERS:
          serviceImpl.getOffers((product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetOffersResponse>) responseObserver);
          break;
        case METHODID_GET_PRICES:
          serviceImpl.getPrices((product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetPricesResponse>) responseObserver);
          break;
        case METHODID_GET_STOCKS:
          serviceImpl.getStocks((product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetStocksResponse>) responseObserver);
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

  private static abstract class ProductHubBackStreamBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductHubBackStreamBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return product_hub_back_stream.ProductHubBackStreamOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductHubBackStream");
    }
  }

  private static final class ProductHubBackStreamFileDescriptorSupplier
      extends ProductHubBackStreamBaseDescriptorSupplier {
    ProductHubBackStreamFileDescriptorSupplier() {}
  }

  private static final class ProductHubBackStreamMethodDescriptorSupplier
      extends ProductHubBackStreamBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductHubBackStreamMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProductHubBackStreamGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductHubBackStreamFileDescriptorSupplier())
              .addMethod(getGetProductsMethod())
              .addMethod(getGetOffersMethod())
              .addMethod(getGetPricesMethod())
              .addMethod(getGetStocksMethod())
              .build();
        }
      }
    }
    return result;
  }
}
