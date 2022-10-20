package surgelevel.v2;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: on_demand/surgelevel.v2.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServiceGrpc {

  private ServiceGrpc() {}

  public static final String SERVICE_NAME = "surgelevel.v2.Service";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveFormulaRequest,
      surgelevel.v2.SurgelevelV2.SaveFormulaResponse> getSaveFormulaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveFormula",
      requestType = surgelevel.v2.SurgelevelV2.SaveFormulaRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.SaveFormulaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveFormulaRequest,
      surgelevel.v2.SurgelevelV2.SaveFormulaResponse> getSaveFormulaMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveFormulaRequest, surgelevel.v2.SurgelevelV2.SaveFormulaResponse> getSaveFormulaMethod;
    if ((getSaveFormulaMethod = ServiceGrpc.getSaveFormulaMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSaveFormulaMethod = ServiceGrpc.getSaveFormulaMethod) == null) {
          ServiceGrpc.getSaveFormulaMethod = getSaveFormulaMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.SaveFormulaRequest, surgelevel.v2.SurgelevelV2.SaveFormulaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveFormula"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SaveFormulaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SaveFormulaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SaveFormula"))
              .build();
        }
      }
    }
    return getSaveFormulaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.CallFormulaRequest,
      surgelevel.v2.SurgelevelV2.CallFormulaResponse> getCallFormulaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CallFormula",
      requestType = surgelevel.v2.SurgelevelV2.CallFormulaRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.CallFormulaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.CallFormulaRequest,
      surgelevel.v2.SurgelevelV2.CallFormulaResponse> getCallFormulaMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.CallFormulaRequest, surgelevel.v2.SurgelevelV2.CallFormulaResponse> getCallFormulaMethod;
    if ((getCallFormulaMethod = ServiceGrpc.getCallFormulaMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getCallFormulaMethod = ServiceGrpc.getCallFormulaMethod) == null) {
          ServiceGrpc.getCallFormulaMethod = getCallFormulaMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.CallFormulaRequest, surgelevel.v2.SurgelevelV2.CallFormulaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CallFormula"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.CallFormulaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.CallFormulaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("CallFormula"))
              .build();
        }
      }
    }
    return getCallFormulaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindFormulaRequest,
      surgelevel.v2.SurgelevelV2.FindFormulaResponse> getFindFormulaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindFormula",
      requestType = surgelevel.v2.SurgelevelV2.FindFormulaRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.FindFormulaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindFormulaRequest,
      surgelevel.v2.SurgelevelV2.FindFormulaResponse> getFindFormulaMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindFormulaRequest, surgelevel.v2.SurgelevelV2.FindFormulaResponse> getFindFormulaMethod;
    if ((getFindFormulaMethod = ServiceGrpc.getFindFormulaMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFindFormulaMethod = ServiceGrpc.getFindFormulaMethod) == null) {
          ServiceGrpc.getFindFormulaMethod = getFindFormulaMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.FindFormulaRequest, surgelevel.v2.SurgelevelV2.FindFormulaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindFormula"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.FindFormulaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.FindFormulaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("FindFormula"))
              .build();
        }
      }
    }
    return getFindFormulaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SetConfigRequest,
      surgelevel.v2.SurgelevelV2.SetConfigResponse> getSetConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetConfig",
      requestType = surgelevel.v2.SurgelevelV2.SetConfigRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.SetConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SetConfigRequest,
      surgelevel.v2.SurgelevelV2.SetConfigResponse> getSetConfigMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SetConfigRequest, surgelevel.v2.SurgelevelV2.SetConfigResponse> getSetConfigMethod;
    if ((getSetConfigMethod = ServiceGrpc.getSetConfigMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSetConfigMethod = ServiceGrpc.getSetConfigMethod) == null) {
          ServiceGrpc.getSetConfigMethod = getSetConfigMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.SetConfigRequest, surgelevel.v2.SurgelevelV2.SetConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SetConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SetConfigResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SetConfig"))
              .build();
        }
      }
    }
    return getSetConfigMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.GetConfigRequest,
      surgelevel.v2.SurgelevelV2.GetConfigResponse> getGetConfigMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConfig",
      requestType = surgelevel.v2.SurgelevelV2.GetConfigRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.GetConfigResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.GetConfigRequest,
      surgelevel.v2.SurgelevelV2.GetConfigResponse> getGetConfigMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.GetConfigRequest, surgelevel.v2.SurgelevelV2.GetConfigResponse> getGetConfigMethod;
    if ((getGetConfigMethod = ServiceGrpc.getGetConfigMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getGetConfigMethod = ServiceGrpc.getGetConfigMethod) == null) {
          ServiceGrpc.getGetConfigMethod = getGetConfigMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.GetConfigRequest, surgelevel.v2.SurgelevelV2.GetConfigResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetConfig"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.GetConfigRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.GetConfigResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("GetConfig"))
              .build();
        }
      }
    }
    return getGetConfigMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveRegionRequest,
      surgelevel.v2.SurgelevelV2.SaveRegionResponse> getSaveRegionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveRegion",
      requestType = surgelevel.v2.SurgelevelV2.SaveRegionRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.SaveRegionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveRegionRequest,
      surgelevel.v2.SurgelevelV2.SaveRegionResponse> getSaveRegionMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveRegionRequest, surgelevel.v2.SurgelevelV2.SaveRegionResponse> getSaveRegionMethod;
    if ((getSaveRegionMethod = ServiceGrpc.getSaveRegionMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSaveRegionMethod = ServiceGrpc.getSaveRegionMethod) == null) {
          ServiceGrpc.getSaveRegionMethod = getSaveRegionMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.SaveRegionRequest, surgelevel.v2.SurgelevelV2.SaveRegionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveRegion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SaveRegionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SaveRegionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SaveRegion"))
              .build();
        }
      }
    }
    return getSaveRegionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindRegionRequest,
      surgelevel.v2.SurgelevelV2.FindRegionResponse> getFindRegionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindRegion",
      requestType = surgelevel.v2.SurgelevelV2.FindRegionRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.FindRegionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindRegionRequest,
      surgelevel.v2.SurgelevelV2.FindRegionResponse> getFindRegionMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindRegionRequest, surgelevel.v2.SurgelevelV2.FindRegionResponse> getFindRegionMethod;
    if ((getFindRegionMethod = ServiceGrpc.getFindRegionMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFindRegionMethod = ServiceGrpc.getFindRegionMethod) == null) {
          ServiceGrpc.getFindRegionMethod = getFindRegionMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.FindRegionRequest, surgelevel.v2.SurgelevelV2.FindRegionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindRegion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.FindRegionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.FindRegionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("FindRegion"))
              .build();
        }
      }
    }
    return getFindRegionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveRetailerRequest,
      surgelevel.v2.SurgelevelV2.SaveRetailerResponse> getSaveRetailerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveRetailer",
      requestType = surgelevel.v2.SurgelevelV2.SaveRetailerRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.SaveRetailerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveRetailerRequest,
      surgelevel.v2.SurgelevelV2.SaveRetailerResponse> getSaveRetailerMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveRetailerRequest, surgelevel.v2.SurgelevelV2.SaveRetailerResponse> getSaveRetailerMethod;
    if ((getSaveRetailerMethod = ServiceGrpc.getSaveRetailerMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSaveRetailerMethod = ServiceGrpc.getSaveRetailerMethod) == null) {
          ServiceGrpc.getSaveRetailerMethod = getSaveRetailerMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.SaveRetailerRequest, surgelevel.v2.SurgelevelV2.SaveRetailerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveRetailer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SaveRetailerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SaveRetailerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SaveRetailer"))
              .build();
        }
      }
    }
    return getSaveRetailerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindRetailerRequest,
      surgelevel.v2.SurgelevelV2.FindRetailerResponse> getFindRetailerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindRetailer",
      requestType = surgelevel.v2.SurgelevelV2.FindRetailerRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.FindRetailerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindRetailerRequest,
      surgelevel.v2.SurgelevelV2.FindRetailerResponse> getFindRetailerMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindRetailerRequest, surgelevel.v2.SurgelevelV2.FindRetailerResponse> getFindRetailerMethod;
    if ((getFindRetailerMethod = ServiceGrpc.getFindRetailerMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFindRetailerMethod = ServiceGrpc.getFindRetailerMethod) == null) {
          ServiceGrpc.getFindRetailerMethod = getFindRetailerMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.FindRetailerRequest, surgelevel.v2.SurgelevelV2.FindRetailerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindRetailer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.FindRetailerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.FindRetailerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("FindRetailer"))
              .build();
        }
      }
    }
    return getFindRetailerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveStoreRequest,
      surgelevel.v2.SurgelevelV2.SaveStoreResponse> getSaveStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveStore",
      requestType = surgelevel.v2.SurgelevelV2.SaveStoreRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.SaveStoreResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveStoreRequest,
      surgelevel.v2.SurgelevelV2.SaveStoreResponse> getSaveStoreMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.SaveStoreRequest, surgelevel.v2.SurgelevelV2.SaveStoreResponse> getSaveStoreMethod;
    if ((getSaveStoreMethod = ServiceGrpc.getSaveStoreMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getSaveStoreMethod = ServiceGrpc.getSaveStoreMethod) == null) {
          ServiceGrpc.getSaveStoreMethod = getSaveStoreMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.SaveStoreRequest, surgelevel.v2.SurgelevelV2.SaveStoreResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SaveStoreRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.SaveStoreResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("SaveStore"))
              .build();
        }
      }
    }
    return getSaveStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindStoreRequest,
      surgelevel.v2.SurgelevelV2.FindStoreResponse> getFindStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindStore",
      requestType = surgelevel.v2.SurgelevelV2.FindStoreRequest.class,
      responseType = surgelevel.v2.SurgelevelV2.FindStoreResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindStoreRequest,
      surgelevel.v2.SurgelevelV2.FindStoreResponse> getFindStoreMethod() {
    io.grpc.MethodDescriptor<surgelevel.v2.SurgelevelV2.FindStoreRequest, surgelevel.v2.SurgelevelV2.FindStoreResponse> getFindStoreMethod;
    if ((getFindStoreMethod = ServiceGrpc.getFindStoreMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getFindStoreMethod = ServiceGrpc.getFindStoreMethod) == null) {
          ServiceGrpc.getFindStoreMethod = getFindStoreMethod =
              io.grpc.MethodDescriptor.<surgelevel.v2.SurgelevelV2.FindStoreRequest, surgelevel.v2.SurgelevelV2.FindStoreResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.FindStoreRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  surgelevel.v2.SurgelevelV2.FindStoreResponse.getDefaultInstance()))
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
    public void saveFormula(surgelevel.v2.SurgelevelV2.SaveFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveFormulaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveFormulaMethod(), responseObserver);
    }

    /**
     */
    public void callFormula(surgelevel.v2.SurgelevelV2.CallFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.CallFormulaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCallFormulaMethod(), responseObserver);
    }

    /**
     */
    public void findFormula(surgelevel.v2.SurgelevelV2.FindFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindFormulaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindFormulaMethod(), responseObserver);
    }

    /**
     */
    public void setConfig(surgelevel.v2.SurgelevelV2.SetConfigRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SetConfigResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetConfigMethod(), responseObserver);
    }

    /**
     */
    public void getConfig(surgelevel.v2.SurgelevelV2.GetConfigRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.GetConfigResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetConfigMethod(), responseObserver);
    }

    /**
     */
    public void saveRegion(surgelevel.v2.SurgelevelV2.SaveRegionRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveRegionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveRegionMethod(), responseObserver);
    }

    /**
     */
    public void findRegion(surgelevel.v2.SurgelevelV2.FindRegionRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindRegionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindRegionMethod(), responseObserver);
    }

    /**
     */
    public void saveRetailer(surgelevel.v2.SurgelevelV2.SaveRetailerRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveRetailerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveRetailerMethod(), responseObserver);
    }

    /**
     */
    public void findRetailer(surgelevel.v2.SurgelevelV2.FindRetailerRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindRetailerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindRetailerMethod(), responseObserver);
    }

    /**
     */
    public void saveStore(surgelevel.v2.SurgelevelV2.SaveStoreRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveStoreResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveStoreMethod(), responseObserver);
    }

    /**
     */
    public void findStore(surgelevel.v2.SurgelevelV2.FindStoreRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindStoreResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindStoreMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSaveFormulaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.SaveFormulaRequest,
                surgelevel.v2.SurgelevelV2.SaveFormulaResponse>(
                  this, METHODID_SAVE_FORMULA)))
          .addMethod(
            getCallFormulaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.CallFormulaRequest,
                surgelevel.v2.SurgelevelV2.CallFormulaResponse>(
                  this, METHODID_CALL_FORMULA)))
          .addMethod(
            getFindFormulaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.FindFormulaRequest,
                surgelevel.v2.SurgelevelV2.FindFormulaResponse>(
                  this, METHODID_FIND_FORMULA)))
          .addMethod(
            getSetConfigMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.SetConfigRequest,
                surgelevel.v2.SurgelevelV2.SetConfigResponse>(
                  this, METHODID_SET_CONFIG)))
          .addMethod(
            getGetConfigMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.GetConfigRequest,
                surgelevel.v2.SurgelevelV2.GetConfigResponse>(
                  this, METHODID_GET_CONFIG)))
          .addMethod(
            getSaveRegionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.SaveRegionRequest,
                surgelevel.v2.SurgelevelV2.SaveRegionResponse>(
                  this, METHODID_SAVE_REGION)))
          .addMethod(
            getFindRegionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.FindRegionRequest,
                surgelevel.v2.SurgelevelV2.FindRegionResponse>(
                  this, METHODID_FIND_REGION)))
          .addMethod(
            getSaveRetailerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.SaveRetailerRequest,
                surgelevel.v2.SurgelevelV2.SaveRetailerResponse>(
                  this, METHODID_SAVE_RETAILER)))
          .addMethod(
            getFindRetailerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.FindRetailerRequest,
                surgelevel.v2.SurgelevelV2.FindRetailerResponse>(
                  this, METHODID_FIND_RETAILER)))
          .addMethod(
            getSaveStoreMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.SaveStoreRequest,
                surgelevel.v2.SurgelevelV2.SaveStoreResponse>(
                  this, METHODID_SAVE_STORE)))
          .addMethod(
            getFindStoreMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                surgelevel.v2.SurgelevelV2.FindStoreRequest,
                surgelevel.v2.SurgelevelV2.FindStoreResponse>(
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
    public void saveFormula(surgelevel.v2.SurgelevelV2.SaveFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveFormulaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveFormulaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void callFormula(surgelevel.v2.SurgelevelV2.CallFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.CallFormulaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCallFormulaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findFormula(surgelevel.v2.SurgelevelV2.FindFormulaRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindFormulaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindFormulaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setConfig(surgelevel.v2.SurgelevelV2.SetConfigRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SetConfigResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetConfigMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConfig(surgelevel.v2.SurgelevelV2.GetConfigRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.GetConfigResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetConfigMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveRegion(surgelevel.v2.SurgelevelV2.SaveRegionRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveRegionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveRegionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findRegion(surgelevel.v2.SurgelevelV2.FindRegionRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindRegionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindRegionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveRetailer(surgelevel.v2.SurgelevelV2.SaveRetailerRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveRetailerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveRetailerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findRetailer(surgelevel.v2.SurgelevelV2.FindRetailerRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindRetailerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindRetailerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveStore(surgelevel.v2.SurgelevelV2.SaveStoreRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveStoreResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findStore(surgelevel.v2.SurgelevelV2.FindStoreRequest request,
        io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindStoreResponse> responseObserver) {
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
    public surgelevel.v2.SurgelevelV2.SaveFormulaResponse saveFormula(surgelevel.v2.SurgelevelV2.SaveFormulaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveFormulaMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.CallFormulaResponse callFormula(surgelevel.v2.SurgelevelV2.CallFormulaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCallFormulaMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.FindFormulaResponse findFormula(surgelevel.v2.SurgelevelV2.FindFormulaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindFormulaMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.SetConfigResponse setConfig(surgelevel.v2.SurgelevelV2.SetConfigRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetConfigMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.GetConfigResponse getConfig(surgelevel.v2.SurgelevelV2.GetConfigRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetConfigMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.SaveRegionResponse saveRegion(surgelevel.v2.SurgelevelV2.SaveRegionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveRegionMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.FindRegionResponse findRegion(surgelevel.v2.SurgelevelV2.FindRegionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindRegionMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.SaveRetailerResponse saveRetailer(surgelevel.v2.SurgelevelV2.SaveRetailerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveRetailerMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.FindRetailerResponse findRetailer(surgelevel.v2.SurgelevelV2.FindRetailerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindRetailerMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.SaveStoreResponse saveStore(surgelevel.v2.SurgelevelV2.SaveStoreRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveStoreMethod(), getCallOptions(), request);
    }

    /**
     */
    public surgelevel.v2.SurgelevelV2.FindStoreResponse findStore(surgelevel.v2.SurgelevelV2.FindStoreRequest request) {
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
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.SaveFormulaResponse> saveFormula(
        surgelevel.v2.SurgelevelV2.SaveFormulaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveFormulaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.CallFormulaResponse> callFormula(
        surgelevel.v2.SurgelevelV2.CallFormulaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCallFormulaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.FindFormulaResponse> findFormula(
        surgelevel.v2.SurgelevelV2.FindFormulaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindFormulaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.SetConfigResponse> setConfig(
        surgelevel.v2.SurgelevelV2.SetConfigRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetConfigMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.GetConfigResponse> getConfig(
        surgelevel.v2.SurgelevelV2.GetConfigRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetConfigMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.SaveRegionResponse> saveRegion(
        surgelevel.v2.SurgelevelV2.SaveRegionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveRegionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.FindRegionResponse> findRegion(
        surgelevel.v2.SurgelevelV2.FindRegionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindRegionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.SaveRetailerResponse> saveRetailer(
        surgelevel.v2.SurgelevelV2.SaveRetailerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveRetailerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.FindRetailerResponse> findRetailer(
        surgelevel.v2.SurgelevelV2.FindRetailerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindRetailerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.SaveStoreResponse> saveStore(
        surgelevel.v2.SurgelevelV2.SaveStoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveStoreMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<surgelevel.v2.SurgelevelV2.FindStoreResponse> findStore(
        surgelevel.v2.SurgelevelV2.FindStoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindStoreMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_FORMULA = 0;
  private static final int METHODID_CALL_FORMULA = 1;
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
          serviceImpl.saveFormula((surgelevel.v2.SurgelevelV2.SaveFormulaRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveFormulaResponse>) responseObserver);
          break;
        case METHODID_CALL_FORMULA:
          serviceImpl.callFormula((surgelevel.v2.SurgelevelV2.CallFormulaRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.CallFormulaResponse>) responseObserver);
          break;
        case METHODID_FIND_FORMULA:
          serviceImpl.findFormula((surgelevel.v2.SurgelevelV2.FindFormulaRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindFormulaResponse>) responseObserver);
          break;
        case METHODID_SET_CONFIG:
          serviceImpl.setConfig((surgelevel.v2.SurgelevelV2.SetConfigRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SetConfigResponse>) responseObserver);
          break;
        case METHODID_GET_CONFIG:
          serviceImpl.getConfig((surgelevel.v2.SurgelevelV2.GetConfigRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.GetConfigResponse>) responseObserver);
          break;
        case METHODID_SAVE_REGION:
          serviceImpl.saveRegion((surgelevel.v2.SurgelevelV2.SaveRegionRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveRegionResponse>) responseObserver);
          break;
        case METHODID_FIND_REGION:
          serviceImpl.findRegion((surgelevel.v2.SurgelevelV2.FindRegionRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindRegionResponse>) responseObserver);
          break;
        case METHODID_SAVE_RETAILER:
          serviceImpl.saveRetailer((surgelevel.v2.SurgelevelV2.SaveRetailerRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveRetailerResponse>) responseObserver);
          break;
        case METHODID_FIND_RETAILER:
          serviceImpl.findRetailer((surgelevel.v2.SurgelevelV2.FindRetailerRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindRetailerResponse>) responseObserver);
          break;
        case METHODID_SAVE_STORE:
          serviceImpl.saveStore((surgelevel.v2.SurgelevelV2.SaveStoreRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.SaveStoreResponse>) responseObserver);
          break;
        case METHODID_FIND_STORE:
          serviceImpl.findStore((surgelevel.v2.SurgelevelV2.FindStoreRequest) request,
              (io.grpc.stub.StreamObserver<surgelevel.v2.SurgelevelV2.FindStoreResponse>) responseObserver);
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
      return surgelevel.v2.SurgelevelV2.getDescriptor();
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
              .addMethod(getCallFormulaMethod())
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
