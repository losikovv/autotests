package product_hub_back_stream;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
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
   */
  public static abstract class ProductHubBackStreamImplBase implements io.grpc.BindableService {

    /**
     */
    public void getProducts(product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsMethod(), responseObserver);
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
          .build();
    }
  }

  /**
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
     */
    public void getProducts(product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest request,
        io.grpc.stub.StreamObserver<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetProductsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
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
     */
    public java.util.Iterator<product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsResponse> getProducts(
        product_hub_back_stream.ProductHubBackStreamOuterClass.GetProductsRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetProductsMethod(), getCallOptions(), request);
    }
  }

  /**
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
              .build();
        }
      }
    }
    return result;
  }
}
