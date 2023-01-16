package product_filter_job;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/product_filter/product-filter-job.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductFilterJobGrpc {

  private ProductFilterJobGrpc() {}

  public static final String SERVICE_NAME = "product_filter_job.ProductFilterJob";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest,
      product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse> getUploadCheckTopicMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadCheckTopic",
      requestType = product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest.class,
      responseType = product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest,
      product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse> getUploadCheckTopicMethod() {
    io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest, product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse> getUploadCheckTopicMethod;
    if ((getUploadCheckTopicMethod = ProductFilterJobGrpc.getUploadCheckTopicMethod) == null) {
      synchronized (ProductFilterJobGrpc.class) {
        if ((getUploadCheckTopicMethod = ProductFilterJobGrpc.getUploadCheckTopicMethod) == null) {
          ProductFilterJobGrpc.getUploadCheckTopicMethod = getUploadCheckTopicMethod =
              io.grpc.MethodDescriptor.<product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest, product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadCheckTopic"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductFilterJobMethodDescriptorSupplier("UploadCheckTopic"))
              .build();
        }
      }
    }
    return getUploadCheckTopicMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest,
      product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse> getGetCacheValuesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCacheValues",
      requestType = product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest.class,
      responseType = product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest,
      product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse> getGetCacheValuesMethod() {
    io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest, product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse> getGetCacheValuesMethod;
    if ((getGetCacheValuesMethod = ProductFilterJobGrpc.getGetCacheValuesMethod) == null) {
      synchronized (ProductFilterJobGrpc.class) {
        if ((getGetCacheValuesMethod = ProductFilterJobGrpc.getGetCacheValuesMethod) == null) {
          ProductFilterJobGrpc.getGetCacheValuesMethod = getGetCacheValuesMethod =
              io.grpc.MethodDescriptor.<product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest, product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCacheValues"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductFilterJobMethodDescriptorSupplier("GetCacheValues"))
              .build();
        }
      }
    }
    return getGetCacheValuesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest,
      product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse> getGetIndexValuesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetIndexValues",
      requestType = product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest.class,
      responseType = product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest,
      product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse> getGetIndexValuesMethod() {
    io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest, product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse> getGetIndexValuesMethod;
    if ((getGetIndexValuesMethod = ProductFilterJobGrpc.getGetIndexValuesMethod) == null) {
      synchronized (ProductFilterJobGrpc.class) {
        if ((getGetIndexValuesMethod = ProductFilterJobGrpc.getGetIndexValuesMethod) == null) {
          ProductFilterJobGrpc.getGetIndexValuesMethod = getGetIndexValuesMethod =
              io.grpc.MethodDescriptor.<product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest, product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetIndexValues"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductFilterJobMethodDescriptorSupplier("GetIndexValues"))
              .build();
        }
      }
    }
    return getGetIndexValuesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest,
      product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse> getUploadIDsTopicMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadIDsTopic",
      requestType = product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest.class,
      responseType = product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest,
      product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse> getUploadIDsTopicMethod() {
    io.grpc.MethodDescriptor<product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest, product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse> getUploadIDsTopicMethod;
    if ((getUploadIDsTopicMethod = ProductFilterJobGrpc.getUploadIDsTopicMethod) == null) {
      synchronized (ProductFilterJobGrpc.class) {
        if ((getUploadIDsTopicMethod = ProductFilterJobGrpc.getUploadIDsTopicMethod) == null) {
          ProductFilterJobGrpc.getUploadIDsTopicMethod = getUploadIDsTopicMethod =
              io.grpc.MethodDescriptor.<product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest, product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UploadIDsTopic"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductFilterJobMethodDescriptorSupplier("UploadIDsTopic"))
              .build();
        }
      }
    }
    return getUploadIDsTopicMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductFilterJobStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductFilterJobStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductFilterJobStub>() {
        @java.lang.Override
        public ProductFilterJobStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductFilterJobStub(channel, callOptions);
        }
      };
    return ProductFilterJobStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductFilterJobBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductFilterJobBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductFilterJobBlockingStub>() {
        @java.lang.Override
        public ProductFilterJobBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductFilterJobBlockingStub(channel, callOptions);
        }
      };
    return ProductFilterJobBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductFilterJobFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductFilterJobFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductFilterJobFutureStub>() {
        @java.lang.Override
        public ProductFilterJobFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductFilterJobFutureStub(channel, callOptions);
        }
      };
    return ProductFilterJobFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ProductFilterJobImplBase implements io.grpc.BindableService {

    /**
     */
    public void uploadCheckTopic(product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest request,
        io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUploadCheckTopicMethod(), responseObserver);
    }

    /**
     */
    public void getCacheValues(product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest request,
        io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCacheValuesMethod(), responseObserver);
    }

    /**
     */
    public void getIndexValues(product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest request,
        io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetIndexValuesMethod(), responseObserver);
    }

    /**
     */
    public void uploadIDsTopic(product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest request,
        io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUploadIDsTopicMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadCheckTopicMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest,
                product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse>(
                  this, METHODID_UPLOAD_CHECK_TOPIC)))
          .addMethod(
            getGetCacheValuesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest,
                product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse>(
                  this, METHODID_GET_CACHE_VALUES)))
          .addMethod(
            getGetIndexValuesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest,
                product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse>(
                  this, METHODID_GET_INDEX_VALUES)))
          .addMethod(
            getUploadIDsTopicMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest,
                product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse>(
                  this, METHODID_UPLOAD_IDS_TOPIC)))
          .build();
    }
  }

  /**
   */
  public static final class ProductFilterJobStub extends io.grpc.stub.AbstractAsyncStub<ProductFilterJobStub> {
    private ProductFilterJobStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductFilterJobStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductFilterJobStub(channel, callOptions);
    }

    /**
     */
    public void uploadCheckTopic(product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest request,
        io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUploadCheckTopicMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCacheValues(product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest request,
        io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCacheValuesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getIndexValues(product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest request,
        io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetIndexValuesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void uploadIDsTopic(product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest request,
        io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUploadIDsTopicMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProductFilterJobBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductFilterJobBlockingStub> {
    private ProductFilterJobBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductFilterJobBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductFilterJobBlockingStub(channel, callOptions);
    }

    /**
     */
    public product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse uploadCheckTopic(product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUploadCheckTopicMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse getCacheValues(product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCacheValuesMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse getIndexValues(product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetIndexValuesMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse uploadIDsTopic(product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUploadIDsTopicMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProductFilterJobFutureStub extends io.grpc.stub.AbstractFutureStub<ProductFilterJobFutureStub> {
    private ProductFilterJobFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductFilterJobFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductFilterJobFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse> uploadCheckTopic(
        product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUploadCheckTopicMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse> getCacheValues(
        product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCacheValuesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse> getIndexValues(
        product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetIndexValuesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse> uploadIDsTopic(
        product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUploadIDsTopicMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UPLOAD_CHECK_TOPIC = 0;
  private static final int METHODID_GET_CACHE_VALUES = 1;
  private static final int METHODID_GET_INDEX_VALUES = 2;
  private static final int METHODID_UPLOAD_IDS_TOPIC = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductFilterJobImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductFilterJobImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_CHECK_TOPIC:
          serviceImpl.uploadCheckTopic((product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicRequest) request,
              (io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.UploadCheckTopicResponse>) responseObserver);
          break;
        case METHODID_GET_CACHE_VALUES:
          serviceImpl.getCacheValues((product_filter_job.ProductFilterJobOuterClass.GetCacheValuesRequest) request,
              (io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.GetCacheValuesResponse>) responseObserver);
          break;
        case METHODID_GET_INDEX_VALUES:
          serviceImpl.getIndexValues((product_filter_job.ProductFilterJobOuterClass.GetIndexValuesRequest) request,
              (io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.GetIndexValuesResponse>) responseObserver);
          break;
        case METHODID_UPLOAD_IDS_TOPIC:
          serviceImpl.uploadIDsTopic((product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicRequest) request,
              (io.grpc.stub.StreamObserver<product_filter_job.ProductFilterJobOuterClass.UploadIDsTopicResponse>) responseObserver);
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

  private static abstract class ProductFilterJobBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductFilterJobBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return product_filter_job.ProductFilterJobOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductFilterJob");
    }
  }

  private static final class ProductFilterJobFileDescriptorSupplier
      extends ProductFilterJobBaseDescriptorSupplier {
    ProductFilterJobFileDescriptorSupplier() {}
  }

  private static final class ProductFilterJobMethodDescriptorSupplier
      extends ProductFilterJobBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductFilterJobMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProductFilterJobGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductFilterJobFileDescriptorSupplier())
              .addMethod(getUploadCheckTopicMethod())
              .addMethod(getGetCacheValuesMethod())
              .addMethod(getGetIndexValuesMethod())
              .addMethod(getUploadIDsTopicMethod())
              .build();
        }
      }
    }
    return result;
  }
}
