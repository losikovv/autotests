package order_redispatch;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: on_demand/order_redispatch.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class OrderGrpc {

  private OrderGrpc() {}

  public static final String SERVICE_NAME = "order_redispatch.Order";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<order_redispatch.OrderRedispatch.RedispatchRequest,
      order_redispatch.OrderRedispatch.RedispatchReply> getRedispatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Redispatch",
      requestType = order_redispatch.OrderRedispatch.RedispatchRequest.class,
      responseType = order_redispatch.OrderRedispatch.RedispatchReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<order_redispatch.OrderRedispatch.RedispatchRequest,
      order_redispatch.OrderRedispatch.RedispatchReply> getRedispatchMethod() {
    io.grpc.MethodDescriptor<order_redispatch.OrderRedispatch.RedispatchRequest, order_redispatch.OrderRedispatch.RedispatchReply> getRedispatchMethod;
    if ((getRedispatchMethod = OrderGrpc.getRedispatchMethod) == null) {
      synchronized (OrderGrpc.class) {
        if ((getRedispatchMethod = OrderGrpc.getRedispatchMethod) == null) {
          OrderGrpc.getRedispatchMethod = getRedispatchMethod =
              io.grpc.MethodDescriptor.<order_redispatch.OrderRedispatch.RedispatchRequest, order_redispatch.OrderRedispatch.RedispatchReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Redispatch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  order_redispatch.OrderRedispatch.RedispatchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  order_redispatch.OrderRedispatch.RedispatchReply.getDefaultInstance()))
              .setSchemaDescriptor(new OrderMethodDescriptorSupplier("Redispatch"))
              .build();
        }
      }
    }
    return getRedispatchMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OrderStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderStub>() {
        @java.lang.Override
        public OrderStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderStub(channel, callOptions);
        }
      };
    return OrderStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OrderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderBlockingStub>() {
        @java.lang.Override
        public OrderBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderBlockingStub(channel, callOptions);
        }
      };
    return OrderBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OrderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<OrderFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<OrderFutureStub>() {
        @java.lang.Override
        public OrderFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new OrderFutureStub(channel, callOptions);
        }
      };
    return OrderFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class OrderImplBase implements io.grpc.BindableService {

    /**
     */
    public void redispatch(order_redispatch.OrderRedispatch.RedispatchRequest request,
        io.grpc.stub.StreamObserver<order_redispatch.OrderRedispatch.RedispatchReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRedispatchMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRedispatchMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                order_redispatch.OrderRedispatch.RedispatchRequest,
                order_redispatch.OrderRedispatch.RedispatchReply>(
                  this, METHODID_REDISPATCH)))
          .build();
    }
  }

  /**
   */
  public static final class OrderStub extends io.grpc.stub.AbstractAsyncStub<OrderStub> {
    private OrderStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderStub(channel, callOptions);
    }

    /**
     */
    public void redispatch(order_redispatch.OrderRedispatch.RedispatchRequest request,
        io.grpc.stub.StreamObserver<order_redispatch.OrderRedispatch.RedispatchReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRedispatchMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class OrderBlockingStub extends io.grpc.stub.AbstractBlockingStub<OrderBlockingStub> {
    private OrderBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderBlockingStub(channel, callOptions);
    }

    /**
     */
    public order_redispatch.OrderRedispatch.RedispatchReply redispatch(order_redispatch.OrderRedispatch.RedispatchRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRedispatchMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class OrderFutureStub extends io.grpc.stub.AbstractFutureStub<OrderFutureStub> {
    private OrderFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OrderFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new OrderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<order_redispatch.OrderRedispatch.RedispatchReply> redispatch(
        order_redispatch.OrderRedispatch.RedispatchRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRedispatchMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REDISPATCH = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OrderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OrderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REDISPATCH:
          serviceImpl.redispatch((order_redispatch.OrderRedispatch.RedispatchRequest) request,
              (io.grpc.stub.StreamObserver<order_redispatch.OrderRedispatch.RedispatchReply>) responseObserver);
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

  private static abstract class OrderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OrderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return order_redispatch.OrderRedispatch.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Order");
    }
  }

  private static final class OrderFileDescriptorSupplier
      extends OrderBaseDescriptorSupplier {
    OrderFileDescriptorSupplier() {}
  }

  private static final class OrderMethodDescriptorSupplier
      extends OrderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OrderMethodDescriptorSupplier(String methodName) {
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
      synchronized (OrderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OrderFileDescriptorSupplier())
              .addMethod(getRedispatchMethod())
              .build();
        }
      }
    }
    return result;
  }
}
