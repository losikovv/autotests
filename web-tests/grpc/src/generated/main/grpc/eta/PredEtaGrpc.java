package eta;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/operations/eta.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PredEtaGrpc {

  private PredEtaGrpc() {}

  public static final String SERVICE_NAME = "eta.PredEta";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<eta.Eta.StoreUserEtaRequest,
      eta.Eta.StoreUserEtaResponse> getGetStoreEtaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStoreEta",
      requestType = eta.Eta.StoreUserEtaRequest.class,
      responseType = eta.Eta.StoreUserEtaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<eta.Eta.StoreUserEtaRequest,
      eta.Eta.StoreUserEtaResponse> getGetStoreEtaMethod() {
    io.grpc.MethodDescriptor<eta.Eta.StoreUserEtaRequest, eta.Eta.StoreUserEtaResponse> getGetStoreEtaMethod;
    if ((getGetStoreEtaMethod = PredEtaGrpc.getGetStoreEtaMethod) == null) {
      synchronized (PredEtaGrpc.class) {
        if ((getGetStoreEtaMethod = PredEtaGrpc.getGetStoreEtaMethod) == null) {
          PredEtaGrpc.getGetStoreEtaMethod = getGetStoreEtaMethod =
              io.grpc.MethodDescriptor.<eta.Eta.StoreUserEtaRequest, eta.Eta.StoreUserEtaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStoreEta"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  eta.Eta.StoreUserEtaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  eta.Eta.StoreUserEtaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PredEtaMethodDescriptorSupplier("GetStoreEta"))
              .build();
        }
      }
    }
    return getGetStoreEtaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<eta.Eta.UserEtaRequest,
      eta.Eta.UserEtaResponse> getGetBasketEtaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBasketEta",
      requestType = eta.Eta.UserEtaRequest.class,
      responseType = eta.Eta.UserEtaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<eta.Eta.UserEtaRequest,
      eta.Eta.UserEtaResponse> getGetBasketEtaMethod() {
    io.grpc.MethodDescriptor<eta.Eta.UserEtaRequest, eta.Eta.UserEtaResponse> getGetBasketEtaMethod;
    if ((getGetBasketEtaMethod = PredEtaGrpc.getGetBasketEtaMethod) == null) {
      synchronized (PredEtaGrpc.class) {
        if ((getGetBasketEtaMethod = PredEtaGrpc.getGetBasketEtaMethod) == null) {
          PredEtaGrpc.getGetBasketEtaMethod = getGetBasketEtaMethod =
              io.grpc.MethodDescriptor.<eta.Eta.UserEtaRequest, eta.Eta.UserEtaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBasketEta"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  eta.Eta.UserEtaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  eta.Eta.UserEtaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PredEtaMethodDescriptorSupplier("GetBasketEta"))
              .build();
        }
      }
    }
    return getGetBasketEtaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<eta.Eta.CourierEtaRequest,
      eta.Eta.CourierEtaResponse> getGetCourierEtaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCourierEta",
      requestType = eta.Eta.CourierEtaRequest.class,
      responseType = eta.Eta.CourierEtaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<eta.Eta.CourierEtaRequest,
      eta.Eta.CourierEtaResponse> getGetCourierEtaMethod() {
    io.grpc.MethodDescriptor<eta.Eta.CourierEtaRequest, eta.Eta.CourierEtaResponse> getGetCourierEtaMethod;
    if ((getGetCourierEtaMethod = PredEtaGrpc.getGetCourierEtaMethod) == null) {
      synchronized (PredEtaGrpc.class) {
        if ((getGetCourierEtaMethod = PredEtaGrpc.getGetCourierEtaMethod) == null) {
          PredEtaGrpc.getGetCourierEtaMethod = getGetCourierEtaMethod =
              io.grpc.MethodDescriptor.<eta.Eta.CourierEtaRequest, eta.Eta.CourierEtaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCourierEta"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  eta.Eta.CourierEtaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  eta.Eta.CourierEtaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PredEtaMethodDescriptorSupplier("GetCourierEta"))
              .build();
        }
      }
    }
    return getGetCourierEtaMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PredEtaStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PredEtaStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PredEtaStub>() {
        @java.lang.Override
        public PredEtaStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PredEtaStub(channel, callOptions);
        }
      };
    return PredEtaStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PredEtaBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PredEtaBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PredEtaBlockingStub>() {
        @java.lang.Override
        public PredEtaBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PredEtaBlockingStub(channel, callOptions);
        }
      };
    return PredEtaBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PredEtaFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PredEtaFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PredEtaFutureStub>() {
        @java.lang.Override
        public PredEtaFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PredEtaFutureStub(channel, callOptions);
        }
      };
    return PredEtaFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PredEtaImplBase implements io.grpc.BindableService {

    /**
     */
    public void getStoreEta(eta.Eta.StoreUserEtaRequest request,
        io.grpc.stub.StreamObserver<eta.Eta.StoreUserEtaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStoreEtaMethod(), responseObserver);
    }

    /**
     */
    public void getBasketEta(eta.Eta.UserEtaRequest request,
        io.grpc.stub.StreamObserver<eta.Eta.UserEtaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBasketEtaMethod(), responseObserver);
    }

    /**
     */
    public void getCourierEta(eta.Eta.CourierEtaRequest request,
        io.grpc.stub.StreamObserver<eta.Eta.CourierEtaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCourierEtaMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetStoreEtaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                eta.Eta.StoreUserEtaRequest,
                eta.Eta.StoreUserEtaResponse>(
                  this, METHODID_GET_STORE_ETA)))
          .addMethod(
            getGetBasketEtaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                eta.Eta.UserEtaRequest,
                eta.Eta.UserEtaResponse>(
                  this, METHODID_GET_BASKET_ETA)))
          .addMethod(
            getGetCourierEtaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                eta.Eta.CourierEtaRequest,
                eta.Eta.CourierEtaResponse>(
                  this, METHODID_GET_COURIER_ETA)))
          .build();
    }
  }

  /**
   */
  public static final class PredEtaStub extends io.grpc.stub.AbstractAsyncStub<PredEtaStub> {
    private PredEtaStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredEtaStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PredEtaStub(channel, callOptions);
    }

    /**
     */
    public void getStoreEta(eta.Eta.StoreUserEtaRequest request,
        io.grpc.stub.StreamObserver<eta.Eta.StoreUserEtaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStoreEtaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBasketEta(eta.Eta.UserEtaRequest request,
        io.grpc.stub.StreamObserver<eta.Eta.UserEtaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBasketEtaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCourierEta(eta.Eta.CourierEtaRequest request,
        io.grpc.stub.StreamObserver<eta.Eta.CourierEtaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCourierEtaMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PredEtaBlockingStub extends io.grpc.stub.AbstractBlockingStub<PredEtaBlockingStub> {
    private PredEtaBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredEtaBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PredEtaBlockingStub(channel, callOptions);
    }

    /**
     */
    public eta.Eta.StoreUserEtaResponse getStoreEta(eta.Eta.StoreUserEtaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStoreEtaMethod(), getCallOptions(), request);
    }

    /**
     */
    public eta.Eta.UserEtaResponse getBasketEta(eta.Eta.UserEtaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBasketEtaMethod(), getCallOptions(), request);
    }

    /**
     */
    public eta.Eta.CourierEtaResponse getCourierEta(eta.Eta.CourierEtaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCourierEtaMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PredEtaFutureStub extends io.grpc.stub.AbstractFutureStub<PredEtaFutureStub> {
    private PredEtaFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PredEtaFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PredEtaFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<eta.Eta.StoreUserEtaResponse> getStoreEta(
        eta.Eta.StoreUserEtaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStoreEtaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<eta.Eta.UserEtaResponse> getBasketEta(
        eta.Eta.UserEtaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBasketEtaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<eta.Eta.CourierEtaResponse> getCourierEta(
        eta.Eta.CourierEtaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCourierEtaMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_STORE_ETA = 0;
  private static final int METHODID_GET_BASKET_ETA = 1;
  private static final int METHODID_GET_COURIER_ETA = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PredEtaImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PredEtaImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STORE_ETA:
          serviceImpl.getStoreEta((eta.Eta.StoreUserEtaRequest) request,
              (io.grpc.stub.StreamObserver<eta.Eta.StoreUserEtaResponse>) responseObserver);
          break;
        case METHODID_GET_BASKET_ETA:
          serviceImpl.getBasketEta((eta.Eta.UserEtaRequest) request,
              (io.grpc.stub.StreamObserver<eta.Eta.UserEtaResponse>) responseObserver);
          break;
        case METHODID_GET_COURIER_ETA:
          serviceImpl.getCourierEta((eta.Eta.CourierEtaRequest) request,
              (io.grpc.stub.StreamObserver<eta.Eta.CourierEtaResponse>) responseObserver);
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

  private static abstract class PredEtaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PredEtaBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return eta.Eta.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PredEta");
    }
  }

  private static final class PredEtaFileDescriptorSupplier
      extends PredEtaBaseDescriptorSupplier {
    PredEtaFileDescriptorSupplier() {}
  }

  private static final class PredEtaMethodDescriptorSupplier
      extends PredEtaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PredEtaMethodDescriptorSupplier(String methodName) {
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
      synchronized (PredEtaGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PredEtaFileDescriptorSupplier())
              .addMethod(getGetStoreEtaMethod())
              .addMethod(getGetBasketEtaMethod())
              .addMethod(getGetCourierEtaMethod())
              .build();
        }
      }
    }
    return result;
  }
}
