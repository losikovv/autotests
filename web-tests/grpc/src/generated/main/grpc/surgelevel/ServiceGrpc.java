package surgelevel;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: on_demand/surgelevel.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServiceGrpc {

  private ServiceGrpc() {}

  public static final String SERVICE_NAME = "surgelevel.Service";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveFormulaRequest,
      surgelevel.Surgelevel.SaveFormulaResponse> getSaveFormulaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveFormula",
      requestType = surgelevel.Surgelevel.SaveFormulaRequest.class,
      responseType = surgelevel.Surgelevel.SaveFormulaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveFormulaRequest,
      surgelevel.Surgelevel.SaveFormulaResponse> getSaveFormulaMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveFormulaRequest, surgelevel.Surgelevel.SaveFormulaResponse> getSaveFormulaMethod;
    if ((getSaveFormulaMethod = ServiceGrpc.getSaveFormulaMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSaveFormulaMethod = ServiceGrpc.getSaveFormulaMethod) == null) {
          ServiceGrpc.getSaveFormulaMethod = getSaveFormulaMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.SaveFormulaRequest, surgelevel.Surgelevel.SaveFormulaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveFormula"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SaveFormulaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SaveFormulaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SaveFormula"))
              .build();
        }
      }
    }
    return getSaveFormulaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.ExecuteFormulaRequest,
      surgelevel.Surgelevel.ExecuteFormulaResponse> getExecuteFormulaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExecuteFormula",
      requestType = surgelevel.Surgelevel.ExecuteFormulaRequest.class,
      responseType = surgelevel.Surgelevel.ExecuteFormulaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.ExecuteFormulaRequest,
      surgelevel.Surgelevel.ExecuteFormulaResponse> getExecuteFormulaMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.ExecuteFormulaRequest, surgelevel.Surgelevel.ExecuteFormulaResponse> getExecuteFormulaMethod;
    if ((getExecuteFormulaMethod = ServiceGrpc.getExecuteFormulaMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getExecuteFormulaMethod = ServiceGrpc.getExecuteFormulaMethod) == null) {
          ServiceGrpc.getExecuteFormulaMethod = getExecuteFormulaMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.ExecuteFormulaRequest, surgelevel.Surgelevel.ExecuteFormulaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExecuteFormula"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.ExecuteFormulaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.ExecuteFormulaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("ExecuteFormula"))
              .build();
        }
      }
    }
    return getExecuteFormulaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindFormulaRequest,
      surgelevel.Surgelevel.FindFormulaResponse> getFindFormulaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindFormula",
      requestType = surgelevel.Surgelevel.FindFormulaRequest.class,
      responseType = surgelevel.Surgelevel.FindFormulaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindFormulaRequest,
      surgelevel.Surgelevel.FindFormulaResponse> getFindFormulaMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindFormulaRequest, surgelevel.Surgelevel.FindFormulaResponse> getFindFormulaMethod;
    if ((getFindFormulaMethod = ServiceGrpc.getFindFormulaMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFindFormulaMethod = ServiceGrpc.getFindFormulaMethod) == null) {
          ServiceGrpc.getFindFormulaMethod = getFindFormulaMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.FindFormulaRequest, surgelevel.Surgelevel.FindFormulaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindFormula"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.FindFormulaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.FindFormulaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("FindFormula"))
              .build();
        }
      }
    }
    return getFindFormulaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.SetConfigRequest,
      surgelevel.Surgelevel.SetConfigResponse> getSetConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetConfig",
      requestType = surgelevel.Surgelevel.SetConfigRequest.class,
      responseType = surgelevel.Surgelevel.SetConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.SetConfigRequest,
      surgelevel.Surgelevel.SetConfigResponse> getSetConfigMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.SetConfigRequest, surgelevel.Surgelevel.SetConfigResponse> getSetConfigMethod;
    if ((getSetConfigMethod = ServiceGrpc.getSetConfigMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSetConfigMethod = ServiceGrpc.getSetConfigMethod) == null) {
          ServiceGrpc.getSetConfigMethod = getSetConfigMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.SetConfigRequest, surgelevel.Surgelevel.SetConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SetConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SetConfigResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SetConfig"))
              .build();
        }
      }
    }
    return getSetConfigMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.GetConfigRequest,
      surgelevel.Surgelevel.GetConfigResponse> getGetConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConfig",
      requestType = surgelevel.Surgelevel.GetConfigRequest.class,
      responseType = surgelevel.Surgelevel.GetConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.GetConfigRequest,
      surgelevel.Surgelevel.GetConfigResponse> getGetConfigMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.GetConfigRequest, surgelevel.Surgelevel.GetConfigResponse> getGetConfigMethod;
    if ((getGetConfigMethod = ServiceGrpc.getGetConfigMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getGetConfigMethod = ServiceGrpc.getGetConfigMethod) == null) {
          ServiceGrpc.getGetConfigMethod = getGetConfigMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.GetConfigRequest, surgelevel.Surgelevel.GetConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.GetConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.GetConfigResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("GetConfig"))
              .build();
        }
      }
    }
    return getGetConfigMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveRegionRequest,
      surgelevel.Surgelevel.SaveRegionResponse> getSaveRegionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveRegion",
      requestType = surgelevel.Surgelevel.SaveRegionRequest.class,
      responseType = surgelevel.Surgelevel.SaveRegionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveRegionRequest,
      surgelevel.Surgelevel.SaveRegionResponse> getSaveRegionMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveRegionRequest, surgelevel.Surgelevel.SaveRegionResponse> getSaveRegionMethod;
    if ((getSaveRegionMethod = ServiceGrpc.getSaveRegionMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSaveRegionMethod = ServiceGrpc.getSaveRegionMethod) == null) {
          ServiceGrpc.getSaveRegionMethod = getSaveRegionMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.SaveRegionRequest, surgelevel.Surgelevel.SaveRegionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveRegion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SaveRegionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SaveRegionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SaveRegion"))
              .build();
        }
      }
    }
    return getSaveRegionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindRegionRequest,
      surgelevel.Surgelevel.FindRegionResponse> getFindRegionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindRegion",
      requestType = surgelevel.Surgelevel.FindRegionRequest.class,
      responseType = surgelevel.Surgelevel.FindRegionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindRegionRequest,
      surgelevel.Surgelevel.FindRegionResponse> getFindRegionMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindRegionRequest, surgelevel.Surgelevel.FindRegionResponse> getFindRegionMethod;
    if ((getFindRegionMethod = ServiceGrpc.getFindRegionMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFindRegionMethod = ServiceGrpc.getFindRegionMethod) == null) {
          ServiceGrpc.getFindRegionMethod = getFindRegionMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.FindRegionRequest, surgelevel.Surgelevel.FindRegionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindRegion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.FindRegionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.FindRegionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("FindRegion"))
              .build();
        }
      }
    }
    return getFindRegionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveRetailerRequest,
      surgelevel.Surgelevel.SaveRetailerResponse> getSaveRetailerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveRetailer",
      requestType = surgelevel.Surgelevel.SaveRetailerRequest.class,
      responseType = surgelevel.Surgelevel.SaveRetailerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveRetailerRequest,
      surgelevel.Surgelevel.SaveRetailerResponse> getSaveRetailerMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveRetailerRequest, surgelevel.Surgelevel.SaveRetailerResponse> getSaveRetailerMethod;
    if ((getSaveRetailerMethod = ServiceGrpc.getSaveRetailerMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSaveRetailerMethod = ServiceGrpc.getSaveRetailerMethod) == null) {
          ServiceGrpc.getSaveRetailerMethod = getSaveRetailerMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.SaveRetailerRequest, surgelevel.Surgelevel.SaveRetailerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveRetailer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SaveRetailerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SaveRetailerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SaveRetailer"))
              .build();
        }
      }
    }
    return getSaveRetailerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindRetailerRequest,
      surgelevel.Surgelevel.FindRetailerResponse> getFindRetailerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindRetailer",
      requestType = surgelevel.Surgelevel.FindRetailerRequest.class,
      responseType = surgelevel.Surgelevel.FindRetailerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindRetailerRequest,
      surgelevel.Surgelevel.FindRetailerResponse> getFindRetailerMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindRetailerRequest, surgelevel.Surgelevel.FindRetailerResponse> getFindRetailerMethod;
    if ((getFindRetailerMethod = ServiceGrpc.getFindRetailerMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFindRetailerMethod = ServiceGrpc.getFindRetailerMethod) == null) {
          ServiceGrpc.getFindRetailerMethod = getFindRetailerMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.FindRetailerRequest, surgelevel.Surgelevel.FindRetailerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindRetailer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.FindRetailerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.FindRetailerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("FindRetailer"))
              .build();
        }
      }
    }
    return getFindRetailerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveStoreRequest,
      surgelevel.Surgelevel.SaveStoreResponse> getSaveStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveStore",
      requestType = surgelevel.Surgelevel.SaveStoreRequest.class,
      responseType = surgelevel.Surgelevel.SaveStoreResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveStoreRequest,
      surgelevel.Surgelevel.SaveStoreResponse> getSaveStoreMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.SaveStoreRequest, surgelevel.Surgelevel.SaveStoreResponse> getSaveStoreMethod;
    if ((getSaveStoreMethod = ServiceGrpc.getSaveStoreMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSaveStoreMethod = ServiceGrpc.getSaveStoreMethod) == null) {
          ServiceGrpc.getSaveStoreMethod = getSaveStoreMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.SaveStoreRequest, surgelevel.Surgelevel.SaveStoreResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SaveStoreRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.SaveStoreResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SaveStore"))
              .build();
        }
      }
    }
    return getSaveStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindStoreRequest,
      surgelevel.Surgelevel.FindStoreResponse> getFindStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindStore",
      requestType = surgelevel.Surgelevel.FindStoreRequest.class,
      responseType = surgelevel.Surgelevel.FindStoreResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindStoreRequest,
      surgelevel.Surgelevel.FindStoreResponse> getFindStoreMethod() {
    io.grpc.MethodDescriptor<surgelevel.Surgelevel.FindStoreRequest, surgelevel.Surgelevel.FindStoreResponse> getFindStoreMethod;
    if ((getFindStoreMethod = ServiceGrpc.getFindStoreMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFindStoreMethod = ServiceGrpc.getFindStoreMethod) == null) {
          ServiceGrpc.getFindStoreMethod = getFindStoreMethod =
              io.grpc.MethodDescriptor.<surgelevel.Surgelevel.FindStoreRequest, surgelevel.Surgelevel.FindStoreResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.FindStoreRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.Surgelevel.FindStoreResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("FindStore"))
              .build();
        }
      }
    }
    return getFindStoreMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceStub>() {
        @java.lang.Override
        public ServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceStub(channel, callOptions);
        }
      };
    return ServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceBlockingStub>() {
        @java.lang.Override
        public ServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceBlockingStub(channel, callOptions);
        }
      };
    return ServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceFutureStub>() {
        @java.lang.Override
        public ServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceFutureStub(channel, callOptions);
        }
      };
    return ServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void saveFormula(surgelevel.Surgelevel.SaveFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveFormulaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveFormulaMethod(), responseObserver);
    }

    /**
     */
    public void executeFormula(surgelevel.Surgelevel.ExecuteFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.ExecuteFormulaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExecuteFormulaMethod(), responseObserver);
    }

    /**
     */
    public void findFormula(surgelevel.Surgelevel.FindFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindFormulaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindFormulaMethod(), responseObserver);
    }

    /**
     */
    public void setConfig(surgelevel.Surgelevel.SetConfigRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SetConfigResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetConfigMethod(), responseObserver);
    }

    /**
     */
    public void getConfig(surgelevel.Surgelevel.GetConfigRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.GetConfigResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetConfigMethod(), responseObserver);
    }

    /**
     */
    public void saveRegion(surgelevel.Surgelevel.SaveRegionRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveRegionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveRegionMethod(), responseObserver);
    }

    /**
     */
    public void findRegion(surgelevel.Surgelevel.FindRegionRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindRegionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindRegionMethod(), responseObserver);
    }

    /**
     */
    public void saveRetailer(surgelevel.Surgelevel.SaveRetailerRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveRetailerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveRetailerMethod(), responseObserver);
    }

    /**
     */
    public void findRetailer(surgelevel.Surgelevel.FindRetailerRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindRetailerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindRetailerMethod(), responseObserver);
    }

    /**
     */
    public void saveStore(surgelevel.Surgelevel.SaveStoreRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveStoreResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveStoreMethod(), responseObserver);
    }

    /**
     */
    public void findStore(surgelevel.Surgelevel.FindStoreRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindStoreResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindStoreMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSaveFormulaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.SaveFormulaRequest,
                surgelevel.Surgelevel.SaveFormulaResponse>(
                  this, METHODID_SAVE_FORMULA)))
          .addMethod(
            getExecuteFormulaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.ExecuteFormulaRequest,
                surgelevel.Surgelevel.ExecuteFormulaResponse>(
                  this, METHODID_EXECUTE_FORMULA)))
          .addMethod(
            getFindFormulaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.FindFormulaRequest,
                surgelevel.Surgelevel.FindFormulaResponse>(
                  this, METHODID_FIND_FORMULA)))
          .addMethod(
            getSetConfigMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.SetConfigRequest,
                surgelevel.Surgelevel.SetConfigResponse>(
                  this, METHODID_SET_CONFIG)))
          .addMethod(
            getGetConfigMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.GetConfigRequest,
                surgelevel.Surgelevel.GetConfigResponse>(
                  this, METHODID_GET_CONFIG)))
          .addMethod(
            getSaveRegionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.SaveRegionRequest,
                surgelevel.Surgelevel.SaveRegionResponse>(
                  this, METHODID_SAVE_REGION)))
          .addMethod(
            getFindRegionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.FindRegionRequest,
                surgelevel.Surgelevel.FindRegionResponse>(
                  this, METHODID_FIND_REGION)))
          .addMethod(
            getSaveRetailerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.SaveRetailerRequest,
                surgelevel.Surgelevel.SaveRetailerResponse>(
                  this, METHODID_SAVE_RETAILER)))
          .addMethod(
            getFindRetailerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.FindRetailerRequest,
                surgelevel.Surgelevel.FindRetailerResponse>(
                  this, METHODID_FIND_RETAILER)))
          .addMethod(
            getSaveStoreMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.SaveStoreRequest,
                surgelevel.Surgelevel.SaveStoreResponse>(
                  this, METHODID_SAVE_STORE)))
          .addMethod(
            getFindStoreMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.Surgelevel.FindStoreRequest,
                surgelevel.Surgelevel.FindStoreResponse>(
                  this, METHODID_FIND_STORE)))
          .build();
    }
  }

  /**
   */
  public static final class ServiceStub extends io.grpc.stub.AbstractAsyncStub<ServiceStub> {
    private ServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceStub(channel, callOptions);
    }

    /**
     */
    public void saveFormula(surgelevel.Surgelevel.SaveFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveFormulaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveFormulaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void executeFormula(surgelevel.Surgelevel.ExecuteFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.ExecuteFormulaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExecuteFormulaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findFormula(surgelevel.Surgelevel.FindFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindFormulaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindFormulaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setConfig(surgelevel.Surgelevel.SetConfigRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SetConfigResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetConfigMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConfig(surgelevel.Surgelevel.GetConfigRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.GetConfigResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetConfigMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveRegion(surgelevel.Surgelevel.SaveRegionRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveRegionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveRegionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findRegion(surgelevel.Surgelevel.FindRegionRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindRegionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindRegionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveRetailer(surgelevel.Surgelevel.SaveRetailerRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveRetailerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveRetailerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findRetailer(surgelevel.Surgelevel.FindRetailerRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindRetailerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindRetailerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveStore(surgelevel.Surgelevel.SaveStoreRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveStoreResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findStore(surgelevel.Surgelevel.FindStoreRequest request,
        io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindStoreResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindStoreMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServiceBlockingStub> {
    private ServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public surgelevel.Surgelevel.SaveFormulaResponse saveFormula(surgelevel.Surgelevel.SaveFormulaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveFormulaMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.ExecuteFormulaResponse executeFormula(surgelevel.Surgelevel.ExecuteFormulaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExecuteFormulaMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.FindFormulaResponse findFormula(surgelevel.Surgelevel.FindFormulaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindFormulaMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.SetConfigResponse setConfig(surgelevel.Surgelevel.SetConfigRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetConfigMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.GetConfigResponse getConfig(surgelevel.Surgelevel.GetConfigRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetConfigMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.SaveRegionResponse saveRegion(surgelevel.Surgelevel.SaveRegionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveRegionMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.FindRegionResponse findRegion(surgelevel.Surgelevel.FindRegionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindRegionMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.SaveRetailerResponse saveRetailer(surgelevel.Surgelevel.SaveRetailerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveRetailerMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.FindRetailerResponse findRetailer(surgelevel.Surgelevel.FindRetailerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindRetailerMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.SaveStoreResponse saveStore(surgelevel.Surgelevel.SaveStoreRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveStoreMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.Surgelevel.FindStoreResponse findStore(surgelevel.Surgelevel.FindStoreRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindStoreMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ServiceFutureStub> {
    private ServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.SaveFormulaResponse> saveFormula(
        surgelevel.Surgelevel.SaveFormulaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveFormulaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.ExecuteFormulaResponse> executeFormula(
        surgelevel.Surgelevel.ExecuteFormulaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExecuteFormulaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.FindFormulaResponse> findFormula(
        surgelevel.Surgelevel.FindFormulaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindFormulaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.SetConfigResponse> setConfig(
        surgelevel.Surgelevel.SetConfigRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetConfigMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.GetConfigResponse> getConfig(
        surgelevel.Surgelevel.GetConfigRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetConfigMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.SaveRegionResponse> saveRegion(
        surgelevel.Surgelevel.SaveRegionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveRegionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.FindRegionResponse> findRegion(
        surgelevel.Surgelevel.FindRegionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindRegionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.SaveRetailerResponse> saveRetailer(
        surgelevel.Surgelevel.SaveRetailerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveRetailerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.FindRetailerResponse> findRetailer(
        surgelevel.Surgelevel.FindRetailerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindRetailerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.SaveStoreResponse> saveStore(
        surgelevel.Surgelevel.SaveStoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveStoreMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.Surgelevel.FindStoreResponse> findStore(
        surgelevel.Surgelevel.FindStoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindStoreMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_FORMULA = 0;
  private static final int METHODID_EXECUTE_FORMULA = 1;
  private static final int METHODID_FIND_FORMULA = 2;
  private static final int METHODID_SET_CONFIG = 3;
  private static final int METHODID_GET_CONFIG = 4;
  private static final int METHODID_SAVE_REGION = 5;
  private static final int METHODID_FIND_REGION = 6;
  private static final int METHODID_SAVE_RETAILER = 7;
  private static final int METHODID_FIND_RETAILER = 8;
  private static final int METHODID_SAVE_STORE = 9;
  private static final int METHODID_FIND_STORE = 10;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAVE_FORMULA:
          serviceImpl.saveFormula((surgelevel.Surgelevel.SaveFormulaRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveFormulaResponse>) responseObserver);
          break;
        case METHODID_EXECUTE_FORMULA:
          serviceImpl.executeFormula((surgelevel.Surgelevel.ExecuteFormulaRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.ExecuteFormulaResponse>) responseObserver);
          break;
        case METHODID_FIND_FORMULA:
          serviceImpl.findFormula((surgelevel.Surgelevel.FindFormulaRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindFormulaResponse>) responseObserver);
          break;
        case METHODID_SET_CONFIG:
          serviceImpl.setConfig((surgelevel.Surgelevel.SetConfigRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SetConfigResponse>) responseObserver);
          break;
        case METHODID_GET_CONFIG:
          serviceImpl.getConfig((surgelevel.Surgelevel.GetConfigRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.GetConfigResponse>) responseObserver);
          break;
        case METHODID_SAVE_REGION:
          serviceImpl.saveRegion((surgelevel.Surgelevel.SaveRegionRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveRegionResponse>) responseObserver);
          break;
        case METHODID_FIND_REGION:
          serviceImpl.findRegion((surgelevel.Surgelevel.FindRegionRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindRegionResponse>) responseObserver);
          break;
        case METHODID_SAVE_RETAILER:
          serviceImpl.saveRetailer((surgelevel.Surgelevel.SaveRetailerRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveRetailerResponse>) responseObserver);
          break;
        case METHODID_FIND_RETAILER:
          serviceImpl.findRetailer((surgelevel.Surgelevel.FindRetailerRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindRetailerResponse>) responseObserver);
          break;
        case METHODID_SAVE_STORE:
          serviceImpl.saveStore((surgelevel.Surgelevel.SaveStoreRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.SaveStoreResponse>) responseObserver);
          break;
        case METHODID_FIND_STORE:
          serviceImpl.findStore((surgelevel.Surgelevel.FindStoreRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.Surgelevel.FindStoreResponse>) responseObserver);
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

  private static abstract class ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return surgelevel.Surgelevel.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Service");
    }
  }

  private static final class ServiceFileDescriptorSupplier
      extends ServiceBaseDescriptorSupplier {
    ServiceFileDescriptorSupplier() {}
  }

  private static final class ServiceMethodDescriptorSupplier
      extends ServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServiceFileDescriptorSupplier())
              .addMethod(getSaveFormulaMethod())
              .addMethod(getExecuteFormulaMethod())
              .addMethod(getFindFormulaMethod())
              .addMethod(getSetConfigMethod())
              .addMethod(getGetConfigMethod())
              .addMethod(getSaveRegionMethod())
              .addMethod(getFindRegionMethod())
              .addMethod(getSaveRetailerMethod())
              .addMethod(getFindRetailerMethod())
              .addMethod(getSaveStoreMethod())
              .addMethod(getFindStoreMethod())
              .build();
        }
      }
    }
    return result;
  }
}
