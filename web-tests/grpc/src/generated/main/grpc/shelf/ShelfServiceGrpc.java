package shelf;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 **
 * slack:
 *    #product-hub
 * swagger:
 *     https://paas-content-catalog-shelf.sbmt.io/api
 * public prod grpc uri:
 *    paas-content-catalog-shelf.sbmt.io:443
 * public stg grpc uri:
 *    paas-content-catalog-shelf.gw-stage.sbmt.io:443
 * kuber prod grpc uri:
 *    base-product-hub.paas-content-catalog-shelf:3009
 * description:
 *    Фронтовый сервис catalog shelf возвращающий полки.
 *    Предназначет для real-time нагрузки.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/catalog/shelf.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ShelfServiceGrpc {

  private ShelfServiceGrpc() {}

  public static final String SERVICE_NAME = "shelf.ShelfService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest,
      shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> getGetShelfByOriginalCategoryIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetShelfByOriginalCategoryID",
      requestType = shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest.class,
      responseType = shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest,
      shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> getGetShelfByOriginalCategoryIDMethod() {
    io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest, shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> getGetShelfByOriginalCategoryIDMethod;
    if ((getGetShelfByOriginalCategoryIDMethod = ShelfServiceGrpc.getGetShelfByOriginalCategoryIDMethod) == null) {
      synchronized (ShelfServiceGrpc.class) {
        if ((getGetShelfByOriginalCategoryIDMethod = ShelfServiceGrpc.getGetShelfByOriginalCategoryIDMethod) == null) {
          ShelfServiceGrpc.getGetShelfByOriginalCategoryIDMethod = getGetShelfByOriginalCategoryIDMethod =
              io.grpc.MethodDescriptor.<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest, shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetShelfByOriginalCategoryID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShelfServiceMethodDescriptorSupplier("GetShelfByOriginalCategoryID"))
              .build();
        }
      }
    }
    return getGetShelfByOriginalCategoryIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest,
      shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> getGetShelfByOriginalCategoryIDWithPaginationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetShelfByOriginalCategoryIDWithPagination",
      requestType = shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest.class,
      responseType = shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest,
      shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> getGetShelfByOriginalCategoryIDWithPaginationMethod() {
    io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest, shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> getGetShelfByOriginalCategoryIDWithPaginationMethod;
    if ((getGetShelfByOriginalCategoryIDWithPaginationMethod = ShelfServiceGrpc.getGetShelfByOriginalCategoryIDWithPaginationMethod) == null) {
      synchronized (ShelfServiceGrpc.class) {
        if ((getGetShelfByOriginalCategoryIDWithPaginationMethod = ShelfServiceGrpc.getGetShelfByOriginalCategoryIDWithPaginationMethod) == null) {
          ShelfServiceGrpc.getGetShelfByOriginalCategoryIDWithPaginationMethod = getGetShelfByOriginalCategoryIDWithPaginationMethod =
              io.grpc.MethodDescriptor.<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest, shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetShelfByOriginalCategoryIDWithPagination"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShelfServiceMethodDescriptorSupplier("GetShelfByOriginalCategoryIDWithPagination"))
              .build();
        }
      }
    }
    return getGetShelfByOriginalCategoryIDWithPaginationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest,
      shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> getGetShelfByCategoryPermalinkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetShelfByCategoryPermalink",
      requestType = shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest.class,
      responseType = shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest,
      shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> getGetShelfByCategoryPermalinkMethod() {
    io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest, shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> getGetShelfByCategoryPermalinkMethod;
    if ((getGetShelfByCategoryPermalinkMethod = ShelfServiceGrpc.getGetShelfByCategoryPermalinkMethod) == null) {
      synchronized (ShelfServiceGrpc.class) {
        if ((getGetShelfByCategoryPermalinkMethod = ShelfServiceGrpc.getGetShelfByCategoryPermalinkMethod) == null) {
          ShelfServiceGrpc.getGetShelfByCategoryPermalinkMethod = getGetShelfByCategoryPermalinkMethod =
              io.grpc.MethodDescriptor.<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest, shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetShelfByCategoryPermalink"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShelfServiceMethodDescriptorSupplier("GetShelfByCategoryPermalink"))
              .build();
        }
      }
    }
    return getGetShelfByCategoryPermalinkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest,
      shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> getGetShelfByCategoryPermalinkWithPaginationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetShelfByCategoryPermalinkWithPagination",
      requestType = shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest.class,
      responseType = shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest,
      shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> getGetShelfByCategoryPermalinkWithPaginationMethod() {
    io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest, shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> getGetShelfByCategoryPermalinkWithPaginationMethod;
    if ((getGetShelfByCategoryPermalinkWithPaginationMethod = ShelfServiceGrpc.getGetShelfByCategoryPermalinkWithPaginationMethod) == null) {
      synchronized (ShelfServiceGrpc.class) {
        if ((getGetShelfByCategoryPermalinkWithPaginationMethod = ShelfServiceGrpc.getGetShelfByCategoryPermalinkWithPaginationMethod) == null) {
          ShelfServiceGrpc.getGetShelfByCategoryPermalinkWithPaginationMethod = getGetShelfByCategoryPermalinkWithPaginationMethod =
              io.grpc.MethodDescriptor.<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest, shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetShelfByCategoryPermalinkWithPagination"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShelfServiceMethodDescriptorSupplier("GetShelfByCategoryPermalinkWithPagination"))
              .build();
        }
      }
    }
    return getGetShelfByCategoryPermalinkWithPaginationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shelf.ShelfOuterClass.Empty,
      shelf.ShelfOuterClass.Empty> getFlushCategoryFacetsCacheMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FlushCategoryFacetsCache",
      requestType = shelf.ShelfOuterClass.Empty.class,
      responseType = shelf.ShelfOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shelf.ShelfOuterClass.Empty,
      shelf.ShelfOuterClass.Empty> getFlushCategoryFacetsCacheMethod() {
    io.grpc.MethodDescriptor<shelf.ShelfOuterClass.Empty, shelf.ShelfOuterClass.Empty> getFlushCategoryFacetsCacheMethod;
    if ((getFlushCategoryFacetsCacheMethod = ShelfServiceGrpc.getFlushCategoryFacetsCacheMethod) == null) {
      synchronized (ShelfServiceGrpc.class) {
        if ((getFlushCategoryFacetsCacheMethod = ShelfServiceGrpc.getFlushCategoryFacetsCacheMethod) == null) {
          ShelfServiceGrpc.getFlushCategoryFacetsCacheMethod = getFlushCategoryFacetsCacheMethod =
              io.grpc.MethodDescriptor.<shelf.ShelfOuterClass.Empty, shelf.ShelfOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FlushCategoryFacetsCache"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ShelfServiceMethodDescriptorSupplier("FlushCategoryFacetsCache"))
              .build();
        }
      }
    }
    return getFlushCategoryFacetsCacheMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shelf.ShelfOuterClass.Empty,
      shelf.ShelfOuterClass.Empty> getUpdateCategoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateCategories",
      requestType = shelf.ShelfOuterClass.Empty.class,
      responseType = shelf.ShelfOuterClass.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shelf.ShelfOuterClass.Empty,
      shelf.ShelfOuterClass.Empty> getUpdateCategoriesMethod() {
    io.grpc.MethodDescriptor<shelf.ShelfOuterClass.Empty, shelf.ShelfOuterClass.Empty> getUpdateCategoriesMethod;
    if ((getUpdateCategoriesMethod = ShelfServiceGrpc.getUpdateCategoriesMethod) == null) {
      synchronized (ShelfServiceGrpc.class) {
        if ((getUpdateCategoriesMethod = ShelfServiceGrpc.getUpdateCategoriesMethod) == null) {
          ShelfServiceGrpc.getUpdateCategoriesMethod = getUpdateCategoriesMethod =
              io.grpc.MethodDescriptor.<shelf.ShelfOuterClass.Empty, shelf.ShelfOuterClass.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateCategories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ShelfServiceMethodDescriptorSupplier("UpdateCategories"))
              .build();
        }
      }
    }
    return getUpdateCategoriesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ShelfServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShelfServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShelfServiceStub>() {
        @java.lang.Override
        public ShelfServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShelfServiceStub(channel, callOptions);
        }
      };
    return ShelfServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ShelfServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShelfServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShelfServiceBlockingStub>() {
        @java.lang.Override
        public ShelfServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShelfServiceBlockingStub(channel, callOptions);
        }
      };
    return ShelfServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ShelfServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShelfServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShelfServiceFutureStub>() {
        @java.lang.Override
        public ShelfServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShelfServiceFutureStub(channel, callOptions);
        }
      };
    return ShelfServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-catalog-shelf.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog-shelf.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog-shelf.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-catalog-shelf:3009
   * description:
   *    Фронтовый сервис catalog shelf возвращающий полки.
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static abstract class ShelfServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     **
     * Возвращает полки по идентификатору монолитовской категории (mysql.taxons.id)
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    products_limit - кол-во продуктов в 1 полке
     * Примеры:
     *    Вернуть полки для рутовой категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     *    Вернуть полки для конкретной категории:
     *        grpcurl -d '{"original_category_id": "6245", "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     *    Вернуть полки для конкретной категории с атрибутам товаров image, brand:
     *        grpcurl -d '{"original_category_id": "6245", "display_attributes": [{"keys":["brand", "image"]}], "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     * </pre>
     */
    public void getShelfByOriginalCategoryID(shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetShelfByOriginalCategoryIDMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает полки по идентификатору монолитовской категории (mysql.taxons.id) с пагинацией
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    products_limit - кол-во продуктов в 1 полке
     *	  limit - кол-во полок в ответе
     *	  offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть полки для рутовой категории с пигинацией:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     *    Вернуть полки для конкретной категории с пигинацией:
     *        grpcurl -d '{"original_category_id": "6245", "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     *    Вернуть полки для конкретной категории с атрибутам товаров image, brand с пигинацией:
     *        grpcurl -d '{"original_category_id": "6245", "display_attributes": [{"keys":["brand", "image"]}], "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     * </pre>
     */
    public void getShelfByOriginalCategoryIDWithPagination(shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetShelfByOriginalCategoryIDWithPaginationMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Аналог GetShelfByOriginalCategoryID, но в качестве идентификатора категории принимает permalink ктаегории
     * </pre>
     */
    public void getShelfByCategoryPermalink(shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetShelfByCategoryPermalinkMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Аналог GetShelfByOriginalCategoryIDWithPagination, но в качестве идентификатора категории принимает permalink ктаегории
     * </pre>
     */
    public void getShelfByCategoryPermalinkWithPagination(shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetShelfByCategoryPermalinkWithPaginationMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * PRIVATE:Удаление кеша от product-filter
     * Примеры:
     *    Удалить кеш с пода:
     *        grpcurl -d '{}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.FlushCategoryFacetsCache
     * </pre>
     */
    public void flushCategoryFacetsCache(shelf.ShelfOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFlushCategoryFacetsCacheMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * PRIVATE:Форсированный импорт категорий из product-hub
     * Примеры:
     *    Обновить дерево в поде:
     *        grpcurl -d '{}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.UpdateCategories
     * </pre>
     */
    public void updateCategories(shelf.ShelfOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateCategoriesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetShelfByOriginalCategoryIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest,
                shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse>(
                  this, METHODID_GET_SHELF_BY_ORIGINAL_CATEGORY_ID)))
          .addMethod(
            getGetShelfByOriginalCategoryIDWithPaginationMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest,
                shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse>(
                  this, METHODID_GET_SHELF_BY_ORIGINAL_CATEGORY_IDWITH_PAGINATION)))
          .addMethod(
            getGetShelfByCategoryPermalinkMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest,
                shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse>(
                  this, METHODID_GET_SHELF_BY_CATEGORY_PERMALINK)))
          .addMethod(
            getGetShelfByCategoryPermalinkWithPaginationMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest,
                shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse>(
                  this, METHODID_GET_SHELF_BY_CATEGORY_PERMALINK_WITH_PAGINATION)))
          .addMethod(
            getFlushCategoryFacetsCacheMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shelf.ShelfOuterClass.Empty,
                shelf.ShelfOuterClass.Empty>(
                  this, METHODID_FLUSH_CATEGORY_FACETS_CACHE)))
          .addMethod(
            getUpdateCategoriesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shelf.ShelfOuterClass.Empty,
                shelf.ShelfOuterClass.Empty>(
                  this, METHODID_UPDATE_CATEGORIES)))
          .build();
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-catalog-shelf.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog-shelf.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog-shelf.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-catalog-shelf:3009
   * description:
   *    Фронтовый сервис catalog shelf возвращающий полки.
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class ShelfServiceStub extends io.grpc.stub.AbstractAsyncStub<ShelfServiceStub> {
    private ShelfServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShelfServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShelfServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает полки по идентификатору монолитовской категории (mysql.taxons.id)
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    products_limit - кол-во продуктов в 1 полке
     * Примеры:
     *    Вернуть полки для рутовой категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     *    Вернуть полки для конкретной категории:
     *        grpcurl -d '{"original_category_id": "6245", "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     *    Вернуть полки для конкретной категории с атрибутам товаров image, brand:
     *        grpcurl -d '{"original_category_id": "6245", "display_attributes": [{"keys":["brand", "image"]}], "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     * </pre>
     */
    public void getShelfByOriginalCategoryID(shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetShelfByOriginalCategoryIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает полки по идентификатору монолитовской категории (mysql.taxons.id) с пагинацией
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    products_limit - кол-во продуктов в 1 полке
     *	  limit - кол-во полок в ответе
     *	  offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть полки для рутовой категории с пигинацией:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     *    Вернуть полки для конкретной категории с пигинацией:
     *        grpcurl -d '{"original_category_id": "6245", "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     *    Вернуть полки для конкретной категории с атрибутам товаров image, brand с пигинацией:
     *        grpcurl -d '{"original_category_id": "6245", "display_attributes": [{"keys":["brand", "image"]}], "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     * </pre>
     */
    public void getShelfByOriginalCategoryIDWithPagination(shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetShelfByOriginalCategoryIDWithPaginationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Аналог GetShelfByOriginalCategoryID, но в качестве идентификатора категории принимает permalink ктаегории
     * </pre>
     */
    public void getShelfByCategoryPermalink(shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetShelfByCategoryPermalinkMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Аналог GetShelfByOriginalCategoryIDWithPagination, но в качестве идентификатора категории принимает permalink ктаегории
     * </pre>
     */
    public void getShelfByCategoryPermalinkWithPagination(shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetShelfByCategoryPermalinkWithPaginationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * PRIVATE:Удаление кеша от product-filter
     * Примеры:
     *    Удалить кеш с пода:
     *        grpcurl -d '{}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.FlushCategoryFacetsCache
     * </pre>
     */
    public void flushCategoryFacetsCache(shelf.ShelfOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFlushCategoryFacetsCacheMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * PRIVATE:Форсированный импорт категорий из product-hub
     * Примеры:
     *    Обновить дерево в поде:
     *        grpcurl -d '{}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.UpdateCategories
     * </pre>
     */
    public void updateCategories(shelf.ShelfOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateCategoriesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-catalog-shelf.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog-shelf.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog-shelf.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-catalog-shelf:3009
   * description:
   *    Фронтовый сервис catalog shelf возвращающий полки.
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class ShelfServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ShelfServiceBlockingStub> {
    private ShelfServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShelfServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShelfServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает полки по идентификатору монолитовской категории (mysql.taxons.id)
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    products_limit - кол-во продуктов в 1 полке
     * Примеры:
     *    Вернуть полки для рутовой категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     *    Вернуть полки для конкретной категории:
     *        grpcurl -d '{"original_category_id": "6245", "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     *    Вернуть полки для конкретной категории с атрибутам товаров image, brand:
     *        grpcurl -d '{"original_category_id": "6245", "display_attributes": [{"keys":["brand", "image"]}], "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     * </pre>
     */
    public shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse getShelfByOriginalCategoryID(shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetShelfByOriginalCategoryIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает полки по идентификатору монолитовской категории (mysql.taxons.id) с пагинацией
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    products_limit - кол-во продуктов в 1 полке
     *	  limit - кол-во полок в ответе
     *	  offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть полки для рутовой категории с пигинацией:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     *    Вернуть полки для конкретной категории с пигинацией:
     *        grpcurl -d '{"original_category_id": "6245", "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     *    Вернуть полки для конкретной категории с атрибутам товаров image, brand с пигинацией:
     *        grpcurl -d '{"original_category_id": "6245", "display_attributes": [{"keys":["brand", "image"]}], "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     * </pre>
     */
    public shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse getShelfByOriginalCategoryIDWithPagination(shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetShelfByOriginalCategoryIDWithPaginationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Аналог GetShelfByOriginalCategoryID, но в качестве идентификатора категории принимает permalink ктаегории
     * </pre>
     */
    public shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse getShelfByCategoryPermalink(shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetShelfByCategoryPermalinkMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Аналог GetShelfByOriginalCategoryIDWithPagination, но в качестве идентификатора категории принимает permalink ктаегории
     * </pre>
     */
    public shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse getShelfByCategoryPermalinkWithPagination(shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetShelfByCategoryPermalinkWithPaginationMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * PRIVATE:Удаление кеша от product-filter
     * Примеры:
     *    Удалить кеш с пода:
     *        grpcurl -d '{}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.FlushCategoryFacetsCache
     * </pre>
     */
    public shelf.ShelfOuterClass.Empty flushCategoryFacetsCache(shelf.ShelfOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFlushCategoryFacetsCacheMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * PRIVATE:Форсированный импорт категорий из product-hub
     * Примеры:
     *    Обновить дерево в поде:
     *        grpcurl -d '{}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.UpdateCategories
     * </pre>
     */
    public shelf.ShelfOuterClass.Empty updateCategories(shelf.ShelfOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateCategoriesMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-catalog-shelf.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog-shelf.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog-shelf.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-catalog-shelf:3009
   * description:
   *    Фронтовый сервис catalog shelf возвращающий полки.
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class ShelfServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ShelfServiceFutureStub> {
    private ShelfServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShelfServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShelfServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает полки по идентификатору монолитовской категории (mysql.taxons.id)
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    products_limit - кол-во продуктов в 1 полке
     * Примеры:
     *    Вернуть полки для рутовой категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     *    Вернуть полки для конкретной категории:
     *        grpcurl -d '{"original_category_id": "6245", "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     *    Вернуть полки для конкретной категории с атрибутам товаров image, brand:
     *        grpcurl -d '{"original_category_id": "6245", "display_attributes": [{"keys":["brand", "image"]}], "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> getShelfByOriginalCategoryID(
        shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetShelfByOriginalCategoryIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает полки по идентификатору монолитовской категории (mysql.taxons.id) с пагинацией
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    products_limit - кол-во продуктов в 1 полке
     *	  limit - кол-во полок в ответе
     *	  offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть полки для рутовой категории с пигинацией:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     *    Вернуть полки для конкретной категории с пигинацией:
     *        grpcurl -d '{"original_category_id": "6245", "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     *    Вернуть полки для конкретной категории с атрибутам товаров image, brand с пигинацией:
     *        grpcurl -d '{"original_category_id": "6245", "display_attributes": [{"keys":["brand", "image"]}], "store_id": "1", "tenant_id": "sbermarket", "products_limit": 10, "limit": 2, "offset": 0}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.GetShelfByOriginalCategoryIDWithPagination
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse> getShelfByOriginalCategoryIDWithPagination(
        shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetShelfByOriginalCategoryIDWithPaginationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Аналог GetShelfByOriginalCategoryID, но в качестве идентификатора категории принимает permalink ктаегории
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> getShelfByCategoryPermalink(
        shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetShelfByCategoryPermalinkMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Аналог GetShelfByOriginalCategoryIDWithPagination, но в качестве идентификатора категории принимает permalink ктаегории
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse> getShelfByCategoryPermalinkWithPagination(
        shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetShelfByCategoryPermalinkWithPaginationMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * PRIVATE:Удаление кеша от product-filter
     * Примеры:
     *    Удалить кеш с пода:
     *        grpcurl -d '{}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.FlushCategoryFacetsCache
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.Empty> flushCategoryFacetsCache(
        shelf.ShelfOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFlushCategoryFacetsCacheMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * PRIVATE:Форсированный импорт категорий из product-hub
     * Примеры:
     *    Обновить дерево в поде:
     *        grpcurl -d '{}' paas-content-catalog-shelf.sbmt.io:443 shelf.ShelfService.UpdateCategories
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.Empty> updateCategories(
        shelf.ShelfOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateCategoriesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SHELF_BY_ORIGINAL_CATEGORY_ID = 0;
  private static final int METHODID_GET_SHELF_BY_ORIGINAL_CATEGORY_IDWITH_PAGINATION = 1;
  private static final int METHODID_GET_SHELF_BY_CATEGORY_PERMALINK = 2;
  private static final int METHODID_GET_SHELF_BY_CATEGORY_PERMALINK_WITH_PAGINATION = 3;
  private static final int METHODID_FLUSH_CATEGORY_FACETS_CACHE = 4;
  private static final int METHODID_UPDATE_CATEGORIES = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ShelfServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ShelfServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SHELF_BY_ORIGINAL_CATEGORY_ID:
          serviceImpl.getShelfByOriginalCategoryID((shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDRequest) request,
              (io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse>) responseObserver);
          break;
        case METHODID_GET_SHELF_BY_ORIGINAL_CATEGORY_IDWITH_PAGINATION:
          serviceImpl.getShelfByOriginalCategoryIDWithPagination((shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDWithPaginationRequest) request,
              (io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByOriginalCategoryIDResponse>) responseObserver);
          break;
        case METHODID_GET_SHELF_BY_CATEGORY_PERMALINK:
          serviceImpl.getShelfByCategoryPermalink((shelf.ShelfOuterClass.GetShelfByCategoryPermalinkRequest) request,
              (io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse>) responseObserver);
          break;
        case METHODID_GET_SHELF_BY_CATEGORY_PERMALINK_WITH_PAGINATION:
          serviceImpl.getShelfByCategoryPermalinkWithPagination((shelf.ShelfOuterClass.GetShelfByCategoryPermalinkWithPaginationRequest) request,
              (io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryPermalinkResponse>) responseObserver);
          break;
        case METHODID_FLUSH_CATEGORY_FACETS_CACHE:
          serviceImpl.flushCategoryFacetsCache((shelf.ShelfOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty>) responseObserver);
          break;
        case METHODID_UPDATE_CATEGORIES:
          serviceImpl.updateCategories((shelf.ShelfOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty>) responseObserver);
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

  private static abstract class ShelfServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ShelfServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return shelf.ShelfOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ShelfService");
    }
  }

  private static final class ShelfServiceFileDescriptorSupplier
      extends ShelfServiceBaseDescriptorSupplier {
    ShelfServiceFileDescriptorSupplier() {}
  }

  private static final class ShelfServiceMethodDescriptorSupplier
      extends ShelfServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ShelfServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ShelfServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ShelfServiceFileDescriptorSupplier())
              .addMethod(getGetShelfByOriginalCategoryIDMethod())
              .addMethod(getGetShelfByOriginalCategoryIDWithPaginationMethod())
              .addMethod(getGetShelfByCategoryPermalinkMethod())
              .addMethod(getGetShelfByCategoryPermalinkWithPaginationMethod())
              .addMethod(getFlushCategoryFacetsCacheMethod())
              .addMethod(getUpdateCategoriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
