package payments;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: customer/payments.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PaymentsGrpc {

  private PaymentsGrpc() {}

  public static final String SERVICE_NAME = "payments.Payments";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest,
      payments.PaymentsOuterClass.PaymentTool> getCreateSberpayPaymentToolMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateSberpayPaymentTool",
      requestType = payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest.class,
      responseType = payments.PaymentsOuterClass.PaymentTool.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest,
      payments.PaymentsOuterClass.PaymentTool> getCreateSberpayPaymentToolMethod() {
    io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest, payments.PaymentsOuterClass.PaymentTool> getCreateSberpayPaymentToolMethod;
    if ((getCreateSberpayPaymentToolMethod = PaymentsGrpc.getCreateSberpayPaymentToolMethod) == null) {
      synchronized (PaymentsGrpc.class) {
        if ((getCreateSberpayPaymentToolMethod = PaymentsGrpc.getCreateSberpayPaymentToolMethod) == null) {
          PaymentsGrpc.getCreateSberpayPaymentToolMethod = getCreateSberpayPaymentToolMethod =
              io.grpc.MethodDescriptor.<payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest, payments.PaymentsOuterClass.PaymentTool>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateSberpayPaymentTool"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.PaymentTool.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentsMethodDescriptorSupplier("CreateSberpayPaymentTool"))
              .build();
        }
      }
    }
    return getCreateSberpayPaymentToolMethod;
  }

  private static volatile io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateHoldRequest,
      payments.PaymentsOuterClass.Hold> getCreateHoldMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateHold",
      requestType = payments.PaymentsOuterClass.CreateHoldRequest.class,
      responseType = payments.PaymentsOuterClass.Hold.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateHoldRequest,
      payments.PaymentsOuterClass.Hold> getCreateHoldMethod() {
    io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateHoldRequest, payments.PaymentsOuterClass.Hold> getCreateHoldMethod;
    if ((getCreateHoldMethod = PaymentsGrpc.getCreateHoldMethod) == null) {
      synchronized (PaymentsGrpc.class) {
        if ((getCreateHoldMethod = PaymentsGrpc.getCreateHoldMethod) == null) {
          PaymentsGrpc.getCreateHoldMethod = getCreateHoldMethod =
              io.grpc.MethodDescriptor.<payments.PaymentsOuterClass.CreateHoldRequest, payments.PaymentsOuterClass.Hold>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateHold"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.CreateHoldRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.Hold.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentsMethodDescriptorSupplier("CreateHold"))
              .build();
        }
      }
    }
    return getCreateHoldMethod;
  }

  private static volatile io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreatePaymentRequest,
      payments.PaymentsOuterClass.Payment> getCreatePaymentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreatePayment",
      requestType = payments.PaymentsOuterClass.CreatePaymentRequest.class,
      responseType = payments.PaymentsOuterClass.Payment.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreatePaymentRequest,
      payments.PaymentsOuterClass.Payment> getCreatePaymentMethod() {
    io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreatePaymentRequest, payments.PaymentsOuterClass.Payment> getCreatePaymentMethod;
    if ((getCreatePaymentMethod = PaymentsGrpc.getCreatePaymentMethod) == null) {
      synchronized (PaymentsGrpc.class) {
        if ((getCreatePaymentMethod = PaymentsGrpc.getCreatePaymentMethod) == null) {
          PaymentsGrpc.getCreatePaymentMethod = getCreatePaymentMethod =
              io.grpc.MethodDescriptor.<payments.PaymentsOuterClass.CreatePaymentRequest, payments.PaymentsOuterClass.Payment>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreatePayment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.CreatePaymentRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.Payment.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentsMethodDescriptorSupplier("CreatePayment"))
              .build();
        }
      }
    }
    return getCreatePaymentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateCancelRequest,
      payments.PaymentsOuterClass.Cancel> getCreateCancelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateCancel",
      requestType = payments.PaymentsOuterClass.CreateCancelRequest.class,
      responseType = payments.PaymentsOuterClass.Cancel.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateCancelRequest,
      payments.PaymentsOuterClass.Cancel> getCreateCancelMethod() {
    io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateCancelRequest, payments.PaymentsOuterClass.Cancel> getCreateCancelMethod;
    if ((getCreateCancelMethod = PaymentsGrpc.getCreateCancelMethod) == null) {
      synchronized (PaymentsGrpc.class) {
        if ((getCreateCancelMethod = PaymentsGrpc.getCreateCancelMethod) == null) {
          PaymentsGrpc.getCreateCancelMethod = getCreateCancelMethod =
              io.grpc.MethodDescriptor.<payments.PaymentsOuterClass.CreateCancelRequest, payments.PaymentsOuterClass.Cancel>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateCancel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.CreateCancelRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.Cancel.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentsMethodDescriptorSupplier("CreateCancel"))
              .build();
        }
      }
    }
    return getCreateCancelMethod;
  }

  private static volatile io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateRefundRequest,
      payments.PaymentsOuterClass.Refund> getCreateRefundMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateRefund",
      requestType = payments.PaymentsOuterClass.CreateRefundRequest.class,
      responseType = payments.PaymentsOuterClass.Refund.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateRefundRequest,
      payments.PaymentsOuterClass.Refund> getCreateRefundMethod() {
    io.grpc.MethodDescriptor<payments.PaymentsOuterClass.CreateRefundRequest, payments.PaymentsOuterClass.Refund> getCreateRefundMethod;
    if ((getCreateRefundMethod = PaymentsGrpc.getCreateRefundMethod) == null) {
      synchronized (PaymentsGrpc.class) {
        if ((getCreateRefundMethod = PaymentsGrpc.getCreateRefundMethod) == null) {
          PaymentsGrpc.getCreateRefundMethod = getCreateRefundMethod =
              io.grpc.MethodDescriptor.<payments.PaymentsOuterClass.CreateRefundRequest, payments.PaymentsOuterClass.Refund>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateRefund"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.CreateRefundRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.Refund.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentsMethodDescriptorSupplier("CreateRefund"))
              .build();
        }
      }
    }
    return getCreateRefundMethod;
  }

  private static volatile io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetRefundRequest,
      payments.PaymentsOuterClass.Refund> getGetRefundMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRefund",
      requestType = payments.PaymentsOuterClass.GetRefundRequest.class,
      responseType = payments.PaymentsOuterClass.Refund.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetRefundRequest,
      payments.PaymentsOuterClass.Refund> getGetRefundMethod() {
    io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetRefundRequest, payments.PaymentsOuterClass.Refund> getGetRefundMethod;
    if ((getGetRefundMethod = PaymentsGrpc.getGetRefundMethod) == null) {
      synchronized (PaymentsGrpc.class) {
        if ((getGetRefundMethod = PaymentsGrpc.getGetRefundMethod) == null) {
          PaymentsGrpc.getGetRefundMethod = getGetRefundMethod =
              io.grpc.MethodDescriptor.<payments.PaymentsOuterClass.GetRefundRequest, payments.PaymentsOuterClass.Refund>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRefund"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.GetRefundRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.Refund.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentsMethodDescriptorSupplier("GetRefund"))
              .build();
        }
      }
    }
    return getGetRefundMethod;
  }

  private static volatile io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetInvoiceRequest,
      payments.PaymentsOuterClass.Invoice> getGetInvoiceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetInvoice",
      requestType = payments.PaymentsOuterClass.GetInvoiceRequest.class,
      responseType = payments.PaymentsOuterClass.Invoice.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetInvoiceRequest,
      payments.PaymentsOuterClass.Invoice> getGetInvoiceMethod() {
    io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetInvoiceRequest, payments.PaymentsOuterClass.Invoice> getGetInvoiceMethod;
    if ((getGetInvoiceMethod = PaymentsGrpc.getGetInvoiceMethod) == null) {
      synchronized (PaymentsGrpc.class) {
        if ((getGetInvoiceMethod = PaymentsGrpc.getGetInvoiceMethod) == null) {
          PaymentsGrpc.getGetInvoiceMethod = getGetInvoiceMethod =
              io.grpc.MethodDescriptor.<payments.PaymentsOuterClass.GetInvoiceRequest, payments.PaymentsOuterClass.Invoice>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetInvoice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.GetInvoiceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.Invoice.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentsMethodDescriptorSupplier("GetInvoice"))
              .build();
        }
      }
    }
    return getGetInvoiceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetOrderStatusRequest,
      payments.PaymentsOuterClass.OrderStatus> getGetOrderStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOrderStatus",
      requestType = payments.PaymentsOuterClass.GetOrderStatusRequest.class,
      responseType = payments.PaymentsOuterClass.OrderStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetOrderStatusRequest,
      payments.PaymentsOuterClass.OrderStatus> getGetOrderStatusMethod() {
    io.grpc.MethodDescriptor<payments.PaymentsOuterClass.GetOrderStatusRequest, payments.PaymentsOuterClass.OrderStatus> getGetOrderStatusMethod;
    if ((getGetOrderStatusMethod = PaymentsGrpc.getGetOrderStatusMethod) == null) {
      synchronized (PaymentsGrpc.class) {
        if ((getGetOrderStatusMethod = PaymentsGrpc.getGetOrderStatusMethod) == null) {
          PaymentsGrpc.getGetOrderStatusMethod = getGetOrderStatusMethod =
              io.grpc.MethodDescriptor.<payments.PaymentsOuterClass.GetOrderStatusRequest, payments.PaymentsOuterClass.OrderStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOrderStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.GetOrderStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  payments.PaymentsOuterClass.OrderStatus.getDefaultInstance()))
              .setSchemaDescriptor(new PaymentsMethodDescriptorSupplier("GetOrderStatus"))
              .build();
        }
      }
    }
    return getGetOrderStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PaymentsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PaymentsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PaymentsStub>() {
        @java.lang.Override
        public PaymentsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PaymentsStub(channel, callOptions);
        }
      };
    return PaymentsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PaymentsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PaymentsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PaymentsBlockingStub>() {
        @java.lang.Override
        public PaymentsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PaymentsBlockingStub(channel, callOptions);
        }
      };
    return PaymentsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PaymentsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PaymentsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PaymentsFutureStub>() {
        @java.lang.Override
        public PaymentsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PaymentsFutureStub(channel, callOptions);
        }
      };
    return PaymentsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PaymentsImplBase implements io.grpc.BindableService {

    /**
     */
    public void createSberpayPaymentTool(payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.PaymentTool> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateSberpayPaymentToolMethod(), responseObserver);
    }

    /**
     */
    public void createHold(payments.PaymentsOuterClass.CreateHoldRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Hold> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateHoldMethod(), responseObserver);
    }

    /**
     */
    public void createPayment(payments.PaymentsOuterClass.CreatePaymentRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Payment> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreatePaymentMethod(), responseObserver);
    }

    /**
     */
    public void createCancel(payments.PaymentsOuterClass.CreateCancelRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Cancel> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateCancelMethod(), responseObserver);
    }

    /**
     */
    public void createRefund(payments.PaymentsOuterClass.CreateRefundRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Refund> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRefundMethod(), responseObserver);
    }

    /**
     */
    public void getRefund(payments.PaymentsOuterClass.GetRefundRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Refund> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRefundMethod(), responseObserver);
    }

    /**
     */
    public void getInvoice(payments.PaymentsOuterClass.GetInvoiceRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Invoice> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetInvoiceMethod(), responseObserver);
    }

    /**
     */
    public void getOrderStatus(payments.PaymentsOuterClass.GetOrderStatusRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.OrderStatus> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOrderStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateSberpayPaymentToolMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest,
                payments.PaymentsOuterClass.PaymentTool>(
                  this, METHODID_CREATE_SBERPAY_PAYMENT_TOOL)))
          .addMethod(
            getCreateHoldMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                payments.PaymentsOuterClass.CreateHoldRequest,
                payments.PaymentsOuterClass.Hold>(
                  this, METHODID_CREATE_HOLD)))
          .addMethod(
            getCreatePaymentMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                payments.PaymentsOuterClass.CreatePaymentRequest,
                payments.PaymentsOuterClass.Payment>(
                  this, METHODID_CREATE_PAYMENT)))
          .addMethod(
            getCreateCancelMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                payments.PaymentsOuterClass.CreateCancelRequest,
                payments.PaymentsOuterClass.Cancel>(
                  this, METHODID_CREATE_CANCEL)))
          .addMethod(
            getCreateRefundMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                payments.PaymentsOuterClass.CreateRefundRequest,
                payments.PaymentsOuterClass.Refund>(
                  this, METHODID_CREATE_REFUND)))
          .addMethod(
            getGetRefundMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                payments.PaymentsOuterClass.GetRefundRequest,
                payments.PaymentsOuterClass.Refund>(
                  this, METHODID_GET_REFUND)))
          .addMethod(
            getGetInvoiceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                payments.PaymentsOuterClass.GetInvoiceRequest,
                payments.PaymentsOuterClass.Invoice>(
                  this, METHODID_GET_INVOICE)))
          .addMethod(
            getGetOrderStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                payments.PaymentsOuterClass.GetOrderStatusRequest,
                payments.PaymentsOuterClass.OrderStatus>(
                  this, METHODID_GET_ORDER_STATUS)))
          .build();
    }
  }

  /**
   */
  public static final class PaymentsStub extends io.grpc.stub.AbstractAsyncStub<PaymentsStub> {
    private PaymentsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PaymentsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PaymentsStub(channel, callOptions);
    }

    /**
     */
    public void createSberpayPaymentTool(payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.PaymentTool> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateSberpayPaymentToolMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createHold(payments.PaymentsOuterClass.CreateHoldRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Hold> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateHoldMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createPayment(payments.PaymentsOuterClass.CreatePaymentRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Payment> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreatePaymentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createCancel(payments.PaymentsOuterClass.CreateCancelRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Cancel> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateCancelMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createRefund(payments.PaymentsOuterClass.CreateRefundRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Refund> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateRefundMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRefund(payments.PaymentsOuterClass.GetRefundRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Refund> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRefundMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getInvoice(payments.PaymentsOuterClass.GetInvoiceRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Invoice> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetInvoiceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getOrderStatus(payments.PaymentsOuterClass.GetOrderStatusRequest request,
        io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.OrderStatus> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOrderStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PaymentsBlockingStub extends io.grpc.stub.AbstractBlockingStub<PaymentsBlockingStub> {
    private PaymentsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PaymentsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PaymentsBlockingStub(channel, callOptions);
    }

    /**
     */
    public payments.PaymentsOuterClass.PaymentTool createSberpayPaymentTool(payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateSberpayPaymentToolMethod(), getCallOptions(), request);
    }

    /**
     */
    public payments.PaymentsOuterClass.Hold createHold(payments.PaymentsOuterClass.CreateHoldRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateHoldMethod(), getCallOptions(), request);
    }

    /**
     */
    public payments.PaymentsOuterClass.Payment createPayment(payments.PaymentsOuterClass.CreatePaymentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreatePaymentMethod(), getCallOptions(), request);
    }

    /**
     */
    public payments.PaymentsOuterClass.Cancel createCancel(payments.PaymentsOuterClass.CreateCancelRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateCancelMethod(), getCallOptions(), request);
    }

    /**
     */
    public payments.PaymentsOuterClass.Refund createRefund(payments.PaymentsOuterClass.CreateRefundRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateRefundMethod(), getCallOptions(), request);
    }

    /**
     */
    public payments.PaymentsOuterClass.Refund getRefund(payments.PaymentsOuterClass.GetRefundRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRefundMethod(), getCallOptions(), request);
    }

    /**
     */
    public payments.PaymentsOuterClass.Invoice getInvoice(payments.PaymentsOuterClass.GetInvoiceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetInvoiceMethod(), getCallOptions(), request);
    }

    /**
     */
    public payments.PaymentsOuterClass.OrderStatus getOrderStatus(payments.PaymentsOuterClass.GetOrderStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOrderStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PaymentsFutureStub extends io.grpc.stub.AbstractFutureStub<PaymentsFutureStub> {
    private PaymentsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PaymentsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PaymentsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<payments.PaymentsOuterClass.PaymentTool> createSberpayPaymentTool(
        payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateSberpayPaymentToolMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<payments.PaymentsOuterClass.Hold> createHold(
        payments.PaymentsOuterClass.CreateHoldRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateHoldMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<payments.PaymentsOuterClass.Payment> createPayment(
        payments.PaymentsOuterClass.CreatePaymentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreatePaymentMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<payments.PaymentsOuterClass.Cancel> createCancel(
        payments.PaymentsOuterClass.CreateCancelRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateCancelMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<payments.PaymentsOuterClass.Refund> createRefund(
        payments.PaymentsOuterClass.CreateRefundRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateRefundMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<payments.PaymentsOuterClass.Refund> getRefund(
        payments.PaymentsOuterClass.GetRefundRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRefundMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<payments.PaymentsOuterClass.Invoice> getInvoice(
        payments.PaymentsOuterClass.GetInvoiceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetInvoiceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<payments.PaymentsOuterClass.OrderStatus> getOrderStatus(
        payments.PaymentsOuterClass.GetOrderStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOrderStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SBERPAY_PAYMENT_TOOL = 0;
  private static final int METHODID_CREATE_HOLD = 1;
  private static final int METHODID_CREATE_PAYMENT = 2;
  private static final int METHODID_CREATE_CANCEL = 3;
  private static final int METHODID_CREATE_REFUND = 4;
  private static final int METHODID_GET_REFUND = 5;
  private static final int METHODID_GET_INVOICE = 6;
  private static final int METHODID_GET_ORDER_STATUS = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PaymentsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PaymentsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SBERPAY_PAYMENT_TOOL:
          serviceImpl.createSberpayPaymentTool((payments.PaymentsOuterClass.CreateSberpayPaymentToolRequest) request,
              (io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.PaymentTool>) responseObserver);
          break;
        case METHODID_CREATE_HOLD:
          serviceImpl.createHold((payments.PaymentsOuterClass.CreateHoldRequest) request,
              (io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Hold>) responseObserver);
          break;
        case METHODID_CREATE_PAYMENT:
          serviceImpl.createPayment((payments.PaymentsOuterClass.CreatePaymentRequest) request,
              (io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Payment>) responseObserver);
          break;
        case METHODID_CREATE_CANCEL:
          serviceImpl.createCancel((payments.PaymentsOuterClass.CreateCancelRequest) request,
              (io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Cancel>) responseObserver);
          break;
        case METHODID_CREATE_REFUND:
          serviceImpl.createRefund((payments.PaymentsOuterClass.CreateRefundRequest) request,
              (io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Refund>) responseObserver);
          break;
        case METHODID_GET_REFUND:
          serviceImpl.getRefund((payments.PaymentsOuterClass.GetRefundRequest) request,
              (io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Refund>) responseObserver);
          break;
        case METHODID_GET_INVOICE:
          serviceImpl.getInvoice((payments.PaymentsOuterClass.GetInvoiceRequest) request,
              (io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.Invoice>) responseObserver);
          break;
        case METHODID_GET_ORDER_STATUS:
          serviceImpl.getOrderStatus((payments.PaymentsOuterClass.GetOrderStatusRequest) request,
              (io.grpc.stub.StreamObserver<payments.PaymentsOuterClass.OrderStatus>) responseObserver);
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

  private static abstract class PaymentsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PaymentsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return payments.PaymentsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Payments");
    }
  }

  private static final class PaymentsFileDescriptorSupplier
      extends PaymentsBaseDescriptorSupplier {
    PaymentsFileDescriptorSupplier() {}
  }

  private static final class PaymentsMethodDescriptorSupplier
      extends PaymentsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PaymentsMethodDescriptorSupplier(String methodName) {
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
      synchronized (PaymentsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PaymentsFileDescriptorSupplier())
              .addMethod(getCreateSberpayPaymentToolMethod())
              .addMethod(getCreateHoldMethod())
              .addMethod(getCreatePaymentMethod())
              .addMethod(getCreateCancelMethod())
              .addMethod(getCreateRefundMethod())
              .addMethod(getGetRefundMethod())
              .addMethod(getGetInvoiceMethod())
              .addMethod(getGetOrderStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
