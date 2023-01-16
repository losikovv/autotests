package product_hub_back;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * slack:
 *    #product-hub
 * base:
 *     base-product-hub-back.paas-content-product-hub:3009
 * local:
 *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
 * swagger prod:
 *     https://paas-content-product-hub-back.sbmt.io/api
 * swagger stg:
 *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
 * public prod grpc uri:
 *    paas-content-product-hub-back.sbmt.io:443
 * public stg grpc uri:
 *    paas-content-product-hub-back.gw-stage-back.sbmt.io:443
 * description:
 *    Back сервис product-hub для импорта данных
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/product_hub/product-hub-back.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductHubBackGrpc {

  private ProductHubBackGrpc() {}

  public static final String SERVICE_NAME = "product_hub_back.ProductHubBack";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveProductsRequest,
      product_hub_back.ProductHubBackOuterClass.SaveProductsResponse> getSaveProductsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveProducts",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveProductsRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveProductsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveProductsRequest,
      product_hub_back.ProductHubBackOuterClass.SaveProductsResponse> getSaveProductsMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveProductsRequest, product_hub_back.ProductHubBackOuterClass.SaveProductsResponse> getSaveProductsMethod;
    if ((getSaveProductsMethod = ProductHubBackGrpc.getSaveProductsMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveProductsMethod = ProductHubBackGrpc.getSaveProductsMethod) == null) {
          ProductHubBackGrpc.getSaveProductsMethod = getSaveProductsMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveProductsRequest, product_hub_back.ProductHubBackOuterClass.SaveProductsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveProducts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveProductsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveProductsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveProducts"))
              .build();
        }
      }
    }
    return getSaveProductsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveStocksRequest,
      product_hub_back.ProductHubBackOuterClass.SaveStocksResponse> getSaveStocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveStocks",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveStocksRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveStocksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveStocksRequest,
      product_hub_back.ProductHubBackOuterClass.SaveStocksResponse> getSaveStocksMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveStocksRequest, product_hub_back.ProductHubBackOuterClass.SaveStocksResponse> getSaveStocksMethod;
    if ((getSaveStocksMethod = ProductHubBackGrpc.getSaveStocksMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveStocksMethod = ProductHubBackGrpc.getSaveStocksMethod) == null) {
          ProductHubBackGrpc.getSaveStocksMethod = getSaveStocksMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveStocksRequest, product_hub_back.ProductHubBackOuterClass.SaveStocksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveStocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveStocksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveStocksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveStocks"))
              .build();
        }
      }
    }
    return getSaveStocksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveOffersRequest,
      product_hub_back.ProductHubBackOuterClass.SaveOffersResponse> getSaveOffersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveOffers",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveOffersRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveOffersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveOffersRequest,
      product_hub_back.ProductHubBackOuterClass.SaveOffersResponse> getSaveOffersMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveOffersRequest, product_hub_back.ProductHubBackOuterClass.SaveOffersResponse> getSaveOffersMethod;
    if ((getSaveOffersMethod = ProductHubBackGrpc.getSaveOffersMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveOffersMethod = ProductHubBackGrpc.getSaveOffersMethod) == null) {
          ProductHubBackGrpc.getSaveOffersMethod = getSaveOffersMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveOffersRequest, product_hub_back.ProductHubBackOuterClass.SaveOffersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveOffers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveOffersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveOffersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveOffers"))
              .build();
        }
      }
    }
    return getSaveOffersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SavePricesRequest,
      product_hub_back.ProductHubBackOuterClass.SavePricesResponse> getSavePricesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SavePrices",
      requestType = product_hub_back.ProductHubBackOuterClass.SavePricesRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SavePricesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SavePricesRequest,
      product_hub_back.ProductHubBackOuterClass.SavePricesResponse> getSavePricesMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SavePricesRequest, product_hub_back.ProductHubBackOuterClass.SavePricesResponse> getSavePricesMethod;
    if ((getSavePricesMethod = ProductHubBackGrpc.getSavePricesMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSavePricesMethod = ProductHubBackGrpc.getSavePricesMethod) == null) {
          ProductHubBackGrpc.getSavePricesMethod = getSavePricesMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SavePricesRequest, product_hub_back.ProductHubBackOuterClass.SavePricesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SavePrices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SavePricesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SavePricesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SavePrices"))
              .build();
        }
      }
    }
    return getSavePricesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest,
      product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse> getSaveCategoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveCategories",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest,
      product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse> getSaveCategoriesMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest, product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse> getSaveCategoriesMethod;
    if ((getSaveCategoriesMethod = ProductHubBackGrpc.getSaveCategoriesMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveCategoriesMethod = ProductHubBackGrpc.getSaveCategoriesMethod) == null) {
          ProductHubBackGrpc.getSaveCategoriesMethod = getSaveCategoriesMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest, product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveCategories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveCategories"))
              .build();
        }
      }
    }
    return getSaveCategoriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest,
      product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse> getSaveAttributesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveAttributes",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest,
      product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse> getSaveAttributesMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest, product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse> getSaveAttributesMethod;
    if ((getSaveAttributesMethod = ProductHubBackGrpc.getSaveAttributesMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveAttributesMethod = ProductHubBackGrpc.getSaveAttributesMethod) == null) {
          ProductHubBackGrpc.getSaveAttributesMethod = getSaveAttributesMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest, product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveAttributes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveAttributes"))
              .build();
        }
      }
    }
    return getSaveAttributesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest,
      product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse> getSaveDictionariesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveDictionaries",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest,
      product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse> getSaveDictionariesMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest, product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse> getSaveDictionariesMethod;
    if ((getSaveDictionariesMethod = ProductHubBackGrpc.getSaveDictionariesMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveDictionariesMethod = ProductHubBackGrpc.getSaveDictionariesMethod) == null) {
          ProductHubBackGrpc.getSaveDictionariesMethod = getSaveDictionariesMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest, product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveDictionaries"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveDictionaries"))
              .build();
        }
      }
    }
    return getSaveDictionariesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest,
      product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse> getSaveCategoryFiltersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveCategoryFilters",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest,
      product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse> getSaveCategoryFiltersMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest, product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse> getSaveCategoryFiltersMethod;
    if ((getSaveCategoryFiltersMethod = ProductHubBackGrpc.getSaveCategoryFiltersMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveCategoryFiltersMethod = ProductHubBackGrpc.getSaveCategoryFiltersMethod) == null) {
          ProductHubBackGrpc.getSaveCategoryFiltersMethod = getSaveCategoryFiltersMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest, product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveCategoryFilters"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveCategoryFilters"))
              .build();
        }
      }
    }
    return getSaveCategoryFiltersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest,
      product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse> getSaveRetailerStoresMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveRetailerStores",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest,
      product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse> getSaveRetailerStoresMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest, product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse> getSaveRetailerStoresMethod;
    if ((getSaveRetailerStoresMethod = ProductHubBackGrpc.getSaveRetailerStoresMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveRetailerStoresMethod = ProductHubBackGrpc.getSaveRetailerStoresMethod) == null) {
          ProductHubBackGrpc.getSaveRetailerStoresMethod = getSaveRetailerStoresMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest, product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveRetailerStores"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveRetailerStores"))
              .build();
        }
      }
    }
    return getSaveRetailerStoresMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest,
      product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse> getSavePopularitiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SavePopularities",
      requestType = product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest,
      product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse> getSavePopularitiesMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest, product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse> getSavePopularitiesMethod;
    if ((getSavePopularitiesMethod = ProductHubBackGrpc.getSavePopularitiesMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSavePopularitiesMethod = ProductHubBackGrpc.getSavePopularitiesMethod) == null) {
          ProductHubBackGrpc.getSavePopularitiesMethod = getSavePopularitiesMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest, product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SavePopularities"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SavePopularities"))
              .build();
        }
      }
    }
    return getSavePopularitiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.DisableOffersRequest,
      product_hub_back.ProductHubBackOuterClass.DisableOffersResponse> getDisableOffersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DisableOffers",
      requestType = product_hub_back.ProductHubBackOuterClass.DisableOffersRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.DisableOffersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.DisableOffersRequest,
      product_hub_back.ProductHubBackOuterClass.DisableOffersResponse> getDisableOffersMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.DisableOffersRequest, product_hub_back.ProductHubBackOuterClass.DisableOffersResponse> getDisableOffersMethod;
    if ((getDisableOffersMethod = ProductHubBackGrpc.getDisableOffersMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getDisableOffersMethod = ProductHubBackGrpc.getDisableOffersMethod) == null) {
          ProductHubBackGrpc.getDisableOffersMethod = getDisableOffersMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.DisableOffersRequest, product_hub_back.ProductHubBackOuterClass.DisableOffersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DisableOffers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.DisableOffersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.DisableOffersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("DisableOffers"))
              .build();
        }
      }
    }
    return getDisableOffersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest,
      product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse> getSaveStoreIDtoRetailerIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveStoreIDtoRetailerID",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest,
      product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse> getSaveStoreIDtoRetailerIDMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest, product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse> getSaveStoreIDtoRetailerIDMethod;
    if ((getSaveStoreIDtoRetailerIDMethod = ProductHubBackGrpc.getSaveStoreIDtoRetailerIDMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveStoreIDtoRetailerIDMethod = ProductHubBackGrpc.getSaveStoreIDtoRetailerIDMethod) == null) {
          ProductHubBackGrpc.getSaveStoreIDtoRetailerIDMethod = getSaveStoreIDtoRetailerIDMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest, product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveStoreIDtoRetailerID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveStoreIDtoRetailerID"))
              .build();
        }
      }
    }
    return getSaveStoreIDtoRetailerIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest,
      product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse> getSaveGroupsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveGroups",
      requestType = product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest.class,
      responseType = product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest,
      product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse> getSaveGroupsMethod() {
    io.grpc.MethodDescriptor<product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest, product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse> getSaveGroupsMethod;
    if ((getSaveGroupsMethod = ProductHubBackGrpc.getSaveGroupsMethod) == null) {
      synchronized (ProductHubBackGrpc.class) {
        if ((getSaveGroupsMethod = ProductHubBackGrpc.getSaveGroupsMethod) == null) {
          ProductHubBackGrpc.getSaveGroupsMethod = getSaveGroupsMethod =
              io.grpc.MethodDescriptor.<product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest, product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveGroups"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubBackMethodDescriptorSupplier("SaveGroups"))
              .build();
        }
      }
    }
    return getSaveGroupsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductHubBackStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubBackStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubBackStub>() {
        @java.lang.Override
        public ProductHubBackStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubBackStub(channel, callOptions);
        }
      };
    return ProductHubBackStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductHubBackBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubBackBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubBackBlockingStub>() {
        @java.lang.Override
        public ProductHubBackBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubBackBlockingStub(channel, callOptions);
        }
      };
    return ProductHubBackBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductHubBackFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubBackFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubBackFutureStub>() {
        @java.lang.Override
        public ProductHubBackFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubBackFutureStub(channel, callOptions);
        }
      };
    return ProductHubBackFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub-back.paas-content-product-hub:3009
   * local:
   *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub-back.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub-back.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub-back.gw-stage-back.sbmt.io:443
   * description:
   *    Back сервис product-hub для импорта данных
   * </pre>
   */
  public static abstract class ProductHubBackImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Сохраняет описание продуктов
     * </pre>
     */
    public void saveProducts(product_hub_back.ProductHubBackOuterClass.SaveProductsRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveProductsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveProductsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Сохраняет стоки
     * </pre>
     */
    public void saveStocks(product_hub_back.ProductHubBackOuterClass.SaveStocksRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveStocksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveStocksMethod(), responseObserver);
    }

    /**
     * <pre>
     * Сохраняет товарные предложения
     * </pre>
     */
    public void saveOffers(product_hub_back.ProductHubBackOuterClass.SaveOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveOffersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveOffersMethod(), responseObserver);
    }

    /**
     * <pre>
     * Сохраняет цены
     * </pre>
     */
    public void savePrices(product_hub_back.ProductHubBackOuterClass.SavePricesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SavePricesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSavePricesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Сохраняет категории
     * </pre>
     */
    public void saveCategories(product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveCategoriesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Сохраняет атрибуты
     * </pre>
     */
    public void saveAttributes(product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveAttributesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Сохраняет словари и значения словарей
     * </pre>
     */
    public void saveDictionaries(product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveDictionariesMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Сохраняет категорийные фильтры
     * </pre>
     */
    public void saveCategoryFilters(product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveCategoryFiltersMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Сохраняет связки магазинов на ритейлеров
     * </pre>
     */
    public void saveRetailerStores(product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveRetailerStoresMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Сохраняет популярность
     * </pre>
     */
    public void savePopularities(product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSavePopularitiesMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Выключение офферов
     * </pre>
     */
    public void disableOffers(product_hub_back.ProductHubBackOuterClass.DisableOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.DisableOffersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDisableOffersMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Связка store_id-&gt;retailer_id
     * </pre>
     */
    public void saveStoreIDtoRetailerID(product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveStoreIDtoRetailerIDMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Сохранения групп скушек
     * </pre>
     */
    public void saveGroups(product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveGroupsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSaveProductsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveProductsRequest,
                product_hub_back.ProductHubBackOuterClass.SaveProductsResponse>(
                  this, METHODID_SAVE_PRODUCTS)))
          .addMethod(
            getSaveStocksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveStocksRequest,
                product_hub_back.ProductHubBackOuterClass.SaveStocksResponse>(
                  this, METHODID_SAVE_STOCKS)))
          .addMethod(
            getSaveOffersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveOffersRequest,
                product_hub_back.ProductHubBackOuterClass.SaveOffersResponse>(
                  this, METHODID_SAVE_OFFERS)))
          .addMethod(
            getSavePricesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SavePricesRequest,
                product_hub_back.ProductHubBackOuterClass.SavePricesResponse>(
                  this, METHODID_SAVE_PRICES)))
          .addMethod(
            getSaveCategoriesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest,
                product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse>(
                  this, METHODID_SAVE_CATEGORIES)))
          .addMethod(
            getSaveAttributesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest,
                product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse>(
                  this, METHODID_SAVE_ATTRIBUTES)))
          .addMethod(
            getSaveDictionariesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest,
                product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse>(
                  this, METHODID_SAVE_DICTIONARIES)))
          .addMethod(
            getSaveCategoryFiltersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest,
                product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse>(
                  this, METHODID_SAVE_CATEGORY_FILTERS)))
          .addMethod(
            getSaveRetailerStoresMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest,
                product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse>(
                  this, METHODID_SAVE_RETAILER_STORES)))
          .addMethod(
            getSavePopularitiesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest,
                product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse>(
                  this, METHODID_SAVE_POPULARITIES)))
          .addMethod(
            getDisableOffersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.DisableOffersRequest,
                product_hub_back.ProductHubBackOuterClass.DisableOffersResponse>(
                  this, METHODID_DISABLE_OFFERS)))
          .addMethod(
            getSaveStoreIDtoRetailerIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest,
                product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse>(
                  this, METHODID_SAVE_STORE_IDTO_RETAILER_ID)))
          .addMethod(
            getSaveGroupsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest,
                product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse>(
                  this, METHODID_SAVE_GROUPS)))
          .build();
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub-back.paas-content-product-hub:3009
   * local:
   *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub-back.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub-back.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub-back.gw-stage-back.sbmt.io:443
   * description:
   *    Back сервис product-hub для импорта данных
   * </pre>
   */
  public static final class ProductHubBackStub extends io.grpc.stub.AbstractAsyncStub<ProductHubBackStub> {
    private ProductHubBackStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubBackStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubBackStub(channel, callOptions);
    }

    /**
     * <pre>
     * Сохраняет описание продуктов
     * </pre>
     */
    public void saveProducts(product_hub_back.ProductHubBackOuterClass.SaveProductsRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveProductsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveProductsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Сохраняет стоки
     * </pre>
     */
    public void saveStocks(product_hub_back.ProductHubBackOuterClass.SaveStocksRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveStocksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveStocksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Сохраняет товарные предложения
     * </pre>
     */
    public void saveOffers(product_hub_back.ProductHubBackOuterClass.SaveOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveOffersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveOffersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Сохраняет цены
     * </pre>
     */
    public void savePrices(product_hub_back.ProductHubBackOuterClass.SavePricesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SavePricesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSavePricesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Сохраняет категории
     * </pre>
     */
    public void saveCategories(product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveCategoriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Сохраняет атрибуты
     * </pre>
     */
    public void saveAttributes(product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveAttributesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Сохраняет словари и значения словарей
     * </pre>
     */
    public void saveDictionaries(product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveDictionariesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Сохраняет категорийные фильтры
     * </pre>
     */
    public void saveCategoryFilters(product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveCategoryFiltersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Сохраняет связки магазинов на ритейлеров
     * </pre>
     */
    public void saveRetailerStores(product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveRetailerStoresMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Сохраняет популярность
     * </pre>
     */
    public void savePopularities(product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSavePopularitiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Выключение офферов
     * </pre>
     */
    public void disableOffers(product_hub_back.ProductHubBackOuterClass.DisableOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.DisableOffersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDisableOffersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Связка store_id-&gt;retailer_id
     * </pre>
     */
    public void saveStoreIDtoRetailerID(product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveStoreIDtoRetailerIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Сохранения групп скушек
     * </pre>
     */
    public void saveGroups(product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest request,
        io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveGroupsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub-back.paas-content-product-hub:3009
   * local:
   *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub-back.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub-back.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub-back.gw-stage-back.sbmt.io:443
   * description:
   *    Back сервис product-hub для импорта данных
   * </pre>
   */
  public static final class ProductHubBackBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductHubBackBlockingStub> {
    private ProductHubBackBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubBackBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubBackBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Сохраняет описание продуктов
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveProductsResponse saveProducts(product_hub_back.ProductHubBackOuterClass.SaveProductsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveProductsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Сохраняет стоки
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveStocksResponse saveStocks(product_hub_back.ProductHubBackOuterClass.SaveStocksRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveStocksMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Сохраняет товарные предложения
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveOffersResponse saveOffers(product_hub_back.ProductHubBackOuterClass.SaveOffersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveOffersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Сохраняет цены
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SavePricesResponse savePrices(product_hub_back.ProductHubBackOuterClass.SavePricesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSavePricesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Сохраняет категории
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse saveCategories(product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveCategoriesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Сохраняет атрибуты
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse saveAttributes(product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveAttributesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Сохраняет словари и значения словарей
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse saveDictionaries(product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveDictionariesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Сохраняет категорийные фильтры
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse saveCategoryFilters(product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveCategoryFiltersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Сохраняет связки магазинов на ритейлеров
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse saveRetailerStores(product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveRetailerStoresMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Сохраняет популярность
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse savePopularities(product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSavePopularitiesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Выключение офферов
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.DisableOffersResponse disableOffers(product_hub_back.ProductHubBackOuterClass.DisableOffersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDisableOffersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Связка store_id-&gt;retailer_id
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse saveStoreIDtoRetailerID(product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveStoreIDtoRetailerIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Сохранения групп скушек
     * </pre>
     */
    public product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse saveGroups(product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveGroupsMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * slack:
   *    #product-hub
   * base:
   *     base-product-hub-back.paas-content-product-hub:3009
   * local:
   *     base-product-hub-back.paas-content-product-hub.svc.cluster.local:3009
   * swagger prod:
   *     https://paas-content-product-hub-back.sbmt.io/api
   * swagger stg:
   *     https://paas-content-product-hub-back.gw-stage.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub-back.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub-back.gw-stage-back.sbmt.io:443
   * description:
   *    Back сервис product-hub для импорта данных
   * </pre>
   */
  public static final class ProductHubBackFutureStub extends io.grpc.stub.AbstractFutureStub<ProductHubBackFutureStub> {
    private ProductHubBackFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubBackFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubBackFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Сохраняет описание продуктов
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveProductsResponse> saveProducts(
        product_hub_back.ProductHubBackOuterClass.SaveProductsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveProductsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Сохраняет стоки
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveStocksResponse> saveStocks(
        product_hub_back.ProductHubBackOuterClass.SaveStocksRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveStocksMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Сохраняет товарные предложения
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveOffersResponse> saveOffers(
        product_hub_back.ProductHubBackOuterClass.SaveOffersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveOffersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Сохраняет цены
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SavePricesResponse> savePrices(
        product_hub_back.ProductHubBackOuterClass.SavePricesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSavePricesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Сохраняет категории
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse> saveCategories(
        product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveCategoriesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Сохраняет атрибуты
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse> saveAttributes(
        product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveAttributesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Сохраняет словари и значения словарей
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse> saveDictionaries(
        product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveDictionariesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Сохраняет категорийные фильтры
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse> saveCategoryFilters(
        product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveCategoryFiltersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Сохраняет связки магазинов на ритейлеров
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse> saveRetailerStores(
        product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveRetailerStoresMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Сохраняет популярность
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse> savePopularities(
        product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSavePopularitiesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Выключение офферов
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.DisableOffersResponse> disableOffers(
        product_hub_back.ProductHubBackOuterClass.DisableOffersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDisableOffersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Связка store_id-&gt;retailer_id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse> saveStoreIDtoRetailerID(
        product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveStoreIDtoRetailerIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Сохранения групп скушек
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse> saveGroups(
        product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveGroupsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_PRODUCTS = 0;
  private static final int METHODID_SAVE_STOCKS = 1;
  private static final int METHODID_SAVE_OFFERS = 2;
  private static final int METHODID_SAVE_PRICES = 3;
  private static final int METHODID_SAVE_CATEGORIES = 4;
  private static final int METHODID_SAVE_ATTRIBUTES = 5;
  private static final int METHODID_SAVE_DICTIONARIES = 6;
  private static final int METHODID_SAVE_CATEGORY_FILTERS = 7;
  private static final int METHODID_SAVE_RETAILER_STORES = 8;
  private static final int METHODID_SAVE_POPULARITIES = 9;
  private static final int METHODID_DISABLE_OFFERS = 10;
  private static final int METHODID_SAVE_STORE_IDTO_RETAILER_ID = 11;
  private static final int METHODID_SAVE_GROUPS = 12;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductHubBackImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductHubBackImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAVE_PRODUCTS:
          serviceImpl.saveProducts((product_hub_back.ProductHubBackOuterClass.SaveProductsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveProductsResponse>) responseObserver);
          break;
        case METHODID_SAVE_STOCKS:
          serviceImpl.saveStocks((product_hub_back.ProductHubBackOuterClass.SaveStocksRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveStocksResponse>) responseObserver);
          break;
        case METHODID_SAVE_OFFERS:
          serviceImpl.saveOffers((product_hub_back.ProductHubBackOuterClass.SaveOffersRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveOffersResponse>) responseObserver);
          break;
        case METHODID_SAVE_PRICES:
          serviceImpl.savePrices((product_hub_back.ProductHubBackOuterClass.SavePricesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SavePricesResponse>) responseObserver);
          break;
        case METHODID_SAVE_CATEGORIES:
          serviceImpl.saveCategories((product_hub_back.ProductHubBackOuterClass.SaveCategoriesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveCategoriesResponse>) responseObserver);
          break;
        case METHODID_SAVE_ATTRIBUTES:
          serviceImpl.saveAttributes((product_hub_back.ProductHubBackOuterClass.SaveAttributesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveAttributesResponse>) responseObserver);
          break;
        case METHODID_SAVE_DICTIONARIES:
          serviceImpl.saveDictionaries((product_hub_back.ProductHubBackOuterClass.SaveDictionariesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveDictionariesResponse>) responseObserver);
          break;
        case METHODID_SAVE_CATEGORY_FILTERS:
          serviceImpl.saveCategoryFilters((product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveCategoryFiltersResponse>) responseObserver);
          break;
        case METHODID_SAVE_RETAILER_STORES:
          serviceImpl.saveRetailerStores((product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveRetailerStoresResponse>) responseObserver);
          break;
        case METHODID_SAVE_POPULARITIES:
          serviceImpl.savePopularities((product_hub_back.ProductHubBackOuterClass.SavePopularitiesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SavePopularitiesResponse>) responseObserver);
          break;
        case METHODID_DISABLE_OFFERS:
          serviceImpl.disableOffers((product_hub_back.ProductHubBackOuterClass.DisableOffersRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.DisableOffersResponse>) responseObserver);
          break;
        case METHODID_SAVE_STORE_IDTO_RETAILER_ID:
          serviceImpl.saveStoreIDtoRetailerID((product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveStoreIDtoRetailerIDResponse>) responseObserver);
          break;
        case METHODID_SAVE_GROUPS:
          serviceImpl.saveGroups((product_hub_back.ProductHubBackOuterClass.SaveGroupsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_back.ProductHubBackOuterClass.SaveGroupsResponse>) responseObserver);
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

  private static abstract class ProductHubBackBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductHubBackBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return product_hub_back.ProductHubBackOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductHubBack");
    }
  }

  private static final class ProductHubBackFileDescriptorSupplier
      extends ProductHubBackBaseDescriptorSupplier {
    ProductHubBackFileDescriptorSupplier() {}
  }

  private static final class ProductHubBackMethodDescriptorSupplier
      extends ProductHubBackBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductHubBackMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProductHubBackGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductHubBackFileDescriptorSupplier())
              .addMethod(getSaveProductsMethod())
              .addMethod(getSaveStocksMethod())
              .addMethod(getSaveOffersMethod())
              .addMethod(getSavePricesMethod())
              .addMethod(getSaveCategoriesMethod())
              .addMethod(getSaveAttributesMethod())
              .addMethod(getSaveDictionariesMethod())
              .addMethod(getSaveCategoryFiltersMethod())
              .addMethod(getSaveRetailerStoresMethod())
              .addMethod(getSavePopularitiesMethod())
              .addMethod(getDisableOffersMethod())
              .addMethod(getSaveStoreIDtoRetailerIDMethod())
              .addMethod(getSaveGroupsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
