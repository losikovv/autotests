package estimator;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: estimator.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RouteEstimatorGrpc {

  private RouteEstimatorGrpc() {}

  public static final String SERVICE_NAME = "estimator.RouteEstimator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<estimator.Estimator.GetRouteEstimationRequest,
      estimator.Estimator.GetRouteEstimationResponse> getGetRouteEstimationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRouteEstimation",
      requestType = estimator.Estimator.GetRouteEstimationRequest.class,
      responseType = estimator.Estimator.GetRouteEstimationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<estimator.Estimator.GetRouteEstimationRequest,
      estimator.Estimator.GetRouteEstimationResponse> getGetRouteEstimationMethod() {
    io.grpc.MethodDescriptor<estimator.Estimator.GetRouteEstimationRequest, estimator.Estimator.GetRouteEstimationResponse> getGetRouteEstimationMethod;
    if ((getGetRouteEstimationMethod = RouteEstimatorGrpc.getGetRouteEstimationMethod) == null) {
      synchronized (RouteEstimatorGrpc.class) {
        if ((getGetRouteEstimationMethod = RouteEstimatorGrpc.getGetRouteEstimationMethod) == null) {
          RouteEstimatorGrpc.getGetRouteEstimationMethod = getGetRouteEstimationMethod =
              io.grpc.MethodDescriptor.<estimator.Estimator.GetRouteEstimationRequest, estimator.Estimator.GetRouteEstimationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRouteEstimation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  estimator.Estimator.GetRouteEstimationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  estimator.Estimator.GetRouteEstimationResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RouteEstimatorMethodDescriptorSupplier("GetRouteEstimation"))
              .build();
        }
      }
    }
    return getGetRouteEstimationMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RouteEstimatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RouteEstimatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RouteEstimatorStub>() {
        @java.lang.Override
        public RouteEstimatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RouteEstimatorStub(channel, callOptions);
        }
      };
    return RouteEstimatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RouteEstimatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RouteEstimatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RouteEstimatorBlockingStub>() {
        @java.lang.Override
        public RouteEstimatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RouteEstimatorBlockingStub(channel, callOptions);
        }
      };
    return RouteEstimatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RouteEstimatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RouteEstimatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RouteEstimatorFutureStub>() {
        @java.lang.Override
        public RouteEstimatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RouteEstimatorFutureStub(channel, callOptions);
        }
      };
    return RouteEstimatorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class RouteEstimatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void getRouteEstimation(estimator.Estimator.GetRouteEstimationRequest request,
        io.grpc.stub.StreamObserver<estimator.Estimator.GetRouteEstimationResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRouteEstimationMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetRouteEstimationMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                estimator.Estimator.GetRouteEstimationRequest,
                estimator.Estimator.GetRouteEstimationResponse>(
                  this, METHODID_GET_ROUTE_ESTIMATION)))
          .build();
    }
  }

  /**
   */
  public static final class RouteEstimatorStub extends io.grpc.stub.AbstractAsyncStub<RouteEstimatorStub> {
    private RouteEstimatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RouteEstimatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RouteEstimatorStub(channel, callOptions);
    }

    /**
     */
    public void getRouteEstimation(estimator.Estimator.GetRouteEstimationRequest request,
        io.grpc.stub.StreamObserver<estimator.Estimator.GetRouteEstimationResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRouteEstimationMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RouteEstimatorBlockingStub extends io.grpc.stub.AbstractBlockingStub<RouteEstimatorBlockingStub> {
    private RouteEstimatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RouteEstimatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RouteEstimatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public estimator.Estimator.GetRouteEstimationResponse getRouteEstimation(estimator.Estimator.GetRouteEstimationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRouteEstimationMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RouteEstimatorFutureStub extends io.grpc.stub.AbstractFutureStub<RouteEstimatorFutureStub> {
    private RouteEstimatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RouteEstimatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RouteEstimatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<estimator.Estimator.GetRouteEstimationResponse> getRouteEstimation(
        estimator.Estimator.GetRouteEstimationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRouteEstimationMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ROUTE_ESTIMATION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RouteEstimatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RouteEstimatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ROUTE_ESTIMATION:
          serviceImpl.getRouteEstimation((estimator.Estimator.GetRouteEstimationRequest) request,
              (io.grpc.stub.StreamObserver<estimator.Estimator.GetRouteEstimationResponse>) responseObserver);
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

  private static abstract class RouteEstimatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RouteEstimatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return estimator.Estimator.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RouteEstimator");
    }
  }

  private static final class RouteEstimatorFileDescriptorSupplier
      extends RouteEstimatorBaseDescriptorSupplier {
    RouteEstimatorFileDescriptorSupplier() {}
  }

  private static final class RouteEstimatorMethodDescriptorSupplier
      extends RouteEstimatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RouteEstimatorMethodDescriptorSupplier(String methodName) {
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
      synchronized (RouteEstimatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RouteEstimatorFileDescriptorSupplier())
              .addMethod(getGetRouteEstimationMethod())
              .build();
        }
      }
    }
    return result;
  }
}
