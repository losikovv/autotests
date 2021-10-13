package product_filter;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/product_filter/product-filter.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductFilterGrpc {

  private ProductFilterGrpc() {}

  public static final String SERVICE_NAME = "product_filter.ProductFilter";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest,
      product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse> getGetCategoryFacetsByCategoryIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCategoryFacetsByCategoryIDs",
      requestType = product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest.class,
      responseType = product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest,
      product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse> getGetCategoryFacetsByCategoryIDsMethod() {
    io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest, product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse> getGetCategoryFacetsByCategoryIDsMethod;
    if ((getGetCategoryFacetsByCategoryIDsMethod = ProductFilterGrpc.getGetCategoryFacetsByCategoryIDsMethod) == null) {
      synchronized (ProductFilterGrpc.class) {
        if ((getGetCategoryFacetsByCategoryIDsMethod = ProductFilterGrpc.getGetCategoryFacetsByCategoryIDsMethod) == null) {
          ProductFilterGrpc.getGetCategoryFacetsByCategoryIDsMethod = getGetCategoryFacetsByCategoryIDsMethod =
              io.grpc.MethodDescriptor.<product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest, product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCategoryFacetsByCategoryIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductFilterMethodDescriptorSupplier("GetCategoryFacetsByCategoryIDs"))
              .build();
        }
      }
    }
    return getGetCategoryFacetsByCategoryIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest,
      product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse> getGetPopularProductsSKUByCategoryIDsBatchesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPopularProductsSKUByCategoryIDsBatches",
      requestType = product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest.class,
      responseType = product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest,
      product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse> getGetPopularProductsSKUByCategoryIDsBatchesMethod() {
    io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest, product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse> getGetPopularProductsSKUByCategoryIDsBatchesMethod;
    if ((getGetPopularProductsSKUByCategoryIDsBatchesMethod = ProductFilterGrpc.getGetPopularProductsSKUByCategoryIDsBatchesMethod) == null) {
      synchronized (ProductFilterGrpc.class) {
        if ((getGetPopularProductsSKUByCategoryIDsBatchesMethod = ProductFilterGrpc.getGetPopularProductsSKUByCategoryIDsBatchesMethod) == null) {
          ProductFilterGrpc.getGetPopularProductsSKUByCategoryIDsBatchesMethod = getGetPopularProductsSKUByCategoryIDsBatchesMethod =
              io.grpc.MethodDescriptor.<product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest, product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPopularProductsSKUByCategoryIDsBatches"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductFilterMethodDescriptorSupplier("GetPopularProductsSKUByCategoryIDsBatches"))
              .build();
        }
      }
    }
    return getGetPopularProductsSKUByCategoryIDsBatchesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest,
      product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse> getGetProductsSKUByAttributesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsSKUByAttributes",
      requestType = product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest.class,
      responseType = product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest,
      product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse> getGetProductsSKUByAttributesMethod() {
    io.grpc.MethodDescriptor<product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest, product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse> getGetProductsSKUByAttributesMethod;
    if ((getGetProductsSKUByAttributesMethod = ProductFilterGrpc.getGetProductsSKUByAttributesMethod) == null) {
      synchronized (ProductFilterGrpc.class) {
        if ((getGetProductsSKUByAttributesMethod = ProductFilterGrpc.getGetProductsSKUByAttributesMethod) == null) {
          ProductFilterGrpc.getGetProductsSKUByAttributesMethod = getGetProductsSKUByAttributesMethod =
              io.grpc.MethodDescriptor.<product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest, product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsSKUByAttributes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductFilterMethodDescriptorSupplier("GetProductsSKUByAttributes"))
              .build();
        }
      }
    }
    return getGetProductsSKUByAttributesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductFilterStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductFilterStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductFilterStub>() {
        @java.lang.Override
        public ProductFilterStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductFilterStub(channel, callOptions);
        }
      };
    return ProductFilterStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductFilterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductFilterBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductFilterBlockingStub>() {
        @java.lang.Override
        public ProductFilterBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductFilterBlockingStub(channel, callOptions);
        }
      };
    return ProductFilterBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductFilterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductFilterFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductFilterFutureStub>() {
        @java.lang.Override
        public ProductFilterFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductFilterFutureStub(channel, callOptions);
        }
      };
    return ProductFilterFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ProductFilterImplBase implements io.grpc.BindableService {

    /**
     */
    public void getCategoryFacetsByCategoryIDs(product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCategoryFacetsByCategoryIDsMethod(), responseObserver);
    }

    /**
     */
    public void getPopularProductsSKUByCategoryIDsBatches(product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest request,
        io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPopularProductsSKUByCategoryIDsBatchesMethod(), responseObserver);
    }

    /**
     */
    public void getProductsSKUByAttributes(product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest request,
        io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsSKUByAttributesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetCategoryFacetsByCategoryIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest,
                product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse>(
                  this, METHODID_GET_CATEGORY_FACETS_BY_CATEGORY_IDS)))
          .addMethod(
            getGetPopularProductsSKUByCategoryIDsBatchesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest,
                product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse>(
                  this, METHODID_GET_POPULAR_PRODUCTS_SKUBY_CATEGORY_IDS_BATCHES)))
          .addMethod(
            getGetProductsSKUByAttributesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest,
                product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse>(
                  this, METHODID_GET_PRODUCTS_SKUBY_ATTRIBUTES)))
          .build();
    }
  }

  /**
   */
  public static final class ProductFilterStub extends io.grpc.stub.AbstractAsyncStub<ProductFilterStub> {
    private ProductFilterStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductFilterStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductFilterStub(channel, callOptions);
    }

    /**
     */
    public void getCategoryFacetsByCategoryIDs(product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCategoryFacetsByCategoryIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPopularProductsSKUByCategoryIDsBatches(product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest request,
        io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPopularProductsSKUByCategoryIDsBatchesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProductsSKUByAttributes(product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest request,
        io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsSKUByAttributesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProductFilterBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductFilterBlockingStub> {
    private ProductFilterBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductFilterBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductFilterBlockingStub(channel, callOptions);
    }

    /**
     */
    public product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse getCategoryFacetsByCategoryIDs(product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCategoryFacetsByCategoryIDsMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse getPopularProductsSKUByCategoryIDsBatches(product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPopularProductsSKUByCategoryIDsBatchesMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse getProductsSKUByAttributes(product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsSKUByAttributesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProductFilterFutureStub extends io.grpc.stub.AbstractFutureStub<ProductFilterFutureStub> {
    private ProductFilterFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductFilterFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductFilterFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse> getCategoryFacetsByCategoryIDs(
        product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCategoryFacetsByCategoryIDsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse> getPopularProductsSKUByCategoryIDsBatches(
        product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPopularProductsSKUByCategoryIDsBatchesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse> getProductsSKUByAttributes(
        product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsSKUByAttributesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CATEGORY_FACETS_BY_CATEGORY_IDS = 0;
  private static final int METHODID_GET_POPULAR_PRODUCTS_SKUBY_CATEGORY_IDS_BATCHES = 1;
  private static final int METHODID_GET_PRODUCTS_SKUBY_ATTRIBUTES = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductFilterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductFilterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CATEGORY_FACETS_BY_CATEGORY_IDS:
          serviceImpl.getCategoryFacetsByCategoryIDs((product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsRequest) request,
              (io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetCategoryFacetsByCategoryIDsResponse>) responseObserver);
          break;
        case METHODID_GET_POPULAR_PRODUCTS_SKUBY_CATEGORY_IDS_BATCHES:
          serviceImpl.getPopularProductsSKUByCategoryIDsBatches((product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesRequest) request,
              (io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetPopularProductsSKUByCategoryIDsBatchesResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_SKUBY_ATTRIBUTES:
          serviceImpl.getProductsSKUByAttributes((product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesRequest) request,
              (io.grpc.stub.StreamObserver<product_filter.ProductFilterOuterClass.GetProductsSKUByAttributesResponse>) responseObserver);
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

  private static abstract class ProductFilterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductFilterBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return product_filter.ProductFilterOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductFilter");
    }
  }

  private static final class ProductFilterFileDescriptorSupplier
      extends ProductFilterBaseDescriptorSupplier {
    ProductFilterFileDescriptorSupplier() {}
  }

  private static final class ProductFilterMethodDescriptorSupplier
      extends ProductFilterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductFilterMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProductFilterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductFilterFileDescriptorSupplier())
              .addMethod(getGetCategoryFacetsByCategoryIDsMethod())
              .addMethod(getGetPopularProductsSKUByCategoryIDsBatchesMethod())
              .addMethod(getGetProductsSKUByAttributesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
