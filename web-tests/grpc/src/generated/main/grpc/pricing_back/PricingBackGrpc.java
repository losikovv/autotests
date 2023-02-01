package pricing_back;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/pricing_service/pricing-back.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PricingBackGrpc {

  private PricingBackGrpc() {}

  public static final String SERVICE_NAME = "pricing_back.PricingBack";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest,
      pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse> getGetDiscountTemplatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDiscountTemplates",
      requestType = pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest.class,
      responseType = pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest,
      pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse> getGetDiscountTemplatesMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest, pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse> getGetDiscountTemplatesMethod;
    if ((getGetDiscountTemplatesMethod = PricingBackGrpc.getGetDiscountTemplatesMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getGetDiscountTemplatesMethod = PricingBackGrpc.getGetDiscountTemplatesMethod) == null) {
          PricingBackGrpc.getGetDiscountTemplatesMethod = getGetDiscountTemplatesMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest, pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDiscountTemplates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("GetDiscountTemplates"))
              .build();
        }
      }
    }
    return getGetDiscountTemplatesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest,
      pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse> getCheckDiscountRetailerSKUTemplateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckDiscountRetailerSKUTemplate",
      requestType = pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest.class,
      responseType = pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest,
      pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse> getCheckDiscountRetailerSKUTemplateMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest, pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse> getCheckDiscountRetailerSKUTemplateMethod;
    if ((getCheckDiscountRetailerSKUTemplateMethod = PricingBackGrpc.getCheckDiscountRetailerSKUTemplateMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getCheckDiscountRetailerSKUTemplateMethod = PricingBackGrpc.getCheckDiscountRetailerSKUTemplateMethod) == null) {
          PricingBackGrpc.getCheckDiscountRetailerSKUTemplateMethod = getCheckDiscountRetailerSKUTemplateMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest, pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckDiscountRetailerSKUTemplate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("CheckDiscountRetailerSKUTemplate"))
              .build();
        }
      }
    }
    return getCheckDiscountRetailerSKUTemplateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest,
      pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse> getCheckDiscountProductSKUTemplateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckDiscountProductSKUTemplate",
      requestType = pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest.class,
      responseType = pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest,
      pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse> getCheckDiscountProductSKUTemplateMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest, pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse> getCheckDiscountProductSKUTemplateMethod;
    if ((getCheckDiscountProductSKUTemplateMethod = PricingBackGrpc.getCheckDiscountProductSKUTemplateMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getCheckDiscountProductSKUTemplateMethod = PricingBackGrpc.getCheckDiscountProductSKUTemplateMethod) == null) {
          PricingBackGrpc.getCheckDiscountProductSKUTemplateMethod = getCheckDiscountProductSKUTemplateMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest, pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckDiscountProductSKUTemplate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("CheckDiscountProductSKUTemplate"))
              .build();
        }
      }
    }
    return getCheckDiscountProductSKUTemplateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest,
      pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse> getSaveDiscountTemplatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveDiscountTemplates",
      requestType = pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest.class,
      responseType = pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest,
      pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse> getSaveDiscountTemplatesMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest, pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse> getSaveDiscountTemplatesMethod;
    if ((getSaveDiscountTemplatesMethod = PricingBackGrpc.getSaveDiscountTemplatesMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getSaveDiscountTemplatesMethod = PricingBackGrpc.getSaveDiscountTemplatesMethod) == null) {
          PricingBackGrpc.getSaveDiscountTemplatesMethod = getSaveDiscountTemplatesMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest, pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveDiscountTemplates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("SaveDiscountTemplates"))
              .build();
        }
      }
    }
    return getSaveDiscountTemplatesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest,
      pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse> getDeactivateDiscountTemplatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeactivateDiscountTemplates",
      requestType = pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest.class,
      responseType = pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest,
      pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse> getDeactivateDiscountTemplatesMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest, pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse> getDeactivateDiscountTemplatesMethod;
    if ((getDeactivateDiscountTemplatesMethod = PricingBackGrpc.getDeactivateDiscountTemplatesMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getDeactivateDiscountTemplatesMethod = PricingBackGrpc.getDeactivateDiscountTemplatesMethod) == null) {
          PricingBackGrpc.getDeactivateDiscountTemplatesMethod = getDeactivateDiscountTemplatesMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest, pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeactivateDiscountTemplates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("DeactivateDiscountTemplates"))
              .build();
        }
      }
    }
    return getDeactivateDiscountTemplatesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SavePricesRequest,
      pricing_back.PricingBackOuterClass.SavePricesResponse> getSavePricesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SavePrices",
      requestType = pricing_back.PricingBackOuterClass.SavePricesRequest.class,
      responseType = pricing_back.PricingBackOuterClass.SavePricesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SavePricesRequest,
      pricing_back.PricingBackOuterClass.SavePricesResponse> getSavePricesMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SavePricesRequest, pricing_back.PricingBackOuterClass.SavePricesResponse> getSavePricesMethod;
    if ((getSavePricesMethod = PricingBackGrpc.getSavePricesMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getSavePricesMethod = PricingBackGrpc.getSavePricesMethod) == null) {
          PricingBackGrpc.getSavePricesMethod = getSavePricesMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.SavePricesRequest, pricing_back.PricingBackOuterClass.SavePricesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SavePrices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.SavePricesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.SavePricesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("SavePrices"))
              .build();
        }
      }
    }
    return getSavePricesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest,
      pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse> getSaveProductDescriptionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveProductDescription",
      requestType = pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest.class,
      responseType = pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest,
      pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse> getSaveProductDescriptionMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest, pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse> getSaveProductDescriptionMethod;
    if ((getSaveProductDescriptionMethod = PricingBackGrpc.getSaveProductDescriptionMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getSaveProductDescriptionMethod = PricingBackGrpc.getSaveProductDescriptionMethod) == null) {
          PricingBackGrpc.getSaveProductDescriptionMethod = getSaveProductDescriptionMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest, pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveProductDescription"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("SaveProductDescription"))
              .build();
        }
      }
    }
    return getSaveProductDescriptionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest,
      pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse> getSaveStoreSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveStoreSettings",
      requestType = pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest.class,
      responseType = pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest,
      pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse> getSaveStoreSettingsMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest, pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse> getSaveStoreSettingsMethod;
    if ((getSaveStoreSettingsMethod = PricingBackGrpc.getSaveStoreSettingsMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getSaveStoreSettingsMethod = PricingBackGrpc.getSaveStoreSettingsMethod) == null) {
          PricingBackGrpc.getSaveStoreSettingsMethod = getSaveStoreSettingsMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest, pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveStoreSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("SaveStoreSettings"))
              .build();
        }
      }
    }
    return getSaveStoreSettingsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.GetStoreSettingsRequest,
      pricing_back.PricingBackOuterClass.GetStoreSettingsResponse> getGetStoreSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStoreSettings",
      requestType = pricing_back.PricingBackOuterClass.GetStoreSettingsRequest.class,
      responseType = pricing_back.PricingBackOuterClass.GetStoreSettingsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.GetStoreSettingsRequest,
      pricing_back.PricingBackOuterClass.GetStoreSettingsResponse> getGetStoreSettingsMethod() {
    io.grpc.MethodDescriptor<pricing_back.PricingBackOuterClass.GetStoreSettingsRequest, pricing_back.PricingBackOuterClass.GetStoreSettingsResponse> getGetStoreSettingsMethod;
    if ((getGetStoreSettingsMethod = PricingBackGrpc.getGetStoreSettingsMethod) == null) {
      synchronized (PricingBackGrpc.class) {
        if ((getGetStoreSettingsMethod = PricingBackGrpc.getGetStoreSettingsMethod) == null) {
          PricingBackGrpc.getGetStoreSettingsMethod = getGetStoreSettingsMethod =
              io.grpc.MethodDescriptor.<pricing_back.PricingBackOuterClass.GetStoreSettingsRequest, pricing_back.PricingBackOuterClass.GetStoreSettingsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStoreSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.GetStoreSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  pricing_back.PricingBackOuterClass.GetStoreSettingsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PricingBackMethodDescriptorSupplier("GetStoreSettings"))
              .build();
        }
      }
    }
    return getGetStoreSettingsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PricingBackStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingBackStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingBackStub>() {
        @java.lang.Override
        public PricingBackStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingBackStub(channel, callOptions);
        }
      };
    return PricingBackStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PricingBackBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingBackBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingBackBlockingStub>() {
        @java.lang.Override
        public PricingBackBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingBackBlockingStub(channel, callOptions);
        }
      };
    return PricingBackBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PricingBackFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PricingBackFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PricingBackFutureStub>() {
        @java.lang.Override
        public PricingBackFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PricingBackFutureStub(channel, callOptions);
        }
      };
    return PricingBackFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PricingBackImplBase implements io.grpc.BindableService {

    /**
     */
    public void getDiscountTemplates(pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDiscountTemplatesMethod(), responseObserver);
    }

    /**
     */
    public void checkDiscountRetailerSKUTemplate(pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckDiscountRetailerSKUTemplateMethod(), responseObserver);
    }

    /**
     */
    public void checkDiscountProductSKUTemplate(pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckDiscountProductSKUTemplateMethod(), responseObserver);
    }

    /**
     */
    public void saveDiscountTemplates(pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveDiscountTemplatesMethod(), responseObserver);
    }

    /**
     */
    public void deactivateDiscountTemplates(pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeactivateDiscountTemplatesMethod(), responseObserver);
    }

    /**
     */
    public void savePrices(pricing_back.PricingBackOuterClass.SavePricesRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SavePricesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSavePricesMethod(), responseObserver);
    }

    /**
     */
    public void saveProductDescription(pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveProductDescriptionMethod(), responseObserver);
    }

    /**
     */
    public void saveStoreSettings(pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveStoreSettingsMethod(), responseObserver);
    }

    /**
     */
    public void getStoreSettings(pricing_back.PricingBackOuterClass.GetStoreSettingsRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.GetStoreSettingsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStoreSettingsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetDiscountTemplatesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest,
                pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse>(
                  this, METHODID_GET_DISCOUNT_TEMPLATES)))
          .addMethod(
            getCheckDiscountRetailerSKUTemplateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest,
                pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse>(
                  this, METHODID_CHECK_DISCOUNT_RETAILER_SKUTEMPLATE)))
          .addMethod(
            getCheckDiscountProductSKUTemplateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest,
                pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse>(
                  this, METHODID_CHECK_DISCOUNT_PRODUCT_SKUTEMPLATE)))
          .addMethod(
            getSaveDiscountTemplatesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest,
                pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse>(
                  this, METHODID_SAVE_DISCOUNT_TEMPLATES)))
          .addMethod(
            getDeactivateDiscountTemplatesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest,
                pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse>(
                  this, METHODID_DEACTIVATE_DISCOUNT_TEMPLATES)))
          .addMethod(
            getSavePricesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.SavePricesRequest,
                pricing_back.PricingBackOuterClass.SavePricesResponse>(
                  this, METHODID_SAVE_PRICES)))
          .addMethod(
            getSaveProductDescriptionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest,
                pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse>(
                  this, METHODID_SAVE_PRODUCT_DESCRIPTION)))
          .addMethod(
            getSaveStoreSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest,
                pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse>(
                  this, METHODID_SAVE_STORE_SETTINGS)))
          .addMethod(
            getGetStoreSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                pricing_back.PricingBackOuterClass.GetStoreSettingsRequest,
                pricing_back.PricingBackOuterClass.GetStoreSettingsResponse>(
                  this, METHODID_GET_STORE_SETTINGS)))
          .build();
    }
  }

  /**
   */
  public static final class PricingBackStub extends io.grpc.stub.AbstractAsyncStub<PricingBackStub> {
    private PricingBackStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingBackStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingBackStub(channel, callOptions);
    }

    /**
     */
    public void getDiscountTemplates(pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDiscountTemplatesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkDiscountRetailerSKUTemplate(pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckDiscountRetailerSKUTemplateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkDiscountProductSKUTemplate(pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckDiscountProductSKUTemplateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveDiscountTemplates(pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveDiscountTemplatesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deactivateDiscountTemplates(pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeactivateDiscountTemplatesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void savePrices(pricing_back.PricingBackOuterClass.SavePricesRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SavePricesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSavePricesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveProductDescription(pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveProductDescriptionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveStoreSettings(pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveStoreSettingsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStoreSettings(pricing_back.PricingBackOuterClass.GetStoreSettingsRequest request,
        io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.GetStoreSettingsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStoreSettingsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PricingBackBlockingStub extends io.grpc.stub.AbstractBlockingStub<PricingBackBlockingStub> {
    private PricingBackBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingBackBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingBackBlockingStub(channel, callOptions);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse getDiscountTemplates(pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDiscountTemplatesMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse checkDiscountRetailerSKUTemplate(pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckDiscountRetailerSKUTemplateMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse checkDiscountProductSKUTemplate(pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckDiscountProductSKUTemplateMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse saveDiscountTemplates(pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveDiscountTemplatesMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse deactivateDiscountTemplates(pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeactivateDiscountTemplatesMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.SavePricesResponse savePrices(pricing_back.PricingBackOuterClass.SavePricesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSavePricesMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse saveProductDescription(pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveProductDescriptionMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse saveStoreSettings(pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveStoreSettingsMethod(), getCallOptions(), request);
    }

    /**
     */
    public pricing_back.PricingBackOuterClass.GetStoreSettingsResponse getStoreSettings(pricing_back.PricingBackOuterClass.GetStoreSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStoreSettingsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PricingBackFutureStub extends io.grpc.stub.AbstractFutureStub<PricingBackFutureStub> {
    private PricingBackFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PricingBackFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PricingBackFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse> getDiscountTemplates(
        pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDiscountTemplatesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse> checkDiscountRetailerSKUTemplate(
        pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckDiscountRetailerSKUTemplateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse> checkDiscountProductSKUTemplate(
        pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckDiscountProductSKUTemplateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse> saveDiscountTemplates(
        pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveDiscountTemplatesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse> deactivateDiscountTemplates(
        pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeactivateDiscountTemplatesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.SavePricesResponse> savePrices(
        pricing_back.PricingBackOuterClass.SavePricesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSavePricesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse> saveProductDescription(
        pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveProductDescriptionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse> saveStoreSettings(
        pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveStoreSettingsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<pricing_back.PricingBackOuterClass.GetStoreSettingsResponse> getStoreSettings(
        pricing_back.PricingBackOuterClass.GetStoreSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStoreSettingsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DISCOUNT_TEMPLATES = 0;
  private static final int METHODID_CHECK_DISCOUNT_RETAILER_SKUTEMPLATE = 1;
  private static final int METHODID_CHECK_DISCOUNT_PRODUCT_SKUTEMPLATE = 2;
  private static final int METHODID_SAVE_DISCOUNT_TEMPLATES = 3;
  private static final int METHODID_DEACTIVATE_DISCOUNT_TEMPLATES = 4;
  private static final int METHODID_SAVE_PRICES = 5;
  private static final int METHODID_SAVE_PRODUCT_DESCRIPTION = 6;
  private static final int METHODID_SAVE_STORE_SETTINGS = 7;
  private static final int METHODID_GET_STORE_SETTINGS = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PricingBackImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PricingBackImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DISCOUNT_TEMPLATES:
          serviceImpl.getDiscountTemplates((pricing_back.PricingBackOuterClass.GetDiscountTemplatesRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.GetDiscountTemplatesResponse>) responseObserver);
          break;
        case METHODID_CHECK_DISCOUNT_RETAILER_SKUTEMPLATE:
          serviceImpl.checkDiscountRetailerSKUTemplate((pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.CheckDiscountRetailerSKUTemplateResponse>) responseObserver);
          break;
        case METHODID_CHECK_DISCOUNT_PRODUCT_SKUTEMPLATE:
          serviceImpl.checkDiscountProductSKUTemplate((pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.CheckDiscountProductSKUTemplateResponse>) responseObserver);
          break;
        case METHODID_SAVE_DISCOUNT_TEMPLATES:
          serviceImpl.saveDiscountTemplates((pricing_back.PricingBackOuterClass.SaveDiscountTemplatesRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveDiscountTemplatesResponse>) responseObserver);
          break;
        case METHODID_DEACTIVATE_DISCOUNT_TEMPLATES:
          serviceImpl.deactivateDiscountTemplates((pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.DeactivateDiscountTemplatesResponse>) responseObserver);
          break;
        case METHODID_SAVE_PRICES:
          serviceImpl.savePrices((pricing_back.PricingBackOuterClass.SavePricesRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SavePricesResponse>) responseObserver);
          break;
        case METHODID_SAVE_PRODUCT_DESCRIPTION:
          serviceImpl.saveProductDescription((pricing_back.PricingBackOuterClass.SaveProductDescriptionRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveProductDescriptionResponse>) responseObserver);
          break;
        case METHODID_SAVE_STORE_SETTINGS:
          serviceImpl.saveStoreSettings((pricing_back.PricingBackOuterClass.SaveStoreSettingsRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.SaveStoreSettingsResponse>) responseObserver);
          break;
        case METHODID_GET_STORE_SETTINGS:
          serviceImpl.getStoreSettings((pricing_back.PricingBackOuterClass.GetStoreSettingsRequest) request,
              (io.grpc.stub.StreamObserver<pricing_back.PricingBackOuterClass.GetStoreSettingsResponse>) responseObserver);
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

  private static abstract class PricingBackBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PricingBackBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pricing_back.PricingBackOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PricingBack");
    }
  }

  private static final class PricingBackFileDescriptorSupplier
      extends PricingBackBaseDescriptorSupplier {
    PricingBackFileDescriptorSupplier() {}
  }

  private static final class PricingBackMethodDescriptorSupplier
      extends PricingBackBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PricingBackMethodDescriptorSupplier(String methodName) {
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
      synchronized (PricingBackGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PricingBackFileDescriptorSupplier())
              .addMethod(getGetDiscountTemplatesMethod())
              .addMethod(getCheckDiscountRetailerSKUTemplateMethod())
              .addMethod(getCheckDiscountProductSKUTemplateMethod())
              .addMethod(getSaveDiscountTemplatesMethod())
              .addMethod(getDeactivateDiscountTemplatesMethod())
              .addMethod(getSavePricesMethod())
              .addMethod(getSaveProductDescriptionMethod())
              .addMethod(getSaveStoreSettingsMethod())
              .addMethod(getGetStoreSettingsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
