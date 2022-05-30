package shippingcalc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: on_demand/shippingcalc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ShippingcalcGrpc {

  private ShippingcalcGrpc() {}

  public static final String SERVICE_NAME = "shippingcalc.Shippingcalc";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse> getCreateStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateStrategy",
      requestType = shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse> getCreateStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest, shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse> getCreateStrategyMethod;
    if ((getCreateStrategyMethod = ShippingcalcGrpc.getCreateStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getCreateStrategyMethod = ShippingcalcGrpc.getCreateStrategyMethod) == null) {
          ShippingcalcGrpc.getCreateStrategyMethod = getCreateStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest, shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("CreateStrategy"))
              .build();
        }
      }
    }
    return getCreateStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse> getUpdateStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateStrategy",
      requestType = shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse> getUpdateStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest, shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse> getUpdateStrategyMethod;
    if ((getUpdateStrategyMethod = ShippingcalcGrpc.getUpdateStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getUpdateStrategyMethod = ShippingcalcGrpc.getUpdateStrategyMethod) == null) {
          ShippingcalcGrpc.getUpdateStrategyMethod = getUpdateStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest, shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("UpdateStrategy"))
              .build();
        }
      }
    }
    return getUpdateStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.BindStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.BindStrategyResponse> getBindStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "BindStrategy",
      requestType = shippingcalc.ShippingcalcOuterClass.BindStrategyRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.BindStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.BindStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.BindStrategyResponse> getBindStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.BindStrategyRequest, shippingcalc.ShippingcalcOuterClass.BindStrategyResponse> getBindStrategyMethod;
    if ((getBindStrategyMethod = ShippingcalcGrpc.getBindStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getBindStrategyMethod = ShippingcalcGrpc.getBindStrategyMethod) == null) {
          ShippingcalcGrpc.getBindStrategyMethod = getBindStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.BindStrategyRequest, shippingcalc.ShippingcalcOuterClass.BindStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BindStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.BindStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.BindStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("BindStrategy"))
              .build();
        }
      }
    }
    return getBindStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse> getUnbindStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UnbindStrategy",
      requestType = shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse> getUnbindStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest, shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse> getUnbindStrategyMethod;
    if ((getUnbindStrategyMethod = ShippingcalcGrpc.getUnbindStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getUnbindStrategyMethod = ShippingcalcGrpc.getUnbindStrategyMethod) == null) {
          ShippingcalcGrpc.getUnbindStrategyMethod = getUnbindStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest, shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UnbindStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("UnbindStrategy"))
              .build();
        }
      }
    }
    return getUnbindStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest,
      shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse> getGetStrategiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStrategies",
      requestType = shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest,
      shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse> getGetStrategiesMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest, shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse> getGetStrategiesMethod;
    if ((getGetStrategiesMethod = ShippingcalcGrpc.getGetStrategiesMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetStrategiesMethod = ShippingcalcGrpc.getGetStrategiesMethod) == null) {
          ShippingcalcGrpc.getGetStrategiesMethod = getGetStrategiesMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest, shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStrategies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetStrategies"))
              .build();
        }
      }
    }
    return getGetStrategiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.GetStrategyResponse> getGetStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStrategy",
      requestType = shippingcalc.ShippingcalcOuterClass.GetStrategyRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.GetStrategyResponse> getGetStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategyRequest, shippingcalc.ShippingcalcOuterClass.GetStrategyResponse> getGetStrategyMethod;
    if ((getGetStrategyMethod = ShippingcalcGrpc.getGetStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetStrategyMethod = ShippingcalcGrpc.getGetStrategyMethod) == null) {
          ShippingcalcGrpc.getGetStrategyMethod = getGetStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetStrategyRequest, shippingcalc.ShippingcalcOuterClass.GetStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetStrategy"))
              .build();
        }
      }
    }
    return getGetStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest,
      shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse> getGetStrategiesForStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStrategiesForStore",
      requestType = shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest,
      shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse> getGetStrategiesForStoreMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest, shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse> getGetStrategiesForStoreMethod;
    if ((getGetStrategiesForStoreMethod = ShippingcalcGrpc.getGetStrategiesForStoreMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetStrategiesForStoreMethod = ShippingcalcGrpc.getGetStrategiesForStoreMethod) == null) {
          ShippingcalcGrpc.getGetStrategiesForStoreMethod = getGetStrategiesForStoreMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest, shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStrategiesForStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetStrategiesForStore"))
              .build();
        }
      }
    }
    return getGetStrategiesForStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse> getDeleteStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteStrategy",
      requestType = shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest,
      shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse> getDeleteStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest, shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse> getDeleteStrategyMethod;
    if ((getDeleteStrategyMethod = ShippingcalcGrpc.getDeleteStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getDeleteStrategyMethod = ShippingcalcGrpc.getDeleteStrategyMethod) == null) {
          ShippingcalcGrpc.getDeleteStrategyMethod = getDeleteStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest, shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("DeleteStrategy"))
              .build();
        }
      }
    }
    return getDeleteStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.CreateScriptRequest,
      shippingcalc.ShippingcalcOuterClass.CreateScriptResponse> getCreateScriptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateScript",
      requestType = shippingcalc.ShippingcalcOuterClass.CreateScriptRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.CreateScriptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.CreateScriptRequest,
      shippingcalc.ShippingcalcOuterClass.CreateScriptResponse> getCreateScriptMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.CreateScriptRequest, shippingcalc.ShippingcalcOuterClass.CreateScriptResponse> getCreateScriptMethod;
    if ((getCreateScriptMethod = ShippingcalcGrpc.getCreateScriptMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getCreateScriptMethod = ShippingcalcGrpc.getCreateScriptMethod) == null) {
          ShippingcalcGrpc.getCreateScriptMethod = getCreateScriptMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.CreateScriptRequest, shippingcalc.ShippingcalcOuterClass.CreateScriptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateScript"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.CreateScriptRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.CreateScriptResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("CreateScript"))
              .build();
        }
      }
    }
    return getCreateScriptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest,
      shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse> getUpdateScriptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateScript",
      requestType = shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest,
      shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse> getUpdateScriptMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest, shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse> getUpdateScriptMethod;
    if ((getUpdateScriptMethod = ShippingcalcGrpc.getUpdateScriptMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getUpdateScriptMethod = ShippingcalcGrpc.getUpdateScriptMethod) == null) {
          ShippingcalcGrpc.getUpdateScriptMethod = getUpdateScriptMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest, shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateScript"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("UpdateScript"))
              .build();
        }
      }
    }
    return getUpdateScriptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptRequest,
      shippingcalc.ShippingcalcOuterClass.GetScriptResponse> getGetScriptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetScript",
      requestType = shippingcalc.ShippingcalcOuterClass.GetScriptRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetScriptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptRequest,
      shippingcalc.ShippingcalcOuterClass.GetScriptResponse> getGetScriptMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptRequest, shippingcalc.ShippingcalcOuterClass.GetScriptResponse> getGetScriptMethod;
    if ((getGetScriptMethod = ShippingcalcGrpc.getGetScriptMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetScriptMethod = ShippingcalcGrpc.getGetScriptMethod) == null) {
          ShippingcalcGrpc.getGetScriptMethod = getGetScriptMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetScriptRequest, shippingcalc.ShippingcalcOuterClass.GetScriptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetScript"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetScriptRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetScriptResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetScript"))
              .build();
        }
      }
    }
    return getGetScriptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptsRequest,
      shippingcalc.ShippingcalcOuterClass.GetScriptsResponse> getGetScriptsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetScripts",
      requestType = shippingcalc.ShippingcalcOuterClass.GetScriptsRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetScriptsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptsRequest,
      shippingcalc.ShippingcalcOuterClass.GetScriptsResponse> getGetScriptsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptsRequest, shippingcalc.ShippingcalcOuterClass.GetScriptsResponse> getGetScriptsMethod;
    if ((getGetScriptsMethod = ShippingcalcGrpc.getGetScriptsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetScriptsMethod = ShippingcalcGrpc.getGetScriptsMethod) == null) {
          ShippingcalcGrpc.getGetScriptsMethod = getGetScriptsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetScriptsRequest, shippingcalc.ShippingcalcOuterClass.GetScriptsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetScripts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetScriptsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetScriptsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetScripts"))
              .build();
        }
      }
    }
    return getGetScriptsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest,
      shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse> getGetScriptTestResultsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetScriptTestResults",
      requestType = shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest,
      shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse> getGetScriptTestResultsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest, shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse> getGetScriptTestResultsMethod;
    if ((getGetScriptTestResultsMethod = ShippingcalcGrpc.getGetScriptTestResultsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetScriptTestResultsMethod = ShippingcalcGrpc.getGetScriptTestResultsMethod) == null) {
          ShippingcalcGrpc.getGetScriptTestResultsMethod = getGetScriptTestResultsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest, shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetScriptTestResults"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetScriptTestResults"))
              .build();
        }
      }
    }
    return getGetScriptTestResultsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest,
      shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse> getDeleteScriptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteScript",
      requestType = shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest,
      shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse> getDeleteScriptMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest, shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse> getDeleteScriptMethod;
    if ((getDeleteScriptMethod = ShippingcalcGrpc.getDeleteScriptMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getDeleteScriptMethod = ShippingcalcGrpc.getDeleteScriptMethod) == null) {
          ShippingcalcGrpc.getDeleteScriptMethod = getDeleteScriptMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest, shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteScript"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("DeleteScript"))
              .build();
        }
      }
    }
    return getDeleteScriptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest,
      shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse> getGetDeliveryPriceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDeliveryPrice",
      requestType = shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest,
      shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse> getGetDeliveryPriceMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest, shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse> getGetDeliveryPriceMethod;
    if ((getGetDeliveryPriceMethod = ShippingcalcGrpc.getGetDeliveryPriceMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetDeliveryPriceMethod = ShippingcalcGrpc.getGetDeliveryPriceMethod) == null) {
          ShippingcalcGrpc.getGetDeliveryPriceMethod = getGetDeliveryPriceMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest, shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDeliveryPrice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetDeliveryPrice"))
              .build();
        }
      }
    }
    return getGetDeliveryPriceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest,
      shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse> getSetIntervalsSurgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetIntervalsSurge",
      requestType = shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest,
      shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse> getSetIntervalsSurgeMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest, shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse> getSetIntervalsSurgeMethod;
    if ((getSetIntervalsSurgeMethod = ShippingcalcGrpc.getSetIntervalsSurgeMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getSetIntervalsSurgeMethod = ShippingcalcGrpc.getSetIntervalsSurgeMethod) == null) {
          ShippingcalcGrpc.getSetIntervalsSurgeMethod = getSetIntervalsSurgeMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest, shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetIntervalsSurge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("SetIntervalsSurge"))
              .build();
        }
      }
    }
    return getSetIntervalsSurgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest,
      shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse> getGetIntervalsSurgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetIntervalsSurge",
      requestType = shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest,
      shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse> getGetIntervalsSurgeMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest, shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse> getGetIntervalsSurgeMethod;
    if ((getGetIntervalsSurgeMethod = ShippingcalcGrpc.getGetIntervalsSurgeMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetIntervalsSurgeMethod = ShippingcalcGrpc.getGetIntervalsSurgeMethod) == null) {
          ShippingcalcGrpc.getGetIntervalsSurgeMethod = getGetIntervalsSurgeMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest, shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetIntervalsSurge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetIntervalsSurge"))
              .build();
        }
      }
    }
    return getGetIntervalsSurgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest,
      shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse> getSetSwitchbackExperimentsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetSwitchbackExperiments",
      requestType = shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest,
      shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse> getSetSwitchbackExperimentsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest, shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse> getSetSwitchbackExperimentsMethod;
    if ((getSetSwitchbackExperimentsMethod = ShippingcalcGrpc.getSetSwitchbackExperimentsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getSetSwitchbackExperimentsMethod = ShippingcalcGrpc.getSetSwitchbackExperimentsMethod) == null) {
          ShippingcalcGrpc.getSetSwitchbackExperimentsMethod = getSetSwitchbackExperimentsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest, shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetSwitchbackExperiments"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("SetSwitchbackExperiments"))
              .build();
        }
      }
    }
    return getSetSwitchbackExperimentsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest,
      shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse> getGetSwitchbackExperimentsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSwitchbackExperiments",
      requestType = shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest.class,
      responseType = shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest,
      shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse> getGetSwitchbackExperimentsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest, shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse> getGetSwitchbackExperimentsMethod;
    if ((getGetSwitchbackExperimentsMethod = ShippingcalcGrpc.getGetSwitchbackExperimentsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetSwitchbackExperimentsMethod = ShippingcalcGrpc.getGetSwitchbackExperimentsMethod) == null) {
          ShippingcalcGrpc.getGetSwitchbackExperimentsMethod = getGetSwitchbackExperimentsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest, shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSwitchbackExperiments"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetSwitchbackExperiments"))
              .build();
        }
      }
    }
    return getGetSwitchbackExperimentsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ShippingcalcStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShippingcalcStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShippingcalcStub>() {
        @java.lang.Override
        public ShippingcalcStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShippingcalcStub(channel, callOptions);
        }
      };
    return ShippingcalcStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ShippingcalcBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShippingcalcBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShippingcalcBlockingStub>() {
        @java.lang.Override
        public ShippingcalcBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShippingcalcBlockingStub(channel, callOptions);
        }
      };
    return ShippingcalcBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ShippingcalcFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ShippingcalcFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ShippingcalcFutureStub>() {
        @java.lang.Override
        public ShippingcalcFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ShippingcalcFutureStub(channel, callOptions);
        }
      };
    return ShippingcalcFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ShippingcalcImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Создать стратегию
     * </pre>
     */
    public void createStrategy(shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Обновить стратегию
     * </pre>
     */
    public void updateStrategy(shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Прикрепить стратегию к магазину
     * </pre>
     */
    public void bindStrategy(shippingcalc.ShippingcalcOuterClass.BindStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.BindStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBindStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Открепить стратегию от магазина
     * </pre>
     */
    public void unbindStrategy(shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnbindStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все существующие стратегии
     * </pre>
     */
    public void getStrategies(shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStrategiesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить стратегию по идентификатору
     * </pre>
     */
    public void getStrategy(shippingcalc.ShippingcalcOuterClass.GetStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить стратегии для магазина
     * </pre>
     */
    public void getStrategiesForStore(shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStrategiesForStoreMethod(), responseObserver);
    }

    /**
     * <pre>
     * Удалить стратегию по идентификатору
     * </pre>
     */
    public void deleteStrategy(shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Создать скрипт
     * </pre>
     */
    public void createScript(shippingcalc.ShippingcalcOuterClass.CreateScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.CreateScriptResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateScriptMethod(), responseObserver);
    }

    /**
     * <pre>
     * Обновить скрипт
     * </pre>
     */
    public void updateScript(shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateScriptMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить скрипт по идентификатору
     * </pre>
     */
    public void getScript(shippingcalc.ShippingcalcOuterClass.GetScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetScriptMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все существующие скрипты
     * </pre>
     */
    public void getScripts(shippingcalc.ShippingcalcOuterClass.GetScriptsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetScriptsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить результаты теста скрипта
     * </pre>
     */
    public void getScriptTestResults(shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetScriptTestResultsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Удалить скрипт по идентификатору
     * </pre>
     */
    public void deleteScript(shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteScriptMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить стоимость доставки
     * </pre>
     */
    public void getDeliveryPrice(shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDeliveryPriceMethod(), responseObserver);
    }

    /**
     * <pre>
     * Установить сразу все интервалы surge
     * </pre>
     */
    public void setIntervalsSurge(shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetIntervalsSurgeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все интервалы surge
     * </pre>
     */
    public void getIntervalsSurge(shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetIntervalsSurgeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Установить сразу все интервалы switchback experiments for surge
     * </pre>
     */
    public void setSwitchbackExperiments(shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetSwitchbackExperimentsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все интервалы switchback experiments for surge
     * </pre>
     */
    public void getSwitchbackExperiments(shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSwitchbackExperimentsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest,
                shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse>(
                  this, METHODID_CREATE_STRATEGY)))
          .addMethod(
            getUpdateStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest,
                shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse>(
                  this, METHODID_UPDATE_STRATEGY)))
          .addMethod(
            getBindStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.BindStrategyRequest,
                shippingcalc.ShippingcalcOuterClass.BindStrategyResponse>(
                  this, METHODID_BIND_STRATEGY)))
          .addMethod(
            getUnbindStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest,
                shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse>(
                  this, METHODID_UNBIND_STRATEGY)))
          .addMethod(
            getGetStrategiesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest,
                shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse>(
                  this, METHODID_GET_STRATEGIES)))
          .addMethod(
            getGetStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetStrategyRequest,
                shippingcalc.ShippingcalcOuterClass.GetStrategyResponse>(
                  this, METHODID_GET_STRATEGY)))
          .addMethod(
            getGetStrategiesForStoreMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest,
                shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse>(
                  this, METHODID_GET_STRATEGIES_FOR_STORE)))
          .addMethod(
            getDeleteStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest,
                shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse>(
                  this, METHODID_DELETE_STRATEGY)))
          .addMethod(
            getCreateScriptMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.CreateScriptRequest,
                shippingcalc.ShippingcalcOuterClass.CreateScriptResponse>(
                  this, METHODID_CREATE_SCRIPT)))
          .addMethod(
            getUpdateScriptMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest,
                shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse>(
                  this, METHODID_UPDATE_SCRIPT)))
          .addMethod(
            getGetScriptMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetScriptRequest,
                shippingcalc.ShippingcalcOuterClass.GetScriptResponse>(
                  this, METHODID_GET_SCRIPT)))
          .addMethod(
            getGetScriptsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetScriptsRequest,
                shippingcalc.ShippingcalcOuterClass.GetScriptsResponse>(
                  this, METHODID_GET_SCRIPTS)))
          .addMethod(
            getGetScriptTestResultsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest,
                shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse>(
                  this, METHODID_GET_SCRIPT_TEST_RESULTS)))
          .addMethod(
            getDeleteScriptMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest,
                shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse>(
                  this, METHODID_DELETE_SCRIPT)))
          .addMethod(
            getGetDeliveryPriceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest,
                shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse>(
                  this, METHODID_GET_DELIVERY_PRICE)))
          .addMethod(
            getSetIntervalsSurgeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest,
                shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse>(
                  this, METHODID_SET_INTERVALS_SURGE)))
          .addMethod(
            getGetIntervalsSurgeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest,
                shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse>(
                  this, METHODID_GET_INTERVALS_SURGE)))
          .addMethod(
            getSetSwitchbackExperimentsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest,
                shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse>(
                  this, METHODID_SET_SWITCHBACK_EXPERIMENTS)))
          .addMethod(
            getGetSwitchbackExperimentsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest,
                shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse>(
                  this, METHODID_GET_SWITCHBACK_EXPERIMENTS)))
          .build();
    }
  }

  /**
   */
  public static final class ShippingcalcStub extends io.grpc.stub.AbstractAsyncStub<ShippingcalcStub> {
    private ShippingcalcStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShippingcalcStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShippingcalcStub(channel, callOptions);
    }

    /**
     * <pre>
     * Создать стратегию
     * </pre>
     */
    public void createStrategy(shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Обновить стратегию
     * </pre>
     */
    public void updateStrategy(shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Прикрепить стратегию к магазину
     * </pre>
     */
    public void bindStrategy(shippingcalc.ShippingcalcOuterClass.BindStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.BindStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBindStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Открепить стратегию от магазина
     * </pre>
     */
    public void unbindStrategy(shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnbindStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все существующие стратегии
     * </pre>
     */
    public void getStrategies(shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStrategiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить стратегию по идентификатору
     * </pre>
     */
    public void getStrategy(shippingcalc.ShippingcalcOuterClass.GetStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить стратегии для магазина
     * </pre>
     */
    public void getStrategiesForStore(shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStrategiesForStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Удалить стратегию по идентификатору
     * </pre>
     */
    public void deleteStrategy(shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Создать скрипт
     * </pre>
     */
    public void createScript(shippingcalc.ShippingcalcOuterClass.CreateScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.CreateScriptResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateScriptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Обновить скрипт
     * </pre>
     */
    public void updateScript(shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateScriptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить скрипт по идентификатору
     * </pre>
     */
    public void getScript(shippingcalc.ShippingcalcOuterClass.GetScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetScriptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все существующие скрипты
     * </pre>
     */
    public void getScripts(shippingcalc.ShippingcalcOuterClass.GetScriptsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetScriptsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить результаты теста скрипта
     * </pre>
     */
    public void getScriptTestResults(shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetScriptTestResultsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Удалить скрипт по идентификатору
     * </pre>
     */
    public void deleteScript(shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteScriptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить стоимость доставки
     * </pre>
     */
    public void getDeliveryPrice(shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDeliveryPriceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Установить сразу все интервалы surge
     * </pre>
     */
    public void setIntervalsSurge(shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetIntervalsSurgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все интервалы surge
     * </pre>
     */
    public void getIntervalsSurge(shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetIntervalsSurgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Установить сразу все интервалы switchback experiments for surge
     * </pre>
     */
    public void setSwitchbackExperiments(shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetSwitchbackExperimentsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все интервалы switchback experiments for surge
     * </pre>
     */
    public void getSwitchbackExperiments(shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSwitchbackExperimentsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ShippingcalcBlockingStub extends io.grpc.stub.AbstractBlockingStub<ShippingcalcBlockingStub> {
    private ShippingcalcBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShippingcalcBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShippingcalcBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Создать стратегию
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse createStrategy(shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Обновить стратегию
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse updateStrategy(shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Прикрепить стратегию к магазину
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.BindStrategyResponse bindStrategy(shippingcalc.ShippingcalcOuterClass.BindStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBindStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Открепить стратегию от магазина
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse unbindStrategy(shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnbindStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все существующие стратегии
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse getStrategies(shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStrategiesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить стратегию по идентификатору
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetStrategyResponse getStrategy(shippingcalc.ShippingcalcOuterClass.GetStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить стратегии для магазина
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse getStrategiesForStore(shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStrategiesForStoreMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Удалить стратегию по идентификатору
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse deleteStrategy(shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Создать скрипт
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.CreateScriptResponse createScript(shippingcalc.ShippingcalcOuterClass.CreateScriptRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateScriptMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Обновить скрипт
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse updateScript(shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateScriptMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить скрипт по идентификатору
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetScriptResponse getScript(shippingcalc.ShippingcalcOuterClass.GetScriptRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetScriptMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все существующие скрипты
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetScriptsResponse getScripts(shippingcalc.ShippingcalcOuterClass.GetScriptsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetScriptsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить результаты теста скрипта
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse getScriptTestResults(shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetScriptTestResultsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Удалить скрипт по идентификатору
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse deleteScript(shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteScriptMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить стоимость доставки
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse getDeliveryPrice(shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDeliveryPriceMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Установить сразу все интервалы surge
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse setIntervalsSurge(shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetIntervalsSurgeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все интервалы surge
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse getIntervalsSurge(shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetIntervalsSurgeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Установить сразу все интервалы switchback experiments for surge
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse setSwitchbackExperiments(shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetSwitchbackExperimentsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все интервалы switchback experiments for surge
     * </pre>
     */
    public shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse getSwitchbackExperiments(shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSwitchbackExperimentsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ShippingcalcFutureStub extends io.grpc.stub.AbstractFutureStub<ShippingcalcFutureStub> {
    private ShippingcalcFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ShippingcalcFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ShippingcalcFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Создать стратегию
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse> createStrategy(
        shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Обновить стратегию
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse> updateStrategy(
        shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Прикрепить стратегию к магазину
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.BindStrategyResponse> bindStrategy(
        shippingcalc.ShippingcalcOuterClass.BindStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBindStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Открепить стратегию от магазина
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse> unbindStrategy(
        shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnbindStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все существующие стратегии
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse> getStrategies(
        shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStrategiesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить стратегию по идентификатору
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetStrategyResponse> getStrategy(
        shippingcalc.ShippingcalcOuterClass.GetStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить стратегии для магазина
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse> getStrategiesForStore(
        shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStrategiesForStoreMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Удалить стратегию по идентификатору
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse> deleteStrategy(
        shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Создать скрипт
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.CreateScriptResponse> createScript(
        shippingcalc.ShippingcalcOuterClass.CreateScriptRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateScriptMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Обновить скрипт
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse> updateScript(
        shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateScriptMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить скрипт по идентификатору
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetScriptResponse> getScript(
        shippingcalc.ShippingcalcOuterClass.GetScriptRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetScriptMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все существующие скрипты
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetScriptsResponse> getScripts(
        shippingcalc.ShippingcalcOuterClass.GetScriptsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetScriptsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить результаты теста скрипта
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse> getScriptTestResults(
        shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetScriptTestResultsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Удалить скрипт по идентификатору
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse> deleteScript(
        shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteScriptMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить стоимость доставки
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse> getDeliveryPrice(
        shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDeliveryPriceMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Установить сразу все интервалы surge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse> setIntervalsSurge(
        shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetIntervalsSurgeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все интервалы surge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse> getIntervalsSurge(
        shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetIntervalsSurgeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Установить сразу все интервалы switchback experiments for surge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse> setSwitchbackExperiments(
        shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetSwitchbackExperimentsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все интервалы switchback experiments for surge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse> getSwitchbackExperiments(
        shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSwitchbackExperimentsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_STRATEGY = 0;
  private static final int METHODID_UPDATE_STRATEGY = 1;
  private static final int METHODID_BIND_STRATEGY = 2;
  private static final int METHODID_UNBIND_STRATEGY = 3;
  private static final int METHODID_GET_STRATEGIES = 4;
  private static final int METHODID_GET_STRATEGY = 5;
  private static final int METHODID_GET_STRATEGIES_FOR_STORE = 6;
  private static final int METHODID_DELETE_STRATEGY = 7;
  private static final int METHODID_CREATE_SCRIPT = 8;
  private static final int METHODID_UPDATE_SCRIPT = 9;
  private static final int METHODID_GET_SCRIPT = 10;
  private static final int METHODID_GET_SCRIPTS = 11;
  private static final int METHODID_GET_SCRIPT_TEST_RESULTS = 12;
  private static final int METHODID_DELETE_SCRIPT = 13;
  private static final int METHODID_GET_DELIVERY_PRICE = 14;
  private static final int METHODID_SET_INTERVALS_SURGE = 15;
  private static final int METHODID_GET_INTERVALS_SURGE = 16;
  private static final int METHODID_SET_SWITCHBACK_EXPERIMENTS = 17;
  private static final int METHODID_GET_SWITCHBACK_EXPERIMENTS = 18;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ShippingcalcImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ShippingcalcImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_STRATEGY:
          serviceImpl.createStrategy((shippingcalc.ShippingcalcOuterClass.CreateStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.CreateStrategyResponse>) responseObserver);
          break;
        case METHODID_UPDATE_STRATEGY:
          serviceImpl.updateStrategy((shippingcalc.ShippingcalcOuterClass.UpdateStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UpdateStrategyResponse>) responseObserver);
          break;
        case METHODID_BIND_STRATEGY:
          serviceImpl.bindStrategy((shippingcalc.ShippingcalcOuterClass.BindStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.BindStrategyResponse>) responseObserver);
          break;
        case METHODID_UNBIND_STRATEGY:
          serviceImpl.unbindStrategy((shippingcalc.ShippingcalcOuterClass.UnbindStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UnbindStrategyResponse>) responseObserver);
          break;
        case METHODID_GET_STRATEGIES:
          serviceImpl.getStrategies((shippingcalc.ShippingcalcOuterClass.GetStrategiesRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategiesResponse>) responseObserver);
          break;
        case METHODID_GET_STRATEGY:
          serviceImpl.getStrategy((shippingcalc.ShippingcalcOuterClass.GetStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategyResponse>) responseObserver);
          break;
        case METHODID_GET_STRATEGIES_FOR_STORE:
          serviceImpl.getStrategiesForStore((shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetStrategiesForStoreResponse>) responseObserver);
          break;
        case METHODID_DELETE_STRATEGY:
          serviceImpl.deleteStrategy((shippingcalc.ShippingcalcOuterClass.DeleteStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.DeleteStrategyResponse>) responseObserver);
          break;
        case METHODID_CREATE_SCRIPT:
          serviceImpl.createScript((shippingcalc.ShippingcalcOuterClass.CreateScriptRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.CreateScriptResponse>) responseObserver);
          break;
        case METHODID_UPDATE_SCRIPT:
          serviceImpl.updateScript((shippingcalc.ShippingcalcOuterClass.UpdateScriptRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.UpdateScriptResponse>) responseObserver);
          break;
        case METHODID_GET_SCRIPT:
          serviceImpl.getScript((shippingcalc.ShippingcalcOuterClass.GetScriptRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptResponse>) responseObserver);
          break;
        case METHODID_GET_SCRIPTS:
          serviceImpl.getScripts((shippingcalc.ShippingcalcOuterClass.GetScriptsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptsResponse>) responseObserver);
          break;
        case METHODID_GET_SCRIPT_TEST_RESULTS:
          serviceImpl.getScriptTestResults((shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetScriptTestResultsResponse>) responseObserver);
          break;
        case METHODID_DELETE_SCRIPT:
          serviceImpl.deleteScript((shippingcalc.ShippingcalcOuterClass.DeleteScriptRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.DeleteScriptResponse>) responseObserver);
          break;
        case METHODID_GET_DELIVERY_PRICE:
          serviceImpl.getDeliveryPrice((shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetDeliveryPriceResponse>) responseObserver);
          break;
        case METHODID_SET_INTERVALS_SURGE:
          serviceImpl.setIntervalsSurge((shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.SetIntervalsSurgeResponse>) responseObserver);
          break;
        case METHODID_GET_INTERVALS_SURGE:
          serviceImpl.getIntervalsSurge((shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetIntervalsSurgeResponse>) responseObserver);
          break;
        case METHODID_SET_SWITCHBACK_EXPERIMENTS:
          serviceImpl.setSwitchbackExperiments((shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.SetSwitchbackExperimentsResponse>) responseObserver);
          break;
        case METHODID_GET_SWITCHBACK_EXPERIMENTS:
          serviceImpl.getSwitchbackExperiments((shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.ShippingcalcOuterClass.GetSwitchbackExperimentsResponse>) responseObserver);
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

  private static abstract class ShippingcalcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ShippingcalcBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return shippingcalc.ShippingcalcOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Shippingcalc");
    }
  }

  private static final class ShippingcalcFileDescriptorSupplier
      extends ShippingcalcBaseDescriptorSupplier {
    ShippingcalcFileDescriptorSupplier() {}
  }

  private static final class ShippingcalcMethodDescriptorSupplier
      extends ShippingcalcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ShippingcalcMethodDescriptorSupplier(String methodName) {
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
      synchronized (ShippingcalcGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ShippingcalcFileDescriptorSupplier())
              .addMethod(getCreateStrategyMethod())
              .addMethod(getUpdateStrategyMethod())
              .addMethod(getBindStrategyMethod())
              .addMethod(getUnbindStrategyMethod())
              .addMethod(getGetStrategiesMethod())
              .addMethod(getGetStrategyMethod())
              .addMethod(getGetStrategiesForStoreMethod())
              .addMethod(getDeleteStrategyMethod())
              .addMethod(getCreateScriptMethod())
              .addMethod(getUpdateScriptMethod())
              .addMethod(getGetScriptMethod())
              .addMethod(getGetScriptsMethod())
              .addMethod(getGetScriptTestResultsMethod())
              .addMethod(getDeleteScriptMethod())
              .addMethod(getGetDeliveryPriceMethod())
              .addMethod(getSetIntervalsSurgeMethod())
              .addMethod(getGetIntervalsSurgeMethod())
              .addMethod(getSetSwitchbackExperimentsMethod())
              .addMethod(getGetSwitchbackExperimentsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
