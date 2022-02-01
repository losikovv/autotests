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
  private static volatile io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductListRequestV3,
      catalog_api_v3.CatalogApiV3.GetProductListResponseV3> getGetProductListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductList",
      requestType = catalog_api_v3.CatalogApiV3.GetProductListRequestV3.class,
      responseType = catalog_api_v3.CatalogApiV3.GetProductListResponseV3.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductListRequestV3,
      catalog_api_v3.CatalogApiV3.GetProductListResponseV3> getGetProductListMethod() {
    io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductListRequestV3, catalog_api_v3.CatalogApiV3.GetProductListResponseV3> getGetProductListMethod;
    if ((getGetProductListMethod = CatalogAPIV3ServiceGrpc.getGetProductListMethod) == null) {
      synchronized (CatalogAPIV3ServiceGrpc.class) {
        if ((getGetProductListMethod = CatalogAPIV3ServiceGrpc.getGetProductListMethod) == null) {
          CatalogAPIV3ServiceGrpc.getGetProductListMethod = getGetProductListMethod =
              io.grpc.MethodDescriptor.<catalog_api_v3.CatalogApiV3.GetProductListRequestV3, catalog_api_v3.CatalogApiV3.GetProductListResponseV3>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetProductListRequestV3.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetProductListResponseV3.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogAPIV3ServiceMethodDescriptorSupplier("GetProductList"))
              .build();
        }
      }
    }
    return getGetProductListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductRequestV3,
      catalog_api_v3.CatalogApiV3.GetProductResponseV3> getGetProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProduct",
      requestType = catalog_api_v3.CatalogApiV3.GetProductRequestV3.class,
      responseType = catalog_api_v3.CatalogApiV3.GetProductResponseV3.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductRequestV3,
      catalog_api_v3.CatalogApiV3.GetProductResponseV3> getGetProductMethod() {
    io.grpc.MethodDescriptor<catalog_api_v3.CatalogApiV3.GetProductRequestV3, catalog_api_v3.CatalogApiV3.GetProductResponseV3> getGetProductMethod;
    if ((getGetProductMethod = CatalogAPIV3ServiceGrpc.getGetProductMethod) == null) {
      synchronized (CatalogAPIV3ServiceGrpc.class) {
        if ((getGetProductMethod = CatalogAPIV3ServiceGrpc.getGetProductMethod) == null) {
          CatalogAPIV3ServiceGrpc.getGetProductMethod = getGetProductMethod =
              io.grpc.MethodDescriptor.<catalog_api_v3.CatalogApiV3.GetProductRequestV3, catalog_api_v3.CatalogApiV3.GetProductResponseV3>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetProductRequestV3.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  catalog_api_v3.CatalogApiV3.GetProductResponseV3.getDefaultInstance()))
              .setSchemaDescriptor(new CatalogAPIV3ServiceMethodDescriptorSupplier("GetProduct"))
              .build();
        }
      }
    }
    return getGetProductMethod;
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
     */
    public void getProductList(catalog_api_v3.CatalogApiV3.GetProductListRequestV3 request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductListResponseV3> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductListMethod(), responseObserver);
    }

    /**
     */
    public void getProduct(catalog_api_v3.CatalogApiV3.GetProductRequestV3 request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductResponseV3> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetProductListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog_api_v3.CatalogApiV3.GetProductListRequestV3,
                catalog_api_v3.CatalogApiV3.GetProductListResponseV3>(
                  this, METHODID_GET_PRODUCT_LIST)))
          .addMethod(
            getGetProductMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                catalog_api_v3.CatalogApiV3.GetProductRequestV3,
                catalog_api_v3.CatalogApiV3.GetProductResponseV3>(
                  this, METHODID_GET_PRODUCT)))
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
     */
    public void getProductList(catalog_api_v3.CatalogApiV3.GetProductListRequestV3 request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductListResponseV3> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProduct(catalog_api_v3.CatalogApiV3.GetProductRequestV3 request,
        io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductResponseV3> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductMethod(), getCallOptions()), request, responseObserver);
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
     */
    public catalog_api_v3.CatalogApiV3.GetProductListResponseV3 getProductList(catalog_api_v3.CatalogApiV3.GetProductListRequestV3 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductListMethod(), getCallOptions(), request);
    }

    /**
     */
    public catalog_api_v3.CatalogApiV3.GetProductResponseV3 getProduct(catalog_api_v3.CatalogApiV3.GetProductRequestV3 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductMethod(), getCallOptions(), request);
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
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog_api_v3.CatalogApiV3.GetProductListResponseV3> getProductList(
        catalog_api_v3.CatalogApiV3.GetProductListRequestV3 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductListMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<catalog_api_v3.CatalogApiV3.GetProductResponseV3> getProduct(
        catalog_api_v3.CatalogApiV3.GetProductRequestV3 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCT_LIST = 0;
  private static final int METHODID_GET_PRODUCT = 1;

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
        case METHODID_GET_PRODUCT_LIST:
          serviceImpl.getProductList((catalog_api_v3.CatalogApiV3.GetProductListRequestV3) request,
              (io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductListResponseV3>) responseObserver);
          break;
        case METHODID_GET_PRODUCT:
          serviceImpl.getProduct((catalog_api_v3.CatalogApiV3.GetProductRequestV3) request,
              (io.grpc.stub.StreamObserver<catalog_api_v3.CatalogApiV3.GetProductResponseV3>) responseObserver);
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
              .addMethod(getGetProductListMethod())
              .addMethod(getGetProductMethod())
              .build();
        }
      }
    }
    return result;
  }
}
