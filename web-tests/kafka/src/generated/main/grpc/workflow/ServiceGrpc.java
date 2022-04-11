package workflow;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: workflow/workflow.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServiceGrpc {

  private ServiceGrpc() {}

  public static final String SERVICE_NAME = "workflow.Service";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CreateWorkflowsRequest,
      workflow.WorkflowOuterClass.CreateWorkflowsResponse> getCreateWorkflowsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateWorkflows",
      requestType = workflow.WorkflowOuterClass.CreateWorkflowsRequest.class,
      responseType = workflow.WorkflowOuterClass.CreateWorkflowsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CreateWorkflowsRequest,
      workflow.WorkflowOuterClass.CreateWorkflowsResponse> getCreateWorkflowsMethod() {
    io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CreateWorkflowsRequest, workflow.WorkflowOuterClass.CreateWorkflowsResponse> getCreateWorkflowsMethod;
    if ((getCreateWorkflowsMethod = ServiceGrpc.getCreateWorkflowsMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getCreateWorkflowsMethod = ServiceGrpc.getCreateWorkflowsMethod) == null) {
          ServiceGrpc.getCreateWorkflowsMethod = getCreateWorkflowsMethod =
              io.grpc.MethodDescriptor.<workflow.WorkflowOuterClass.CreateWorkflowsRequest, workflow.WorkflowOuterClass.CreateWorkflowsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateWorkflows"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.CreateWorkflowsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.CreateWorkflowsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("CreateWorkflows"))
              .build();
        }
      }
    }
    return getCreateWorkflowsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CreateWorkflowsRequest,
      workflow.WorkflowOuterClass.CreateWorkflowsV2Response> getCreateWorkflowsV2Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateWorkflowsV2",
      requestType = workflow.WorkflowOuterClass.CreateWorkflowsRequest.class,
      responseType = workflow.WorkflowOuterClass.CreateWorkflowsV2Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CreateWorkflowsRequest,
      workflow.WorkflowOuterClass.CreateWorkflowsV2Response> getCreateWorkflowsV2Method() {
    io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CreateWorkflowsRequest, workflow.WorkflowOuterClass.CreateWorkflowsV2Response> getCreateWorkflowsV2Method;
    if ((getCreateWorkflowsV2Method = ServiceGrpc.getCreateWorkflowsV2Method) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getCreateWorkflowsV2Method = ServiceGrpc.getCreateWorkflowsV2Method) == null) {
          ServiceGrpc.getCreateWorkflowsV2Method = getCreateWorkflowsV2Method =
              io.grpc.MethodDescriptor.<workflow.WorkflowOuterClass.CreateWorkflowsRequest, workflow.WorkflowOuterClass.CreateWorkflowsV2Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateWorkflowsV2"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.CreateWorkflowsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.CreateWorkflowsV2Response.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("CreateWorkflowsV2"))
              .build();
        }
      }
    }
    return getCreateWorkflowsV2Method;
  }

  private static volatile io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest,
      com.google.protobuf.Empty> getCancelActiveWorkflowByShipmentUUIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CancelActiveWorkflowByShipmentUUID",
      requestType = workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest,
      com.google.protobuf.Empty> getCancelActiveWorkflowByShipmentUUIDMethod() {
    io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest, com.google.protobuf.Empty> getCancelActiveWorkflowByShipmentUUIDMethod;
    if ((getCancelActiveWorkflowByShipmentUUIDMethod = ServiceGrpc.getCancelActiveWorkflowByShipmentUUIDMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getCancelActiveWorkflowByShipmentUUIDMethod = ServiceGrpc.getCancelActiveWorkflowByShipmentUUIDMethod) == null) {
          ServiceGrpc.getCancelActiveWorkflowByShipmentUUIDMethod = getCancelActiveWorkflowByShipmentUUIDMethod =
              io.grpc.MethodDescriptor.<workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CancelActiveWorkflowByShipmentUUID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("CancelActiveWorkflowByShipmentUUID"))
              .build();
        }
      }
    }
    return getCancelActiveWorkflowByShipmentUUIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest,
      workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse> getRejectWorkflowByShipmentUUIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RejectWorkflowByShipmentUUID",
      requestType = workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest.class,
      responseType = workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest,
      workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse> getRejectWorkflowByShipmentUUIDMethod() {
    io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest, workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse> getRejectWorkflowByShipmentUUIDMethod;
    if ((getRejectWorkflowByShipmentUUIDMethod = ServiceGrpc.getRejectWorkflowByShipmentUUIDMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getRejectWorkflowByShipmentUUIDMethod = ServiceGrpc.getRejectWorkflowByShipmentUUIDMethod) == null) {
          ServiceGrpc.getRejectWorkflowByShipmentUUIDMethod = getRejectWorkflowByShipmentUUIDMethod =
              io.grpc.MethodDescriptor.<workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest, workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RejectWorkflowByShipmentUUID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("RejectWorkflowByShipmentUUID"))
              .build();
        }
      }
    }
    return getRejectWorkflowByShipmentUUIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.PutRegionSettingsRequest,
      workflow.WorkflowOuterClass.PutRegionSettingsReply> getPutRegionSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PutRegionSettings",
      requestType = workflow.WorkflowOuterClass.PutRegionSettingsRequest.class,
      responseType = workflow.WorkflowOuterClass.PutRegionSettingsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.PutRegionSettingsRequest,
      workflow.WorkflowOuterClass.PutRegionSettingsReply> getPutRegionSettingsMethod() {
    io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.PutRegionSettingsRequest, workflow.WorkflowOuterClass.PutRegionSettingsReply> getPutRegionSettingsMethod;
    if ((getPutRegionSettingsMethod = ServiceGrpc.getPutRegionSettingsMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getPutRegionSettingsMethod = ServiceGrpc.getPutRegionSettingsMethod) == null) {
          ServiceGrpc.getPutRegionSettingsMethod = getPutRegionSettingsMethod =
              io.grpc.MethodDescriptor.<workflow.WorkflowOuterClass.PutRegionSettingsRequest, workflow.WorkflowOuterClass.PutRegionSettingsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PutRegionSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.PutRegionSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.PutRegionSettingsReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("PutRegionSettings"))
              .build();
        }
      }
    }
    return getPutRegionSettingsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.GetRegionSettingsRequest,
      workflow.WorkflowOuterClass.GetRegionSettingsReply> getGetRegionSettingsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRegionSettings",
      requestType = workflow.WorkflowOuterClass.GetRegionSettingsRequest.class,
      responseType = workflow.WorkflowOuterClass.GetRegionSettingsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.GetRegionSettingsRequest,
      workflow.WorkflowOuterClass.GetRegionSettingsReply> getGetRegionSettingsMethod() {
    io.grpc.MethodDescriptor<workflow.WorkflowOuterClass.GetRegionSettingsRequest, workflow.WorkflowOuterClass.GetRegionSettingsReply> getGetRegionSettingsMethod;
    if ((getGetRegionSettingsMethod = ServiceGrpc.getGetRegionSettingsMethod) == null) {
      synchronized (ServiceGrpc.class) {
        if ((getGetRegionSettingsMethod = ServiceGrpc.getGetRegionSettingsMethod) == null) {
          ServiceGrpc.getGetRegionSettingsMethod = getGetRegionSettingsMethod =
              io.grpc.MethodDescriptor.<workflow.WorkflowOuterClass.GetRegionSettingsRequest, workflow.WorkflowOuterClass.GetRegionSettingsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRegionSettings"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.GetRegionSettingsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  workflow.WorkflowOuterClass.GetRegionSettingsReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceMethodDescriptorSupplier("GetRegionSettings"))
              .build();
        }
      }
    }
    return getGetRegionSettingsMethod;
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
    public void createWorkflows(workflow.WorkflowOuterClass.CreateWorkflowsRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.CreateWorkflowsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateWorkflowsMethod(), responseObserver);
    }

    /**
     */
    public void createWorkflowsV2(workflow.WorkflowOuterClass.CreateWorkflowsRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.CreateWorkflowsV2Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateWorkflowsV2Method(), responseObserver);
    }

    /**
     */
    public void cancelActiveWorkflowByShipmentUUID(workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCancelActiveWorkflowByShipmentUUIDMethod(), responseObserver);
    }

    /**
     */
    public void rejectWorkflowByShipmentUUID(workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRejectWorkflowByShipmentUUIDMethod(), responseObserver);
    }

    /**
     */
    public void putRegionSettings(workflow.WorkflowOuterClass.PutRegionSettingsRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.PutRegionSettingsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutRegionSettingsMethod(), responseObserver);
    }

    /**
     */
    public void getRegionSettings(workflow.WorkflowOuterClass.GetRegionSettingsRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.GetRegionSettingsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRegionSettingsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateWorkflowsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                workflow.WorkflowOuterClass.CreateWorkflowsRequest,
                workflow.WorkflowOuterClass.CreateWorkflowsResponse>(
                  this, METHODID_CREATE_WORKFLOWS)))
          .addMethod(
            getCreateWorkflowsV2Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                workflow.WorkflowOuterClass.CreateWorkflowsRequest,
                workflow.WorkflowOuterClass.CreateWorkflowsV2Response>(
                  this, METHODID_CREATE_WORKFLOWS_V2)))
          .addMethod(
            getCancelActiveWorkflowByShipmentUUIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_CANCEL_ACTIVE_WORKFLOW_BY_SHIPMENT_UUID)))
          .addMethod(
            getRejectWorkflowByShipmentUUIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest,
                workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse>(
                  this, METHODID_REJECT_WORKFLOW_BY_SHIPMENT_UUID)))
          .addMethod(
            getPutRegionSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                workflow.WorkflowOuterClass.PutRegionSettingsRequest,
                workflow.WorkflowOuterClass.PutRegionSettingsReply>(
                  this, METHODID_PUT_REGION_SETTINGS)))
          .addMethod(
            getGetRegionSettingsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                workflow.WorkflowOuterClass.GetRegionSettingsRequest,
                workflow.WorkflowOuterClass.GetRegionSettingsReply>(
                  this, METHODID_GET_REGION_SETTINGS)))
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
    public void createWorkflows(workflow.WorkflowOuterClass.CreateWorkflowsRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.CreateWorkflowsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateWorkflowsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createWorkflowsV2(workflow.WorkflowOuterClass.CreateWorkflowsRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.CreateWorkflowsV2Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateWorkflowsV2Method(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void cancelActiveWorkflowByShipmentUUID(workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCancelActiveWorkflowByShipmentUUIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rejectWorkflowByShipmentUUID(workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRejectWorkflowByShipmentUUIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void putRegionSettings(workflow.WorkflowOuterClass.PutRegionSettingsRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.PutRegionSettingsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutRegionSettingsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRegionSettings(workflow.WorkflowOuterClass.GetRegionSettingsRequest request,
        io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.GetRegionSettingsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRegionSettingsMethod(), getCallOptions()), request, responseObserver);
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
    public workflow.WorkflowOuterClass.CreateWorkflowsResponse createWorkflows(workflow.WorkflowOuterClass.CreateWorkflowsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateWorkflowsMethod(), getCallOptions(), request);
    }

    /**
     */
    public workflow.WorkflowOuterClass.CreateWorkflowsV2Response createWorkflowsV2(workflow.WorkflowOuterClass.CreateWorkflowsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateWorkflowsV2Method(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty cancelActiveWorkflowByShipmentUUID(workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCancelActiveWorkflowByShipmentUUIDMethod(), getCallOptions(), request);
    }

    /**
     */
    public workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse rejectWorkflowByShipmentUUID(workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRejectWorkflowByShipmentUUIDMethod(), getCallOptions(), request);
    }

    /**
     */
    public workflow.WorkflowOuterClass.PutRegionSettingsReply putRegionSettings(workflow.WorkflowOuterClass.PutRegionSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutRegionSettingsMethod(), getCallOptions(), request);
    }

    /**
     */
    public workflow.WorkflowOuterClass.GetRegionSettingsReply getRegionSettings(workflow.WorkflowOuterClass.GetRegionSettingsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRegionSettingsMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<workflow.WorkflowOuterClass.CreateWorkflowsResponse> createWorkflows(
        workflow.WorkflowOuterClass.CreateWorkflowsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateWorkflowsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<workflow.WorkflowOuterClass.CreateWorkflowsV2Response> createWorkflowsV2(
        workflow.WorkflowOuterClass.CreateWorkflowsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateWorkflowsV2Method(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> cancelActiveWorkflowByShipmentUUID(
        workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCancelActiveWorkflowByShipmentUUIDMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse> rejectWorkflowByShipmentUUID(
        workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRejectWorkflowByShipmentUUIDMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<workflow.WorkflowOuterClass.PutRegionSettingsReply> putRegionSettings(
        workflow.WorkflowOuterClass.PutRegionSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutRegionSettingsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<workflow.WorkflowOuterClass.GetRegionSettingsReply> getRegionSettings(
        workflow.WorkflowOuterClass.GetRegionSettingsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRegionSettingsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_WORKFLOWS = 0;
  private static final int METHODID_CREATE_WORKFLOWS_V2 = 1;
  private static final int METHODID_CANCEL_ACTIVE_WORKFLOW_BY_SHIPMENT_UUID = 2;
  private static final int METHODID_REJECT_WORKFLOW_BY_SHIPMENT_UUID = 3;
  private static final int METHODID_PUT_REGION_SETTINGS = 4;
  private static final int METHODID_GET_REGION_SETTINGS = 5;

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
        case METHODID_CREATE_WORKFLOWS:
          serviceImpl.createWorkflows((workflow.WorkflowOuterClass.CreateWorkflowsRequest) request,
              (io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.CreateWorkflowsResponse>) responseObserver);
          break;
        case METHODID_CREATE_WORKFLOWS_V2:
          serviceImpl.createWorkflowsV2((workflow.WorkflowOuterClass.CreateWorkflowsRequest) request,
              (io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.CreateWorkflowsV2Response>) responseObserver);
          break;
        case METHODID_CANCEL_ACTIVE_WORKFLOW_BY_SHIPMENT_UUID:
          serviceImpl.cancelActiveWorkflowByShipmentUUID((workflow.WorkflowOuterClass.CancelActiveWorkflowByShipmentUuidRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_REJECT_WORKFLOW_BY_SHIPMENT_UUID:
          serviceImpl.rejectWorkflowByShipmentUUID((workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidRequest) request,
              (io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.RejectWorkflowByShipmentUuidResponse>) responseObserver);
          break;
        case METHODID_PUT_REGION_SETTINGS:
          serviceImpl.putRegionSettings((workflow.WorkflowOuterClass.PutRegionSettingsRequest) request,
              (io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.PutRegionSettingsReply>) responseObserver);
          break;
        case METHODID_GET_REGION_SETTINGS:
          serviceImpl.getRegionSettings((workflow.WorkflowOuterClass.GetRegionSettingsRequest) request,
              (io.grpc.stub.StreamObserver<workflow.WorkflowOuterClass.GetRegionSettingsReply>) responseObserver);
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
      return workflow.WorkflowOuterClass.getDescriptor();
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
              .addMethod(getCreateWorkflowsMethod())
              .addMethod(getCreateWorkflowsV2Method())
              .addMethod(getCancelActiveWorkflowByShipmentUUIDMethod())
              .addMethod(getRejectWorkflowByShipmentUUIDMethod())
              .addMethod(getPutRegionSettingsMethod())
              .addMethod(getGetRegionSettingsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
