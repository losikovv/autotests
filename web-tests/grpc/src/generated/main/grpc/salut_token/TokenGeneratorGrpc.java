package salut_token;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/salut_token/salut-token.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TokenGeneratorGrpc {

  private TokenGeneratorGrpc() {}

  public static final String SERVICE_NAME = "salut_token.TokenGenerator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<salut_token.SalutToken.TokenRequest,
      salut_token.SalutToken.TokenResponse> getGetTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetToken",
      requestType = salut_token.SalutToken.TokenRequest.class,
      responseType = salut_token.SalutToken.TokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<salut_token.SalutToken.TokenRequest,
      salut_token.SalutToken.TokenResponse> getGetTokenMethod() {
    io.grpc.MethodDescriptor<salut_token.SalutToken.TokenRequest, salut_token.SalutToken.TokenResponse> getGetTokenMethod;
    if ((getGetTokenMethod = TokenGeneratorGrpc.getGetTokenMethod) == null) {
      synchronized (TokenGeneratorGrpc.class) {
        if ((getGetTokenMethod = TokenGeneratorGrpc.getGetTokenMethod) == null) {
          TokenGeneratorGrpc.getGetTokenMethod = getGetTokenMethod =
              io.grpc.MethodDescriptor.<salut_token.SalutToken.TokenRequest, salut_token.SalutToken.TokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  salut_token.SalutToken.TokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  salut_token.SalutToken.TokenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TokenGeneratorMethodDescriptorSupplier("GetToken"))
              .build();
        }
      }
    }
    return getGetTokenMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TokenGeneratorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TokenGeneratorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TokenGeneratorStub>() {
        @java.lang.Override
        public TokenGeneratorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TokenGeneratorStub(channel, callOptions);
        }
      };
    return TokenGeneratorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TokenGeneratorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TokenGeneratorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TokenGeneratorBlockingStub>() {
        @java.lang.Override
        public TokenGeneratorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TokenGeneratorBlockingStub(channel, callOptions);
        }
      };
    return TokenGeneratorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TokenGeneratorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TokenGeneratorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TokenGeneratorFutureStub>() {
        @java.lang.Override
        public TokenGeneratorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TokenGeneratorFutureStub(channel, callOptions);
        }
      };
    return TokenGeneratorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TokenGeneratorImplBase implements io.grpc.BindableService {

    /**
     */
    public void getToken(salut_token.SalutToken.TokenRequest request,
        io.grpc.stub.StreamObserver<salut_token.SalutToken.TokenResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTokenMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetTokenMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                salut_token.SalutToken.TokenRequest,
                salut_token.SalutToken.TokenResponse>(
                  this, METHODID_GET_TOKEN)))
          .build();
    }
  }

  /**
   */
  public static final class TokenGeneratorStub extends io.grpc.stub.AbstractAsyncStub<TokenGeneratorStub> {
    private TokenGeneratorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TokenGeneratorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TokenGeneratorStub(channel, callOptions);
    }

    /**
     */
    public void getToken(salut_token.SalutToken.TokenRequest request,
        io.grpc.stub.StreamObserver<salut_token.SalutToken.TokenResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTokenMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TokenGeneratorBlockingStub extends io.grpc.stub.AbstractBlockingStub<TokenGeneratorBlockingStub> {
    private TokenGeneratorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TokenGeneratorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TokenGeneratorBlockingStub(channel, callOptions);
    }

    /**
     */
    public salut_token.SalutToken.TokenResponse getToken(salut_token.SalutToken.TokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTokenMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TokenGeneratorFutureStub extends io.grpc.stub.AbstractFutureStub<TokenGeneratorFutureStub> {
    private TokenGeneratorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TokenGeneratorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TokenGeneratorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<salut_token.SalutToken.TokenResponse> getToken(
        salut_token.SalutToken.TokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTokenMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_TOKEN = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TokenGeneratorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TokenGeneratorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_TOKEN:
          serviceImpl.getToken((salut_token.SalutToken.TokenRequest) request,
              (io.grpc.stub.StreamObserver<salut_token.SalutToken.TokenResponse>) responseObserver);
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

  private static abstract class TokenGeneratorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TokenGeneratorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return salut_token.SalutToken.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TokenGenerator");
    }
  }

  private static final class TokenGeneratorFileDescriptorSupplier
      extends TokenGeneratorBaseDescriptorSupplier {
    TokenGeneratorFileDescriptorSupplier() {}
  }

  private static final class TokenGeneratorMethodDescriptorSupplier
      extends TokenGeneratorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TokenGeneratorMethodDescriptorSupplier(String methodName) {
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
      synchronized (TokenGeneratorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TokenGeneratorFileDescriptorSupplier())
              .addMethod(getGetTokenMethod())
              .build();
        }
      }
    }
    return result;
  }
}
