package shelf;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/catalog/shelf.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ShelfServiceGrpc {

  private ShelfServiceGrpc() {}

  public static final String SERVICE_NAME = "shelf.ShelfService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryIDRequest,
      shelf.ShelfOuterClass.GetShelfByCategoryIDResponse> getGetShelfByCategoryIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetShelfByCategoryID",
      requestType = shelf.ShelfOuterClass.GetShelfByCategoryIDRequest.class,
      responseType = shelf.ShelfOuterClass.GetShelfByCategoryIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryIDRequest,
      shelf.ShelfOuterClass.GetShelfByCategoryIDResponse> getGetShelfByCategoryIDMethod() {
    io.grpc.MethodDescriptor<shelf.ShelfOuterClass.GetShelfByCategoryIDRequest, shelf.ShelfOuterClass.GetShelfByCategoryIDResponse> getGetShelfByCategoryIDMethod;
    if ((getGetShelfByCategoryIDMethod = ShelfServiceGrpc.getGetShelfByCategoryIDMethod) == null) {
      synchronized (ShelfServiceGrpc.class) {
        if ((getGetShelfByCategoryIDMethod = ShelfServiceGrpc.getGetShelfByCategoryIDMethod) == null) {
          ShelfServiceGrpc.getGetShelfByCategoryIDMethod = getGetShelfByCategoryIDMethod =
              io.grpc.MethodDescriptor.<shelf.ShelfOuterClass.GetShelfByCategoryIDRequest, shelf.ShelfOuterClass.GetShelfByCategoryIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetShelfByCategoryID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByCategoryIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shelf.ShelfOuterClass.GetShelfByCategoryIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShelfServiceMethodDescriptorSupplier("GetShelfByCategoryID"))
              .build();
        }
      }
    }
    return getGetShelfByCategoryIDMethod;
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
   */
  public static abstract class ShelfServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getShelfByCategoryID(shelf.ShelfOuterClass.GetShelfByCategoryIDRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetShelfByCategoryIDMethod(), responseObserver);
    }

    /**
     */
    public void flushCategoryFacetsCache(shelf.ShelfOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFlushCategoryFacetsCacheMethod(), responseObserver);
    }

    /**
     */
    public void updateCategories(shelf.ShelfOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateCategoriesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetShelfByCategoryIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shelf.ShelfOuterClass.GetShelfByCategoryIDRequest,
                shelf.ShelfOuterClass.GetShelfByCategoryIDResponse>(
                  this, METHODID_GET_SHELF_BY_CATEGORY_ID)))
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
     */
    public void getShelfByCategoryID(shelf.ShelfOuterClass.GetShelfByCategoryIDRequest request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetShelfByCategoryIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void flushCategoryFacetsCache(shelf.ShelfOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFlushCategoryFacetsCacheMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateCategories(shelf.ShelfOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateCategoriesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
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
     */
    public shelf.ShelfOuterClass.GetShelfByCategoryIDResponse getShelfByCategoryID(shelf.ShelfOuterClass.GetShelfByCategoryIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetShelfByCategoryIDMethod(), getCallOptions(), request);
    }

    /**
     */
    public shelf.ShelfOuterClass.Empty flushCategoryFacetsCache(shelf.ShelfOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFlushCategoryFacetsCacheMethod(), getCallOptions(), request);
    }

    /**
     */
    public shelf.ShelfOuterClass.Empty updateCategories(shelf.ShelfOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateCategoriesMethod(), getCallOptions(), request);
    }
  }

  /**
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
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.GetShelfByCategoryIDResponse> getShelfByCategoryID(
        shelf.ShelfOuterClass.GetShelfByCategoryIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetShelfByCategoryIDMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.Empty> flushCategoryFacetsCache(
        shelf.ShelfOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFlushCategoryFacetsCacheMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<shelf.ShelfOuterClass.Empty> updateCategories(
        shelf.ShelfOuterClass.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateCategoriesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SHELF_BY_CATEGORY_ID = 0;
  private static final int METHODID_FLUSH_CATEGORY_FACETS_CACHE = 1;
  private static final int METHODID_UPDATE_CATEGORIES = 2;

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
        case METHODID_GET_SHELF_BY_CATEGORY_ID:
          serviceImpl.getShelfByCategoryID((shelf.ShelfOuterClass.GetShelfByCategoryIDRequest) request,
              (io.grpc.stub.StreamObserver<shelf.ShelfOuterClass.GetShelfByCategoryIDResponse>) responseObserver);
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
              .addMethod(getGetShelfByCategoryIDMethod())
              .addMethod(getFlushCategoryFacetsCacheMethod())
              .addMethod(getUpdateCategoriesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
