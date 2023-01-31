package order_pricing_grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/pricing/pricing.v1.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PricingServiceV1Grpc {

  private PricingServiceV1Grpc() {}

  public static final String SERVICE_NAME = "order_pricing_grpc.PricingServiceV1";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest,
      order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse> getGetOfferOrderPriceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOfferOrderPrice",
      requestType = order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest.class,
      responseType = order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest,
      order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse> getGetOfferOrderPriceMethod() {
    io.grpc.MethodDescriptor<order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest, order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse> getGetOfferOrderPriceMethod;
    if ((getGetOfferOrderPriceMethod = PricingServiceV1Grpc.getGetOfferOrderPriceMethod) == null) {
      synchronized (PricingServiceV1Grpc.class) {
        if ((getGetOfferOrderPriceMethod = PricingServiceV1Grpc.getGetOfferOrderPriceMethod) == null) {
          PricingServiceV1Grpc.getGetOfferOrderPriceMethod = getGetOfferOrderPriceMethod =
              io.grpc.MethodDescriptor.<order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest, order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOfferOrderPrice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingServiceV1MethodDescriptorSupplier("GetOfferOrderPrice"))
              .build();
        }
      }
    }
    return getGetOfferOrderPriceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest,
      order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse> getGetFinalOrderPriceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetFinalOrderPrice",
      requestType = order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest.class,
      responseType = order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest,
      order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse> getGetFinalOrderPriceMethod() {
    io.grpc.MethodDescriptor<order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest, order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse> getGetFinalOrderPriceMethod;
    if ((getGetFinalOrderPriceMethod = PricingServiceV1Grpc.getGetFinalOrderPriceMethod) == null) {
      synchronized (PricingServiceV1Grpc.class) {
        if ((getGetFinalOrderPriceMethod = PricingServiceV1Grpc.getGetFinalOrderPriceMethod) == null) {
          PricingServiceV1Grpc.getGetFinalOrderPriceMethod = getGetFinalOrderPriceMethod =
              io.grpc.MethodDescriptor.<order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest, order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetFinalOrderPrice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingServiceV1MethodDescriptorSupplier("GetFinalOrderPrice"))
              .build();
        }
      }
    }
    return getGetFinalOrderPriceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PricingServiceV1Stub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingServiceV1Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingServiceV1Stub>() {
        @java.lang.Override
        public PricingServiceV1Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingServiceV1Stub(channel, callOptions);
        }
      };
    return PricingServiceV1Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PricingServiceV1BlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingServiceV1BlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingServiceV1BlockingStub>() {
        @java.lang.Override
        public PricingServiceV1BlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingServiceV1BlockingStub(channel, callOptions);
        }
      };
    return PricingServiceV1BlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PricingServiceV1FutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingServiceV1FutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingServiceV1FutureStub>() {
        @java.lang.Override
        public PricingServiceV1FutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingServiceV1FutureStub(channel, callOptions);
        }
      };
    return PricingServiceV1FutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PricingServiceV1ImplBase implements io.grpc.BindableService {

    /**
     */
    public void getOfferOrderPrice(order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest request,
        io.grpc.stub.StreamObserver<order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOfferOrderPriceMethod(), responseObserver);
    }

    /**
     */
    public void getFinalOrderPrice(order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest request,
        io.grpc.stub.StreamObserver<order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetFinalOrderPriceMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetOfferOrderPriceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest,
                order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse>(
                  this, METHODID_GET_OFFER_ORDER_PRICE)))
          .addMethod(
            getGetFinalOrderPriceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest,
                order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse>(
                  this, METHODID_GET_FINAL_ORDER_PRICE)))
          .build();
    }
  }

  /**
   */
  public static final class PricingServiceV1Stub extends io.grpc.stub.AbstractAsyncStub<PricingServiceV1Stub> {
    private PricingServiceV1Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingServiceV1Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingServiceV1Stub(channel, callOptions);
    }

    /**
     */
    public void getOfferOrderPrice(order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest request,
        io.grpc.stub.StreamObserver<order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOfferOrderPriceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFinalOrderPrice(order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest request,
        io.grpc.stub.StreamObserver<order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetFinalOrderPriceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PricingServiceV1BlockingStub extends io.grpc.stub.AbstractBlockingStub<PricingServiceV1BlockingStub> {
    private PricingServiceV1BlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingServiceV1BlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingServiceV1BlockingStub(channel, callOptions);
    }

    /**
     */
    public order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse getOfferOrderPrice(order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOfferOrderPriceMethod(), getCallOptions(), request);
    }

    /**
     */
    public order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse getFinalOrderPrice(order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetFinalOrderPriceMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PricingServiceV1FutureStub extends io.grpc.stub.AbstractFutureStub<PricingServiceV1FutureStub> {
    private PricingServiceV1FutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingServiceV1FutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingServiceV1FutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse> getOfferOrderPrice(
        order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOfferOrderPriceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse> getFinalOrderPrice(
        order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetFinalOrderPriceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_OFFER_ORDER_PRICE = 0;
  private static final int METHODID_GET_FINAL_ORDER_PRICE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PricingServiceV1ImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PricingServiceV1ImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_OFFER_ORDER_PRICE:
          serviceImpl.getOfferOrderPrice((order_pricing_grpc.PricingV1.GetOfferOrderPriceRequest) request,
              (io.grpc.stub.StreamObserver<order_pricing_grpc.PricingV1.GetOfferOrderPriceResponse>) responseObserver);
          break;
        case METHODID_GET_FINAL_ORDER_PRICE:
          serviceImpl.getFinalOrderPrice((order_pricing_grpc.PricingV1.GetFinalOrderPriceRequest) request,
              (io.grpc.stub.StreamObserver<order_pricing_grpc.PricingV1.GetFinalOrderPriceResponse>) responseObserver);
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

  private static abstract class PricingServiceV1BaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PricingServiceV1BaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return order_pricing_grpc.PricingV1.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PricingServiceV1");
    }
  }

  private static final class PricingServiceV1FileDescriptorSupplier
      extends PricingServiceV1BaseDescriptorSupplier {
    PricingServiceV1FileDescriptorSupplier() {}
  }

  private static final class PricingServiceV1MethodDescriptorSupplier
      extends PricingServiceV1BaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PricingServiceV1MethodDescriptorSupplier(String methodName) {
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
      synchronized (PricingServiceV1Grpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PricingServiceV1FileDescriptorSupplier())
              .addMethod(getGetOfferOrderPriceMethod())
              .addMethod(getGetFinalOrderPriceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
