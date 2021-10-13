package catalog;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/catalog/catalog.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CatalogServiceGrpc {

  private CatalogServiceGrpc() {}

  public static final String SERVICE_NAME = "catalog.CatalogService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<catalog.Catalog.GetProductsRequest,
      catalog.Catalog.GetProductsResponse> getGetProductsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProducts",
      requestType = catalog.Catalog.GetProductsRequest.class,
      responseType = catalog.Catalog.GetProductsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog.Catalog.GetProductsRequest,
      catalog.Catalog.GetProductsResponse> getGetProductsMethod() {
    io.grpc.MethodDescriptor<catalog.Catalog.GetProductsRequest, catalog.Catalog.GetProductsResponse> getGetProductsMethod;
    if ((getGetProductsMethod = CatalogServiceGrpc.getGetProductsMethod) == null) {
      synchronized (CatalogServiceGrpc.class) {
        if ((getGetProductsMethod = CatalogServiceGrpc.getGetProductsMethod) == null) {
          CatalogServiceGrpc.getGetProductsMethod = getGetProductsMethod =
              io.grpc.MethodDescriptor.<catalog.Catalog.GetProductsRequest, catalog.Catalog.GetProductsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProducts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog.Catalog.GetProductsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog.Catalog.GetProductsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogServiceMethodDescriptorSupplier("GetProducts"))
              .build();
        }
      }
    }
    return getGetProductsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<catalog.Catalog.Empty,
      catalog.Catalog.Empty> getFlushCategoryFacetsCacheMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FlushCategoryFacetsCache",
      requestType = catalog.Catalog.Empty.class,
      responseType = catalog.Catalog.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog.Catalog.Empty,
      catalog.Catalog.Empty> getFlushCategoryFacetsCacheMethod() {
    io.grpc.MethodDescriptor<catalog.Catalog.Empty, catalog.Catalog.Empty> getFlushCategoryFacetsCacheMethod;
    if ((getFlushCategoryFacetsCacheMethod = CatalogServiceGrpc.getFlushCategoryFacetsCacheMethod) == null) {
      synchronized (CatalogServiceGrpc.class) {
        if ((getFlushCategoryFacetsCacheMethod = CatalogServiceGrpc.getFlushCategoryFacetsCacheMethod) == null) {
          CatalogServiceGrpc.getFlushCategoryFacetsCacheMethod = getFlushCategoryFacetsCacheMethod =
              io.grpc.MethodDescriptor.<catalog.Catalog.Empty, catalog.Catalog.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FlushCategoryFacetsCache"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog.Catalog.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog.Catalog.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogServiceMethodDescriptorSupplier("FlushCategoryFacetsCache"))
              .build();
        }
      }
    }
    return getFlushCategoryFacetsCacheMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CatalogServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogServiceStub>() {
        @java.lang.Override
        public CatalogServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogServiceStub(channel, callOptions);
        }
      };
    return CatalogServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CatalogServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogServiceBlockingStub>() {
        @java.lang.Override
        public CatalogServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogServiceBlockingStub(channel, callOptions);
        }
      };
    return CatalogServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CatalogServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatalogServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatalogServiceFutureStub>() {
        @java.lang.Override
        public CatalogServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatalogServiceFutureStub(channel, callOptions);
        }
      };
    return CatalogServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CatalogServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getProducts(catalog.Catalog.GetProductsRequest request,
        io.grpc.stub.StreamObserver<catalog.Catalog.GetProductsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsMethod(), responseObserver);
    }

    /**
     */
    public void flushCategoryFacetsCache(catalog.Catalog.Empty request,
        io.grpc.stub.StreamObserver<catalog.Catalog.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFlushCategoryFacetsCacheMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetProductsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog.Catalog.GetProductsRequest,
                catalog.Catalog.GetProductsResponse>(
                  this, METHODID_GET_PRODUCTS)))
          .addMethod(
            getFlushCategoryFacetsCacheMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog.Catalog.Empty,
                catalog.Catalog.Empty>(
                  this, METHODID_FLUSH_CATEGORY_FACETS_CACHE)))
          .build();
    }
  }

  /**
   */
  public static final class CatalogServiceStub extends io.grpc.stub.AbstractAsyncStub<CatalogServiceStub> {
    private CatalogServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogServiceStub(channel, callOptions);
    }

    /**
     */
    public void getProducts(catalog.Catalog.GetProductsRequest request,
        io.grpc.stub.StreamObserver<catalog.Catalog.GetProductsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void flushCategoryFacetsCache(catalog.Catalog.Empty request,
        io.grpc.stub.StreamObserver<catalog.Catalog.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFlushCategoryFacetsCacheMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CatalogServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CatalogServiceBlockingStub> {
    private CatalogServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public catalog.Catalog.GetProductsResponse getProducts(catalog.Catalog.GetProductsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsMethod(), getCallOptions(), request);
    }

    /**
     */
    public catalog.Catalog.Empty flushCategoryFacetsCache(catalog.Catalog.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFlushCategoryFacetsCacheMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CatalogServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CatalogServiceFutureStub> {
    private CatalogServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatalogServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatalogServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog.Catalog.GetProductsResponse> getProducts(
        catalog.Catalog.GetProductsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog.Catalog.Empty> flushCategoryFacetsCache(
        catalog.Catalog.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFlushCategoryFacetsCacheMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCTS = 0;
  private static final int METHODID_FLUSH_CATEGORY_FACETS_CACHE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CatalogServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CatalogServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCTS:
          serviceImpl.getProducts((catalog.Catalog.GetProductsRequest) request,
              (io.grpc.stub.StreamObserver<catalog.Catalog.GetProductsResponse>) responseObserver);
          break;
        case METHODID_FLUSH_CATEGORY_FACETS_CACHE:
          serviceImpl.flushCategoryFacetsCache((catalog.Catalog.Empty) request,
              (io.grpc.stub.StreamObserver<catalog.Catalog.Empty>) responseObserver);
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

  private static abstract class CatalogServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CatalogServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return catalog.Catalog.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CatalogService");
    }
  }

  private static final class CatalogServiceFileDescriptorSupplier
      extends CatalogServiceBaseDescriptorSupplier {
    CatalogServiceFileDescriptorSupplier() {}
  }

  private static final class CatalogServiceMethodDescriptorSupplier
      extends CatalogServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CatalogServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CatalogServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CatalogServiceFileDescriptorSupplier())
              .addMethod(getGetProductsMethod())
              .addMethod(getFlushCategoryFacetsCacheMethod())
              .build();
        }
      }
    }
    return result;
  }
}
