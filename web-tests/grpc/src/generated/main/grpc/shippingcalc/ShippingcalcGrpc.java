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
  private static volatile io.grpc.MethodDescriptor<shippingcalc.CreateStrategyRequest,
      shippingcalc.CreateStrategyResponse> getCreateStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateStrategy",
      requestType = shippingcalc.CreateStrategyRequest.class,
      responseType = shippingcalc.CreateStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.CreateStrategyRequest,
      shippingcalc.CreateStrategyResponse> getCreateStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.CreateStrategyRequest, shippingcalc.CreateStrategyResponse> getCreateStrategyMethod;
    if ((getCreateStrategyMethod = ShippingcalcGrpc.getCreateStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getCreateStrategyMethod = ShippingcalcGrpc.getCreateStrategyMethod) == null) {
          ShippingcalcGrpc.getCreateStrategyMethod = getCreateStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.CreateStrategyRequest, shippingcalc.CreateStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.CreateStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.CreateStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("CreateStrategy"))
              .build();
        }
      }
    }
    return getCreateStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.UpdateStrategyRequest,
      shippingcalc.UpdateStrategyResponse> getUpdateStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateStrategy",
      requestType = shippingcalc.UpdateStrategyRequest.class,
      responseType = shippingcalc.UpdateStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.UpdateStrategyRequest,
      shippingcalc.UpdateStrategyResponse> getUpdateStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.UpdateStrategyRequest, shippingcalc.UpdateStrategyResponse> getUpdateStrategyMethod;
    if ((getUpdateStrategyMethod = ShippingcalcGrpc.getUpdateStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getUpdateStrategyMethod = ShippingcalcGrpc.getUpdateStrategyMethod) == null) {
          ShippingcalcGrpc.getUpdateStrategyMethod = getUpdateStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.UpdateStrategyRequest, shippingcalc.UpdateStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.UpdateStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.UpdateStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("UpdateStrategy"))
              .build();
        }
      }
    }
    return getUpdateStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.BindStrategyRequest,
      shippingcalc.BindStrategyResponse> getBindStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "BindStrategy",
      requestType = shippingcalc.BindStrategyRequest.class,
      responseType = shippingcalc.BindStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.BindStrategyRequest,
      shippingcalc.BindStrategyResponse> getBindStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.BindStrategyRequest, shippingcalc.BindStrategyResponse> getBindStrategyMethod;
    if ((getBindStrategyMethod = ShippingcalcGrpc.getBindStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getBindStrategyMethod = ShippingcalcGrpc.getBindStrategyMethod) == null) {
          ShippingcalcGrpc.getBindStrategyMethod = getBindStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.BindStrategyRequest, shippingcalc.BindStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BindStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.BindStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.BindStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("BindStrategy"))
              .build();
        }
      }
    }
    return getBindStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.UnbindStrategyRequest,
      shippingcalc.UnbindStrategyResponse> getUnbindStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UnbindStrategy",
      requestType = shippingcalc.UnbindStrategyRequest.class,
      responseType = shippingcalc.UnbindStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.UnbindStrategyRequest,
      shippingcalc.UnbindStrategyResponse> getUnbindStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.UnbindStrategyRequest, shippingcalc.UnbindStrategyResponse> getUnbindStrategyMethod;
    if ((getUnbindStrategyMethod = ShippingcalcGrpc.getUnbindStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getUnbindStrategyMethod = ShippingcalcGrpc.getUnbindStrategyMethod) == null) {
          ShippingcalcGrpc.getUnbindStrategyMethod = getUnbindStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.UnbindStrategyRequest, shippingcalc.UnbindStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UnbindStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.UnbindStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.UnbindStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("UnbindStrategy"))
              .build();
        }
      }
    }
    return getUnbindStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetStrategiesRequest,
      shippingcalc.GetStrategiesResponse> getGetStrategiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStrategies",
      requestType = shippingcalc.GetStrategiesRequest.class,
      responseType = shippingcalc.GetStrategiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetStrategiesRequest,
      shippingcalc.GetStrategiesResponse> getGetStrategiesMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetStrategiesRequest, shippingcalc.GetStrategiesResponse> getGetStrategiesMethod;
    if ((getGetStrategiesMethod = ShippingcalcGrpc.getGetStrategiesMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetStrategiesMethod = ShippingcalcGrpc.getGetStrategiesMethod) == null) {
          ShippingcalcGrpc.getGetStrategiesMethod = getGetStrategiesMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetStrategiesRequest, shippingcalc.GetStrategiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStrategies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetStrategiesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetStrategiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetStrategies"))
              .build();
        }
      }
    }
    return getGetStrategiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetStrategyRequest,
      shippingcalc.GetStrategyResponse> getGetStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStrategy",
      requestType = shippingcalc.GetStrategyRequest.class,
      responseType = shippingcalc.GetStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetStrategyRequest,
      shippingcalc.GetStrategyResponse> getGetStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetStrategyRequest, shippingcalc.GetStrategyResponse> getGetStrategyMethod;
    if ((getGetStrategyMethod = ShippingcalcGrpc.getGetStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetStrategyMethod = ShippingcalcGrpc.getGetStrategyMethod) == null) {
          ShippingcalcGrpc.getGetStrategyMethod = getGetStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetStrategyRequest, shippingcalc.GetStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetStrategy"))
              .build();
        }
      }
    }
    return getGetStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetStrategiesForStoreRequest,
      shippingcalc.GetStrategiesForStoreResponse> getGetStrategiesForStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStrategiesForStore",
      requestType = shippingcalc.GetStrategiesForStoreRequest.class,
      responseType = shippingcalc.GetStrategiesForStoreResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetStrategiesForStoreRequest,
      shippingcalc.GetStrategiesForStoreResponse> getGetStrategiesForStoreMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetStrategiesForStoreRequest, shippingcalc.GetStrategiesForStoreResponse> getGetStrategiesForStoreMethod;
    if ((getGetStrategiesForStoreMethod = ShippingcalcGrpc.getGetStrategiesForStoreMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetStrategiesForStoreMethod = ShippingcalcGrpc.getGetStrategiesForStoreMethod) == null) {
          ShippingcalcGrpc.getGetStrategiesForStoreMethod = getGetStrategiesForStoreMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetStrategiesForStoreRequest, shippingcalc.GetStrategiesForStoreResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStrategiesForStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetStrategiesForStoreRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetStrategiesForStoreResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetStrategiesForStore"))
              .build();
        }
      }
    }
    return getGetStrategiesForStoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.DeleteStrategyRequest,
      shippingcalc.DeleteStrategyResponse> getDeleteStrategyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteStrategy",
      requestType = shippingcalc.DeleteStrategyRequest.class,
      responseType = shippingcalc.DeleteStrategyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.DeleteStrategyRequest,
      shippingcalc.DeleteStrategyResponse> getDeleteStrategyMethod() {
    io.grpc.MethodDescriptor<shippingcalc.DeleteStrategyRequest, shippingcalc.DeleteStrategyResponse> getDeleteStrategyMethod;
    if ((getDeleteStrategyMethod = ShippingcalcGrpc.getDeleteStrategyMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getDeleteStrategyMethod = ShippingcalcGrpc.getDeleteStrategyMethod) == null) {
          ShippingcalcGrpc.getDeleteStrategyMethod = getDeleteStrategyMethod =
              io.grpc.MethodDescriptor.<shippingcalc.DeleteStrategyRequest, shippingcalc.DeleteStrategyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteStrategy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.DeleteStrategyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.DeleteStrategyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("DeleteStrategy"))
              .build();
        }
      }
    }
    return getDeleteStrategyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.CreateScriptRequest,
      shippingcalc.CreateScriptResponse> getCreateScriptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateScript",
      requestType = shippingcalc.CreateScriptRequest.class,
      responseType = shippingcalc.CreateScriptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.CreateScriptRequest,
      shippingcalc.CreateScriptResponse> getCreateScriptMethod() {
    io.grpc.MethodDescriptor<shippingcalc.CreateScriptRequest, shippingcalc.CreateScriptResponse> getCreateScriptMethod;
    if ((getCreateScriptMethod = ShippingcalcGrpc.getCreateScriptMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getCreateScriptMethod = ShippingcalcGrpc.getCreateScriptMethod) == null) {
          ShippingcalcGrpc.getCreateScriptMethod = getCreateScriptMethod =
              io.grpc.MethodDescriptor.<shippingcalc.CreateScriptRequest, shippingcalc.CreateScriptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateScript"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.CreateScriptRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.CreateScriptResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("CreateScript"))
              .build();
        }
      }
    }
    return getCreateScriptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.UpdateScriptRequest,
      shippingcalc.UpdateScriptResponse> getUpdateScriptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateScript",
      requestType = shippingcalc.UpdateScriptRequest.class,
      responseType = shippingcalc.UpdateScriptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.UpdateScriptRequest,
      shippingcalc.UpdateScriptResponse> getUpdateScriptMethod() {
    io.grpc.MethodDescriptor<shippingcalc.UpdateScriptRequest, shippingcalc.UpdateScriptResponse> getUpdateScriptMethod;
    if ((getUpdateScriptMethod = ShippingcalcGrpc.getUpdateScriptMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getUpdateScriptMethod = ShippingcalcGrpc.getUpdateScriptMethod) == null) {
          ShippingcalcGrpc.getUpdateScriptMethod = getUpdateScriptMethod =
              io.grpc.MethodDescriptor.<shippingcalc.UpdateScriptRequest, shippingcalc.UpdateScriptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateScript"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.UpdateScriptRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.UpdateScriptResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("UpdateScript"))
              .build();
        }
      }
    }
    return getUpdateScriptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetScriptRequest,
      shippingcalc.GetScriptResponse> getGetScriptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetScript",
      requestType = shippingcalc.GetScriptRequest.class,
      responseType = shippingcalc.GetScriptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetScriptRequest,
      shippingcalc.GetScriptResponse> getGetScriptMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetScriptRequest, shippingcalc.GetScriptResponse> getGetScriptMethod;
    if ((getGetScriptMethod = ShippingcalcGrpc.getGetScriptMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetScriptMethod = ShippingcalcGrpc.getGetScriptMethod) == null) {
          ShippingcalcGrpc.getGetScriptMethod = getGetScriptMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetScriptRequest, shippingcalc.GetScriptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetScript"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetScriptRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetScriptResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetScript"))
              .build();
        }
      }
    }
    return getGetScriptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetScriptsRequest,
      shippingcalc.GetScriptsResponse> getGetScriptsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetScripts",
      requestType = shippingcalc.GetScriptsRequest.class,
      responseType = shippingcalc.GetScriptsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetScriptsRequest,
      shippingcalc.GetScriptsResponse> getGetScriptsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetScriptsRequest, shippingcalc.GetScriptsResponse> getGetScriptsMethod;
    if ((getGetScriptsMethod = ShippingcalcGrpc.getGetScriptsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetScriptsMethod = ShippingcalcGrpc.getGetScriptsMethod) == null) {
          ShippingcalcGrpc.getGetScriptsMethod = getGetScriptsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetScriptsRequest, shippingcalc.GetScriptsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetScripts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetScriptsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetScriptsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetScripts"))
              .build();
        }
      }
    }
    return getGetScriptsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetScriptTestResultsRequest,
      shippingcalc.GetScriptTestResultsResponse> getGetScriptTestResultsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetScriptTestResults",
      requestType = shippingcalc.GetScriptTestResultsRequest.class,
      responseType = shippingcalc.GetScriptTestResultsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetScriptTestResultsRequest,
      shippingcalc.GetScriptTestResultsResponse> getGetScriptTestResultsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetScriptTestResultsRequest, shippingcalc.GetScriptTestResultsResponse> getGetScriptTestResultsMethod;
    if ((getGetScriptTestResultsMethod = ShippingcalcGrpc.getGetScriptTestResultsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetScriptTestResultsMethod = ShippingcalcGrpc.getGetScriptTestResultsMethod) == null) {
          ShippingcalcGrpc.getGetScriptTestResultsMethod = getGetScriptTestResultsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetScriptTestResultsRequest, shippingcalc.GetScriptTestResultsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetScriptTestResults"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetScriptTestResultsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetScriptTestResultsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetScriptTestResults"))
              .build();
        }
      }
    }
    return getGetScriptTestResultsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.DeleteScriptRequest,
      shippingcalc.DeleteScriptResponse> getDeleteScriptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteScript",
      requestType = shippingcalc.DeleteScriptRequest.class,
      responseType = shippingcalc.DeleteScriptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.DeleteScriptRequest,
      shippingcalc.DeleteScriptResponse> getDeleteScriptMethod() {
    io.grpc.MethodDescriptor<shippingcalc.DeleteScriptRequest, shippingcalc.DeleteScriptResponse> getDeleteScriptMethod;
    if ((getDeleteScriptMethod = ShippingcalcGrpc.getDeleteScriptMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getDeleteScriptMethod = ShippingcalcGrpc.getDeleteScriptMethod) == null) {
          ShippingcalcGrpc.getDeleteScriptMethod = getDeleteScriptMethod =
              io.grpc.MethodDescriptor.<shippingcalc.DeleteScriptRequest, shippingcalc.DeleteScriptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteScript"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.DeleteScriptRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.DeleteScriptResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("DeleteScript"))
              .build();
        }
      }
    }
    return getDeleteScriptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetDeliveryPriceRequest,
      shippingcalc.GetDeliveryPriceResponse> getGetDeliveryPriceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDeliveryPrice",
      requestType = shippingcalc.GetDeliveryPriceRequest.class,
      responseType = shippingcalc.GetDeliveryPriceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetDeliveryPriceRequest,
      shippingcalc.GetDeliveryPriceResponse> getGetDeliveryPriceMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetDeliveryPriceRequest, shippingcalc.GetDeliveryPriceResponse> getGetDeliveryPriceMethod;
    if ((getGetDeliveryPriceMethod = ShippingcalcGrpc.getGetDeliveryPriceMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetDeliveryPriceMethod = ShippingcalcGrpc.getGetDeliveryPriceMethod) == null) {
          ShippingcalcGrpc.getGetDeliveryPriceMethod = getGetDeliveryPriceMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetDeliveryPriceRequest, shippingcalc.GetDeliveryPriceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDeliveryPrice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetDeliveryPriceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetDeliveryPriceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetDeliveryPrice"))
              .build();
        }
      }
    }
    return getGetDeliveryPriceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.SetIntervalsSurgeRequest,
      shippingcalc.SetIntervalsSurgeResponse> getSetIntervalsSurgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetIntervalsSurge",
      requestType = shippingcalc.SetIntervalsSurgeRequest.class,
      responseType = shippingcalc.SetIntervalsSurgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.SetIntervalsSurgeRequest,
      shippingcalc.SetIntervalsSurgeResponse> getSetIntervalsSurgeMethod() {
    io.grpc.MethodDescriptor<shippingcalc.SetIntervalsSurgeRequest, shippingcalc.SetIntervalsSurgeResponse> getSetIntervalsSurgeMethod;
    if ((getSetIntervalsSurgeMethod = ShippingcalcGrpc.getSetIntervalsSurgeMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getSetIntervalsSurgeMethod = ShippingcalcGrpc.getSetIntervalsSurgeMethod) == null) {
          ShippingcalcGrpc.getSetIntervalsSurgeMethod = getSetIntervalsSurgeMethod =
              io.grpc.MethodDescriptor.<shippingcalc.SetIntervalsSurgeRequest, shippingcalc.SetIntervalsSurgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetIntervalsSurge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.SetIntervalsSurgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.SetIntervalsSurgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("SetIntervalsSurge"))
              .build();
        }
      }
    }
    return getSetIntervalsSurgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetIntervalsSurgeRequest,
      shippingcalc.GetIntervalsSurgeResponse> getGetIntervalsSurgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetIntervalsSurge",
      requestType = shippingcalc.GetIntervalsSurgeRequest.class,
      responseType = shippingcalc.GetIntervalsSurgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetIntervalsSurgeRequest,
      shippingcalc.GetIntervalsSurgeResponse> getGetIntervalsSurgeMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetIntervalsSurgeRequest, shippingcalc.GetIntervalsSurgeResponse> getGetIntervalsSurgeMethod;
    if ((getGetIntervalsSurgeMethod = ShippingcalcGrpc.getGetIntervalsSurgeMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetIntervalsSurgeMethod = ShippingcalcGrpc.getGetIntervalsSurgeMethod) == null) {
          ShippingcalcGrpc.getGetIntervalsSurgeMethod = getGetIntervalsSurgeMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetIntervalsSurgeRequest, shippingcalc.GetIntervalsSurgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetIntervalsSurge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetIntervalsSurgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetIntervalsSurgeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetIntervalsSurge"))
              .build();
        }
      }
    }
    return getGetIntervalsSurgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.SetSwitchbackExperimentsRequest,
      shippingcalc.SetSwitchbackExperimentsResponse> getSetSwitchbackExperimentsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetSwitchbackExperiments",
      requestType = shippingcalc.SetSwitchbackExperimentsRequest.class,
      responseType = shippingcalc.SetSwitchbackExperimentsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.SetSwitchbackExperimentsRequest,
      shippingcalc.SetSwitchbackExperimentsResponse> getSetSwitchbackExperimentsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.SetSwitchbackExperimentsRequest, shippingcalc.SetSwitchbackExperimentsResponse> getSetSwitchbackExperimentsMethod;
    if ((getSetSwitchbackExperimentsMethod = ShippingcalcGrpc.getSetSwitchbackExperimentsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getSetSwitchbackExperimentsMethod = ShippingcalcGrpc.getSetSwitchbackExperimentsMethod) == null) {
          ShippingcalcGrpc.getSetSwitchbackExperimentsMethod = getSetSwitchbackExperimentsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.SetSwitchbackExperimentsRequest, shippingcalc.SetSwitchbackExperimentsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetSwitchbackExperiments"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.SetSwitchbackExperimentsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.SetSwitchbackExperimentsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("SetSwitchbackExperiments"))
              .build();
        }
      }
    }
    return getSetSwitchbackExperimentsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetSwitchbackExperimentsRequest,
      shippingcalc.GetSwitchbackExperimentsResponse> getGetSwitchbackExperimentsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSwitchbackExperiments",
      requestType = shippingcalc.GetSwitchbackExperimentsRequest.class,
      responseType = shippingcalc.GetSwitchbackExperimentsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetSwitchbackExperimentsRequest,
      shippingcalc.GetSwitchbackExperimentsResponse> getGetSwitchbackExperimentsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetSwitchbackExperimentsRequest, shippingcalc.GetSwitchbackExperimentsResponse> getGetSwitchbackExperimentsMethod;
    if ((getGetSwitchbackExperimentsMethod = ShippingcalcGrpc.getGetSwitchbackExperimentsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetSwitchbackExperimentsMethod = ShippingcalcGrpc.getGetSwitchbackExperimentsMethod) == null) {
          ShippingcalcGrpc.getGetSwitchbackExperimentsMethod = getGetSwitchbackExperimentsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetSwitchbackExperimentsRequest, shippingcalc.GetSwitchbackExperimentsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSwitchbackExperiments"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetSwitchbackExperimentsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetSwitchbackExperimentsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetSwitchbackExperiments"))
              .build();
        }
      }
    }
    return getGetSwitchbackExperimentsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetMinCartAmountsRequest,
      shippingcalc.GetMinCartAmountsResponse> getGetMinCartAmountsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMinCartAmounts",
      requestType = shippingcalc.GetMinCartAmountsRequest.class,
      responseType = shippingcalc.GetMinCartAmountsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetMinCartAmountsRequest,
      shippingcalc.GetMinCartAmountsResponse> getGetMinCartAmountsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetMinCartAmountsRequest, shippingcalc.GetMinCartAmountsResponse> getGetMinCartAmountsMethod;
    if ((getGetMinCartAmountsMethod = ShippingcalcGrpc.getGetMinCartAmountsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetMinCartAmountsMethod = ShippingcalcGrpc.getGetMinCartAmountsMethod) == null) {
          ShippingcalcGrpc.getGetMinCartAmountsMethod = getGetMinCartAmountsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetMinCartAmountsRequest, shippingcalc.GetMinCartAmountsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMinCartAmounts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetMinCartAmountsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetMinCartAmountsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetMinCartAmounts"))
              .build();
        }
      }
    }
    return getGetMinCartAmountsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<shippingcalc.GetDeliveryConditionsRequest,
      shippingcalc.GetDeliveryConditionsResponse> getGetDeliveryConditionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDeliveryConditions",
      requestType = shippingcalc.GetDeliveryConditionsRequest.class,
      responseType = shippingcalc.GetDeliveryConditionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shippingcalc.GetDeliveryConditionsRequest,
      shippingcalc.GetDeliveryConditionsResponse> getGetDeliveryConditionsMethod() {
    io.grpc.MethodDescriptor<shippingcalc.GetDeliveryConditionsRequest, shippingcalc.GetDeliveryConditionsResponse> getGetDeliveryConditionsMethod;
    if ((getGetDeliveryConditionsMethod = ShippingcalcGrpc.getGetDeliveryConditionsMethod) == null) {
      synchronized (ShippingcalcGrpc.class) {
        if ((getGetDeliveryConditionsMethod = ShippingcalcGrpc.getGetDeliveryConditionsMethod) == null) {
          ShippingcalcGrpc.getGetDeliveryConditionsMethod = getGetDeliveryConditionsMethod =
              io.grpc.MethodDescriptor.<shippingcalc.GetDeliveryConditionsRequest, shippingcalc.GetDeliveryConditionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDeliveryConditions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetDeliveryConditionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shippingcalc.GetDeliveryConditionsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ShippingcalcMethodDescriptorSupplier("GetDeliveryConditions"))
              .build();
        }
      }
    }
    return getGetDeliveryConditionsMethod;
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
    public void createStrategy(shippingcalc.CreateStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.CreateStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Обновить стратегию
     * </pre>
     */
    public void updateStrategy(shippingcalc.UpdateStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.UpdateStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Прикрепить стратегию к магазину
     * </pre>
     */
    public void bindStrategy(shippingcalc.BindStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.BindStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBindStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Открепить стратегию от магазина
     * </pre>
     */
    public void unbindStrategy(shippingcalc.UnbindStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.UnbindStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnbindStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все существующие стратегии
     * </pre>
     */
    public void getStrategies(shippingcalc.GetStrategiesRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetStrategiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStrategiesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить стратегию по идентификатору
     * </pre>
     */
    public void getStrategy(shippingcalc.GetStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить стратегии для магазина
     * </pre>
     */
    public void getStrategiesForStore(shippingcalc.GetStrategiesForStoreRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetStrategiesForStoreResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStrategiesForStoreMethod(), responseObserver);
    }

    /**
     * <pre>
     * Удалить стратегию по идентификатору
     * </pre>
     */
    public void deleteStrategy(shippingcalc.DeleteStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.DeleteStrategyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteStrategyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Создать скрипт
     * </pre>
     */
    public void createScript(shippingcalc.CreateScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.CreateScriptResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateScriptMethod(), responseObserver);
    }

    /**
     * <pre>
     * Обновить скрипт
     * </pre>
     */
    public void updateScript(shippingcalc.UpdateScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.UpdateScriptResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateScriptMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить скрипт по идентификатору
     * </pre>
     */
    public void getScript(shippingcalc.GetScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetScriptResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetScriptMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все существующие скрипты
     * </pre>
     */
    public void getScripts(shippingcalc.GetScriptsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetScriptsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetScriptsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить результаты теста скрипта
     * </pre>
     */
    public void getScriptTestResults(shippingcalc.GetScriptTestResultsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetScriptTestResultsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetScriptTestResultsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Удалить скрипт по идентификатору
     * </pre>
     */
    public void deleteScript(shippingcalc.DeleteScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.DeleteScriptResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteScriptMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить стоимость доставки
     * </pre>
     */
    public void getDeliveryPrice(shippingcalc.GetDeliveryPriceRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetDeliveryPriceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDeliveryPriceMethod(), responseObserver);
    }

    /**
     * <pre>
     * Установить сразу все интервалы surge
     * </pre>
     */
    public void setIntervalsSurge(shippingcalc.SetIntervalsSurgeRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.SetIntervalsSurgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetIntervalsSurgeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все интервалы surge
     * </pre>
     */
    public void getIntervalsSurge(shippingcalc.GetIntervalsSurgeRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetIntervalsSurgeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetIntervalsSurgeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Установить сразу все интервалы switchback experiments for surge
     * </pre>
     */
    public void setSwitchbackExperiments(shippingcalc.SetSwitchbackExperimentsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.SetSwitchbackExperimentsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetSwitchbackExperimentsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все интервалы switchback experiments for surge
     * </pre>
     */
    public void getSwitchbackExperiments(shippingcalc.GetSwitchbackExperimentsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetSwitchbackExperimentsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSwitchbackExperimentsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить минимальные корзины по списку
     * </pre>
     */
    public void getMinCartAmounts(shippingcalc.GetMinCartAmountsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetMinCartAmountsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMinCartAmountsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Наиболее выгодные условия доставки для магазинов
     * </pre>
     */
    public void getDeliveryConditions(shippingcalc.GetDeliveryConditionsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetDeliveryConditionsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDeliveryConditionsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.CreateStrategyRequest,
                shippingcalc.CreateStrategyResponse>(
                  this, METHODID_CREATE_STRATEGY)))
          .addMethod(
            getUpdateStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.UpdateStrategyRequest,
                shippingcalc.UpdateStrategyResponse>(
                  this, METHODID_UPDATE_STRATEGY)))
          .addMethod(
            getBindStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.BindStrategyRequest,
                shippingcalc.BindStrategyResponse>(
                  this, METHODID_BIND_STRATEGY)))
          .addMethod(
            getUnbindStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.UnbindStrategyRequest,
                shippingcalc.UnbindStrategyResponse>(
                  this, METHODID_UNBIND_STRATEGY)))
          .addMethod(
            getGetStrategiesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetStrategiesRequest,
                shippingcalc.GetStrategiesResponse>(
                  this, METHODID_GET_STRATEGIES)))
          .addMethod(
            getGetStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetStrategyRequest,
                shippingcalc.GetStrategyResponse>(
                  this, METHODID_GET_STRATEGY)))
          .addMethod(
            getGetStrategiesForStoreMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetStrategiesForStoreRequest,
                shippingcalc.GetStrategiesForStoreResponse>(
                  this, METHODID_GET_STRATEGIES_FOR_STORE)))
          .addMethod(
            getDeleteStrategyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.DeleteStrategyRequest,
                shippingcalc.DeleteStrategyResponse>(
                  this, METHODID_DELETE_STRATEGY)))
          .addMethod(
            getCreateScriptMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.CreateScriptRequest,
                shippingcalc.CreateScriptResponse>(
                  this, METHODID_CREATE_SCRIPT)))
          .addMethod(
            getUpdateScriptMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.UpdateScriptRequest,
                shippingcalc.UpdateScriptResponse>(
                  this, METHODID_UPDATE_SCRIPT)))
          .addMethod(
            getGetScriptMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetScriptRequest,
                shippingcalc.GetScriptResponse>(
                  this, METHODID_GET_SCRIPT)))
          .addMethod(
            getGetScriptsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetScriptsRequest,
                shippingcalc.GetScriptsResponse>(
                  this, METHODID_GET_SCRIPTS)))
          .addMethod(
            getGetScriptTestResultsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetScriptTestResultsRequest,
                shippingcalc.GetScriptTestResultsResponse>(
                  this, METHODID_GET_SCRIPT_TEST_RESULTS)))
          .addMethod(
            getDeleteScriptMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.DeleteScriptRequest,
                shippingcalc.DeleteScriptResponse>(
                  this, METHODID_DELETE_SCRIPT)))
          .addMethod(
            getGetDeliveryPriceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetDeliveryPriceRequest,
                shippingcalc.GetDeliveryPriceResponse>(
                  this, METHODID_GET_DELIVERY_PRICE)))
          .addMethod(
            getSetIntervalsSurgeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.SetIntervalsSurgeRequest,
                shippingcalc.SetIntervalsSurgeResponse>(
                  this, METHODID_SET_INTERVALS_SURGE)))
          .addMethod(
            getGetIntervalsSurgeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetIntervalsSurgeRequest,
                shippingcalc.GetIntervalsSurgeResponse>(
                  this, METHODID_GET_INTERVALS_SURGE)))
          .addMethod(
            getSetSwitchbackExperimentsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.SetSwitchbackExperimentsRequest,
                shippingcalc.SetSwitchbackExperimentsResponse>(
                  this, METHODID_SET_SWITCHBACK_EXPERIMENTS)))
          .addMethod(
            getGetSwitchbackExperimentsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetSwitchbackExperimentsRequest,
                shippingcalc.GetSwitchbackExperimentsResponse>(
                  this, METHODID_GET_SWITCHBACK_EXPERIMENTS)))
          .addMethod(
            getGetMinCartAmountsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetMinCartAmountsRequest,
                shippingcalc.GetMinCartAmountsResponse>(
                  this, METHODID_GET_MIN_CART_AMOUNTS)))
          .addMethod(
            getGetDeliveryConditionsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                shippingcalc.GetDeliveryConditionsRequest,
                shippingcalc.GetDeliveryConditionsResponse>(
                  this, METHODID_GET_DELIVERY_CONDITIONS)))
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
    public void createStrategy(shippingcalc.CreateStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.CreateStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Обновить стратегию
     * </pre>
     */
    public void updateStrategy(shippingcalc.UpdateStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.UpdateStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Прикрепить стратегию к магазину
     * </pre>
     */
    public void bindStrategy(shippingcalc.BindStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.BindStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBindStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Открепить стратегию от магазина
     * </pre>
     */
    public void unbindStrategy(shippingcalc.UnbindStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.UnbindStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnbindStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все существующие стратегии
     * </pre>
     */
    public void getStrategies(shippingcalc.GetStrategiesRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetStrategiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStrategiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить стратегию по идентификатору
     * </pre>
     */
    public void getStrategy(shippingcalc.GetStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить стратегии для магазина
     * </pre>
     */
    public void getStrategiesForStore(shippingcalc.GetStrategiesForStoreRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetStrategiesForStoreResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStrategiesForStoreMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Удалить стратегию по идентификатору
     * </pre>
     */
    public void deleteStrategy(shippingcalc.DeleteStrategyRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.DeleteStrategyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteStrategyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Создать скрипт
     * </pre>
     */
    public void createScript(shippingcalc.CreateScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.CreateScriptResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateScriptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Обновить скрипт
     * </pre>
     */
    public void updateScript(shippingcalc.UpdateScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.UpdateScriptResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateScriptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить скрипт по идентификатору
     * </pre>
     */
    public void getScript(shippingcalc.GetScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetScriptResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetScriptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все существующие скрипты
     * </pre>
     */
    public void getScripts(shippingcalc.GetScriptsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetScriptsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetScriptsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить результаты теста скрипта
     * </pre>
     */
    public void getScriptTestResults(shippingcalc.GetScriptTestResultsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetScriptTestResultsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetScriptTestResultsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Удалить скрипт по идентификатору
     * </pre>
     */
    public void deleteScript(shippingcalc.DeleteScriptRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.DeleteScriptResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteScriptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить стоимость доставки
     * </pre>
     */
    public void getDeliveryPrice(shippingcalc.GetDeliveryPriceRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetDeliveryPriceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDeliveryPriceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Установить сразу все интервалы surge
     * </pre>
     */
    public void setIntervalsSurge(shippingcalc.SetIntervalsSurgeRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.SetIntervalsSurgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetIntervalsSurgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все интервалы surge
     * </pre>
     */
    public void getIntervalsSurge(shippingcalc.GetIntervalsSurgeRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetIntervalsSurgeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetIntervalsSurgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Установить сразу все интервалы switchback experiments for surge
     * </pre>
     */
    public void setSwitchbackExperiments(shippingcalc.SetSwitchbackExperimentsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.SetSwitchbackExperimentsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetSwitchbackExperimentsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все интервалы switchback experiments for surge
     * </pre>
     */
    public void getSwitchbackExperiments(shippingcalc.GetSwitchbackExperimentsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetSwitchbackExperimentsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSwitchbackExperimentsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить минимальные корзины по списку
     * </pre>
     */
    public void getMinCartAmounts(shippingcalc.GetMinCartAmountsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetMinCartAmountsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMinCartAmountsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Наиболее выгодные условия доставки для магазинов
     * </pre>
     */
    public void getDeliveryConditions(shippingcalc.GetDeliveryConditionsRequest request,
        io.grpc.stub.StreamObserver<shippingcalc.GetDeliveryConditionsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDeliveryConditionsMethod(), getCallOptions()), request, responseObserver);
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
    public shippingcalc.CreateStrategyResponse createStrategy(shippingcalc.CreateStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Обновить стратегию
     * </pre>
     */
    public shippingcalc.UpdateStrategyResponse updateStrategy(shippingcalc.UpdateStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Прикрепить стратегию к магазину
     * </pre>
     */
    public shippingcalc.BindStrategyResponse bindStrategy(shippingcalc.BindStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBindStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Открепить стратегию от магазина
     * </pre>
     */
    public shippingcalc.UnbindStrategyResponse unbindStrategy(shippingcalc.UnbindStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnbindStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все существующие стратегии
     * </pre>
     */
    public shippingcalc.GetStrategiesResponse getStrategies(shippingcalc.GetStrategiesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStrategiesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить стратегию по идентификатору
     * </pre>
     */
    public shippingcalc.GetStrategyResponse getStrategy(shippingcalc.GetStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить стратегии для магазина
     * </pre>
     */
    public shippingcalc.GetStrategiesForStoreResponse getStrategiesForStore(shippingcalc.GetStrategiesForStoreRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStrategiesForStoreMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Удалить стратегию по идентификатору
     * </pre>
     */
    public shippingcalc.DeleteStrategyResponse deleteStrategy(shippingcalc.DeleteStrategyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteStrategyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Создать скрипт
     * </pre>
     */
    public shippingcalc.CreateScriptResponse createScript(shippingcalc.CreateScriptRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateScriptMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Обновить скрипт
     * </pre>
     */
    public shippingcalc.UpdateScriptResponse updateScript(shippingcalc.UpdateScriptRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateScriptMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить скрипт по идентификатору
     * </pre>
     */
    public shippingcalc.GetScriptResponse getScript(shippingcalc.GetScriptRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetScriptMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все существующие скрипты
     * </pre>
     */
    public shippingcalc.GetScriptsResponse getScripts(shippingcalc.GetScriptsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetScriptsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить результаты теста скрипта
     * </pre>
     */
    public shippingcalc.GetScriptTestResultsResponse getScriptTestResults(shippingcalc.GetScriptTestResultsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetScriptTestResultsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Удалить скрипт по идентификатору
     * </pre>
     */
    public shippingcalc.DeleteScriptResponse deleteScript(shippingcalc.DeleteScriptRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteScriptMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить стоимость доставки
     * </pre>
     */
    public shippingcalc.GetDeliveryPriceResponse getDeliveryPrice(shippingcalc.GetDeliveryPriceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDeliveryPriceMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Установить сразу все интервалы surge
     * </pre>
     */
    public shippingcalc.SetIntervalsSurgeResponse setIntervalsSurge(shippingcalc.SetIntervalsSurgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetIntervalsSurgeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все интервалы surge
     * </pre>
     */
    public shippingcalc.GetIntervalsSurgeResponse getIntervalsSurge(shippingcalc.GetIntervalsSurgeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetIntervalsSurgeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Установить сразу все интервалы switchback experiments for surge
     * </pre>
     */
    public shippingcalc.SetSwitchbackExperimentsResponse setSwitchbackExperiments(shippingcalc.SetSwitchbackExperimentsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetSwitchbackExperimentsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все интервалы switchback experiments for surge
     * </pre>
     */
    public shippingcalc.GetSwitchbackExperimentsResponse getSwitchbackExperiments(shippingcalc.GetSwitchbackExperimentsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSwitchbackExperimentsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить минимальные корзины по списку
     * </pre>
     */
    public shippingcalc.GetMinCartAmountsResponse getMinCartAmounts(shippingcalc.GetMinCartAmountsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMinCartAmountsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Наиболее выгодные условия доставки для магазинов
     * </pre>
     */
    public shippingcalc.GetDeliveryConditionsResponse getDeliveryConditions(shippingcalc.GetDeliveryConditionsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDeliveryConditionsMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.CreateStrategyResponse> createStrategy(
        shippingcalc.CreateStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Обновить стратегию
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.UpdateStrategyResponse> updateStrategy(
        shippingcalc.UpdateStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Прикрепить стратегию к магазину
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.BindStrategyResponse> bindStrategy(
        shippingcalc.BindStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBindStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Открепить стратегию от магазина
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.UnbindStrategyResponse> unbindStrategy(
        shippingcalc.UnbindStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnbindStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все существующие стратегии
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetStrategiesResponse> getStrategies(
        shippingcalc.GetStrategiesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStrategiesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить стратегию по идентификатору
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetStrategyResponse> getStrategy(
        shippingcalc.GetStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить стратегии для магазина
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetStrategiesForStoreResponse> getStrategiesForStore(
        shippingcalc.GetStrategiesForStoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStrategiesForStoreMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Удалить стратегию по идентификатору
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.DeleteStrategyResponse> deleteStrategy(
        shippingcalc.DeleteStrategyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteStrategyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Создать скрипт
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.CreateScriptResponse> createScript(
        shippingcalc.CreateScriptRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateScriptMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Обновить скрипт
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.UpdateScriptResponse> updateScript(
        shippingcalc.UpdateScriptRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateScriptMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить скрипт по идентификатору
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetScriptResponse> getScript(
        shippingcalc.GetScriptRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetScriptMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все существующие скрипты
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetScriptsResponse> getScripts(
        shippingcalc.GetScriptsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetScriptsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить результаты теста скрипта
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetScriptTestResultsResponse> getScriptTestResults(
        shippingcalc.GetScriptTestResultsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetScriptTestResultsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Удалить скрипт по идентификатору
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.DeleteScriptResponse> deleteScript(
        shippingcalc.DeleteScriptRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteScriptMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить стоимость доставки
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetDeliveryPriceResponse> getDeliveryPrice(
        shippingcalc.GetDeliveryPriceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDeliveryPriceMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Установить сразу все интервалы surge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.SetIntervalsSurgeResponse> setIntervalsSurge(
        shippingcalc.SetIntervalsSurgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetIntervalsSurgeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все интервалы surge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetIntervalsSurgeResponse> getIntervalsSurge(
        shippingcalc.GetIntervalsSurgeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetIntervalsSurgeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Установить сразу все интервалы switchback experiments for surge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.SetSwitchbackExperimentsResponse> setSwitchbackExperiments(
        shippingcalc.SetSwitchbackExperimentsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetSwitchbackExperimentsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все интервалы switchback experiments for surge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetSwitchbackExperimentsResponse> getSwitchbackExperiments(
        shippingcalc.GetSwitchbackExperimentsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSwitchbackExperimentsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить минимальные корзины по списку
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetMinCartAmountsResponse> getMinCartAmounts(
        shippingcalc.GetMinCartAmountsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMinCartAmountsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Наиболее выгодные условия доставки для магазинов
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<shippingcalc.GetDeliveryConditionsResponse> getDeliveryConditions(
        shippingcalc.GetDeliveryConditionsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDeliveryConditionsMethod(), getCallOptions()), request);
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
  private static final int METHODID_GET_MIN_CART_AMOUNTS = 19;
  private static final int METHODID_GET_DELIVERY_CONDITIONS = 20;

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
          serviceImpl.createStrategy((shippingcalc.CreateStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.CreateStrategyResponse>) responseObserver);
          break;
        case METHODID_UPDATE_STRATEGY:
          serviceImpl.updateStrategy((shippingcalc.UpdateStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.UpdateStrategyResponse>) responseObserver);
          break;
        case METHODID_BIND_STRATEGY:
          serviceImpl.bindStrategy((shippingcalc.BindStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.BindStrategyResponse>) responseObserver);
          break;
        case METHODID_UNBIND_STRATEGY:
          serviceImpl.unbindStrategy((shippingcalc.UnbindStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.UnbindStrategyResponse>) responseObserver);
          break;
        case METHODID_GET_STRATEGIES:
          serviceImpl.getStrategies((shippingcalc.GetStrategiesRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetStrategiesResponse>) responseObserver);
          break;
        case METHODID_GET_STRATEGY:
          serviceImpl.getStrategy((shippingcalc.GetStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetStrategyResponse>) responseObserver);
          break;
        case METHODID_GET_STRATEGIES_FOR_STORE:
          serviceImpl.getStrategiesForStore((shippingcalc.GetStrategiesForStoreRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetStrategiesForStoreResponse>) responseObserver);
          break;
        case METHODID_DELETE_STRATEGY:
          serviceImpl.deleteStrategy((shippingcalc.DeleteStrategyRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.DeleteStrategyResponse>) responseObserver);
          break;
        case METHODID_CREATE_SCRIPT:
          serviceImpl.createScript((shippingcalc.CreateScriptRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.CreateScriptResponse>) responseObserver);
          break;
        case METHODID_UPDATE_SCRIPT:
          serviceImpl.updateScript((shippingcalc.UpdateScriptRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.UpdateScriptResponse>) responseObserver);
          break;
        case METHODID_GET_SCRIPT:
          serviceImpl.getScript((shippingcalc.GetScriptRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetScriptResponse>) responseObserver);
          break;
        case METHODID_GET_SCRIPTS:
          serviceImpl.getScripts((shippingcalc.GetScriptsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetScriptsResponse>) responseObserver);
          break;
        case METHODID_GET_SCRIPT_TEST_RESULTS:
          serviceImpl.getScriptTestResults((shippingcalc.GetScriptTestResultsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetScriptTestResultsResponse>) responseObserver);
          break;
        case METHODID_DELETE_SCRIPT:
          serviceImpl.deleteScript((shippingcalc.DeleteScriptRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.DeleteScriptResponse>) responseObserver);
          break;
        case METHODID_GET_DELIVERY_PRICE:
          serviceImpl.getDeliveryPrice((shippingcalc.GetDeliveryPriceRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetDeliveryPriceResponse>) responseObserver);
          break;
        case METHODID_SET_INTERVALS_SURGE:
          serviceImpl.setIntervalsSurge((shippingcalc.SetIntervalsSurgeRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.SetIntervalsSurgeResponse>) responseObserver);
          break;
        case METHODID_GET_INTERVALS_SURGE:
          serviceImpl.getIntervalsSurge((shippingcalc.GetIntervalsSurgeRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetIntervalsSurgeResponse>) responseObserver);
          break;
        case METHODID_SET_SWITCHBACK_EXPERIMENTS:
          serviceImpl.setSwitchbackExperiments((shippingcalc.SetSwitchbackExperimentsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.SetSwitchbackExperimentsResponse>) responseObserver);
          break;
        case METHODID_GET_SWITCHBACK_EXPERIMENTS:
          serviceImpl.getSwitchbackExperiments((shippingcalc.GetSwitchbackExperimentsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetSwitchbackExperimentsResponse>) responseObserver);
          break;
        case METHODID_GET_MIN_CART_AMOUNTS:
          serviceImpl.getMinCartAmounts((shippingcalc.GetMinCartAmountsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetMinCartAmountsResponse>) responseObserver);
          break;
        case METHODID_GET_DELIVERY_CONDITIONS:
          serviceImpl.getDeliveryConditions((shippingcalc.GetDeliveryConditionsRequest) request,
              (io.grpc.stub.StreamObserver<shippingcalc.GetDeliveryConditionsResponse>) responseObserver);
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
              .addMethod(getGetMinCartAmountsMethod())
              .addMethod(getGetDeliveryConditionsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
