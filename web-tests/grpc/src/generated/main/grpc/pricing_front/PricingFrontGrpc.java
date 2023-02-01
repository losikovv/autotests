package pricing_front;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/pricing_service/pricing-front.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PricingFrontGrpc {

  private PricingFrontGrpc() {}

  public static final String SERVICE_NAME = "pricing_front.PricingFront";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pricing_front.PricingFrontOuterClass.GetPricesBySKURequest,
      pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse> getGetPricesBySKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPricesBySKU",
      requestType = pricing_front.PricingFrontOuterClass.GetPricesBySKURequest.class,
      responseType = pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_front.PricingFrontOuterClass.GetPricesBySKURequest,
      pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse> getGetPricesBySKUMethod() {
    io.grpc.MethodDescriptor<pricing_front.PricingFrontOuterClass.GetPricesBySKURequest, pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse> getGetPricesBySKUMethod;
    if ((getGetPricesBySKUMethod = PricingFrontGrpc.getGetPricesBySKUMethod) == null) {
      synchronized (PricingFrontGrpc.class) {
        if ((getGetPricesBySKUMethod = PricingFrontGrpc.getGetPricesBySKUMethod) == null) {
          PricingFrontGrpc.getGetPricesBySKUMethod = getGetPricesBySKUMethod =
              io.grpc.MethodDescriptor.<pricing_front.PricingFrontOuterClass.GetPricesBySKURequest, pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPricesBySKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_front.PricingFrontOuterClass.GetPricesBySKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingFrontMethodDescriptorSupplier("GetPricesBySKU"))
              .build();
        }
      }
    }
    return getGetPricesBySKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest,
      pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse> getGetPricesByRetailerSKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPricesByRetailerSKU",
      requestType = pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest.class,
      responseType = pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest,
      pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse> getGetPricesByRetailerSKUMethod() {
    io.grpc.MethodDescriptor<pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest, pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse> getGetPricesByRetailerSKUMethod;
    if ((getGetPricesByRetailerSKUMethod = PricingFrontGrpc.getGetPricesByRetailerSKUMethod) == null) {
      synchronized (PricingFrontGrpc.class) {
        if ((getGetPricesByRetailerSKUMethod = PricingFrontGrpc.getGetPricesByRetailerSKUMethod) == null) {
          PricingFrontGrpc.getGetPricesByRetailerSKUMethod = getGetPricesByRetailerSKUMethod =
              io.grpc.MethodDescriptor.<pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest, pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPricesByRetailerSKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingFrontMethodDescriptorSupplier("GetPricesByRetailerSKU"))
              .build();
        }
      }
    }
    return getGetPricesByRetailerSKUMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PricingFrontStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingFrontStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingFrontStub>() {
        @java.lang.Override
        public PricingFrontStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingFrontStub(channel, callOptions);
        }
      };
    return PricingFrontStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PricingFrontBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingFrontBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingFrontBlockingStub>() {
        @java.lang.Override
        public PricingFrontBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingFrontBlockingStub(channel, callOptions);
        }
      };
    return PricingFrontBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PricingFrontFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingFrontFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingFrontFutureStub>() {
        @java.lang.Override
        public PricingFrontFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingFrontFutureStub(channel, callOptions);
        }
      };
    return PricingFrontFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PricingFrontImplBase implements io.grpc.BindableService {

    /**
     */
    public void getPricesBySKU(pricing_front.PricingFrontOuterClass.GetPricesBySKURequest request,
        io.grpc.stub.StreamObserver<pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPricesBySKUMethod(), responseObserver);
    }

    /**
     */
    public void getPricesByRetailerSKU(pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPricesByRetailerSKUMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetPricesBySKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_front.PricingFrontOuterClass.GetPricesBySKURequest,
                pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse>(
                  this, METHODID_GET_PRICES_BY_SKU)))
          .addMethod(
            getGetPricesByRetailerSKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest,
                pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse>(
                  this, METHODID_GET_PRICES_BY_RETAILER_SKU)))
          .build();
    }
  }

  /**
   */
  public static final class PricingFrontStub extends io.grpc.stub.AbstractAsyncStub<PricingFrontStub> {
    private PricingFrontStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingFrontStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingFrontStub(channel, callOptions);
    }

    /**
     */
    public void getPricesBySKU(pricing_front.PricingFrontOuterClass.GetPricesBySKURequest request,
        io.grpc.stub.StreamObserver<pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPricesBySKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getPricesByRetailerSKU(pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest request,
        io.grpc.stub.StreamObserver<pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPricesByRetailerSKUMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PricingFrontBlockingStub extends io.grpc.stub.AbstractBlockingStub<PricingFrontBlockingStub> {
    private PricingFrontBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingFrontBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingFrontBlockingStub(channel, callOptions);
    }

    /**
     */
    public pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse getPricesBySKU(pricing_front.PricingFrontOuterClass.GetPricesBySKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPricesBySKUMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse getPricesByRetailerSKU(pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPricesByRetailerSKUMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PricingFrontFutureStub extends io.grpc.stub.AbstractFutureStub<PricingFrontFutureStub> {
    private PricingFrontFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingFrontFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingFrontFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse> getPricesBySKU(
        pricing_front.PricingFrontOuterClass.GetPricesBySKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPricesBySKUMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse> getPricesByRetailerSKU(
        pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPricesByRetailerSKUMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRICES_BY_SKU = 0;
  private static final int METHODID_GET_PRICES_BY_RETAILER_SKU = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PricingFrontImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PricingFrontImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRICES_BY_SKU:
          serviceImpl.getPricesBySKU((pricing_front.PricingFrontOuterClass.GetPricesBySKURequest) request,
              (io.grpc.stub.StreamObserver<pricing_front.PricingFrontOuterClass.GetPricesBySKUResponse>) responseObserver);
          break;
        case METHODID_GET_PRICES_BY_RETAILER_SKU:
          serviceImpl.getPricesByRetailerSKU((pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKURequest) request,
              (io.grpc.stub.StreamObserver<pricing_front.PricingFrontOuterClass.GetPricesByRetailerSKUResponse>) responseObserver);
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

  private static abstract class PricingFrontBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PricingFrontBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pricing_front.PricingFrontOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PricingFront");
    }
  }

  private static final class PricingFrontFileDescriptorSupplier
      extends PricingFrontBaseDescriptorSupplier {
    PricingFrontFileDescriptorSupplier() {}
  }

  private static final class PricingFrontMethodDescriptorSupplier
      extends PricingFrontBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PricingFrontMethodDescriptorSupplier(String methodName) {
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
      synchronized (PricingFrontGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PricingFrontFileDescriptorSupplier())
              .addMethod(getGetPricesBySKUMethod())
              .addMethod(getGetPricesByRetailerSKUMethod())
              .build();
        }
      }
    }
    return result;
  }
}
