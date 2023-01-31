package planned_dispatch;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: on_demand/assembly_changed.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DispatchGrpc {

  private DispatchGrpc() {}

  public static final String SERVICE_NAME = "planned_dispatch.Dispatch";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyStartRequest,
      com.google.protobuf.Empty> getAssemblyStartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssemblyStart",
      requestType = planned_dispatch.AssemblyChanged.AssemblyStartRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyStartRequest,
      com.google.protobuf.Empty> getAssemblyStartMethod() {
    io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyStartRequest, com.google.protobuf.Empty> getAssemblyStartMethod;
    if ((getAssemblyStartMethod = DispatchGrpc.getAssemblyStartMethod) == null) {
      synchronized (DispatchGrpc.class) {
        if ((getAssemblyStartMethod = DispatchGrpc.getAssemblyStartMethod) == null) {
          DispatchGrpc.getAssemblyStartMethod = getAssemblyStartMethod =
              io.grpc.MethodDescriptor.<planned_dispatch.AssemblyChanged.AssemblyStartRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AssemblyStart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  planned_dispatch.AssemblyChanged.AssemblyStartRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new DispatchMethodDescriptorSupplier("AssemblyStart"))
              .build();
        }
      }
    }
    return getAssemblyStartMethod;
  }

  private static volatile io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyPauseRequest,
      com.google.protobuf.Empty> getAssemblyPauseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssemblyPause",
      requestType = planned_dispatch.AssemblyChanged.AssemblyPauseRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyPauseRequest,
      com.google.protobuf.Empty> getAssemblyPauseMethod() {
    io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyPauseRequest, com.google.protobuf.Empty> getAssemblyPauseMethod;
    if ((getAssemblyPauseMethod = DispatchGrpc.getAssemblyPauseMethod) == null) {
      synchronized (DispatchGrpc.class) {
        if ((getAssemblyPauseMethod = DispatchGrpc.getAssemblyPauseMethod) == null) {
          DispatchGrpc.getAssemblyPauseMethod = getAssemblyPauseMethod =
              io.grpc.MethodDescriptor.<planned_dispatch.AssemblyChanged.AssemblyPauseRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AssemblyPause"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  planned_dispatch.AssemblyChanged.AssemblyPauseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new DispatchMethodDescriptorSupplier("AssemblyPause"))
              .build();
        }
      }
    }
    return getAssemblyPauseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyFinishRequest,
      com.google.protobuf.Empty> getAssemblyFinishMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssemblyFinish",
      requestType = planned_dispatch.AssemblyChanged.AssemblyFinishRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyFinishRequest,
      com.google.protobuf.Empty> getAssemblyFinishMethod() {
    io.grpc.MethodDescriptor<planned_dispatch.AssemblyChanged.AssemblyFinishRequest, com.google.protobuf.Empty> getAssemblyFinishMethod;
    if ((getAssemblyFinishMethod = DispatchGrpc.getAssemblyFinishMethod) == null) {
      synchronized (DispatchGrpc.class) {
        if ((getAssemblyFinishMethod = DispatchGrpc.getAssemblyFinishMethod) == null) {
          DispatchGrpc.getAssemblyFinishMethod = getAssemblyFinishMethod =
              io.grpc.MethodDescriptor.<planned_dispatch.AssemblyChanged.AssemblyFinishRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AssemblyFinish"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  planned_dispatch.AssemblyChanged.AssemblyFinishRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new DispatchMethodDescriptorSupplier("AssemblyFinish"))
              .build();
        }
      }
    }
    return getAssemblyFinishMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DispatchStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DispatchStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DispatchStub>() {
        @java.lang.Override
        public DispatchStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DispatchStub(channel, callOptions);
        }
      };
    return DispatchStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DispatchBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DispatchBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DispatchBlockingStub>() {
        @java.lang.Override
        public DispatchBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DispatchBlockingStub(channel, callOptions);
        }
      };
    return DispatchBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DispatchFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DispatchFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DispatchFutureStub>() {
        @java.lang.Override
        public DispatchFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DispatchFutureStub(channel, callOptions);
        }
      };
    return DispatchFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class DispatchImplBase implements io.grpc.BindableService {

    /**
     */
    public void assemblyStart(planned_dispatch.AssemblyChanged.AssemblyStartRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAssemblyStartMethod(), responseObserver);
    }

    /**
     */
    public void assemblyPause(planned_dispatch.AssemblyChanged.AssemblyPauseRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAssemblyPauseMethod(), responseObserver);
    }

    /**
     */
    public void assemblyFinish(planned_dispatch.AssemblyChanged.AssemblyFinishRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAssemblyFinishMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAssemblyStartMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                planned_dispatch.AssemblyChanged.AssemblyStartRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_ASSEMBLY_START)))
          .addMethod(
            getAssemblyPauseMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                planned_dispatch.AssemblyChanged.AssemblyPauseRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_ASSEMBLY_PAUSE)))
          .addMethod(
            getAssemblyFinishMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                planned_dispatch.AssemblyChanged.AssemblyFinishRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_ASSEMBLY_FINISH)))
          .build();
    }
  }

  /**
   */
  public static final class DispatchStub extends io.grpc.stub.AbstractAsyncStub<DispatchStub> {
    private DispatchStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DispatchStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DispatchStub(channel, callOptions);
    }

    /**
     */
    public void assemblyStart(planned_dispatch.AssemblyChanged.AssemblyStartRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAssemblyStartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void assemblyPause(planned_dispatch.AssemblyChanged.AssemblyPauseRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAssemblyPauseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void assemblyFinish(planned_dispatch.AssemblyChanged.AssemblyFinishRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAssemblyFinishMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DispatchBlockingStub extends io.grpc.stub.AbstractBlockingStub<DispatchBlockingStub> {
    private DispatchBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DispatchBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DispatchBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty assemblyStart(planned_dispatch.AssemblyChanged.AssemblyStartRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAssemblyStartMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty assemblyPause(planned_dispatch.AssemblyChanged.AssemblyPauseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAssemblyPauseMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty assemblyFinish(planned_dispatch.AssemblyChanged.AssemblyFinishRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAssemblyFinishMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DispatchFutureStub extends io.grpc.stub.AbstractFutureStub<DispatchFutureStub> {
    private DispatchFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DispatchFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DispatchFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> assemblyStart(
        planned_dispatch.AssemblyChanged.AssemblyStartRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAssemblyStartMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> assemblyPause(
        planned_dispatch.AssemblyChanged.AssemblyPauseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAssemblyPauseMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> assemblyFinish(
        planned_dispatch.AssemblyChanged.AssemblyFinishRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAssemblyFinishMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ASSEMBLY_START = 0;
  private static final int METHODID_ASSEMBLY_PAUSE = 1;
  private static final int METHODID_ASSEMBLY_FINISH = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DispatchImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DispatchImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ASSEMBLY_START:
          serviceImpl.assemblyStart((planned_dispatch.AssemblyChanged.AssemblyStartRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_ASSEMBLY_PAUSE:
          serviceImpl.assemblyPause((planned_dispatch.AssemblyChanged.AssemblyPauseRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_ASSEMBLY_FINISH:
          serviceImpl.assemblyFinish((planned_dispatch.AssemblyChanged.AssemblyFinishRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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

  private static abstract class DispatchBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DispatchBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return planned_dispatch.AssemblyChanged.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Dispatch");
    }
  }

  private static final class DispatchFileDescriptorSupplier
      extends DispatchBaseDescriptorSupplier {
    DispatchFileDescriptorSupplier() {}
  }

  private static final class DispatchMethodDescriptorSupplier
      extends DispatchBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DispatchMethodDescriptorSupplier(String methodName) {
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
      synchronized (DispatchGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DispatchFileDescriptorSupplier())
              .addMethod(getAssemblyStartMethod())
              .addMethod(getAssemblyPauseMethod())
              .addMethod(getAssemblyFinishMethod())
              .build();
        }
      }
    }
    return result;
  }
}
