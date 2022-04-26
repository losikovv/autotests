package tagmanager;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/operations/tagmanager.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TagManagerGrpc {

  private TagManagerGrpc() {}

  public static final String SERVICE_NAME = "tagmanager.TagManager";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateOwnerRequest,
      tagmanager.Tagmanager.CreateOwnerResponse> getCreateOwnerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateOwner",
      requestType = tagmanager.Tagmanager.CreateOwnerRequest.class,
      responseType = tagmanager.Tagmanager.CreateOwnerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateOwnerRequest,
      tagmanager.Tagmanager.CreateOwnerResponse> getCreateOwnerMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateOwnerRequest, tagmanager.Tagmanager.CreateOwnerResponse> getCreateOwnerMethod;
    if ((getCreateOwnerMethod = TagManagerGrpc.getCreateOwnerMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getCreateOwnerMethod = TagManagerGrpc.getCreateOwnerMethod) == null) {
          TagManagerGrpc.getCreateOwnerMethod = getCreateOwnerMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.CreateOwnerRequest, tagmanager.Tagmanager.CreateOwnerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateOwner"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.CreateOwnerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.CreateOwnerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("CreateOwner"))
              .build();
        }
      }
    }
    return getCreateOwnerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetOwnersRequest,
      tagmanager.Tagmanager.GetOwnersResponse> getGetOwnersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOwners",
      requestType = tagmanager.Tagmanager.GetOwnersRequest.class,
      responseType = tagmanager.Tagmanager.GetOwnersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetOwnersRequest,
      tagmanager.Tagmanager.GetOwnersResponse> getGetOwnersMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetOwnersRequest, tagmanager.Tagmanager.GetOwnersResponse> getGetOwnersMethod;
    if ((getGetOwnersMethod = TagManagerGrpc.getGetOwnersMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetOwnersMethod = TagManagerGrpc.getGetOwnersMethod) == null) {
          TagManagerGrpc.getGetOwnersMethod = getGetOwnersMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetOwnersRequest, tagmanager.Tagmanager.GetOwnersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOwners"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetOwnersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetOwnersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetOwners"))
              .build();
        }
      }
    }
    return getGetOwnersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateTagRequest,
      tagmanager.Tagmanager.CreateTagResponse> getCreateTagMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateTag",
      requestType = tagmanager.Tagmanager.CreateTagRequest.class,
      responseType = tagmanager.Tagmanager.CreateTagResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateTagRequest,
      tagmanager.Tagmanager.CreateTagResponse> getCreateTagMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateTagRequest, tagmanager.Tagmanager.CreateTagResponse> getCreateTagMethod;
    if ((getCreateTagMethod = TagManagerGrpc.getCreateTagMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getCreateTagMethod = TagManagerGrpc.getCreateTagMethod) == null) {
          TagManagerGrpc.getCreateTagMethod = getCreateTagMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.CreateTagRequest, tagmanager.Tagmanager.CreateTagResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateTag"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.CreateTagRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.CreateTagResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("CreateTag"))
              .build();
        }
      }
    }
    return getCreateTagMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.DeleteTagRequest,
      tagmanager.Tagmanager.DeleteTagResponse> getDeleteTagMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteTag",
      requestType = tagmanager.Tagmanager.DeleteTagRequest.class,
      responseType = tagmanager.Tagmanager.DeleteTagResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.DeleteTagRequest,
      tagmanager.Tagmanager.DeleteTagResponse> getDeleteTagMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.DeleteTagRequest, tagmanager.Tagmanager.DeleteTagResponse> getDeleteTagMethod;
    if ((getDeleteTagMethod = TagManagerGrpc.getDeleteTagMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getDeleteTagMethod = TagManagerGrpc.getDeleteTagMethod) == null) {
          TagManagerGrpc.getDeleteTagMethod = getDeleteTagMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.DeleteTagRequest, tagmanager.Tagmanager.DeleteTagResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteTag"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.DeleteTagRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.DeleteTagResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("DeleteTag"))
              .build();
        }
      }
    }
    return getDeleteTagMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByOwnerRequest,
      tagmanager.Tagmanager.GetTagsByOwnerResponse> getGetTagsByOwnerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTagsByOwner",
      requestType = tagmanager.Tagmanager.GetTagsByOwnerRequest.class,
      responseType = tagmanager.Tagmanager.GetTagsByOwnerResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByOwnerRequest,
      tagmanager.Tagmanager.GetTagsByOwnerResponse> getGetTagsByOwnerMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByOwnerRequest, tagmanager.Tagmanager.GetTagsByOwnerResponse> getGetTagsByOwnerMethod;
    if ((getGetTagsByOwnerMethod = TagManagerGrpc.getGetTagsByOwnerMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetTagsByOwnerMethod = TagManagerGrpc.getGetTagsByOwnerMethod) == null) {
          TagManagerGrpc.getGetTagsByOwnerMethod = getGetTagsByOwnerMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetTagsByOwnerRequest, tagmanager.Tagmanager.GetTagsByOwnerResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTagsByOwner"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetTagsByOwnerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetTagsByOwnerResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetTagsByOwner"))
              .build();
        }
      }
    }
    return getGetTagsByOwnerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.BindTagRequest,
      tagmanager.Tagmanager.BindTagResponse> getBindTagMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "BindTag",
      requestType = tagmanager.Tagmanager.BindTagRequest.class,
      responseType = tagmanager.Tagmanager.BindTagResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.BindTagRequest,
      tagmanager.Tagmanager.BindTagResponse> getBindTagMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.BindTagRequest, tagmanager.Tagmanager.BindTagResponse> getBindTagMethod;
    if ((getBindTagMethod = TagManagerGrpc.getBindTagMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getBindTagMethod = TagManagerGrpc.getBindTagMethod) == null) {
          TagManagerGrpc.getBindTagMethod = getBindTagMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.BindTagRequest, tagmanager.Tagmanager.BindTagResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BindTag"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.BindTagRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.BindTagResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("BindTag"))
              .build();
        }
      }
    }
    return getBindTagMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.UnbindTagRequest,
      tagmanager.Tagmanager.UnbindTagResponse> getUnbindTagMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UnbindTag",
      requestType = tagmanager.Tagmanager.UnbindTagRequest.class,
      responseType = tagmanager.Tagmanager.UnbindTagResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.UnbindTagRequest,
      tagmanager.Tagmanager.UnbindTagResponse> getUnbindTagMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.UnbindTagRequest, tagmanager.Tagmanager.UnbindTagResponse> getUnbindTagMethod;
    if ((getUnbindTagMethod = TagManagerGrpc.getUnbindTagMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getUnbindTagMethod = TagManagerGrpc.getUnbindTagMethod) == null) {
          TagManagerGrpc.getUnbindTagMethod = getUnbindTagMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.UnbindTagRequest, tagmanager.Tagmanager.UnbindTagResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UnbindTag"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.UnbindTagRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.UnbindTagResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("UnbindTag"))
              .build();
        }
      }
    }
    return getUnbindTagMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByTagRequest,
      tagmanager.Tagmanager.GetBindsByTagResponse> getGetBindsByTagMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBindsByTag",
      requestType = tagmanager.Tagmanager.GetBindsByTagRequest.class,
      responseType = tagmanager.Tagmanager.GetBindsByTagResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByTagRequest,
      tagmanager.Tagmanager.GetBindsByTagResponse> getGetBindsByTagMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByTagRequest, tagmanager.Tagmanager.GetBindsByTagResponse> getGetBindsByTagMethod;
    if ((getGetBindsByTagMethod = TagManagerGrpc.getGetBindsByTagMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetBindsByTagMethod = TagManagerGrpc.getGetBindsByTagMethod) == null) {
          TagManagerGrpc.getGetBindsByTagMethod = getGetBindsByTagMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetBindsByTagRequest, tagmanager.Tagmanager.GetBindsByTagResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBindsByTag"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetBindsByTagRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetBindsByTagResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetBindsByTag"))
              .build();
        }
      }
    }
    return getGetBindsByTagMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByItemRequest,
      tagmanager.Tagmanager.GetBindsByItemResponse> getGetBindsByItemMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBindsByItem",
      requestType = tagmanager.Tagmanager.GetBindsByItemRequest.class,
      responseType = tagmanager.Tagmanager.GetBindsByItemResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByItemRequest,
      tagmanager.Tagmanager.GetBindsByItemResponse> getGetBindsByItemMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByItemRequest, tagmanager.Tagmanager.GetBindsByItemResponse> getGetBindsByItemMethod;
    if ((getGetBindsByItemMethod = TagManagerGrpc.getGetBindsByItemMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetBindsByItemMethod = TagManagerGrpc.getGetBindsByItemMethod) == null) {
          TagManagerGrpc.getGetBindsByItemMethod = getGetBindsByItemMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetBindsByItemRequest, tagmanager.Tagmanager.GetBindsByItemResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBindsByItem"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetBindsByItemRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetBindsByItemResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetBindsByItem"))
              .build();
        }
      }
    }
    return getGetBindsByItemMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByItemsRequest,
      tagmanager.Tagmanager.GetBindsByItemsResponse> getGetBindsByItemsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBindsByItems",
      requestType = tagmanager.Tagmanager.GetBindsByItemsRequest.class,
      responseType = tagmanager.Tagmanager.GetBindsByItemsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByItemsRequest,
      tagmanager.Tagmanager.GetBindsByItemsResponse> getGetBindsByItemsMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetBindsByItemsRequest, tagmanager.Tagmanager.GetBindsByItemsResponse> getGetBindsByItemsMethod;
    if ((getGetBindsByItemsMethod = TagManagerGrpc.getGetBindsByItemsMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetBindsByItemsMethod = TagManagerGrpc.getGetBindsByItemsMethod) == null) {
          TagManagerGrpc.getGetBindsByItemsMethod = getGetBindsByItemsMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetBindsByItemsRequest, tagmanager.Tagmanager.GetBindsByItemsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBindsByItems"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetBindsByItemsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetBindsByItemsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetBindsByItems"))
              .build();
        }
      }
    }
    return getGetBindsByItemsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetItemsByTagsListRequest,
      tagmanager.Tagmanager.GetItemsByTagsListResponse> getGetItemsByTagsListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetItemsByTagsList",
      requestType = tagmanager.Tagmanager.GetItemsByTagsListRequest.class,
      responseType = tagmanager.Tagmanager.GetItemsByTagsListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetItemsByTagsListRequest,
      tagmanager.Tagmanager.GetItemsByTagsListResponse> getGetItemsByTagsListMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetItemsByTagsListRequest, tagmanager.Tagmanager.GetItemsByTagsListResponse> getGetItemsByTagsListMethod;
    if ((getGetItemsByTagsListMethod = TagManagerGrpc.getGetItemsByTagsListMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetItemsByTagsListMethod = TagManagerGrpc.getGetItemsByTagsListMethod) == null) {
          TagManagerGrpc.getGetItemsByTagsListMethod = getGetItemsByTagsListMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetItemsByTagsListRequest, tagmanager.Tagmanager.GetItemsByTagsListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetItemsByTagsList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetItemsByTagsListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetItemsByTagsListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetItemsByTagsList"))
              .build();
        }
      }
    }
    return getGetItemsByTagsListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByItemRequest,
      tagmanager.Tagmanager.GetTagsByItemResponse> getGetTagsByItemMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTagsByItem",
      requestType = tagmanager.Tagmanager.GetTagsByItemRequest.class,
      responseType = tagmanager.Tagmanager.GetTagsByItemResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByItemRequest,
      tagmanager.Tagmanager.GetTagsByItemResponse> getGetTagsByItemMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByItemRequest, tagmanager.Tagmanager.GetTagsByItemResponse> getGetTagsByItemMethod;
    if ((getGetTagsByItemMethod = TagManagerGrpc.getGetTagsByItemMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetTagsByItemMethod = TagManagerGrpc.getGetTagsByItemMethod) == null) {
          TagManagerGrpc.getGetTagsByItemMethod = getGetTagsByItemMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetTagsByItemRequest, tagmanager.Tagmanager.GetTagsByItemResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTagsByItem"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetTagsByItemRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetTagsByItemResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetTagsByItem"))
              .build();
        }
      }
    }
    return getGetTagsByItemMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByItemsRequest,
      tagmanager.Tagmanager.GetTagsByItemsResponse> getGetTagsByItemsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTagsByItems",
      requestType = tagmanager.Tagmanager.GetTagsByItemsRequest.class,
      responseType = tagmanager.Tagmanager.GetTagsByItemsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByItemsRequest,
      tagmanager.Tagmanager.GetTagsByItemsResponse> getGetTagsByItemsMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetTagsByItemsRequest, tagmanager.Tagmanager.GetTagsByItemsResponse> getGetTagsByItemsMethod;
    if ((getGetTagsByItemsMethod = TagManagerGrpc.getGetTagsByItemsMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetTagsByItemsMethod = TagManagerGrpc.getGetTagsByItemsMethod) == null) {
          TagManagerGrpc.getGetTagsByItemsMethod = getGetTagsByItemsMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetTagsByItemsRequest, tagmanager.Tagmanager.GetTagsByItemsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTagsByItems"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetTagsByItemsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetTagsByItemsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetTagsByItems"))
              .build();
        }
      }
    }
    return getGetTagsByItemsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateRuleRequest,
      tagmanager.Tagmanager.CreateRuleResponse> getCreateRuleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateRule",
      requestType = tagmanager.Tagmanager.CreateRuleRequest.class,
      responseType = tagmanager.Tagmanager.CreateRuleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateRuleRequest,
      tagmanager.Tagmanager.CreateRuleResponse> getCreateRuleMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.CreateRuleRequest, tagmanager.Tagmanager.CreateRuleResponse> getCreateRuleMethod;
    if ((getCreateRuleMethod = TagManagerGrpc.getCreateRuleMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getCreateRuleMethod = TagManagerGrpc.getCreateRuleMethod) == null) {
          TagManagerGrpc.getCreateRuleMethod = getCreateRuleMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.CreateRuleRequest, tagmanager.Tagmanager.CreateRuleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateRule"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.CreateRuleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.CreateRuleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("CreateRule"))
              .build();
        }
      }
    }
    return getCreateRuleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.DeleteRuleRequest,
      tagmanager.Tagmanager.DeleteRuleResponse> getDeleteRuleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteRule",
      requestType = tagmanager.Tagmanager.DeleteRuleRequest.class,
      responseType = tagmanager.Tagmanager.DeleteRuleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.DeleteRuleRequest,
      tagmanager.Tagmanager.DeleteRuleResponse> getDeleteRuleMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.DeleteRuleRequest, tagmanager.Tagmanager.DeleteRuleResponse> getDeleteRuleMethod;
    if ((getDeleteRuleMethod = TagManagerGrpc.getDeleteRuleMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getDeleteRuleMethod = TagManagerGrpc.getDeleteRuleMethod) == null) {
          TagManagerGrpc.getDeleteRuleMethod = getDeleteRuleMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.DeleteRuleRequest, tagmanager.Tagmanager.DeleteRuleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteRule"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.DeleteRuleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.DeleteRuleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("DeleteRule"))
              .build();
        }
      }
    }
    return getDeleteRuleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetApplicableRulesRequest,
      tagmanager.Tagmanager.GetApplicableRulesResponse> getGetApplicableRulesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetApplicableRules",
      requestType = tagmanager.Tagmanager.GetApplicableRulesRequest.class,
      responseType = tagmanager.Tagmanager.GetApplicableRulesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetApplicableRulesRequest,
      tagmanager.Tagmanager.GetApplicableRulesResponse> getGetApplicableRulesMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.GetApplicableRulesRequest, tagmanager.Tagmanager.GetApplicableRulesResponse> getGetApplicableRulesMethod;
    if ((getGetApplicableRulesMethod = TagManagerGrpc.getGetApplicableRulesMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getGetApplicableRulesMethod = TagManagerGrpc.getGetApplicableRulesMethod) == null) {
          TagManagerGrpc.getGetApplicableRulesMethod = getGetApplicableRulesMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.GetApplicableRulesRequest, tagmanager.Tagmanager.GetApplicableRulesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetApplicableRules"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetApplicableRulesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.GetApplicableRulesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("GetApplicableRules"))
              .build();
        }
      }
    }
    return getGetApplicableRulesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<tagmanager.Tagmanager.FilterByRulesRequest,
      tagmanager.Tagmanager.FilterByRulesResponse> getFilterByRulesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FilterByRules",
      requestType = tagmanager.Tagmanager.FilterByRulesRequest.class,
      responseType = tagmanager.Tagmanager.FilterByRulesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<tagmanager.Tagmanager.FilterByRulesRequest,
      tagmanager.Tagmanager.FilterByRulesResponse> getFilterByRulesMethod() {
    io.grpc.MethodDescriptor<tagmanager.Tagmanager.FilterByRulesRequest, tagmanager.Tagmanager.FilterByRulesResponse> getFilterByRulesMethod;
    if ((getFilterByRulesMethod = TagManagerGrpc.getFilterByRulesMethod) == null) {
      synchronized (TagManagerGrpc.class) {
        if ((getFilterByRulesMethod = TagManagerGrpc.getFilterByRulesMethod) == null) {
          TagManagerGrpc.getFilterByRulesMethod = getFilterByRulesMethod =
              io.grpc.MethodDescriptor.<tagmanager.Tagmanager.FilterByRulesRequest, tagmanager.Tagmanager.FilterByRulesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FilterByRules"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.FilterByRulesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  tagmanager.Tagmanager.FilterByRulesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TagManagerMethodDescriptorSupplier("FilterByRules"))
              .build();
        }
      }
    }
    return getFilterByRulesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TagManagerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TagManagerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TagManagerStub>() {
        @java.lang.Override
        public TagManagerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TagManagerStub(channel, callOptions);
        }
      };
    return TagManagerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TagManagerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TagManagerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TagManagerBlockingStub>() {
        @java.lang.Override
        public TagManagerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TagManagerBlockingStub(channel, callOptions);
        }
      };
    return TagManagerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TagManagerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TagManagerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TagManagerFutureStub>() {
        @java.lang.Override
        public TagManagerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TagManagerFutureStub(channel, callOptions);
        }
      };
    return TagManagerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TagManagerImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Создать владельца тега
     * </pre>
     */
    public void createOwner(tagmanager.Tagmanager.CreateOwnerRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateOwnerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateOwnerMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить владельцев
     * </pre>
     */
    public void getOwners(tagmanager.Tagmanager.GetOwnersRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetOwnersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOwnersMethod(), responseObserver);
    }

    /**
     * <pre>
     * Создать тег для владельца
     * </pre>
     */
    public void createTag(tagmanager.Tagmanager.CreateTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateTagResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateTagMethod(), responseObserver);
    }

    /**
     * <pre>
     * Удалить тег
     * </pre>
     */
    public void deleteTag(tagmanager.Tagmanager.DeleteTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.DeleteTagResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteTagMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить для владельца все используемые им теги
     * </pre>
     */
    public void getTagsByOwner(tagmanager.Tagmanager.GetTagsByOwnerRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByOwnerResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTagsByOwnerMethod(), responseObserver);
    }

    /**
     * <pre>
     * Привязать тег к элементам владельца
     * </pre>
     */
    public void bindTag(tagmanager.Tagmanager.BindTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.BindTagResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBindTagMethod(), responseObserver);
    }

    /**
     * <pre>
     * Удалить привязку по тегу и элементам владельца
     * </pre>
     */
    public void unbindTag(tagmanager.Tagmanager.UnbindTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.UnbindTagResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUnbindTagMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все привязки тега
     * </pre>
     */
    public void getBindsByTag(tagmanager.Tagmanager.GetBindsByTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByTagResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBindsByTagMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все привязки тега
     * </pre>
     */
    public void getBindsByItem(tagmanager.Tagmanager.GetBindsByItemRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByItemResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBindsByItemMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все привязки тегов
     * </pre>
     */
    public void getBindsByItems(tagmanager.Tagmanager.GetBindsByItemsRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByItemsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBindsByItemsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить элементы владельца, у которых есть все теги из списка
     * </pre>
     */
    public void getItemsByTagsList(tagmanager.Tagmanager.GetItemsByTagsListRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetItemsByTagsListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetItemsByTagsListMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все теги (с наименованиями) по элементу
     * </pre>
     */
    public void getTagsByItem(tagmanager.Tagmanager.GetTagsByItemRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByItemResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTagsByItemMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить все теги (с наименованиями) по каждому элементу из списка
     * </pre>
     */
    public void getTagsByItems(tagmanager.Tagmanager.GetTagsByItemsRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByItemsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTagsByItemsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Создать правило
     * </pre>
     */
    public void createRule(tagmanager.Tagmanager.CreateRuleRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateRuleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateRuleMethod(), responseObserver);
    }

    /**
     * <pre>
     * Удалить правило по его ID
     * </pre>
     */
    public void deleteRule(tagmanager.Tagmanager.DeleteRuleRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.DeleteRuleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteRuleMethod(), responseObserver);
    }

    /**
     * <pre>
     * Получить правила, применимые к запрашиваемому владельцу
     * </pre>
     */
    public void getApplicableRules(tagmanager.Tagmanager.GetApplicableRulesRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetApplicableRulesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetApplicableRulesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Отфильтровать элементы по правилам
     * </pre>
     */
    public void filterByRules(tagmanager.Tagmanager.FilterByRulesRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.FilterByRulesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFilterByRulesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateOwnerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.CreateOwnerRequest,
                tagmanager.Tagmanager.CreateOwnerResponse>(
                  this, METHODID_CREATE_OWNER)))
          .addMethod(
            getGetOwnersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetOwnersRequest,
                tagmanager.Tagmanager.GetOwnersResponse>(
                  this, METHODID_GET_OWNERS)))
          .addMethod(
            getCreateTagMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.CreateTagRequest,
                tagmanager.Tagmanager.CreateTagResponse>(
                  this, METHODID_CREATE_TAG)))
          .addMethod(
            getDeleteTagMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.DeleteTagRequest,
                tagmanager.Tagmanager.DeleteTagResponse>(
                  this, METHODID_DELETE_TAG)))
          .addMethod(
            getGetTagsByOwnerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetTagsByOwnerRequest,
                tagmanager.Tagmanager.GetTagsByOwnerResponse>(
                  this, METHODID_GET_TAGS_BY_OWNER)))
          .addMethod(
            getBindTagMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.BindTagRequest,
                tagmanager.Tagmanager.BindTagResponse>(
                  this, METHODID_BIND_TAG)))
          .addMethod(
            getUnbindTagMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.UnbindTagRequest,
                tagmanager.Tagmanager.UnbindTagResponse>(
                  this, METHODID_UNBIND_TAG)))
          .addMethod(
            getGetBindsByTagMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetBindsByTagRequest,
                tagmanager.Tagmanager.GetBindsByTagResponse>(
                  this, METHODID_GET_BINDS_BY_TAG)))
          .addMethod(
            getGetBindsByItemMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetBindsByItemRequest,
                tagmanager.Tagmanager.GetBindsByItemResponse>(
                  this, METHODID_GET_BINDS_BY_ITEM)))
          .addMethod(
            getGetBindsByItemsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetBindsByItemsRequest,
                tagmanager.Tagmanager.GetBindsByItemsResponse>(
                  this, METHODID_GET_BINDS_BY_ITEMS)))
          .addMethod(
            getGetItemsByTagsListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetItemsByTagsListRequest,
                tagmanager.Tagmanager.GetItemsByTagsListResponse>(
                  this, METHODID_GET_ITEMS_BY_TAGS_LIST)))
          .addMethod(
            getGetTagsByItemMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetTagsByItemRequest,
                tagmanager.Tagmanager.GetTagsByItemResponse>(
                  this, METHODID_GET_TAGS_BY_ITEM)))
          .addMethod(
            getGetTagsByItemsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetTagsByItemsRequest,
                tagmanager.Tagmanager.GetTagsByItemsResponse>(
                  this, METHODID_GET_TAGS_BY_ITEMS)))
          .addMethod(
            getCreateRuleMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.CreateRuleRequest,
                tagmanager.Tagmanager.CreateRuleResponse>(
                  this, METHODID_CREATE_RULE)))
          .addMethod(
            getDeleteRuleMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.DeleteRuleRequest,
                tagmanager.Tagmanager.DeleteRuleResponse>(
                  this, METHODID_DELETE_RULE)))
          .addMethod(
            getGetApplicableRulesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.GetApplicableRulesRequest,
                tagmanager.Tagmanager.GetApplicableRulesResponse>(
                  this, METHODID_GET_APPLICABLE_RULES)))
          .addMethod(
            getFilterByRulesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                tagmanager.Tagmanager.FilterByRulesRequest,
                tagmanager.Tagmanager.FilterByRulesResponse>(
                  this, METHODID_FILTER_BY_RULES)))
          .build();
    }
  }

  /**
   */
  public static final class TagManagerStub extends io.grpc.stub.AbstractAsyncStub<TagManagerStub> {
    private TagManagerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TagManagerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TagManagerStub(channel, callOptions);
    }

    /**
     * <pre>
     * Создать владельца тега
     * </pre>
     */
    public void createOwner(tagmanager.Tagmanager.CreateOwnerRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateOwnerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateOwnerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить владельцев
     * </pre>
     */
    public void getOwners(tagmanager.Tagmanager.GetOwnersRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetOwnersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOwnersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Создать тег для владельца
     * </pre>
     */
    public void createTag(tagmanager.Tagmanager.CreateTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateTagResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateTagMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Удалить тег
     * </pre>
     */
    public void deleteTag(tagmanager.Tagmanager.DeleteTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.DeleteTagResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteTagMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить для владельца все используемые им теги
     * </pre>
     */
    public void getTagsByOwner(tagmanager.Tagmanager.GetTagsByOwnerRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByOwnerResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTagsByOwnerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Привязать тег к элементам владельца
     * </pre>
     */
    public void bindTag(tagmanager.Tagmanager.BindTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.BindTagResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBindTagMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Удалить привязку по тегу и элементам владельца
     * </pre>
     */
    public void unbindTag(tagmanager.Tagmanager.UnbindTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.UnbindTagResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUnbindTagMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все привязки тега
     * </pre>
     */
    public void getBindsByTag(tagmanager.Tagmanager.GetBindsByTagRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByTagResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBindsByTagMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все привязки тега
     * </pre>
     */
    public void getBindsByItem(tagmanager.Tagmanager.GetBindsByItemRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByItemResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBindsByItemMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все привязки тегов
     * </pre>
     */
    public void getBindsByItems(tagmanager.Tagmanager.GetBindsByItemsRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByItemsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBindsByItemsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить элементы владельца, у которых есть все теги из списка
     * </pre>
     */
    public void getItemsByTagsList(tagmanager.Tagmanager.GetItemsByTagsListRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetItemsByTagsListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetItemsByTagsListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все теги (с наименованиями) по элементу
     * </pre>
     */
    public void getTagsByItem(tagmanager.Tagmanager.GetTagsByItemRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByItemResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTagsByItemMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить все теги (с наименованиями) по каждому элементу из списка
     * </pre>
     */
    public void getTagsByItems(tagmanager.Tagmanager.GetTagsByItemsRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByItemsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTagsByItemsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Создать правило
     * </pre>
     */
    public void createRule(tagmanager.Tagmanager.CreateRuleRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateRuleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateRuleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Удалить правило по его ID
     * </pre>
     */
    public void deleteRule(tagmanager.Tagmanager.DeleteRuleRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.DeleteRuleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteRuleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Получить правила, применимые к запрашиваемому владельцу
     * </pre>
     */
    public void getApplicableRules(tagmanager.Tagmanager.GetApplicableRulesRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetApplicableRulesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetApplicableRulesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Отфильтровать элементы по правилам
     * </pre>
     */
    public void filterByRules(tagmanager.Tagmanager.FilterByRulesRequest request,
        io.grpc.stub.StreamObserver<tagmanager.Tagmanager.FilterByRulesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFilterByRulesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TagManagerBlockingStub extends io.grpc.stub.AbstractBlockingStub<TagManagerBlockingStub> {
    private TagManagerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TagManagerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TagManagerBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Создать владельца тега
     * </pre>
     */
    public tagmanager.Tagmanager.CreateOwnerResponse createOwner(tagmanager.Tagmanager.CreateOwnerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateOwnerMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить владельцев
     * </pre>
     */
    public tagmanager.Tagmanager.GetOwnersResponse getOwners(tagmanager.Tagmanager.GetOwnersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOwnersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Создать тег для владельца
     * </pre>
     */
    public tagmanager.Tagmanager.CreateTagResponse createTag(tagmanager.Tagmanager.CreateTagRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateTagMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Удалить тег
     * </pre>
     */
    public tagmanager.Tagmanager.DeleteTagResponse deleteTag(tagmanager.Tagmanager.DeleteTagRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteTagMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить для владельца все используемые им теги
     * </pre>
     */
    public tagmanager.Tagmanager.GetTagsByOwnerResponse getTagsByOwner(tagmanager.Tagmanager.GetTagsByOwnerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTagsByOwnerMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Привязать тег к элементам владельца
     * </pre>
     */
    public tagmanager.Tagmanager.BindTagResponse bindTag(tagmanager.Tagmanager.BindTagRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBindTagMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Удалить привязку по тегу и элементам владельца
     * </pre>
     */
    public tagmanager.Tagmanager.UnbindTagResponse unbindTag(tagmanager.Tagmanager.UnbindTagRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUnbindTagMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все привязки тега
     * </pre>
     */
    public tagmanager.Tagmanager.GetBindsByTagResponse getBindsByTag(tagmanager.Tagmanager.GetBindsByTagRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBindsByTagMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все привязки тега
     * </pre>
     */
    public tagmanager.Tagmanager.GetBindsByItemResponse getBindsByItem(tagmanager.Tagmanager.GetBindsByItemRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBindsByItemMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все привязки тегов
     * </pre>
     */
    public tagmanager.Tagmanager.GetBindsByItemsResponse getBindsByItems(tagmanager.Tagmanager.GetBindsByItemsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBindsByItemsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить элементы владельца, у которых есть все теги из списка
     * </pre>
     */
    public tagmanager.Tagmanager.GetItemsByTagsListResponse getItemsByTagsList(tagmanager.Tagmanager.GetItemsByTagsListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetItemsByTagsListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все теги (с наименованиями) по элементу
     * </pre>
     */
    public tagmanager.Tagmanager.GetTagsByItemResponse getTagsByItem(tagmanager.Tagmanager.GetTagsByItemRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTagsByItemMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить все теги (с наименованиями) по каждому элементу из списка
     * </pre>
     */
    public tagmanager.Tagmanager.GetTagsByItemsResponse getTagsByItems(tagmanager.Tagmanager.GetTagsByItemsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTagsByItemsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Создать правило
     * </pre>
     */
    public tagmanager.Tagmanager.CreateRuleResponse createRule(tagmanager.Tagmanager.CreateRuleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateRuleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Удалить правило по его ID
     * </pre>
     */
    public tagmanager.Tagmanager.DeleteRuleResponse deleteRule(tagmanager.Tagmanager.DeleteRuleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteRuleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Получить правила, применимые к запрашиваемому владельцу
     * </pre>
     */
    public tagmanager.Tagmanager.GetApplicableRulesResponse getApplicableRules(tagmanager.Tagmanager.GetApplicableRulesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetApplicableRulesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Отфильтровать элементы по правилам
     * </pre>
     */
    public tagmanager.Tagmanager.FilterByRulesResponse filterByRules(tagmanager.Tagmanager.FilterByRulesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFilterByRulesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TagManagerFutureStub extends io.grpc.stub.AbstractFutureStub<TagManagerFutureStub> {
    private TagManagerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TagManagerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TagManagerFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Создать владельца тега
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.CreateOwnerResponse> createOwner(
        tagmanager.Tagmanager.CreateOwnerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateOwnerMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить владельцев
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetOwnersResponse> getOwners(
        tagmanager.Tagmanager.GetOwnersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOwnersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Создать тег для владельца
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.CreateTagResponse> createTag(
        tagmanager.Tagmanager.CreateTagRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateTagMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Удалить тег
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.DeleteTagResponse> deleteTag(
        tagmanager.Tagmanager.DeleteTagRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteTagMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить для владельца все используемые им теги
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetTagsByOwnerResponse> getTagsByOwner(
        tagmanager.Tagmanager.GetTagsByOwnerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTagsByOwnerMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Привязать тег к элементам владельца
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.BindTagResponse> bindTag(
        tagmanager.Tagmanager.BindTagRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBindTagMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Удалить привязку по тегу и элементам владельца
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.UnbindTagResponse> unbindTag(
        tagmanager.Tagmanager.UnbindTagRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUnbindTagMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все привязки тега
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetBindsByTagResponse> getBindsByTag(
        tagmanager.Tagmanager.GetBindsByTagRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBindsByTagMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все привязки тега
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetBindsByItemResponse> getBindsByItem(
        tagmanager.Tagmanager.GetBindsByItemRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBindsByItemMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все привязки тегов
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetBindsByItemsResponse> getBindsByItems(
        tagmanager.Tagmanager.GetBindsByItemsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBindsByItemsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить элементы владельца, у которых есть все теги из списка
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetItemsByTagsListResponse> getItemsByTagsList(
        tagmanager.Tagmanager.GetItemsByTagsListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetItemsByTagsListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все теги (с наименованиями) по элементу
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetTagsByItemResponse> getTagsByItem(
        tagmanager.Tagmanager.GetTagsByItemRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTagsByItemMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить все теги (с наименованиями) по каждому элементу из списка
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetTagsByItemsResponse> getTagsByItems(
        tagmanager.Tagmanager.GetTagsByItemsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTagsByItemsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Создать правило
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.CreateRuleResponse> createRule(
        tagmanager.Tagmanager.CreateRuleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateRuleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Удалить правило по его ID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.DeleteRuleResponse> deleteRule(
        tagmanager.Tagmanager.DeleteRuleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteRuleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Получить правила, применимые к запрашиваемому владельцу
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.GetApplicableRulesResponse> getApplicableRules(
        tagmanager.Tagmanager.GetApplicableRulesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetApplicableRulesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Отфильтровать элементы по правилам
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<tagmanager.Tagmanager.FilterByRulesResponse> filterByRules(
        tagmanager.Tagmanager.FilterByRulesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFilterByRulesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_OWNER = 0;
  private static final int METHODID_GET_OWNERS = 1;
  private static final int METHODID_CREATE_TAG = 2;
  private static final int METHODID_DELETE_TAG = 3;
  private static final int METHODID_GET_TAGS_BY_OWNER = 4;
  private static final int METHODID_BIND_TAG = 5;
  private static final int METHODID_UNBIND_TAG = 6;
  private static final int METHODID_GET_BINDS_BY_TAG = 7;
  private static final int METHODID_GET_BINDS_BY_ITEM = 8;
  private static final int METHODID_GET_BINDS_BY_ITEMS = 9;
  private static final int METHODID_GET_ITEMS_BY_TAGS_LIST = 10;
  private static final int METHODID_GET_TAGS_BY_ITEM = 11;
  private static final int METHODID_GET_TAGS_BY_ITEMS = 12;
  private static final int METHODID_CREATE_RULE = 13;
  private static final int METHODID_DELETE_RULE = 14;
  private static final int METHODID_GET_APPLICABLE_RULES = 15;
  private static final int METHODID_FILTER_BY_RULES = 16;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TagManagerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TagManagerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_OWNER:
          serviceImpl.createOwner((tagmanager.Tagmanager.CreateOwnerRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateOwnerResponse>) responseObserver);
          break;
        case METHODID_GET_OWNERS:
          serviceImpl.getOwners((tagmanager.Tagmanager.GetOwnersRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetOwnersResponse>) responseObserver);
          break;
        case METHODID_CREATE_TAG:
          serviceImpl.createTag((tagmanager.Tagmanager.CreateTagRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateTagResponse>) responseObserver);
          break;
        case METHODID_DELETE_TAG:
          serviceImpl.deleteTag((tagmanager.Tagmanager.DeleteTagRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.DeleteTagResponse>) responseObserver);
          break;
        case METHODID_GET_TAGS_BY_OWNER:
          serviceImpl.getTagsByOwner((tagmanager.Tagmanager.GetTagsByOwnerRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByOwnerResponse>) responseObserver);
          break;
        case METHODID_BIND_TAG:
          serviceImpl.bindTag((tagmanager.Tagmanager.BindTagRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.BindTagResponse>) responseObserver);
          break;
        case METHODID_UNBIND_TAG:
          serviceImpl.unbindTag((tagmanager.Tagmanager.UnbindTagRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.UnbindTagResponse>) responseObserver);
          break;
        case METHODID_GET_BINDS_BY_TAG:
          serviceImpl.getBindsByTag((tagmanager.Tagmanager.GetBindsByTagRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByTagResponse>) responseObserver);
          break;
        case METHODID_GET_BINDS_BY_ITEM:
          serviceImpl.getBindsByItem((tagmanager.Tagmanager.GetBindsByItemRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByItemResponse>) responseObserver);
          break;
        case METHODID_GET_BINDS_BY_ITEMS:
          serviceImpl.getBindsByItems((tagmanager.Tagmanager.GetBindsByItemsRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetBindsByItemsResponse>) responseObserver);
          break;
        case METHODID_GET_ITEMS_BY_TAGS_LIST:
          serviceImpl.getItemsByTagsList((tagmanager.Tagmanager.GetItemsByTagsListRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetItemsByTagsListResponse>) responseObserver);
          break;
        case METHODID_GET_TAGS_BY_ITEM:
          serviceImpl.getTagsByItem((tagmanager.Tagmanager.GetTagsByItemRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByItemResponse>) responseObserver);
          break;
        case METHODID_GET_TAGS_BY_ITEMS:
          serviceImpl.getTagsByItems((tagmanager.Tagmanager.GetTagsByItemsRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetTagsByItemsResponse>) responseObserver);
          break;
        case METHODID_CREATE_RULE:
          serviceImpl.createRule((tagmanager.Tagmanager.CreateRuleRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.CreateRuleResponse>) responseObserver);
          break;
        case METHODID_DELETE_RULE:
          serviceImpl.deleteRule((tagmanager.Tagmanager.DeleteRuleRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.DeleteRuleResponse>) responseObserver);
          break;
        case METHODID_GET_APPLICABLE_RULES:
          serviceImpl.getApplicableRules((tagmanager.Tagmanager.GetApplicableRulesRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.GetApplicableRulesResponse>) responseObserver);
          break;
        case METHODID_FILTER_BY_RULES:
          serviceImpl.filterByRules((tagmanager.Tagmanager.FilterByRulesRequest) request,
              (io.grpc.stub.StreamObserver<tagmanager.Tagmanager.FilterByRulesResponse>) responseObserver);
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

  private static abstract class TagManagerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TagManagerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return tagmanager.Tagmanager.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TagManager");
    }
  }

  private static final class TagManagerFileDescriptorSupplier
      extends TagManagerBaseDescriptorSupplier {
    TagManagerFileDescriptorSupplier() {}
  }

  private static final class TagManagerMethodDescriptorSupplier
      extends TagManagerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TagManagerMethodDescriptorSupplier(String methodName) {
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
      synchronized (TagManagerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TagManagerFileDescriptorSupplier())
              .addMethod(getCreateOwnerMethod())
              .addMethod(getGetOwnersMethod())
              .addMethod(getCreateTagMethod())
              .addMethod(getDeleteTagMethod())
              .addMethod(getGetTagsByOwnerMethod())
              .addMethod(getBindTagMethod())
              .addMethod(getUnbindTagMethod())
              .addMethod(getGetBindsByTagMethod())
              .addMethod(getGetBindsByItemMethod())
              .addMethod(getGetBindsByItemsMethod())
              .addMethod(getGetItemsByTagsListMethod())
              .addMethod(getGetTagsByItemMethod())
              .addMethod(getGetTagsByItemsMethod())
              .addMethod(getCreateRuleMethod())
              .addMethod(getDeleteRuleMethod())
              .addMethod(getGetApplicableRulesMethod())
              .addMethod(getFilterByRulesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
