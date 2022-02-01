package product_hub_front_data;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 **
 * slack:
 *    #product-hub
 * swagger:
 *     https://paas-content-product-hub.sbmt.io/api
 * public prod grpc uri:
 *    paas-content-product-hub.sbmt.io:443
 * public stg grpc uri:
 *    paas-content-product-hub.gw-stage.sbmt.io:443
 * kuber prod grpc uri:
 *    base-product-hub.paas-content-product-hub:3009
 * description:
 *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
 *    товарное вредложение (offer), цену (price) и остатки (stock).
 *    Предназначет для real-time нагрузки.
 *    Основные параметры запроса:
 *       sku - сбермартовский идентификатор описания товара
 *       store_id - сбермартовский идентификатор магазина
 *       tenant_id - сбермартовский идентификатор площадки
 *       retailer_id - сбермартовский идентификатор ритейлера
 *       retailer_sku - ритейлерский идентификатор товарного предложения
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.0)",
    comments = "Source: content/product_hub/product-hub-front-data.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductHubFrontDataGrpc {

  private ProductHubFrontDataGrpc() {}

  public static final String SERVICE_NAME = "product_hub_front_data.ProductHubFrontData";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> getGetProductsBySKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsBySKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> getGetProductsBySKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> getGetProductsBySKUMethod;
    if ((getGetProductsBySKUMethod = ProductHubFrontDataGrpc.getGetProductsBySKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsBySKUMethod = ProductHubFrontDataGrpc.getGetProductsBySKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsBySKUMethod = getGetProductsBySKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsBySKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsBySKU"))
              .build();
        }
      }
    }
    return getGetProductsBySKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> getGetProductsWithOfferBySKUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsWithOfferBySKU",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> getGetProductsWithOfferBySKUMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> getGetProductsWithOfferBySKUMethod;
    if ((getGetProductsWithOfferBySKUMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferBySKUMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsWithOfferBySKUMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferBySKUMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsWithOfferBySKUMethod = getGetProductsWithOfferBySKUMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsWithOfferBySKU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsWithOfferBySKU"))
              .build();
        }
      }
    }
    return getGetProductsWithOfferBySKUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> getGetProductsWithOfferByOfferIDsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsWithOfferByOfferIDs",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> getGetProductsWithOfferByOfferIDsMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> getGetProductsWithOfferByOfferIDsMethod;
    if ((getGetProductsWithOfferByOfferIDsMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByOfferIDsMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsWithOfferByOfferIDsMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByOfferIDsMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsWithOfferByOfferIDsMethod = getGetProductsWithOfferByOfferIDsMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsWithOfferByOfferIDs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsWithOfferByOfferIDs"))
              .build();
        }
      }
    }
    return getGetProductsWithOfferByOfferIDsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> getGetProductsByEANMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsByEAN",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> getGetProductsByEANMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> getGetProductsByEANMethod;
    if ((getGetProductsByEANMethod = ProductHubFrontDataGrpc.getGetProductsByEANMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsByEANMethod = ProductHubFrontDataGrpc.getGetProductsByEANMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsByEANMethod = getGetProductsByEANMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsByEAN"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsByEAN"))
              .build();
        }
      }
    }
    return getGetProductsByEANMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> getGetProductsWithOfferByEANMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsWithOfferByEAN",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> getGetProductsWithOfferByEANMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> getGetProductsWithOfferByEANMethod;
    if ((getGetProductsWithOfferByEANMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByEANMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsWithOfferByEANMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByEANMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsWithOfferByEANMethod = getGetProductsWithOfferByEANMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsWithOfferByEAN"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsWithOfferByEAN"))
              .build();
        }
      }
    }
    return getGetProductsWithOfferByEANMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> getGetProductsByPermalinkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsByPermalink",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> getGetProductsByPermalinkMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> getGetProductsByPermalinkMethod;
    if ((getGetProductsByPermalinkMethod = ProductHubFrontDataGrpc.getGetProductsByPermalinkMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsByPermalinkMethod = ProductHubFrontDataGrpc.getGetProductsByPermalinkMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsByPermalinkMethod = getGetProductsByPermalinkMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsByPermalink"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsByPermalink"))
              .build();
        }
      }
    }
    return getGetProductsByPermalinkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> getGetProductsWithOfferByPermalinkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProductsWithOfferByPermalink",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> getGetProductsWithOfferByPermalinkMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> getGetProductsWithOfferByPermalinkMethod;
    if ((getGetProductsWithOfferByPermalinkMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByPermalinkMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetProductsWithOfferByPermalinkMethod = ProductHubFrontDataGrpc.getGetProductsWithOfferByPermalinkMethod) == null) {
          ProductHubFrontDataGrpc.getGetProductsWithOfferByPermalinkMethod = getGetProductsWithOfferByPermalinkMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProductsWithOfferByPermalink"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetProductsWithOfferByPermalink"))
              .build();
        }
      }
    }
    return getGetProductsWithOfferByPermalinkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> getGetOffersBySKUANDStoreIDMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOffersBySKUANDStoreID",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> getGetOffersBySKUANDStoreIDMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> getGetOffersBySKUANDStoreIDMethod;
    if ((getGetOffersBySKUANDStoreIDMethod = ProductHubFrontDataGrpc.getGetOffersBySKUANDStoreIDMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetOffersBySKUANDStoreIDMethod = ProductHubFrontDataGrpc.getGetOffersBySKUANDStoreIDMethod) == null) {
          ProductHubFrontDataGrpc.getGetOffersBySKUANDStoreIDMethod = getGetOffersBySKUANDStoreIDMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOffersBySKUANDStoreID"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetOffersBySKUANDStoreID"))
              .build();
        }
      }
    }
    return getGetOffersBySKUANDStoreIDMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStocks",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getGetStocksMethod;
    if ((getGetStocksMethod = ProductHubFrontDataGrpc.getGetStocksMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetStocksMethod = ProductHubFrontDataGrpc.getGetStocksMethod) == null) {
          ProductHubFrontDataGrpc.getGetStocksMethod = getGetStocksMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetStocks"))
              .build();
        }
      }
    }
    return getGetStocksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> getGetOffersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOffers",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> getGetOffersMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> getGetOffersMethod;
    if ((getGetOffersMethod = ProductHubFrontDataGrpc.getGetOffersMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetOffersMethod = ProductHubFrontDataGrpc.getGetOffersMethod) == null) {
          ProductHubFrontDataGrpc.getGetOffersMethod = getGetOffersMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOffers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetOffers"))
              .build();
        }
      }
    }
    return getGetOffersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> getGetPricesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPrices",
      requestType = product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest.class,
      responseType = product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest,
      product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> getGetPricesMethod() {
    io.grpc.MethodDescriptor<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> getGetPricesMethod;
    if ((getGetPricesMethod = ProductHubFrontDataGrpc.getGetPricesMethod) == null) {
      synchronized (ProductHubFrontDataGrpc.class) {
        if ((getGetPricesMethod = ProductHubFrontDataGrpc.getGetPricesMethod) == null) {
          ProductHubFrontDataGrpc.getGetPricesMethod = getGetPricesMethod =
              io.grpc.MethodDescriptor.<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest, product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPrices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductHubFrontDataMethodDescriptorSupplier("GetPrices"))
              .build();
        }
      }
    }
    return getGetPricesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductHubFrontDataStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataStub>() {
        @java.lang.Override
        public ProductHubFrontDataStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontDataStub(channel, callOptions);
        }
      };
    return ProductHubFrontDataStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductHubFrontDataBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataBlockingStub>() {
        @java.lang.Override
        public ProductHubFrontDataBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontDataBlockingStub(channel, callOptions);
        }
      };
    return ProductHubFrontDataBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductHubFrontDataFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductHubFrontDataFutureStub>() {
        @java.lang.Override
        public ProductHubFrontDataFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductHubFrontDataFutureStub(channel, callOptions);
        }
      };
    return ProductHubFrontDataFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-product-hub.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-product-hub:3009
   * description:
   *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
   *    товарное вредложение (offer), цену (price) и остатки (stock).
   *    Предназначет для real-time нагрузки.
   *    Основные параметры запроса:
   *       sku - сбермартовский идентификатор описания товара
   *       store_id - сбермартовский идентификатор магазина
   *       tenant_id - сбермартовский идентификатор площадки
   *       retailer_id - сбермартовский идентификатор ритейлера
   *       retailer_sku - ритейлерский идентификатор товарного предложения
   * </pre>
   */
  public static abstract class ProductHubFrontDataImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     **
     * Возвращает описание товаров по sku в виде атрибутов.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"sku": ["16296"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенные атрибуты:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"keys":["brand", "image"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенную группу атрибутов размеченную флагом:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"flags":["show_as_characteristic"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     * </pre>
     */
    public void getProductsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsBySKUMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по sku.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferBySKU
     * </pre>
     */
    public void getProductsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsWithOfferBySKUMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по offer_id (идентификатор базы mysql таблички offers).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    offer_ids - идентификатор офера
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"offer_ids": ["231461"], "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByOfferIDs
     * </pre>
     */
    public void getProductsWithOfferByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsWithOfferByOfferIDsMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по EAN.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    EAN - штрихкод
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"ean": ["4000417048103"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByEAN
     * </pre>
     */
    public void getProductsByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsByEANMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по EAN.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    EAN - штрихкод
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"ean": ["4000417048103"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByEAN
     * </pre>
     */
    public void getProductsWithOfferByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsWithOfferByEANMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по permalink товаров (атрибут описания permalink).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    permalink - пермалинк товара
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByPermalink
     * </pre>
     */
    public void getProductsByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsByPermalinkMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по permalink товаров (атрибут описания permalink).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    permalink - пермалинк товара
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByPermalink
     * </pre>
     */
    public void getProductsWithOfferByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductsWithOfferByPermalinkMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения по sku описания + store_id идентификатору магазина
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersBySKUANDStoreID
     * </pre>
     */
    public void getOffersBySKUANDStoreID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOffersBySKUANDStoreIDMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает остатки по по sku описания + store_id идентификатору магазина
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocks
     * </pre>
     */
    public void getStocks(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStocksMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения по retailer_sku идентификатор товарного предложения ритейлера + retailer_id идентификатору ритейлера
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    retailer_sku - идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffers
     * </pre>
     */
    public void getOffers(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOffersMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает цены по sku + store_id + tenant_id
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPrices
     * </pre>
     */
    public void getPrices(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetPricesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetProductsBySKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse>(
                  this, METHODID_GET_PRODUCTS_BY_SKU)))
          .addMethod(
            getGetProductsWithOfferBySKUMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse>(
                  this, METHODID_GET_PRODUCTS_WITH_OFFER_BY_SKU)))
          .addMethod(
            getGetProductsWithOfferByOfferIDsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse>(
                  this, METHODID_GET_PRODUCTS_WITH_OFFER_BY_OFFER_IDS)))
          .addMethod(
            getGetProductsByEANMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse>(
                  this, METHODID_GET_PRODUCTS_BY_EAN)))
          .addMethod(
            getGetProductsWithOfferByEANMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse>(
                  this, METHODID_GET_PRODUCTS_WITH_OFFER_BY_EAN)))
          .addMethod(
            getGetProductsByPermalinkMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse>(
                  this, METHODID_GET_PRODUCTS_BY_PERMALINK)))
          .addMethod(
            getGetProductsWithOfferByPermalinkMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse>(
                  this, METHODID_GET_PRODUCTS_WITH_OFFER_BY_PERMALINK)))
          .addMethod(
            getGetOffersBySKUANDStoreIDMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse>(
                  this, METHODID_GET_OFFERS_BY_SKUANDSTORE_ID)))
          .addMethod(
            getGetStocksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>(
                  this, METHODID_GET_STOCKS)))
          .addMethod(
            getGetOffersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse>(
                  this, METHODID_GET_OFFERS)))
          .addMethod(
            getGetPricesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest,
                product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse>(
                  this, METHODID_GET_PRICES)))
          .build();
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-product-hub.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-product-hub:3009
   * description:
   *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
   *    товарное вредложение (offer), цену (price) и остатки (stock).
   *    Предназначет для real-time нагрузки.
   *    Основные параметры запроса:
   *       sku - сбермартовский идентификатор описания товара
   *       store_id - сбермартовский идентификатор магазина
   *       tenant_id - сбермартовский идентификатор площадки
   *       retailer_id - сбермартовский идентификатор ритейлера
   *       retailer_sku - ритейлерский идентификатор товарного предложения
   * </pre>
   */
  public static final class ProductHubFrontDataStub extends io.grpc.stub.AbstractAsyncStub<ProductHubFrontDataStub> {
    private ProductHubFrontDataStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontDataStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontDataStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по sku в виде атрибутов.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"sku": ["16296"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенные атрибуты:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"keys":["brand", "image"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенную группу атрибутов размеченную флагом:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"flags":["show_as_characteristic"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     * </pre>
     */
    public void getProductsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsBySKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по sku.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferBySKU
     * </pre>
     */
    public void getProductsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsWithOfferBySKUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по offer_id (идентификатор базы mysql таблички offers).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    offer_ids - идентификатор офера
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"offer_ids": ["231461"], "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByOfferIDs
     * </pre>
     */
    public void getProductsWithOfferByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByOfferIDsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по EAN.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    EAN - штрихкод
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"ean": ["4000417048103"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByEAN
     * </pre>
     */
    public void getProductsByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsByEANMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по EAN.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    EAN - штрихкод
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"ean": ["4000417048103"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByEAN
     * </pre>
     */
    public void getProductsWithOfferByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByEANMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по permalink товаров (атрибут описания permalink).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    permalink - пермалинк товара
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByPermalink
     * </pre>
     */
    public void getProductsByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsByPermalinkMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по permalink товаров (атрибут описания permalink).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    permalink - пермалинк товара
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByPermalink
     * </pre>
     */
    public void getProductsWithOfferByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByPermalinkMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения по sku описания + store_id идентификатору магазина
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersBySKUANDStoreID
     * </pre>
     */
    public void getOffersBySKUANDStoreID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOffersBySKUANDStoreIDMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает остатки по по sku описания + store_id идентификатору магазина
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocks
     * </pre>
     */
    public void getStocks(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStocksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения по retailer_sku идентификатор товарного предложения ритейлера + retailer_id идентификатору ритейлера
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    retailer_sku - идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffers
     * </pre>
     */
    public void getOffers(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOffersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Возвращает цены по sku + store_id + tenant_id
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPrices
     * </pre>
     */
    public void getPrices(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest request,
        io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPricesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-product-hub.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-product-hub:3009
   * description:
   *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
   *    товарное вредложение (offer), цену (price) и остатки (stock).
   *    Предназначет для real-time нагрузки.
   *    Основные параметры запроса:
   *       sku - сбермартовский идентификатор описания товара
   *       store_id - сбермартовский идентификатор магазина
   *       tenant_id - сбермартовский идентификатор площадки
   *       retailer_id - сбермартовский идентификатор ритейлера
   *       retailer_sku - ритейлерский идентификатор товарного предложения
   * </pre>
   */
  public static final class ProductHubFrontDataBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductHubFrontDataBlockingStub> {
    private ProductHubFrontDataBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontDataBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontDataBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по sku в виде атрибутов.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"sku": ["16296"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенные атрибуты:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"keys":["brand", "image"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенную группу атрибутов размеченную флагом:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"flags":["show_as_characteristic"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse getProductsBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsBySKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по sku.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferBySKU
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse getProductsWithOfferBySKU(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsWithOfferBySKUMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по offer_id (идентификатор базы mysql таблички offers).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    offer_ids - идентификатор офера
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"offer_ids": ["231461"], "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByOfferIDs
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse getProductsWithOfferByOfferIDs(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsWithOfferByOfferIDsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по EAN.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    EAN - штрихкод
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"ean": ["4000417048103"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByEAN
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse getProductsByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsByEANMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по EAN.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    EAN - штрихкод
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"ean": ["4000417048103"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByEAN
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse getProductsWithOfferByEAN(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsWithOfferByEANMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по permalink товаров (атрибут описания permalink).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    permalink - пермалинк товара
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByPermalink
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse getProductsByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsByPermalinkMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по permalink товаров (атрибут описания permalink).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    permalink - пермалинк товара
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByPermalink
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse getProductsWithOfferByPermalink(product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductsWithOfferByPermalinkMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения по sku описания + store_id идентификатору магазина
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersBySKUANDStoreID
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse getOffersBySKUANDStoreID(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOffersBySKUANDStoreIDMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает остатки по по sku описания + store_id идентификатору магазина
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocks
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse getStocks(product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStocksMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения по retailer_sku идентификатор товарного предложения ритейлера + retailer_id идентификатору ритейлера
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    retailer_sku - идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffers
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse getOffers(product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOffersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Возвращает цены по sku + store_id + tenant_id
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPrices
     * </pre>
     */
    public product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse getPrices(product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPricesMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   **
   * slack:
   *    #product-hub
   * swagger:
   *     https://paas-content-product-hub.sbmt.io/api
   * public prod grpc uri:
   *    paas-content-product-hub.sbmt.io:443
   * public stg grpc uri:
   *    paas-content-product-hub.gw-stage.sbmt.io:443
   * kuber prod grpc uri:
   *    base-product-hub.paas-content-product-hub:3009
   * description:
   *    Фронтовый сервис product-hub возвращающий описание товаров (product+attribute),
   *    товарное вредложение (offer), цену (price) и остатки (stock).
   *    Предназначет для real-time нагрузки.
   *    Основные параметры запроса:
   *       sku - сбермартовский идентификатор описания товара
   *       store_id - сбермартовский идентификатор магазина
   *       tenant_id - сбермартовский идентификатор площадки
   *       retailer_id - сбермартовский идентификатор ритейлера
   *       retailer_sku - ритейлерский идентификатор товарного предложения
   * </pre>
   */
  public static final class ProductHubFrontDataFutureStub extends io.grpc.stub.AbstractFutureStub<ProductHubFrontDataFutureStub> {
    private ProductHubFrontDataFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductHubFrontDataFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductHubFrontDataFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по sku в виде атрибутов.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"sku": ["16296"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенные атрибуты:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"keys":["brand", "image"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     *    Вернуть только определенную группу атрибутов размеченную флагом:
     *        grpcurl -d '{"sku": ["16296"], "display_attributes": [{"flags":["show_as_characteristic"]}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsBySKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse> getProductsBySKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsBySKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по sku.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferBySKU
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse> getProductsWithOfferBySKU(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsWithOfferBySKUMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по offer_id (идентификатор базы mysql таблички offers).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    offer_ids - идентификатор офера
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"offer_ids": ["231461"], "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByOfferIDs
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse> getProductsWithOfferByOfferIDs(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByOfferIDsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по EAN.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    EAN - штрихкод
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"ean": ["4000417048103"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByEAN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse> getProductsByEAN(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsByEANMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по EAN.
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    EAN - штрихкод
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"ean": ["4000417048103"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByEAN
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse> getProductsWithOfferByEAN(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByEANMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров по permalink товаров (атрибут описания permalink).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    permalink - пермалинк товара
     * Примеры:
     *    Вернуть все атрибуты:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsByPermalink
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse> getProductsByPermalink(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsByPermalinkMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает описание товаров c ценой, стоком и товарным предложением по permalink товаров (атрибут описания permalink).
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    permalink - пермалинк товара
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть все атрибуты + сток + цену + оффер:
     *        grpcurl -d '{"permalink": ["shokolad-ritter-sport-mini-bunter-mix-7-vkusov-16-67-g-h-84-sht-691a0cc"], "store_id": "1", "tenant_id": "sbermarket"}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetProductsWithOfferByPermalink
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse> getProductsWithOfferByPermalink(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductsWithOfferByPermalinkMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения по sku описания + store_id идентификатору магазина
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffersBySKUANDStoreID
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse> getOffersBySKUANDStoreID(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOffersBySKUANDStoreIDMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает остатки по по sku описания + store_id идентификатору магазина
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     * Примеры:
     *    Вернуть остатки (stock):
     *        grpcurl -d '{"stocks": [{"sku": "16296", "store_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetStocks
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse> getStocks(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStocksMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает товарные предложения по retailer_sku идентификатор товарного предложения ритейлера + retailer_id идентификатору ритейлера
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    retailer_sku - идентификатор товарного предлоежния ритейлера
     *    retailer_id - идентификатор ритейлера
     * Примеры:
     *    Вернуть товарное предложение (offer):
     *        grpcurl -d '{"offers": [{"retailer_sku": "337582", "retailer_id": "1"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetOffers
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse> getOffers(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOffersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Возвращает цены по sku + store_id + tenant_id
     * Полный набор атрибутов и флагов для всех сущностей описан здесь https://instamart.atlassian.net/wiki/spaces/CP/pages/2951120403/product-hub
     * Обязательные параметры:
     *    sku - идентификатор описания
     *    store_id - идентификатор магазина
     *    tenant_id - идентификатор площадки
     * Примеры:
     *    Вернуть цены (price):
     *        grpcurl -d '{"prices": [{"sku": ["16296"], "store_id": "1", "tenant_id": "sbermarket"}]}' paas-content-product-hub.sbmt.io:443 product_hub_front_data.ProductHubFrontData.GetPrices
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse> getPrices(
        product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPricesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCTS_BY_SKU = 0;
  private static final int METHODID_GET_PRODUCTS_WITH_OFFER_BY_SKU = 1;
  private static final int METHODID_GET_PRODUCTS_WITH_OFFER_BY_OFFER_IDS = 2;
  private static final int METHODID_GET_PRODUCTS_BY_EAN = 3;
  private static final int METHODID_GET_PRODUCTS_WITH_OFFER_BY_EAN = 4;
  private static final int METHODID_GET_PRODUCTS_BY_PERMALINK = 5;
  private static final int METHODID_GET_PRODUCTS_WITH_OFFER_BY_PERMALINK = 6;
  private static final int METHODID_GET_OFFERS_BY_SKUANDSTORE_ID = 7;
  private static final int METHODID_GET_STOCKS = 8;
  private static final int METHODID_GET_OFFERS = 9;
  private static final int METHODID_GET_PRICES = 10;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductHubFrontDataImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductHubFrontDataImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCTS_BY_SKU:
          serviceImpl.getProductsBySKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsBySKUResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_WITH_OFFER_BY_SKU:
          serviceImpl.getProductsWithOfferBySKU((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKURequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferBySKUResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_WITH_OFFER_BY_OFFER_IDS:
          serviceImpl.getProductsWithOfferByOfferIDs((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByOfferIDsResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_BY_EAN:
          serviceImpl.getProductsByEAN((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByEANResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_WITH_OFFER_BY_EAN:
          serviceImpl.getProductsWithOfferByEAN((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByEANResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_BY_PERMALINK:
          serviceImpl.getProductsByPermalink((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsByPermalinkResponse>) responseObserver);
          break;
        case METHODID_GET_PRODUCTS_WITH_OFFER_BY_PERMALINK:
          serviceImpl.getProductsWithOfferByPermalink((product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetProductsWithOfferByPermalinkResponse>) responseObserver);
          break;
        case METHODID_GET_OFFERS_BY_SKUANDSTORE_ID:
          serviceImpl.getOffersBySKUANDStoreID((product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersBySKUANDStoreIDResponse>) responseObserver);
          break;
        case METHODID_GET_STOCKS:
          serviceImpl.getStocks((product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetStocksResponse>) responseObserver);
          break;
        case METHODID_GET_OFFERS:
          serviceImpl.getOffers((product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetOffersResponse>) responseObserver);
          break;
        case METHODID_GET_PRICES:
          serviceImpl.getPrices((product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesRequest) request,
              (io.grpc.stub.StreamObserver<product_hub_front_data.ProductHubFrontDataOuterClass.GetPricesResponse>) responseObserver);
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

  private static abstract class ProductHubFrontDataBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductHubFrontDataBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return product_hub_front_data.ProductHubFrontDataOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductHubFrontData");
    }
  }

  private static final class ProductHubFrontDataFileDescriptorSupplier
      extends ProductHubFrontDataBaseDescriptorSupplier {
    ProductHubFrontDataFileDescriptorSupplier() {}
  }

  private static final class ProductHubFrontDataMethodDescriptorSupplier
      extends ProductHubFrontDataBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductHubFrontDataMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProductHubFrontDataGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductHubFrontDataFileDescriptorSupplier())
              .addMethod(getGetProductsBySKUMethod())
              .addMethod(getGetProductsWithOfferBySKUMethod())
              .addMethod(getGetProductsWithOfferByOfferIDsMethod())
              .addMethod(getGetProductsByEANMethod())
              .addMethod(getGetProductsWithOfferByEANMethod())
              .addMethod(getGetProductsByPermalinkMethod())
              .addMethod(getGetProductsWithOfferByPermalinkMethod())
              .addMethod(getGetOffersBySKUANDStoreIDMethod())
              .addMethod(getGetStocksMethod())
              .addMethod(getGetOffersMethod())
              .addMethod(getGetPricesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
