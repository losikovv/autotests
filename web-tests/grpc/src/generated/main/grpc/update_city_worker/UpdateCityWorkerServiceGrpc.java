package update_city_worker;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/pricing_service/update-city-worker.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UpdateCityWorkerServiceGrpc {

  private UpdateCityWorkerServiceGrpc() {}

  public static final String SERVICE_NAME = "update_city_worker.UpdateCityWorkerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveCityIDMapRequest,
      update_city_worker.UpdateCityWorker.SaveCityIDMapResponse> getSaveCityIDMapMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveCityIDMap",
      requestType = update_city_worker.UpdateCityWorker.SaveCityIDMapRequest.class,
      responseType = update_city_worker.UpdateCityWorker.SaveCityIDMapResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveCityIDMapRequest,
      update_city_worker.UpdateCityWorker.SaveCityIDMapResponse> getSaveCityIDMapMethod() {
    io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveCityIDMapRequest, update_city_worker.UpdateCityWorker.SaveCityIDMapResponse> getSaveCityIDMapMethod;
    if ((getSaveCityIDMapMethod = UpdateCityWorkerServiceGrpc.getSaveCityIDMapMethod) == null) {
      synchronized (UpdateCityWorkerServiceGrpc.class) {
        if ((getSaveCityIDMapMethod = UpdateCityWorkerServiceGrpc.getSaveCityIDMapMethod) == null) {
          UpdateCityWorkerServiceGrpc.getSaveCityIDMapMethod = getSaveCityIDMapMethod =
              io.grpc.MethodDescriptor.<update_city_worker.UpdateCityWorker.SaveCityIDMapRequest, update_city_worker.UpdateCityWorker.SaveCityIDMapResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveCityIDMap"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  update_city_worker.UpdateCityWorker.SaveCityIDMapRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  update_city_worker.UpdateCityWorker.SaveCityIDMapResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UpdateCityWorkerServiceMethodDescriptorSupplier("SaveCityIDMap"))
              .build();
        }
      }
    }
    return getSaveCityIDMapMethod;
  }

  private static volatile io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest,
      update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse> getSaveCityStoreIDMapMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveCityStoreIDMap",
      requestType = update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest.class,
      responseType = update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest,
      update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse> getSaveCityStoreIDMapMethod() {
    io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest, update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse> getSaveCityStoreIDMapMethod;
    if ((getSaveCityStoreIDMapMethod = UpdateCityWorkerServiceGrpc.getSaveCityStoreIDMapMethod) == null) {
      synchronized (UpdateCityWorkerServiceGrpc.class) {
        if ((getSaveCityStoreIDMapMethod = UpdateCityWorkerServiceGrpc.getSaveCityStoreIDMapMethod) == null) {
          UpdateCityWorkerServiceGrpc.getSaveCityStoreIDMapMethod = getSaveCityStoreIDMapMethod =
              io.grpc.MethodDescriptor.<update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest, update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveCityStoreIDMap"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UpdateCityWorkerServiceMethodDescriptorSupplier("SaveCityStoreIDMap"))
              .build();
        }
      }
    }
    return getSaveCityStoreIDMapMethod;
  }

  private static volatile io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest,
      update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse> getSaveRetailerStoreMapMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveRetailerStoreMap",
      requestType = update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest.class,
      responseType = update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest,
      update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse> getSaveRetailerStoreMapMethod() {
    io.grpc.MethodDescriptor<update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest, update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse> getSaveRetailerStoreMapMethod;
    if ((getSaveRetailerStoreMapMethod = UpdateCityWorkerServiceGrpc.getSaveRetailerStoreMapMethod) == null) {
      synchronized (UpdateCityWorkerServiceGrpc.class) {
        if ((getSaveRetailerStoreMapMethod = UpdateCityWorkerServiceGrpc.getSaveRetailerStoreMapMethod) == null) {
          UpdateCityWorkerServiceGrpc.getSaveRetailerStoreMapMethod = getSaveRetailerStoreMapMethod =
              io.grpc.MethodDescriptor.<update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest, update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveRetailerStoreMap"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UpdateCityWorkerServiceMethodDescriptorSupplier("SaveRetailerStoreMap"))
              .build();
        }
      }
    }
    return getSaveRetailerStoreMapMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UpdateCityWorkerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UpdateCityWorkerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UpdateCityWorkerServiceStub>() {
        @java.lang.Override
        public UpdateCityWorkerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UpdateCityWorkerServiceStub(channel, callOptions);
        }
      };
    return UpdateCityWorkerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UpdateCityWorkerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UpdateCityWorkerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UpdateCityWorkerServiceBlockingStub>() {
        @java.lang.Override
        public UpdateCityWorkerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UpdateCityWorkerServiceBlockingStub(channel, callOptions);
        }
      };
    return UpdateCityWorkerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UpdateCityWorkerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UpdateCityWorkerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UpdateCityWorkerServiceFutureStub>() {
        @java.lang.Override
        public UpdateCityWorkerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UpdateCityWorkerServiceFutureStub(channel, callOptions);
        }
      };
    return UpdateCityWorkerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UpdateCityWorkerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void saveCityIDMap(update_city_worker.UpdateCityWorker.SaveCityIDMapRequest request,
        io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveCityIDMapResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveCityIDMapMethod(), responseObserver);
    }

    /**
     */
    public void saveCityStoreIDMap(update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest request,
        io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveCityStoreIDMapMethod(), responseObserver);
    }

    /**
     */
    public void saveRetailerStoreMap(update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest request,
        io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveRetailerStoreMapMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSaveCityIDMapMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                update_city_worker.UpdateCityWorker.SaveCityIDMapRequest,
                update_city_worker.UpdateCityWorker.SaveCityIDMapResponse>(
                  this, METHODID_SAVE_CITY_IDMAP)))
          .addMethod(
            getSaveCityStoreIDMapMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest,
                update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse>(
                  this, METHODID_SAVE_CITY_STORE_IDMAP)))
          .addMethod(
            getSaveRetailerStoreMapMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest,
                update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse>(
                  this, METHODID_SAVE_RETAILER_STORE_MAP)))
          .build();
    }
  }

  /**
   */
  public static final class UpdateCityWorkerServiceStub extends io.grpc.stub.AbstractAsyncStub<UpdateCityWorkerServiceStub> {
    private UpdateCityWorkerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UpdateCityWorkerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UpdateCityWorkerServiceStub(channel, callOptions);
    }

    /**
     */
    public void saveCityIDMap(update_city_worker.UpdateCityWorker.SaveCityIDMapRequest request,
        io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveCityIDMapResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveCityIDMapMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveCityStoreIDMap(update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest request,
        io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveCityStoreIDMapMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveRetailerStoreMap(update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest request,
        io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveRetailerStoreMapMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UpdateCityWorkerServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<UpdateCityWorkerServiceBlockingStub> {
    private UpdateCityWorkerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UpdateCityWorkerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UpdateCityWorkerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public update_city_worker.UpdateCityWorker.SaveCityIDMapResponse saveCityIDMap(update_city_worker.UpdateCityWorker.SaveCityIDMapRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveCityIDMapMethod(), getCallOptions(), request);
    }

    /**
     */
    public update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse saveCityStoreIDMap(update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveCityStoreIDMapMethod(), getCallOptions(), request);
    }

    /**
     */
    public update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse saveRetailerStoreMap(update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveRetailerStoreMapMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UpdateCityWorkerServiceFutureStub extends io.grpc.stub.AbstractFutureStub<UpdateCityWorkerServiceFutureStub> {
    private UpdateCityWorkerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UpdateCityWorkerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UpdateCityWorkerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<update_city_worker.UpdateCityWorker.SaveCityIDMapResponse> saveCityIDMap(
        update_city_worker.UpdateCityWorker.SaveCityIDMapRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveCityIDMapMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse> saveCityStoreIDMap(
        update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveCityStoreIDMapMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse> saveRetailerStoreMap(
        update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveRetailerStoreMapMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_CITY_IDMAP = 0;
  private static final int METHODID_SAVE_CITY_STORE_IDMAP = 1;
  private static final int METHODID_SAVE_RETAILER_STORE_MAP = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UpdateCityWorkerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UpdateCityWorkerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAVE_CITY_IDMAP:
          serviceImpl.saveCityIDMap((update_city_worker.UpdateCityWorker.SaveCityIDMapRequest) request,
              (io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveCityIDMapResponse>) responseObserver);
          break;
        case METHODID_SAVE_CITY_STORE_IDMAP:
          serviceImpl.saveCityStoreIDMap((update_city_worker.UpdateCityWorker.SaveCityStoreIDMapRequest) request,
              (io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveCityStoreIDMapResponse>) responseObserver);
          break;
        case METHODID_SAVE_RETAILER_STORE_MAP:
          serviceImpl.saveRetailerStoreMap((update_city_worker.UpdateCityWorker.SaveRetailerStoreMapRequest) request,
              (io.grpc.stub.StreamObserver<update_city_worker.UpdateCityWorker.SaveRetailerStoreMapResponse>) responseObserver);
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

  private static abstract class UpdateCityWorkerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UpdateCityWorkerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return update_city_worker.UpdateCityWorker.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UpdateCityWorkerService");
    }
  }

  private static final class UpdateCityWorkerServiceFileDescriptorSupplier
      extends UpdateCityWorkerServiceBaseDescriptorSupplier {
    UpdateCityWorkerServiceFileDescriptorSupplier() {}
  }

  private static final class UpdateCityWorkerServiceMethodDescriptorSupplier
      extends UpdateCityWorkerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UpdateCityWorkerServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (UpdateCityWorkerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UpdateCityWorkerServiceFileDescriptorSupplier())
              .addMethod(getSaveCityIDMapMethod())
              .addMethod(getSaveCityStoreIDMapMethod())
              .addMethod(getSaveRetailerStoreMapMethod())
              .build();
        }
      }
    }
    return result;
  }
}
