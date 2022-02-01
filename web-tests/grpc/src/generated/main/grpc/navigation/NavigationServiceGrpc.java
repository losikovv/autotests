package navigation;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 **
 * slack:
 *    #product-hub
 * swagger:
 *     https://paas-content-catalog-navigation.sbmt.io/api
 * public prod grpc uri:
 *    paas-content-catalog-navigation.sbmt.io:443
 * public stg grpc uri:
 *    paas-content-catalog-navigation.gw-stage.sbmt.io:443
 * kuber prod grpc uri:
 *    base-product-hub.paas-content-catalog-navigation:3009
 * description:
 *    Фронтовый сервис catalog navigation возвращающий меню, хлебные крошки.
 *    Предназначет для real-time нагрузки.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/catalog/navigation.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NavigationServiceGrpc {

  private NavigationServiceGrpc() {}

  public static final String SERVICE_NAME = "navigation.NavigationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<navigation.Navigation.GetMenuTreeRequest,
      navigation.Navigation.GetMenuTreeResponse> getGetMenuTreeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMenuTree",
      requestType = navigation.Navigation.GetMenuTreeRequest.class,
      responseType = navigation.Navigation.GetMenuTreeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<navigation.Navigation.GetMenuTreeRequest,
      navigation.Navigation.GetMenuTreeResponse> getGetMenuTreeMethod() {
    io.grpc.MethodDescriptor<navigation.Navigation.GetMenuTreeRequest, navigation.Navigation.GetMenuTreeResponse> getGetMenuTreeMethod;
    if ((getGetMenuTreeMethod = NavigationServiceGrpc.getGetMenuTreeMethod) == null) {
      synchronized (NavigationServiceGrpc.class) {
        if ((getGetMenuTreeMethod = NavigationServiceGrpc.getGetMenuTreeMethod) == null) {
          NavigationServiceGrpc.getGetMenuTreeMethod = getGetMenuTreeMethod =
              io.grpc.MethodDescriptor.<navigation.Navigation.GetMenuTreeRequest, navigation.Navigation.GetMenuTreeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMenuTree"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.GetMenuTreeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.GetMenuTreeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NavigationServiceMethodDescriptorSupplier("GetMenuTree"))
              .build();
        }
      }
    }
    return getGetMenuTreeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<navigation.Navigation.GetCategoryTreesRequest,
      navigation.Navigation.GetCategoryTreesResponse> getGetCategoryTreesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCategoryTrees",
      requestType = navigation.Navigation.GetCategoryTreesRequest.class,
      responseType = navigation.Navigation.GetCategoryTreesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<navigation.Navigation.GetCategoryTreesRequest,
      navigation.Navigation.GetCategoryTreesResponse> getGetCategoryTreesMethod() {
    io.grpc.MethodDescriptor<navigation.Navigation.GetCategoryTreesRequest, navigation.Navigation.GetCategoryTreesResponse> getGetCategoryTreesMethod;
    if ((getGetCategoryTreesMethod = NavigationServiceGrpc.getGetCategoryTreesMethod) == null) {
      synchronized (NavigationServiceGrpc.class) {
        if ((getGetCategoryTreesMethod = NavigationServiceGrpc.getGetCategoryTreesMethod) == null) {
          NavigationServiceGrpc.getGetCategoryTreesMethod = getGetCategoryTreesMethod =
              io.grpc.MethodDescriptor.<navigation.Navigation.GetCategoryTreesRequest, navigation.Navigation.GetCategoryTreesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCategoryTrees"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.GetCategoryTreesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.GetCategoryTreesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NavigationServiceMethodDescriptorSupplier("GetCategoryTrees"))
              .build();
        }
      }
    }
    return getGetCategoryTreesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<navigation.Navigation.GetBreadcrumbsByCategoryIDRequest,
      navigation.Navigation.GetBreadcrumbsByCategoryIDResponse> getGetBreadcrumbsByCategoryIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBreadcrumbsByCategoryID",
      requestType = navigation.Navigation.GetBreadcrumbsByCategoryIDRequest.class,
      responseType = navigation.Navigation.GetBreadcrumbsByCategoryIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<navigation.Navigation.GetBreadcrumbsByCategoryIDRequest,
      navigation.Navigation.GetBreadcrumbsByCategoryIDResponse> getGetBreadcrumbsByCategoryIDMethod() {
    io.grpc.MethodDescriptor<navigation.Navigation.GetBreadcrumbsByCategoryIDRequest, navigation.Navigation.GetBreadcrumbsByCategoryIDResponse> getGetBreadcrumbsByCategoryIDMethod;
    if ((getGetBreadcrumbsByCategoryIDMethod = NavigationServiceGrpc.getGetBreadcrumbsByCategoryIDMethod) == null) {
      synchronized (NavigationServiceGrpc.class) {
        if ((getGetBreadcrumbsByCategoryIDMethod = NavigationServiceGrpc.getGetBreadcrumbsByCategoryIDMethod) == null) {
          NavigationServiceGrpc.getGetBreadcrumbsByCategoryIDMethod = getGetBreadcrumbsByCategoryIDMethod =
              io.grpc.MethodDescriptor.<navigation.Navigation.GetBreadcrumbsByCategoryIDRequest, navigation.Navigation.GetBreadcrumbsByCategoryIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBreadcrumbsByCategoryID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.GetBreadcrumbsByCategoryIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.GetBreadcrumbsByCategoryIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new NavigationServiceMethodDescriptorSupplier("GetBreadcrumbsByCategoryID"))
              .build();
        }
      }
    }
    return getGetBreadcrumbsByCategoryIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<navigation.Navigation.Empty,
      navigation.Navigation.Empty> getFlushCategoryFacetsCacheMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FlushCategoryFacetsCache",
      requestType = navigation.Navigation.Empty.class,
      responseType = navigation.Navigation.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<navigation.Navigation.Empty,
      navigation.Navigation.Empty> getFlushCategoryFacetsCacheMethod() {
    io.grpc.MethodDescriptor<navigation.Navigation.Empty, navigation.Navigation.Empty> getFlushCategoryFacetsCacheMethod;
    if ((getFlushCategoryFacetsCacheMethod = NavigationServiceGrpc.getFlushCategoryFacetsCacheMethod) == null) {
      synchronized (NavigationServiceGrpc.class) {
        if ((getFlushCategoryFacetsCacheMethod = NavigationServiceGrpc.getFlushCategoryFacetsCacheMethod) == null) {
          NavigationServiceGrpc.getFlushCategoryFacetsCacheMethod = getFlushCategoryFacetsCacheMethod =
              io.grpc.MethodDescriptor.<navigation.Navigation.Empty, navigation.Navigation.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FlushCategoryFacetsCache"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new NavigationServiceMethodDescriptorSupplier("FlushCategoryFacetsCache"))
              .build();
        }
      }
    }
    return getFlushCategoryFacetsCacheMethod;
  }

  private static volatile io.grpc.MethodDescriptor<navigation.Navigation.Empty,
      navigation.Navigation.Empty> getUpdateCategoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateCategories",
      requestType = navigation.Navigation.Empty.class,
      responseType = navigation.Navigation.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<navigation.Navigation.Empty,
      navigation.Navigation.Empty> getUpdateCategoriesMethod() {
    io.grpc.MethodDescriptor<navigation.Navigation.Empty, navigation.Navigation.Empty> getUpdateCategoriesMethod;
    if ((getUpdateCategoriesMethod = NavigationServiceGrpc.getUpdateCategoriesMethod) == null) {
      synchronized (NavigationServiceGrpc.class) {
        if ((getUpdateCategoriesMethod = NavigationServiceGrpc.getUpdateCategoriesMethod) == null) {
          NavigationServiceGrpc.getUpdateCategoriesMethod = getUpdateCategoriesMethod =
              io.grpc.MethodDescriptor.<navigation.Navigation.Empty, navigation.Navigation.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateCategories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  navigation.Navigation.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new NavigationServiceMethodDescriptorSupplier("UpdateCategories"))
              .build();
        }
      }
    }
    return getUpdateCategoriesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NavigationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NavigationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NavigationServiceStub>() {
        @java.lang.Override
        public NavigationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NavigationServiceStub(channel, callOptions);
        }
      };
    return NavigationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NavigationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NavigationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NavigationServiceBlockingStub>() {
        @java.lang.Override
        public NavigationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NavigationServiceBlockingStub(channel, callOptions);
        }
      };
    return NavigationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NavigationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NavigationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NavigationServiceFutureStub>() {
        @java.lang.Override
        public NavigationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NavigationServiceFutureStub(channel, callOptions);
        }
      };
    return NavigationServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-catalog-navigation.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog-navigation.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog-navigation.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-catalog-navigation:3009
   * description:
   *    Фронтовый сервис catalog navigation возвращающий меню, хлебные крошки.
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static abstract class NavigationServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     **
     * Возвращает дерево меню с категориями в которых есть в наличии товары
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    tree_depth - глубина возвращаемого дерева
     * Примеры:
     *    Вернуть меню со всеми атрибутами категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "tree_depth": 2}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetMenuTree
     *    Вернуть меню только с определенными атрибутами категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "tree_depth": 2, "category_data_keys": ["image"]}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetMenuTree
     * </pre>
     */
    public void getMenuTree(navigation.Navigation.GetMenuTreeRequest request,
        io.grpc.stub.StreamObserver<navigation.Navigation.GetMenuTreeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMenuTreeMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает дерево меню с категориями без учета наличия товаров
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Примеры:
     *    Вернуть дерево начиная с корня:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetCategoryTrees
     *    Вернуть дерево начиная с категории 28090000:
     *        grpcurl -d '{"category_id": "28090000"}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetCategoryTrees
     * </pre>
     */
    public void getCategoryTrees(navigation.Navigation.GetCategoryTreesRequest request,
        io.grpc.stub.StreamObserver<navigation.Navigation.GetCategoryTreesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCategoryTreesMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает хлебные крошки
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    category_id - идентификатор категории
     * Примеры:
     *    Вернуть крошки:
     *        grpcurl -d '{"category_id": "28090000"}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetBreadcrumbsByCategoryID
     * </pre>
     */
    public void getBreadcrumbsByCategoryID(navigation.Navigation.GetBreadcrumbsByCategoryIDRequest request,
        io.grpc.stub.StreamObserver<navigation.Navigation.GetBreadcrumbsByCategoryIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBreadcrumbsByCategoryIDMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * PRIVATE:Удаление кеша от product-filter
     * Примеры:
     *    Удалить кеш с пода:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.FlushCategoryFacetsCache
     * </pre>
     */
    public void flushCategoryFacetsCache(navigation.Navigation.Empty request,
        io.grpc.stub.StreamObserver<navigation.Navigation.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFlushCategoryFacetsCacheMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * PRIVATE:Форсированный импорт категорий из product-hub
     * Примеры:
     *    Обновить дерево в поде:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.UpdateCategories
     * </pre>
     */
    public void updateCategories(navigation.Navigation.Empty request,
        io.grpc.stub.StreamObserver<navigation.Navigation.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateCategoriesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMenuTreeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                navigation.Navigation.GetMenuTreeRequest,
                navigation.Navigation.GetMenuTreeResponse>(
                  this, METHODID_GET_MENU_TREE)))
          .addMethod(
            getGetCategoryTreesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                navigation.Navigation.GetCategoryTreesRequest,
                navigation.Navigation.GetCategoryTreesResponse>(
                  this, METHODID_GET_CATEGORY_TREES)))
          .addMethod(
            getGetBreadcrumbsByCategoryIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                navigation.Navigation.GetBreadcrumbsByCategoryIDRequest,
                navigation.Navigation.GetBreadcrumbsByCategoryIDResponse>(
                  this, METHODID_GET_BREADCRUMBS_BY_CATEGORY_ID)))
          .addMethod(
            getFlushCategoryFacetsCacheMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                navigation.Navigation.Empty,
                navigation.Navigation.Empty>(
                  this, METHODID_FLUSH_CATEGORY_FACETS_CACHE)))
          .addMethod(
            getUpdateCategoriesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                navigation.Navigation.Empty,
                navigation.Navigation.Empty>(
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
   *     https://paas-content-catalog-navigation.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog-navigation.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog-navigation.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-catalog-navigation:3009
   * description:
   *    Фронтовый сервис catalog navigation возвращающий меню, хлебные крошки.
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class NavigationServiceStub extends io.grpc.stub.AbstractAsyncStub<NavigationServiceStub> {
    private NavigationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NavigationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NavigationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает дерево меню с категориями в которых есть в наличии товары
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    tree_depth - глубина возвращаемого дерева
     * Примеры:
     *    Вернуть меню со всеми атрибутами категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "tree_depth": 2}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetMenuTree
     *    Вернуть меню только с определенными атрибутами категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "tree_depth": 2, "category_data_keys": ["image"]}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetMenuTree
     * </pre>
     */
    public void getMenuTree(navigation.Navigation.GetMenuTreeRequest request,
        io.grpc.stub.StreamObserver<navigation.Navigation.GetMenuTreeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMenuTreeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает дерево меню с категориями без учета наличия товаров
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Примеры:
     *    Вернуть дерево начиная с корня:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetCategoryTrees
     *    Вернуть дерево начиная с категории 28090000:
     *        grpcurl -d '{"category_id": "28090000"}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetCategoryTrees
     * </pre>
     */
    public void getCategoryTrees(navigation.Navigation.GetCategoryTreesRequest request,
        io.grpc.stub.StreamObserver<navigation.Navigation.GetCategoryTreesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCategoryTreesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает хлебные крошки
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    category_id - идентификатор категории
     * Примеры:
     *    Вернуть крошки:
     *        grpcurl -d '{"category_id": "28090000"}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetBreadcrumbsByCategoryID
     * </pre>
     */
    public void getBreadcrumbsByCategoryID(navigation.Navigation.GetBreadcrumbsByCategoryIDRequest request,
        io.grpc.stub.StreamObserver<navigation.Navigation.GetBreadcrumbsByCategoryIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBreadcrumbsByCategoryIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * PRIVATE:Удаление кеша от product-filter
     * Примеры:
     *    Удалить кеш с пода:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.FlushCategoryFacetsCache
     * </pre>
     */
    public void flushCategoryFacetsCache(navigation.Navigation.Empty request,
        io.grpc.stub.StreamObserver<navigation.Navigation.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFlushCategoryFacetsCacheMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * PRIVATE:Форсированный импорт категорий из product-hub
     * Примеры:
     *    Обновить дерево в поде:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.UpdateCategories
     * </pre>
     */
    public void updateCategories(navigation.Navigation.Empty request,
        io.grpc.stub.StreamObserver<navigation.Navigation.Empty> responseObserver) {
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
   *     https://paas-content-catalog-navigation.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog-navigation.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog-navigation.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-catalog-navigation:3009
   * description:
   *    Фронтовый сервис catalog navigation возвращающий меню, хлебные крошки.
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class NavigationServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<NavigationServiceBlockingStub> {
    private NavigationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NavigationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NavigationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает дерево меню с категориями в которых есть в наличии товары
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    tree_depth - глубина возвращаемого дерева
     * Примеры:
     *    Вернуть меню со всеми атрибутами категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "tree_depth": 2}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetMenuTree
     *    Вернуть меню только с определенными атрибутами категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "tree_depth": 2, "category_data_keys": ["image"]}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetMenuTree
     * </pre>
     */
    public navigation.Navigation.GetMenuTreeResponse getMenuTree(navigation.Navigation.GetMenuTreeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMenuTreeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает дерево меню с категориями без учета наличия товаров
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Примеры:
     *    Вернуть дерево начиная с корня:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetCategoryTrees
     *    Вернуть дерево начиная с категории 28090000:
     *        grpcurl -d '{"category_id": "28090000"}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetCategoryTrees
     * </pre>
     */
    public navigation.Navigation.GetCategoryTreesResponse getCategoryTrees(navigation.Navigation.GetCategoryTreesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCategoryTreesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает хлебные крошки
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    category_id - идентификатор категории
     * Примеры:
     *    Вернуть крошки:
     *        grpcurl -d '{"category_id": "28090000"}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetBreadcrumbsByCategoryID
     * </pre>
     */
    public navigation.Navigation.GetBreadcrumbsByCategoryIDResponse getBreadcrumbsByCategoryID(navigation.Navigation.GetBreadcrumbsByCategoryIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBreadcrumbsByCategoryIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * PRIVATE:Удаление кеша от product-filter
     * Примеры:
     *    Удалить кеш с пода:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.FlushCategoryFacetsCache
     * </pre>
     */
    public navigation.Navigation.Empty flushCategoryFacetsCache(navigation.Navigation.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFlushCategoryFacetsCacheMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * PRIVATE:Форсированный импорт категорий из product-hub
     * Примеры:
     *    Обновить дерево в поде:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.UpdateCategories
     * </pre>
     */
    public navigation.Navigation.Empty updateCategories(navigation.Navigation.Empty request) {
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
   *     https://paas-content-catalog-navigation.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-catalog-navigation.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-catalog-navigation.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-catalog-navigation:3009
   * description:
   *    Фронтовый сервис catalog navigation возвращающий меню, хлебные крошки.
   *    Предназначет для real-time нагрузки.
   * </pre>
   */
  public static final class NavigationServiceFutureStub extends io.grpc.stub.AbstractFutureStub<NavigationServiceFutureStub> {
    private NavigationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NavigationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NavigationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает дерево меню с категориями в которых есть в наличии товары
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     *    tree_depth - глубина возвращаемого дерева
     * Примеры:
     *    Вернуть меню со всеми атрибутами категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "tree_depth": 2}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetMenuTree
     *    Вернуть меню только с определенными атрибутами категории:
     *        grpcurl -d '{"store_id": "1", "tenant_id": "sbermarket", "tree_depth": 2, "category_data_keys": ["image"]}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetMenuTree
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<navigation.Navigation.GetMenuTreeResponse> getMenuTree(
        navigation.Navigation.GetMenuTreeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMenuTreeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает дерево меню с категориями без учета наличия товаров
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Примеры:
     *    Вернуть дерево начиная с корня:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetCategoryTrees
     *    Вернуть дерево начиная с категории 28090000:
     *        grpcurl -d '{"category_id": "28090000"}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetCategoryTrees
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<navigation.Navigation.GetCategoryTreesResponse> getCategoryTrees(
        navigation.Navigation.GetCategoryTreesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCategoryTreesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает хлебные крошки
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    category_id - идентификатор категории
     * Примеры:
     *    Вернуть крошки:
     *        grpcurl -d '{"category_id": "28090000"}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.GetBreadcrumbsByCategoryID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<navigation.Navigation.GetBreadcrumbsByCategoryIDResponse> getBreadcrumbsByCategoryID(
        navigation.Navigation.GetBreadcrumbsByCategoryIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBreadcrumbsByCategoryIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * PRIVATE:Удаление кеша от product-filter
     * Примеры:
     *    Удалить кеш с пода:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.FlushCategoryFacetsCache
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<navigation.Navigation.Empty> flushCategoryFacetsCache(
        navigation.Navigation.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFlushCategoryFacetsCacheMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * PRIVATE:Форсированный импорт категорий из product-hub
     * Примеры:
     *    Обновить дерево в поде:
     *        grpcurl -d '{}' paas-content-catalog-navigation.sbmt.io:443 navigation.NavigationService.UpdateCategories
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<navigation.Navigation.Empty> updateCategories(
        navigation.Navigation.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateCategoriesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_MENU_TREE = 0;
  private static final int METHODID_GET_CATEGORY_TREES = 1;
  private static final int METHODID_GET_BREADCRUMBS_BY_CATEGORY_ID = 2;
  private static final int METHODID_FLUSH_CATEGORY_FACETS_CACHE = 3;
  private static final int METHODID_UPDATE_CATEGORIES = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final NavigationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(NavigationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_MENU_TREE:
          serviceImpl.getMenuTree((navigation.Navigation.GetMenuTreeRequest) request,
              (io.grpc.stub.StreamObserver<navigation.Navigation.GetMenuTreeResponse>) responseObserver);
          break;
        case METHODID_GET_CATEGORY_TREES:
          serviceImpl.getCategoryTrees((navigation.Navigation.GetCategoryTreesRequest) request,
              (io.grpc.stub.StreamObserver<navigation.Navigation.GetCategoryTreesResponse>) responseObserver);
          break;
        case METHODID_GET_BREADCRUMBS_BY_CATEGORY_ID:
          serviceImpl.getBreadcrumbsByCategoryID((navigation.Navigation.GetBreadcrumbsByCategoryIDRequest) request,
              (io.grpc.stub.StreamObserver<navigation.Navigation.GetBreadcrumbsByCategoryIDResponse>) responseObserver);
          break;
        case METHODID_FLUSH_CATEGORY_FACETS_CACHE:
          serviceImpl.flushCategoryFacetsCache((navigation.Navigation.Empty) request,
              (io.grpc.stub.StreamObserver<navigation.Navigation.Empty>) responseObserver);
          break;
        case METHODID_UPDATE_CATEGORIES:
          serviceImpl.updateCategories((navigation.Navigation.Empty) request,
              (io.grpc.stub.StreamObserver<navigation.Navigation.Empty>) responseObserver);
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

  private static abstract class NavigationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    NavigationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return navigation.Navigation.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("NavigationService");
    }
  }

  private static final class NavigationServiceFileDescriptorSupplier
      extends NavigationServiceBaseDescriptorSupplier {
    NavigationServiceFileDescriptorSupplier() {}
  }

  private static final class NavigationServiceMethodDescriptorSupplier
      extends NavigationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    NavigationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (NavigationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new NavigationServiceFileDescriptorSupplier())
              .addMethod(getGetMenuTreeMethod())
              .addMethod(getGetCategoryTreesMethod())
              .addMethod(getGetBreadcrumbsByCategoryIDMethod())
              .addMethod(getFlushCategoryFacetsCacheMethod())
              .addMethod(getUpdateCategoriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
