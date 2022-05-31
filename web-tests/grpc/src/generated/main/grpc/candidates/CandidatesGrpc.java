package candidates;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: on_demand/candidates.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CandidatesGrpc {

  private CandidatesGrpc() {}

  public static final String SERVICE_NAME = "candidates.Candidates";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectCandidatesRequest,
      candidates.CandidatesOuterClass.SelectCandidatesResponse> getSelectCandidatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SelectCandidates",
      requestType = candidates.CandidatesOuterClass.SelectCandidatesRequest.class,
      responseType = candidates.CandidatesOuterClass.SelectCandidatesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectCandidatesRequest,
      candidates.CandidatesOuterClass.SelectCandidatesResponse> getSelectCandidatesMethod() {
    io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectCandidatesRequest, candidates.CandidatesOuterClass.SelectCandidatesResponse> getSelectCandidatesMethod;
    if ((getSelectCandidatesMethod = CandidatesGrpc.getSelectCandidatesMethod) == null) {
      synchronized (CandidatesGrpc.class) {
        if ((getSelectCandidatesMethod = CandidatesGrpc.getSelectCandidatesMethod) == null) {
          CandidatesGrpc.getSelectCandidatesMethod = getSelectCandidatesMethod =
              io.grpc.MethodDescriptor.<candidates.CandidatesOuterClass.SelectCandidatesRequest, candidates.CandidatesOuterClass.SelectCandidatesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SelectCandidates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SelectCandidatesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SelectCandidatesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CandidatesMethodDescriptorSupplier("SelectCandidates"))
              .build();
        }
      }
    }
    return getSelectCandidatesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SetCandidateStateRequest,
      candidates.CandidatesOuterClass.SetCandidateStateResponse> getSetCandidatesStateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetCandidatesState",
      requestType = candidates.CandidatesOuterClass.SetCandidateStateRequest.class,
      responseType = candidates.CandidatesOuterClass.SetCandidateStateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SetCandidateStateRequest,
      candidates.CandidatesOuterClass.SetCandidateStateResponse> getSetCandidatesStateMethod() {
    io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SetCandidateStateRequest, candidates.CandidatesOuterClass.SetCandidateStateResponse> getSetCandidatesStateMethod;
    if ((getSetCandidatesStateMethod = CandidatesGrpc.getSetCandidatesStateMethod) == null) {
      synchronized (CandidatesGrpc.class) {
        if ((getSetCandidatesStateMethod = CandidatesGrpc.getSetCandidatesStateMethod) == null) {
          CandidatesGrpc.getSetCandidatesStateMethod = getSetCandidatesStateMethod =
              io.grpc.MethodDescriptor.<candidates.CandidatesOuterClass.SetCandidateStateRequest, candidates.CandidatesOuterClass.SetCandidateStateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetCandidatesState"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SetCandidateStateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SetCandidateStateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CandidatesMethodDescriptorSupplier("SetCandidatesState"))
              .build();
        }
      }
    }
    return getSetCandidatesStateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectInRectRequest,
      candidates.CandidatesOuterClass.SelectInRectResponse> getSelectInRectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SelectInRect",
      requestType = candidates.CandidatesOuterClass.SelectInRectRequest.class,
      responseType = candidates.CandidatesOuterClass.SelectInRectResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectInRectRequest,
      candidates.CandidatesOuterClass.SelectInRectResponse> getSelectInRectMethod() {
    io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectInRectRequest, candidates.CandidatesOuterClass.SelectInRectResponse> getSelectInRectMethod;
    if ((getSelectInRectMethod = CandidatesGrpc.getSelectInRectMethod) == null) {
      synchronized (CandidatesGrpc.class) {
        if ((getSelectInRectMethod = CandidatesGrpc.getSelectInRectMethod) == null) {
          CandidatesGrpc.getSelectInRectMethod = getSelectInRectMethod =
              io.grpc.MethodDescriptor.<candidates.CandidatesOuterClass.SelectInRectRequest, candidates.CandidatesOuterClass.SelectInRectResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SelectInRect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SelectInRectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SelectInRectResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CandidatesMethodDescriptorSupplier("SelectInRect"))
              .build();
        }
      }
    }
    return getSelectInRectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest,
      candidates.CandidatesOuterClass.SelectCandidateByUUIDResult> getSelectCandidateByUUIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SelectCandidateByUUID",
      requestType = candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest.class,
      responseType = candidates.CandidatesOuterClass.SelectCandidateByUUIDResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest,
      candidates.CandidatesOuterClass.SelectCandidateByUUIDResult> getSelectCandidateByUUIDMethod() {
    io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest, candidates.CandidatesOuterClass.SelectCandidateByUUIDResult> getSelectCandidateByUUIDMethod;
    if ((getSelectCandidateByUUIDMethod = CandidatesGrpc.getSelectCandidateByUUIDMethod) == null) {
      synchronized (CandidatesGrpc.class) {
        if ((getSelectCandidateByUUIDMethod = CandidatesGrpc.getSelectCandidateByUUIDMethod) == null) {
          CandidatesGrpc.getSelectCandidateByUUIDMethod = getSelectCandidateByUUIDMethod =
              io.grpc.MethodDescriptor.<candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest, candidates.CandidatesOuterClass.SelectCandidateByUUIDResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SelectCandidateByUUID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SelectCandidateByUUIDResult.getDefaultInstance()))
              .setSchemaDescriptor(new CandidatesMethodDescriptorSupplier("SelectCandidateByUUID"))
              .build();
        }
      }
    }
    return getSelectCandidateByUUIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SearchRequest,
      candidates.CandidatesOuterClass.SearchResult> getSearchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Search",
      requestType = candidates.CandidatesOuterClass.SearchRequest.class,
      responseType = candidates.CandidatesOuterClass.SearchResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SearchRequest,
      candidates.CandidatesOuterClass.SearchResult> getSearchMethod() {
    io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.SearchRequest, candidates.CandidatesOuterClass.SearchResult> getSearchMethod;
    if ((getSearchMethod = CandidatesGrpc.getSearchMethod) == null) {
      synchronized (CandidatesGrpc.class) {
        if ((getSearchMethod = CandidatesGrpc.getSearchMethod) == null) {
          CandidatesGrpc.getSearchMethod = getSearchMethod =
              io.grpc.MethodDescriptor.<candidates.CandidatesOuterClass.SearchRequest, candidates.CandidatesOuterClass.SearchResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Search"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.SearchResult.getDefaultInstance()))
              .setSchemaDescriptor(new CandidatesMethodDescriptorSupplier("Search"))
              .build();
        }
      }
    }
    return getSearchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.HaveActiveWorkflowRequest,
      candidates.CandidatesOuterClass.HaveActiveWorkflowResponse> getHaveActiveWorkflowMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HaveActiveWorkflow",
      requestType = candidates.CandidatesOuterClass.HaveActiveWorkflowRequest.class,
      responseType = candidates.CandidatesOuterClass.HaveActiveWorkflowResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.HaveActiveWorkflowRequest,
      candidates.CandidatesOuterClass.HaveActiveWorkflowResponse> getHaveActiveWorkflowMethod() {
    io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.HaveActiveWorkflowRequest, candidates.CandidatesOuterClass.HaveActiveWorkflowResponse> getHaveActiveWorkflowMethod;
    if ((getHaveActiveWorkflowMethod = CandidatesGrpc.getHaveActiveWorkflowMethod) == null) {
      synchronized (CandidatesGrpc.class) {
        if ((getHaveActiveWorkflowMethod = CandidatesGrpc.getHaveActiveWorkflowMethod) == null) {
          CandidatesGrpc.getHaveActiveWorkflowMethod = getHaveActiveWorkflowMethod =
              io.grpc.MethodDescriptor.<candidates.CandidatesOuterClass.HaveActiveWorkflowRequest, candidates.CandidatesOuterClass.HaveActiveWorkflowResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HaveActiveWorkflow"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.HaveActiveWorkflowRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.HaveActiveWorkflowResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CandidatesMethodDescriptorSupplier("HaveActiveWorkflow"))
              .build();
        }
      }
    }
    return getHaveActiveWorkflowMethod;
  }

  private static volatile io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.ZoneSettingsRequest,
      candidates.CandidatesOuterClass.ZoneSetting> getGetZoneSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetZoneSettings",
      requestType = candidates.CandidatesOuterClass.ZoneSettingsRequest.class,
      responseType = candidates.CandidatesOuterClass.ZoneSetting.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.ZoneSettingsRequest,
      candidates.CandidatesOuterClass.ZoneSetting> getGetZoneSettingsMethod() {
    io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.ZoneSettingsRequest, candidates.CandidatesOuterClass.ZoneSetting> getGetZoneSettingsMethod;
    if ((getGetZoneSettingsMethod = CandidatesGrpc.getGetZoneSettingsMethod) == null) {
      synchronized (CandidatesGrpc.class) {
        if ((getGetZoneSettingsMethod = CandidatesGrpc.getGetZoneSettingsMethod) == null) {
          CandidatesGrpc.getGetZoneSettingsMethod = getGetZoneSettingsMethod =
              io.grpc.MethodDescriptor.<candidates.CandidatesOuterClass.ZoneSettingsRequest, candidates.CandidatesOuterClass.ZoneSetting>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetZoneSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.ZoneSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.ZoneSetting.getDefaultInstance()))
              .setSchemaDescriptor(new CandidatesMethodDescriptorSupplier("GetZoneSettings"))
              .build();
        }
      }
    }
    return getGetZoneSettingsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.ZoneSetting,
      com.google.protobuf.Empty> getUpdateZoneSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateZoneSettings",
      requestType = candidates.CandidatesOuterClass.ZoneSetting.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.ZoneSetting,
      com.google.protobuf.Empty> getUpdateZoneSettingsMethod() {
    io.grpc.MethodDescriptor<candidates.CandidatesOuterClass.ZoneSetting, com.google.protobuf.Empty> getUpdateZoneSettingsMethod;
    if ((getUpdateZoneSettingsMethod = CandidatesGrpc.getUpdateZoneSettingsMethod) == null) {
      synchronized (CandidatesGrpc.class) {
        if ((getUpdateZoneSettingsMethod = CandidatesGrpc.getUpdateZoneSettingsMethod) == null) {
          CandidatesGrpc.getUpdateZoneSettingsMethod = getUpdateZoneSettingsMethod =
              io.grpc.MethodDescriptor.<candidates.CandidatesOuterClass.ZoneSetting, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateZoneSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  candidates.CandidatesOuterClass.ZoneSetting.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new CandidatesMethodDescriptorSupplier("UpdateZoneSettings"))
              .build();
        }
      }
    }
    return getUpdateZoneSettingsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CandidatesStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CandidatesStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CandidatesStub>() {
        @java.lang.Override
        public CandidatesStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CandidatesStub(channel, callOptions);
        }
      };
    return CandidatesStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CandidatesBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CandidatesBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CandidatesBlockingStub>() {
        @java.lang.Override
        public CandidatesBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CandidatesBlockingStub(channel, callOptions);
        }
      };
    return CandidatesBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CandidatesFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CandidatesFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CandidatesFutureStub>() {
        @java.lang.Override
        public CandidatesFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CandidatesFutureStub(channel, callOptions);
        }
      };
    return CandidatesFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CandidatesImplBase implements io.grpc.BindableService {

    /**
     */
    public void selectCandidates(candidates.CandidatesOuterClass.SelectCandidatesRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectCandidatesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSelectCandidatesMethod(), responseObserver);
    }

    /**
     */
    public void setCandidatesState(candidates.CandidatesOuterClass.SetCandidateStateRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SetCandidateStateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetCandidatesStateMethod(), responseObserver);
    }

    /**
     */
    public void selectInRect(candidates.CandidatesOuterClass.SelectInRectRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectInRectResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSelectInRectMethod(), responseObserver);
    }

    /**
     */
    public void selectCandidateByUUID(candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectCandidateByUUIDResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSelectCandidateByUUIDMethod(), responseObserver);
    }

    /**
     */
    public void search(candidates.CandidatesOuterClass.SearchRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SearchResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchMethod(), responseObserver);
    }

    /**
     */
    public void haveActiveWorkflow(candidates.CandidatesOuterClass.HaveActiveWorkflowRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.HaveActiveWorkflowResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHaveActiveWorkflowMethod(), responseObserver);
    }

    /**
     */
    public void getZoneSettings(candidates.CandidatesOuterClass.ZoneSettingsRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.ZoneSetting> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetZoneSettingsMethod(), responseObserver);
    }

    /**
     */
    public void updateZoneSettings(candidates.CandidatesOuterClass.ZoneSetting request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateZoneSettingsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSelectCandidatesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                candidates.CandidatesOuterClass.SelectCandidatesRequest,
                candidates.CandidatesOuterClass.SelectCandidatesResponse>(
                  this, METHODID_SELECT_CANDIDATES)))
          .addMethod(
            getSetCandidatesStateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                candidates.CandidatesOuterClass.SetCandidateStateRequest,
                candidates.CandidatesOuterClass.SetCandidateStateResponse>(
                  this, METHODID_SET_CANDIDATES_STATE)))
          .addMethod(
            getSelectInRectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                candidates.CandidatesOuterClass.SelectInRectRequest,
                candidates.CandidatesOuterClass.SelectInRectResponse>(
                  this, METHODID_SELECT_IN_RECT)))
          .addMethod(
            getSelectCandidateByUUIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest,
                candidates.CandidatesOuterClass.SelectCandidateByUUIDResult>(
                  this, METHODID_SELECT_CANDIDATE_BY_UUID)))
          .addMethod(
            getSearchMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                candidates.CandidatesOuterClass.SearchRequest,
                candidates.CandidatesOuterClass.SearchResult>(
                  this, METHODID_SEARCH)))
          .addMethod(
            getHaveActiveWorkflowMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                candidates.CandidatesOuterClass.HaveActiveWorkflowRequest,
                candidates.CandidatesOuterClass.HaveActiveWorkflowResponse>(
                  this, METHODID_HAVE_ACTIVE_WORKFLOW)))
          .addMethod(
            getGetZoneSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                candidates.CandidatesOuterClass.ZoneSettingsRequest,
                candidates.CandidatesOuterClass.ZoneSetting>(
                  this, METHODID_GET_ZONE_SETTINGS)))
          .addMethod(
            getUpdateZoneSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                candidates.CandidatesOuterClass.ZoneSetting,
                com.google.protobuf.Empty>(
                  this, METHODID_UPDATE_ZONE_SETTINGS)))
          .build();
    }
  }

  /**
   */
  public static final class CandidatesStub extends io.grpc.stub.AbstractAsyncStub<CandidatesStub> {
    private CandidatesStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CandidatesStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CandidatesStub(channel, callOptions);
    }

    /**
     */
    public void selectCandidates(candidates.CandidatesOuterClass.SelectCandidatesRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectCandidatesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSelectCandidatesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setCandidatesState(candidates.CandidatesOuterClass.SetCandidateStateRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SetCandidateStateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetCandidatesStateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectInRect(candidates.CandidatesOuterClass.SelectInRectRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectInRectResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSelectInRectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void selectCandidateByUUID(candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectCandidateByUUIDResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSelectCandidateByUUIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void search(candidates.CandidatesOuterClass.SearchRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SearchResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSearchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void haveActiveWorkflow(candidates.CandidatesOuterClass.HaveActiveWorkflowRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.HaveActiveWorkflowResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHaveActiveWorkflowMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getZoneSettings(candidates.CandidatesOuterClass.ZoneSettingsRequest request,
        io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.ZoneSetting> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetZoneSettingsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateZoneSettings(candidates.CandidatesOuterClass.ZoneSetting request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateZoneSettingsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CandidatesBlockingStub extends io.grpc.stub.AbstractBlockingStub<CandidatesBlockingStub> {
    private CandidatesBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CandidatesBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CandidatesBlockingStub(channel, callOptions);
    }

    /**
     */
    public candidates.CandidatesOuterClass.SelectCandidatesResponse selectCandidates(candidates.CandidatesOuterClass.SelectCandidatesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSelectCandidatesMethod(), getCallOptions(), request);
    }

    /**
     */
    public candidates.CandidatesOuterClass.SetCandidateStateResponse setCandidatesState(candidates.CandidatesOuterClass.SetCandidateStateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetCandidatesStateMethod(), getCallOptions(), request);
    }

    /**
     */
    public candidates.CandidatesOuterClass.SelectInRectResponse selectInRect(candidates.CandidatesOuterClass.SelectInRectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSelectInRectMethod(), getCallOptions(), request);
    }

    /**
     */
    public candidates.CandidatesOuterClass.SelectCandidateByUUIDResult selectCandidateByUUID(candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSelectCandidateByUUIDMethod(), getCallOptions(), request);
    }

    /**
     */
    public candidates.CandidatesOuterClass.SearchResult search(candidates.CandidatesOuterClass.SearchRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSearchMethod(), getCallOptions(), request);
    }

    /**
     */
    public candidates.CandidatesOuterClass.HaveActiveWorkflowResponse haveActiveWorkflow(candidates.CandidatesOuterClass.HaveActiveWorkflowRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHaveActiveWorkflowMethod(), getCallOptions(), request);
    }

    /**
     */
    public candidates.CandidatesOuterClass.ZoneSetting getZoneSettings(candidates.CandidatesOuterClass.ZoneSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetZoneSettingsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateZoneSettings(candidates.CandidatesOuterClass.ZoneSetting request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateZoneSettingsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CandidatesFutureStub extends io.grpc.stub.AbstractFutureStub<CandidatesFutureStub> {
    private CandidatesFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CandidatesFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CandidatesFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<candidates.CandidatesOuterClass.SelectCandidatesResponse> selectCandidates(
        candidates.CandidatesOuterClass.SelectCandidatesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSelectCandidatesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<candidates.CandidatesOuterClass.SetCandidateStateResponse> setCandidatesState(
        candidates.CandidatesOuterClass.SetCandidateStateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetCandidatesStateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<candidates.CandidatesOuterClass.SelectInRectResponse> selectInRect(
        candidates.CandidatesOuterClass.SelectInRectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSelectInRectMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<candidates.CandidatesOuterClass.SelectCandidateByUUIDResult> selectCandidateByUUID(
        candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSelectCandidateByUUIDMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<candidates.CandidatesOuterClass.SearchResult> search(
        candidates.CandidatesOuterClass.SearchRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSearchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<candidates.CandidatesOuterClass.HaveActiveWorkflowResponse> haveActiveWorkflow(
        candidates.CandidatesOuterClass.HaveActiveWorkflowRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHaveActiveWorkflowMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<candidates.CandidatesOuterClass.ZoneSetting> getZoneSettings(
        candidates.CandidatesOuterClass.ZoneSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetZoneSettingsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> updateZoneSettings(
        candidates.CandidatesOuterClass.ZoneSetting request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateZoneSettingsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SELECT_CANDIDATES = 0;
  private static final int METHODID_SET_CANDIDATES_STATE = 1;
  private static final int METHODID_SELECT_IN_RECT = 2;
  private static final int METHODID_SELECT_CANDIDATE_BY_UUID = 3;
  private static final int METHODID_SEARCH = 4;
  private static final int METHODID_HAVE_ACTIVE_WORKFLOW = 5;
  private static final int METHODID_GET_ZONE_SETTINGS = 6;
  private static final int METHODID_UPDATE_ZONE_SETTINGS = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CandidatesImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CandidatesImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SELECT_CANDIDATES:
          serviceImpl.selectCandidates((candidates.CandidatesOuterClass.SelectCandidatesRequest) request,
              (io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectCandidatesResponse>) responseObserver);
          break;
        case METHODID_SET_CANDIDATES_STATE:
          serviceImpl.setCandidatesState((candidates.CandidatesOuterClass.SetCandidateStateRequest) request,
              (io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SetCandidateStateResponse>) responseObserver);
          break;
        case METHODID_SELECT_IN_RECT:
          serviceImpl.selectInRect((candidates.CandidatesOuterClass.SelectInRectRequest) request,
              (io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectInRectResponse>) responseObserver);
          break;
        case METHODID_SELECT_CANDIDATE_BY_UUID:
          serviceImpl.selectCandidateByUUID((candidates.CandidatesOuterClass.SelectCandidateByUUIDRequest) request,
              (io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SelectCandidateByUUIDResult>) responseObserver);
          break;
        case METHODID_SEARCH:
          serviceImpl.search((candidates.CandidatesOuterClass.SearchRequest) request,
              (io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.SearchResult>) responseObserver);
          break;
        case METHODID_HAVE_ACTIVE_WORKFLOW:
          serviceImpl.haveActiveWorkflow((candidates.CandidatesOuterClass.HaveActiveWorkflowRequest) request,
              (io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.HaveActiveWorkflowResponse>) responseObserver);
          break;
        case METHODID_GET_ZONE_SETTINGS:
          serviceImpl.getZoneSettings((candidates.CandidatesOuterClass.ZoneSettingsRequest) request,
              (io.grpc.stub.StreamObserver<candidates.CandidatesOuterClass.ZoneSetting>) responseObserver);
          break;
        case METHODID_UPDATE_ZONE_SETTINGS:
          serviceImpl.updateZoneSettings((candidates.CandidatesOuterClass.ZoneSetting) request,
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

  private static abstract class CandidatesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CandidatesBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return candidates.CandidatesOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Candidates");
    }
  }

  private static final class CandidatesFileDescriptorSupplier
      extends CandidatesBaseDescriptorSupplier {
    CandidatesFileDescriptorSupplier() {}
  }

  private static final class CandidatesMethodDescriptorSupplier
      extends CandidatesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CandidatesMethodDescriptorSupplier(String methodName) {
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
      synchronized (CandidatesGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CandidatesFileDescriptorSupplier())
              .addMethod(getSelectCandidatesMethod())
              .addMethod(getSetCandidatesStateMethod())
              .addMethod(getSelectInRectMethod())
              .addMethod(getSelectCandidateByUUIDMethod())
              .addMethod(getSearchMethod())
              .addMethod(getHaveActiveWorkflowMethod())
              .addMethod(getGetZoneSettingsMethod())
              .addMethod(getUpdateZoneSettingsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
