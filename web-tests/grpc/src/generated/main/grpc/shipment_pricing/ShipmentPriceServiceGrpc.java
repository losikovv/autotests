package shipment_pricing;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/analytics/order_pricing.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ShipmentPriceServiceGrpc {

  private ShipmentPriceServiceGrpc() {}

  public static final String SERVICE_NAME = "shipment_pricing.ShipmentPriceService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.GetShipmentPriceRequest,
      shipment_pricing.OrderPricing.GetShipmentPriceResponse> getGetShipmentPriceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetShipmentPrice",
      requestType = shipment_pricing.OrderPricing.GetShipmentPriceRequest.class,
      responseType = shipment_pricing.OrderPricing.GetShipmentPriceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.GetShipmentPriceRequest,
      shipment_pricing.OrderPricing.GetShipmentPriceResponse> getGetShipmentPriceMethod() {
    io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.GetShipmentPriceRequest, shipment_pricing.OrderPricing.GetShipmentPriceResponse> getGetShipmentPriceMethod;
    if ((getGetShipmentPriceMethod = ShipmentPriceServiceGrpc.getGetShipmentPriceMethod) == null) {
      synchronized (ShipmentPriceServiceGrpc.class) {
        if ((getGetShipmentPriceMethod = ShipmentPriceServiceGrpc.getGetShipmentPriceMethod) == null) {
          ShipmentPriceServiceGrpc.getGetShipmentPriceMethod = getGetShipmentPriceMethod =
              io.grpc.MethodDescriptor.<shipment_pricing.OrderPricing.GetShipmentPriceRequest, shipment_pricing.OrderPricing.GetShipmentPriceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetShipmentPrice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shipment_pricing.OrderPricing.GetShipmentPriceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shipment_pricing.OrderPricing.GetShipmentPriceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShipmentPriceServiceMethodDescriptorSupplier("GetShipmentPrice"))
              .build();
        }
      }
    }
    return getGetShipmentPriceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.SetShipmentParamsRequest,
      shipment_pricing.OrderPricing.SetShipmentParamsResponse> getSetShipmentParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetShipmentParams",
      requestType = shipment_pricing.OrderPricing.SetShipmentParamsRequest.class,
      responseType = shipment_pricing.OrderPricing.SetShipmentParamsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.SetShipmentParamsRequest,
      shipment_pricing.OrderPricing.SetShipmentParamsResponse> getSetShipmentParamsMethod() {
    io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.SetShipmentParamsRequest, shipment_pricing.OrderPricing.SetShipmentParamsResponse> getSetShipmentParamsMethod;
    if ((getSetShipmentParamsMethod = ShipmentPriceServiceGrpc.getSetShipmentParamsMethod) == null) {
      synchronized (ShipmentPriceServiceGrpc.class) {
        if ((getSetShipmentParamsMethod = ShipmentPriceServiceGrpc.getSetShipmentParamsMethod) == null) {
          ShipmentPriceServiceGrpc.getSetShipmentParamsMethod = getSetShipmentParamsMethod =
              io.grpc.MethodDescriptor.<shipment_pricing.OrderPricing.SetShipmentParamsRequest, shipment_pricing.OrderPricing.SetShipmentParamsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetShipmentParams"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shipment_pricing.OrderPricing.SetShipmentParamsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shipment_pricing.OrderPricing.SetShipmentParamsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShipmentPriceServiceMethodDescriptorSupplier("SetShipmentParams"))
              .build();
        }
      }
    }
    return getSetShipmentParamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.GetShipmentParamsRequest,
      shipment_pricing.OrderPricing.GetShipmentParamsResponse> getGetShipmentParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetShipmentParams",
      requestType = shipment_pricing.OrderPricing.GetShipmentParamsRequest.class,
      responseType = shipment_pricing.OrderPricing.GetShipmentParamsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.GetShipmentParamsRequest,
      shipment_pricing.OrderPricing.GetShipmentParamsResponse> getGetShipmentParamsMethod() {
    io.grpc.MethodDescriptor<shipment_pricing.OrderPricing.GetShipmentParamsRequest, shipment_pricing.OrderPricing.GetShipmentParamsResponse> getGetShipmentParamsMethod;
    if ((getGetShipmentParamsMethod = ShipmentPriceServiceGrpc.getGetShipmentParamsMethod) == null) {
      synchronized (ShipmentPriceServiceGrpc.class) {
        if ((getGetShipmentParamsMethod = ShipmentPriceServiceGrpc.getGetShipmentParamsMethod) == null) {
          ShipmentPriceServiceGrpc.getGetShipmentParamsMethod = getGetShipmentParamsMethod =
              io.grpc.MethodDescriptor.<shipment_pricing.OrderPricing.GetShipmentParamsRequest, shipment_pricing.OrderPricing.GetShipmentParamsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetShipmentParams"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shipment_pricing.OrderPricing.GetShipmentParamsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shipment_pricing.OrderPricing.GetShipmentParamsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShipmentPriceServiceMethodDescriptorSupplier("GetShipmentParams"))
              .build();
        }
      }
    }
    return getGetShipmentParamsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ShipmentPriceServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShipmentPriceServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShipmentPriceServiceStub>() {
        @java.lang.Override
        public ShipmentPriceServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShipmentPriceServiceStub(channel, callOptions);
        }
      };
    return ShipmentPriceServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ShipmentPriceServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShipmentPriceServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShipmentPriceServiceBlockingStub>() {
        @java.lang.Override
        public ShipmentPriceServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShipmentPriceServiceBlockingStub(channel, callOptions);
        }
      };
    return ShipmentPriceServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ShipmentPriceServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShipmentPriceServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShipmentPriceServiceFutureStub>() {
        @java.lang.Override
        public ShipmentPriceServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShipmentPriceServiceFutureStub(channel, callOptions);
        }
      };
    return ShipmentPriceServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ShipmentPriceServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getShipmentPrice(shipment_pricing.OrderPricing.GetShipmentPriceRequest request,
        io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.GetShipmentPriceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetShipmentPriceMethod(), responseObserver);
    }

    /**
     */
    public void setShipmentParams(shipment_pricing.OrderPricing.SetShipmentParamsRequest request,
        io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.SetShipmentParamsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetShipmentParamsMethod(), responseObserver);
    }

    /**
     */
    public void getShipmentParams(shipment_pricing.OrderPricing.GetShipmentParamsRequest request,
        io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.GetShipmentParamsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetShipmentParamsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetShipmentPriceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shipment_pricing.OrderPricing.GetShipmentPriceRequest,
                shipment_pricing.OrderPricing.GetShipmentPriceResponse>(
                  this, METHODID_GET_SHIPMENT_PRICE)))
          .addMethod(
            getSetShipmentParamsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shipment_pricing.OrderPricing.SetShipmentParamsRequest,
                shipment_pricing.OrderPricing.SetShipmentParamsResponse>(
                  this, METHODID_SET_SHIPMENT_PARAMS)))
          .addMethod(
            getGetShipmentParamsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shipment_pricing.OrderPricing.GetShipmentParamsRequest,
                shipment_pricing.OrderPricing.GetShipmentParamsResponse>(
                  this, METHODID_GET_SHIPMENT_PARAMS)))
          .build();
    }
  }

  /**
   */
  public static final class ShipmentPriceServiceStub extends io.grpc.stub.AbstractAsyncStub<ShipmentPriceServiceStub> {
    private ShipmentPriceServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShipmentPriceServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShipmentPriceServiceStub(channel, callOptions);
    }

    /**
     */
    public void getShipmentPrice(shipment_pricing.OrderPricing.GetShipmentPriceRequest request,
        io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.GetShipmentPriceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetShipmentPriceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setShipmentParams(shipment_pricing.OrderPricing.SetShipmentParamsRequest request,
        io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.SetShipmentParamsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetShipmentParamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getShipmentParams(shipment_pricing.OrderPricing.GetShipmentParamsRequest request,
        io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.GetShipmentParamsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetShipmentParamsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ShipmentPriceServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ShipmentPriceServiceBlockingStub> {
    private ShipmentPriceServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShipmentPriceServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShipmentPriceServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public shipment_pricing.OrderPricing.GetShipmentPriceResponse getShipmentPrice(shipment_pricing.OrderPricing.GetShipmentPriceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetShipmentPriceMethod(), getCallOptions(), request);
    }

    /**
     */
    public shipment_pricing.OrderPricing.SetShipmentParamsResponse setShipmentParams(shipment_pricing.OrderPricing.SetShipmentParamsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetShipmentParamsMethod(), getCallOptions(), request);
    }

    /**
     */
    public shipment_pricing.OrderPricing.GetShipmentParamsResponse getShipmentParams(shipment_pricing.OrderPricing.GetShipmentParamsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetShipmentParamsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ShipmentPriceServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ShipmentPriceServiceFutureStub> {
    private ShipmentPriceServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShipmentPriceServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShipmentPriceServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<shipment_pricing.OrderPricing.GetShipmentPriceResponse> getShipmentPrice(
        shipment_pricing.OrderPricing.GetShipmentPriceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetShipmentPriceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<shipment_pricing.OrderPricing.SetShipmentParamsResponse> setShipmentParams(
        shipment_pricing.OrderPricing.SetShipmentParamsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetShipmentParamsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<shipment_pricing.OrderPricing.GetShipmentParamsResponse> getShipmentParams(
        shipment_pricing.OrderPricing.GetShipmentParamsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetShipmentParamsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SHIPMENT_PRICE = 0;
  private static final int METHODID_SET_SHIPMENT_PARAMS = 1;
  private static final int METHODID_GET_SHIPMENT_PARAMS = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ShipmentPriceServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ShipmentPriceServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SHIPMENT_PRICE:
          serviceImpl.getShipmentPrice((shipment_pricing.OrderPricing.GetShipmentPriceRequest) request,
              (io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.GetShipmentPriceResponse>) responseObserver);
          break;
        case METHODID_SET_SHIPMENT_PARAMS:
          serviceImpl.setShipmentParams((shipment_pricing.OrderPricing.SetShipmentParamsRequest) request,
              (io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.SetShipmentParamsResponse>) responseObserver);
          break;
        case METHODID_GET_SHIPMENT_PARAMS:
          serviceImpl.getShipmentParams((shipment_pricing.OrderPricing.GetShipmentParamsRequest) request,
              (io.grpc.stub.StreamObserver<shipment_pricing.OrderPricing.GetShipmentParamsResponse>) responseObserver);
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

  private static abstract class ShipmentPriceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ShipmentPriceServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return shipment_pricing.OrderPricing.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ShipmentPriceService");
    }
  }

  private static final class ShipmentPriceServiceFileDescriptorSupplier
      extends ShipmentPriceServiceBaseDescriptorSupplier {
    ShipmentPriceServiceFileDescriptorSupplier() {}
  }

  private static final class ShipmentPriceServiceMethodDescriptorSupplier
      extends ShipmentPriceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ShipmentPriceServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ShipmentPriceServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ShipmentPriceServiceFileDescriptorSupplier())
              .addMethod(getGetShipmentPriceMethod())
              .addMethod(getSetShipmentParamsMethod())
              .addMethod(getGetShipmentParamsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
