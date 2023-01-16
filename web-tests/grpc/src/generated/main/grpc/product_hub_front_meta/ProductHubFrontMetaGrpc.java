package product_hub_front_meta;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * slack:
 *    #product-hub
 * base:
 *     base-product-hub.paas-content-product-hub:3009
 * local:
 *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
 * swagger prod:
 *     https://paas-content-product-hub.sbmt.io/api
 * swagger stg:
 *     https://paas-content-product-hub.gw-stage.sbmt.io/api
 * public prod grpc uri:
 *    paas-content-product-hub.sbmt.io:443
 * public stg grpc uri:
 *    paas-content-product-hub.gw-stage.sbmt.io:443
 * kuber prod grpc uri:
 *    base-product-hub.paas-content-product-hub:3009
 * description:
 *    Мета сервис product-hub возвращающий мета данные (Category, Attribute, Dictionary, DictionaryValue).
 *    Предназначет для real-time нагрузки.
 *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
 * </pre>
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

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse> getGetAllCategoriesWithStoresByCursorIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllCategoriesWithStoresByCursorID",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse> getGetAllCategoriesWithStoresByCursorIDMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse> getGetAllCategoriesWithStoresByCursorIDMethod;
    if ((getGetAllCategoriesWithStoresByCursorIDMethod = ProductHubFrontMetaGrpc.getGetAllCategoriesWithStoresByCursorIDMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllCategoriesWithStoresByCursorIDMethod = ProductHubFrontMetaGrpc.getGetAllCategoriesWithStoresByCursorIDMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllCategoriesWithStoresByCursorIDMethod = getGetAllCategoriesWithStoresByCursorIDMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllCategoriesWithStoresByCursorID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllCategoriesWithStoresByCursorID"))
              .build();
        }
      }
    }
    return getGetAllCategoriesWithStoresByCursorIDMethod;
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

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse> getGetDictionariesByKeysMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDictionariesByKeys",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse> getGetDictionariesByKeysMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse> getGetDictionariesByKeysMethod;
    if ((getGetDictionariesByKeysMethod = ProductHubFrontMetaGrpc.getGetDictionariesByKeysMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetDictionariesByKeysMethod = ProductHubFrontMetaGrpc.getGetDictionariesByKeysMethod) == null) {
          ProductHubFrontMetaGrpc.getGetDictionariesByKeysMethod = getGetDictionariesByKeysMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDictionariesByKeys"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetDictionariesByKeys"))
              .build();
        }
      }
    }
    return getGetDictionariesByKeysMethod;
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

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse> getGetAllOriginalCategoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllOriginalCategories",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse> getGetAllOriginalCategoriesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse> getGetAllOriginalCategoriesMethod;
    if ((getGetAllOriginalCategoriesMethod = ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllOriginalCategoriesMethod = ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesMethod = getGetAllOriginalCategoriesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllOriginalCategories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllOriginalCategories"))
              .build();
        }
      }
    }
    return getGetAllOriginalCategoriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse> getGetOriginalCategoriesByIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOriginalCategoriesByIDs",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse> getGetOriginalCategoriesByIDsMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse> getGetOriginalCategoriesByIDsMethod;
    if ((getGetOriginalCategoriesByIDsMethod = ProductHubFrontMetaGrpc.getGetOriginalCategoriesByIDsMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetOriginalCategoriesByIDsMethod = ProductHubFrontMetaGrpc.getGetOriginalCategoriesByIDsMethod) == null) {
          ProductHubFrontMetaGrpc.getGetOriginalCategoriesByIDsMethod = getGetOriginalCategoriesByIDsMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOriginalCategoriesByIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetOriginalCategoriesByIDs"))
              .build();
        }
      }
    }
    return getGetOriginalCategoriesByIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse> getGetAllOriginalCategoriesByCursorIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllOriginalCategoriesByCursorID",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse> getGetAllOriginalCategoriesByCursorIDMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse> getGetAllOriginalCategoriesByCursorIDMethod;
    if ((getGetAllOriginalCategoriesByCursorIDMethod = ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesByCursorIDMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllOriginalCategoriesByCursorIDMethod = ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesByCursorIDMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesByCursorIDMethod = getGetAllOriginalCategoriesByCursorIDMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllOriginalCategoriesByCursorID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllOriginalCategoriesByCursorID"))
              .build();
        }
      }
    }
    return getGetAllOriginalCategoriesByCursorIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse> getGetAllOriginalCategoriesByStoreIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllOriginalCategoriesByStoreID",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse> getGetAllOriginalCategoriesByStoreIDMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse> getGetAllOriginalCategoriesByStoreIDMethod;
    if ((getGetAllOriginalCategoriesByStoreIDMethod = ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesByStoreIDMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllOriginalCategoriesByStoreIDMethod = ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesByStoreIDMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllOriginalCategoriesByStoreIDMethod = getGetAllOriginalCategoriesByStoreIDMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllOriginalCategoriesByStoreID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllOriginalCategoriesByStoreID"))
              .build();
        }
      }
    }
    return getGetAllOriginalCategoriesByStoreIDMethod;
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

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse> getGetAllRetailerStoresMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllRetailerStores",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse> getGetAllRetailerStoresMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse> getGetAllRetailerStoresMethod;
    if ((getGetAllRetailerStoresMethod = ProductHubFrontMetaGrpc.getGetAllRetailerStoresMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetAllRetailerStoresMethod = ProductHubFrontMetaGrpc.getGetAllRetailerStoresMethod) == null) {
          ProductHubFrontMetaGrpc.getGetAllRetailerStoresMethod = getGetAllRetailerStoresMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllRetailerStores"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetAllRetailerStores"))
              .build();
        }
      }
    }
    return getGetAllRetailerStoresMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse> getGetDictionaryAttributesValuesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDictionaryAttributesValues",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse> getGetDictionaryAttributesValuesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse> getGetDictionaryAttributesValuesMethod;
    if ((getGetDictionaryAttributesValuesMethod = ProductHubFrontMetaGrpc.getGetDictionaryAttributesValuesMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetDictionaryAttributesValuesMethod = ProductHubFrontMetaGrpc.getGetDictionaryAttributesValuesMethod) == null) {
          ProductHubFrontMetaGrpc.getGetDictionaryAttributesValuesMethod = getGetDictionaryAttributesValuesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDictionaryAttributesValues"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetDictionaryAttributesValues"))
              .build();
        }
      }
    }
    return getGetDictionaryAttributesValuesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse> getGetPopularitiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPopularities",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse> getGetPopularitiesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse> getGetPopularitiesMethod;
    if ((getGetPopularitiesMethod = ProductHubFrontMetaGrpc.getGetPopularitiesMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetPopularitiesMethod = ProductHubFrontMetaGrpc.getGetPopularitiesMethod) == null) {
          ProductHubFrontMetaGrpc.getGetPopularitiesMethod = getGetPopularitiesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPopularities"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetPopularities"))
              .build();
        }
      }
    }
    return getGetPopularitiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse> getGetRetailerIDsByStoreIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRetailerIDsByStoreIDs",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse> getGetRetailerIDsByStoreIDsMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse> getGetRetailerIDsByStoreIDsMethod;
    if ((getGetRetailerIDsByStoreIDsMethod = ProductHubFrontMetaGrpc.getGetRetailerIDsByStoreIDsMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetRetailerIDsByStoreIDsMethod = ProductHubFrontMetaGrpc.getGetRetailerIDsByStoreIDsMethod) == null) {
          ProductHubFrontMetaGrpc.getGetRetailerIDsByStoreIDsMethod = getGetRetailerIDsByStoreIDsMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRetailerIDsByStoreIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetRetailerIDsByStoreIDs"))
              .build();
        }
      }
    }
    return getGetRetailerIDsByStoreIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse> getGetDictionaryValuesByPermalinksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDictionaryValuesByPermalinks",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse> getGetDictionaryValuesByPermalinksMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse> getGetDictionaryValuesByPermalinksMethod;
    if ((getGetDictionaryValuesByPermalinksMethod = ProductHubFrontMetaGrpc.getGetDictionaryValuesByPermalinksMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetDictionaryValuesByPermalinksMethod = ProductHubFrontMetaGrpc.getGetDictionaryValuesByPermalinksMethod) == null) {
          ProductHubFrontMetaGrpc.getGetDictionaryValuesByPermalinksMethod = getGetDictionaryValuesByPermalinksMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetDictionaryValuesByPermalinks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetDictionaryValuesByPermalinks"))
              .build();
        }
      }
    }
    return getGetDictionaryValuesByPermalinksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse> getGetRetailersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRetailers",
      requestType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest.class,
      responseType = product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest,
      product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse> getGetRetailersMethod() {
    io.grpc.MethodDescriptor<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse> getGetRetailersMethod;
    if ((getGetRetailersMethod = ProductHubFrontMetaGrpc.getGetRetailersMethod) == null) {
      synchronized (ProductHubFrontMetaGrpc.class) {
        if ((getGetRetailersMethod = ProductHubFrontMetaGrpc.getGetRetailersMethod) == null) {
          ProductHubFrontMetaGrpc.getGetRetailersMethod = getGetRetailersMethod =
              io.grpc.MethodDescriptor.<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest, product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRetailers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontMetaMethodDescriptorSupplier("GetRetailers"))
              .build();
        }
      }
    }
    return getGetRetailersMethod;
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
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub.paas-content-product-hub:3009
   * local:
   *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-product-hub:3009
   * description:
   *    Мета сервис product-hub возвращающий мета данные (Category, Attribute, Dictionary, DictionaryValue).
   *    Предназначет для real-time нагрузки.
   *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
   * </pre>
   */
  public static abstract class ProductHubFrontMetaImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Возвращает плоский список всех категорий батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategories
     * </pre>
     */
    public void getAllCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllCategoriesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает плоский список всех категорий со списком идентификаторов магазинов батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategoriesWithStores
     * </pre>
     */
    public void getAllCategoriesWithStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllCategoriesWithStoresMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает плоский список всех категорий со списком идентификаторов магазинов батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    cursor_id - ид запрашиваемых данных, не является последовательным идентификатором, нужно передавать в каждый последующий запрос
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "cursor_id": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategoriesWithStoresByCursorID
     * </pre>
     */
    public void getAllCategoriesWithStoresByCursorID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllCategoriesWithStoresByCursorIDMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает экземпляры категорий по идентификаторам
     * Обязательные параметры:
     *    category_ids - идентификатор категории
     * Примеры:
     *    Вернуть категории по ид:
     *        grpcurl -d '{"category_ids": ["175", "5440"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetCategoriesByCategoryIDs
     * </pre>
     */
    public void getCategoriesByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCategoriesByCategoryIDsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список ключей атрибутов которые необходимо вывести в фасетах фильтров
     * Обязательные параметры:
     *    category_ids - идентификатор категории
     * Примеры:
     *    Вернуть ключи атрибутов по ид категорий:
     *        grpcurl -d '{"category_ids": ["175", "5440"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetCategoryFiltersByCategoryIDs
     * </pre>
     */
    public void getCategoryFiltersByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCategoryFiltersByCategoryIDsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает экземпляры атрибутов по ключам
     * Обязательные параметры:
     *    attribute_keys - ключи атрибутов
     * Примеры:
     *    Вернуть экземпляры атрибутов:
     *        grpcurl -d '{"attribute_keys": ["brand", "image"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAttributesByKeys
     * </pre>
     */
    public void getAttributesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAttributesByKeysMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список всех атрибутов
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllAttributes
     * </pre>
     */
    public void getAllAttributes(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllAttributesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список всех словарей
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все словари:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllDictionaries
     * </pre>
     */
    public void getAllDictionaries(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllDictionariesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает экземпляры словарей по ключам
     * Обязательные параметры:
     *    dictionary_keys - ключи словарей
     * Примеры:
     *    Вернуть экземпляры словарей:
     *        grpcurl -d '{"dictionary_keys": ["brand"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetDictionariesByKeys
     * </pre>
     */
    public void getDictionariesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDictionariesByKeysMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает все словарные значения конкретного словаря
     * Обязательные параметры:
     *    dictionary_key - ключ словаря
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть значения словарей:
     *        grpcurl -d '{"dictionary_key": "brand", "limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllDictionaryValues
     * </pre>
     */
    public void getAllDictionaryValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllDictionaryValuesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategories
     * </pre>
     */
    public void getAllOriginalCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllOriginalCategoriesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    original_ids - идентификаторы mysql.taxons
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"original_ids": [73278]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetOriginalCategoriesByIDs
     * </pre>
     */
    public void getOriginalCategoriesByIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOriginalCategoriesByIDsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    cursor_id - ид запрашиваемых данных, не является последовательным идентификатором, нужно передавать в каждый последующий запрос
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"limit": 10, "cursord_id": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategoriesByCursorID
     * </pre>
     */
    public void getAllOriginalCategoriesByCursorID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllOriginalCategoriesByCursorIDMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons) для магазина
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"store_id": "1", "limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategoriesByStoreID
     * </pre>
     */
    public void getAllOriginalCategoriesByStoreID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllOriginalCategoriesByStoreIDMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список идентификаторов магазинов для конкретных ритейлеров
     * Обязательные параметры:
     *    retailer_ids - идентификаторы ритейлеров
     * Примеры:
     *    Вернуть идентификаторы магазины:
     *        grpcurl -d '{"retailer_ids": ["1", "3"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetRetailerStores
     * </pre>
     */
    public void getRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRetailerStoresMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список всех идентификаторов магазинов с ретейлером по лимиту, оффсету
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть идентификаторы магазины:
     *        grpcurl -d '{"limit": 10, "offset": 5}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllRetailerStores
     * </pre>
     */
    public void getAllRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllRetailerStoresMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список словарных значений по ключу атрибута и идентифиатору словарного значения
     * Обязательные параметры:
     *    attribute_key - ключ атрибута
     *    dictionary_value_ids - идентификаторы словарных значений
     * Примеры:
     *    Вернуть словарные значения:
     *        grpcurl -d '{"attributes": [{"attribute_key": "brand", "dictionary_value_ids": ["48739", "30248"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetDictionaryAttributesValues
     * </pre>
     */
    public void getDictionaryAttributesValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDictionaryAttributesValuesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список популярностей по ключу магазина и скушкам
     * Обязательные параметры:
     *    store_id - ключ магазина
     * Примеры:
     * </pre>
     */
    public void getPopularities(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPopularitiesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список популярностей по ключу магазина и скушкам
     * Обязательные параметры:
     *    store_id - ключ магазина
     * Примеры:
     * </pre>
     */
    public void getRetailerIDsByStoreIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRetailerIDsByStoreIDsMethod(), responseObserver);
    }

    /**
     */
    public void getDictionaryValuesByPermalinks(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetDictionaryValuesByPermalinksMethod(), responseObserver);
    }

    /**
     * <pre>
     * Возвращает список ретейлеров по retailer_id
     * Обязательные параметры:
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Возвращает список ретейлеров (retailer):
     *        grpcurl -d '{"retailer_ids": ["1", "330"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetRetailers
     * </pre>
     */
    public void getRetailers(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRetailersMethod(), responseObserver);
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
            getGetAllCategoriesWithStoresByCursorIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse>(
                  this, METHODID_GET_ALL_CATEGORIES_WITH_STORES_BY_CURSOR_ID)))
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
            getGetDictionariesByKeysMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse>(
                  this, METHODID_GET_DICTIONARIES_BY_KEYS)))
          .addMethod(
            getGetAllDictionaryValuesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse>(
                  this, METHODID_GET_ALL_DICTIONARY_VALUES)))
          .addMethod(
            getGetAllOriginalCategoriesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse>(
                  this, METHODID_GET_ALL_ORIGINAL_CATEGORIES)))
          .addMethod(
            getGetOriginalCategoriesByIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse>(
                  this, METHODID_GET_ORIGINAL_CATEGORIES_BY_IDS)))
          .addMethod(
            getGetAllOriginalCategoriesByCursorIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse>(
                  this, METHODID_GET_ALL_ORIGINAL_CATEGORIES_BY_CURSOR_ID)))
          .addMethod(
            getGetAllOriginalCategoriesByStoreIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse>(
                  this, METHODID_GET_ALL_ORIGINAL_CATEGORIES_BY_STORE_ID)))
          .addMethod(
            getGetRetailerStoresMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse>(
                  this, METHODID_GET_RETAILER_STORES)))
          .addMethod(
            getGetAllRetailerStoresMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse>(
                  this, METHODID_GET_ALL_RETAILER_STORES)))
          .addMethod(
            getGetDictionaryAttributesValuesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse>(
                  this, METHODID_GET_DICTIONARY_ATTRIBUTES_VALUES)))
          .addMethod(
            getGetPopularitiesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse>(
                  this, METHODID_GET_POPULARITIES)))
          .addMethod(
            getGetRetailerIDsByStoreIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse>(
                  this, METHODID_GET_RETAILER_IDS_BY_STORE_IDS)))
          .addMethod(
            getGetDictionaryValuesByPermalinksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse>(
                  this, METHODID_GET_DICTIONARY_VALUES_BY_PERMALINKS)))
          .addMethod(
            getGetRetailersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest,
                product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse>(
                  this, METHODID_GET_RETAILERS)))
          .build();
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub.paas-content-product-hub:3009
   * local:
   *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-product-hub:3009
   * description:
   *    Мета сервис product-hub возвращающий мета данные (Category, Attribute, Dictionary, DictionaryValue).
   *    Предназначет для real-time нагрузки.
   *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
   * </pre>
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
     * <pre>
     * Возвращает плоский список всех категорий батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategories
     * </pre>
     */
    public void getAllCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllCategoriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает плоский список всех категорий со списком идентификаторов магазинов батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategoriesWithStores
     * </pre>
     */
    public void getAllCategoriesWithStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllCategoriesWithStoresMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает плоский список всех категорий со списком идентификаторов магазинов батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    cursor_id - ид запрашиваемых данных, не является последовательным идентификатором, нужно передавать в каждый последующий запрос
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "cursor_id": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategoriesWithStoresByCursorID
     * </pre>
     */
    public void getAllCategoriesWithStoresByCursorID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllCategoriesWithStoresByCursorIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает экземпляры категорий по идентификаторам
     * Обязательные параметры:
     *    category_ids - идентификатор категории
     * Примеры:
     *    Вернуть категории по ид:
     *        grpcurl -d '{"category_ids": ["175", "5440"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetCategoriesByCategoryIDs
     * </pre>
     */
    public void getCategoriesByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCategoriesByCategoryIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список ключей атрибутов которые необходимо вывести в фасетах фильтров
     * Обязательные параметры:
     *    category_ids - идентификатор категории
     * Примеры:
     *    Вернуть ключи атрибутов по ид категорий:
     *        grpcurl -d '{"category_ids": ["175", "5440"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetCategoryFiltersByCategoryIDs
     * </pre>
     */
    public void getCategoryFiltersByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCategoryFiltersByCategoryIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает экземпляры атрибутов по ключам
     * Обязательные параметры:
     *    attribute_keys - ключи атрибутов
     * Примеры:
     *    Вернуть экземпляры атрибутов:
     *        grpcurl -d '{"attribute_keys": ["brand", "image"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAttributesByKeys
     * </pre>
     */
    public void getAttributesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAttributesByKeysMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список всех атрибутов
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllAttributes
     * </pre>
     */
    public void getAllAttributes(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllAttributesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список всех словарей
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все словари:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllDictionaries
     * </pre>
     */
    public void getAllDictionaries(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllDictionariesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает экземпляры словарей по ключам
     * Обязательные параметры:
     *    dictionary_keys - ключи словарей
     * Примеры:
     *    Вернуть экземпляры словарей:
     *        grpcurl -d '{"dictionary_keys": ["brand"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetDictionariesByKeys
     * </pre>
     */
    public void getDictionariesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDictionariesByKeysMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает все словарные значения конкретного словаря
     * Обязательные параметры:
     *    dictionary_key - ключ словаря
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть значения словарей:
     *        grpcurl -d '{"dictionary_key": "brand", "limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllDictionaryValues
     * </pre>
     */
    public void getAllDictionaryValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllDictionaryValuesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategories
     * </pre>
     */
    public void getAllOriginalCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllOriginalCategoriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    original_ids - идентификаторы mysql.taxons
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"original_ids": [73278]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetOriginalCategoriesByIDs
     * </pre>
     */
    public void getOriginalCategoriesByIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOriginalCategoriesByIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    cursor_id - ид запрашиваемых данных, не является последовательным идентификатором, нужно передавать в каждый последующий запрос
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"limit": 10, "cursord_id": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategoriesByCursorID
     * </pre>
     */
    public void getAllOriginalCategoriesByCursorID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllOriginalCategoriesByCursorIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons) для магазина
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"store_id": "1", "limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategoriesByStoreID
     * </pre>
     */
    public void getAllOriginalCategoriesByStoreID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllOriginalCategoriesByStoreIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список идентификаторов магазинов для конкретных ритейлеров
     * Обязательные параметры:
     *    retailer_ids - идентификаторы ритейлеров
     * Примеры:
     *    Вернуть идентификаторы магазины:
     *        grpcurl -d '{"retailer_ids": ["1", "3"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetRetailerStores
     * </pre>
     */
    public void getRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRetailerStoresMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список всех идентификаторов магазинов с ретейлером по лимиту, оффсету
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть идентификаторы магазины:
     *        grpcurl -d '{"limit": 10, "offset": 5}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllRetailerStores
     * </pre>
     */
    public void getAllRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllRetailerStoresMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список словарных значений по ключу атрибута и идентифиатору словарного значения
     * Обязательные параметры:
     *    attribute_key - ключ атрибута
     *    dictionary_value_ids - идентификаторы словарных значений
     * Примеры:
     *    Вернуть словарные значения:
     *        grpcurl -d '{"attributes": [{"attribute_key": "brand", "dictionary_value_ids": ["48739", "30248"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetDictionaryAttributesValues
     * </pre>
     */
    public void getDictionaryAttributesValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDictionaryAttributesValuesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список популярностей по ключу магазина и скушкам
     * Обязательные параметры:
     *    store_id - ключ магазина
     * Примеры:
     * </pre>
     */
    public void getPopularities(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPopularitiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список популярностей по ключу магазина и скушкам
     * Обязательные параметры:
     *    store_id - ключ магазина
     * Примеры:
     * </pre>
     */
    public void getRetailerIDsByStoreIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRetailerIDsByStoreIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getDictionaryValuesByPermalinks(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDictionaryValuesByPermalinksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Возвращает список ретейлеров по retailer_id
     * Обязательные параметры:
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Возвращает список ретейлеров (retailer):
     *        grpcurl -d '{"retailer_ids": ["1", "330"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetRetailers
     * </pre>
     */
    public void getRetailers(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRetailersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub.paas-content-product-hub:3009
   * local:
   *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-product-hub:3009
   * description:
   *    Мета сервис product-hub возвращающий мета данные (Category, Attribute, Dictionary, DictionaryValue).
   *    Предназначет для real-time нагрузки.
   *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
   * </pre>
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
     * <pre>
     * Возвращает плоский список всех категорий батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategories
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse getAllCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllCategoriesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает плоский список всех категорий со списком идентификаторов магазинов батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategoriesWithStores
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse getAllCategoriesWithStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllCategoriesWithStoresMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает плоский список всех категорий со списком идентификаторов магазинов батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    cursor_id - ид запрашиваемых данных, не является последовательным идентификатором, нужно передавать в каждый последующий запрос
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "cursor_id": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategoriesWithStoresByCursorID
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse getAllCategoriesWithStoresByCursorID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllCategoriesWithStoresByCursorIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает экземпляры категорий по идентификаторам
     * Обязательные параметры:
     *    category_ids - идентификатор категории
     * Примеры:
     *    Вернуть категории по ид:
     *        grpcurl -d '{"category_ids": ["175", "5440"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetCategoriesByCategoryIDs
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse getCategoriesByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCategoriesByCategoryIDsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список ключей атрибутов которые необходимо вывести в фасетах фильтров
     * Обязательные параметры:
     *    category_ids - идентификатор категории
     * Примеры:
     *    Вернуть ключи атрибутов по ид категорий:
     *        grpcurl -d '{"category_ids": ["175", "5440"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetCategoryFiltersByCategoryIDs
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse getCategoryFiltersByCategoryIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCategoryFiltersByCategoryIDsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает экземпляры атрибутов по ключам
     * Обязательные параметры:
     *    attribute_keys - ключи атрибутов
     * Примеры:
     *    Вернуть экземпляры атрибутов:
     *        grpcurl -d '{"attribute_keys": ["brand", "image"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAttributesByKeys
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse getAttributesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAttributesByKeysMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список всех атрибутов
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllAttributes
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse getAllAttributes(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllAttributesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список всех словарей
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все словари:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllDictionaries
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse getAllDictionaries(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllDictionariesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает экземпляры словарей по ключам
     * Обязательные параметры:
     *    dictionary_keys - ключи словарей
     * Примеры:
     *    Вернуть экземпляры словарей:
     *        grpcurl -d '{"dictionary_keys": ["brand"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetDictionariesByKeys
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse getDictionariesByKeys(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDictionariesByKeysMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает все словарные значения конкретного словаря
     * Обязательные параметры:
     *    dictionary_key - ключ словаря
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть значения словарей:
     *        grpcurl -d '{"dictionary_key": "brand", "limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllDictionaryValues
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse getAllDictionaryValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllDictionaryValuesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategories
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse getAllOriginalCategories(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllOriginalCategoriesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    original_ids - идентификаторы mysql.taxons
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"original_ids": [73278]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetOriginalCategoriesByIDs
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse getOriginalCategoriesByIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOriginalCategoriesByIDsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    cursor_id - ид запрашиваемых данных, не является последовательным идентификатором, нужно передавать в каждый последующий запрос
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"limit": 10, "cursord_id": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategoriesByCursorID
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse getAllOriginalCategoriesByCursorID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllOriginalCategoriesByCursorIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons) для магазина
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"store_id": "1", "limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategoriesByStoreID
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse getAllOriginalCategoriesByStoreID(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllOriginalCategoriesByStoreIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список идентификаторов магазинов для конкретных ритейлеров
     * Обязательные параметры:
     *    retailer_ids - идентификаторы ритейлеров
     * Примеры:
     *    Вернуть идентификаторы магазины:
     *        grpcurl -d '{"retailer_ids": ["1", "3"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetRetailerStores
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse getRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRetailerStoresMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список всех идентификаторов магазинов с ретейлером по лимиту, оффсету
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть идентификаторы магазины:
     *        grpcurl -d '{"limit": 10, "offset": 5}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllRetailerStores
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse getAllRetailerStores(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllRetailerStoresMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список словарных значений по ключу атрибута и идентифиатору словарного значения
     * Обязательные параметры:
     *    attribute_key - ключ атрибута
     *    dictionary_value_ids - идентификаторы словарных значений
     * Примеры:
     *    Вернуть словарные значения:
     *        grpcurl -d '{"attributes": [{"attribute_key": "brand", "dictionary_value_ids": ["48739", "30248"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetDictionaryAttributesValues
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse getDictionaryAttributesValues(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDictionaryAttributesValuesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список популярностей по ключу магазина и скушкам
     * Обязательные параметры:
     *    store_id - ключ магазина
     * Примеры:
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse getPopularities(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPopularitiesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список популярностей по ключу магазина и скушкам
     * Обязательные параметры:
     *    store_id - ключ магазина
     * Примеры:
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse getRetailerIDsByStoreIDs(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRetailerIDsByStoreIDsMethod(), getCallOptions(), request);
    }

    /**
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse getDictionaryValuesByPermalinks(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDictionaryValuesByPermalinksMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Возвращает список ретейлеров по retailer_id
     * Обязательные параметры:
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Возвращает список ретейлеров (retailer):
     *        grpcurl -d '{"retailer_ids": ["1", "330"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetRetailers
     * </pre>
     */
    public product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse getRetailers(product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRetailersMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub.paas-content-product-hub:3009
   * local:
   *     base-product-hub.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-product-hub:3009
   * description:
   *    Мета сервис product-hub возвращающий мета данные (Category, Attribute, Dictionary, DictionaryValue).
   *    Предназначет для real-time нагрузки.
   *    Полный набор атрибутов и флагов для всех сущностей описан здесь https://wiki.sbmt.io/display/CP/Product+Hub
   * </pre>
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
     * <pre>
     * Возвращает плоский список всех категорий батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategories
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesResponse> getAllCategories(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllCategoriesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает плоский список всех категорий со списком идентификаторов магазинов батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategoriesWithStores
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresResponse> getAllCategoriesWithStores(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllCategoriesWithStoresMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает плоский список всех категорий со списком идентификаторов магазинов батчами (без спецпредложений)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    cursor_id - ид запрашиваемых данных, не является последовательным идентификатором, нужно передавать в каждый последующий запрос
     * Примеры:
     *    Вернуть все категории:
     *        grpcurl -d '{"limit": 10, "cursor_id": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllCategoriesWithStoresByCursorID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse> getAllCategoriesWithStoresByCursorID(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllCategoriesWithStoresByCursorIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает экземпляры категорий по идентификаторам
     * Обязательные параметры:
     *    category_ids - идентификатор категории
     * Примеры:
     *    Вернуть категории по ид:
     *        grpcurl -d '{"category_ids": ["175", "5440"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetCategoriesByCategoryIDs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsResponse> getCategoriesByCategoryIDs(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoriesByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCategoriesByCategoryIDsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список ключей атрибутов которые необходимо вывести в фасетах фильтров
     * Обязательные параметры:
     *    category_ids - идентификатор категории
     * Примеры:
     *    Вернуть ключи атрибутов по ид категорий:
     *        grpcurl -d '{"category_ids": ["175", "5440"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetCategoryFiltersByCategoryIDs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsResponse> getCategoryFiltersByCategoryIDs(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetCategoryFiltersByCategoryIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCategoryFiltersByCategoryIDsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает экземпляры атрибутов по ключам
     * Обязательные параметры:
     *    attribute_keys - ключи атрибутов
     * Примеры:
     *    Вернуть экземпляры атрибутов:
     *        grpcurl -d '{"attribute_keys": ["brand", "image"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAttributesByKeys
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysResponse> getAttributesByKeys(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAttributesByKeysRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAttributesByKeysMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список всех атрибутов
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllAttributes
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesResponse> getAllAttributes(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllAttributesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllAttributesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список всех словарей
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть все словари:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllDictionaries
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesResponse> getAllDictionaries(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionariesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllDictionariesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает экземпляры словарей по ключам
     * Обязательные параметры:
     *    dictionary_keys - ключи словарей
     * Примеры:
     *    Вернуть экземпляры словарей:
     *        grpcurl -d '{"dictionary_keys": ["brand"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetDictionariesByKeys
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse> getDictionariesByKeys(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDictionariesByKeysMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает все словарные значения конкретного словаря
     * Обязательные параметры:
     *    dictionary_key - ключ словаря
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть значения словарей:
     *        grpcurl -d '{"dictionary_key": "brand", "limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllDictionaryValues
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse> getAllDictionaryValues(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllDictionaryValuesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategories
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse> getAllOriginalCategories(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllOriginalCategoriesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    original_ids - идентификаторы mysql.taxons
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"original_ids": [73278]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetOriginalCategoriesByIDs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse> getOriginalCategoriesByIDs(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOriginalCategoriesByIDsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons)
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    cursor_id - ид запрашиваемых данных, не является последовательным идентификатором, нужно передавать в каждый последующий запрос
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"limit": 10, "cursord_id": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategoriesByCursorID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse> getAllOriginalCategoriesByCursorID(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllOriginalCategoriesByCursorIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает полный список монолитовских категорий (табличка mysql.taxons) для магазина
     * Обязательные параметры:
     *    store_id - идентификатор магазина
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть монолитовские категории:
     *        grpcurl -d '{"store_id": "1", "limit": 10, "offset": 0}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllOriginalCategoriesByStoreID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse> getAllOriginalCategoriesByStoreID(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllOriginalCategoriesByStoreIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список идентификаторов магазинов для конкретных ритейлеров
     * Обязательные параметры:
     *    retailer_ids - идентификаторы ритейлеров
     * Примеры:
     *    Вернуть идентификаторы магазины:
     *        grpcurl -d '{"retailer_ids": ["1", "3"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetRetailerStores
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse> getRetailerStores(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRetailerStoresMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список всех идентификаторов магазинов с ретейлером по лимиту, оффсету
     * Обязательные параметры:
     *    limit - кол-во запрашиваемых элементов
     *    offset - сдвиг, начинать с 0
     * Примеры:
     *    Вернуть идентификаторы магазины:
     *        grpcurl -d '{"limit": 10, "offset": 5}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetAllRetailerStores
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse> getAllRetailerStores(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllRetailerStoresMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список словарных значений по ключу атрибута и идентифиатору словарного значения
     * Обязательные параметры:
     *    attribute_key - ключ атрибута
     *    dictionary_value_ids - идентификаторы словарных значений
     * Примеры:
     *    Вернуть словарные значения:
     *        grpcurl -d '{"attributes": [{"attribute_key": "brand", "dictionary_value_ids": ["48739", "30248"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetDictionaryAttributesValues
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse> getDictionaryAttributesValues(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDictionaryAttributesValuesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список популярностей по ключу магазина и скушкам
     * Обязательные параметры:
     *    store_id - ключ магазина
     * Примеры:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse> getPopularities(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPopularitiesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список популярностей по ключу магазина и скушкам
     * Обязательные параметры:
     *    store_id - ключ магазина
     * Примеры:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse> getRetailerIDsByStoreIDs(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRetailerIDsByStoreIDsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse> getDictionaryValuesByPermalinks(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDictionaryValuesByPermalinksMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Возвращает список ретейлеров по retailer_id
     * Обязательные параметры:
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Возвращает список ретейлеров (retailer):
     *        grpcurl -d '{"retailer_ids": ["1", "330"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_meta.ProductHubFrontMeta.GetRetailers
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse> getRetailers(
        product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRetailersMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_CATEGORIES = 0;
  private static final int METHODID_GET_ALL_CATEGORIES_WITH_STORES = 1;
  private static final int METHODID_GET_ALL_CATEGORIES_WITH_STORES_BY_CURSOR_ID = 2;
  private static final int METHODID_GET_CATEGORIES_BY_CATEGORY_IDS = 3;
  private static final int METHODID_GET_CATEGORY_FILTERS_BY_CATEGORY_IDS = 4;
  private static final int METHODID_GET_ATTRIBUTES_BY_KEYS = 5;
  private static final int METHODID_GET_ALL_ATTRIBUTES = 6;
  private static final int METHODID_GET_ALL_DICTIONARIES = 7;
  private static final int METHODID_GET_DICTIONARIES_BY_KEYS = 8;
  private static final int METHODID_GET_ALL_DICTIONARY_VALUES = 9;
  private static final int METHODID_GET_ALL_ORIGINAL_CATEGORIES = 10;
  private static final int METHODID_GET_ORIGINAL_CATEGORIES_BY_IDS = 11;
  private static final int METHODID_GET_ALL_ORIGINAL_CATEGORIES_BY_CURSOR_ID = 12;
  private static final int METHODID_GET_ALL_ORIGINAL_CATEGORIES_BY_STORE_ID = 13;
  private static final int METHODID_GET_RETAILER_STORES = 14;
  private static final int METHODID_GET_ALL_RETAILER_STORES = 15;
  private static final int METHODID_GET_DICTIONARY_ATTRIBUTES_VALUES = 16;
  private static final int METHODID_GET_POPULARITIES = 17;
  private static final int METHODID_GET_RETAILER_IDS_BY_STORE_IDS = 18;
  private static final int METHODID_GET_DICTIONARY_VALUES_BY_PERMALINKS = 19;
  private static final int METHODID_GET_RETAILERS = 20;

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
        case METHODID_GET_ALL_CATEGORIES_WITH_STORES_BY_CURSOR_ID:
          serviceImpl.getAllCategoriesWithStoresByCursorID((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllCategoriesWithStoresByCursorIDResponse>) responseObserver);
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
        case METHODID_GET_DICTIONARIES_BY_KEYS:
          serviceImpl.getDictionariesByKeys((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionariesByKeysResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_DICTIONARY_VALUES:
          serviceImpl.getAllDictionaryValues((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllDictionaryValuesResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_ORIGINAL_CATEGORIES:
          serviceImpl.getAllOriginalCategories((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesResponse>) responseObserver);
          break;
        case METHODID_GET_ORIGINAL_CATEGORIES_BY_IDS:
          serviceImpl.getOriginalCategoriesByIDs((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetOriginalCategoriesByIDsResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_ORIGINAL_CATEGORIES_BY_CURSOR_ID:
          serviceImpl.getAllOriginalCategoriesByCursorID((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByCursorIDResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_ORIGINAL_CATEGORIES_BY_STORE_ID:
          serviceImpl.getAllOriginalCategoriesByStoreID((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllOriginalCategoriesByStoreIDResponse>) responseObserver);
          break;
        case METHODID_GET_RETAILER_STORES:
          serviceImpl.getRetailerStores((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerStoresResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_RETAILER_STORES:
          serviceImpl.getAllRetailerStores((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetAllRetailerStoresResponse>) responseObserver);
          break;
        case METHODID_GET_DICTIONARY_ATTRIBUTES_VALUES:
          serviceImpl.getDictionaryAttributesValues((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryAttributesValuesResponse>) responseObserver);
          break;
        case METHODID_GET_POPULARITIES:
          serviceImpl.getPopularities((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetPopularitiesResponse>) responseObserver);
          break;
        case METHODID_GET_RETAILER_IDS_BY_STORE_IDS:
          serviceImpl.getRetailerIDsByStoreIDs((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetRetailerIDsByStoreIDsResponse>) responseObserver);
          break;
        case METHODID_GET_DICTIONARY_VALUES_BY_PERMALINKS:
          serviceImpl.getDictionaryValuesByPermalinks((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetDictionaryValuesByPermalinksResponse>) responseObserver);
          break;
        case METHODID_GET_RETAILERS:
          serviceImpl.getRetailers((product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_meta.ProductHubFrontMetaOuterClass.GetGetRetailersResponse>) responseObserver);
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
              .addMethod(getGetAllCategoriesWithStoresByCursorIDMethod())
              .addMethod(getGetCategoriesByCategoryIDsMethod())
              .addMethod(getGetCategoryFiltersByCategoryIDsMethod())
              .addMethod(getGetAttributesByKeysMethod())
              .addMethod(getGetAllAttributesMethod())
              .addMethod(getGetAllDictionariesMethod())
              .addMethod(getGetDictionariesByKeysMethod())
              .addMethod(getGetAllDictionaryValuesMethod())
              .addMethod(getGetAllOriginalCategoriesMethod())
              .addMethod(getGetOriginalCategoriesByIDsMethod())
              .addMethod(getGetAllOriginalCategoriesByCursorIDMethod())
              .addMethod(getGetAllOriginalCategoriesByStoreIDMethod())
              .addMethod(getGetRetailerStoresMethod())
              .addMethod(getGetAllRetailerStoresMethod())
              .addMethod(getGetDictionaryAttributesValuesMethod())
              .addMethod(getGetPopularitiesMethod())
              .addMethod(getGetRetailerIDsByStoreIDsMethod())
              .addMethod(getGetDictionaryValuesByPermalinksMethod())
              .addMethod(getGetRetailersMethod())
              .build();
        }
      }
    }
    return result;
  }
}
