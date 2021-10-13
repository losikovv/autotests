package product_hub_front_meta;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/product_hub/product-hub-front-meta.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductHubFrontMetaGrpc {

  private ProductHubFrontMetaGrpc() {}

  public static final String SERVICE_NAME = "product_hub_front_meta.ProductHubFrontMeta";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> getGetAllCategoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllCategories",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> getGetAllCategoriesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> getGetAllCategoriesMethod;
    if ((getGetAllCategoriesMethod = ProductHubFrontMetaGrpc.getGetAllCategoriesMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllCategoriesMethod = ProductHubFrontMetaGrpc.getGetAllCategoriesMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllCategoriesMethod = getGetAllCategoriesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllCategories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllCategories"))
              .build();
        }
      }
    }
    return getGetAllCategoriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> getGetAllCategoriesWithStoresMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllCategoriesWithStores",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> getGetAllCategoriesWithStoresMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> getGetAllCategoriesWithStoresMethod;
    if ((getGetAllCategoriesWithStoresMethod = ProductHubFrontMetaGrpc.getGetAllCategoriesWithStoresMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllCategoriesWithStoresMethod = ProductHubFrontMetaGrpc.getGetAllCategoriesWithStoresMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllCategoriesWithStoresMethod = getGetAllCategoriesWithStoresMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllCategoriesWithStores"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllCategoriesWithStores"))
              .build();
        }
      }
    }
    return getGetAllCategoriesWithStoresMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> getGetCategoriesByCategoryIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCategoriesByCategoryIDs",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> getGetCategoriesByCategoryIDsMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> getGetCategoriesByCategoryIDsMethod;
    if ((getGetCategoriesByCategoryIDsMethod = ProductHubFrontMetaGrpc.getGetCategoriesByCategoryIDsMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetCategoriesByCategoryIDsMethod = ProductHubFrontMetaGrpc.getGetCategoriesByCategoryIDsMethod) == null) {
          ProductHubFrontMetaGrpc.getGetCategoriesByCategoryIDsMethod = getGetCategoriesByCategoryIDsMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCategoriesByCategoryIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetCategoriesByCategoryIDs"))
              .build();
        }
      }
    }
    return getGetCategoriesByCategoryIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> getGetCategoryFiltersByCategoryIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCategoryFiltersByCategoryIDs",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> getGetCategoryFiltersByCategoryIDsMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> getGetCategoryFiltersByCategoryIDsMethod;
    if ((getGetCategoryFiltersByCategoryIDsMethod = ProductHubFrontMetaGrpc.getGetCategoryFiltersByCategoryIDsMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetCategoryFiltersByCategoryIDsMethod = ProductHubFrontMetaGrpc.getGetCategoryFiltersByCategoryIDsMethod) == null) {
          ProductHubFrontMetaGrpc.getGetCategoryFiltersByCategoryIDsMethod = getGetCategoryFiltersByCategoryIDsMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCategoryFiltersByCategoryIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetCategoryFiltersByCategoryIDs"))
              .build();
        }
      }
    }
    return getGetCategoryFiltersByCategoryIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> getGetAttributesByKeysMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAttributesByKeys",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> getGetAttributesByKeysMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> getGetAttributesByKeysMethod;
    if ((getGetAttributesByKeysMethod = ProductHubFrontMetaGrpc.getGetAttributesByKeysMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAttributesByKeysMethod = ProductHubFrontMetaGrpc.getGetAttributesByKeysMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAttributesByKeysMethod = getGetAttributesByKeysMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAttributesByKeys"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAttributesByKeys"))
              .build();
        }
      }
    }
    return getGetAttributesByKeysMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> getGetAllAttributesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllAttributes",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> getGetAllAttributesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> getGetAllAttributesMethod;
    if ((getGetAllAttributesMethod = ProductHubFrontMetaGrpc.getGetAllAttributesMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllAttributesMethod = ProductHubFrontMetaGrpc.getGetAllAttributesMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllAttributesMethod = getGetAllAttributesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllAttributes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllAttributes"))
              .build();
        }
      }
    }
    return getGetAllAttributesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> getGetAllDictionariesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllDictionaries",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> getGetAllDictionariesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> getGetAllDictionariesMethod;
    if ((getGetAllDictionariesMethod = ProductHubFrontMetaGrpc.getGetAllDictionariesMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllDictionariesMethod = ProductHubFrontMetaGrpc.getGetAllDictionariesMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllDictionariesMethod = getGetAllDictionariesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllDictionaries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllDictionaries"))
              .build();
        }
      }
    }
    return getGetAllDictionariesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> getGetAllDictionaryValuesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllDictionaryValues",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> getGetAllDictionaryValuesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> getGetAllDictionaryValuesMethod;
    if ((getGetAllDictionaryValuesMethod = ProductHubFrontMetaGrpc.getGetAllDictionaryValuesMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllDictionaryValuesMethod = ProductHubFrontMetaGrpc.getGetAllDictionaryValuesMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllDictionaryValuesMethod = getGetAllDictionaryValuesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllDictionaryValues"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllDictionaryValues"))
              .build();
        }
      }
    }
    return getGetAllDictionaryValuesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> getGetRetailerStoresMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRetailerStores",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> getGetRetailerStoresMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> getGetRetailerStoresMethod;
    if ((getGetRetailerStoresMethod = ProductHubFrontMetaGrpc.getGetRetailerStoresMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetRetailerStoresMethod = ProductHubFrontMetaGrpc.getGetRetailerStoresMethod) == null) {
          ProductHubFrontMetaGrpc.getGetRetailerStoresMethod = getGetRetailerStoresMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRetailerStores"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetRetailerStores"))
              .build();
        }
      }
    }
    return getGetRetailerStoresMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductHubFrontMetaStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontMetaStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontMetaStub>() {
        @java.lang.Override
        public ProductHubFrontMetaStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontMetaStub(channel, callOptions);
        }
      };
    return ProductHubFrontMetaStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductHubFrontMetaBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontMetaBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontMetaBlockingStub>() {
        @java.lang.Override
        public ProductHubFrontMetaBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontMetaBlockingStub(channel, callOptions);
        }
      };
    return ProductHubFrontMetaBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductHubFrontMetaFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontMetaFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontMetaFutureStub>() {
        @java.lang.Override
        public ProductHubFrontMetaFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontMetaFutureStub(channel, callOptions);
        }
      };
    return ProductHubFrontMetaFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ProductHubFrontMetaImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAllCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllCategoriesMethod(), responseObserver);
    }

    /**
     */
    public void getAllCategoriesWithStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllCategoriesWithStoresMethod(), responseObserver);
    }

    /**
     */
    public void getCategoriesByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCategoriesByCategoryIDsMethod(), responseObserver);
    }

    /**
     */
    public void getCategoryFiltersByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCategoryFiltersByCategoryIDsMethod(), responseObserver);
    }

    /**
     */
    public void getAttributesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAttributesByKeysMethod(), responseObserver);
    }

    /**
     */
    public void getAllAttributes(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllAttributesMethod(), responseObserver);
    }

    /**
     */
    public void getAllDictionaries(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllDictionariesMethod(), responseObserver);
    }

    /**
     */
    public void getAllDictionaryValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllDictionaryValuesMethod(), responseObserver);
    }

    /**
     */
    public void getRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRetailerStoresMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAllCategoriesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse>(
                  this, METHODID_GET_ALL_CATEGORIES)))
          .addMethod(
            getGetAllCategoriesWithStoresMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse>(
                  this, METHODID_GET_ALL_CATEGORIES_WITH_STORES)))
          .addMethod(
            getGetCategoriesByCategoryIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse>(
                  this, METHODID_GET_CATEGORIES_BY_CATEGORY_IDS)))
          .addMethod(
            getGetCategoryFiltersByCategoryIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse>(
                  this, METHODID_GET_CATEGORY_FILTERS_BY_CATEGORY_IDS)))
          .addMethod(
            getGetAttributesByKeysMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse>(
                  this, METHODID_GET_ATTRIBUTES_BY_KEYS)))
          .addMethod(
            getGetAllAttributesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse>(
                  this, METHODID_GET_ALL_ATTRIBUTES)))
          .addMethod(
            getGetAllDictionariesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse>(
                  this, METHODID_GET_ALL_DICTIONARIES)))
          .addMethod(
            getGetAllDictionaryValuesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse>(
                  this, METHODID_GET_ALL_DICTIONARY_VALUES)))
          .addMethod(
            getGetRetailerStoresMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse>(
                  this, METHODID_GET_RETAILER_STORES)))
          .build();
    }
  }

  /**
   */
  public static final class ProductHubFrontMetaStub extends io.grpc.stub.AbstractAsyncStub<ProductHubFrontMetaStub> {
    private ProductHubFrontMetaStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontMetaStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontMetaStub(channel, callOptions);
    }

    /**
     */
    public void getAllCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllCategoriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllCategoriesWithStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllCategoriesWithStoresMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCategoriesByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCategoriesByCategoryIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCategoryFiltersByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCategoryFiltersByCategoryIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAttributesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAttributesByKeysMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllAttributes(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllAttributesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllDictionaries(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllDictionariesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllDictionaryValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllDictionaryValuesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRetailerStoresMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProductHubFrontMetaBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductHubFrontMetaBlockingStub> {
    private ProductHubFrontMetaBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontMetaBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontMetaBlockingStub(channel, callOptions);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse getAllCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllCategoriesMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse getAllCategoriesWithStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllCategoriesWithStoresMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse getCategoriesByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCategoriesByCategoryIDsMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse getCategoryFiltersByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCategoryFiltersByCategoryIDsMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse getAttributesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAttributesByKeysMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse getAllAttributes(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllAttributesMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse getAllDictionaries(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllDictionariesMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse getAllDictionaryValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllDictionaryValuesMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse getRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRetailerStoresMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProductHubFrontMetaFutureStub extends io.grpc.stub.AbstractFutureStub<ProductHubFrontMetaFutureStub> {
    private ProductHubFrontMetaFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontMetaFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontMetaFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> getAllCategories(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllCategoriesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> getAllCategoriesWithStores(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllCategoriesWithStoresMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> getCategoriesByCategoryIDs(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCategoriesByCategoryIDsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> getCategoryFiltersByCategoryIDs(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCategoryFiltersByCategoryIDsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> getAttributesByKeys(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAttributesByKeysMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> getAllAttributes(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllAttributesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> getAllDictionaries(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllDictionariesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> getAllDictionaryValues(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllDictionaryValuesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> getRetailerStores(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRetailerStoresMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_CATEGORIES = 0;
  private static final int METHODID_GET_ALL_CATEGORIES_WITH_STORES = 1;
  private static final int METHODID_GET_CATEGORIES_BY_CATEGORY_IDS = 2;
  private static final int METHODID_GET_CATEGORY_FILTERS_BY_CATEGORY_IDS = 3;
  private static final int METHODID_GET_ATTRIBUTES_BY_KEYS = 4;
  private static final int METHODID_GET_ALL_ATTRIBUTES = 5;
  private static final int METHODID_GET_ALL_DICTIONARIES = 6;
  private static final int METHODID_GET_ALL_DICTIONARY_VALUES = 7;
  private static final int METHODID_GET_RETAILER_STORES = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductHubFrontMetaImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductHubFrontMetaImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_CATEGORIES:
          serviceImpl.getAllCategories((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_CATEGORIES_WITH_STORES:
          serviceImpl.getAllCategoriesWithStores((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse>) responseObserver);
          break;
        case METHODID_GET_CATEGORIES_BY_CATEGORY_IDS:
          serviceImpl.getCategoriesByCategoryIDs((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse>) responseObserver);
          break;
        case METHODID_GET_CATEGORY_FILTERS_BY_CATEGORY_IDS:
          serviceImpl.getCategoryFiltersByCategoryIDs((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse>) responseObserver);
          break;
        case METHODID_GET_ATTRIBUTES_BY_KEYS:
          serviceImpl.getAttributesByKeys((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_ATTRIBUTES:
          serviceImpl.getAllAttributes((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_DICTIONARIES:
          serviceImpl.getAllDictionaries((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_DICTIONARY_VALUES:
          serviceImpl.getAllDictionaryValues((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse>) responseObserver);
          break;
        case METHODID_GET_RETAILER_STORES:
          serviceImpl.getRetailerStores((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse>) responseObserver);
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

  private static abstract class ProductHubFrontMetaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductHubFrontMetaBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return product_hub_front_meta.ProductHubFrontMetaOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductHubFrontMeta");
    }
  }

  private static final class ProductHubFrontMetaFileDescriptorSupplier
      extends ProductHubFrontMetaBaseDescriptorSupplier {
    ProductHubFrontMetaFileDescriptorSupplier() {}
  }

  private static final class ProductHubFrontMetaMethodDescriptorSupplier
      extends ProductHubFrontMetaBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductHubFrontMetaMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProductHubFrontMetaGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductHubFrontMetaFileDescriptorSupplier())
              .addMethod(getGetAllCategoriesMethod())
              .addMethod(getGetAllCategoriesWithStoresMethod())
              .addMethod(getGetCategoriesByCategoryIDsMethod())
              .addMethod(getGetCategoryFiltersByCategoryIDsMethod())
              .addMethod(getGetAttributesByKeysMethod())
              .addMethod(getGetAllAttributesMethod())
              .addMethod(getGetAllDictionariesMethod())
              .addMethod(getGetAllDictionaryValuesMethod())
              .addMethod(getGetRetailerStoresMethod())
              .build();
        }
      }
    }
    return result;
  }
}
