package settings;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: on_demand/settings.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SettingsServiceGrpc {

  private SettingsServiceGrpc() {}

  public static final String SERVICE_NAME = "settings.SettingsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<settings.Settings.PutResSettingsRequest,
      settings.Settings.PutResSettingsReply> getPutResSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PutResSettings",
      requestType = settings.Settings.PutResSettingsRequest.class,
      responseType = settings.Settings.PutResSettingsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<settings.Settings.PutResSettingsRequest,
      settings.Settings.PutResSettingsReply> getPutResSettingsMethod() {
    io.grpc.MethodDescriptor<settings.Settings.PutResSettingsRequest, settings.Settings.PutResSettingsReply> getPutResSettingsMethod;
    if ((getPutResSettingsMethod = SettingsServiceGrpc.getPutResSettingsMethod) == null) {
      synchronized (SettingsServiceGrpc.class) {
        if ((getPutResSettingsMethod = SettingsServiceGrpc.getPutResSettingsMethod) == null) {
          SettingsServiceGrpc.getPutResSettingsMethod = getPutResSettingsMethod =
              io.grpc.MethodDescriptor.<settings.Settings.PutResSettingsRequest, settings.Settings.PutResSettingsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PutResSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  settings.Settings.PutResSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  settings.Settings.PutResSettingsReply.getDefaultInstance()))
              .setSchemaDescriptor(new SettingsServiceMethodDescriptorSupplier("PutResSettings"))
              .build();
        }
      }
    }
    return getPutResSettingsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<settings.Settings.GetResSettingsRequest,
      settings.Settings.GetResSettingsReply> getGetResSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetResSettings",
      requestType = settings.Settings.GetResSettingsRequest.class,
      responseType = settings.Settings.GetResSettingsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<settings.Settings.GetResSettingsRequest,
      settings.Settings.GetResSettingsReply> getGetResSettingsMethod() {
    io.grpc.MethodDescriptor<settings.Settings.GetResSettingsRequest, settings.Settings.GetResSettingsReply> getGetResSettingsMethod;
    if ((getGetResSettingsMethod = SettingsServiceGrpc.getGetResSettingsMethod) == null) {
      synchronized (SettingsServiceGrpc.class) {
        if ((getGetResSettingsMethod = SettingsServiceGrpc.getGetResSettingsMethod) == null) {
          SettingsServiceGrpc.getGetResSettingsMethod = getGetResSettingsMethod =
              io.grpc.MethodDescriptor.<settings.Settings.GetResSettingsRequest, settings.Settings.GetResSettingsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetResSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  settings.Settings.GetResSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  settings.Settings.GetResSettingsReply.getDefaultInstance()))
              .setSchemaDescriptor(new SettingsServiceMethodDescriptorSupplier("GetResSettings"))
              .build();
        }
      }
    }
    return getGetResSettingsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<settings.Settings.PutResOperationalZoneSettingsRequest,
      settings.Settings.PutResOperationalZoneSettingsReply> getPutOperationalZoneSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PutOperationalZoneSettings",
      requestType = settings.Settings.PutResOperationalZoneSettingsRequest.class,
      responseType = settings.Settings.PutResOperationalZoneSettingsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<settings.Settings.PutResOperationalZoneSettingsRequest,
      settings.Settings.PutResOperationalZoneSettingsReply> getPutOperationalZoneSettingsMethod() {
    io.grpc.MethodDescriptor<settings.Settings.PutResOperationalZoneSettingsRequest, settings.Settings.PutResOperationalZoneSettingsReply> getPutOperationalZoneSettingsMethod;
    if ((getPutOperationalZoneSettingsMethod = SettingsServiceGrpc.getPutOperationalZoneSettingsMethod) == null) {
      synchronized (SettingsServiceGrpc.class) {
        if ((getPutOperationalZoneSettingsMethod = SettingsServiceGrpc.getPutOperationalZoneSettingsMethod) == null) {
          SettingsServiceGrpc.getPutOperationalZoneSettingsMethod = getPutOperationalZoneSettingsMethod =
              io.grpc.MethodDescriptor.<settings.Settings.PutResOperationalZoneSettingsRequest, settings.Settings.PutResOperationalZoneSettingsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PutOperationalZoneSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  settings.Settings.PutResOperationalZoneSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  settings.Settings.PutResOperationalZoneSettingsReply.getDefaultInstance()))
              .setSchemaDescriptor(new SettingsServiceMethodDescriptorSupplier("PutOperationalZoneSettings"))
              .build();
        }
      }
    }
    return getPutOperationalZoneSettingsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<settings.Settings.GetResOperationalZoneSettingsRequest,
      settings.Settings.GetResOperationalZoneSettingsReply> getGetOperationalZoneSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOperationalZoneSettings",
      requestType = settings.Settings.GetResOperationalZoneSettingsRequest.class,
      responseType = settings.Settings.GetResOperationalZoneSettingsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<settings.Settings.GetResOperationalZoneSettingsRequest,
      settings.Settings.GetResOperationalZoneSettingsReply> getGetOperationalZoneSettingsMethod() {
    io.grpc.MethodDescriptor<settings.Settings.GetResOperationalZoneSettingsRequest, settings.Settings.GetResOperationalZoneSettingsReply> getGetOperationalZoneSettingsMethod;
    if ((getGetOperationalZoneSettingsMethod = SettingsServiceGrpc.getGetOperationalZoneSettingsMethod) == null) {
      synchronized (SettingsServiceGrpc.class) {
        if ((getGetOperationalZoneSettingsMethod = SettingsServiceGrpc.getGetOperationalZoneSettingsMethod) == null) {
          SettingsServiceGrpc.getGetOperationalZoneSettingsMethod = getGetOperationalZoneSettingsMethod =
              io.grpc.MethodDescriptor.<settings.Settings.GetResOperationalZoneSettingsRequest, settings.Settings.GetResOperationalZoneSettingsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOperationalZoneSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  settings.Settings.GetResOperationalZoneSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  settings.Settings.GetResOperationalZoneSettingsReply.getDefaultInstance()))
              .setSchemaDescriptor(new SettingsServiceMethodDescriptorSupplier("GetOperationalZoneSettings"))
              .build();
        }
      }
    }
    return getGetOperationalZoneSettingsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SettingsServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SettingsServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SettingsServiceStub>() {
        @java.lang.Override
        public SettingsServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SettingsServiceStub(channel, callOptions);
        }
      };
    return SettingsServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SettingsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SettingsServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SettingsServiceBlockingStub>() {
        @java.lang.Override
        public SettingsServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SettingsServiceBlockingStub(channel, callOptions);
        }
      };
    return SettingsServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SettingsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SettingsServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SettingsServiceFutureStub>() {
        @java.lang.Override
        public SettingsServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SettingsServiceFutureStub(channel, callOptions);
        }
      };
    return SettingsServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SettingsServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void putResSettings(settings.Settings.PutResSettingsRequest request,
        io.grpc.stub.StreamObserver<settings.Settings.PutResSettingsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutResSettingsMethod(), responseObserver);
    }

    /**
     */
    public void getResSettings(settings.Settings.GetResSettingsRequest request,
        io.grpc.stub.StreamObserver<settings.Settings.GetResSettingsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetResSettingsMethod(), responseObserver);
    }

    /**
     */
    public void putOperationalZoneSettings(settings.Settings.PutResOperationalZoneSettingsRequest request,
        io.grpc.stub.StreamObserver<settings.Settings.PutResOperationalZoneSettingsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutOperationalZoneSettingsMethod(), responseObserver);
    }

    /**
     */
    public void getOperationalZoneSettings(settings.Settings.GetResOperationalZoneSettingsRequest request,
        io.grpc.stub.StreamObserver<settings.Settings.GetResOperationalZoneSettingsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOperationalZoneSettingsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPutResSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                settings.Settings.PutResSettingsRequest,
                settings.Settings.PutResSettingsReply>(
                  this, METHODID_PUT_RES_SETTINGS)))
          .addMethod(
            getGetResSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                settings.Settings.GetResSettingsRequest,
                settings.Settings.GetResSettingsReply>(
                  this, METHODID_GET_RES_SETTINGS)))
          .addMethod(
            getPutOperationalZoneSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                settings.Settings.PutResOperationalZoneSettingsRequest,
                settings.Settings.PutResOperationalZoneSettingsReply>(
                  this, METHODID_PUT_OPERATIONAL_ZONE_SETTINGS)))
          .addMethod(
            getGetOperationalZoneSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                settings.Settings.GetResOperationalZoneSettingsRequest,
                settings.Settings.GetResOperationalZoneSettingsReply>(
                  this, METHODID_GET_OPERATIONAL_ZONE_SETTINGS)))
          .build();
    }
  }

  /**
   */
  public static final class SettingsServiceStub extends io.grpc.stub.AbstractAsyncStub<SettingsServiceStub> {
    private SettingsServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SettingsServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SettingsServiceStub(channel, callOptions);
    }

    /**
     */
    public void putResSettings(settings.Settings.PutResSettingsRequest request,
        io.grpc.stub.StreamObserver<settings.Settings.PutResSettingsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutResSettingsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getResSettings(settings.Settings.GetResSettingsRequest request,
        io.grpc.stub.StreamObserver<settings.Settings.GetResSettingsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetResSettingsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void putOperationalZoneSettings(settings.Settings.PutResOperationalZoneSettingsRequest request,
        io.grpc.stub.StreamObserver<settings.Settings.PutResOperationalZoneSettingsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutOperationalZoneSettingsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getOperationalZoneSettings(settings.Settings.GetResOperationalZoneSettingsRequest request,
        io.grpc.stub.StreamObserver<settings.Settings.GetResOperationalZoneSettingsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOperationalZoneSettingsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SettingsServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SettingsServiceBlockingStub> {
    private SettingsServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SettingsServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SettingsServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public settings.Settings.PutResSettingsReply putResSettings(settings.Settings.PutResSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutResSettingsMethod(), getCallOptions(), request);
    }

    /**
     */
    public settings.Settings.GetResSettingsReply getResSettings(settings.Settings.GetResSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetResSettingsMethod(), getCallOptions(), request);
    }

    /**
     */
    public settings.Settings.PutResOperationalZoneSettingsReply putOperationalZoneSettings(settings.Settings.PutResOperationalZoneSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutOperationalZoneSettingsMethod(), getCallOptions(), request);
    }

    /**
     */
    public settings.Settings.GetResOperationalZoneSettingsReply getOperationalZoneSettings(settings.Settings.GetResOperationalZoneSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOperationalZoneSettingsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SettingsServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SettingsServiceFutureStub> {
    private SettingsServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SettingsServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SettingsServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<settings.Settings.PutResSettingsReply> putResSettings(
        settings.Settings.PutResSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutResSettingsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<settings.Settings.GetResSettingsReply> getResSettings(
        settings.Settings.GetResSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetResSettingsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<settings.Settings.PutResOperationalZoneSettingsReply> putOperationalZoneSettings(
        settings.Settings.PutResOperationalZoneSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutOperationalZoneSettingsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<settings.Settings.GetResOperationalZoneSettingsReply> getOperationalZoneSettings(
        settings.Settings.GetResOperationalZoneSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOperationalZoneSettingsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PUT_RES_SETTINGS = 0;
  private static final int METHODID_GET_RES_SETTINGS = 1;
  private static final int METHODID_PUT_OPERATIONAL_ZONE_SETTINGS = 2;
  private static final int METHODID_GET_OPERATIONAL_ZONE_SETTINGS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SettingsServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SettingsServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUT_RES_SETTINGS:
          serviceImpl.putResSettings((settings.Settings.PutResSettingsRequest) request,
              (io.grpc.stub.StreamObserver<settings.Settings.PutResSettingsReply>) responseObserver);
          break;
        case METHODID_GET_RES_SETTINGS:
          serviceImpl.getResSettings((settings.Settings.GetResSettingsRequest) request,
              (io.grpc.stub.StreamObserver<settings.Settings.GetResSettingsReply>) responseObserver);
          break;
        case METHODID_PUT_OPERATIONAL_ZONE_SETTINGS:
          serviceImpl.putOperationalZoneSettings((settings.Settings.PutResOperationalZoneSettingsRequest) request,
              (io.grpc.stub.StreamObserver<settings.Settings.PutResOperationalZoneSettingsReply>) responseObserver);
          break;
        case METHODID_GET_OPERATIONAL_ZONE_SETTINGS:
          serviceImpl.getOperationalZoneSettings((settings.Settings.GetResOperationalZoneSettingsRequest) request,
              (io.grpc.stub.StreamObserver<settings.Settings.GetResOperationalZoneSettingsReply>) responseObserver);
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

  private static abstract class SettingsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SettingsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return settings.Settings.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SettingsService");
    }
  }

  private static final class SettingsServiceFileDescriptorSupplier
      extends SettingsServiceBaseDescriptorSupplier {
    SettingsServiceFileDescriptorSupplier() {}
  }

  private static final class SettingsServiceMethodDescriptorSupplier
      extends SettingsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SettingsServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SettingsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SettingsServiceFileDescriptorSupplier())
              .addMethod(getPutResSettingsMethod())
              .addMethod(getGetResSettingsMethod())
              .addMethod(getPutOperationalZoneSettingsMethod())
              .addMethod(getGetOperationalZoneSettingsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
