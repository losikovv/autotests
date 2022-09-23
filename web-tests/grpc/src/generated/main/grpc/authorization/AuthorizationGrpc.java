package authorization;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: authorization_service/authorization.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AuthorizationGrpc {

  private AuthorizationGrpc() {}

  public static final String SERVICE_NAME = "authorization.Authorization";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest,
      authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse> getAuthorizedPermissionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AuthorizedPermissions",
      requestType = authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest.class,
      responseType = authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest,
      authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse> getAuthorizedPermissionsMethod() {
    io.grpc.MethodDescriptor<authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest, authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse> getAuthorizedPermissionsMethod;
    if ((getAuthorizedPermissionsMethod = AuthorizationGrpc.getAuthorizedPermissionsMethod) == null) {
      synchronized (AuthorizationGrpc.class) {
        if ((getAuthorizedPermissionsMethod = AuthorizationGrpc.getAuthorizedPermissionsMethod) == null) {
          AuthorizationGrpc.getAuthorizedPermissionsMethod = getAuthorizedPermissionsMethod =
              io.grpc.MethodDescriptor.<authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest, authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AuthorizedPermissions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthorizationMethodDescriptorSupplier("AuthorizedPermissions"))
              .build();
        }
      }
    }
    return getAuthorizedPermissionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<authorization.AuthorizationOuterClass.DataFiltersRequest,
      authorization.AuthorizationOuterClass.DataFiltersResponse> getDataFiltersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DataFilters",
      requestType = authorization.AuthorizationOuterClass.DataFiltersRequest.class,
      responseType = authorization.AuthorizationOuterClass.DataFiltersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<authorization.AuthorizationOuterClass.DataFiltersRequest,
      authorization.AuthorizationOuterClass.DataFiltersResponse> getDataFiltersMethod() {
    io.grpc.MethodDescriptor<authorization.AuthorizationOuterClass.DataFiltersRequest, authorization.AuthorizationOuterClass.DataFiltersResponse> getDataFiltersMethod;
    if ((getDataFiltersMethod = AuthorizationGrpc.getDataFiltersMethod) == null) {
      synchronized (AuthorizationGrpc.class) {
        if ((getDataFiltersMethod = AuthorizationGrpc.getDataFiltersMethod) == null) {
          AuthorizationGrpc.getDataFiltersMethod = getDataFiltersMethod =
              io.grpc.MethodDescriptor.<authorization.AuthorizationOuterClass.DataFiltersRequest, authorization.AuthorizationOuterClass.DataFiltersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DataFilters"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authorization.AuthorizationOuterClass.DataFiltersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  authorization.AuthorizationOuterClass.DataFiltersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthorizationMethodDescriptorSupplier("DataFilters"))
              .build();
        }
      }
    }
    return getDataFiltersMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthorizationStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthorizationStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthorizationStub>() {
        @java.lang.Override
        public AuthorizationStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthorizationStub(channel, callOptions);
        }
      };
    return AuthorizationStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthorizationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthorizationBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthorizationBlockingStub>() {
        @java.lang.Override
        public AuthorizationBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthorizationBlockingStub(channel, callOptions);
        }
      };
    return AuthorizationBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthorizationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthorizationFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthorizationFutureStub>() {
        @java.lang.Override
        public AuthorizationFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthorizationFutureStub(channel, callOptions);
        }
      };
    return AuthorizationFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AuthorizationImplBase implements io.grpc.BindableService {

    /**
     */
    public void authorizedPermissions(authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest request,
        io.grpc.stub.StreamObserver<authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAuthorizedPermissionsMethod(), responseObserver);
    }

    /**
     */
    public void dataFilters(authorization.AuthorizationOuterClass.DataFiltersRequest request,
        io.grpc.stub.StreamObserver<authorization.AuthorizationOuterClass.DataFiltersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDataFiltersMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthorizedPermissionsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest,
                authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse>(
                  this, METHODID_AUTHORIZED_PERMISSIONS)))
          .addMethod(
            getDataFiltersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                authorization.AuthorizationOuterClass.DataFiltersRequest,
                authorization.AuthorizationOuterClass.DataFiltersResponse>(
                  this, METHODID_DATA_FILTERS)))
          .build();
    }
  }

  /**
   */
  public static final class AuthorizationStub extends io.grpc.stub.AbstractAsyncStub<AuthorizationStub> {
    private AuthorizationStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthorizationStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthorizationStub(channel, callOptions);
    }

    /**
     */
    public void authorizedPermissions(authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest request,
        io.grpc.stub.StreamObserver<authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAuthorizedPermissionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void dataFilters(authorization.AuthorizationOuterClass.DataFiltersRequest request,
        io.grpc.stub.StreamObserver<authorization.AuthorizationOuterClass.DataFiltersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDataFiltersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AuthorizationBlockingStub extends io.grpc.stub.AbstractBlockingStub<AuthorizationBlockingStub> {
    private AuthorizationBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthorizationBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthorizationBlockingStub(channel, callOptions);
    }

    /**
     */
    public authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse authorizedPermissions(authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAuthorizedPermissionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public authorization.AuthorizationOuterClass.DataFiltersResponse dataFilters(authorization.AuthorizationOuterClass.DataFiltersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDataFiltersMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AuthorizationFutureStub extends io.grpc.stub.AbstractFutureStub<AuthorizationFutureStub> {
    private AuthorizationFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthorizationFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthorizationFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse> authorizedPermissions(
        authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAuthorizedPermissionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<authorization.AuthorizationOuterClass.DataFiltersResponse> dataFilters(
        authorization.AuthorizationOuterClass.DataFiltersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDataFiltersMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHORIZED_PERMISSIONS = 0;
  private static final int METHODID_DATA_FILTERS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AuthorizationImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AuthorizationImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHORIZED_PERMISSIONS:
          serviceImpl.authorizedPermissions((authorization.AuthorizationOuterClass.AuthorizedPermissionsRequest) request,
              (io.grpc.stub.StreamObserver<authorization.AuthorizationOuterClass.AuthorizedPermissionsResponse>) responseObserver);
          break;
        case METHODID_DATA_FILTERS:
          serviceImpl.dataFilters((authorization.AuthorizationOuterClass.DataFiltersRequest) request,
              (io.grpc.stub.StreamObserver<authorization.AuthorizationOuterClass.DataFiltersResponse>) responseObserver);
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

  private static abstract class AuthorizationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthorizationBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return authorization.AuthorizationOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Authorization");
    }
  }

  private static final class AuthorizationFileDescriptorSupplier
      extends AuthorizationBaseDescriptorSupplier {
    AuthorizationFileDescriptorSupplier() {}
  }

  private static final class AuthorizationMethodDescriptorSupplier
      extends AuthorizationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AuthorizationMethodDescriptorSupplier(String methodName) {
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
      synchronized (AuthorizationGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthorizationFileDescriptorSupplier())
              .addMethod(getAuthorizedPermissionsMethod())
              .addMethod(getDataFiltersMethod())
              .build();
        }
      }
    }
    return result;
  }
}
